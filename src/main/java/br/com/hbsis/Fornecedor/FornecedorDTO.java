package br.com.hbsis.Fornecedor;


public class FornecedorDTO {
    private Long id;
    private String razao_social;
    private String nome_fantasia;
    private String endereco;
    private String telefone;
    private String email;
    private String cnpj;

    public void setId(Long id) {
        this.id = id;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long id, String razao_social, String cnpj, String nome_fantasia, String endereco, String telefone, String email) {
        this.razao_social = razao_social;
        this.id = id;
        this.cnpj = cnpj;
        this.nome_fantasia = nome_fantasia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getRazao_social() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FornecedorDTO(String razao_social, String cnpj, String nome_fantasia, String endereco, String telefone, String email) {
    }


    public static FornecedorDTO of(Fornecedor Fornecedor) {
        return new FornecedorDTO(
                Fornecedor.getId(),
                Fornecedor.getRazao_social(),
                Fornecedor.getCnpj(),
                Fornecedor.getNome_fantasia(),
                Fornecedor.getEndereco(),
                Fornecedor.getTelefone(),
                Fornecedor.getEmail()
        );
    }

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