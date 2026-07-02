# Configuracao SMTP para recuperacao de senha

O Rota TI ja possui o fluxo de recuperacao de senha. Para o envio real funcionar, basta configurar um servidor SMTP por variaveis de ambiente antes de iniciar a aplicacao.

## Minha recomendacao sobre validar e-mail no cadastro

Para este projeto, o cadastro pode continuar sem validacao obrigatoria de e-mail. O foco do site e manter o quiz simples e rapido.

O equilibrio atual fica bom assim:

- cadastro rapido, sem travar o aluno;
- senha protegida com BCrypt;
- recuperacao de senha so funciona se o e-mail existir e receber o link;
- admin sem senha padrao no codigo.

Em um sistema de producao maior, a validacao de e-mail seria recomendada. Para a apresentacao e uso em escolas, ela pode atrapalhar mais do que ajudar.

## Variaveis necessarias

No PowerShell, dentro da pasta do projeto:

```powershell
$env:MAIL_HOST="smtp.gmail.com"
$env:MAIL_PORT="587"
$env:MAIL_USERNAME="seu-email@gmail.com"
$env:MAIL_PASSWORD="SENHA_DE_APP_DO_EMAIL"
$env:APP_EMAIL_REMETENTE="seu-email@gmail.com"
$env:APP_URL_BASE="http://127.0.0.1:8080"
$env:MAIL_SMTP_AUTH="true"
$env:MAIL_SMTP_STARTTLS_ENABLE="true"
```

Depois rode a aplicacao:

```powershell
.\mvnw.cmd spring-boot:run
```

Se estiver usando MySQL junto:

```powershell
$env:SPRING_PROFILES_ACTIVE="mysql"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"

.\mvnw.cmd spring-boot:run
```

## Gmail

No Gmail, normalmente nao se usa a senha normal da conta no SMTP. Use uma senha de app:

1. Ative a verificacao em duas etapas da conta Google.
2. Gere uma senha de app para e-mail.
3. Use essa senha em `MAIL_PASSWORD`.

Nao coloque essa senha em arquivo do projeto e nao suba para o Git.

Se o Google mostrar a senha de app com espacos, use a senha sem os espacos no terminal.

Exemplo:

```powershell
$env:MAIL_PASSWORD="abcdefghijklmnop"
```

## Teste rapido

1. Inicie o site com as variaveis SMTP configuradas.
2. Crie uma conta com um e-mail que voce consiga acessar.
3. Saia da conta.
4. Abra `/esqueci-senha`.
5. Informe o e-mail da conta.
6. Confira a caixa de entrada e spam.
7. Abra o link recebido e defina uma nova senha.

## Teste no celular

Se for testar no celular pela mesma rede, o link do e-mail precisa apontar para o IP do computador, nao para `127.0.0.1`.

Exemplo:

```powershell
$env:APP_URL_BASE="http://192.168.0.10:8080"
$env:SERVER_ADDRESS="0.0.0.0"
```

Troque `192.168.0.10` pelo IP do seu computador na rede.

## Como saber se o SMTP nao esta configurado

Na tela `/esqueci-senha`, o sistema mostra um aviso quando o envio de e-mail ainda nao esta configurado. O pedido de recuperacao continua seguro: ele nao revela se o e-mail existe ou nao.

## Erros comuns

- `535` ou `Authentication failed`: usuario ou senha SMTP incorretos. No Gmail, quase sempre e senha normal em vez de senha de app.
- `Username and Password not accepted`: confirme se `MAIL_USERNAME` e o mesmo e-mail que gerou a senha de app.
- `Connection timed out`: porta, internet, firewall ou host SMTP incorreto.
- `From address rejected`: use `APP_EMAIL_REMETENTE` igual ao e-mail autenticado em `MAIL_USERNAME`.
- As variaveis foram configuradas depois do sistema ja estar rodando: pare o Spring e rode novamente.

Para conferir as variaveis sem mostrar a senha:

```powershell
echo $env:MAIL_HOST
echo $env:MAIL_PORT
echo $env:MAIL_USERNAME
echo $env:APP_EMAIL_REMETENTE
```
