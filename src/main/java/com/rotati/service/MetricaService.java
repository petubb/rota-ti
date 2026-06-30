package com.rotati.service;

import com.rotati.dto.DashboardAreaResumo;
import com.rotati.dto.DashboardContaRecente;
import com.rotati.dto.DashboardMetricas;
import com.rotati.dto.DashboardPerguntasResumo;
import com.rotati.dto.DashboardResultadoRecente;
import com.rotati.model.AreaTi;
import com.rotati.model.Conta;
import com.rotati.model.TipoPergunta;
import com.rotati.model.Resultado;
import com.rotati.repository.ContaRepository;
import com.rotati.repository.PerguntaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricaService {

    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final UsuarioRepository usuarioRepository;
    private final ResultadoRepository resultadoRepository;
    private final ContaRepository contaRepository;
    private final PerguntaRepository perguntaRepository;

    public MetricaService(
            UsuarioRepository usuarioRepository,
            ResultadoRepository resultadoRepository,
            ContaRepository contaRepository,
            PerguntaRepository perguntaRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.resultadoRepository = resultadoRepository;
        this.contaRepository = contaRepository;
        this.perguntaRepository = perguntaRepository;
    }

    @Transactional(readOnly = true)
    public DashboardMetricas gerarDashboard() {
        List<Resultado> resultados = resultadoRepository.findAll();
        long totalResultados = resultados.size();

        Map<String, Long> distribuicao = resultados.stream()
                .collect(Collectors.groupingBy(Resultado::getAreaSlug, Collectors.counting()));

        Map<String, Long> distribuicaoComTitulos = new LinkedHashMap<>();
        List<DashboardAreaResumo> areas = new ArrayList<>();
        for (AreaTi area : AreaTi.values()) {
            long totalArea = distribuicao.getOrDefault(area.getSlug(), 0L);
            distribuicaoComTitulos.put(area.getTitulo(), totalArea);
            areas.add(new DashboardAreaResumo(
                    area.getTitulo(),
                    area.getSlug(),
                    totalArea,
                    percentual(totalArea, totalResultados)
            ));
        }

        double mediaSatisfacao = resultados.stream()
                .filter(resultado -> resultado.getSatisfacao() != null)
                .mapToInt(Resultado::getSatisfacao)
                .average()
                .orElse(0.0);

        double mediaScore = resultados.stream()
                .mapToDouble(Resultado::getScore)
                .average()
                .orElse(0.0);

        long resultadosSalvos = resultadoRepository.countByContaIsNotNull();
        long satisfacoesRecebidas = resultados.stream()
                .filter(resultado -> resultado.getSatisfacao() != null)
                .count();
        long totalContas = contaRepository.count();

        return new DashboardMetricas(
                usuarioRepository.count(),
                totalResultados,
                totalContas,
                contaRepository.countByAtivoTrue(),
                contaRepository.countByBloqueadoAteAfter(LocalDateTime.now()),
                resultadosSalvos,
                satisfacoesRecebidas,
                mediaSatisfacao,
                mediaScore,
                percentual(resultadosSalvos, totalResultados),
                distribuicaoComTitulos,
                areas,
                resultadosRecentes(),
                contasRecentes(),
                perguntasResumo()
        );
    }

    private List<DashboardResultadoRecente> resultadosRecentes() {
        return resultadoRepository.findTop8ByOrderByCreatedAtDesc().stream()
                .map(resultado -> {
                    AreaTi area = AreaTi.fromSlug(resultado.getAreaSlug()).orElse(null);
                    return new DashboardResultadoRecente(
                            resultado.getId(),
                            area == null ? resultado.getAreaSlug() : area.getTitulo(),
                            resultado.getAreaSlug(),
                            resultado.getScore(),
                            resultado.getSatisfacao(),
                            resultado.getUsuario().getIdade(),
                            resultado.getUsuario().getEscola(),
                            resultado.getConta() == null ? "Visitante" : resultado.getConta().getNome(),
                            formatar(resultado.getCreatedAt())
                    );
                })
                .toList();
    }

    private List<DashboardContaRecente> contasRecentes() {
        return contaRepository.findTop6ByOrderByCreatedAtDesc().stream()
                .map(conta -> new DashboardContaRecente(
                        conta.getId(),
                        conta.getNome(),
                        conta.getEmail(),
                        conta.getPapel().name(),
                        conta.isAtivo(),
                        conta.estaBloqueada(),
                        contarResultados(conta),
                        formatar(conta.getCreatedAt())
                ))
                .toList();
    }

    private DashboardPerguntasResumo perguntasResumo() {
        return new DashboardPerguntasResumo(
                perguntaRepository.count(),
                perguntaRepository.countByTipo(TipoPergunta.BASE),
                perguntaRepository.countByTipo(TipoPergunta.DESEMPATE)
        );
    }

    private long contarResultados(Conta conta) {
        return resultadoRepository.countByConta(conta);
    }

    private double percentual(long parte, long total) {
        if (total == 0) {
            return 0.0;
        }
        return (parte * 100.0) / total;
    }

    private String formatar(LocalDateTime data) {
        return data == null ? "-" : data.format(DATA_HORA);
    }
}
