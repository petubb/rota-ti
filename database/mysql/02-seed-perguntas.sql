-- Rota TI - catalogo ponderado do questionario
-- 16 perguntas principais ativas, 2 arquivadas e 6 candidatas a desempate.

USE rotati;

START TRANSACTION;

INSERT INTO perguntas (codigo, texto, categoria, area_slug, tipo, ativa) VALUES
('BASE_DEV_CRIAR', 'Gosto de criar sites, aplicativos ou sistemas.', 'criatividade', 'desenvolvimento-software', 'BASE', TRUE),
('BASE_DEV_LOGICA', 'Gosto de resolver problemas em etapas.', 'logica', 'desenvolvimento-software', 'BASE', TRUE),
('BASE_DADOS_ORGANIZAR', 'Quando recebo muitas informacoes, gosto de organiza-las em tabelas ou relatorios.', 'analise', 'dados-bi', 'BASE', TRUE),
('BASE_DADOS_PADROES', 'Percebo padroes em numeros, tabelas ou graficos.', 'analise', 'dados-bi', 'BASE', TRUE),
('BASE_SEG_INVESTIGAR', 'Tenho interesse em investigar falhas ou golpes digitais.', 'investigacao', 'seguranca-cibernetica', 'BASE', TRUE),
('BASE_SEG_DETALHES', 'Costumo perceber quando algo parece fora do normal em um sistema ou conta digital.', 'detalhe', 'seguranca-cibernetica', 'BASE', TRUE),
('BASE_INFRA_CONFIGURAR', 'Gosto de configurar computadores, redes ou servicos.', 'operacao', 'infraestrutura-redes', 'BASE', TRUE),
('BASE_INFRA_ESTABILIDADE', 'Tenho satisfacao em manter sistemas estaveis, organizados e funcionando.', 'organizacao', 'infraestrutura-redes', 'BASE', TRUE),
('BASE_UX_INTERFACES', 'Gosto de pensar em telas simples e faceis de usar.', 'design', 'ux-ui-design', 'BASE', TRUE),
('BASE_UX_USUARIOS', 'Gosto de entender o que as pessoas precisam.', 'empatia', 'ux-ui-design', 'BASE', TRUE),
('BASE_GAME_MECANICAS', 'Gosto de imaginar regras, fases ou desafios de jogos.', 'narrativa', 'game-design', 'BASE', TRUE),
('BASE_GAME_BALANCEAMENTO', 'Quando jogo, gosto de imaginar ajustes na dificuldade, nas regras ou nas recompensas.', 'experimentacao', 'game-design', 'BASE', TRUE),
('BASE_IA_CURIOSIDADE', 'Tenho curiosidade sobre inteligencia artificial e automacao.', 'curiosidade', 'inteligencia-artificial', 'BASE', TRUE),
('BASE_IA_EXPERIMENTAR', 'Gosto de testar uma ideia, comparar os resultados e ajustar o que nao funcionou.', 'experimentacao', 'inteligencia-artificial', 'BASE', TRUE),
('BASE_GESTAO_LIDERAR', 'Gosto de organizar tarefas e ajudar um grupo a avancar.', 'lideranca', 'gestao-ti', 'BASE', TRUE),
('BASE_GESTAO_COMUNICAR', 'Gosto de conectar pessoas, prazos e objetivos para que um projeto avance.', 'comunicacao', 'gestao-ti', 'BASE', TRUE),
('BASE_PERSISTENCIA', 'Continuo tentando quando uma solucao nao funciona de primeira.', 'persistencia', 'desenvolvimento-software', 'BASE', FALSE),
('BASE_EXPLICAR_IDEIAS', 'Gosto de explicar ideias de um jeito simples.', 'comunicacao', 'gestao-ti', 'BASE', FALSE),
('DESEMPATE_CRIAR_MANTER', 'Entre criar algo novo e manter tudo estavel, eu prefiro criar.', 'criatividade', 'desenvolvimento-software', 'DESEMPATE', TRUE),
('DESEMPATE_PREVENIR_EXPLORAR', 'Entre investigar riscos e explorar novidades, eu prefiro investigar riscos.', 'investigacao', 'seguranca-cibernetica', 'DESEMPATE', TRUE),
('DESEMPATE_PESSOAS_LOGICA', 'Prefiro entender pessoas e comunicacao a lidar com logica e numeros.', 'empatia', 'ux-ui-design', 'DESEMPATE', TRUE),
('DESEMPATE_DADOS_EXPERIENCIA', 'Prefiro padroes e evidencias a partes visuais ou narrativas.', 'analise', 'dados-bi', 'DESEMPATE', TRUE),
('DESEMPATE_COORDENAR_CONSTRUIR', 'Prefiro coordenar prioridades a construir a solucao diretamente.', 'lideranca', 'gestao-ti', 'DESEMPATE', TRUE),
('DESEMPATE_INTERATIVO_REDES', 'Prefiro criar experiencias interativas a administrar redes e servicos.', 'narrativa', 'game-design', 'DESEMPATE', TRUE)
ON DUPLICATE KEY UPDATE
    texto = VALUES(texto),
    categoria = VALUES(categoria),
    area_slug = VALUES(area_slug),
    tipo = VALUES(tipo),
    ativa = VALUES(ativa);

DELETE pp
FROM pergunta_pesos pp
JOIN perguntas p ON p.id = pp.pergunta_id
WHERE (p.codigo = 'BASE_SEG_DETALHES' AND pp.area_slug = 'dados-bi')
   OR (p.codigo = 'BASE_UX_USUARIOS' AND pp.area_slug = 'dados-bi')
   OR (p.codigo = 'BASE_IA_CURIOSIDADE' AND pp.area_slug = 'dados-bi');

INSERT INTO pergunta_pesos (pergunta_id, area_slug, peso)
SELECT p.id, dados.area_slug, dados.peso
FROM perguntas p
JOIN (
    SELECT 'BASE_DEV_CRIAR' codigo, 'desenvolvimento-software' area_slug, 3 peso
    UNION ALL SELECT 'BASE_DEV_CRIAR', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_DEV_LOGICA', 'desenvolvimento-software', 3
    UNION ALL SELECT 'BASE_DEV_LOGICA', 'dados-bi', 1
    UNION ALL SELECT 'BASE_DEV_LOGICA', 'inteligencia-artificial', 1
    UNION ALL SELECT 'BASE_DEV_LOGICA', 'game-design', 1
    UNION ALL SELECT 'BASE_DADOS_ORGANIZAR', 'dados-bi', 3
    UNION ALL SELECT 'BASE_DADOS_ORGANIZAR', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_DADOS_ORGANIZAR', 'inteligencia-artificial', 1
    UNION ALL SELECT 'BASE_DADOS_PADROES', 'dados-bi', 3
    UNION ALL SELECT 'BASE_DADOS_PADROES', 'inteligencia-artificial', 1
    UNION ALL SELECT 'BASE_DADOS_PADROES', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_SEG_INVESTIGAR', 'seguranca-cibernetica', 3
    UNION ALL SELECT 'BASE_SEG_INVESTIGAR', 'infraestrutura-redes', 1
    UNION ALL SELECT 'BASE_SEG_DETALHES', 'seguranca-cibernetica', 3
    UNION ALL SELECT 'BASE_SEG_DETALHES', 'infraestrutura-redes', 1
    UNION ALL SELECT 'BASE_INFRA_CONFIGURAR', 'infraestrutura-redes', 3
    UNION ALL SELECT 'BASE_INFRA_CONFIGURAR', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_INFRA_ESTABILIDADE', 'infraestrutura-redes', 3
    UNION ALL SELECT 'BASE_INFRA_ESTABILIDADE', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_INFRA_ESTABILIDADE', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_UX_INTERFACES', 'ux-ui-design', 3
    UNION ALL SELECT 'BASE_UX_INTERFACES', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_UX_USUARIOS', 'ux-ui-design', 3
    UNION ALL SELECT 'BASE_UX_USUARIOS', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_UX_USUARIOS', 'game-design', 1
    UNION ALL SELECT 'BASE_GAME_MECANICAS', 'game-design', 3
    UNION ALL SELECT 'BASE_GAME_MECANICAS', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_GAME_MECANICAS', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_GAME_BALANCEAMENTO', 'game-design', 3
    UNION ALL SELECT 'BASE_GAME_BALANCEAMENTO', 'dados-bi', 1
    UNION ALL SELECT 'BASE_GAME_BALANCEAMENTO', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_IA_CURIOSIDADE', 'inteligencia-artificial', 3
    UNION ALL SELECT 'BASE_IA_CURIOSIDADE', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_IA_EXPERIMENTAR', 'inteligencia-artificial', 3
    UNION ALL SELECT 'BASE_IA_EXPERIMENTAR', 'dados-bi', 1
    UNION ALL SELECT 'BASE_IA_EXPERIMENTAR', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_IA_EXPERIMENTAR', 'game-design', 1
    UNION ALL SELECT 'BASE_GESTAO_LIDERAR', 'gestao-ti', 3
    UNION ALL SELECT 'BASE_GESTAO_LIDERAR', 'infraestrutura-redes', 1
    UNION ALL SELECT 'BASE_GESTAO_COMUNICAR', 'gestao-ti', 3
    UNION ALL SELECT 'BASE_GESTAO_COMUNICAR', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'inteligencia-artificial', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'game-design', 1
    UNION ALL SELECT 'BASE_EXPLICAR_IDEIAS', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_EXPLICAR_IDEIAS', 'dados-bi', 1
    UNION ALL SELECT 'BASE_EXPLICAR_IDEIAS', 'ux-ui-design', 1
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'desenvolvimento-software', 3
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'game-design', 1
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'inteligencia-artificial', 1
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'infraestrutura-redes', -3
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'seguranca-cibernetica', -1
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'seguranca-cibernetica', 3
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'infraestrutura-redes', 1
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'inteligencia-artificial', -3
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'game-design', -1
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'ux-ui-design', 3
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'gestao-ti', 2
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'dados-bi', -3
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'desenvolvimento-software', -1
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'inteligencia-artificial', -1
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'dados-bi', 3
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'inteligencia-artificial', 1
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'ux-ui-design', -3
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'game-design', -2
    UNION ALL SELECT 'DESEMPATE_COORDENAR_CONSTRUIR', 'gestao-ti', 3
    UNION ALL SELECT 'DESEMPATE_COORDENAR_CONSTRUIR', 'desenvolvimento-software', -3
    UNION ALL SELECT 'DESEMPATE_COORDENAR_CONSTRUIR', 'infraestrutura-redes', -1
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'game-design', 3
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'ux-ui-design', 1
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'infraestrutura-redes', -3
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'seguranca-cibernetica', -1
) dados ON dados.codigo = p.codigo
ON DUPLICATE KEY UPDATE peso = VALUES(peso);

COMMIT;

SELECT tipo, ativa, COUNT(*) AS total
FROM perguntas
GROUP BY tipo, ativa
ORDER BY tipo, ativa DESC;

SELECT COUNT(*) AS total_pesos FROM pergunta_pesos;
