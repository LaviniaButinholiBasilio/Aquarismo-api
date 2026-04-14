-- ============================================================
-- V2 - Dados iniciais de exemplo para desenvolvimento
-- ============================================================

INSERT INTO tanques (nome, capacidade_litros, tipo_agua, localizacao, descricao, status, criado_em, atualizado_em) VALUES
('Tanque Comunidade A', 200.0, 'DOCE',    'Sala principal - Estante 1', 'Tanque comunitário para peixes tropicais de água doce', 'ATIVO', NOW(), NOW()),
('Tanque Marinho 1',    300.0, 'SALGADA', 'Sala principal - Estante 2', 'Recife de coral com peixes marinhos ornamentais',        'ATIVO', NOW(), NOW()),
('Tanque Reprodução',   100.0, 'DOCE',    'Sala de reprodução',         'Reservado exclusivamente para casais em reprodução',     'ATIVO', NOW(), NOW()),
('Tanque Quarentena',    50.0, 'DOCE',    'Sala de quarentena',         'Isolamento e aclimatação de peixes recém-adquiridos',    'ATIVO', NOW(), NOW());

INSERT INTO peixes (nome, nome_cientifico, especie, sexo, data_aquisicao, preco_aquisicao, status, tanque_id, criado_em, atualizado_em) VALUES
('Nemo',       'Amphiprioninae',          'Peixe-palhaço',  'MACHO',      '2025-01-10', 35.00,  'ATIVO', 2, NOW(), NOW()),
('Guppy Alpha','Poecilia reticulata',     'Guppy',          'MACHO',      '2025-02-01', 12.00,  'ATIVO', 1, NOW(), NOW()),
('Guppy Beta', 'Poecilia reticulata',     'Guppy',          'FEMEA',      '2025-02-01', 12.00,  'ATIVO', 1, NOW(), NOW()),
('Blue Tang',  'Paracanthurus hepatus',   'Cirurgião-azul', 'INDEFINIDO', '2025-03-05', 150.00, 'ATIVO', 2, NOW(), NOW()),
('Disco Sol',  'Symphysodon discus',      'Disco',          'MACHO',      '2025-03-15', 80.00,  'ATIVO', 3, NOW(), NOW()),
('Disco Luna', 'Symphysodon discus',      'Disco',          'FEMEA',      '2025-03-15', 80.00,  'ATIVO', 3, NOW(), NOW());

INSERT INTO parametros_agua (tanque_id, data_hora_medicao, ph, temperatura, amonia, nitrito, nitrato, dureza_total, criado_em) VALUES
(1, NOW(), 7.2, 26.5, 0.0,  0.02, 10.0, 8.0,  NOW()),
(2, NOW(), 8.3, 25.0, 0.0,  0.00,  5.0, 18.0, NOW()),
(3, NOW(), 7.0, 28.0, 0.02, 0.05, 15.0, 7.5,  NOW()),
(4, NOW(), 7.1, 27.0, 0.0,  0.01,  8.0, 7.0,  NOW());
