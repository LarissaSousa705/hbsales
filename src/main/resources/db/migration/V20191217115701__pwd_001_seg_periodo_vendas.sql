create table seg_periodo_vendas
(
        id                           BIGINT IDENTITY (1,1)  NOT NULL PRIMARY KEY,
        data_inicio                  DATE                   NOT NULL,
        data_fim                     DATE                   NOT NULL,
        periodo_vendas_fornecedor     BIGINT         REFERENCES seg_fornecedor(id)      NOT NULL,
        data_retirada                DATE                    NOT NULL,
        descricao                    VARCHAR(50)             NOT NULL


)