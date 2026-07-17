package com.rotati.service;

import com.rotati.model.AreaTi;
import com.rotati.model.Pergunta;
import com.rotati.model.TipoPergunta;
import com.rotati.repository.PerguntaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PerguntaRepository perguntaRepository;

    public DataInitializer(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @Override
    public void run(String... args) {
        if (perguntaRepository.count() > 0) {
            return;
        }

        perguntaRepository.saveAll(List.of(
                base(
                        "BASE_DEV_CRIAR",
                        "Gosto de criar sites, aplicativos ou sistemas.",
                        "criatividade",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 3), peso(AreaTi.UX_UI, 1)
                ),
                base(
                        "BASE_DEV_LOGICA",
                        "Gosto de resolver problemas em etapas.",
                        "logica",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 3), peso(AreaTi.DADOS, 1), peso(AreaTi.IA, 1)
                ),
                baseInativa(
                        "BASE_DADOS_ORGANIZAR",
                        "Gosto de organizar informacoes em tabelas ou relatorios.",
                        "analise",
                        AreaTi.DADOS,
                        peso(AreaTi.DADOS, 2), peso(AreaTi.GESTAO, 1)
                ),
                base(
                        "BASE_DADOS_PADROES",
                        "Percebo padroes em numeros, tabelas ou graficos.",
                        "analise",
                        AreaTi.DADOS,
                        peso(AreaTi.DADOS, 3), peso(AreaTi.IA, 1), peso(AreaTi.SEGURANCA, 1)
                ),
                base(
                        "BASE_SEG_INVESTIGAR",
                        "Tenho interesse em investigar falhas ou golpes digitais.",
                        "investigacao",
                        AreaTi.SEGURANCA,
                        peso(AreaTi.SEGURANCA, 3), peso(AreaTi.INFRAESTRUTURA, 1)
                ),
                baseInativa(
                        "BASE_SEG_DETALHES",
                        "Percebo quando algo parece fora do normal.",
                        "detalhe",
                        AreaTi.SEGURANCA,
                        peso(AreaTi.SEGURANCA, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.INFRAESTRUTURA, 1)
                ),
                base(
                        "BASE_INFRA_CONFIGURAR",
                        "Gosto de configurar computadores, redes ou servicos.",
                        "operacao",
                        AreaTi.INFRAESTRUTURA,
                        peso(AreaTi.INFRAESTRUTURA, 3), peso(AreaTi.SEGURANCA, 1)
                ),
                baseInativa(
                        "BASE_INFRA_ESTABILIDADE",
                        "Prefiro manter sistemas estaveis e organizados.",
                        "organizacao",
                        AreaTi.INFRAESTRUTURA,
                        peso(AreaTi.INFRAESTRUTURA, 2), peso(AreaTi.GESTAO, 1)
                ),
                base(
                        "BASE_UX_INTERFACES",
                        "Gosto de pensar em telas simples e faceis de usar.",
                        "design",
                        AreaTi.UX_UI,
                        peso(AreaTi.UX_UI, 3), peso(AreaTi.DESENVOLVIMENTO, 1)
                ),
                base(
                        "BASE_UX_USUARIOS",
                        "Gosto de entender o que as pessoas precisam.",
                        "empatia",
                        AreaTi.UX_UI,
                        peso(AreaTi.UX_UI, 3), peso(AreaTi.GESTAO, 1), peso(AreaTi.DADOS, 1)
                ),
                base(
                        "BASE_GAME_MECANICAS",
                        "Gosto de imaginar regras, fases ou desafios de jogos.",
                        "narrativa",
                        AreaTi.GAME_DESIGN,
                        peso(AreaTi.GAME_DESIGN, 3), peso(AreaTi.DESENVOLVIMENTO, 1), peso(AreaTi.UX_UI, 1)
                ),
                baseInativa(
                        "BASE_GAME_BALANCEAMENTO",
                        "Tenho interesse em testar e ajustar experiencias interativas.",
                        "experimentacao",
                        AreaTi.GAME_DESIGN,
                        peso(AreaTi.GAME_DESIGN, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.UX_UI, 1)
                ),
                base(
                        "BASE_IA_CURIOSIDADE",
                        "Tenho curiosidade sobre inteligencia artificial e automacao.",
                        "curiosidade",
                        AreaTi.IA,
                        peso(AreaTi.IA, 3), peso(AreaTi.DADOS, 1), peso(AreaTi.DESENVOLVIMENTO, 1)
                ),
                baseInativa(
                        "BASE_IA_EXPERIMENTAR",
                        "Gosto de testar ideias e comparar resultados.",
                        "experimentacao",
                        AreaTi.IA,
                        peso(AreaTi.IA, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.DESENVOLVIMENTO, 1)
                ),
                base(
                        "BASE_GESTAO_LIDERAR",
                        "Gosto de organizar tarefas e ajudar um grupo a avancar.",
                        "lideranca",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 3)
                ),
                baseInativa(
                        "BASE_GESTAO_COMUNICAR",
                        "Gosto de conectar pessoas, prazos e objetivos.",
                        "comunicacao",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 2), peso(AreaTi.UX_UI, 1)
                ),
                base(
                        "BASE_PERSISTENCIA",
                        "Continuo tentando quando uma solucao nao funciona de primeira.",
                        "persistencia",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 1), peso(AreaTi.SEGURANCA, 1),
                        peso(AreaTi.IA, 1), peso(AreaTi.GAME_DESIGN, 1)
                ),
                base(
                        "BASE_EXPLICAR_IDEIAS",
                        "Gosto de explicar ideias de um jeito simples.",
                        "comunicacao",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 1), peso(AreaTi.DADOS, 1), peso(AreaTi.UX_UI, 1)
                ),
                desempate(
                        "DESEMPATE_CRIAR_MANTER",
                        "Entre criar algo novo e manter tudo estavel, eu prefiro criar.",
                        "criatividade",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 3), peso(AreaTi.GAME_DESIGN, 1), peso(AreaTi.IA, 1),
                        peso(AreaTi.INFRAESTRUTURA, -3), peso(AreaTi.SEGURANCA, -1)
                ),
                desempate(
                        "DESEMPATE_PREVENIR_EXPLORAR",
                        "Entre investigar riscos e explorar novidades, eu prefiro investigar riscos.",
                        "investigacao",
                        AreaTi.SEGURANCA,
                        peso(AreaTi.SEGURANCA, 3), peso(AreaTi.INFRAESTRUTURA, 1),
                        peso(AreaTi.IA, -3), peso(AreaTi.GAME_DESIGN, -1)
                ),
                desempate(
                        "DESEMPATE_PESSOAS_LOGICA",
                        "Prefiro entender pessoas e comunicacao a lidar com logica e numeros.",
                        "empatia",
                        AreaTi.UX_UI,
                        peso(AreaTi.UX_UI, 3), peso(AreaTi.GESTAO, 2), peso(AreaTi.DADOS, -3),
                        peso(AreaTi.DESENVOLVIMENTO, -1), peso(AreaTi.IA, -1)
                ),
                desempate(
                        "DESEMPATE_DADOS_EXPERIENCIA",
                        "Prefiro padroes e evidencias a partes visuais ou narrativas.",
                        "analise",
                        AreaTi.DADOS,
                        peso(AreaTi.DADOS, 3), peso(AreaTi.IA, 1), peso(AreaTi.SEGURANCA, 1),
                        peso(AreaTi.UX_UI, -3), peso(AreaTi.GAME_DESIGN, -2)
                ),
                desempate(
                        "DESEMPATE_COORDENAR_CONSTRUIR",
                        "Prefiro coordenar prioridades a construir a solucao diretamente.",
                        "lideranca",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 3), peso(AreaTi.DESENVOLVIMENTO, -3), peso(AreaTi.INFRAESTRUTURA, -1)
                ),
                desempate(
                        "DESEMPATE_INTERATIVO_REDES",
                        "Prefiro criar experiencias interativas a administrar redes e servicos.",
                        "narrativa",
                        AreaTi.GAME_DESIGN,
                        peso(AreaTi.GAME_DESIGN, 3), peso(AreaTi.UX_UI, 1),
                        peso(AreaTi.INFRAESTRUTURA, -3), peso(AreaTi.SEGURANCA, -1)
                )
        ));
    }

    private Pergunta base(String codigo, String texto, String categoria, AreaTi principal, Peso... pesos) {
        return pergunta(codigo, texto, categoria, principal, TipoPergunta.BASE, true, pesos);
    }

    private Pergunta baseInativa(String codigo, String texto, String categoria, AreaTi principal, Peso... pesos) {
        return pergunta(codigo, texto, categoria, principal, TipoPergunta.BASE, false, pesos);
    }

    private Pergunta desempate(String codigo, String texto, String categoria, AreaTi principal, Peso... pesos) {
        return pergunta(codigo, texto, categoria, principal, TipoPergunta.DESEMPATE, true, pesos);
    }

    private Pergunta pergunta(
            String codigo,
            String texto,
            String categoria,
            AreaTi principal,
            TipoPergunta tipo,
            boolean ativa,
            Peso... pesos
    ) {
        Pergunta pergunta = new Pergunta(codigo, texto, categoria, principal, tipo);
        pergunta.setAtiva(ativa);
        Arrays.stream(pesos).forEach(item -> pergunta.adicionarPeso(item.area(), item.valor()));
        return pergunta;
    }

    private Peso peso(AreaTi area, int valor) {
        return new Peso(area, valor);
    }

    private record Peso(AreaTi area, int valor) {
    }
}
