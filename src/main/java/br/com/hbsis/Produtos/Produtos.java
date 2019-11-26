package br.com.hbsis.Produtos;


import javax.persistence.*;

/**
 * Classe responsável pelo mapeamento da entidade do banco de dados
 */
@Entity
@Table(name = "seg_produtos")
public
class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_categoria", unique = false,nullable = false, length = 500)
    private String nome_categoria;
    @Column(name = "Fornecedor_id", unique = false, nullable = false, length = 200)
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

    @Override
    public String toString() {
        return "br.com.hbsis.Produtos{" +
                "fornecedor_id=" + fornecedor_id +
                ", nome_categoria='" + nome_categoria + '\'' +

                '}';
    }
}