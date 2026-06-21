# Rota TI

Projeto MVC em Java 21 com Spring Boot, Thymeleaf, Spring Data JPA, H2 e MySQL Driver.

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
- `service`: regras de negocio, scoring do quiz e metricas.
- `dto`: objetos de entrada/saida entre view e service.
- `templates`: paginas Thymeleaf.
- `static`: CSS e JavaScript.

## Rotas principais

- `/`: pagina inicial.
- `/sobre`: explicacao do projeto.
- `/quiz`: formulario do questionario.
- `/resultado/{id}`: resultado de um questionario respondido.
- `/areas`: catalogo das areas de TI.
- `/area/{slug}`: detalhes de uma area.
- `/dashboard`: metricas internas.
- `/api/areas`: lista de areas em JSON.
- `/api/perguntas`: lista de perguntas em JSON.

## Proximas etapas sugeridas

1. Refinar perguntas e pesos do algoritmo.
2. Completar conteudos reais de cursos, salarios e referencias.
3. Criar tela de login/admin se o dashboard precisar ficar restrito.
4. Validar a configuracao MySQL no ambiente de apresentacao.
5. Preparar a apresentacao PDF e roteiro da demo.
