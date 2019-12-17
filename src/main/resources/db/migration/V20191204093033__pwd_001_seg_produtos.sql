create table seg_produtos
(
            id                          BIGINT IDENTITY(1,1)    NOT NULL PRIMARY KEY,
            nome_produto                VARCHAR(5000)    NOT NULL,
            preco                       DECIMAL          NOT NULL,
            unidade_caixa               DECIMAL          NOT NULL,
            peso_unidade                DECIMAL          NOT NULL,
            validade                   DATE      NOT NULL,
            linha_categoria_produto     BIGINT FOREIGN KEY REFERENCES linha_categoria (id)  NOT NULL
);
