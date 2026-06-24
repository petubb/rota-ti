# Banco MySQL no DBeaver

O DBeaver e o cliente usado para acessar o banco. O servidor MySQL tambem precisa estar instalado e em execucao.

## 1. Criar a conexao

No DBeaver, crie uma conexao MySQL com os dados locais:

- Host: `localhost`
- Porta: `3306`
- Usuario: `root`
- Senha: a senha definida na instalacao do MySQL

Use **Test Connection** antes de continuar. Na primeira conexao, o DBeaver pode solicitar o download do driver.

## 2. Criar um banco novo

Abra um editor SQL na conexao MySQL e execute o arquivo inteiro:

```text
database/mysql/01-schema.sql
```

Depois execute:

```text
database/mysql/02-seed-perguntas.sql
```

O segundo script deve retornar:

- `18` perguntas do tipo `BASE`;
- `6` perguntas do tipo `DESEMPATE`;
- a quantidade total de pesos cadastrados.

## Atualizar um banco que ja existia

Se o banco foi criado antes do quiz ponderado, execute nesta ordem:

```text
database/mysql/03-upgrade-quiz-ponderado.sql
database/mysql/02-seed-perguntas.sql
```

O script `03` remove usuarios, respostas e resultados de teste antigos. Isso e necessario porque eles foram calculados por uma regra que nao e comparavel com a pontuacao ponderada atual.

Para adicionar contas e autenticacao ao banco atual, execute uma unica vez:

```text
database/mysql/04-contas-autenticacao.sql
```

O script `04` e aditivo: ele cria a tabela `contas` e adiciona a coluna opcional `conta_id` em `resultados`. Ele nao apaga perguntas, usuarios, respostas ou resultados.

## 3. Conferir no DBeaver

Atualize a arvore da conexao. O banco `rotati` deve conter:

- `usuarios`
- `perguntas`
- `pergunta_pesos`
- `respostas`
- `resultados`
- `contas`

Consultas de verificacao:

```sql
USE rotati;

SHOW TABLES;

SELECT id, codigo, texto, categoria, area_slug, tipo
FROM perguntas
ORDER BY id;

SELECT p.codigo, pp.area_slug, pp.peso
FROM pergunta_pesos pp
JOIN perguntas p ON p.id = pp.pergunta_id
ORDER BY p.id, pp.id;
```

## 4. Executar o Spring com MySQL

No PowerShell, dentro da pasta do projeto:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"
$env:SPRING_PROFILES_ACTIVE="mysql"
.\mvnw.cmd spring-boot:run
```

Substitua `SUA_SENHA_DO_MYSQL` pela mesma senha usada na conexao do DBeaver. Nao execute o texto de exemplo literalmente.

Se o usuario nao tiver senha, use:

```powershell
$env:DB_PASSWORD=""
```

Para voltar ao H2 na proxima execucao:

```powershell
Remove-Item Env:SPRING_PROFILES_ACTIVE -ErrorAction SilentlyContinue
```

## Variaveis opcionais

Os valores padrao atendem uma instalacao local. Altere apenas quando necessario:

```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="3306"
$env:DB_NAME="rotati"
```

Com o perfil `mysql`, o Hibernate usa `validate`: ele confirma se as tabelas correspondem as entidades, mas nao altera a estrutura automaticamente.

## Conceder acesso administrativo

Novas contas recebem o papel `USER`. Para preparar uma conta administrativa, primeiro crie a conta pela tela normal e depois conceda o papel diretamente no banco:

```sql
USE rotati;

UPDATE contas
SET papel = 'ADMIN'
WHERE email = 'email-do-administrador@exemplo.com';
```

Encerre e abra a sessao novamente para o novo papel entrar no contexto de seguranca. O projeto nao cria administrador com senha padrao, evitando uma credencial conhecida dentro do codigo ou do Git.
