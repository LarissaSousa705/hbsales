package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;
import java.time.LocalDateTime;

//mapeamento da entidade do banco de dados
@Entity
@Table(name = "seg_periodo_vendas")
public class PeriodoVendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_inicio",unique = false, nullable = false)
    private LocalDateTime dataInicio;
    @Column(name = "data_fim", unique = false,nullable = false)
    private LocalDateTime dataFim;
    @OneToOne
    @JoinColumn(name = "periodo_vendas_fornecedor",referencedColumnName = "id")
    private Fornecedor periodoVendasFornecedor;
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

    public LocalDateTime setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public LocalDateTime setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
        return dataFim;
    }

    public Fornecedor getPeriodoVendasFornecedor() {
        return periodoVendasFornecedor;
    }

    public void setPeriodoVendasFornecedor(Fornecedor periodoVendasFornecedor) {
        this.periodoVendasFornecedor = periodoVendasFornecedor;
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
                ", periodoVendasFornecedor=" + periodoVendasFornecedor +
                ", dataRetirada=" + dataRetirada +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
