package barbershopfx.db.entidade;

import java.time.LocalDate;

/**
 *
 * @author daniel
 */

public class Cliente 
{
    protected int id;
    protected String nome;
    protected String cpf;
    protected String cep;
    protected String endereco;
    protected int numero;
    protected String bairro;
    protected String cidade;
    protected String Complemento;
    protected String fone;
    protected LocalDate dtnasc;

    public Cliente() 
    {
        this(1,"","","","",1,"","","","",LocalDate.now());
    }

    public Cliente(String nome, String cpf, String cep, String endereco, int numero, String bairro, String cidade, String Complemento, String fone, LocalDate dtnasc) {
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.Complemento = Complemento;
        this.fone = fone;
        this.dtnasc = dtnasc;
    }
    
    public Cliente(int id, String nome, String cpf, String cep, String endereco, int numero, String bairro, String cidade, String Complemento, String fone, LocalDate dtnasc) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.Complemento = Complemento;
        this.fone = fone;
        this.dtnasc = dtnasc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String Complemento) {
        this.Complemento = Complemento;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public LocalDate getDtnasc() {
        return dtnasc;
    }

    public void setDtnasc(LocalDate dtnasc) {
        this.dtnasc = dtnasc;
    }
    
    @Override
    public String toString() 
    {
        return nome.toUpperCase();
    }
}
