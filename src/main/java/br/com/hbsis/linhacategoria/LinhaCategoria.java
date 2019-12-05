
package br.com.hbsis.linhacategoria;
import br.com.hbsis.categoria.Categorias;

import javax.persistence.*;

@Entity
@Table (name = "linha_categoria")
public class LinhaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "cod_linha_categoria", unique = false, nullable = false, length = 5000)
    private Long codLinhaCategoria;
    @Column(name = "nome_categoria", unique = false, nullable = false, length = 5000)
    private String nomeCategoria;
    @Column(name = "categoria_linha", unique = false, nullable = false)
    private String categoriaLinha;

  //  @ManyToOne
    @JoinColumn(name = "id_categoria_produtos",referencedColumnName = "id")
    private Long idCategoriaProdutos;

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

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
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
        return "LinhaCategoria{" +
                "Id=" + Id +
                ", codLinhaCategoria=" + codLinhaCategoria +
                ", idCategoriaProdutos=" + idCategoriaProdutos +
                ", categoriaLinha='" + categoriaLinha + '\'' +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }


}

