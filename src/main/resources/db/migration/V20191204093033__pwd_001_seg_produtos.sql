create table seg_produtos
(
            id                          BIGINT IDENTITY(1,1)    PRIMARY KEY,
            nome_produto                VARCHAR(200)            NOT NULL,
            preco                       DECIMAL(25,2)           NOT NULL,
            unidade_caixa               INTEGER                 NOT NULL,
            peso_unidade                DECIMAL(25,3)           NOT NULL,
            validade                    DATE                    NOT NULL,
            cod_produto                 VARCHAR(10)             NOT NULL,
            medida_peso                 VARCHAR(50)             NOT NULL,
            linha_categoria_produto     BIGINT                  REFERENCES      linha_categoria (id) NULL
);
