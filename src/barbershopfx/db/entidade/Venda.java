package barbershopfx.db.entidade;

import java.time.LocalDate;

/**
 *
 * @author daniel
 */

public class Venda 
{
  protected int id;
  protected LocalDate data;
  protected float valor;
  protected Cliente cliente;
  protected Funcionario funcionario;
  protected Produto produto;
  protected int quantidade;

    public Venda() {
    }

    public Venda(int id) {
        this.id = id;
        this.data = LocalDate.now();
        this.valor = 0;
        this.cliente = new Cliente();
        this.funcionario = new Funcionario();
    }

    
    public Venda(int id, LocalDate data, float valor, Cliente cliente, Funcionario funcionario) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
 
    public void setProduto_id(Produto produto) {
        this.produto.id = produto.id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
