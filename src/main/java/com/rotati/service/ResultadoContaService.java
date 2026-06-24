package com.rotati.service;

import com.rotati.dto.ResultadoHistoricoView;
import com.rotati.model.AreaTi;
import com.rotati.model.Conta;
import com.rotati.model.Resultado;
import com.rotati.repository.ContaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.security.ContaPrincipal;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ResultadoContaService {

    private static final String RESULTADOS_DA_SESSAO = "resultadosDaSessao";
    private static final String RESULTADO_PENDENTE = "resultadoPendente";

    private final ResultadoRepository resultadoRepository;
    private final ContaRepository contaRepository;

    public ResultadoContaService(ResultadoRepository resultadoRepository, ContaRepository contaRepository) {
        this.resultadoRepository = resultadoRepository;
        this.contaRepository = contaRepository;
    }

    @Transactional
    public void registrarResultadoGerado(Resultado resultado, ContaPrincipal principal, HttpSession session) {
        if (principal == null) {
            resultadosDaSessao(session).add(resultado.getId());
            return;
        }

        Conta conta = buscarConta(principal.getId());
        resultado.setConta(conta);
        resultadoRepository.save(resultado);
    }

    @Transactional(readOnly = true)
    public void exigirAcesso(Long resultadoId, ContaPrincipal principal, HttpSession session) {
        Resultado resultado = buscarResultado(resultadoId);

        if (resultado.getConta() != null) {
            boolean proprietario = principal != null && resultado.getConta().getId().equals(principal.getId());
            if (!proprietario) {
                throw new ResultadoNaoEncontradoException();
            }
            return;
        }

        if (!resultadosDaSessao(session).contains(resultadoId)) {
            throw new ResultadoNaoEncontradoException();
        }
    }

    @Transactional(readOnly = true)
    public void prepararSalvamento(Long resultadoId, HttpSession session) {
        exigirAcesso(resultadoId, null, session);
        session.setAttribute(RESULTADO_PENDENTE, resultadoId);
    }

    @Transactional
    public void salvarResultado(Long resultadoId, ContaPrincipal principal, HttpSession session) {
        Resultado resultado = buscarResultado(resultadoId);
        if (resultado.getConta() == null && resultadosDaSessao(session).contains(resultadoId)) {
            resultado.setConta(buscarConta(principal.getId()));
            resultadoRepository.save(resultado);
            resultadosDaSessao(session).remove(resultadoId);
            return;
        }

        if (resultado.getConta() == null || !resultado.getConta().getId().equals(principal.getId())) {
            throw new ResultadoNaoEncontradoException();
        }
    }

    @Transactional
    public Long vincularResultadoPendente(ContaPrincipal principal, HttpSession session) {
        Object valorPendente = session.getAttribute(RESULTADO_PENDENTE);
        if (!(valorPendente instanceof Long resultadoId)) {
            return null;
        }

        Resultado resultado = resultadoRepository.findById(resultadoId).orElse(null);
        if (resultado == null) {
            session.removeAttribute(RESULTADO_PENDENTE);
            return null;
        }
        if (resultado.getConta() == null && resultadosDaSessao(session).contains(resultadoId)) {
            resultado.setConta(buscarConta(principal.getId()));
            resultadoRepository.save(resultado);
            resultadosDaSessao(session).remove(resultadoId);
        } else if (resultado.getConta() == null || !resultado.getConta().getId().equals(principal.getId())) {
            session.removeAttribute(RESULTADO_PENDENTE);
            throw new ResultadoNaoEncontradoException();
        }

        session.removeAttribute(RESULTADO_PENDENTE);
        return resultadoId;
    }

    @Transactional(readOnly = true)
    public List<ResultadoHistoricoView> listarHistorico(ContaPrincipal principal) {
        Conta conta = buscarConta(principal.getId());
        return resultadoRepository.findByContaOrderByCreatedAtDesc(conta)
                .stream()
                .map(resultado -> new ResultadoHistoricoView(
                        resultado.getId(),
                        AreaTi.fromSlug(resultado.getAreaSlug())
                                .orElseThrow(() -> new IllegalStateException("Area invalida no resultado.")),
                        resultado.getScore(),
                        resultado.getCreatedAt()
                ))
                .toList();
    }

    private Resultado buscarResultado(Long id) {
        return resultadoRepository.findById(id)
                .orElseThrow(ResultadoNaoEncontradoException::new);
    }

    private Conta buscarConta(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Conta nao encontrada."));
    }

    @SuppressWarnings("unchecked")
    private Set<Long> resultadosDaSessao(HttpSession session) {
        Object existente = session.getAttribute(RESULTADOS_DA_SESSAO);
        if (existente instanceof Set<?>) {
            return (Set<Long>) existente;
        }

        Set<Long> resultados = new HashSet<>();
        session.setAttribute(RESULTADOS_DA_SESSAO, resultados);
        return resultados;
    }
}
