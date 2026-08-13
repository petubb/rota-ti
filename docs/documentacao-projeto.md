# Documentacao formal do projeto - Rota TI

## 1. Identificacao

**Nome do projeto:** Rota TI

**Tipo:** Aplicacao web MVC de orientacao profissional em tecnologia.

**Tecnologias principais:** Java 21, Spring Boot, Maven, Thymeleaf, Spring Data JPA, Spring Security, MySQL, H2, HTML, CSS e JavaScript.

## 2. Problema que o projeto resolve

Muitos estudantes se interessam por tecnologia, mas nao conhecem bem as diferentes areas de atuacao dentro de TI. Em geral, existe a ideia de que tecnologia se resume a programacao, o que pode afastar alunos que teriam afinidade com outras areas, como dados, seguranca, infraestrutura, design, jogos, inteligencia artificial ou gestao.

O Rota TI resolve esse problema oferecendo um quiz simples e acessivel, que transforma interesses pessoais em uma rota inicial de carreira. O objetivo nao e decidir o futuro do estudante, mas oferecer um ponto de partida para pesquisa, conversa e experimentacao.

## 3. Publico-alvo

O publico principal sao estudantes do ensino fundamental final, ensino medio, cursos tecnicos e pessoas em fase inicial de escolha profissional.

Tambem pode ser usado por professores, coordenadores e equipes pedagogicas como apoio em atividades de projeto de vida, feiras de profissao, semanas tecnologicas e apresentacoes em escolas.

## 4. Objetivos

### Objetivo geral

Criar uma aplicacao web que ajude estudantes a identificar areas de tecnologia alinhadas ao seu perfil de interesses.

### Objetivos especificos

- Mostrar que tecnologia possui varias rotas profissionais.
- Aplicar um quiz objetivo com perguntas ponderadas.
- Gerar um resultado personalizado com area principal e ranking de compatibilidade.
- Apresentar informacoes de carreira, videos, referencias, estimativas salariais e formacoes.
- Oferecer roadmaps interativos com recursos e projetos para cada area.
- Permitir cadastro opcional para comparar tentativas e acompanhar a evolucao dos interesses.
- Disponibilizar um painel administrativo com metricas gerais de uso.
- Manter a aplicacao online para testes em escolas e apresentacao final.

## 5. Funcionalidades

- Pagina inicial com apresentacao da proposta.
- Pagina "Sobre" explicando o objetivo do projeto.
- Explorador pesquisavel de areas de TI, com busca e filtros.
- Pagina detalhada para cada area, com rotina, habilidades, cargos e mercado.
- Quiz publico com dados iniciais do estudante.
- Selecao de escolas estaduais de Pimenta Bueno com opcao para outra escola.
- Quiz com 16 perguntas objetivas, cinco opcoes de resposta e desempate dinamico.
- Navegacao por clique ou tecla `Enter`.
- Resultado personalizado com compatibilidade, top 3 e proximos passos.
- Estimativas salariais com nivel, escopo, metodologia e fontes.
- Formacoes regionais e recursos online gratuitos por area.
- Roadmap completo por area, com explicacoes, links, projetos e checklist local.
- Compartilhamento simples do resultado.
- Cadastro e login opcionais.
- Area "Minha evolucao" com resumo, comparacao e historico para usuarios autenticados.
- Recuperacao de senha por e-mail com token temporario.
- Dashboard administrativo com estatisticas.
- Deploy online com Render e banco MySQL no Aiven.

## 6. Arquitetura MVC

O projeto segue o padrao MVC:

- **Model:** representa as entidades do dominio e tabelas do banco, como `Conta`, `Usuario`, `Resultado`, `Pergunta`, `Resposta` e `PerguntaPeso`.
- **View:** paginas Thymeleaf localizadas em `src/main/resources/templates`.
- **Controller:** recebe requisicoes web, valida entradas e direciona respostas, como `QuizController`, `AuthController`, `AreaController` e `DashboardController`.
- **Service:** concentra regras de negocio, como calculo do quiz, seguranca de conta, metricas, exploracao de areas, roadmaps, conteudos de carreira e recuperacao de senha.
- **Repository:** acessa o banco de dados com Spring Data JPA.

Essa separacao facilita manutencao, testes e explicacao tecnica do sistema.

## 7. Tecnologias utilizadas

| Tecnologia | Uso no projeto |
| --- | --- |
| Java 21 | Linguagem principal |
| Spring Boot | Base da aplicacao web |
| Maven | Gerenciamento de dependencias e build |
| Thymeleaf | Renderizacao das paginas HTML |
| Spring Data JPA | Persistencia com banco de dados |
| Spring Security | Login, autorizacao, CSRF e protecoes HTTP |
| MySQL | Banco de dados principal |
| H2 | Banco em memoria para desenvolvimento e testes rapidos |
| HTML, CSS modular e JavaScript | Interface responsiva, estilos por pagina e interacoes |
| Render | Hospedagem da aplicacao |
| Aiven MySQL | Banco MySQL online |
| DBeaver | Cliente para administrar e consultar o banco |

## 8. Banco de dados

O banco foi modelado para separar o uso publico do quiz da autenticacao por conta.

| Tabela | Finalidade |
| --- | --- |
| `usuarios` | Guarda o perfil informado no quiz: nome, idade e escola |
| `perguntas` | Guarda as perguntas do questionario |
| `pergunta_pesos` | Define os pesos de cada pergunta para cada area |
| `respostas` | Registra as respostas dadas no quiz |
| `resultados` | Guarda a area recomendada, score, satisfacao e vinculo opcional com conta |
| `contas` | Guarda cadastro, e-mail, papel, status e hash da senha |
| `tokens_recuperacao_senha` | Guarda tokens seguros para recuperacao de senha |

Relacao principal:

```text
usuarios 1---N resultados N---0..1 contas
perguntas 1---N pergunta_pesos
usuarios 1---N respostas
perguntas 1---N respostas
```

O campo `conta_id` em `resultados` e opcional. Isso permite que o visitante faca o quiz sem login. Quando a pessoa se cadastra ou entra no sistema, o resultado pode ser vinculado a sua conta.

As respostas usam valores entre `-2` e `2`, representando discordancia total, discordancia parcial, neutralidade, concordancia parcial e concordancia total. Em bancos existentes, essa restricao e atualizada pelo script `08-respostas-parciais.sql`.

## 9. Fluxo do quiz

1. O estudante acessa a pagina do quiz.
2. Informa nome, idade e escola.
3. Responde 16 perguntas principais, duas para cada area, usando uma escala de cinco opcoes.
4. Pode avancar por clique ou pela tecla `Enter`.
5. Cada resposta soma ou reduz pontos em uma ou mais areas de TI, conforme os pesos cadastrados.
6. O sistema normaliza as pontuacoes e calcula o ranking de areas.
7. Se algumas areas ficarem muito proximas, o sistema aplica de duas a tres perguntas extras de desempate.
8. A pagina de resultado mostra a rota mais compativel, porcentagem, top 3, resumo do roadmap e proximos passos.
9. O visitante pode explorar formacoes, salario, projetos e o roadmap completo sem criar conta.
10. O visitante pode salvar o resultado criando conta ou fazendo login.
11. Com duas ou mais tentativas salvas, a area "Minha evolucao" compara os resultados recentes.

O resultado e orientativo. Ele nao substitui acompanhamento pedagogico ou decisao pessoal de carreira.

## 10. Seguranca e LGPD

O projeto adota cuidados de seguranca e privacidade:

- O quiz e publico para reduzir barreira de entrada.
- O cadastro e opcional.
- Senhas nao sao salvas em texto puro.
- O sistema usa BCrypt com custo 12.
- Formularios POST usam protecao CSRF.
- A area administrativa exige papel `ADMIN`.
- Usuarios comuns nao acessam dados administrativos.
- Recuperacao de senha usa token temporario, de uso unico e com expiracao.
- O banco salva apenas o hash do token de recuperacao.
- Credenciais de banco e SMTP ficam em variaveis de ambiente.
- O sistema coleta apenas dados necessarios para o funcionamento do quiz e historico.
- O progresso do roadmap fica somente no navegador da pessoa, em `localStorage`, e nao e enviado ao banco.

Pela LGPD, o projeto deve informar ao usuario quais dados sao coletados e para qual finalidade. Os dados principais sao nome, idade, escola, e-mail e respostas do quiz, usados para gerar resultados, salvar historico e produzir estatisticas gerais.

## 11. Como rodar localmente

Requisitos:

- Java 21.
- Maven ou Maven Wrapper do projeto.

Com H2 em memoria:

```powershell
.\mvnw.cmd spring-boot:run
```

Acessar:

```text
http://127.0.0.1:8080
```

Com MySQL:

```powershell
$env:SPRING_PROFILES_ACTIVE="mysql"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"
.\mvnw.cmd spring-boot:run
```

Antes de rodar com MySQL, execute os scripts da pasta `database/mysql`.

## 12. Deploy

O deploy foi preparado com:

- **Render:** publica a aplicacao Spring Boot por Docker.
- **Aiven MySQL:** hospeda o banco MySQL online.
- **Variaveis de ambiente:** configuram banco, SMTP, URL publica e protecoes de producao.

Arquivos importantes:

- `Dockerfile`
- `render.yaml`
- `docs/deploy-online.md`
- `database/mysql/01-schema.sql`
- `database/mysql/02-seed-perguntas.sql`
- `database/mysql/08-respostas-parciais.sql`
- `database/mysql/09-balanceamento-quiz.sql`

Variaveis principais:

```text
SPRING_PROFILES_ACTIVE=mysql
DB_HOST=host-do-aiven
DB_PORT=porta-do-aiven
DB_NAME=rotati
DB_USERNAME=usuario-do-aiven
DB_PASSWORD=senha-do-aiven
DB_SSL_MODE=REQUIRED
APP_URL_BASE=https://url-publica-do-render
SESSION_COOKIE_SECURE=true
```

## 13. Prints das telas

O PDF gerado a partir desta documentacao inclui prints das principais telas:

- Home.
- Sobre.
- Areas.
- Detalhe de uma area.
- Inicio do quiz.
- Resultado gerado.
- Roadmap interativo.
- Explorador de areas.
- Minha evolucao.

## 14. Testes e validacao

O projeto possui testes automatizados para validar:

- carregamento da aplicacao;
- calculo do quiz;
- montagem da visualizacao de resultado;
- conteudos das areas;
- busca de escolas estaduais;
- autenticacao e seguranca;
- balanceamento e desempate do quiz;
- exploracao das areas;
- estrutura dos roadmaps;
- comparacao de resultados da conta.

Comando:

```powershell
.\mvnw.cmd test
```

## 15. Consideracoes finais

O Rota TI entrega uma solucao funcional para orientacao inicial em carreiras de tecnologia. O projeto combina um quiz simples com conteudo pratico de carreira: exploracao de areas, formacoes regionais, estimativas salariais, projetos e roadmaps interativos. A estrutura tecnica permanece organizada em MVC, com persistencia em banco, seguranca, dashboard administrativo, deploy online e documentacao de apoio.

Como evolucoes futuras, o sistema pode receber confirmacao de e-mail, CRUD administrativo de perguntas, exportacao de relatorios anonimizados, revisao periodica automatizada dos links e novos conteudos baseados em entrevistas com estudantes.
