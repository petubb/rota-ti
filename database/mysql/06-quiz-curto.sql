-- Rota TI - ajuste do quiz curto
-- Execute uma unica vez em bancos que ja existem e depois rode 02-seed-perguntas.sql.
-- Esta migracao nao apaga usuarios, contas, respostas ou resultados.

USE rotati;

ALTER TABLE perguntas
    ADD COLUMN ativa BOOLEAN NOT NULL DEFAULT TRUE AFTER tipo;

ALTER TABLE perguntas
    ADD INDEX idx_perguntas_tipo_ativa (tipo, ativa);

ALTER TABLE pergunta_pesos
    DROP CHECK chk_pergunta_pesos_valor;

ALTER TABLE pergunta_pesos
    ADD CONSTRAINT chk_pergunta_pesos_valor CHECK (peso BETWEEN -3 AND 3 AND peso <> 0);
