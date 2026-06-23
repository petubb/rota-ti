-- Rota TI - atualizacao de um banco criado antes do quiz ponderado
-- Execute uma unica vez e depois rode 02-seed-perguntas.sql.
-- Os resultados antigos sao removidos porque usavam uma regra de pontuacao incompativel.

USE rotati;

ALTER TABLE perguntas
    ADD COLUMN codigo VARCHAR(50) NULL AFTER id,
    ADD COLUMN tipo VARCHAR(20) NOT NULL DEFAULT 'BASE' AFTER area_slug;

CREATE TABLE pergunta_pesos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pergunta_id BIGINT NOT NULL,
    area_slug VARCHAR(80) NOT NULL,
    peso INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_pergunta_pesos_pergunta_area UNIQUE (pergunta_id, area_slug),
    CONSTRAINT chk_pergunta_pesos_valor CHECK (peso BETWEEN -2 AND 2 AND peso <> 0),
    CONSTRAINT fk_pergunta_pesos_pergunta
        FOREIGN KEY (pergunta_id) REFERENCES perguntas (id)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    INDEX idx_pergunta_pesos_area_slug (area_slug)
) ENGINE = InnoDB;

DELETE FROM respostas;
DELETE FROM resultados;
DELETE FROM usuarios;
DELETE FROM pergunta_pesos;
DELETE FROM perguntas;

ALTER TABLE perguntas
    MODIFY COLUMN codigo VARCHAR(50) NOT NULL,
    ALTER COLUMN tipo DROP DEFAULT,
    ADD CONSTRAINT uk_perguntas_codigo UNIQUE (codigo),
    ADD CONSTRAINT chk_perguntas_tipo CHECK (tipo IN ('BASE', 'DESEMPATE')),
    ADD INDEX idx_perguntas_tipo (tipo);

ALTER TABLE usuarios AUTO_INCREMENT = 1;
ALTER TABLE perguntas AUTO_INCREMENT = 1;
ALTER TABLE pergunta_pesos AUTO_INCREMENT = 1;
ALTER TABLE respostas AUTO_INCREMENT = 1;
ALTER TABLE resultados AUTO_INCREMENT = 1;
