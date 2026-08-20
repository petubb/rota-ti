-- Rota TI - acompanhamento de inicio e conclusao do quiz
-- Migracao aditiva: nao remove nem altera dados existentes.

USE rotati;

CREATE TABLE IF NOT EXISTS tentativas_quiz (
    id BIGINT NOT NULL AUTO_INCREMENT,
    resultado_id BIGINT NULL,
    iniciada_em DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    concluida_em DATETIME(6) NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_tentativas_quiz_resultado UNIQUE (resultado_id),
    CONSTRAINT fk_tentativas_quiz_resultado
        FOREIGN KEY (resultado_id) REFERENCES resultados (id)
        ON UPDATE RESTRICT ON DELETE SET NULL,
    INDEX idx_tentativas_quiz_iniciada_em (iniciada_em),
    INDEX idx_tentativas_quiz_concluida_em (concluida_em)
) ENGINE = InnoDB;

-- Resultados anteriores a esta migracao representam quizzes concluidos.
-- O LEFT JOIN torna o preenchimento seguro caso o script seja executado novamente.
INSERT INTO tentativas_quiz (resultado_id, iniciada_em, concluida_em)
SELECT r.id, r.created_at, r.created_at
FROM resultados r
LEFT JOIN tentativas_quiz tq ON tq.resultado_id = r.id
WHERE tq.id IS NULL;
