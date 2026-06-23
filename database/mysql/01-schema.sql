-- Rota TI - estrutura do banco de dados
-- Compatibilidade: MySQL 8.0+

CREATE DATABASE IF NOT EXISTS rotati
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE rotati;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    idade INT NOT NULL,
    escola VARCHAR(100) NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT chk_usuarios_idade CHECK (idade BETWEEN 12 AND 25),
    INDEX idx_usuarios_created_at (created_at)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS perguntas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(50) NOT NULL,
    texto TEXT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    area_slug VARCHAR(80) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_perguntas_codigo UNIQUE (codigo),
    CONSTRAINT chk_perguntas_tipo CHECK (tipo IN ('BASE', 'DESEMPATE')),
    INDEX idx_perguntas_area_slug (area_slug),
    INDEX idx_perguntas_categoria (categoria),
    INDEX idx_perguntas_tipo (tipo)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS pergunta_pesos (
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

CREATE TABLE IF NOT EXISTS respostas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    pergunta_id BIGINT NOT NULL,
    valor INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_respostas_usuario_pergunta UNIQUE (usuario_id, pergunta_id),
    CONSTRAINT chk_respostas_valor CHECK (valor BETWEEN -1 AND 1),
    CONSTRAINT fk_respostas_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_respostas_pergunta
        FOREIGN KEY (pergunta_id) REFERENCES perguntas (id)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    INDEX idx_respostas_usuario_id (usuario_id),
    INDEX idx_respostas_pergunta_id (pergunta_id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS resultados (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    area VARCHAR(100) NOT NULL,
    score DOUBLE NOT NULL,
    satisfacao INT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT chk_resultados_score CHECK (score BETWEEN 0 AND 100),
    CONSTRAINT chk_resultados_satisfacao CHECK (satisfacao IS NULL OR satisfacao BETWEEN 1 AND 5),
    CONSTRAINT fk_resultados_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    INDEX idx_resultados_usuario_id (usuario_id),
    INDEX idx_resultados_area (area),
    INDEX idx_resultados_created_at (created_at)
) ENGINE = InnoDB;
