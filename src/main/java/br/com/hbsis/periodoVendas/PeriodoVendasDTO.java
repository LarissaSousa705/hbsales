package br.com.hbsis.periodoVendas;

//tráfego de informaçoes

import java.time.LocalDateTime;

public class PeriodoVendasDTO {

    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime peridoVendasFornecedor;
    private LocalDateTime dataRetirada;
    private String descricao;

    public PeriodoVendasDTO(Long id, LocalDateTime dataInicio, LocalDateTime dataFim, LocalDateTime peridoVendasFornecedor, LocalDateTime dataRetirada, String descricao) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.peridoVendasFornecedor = peridoVendasFornecedor;
        this.dataRetirada = dataRetirada;
        this.descricao = descricao;
    }
    public static PeriodoVendasDTO of(PeriodoVendas periodoVendas){
        return new PeriodoVendasDTO(
                periodoVendas.getId(),
                periodoVendas.getDataInicio(),
                periodoVendas.getDataFim(),
                periodoVendas.getPeridoVendasFornecedor(),
                periodoVendas.getDataRetirada(),
                periodoVendas.getDescricao()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getPeridoVendasFornecedor() {
        return peridoVendasFornecedor;
    }

    public void setPeridoVendasFornecedor(LocalDateTime peridoVendasFornecedor) {
        this.peridoVendasFornecedor = peridoVendasFornecedor;
    }

    public LocalDateTime getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDateTime dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "PeriodoVendasDTO{" +
                "id=" + id +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", peridoVendasFornecedor=" + peridoVendasFornecedor +
                ", dataRetirada=" + dataRetirada +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}

