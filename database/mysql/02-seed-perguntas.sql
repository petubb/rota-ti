-- Rota TI - perguntas iniciais do questionario
-- Pode ser executado novamente: cada INSERT verifica se a pergunta ja existe.

USE rotati;

START TRANSACTION;

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce gosta de criar sites, aplicativos ou sistemas?', 'criatividade', 'desenvolvimento-software'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce gosta de criar sites, aplicativos ou sistemas?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce se sente bem resolvendo problemas logicos passo a passo?', 'logica', 'desenvolvimento-software'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce se sente bem resolvendo problemas logicos passo a passo?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce gosta de organizar dados e transformar informacoes em relatorios?', 'analise', 'dados-bi'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce gosta de organizar dados e transformar informacoes em relatorios?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce percebe padroes em numeros, tabelas ou graficos com facilidade?', 'analise', 'dados-bi'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce percebe padroes em numeros, tabelas ou graficos com facilidade?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce se interessa por investigar falhas, golpes digitais ou vulnerabilidades?', 'investigacao', 'seguranca-cibernetica'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce se interessa por investigar falhas, golpes digitais ou vulnerabilidades?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce tem atencao aos detalhes quando algo parece fora do normal?', 'detalhe', 'seguranca-cibernetica'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce tem atencao aos detalhes quando algo parece fora do normal?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce gosta de configurar computadores, redes ou equipamentos?', 'operacao', 'infraestrutura-redes'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce gosta de configurar computadores, redes ou equipamentos?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce prefere manter sistemas funcionando e organizados?', 'organizacao', 'infraestrutura-redes'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce prefere manter sistemas funcionando e organizados?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce gosta de pensar em telas bonitas, simples e faceis de usar?', 'design', 'ux-ui-design'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce gosta de pensar em telas bonitas, simples e faceis de usar?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce costuma se colocar no lugar das pessoas para melhorar uma experiencia?', 'empatia', 'ux-ui-design'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce costuma se colocar no lugar das pessoas para melhorar uma experiencia?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce gosta de imaginar mundos, personagens, fases ou regras de jogos?', 'narrativa', 'game-design'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce gosta de imaginar mundos, personagens, fases ou regras de jogos?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce tem curiosidade sobre inteligencia artificial, automacao e modelos que aprendem com dados?', 'curiosidade', 'inteligencia-artificial'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce tem curiosidade sobre inteligencia artificial, automacao e modelos que aprendem com dados?'
);

INSERT INTO perguntas (texto, categoria, area_slug)
SELECT 'Voce gosta de liderar, organizar tarefas e ajudar pessoas a trabalharem melhor em equipe?', 'lideranca', 'gestao-ti'
WHERE NOT EXISTS (
    SELECT 1 FROM perguntas WHERE texto = 'Voce gosta de liderar, organizar tarefas e ajudar pessoas a trabalharem melhor em equipe?'
);

COMMIT;

SELECT COUNT(*) AS total_perguntas FROM perguntas;
