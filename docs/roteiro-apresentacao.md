# Roteiro de apresentacao - Rota TI

Este roteiro serve como guia de fala para demonstrar o projeto e explicar as decisoes tecnicas sem precisar decorar tudo palavra por palavra.

## 1. Abertura

Fala sugerida:

> O Rota TI e uma aplicacao web de orientacao profissional para estudantes que querem entender quais areas de tecnologia combinam mais com seu perfil. A ideia e mostrar que TI nao e so programacao: tambem existem dados, seguranca, infraestrutura, UX/UI, jogos, inteligencia artificial e gestao.

Pontos principais:

- O problema e a falta de orientacao clara sobre carreiras em tecnologia.
- O publico principal sao estudantes em fase de escolha ou curiosidade profissional.
- O projeto entrega um quiz simples, resultado personalizado, paginas de carreira e uma area administrativa.

## 2. Fluxo da demonstracao

Ordem recomendada para a banca:

1. Abrir a Home.
2. Mostrar que o site apresenta a proposta e chama para o quiz.
3. Abrir a pagina de Areas.
4. Entrar em uma area e mostrar:
   - descricao;
   - rotina;
   - habilidades;
   - faixa salarial com fontes;
   - plano de carreira;
   - videos e referencias.
5. Fazer o quiz como usuario comum ou visitante.
6. Mostrar a pagina de resultado:
   - area principal;
   - compatibilidade;
   - top 3;
   - proximos passos;
   - conteudos relacionados.
7. Fazer login ou cadastro.
8. Mostrar "Meus resultados".
9. Abrir o DBeaver e mostrar as tabelas principais.
10. Entrar com conta admin e abrir `/dashboard`.
11. Mostrar o GitHub com branches e commits.

## 3. Explicacao do MVC

Fala sugerida:

> O projeto segue o padrao MVC. Os controllers recebem as requisicoes, os services concentram a regra de negocio, os repositories acessam o banco com Spring Data JPA, os models representam as entidades e os templates Thymeleaf montam as paginas.

Como explicar as camadas:

- `controller`: recebe rotas como `/quiz`, `/resultado/{id}`, `/areas`, `/dashboard`.
- `service`: calcula resultado do quiz, gera metricas, cria contas, salva resultado e aplica regras de seguranca.
- `repository`: conversa com o banco usando Spring Data JPA.
- `model`: representa tabelas como `Conta`, `Usuario`, `Resultado`, `Pergunta`.
- `templates`: paginas Thymeleaf renderizadas no servidor.
- `static`: CSS, JS e imagens.
- `security`: configuracoes e classes de autenticacao/autorizacao.

Exemplo pratico:

1. O usuario envia o quiz.
2. `QuizController` recebe o formulario.
3. `QuizService` calcula a area mais compativel.
4. `ResultadoRepository` salva no banco.
5. `resultado.html` mostra a resposta ao usuario.

## 4. Banco de dados

Fala sugerida:

> O banco separa participacao no quiz de conta de login. Isso permite que o quiz continue publico, mas que o usuario possa criar conta se quiser salvar o historico.

Tabelas principais:

- `usuarios`: guarda a participacao no quiz, como idade e escola.
- `contas`: guarda login, nome, email, senha criptografada, papel e status.
- `resultados`: guarda o resultado do quiz e faz a ponte com `usuarios` e, opcionalmente, `contas`.
- `respostas`: guarda as respostas dadas em cada pergunta.
- `perguntas`: guarda as perguntas do quiz.
- `pergunta_pesos`: guarda o peso de cada pergunta para cada area.
- `tokens_recuperacao_senha`: guarda tokens de recuperacao de senha em formato seguro.

Relacao importante:

```text
contas        resultados        usuarios
  id   <--     conta_id           id
              usuario_id   -->    id
```

Explicacao:

- `usuario_id` em `resultados` e obrigatorio porque todo resultado vem de uma participacao no quiz.
- `conta_id` em `resultados` e opcional porque o visitante pode fazer o quiz sem login.
- Quando a pessoa cria conta ou esta logada, o resultado passa a ficar vinculado a ela.

Consultas uteis para demonstrar:

```sql
USE rotati;

SELECT id, nome, email, papel, ativo
FROM contas
ORDER BY id DESC;

SELECT id, usuario_id, conta_id, area, score, satisfacao, created_at
FROM resultados
ORDER BY id DESC;

SELECT
    r.id AS resultado_id,
    r.area,
    r.score,
    u.idade,
    u.escola,
    c.nome,
    c.email
FROM resultados r
JOIN usuarios u ON u.id = r.usuario_id
LEFT JOIN contas c ON c.id = r.conta_id
ORDER BY r.id DESC;
```

## 5. Login e seguranca

Fala sugerida:

> O login e opcional para o usuario comum. A pessoa pode fazer o quiz sem conta. A conta entra quando ela quer salvar resultado, consultar historico ou acessar area administrativa, no caso de admin.

Pontos para explicar:

- Senhas nao sao salvas em texto puro.
- O projeto usa BCrypt com custo 12.
- O BCrypt gera hash com salt, entao duas senhas iguais nao geram o mesmo hash.
- O formulario usa CSRF para proteger requisicoes POST.
- A area `/dashboard` exige papel `ADMIN`.
- Usuario comum recebe acesso negado se tentar abrir o dashboard.
- Depois de alterar uma conta para `ADMIN` no banco, e necessario sair e entrar novamente.
- O sistema bloqueia login apos varias tentativas incorretas.
- Recuperacao de senha usa token temporario.
- O banco guarda somente o hash SHA-256 do token de recuperacao, nao o token puro.
- SMTP ficou configuravel por variaveis de ambiente, para nao colocar senha de email no Git.

Resumo para a banca:

> A seguranca foi pensada para proteger senha, sessao, formularios e rotas administrativas, sem obrigar login para usar o quiz.

## 6. Area administrativa

Fala sugerida:

> A area admin existe para acompanhar o uso do sistema e demonstrar que os dados do quiz estao sendo persistidos e analisados.

O dashboard mostra:

- total de participacoes no quiz;
- total de resultados gerados;
- resultados salvos em contas;
- total de contas;
- media de satisfacao;
- quantidade de perguntas no banco;
- distribuicao de resultados por area;
- ultimos resultados;
- contas recentes.

Como acessar:

1. Criar uma conta normal pelo site.
2. Atualizar o papel no banco:

```sql
UPDATE contas
SET papel = 'ADMIN'
WHERE email = 'email-da-conta';
```

3. Sair do sistema.
4. Entrar novamente.
5. Abrir `/dashboard`.

Explicacao importante:

> Nao existe admin com senha padrao no codigo. Isso evita deixar uma credencial conhecida dentro do projeto.

## 7. Versionamento

Fala sugerida:

> O desenvolvimento foi organizado por branches de funcionalidade. Cada etapa importante ficou separada para facilitar revisao e demonstrar historico de evolucao.

Branches importantes:

- `main`: versao principal/estavel.
- `feature/optional-auth`: login opcional, seguranca e recuperacao de senha.
- `feature/area-detalhe-carreira`: paginas de area com plano de carreira, referencias e salario com fonte.
- `feature/admin-dashboard`: dashboard administrativo.

Comandos que mostram o historico:

```powershell
git branch
git log --oneline --decorate --graph --all
git status
```

O que explicar:

- Cada branch representa uma entrega.
- Os commits tem mensagens objetivas.
- Pull Requests podem ser usados para mostrar a revisao antes de entrar na `main`.

## 8. Checklist antes da apresentacao

Rodar testes:

```powershell
.\mvnw.cmd test
```

Conferir Git:

```powershell
git status
```

Rodar com MySQL:

```powershell
$env:SPRING_PROFILES_ACTIVE="mysql"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="SUA_SENHA_DO_MYSQL"
.\mvnw.cmd spring-boot:run
```

Validar no navegador:

- `http://127.0.0.1:8080`
- `http://127.0.0.1:8080/areas`
- `http://127.0.0.1:8080/quiz`
- `http://127.0.0.1:8080/minha-conta/resultados`
- `http://127.0.0.1:8080/dashboard`

Validar no DBeaver:

- tabela `contas`;
- tabela `usuarios`;
- tabela `resultados`;
- tabela `respostas`;
- tabela `perguntas`.

## 9. Perguntas provaveis da banca

### Por que o login e opcional?

Porque o foco do projeto e orientar o estudante rapidamente. Obrigar cadastro antes do quiz poderia atrapalhar a experiencia. O login entra quando a pessoa quer salvar o historico.

### Por que `usuarios` e `contas` sao tabelas separadas?

Porque `usuarios` representa uma participacao no quiz, enquanto `contas` representa autenticacao. Isso permite quiz publico e resultado salvo opcionalmente.

### Como o resultado e calculado?

Cada pergunta tem pesos associados as areas. As respostas somam pontos por area, gerando ranking de compatibilidade. Quando duas areas ficam muito proximas, perguntas de desempate refinam o resultado.

### Como as senhas sao protegidas?

Com BCrypt. O sistema nunca salva senha pura. Ele salva apenas o hash, com salt e custo computacional.

### O que impede usuario comum de acessar o admin?

O Spring Security exige `ROLE_ADMIN` para `/dashboard`. Se a conta for `USER`, o acesso retorna 403.

### O que ainda pode evoluir?

- Configurar SMTP definitivo para recuperacao de senha em producao.
- Criar CRUD administrativo para perguntas.
- Melhorar relatorios do dashboard.
- Exportar dados anonimizados.
- Publicar o sistema em ambiente online.

## 10. Encerramento

Fala sugerida:

> O Rota TI busca tornar a escolha de carreira em tecnologia mais clara e acessivel. O projeto une um fluxo simples para o estudante com uma estrutura tecnica em MVC, persistencia em banco, autenticacao segura, area administrativa e versionamento por branches.
