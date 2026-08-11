package com.rotati.service;

import com.rotati.dto.AreaScore;
import com.rotati.dto.AreaRelacionadaView;
import com.rotati.dto.QuizSubmission;
import com.rotati.dto.ResultadoView;
import com.rotati.model.AreaTi;
import com.rotati.model.Pergunta;
import com.rotati.model.PerguntaPeso;
import com.rotati.model.Resposta;
import com.rotati.model.Resultado;
import com.rotati.model.TipoPergunta;
import com.rotati.model.Usuario;
import com.rotati.repository.PerguntaRepository;
import com.rotati.repository.RespostaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private static final double LIMIAR_DESEMPATE = 8.0;
    private static final int MAXIMO_PERGUNTAS_DESEMPATE = 3;
    private static final int RESPOSTA_MINIMA = -2;
    private static final int RESPOSTA_MAXIMA = 2;

    private static final Map<String, String> ROTULOS_CATEGORIAS = new LinkedHashMap<>();

    static {
        ROTULOS_CATEGORIAS.put("criatividade", "Criatividade para construir solucoes");
        ROTULOS_CATEGORIAS.put("logica", "Raciocinio logico");
        ROTULOS_CATEGORIAS.put("analise", "Pensamento analitico");
        ROTULOS_CATEGORIAS.put("investigacao", "Curiosidade investigativa");
        ROTULOS_CATEGORIAS.put("detalhe", "Atencao aos detalhes");
        ROTULOS_CATEGORIAS.put("operacao", "Interesse por operacao tecnica");
        ROTULOS_CATEGORIAS.put("organizacao", "Organizacao e confiabilidade");
        ROTULOS_CATEGORIAS.put("design", "Sensibilidade visual");
        ROTULOS_CATEGORIAS.put("empatia", "Empatia com usuarios");
        ROTULOS_CATEGORIAS.put("narrativa", "Criatividade interativa");
        ROTULOS_CATEGORIAS.put("curiosidade", "Curiosidade por novas tecnologias");
        ROTULOS_CATEGORIAS.put("experimentacao", "Aprendizado por experimentacao");
        ROTULOS_CATEGORIAS.put("lideranca", "Lideranca e iniciativa");
        ROTULOS_CATEGORIAS.put("comunicacao", "Comunicacao clara");
        ROTULOS_CATEGORIAS.put("persistencia", "Persistencia para resolver problemas");
    }

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

    @Transactional(readOnly = true)
    public List<Pergunta> listarPerguntas() {
        return perguntaRepository.findAllByTipoAndAtivaTrueOrderByIdAsc(TipoPergunta.BASE);
    }

    @Transactional(readOnly = true)
    public long totalPerguntas() {
        return listarPerguntas().size();
    }

    @Transactional(readOnly = true)
    public boolean todasPerguntasRespondidas(QuizSubmission submission) {
        return perguntasRespondidas(listarPerguntas(), submission);
    }

    public boolean perguntasRespondidas(List<Pergunta> perguntas, QuizSubmission submission) {
        if (submission == null || submission.getRespostas() == null) {
            return false;
        }

        return perguntas.stream().allMatch(pergunta -> {
            Integer valor = submission.getRespostas().get(pergunta.getId());
            return valor != null && valor >= RESPOSTA_MINIMA && valor <= RESPOSTA_MAXIMA;
        });
    }

    @Transactional(readOnly = true)
    public List<AreaScore> analisar(QuizSubmission submission) {
        List<Pergunta> perguntasRespondidas = perguntaRepository.findAllByOrderByIdAsc()
                .stream()
                .filter(pergunta -> submission.getRespostas().containsKey(pergunta.getId()))
                .toList();
        return calcularRanking(perguntasRespondidas.stream()
                .map(pergunta -> new RespostaCalculada(pergunta, submission.getRespostas().get(pergunta.getId())))
                .toList());
    }

    @Transactional(readOnly = true)
    public List<Pergunta> selecionarPerguntasDesempate(QuizSubmission submission) {
        List<AreaScore> ranking = analisar(submission);
        double maiorCompatibilidade = ranking.getFirst().getCompatibilidade();
        List<AreaTi> areasCandidatas = ranking.stream()
                .filter(score -> maiorCompatibilidade - score.getCompatibilidade() < LIMIAR_DESEMPATE)
                .map(AreaScore::getArea)
                .toList();

        if (areasCandidatas.size() < 2) {
            return List.of();
        }

        int totalPerguntas = Math.min(
                MAXIMO_PERGUNTAS_DESEMPATE,
                Math.max(2, areasCandidatas.size() - 1)
        );
        List<Pergunta> disponiveis = new ArrayList<>(
                perguntaRepository.findAllByTipoAndAtivaTrueOrderByIdAsc(TipoPergunta.DESEMPATE)
        );
        List<ParAreas> paresPendentes = paresEntre(areasCandidatas);
        List<Pergunta> selecionadas = new ArrayList<>();

        while (selecionadas.size() < totalPerguntas && !disponiveis.isEmpty()) {
            Pergunta melhorPergunta = disponiveis.stream()
                    .filter(pergunta -> discriminacaoTotal(pergunta, areasCandidatas) > 0)
                    .sorted(Comparator
                            .comparingInt((Pergunta pergunta) -> paresNovosCobertos(pergunta, paresPendentes))
                            .reversed()
                            .thenComparing(Comparator.comparingInt(
                                    (Pergunta pergunta) -> discriminacaoTotal(pergunta, areasCandidatas)
                            ).reversed())
                            .thenComparing(Pergunta::getCodigo))
                    .findFirst()
                    .orElse(null);

            if (melhorPergunta == null) {
                break;
            }

            selecionadas.add(melhorPergunta);
            disponiveis.remove(melhorPergunta);
            paresPendentes.removeIf(par -> discriminacao(melhorPergunta, par.primeira(), par.segunda()) > 0);
        }

        return List.copyOf(selecionadas);
    }

    @Transactional(readOnly = true)
    public List<Pergunta> buscarPerguntasPorIds(List<Long> ids) {
        Map<Long, Pergunta> perguntasPorId = perguntaRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(Pergunta::getId, Function.identity()));

        return ids.stream()
                .map(id -> {
                    Pergunta pergunta = perguntasPorId.get(id);
                    if (pergunta == null) {
                        throw new NoSuchElementException("Pergunta de desempate nao encontrada.");
                    }
                    return pergunta;
                })
                .toList();
    }

    @Transactional
    public Resultado processar(QuizSubmission submission) {
        List<Pergunta> perguntasBase = listarPerguntas();
        if (!perguntasRespondidas(perguntasBase, submission)) {
            throw new IllegalArgumentException("Todas as perguntas principais devem ser respondidas.");
        }

        List<Pergunta> perguntasRespondidas = perguntaRepository.findAllByOrderByIdAsc()
                .stream()
                .filter(pergunta -> submission.getRespostas().containsKey(pergunta.getId()))
                .toList();

        Usuario usuario = usuarioRepository.save(new Usuario(
                submission.getNome().trim(),
                submission.getIdade(),
                submission.getEscola().trim()
        ));
        List<Resposta> respostas = perguntasRespondidas.stream()
                .map(pergunta -> new Resposta(usuario, pergunta, submission.getRespostas().get(pergunta.getId())))
                .toList();
        respostaRepository.saveAll(respostas);

        List<AreaScore> ranking = calcularRanking(respostas.stream()
                .map(resposta -> new RespostaCalculada(resposta.getPergunta(), resposta.getValor()))
                .toList());
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
        List<AreaScore> ranking = calcularRanking(respostas.stream()
                .map(resposta -> new RespostaCalculada(resposta.getPergunta(), resposta.getValor()))
                .toList());
        AreaScore principal = ranking.stream()
                .filter(score -> score.getArea().getSlug().equals(resultado.getAreaSlug()))
                .findFirst()
                .orElse(ranking.getFirst());

        return new ResultadoView(
                resultado,
                usuario,
                principal,
                ranking,
                montarAreasRelacionadas(ranking, principal, respostas),
                identificarDestaques(respostas),
                classificarConfianca(ranking)
        );
    }

    @Transactional
    public void registrarSatisfacao(Long resultadoId, Integer satisfacao) {
        Resultado resultado = resultadoRepository.findById(resultadoId)
                .orElseThrow(() -> new NoSuchElementException("Resultado nao encontrado."));
        resultado.setSatisfacao(satisfacao);
        resultadoRepository.save(resultado);
    }

    private List<AreaScore> calcularRanking(List<RespostaCalculada> respostas) {
        Map<AreaTi, Integer> pontosPorArea = new EnumMap<>(AreaTi.class);
        Map<AreaTi, Integer> maximoPorArea = new EnumMap<>(AreaTi.class);

        for (AreaTi area : AreaTi.values()) {
            pontosPorArea.put(area, 0);
            maximoPorArea.put(area, 0);
        }

        for (RespostaCalculada resposta : respostas) {
            int valor = resposta.valor() == null ? 0 : resposta.valor();
            for (PerguntaPeso peso : resposta.pergunta().getPesos()) {
                AreaTi area = AreaTi.fromSlug(peso.getAreaSlug())
                        .orElseThrow(() -> new IllegalStateException("Area invalida no peso da pergunta."));
                pontosPorArea.merge(area, valor * peso.getPeso(), Integer::sum);
                maximoPorArea.merge(area, Math.abs(peso.getPeso()) * RESPOSTA_MAXIMA, Integer::sum);
            }
        }

        return pontosPorArea.entrySet()
                .stream()
                .map(entry -> {
                    AreaTi area = entry.getKey();
                    int pontos = entry.getValue();
                    int maximo = maximoPorArea.get(area);
                    double compatibilidade = maximo == 0
                            ? 50.0
                            : 50.0 + ((pontos * 50.0) / maximo);
                    compatibilidade = Math.max(0.0, Math.min(100.0, compatibilidade));
                    return new AreaScore(area, pontos, compatibilidade);
                })
                .sorted(Comparator.comparingDouble(AreaScore::getCompatibilidade).reversed()
                        .thenComparing(score -> score.getArea().getTitulo()))
                .toList();
    }

    private int discriminacao(Pergunta pergunta, AreaTi primeira, AreaTi segunda) {
        return Math.abs(pergunta.getPeso(primeira) - pergunta.getPeso(segunda));
    }

    private int discriminacaoTotal(Pergunta pergunta, List<AreaTi> areas) {
        return paresEntre(areas).stream()
                .mapToInt(par -> discriminacao(pergunta, par.primeira(), par.segunda()))
                .sum();
    }

    private int paresNovosCobertos(Pergunta pergunta, List<ParAreas> paresPendentes) {
        return (int) paresPendentes.stream()
                .filter(par -> discriminacao(pergunta, par.primeira(), par.segunda()) > 0)
                .count();
    }

    private List<ParAreas> paresEntre(List<AreaTi> areas) {
        List<ParAreas> pares = new ArrayList<>();
        for (int primeira = 0; primeira < areas.size(); primeira++) {
            for (int segunda = primeira + 1; segunda < areas.size(); segunda++) {
                pares.add(new ParAreas(areas.get(primeira), areas.get(segunda)));
            }
        }
        return pares;
    }

    private List<String> identificarDestaques(List<Resposta> respostas) {
        Map<String, Long> frequencias = respostas.stream()
                .filter(resposta -> resposta.getValor() != null && resposta.getValor() > 0)
                .collect(Collectors.groupingBy(
                        resposta -> resposta.getPergunta().getCategoria(),
                        Collectors.counting()
                ));

        List<String> destaques = frequencias.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(3)
                .map(entry -> ROTULOS_CATEGORIAS.getOrDefault(entry.getKey(), entry.getKey()))
                .toList();

        return destaques.isEmpty() ? List.of("Interesses ainda equilibrados") : destaques;
    }

    private List<AreaRelacionadaView> montarAreasRelacionadas(
            List<AreaScore> ranking,
            AreaScore principal,
            List<Resposta> respostas
    ) {
        return ranking.stream()
                .filter(item -> item.getArea() != principal.getArea())
                .limit(3)
                .map(item -> new AreaRelacionadaView(
                        item,
                        explicarAreaRelacionada(item.getArea(), respostas)
                ))
                .toList();
    }

    private String explicarAreaRelacionada(AreaTi area, List<Resposta> respostas) {
        List<String> sinais = sinaisDaArea(area, respostas);
        if (sinais.isEmpty()) {
            return "Ela ficou proxima porque suas respostas tambem combinam com esse perfil.";
        }
        if (sinais.size() == 1) {
            return "Ela apareceu por causa do seu interesse em " + sinais.getFirst() + ".";
        }
        return "Ela apareceu porque suas respostas tambem indicaram "
                + sinais.get(0)
                + " e "
                + sinais.get(1)
                + ".";
    }

    private List<String> sinaisDaArea(AreaTi area, List<Resposta> respostas) {
        Map<String, Integer> pontosPorCategoria = new HashMap<>();

        for (Resposta resposta : respostas) {
            int valor = resposta.getValor() == null ? 0 : resposta.getValor();
            for (PerguntaPeso peso : resposta.getPergunta().getPesos()) {
                if (!peso.getAreaSlug().equals(area.getSlug())) {
                    continue;
                }

                int contribuicao = valor * peso.getPeso();
                if (contribuicao <= 0) {
                    continue;
                }

                pontosPorCategoria.merge(resposta.getPergunta().getCategoria(), contribuicao, Integer::sum);
            }
        }

        return pontosPorCategoria.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry::getKey))
                .limit(2)
                .map(entry -> ROTULOS_CATEGORIAS.getOrDefault(entry.getKey(), entry.getKey()))
                .toList();
    }

    private String classificarConfianca(List<AreaScore> ranking) {
        double diferenca = ranking.get(0).getCompatibilidade() - ranking.get(1).getCompatibilidade();
        if (diferenca >= 20.0) {
            return "Alta";
        }
        if (diferenca >= 10.0) {
            return "Moderada";
        }
        return "Em exploracao";
    }

    private record RespostaCalculada(Pergunta pergunta, Integer valor) {
    }

    private record ParAreas(AreaTi primeira, AreaTi segunda) {
    }
}
