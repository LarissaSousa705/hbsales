package br.com.hbsis.periodoVendas;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

//mapeamento da entidade do banco de dados
@Entity
@Table(name = "seg_perido_vendas")
public class PeriodoVendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_inicio",unique = false, nullable = false)
    private LocalDateTime dataInicio;
    @Column(name = "data_fim", unique = false,nullable = false)
    private LocalDateTime dataFim;
    @Column(name = "perido_vendas_fornecedor", unique = false, nullable = false)
    private LocalDateTime peridoVendasFornecedor;
    @Column(name = "data_retirada", unique = false, nullable = false)
    private LocalDateTime dataRetirada;
    @Column(name = "descricao", unique = false, nullable = false, length = 50)
    private String descricao;

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
        return "PeriodoVendas{" +
                "id=" + id +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", peridoVendasFornecedor=" + peridoVendasFornecedor +
                ", dataRetirada=" + dataRetirada +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
