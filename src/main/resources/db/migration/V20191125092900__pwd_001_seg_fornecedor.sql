create table seg_fornecedor
(
    id    BIGINT IDENTITY (1, 1)         PRIMARY KEY  ,
    razao         VARCHAR(100)           NOT NULL,
    nome_fantasia  VARCHAR(100)           NOT NULL,
    endereco      VARCHAR(100)           NOT NULL,
    telefone      VARCHAR(14)            NOT NULL,
    email         VARCHAR(50)           NOT NULL,
    cnpj          VARCHAR(14)            NOT NULL
    );


