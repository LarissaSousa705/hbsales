package br.com.hbsis.fornecedor;

import javax.persistence.*;

/**
 * Classe responsável pelo mapeamento da entidade do banco de dados
 */
@Entity
@Table(name = "seg_fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao", unique = false, nullable = false, length = 100)
    private String razao;
    @Column(name = "nome_fantasia", unique = false, length = 100)
    private String nomeFantasia;
    @Column(name = "endereco", unique = false, updatable = false, length = 200)
    private String endereco;
    @Column(name = "telefone", unique = false, updatable = false, length = 14)
    private String telefone;
    @Column(name = "email", unique = false, updatable = false, length = 50)
    private String email;
    @Column(name = "cnpj", unique = false, updatable = false, length = 14)
    private String cnpj;


    public Fornecedor() {
        return;
    }

    public static Fornecedor of(Fornecedor fornecedor) {
        return fornecedor;
    }

    public String getTelefone() {return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCnpj() { return cnpj; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public Long getId() {return id; }

    public String getRazao() { return razao; }

    public void setRazao(String razao) { this.razao = razao; }

    public String getNomeFantasia() { return nomeFantasia; }

    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEmail() {return email; }

    public void setEmail(String email) { this.email = email; }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", razaoSocial='" + razao + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}