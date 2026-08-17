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

Durante a preparacao, acesse `http://192.168.1.36:8080`. Antes da publicacao, `APP_URL_BASE` deve receber a URL HTTPS definitiva e `SESSION_COOKIE_SECURE` deve mudar para `true`.

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

## Proximas protecoes

- iniciar o WSL e os containers automaticamente com o Windows;
- gerar backups diarios do MySQL fora do volume Docker;
- publicar por um tunel HTTPS, sem expor MySQL ou SSH na internet;
- reservar o IP `192.168.1.36` no roteador ou configurar um endereco fixo;
- trocar a senha do usuario Windows usada durante a configuracao inicial.
