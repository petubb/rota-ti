package com.rotati.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum AreaTi {
    DESENVOLVIMENTO(
            "desenvolvimento-software",
            "Desenvolvimento de Software",
            "Criatividade + raciocinio logico",
            "Cria sistemas, sites, aplicativos e solucoes digitais para resolver problemas reais.",
            List.of("Logica de programacao", "Java, JavaScript ou Python", "Git e GitHub", "Resolucao de problemas"),
            "R$ 2.500 a R$ 6.000 para niveis iniciais e intermediarios",
            "Transforma uma necessidade em codigo, testa, corrige bugs e melhora funcionalidades com o time.",
            List.of("Grace Hopper", "Linus Torvalds", "James Gosling"),
            List.of("Logica de Programacao", "HTML, CSS e JavaScript", "Java com Spring Boot", "Projetos para portfolio"),
            List.of("Aprender logica", "Criar pequenos projetos", "Publicar no GitHub", "Buscar estagio ou vaga junior")
    ),
    DADOS(
            "dados-bi",
            "Dados / Business Intelligence",
            "Analise + organizacao",
            "Organiza dados e cria relatorios para ajudar pessoas e empresas a tomar decisoes melhores.",
            List.of("Excel ou Google Sheets", "SQL", "Power BI", "Pensamento analitico"),
            "R$ 2.800 a R$ 7.000 para analistas em inicio e meio de carreira",
            "Coleta dados, limpa informacoes, monta dashboards e explica os resultados para outras areas.",
            List.of("Edward Tufte", "Florence Nightingale", "Hans Rosling"),
            List.of("Excel para analise", "SQL basico", "Power BI", "Estatistica introdutoria"),
            List.of("Dominar planilhas", "Aprender SQL", "Criar dashboards", "Montar cases de analise")
    ),
    SEGURANCA(
            "seguranca-cibernetica",
            "Seguranca Cibernetica",
            "Investigacao + atencao aos detalhes",
            "Protege sistemas, redes e dados contra falhas, golpes e ataques digitais.",
            List.of("Redes", "Linux", "Analise de vulnerabilidades", "Etica e responsabilidade"),
            "R$ 3.000 a R$ 8.000 em funcoes iniciais e intermediarias",
            "Analisa alertas, investiga riscos, corrige configuracoes e orienta boas praticas de protecao.",
            List.of("Whitfield Diffie", "Radia Perlman", "Katie Moussouris"),
            List.of("Fundamentos de redes", "Linux basico", "Introducao a seguranca", "Laboratorios CTF para iniciantes"),
            List.of("Aprender redes", "Praticar Linux", "Estudar seguranca defensiva", "Fazer laboratorios guiados")
    ),
    INFRAESTRUTURA(
            "infraestrutura-redes",
            "Infraestrutura e Redes",
            "Organizacao + operacao",
            "Mantem computadores, servidores, redes e servicos funcionando com estabilidade.",
            List.of("Redes de computadores", "Sistemas operacionais", "Suporte tecnico", "Documentacao"),
            "R$ 2.200 a R$ 6.000 conforme certificacoes e experiencia",
            "Configura equipamentos, monitora sistemas, atende chamados e resolve indisponibilidades.",
            List.of("Radia Perlman", "Vint Cerf", "Bob Kahn"),
            List.of("Redes basicas", "Windows e Linux", "Cloud introdutoria", "ITIL fundamentos"),
            List.of("Estudar hardware e redes", "Montar laboratorio local", "Aprender cloud", "Buscar certificacoes iniciais")
    ),
    UX_UI(
            "ux-ui-design",
            "UX / UI Design",
            "Criatividade + empatia",
            "Desenha experiencias digitais simples, bonitas e acessiveis para usuarios reais.",
            List.of("Pesquisa com usuarios", "Figma", "Prototipacao", "Comunicacao visual"),
            "R$ 2.500 a R$ 6.500 em cargos junior e pleno",
            "Entende dores dos usuarios, cria telas, testa fluxos e melhora a experiencia do produto.",
            List.of("Don Norman", "Jakob Nielsen", "Susan Kare"),
            List.of("Figma basico", "UX Research", "Design System", "Acessibilidade web"),
            List.of("Aprender fundamentos", "Copiar boas interfaces para treino", "Criar prototipos", "Montar portfolio")
    ),
    GAME_DESIGN(
            "game-design",
            "Game Design",
            "Criatividade + narrativa",
            "Planeja mecanicas, fases, regras e experiencias para jogos digitais ou analogicos.",
            List.of("Narrativa", "Prototipacao", "Unity ou Godot", "Balanceamento de regras"),
            "R$ 2.000 a R$ 6.000, variando bastante por estudio e tipo de projeto",
            "Cria conceitos de jogo, testa mecanicas, ajusta dificuldade e trabalha com arte e programacao.",
            List.of("Shigeru Miyamoto", "Hideo Kojima", "Jane McGonigal"),
            List.of("Game design basico", "Godot ou Unity", "Roteiro para jogos", "Prototipos pequenos"),
            List.of("Estudar jogos conhecidos", "Criar prototipos simples", "Publicar jogos pequenos", "Participar de game jams")
    ),
    IA(
            "inteligencia-artificial",
            "Inteligencia Artificial",
            "Curiosidade + matematica",
            "Usa dados, modelos e algoritmos para criar sistemas que reconhecem padroes e apoiam decisoes.",
            List.of("Matematica basica", "Python", "Dados", "Pensamento experimental"),
            "R$ 4.000 a R$ 10.000, normalmente exigindo base forte em dados e programacao",
            "Treina modelos, testa resultados, ajusta dados e transforma experimentos em solucoes uteis.",
            List.of("Alan Turing", "Fei-Fei Li", "Geoffrey Hinton"),
            List.of("Python basico", "Estatistica", "Machine Learning introdutorio", "Projetos com dados publicos"),
            List.of("Aprender Python", "Estudar estatistica", "Fazer projetos guiados", "Aprofundar machine learning")
    ),
    GESTAO(
            "gestao-ti",
            "Gestao de TI",
            "Lideranca + organizacao",
            "Coordena pessoas, processos e prioridades para entregar tecnologia com valor.",
            List.of("Comunicacao", "Organizacao", "Metodologias ageis", "Visao de negocio"),
            "R$ 3.500 a R$ 9.000, dependendo do nivel de responsabilidade",
            "Organiza demandas, conversa com equipes, acompanha prazos e ajuda a remover bloqueios.",
            List.of("Peter Drucker", "Mary Parker Follett", "Marty Cagan"),
            List.of("Scrum basico", "Gestao de projetos", "Comunicacao profissional", "Produto digital"),
            List.of("Entender tecnologia", "Aprender metodos ageis", "Participar de projetos", "Praticar lideranca")
    );

    private final String slug;
    private final String titulo;
    private final String perfil;
    private final String descricao;
    private final List<String> habilidades;
    private final String salario;
    private final String rotina;
    private final List<String> referencias;
    private final List<String> cursos;
    private final List<String> roadmap;

    AreaTi(
            String slug,
            String titulo,
            String perfil,
            String descricao,
            List<String> habilidades,
            String salario,
            String rotina,
            List<String> referencias,
            List<String> cursos,
            List<String> roadmap
    ) {
        this.slug = slug;
        this.titulo = titulo;
        this.perfil = perfil;
        this.descricao = descricao;
        this.habilidades = habilidades;
        this.salario = salario;
        this.rotina = rotina;
        this.referencias = referencias;
        this.cursos = cursos;
        this.roadmap = roadmap;
    }

    public static Optional<AreaTi> fromSlug(String slug) {
        return Arrays.stream(values())
                .filter(area -> area.slug.equals(slug))
                .findFirst();
    }

    public String getSlug() {
        return slug;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public String getSalario() {
        return salario;
    }

    public String getRotina() {
        return rotina;
    }

    public List<String> getReferencias() {
        return referencias;
    }

    public List<String> getCursos() {
        return cursos;
    }

    public List<String> getRoadmap() {
        return roadmap;
    }
}
