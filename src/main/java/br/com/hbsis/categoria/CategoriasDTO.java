package br.com.hbsis.categoria;

public class CategoriasDTO {

    private Long id;
    private String nomeCategoria;
    private Long codCategoria;
    private Long fornecedor04;

    public CategoriasDTO(){
    }


    public CategoriasDTO(Long id, String nomeCategoria, Long codCategoria, Long fornecedor04) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.codCategoria = codCategoria;
        this.fornecedor04 = fornecedor04;
    }

    public static CategoriasDTO of(Categorias categorias) {
        return new  CategoriasDTO(
                categorias.getId(),
                categorias.getNome_categoria(),
                categorias.getCod_categoria(),
                categorias.getFornecedor04().getId()
        );
    }

    public Long getFornecedor04() {
        return fornecedor04;
    }

    public void setFornecedor04(Long fornecedor04) {
        this.fornecedor04 = fornecedor04;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }


    public Long getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Long codCategoria) {
        this.codCategoria = codCategoria;
    }


    @Override
    public String toString() {
        return "CategoriasDTO{" +
                "id=" + id +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", codCategoria=" + codCategoria +
                ", fornecedor04=" + fornecedor04 +
                '}';
    }


}