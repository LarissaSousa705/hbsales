package br.com.hbsis.funcionario;
//tráfego de informaçoes

public class FuncionarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String uuidApi;

    public FuncionarioDTO(Long id, String nome, String email, String uuidApi) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.uuidApi = uuidApi;
    }

    public static FuncionarioDTO of(Funcionario funcionario){
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getEmail(),
                funcionario.getUuidApi()
        );
    }

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
        return "FuncionarioDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", uiidApi='" + uuidApi + '\'' +
                '}';
    }
}
