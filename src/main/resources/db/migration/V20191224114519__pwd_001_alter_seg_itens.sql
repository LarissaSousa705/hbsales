CREATE TABLE seg_itens
(
        id INTEGER IDENTITY (1,1) NOT NULL,
       id_pedidos   BIGINT REFERENCES seg_pedido(id)  NOT NULL,
       id_produtos   BIGINT REFERENCES seg_produtos(id) NOT NULL
);
