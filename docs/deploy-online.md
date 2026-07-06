# Deploy online com Render + Aiven MySQL

Este guia deixa o Rota TI online para testar no celular, nas escolas e na apresentacao.

Escolha recomendada:

- Render: hospeda a aplicacao Spring Boot.
- Aiven MySQL: hospeda o banco online.

Nao coloque senhas no codigo, no README, no `render.yaml` ou em commits. As senhas entram apenas nas variaveis de ambiente do Render e na tela de conexao do Aiven/DBeaver.

## 1. Criar o banco no Aiven

1. Acesse `https://console.aiven.io`.
2. Crie um servico novo.
3. Escolha **Aiven for MySQL**.
4. Escolha o plano **Free**.
5. Aguarde o status ficar **Running**.
6. No servico MySQL, confira a area **Connection information**.

Voce vai precisar destes dados:

```text
Host
Port
User
Password
Database
```

Para o projeto, vamos usar o banco chamado:

```text
rotati
```

Se o Aiven criar apenas `defaultdb`, tudo bem: rode o script `01-schema.sql`, porque ele cria `rotati` se ainda nao existir.

## 2. Conectar o Aiven no DBeaver

O caminho mais simples no DBeaver e usar a URL JDBC do proprio Aiven:

1. No Aiven, abra o servico MySQL.
2. Clique em **Quick connect**.
3. Em **Connect with**, escolha **Java**.
4. Copie a **JDBC URI**.
5. No DBeaver, crie uma conexao MySQL.
6. Na aba principal, escolha conexao por URL e cole a JDBC URI.
7. Teste a conexao.

O Aiven usa conexao segura. Para a aplicacao no Render, vamos configurar:

```text
DB_SSL_MODE=REQUIRED
```

## 3. Criar as tabelas no banco online

Em um banco novo do Aiven, execute somente:

```text
database/mysql/01-schema.sql
database/mysql/02-seed-perguntas.sql
```

Nao execute os scripts `04`, `05`, `06` e `07` em banco novo, porque o `01-schema.sql` ja esta atualizado com contas, recuperacao de senha, quiz curto e nome do estudante.

Depois confira:

```sql
USE rotati;

SHOW TABLES;

SELECT COUNT(*) AS perguntas_ativas
FROM perguntas
WHERE ativa = TRUE;
```

O esperado e ter as tabelas principais e perguntas cadastradas.

## 4. Subir a aplicacao no Render

O projeto tem um `Dockerfile` e um `render.yaml`, entao o Render consegue publicar usando Docker.

Fluxo recomendado:

1. Suba a branch desejada no GitHub.
2. Acesse `https://dashboard.render.com`.
3. Clique em **New**.
4. Escolha **Blueprint** se quiser usar o `render.yaml`.
5. Conecte o repositorio `rota-ti`.
6. Escolha a branch que sera publicada.
7. Preencha as variaveis marcadas como secret/sync.

Se preferir criar manualmente:

1. Clique em **New Web Service**.
2. Conecte o repositorio `rota-ti`.
3. Escolha **Docker**.
4. Use o plano **Free**.
5. Configure as variaveis de ambiente da proxima secao.

## 5. Variaveis de ambiente no Render

Obrigatorias para MySQL/Aiven:

```text
SPRING_PROFILES_ACTIVE=mysql
DB_HOST=host-do-aiven
DB_PORT=porta-do-aiven
DB_NAME=rotati
DB_USERNAME=usuario-do-aiven
DB_PASSWORD=senha-do-aiven
DB_SSL_MODE=REQUIRED
```

Obrigatorias para ambiente online:

```text
APP_URL_BASE=https://sua-url-do-render.onrender.com
SESSION_COOKIE_SECURE=true
H2_CONSOLE_ENABLED=false
THYMELEAF_CACHE=true
```

Recuperacao de senha por Gmail:

```text
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu-email@gmail.com
MAIL_PASSWORD=sua-senha-de-app-sem-espacos
APP_EMAIL_REMETENTE=seu-email@gmail.com
MAIL_SMTP_AUTH=true
MAIL_SMTP_STARTTLS_ENABLE=true
```

Observacao importante sobre `APP_URL_BASE`:

- No primeiro deploy, talvez voce ainda nao saiba a URL final.
- Coloque uma URL temporaria se o Render pedir.
- Depois que o Render gerar a URL publica, volte em **Environment**, atualize `APP_URL_BASE` e redeploy.

## 6. Teste rapido depois do deploy

Abra a URL publica no computador e no celular.

Checklist:

1. Home carrega.
2. `/sobre` carrega.
3. `/areas` carrega.
4. Quiz completo gera resultado.
5. Resultado fica salvo em `resultados`.
6. Cadastro cria registro em `contas`.
7. Login funciona.
8. Meus resultados mostra historico.
9. Recuperacao de senha chega no e-mail ou spam.
10. Dashboard abre apenas para conta com papel `ADMIN`.

Consultas uteis no DBeaver:

```sql
USE rotati;

SELECT id, nome, email, papel, ativo, created_at
FROM contas
ORDER BY id DESC;

SELECT r.id, u.nome, u.idade, u.escola, r.area, r.score, r.created_at
FROM resultados r
JOIN usuarios u ON u.id = r.usuario_id
ORDER BY r.created_at DESC;
```

## 7. No dia da apresentacao

No dia anterior:

1. Abra o site publicado.
2. Faca um quiz teste.
3. Crie uma conta teste.
4. Teste recuperacao de senha.
5. Confira se o banco Aiven esta **Running**.

No dia da apresentacao:

1. Abra o site uns 10 minutos antes.
2. Faca login na conta admin.
3. Deixe uma aba com o dashboard aberta.
4. Tenha o projeto local pronto como plano B.

Plano B local:

```powershell
$env:SPRING_PROFILES_ACTIVE="mysql"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="SUA_SENHA_LOCAL"
.\mvnw.cmd spring-boot:run
```

Se a internet falhar, tambem da para remover o perfil MySQL e rodar com H2 em memoria:

```powershell
Remove-Item Env:SPRING_PROFILES_ACTIVE -ErrorAction SilentlyContinue
.\mvnw.cmd spring-boot:run
```
