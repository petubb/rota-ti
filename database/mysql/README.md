# Banco MySQL no DBeaver

O DBeaver e o cliente usado para acessar o banco. O servidor MySQL tambem precisa estar instalado e em execucao.

## 1. Criar a conexao

No DBeaver, crie uma conexao MySQL com os dados locais:

- Host: `localhost`
- Porta: `3306`
- Usuario: `root`
- Senha: a senha definida na instalacao do MySQL

Use **Test Connection** antes de continuar. Na primeira conexao, o DBeaver pode solicitar o download do driver.

## 2. Criar o banco e as tabelas

Abra um editor SQL na conexao MySQL e execute o arquivo inteiro:

```text
database/mysql/01-schema.sql
```

Depois execute:

```text
database/mysql/02-seed-perguntas.sql
```

O segundo script deve retornar `13` em `total_perguntas`.

## 3. Conferir no DBeaver

Atualize a arvore da conexao. O banco `rotati` deve conter:

- `usuarios`
- `perguntas`
- `respostas`
- `resultados`

Consultas de verificacao:

```sql
USE rotati;

SHOW TABLES;

SELECT id, texto, categoria, area_slug
FROM perguntas
ORDER BY id;
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
