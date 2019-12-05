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
    @Column(name = "razao_social", unique = false, nullable = false, length = 200)
    private String razaoSocial;
    @Column(name = "nome_fantasia", unique = false, length = 50)
    private String nomeFantasia;
    @Column(name = "endereco", unique = false, updatable = false, length = 200)
    private String endereco;
    @Column(name = "telefone", unique = false, updatable = false, length = 11)
    private String telefone;
    @Column(name = "email", unique = false, updatable = false, length = 200)
    private String email;
    @Column(name = "cnpj", unique = false, updatable = false, length = 11)
    private String cnpj;


    public Fornecedor() {
        return;
    }

    public String getTelefone() {return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCnpj() { return cnpj; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public Long getId() {return id; }

    public String getRazaoSocial() { return razaoSocial; }

    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

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
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}