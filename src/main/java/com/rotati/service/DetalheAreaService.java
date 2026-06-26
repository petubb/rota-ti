package com.rotati.service;

import com.rotati.dto.DetalheAreaView;
import com.rotati.dto.FonteSalarioView;
import com.rotati.dto.PlanoCarreiraView;
import com.rotati.dto.ReferenciaAreaView;
import com.rotati.dto.SalarioAreaView;
import com.rotati.model.AreaTi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DetalheAreaService {

    private static final String ROBERT_HALF_TECNOLOGIA =
            "https://www.roberthalf.com/br/pt/insights/guia-salarial/tecnologia";

    private final Map<String, DetalheAreaView> detalhes = Map.ofEntries(
            Map.entry("desenvolvimento-software", detalhe(
                    salario(
                            "R$ 3.400 a R$ 9.500 por mes entre vagas junior e pleno em grandes mercados.",
                            "Use como referencia inicial. A remuneracao muda bastante por cidade, stack, senioridade e modelo remoto.",
                            fonte("Glassdoor - Desenvolvedor de Software Junior", "https://www.glassdoor.com/Salaries/s%C3%A3o-paulo-desenvolvedor-de-software-junior-i-salary-SRCH_IL.0%2C9_IM1009_KO10%2C44.htm"),
                            fonte("Glassdoor - Desenvolvedor de Software Pleno", "https://www.glassdoor.com/Salaries/sao-paulo-brazil-desenvolvedor-de-software-pleno-salary-SRCH_IL.0%2C16_IM1009_KO17%2C48.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Area forte para quem gosta de construir produtos, resolver problemas em etapas e aprender continuamente.",
                    List.of(
                            plano("0-2 meses", "Fundamentos", "Estude logica, variaveis, estruturas de decisao, repeticao e Git."),
                            plano("3-5 meses", "Primeiros projetos", "Crie sites simples, APIs pequenas e publique tudo no GitHub com README claro."),
                            plano("6-9 meses", "Stack de mercado", "Escolha uma trilha, como Java com Spring Boot ou JavaScript com Node/React."),
                            plano("10-12 meses", "Portfolio e vaga junior", "Monte 2 ou 3 projetos completos, treine entrevista tecnica e busque estagio ou junior.")
                    ),
                    List.of(
                            referencia("Roadmap", "roadmap.sh Backend", "Mapa de assuntos para evoluir de logica para backend profissional.", "https://roadmap.sh/backend"),
                            referencia("Documentacao", "MDN Web Docs", "Base clara para HTML, CSS, JavaScript e conceitos da web.", "https://developer.mozilla.org/pt-BR/"),
                            referencia("Documentacao", "Spring Guides", "Guias oficiais para criar aplicacoes Java com Spring.", "https://spring.io/guides")
                    ),
                    List.of("Git e GitHub", "Java ou JavaScript", "Spring Boot ou Node.js", "SQL", "Testes automatizados")
            )),
            Map.entry("dados-bi", detalhe(
                    salario(
                            "R$ 3.200 a R$ 6.600 por mes para Analista de Dados Junior no Brasil.",
                            "BI cresce quando a pessoa junta SQL, visualizacao, negocio e comunicacao com areas nao tecnicas.",
                            fonte("Glassdoor - Analista de Dados Junior", "https://www.glassdoor.com.br/Sal%C3%A1rios/analista-de-dados-junior-sal%C3%A1rio-SRCH_KO0%2C24.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Boa rota para quem gosta de organizar informacoes, fazer perguntas e transformar numeros em decisao.",
                    List.of(
                            plano("0-2 meses", "Planilhas bem feitas", "Treine formulas, tabelas dinamicas, filtros e limpeza basica de dados."),
                            plano("3-5 meses", "SQL e bancos", "Aprenda SELECT, JOIN, GROUP BY e crie consultas para responder perguntas reais."),
                            plano("6-8 meses", "Dashboards", "Monte paineis no Power BI ou Looker Studio com indicadores e narrativa visual."),
                            plano("9-12 meses", "Cases de analise", "Publique estudos com problema, base usada, metodo e conclusao.")
                    ),
                    List.of(
                            referencia("Roadmap", "roadmap.sh Data Analyst", "Trilha com estatistica, SQL, visualizacao e ferramentas de analise.", "https://roadmap.sh/data-analyst"),
                            referencia("Ferramenta", "Microsoft Power BI Learn", "Material oficial para criar relatorios e dashboards.", "https://learn.microsoft.com/pt-br/power-bi/"),
                            referencia("Dados", "Kaggle Datasets", "Bases publicas para praticar analise e montar portfolio.", "https://www.kaggle.com/datasets")
                    ),
                    List.of("Excel ou Google Sheets", "SQL", "Power BI", "Python basico", "Storytelling com dados")
            )),
            Map.entry("seguranca-cibernetica", detalhe(
                    salario(
                            "R$ 3.200 a R$ 5.800 por mes em cargos junior de Seguranca da Informacao.",
                            "Certificacoes, experiencia com redes e pratica em laboratorio costumam acelerar a evolucao salarial.",
                            fonte("Glassdoor - Analista de Seguranca da Informacao Jr", "https://www.glassdoor.com.br/Sal%C3%A1rios/analista-de-seguranca-da-informacao-jr-sal%C3%A1rio-SRCH_KO0%2C38.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Area indicada para quem gosta de investigar, proteger sistemas e pensar em riscos antes que eles virem problema.",
                    List.of(
                            plano("0-2 meses", "Redes e sistemas", "Entenda IP, DNS, HTTP, Linux, permissoes e linha de comando."),
                            plano("3-5 meses", "Seguranca defensiva", "Estude autenticacao, backups, logs, vulnerabilidades comuns e boas praticas."),
                            plano("6-8 meses", "Laboratorios controlados", "Pratique em ambientes legais como CTFs iniciantes e maquinas vulneraveis didaticas."),
                            plano("9-12 meses", "Especializacao inicial", "Escolha SOC, DevSecOps, redes ou testes e monte um portfolio de estudos.")
                    ),
                    List.of(
                            referencia("Guia", "OWASP Top 10", "Lista das vulnerabilidades web mais importantes para quem esta comecando.", "https://owasp.org/www-project-top-ten/"),
                            referencia("Roadmap", "roadmap.sh Cyber Security", "Mapa visual de fundamentos, defesa, ofensiva e governanca.", "https://roadmap.sh/cyber-security"),
                            referencia("Curso", "Cisco Skills For All", "Cursos introdutorios de redes e ciberseguranca.", "https://skillsforall.com/")
                    ),
                    List.of("Redes", "Linux", "Logs", "OWASP", "Git", "Conceitos de cloud")
            )),
            Map.entry("infraestrutura-redes", detalhe(
                    salario(
                            "R$ 2.600 a R$ 4.800 por mes para Analista de Infraestrutura Jr no Brasil.",
                            "Cloud, automacao e certificacoes podem abrir caminho para faixas mais altas dentro de infraestrutura.",
                            fonte("Glassdoor - Analista de Infraestrutura Jr", "https://www.glassdoor.com.br/Sal%C3%A1rios/analista-de-infraestrutura-jr-sal%C3%A1rio-SRCH_KO0%2C29.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Rota para quem gosta de manter ambientes funcionando, diagnosticar falhas e cuidar da base tecnica das empresas.",
                    List.of(
                            plano("0-2 meses", "Suporte e hardware", "Revise componentes, sistemas operacionais, backup e atendimento ao usuario."),
                            plano("3-5 meses", "Redes", "Aprenda IP, sub-redes, roteadores, switches, DNS, DHCP e Wi-Fi corporativo."),
                            plano("6-8 meses", "Servidores e cloud", "Pratique Linux, Windows Server, virtualizacao e fundamentos de AWS ou Azure."),
                            plano("9-12 meses", "Operacao profissional", "Documente processos, monitore servicos e estude automacao com scripts.")
                    ),
                    List.of(
                            referencia("Curso", "Cisco Networking Basics", "Base de redes para entender conectividade e infraestrutura.", "https://skillsforall.com/course/networking-basics"),
                            referencia("Documentacao", "AWS Skill Builder", "Conteudos oficiais para iniciar em computacao em nuvem.", "https://skillbuilder.aws/"),
                            referencia("Roadmap", "roadmap.sh DevOps", "Mapa util para evoluir de infraestrutura para operacao moderna.", "https://roadmap.sh/devops")
                    ),
                    List.of("Windows e Linux", "Redes TCP/IP", "Virtualizacao", "Cloud", "Monitoramento", "PowerShell ou Bash")
            )),
            Map.entry("ux-ui-design", detalhe(
                    salario(
                            "R$ 2.600 a R$ 5.300 por mes para UI/UX Designer Junior no Brasil.",
                            "Portfolio com processo, pesquisa e prototipos pesa muito na avaliacao de candidatos iniciantes.",
                            fonte("Glassdoor - UI/UX Designer Junior", "https://www.glassdoor.com.br/Sal%C3%A1rios/ui-ux-designer-junior-sal%C3%A1rio-SRCH_KO0%2C21.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Area para quem junta empatia, organizacao visual e vontade de testar solucoes com pessoas reais.",
                    List.of(
                            plano("0-2 meses", "Fundamentos visuais", "Estude hierarquia, contraste, alinhamento, tipografia e acessibilidade."),
                            plano("3-5 meses", "Figma e prototipos", "Crie telas, componentes, fluxos clicaveis e pequenas interfaces responsivas."),
                            plano("6-8 meses", "Pesquisa e validacao", "Pratique entrevista, mapa de jornada, teste de usabilidade e melhoria de fluxo."),
                            plano("9-12 meses", "Portfolio narrativo", "Mostre problema, decisao, alternativa descartada e resultado do projeto.")
                    ),
                    List.of(
                            referencia("Referencia", "Nielsen Norman Group", "Artigos classicos de UX, pesquisa e usabilidade.", "https://www.nngroup.com/articles/"),
                            referencia("Guia", "Material Design", "Sistema de design com principios, componentes e boas praticas.", "https://m3.material.io/"),
                            referencia("Acessibilidade", "WCAG Overview", "Referencia internacional para acessibilidade digital.", "https://www.w3.org/WAI/standards-guidelines/wcag/")
                    ),
                    List.of("Figma", "Pesquisa com usuarios", "Prototipacao", "Design system", "Acessibilidade")
            )),
            Map.entry("game-design", detalhe(
                    salario(
                            "R$ 2.400 a R$ 3.300 por mes para Junior Game Designer no Brasil.",
                            "O mercado de jogos varia muito por estudio, contrato, publicacao independente e experiencia com engines.",
                            fonte("Glassdoor - Game Designer Junior", "https://www.glassdoor.com.br/Sal%C3%A1rios/game-designer-junior-sal%C3%A1rio-SRCH_KO0%2C20.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Boa rota para quem gosta de regras, narrativa, prototipos rapidos e iteracao constante com jogadores.",
                    List.of(
                            plano("0-2 meses", "Linguagem dos jogos", "Analise mecanicas, objetivos, feedback, recompensa, dificuldade e ritmo."),
                            plano("3-5 meses", "Prototipos pequenos", "Crie jogos de uma tela ou fases curtas em Godot, Unity ou Construct."),
                            plano("6-8 meses", "Teste com jogadores", "Observe pessoas jogando, anote problemas e ajuste regras com base no comportamento."),
                            plano("9-12 meses", "Portfolio jogavel", "Publique projetos pequenos com pagina, trailer curto e explicacao das decisoes.")
                    ),
                    List.of(
                            referencia("Documentacao", "Godot Docs", "Documentacao oficial de uma engine aberta e amigavel para iniciar.", "https://docs.godotengine.org/"),
                            referencia("Referencia", "Game Developer", "Artigos sobre design, producao e desenvolvimento de jogos.", "https://www.gamedeveloper.com/"),
                            referencia("Evento", "Global Game Jam", "Evento para praticar prototipacao e trabalho em equipe.", "https://globalgamejam.org/")
                    ),
                    List.of("Godot ou Unity", "Level design", "Narrativa", "Balanceamento", "Playtest")
            )),
            Map.entry("inteligencia-artificial", detalhe(
                    salario(
                            "R$ 5.200 a R$ 8.800 por mes para Junior Data Scientist no Brasil.",
                            "IA costuma exigir uma base mais forte em dados, estatistica, Python e avaliacao de modelos.",
                            fonte("Glassdoor - Junior Data Scientist", "https://www.glassdoor.com.br/Sal%C3%A1rios/junior-data-scientist-sal%C3%A1rio-SRCH_KO0%2C21.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Caminho para quem gosta de testar hipoteses, lidar com incerteza e transformar dados em sistemas inteligentes.",
                    List.of(
                            plano("0-2 meses", "Python e matematica base", "Revise funcoes, listas, bibliotecas, media, variancia e interpretacao de graficos."),
                            plano("3-5 meses", "Dados antes do modelo", "Aprenda limpeza, exploracao, visualizacao e separacao de treino e teste."),
                            plano("6-8 meses", "Modelos iniciais", "Treine classificacao, regressao e clustering com metricas bem explicadas."),
                            plano("9-12 meses", "Projeto aplicado", "Construa um caso com problema real, modelo simples, avaliacao e limite etico.")
                    ),
                    List.of(
                            referencia("Curso", "Google Machine Learning Crash Course", "Curso gratuito de fundamentos de machine learning.", "https://developers.google.com/machine-learning/crash-course"),
                            referencia("Roadmap", "roadmap.sh AI Data Scientist", "Mapa de fundamentos para ciencia de dados e IA.", "https://roadmap.sh/ai-data-scientist"),
                            referencia("Biblioteca", "scikit-learn", "Documentacao de uma das bibliotecas mais usadas para ML em Python.", "https://scikit-learn.org/stable/")
                    ),
                    List.of("Python", "Pandas", "Estatistica", "Machine learning", "Etica em IA")
            )),
            Map.entry("gestao-ti", detalhe(
                    salario(
                            "R$ 3.700 a R$ 9.100 por mes para Gerente de Projetos Junior no Brasil.",
                            "Gestao em TI valoriza comunicacao, leitura de negocio, organizacao e experiencia previa em projetos.",
                            fonte("Glassdoor - Gerente de Projetos Junior", "https://www.glassdoor.com.br/Sal%C3%A1rios/gerente-de-projetos-junior-sal%C3%A1rio-SRCH_KO0%2C26.htm"),
                            fonte("Robert Half - Guia Salarial 2026 Tecnologia", ROBERT_HALF_TECNOLOGIA)
                    ),
                    "Rota para quem gosta de coordenar pessoas, alinhar prioridades e transformar necessidade em entrega organizada.",
                    List.of(
                            plano("0-2 meses", "Entender projetos", "Aprenda escopo, prazo, risco, priorizacao, stakeholders e comunicacao."),
                            plano("3-5 meses", "Metodos ageis", "Estude Scrum, Kanban, cerimonias, backlog e acompanhamento de fluxo."),
                            plano("6-8 meses", "Pratica em equipe", "Atue como organizador de um projeto academico, voluntario ou interno."),
                            plano("9-12 meses", "Produto e negocio", "Treine escrita de requisitos, metricas, discovery e apresentacao de resultados.")
                    ),
                    List.of(
                            referencia("Guia", "Scrum Guide", "Referencia oficial e curta sobre Scrum.", "https://scrumguides.org/"),
                            referencia("Ferramenta", "Atlassian Agile Coach", "Guias praticos de Scrum, Kanban e gestao de times.", "https://www.atlassian.com/agile"),
                            referencia("Referencia", "SVPG Articles", "Artigos sobre produto, discovery e gestao de tecnologia.", "https://www.svpg.com/articles/")
                    ),
                    List.of("Scrum e Kanban", "Jira ou Trello", "Comunicacao", "Priorizacao", "Metricas")
            ))
    );

    public DetalheAreaView buscarPorSlug(String slug) {
        DetalheAreaView detalhe = detalhes.get(slug);
        if (detalhe == null) {
            throw new IllegalArgumentException("Detalhes nao encontrados para a area: " + slug);
        }
        return detalhe;
    }

    public DetalheAreaView buscarPorArea(AreaTi area) {
        return buscarPorSlug(area.getSlug());
    }

    private static DetalheAreaView detalhe(
            SalarioAreaView salario,
            String mercado,
            List<PlanoCarreiraView> planoCarreira,
            List<ReferenciaAreaView> referencias,
            List<String> ferramentas
    ) {
        return new DetalheAreaView(salario, mercado, planoCarreira, referencias, ferramentas);
    }

    private static SalarioAreaView salario(String resumo, String observacao, FonteSalarioView... fontes) {
        return new SalarioAreaView(resumo, observacao, List.of(fontes));
    }

    private static FonteSalarioView fonte(String titulo, String url) {
        return new FonteSalarioView(titulo, url);
    }

    private static PlanoCarreiraView plano(String periodo, String titulo, String descricao) {
        return new PlanoCarreiraView(periodo, titulo, descricao);
    }

    private static ReferenciaAreaView referencia(String tipo, String titulo, String descricao, String url) {
        return new ReferenciaAreaView(tipo, titulo, descricao, url);
    }
}
