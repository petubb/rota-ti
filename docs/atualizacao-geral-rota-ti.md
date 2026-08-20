# Atualizacao geral do Rota TI

## Titulo sugerido do Pull Request

`release: consolida nova jornada de orientacao do Rota TI`

## Resumo

Esta branch consolida a evolucao mais recente do Rota TI. O quiz ficou mais expressivo e equilibrado, as paginas de carreira passaram a oferecer caminhos praticos de estudo e a conta opcional agora ajuda a acompanhar mudancas entre tentativas.

O pacote foi construido sobre as branches aprovadas, mantendo o quiz publico e preservando a arquitetura MVC existente.

## O que esta incluido

### Quiz e balanceamento

- Escala de cinco respostas: discordo totalmente, discordo parcialmente, neutro, concordo parcialmente e concordo totalmente.
- Perguntas reescritas com frases mais curtas e diretas.
- Dezesseis perguntas principais ativas, duas para cada area.
- Pesos principais e secundarios revisados para reduzir concentracoes artificiais nos resultados.
- Perguntas extras de desempate quando as primeiras colocadas ficam proximas.
- Avanco pelo teclado com `Enter`, mantendo tambem a navegacao por clique.
- Testes automatizados de distribuicao, pontuacao, desempate e persistencia das respostas.

### Banco de dados

- Restricao de respostas ampliada de `-1..1` para `-2..2`.
- Script `08-respostas-parciais.sql` para bancos existentes.
- Script `09-balanceamento-quiz.sql` para atualizar pesos sem apagar participacoes.
- Seed de perguntas sincronizado com os textos e pesos atuais.
- Migracoes preservam contas, usuarios, respostas e resultados existentes.

### Organizacao do frontend

- CSS principal dividido em base compartilhada e arquivos por pagina.
- Estilos separados para home, quiz, resultado, areas, roadmap, autenticacao, dashboard, privacidade e demais telas.
- Menor acoplamento entre paginas e manutencao visual mais simples.
- Layouts revisados para desktop e celular.

### Exploracao das areas

- Pagina de areas transformada em um explorador com busca e filtros.
- Cards com perfil, atividades, primeiro passo, habilidades e indicacao de dificuldade inicial.
- Contagem de resultados e estado vazio para pesquisas sem correspondencia.
- Paginas de area mais informativas, com rotina, mercado, cargos, ferramentas e projetos praticos.

### Formacao regional e online

- Formacoes e oportunidades em Pimenta Bueno, Cacoal, Rolim de Moura e outras localidades de Rondonia.
- Referencias de IFRO, SENAI-RO, SENAC-RO e polos regionais.
- Cursos e recursos online gratuitos de instituicoes reconhecidas.
- Links quebrados da UNESC removidos.
- Blocos regionais e online reunidos visualmente como parte dos proximos passos da carreira.
- Aviso de que turmas, editais e disponibilidade devem ser conferidos nos sites oficiais.

### Roadmap de carreira

- Resumo guiado do roadmap dentro da pagina de resultado.
- Pagina propria de roadmap para cada uma das oito areas.
- Etapas progressivas do nivel inicial ao aprofundamento.
- Passos expansivos com explicacao do que estudar e por que aquilo importa.
- Links para documentacoes, cursos e materiais oficiais ou confiaveis.
- Sugestoes de projetos praticos para transformar estudo em portfolio.
- Checklist interativo por passo.
- Progresso salvo no navegador por area usando `localStorage`.
- Indicadores de passos concluidos, percentual e conclusao de etapa.
- Opcao para limpar e recomecar o progresso local.

### Faixas salariais

- Valores apresentados como estimativas, nao como promessa salarial.
- Separacao por nivel de experiencia quando existe fonte suficiente.
- Cargo de referencia e abrangencia geografica informados.
- Explicacao de que cidade, empresa, experiencia, beneficios e regime de contratacao alteram os valores.
- Metodologia, data de referencia e links das fontes exibidos na pagina.

### Conta e evolucao

- Login e cadastro continuam opcionais e explicam com clareza o beneficio da conta.
- Quiz permanece acessivel sem cadastro.
- Resultado anonimo pode ser vinculado depois da criacao da conta.
- Area `Minha evolucao` substitui o historico simples.
- Resumo de testes salvos, areas exploradas e rota mais recorrente.
- Comparacao entre as duas tentativas mais recentes.
- Leitura contextual quando a rota muda, se mantem ou altera a compatibilidade.
- Estado especifico para conta vazia e para o primeiro resultado.
- Historico completo continua disponivel para reabrir qualquer resultado.
- Os indicadores sao calculados a partir dos resultados existentes, sem nova tabela no banco.

### Testes e verificacoes

- Suite Maven completa executada com Java 21.
- Fluxos de conta vazia, primeiro resultado e multiplos resultados cobertos.
- Tres quizzes reais automatizados durante a validacao da evolucao da conta.
- Telas conferidas em desktop e viewport de celular, sem overflow horizontal.
- Arquivos verificados com `git diff --check`.

## Ordem para atualizar o MySQL existente

Execute uma vez, nesta ordem:

```text
database/mysql/08-respostas-parciais.sql
database/mysql/09-balanceamento-quiz.sql
database/mysql/02-seed-perguntas.sql
```

Os scripts nao removem contas, usuarios, respostas ou resultados. Depois da execucao, reinicie a aplicacao com o perfil `mysql`.

## Decisoes de consolidacao

- A branch `feature/resultado-explica-areas-relacionadas` nao foi incorporada porque sua apresentacao foi rejeitada durante a revisao.
- A branch antiga `feature/trilhas-estudo-por-nivel` nao foi incorporada isoladamente; sua proposta foi superada pelo roadmap guiado e interativo, que e mais completo.
- Os PDFs de folheto e QR Code que estao locais nao fazem parte deste Pull Request.

## Checklist antes do merge

- [x] Recursos aprovados presentes na sequencia da branch.
- [x] Branch rejeitada de areas relacionadas fora da consolidacao.
- [x] Documentacao tecnica atualizada.
- [x] Suite automatizada executada.
- [x] Layout responsivo validado.
- [ ] Pull Request revisado no GitHub.
- [ ] Merge em `main`.
- [ ] Scripts executados no MySQL online.
- [ ] Deploy do Render validado depois do merge.

## Como validar manualmente

1. Abrir `/quiz`, responder usando clique e `Enter` e conferir a escala de cinco opcoes.
2. Abrir o resultado e acessar o roadmap recomendado.
3. Marcar passos do roadmap, recarregar a pagina e confirmar que o progresso permanece.
4. Abrir `/areas`, pesquisar e usar os filtros.
5. Abrir uma area e conferir salario, metodologia, formacoes regionais e recursos online.
6. Criar uma conta, salvar resultados e conferir `/minha-conta/resultados`.
7. Repetir o quiz para validar a comparacao entre tentativas.
8. Conferir o dashboard com uma conta `ADMIN`.
