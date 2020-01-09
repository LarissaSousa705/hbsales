package br.com.hbsis.linhacategoria;

public class LinhaCategoriaDTO {
    private Long Id;
    private String codLinhaCategoria;
    private String categoriaLinha;
    private  Long idCategoriaProdutos;


    public LinhaCategoriaDTO(Long id, String codLinhaCategoria, String categoriaLinha, Long idCategoriaProdutos) {
        Id = id;
        this.codLinhaCategoria = codLinhaCategoria;
        this.categoriaLinha = categoriaLinha;
        this.idCategoriaProdutos = idCategoriaProdutos;
    }

    public LinhaCategoriaDTO() {

    }

    public static LinhaCategoriaDTO of(LinhaCategoria linhaCategoria){
        return new LinhaCategoriaDTO(
            linhaCategoria.getId(),
                linhaCategoria.getCodLinhaCategoria(),
                linhaCategoria.getCategoriaLinha(),
                linhaCategoria.getIdCategoriaProdutos().getId()
        );
    }
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCodLinhaCategoria() {
        return codLinhaCategoria;
    }

    public void setCodLinhaCategoria(String codLinhaCategoria) {
        this.codLinhaCategoria = codLinhaCategoria;
    }

    public String getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(String categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }

    public Long getIdCategoriaProdutos() {
        return idCategoriaProdutos;
    }

    public void setIdCategoriaProdutos(Long idCategoriaProdutos) {
        this.idCategoriaProdutos = idCategoriaProdutos;
    }

    @Override
    public String toString() {
        return "LinhaCategoriaDTO{" +
                "Id=" + Id +
                ", codLinhaCategoria='" + codLinhaCategoria + '\'' +
                ", categoriaLinha='" + categoriaLinha + '\'' +
                ", idCategoriaProdutos=" + idCategoriaProdutos +
                '}';
    }
}


