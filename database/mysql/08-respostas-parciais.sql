-- Rota TI - escala de respostas com opcoes parciais
-- Execute uma unica vez em bancos existentes antes de publicar o quiz com 5 opcoes.
-- Esta migracao nao apaga usuarios, contas, respostas ou resultados.

USE rotati;

ALTER TABLE respostas
    DROP CHECK chk_respostas_valor;

ALTER TABLE respostas
    ADD CONSTRAINT chk_respostas_valor CHECK (valor BETWEEN -2 AND 2);
