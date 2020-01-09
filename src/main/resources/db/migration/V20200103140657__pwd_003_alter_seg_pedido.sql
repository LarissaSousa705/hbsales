
ALTER TABLE dbo.seg_pedido
ADD CONSTRAINT FK_periodo_vendas
FOREIGN KEY (periodo_vendas) REFERENCES seg_periodo_vendas(id);