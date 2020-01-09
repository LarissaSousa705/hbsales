package br.com.hbsis.itens;

public class ItensDTO {

    private Long id;
    private Long produtos;
    private Long quantidade;
    private Long pedidos;

    public ItensDTO(Long id, Long produtos, Long quantidade, Long pedidos) {
        this.id = id;
        this.produtos = produtos;
        this.quantidade = quantidade;
        this.pedidos = pedidos;
    }

    public ItensDTO() {}

    public static ItensDTO of(Itens itens){
        return new ItensDTO(
                itens.getId(),
                itens.getProdutos().getId(),
                itens.getQuantidade(),
                itens.getPedidos().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutos() {
        return produtos;
    }

    public void setProdutos(Long produtos) {
        this.produtos = produtos;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Long getPedidos() {
        return pedidos;
    }

    public void setPedidos(Long pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "ItensDTO{" +
                "id=" + id +
                ", produtos=" + produtos +
                ", quantidade=" + quantidade +
                ", pedidos=" + pedidos +
                '}';
    }
}
