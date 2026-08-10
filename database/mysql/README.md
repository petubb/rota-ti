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

- `16` perguntas `BASE` ativas;
- `2` perguntas `BASE` arquivadas;
- `6` perguntas `DESEMPATE` ativas;
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

Depois execute a migracao da recuperacao de senha:

```text
database/mysql/05-recuperacao-senha.sql
```

O script `05` adiciona a versao das credenciais e a tabela de tokens temporarios. Ele tambem nao apaga dados.

Para aplicar o quiz curto em um banco ja existente, execute:

```text
database/mysql/06-quiz-curto.sql
database/mysql/02-seed-perguntas.sql
```

O script `06` adiciona a coluna `ativa` em `perguntas` e amplia o limite de pesos para `-3` a `3`. Depois, o script `02` marca 16 perguntas principais como ativas, arquiva as demais e atualiza os pesos. Nenhum usuario, conta, resposta ou resultado e apagado.

Para adicionar o nome do estudante no inicio do quiz, execute:

```text
database/mysql/07-usuario-nome-escola.sql
```

O script `07` adiciona a coluna `nome` em `usuarios` e preserva os registros antigos com o valor inicial `Estudante`.

Para liberar as opcoes parciais do quiz, execute:

```text
database/mysql/08-respostas-parciais.sql
database/mysql/02-seed-perguntas.sql
```

O script `08` amplia o intervalo de respostas para `-2` a `2`. Depois, o script `02` atualiza os textos simplificados das perguntas. Nenhum usuario, conta, resposta ou resultado e apagado.

Para aplicar o ajuste fino de balanceamento das areas do quiz, execute:

```text
database/mysql/09-balanceamento-quiz.sql
database/mysql/02-seed-perguntas.sql
```

O script `09` altera apenas pesos de perguntas. Ele remove pesos secundarios que diluiam a area de Dados / BI e adiciona pesos secundarios em areas relacionadas para deixar a distribuicao mais equilibrada. Nenhum usuario, conta, resposta ou resultado e apagado.

## 3. Conferir no DBeaver

Atualize a arvore da conexao. O banco `rotati` deve conter:

- `usuarios`
- `perguntas`
- `pergunta_pesos`
- `respostas`
- `resultados`
- `contas`
- `tokens_recuperacao_senha`

Consultas de verificacao:

```sql
USE rotati;

SHOW TABLES;

SELECT id, codigo, texto, categoria, area_slug, tipo, ativa
FROM perguntas
ORDER BY id;

SELECT p.codigo, pp.area_slug, pp.peso
FROM pergunta_pesos pp
JOIN perguntas p ON p.id = pp.pergunta_id
ORDER BY p.id, pp.id;

SELECT id, nome, email, papel, ativo, created_at
FROM contas
ORDER BY id DESC;
```

`usuarios` nao e a tabela de login. Ela guarda nome, idade e escola informados no quiz. Cadastros de login aparecem em `contas`. Se uma tabela nova nao aparecer na arvore do DBeaver, use **Refresh** na conexao ou no schema `rotati`.

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
