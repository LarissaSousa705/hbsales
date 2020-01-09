package br.com.hbsis.categoria;

public class CategoriasDTO {

    private Long id;
    private String nomeCategoria;
    private String codCategoria;
    private Long fornecedor;

    public CategoriasDTO(){
    }

    public CategoriasDTO(Long id, String nomeCategoria, String codCategoria, Long fornecedor04) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.codCategoria = codCategoria;
        this.fornecedor = fornecedor04;
    }

    public static CategoriasDTO of(Categorias categorias) {
        return new  CategoriasDTO(
                categorias.getId(),
                categorias.getNomeCategoria(),
                categorias.getCodCategoria(),
                categorias.getFornecedor().getId()
        );
    }

    public Long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Long fornecedor) {
        this.fornecedor = fornecedor;
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

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    @Override
    public String toString() {
        return "CategoriasDTO{" +
                "id=" + id +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", codCategoria=" + codCategoria +
                ", fornecedor04=" + fornecedor +
                '}';
    }


}