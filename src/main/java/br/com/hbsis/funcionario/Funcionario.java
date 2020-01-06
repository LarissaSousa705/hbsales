package br.com.hbsis.funcionario;

import javax.persistence.*;

//mapeamento da entidade do banco de dados
@Entity
@Table(name = "seg_funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", unique = false, nullable = false,length = 50)
    private String nome;
    @Column(name = "email", unique = false, nullable = false, length = 50)
    private String email;
    @Column(name = "uuid_api", unique = false, nullable = false, length = 36)
    private String uuidApi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuidApi() {
        return uuidApi;
    }

    public void setUuidApi(String uuidApi) {
        this.uuidApi = uuidApi;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", uuidApi='" + uuidApi + '\'' +
                '}';
    }
}
