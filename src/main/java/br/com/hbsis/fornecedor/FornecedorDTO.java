package br.com.hbsis.fornecedor;


public class FornecedorDTO {
    private Long id;
    private String razao;
    private String nomeFantasia;
    private String endereco;
    private String telefone;
    private String email;
    private String cnpj;




    public FornecedorDTO(Long id, String razao, String cnpj, String nomeFantasia, String endereco, String telefone, String email) {
        this.razao = razao;
        this.id = id;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public static FornecedorDTO of(Fornecedor Fornecedor) {
        return new FornecedorDTO(
                Fornecedor.getId(),
                Fornecedor.getRazao(),
                Fornecedor.getCnpj(),
                Fornecedor.getNomeFantasia(),
                Fornecedor.getEndereco(),
                Fornecedor.getTelefone(),
                Fornecedor.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "br.com.hbsis.Fornecedor{" +
                "id=" + id +
                ", razao='" + razao + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", endereco='" + endereco + '\'' +
                ", teleofone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}