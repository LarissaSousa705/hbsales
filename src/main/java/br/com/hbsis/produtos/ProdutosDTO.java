package br.com.hbsis.produtos;

import br.com.hbsis.linhacategoria.LinhaCategoria;

import java.time.LocalDateTime;
import java.util.Date;

public class ProdutosDTO {
    private Long id;
    private String nomeProduto;
    private double preco;
    private Long linhaCategoria;
    private double unidadeCaixa;
    private double pesoUnidade;
    private LocalDateTime validade;

    public ProdutosDTO(Long id, String nomeProduto, double preco, Long linhaCategoria, double unidadeCaixa, double pesoUnidade, LocalDateTime validade) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.linhaCategoria = linhaCategoria;
        this.unidadeCaixa = unidadeCaixa;
        this.pesoUnidade = pesoUnidade;
        this.validade = validade;
    }

    public static  ProdutosDTO of(Produtos produtos){
        return new ProdutosDTO(
          produtos.getId(),
          produtos.getNomeProduto(),
          produtos.getPreco(),
          produtos.getLinhaCategoria().getId(),
          produtos.getUnidadeCaixa(),
          produtos.getPesoUnidade(),
          produtos.getValidade()

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

    public Long getLinhaCategoria() {
        return linhaCategoria;
    }

    public void setLinhaCategoria(Long linhaCategoria) {
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
        return "ProdutosDTO{" +
                "id=" + id +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", preco=" + preco +
                ", linhaCategoria='" + linhaCategoria + '\'' +
                ", unidadeCaixa=" + unidadeCaixa +
                ", pesoUnidade=" + pesoUnidade +
                ", validade='" + validade + '\'' +
                '}';
    }
}
