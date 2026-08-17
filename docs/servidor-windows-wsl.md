# Servidor proprio no Windows com WSL 2

Esta configuracao executa o Rota TI e o MySQL em containers dentro do Ubuntu 24.04 no WSL 2. O banco nao publica a porta 3306 na rede; apenas a aplicacao publica a porta configurada em `APP_PORT`.

## Preparar o projeto

No Ubuntu do servidor:

```bash
git clone https://github.com/petubb/rota-ti.git
cd rota-ti
git switch release/consolidacao-aprovada-rota-ti
cp .env.example .env
```

Edite `.env` e defina senhas diferentes e fortes para `DB_PASSWORD` e `MYSQL_ROOT_PASSWORD`. O arquivo `.env` e ignorado pelo Git.

## Subir os servicos

```bash
docker compose up -d --build
docker compose ps
docker compose logs -f app
```

Na primeira inicializacao, o MySQL executa somente `01-schema.sql` e `02-seed-perguntas.sql`. Os scripts seguintes sao migracoes para bancos antigos.

## Comandos cotidianos

```bash
docker compose restart app
docker compose logs --tail=200 app
docker compose pull
docker compose up -d --build
```

Para encerrar sem apagar os dados:

```bash
docker compose down
```

Nao use `docker compose down -v` no servidor: a opcao `-v` remove o volume persistente do MySQL.

## Teste local

Durante a preparacao, acesse `http://192.168.1.36:8080` em dispositivos conectados a mesma rede. O endereco local e util para diagnostico, mas a autenticacao em producao deve ser testada pelo HTTPS publico, pois `SESSION_COOKIE_SECURE=true` impede o envio do cookie por HTTP.

## Publicacao HTTPS com Tailscale Funnel

O servidor usa o Tailscale Funnel para publicar somente a aplicacao, sem abrir portas no roteador e sem expor o MySQL ou o SSH. O endereco atual e:

```text
https://rota-ti.tailcb4d3c.ts.net
```

O Tailscale foi instalado dentro do Ubuntu e seu servico inicia automaticamente pelo systemd. Para consultar a conexao e o tunel:

```bash
tailscale status
sudo tailscale funnel status
```

Para recriar a publicacao, caso seja necessario:

```bash
sudo tailscale funnel --bg 8080
```

No `.env` do servidor, mantenha:

```dotenv
APP_URL_BASE=https://rota-ti.tailcb4d3c.ts.net
SESSION_COOKIE_SECURE=true
```

Depois de alterar essas variaveis, recrie somente a aplicacao:

```bash
docker compose up -d --no-deps --force-recreate app
```

O dominio `*.ts.net` e o certificado HTTPS sao administrados pelo Tailscale. O Funnel precisa permanecer habilitado na conta que autorizou a maquina `rota-ti`.

## Backup diario

Instale o servico e o temporizador no Ubuntu:

```bash
chmod +x infra/backup-mysql.sh
sudo cp infra/systemd/rotati-backup.service /etc/systemd/system/
sudo cp infra/systemd/rotati-backup.timer /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable --now rotati-backup.timer
```

Para testar imediatamente:

```bash
sudo systemctl start rotati-backup.service
sudo systemctl status rotati-backup.service
ls -lh /mnt/c/RotaTI/backups
```

Os arquivos compactados ficam em `C:\RotaTI\backups`, fora do volume Docker. O temporizador roda diariamente por volta das 03:00 e remove backups com mais de 14 dias.

## Inicializacao automatica

O Windows possui a tarefa agendada `RotaTI-WSL-KeepAlive`, executada na inicializacao para manter a distribuicao Ubuntu ativa. Dentro do WSL, Docker, Tailscale, containers e o timer de backup iniciam automaticamente.

Para verificar os componentes:

```powershell
Get-ScheduledTask -TaskName "RotaTI-WSL-KeepAlive"
```

```bash
systemctl is-active docker tailscaled rotati-backup.timer
docker compose ps
```

## Cuidados restantes

- reservar o IP `192.168.1.36` no roteador ou configurar um endereco fixo;
- testar a inicializacao completa depois de uma reinicializacao controlada do Windows;
- manter Windows, Ubuntu, Docker e Tailscale atualizados;
- testar periodicamente a restauracao de um backup em ambiente separado;
- trocar a senha do usuario Windows usada durante a configuracao inicial.
