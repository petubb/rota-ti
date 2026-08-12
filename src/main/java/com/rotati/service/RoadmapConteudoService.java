package com.rotati.service;

import com.rotati.dto.RoadmapCompletoView;
import com.rotati.dto.RoadmapEtapaInterativaView;
import com.rotati.dto.RoadmapEtapaView;
import com.rotati.dto.RoadmapRecursoView;
import com.rotati.model.AreaTi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoadmapConteudoService {

    private final DetalheAreaService detalheAreaService;

    private final Map<String, List<ComplementoEtapa>> complementos = Map.ofEntries(
            Map.entry("desenvolvimento-software", List.of(
                    complemento(
                            "Programar e aprender a decompor um problema grande em instrucoes pequenas. Nesta fase, o mais importante nao e decorar uma linguagem, mas entender variaveis, decisoes, repeticoes, funcoes e como investigar um erro sem desistir no primeiro teste.",
                            "Crie um programa de terminal que receba dados, tome uma decisao e mostre um resultado. Depois, publique o codigo com um README curto explicando o que voce aprendeu.",
                            recurso("Tutorial", "Python: introducao informal", "Exemplos oficiais em portugues para praticar os primeiros comandos.", "https://docs.python.org/pt-br/3/tutorial/introduction.html"),
                            recurso("Livro aberto", "Pro Git", "Guia oficial e gratuito para entender commits, branches e historico.", "https://git-scm.com/book/pt-br/v2")
                    ),
                    complemento(
                            "Uma aplicacao normalmente precisa apresentar informacoes e guarda-las. HTML organiza o conteudo, CSS cuida da apresentacao, JavaScript adiciona comportamento e o banco preserva dados mesmo depois que o programa termina.",
                            "Monte uma pagina com formulario e listagem. Primeiro use dados simulados; depois desenhe as tabelas que seriam necessarias para guardar as mesmas informacoes em um banco.",
                            recurso("Trilha", "MDN: desenvolvimento web", "Conteudo mantido pela comunidade Mozilla sobre a base da web.", "https://developer.mozilla.org/pt-BR/docs/Learn_web_development"),
                            recurso("Documentacao", "MySQL Tutorial", "Introducao oficial ao uso do MySQL e da linguagem SQL.", "https://dev.mysql.com/doc/refman/8.4/en/tutorial.html")
                    ),
                    complemento(
                            "MVC separa responsabilidades para o projeto continuar compreensivel quando cresce. O controller recebe a requisicao, o service concentra regras, o repository conversa com o banco e o model representa os dados do dominio.",
                            "Implemente um cadastro pequeno com Controller, Service, Repository e Model. Explique em um diagrama qual camada participa de cada parte da requisicao.",
                            recurso("Guia oficial", "Spring: Serving Web Content", "Primeiro fluxo MVC com Spring Boot e Thymeleaf.", "https://spring.io/guides/gs/serving-web-content"),
                            recurso("Guia oficial", "Spring: Accessing Data with JPA", "Exemplo de persistencia usando repository e entidades.", "https://spring.io/guides/gs/accessing-data-jpa")
                    ),
                    complemento(
                            "Um projeto completo combina interface, validacao, persistencia, autenticacao e regras que resolvem um problema real. Nesta etapa, vale reduzir o tamanho da ideia para conseguir terminar um fluxo inteiro com qualidade.",
                            "Escolha um unico fluxo principal, como cadastro e acompanhamento de tarefas. Inclua estados de erro e vazio, login e pelo menos uma regra que impeça acesso indevido.",
                            recurso("Guia oficial", "Spring Security", "Arquitetura e recursos para autenticacao e autorizacao.", "https://docs.spring.io/spring-security/reference/index.html"),
                            recurso("Guia oficial", "Validation", "Validacao de dados recebidos em aplicacoes Spring.", "https://spring.io/guides/gs/validating-form-input")
                    ),
                    complemento(
                            "Testes, configuracoes externas e deploy transformam um codigo que funciona apenas no computador do autor em uma aplicacao que outras pessoas conseguem usar. Senhas e chaves nunca devem ficar gravadas no repositorio.",
                            "Escreva testes para duas regras importantes, configure variaveis de ambiente e publique o projeto. Abra a versao online pelo celular e registre os problemas encontrados.",
                            recurso("Documentacao", "JUnit 5 User Guide", "Referencia oficial para organizar testes automatizados em Java.", "https://junit.org/junit5/docs/current/user-guide/"),
                            recurso("Deploy", "Render: deploy de Java", "Documentacao da plataforma usada no deploy do Rota TI.", "https://render.com/docs/deploy-java")
                    ),
                    complemento(
                            "Portfolio nao e quantidade de repositorios: e evidencia de que voce entende um problema, toma decisoes e conclui uma entrega. Um bom projeto explica contexto, arquitetura, dificuldades, testes e o que seria melhorado depois.",
                            "Selecione ate tres projetos, melhore README, prints e instrucoes de execucao. Treine uma apresentacao de tres minutos para uma pessoa tecnica e outra nao tecnica.",
                            recurso("Pratica", "GitHub Skills", "Exercicios guiados para fortalecer GitHub e colaboracao.", "https://skills.github.com/"),
                            recurso("Documentacao", "Sobre perfis no GitHub", "Como organizar o perfil que apresenta seus projetos.", "https://docs.github.com/pt/account-and-profile/setting-up-and-managing-your-github-profile/customizing-your-profile/about-your-profile")
                    )
            )),
            Map.entry("dados-bi", List.of(
                    complemento(
                            "Antes de criar graficos, e preciso formular perguntas e cuidar da qualidade da base. Valores ausentes, nomes escritos de formas diferentes e linhas duplicadas podem mudar completamente uma conclusao.",
                            "Escolha uma planilha publica, crie um dicionario de dados e responda tres perguntas usando filtros, formulas e tabela dinamica.",
                            recurso("Trilha", "Microsoft Learn: conceitos de dados", "Fundamentos de dados, analise e armazenamento.", "https://learn.microsoft.com/pt-br/training/paths/azure-data-fundamentals-explore-core-data-concepts/"),
                            recurso("Dados publicos", "dados.gov.br", "Catalogo brasileiro para encontrar bases reais de pratica.", "https://dados.gov.br/")
                    ),
                    complemento(
                            "SQL permite consultar grandes volumes sem mover tudo para uma planilha. A habilidade central e transformar uma pergunta em filtros, agrupamentos e relacionamentos entre tabelas, sempre conferindo se a consulta responde exatamente ao que foi pedido.",
                            "Modele duas tabelas relacionadas e escreva consultas com WHERE, GROUP BY e JOIN. Para cada consulta, anote a pergunta de negocio que ela responde.",
                            recurso("Documentacao", "MySQL: SELECT", "Referencia oficial para consultas e filtros.", "https://dev.mysql.com/doc/refman/8.4/en/select.html"),
                            recurso("Documentacao", "MySQL: JOIN", "Sintaxe oficial para relacionar dados de tabelas diferentes.", "https://dev.mysql.com/doc/refman/8.4/en/join.html")
                    ),
                    complemento(
                            "Dashboard bom destaca poucos indicadores e ajuda uma decisao. A escolha do grafico depende da pergunta: linha para evolucao, barra para comparacao e cartao para um numero principal. Decoracao nunca deve competir com a informacao.",
                            "Crie um painel com no maximo seis visuais, filtros claros e um paragrafo final explicando os dois achados mais importantes.",
                            recurso("Trilha oficial", "Treinamento do Power BI", "Modulos da Microsoft sobre modelagem, visualizacao e relatorios.", "https://learn.microsoft.com/pt-br/training/powerplatform/power-bi"),
                            recurso("Guia", "Power BI: orientacao de design", "Boas praticas oficiais para relatorios mais claros.", "https://learn.microsoft.com/pt-br/power-bi/create-reports/service-dashboards-design-tips")
                    ),
                    complemento(
                            "Python entra quando a limpeza ou a analise precisa ser repetida. Com Pandas, cada transformacao fica registrada no codigo, o que facilita corrigir erros, atualizar a base e explicar de onde saiu cada resultado.",
                            "Leia um CSV com Pandas, trate campos vazios, crie duas colunas calculadas e gere ao menos dois graficos. Execute tudo novamente com o arquivo original para provar que o processo e reproduzivel.",
                            recurso("Documentacao", "Pandas: primeiros passos", "Tutoriais oficiais para leitura, limpeza e analise de dados.", "https://pandas.pydata.org/docs/getting_started/intro_tutorials/index.html"),
                            recurso("Tutorial", "Python: estruturas de dados", "Base oficial para listas, dicionarios e repeticoes.", "https://docs.python.org/pt-br/3/tutorial/datastructures.html")
                    ),
                    complemento(
                            "Um case de dados precisa ligar pergunta, metodo e conclusao. Mostrar apenas graficos nao deixa claro o raciocinio. Tambem e importante declarar limitacoes, porque correlacao, amostra pequena e dados incompletos podem levar a recomendacoes erradas.",
                            "Publique uma analise com contexto, fonte, limpeza, tres achados, limitacoes e uma recomendacao. Peça para outra pessoa ler e contar o que entendeu.",
                            recurso("Plataforma", "Kaggle Datasets", "Bases e notebooks para praticar e comparar abordagens.", "https://www.kaggle.com/datasets"),
                            recurso("Livro aberto", "Fundamentals of Data Visualization", "Referencia aberta sobre escolhas de visualizacao.", "https://clauswilke.com/dataviz/")
                    ),
                    complemento(
                            "Na rotina profissional, o analista conversa com pessoas, valida definicoes e acompanha se o indicador continua confiavel. Saber apresentar uma descoberta em linguagem simples vale tanto quanto dominar a ferramenta.",
                            "Monte um portfolio com uma planilha tratada, um arquivo SQL, um dashboard e um case em Python. Prepare uma explicacao curta do impacto de cada projeto.",
                            recurso("Carreira", "Microsoft Learn Career Paths", "Trilhas e papeis ligados a dados e Power Platform.", "https://learn.microsoft.com/pt-br/training/career-paths/"),
                            recurso("Pratica", "GitHub Skills", "Organizacao e publicacao dos arquivos do portfolio.", "https://skills.github.com/")
                    )
            )),
            Map.entry("seguranca-cibernetica", List.of(
                    complemento(
                            "Seguranca protege sistemas que dependem de redes, protocolos, usuarios e sistemas operacionais. Sem entender DNS, HTTP, portas, processos e permissoes, alertas parecem aleatorios e fica dificil investigar a causa real.",
                            "Desenhe o caminho de uma requisicao do navegador ate um servidor e monte um laboratorio Linux para praticar usuarios, grupos, permissoes e logs.",
                            recurso("Curso", "Cisco Skills for All", "Cursos introdutorios oficiais de redes e ciberseguranca.", "https://skillsforall.com/"),
                            recurso("Referencia", "MDN: visao geral do HTTP", "Explicacao do protocolo usado por aplicacoes web.", "https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Guides/Overview")
                    ),
                    complemento(
                            "A maior parte da defesa diaria vem de controles simples bem aplicados: autenticacao forte, menor privilegio, atualizacao, backup e capacidade de reconhecer engenharia social. Seguranca tambem envolve orientar pessoas sem culpa-las.",
                            "Crie uma checklist de protecao para uma escola ou pequena empresa e simule a resposta a um e-mail suspeito, registrando sinais, decisao e orientacao.",
                            recurso("Guia", "CISA: Avoiding Social Engineering", "Orientacoes publicas sobre phishing e engenharia social.", "https://www.cisa.gov/news-events/news/avoiding-social-engineering-and-phishing-attacks"),
                            recurso("Guia", "NIST: senhas e autenticacao", "Recomendacoes sobre identidade e autenticacao digital.", "https://pages.nist.gov/800-63-4/sp800-63b.html")
                    ),
                    complemento(
                            "Aplicacoes web falham quando confiam demais em dados enviados pelo usuario ou verificam apenas se alguem esta logado. Validacao, autorizacao por recurso, sessoes seguras e tratamento cuidadoso de erros reduzem riscos comuns.",
                            "Revise um projeto proprio usando o OWASP Top 10. Para cada risco encontrado, descreva impacto, como reproduzir de forma controlada e como corrigir.",
                            recurso("Referencia", "OWASP Top 10", "Principais categorias de risco em aplicacoes web.", "https://owasp.org/www-project-top-ten/"),
                            recurso("Guia", "OWASP Cheat Sheet Series", "Listas praticas de controles para desenvolvimento seguro.", "https://cheatsheetseries.owasp.org/")
                    ),
                    complemento(
                            "Laboratorios autorizados permitem observar uma vulnerabilidade sem atingir sistemas de terceiros. O objetivo nao e somente encontrar a falha: e registrar evidencia suficiente, entender a causa e recomendar uma correcao responsavel.",
                            "Conclua tres laboratorios iniciantes, escreva um relatorio por laboratorio e inclua escopo, passos, evidencia, impacto e correcao. Nunca teste enderecos sem autorizacao.",
                            recurso("Laboratorio", "PortSwigger Web Security Academy", "Laboratorios gratuitos e legais de seguranca web.", "https://portswigger.net/web-security"),
                            recurso("Laboratorio", "OWASP Juice Shop", "Aplicacao intencionalmente vulneravel para estudo controlado.", "https://owasp.org/www-project-juice-shop/")
                    ),
                    complemento(
                            "Monitorar e responder significa transformar eventos em uma linha do tempo: o que aconteceu, qual ativo foi afetado, qual a gravidade e o que precisa ser contido. Nem todo alerta e incidente, por isso contexto e prioridade sao essenciais.",
                            "Use logs simulados para identificar um comportamento suspeito. Monte uma linha do tempo e um plano de resposta com contencao, recuperacao e aprendizado posterior.",
                            recurso("Guia", "NIST Incident Response", "Orientacoes para preparar e conduzir resposta a incidentes.", "https://csrc.nist.gov/pubs/sp/800/61/r3/final"),
                            recurso("Referencia", "MITRE ATT&CK", "Base publica de tecnicas observadas em ataques reais.", "https://attack.mitre.org/")
                    ),
                    complemento(
                            "As portas de entrada incluem SOC, governanca, redes, DevSecOps e testes autorizados. Escolher um foco inicial ajuda a montar um portfolio coerente, mas a base de redes, sistemas e etica continua importante em qualquer especializacao.",
                            "Compare dez vagas, conte os requisitos mais frequentes e escolha uma trilha inicial. Organize relatorios, laboratorios e checklists em um portfolio sem expor dados sensiveis.",
                            recurso("Framework", "NIST Cybersecurity Framework", "Estrutura para entender governanca e gestao de riscos.", "https://www.nist.gov/cyberframework"),
                            recurso("Carreira", "CyberSeek Career Pathway", "Mapa de funcoes e habilidades da area de ciberseguranca.", "https://www.cyberseek.org/pathway.html")
                    )
            )),
            Map.entry("infraestrutura-redes", List.of(
                    complemento(
                            "Suporte tecnico comeca por observar sintomas, fazer perguntas e registrar o que foi testado. Conhecer componentes, armazenamento, sistema operacional e perifericos ajuda a separar falha fisica, configuracao e uso incorreto.",
                            "Desmonte o problema de um computador real ou virtual em etapas: sintoma, hipoteses, testes, solucao e prevencao. Entregue um chamado bem documentado.",
                            recurso("Curso", "Fundamentos de TI", "Curso gratuito sobre hardware e software da Escola Virtual.", "https://www.ev.org.br/cursos/fundamentos-de-ti-hardware-e-software"),
                            recurso("Documentacao", "Microsoft Learn: Windows", "Conteudos oficiais de configuracao e suporte do Windows.", "https://learn.microsoft.com/pt-br/training/windows/")
                    ),
                    complemento(
                            "Redes conectam dispositivos por regras previsiveis. IP identifica, mascara define a rede, gateway leva para outros destinos, DNS traduz nomes e DHCP distribui configuracoes. Diagnostico eficiente testa uma camada por vez.",
                            "Desenhe uma rede pequena com enderecos, roteador e dispositivos. Depois simule falhas de DNS e conectividade e registre quais comandos confirmaram cada causa.",
                            recurso("Curso", "Cisco Skills for All", "Trilhas oficiais de fundamentos e diagnostico de redes.", "https://skillsforall.com/"),
                            recurso("Referencia", "Cloudflare Learning: DNS", "Explicacao visual do funcionamento do DNS.", "https://www.cloudflare.com/pt-br/learning/dns/what-is-dns/")
                    ),
                    complemento(
                            "Administrar sistemas envolve contas, grupos, permissoes, servicos, atualizacoes e logs. Automatizar uma tarefa repetitiva reduz erro humano, mas todo script precisa ser testado em ambiente seguro antes de chegar a uma maquina importante.",
                            "Crie usuarios e grupos em Windows ou Linux, configure uma pasta compartilhada com permissoes diferentes e escreva um script curto para automatizar uma verificacao.",
                            recurso("Documentacao", "Linux command line basics", "Introducao da Ubuntu ao terminal e arquivos.", "https://ubuntu.com/tutorials/command-line-for-beginners"),
                            recurso("Documentacao", "PowerShell", "Referencia oficial para automacao no Windows.", "https://learn.microsoft.com/pt-br/powershell/")
                    ),
                    complemento(
                            "Virtualizacao permite criar varios ambientes isolados no mesmo computador. Ela e ideal para praticar servidor, cliente, rede interna, backup e restauracao sem colocar a maquina principal em risco.",
                            "Monte duas maquinas virtuais em uma rede isolada. Configure um servico, acesso entre elas, snapshot e restauracao, documentando enderecos e credenciais de laboratorio.",
                            recurso("Manual", "VirtualBox User Manual", "Documentacao oficial de maquinas virtuais e redes.", "https://www.virtualbox.org/manual/"),
                            recurso("Documentacao", "Ubuntu Server", "Guias oficiais para administrar um servidor Linux.", "https://documentation.ubuntu.com/server/")
                    ),
                    complemento(
                            "Cloud oferece recursos sob demanda, mas nao elimina a necessidade de rede, identidade, custo e monitoramento. Observabilidade ajuda a perceber indisponibilidade antes que o usuario precise avisar.",
                            "Publique um servico simples em uma camada gratuita ou laboratorio, configure uma verificacao de disponibilidade e estime o que geraria custo em um ambiente maior.",
                            recurso("Curso", "AWS Skill Builder", "Treinamentos oficiais de fundamentos de nuvem.", "https://skillbuilder.aws/"),
                            recurso("Trilha", "Azure Fundamentals", "Conceitos de nuvem, servicos e governanca na Microsoft Learn.", "https://learn.microsoft.com/pt-br/training/paths/describe-cloud-concepts/")
                    ),
                    complemento(
                            "Na operacao profissional, documentacao e comunicacao reduzem tempo de parada. Um tecnico forte sabe priorizar, escalar quando necessario e deixar informacoes para a proxima pessoa continuar o atendimento.",
                            "Organize diagramas, chamados resolvidos e laboratorios em um portfolio. Compare requisitos de vagas de suporte, redes e cloud e identifique o proximo conhecimento a aprofundar.",
                            recurso("Pratica", "GitHub Skills", "Use repositorios para versionar scripts e documentacao tecnica.", "https://skills.github.com/"),
                            recurso("Referencia", "ITIL overview", "Visao geral sobre gestao de servicos de TI.", "https://www.peoplecert.org/browse-certifications/it-governance-and-service-management/ITIL-1")
                    )
            )),
            Map.entry("ux-ui-design", List.of(
                    complemento(
                            "Hierarquia, contraste, espacamento e tipografia orientam o olhar antes de qualquer clique. Uma tela bonita pode continuar confusa; o objetivo e tornar conteudo, acao principal e estado do sistema faceis de perceber.",
                            "Escolha uma tela que voce considera confusa, liste os problemas de hierarquia e faca um redesign em baixa fidelidade antes de trabalhar cores e detalhes.",
                            recurso("Sistema", "Material Design", "Principios e componentes para interfaces consistentes.", "https://m3.material.io/"),
                            recurso("Guia", "Laws of UX", "Principios de psicologia aplicados a produtos digitais.", "https://lawsofux.com/")
                    ),
                    complemento(
                            "Figma ajuda a criar fluxos navegaveis e componentes reutilizaveis. Auto layout, estilos e variantes tornam o arquivo mais consistente e mostram como a interface deve responder a textos e tamanhos diferentes.",
                            "Monte um fluxo de cadastro com pelo menos cinco telas e estados de erro, carregamento e sucesso. Use componentes em vez de copiar botoes manualmente.",
                            recurso("Curso oficial", "Figma Design for beginners", "Curso introdutorio mantido pela equipe do Figma.", "https://help.figma.com/hc/en-us/articles/30848209492887-Course-overview-Figma-Design-for-beginners-2025"),
                            recurso("Documentacao", "Figma: prototyping", "Como conectar telas e testar interacoes.", "https://help.figma.com/hc/en-us/categories/360002051613-Prototyping")
                    ),
                    complemento(
                            "Pesquisa reduz o risco de desenhar apenas para si mesmo. Perguntas abertas, observacao e sintese ajudam a descobrir objetivos e dificuldades reais, mas poucas entrevistas nao representam automaticamente todo o publico.",
                            "Converse com tres pessoas sobre uma tarefa digital, sem apresentar sua solucao primeiro. Agrupe padroes, contradicoes e oportunidades em um resumo anonimo.",
                            recurso("Artigo", "Nielsen Norman Group: user interviews", "Orientacoes praticas para planejar entrevistas de usuarios.", "https://www.nngroup.com/articles/user-interviews/"),
                            recurso("Guia", "IDEO Design Kit", "Metodos abertos de pesquisa e design centrado nas pessoas.", "https://www.designkit.org/methods")
                    ),
                    complemento(
                            "Prototipo serve para responder perguntas antes de investir na versao final. Um teste de usabilidade observa se a pessoa completa uma tarefa, onde hesita e o que interpreta errado; nao e uma prova do participante.",
                            "Defina tres tarefas, teste o prototipo com tres pessoas e registre sucesso, dificuldade e comentario. Priorize mudancas pelos problemas que mais impediram a conclusao.",
                            recurso("Guia", "Usability Testing 101", "Introducao da Nielsen Norman Group a testes de usabilidade.", "https://www.nngroup.com/articles/usability-testing-101/"),
                            recurso("Documentacao", "Figma: share prototypes", "Como compartilhar um prototipo para validacao.", "https://help.figma.com/hc/en-us/articles/360040531773-Share-files-and-prototypes")
                    ),
                    complemento(
                            "Acessibilidade amplia quem consegue usar o produto e melhora clareza para todos. Contraste, foco visivel, teclado, texto alternativo e alvos de toque sao parte do projeto desde o inicio, nao um acabamento posterior.",
                            "Revise seu prototipo com uma checklist de acessibilidade, teste a ordem de foco em uma implementacao e corrija pelo menos cinco barreiras encontradas.",
                            recurso("Tutoriais", "W3C WAI Tutorials", "Exemplos oficiais de formularios, imagens, tabelas e navegacao acessivel.", "https://www.w3.org/WAI/tutorials/"),
                            recurso("Padrao", "WCAG Overview", "Visao geral das diretrizes internacionais de acessibilidade.", "https://www.w3.org/WAI/standards-guidelines/wcag/")
                    ),
                    complemento(
                            "Um case de portfolio conta a historia de uma decisao. Deve mostrar problema, papel exercido, pesquisa, alternativas, validacao, resultado e aprendizado. So exibir telas finais esconde justamente o raciocinio que empresas querem avaliar.",
                            "Prepare dois cases: um individual e um colaborativo. Peça para alguem ler sem explicacao oral e veja se contexto, processo e impacto ficam claros.",
                            recurso("Artigo", "UX portfolio: what hiring managers look for", "Criterios usados na avaliacao de portfolios de UX.", "https://www.nngroup.com/articles/ux-design-portfolios/"),
                            recurso("Acessibilidade", "W3C: writing for accessibility", "Boas praticas para conteudo compreensivel.", "https://www.w3.org/WAI/tips/writing/")
                    )
            )),
            Map.entry("game-design", List.of(
                    complemento(
                            "Game design organiza objetivos, regras, decisoes e feedback. A mecanica descreve o que o jogador faz; a dinamica e o comportamento que surge; a experiencia e o sentimento produzido durante a partida.",
                            "Analise tres jogos curtos e escreva objetivo, acao principal, regra de falha, feedback e curva de dificuldade de cada um.",
                            recurso("Curso", "Unity Learn: Game Design", "Conteudos oficiais sobre design e prototipacao de jogos.", "https://learn.unity.com/"),
                            recurso("Artigos", "Game Developer", "Relatos e analises de profissionais da industria.", "https://www.gamedeveloper.com/design")
                    ),
                    complemento(
                            "Prototipo rapido testa se a ideia central e divertida antes de produzir arte, historia e muitas fases. Papel, formas simples e recursos temporarios permitem alterar regras com baixo custo.",
                            "Crie um jogo com uma unica mecanica em ate uma semana. Use arte simples, defina uma condicao de vitoria e publique uma primeira versao jogavel.",
                            recurso("Tutorial", "Godot: seu primeiro jogo 2D", "Passo a passo oficial para montar um jogo pequeno.", "https://docs.godotengine.org/pt-br/4.x/getting_started/first_2d_game/index.html"),
                            recurso("Documentacao", "Godot: introducao", "Conceitos centrais da engine em portugues.", "https://docs.godotengine.org/pt-br/4.x/getting_started/introduction/index.html")
                    ),
                    complemento(
                            "Uma fase ensina sem depender apenas de texto. Posicao, ritmo, repeticao e recompensa apresentam uma regra, deixam o jogador praticar e depois combinam desafios. Narrativa deve apoiar as escolhas, nao interromper toda a experiencia.",
                            "Desenhe uma fase em papel com tres momentos: ensinar, testar e combinar. Implemente o fluxo e observe se alguem aprende sem receber explicacao externa.",
                            recurso("Documentacao", "Godot: scenes and nodes", "Base para organizar fases, objetos e comportamentos.", "https://docs.godotengine.org/en/stable/getting_started/step_by_step/scenes_and_nodes.html"),
                            recurso("Palestras", "GDC Vault: free content", "Palestras e estudos de caso sobre producao e design.", "https://www.gdcvault.com/free")
                    ),
                    complemento(
                            "Playtest mostra o que o jogo realmente comunica. O designer observa sem conduzir, separa gosto pessoal de problema recorrente e decide quais mudancas testara na proxima versao.",
                            "Realize duas rodadas com pelo menos tres jogadores. Registre onde pararam, erros repetidos, momentos divertidos e quais ajustes melhoraram a segunda rodada.",
                            recurso("Guia", "Playtesting in game design", "Orientacoes da Unity sobre iteracao e feedback.", "https://learn.unity.com/tutorial/playtesting"),
                            recurso("Comunidade", "Global Game Jam", "Eventos para prototipar e testar jogos em equipe.", "https://globalgamejam.org/")
                    ),
                    complemento(
                            "Publicar ensina empacotamento, controles, instrucoes, compatibilidade e comunicacao. Uma pagina clara precisa dizer o que e o jogo, como jogar e em quais dispositivos funciona.",
                            "Exporte uma versao web ou executavel, crie capa e descricao, publique e acompanhe ao menos cinco feedbacks antes de preparar uma atualizacao.",
                            recurso("Documentacao", "Godot: exporting projects", "Como gerar builds para web e outras plataformas.", "https://docs.godotengine.org/en/stable/tutorials/export/exporting_projects.html"),
                            recurso("Plataforma", "itch.io: getting started", "Guia para criar pagina e publicar um projeto jogavel.", "https://itch.io/docs/creators/getting-started")
                    ),
                    complemento(
                            "Portfolio jogavel deve deixar claro qual foi sua contribuicao: regras, level design, narrativa, programacao ou producao. Jogos pequenos e terminados costumam demonstrar mais do que uma ideia enorme incompleta.",
                            "Selecione ate tres jogos, escreva uma pagina por projeto com sua funcao, decisoes e resultados de playtest. Participe de uma game jam para praticar colaboracao e prazo.",
                            recurso("Evento", "Global Game Jam", "Rede internacional de eventos e projetos colaborativos.", "https://globalgamejam.org/"),
                            recurso("Plataforma", "itch.io game jams", "Calendario de desafios para construir portfolio.", "https://itch.io/jams")
                    )
            )),
            Map.entry("inteligencia-artificial", List.of(
                    complemento(
                            "IA aplicada comeca em programacao e dados. Python permite automatizar experimentos, Pandas organiza tabelas e graficos ajudam a enxergar distribuicoes, valores estranhos e relacoes antes de qualquer modelo.",
                            "Conclua exercicios de Python, carregue uma base em um notebook, trate campos ausentes e explique tres observacoes usando graficos simples.",
                            recurso("Tutorial", "Python: introducao informal", "Fundamentos oficiais de numeros, textos, listas e repeticoes.", "https://docs.python.org/pt-br/3/tutorial/introduction.html"),
                            recurso("Documentacao", "Pandas: primeiros passos", "Tutoriais oficiais para trabalhar com dados tabulares.", "https://pandas.pydata.org/docs/getting_started/intro_tutorials/index.html")
                    ),
                    complemento(
                            "A matematica entra para explicar variacao, distancia, erro e ajuste. No inicio, media, desvio, porcentagem, correlacao e leitura de graficos sao mais uteis do que tentar dominar todo o calculo antes de praticar.",
                            "Calcule medidas de uma base pequena, compare duas distribuicoes e explique com suas palavras por que correlacao nao prova causa.",
                            recurso("Preparacao", "Google ML: prerequisites", "Revisao recomendada de algebra, estatistica e Python.", "https://developers.google.com/machine-learning/crash-course/prereqs-and-prework"),
                            recurso("Livro aberto", "OpenIntro Statistics", "Material gratuito para fundamentos de estatistica.", "https://www.openintro.org/book/os/")
                    ),
                    complemento(
                            "Machine learning aprende padroes a partir de exemplos. Separar treino e teste, escolher uma metrica coerente e comparar com um baseline evita achar que um modelo e bom apenas porque produziu um numero alto.",
                            "Treine um modelo de classificacao e outro de regressao. Para cada um, defina baseline, metrica e dois tipos de erro que teriam impacto diferente no mundo real.",
                            recurso("Curso", "Machine Learning Crash Course", "Curso oficial do Google com conceitos e exercicios.", "https://developers.google.com/machine-learning/crash-course"),
                            recurso("Documentacao", "scikit-learn: getting started", "Fluxo oficial para treino, avaliacao e pipelines.", "https://scikit-learn.org/stable/getting_started.html")
                    ),
                    complemento(
                            "Projeto aplicado precisa comecar por uma pergunta e uma base adequada, nao pelo algoritmo mais complexo. Limpeza, avaliacao e interpretacao costumam consumir mais trabalho do que chamar o metodo de treino.",
                            "Escolha uma base publica, escreva o objetivo antes do codigo e publique um notebook com limpeza, baseline, modelo, metrica, erros e limitacoes.",
                            recurso("Plataforma", "Kaggle Learn", "Mini-cursos e notebooks para pratica orientada.", "https://www.kaggle.com/learn"),
                            recurso("Exemplos", "scikit-learn examples", "Galeria oficial de problemas e modelos.", "https://scikit-learn.org/stable/auto_examples/index.html")
                    ),
                    complemento(
                            "Colocar IA em um produto inclui interface, tempo de resposta, custo, privacidade e uma forma de lidar com resultados incorretos. Modelos prontos aceleram prototipos, mas precisam de limites claros e avaliacao no contexto real.",
                            "Crie uma interface pequena para um modelo ou API, inclua exemplos de uso, aviso de limitacoes e uma forma de o usuario corrigir ou rejeitar o resultado.",
                            recurso("Curso", "Hugging Face Course", "Uso pratico de modelos, datasets e bibliotecas abertas.", "https://huggingface.co/learn/nlp-course/chapter1/1"),
                            recurso("Curso", "Hugging Face Audio Course", "Exemplo oficial de uma especializacao aplicada em audio.", "https://huggingface.co/learn/audio-course/chapter0/introduction")
                    ),
                    complemento(
                            "O portfolio deve provar que voce sabe explicar um experimento, nao apenas executar uma biblioteca. Dados, baseline, metrica, erros, vieses e decisao final precisam aparecer para que outra pessoa consiga avaliar o trabalho.",
                            "Selecione dois projetos com problemas diferentes e prepare uma apresentacao sem jargao. Compare vagas de dados, automacao e ML para escolher a proxima especializacao.",
                            recurso("Guia", "Google: Responsible AI", "Praticas para avaliar impactos e limites de sistemas de IA.", "https://ai.google/responsibility/responsible-ai-practices/"),
                            recurso("Documentacao", "Model Cards", "Formato para registrar uso, avaliacao e limitacoes de modelos.", "https://huggingface.co/docs/hub/model-cards")
                    )
            )),
            Map.entry("gestao-ti", List.of(
                    complemento(
                            "Projeto transforma uma necessidade em entrega com escopo, prazo, responsaveis e riscos. O primeiro trabalho da gestao e criar entendimento comum: o que sera entregue, para quem, por que e como saberemos que terminou.",
                            "Escolha um projeto pequeno e escreva objetivo, publico, entregaveis, fora de escopo, riscos e criterio de sucesso em uma pagina.",
                            recurso("Guia", "Atlassian: project management", "Visao pratica de planejamento, execucao e acompanhamento.", "https://www.atlassian.com/work-management/project-management"),
                            recurso("Template", "Project Canvas", "Modelo visual para alinhar os elementos de um projeto.", "https://www.projectcanvas.dk/")
                    ),
                    complemento(
                            "Scrum organiza aprendizado em ciclos e Kanban torna o fluxo visivel. Nenhum metodo resolve falta de prioridade ou comunicacao sozinho; o valor vem de inspecionar o trabalho e adaptar combinados.",
                            "Organize um quadro real, limite tarefas em andamento e realize uma revisao semanal. Registre o que bloqueou o fluxo e qual mudanca sera testada na semana seguinte.",
                            recurso("Guia oficial", "Scrum Guide", "Definicao curta e oficial do framework Scrum.", "https://scrumguides.org/"),
                            recurso("Guia", "Atlassian: Kanban", "Explicacao pratica de quadro, fluxo e limites.", "https://www.atlassian.com/agile/kanban")
                    ),
                    complemento(
                            "Requisito bom descreve uma necessidade e um resultado observavel, sem decidir detalhes demais antes de ouvir o time. Criterios de aceite reduzem interpretacoes diferentes e tornam a validacao objetiva.",
                            "Entreviste uma pessoa sobre um problema, escreva cinco historias com criterios de aceite e valide se ela reconhece a necessidade no texto.",
                            recurso("Guia", "Atlassian: user stories", "Como escrever historias orientadas ao valor do usuario.", "https://www.atlassian.com/agile/project-management/user-stories"),
                            recurso("Guia", "Atlassian: acceptance criteria", "Criterios claros para alinhar entrega e validacao.", "https://www.atlassian.com/work-management/project-management/acceptance-criteria")
                    ),
                    complemento(
                            "Produto conecta entregas a mudanca de comportamento ou resultado. Quantidade de tarefas concluidas nao prova valor; e preciso definir uma metrica ligada ao problema, observar o resultado e ajustar prioridade.",
                            "Crie um mapa simples de objetivo, publico, problema, hipotese, entrega e metrica. Compare tres ideias usando impacto, esforco e risco.",
                            recurso("Guia", "Atlassian: product management", "Fundamentos de estrategia, discovery e priorizacao.", "https://www.atlassian.com/agile/product-management"),
                            recurso("Guia", "Google HEART framework", "Modelo para medir experiencia de usuarios em produtos.", "https://research.google/pubs/how-to-choose-the-right-ux-metrics-for-your-product/")
                    ),
                    complemento(
                            "Lideranca em tecnologia cria contexto, remove bloqueios e facilita decisoes. Reunioes precisam de objetivo e registro; conflitos precisam ser tratados com fatos, escuta e acordos claros.",
                            "Facilite uma reuniao curta com pauta, tempo e decisao esperada. Envie uma ata com responsavel e prazo e conduza uma retrospectiva depois da entrega.",
                            recurso("Guia", "Atlassian: retrospectives", "Formatos e principios para aprender depois de uma iteracao.", "https://www.atlassian.com/team-playbook/plays/retrospective"),
                            recurso("Guia", "Atlassian Team Playbook", "Praticas abertas para alinhamento e colaboracao.", "https://www.atlassian.com/team-playbook")
                    ),
                    complemento(
                            "Portfolio de gestao mostra como voce organizou informacao e ajudou uma entrega a acontecer. Quadros, atas, riscos, requisitos, indicadores e aprendizados formam evidencias mesmo quando o projeto e escolar ou voluntario.",
                            "Monte um case com contexto, seu papel, plano, mudancas, resultado e aprendizado. Remova dados pessoais antes de publicar e treine explicar as decisoes em cinco minutos.",
                            recurso("Documentacao", "GitHub Projects", "Ferramenta para demonstrar planejamento e acompanhamento de trabalho.", "https://docs.github.com/pt/issues/planning-and-tracking-with-projects/learning-about-projects/about-projects"),
                            recurso("Pratica", "GitHub Skills", "Exercicios de issues, pull requests e colaboracao.", "https://skills.github.com/")
                    )
            ))
    );

    public RoadmapConteudoService(DetalheAreaService detalheAreaService) {
        this.detalheAreaService = detalheAreaService;
    }

    public RoadmapCompletoView buscarPorArea(AreaTi area) {
        List<RoadmapEtapaView> etapasBase = detalheAreaService.buscarPorArea(area).getRoadmapCompleto();
        List<ComplementoEtapa> detalhes = complementos.getOrDefault(area.getSlug(), List.of());

        if (etapasBase.size() != detalhes.size()) {
            throw new IllegalStateException("Roadmap incompleto para a area: " + area.getSlug());
        }

        List<RoadmapEtapaInterativaView> etapas = new ArrayList<>();
        for (int indice = 0; indice < etapasBase.size(); indice++) {
            RoadmapEtapaView etapa = etapasBase.get(indice);
            ComplementoEtapa complemento = detalhes.get(indice);
            etapas.add(new RoadmapEtapaInterativaView(
                    "etapa-" + (indice + 1),
                    etapa.getNivel(),
                    etapa.getTitulo(),
                    etapa.getTempo(),
                    etapa.getObjetivo(),
                    complemento.explicacao(),
                    complemento.atividade(),
                    etapa.getPassos(),
                    complemento.recursos()
            ));
        }

        return new RoadmapCompletoView(etapas);
    }

    private static ComplementoEtapa complemento(
            String explicacao,
            String atividade,
            RoadmapRecursoView... recursos
    ) {
        return new ComplementoEtapa(explicacao, atividade, List.of(recursos));
    }

    private static RoadmapRecursoView recurso(String tipo, String titulo, String descricao, String url) {
        return new RoadmapRecursoView(tipo, titulo, descricao, url);
    }

    private record ComplementoEtapa(
            String explicacao,
            String atividade,
            List<RoadmapRecursoView> recursos
    ) {
    }
}
