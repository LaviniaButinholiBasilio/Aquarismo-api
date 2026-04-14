-- ============================================================
-- V1 - Criação das tabelas do Sistema de Gestão de Aquarismo
-- ============================================================

CREATE TABLE IF NOT EXISTS tanques (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(100)   NOT NULL,
    capacidade_litros DOUBLE        NOT NULL,
    tipo_agua        VARCHAR(20)    NOT NULL,
    localizacao      VARCHAR(255),
    descricao        VARCHAR(500),
    status           VARCHAR(20)    NOT NULL DEFAULT 'ATIVO',
    criado_em        DATETIME       NOT NULL,
    atualizado_em    DATETIME       NOT NULL,
    CONSTRAINT chk_tipo_agua  CHECK (tipo_agua IN ('DOCE', 'SALGADA', 'SALOBRA')),
    CONSTRAINT chk_status_tanque CHECK (status IN ('ATIVO', 'EM_MANUTENCAO', 'DESATIVADO'))
);

CREATE TABLE IF NOT EXISTS peixes (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(100)   NOT NULL,
    nome_cientifico  VARCHAR(150),
    especie          VARCHAR(100)   NOT NULL,
    sexo             VARCHAR(20)    NOT NULL DEFAULT 'INDEFINIDO',
    data_aquisicao   DATE           NOT NULL,
    data_nascimento  DATE,
    preco_aquisicao  DECIMAL(10,2),
    observacoes      VARCHAR(500),
    status           VARCHAR(20)    NOT NULL DEFAULT 'ATIVO',
    tanque_id        BIGINT         NOT NULL,
    mae_id           BIGINT,
    pai_id           BIGINT,
    criado_em        DATETIME       NOT NULL,
    atualizado_em    DATETIME       NOT NULL,
    CONSTRAINT fk_peixe_tanque FOREIGN KEY (tanque_id) REFERENCES tanques(id),
    CONSTRAINT fk_peixe_mae    FOREIGN KEY (mae_id)    REFERENCES peixes(id),
    CONSTRAINT fk_peixe_pai    FOREIGN KEY (pai_id)    REFERENCES peixes(id),
    CONSTRAINT chk_sexo_peixe  CHECK (sexo   IN ('MACHO', 'FEMEA', 'INDEFINIDO')),
    CONSTRAINT chk_status_peixe CHECK (status IN ('ATIVO', 'VENDIDO', 'MORTO', 'EM_REPRODUCAO'))
);

CREATE TABLE IF NOT EXISTS reproducoes (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    mae_id               BIGINT   NOT NULL,
    pai_id               BIGINT   NOT NULL,
    data_reproducao      DATE     NOT NULL,
    data_eclosao         DATE,
    quantidade_ovos      INT,
    quantidade_filhotes  INT,
    filhotes_vivos       INT,
    observacoes          VARCHAR(500),
    criado_em            DATETIME NOT NULL,
    atualizado_em        DATETIME NOT NULL,
    CONSTRAINT fk_reprod_mae FOREIGN KEY (mae_id) REFERENCES peixes(id),
    CONSTRAINT fk_reprod_pai FOREIGN KEY (pai_id) REFERENCES peixes(id)
);

CREATE TABLE IF NOT EXISTS vendas (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    peixe_id            BIGINT         NOT NULL,
    data_venda          DATE           NOT NULL,
    valor_unitario      DECIMAL(10,2)  NOT NULL,
    nome_comprador      VARCHAR(150)   NOT NULL,
    telefone_comprador  VARCHAR(20),
    email_comprador     VARCHAR(150),
    observacoes         VARCHAR(500),
    criado_em           DATETIME       NOT NULL,
    atualizado_em       DATETIME       NOT NULL,
    CONSTRAINT fk_venda_peixe FOREIGN KEY (peixe_id) REFERENCES peixes(id)
);

CREATE TABLE IF NOT EXISTS parametros_agua (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    tanque_id          BIGINT   NOT NULL,
    data_hora_medicao  DATETIME NOT NULL,
    ph                 DOUBLE   NOT NULL,
    temperatura        DOUBLE   NOT NULL,
    amonia             DOUBLE,
    nitrito            DOUBLE,
    nitrato            DOUBLE,
    dureza_total       DOUBLE,
    dureza_carbonato   DOUBLE,
    oxigenio_dissolv   DOUBLE,
    salinidade         DOUBLE,
    observacoes        VARCHAR(500),
    criado_em          DATETIME NOT NULL,
    CONSTRAINT fk_param_tanque FOREIGN KEY (tanque_id) REFERENCES tanques(id),
    CONSTRAINT chk_ph CHECK (ph BETWEEN 0 AND 14)
);
