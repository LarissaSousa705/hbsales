create table seg_categorias
(
       id              BIGINT IDENTITY (1, 1)          NOT NULL PRIMARY KEY,
       cod_categoria   BIGINT                          NOT NULL,
       nome_categoria                 VARCHAR(5000)      NOT NULL,
       fornecedor04    BIGINT FOREIGN KEY REFERENCES seg_fornecedor(id) NOT NULL
);

