package br.com.hbsis.produtos;

import br.com.hbsis.categoria.Categorias;
import br.com.hbsis.linhacategoria.LinhaCategoria;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "seg_produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_produto", unique = false, nullable = false, length = 5000)
    private String nomeProduto;
    @Column(name = "preco", unique = false, nullable = false)
    private double preco;
    @ManyToOne
    @JoinColumn(name = "linha_categoria_produto", referencedColumnName = "id")
    private LinhaCategoria linhaCategoria;
    @Column(name = "unidade_caixa", unique = false, nullable = false)
    private double unidadeCaixa;
    @Column(name = "peso_unidade", unique = false, nullable = false)
    private double pesoUnidade;
    @Column(name = "validade", unique = false, nullable = false, length = 20)
    private LocalDateTime validade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public LinhaCategoria getLinhaCategoria() {
        return linhaCategoria;
    }

    public void setLinhaCategoria(LinhaCategoria linhaCategoria) {
        this.linhaCategoria = linhaCategoria;
    }

    public double getUnidadeCaixa() {
        return unidadeCaixa;
    }

    public void setUnidadeCaixa(double unidadeCaixa) {
        this.unidadeCaixa = unidadeCaixa;
    }

    public double getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(double pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public LocalDateTime getValidade() {
        return validade;
    }

    public void setValidade(LocalDateTime validade) {
        this.validade = validade;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "id=" + id +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", preco=" + preco +
                ", linhaCategoria=" + linhaCategoria +
                ", unidadeCaixa=" + unidadeCaixa +
                ", pesoUnidade=" + pesoUnidade +
                ", validade='" + validade + '\'' +
                '}';
    }
}
