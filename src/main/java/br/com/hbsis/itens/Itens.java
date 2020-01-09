package br.com.hbsis.itens;

import br.com.hbsis.pedidos.Pedidos;
import br.com.hbsis.produtos.Produtos;

import javax.persistence.*;

@Entity
@Table(name = "seg_itens")
public class Itens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_produtos", referencedColumnName = "id")
    private Produtos produtos;
    @Column(name = "quantidade",nullable = false)
    private Long quantidade;
    @ManyToOne
    @JoinColumn(name = "id_pedidos", referencedColumnName = "id")
    private Pedidos pedidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public Pedidos getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Itens{" +
                "id=" + id +
                ", produtos=" + produtos +
                ", quantidade=" + quantidade +
                ", pedidos=" + pedidos +
                '}';
    }
}