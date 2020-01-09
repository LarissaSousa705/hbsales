ALTER TABLE seg_funcionario
ADD PRIMARY KEY (id);

ALTER TABLE dbo.seg_pedido
ADD CONSTRAINT FK_funcionario
FOREIGN KEY (funcionario) REFERENCES seg_funcionario(id);