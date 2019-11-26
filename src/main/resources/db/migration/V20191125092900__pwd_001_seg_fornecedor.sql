create table seg_fornecedor
(
    id    BIGINT IDENTITY (1, 1)         PRIMARY KEY  ,
    razao_social  VARCHAR(200)           NOT NULL,
    nome_fantasia  VARCHAR(50)           NOT NULL,
    endereco      VARCHAR(200)           NOT NULL,
    telefone      VARCHAR(11)            NOT NULL,
    email         VARCHAR(200)           NOT NULL,
    cnpj          VARCHAR(11)            NOT NULL
    );
    create unique index ix_seg_fornecedor_07 on seg_fornecedor (cnpj asc);