-- Rota TI - perfil do estudante no quiz
-- Execute uma unica vez em bancos criados antes desta funcionalidade.
-- Esta migracao adiciona o nome informado no inicio do questionario.

USE rotati;

ALTER TABLE usuarios
    ADD COLUMN nome VARCHAR(80) NULL AFTER id;

UPDATE usuarios
SET nome = 'Estudante'
WHERE nome IS NULL OR TRIM(nome) = '';

ALTER TABLE usuarios
    MODIFY nome VARCHAR(80) NOT NULL;

SELECT COUNT(*) AS usuarios_com_nome
FROM usuarios
WHERE nome IS NOT NULL AND TRIM(nome) <> '';
