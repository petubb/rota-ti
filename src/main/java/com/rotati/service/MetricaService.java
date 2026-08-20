package com.rotati.service;

import com.rotati.dto.DashboardAreaResumo;
import com.rotati.dto.DashboardAtividadeResumo;
import com.rotati.dto.DashboardContaRecente;
import com.rotati.dto.DashboardConversaoQuiz;
import com.rotati.dto.DashboardDistribuicaoResumo;
import com.rotati.dto.DashboardFiltros;
import com.rotati.dto.DashboardMetricas;
import com.rotati.dto.DashboardPerguntasResumo;
import com.rotati.dto.DashboardResultadoRecente;
import com.rotati.model.AreaTi;
import com.rotati.model.Conta;
import com.rotati.model.QuizTentativa;
import com.rotati.model.Resultado;
import com.rotati.model.TipoPergunta;
import com.rotati.repository.ContaRepository;
import com.rotati.repository.PerguntaRepository;
import com.rotati.repository.QuizTentativaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class MetricaService {

    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter DIA_MES = DateTimeFormatter.ofPattern("dd/MM");
    private static final int MINUTOS_EM_ANDAMENTO = 30;

    private final UsuarioRepository usuarioRepository;
    private final ResultadoRepository resultadoRepository;
    private final ContaRepository contaRepository;
    private final PerguntaRepository perguntaRepository;
    private final QuizTentativaRepository tentativaRepository;

    public MetricaService(
            UsuarioRepository usuarioRepository,
            ResultadoRepository resultadoRepository,
            ContaRepository contaRepository,
            PerguntaRepository perguntaRepository,
            QuizTentativaRepository tentativaRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.resultadoRepository = resultadoRepository;
        this.contaRepository = contaRepository;
        this.perguntaRepository = perguntaRepository;
        this.tentativaRepository = tentativaRepository;
    }

    @Transactional(readOnly = true)
    public DashboardMetricas gerarDashboard() {
        return gerarDashboard(DashboardFiltros.padrao());
    }

    @Transactional(readOnly = true)
    public DashboardMetricas gerarDashboard(DashboardFiltros filtros) {
        List<Resultado> resultados = resultadoRepository.findAll();
        List<Conta> contas = contaRepository.findAll();
        List<QuizTentativa> tentativas = tentativaRepository.findAll();
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
        long resultadosSalvos = resultados.stream()
                .filter(resultado -> resultado.getConta() != null)
                .count();
        long satisfacoesRecebidas = resultados.stream()
                .filter(resultado -> resultado.getSatisfacao() != null)
                .count();
        long resultadosVisitantes = totalResultados - resultadosSalvos;
        double idadeMedia = resultados.stream()
                .mapToInt(resultado -> resultado.getUsuario().getIdade())
                .average()
                .orElse(0.0);

        return new DashboardMetricas(
                usuarioRepository.count(),
                totalResultados,
                contas.size(),
                contas.stream().filter(Conta::isAtivo).count(),
                contas.stream().filter(Conta::estaBloqueada).count(),
                resultadosSalvos,
                satisfacoesRecebidas,
                mediaSatisfacao,
                mediaScore,
                percentual(resultadosSalvos, totalResultados),
                resultadosVisitantes,
                idadeMedia,
                distribuicaoComTitulos,
                areas,
                atividadePeriodo(resultados, filtros.getDiasAtividade()),
                faixasEtarias(resultados),
                idadesDetalhadas(resultados),
                satisfacaoDistribuicao(resultados, satisfacoesRecebidas),
                resultadosRecentes(resultados, filtros.getDiasResultados()),
                contasRecentes(contas, resultados, filtros.getDiasContas()),
                perguntasResumo(),
                conversaoQuiz(tentativas)
        );
    }

    private List<DashboardAtividadeResumo> atividadePeriodo(List<Resultado> resultados, int dias) {
        LocalDate hoje = LocalDate.now();
        LocalDate primeiroDia = hoje.minusDays(dias - 1L);
        Map<LocalDate, Long> porDia = resultados.stream()
                .filter(resultado -> resultado.getCreatedAt() != null)
                .map(Resultado::getCreatedAt)
                .filter(data -> !data.toLocalDate().isBefore(primeiroDia))
                .filter(data -> !data.toLocalDate().isAfter(hoje))
                .collect(Collectors.groupingBy(
                        LocalDateTime::toLocalDate,
                        Collectors.counting()
                ));

        long maiorTotal = porDia.values().stream().mapToLong(Long::longValue).max().orElse(0L);
        List<DashboardAtividadeResumo> atividade = new ArrayList<>();
        for (int diasAtras = dias - 1; diasAtras >= 0; diasAtras--) {
            LocalDate data = hoje.minusDays(diasAtras);
            long total = porDia.getOrDefault(data, 0L);
            double altura = maiorTotal == 0 ? 0.0 : percentual(total, maiorTotal);
            atividade.add(new DashboardAtividadeResumo(
                    abreviarDia(data),
                    data.format(DIA_MES),
                    total,
                    altura
            ));
        }
        return atividade;
    }

    private List<DashboardDistribuicaoResumo> faixasEtarias(List<Resultado> resultados) {
        long total = resultados.size();
        long ate15 = contarIdades(resultados, 12, 15);
        long ate18 = contarIdades(resultados, 16, 18);
        long ate24 = contarIdades(resultados, 19, 24);
        long acima24 = resultados.stream()
                .filter(resultado -> resultado.getUsuario().getIdade() >= 25)
                .count();

        return List.of(
                distribuicao("12 a 15", ate15, total),
                distribuicao("16 a 18", ate18, total),
                distribuicao("19 a 24", ate24, total),
                distribuicao("25 ou mais", acima24, total)
        );
    }

    private List<DashboardDistribuicaoResumo> idadesDetalhadas(List<Resultado> resultados) {
        Map<Integer, Long> porIdade = resultados.stream()
                .collect(Collectors.groupingBy(
                        resultado -> resultado.getUsuario().getIdade(),
                        TreeMap::new,
                        Collectors.counting()
                ));
        return porIdade.entrySet().stream()
                .map(entry -> distribuicao(
                        entry.getKey() + (entry.getKey() == 1 ? " ano" : " anos"),
                        entry.getValue(),
                        resultados.size()
                ))
                .toList();
    }

    private List<DashboardDistribuicaoResumo> satisfacaoDistribuicao(
            List<Resultado> resultados,
            long satisfacoesRecebidas
    ) {
        List<DashboardDistribuicaoResumo> distribuicao = new ArrayList<>();
        for (int nota = 5; nota >= 1; nota--) {
            int notaAtual = nota;
            long total = resultados.stream()
                    .filter(resultado -> resultado.getSatisfacao() != null)
                    .filter(resultado -> resultado.getSatisfacao() == notaAtual)
                    .count();
            distribuicao.add(distribuicao(nota + " estrelas", total, satisfacoesRecebidas));
        }
        return distribuicao;
    }

    private List<DashboardResultadoRecente> resultadosRecentes(List<Resultado> resultados, int dias) {
        LocalDateTime limite = inicioDoPeriodo(dias);
        return resultados.stream()
                .filter(resultado -> dentroDoPeriodo(resultado.getCreatedAt(), limite))
                .sorted(Comparator.comparing(
                        Resultado::getCreatedAt,
                        Comparator.nullsFirst(Comparator.naturalOrder())
                ).reversed())
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
                            formatar(resultado.getCreatedAt()),
                            ordenarData(resultado.getCreatedAt())
                    );
                })
                .toList();
    }

    private List<DashboardContaRecente> contasRecentes(
            List<Conta> contas,
            List<Resultado> resultados,
            int dias
    ) {
        LocalDateTime limite = inicioDoPeriodo(dias);
        Map<Long, Long> resultadosPorConta = new HashMap<>();
        resultados.stream()
                .filter(resultado -> resultado.getConta() != null)
                .forEach(resultado -> resultadosPorConta.merge(
                        resultado.getConta().getId(),
                        1L,
                        Long::sum
                ));

        return contas.stream()
                .filter(conta -> dentroDoPeriodo(conta.getCreatedAt(), limite))
                .sorted(Comparator.comparing(
                        Conta::getCreatedAt,
                        Comparator.nullsFirst(Comparator.naturalOrder())
                ).reversed())
                .map(conta -> new DashboardContaRecente(
                        conta.getId(),
                        conta.getNome(),
                        conta.getEmail(),
                        conta.getPapel().name(),
                        conta.isAtivo(),
                        conta.estaBloqueada(),
                        resultadosPorConta.getOrDefault(conta.getId(), 0L),
                        formatar(conta.getCreatedAt()),
                        ordenarData(conta.getCreatedAt())
                ))
                .toList();
    }

    private DashboardConversaoQuiz conversaoQuiz(List<QuizTentativa> tentativas) {
        LocalDateTime limiteEmAndamento = LocalDateTime.now().minusMinutes(MINUTOS_EM_ANDAMENTO);
        long concluidas = tentativas.stream().filter(QuizTentativa::estaConcluida).count();
        long emAndamento = tentativas.stream()
                .filter(tentativa -> !tentativa.estaConcluida())
                .filter(tentativa -> !tentativa.getIniciadaEm().isBefore(limiteEmAndamento))
                .count();
        long naoConcluidas = tentativas.stream()
                .filter(tentativa -> !tentativa.estaConcluida())
                .filter(tentativa -> tentativa.getIniciadaEm().isBefore(limiteEmAndamento))
                .count();
        long encerradas = concluidas + naoConcluidas;
        return new DashboardConversaoQuiz(
                tentativas.size(),
                concluidas,
                naoConcluidas,
                emAndamento,
                percentual(concluidas, encerradas)
        );
    }

    private DashboardPerguntasResumo perguntasResumo() {
        long base = perguntaRepository.countByTipoAndAtivaTrue(TipoPergunta.BASE);
        long desempate = perguntaRepository.countByTipoAndAtivaTrue(TipoPergunta.DESEMPATE);
        return new DashboardPerguntasResumo(base + desempate, base, desempate);
    }

    private long contarIdades(List<Resultado> resultados, int minima, int maxima) {
        return resultados.stream()
                .mapToInt(resultado -> resultado.getUsuario().getIdade())
                .filter(idade -> idade >= minima && idade <= maxima)
                .count();
    }

    private DashboardDistribuicaoResumo distribuicao(String label, long total, long universo) {
        return new DashboardDistribuicaoResumo(label, total, percentual(total, universo));
    }

    private LocalDateTime inicioDoPeriodo(int dias) {
        return dias == 0 ? null : LocalDate.now().minusDays(dias - 1L).atStartOfDay();
    }

    private boolean dentroDoPeriodo(LocalDateTime data, LocalDateTime limite) {
        return data != null && (limite == null || !data.isBefore(limite));
    }

    private String abreviarDia(LocalDate data) {
        return switch (data.getDayOfWeek()) {
            case MONDAY -> "Seg";
            case TUESDAY -> "Ter";
            case WEDNESDAY -> "Qua";
            case THURSDAY -> "Qui";
            case FRIDAY -> "Sex";
            case SATURDAY -> "Sab";
            case SUNDAY -> "Dom";
        };
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

    private long ordenarData(LocalDateTime data) {
        if (data == null) {
            return 0L;
        }
        return data.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
