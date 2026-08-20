package com.rotati.service;

import com.rotati.dto.DashboardAreaResumo;
import com.rotati.dto.DashboardAtividadeResumo;
import com.rotati.dto.DashboardContaRecente;
import com.rotati.dto.DashboardDistribuicaoResumo;
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
import java.time.LocalDate;
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
        long resultadosVisitantes = totalResultados - resultadosSalvos;
        double idadeMedia = resultados.stream()
                .mapToInt(resultado -> resultado.getUsuario().getIdade())
                .average()
                .orElse(0.0);

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
                resultadosVisitantes,
                idadeMedia,
                distribuicaoComTitulos,
                areas,
                atividadeSemanal(resultados),
                faixasEtarias(resultados),
                satisfacaoDistribuicao(resultados, satisfacoesRecebidas),
                resultadosRecentes(),
                contasRecentes(),
                perguntasResumo()
        );
    }

    private List<DashboardAtividadeResumo> atividadeSemanal(List<Resultado> resultados) {
        LocalDate hoje = LocalDate.now();
        Map<LocalDate, Long> porDia = resultados.stream()
                .filter(resultado -> resultado.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        resultado -> resultado.getCreatedAt().toLocalDate(),
                        Collectors.counting()
                ));

        long maiorTotal = 0;
        for (int diasAtras = 6; diasAtras >= 0; diasAtras--) {
            maiorTotal = Math.max(maiorTotal, porDia.getOrDefault(hoje.minusDays(diasAtras), 0L));
        }

        List<DashboardAtividadeResumo> atividade = new ArrayList<>();
        for (int diasAtras = 6; diasAtras >= 0; diasAtras--) {
            LocalDate data = hoje.minusDays(diasAtras);
            long total = porDia.getOrDefault(data, 0L);
            double altura = maiorTotal == 0 ? 0.0 : percentual(total, maiorTotal);
            atividade.add(new DashboardAtividadeResumo(
                    abreviarDia(data),
                    data.format(DateTimeFormatter.ofPattern("dd/MM")),
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

    private long contarIdades(List<Resultado> resultados, int minima, int maxima) {
        return resultados.stream()
                .mapToInt(resultado -> resultado.getUsuario().getIdade())
                .filter(idade -> idade >= minima && idade <= maxima)
                .count();
    }

    private DashboardDistribuicaoResumo distribuicao(String label, long total, long universo) {
        return new DashboardDistribuicaoResumo(label, total, percentual(total, universo));
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
        long base = perguntaRepository.countByTipoAndAtivaTrue(TipoPergunta.BASE);
        long desempate = perguntaRepository.countByTipoAndAtivaTrue(TipoPergunta.DESEMPATE);
        return new DashboardPerguntasResumo(
                base + desempate,
                base,
                desempate
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
