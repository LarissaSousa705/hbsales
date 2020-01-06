create table seg_funcionario
(
       id         BIGINT IDENTITY(1,1)  NOT NULL,
       nome       VARCHAR(50)           NOT NULL,
       email      VARCHAR(50)           NOT NULL,
       uuid_api   VARCHAR(36)           NOT NULL
)