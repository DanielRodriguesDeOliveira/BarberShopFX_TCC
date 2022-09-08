package barbershopfx.db.entidade;

import java.time.LocalDate;

/**
 *
 * @author daniel
 */

public class Usuario 
{
    protected int id;
    protected String email;
    protected String senha;
    protected String funcao;
    protected LocalDate criado;
    protected LocalDate modificado;

    public Usuario() 
    {
        this(0, "","","",LocalDate.now(),LocalDate.now());
    }

    public Usuario(String email, String senha, String funcao, LocalDate criado, LocalDate modificado) {
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
        this.criado = criado;
        this.modificado = modificado;
    }
    
    

    public Usuario(int id, String email, String senha, String funcao, LocalDate criado, LocalDate modificado) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
        this.criado = criado;
        this.modificado = modificado;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public LocalDate getCriado() {
        return criado;
    }

    public void setCriado(LocalDate criado) {
        this.criado = criado;
    }

    public LocalDate getModificado() {
        return modificado;
    }

    public void setModificado(LocalDate modificado) {
        this.modificado = modificado;
    }
    
    @Override
    public String toString() 
    {
        return email.toUpperCase();
    }
}