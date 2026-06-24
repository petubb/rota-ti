-- Rota TI - autenticacao opcional e historico por conta
-- Execute uma unica vez em bancos criados antes desta funcionalidade.
-- Este script e aditivo: nao remove nem altera os dados existentes.

USE rotati;

CREATE TABLE IF NOT EXISTS contas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    email VARCHAR(150) NOT NULL,
    senha_hash VARCHAR(100) NOT NULL,
    papel VARCHAR(20) NOT NULL DEFAULT 'USER',
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    tentativas_falhas INT NOT NULL DEFAULT 0,
    bloqueado_ate DATETIME(6) NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_contas_email UNIQUE (email),
    CONSTRAINT chk_contas_papel CHECK (papel IN ('USER', 'ADMIN')),
    CONSTRAINT chk_contas_tentativas CHECK (tentativas_falhas >= 0),
    INDEX idx_contas_created_at (created_at)
) ENGINE = InnoDB;

ALTER TABLE resultados
    ADD COLUMN conta_id BIGINT NULL AFTER usuario_id,
    ADD CONSTRAINT fk_resultados_conta
        FOREIGN KEY (conta_id) REFERENCES contas (id)
        ON UPDATE RESTRICT ON DELETE SET NULL,
    ADD INDEX idx_resultados_conta_id (conta_id);

SELECT COUNT(*) AS total_contas FROM contas;
SELECT COUNT(*) AS resultados_vinculados FROM resultados WHERE conta_id IS NOT NULL;
