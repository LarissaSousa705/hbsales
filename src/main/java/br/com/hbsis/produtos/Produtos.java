package br.com.hbsis.produtos;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.linhacategoria.LinhaCategoria;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "seg_produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cod_produto", unique = false, nullable = false, length = 10)
    private String codProduto;
    @Column(name = "nome_produto", unique = false, nullable = false, length = 200)
    private String nomeProduto;
    @Column(name = "preco", unique = false, nullable = false)
    private double preco;
    @Column(name = "unidade_caixa", unique = false, nullable = false)
    private Long unidadeCaixa;
    @Column(name = "peso_unidade", unique = false, nullable = false)
    private double pesoUnidade;
    @Column(name = "validade", unique = false, nullable = false)
    private Date validade;
    @Column(name = "medida_peso", unique = false, nullable = false, length = 50)
    private String medidaPeso;
    @ManyToOne
    @JoinColumn(name = "linha_categoria_produto", referencedColumnName = "id")
    private LinhaCategoria linhaCategoriaProduto;
    @OneToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor idFornecedor;

    public Produtos() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
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

    public Long getUnidadeCaixa() {
        return unidadeCaixa;
    }

    public void setUnidadeCaixa(Long unidadeCaixa) {
        this.unidadeCaixa = unidadeCaixa;
    }

    public double getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(double pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getMedidaPeso() {
        return medidaPeso;
    }

    public void setMedidaPeso(String medidaPeso) {
        this.medidaPeso = medidaPeso;
    }

    public LinhaCategoria getLinhaCategoriaProduto() {
        return linhaCategoriaProduto;
    }

    public void setLinhaCategoriaProduto(LinhaCategoria linhaCategoriaProduto) {
        this.linhaCategoriaProduto = linhaCategoriaProduto;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "id=" + id +
                ", codProduto='" + codProduto + '\'' +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", preco=" + preco +
                ", unidadeCaixa=" + unidadeCaixa +
                ", pesoUnidade=" + pesoUnidade +
                ", validade=" + validade +
                ", medidaPeso='" + medidaPeso + '\'' +
                ", linhaCategoriaProduto=" + linhaCategoriaProduto +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}


