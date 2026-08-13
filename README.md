# Rota TI

Projeto MVC em Java 21 com Spring Boot, Thymeleaf, Spring Data JPA, Spring Security, H2 e MySQL Driver.

O questionario usa 16 perguntas principais, duas por area, uma escala de cinco respostas e pesos compartilhados entre caracteristicas relacionadas. Quando algumas rotas ficam muito proximas, o sistema seleciona de duas a tres perguntas extras de desempate.

Depois do resultado, a pessoa pode explorar informacoes de carreira, consultar formacoes regionais e online, seguir um roadmap interativo e, opcionalmente, criar uma conta para acompanhar como seus interesses mudam entre novas tentativas.

## Como rodar no VS Code

1. Abra esta pasta `rota-ti` no VS Code.
2. Confira se o Java 21 e o Maven estao instalados.
3. Rode:

```bash
.\mvnw.cmd spring-boot:run
```

4. Acesse:

```text
http://localhost:8080
```

## Banco de desenvolvimento

Por padrao o projeto usa H2 em memoria para facilitar os testes.

- Console H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:rotati`
- Usuario: `sa`
- Senha: vazia

Para abrir o console H2 localmente, habilite antes de rodar:

```powershell
$env:H2_CONSOLE_ENABLED="true"
.\mvnw.cmd spring-boot:run
```

## Rodar com MySQL

Os scripts de criacao, carga inicial e o passo a passo para DBeaver estao em:

```text
database/mysql/README.md
```

Depois de executar os scripts no MySQL, ative o perfil:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"
$env:SPRING_PROFILES_ACTIVE="mysql"
.\mvnw.cmd spring-boot:run
```

## Estrutura MVC

- `controller`: recebe as rotas web e API.
- `model`: entidades JPA e enum das areas de TI.
- `repository`: acesso ao banco com Spring Data JPA.
- `service`: regras de negocio, scoring do quiz, conteudos de carreira, roadmaps e metricas.
- `security`: identidade autenticada e tratadores de login.
- `config`: regras de acesso, BCrypt, CSRF e cabecalhos HTTP.
- `dto`: objetos de entrada/saida entre view e service.
- `templates`: paginas Thymeleaf.
- `static`: CSS modular por pagina e JavaScript das interacoes.

## Rotas principais

- `/`: pagina inicial.
- `/sobre`: explicacao do projeto.
- `/privacidade`: politica de privacidade e LGPD.
- `/quiz`: formulario do questionario.
- `/resultado/{id}`: resultado de um questionario respondido.
- `/areas`: explorador pesquisavel das areas de TI.
- `/area/{slug}`: detalhes, mercado, salario, formacoes e primeiros passos de uma area.
- `/area/{slug}/roadmap`: trilha interativa da area, com etapas, checklist, recursos e projetos.
- `/dashboard`: metricas internas.
- `/entrar`: login opcional.
- `/cadastro`: criacao de conta.
- `/esqueci-senha`: solicitacao de recuperacao por e-mail.
- `/recuperar-senha`: redefinicao por token temporario.
- `/minha-conta/resultados`: evolucao, comparacao e historico da pessoa autenticada.
- `/api/areas`: lista de areas em JSON.
- `/api/perguntas`: lista de perguntas em JSON, restrita a administradores.

O quiz e publico. O dashboard exige papel `ADMIN`, e uma conta comum acessa somente os resultados vinculados a ela. Os detalhes da implementacao estao em:

```text
docs/autenticacao-seguranca.md
```

Configuracao de envio de e-mail para recuperacao de senha:

```text
docs/configuracao-smtp.md
```

Deploy online para testar com outras pessoas:

```text
docs/deploy-online.md
```

## Atualizar um banco MySQL existente

Para liberar a escala de cinco respostas e aplicar o balanceamento atual sem apagar contas, usuarios ou resultados, execute no DBeaver:

```text
database/mysql/08-respostas-parciais.sql
database/mysql/09-balanceamento-quiz.sql
database/mysql/02-seed-perguntas.sql
```

O passo a passo completo e as consultas de conferencia estao em `database/mysql/README.md`.

## Atualizacao geral

O pacote consolidado da experiencia atual esta descrito em:

```text
docs/atualizacao-geral-rota-ti.md
```

## Proximas etapas sugeridas

1. Avaliar o questionario com estudantes e ajustar pesos com base no feedback real.
2. Revisar periodicamente links, editais, cursos e fontes salariais.
3. Validar MySQL, SMTP e deploy no ambiente de apresentacao.
4. Preparar a apresentacao PDF e ensaiar o roteiro da demonstracao.
