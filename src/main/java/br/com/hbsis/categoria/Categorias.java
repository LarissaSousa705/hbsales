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

    @Column(name = "nome_categoria", unique = false,nullable = false, length = 50)
    @CsvBindByPosition(position = 2)
    private String nomeCategoria;
    @CsvBindByPosition(position = 1)
    @Column(name = "cod_categoria", unique = false, nullable = false, length = 50)
    private String codCategoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor04", referencedColumnName = "id")
    private Fornecedor fornecedor04;

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

    public Fornecedor getFornecedor04() {
        return fornecedor04;
    }

    public void setFornecedor04(Fornecedor fornecedor04) {
        this.fornecedor04 = fornecedor04;
    }

    @Override
    public String toString() {
        return "Categorias{" +
                "id=" + id +
                ", nome_categoria='" + nomeCategoria + '\'' +
                ", cod_categoria=" + codCategoria +
                ", fornecedor04=" + fornecedor04 +
                '}';
    }
}