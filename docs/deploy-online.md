# Deploy online

Este guia deixa o Rota TI pronto para um teste publico rapido.

## Modo rapido para testar com outras pessoas

Use o perfil padrao com H2 em memoria. Ele e simples para teste, mas os dados somem se o servidor reiniciar.

Variaveis recomendadas:

```text
APP_URL_BASE=https://sua-url-publica
SESSION_COOKIE_SECURE=true
H2_CONSOLE_ENABLED=false

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu-email@gmail.com
MAIL_PASSWORD=sua-senha-de-app-sem-espacos
APP_EMAIL_REMETENTE=seu-email@gmail.com
MAIL_SMTP_AUTH=true
MAIL_SMTP_STARTTLS_ENABLE=true
```

Nao coloque `MAIL_PASSWORD` no codigo nem no GitHub.

## Modo com MySQL

Use quando quiser salvar contas e resultados mesmo depois de reiniciar o servidor.

Variaveis adicionais:

```text
SPRING_PROFILES_ACTIVE=mysql
DB_HOST=host-do-mysql
DB_PORT=3306
DB_NAME=rotati
DB_USERNAME=usuario
DB_PASSWORD=senha
```

Antes de iniciar com MySQL, execute os scripts da pasta `database/mysql` no banco.

## Render

1. Crie um Web Service conectado ao repositorio do GitHub.
2. Escolha deploy por Docker.
3. Aponte para a branch que voce quer publicar.
4. Configure as variaveis de ambiente acima.
5. Depois que a URL publica existir, atualize `APP_URL_BASE` com essa URL.

## Railway

1. Crie um projeto a partir do repositorio do GitHub.
2. Publique o servico usando o Dockerfile do projeto.
3. Configure as variaveis de ambiente acima.
4. Se adicionar MySQL no Railway, use os dados do servico MySQL para preencher `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME` e `DB_PASSWORD`.
5. Depois que a URL publica existir, atualize `APP_URL_BASE` com essa URL.

## Checagem depois do deploy

1. Abra a home.
2. Acesse `/areas`.
3. Faca um quiz completo.
4. Crie uma conta teste.
5. Solicite recuperacao de senha.
6. Confira caixa de entrada e spam.
