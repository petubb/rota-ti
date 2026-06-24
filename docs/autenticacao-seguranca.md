# Autenticacao e seguranca do Rota TI

## Decisao de produto

O foco continua sendo o questionario. Por isso, o login e opcional:

1. Qualquer pessoa pode abrir e concluir o quiz sem cadastro.
2. O resultado completo aparece imediatamente.
3. A pessoa pode escolher `Salvar meu resultado`.
4. Depois do cadastro ou login, o resultado pendente e vinculado a conta.
5. Quando a pessoa ja esta autenticada, novos resultados sao salvos automaticamente.

Essa escolha reduz a barreira de entrada sem abrir mao do historico pessoal.

## Fluxo tecnico

Um resultado anonimo tem seu ID guardado na sessao HTTP que o criou. O servidor exige esse ID na sessao antes de exibir ou alterar o resultado. Trocar manualmente `/resultado/15` por `/resultado/16` nao libera o registro de outra sessao.

Ao salvar, o ID vira um resultado pendente. Depois que a autenticacao termina, o servidor associa `resultados.conta_id` a conta autenticada. A partir desse momento, somente essa conta consegue abrir e avaliar o resultado. A tentativa indevida responde como resultado inexistente, sem confirmar se aquele ID existe.

## BCrypt: hash, nao criptografia

A senha nunca e gravada em texto puro e nao existe operacao para descriptografa-la. O `BCryptPasswordEncoder` produz um hash de mao unica com:

- salt aleatorio incluido no proprio hash;
- fator de custo `12`, que torna cada tentativa computacionalmente cara;
- limite validado de 72 bytes, que e o limite relevante do BCrypt;
- comparacao por `passwordEncoder.matches`, sem recuperar a senha original.

O salt faz duas pessoas com a mesma senha receberem hashes diferentes. O custo 12 representa um trabalho exponencial aproximado de `2^12`, dificultando ataques de forca bruta contra um banco vazado.

Exemplo conceitual:

```text
senha + salt aleatorio + custo 12 -> hash BCrypt salvo em senha_hash
```

## Autenticacao e autorizacao

Autenticacao responde **quem e a pessoa**. Autorizacao responde **o que ela pode acessar**.

- `USER`: historico da propria conta e resultados que pertencem a ela.
- `ADMIN`: tambem pode acessar `/dashboard` e `/api/perguntas`.
- Visitante: paginas publicas, quiz e resultados criados na propria sessao.

Esconder o link do dashboard e apenas uma melhoria de interface. A protecao real fica no Spring Security, no servidor, com a exigencia de `ROLE_ADMIN`.

## Defesas implementadas

| Risco | Defesa |
| --- | --- |
| Senha vazada pelo banco | Hash BCrypt com salt e custo 12 |
| Forca bruta no login | 5 falhas bloqueiam a conta por 15 minutos |
| Requisicoes simultaneas evitando o limite | Bloqueio pessimista da linha da conta durante a contagem |
| Descoberta de contas pelo login | Mesma mensagem para senha errada, conta inexistente ou bloqueada |
| CSRF em cadastro, feedback, salvar e logout | Token CSRF obrigatorio em todo POST normal |
| Fixacao de sessao | ID da sessao trocado depois da autenticacao |
| Roubo do cookie por JavaScript | Cookie de sessao com `HttpOnly` |
| Envio cruzado indevido do cookie | `SameSite=Lax` |
| Acesso por troca do ID na URL | Validacao de propriedade no servidor |
| Acesso comum ao painel | Controle por papel `ADMIN` |
| Clickjacking | `X-Frame-Options: DENY` e CSP `frame-ancestors 'none'` |
| Carregamento de conteudo inesperado | Content Security Policy restritiva |
| Uso indevido de sensores | Permissions Policy bloqueia camera, microfone e localizacao |
| Redirecionamento malicioso | Destinos de login definidos pelo servidor, sem URL enviada pelo usuario |
| Segredo dentro do Git | Credenciais MySQL recebidas por variaveis de ambiente |

O console H2 usa uma cadeia de seguranca separada porque precisa de frame. Essa excecao so existe quando `spring.h2.console.enabled=true`; no perfil MySQL ela fica desabilitada.

Por padrao, o servidor de desenvolvimento escuta somente em `127.0.0.1`, evitando expor o H2 para outros dispositivos da rede local. Um ambiente de implantacao pode alterar isso com `SERVER_ADDRESS`, junto de HTTPS e das demais protecoes de producao.

## Banco de dados

A tabela `contas` armazena:

- nome e e-mail normalizado;
- `senha_hash`, nunca a senha original;
- papel `USER` ou `ADMIN`;
- estado ativo;
- quantidade de falhas e horario de desbloqueio;
- data de criacao.

`resultados.conta_id` e opcional. Isso permite manter o quiz anonimo. A migracao `04-contas-autenticacao.sql` e aditiva e nao apaga dados existentes.

Nao existe administrador com senha padrao. Uma conta e criada como `USER` e o papel `ADMIN` e concedido diretamente no banco por quem administra o ambiente. Depois disso, a pessoa precisa entrar novamente para atualizar as permissoes da sessao.

## Testes de seguranca

Os testes automatizados verificam:

- quiz publico com cabecalhos de seguranca;
- historico exigindo autenticacao;
- POST sem CSRF recebendo `403`;
- hash BCrypt com custo 12 e diferente da senha original;
- rejeicao de senha fraca;
- usuario comum recebendo `403` no dashboard;
- bloqueio depois de cinco falhas;
- resultado anonimo restrito a sessao e resultado salvo restrito ao dono.

## Limites e proximos reforcos

Para uma implantacao publica, ainda seriam recomendados:

- HTTPS e `SESSION_COOKIE_SECURE=true`;
- verificacao de e-mail e recuperacao segura de senha;
- MFA para administradores;
- limite adicional por IP no proxy ou gateway;
- monitoramento e auditoria de eventos de login;
- politica de privacidade e prazo de retencao dos dados.

Alteracoes de papel ou desativacao de conta entram completamente em vigor depois que a sessao atual termina. O tempo de sessao foi limitado a 30 minutos; sistemas de maior risco podem revalidar a conta em toda requisicao.

O bloqueio por conta reduz forca bruta, mas pode ser explorado para bloquear temporariamente um e-mail conhecido. Em producao, ele deve ser combinado com limite por IP, alertas e desafios progressivos.

## Roteiro curto para apresentar

1. Fazer o quiz sem login e mostrar que o resultado aparece normalmente.
2. Tentar abrir o mesmo ID em outra sessao e mostrar que o acesso e recusado.
3. Clicar em salvar, criar a conta e mostrar o resultado no historico.
4. Abrir a tabela `contas` no DBeaver e mostrar que existe apenas `senha_hash`.
5. Explicar BCrypt, CSRF, troca do ID de sessao e separacao entre `USER` e `ADMIN`.
6. Executar os testes automatizados e mostrar as tentativas de acesso cobertas.
