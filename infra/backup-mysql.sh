#!/usr/bin/env bash
set -euo pipefail

APP_DIR="${APP_DIR:-/home/rotati/apps/rota-ti}"
BACKUP_DIR="${BACKUP_DIR:-/mnt/c/RotaTI/backups}"
RETENTION_DAYS="${RETENTION_DAYS:-14}"

cd "$APP_DIR"
mkdir -p "$BACKUP_DIR"

timestamp="$(date +'%Y-%m-%d_%H-%M-%S')"
destination="$BACKUP_DIR/rotati_${timestamp}.sql.gz"
temporary="${destination}.tmp"

trap 'rm -f "$temporary"' EXIT

docker compose exec -T database sh -c \
  'MYSQL_PWD="$MYSQL_ROOT_PASSWORD" exec mysqldump -uroot --single-transaction --quick --routines --events --triggers rotati' \
  | gzip -9 > "$temporary"

test -s "$temporary"
mv "$temporary" "$destination"
find "$BACKUP_DIR" -type f -name 'rotati_*.sql.gz' -mtime "+$RETENTION_DAYS" -delete

echo "Backup criado em $destination"
