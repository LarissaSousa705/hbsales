package br.com.hbsis.linhacategoria;

public class LinhaCategoriaDTO {
    private Long Id;
    private Long codLinhaCategoria;
    private String categoriaLinha;
    private String nomeCategoria;
    private  Long idCategoriaProdutos;


    public LinhaCategoriaDTO(Long id, Long codLinhaCategoria, String categoriaLinha, String nomeCategoria, Long idCategoriaProdutos) {
        Id = id;
        this.codLinhaCategoria = codLinhaCategoria;
        this.categoriaLinha = categoriaLinha;
        this.nomeCategoria = nomeCategoria;
        this.idCategoriaProdutos = idCategoriaProdutos;
    }

    public static LinhaCategoriaDTO of(LinhaCategoria linhaCategoria){
        return new LinhaCategoriaDTO(
            linhaCategoria.getId(),
                linhaCategoria.getCodLinhaCategoria(),
                linhaCategoria.getCategoriaLinha(),
                linhaCategoria.getNomeCategoria(),
                linhaCategoria.getIdCategoriaProdutos()
        );
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getCodLinhaCategoria() {
        return codLinhaCategoria;
    }

    public void setCodLinhaCategoria(Long codLinhaCategoria) {
        this.codLinhaCategoria = codLinhaCategoria;
    }

    public String getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(String categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
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
                ", codLinhaCategoria=" + codLinhaCategoria +
                ", categoriaLinha='" + categoriaLinha + '\'' +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", idCategoriaProdutos=" + idCategoriaProdutos +
                '}';
    }
}


