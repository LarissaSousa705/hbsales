package br.com.hbsis.Produtos;


import br.com.hbsis.Fornecedor.Fornecedor;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

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
    @CsvBindByPosition(position = 0)
    private Long id;

    @Column(name = "nome_categoria", unique = false,nullable = false, length = 500)
    @CsvBindByPosition(position = 2)
    private String nome_categoria;
    @CsvBindByPosition(position = 1)
    @Column(name = "cod_categoria", unique = false, nullable = false, length = 200)
    private Long cod_categoria;
    @CsvBindByPosition(position = 3)
   // @ManyToOne
    @JoinColumn(name = "Fornecedor_id", referencedColumnName = "id")
    private Long fornecedor_id;


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

    public Long getCod_categoria() {
        return cod_categoria;
    }

    public void setCod_categoria(Long cod_categoria) {
        this.cod_categoria = cod_categoria;
    }

    public Long getFornecedor_id() {
        return fornecedor_id;
    }

    public void setFornecedor_id(Long fornecedor_id) {
        this.fornecedor_id = fornecedor_id;
    }

    @Override
    public String toString() {
        return "br.com.hbsis.Produtos{" +
                "fornecedor_id=" + fornecedor_id +
                ", nome_categoria='" + nome_categoria + '\'' +
                ", cod_categoria='" + cod_categoria + '\'' +
                '}';
    }
}