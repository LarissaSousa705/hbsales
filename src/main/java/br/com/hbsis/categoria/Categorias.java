package br.com.hbsis.categoria;


import br.com.hbsis.fornecedor.Fornecedor;
import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;

/**
 * Classe responsável pelo mapeamento da entidade do banco de dados
 */
@Entity
@Table(name = "seg_categorias")
public
class Categorias {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByPosition(position = 0)
    private Long id;

    @Column(name = "nome_categoria", unique = false,nullable = false, length = 5000)
    @CsvBindByPosition(position = 2)
    private String nome_categoria;
    @CsvBindByPosition(position = 1)
    @Column(name = "cod_categoria", unique = false, nullable = false, length = 200)
    private Long cod_categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor04", referencedColumnName = "id")
    private Fornecedor fornecedor04;

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

    public Fornecedor getFornecedor04() {
        return fornecedor04;
    }

    public void setFornecedor04(Fornecedor fornecedor04) {
        this.fornecedor04 = fornecedor04;
    }

    public Long getFornecedorId(){
        return fornecedor04.getId();
    }

    @Override
    public String toString() {
        return "Categorias{" +
                "id=" + id +
                ", nome_categoria='" + nome_categoria + '\'' +
                ", cod_categoria=" + cod_categoria +
                ", fornecedor04=" + fornecedor04 +
                '}';
    }

}