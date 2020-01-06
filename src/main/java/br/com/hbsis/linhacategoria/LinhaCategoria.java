
package br.com.hbsis.linhacategoria;
import br.com.hbsis.categoria.Categorias;

import javax.persistence.*;

@Entity
@Table (name = "linha_categoria")
public class LinhaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "cod_linha_categoria", unique = false, nullable = false, length = 10)
    private String codLinhaCategoria;
    /*@Column(name = "nome_categoria", unique = false, nullable = false, length = 50)
    private Categorias nomeCategoria;*/
    @Column(name = "categoria_linha", unique = false, nullable = false)
    private String categoriaLinha;


    @ManyToOne
    @JoinColumn(name = "id_categoria_produtos",referencedColumnName = "id")
    private Categorias idCategoriaProdutos;


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

    public Categorias getIdCategoriaProdutos() {
        return idCategoriaProdutos;
    }

    public void setIdCategoriaProdutos(Categorias idCategoriaProdutos) {
        this.idCategoriaProdutos = idCategoriaProdutos;
    }

    public String getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(String categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }

    @Override
    public String toString() {
        return "LinhaCategoria{" +
                "Id=" + Id +
                ", codLinhaCategoria='" + codLinhaCategoria + '\'' +
                ", categoriaLinha='" + categoriaLinha + '\'' +
                ", idCategoriaProdutos=" + idCategoriaProdutos +
                '}';
    }
}

