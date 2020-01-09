package br.com.hbsis.produtos;

import java.util.Date;

public class ProdutosDTO {
    private Long id;
    private String nomeProduto;
    private double preco;
    private Long linhaCategoriaProduto;
    private Long unidadeCaixa;
    private double pesoUnidade;
    private Date validade;
    private String codProduto;
    private String medidaPeso;
    private Long idFornecedor;


    public ProdutosDTO(Long id, String nomeProduto, double preco, Long linhaCategoriaProduto, Long unidadeCaixa, double pesoUnidade, Date validade, String codProduto, String medidaPeso, Long idFornecedor) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.linhaCategoriaProduto = linhaCategoriaProduto;
        this.unidadeCaixa = unidadeCaixa;
        this.pesoUnidade = pesoUnidade;
        this.validade = validade;
        this.codProduto = codProduto;
        this.medidaPeso = medidaPeso;
        this.idFornecedor = idFornecedor;

    }

    public ProdutosDTO() { }


    public static ProdutosDTO of (Produtos produtos){
        return new ProdutosDTO(
                produtos.getId(),
                produtos.getNomeProduto(),
                produtos.getPreco(),
                produtos.getLinhaCategoriaProduto().getId(),
                produtos.getUnidadeCaixa(),
                produtos.getPesoUnidade(),
                produtos.getValidade(),
                produtos.getCodProduto(),
                produtos.getMedidaPeso(),
                produtos.getIdFornecedor().getId()
        );
    }

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

    public Long getLinhaCategoriaProduto() {
        return linhaCategoriaProduto;
    }

    public void setLinhaCategoriaProduto(Long linhaCategoriaProduto) {
        this.linhaCategoriaProduto = linhaCategoriaProduto;
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

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getMedidaPeso() {
        return medidaPeso;
    }

    public void setMedidaPeso(String medidaPeso) {
        this.medidaPeso = medidaPeso;
    }

    public Long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    @Override
    public String toString() {
        return "ProdutosDTO{" +
                "id=" + id +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", preco=" + preco +
                ", linhaCategoriaProduto=" + linhaCategoriaProduto +
                ", unidadeCaixa=" + unidadeCaixa +
                ", pesoUnidade=" + pesoUnidade +
                ", validade=" + validade +
                ", codProduto='" + codProduto + '\'' +
                ", medidaPeso='" + medidaPeso + '\'' +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}

