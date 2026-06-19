package com.rotati.service;

import com.rotati.dto.AreaScore;
import com.rotati.dto.QuizSubmission;
import com.rotati.dto.ResultadoView;
import com.rotati.model.AreaTi;
import com.rotati.model.Pergunta;
import com.rotati.model.Resposta;
import com.rotati.model.Resultado;
import com.rotati.model.Usuario;
import com.rotati.repository.PerguntaRepository;
import com.rotati.repository.RespostaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final PerguntaRepository perguntaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RespostaRepository respostaRepository;
    private final ResultadoRepository resultadoRepository;

    public QuizService(
            PerguntaRepository perguntaRepository,
            UsuarioRepository usuarioRepository,
            RespostaRepository respostaRepository,
            ResultadoRepository resultadoRepository
    ) {
        this.perguntaRepository = perguntaRepository;
        this.usuarioRepository = usuarioRepository;
        this.respostaRepository = respostaRepository;
        this.resultadoRepository = resultadoRepository;
    }

    public List<Pergunta> listarPerguntas() {
        return perguntaRepository.findAllByOrderByIdAsc();
    }

    public boolean todasPerguntasRespondidas(QuizSubmission submission) {
        List<Pergunta> perguntas = listarPerguntas();
        if (submission.getRespostas() == null) {
            return false;
        }

        return perguntas.stream()
                .allMatch(pergunta -> submission.getRespostas().containsKey(pergunta.getId()));
    }

    @Transactional
    public Resultado processar(QuizSubmission submission) {
        Usuario usuario = usuarioRepository.save(new Usuario(submission.getIdade(), submission.getEscola()));
        List<Pergunta> perguntas = listarPerguntas();

        List<Resposta> respostas = perguntas.stream()
                .map(pergunta -> new Resposta(usuario, pergunta, submission.getRespostas().get(pergunta.getId())))
                .toList();
        respostaRepository.saveAll(respostas);

        List<AreaScore> ranking = calcularRanking(respostas);
        AreaScore principal = ranking.getFirst();

        return resultadoRepository.save(new Resultado(
                usuario,
                principal.getArea().getSlug(),
                principal.getCompatibilidade()
        ));
    }

    @Transactional(readOnly = true)
    public ResultadoView buscarResultado(Long resultadoId) {
        Resultado resultado = resultadoRepository.findById(resultadoId)
                .orElseThrow(() -> new NoSuchElementException("Resultado nao encontrado."));
        Usuario usuario = resultado.getUsuario();
        List<Resposta> respostas = respostaRepository.findByUsuario(usuario);
        List<AreaScore> ranking = calcularRanking(respostas);
        AreaScore principal = ranking.stream()
                .filter(score -> score.getArea().getSlug().equals(resultado.getAreaSlug()))
                .findFirst()
                .orElse(ranking.getFirst());

        return new ResultadoView(resultado, usuario, principal, ranking);
    }

    @Transactional
    public void registrarSatisfacao(Long resultadoId, Integer satisfacao) {
        Resultado resultado = resultadoRepository.findById(resultadoId)
                .orElseThrow(() -> new NoSuchElementException("Resultado nao encontrado."));
        resultado.setSatisfacao(satisfacao);
        resultadoRepository.save(resultado);
    }

    private List<AreaScore> calcularRanking(List<Resposta> respostas) {
        Map<AreaTi, Integer> pontosPorArea = new EnumMap<>(AreaTi.class);
        Map<AreaTi, Integer> maximoPorArea = new EnumMap<>(AreaTi.class);

        for (AreaTi area : AreaTi.values()) {
            pontosPorArea.put(area, 0);
            maximoPorArea.put(area, 0);
        }

        for (Resposta resposta : respostas) {
            AreaTi area = AreaTi.fromSlug(resposta.getPergunta().getAreaSlug())
                    .orElseThrow(() -> new IllegalStateException("Area invalida em pergunta."));
            int valor = resposta.getValor() == null ? 0 : resposta.getValor();
            int pontos = Math.max(valor, 0);

            pontosPorArea.merge(area, pontos, Integer::sum);
            maximoPorArea.merge(area, 1, Integer::sum);
        }

        return pontosPorArea.entrySet()
                .stream()
                .map(entry -> {
                    AreaTi area = entry.getKey();
                    int pontos = entry.getValue();
                    int maximo = Math.max(maximoPorArea.get(area), 1);
                    double compatibilidade = (pontos * 100.0) / maximo;
                    return new AreaScore(area, pontos, compatibilidade);
                })
                .sorted(Comparator.comparingDouble(AreaScore::getCompatibilidade).reversed()
                        .thenComparing(score -> score.getArea().getTitulo()))
                .collect(Collectors.toList());
    }
}
