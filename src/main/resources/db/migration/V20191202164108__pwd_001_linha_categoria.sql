create table linha_categoria
(
         id                         BIGINT IDENTITY (1, 1)        NOT NULL PRIMARY KEY,
         cod_linha_categoria        BIGINT                        NOT NULL,
         categoria_linha            VARCHAR(5000)                 NOT NULL,
         nome_categoria             VARCHAR (5000)                NOT NULL,
         id_categoria_produtos      BIGINT  REFERENCES seg_categorias(id) NOT NULL

);