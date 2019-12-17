create table linha_categoria
(
         id                         BIGINT IDENTITY (1, 1)        NOT NULL PRIMARY KEY,
         cod_linha_categoria        VARCHAR(10)                      NOT NULL,
         categoria_linha            VARCHAR(5000)                 NOT NULL,
         nome_categoria             VARCHAR (50)                NOT NULL

);
        create unique index ix_linha_categoria on linha_categoria (cod_linha_categoria asc );

