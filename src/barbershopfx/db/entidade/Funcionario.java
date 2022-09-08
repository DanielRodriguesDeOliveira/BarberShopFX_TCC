package barbershopfx.db.entidade;

/**
 *
 * @author daniel
 */

public class Funcionario 
{
    protected  int id;
    protected  String nome;
    protected  String cpf;
    protected  Usuario usuario;

    public Funcionario() 
    {
        this(1,"","",new Usuario());
    }

    public Funcionario(int id, String nome, String cpf, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() 
    {
        return nome.toUpperCase();
    }
}
