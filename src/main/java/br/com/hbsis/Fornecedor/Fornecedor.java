package br.com.hbsis.Fornecedor;

import br.com.hbsis.Produtos.Produtos;

import javax.persistence.*;
import java.util.List;

/**
 * Classe responsável pelo mapeamento da entidade do banco de dados
 */
@Entity
@Table(name = "seg_fornecedor")
public
class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razao_social", unique = false, nullable = false, length = 200)
    private String razao_social;
    @Column(name = "nome_fantasia", unique = false, length = 50)
    private String nome_fantasia;
    @Column(name = "endereco", unique = false, updatable = false, length = 200)
    private String endereco;
    @Column(name = "telefone", unique = false, updatable = false, length = 11)
    private String telefone;
    @Column(name = "email", unique = false, updatable = false, length = 200)
    private String email;
    @Column(name = "cnpj", unique = false, updatable = false, length = 11)
    private String cnpj;


    public String getTelefone() {return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCnpj() { return cnpj; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public Long getId() {return id; }

    public String getRazao_social() { return razao_social; }

    public void setRazao_social(String razao_social) { this.razao_social = razao_social; }

    public String getNome_fantasia() { return nome_fantasia; }

    public void setNome_fantasia(String nome_fantasia) { this.nome_fantasia = nome_fantasia; }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getEmail() {return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "br.com.hbsis.Fornecedor{" +
                "id=" + id +
                ", razao='" + razao_social + '\'' +
                ", nomeFantasia='" + nome_fantasia + '\'' +
                ", endereco='" + endereco + '\'' +
                ", teleofone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}