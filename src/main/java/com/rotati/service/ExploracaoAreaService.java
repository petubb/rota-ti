package com.rotati.service;

import com.rotati.dto.AreaExploracaoView;
import com.rotati.dto.DetalheAreaView;
import com.rotati.dto.RoadmapEtapaInterativaView;
import com.rotati.model.AreaTi;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ExploracaoAreaService {

    private static final Map<String, List<String>> CATEGORIAS = Map.ofEntries(
            Map.entry("desenvolvimento-software", List.of("construir", "analisar")),
            Map.entry("dados-bi", List.of("analisar", "organizar")),
            Map.entry("seguranca-cibernetica", List.of("analisar", "proteger")),
            Map.entry("infraestrutura-redes", List.of("proteger", "organizar")),
            Map.entry("ux-ui-design", List.of("construir", "pessoas")),
            Map.entry("game-design", List.of("construir", "pessoas")),
            Map.entry("inteligencia-artificial", List.of("construir", "analisar")),
            Map.entry("gestao-ti", List.of("organizar", "pessoas"))
    );

    private static final Map<String, List<String>> SINAIS_AFINIDADE = Map.ofEntries(
            Map.entry("desenvolvimento-software", List.of(
                    "Gosta de transformar uma ideia em algo que funciona",
                    "Tem paciencia para testar, errar e corrigir",
                    "Prefere resolver problemas por partes"
            )),
            Map.entry("dados-bi", List.of(
                    "Gosta de encontrar padroes e explicar o que eles significam",
                    "Se sente bem organizando informacoes",
                    "Prefere decidir com evidencias"
            )),
            Map.entry("seguranca-cibernetica", List.of(
                    "Costuma desconfiar, investigar e conferir detalhes",
                    "Gosta de entender como uma falha aconteceu",
                    "Leva regras e responsabilidade a serio"
            )),
            Map.entry("infraestrutura-redes", List.of(
                    "Gosta de descobrir por que algo parou de funcionar",
                    "Prefere atividades praticas e organizadas",
                    "Tem satisfacao em manter tudo estavel"
            )),
            Map.entry("ux-ui-design", List.of(
                    "Percebe quando uma tela ou processo esta confuso",
                    "Gosta de ouvir pessoas antes de criar",
                    "Une criatividade com organizacao visual"
            )),
            Map.entry("game-design", List.of(
                    "Gosta de imaginar regras, desafios e historias",
                    "Observa por que um jogo diverte ou frustra",
                    "Curte testar ideias com outras pessoas"
            )),
            Map.entry("inteligencia-artificial", List.of(
                    "Tem curiosidade por padroes, previsoes e experimentos",
                    "Gosta de combinar programacao com dados",
                    "Aceita testar hipoteses sem garantia de acerto"
            )),
            Map.entry("gestao-ti", List.of(
                    "Gosta de organizar prioridades e combinar proximos passos",
                    "Consegue conversar com pessoas de perfis diferentes",
                    "Prefere enxergar o projeto como um todo"
            ))
    );

    private final DetalheAreaService detalheAreaService;
    private final RoadmapConteudoService roadmapConteudoService;

    public ExploracaoAreaService(
            DetalheAreaService detalheAreaService,
            RoadmapConteudoService roadmapConteudoService
    ) {
        this.detalheAreaService = detalheAreaService;
        this.roadmapConteudoService = roadmapConteudoService;
    }

    public List<AreaExploracaoView> listar() {
        return Arrays.stream(AreaTi.values())
                .map(this::buscarPorArea)
                .toList();
    }

    public AreaExploracaoView buscarPorArea(AreaTi area) {
        DetalheAreaView detalhe = detalheAreaService.buscarPorArea(area);
        RoadmapEtapaInterativaView primeiraEtapa = roadmapConteudoService
                .buscarPorArea(area)
                .getEtapas()
                .getFirst();

        List<String> cargos = detalhe.getProfissoes().stream()
                .map(profissao -> profissao.getTitulo())
                .limit(2)
                .toList();

        return new AreaExploracaoView(
                area,
                CATEGORIAS.getOrDefault(area.getSlug(), List.of()),
                SINAIS_AFINIDADE.getOrDefault(area.getSlug(), List.of()),
                cargos,
                primeiraEtapa.getTitulo(),
                primeiraEtapa.getObjetivo(),
                primeiraEtapa.getTempo()
        );
    }
}
