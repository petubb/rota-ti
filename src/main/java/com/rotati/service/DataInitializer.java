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
                        "Voce gosta de criar sites, aplicativos ou sistemas para resolver problemas?",
                        "criatividade",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 2), peso(AreaTi.UX_UI, 1)
                ),
                base(
                        "BASE_DEV_LOGICA",
                        "Voce se sente bem resolvendo problemas logicos passo a passo?",
                        "logica",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.IA, 1)
                ),
                base(
                        "BASE_DADOS_ORGANIZAR",
                        "Voce gosta de organizar informacoes e transforma-las em relatorios claros?",
                        "analise",
                        AreaTi.DADOS,
                        peso(AreaTi.DADOS, 2), peso(AreaTi.GESTAO, 1)
                ),
                base(
                        "BASE_DADOS_PADROES",
                        "Voce percebe padroes em numeros, tabelas ou graficos com facilidade?",
                        "analise",
                        AreaTi.DADOS,
                        peso(AreaTi.DADOS, 2), peso(AreaTi.IA, 1), peso(AreaTi.SEGURANCA, 1)
                ),
                base(
                        "BASE_SEG_INVESTIGAR",
                        "Voce se interessa por investigar falhas, golpes digitais ou vulnerabilidades?",
                        "investigacao",
                        AreaTi.SEGURANCA,
                        peso(AreaTi.SEGURANCA, 2), peso(AreaTi.INFRAESTRUTURA, 1)
                ),
                base(
                        "BASE_SEG_DETALHES",
                        "Voce costuma perceber quando algo parece fora do normal e quer descobrir a causa?",
                        "detalhe",
                        AreaTi.SEGURANCA,
                        peso(AreaTi.SEGURANCA, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.INFRAESTRUTURA, 1)
                ),
                base(
                        "BASE_INFRA_CONFIGURAR",
                        "Voce gosta de configurar computadores, redes ou servicos digitais?",
                        "operacao",
                        AreaTi.INFRAESTRUTURA,
                        peso(AreaTi.INFRAESTRUTURA, 2), peso(AreaTi.SEGURANCA, 1)
                ),
                base(
                        "BASE_INFRA_ESTABILIDADE",
                        "Voce prefere manter sistemas estaveis, organizados e bem documentados?",
                        "organizacao",
                        AreaTi.INFRAESTRUTURA,
                        peso(AreaTi.INFRAESTRUTURA, 2), peso(AreaTi.GESTAO, 1)
                ),
                base(
                        "BASE_UX_INTERFACES",
                        "Voce gosta de pensar em telas simples, bonitas e faceis de usar?",
                        "design",
                        AreaTi.UX_UI,
                        peso(AreaTi.UX_UI, 2), peso(AreaTi.DESENVOLVIMENTO, 1)
                ),
                base(
                        "BASE_UX_USUARIOS",
                        "Voce procura entender as necessidades das pessoas antes de propor uma solucao?",
                        "empatia",
                        AreaTi.UX_UI,
                        peso(AreaTi.UX_UI, 2), peso(AreaTi.GESTAO, 1), peso(AreaTi.DADOS, 1)
                ),
                base(
                        "BASE_GAME_MECANICAS",
                        "Voce gosta de imaginar regras, mecanicas, fases ou desafios para jogos?",
                        "narrativa",
                        AreaTi.GAME_DESIGN,
                        peso(AreaTi.GAME_DESIGN, 2), peso(AreaTi.DESENVOLVIMENTO, 1), peso(AreaTi.UX_UI, 1)
                ),
                base(
                        "BASE_GAME_BALANCEAMENTO",
                        "Voce teria interesse em testar e ajustar a dificuldade de uma experiencia interativa?",
                        "experimentacao",
                        AreaTi.GAME_DESIGN,
                        peso(AreaTi.GAME_DESIGN, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.UX_UI, 1)
                ),
                base(
                        "BASE_IA_CURIOSIDADE",
                        "Voce tem curiosidade sobre inteligencia artificial, automacao e modelos que aprendem?",
                        "curiosidade",
                        AreaTi.IA,
                        peso(AreaTi.IA, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.DESENVOLVIMENTO, 1)
                ),
                base(
                        "BASE_IA_EXPERIMENTAR",
                        "Voce gosta de testar hipoteses, comparar resultados e aprender com os erros?",
                        "experimentacao",
                        AreaTi.IA,
                        peso(AreaTi.IA, 2), peso(AreaTi.DADOS, 1), peso(AreaTi.DESENVOLVIMENTO, 1)
                ),
                base(
                        "BASE_GESTAO_LIDERAR",
                        "Voce gosta de liderar, organizar tarefas e ajudar um grupo a avancar?",
                        "lideranca",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 2)
                ),
                base(
                        "BASE_GESTAO_COMUNICAR",
                        "Voce se imagina conectando pessoas tecnicas, prazos e necessidades de negocio?",
                        "comunicacao",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 2), peso(AreaTi.UX_UI, 1)
                ),
                base(
                        "BASE_PERSISTENCIA",
                        "Voce persiste quando uma solucao exige varias tentativas e ajustes?",
                        "persistencia",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 1), peso(AreaTi.SEGURANCA, 1),
                        peso(AreaTi.IA, 1), peso(AreaTi.GAME_DESIGN, 1)
                ),
                base(
                        "BASE_EXPLICAR_IDEIAS",
                        "Voce gosta de explicar ideias complexas de um jeito que outras pessoas entendam?",
                        "comunicacao",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 1), peso(AreaTi.DADOS, 1), peso(AreaTi.UX_UI, 1)
                ),
                desempate(
                        "DESEMPATE_CRIAR_MANTER",
                        "Entre criar uma solucao nova e manter uma estrutura estavel, voce prefere criar?",
                        "criatividade",
                        AreaTi.DESENVOLVIMENTO,
                        peso(AreaTi.DESENVOLVIMENTO, 2), peso(AreaTi.GAME_DESIGN, 1), peso(AreaTi.IA, 1),
                        peso(AreaTi.INFRAESTRUTURA, -2), peso(AreaTi.SEGURANCA, -1)
                ),
                desempate(
                        "DESEMPATE_PREVENIR_EXPLORAR",
                        "Entre investigar riscos e explorar novas possibilidades, voce prefere investigar e prevenir?",
                        "investigacao",
                        AreaTi.SEGURANCA,
                        peso(AreaTi.SEGURANCA, 2), peso(AreaTi.INFRAESTRUTURA, 1),
                        peso(AreaTi.IA, -2), peso(AreaTi.GAME_DESIGN, -1)
                ),
                desempate(
                        "DESEMPATE_PESSOAS_LOGICA",
                        "Voce prefere trabalhar com necessidades de pessoas e comunicacao a lidar com logica e numeros?",
                        "empatia",
                        AreaTi.UX_UI,
                        peso(AreaTi.UX_UI, 2), peso(AreaTi.GESTAO, 2), peso(AreaTi.DADOS, -2),
                        peso(AreaTi.DESENVOLVIMENTO, -1), peso(AreaTi.IA, -1)
                ),
                desempate(
                        "DESEMPATE_DADOS_EXPERIENCIA",
                        "Voce se interessa mais por padroes e evidencias do que por aspectos visuais ou narrativos?",
                        "analise",
                        AreaTi.DADOS,
                        peso(AreaTi.DADOS, 2), peso(AreaTi.IA, 1), peso(AreaTi.SEGURANCA, 1),
                        peso(AreaTi.UX_UI, -2), peso(AreaTi.GAME_DESIGN, -2)
                ),
                desempate(
                        "DESEMPATE_COORDENAR_CONSTRUIR",
                        "Em um projeto, voce prefere coordenar prioridades e alinhar o time a construir a solucao diretamente?",
                        "lideranca",
                        AreaTi.GESTAO,
                        peso(AreaTi.GESTAO, 2), peso(AreaTi.DESENVOLVIMENTO, -2), peso(AreaTi.INFRAESTRUTURA, -1)
                ),
                desempate(
                        "DESEMPATE_INTERATIVO_REDES",
                        "Voce tem mais interesse em criar experiencias interativas do que administrar servicos e redes?",
                        "narrativa",
                        AreaTi.GAME_DESIGN,
                        peso(AreaTi.GAME_DESIGN, 2), peso(AreaTi.UX_UI, 1),
                        peso(AreaTi.INFRAESTRUTURA, -2), peso(AreaTi.SEGURANCA, -1)
                )
        ));
    }

    private Pergunta base(String codigo, String texto, String categoria, AreaTi principal, Peso... pesos) {
        return pergunta(codigo, texto, categoria, principal, TipoPergunta.BASE, pesos);
    }

    private Pergunta desempate(String codigo, String texto, String categoria, AreaTi principal, Peso... pesos) {
        return pergunta(codigo, texto, categoria, principal, TipoPergunta.DESEMPATE, pesos);
    }

    private Pergunta pergunta(
            String codigo,
            String texto,
            String categoria,
            AreaTi principal,
            TipoPergunta tipo,
            Peso... pesos
    ) {
        Pergunta pergunta = new Pergunta(codigo, texto, categoria, principal, tipo);
        Arrays.stream(pesos).forEach(item -> pergunta.adicionarPeso(item.area(), item.valor()));
        return pergunta;
    }

    private Peso peso(AreaTi area, int valor) {
        return new Peso(area, valor);
    }

    private record Peso(AreaTi area, int valor) {
    }
}
