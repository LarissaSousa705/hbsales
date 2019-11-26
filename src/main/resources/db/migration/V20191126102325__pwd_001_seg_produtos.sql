create table seg_produtos
(
       id              BIGINT IDENTITY (1, 1)         NOT NULL,
      nome_categoria        VARCHAR(50)              NOT NULL,
      fornecedor_id    BIGINT FOREIGN KEY REFERENCES seg_fornecedor(id)
);