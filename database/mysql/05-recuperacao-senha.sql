-- Rota TI - recuperacao segura de senha
-- Execute uma unica vez depois do script 04.
-- Este script e aditivo e nao apaga dados existentes.

USE rotati;

ALTER TABLE contas
    ADD COLUMN versao_credenciais INT NOT NULL DEFAULT 0 AFTER bloqueado_ate;

CREATE TABLE IF NOT EXISTS tokens_recuperacao_senha (
    id BIGINT NOT NULL AUTO_INCREMENT,
    conta_id BIGINT NOT NULL,
    token_hash VARCHAR(64) NOT NULL,
    expira_em DATETIME(6) NOT NULL,
    usado_em DATETIME(6) NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_tokens_recuperacao_hash UNIQUE (token_hash),
    CONSTRAINT fk_tokens_recuperacao_conta
        FOREIGN KEY (conta_id) REFERENCES contas (id)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    INDEX idx_tokens_recuperacao_conta_created (conta_id, created_at),
    INDEX idx_tokens_recuperacao_expira_em (expira_em)
) ENGINE = InnoDB;

SELECT COUNT(*) AS tokens_cadastrados FROM tokens_recuperacao_senha;
