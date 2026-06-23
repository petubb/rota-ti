-- Rota TI - catalogo ponderado do questionario
-- 18 perguntas principais e 6 candidatas a desempate.

USE rotati;

START TRANSACTION;

INSERT INTO perguntas (codigo, texto, categoria, area_slug, tipo) VALUES
('BASE_DEV_CRIAR', 'Voce gosta de criar sites, aplicativos ou sistemas para resolver problemas?', 'criatividade', 'desenvolvimento-software', 'BASE'),
('BASE_DEV_LOGICA', 'Voce se sente bem resolvendo problemas logicos passo a passo?', 'logica', 'desenvolvimento-software', 'BASE'),
('BASE_DADOS_ORGANIZAR', 'Voce gosta de organizar informacoes e transforma-las em relatorios claros?', 'analise', 'dados-bi', 'BASE'),
('BASE_DADOS_PADROES', 'Voce percebe padroes em numeros, tabelas ou graficos com facilidade?', 'analise', 'dados-bi', 'BASE'),
('BASE_SEG_INVESTIGAR', 'Voce se interessa por investigar falhas, golpes digitais ou vulnerabilidades?', 'investigacao', 'seguranca-cibernetica', 'BASE'),
('BASE_SEG_DETALHES', 'Voce costuma perceber quando algo parece fora do normal e quer descobrir a causa?', 'detalhe', 'seguranca-cibernetica', 'BASE'),
('BASE_INFRA_CONFIGURAR', 'Voce gosta de configurar computadores, redes ou servicos digitais?', 'operacao', 'infraestrutura-redes', 'BASE'),
('BASE_INFRA_ESTABILIDADE', 'Voce prefere manter sistemas estaveis, organizados e bem documentados?', 'organizacao', 'infraestrutura-redes', 'BASE'),
('BASE_UX_INTERFACES', 'Voce gosta de pensar em telas simples, bonitas e faceis de usar?', 'design', 'ux-ui-design', 'BASE'),
('BASE_UX_USUARIOS', 'Voce procura entender as necessidades das pessoas antes de propor uma solucao?', 'empatia', 'ux-ui-design', 'BASE'),
('BASE_GAME_MECANICAS', 'Voce gosta de imaginar regras, mecanicas, fases ou desafios para jogos?', 'narrativa', 'game-design', 'BASE'),
('BASE_GAME_BALANCEAMENTO', 'Voce teria interesse em testar e ajustar a dificuldade de uma experiencia interativa?', 'experimentacao', 'game-design', 'BASE'),
('BASE_IA_CURIOSIDADE', 'Voce tem curiosidade sobre inteligencia artificial, automacao e modelos que aprendem?', 'curiosidade', 'inteligencia-artificial', 'BASE'),
('BASE_IA_EXPERIMENTAR', 'Voce gosta de testar hipoteses, comparar resultados e aprender com os erros?', 'experimentacao', 'inteligencia-artificial', 'BASE'),
('BASE_GESTAO_LIDERAR', 'Voce gosta de liderar, organizar tarefas e ajudar um grupo a avancar?', 'lideranca', 'gestao-ti', 'BASE'),
('BASE_GESTAO_COMUNICAR', 'Voce se imagina conectando pessoas tecnicas, prazos e necessidades de negocio?', 'comunicacao', 'gestao-ti', 'BASE'),
('BASE_PERSISTENCIA', 'Voce persiste quando uma solucao exige varias tentativas e ajustes?', 'persistencia', 'desenvolvimento-software', 'BASE'),
('BASE_EXPLICAR_IDEIAS', 'Voce gosta de explicar ideias complexas de um jeito que outras pessoas entendam?', 'comunicacao', 'gestao-ti', 'BASE'),
('DESEMPATE_CRIAR_MANTER', 'Entre criar uma solucao nova e manter uma estrutura estavel, voce prefere criar?', 'criatividade', 'desenvolvimento-software', 'DESEMPATE'),
('DESEMPATE_PREVENIR_EXPLORAR', 'Entre investigar riscos e explorar novas possibilidades, voce prefere investigar e prevenir?', 'investigacao', 'seguranca-cibernetica', 'DESEMPATE'),
('DESEMPATE_PESSOAS_LOGICA', 'Voce prefere trabalhar com necessidades de pessoas e comunicacao a lidar com logica e numeros?', 'empatia', 'ux-ui-design', 'DESEMPATE'),
('DESEMPATE_DADOS_EXPERIENCIA', 'Voce se interessa mais por padroes e evidencias do que por aspectos visuais ou narrativos?', 'analise', 'dados-bi', 'DESEMPATE'),
('DESEMPATE_COORDENAR_CONSTRUIR', 'Em um projeto, voce prefere coordenar prioridades e alinhar o time a construir a solucao diretamente?', 'lideranca', 'gestao-ti', 'DESEMPATE'),
('DESEMPATE_INTERATIVO_REDES', 'Voce tem mais interesse em criar experiencias interativas do que administrar servicos e redes?', 'narrativa', 'game-design', 'DESEMPATE')
ON DUPLICATE KEY UPDATE
    texto = VALUES(texto),
    categoria = VALUES(categoria),
    area_slug = VALUES(area_slug),
    tipo = VALUES(tipo);

INSERT INTO pergunta_pesos (pergunta_id, area_slug, peso)
SELECT p.id, dados.area_slug, dados.peso
FROM perguntas p
JOIN (
    SELECT 'BASE_DEV_CRIAR' codigo, 'desenvolvimento-software' area_slug, 2 peso
    UNION ALL SELECT 'BASE_DEV_CRIAR', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_DEV_LOGICA', 'desenvolvimento-software', 2
    UNION ALL SELECT 'BASE_DEV_LOGICA', 'dados-bi', 1
    UNION ALL SELECT 'BASE_DEV_LOGICA', 'inteligencia-artificial', 1
    UNION ALL SELECT 'BASE_DADOS_ORGANIZAR', 'dados-bi', 2
    UNION ALL SELECT 'BASE_DADOS_ORGANIZAR', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_DADOS_PADROES', 'dados-bi', 2
    UNION ALL SELECT 'BASE_DADOS_PADROES', 'inteligencia-artificial', 1
    UNION ALL SELECT 'BASE_DADOS_PADROES', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_SEG_INVESTIGAR', 'seguranca-cibernetica', 2
    UNION ALL SELECT 'BASE_SEG_INVESTIGAR', 'infraestrutura-redes', 1
    UNION ALL SELECT 'BASE_SEG_DETALHES', 'seguranca-cibernetica', 2
    UNION ALL SELECT 'BASE_SEG_DETALHES', 'dados-bi', 1
    UNION ALL SELECT 'BASE_SEG_DETALHES', 'infraestrutura-redes', 1
    UNION ALL SELECT 'BASE_INFRA_CONFIGURAR', 'infraestrutura-redes', 2
    UNION ALL SELECT 'BASE_INFRA_CONFIGURAR', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_INFRA_ESTABILIDADE', 'infraestrutura-redes', 2
    UNION ALL SELECT 'BASE_INFRA_ESTABILIDADE', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_UX_INTERFACES', 'ux-ui-design', 2
    UNION ALL SELECT 'BASE_UX_INTERFACES', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_UX_USUARIOS', 'ux-ui-design', 2
    UNION ALL SELECT 'BASE_UX_USUARIOS', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_UX_USUARIOS', 'dados-bi', 1
    UNION ALL SELECT 'BASE_GAME_MECANICAS', 'game-design', 2
    UNION ALL SELECT 'BASE_GAME_MECANICAS', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_GAME_MECANICAS', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_GAME_BALANCEAMENTO', 'game-design', 2
    UNION ALL SELECT 'BASE_GAME_BALANCEAMENTO', 'dados-bi', 1
    UNION ALL SELECT 'BASE_GAME_BALANCEAMENTO', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_IA_CURIOSIDADE', 'inteligencia-artificial', 2
    UNION ALL SELECT 'BASE_IA_CURIOSIDADE', 'dados-bi', 1
    UNION ALL SELECT 'BASE_IA_CURIOSIDADE', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_IA_EXPERIMENTAR', 'inteligencia-artificial', 2
    UNION ALL SELECT 'BASE_IA_EXPERIMENTAR', 'dados-bi', 1
    UNION ALL SELECT 'BASE_IA_EXPERIMENTAR', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_GESTAO_LIDERAR', 'gestao-ti', 2
    UNION ALL SELECT 'BASE_GESTAO_COMUNICAR', 'gestao-ti', 2
    UNION ALL SELECT 'BASE_GESTAO_COMUNICAR', 'ux-ui-design', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'desenvolvimento-software', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'inteligencia-artificial', 1
    UNION ALL SELECT 'BASE_PERSISTENCIA', 'game-design', 1
    UNION ALL SELECT 'BASE_EXPLICAR_IDEIAS', 'gestao-ti', 1
    UNION ALL SELECT 'BASE_EXPLICAR_IDEIAS', 'dados-bi', 1
    UNION ALL SELECT 'BASE_EXPLICAR_IDEIAS', 'ux-ui-design', 1
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'desenvolvimento-software', 2
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'game-design', 1
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'inteligencia-artificial', 1
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'infraestrutura-redes', -2
    UNION ALL SELECT 'DESEMPATE_CRIAR_MANTER', 'seguranca-cibernetica', -1
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'seguranca-cibernetica', 2
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'infraestrutura-redes', 1
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'inteligencia-artificial', -2
    UNION ALL SELECT 'DESEMPATE_PREVENIR_EXPLORAR', 'game-design', -1
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'ux-ui-design', 2
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'gestao-ti', 2
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'dados-bi', -2
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'desenvolvimento-software', -1
    UNION ALL SELECT 'DESEMPATE_PESSOAS_LOGICA', 'inteligencia-artificial', -1
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'dados-bi', 2
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'inteligencia-artificial', 1
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'ux-ui-design', -2
    UNION ALL SELECT 'DESEMPATE_DADOS_EXPERIENCIA', 'game-design', -2
    UNION ALL SELECT 'DESEMPATE_COORDENAR_CONSTRUIR', 'gestao-ti', 2
    UNION ALL SELECT 'DESEMPATE_COORDENAR_CONSTRUIR', 'desenvolvimento-software', -2
    UNION ALL SELECT 'DESEMPATE_COORDENAR_CONSTRUIR', 'infraestrutura-redes', -1
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'game-design', 2
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'ux-ui-design', 1
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'infraestrutura-redes', -2
    UNION ALL SELECT 'DESEMPATE_INTERATIVO_REDES', 'seguranca-cibernetica', -1
) dados ON dados.codigo = p.codigo
ON DUPLICATE KEY UPDATE peso = VALUES(peso);

COMMIT;

SELECT tipo, COUNT(*) AS total
FROM perguntas
GROUP BY tipo
ORDER BY tipo;

SELECT COUNT(*) AS total_pesos FROM pergunta_pesos;
