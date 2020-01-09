package br.com.hbsis.pedidos;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.itens.Itens;
import br.com.hbsis.periodoVendas.PeriodoVendas;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seg_pedido")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_pedido", unique = true, nullable = false)
    private String codPedido;
    @Column(name = "status", unique = false, nullable = false, length = 20)
    private String status;
    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;
    @ManyToOne
    @JoinColumn(name = "funcionario", nullable = false, referencedColumnName = "id")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "periodo_vendas", nullable = false, referencedColumnName = "id")
    private PeriodoVendas periodoVendas;
    @OneToMany(mappedBy = "pedidos")
    private List<Itens> itensList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public PeriodoVendas getPeriodoVendas() {
        return periodoVendas;
    }

    public void setPeriodoVendas(PeriodoVendas periodoVendas) {
        this.periodoVendas = periodoVendas;
    }

    public List<Itens> getItensList() {
        return itensList;
    }

    public void setItensList(List<Itens> itensList) {
        this.itensList = itensList;
    }


}

