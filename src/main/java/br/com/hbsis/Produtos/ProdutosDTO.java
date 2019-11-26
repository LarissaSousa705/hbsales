package br.com.hbsis.Produtos;

public class ProdutosDTO {

    private Long id;
    private String nome_categoria;
    private Long fornecedor_id;

    public Long getFornecedor_id() {
        return fornecedor_id;
    }

    public void setFornecedor_id(Long fornecedor_id) {
        this.fornecedor_id = fornecedor_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.nome_categoria = nome_categoria;
    }

    public ProdutosDTO(){
    }

    public ProdutosDTO(Long id, String fornecedor_categoria, String nome_categoria, String cod_categoria){
        this.id = id;
        this.nome_categoria = nome_categoria;
        this.fornecedor_id = fornecedor_id;
    }

    public ProdutosDTO( Long fornecedor_id, String nome_categoria){
    }

    public static ProdutosDTO of(Produtos Produtos){
        return new ProdutosDTO(
                Produtos.getFornecedor_id(),
                Produtos.getNome_categoria()

        );
    }

    @Override
    public String toString() {
        return "br.com.hbsis.Produtos{" +
                "id=" + id +
                ", fornecedor_id='" + fornecedor_id + '\'' +
                ", nome_categoria='" + nome_categoria + '\'' +
                '}';
    }
    }