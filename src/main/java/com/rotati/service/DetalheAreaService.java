package com.rotati.service;

import com.rotati.dto.DetalheAreaView;
import com.rotati.dto.FonteSalarioView;
import com.rotati.dto.FormacaoLocalView;
import com.rotati.dto.PlanoCarreiraView;
import com.rotati.dto.ProfissaoAreaView;
import com.rotati.dto.RecursoOnlineView;
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
    private static final String IFRO_CACOAL_CURSOS = "https://portal.ifro.edu.br/cacoal/cursos";
    private static final String IFRO_EAD = "https://portal.ifro.edu.br/educacaoadistancia-nav";
    private static final String SENAC_INFORMATICA = "https://www.ro.senac.br/categoria-produto/area_de_atuacao/informatica/";
    private static final String SENAC_PIMENTA_BUENO = "https://www.ro.senac.br/unidades/senac-pimenta-bueno/";
    private static final String SENAC_ROLIM_MOURA = "https://www.ro.senac.br/category/processo-seletivo/processo-seletivo-rolim-de-moura/";
    private static final String SENAI_RO = "https://portal.fiero.org.br/senai";
    private static final String SENAI_FUTURO_DIGITAL = "https://www.futuro.digital/senai-ro";
    private static final String CRUZEIRO_SUL_ROLIM_MOURA = "https://www.cruzeirodosulvirtual.com.br/polo/rolim-de-moura-centro-ro/";
    private static final String MICROSOFT_LEARN = "https://learn.microsoft.com/pt-br/training/";
    private static final String CISCO_NETACAD = "https://www.netacad.com/";
    private static final String FUNDACAO_BRADESCO_ESCOLA_VIRTUAL = "https://fundacao.bradesco/como-atuamos/escola-virtual";
    private static final String FUNDACAO_BRADESCO_FUNDAMENTOS_TI =
            "https://www.ev.org.br/cursos/fundamentos-de-ti-hardware-e-software";
    private static final String EVG_CATALOGO = "https://www.escolavirtual.gov.br/catalogo";
    private static final String KAGGLE_LEARN = "https://www.kaggle.com/learn";
    private static final String FIGMA_BEGINNERS =
            "https://help.figma.com/hc/en-us/articles/30848209492887-Course-overview-Figma-Design-for-beginners-2025";
    private static final String MATERIAL_DESIGN = "https://m3.material.io/";
    private static final String W3C_WCAG = "https://www.w3.org/WAI/standards-guidelines/wcag/";
    private static final String UNITY_LEARN = "https://learn.unity.com/";
    private static final String GODOT_DOCS = "https://docs.godotengine.org/";
    private static final String GLOBAL_GAME_JAM = "https://globalgamejam.org/";
    private static final String GOOGLE_ML_CRASH_COURSE = "https://developers.google.com/machine-learning/crash-course";
    private static final String GOOGLE_AI_SKILLS = "https://ai.google/learn-ai-skills/";
    private static final String SCRUM_GUIDES = "https://scrumguides.org/";
    private static final String ATLASSIAN_AGILE = "https://www.atlassian.com/agile";

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

    private final Map<String, List<ProfissaoAreaView>> profissoes = Map.ofEntries(
            Map.entry("desenvolvimento-software", List.of(
                    profissao("Desenvolvedor(a) Web", "Entrada", "Cria sites, telas e sistemas web usando HTML, CSS, JavaScript e frameworks."),
                    profissao("Desenvolvedor(a) Backend", "Evolucao", "Constrói APIs, regras de negocio, integracoes e acesso a banco de dados."),
                    profissao("Desenvolvedor(a) Mobile", "Evolucao", "Desenvolve aplicativos para celular e integra recursos do aparelho com servicos online."),
                    profissao("QA / Testador(a) de Software", "Entrada", "Testa funcionalidades, registra falhas e ajuda o time a entregar sistemas mais confiaveis.")
            )),
            Map.entry("dados-bi", List.of(
                    profissao("Analista de Dados", "Entrada", "Organiza bases, cria consultas e transforma informacoes em respostas para o negocio."),
                    profissao("Analista de BI", "Entrada", "Monta dashboards, indicadores e relatorios para acompanhar desempenho."),
                    profissao("Engenheiro(a) de Dados Jr.", "Evolucao", "Prepara fluxos de dados para que analistas e sistemas usem informacoes confiaveis."),
                    profissao("Cientista de Dados Jr.", "Evolucao", "Usa estatistica e modelos para encontrar padroes, previsoes e oportunidades.")
            )),
            Map.entry("seguranca-cibernetica", List.of(
                    profissao("Analista SOC", "Entrada", "Monitora alertas, investiga eventos suspeitos e apoia a resposta a incidentes."),
                    profissao("Analista de Seguranca Jr.", "Entrada", "Revisa acessos, vulnerabilidades, politicas e boas praticas de protecao."),
                    profissao("DevSecOps Jr.", "Evolucao", "Ajuda a inserir seguranca no ciclo de desenvolvimento e entrega de software."),
                    profissao("Pentester Jr.", "Evolucao", "Testa sistemas em ambientes autorizados para encontrar falhas antes de invasores.")
            )),
            Map.entry("infraestrutura-redes", List.of(
                    profissao("Tecnico(a) de Suporte", "Entrada", "Atende usuarios, resolve problemas de computador, rede, sistemas e acessos."),
                    profissao("Analista de Redes Jr.", "Entrada", "Configura, documenta e acompanha redes, Wi-Fi, roteadores e conectividade."),
                    profissao("Administrador(a) de Sistemas Jr.", "Evolucao", "Cuida de servidores, contas, backups, atualizacoes e disponibilidade."),
                    profissao("Analista Cloud Jr.", "Evolucao", "Opera servicos em nuvem, monitora recursos e apoia ambientes escalaveis.")
            )),
            Map.entry("ux-ui-design", List.of(
                    profissao("UI Designer Jr.", "Entrada", "Cria telas, componentes e layouts com foco em clareza visual e consistencia."),
                    profissao("UX Researcher Jr.", "Entrada", "Pesquisa usuarios, conduz entrevistas e transforma achados em melhorias."),
                    profissao("Product Designer Jr.", "Evolucao", "Une pesquisa, interface, prototipos e estrategia de produto digital."),
                    profissao("UX Writer", "Evolucao", "Escreve textos de interface para deixar fluxos mais claros e humanos.")
            )),
            Map.entry("game-design", List.of(
                    profissao("Game Designer Jr.", "Entrada", "Define regras, objetivos, progresso, recompensa e experiencia do jogador."),
                    profissao("Level Designer", "Entrada", "Planeja fases, mapas, desafios e ritmo de aprendizado dentro do jogo."),
                    profissao("Programador(a) Gameplay Jr.", "Evolucao", "Implementa mecanicas jogaveis, controles, interacoes e sistemas do jogo."),
                    profissao("QA de Jogos", "Entrada", "Testa jogos, encontra bugs e ajuda a melhorar jogabilidade e estabilidade.")
            )),
            Map.entry("inteligencia-artificial", List.of(
                    profissao("Analista de Machine Learning Jr.", "Evolucao", "Treina modelos simples, mede resultados e documenta limites das solucoes."),
                    profissao("Desenvolvedor(a) de Automacoes", "Entrada", "Usa APIs, scripts e IA para automatizar tarefas repetitivas."),
                    profissao("Cientista de Dados Jr.", "Evolucao", "Explora dados, cria hipoteses e avalia modelos preditivos."),
                    profissao("Desenvolvedor(a) IA Jr.", "Evolucao", "Integra modelos de IA em sistemas, produtos e fluxos de trabalho.")
            )),
            Map.entry("gestao-ti", List.of(
                    profissao("Analista de Requisitos", "Entrada", "Conversa com usuarios e transforma necessidades em tarefas compreensiveis para o time."),
                    profissao("Scrum Master Jr.", "Entrada", "Ajuda o time a organizar rituais, remover bloqueios e melhorar o fluxo."),
                    profissao("Product Owner Jr.", "Evolucao", "Prioriza demandas, acompanha valor entregue e conecta tecnologia ao negocio."),
                    profissao("Coordenador(a) de Suporte Jr.", "Evolucao", "Organiza atendimento, acompanha indicadores e melhora processos de suporte.")
            ))
    );

    private final Map<String, List<FormacaoLocalView>> formacoes = Map.ofEntries(
            Map.entry("desenvolvimento-software", List.of(
                    formacao("Tecnico em Informatica", "IFRO Campus Cacoal", "Cacoal", "Tecnico", "Boa base para logica, programacao, sistemas e continuidade em graduacao.", IFRO_CACOAL_CURSOS),
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Cacoal, Rolim de Moura e RO", "Livre/Tecnico", "Procure turmas de programacao, informatica, web e fundamentos de tecnologia.", SENAI_FUTURO_DIGITAL),
                    formacao("Informatica e cursos livres", "SENAC-RO", "Pimenta Bueno", "Livre/Tecnico", "Opcao proxima para comecar por fundamentos, ferramentas e trilhas profissionais.", SENAC_INFORMATICA),
                    formacao("Polo EAD e editais", "IFRO", "Pimenta Bueno e regiao", "EAD/Presencial", "Acompanhe cursos tecnicos, FIC e editais que podem abrir oportunidades em desenvolvimento.", IFRO_EAD)
            )),
            Map.entry("dados-bi", List.of(
                    formacao("Tecnico em Informatica", "IFRO Campus Cacoal", "Cacoal", "Tecnico", "Base util para SQL, logica, bancos e projetos com informacao estruturada.", IFRO_CACOAL_CURSOS),
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Cacoal, Rolim de Moura e RO", "Livre/Tecnico", "Procure turmas de informatica, dados, ferramentas digitais e fundamentos de tecnologia.", SENAI_FUTURO_DIGITAL),
                    formacao("Cursos de Informatica", "SENAC-RO", "Pimenta Bueno", "Livre/Tecnico", "Procure turmas com planilhas, informatica, banco de dados ou ferramentas de produtividade.", SENAC_INFORMATICA),
                    formacao("Cursos EAD e polos", "IFRO", "Pimenta Bueno e regiao", "EAD/Presencial", "Vale acompanhar editais e polos para oportunidades de formacao tecnica e continuada.", IFRO_EAD)
            )),
            Map.entry("seguranca-cibernetica", List.of(
                    formacao("Tecnico em Informatica", "IFRO Campus Cacoal", "Cacoal", "Tecnico", "Base para redes, sistemas operacionais, programacao e manutencao segura.", IFRO_CACOAL_CURSOS),
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Cacoal, Rolim de Moura e RO", "Tecnico/Livre", "Procure turmas de informatica, redes, IoT, manutencao e trilhas de tecnologia.", SENAI_FUTURO_DIGITAL),
                    formacao("Informatica", "SENAC-RO", "Pimenta Bueno", "Livre/Tecnico", "Um comeco pratico para fundamentos antes de aprofundar em redes e seguranca.", SENAC_INFORMATICA),
                    formacao("Polo EAD e editais", "IFRO", "Pimenta Bueno e regiao", "EAD/Presencial", "Acompanhe cursos tecnicos, FIC e editais ligados a redes, informatica e tecnologia.", IFRO_EAD)
            )),
            Map.entry("infraestrutura-redes", List.of(
                    formacao("Tecnico em Informatica", "IFRO Campus Cacoal", "Cacoal", "Tecnico", "Caminho forte para suporte, manutencao, redes e sistemas.", IFRO_CACOAL_CURSOS),
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Cacoal, Rolim de Moura e RO", "Tecnico/Livre", "Procure turmas ligadas a informatica, redes, IoT e operacao tecnica.", SENAI_RO),
                    formacao("Tecnico em Informatica", "SENAC-RO", "Pimenta Bueno", "Tecnico", "Alternativa proxima para comecar por suporte, ferramentas e rotina tecnica.", SENAC_INFORMATICA),
                    formacao("Polo EAD e editais", "IFRO", "Pimenta Bueno", "EAD/Presencial", "Acompanhe editais de cursos subsequentes, FIC e polos na regiao.", IFRO_EAD)
            )),
            Map.entry("ux-ui-design", List.of(
                    formacao("Informatica e ferramentas digitais", "SENAC-RO", "Pimenta Bueno", "Livre/Tecnico", "Comece por fundamentos digitais e procure turmas ligadas a design, web ou produtividade.", SENAC_PIMENTA_BUENO),
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Rondonia", "Livre/Tecnico", "Pode ajudar com web, ferramentas visuais e fundamentos para prototipos digitais.", SENAI_FUTURO_DIGITAL),
                    formacao("Polo EAD e editais", "IFRO", "Pimenta Bueno e regiao", "EAD/Presencial", "Acompanhe cursos que aproximem web, informatica, produto digital e ferramentas de criacao.", IFRO_EAD),
                    formacao("Tecnologia a distancia", "Cruzeiro do Sul Virtual", "Rolim de Moura", "EAD/Polo", "Opcao EAD para quem precisa estudar perto de casa e fortalecer base em tecnologia.", CRUZEIRO_SUL_ROLIM_MOURA)
            )),
            Map.entry("game-design", List.of(
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Rondonia", "Livre/Tecnico", "Procure trilhas de programacao, web, design digital ou fundamentos de tecnologia.", SENAI_FUTURO_DIGITAL),
                    formacao("Tecnico em Informatica", "IFRO Campus Cacoal", "Cacoal", "Tecnico", "Ajuda a comecar por logica, programacao e projetos pequenos.", IFRO_CACOAL_CURSOS),
                    formacao("Informatica e cursos livres", "SENAC-RO", "Pimenta Bueno", "Livre/Tecnico", "Boa entrada para fundamentos digitais antes de partir para engines e prototipos.", SENAC_INFORMATICA),
                    formacao("Polo EAD e editais", "IFRO", "Pimenta Bueno e regiao", "EAD/Presencial", "Acompanhe editais de tecnologia para fortalecer a base antes de especializar em jogos.", IFRO_EAD)
            )),
            Map.entry("inteligencia-artificial", List.of(
                    formacao("Tecnico em Informatica", "IFRO Campus Cacoal", "Cacoal", "Tecnico", "Primeiro passo para logica, programacao e organizacao de dados.", IFRO_CACOAL_CURSOS),
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Rondonia", "Livre/Tecnico", "Procure trilhas de programacao, dados, automacao e fundamentos de tecnologia.", SENAI_FUTURO_DIGITAL),
                    formacao("Cursos de Informatica", "SENAC-RO", "Pimenta Bueno", "Livre/Tecnico", "Ajuda a reforcar informatica, ferramentas digitais e base para estudar dados depois.", SENAC_INFORMATICA),
                    formacao("Polo EAD e editais", "IFRO", "Pimenta Bueno e regiao", "EAD/Presencial", "Acompanhe cursos tecnicos, FIC e editais que podem abrir oportunidades em tecnologia.", IFRO_EAD)
            )),
            Map.entry("gestao-ti", List.of(
                    formacao("Gestao e Informatica", "SENAC-RO", "Pimenta Bueno", "Livre/Tecnico", "Procure cursos de gestao, atendimento, informatica e processos administrativos.", SENAC_PIMENTA_BUENO),
                    formacao("Tecnologia a distancia", "Cruzeiro do Sul Virtual", "Rolim de Moura", "EAD/Polo", "Opcoes EAD de tecnologia podem ajudar quem precisa estudar perto de casa.", CRUZEIRO_SUL_ROLIM_MOURA),
                    formacao("Cursos de TI no SENAI-RO", "SENAI-RO", "Rondonia", "Livre/Tecnico", "Bom para entender operacao, processos tecnicos e contexto de equipes.", SENAI_RO),
                    formacao("Polo EAD e editais", "IFRO", "Pimenta Bueno e regiao", "EAD/Presencial", "Acompanhe oportunidades de tecnologia, gestao publica, processos e formacao continuada.", IFRO_EAD)
            ))
    );

    private final Map<String, List<RecursoOnlineView>> recursosOnline = Map.ofEntries(
            Map.entry("desenvolvimento-software", List.of(
                    recursoOnline("Curso online", "Programacao e tecnologia", "Fundacao Bradesco Escola Virtual", "Gratuito", "Cursos introdutorios para comecar por logica, fundamentos de TI e programacao.", FUNDACAO_BRADESCO_ESCOLA_VIRTUAL),
                    recursoOnline("Trilha guiada", "Modulos de desenvolvimento", "Microsoft Learn", "Gratuito", "Treinos curtos para praticar fundamentos, web, cloud e ferramentas usadas no mercado.", MICROSOFT_LEARN),
                    recursoOnline("Curso online", "Programacao, Python e carreira tech", "Cisco Networking Academy", "Gratuito", "Cursos de tecnologia em ritmo proprio para fortalecer base antes de projetos maiores.", CISCO_NETACAD)
            )),
            Map.entry("dados-bi", List.of(
                    recursoOnline("Curso pratico", "Python, Pandas e visualizacao", "Kaggle Learn", "Gratuito", "Mini-cursos curtos para praticar analise de dados direto em bases e notebooks.", KAGGLE_LEARN),
                    recursoOnline("Trilha guiada", "Power BI e fundamentos de dados", "Microsoft Learn", "Gratuito", "Conteudos oficiais para dashboards, indicadores, dados e ferramentas Microsoft.", MICROSOFT_LEARN),
                    recursoOnline("Catalogo publico", "Analise e ciencia de dados", "Escola Virtual Gov", "Gratuito com certificado", "Cursos abertos para dados, governo digital, informacao e transformacao digital.", EVG_CATALOGO)
            )),
            Map.entry("seguranca-cibernetica", List.of(
                    recursoOnline("Curso online", "Redes e ciberseguranca", "Cisco Networking Academy", "Gratuito", "Cursos introdutorios de redes, seguranca, dispositivos e fundamentos digitais.", CISCO_NETACAD),
                    recursoOnline("Guia tecnico", "OWASP Top 10", "OWASP", "Aberto", "Referencia importante para entender riscos comuns em aplicacoes web.", "https://owasp.org/www-project-top-ten/"),
                    recursoOnline("Curso online", "Fundamentos de TI: Hardware e Software", "Fundacao Bradesco Escola Virtual", "Gratuito", "Base de informatica, sistemas operacionais e principios de seguranca da informacao.", FUNDACAO_BRADESCO_FUNDAMENTOS_TI)
            )),
            Map.entry("infraestrutura-redes", List.of(
                    recursoOnline("Curso online", "Networking e dispositivos", "Cisco Networking Academy", "Gratuito", "Boa porta de entrada para redes, conectividade e suporte tecnico.", CISCO_NETACAD),
                    recursoOnline("Curso online", "Fundamentos de TI: Hardware e Software", "Fundacao Bradesco Escola Virtual", "Gratuito", "Ajuda a revisar computador, sistemas, armazenamento e seguranca basica.", FUNDACAO_BRADESCO_FUNDAMENTOS_TI),
                    recursoOnline("Trilha guiada", "Cloud, suporte e administracao", "Microsoft Learn", "Gratuito", "Modulos oficiais para entender nuvem, infraestrutura, Microsoft 365 e Azure.", MICROSOFT_LEARN)
            )),
            Map.entry("ux-ui-design", List.of(
                    recursoOnline("Curso guiado", "Figma Design para iniciantes", "Figma Learn", "Aberto", "Curso oficial para praticar frames, componentes, prototipos e layout responsivo.", FIGMA_BEGINNERS),
                    recursoOnline("Guia visual", "Material Design", "Google", "Aberto", "Sistema de design com componentes, padroes, acessibilidade e boas praticas de interface.", MATERIAL_DESIGN),
                    recursoOnline("Referencia", "Acessibilidade digital", "W3C WAI", "Aberto", "Material para entender contraste, leitura, navegacao e inclusao em interfaces.", W3C_WCAG)
            )),
            Map.entry("game-design", List.of(
                    recursoOnline("Curso e tutorial", "Unity Learn", "Unity", "Gratuito", "Cursos e tutoriais oficiais para criar cenas, mecanicas e primeiros jogos.", UNITY_LEARN),
                    recursoOnline("Documentacao", "Godot Docs", "Godot Engine", "Aberto", "Documentacao oficial de uma engine gratuita e acessivel para prototipos pequenos.", GODOT_DOCS),
                    recursoOnline("Comunidade", "Global Game Jam", "Global Game Jam", "Aberto", "Evento e comunidade para praticar prototipos, trabalho em equipe e criatividade.", GLOBAL_GAME_JAM)
            )),
            Map.entry("inteligencia-artificial", List.of(
                    recursoOnline("Curso pratico", "Machine Learning Crash Course", "Google for Developers", "Gratuito", "Curso introdutorio com videos, visualizacoes interativas e exercicios praticos.", GOOGLE_ML_CRASH_COURSE),
                    recursoOnline("Curso pratico", "Python, ML e ciencia de dados", "Kaggle Learn", "Gratuito", "Mini-cursos para praticar Python, dados, machine learning e etica em IA.", KAGGLE_LEARN),
                    recursoOnline("Catalogo publico", "Inteligencia artificial e governo digital", "Escola Virtual Gov", "Gratuito com certificado", "Cursos abertos para experimentar IA, dados e transformacao digital.", EVG_CATALOGO)
            )),
            Map.entry("gestao-ti", List.of(
                    recursoOnline("Guia oficial", "Scrum Guide", "Scrum.org", "Aberto", "Referencia curta para entender papeis, eventos e artefatos do Scrum.", SCRUM_GUIDES),
                    recursoOnline("Guia pratico", "Agile Coach", "Atlassian", "Aberto", "Conteudos claros sobre Scrum, Kanban, planejamento, backlog e trabalho em equipe.", ATLASSIAN_AGILE),
                    recursoOnline("Catalogo publico", "Gestao, lideranca e transformacao digital", "Escola Virtual Gov", "Gratuito com certificado", "Cursos abertos para comunicacao, lideranca, gestao publica e projetos.", EVG_CATALOGO)
            ))
    );

    public DetalheAreaView buscarPorSlug(String slug) {
        DetalheAreaView detalhe = detalhes.get(slug);
        if (detalhe == null) {
            throw new IllegalArgumentException("Detalhes nao encontrados para a area: " + slug);
        }
        return new DetalheAreaView(
                detalhe.getSalario(),
                detalhe.getMercado(),
                detalhe.getPlanoCarreira(),
                detalhe.getReferencias(),
                profissoes.getOrDefault(slug, List.of()),
                formacoes.getOrDefault(slug, List.of()),
                recursosOnline.getOrDefault(slug, List.of()),
                detalhe.getFerramentas()
        );
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

    private static ProfissaoAreaView profissao(String titulo, String nivel, String descricao) {
        return new ProfissaoAreaView(titulo, nivel, descricao);
    }

    private static FormacaoLocalView formacao(
            String titulo,
            String instituicao,
            String cidade,
            String modalidade,
            String descricao,
            String url
    ) {
        return new FormacaoLocalView(titulo, instituicao, cidade, modalidade, descricao, url);
    }

    private static RecursoOnlineView recursoOnline(
            String tipo,
            String titulo,
            String plataforma,
            String custo,
            String descricao,
            String url
    ) {
        return new RecursoOnlineView(tipo, titulo, plataforma, custo, descricao, url);
    }
}
