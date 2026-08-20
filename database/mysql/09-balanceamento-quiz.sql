-- Rota TI - ajuste fino de balanceamento das areas do quiz
-- Execute em bancos existentes antes de rodar novamente o 02-seed-perguntas.sql.
-- Esta migracao altera apenas pesos de perguntas; nao apaga usuarios, contas, respostas ou resultados.

USE rotati;

START TRANSACTION;

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
    SELECT 'BASE_DADOS_ORGANIZAR' codigo, 'inteligencia-artificial' area_slug, 1 peso
    UNION ALL SELECT 'BASE_INFRA_ESTABILIDADE', 'seguranca-cibernetica', 1
    UNION ALL SELECT 'BASE_GESTAO_LIDERAR', 'infraestrutura-redes', 1
) dados ON dados.codigo = p.codigo
ON DUPLICATE KEY UPDATE peso = VALUES(peso);

COMMIT;

SELECT p.codigo, pp.area_slug, pp.peso
FROM pergunta_pesos pp
JOIN perguntas p ON p.id = pp.pergunta_id
WHERE p.codigo IN (
    'BASE_DADOS_ORGANIZAR',
    'BASE_SEG_DETALHES',
    'BASE_INFRA_ESTABILIDADE',
    'BASE_UX_USUARIOS',
    'BASE_IA_CURIOSIDADE',
    'BASE_GESTAO_LIDERAR'
)
ORDER BY p.codigo, pp.area_slug;
