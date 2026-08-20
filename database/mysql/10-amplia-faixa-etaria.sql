-- Rota TI - amplia a idade maxima aceita pelo quiz
-- Execute uma unica vez em bancos criados antes desta alteracao.
-- Esta migracao nao apaga usuarios, contas, respostas ou resultados.

USE rotati;

SET @idade_constraint_exists = (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME = 'usuarios'
      AND CONSTRAINT_NAME = 'chk_usuarios_idade'
      AND CONSTRAINT_TYPE = 'CHECK'
);

SET @drop_idade_constraint = IF(
    @idade_constraint_exists > 0,
    'ALTER TABLE usuarios DROP CHECK chk_usuarios_idade',
    'SELECT 1'
);

PREPARE drop_idade_constraint_statement FROM @drop_idade_constraint;
EXECUTE drop_idade_constraint_statement;
DEALLOCATE PREPARE drop_idade_constraint_statement;

ALTER TABLE usuarios
    ADD CONSTRAINT chk_usuarios_idade CHECK (idade BETWEEN 12 AND 120);
