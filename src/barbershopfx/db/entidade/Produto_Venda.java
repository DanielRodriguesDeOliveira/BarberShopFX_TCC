package barbershopfx.db.entidade;

/**
 *
 * @author daniel
 */
public class Produto_Venda {
    protected Produto produto;
    protected Venda venda;
    protected int quantidade_produto;
    protected float valor;
    

    public Produto_Venda() {
    }
    
    public Produto_Venda(Produto produto, Venda venda, int quantidade_produto) {
        this.produto = produto;
        this.venda = venda;
        this.quantidade_produto = quantidade_produto;
    }
    
    public Produto_Venda(Produto produto, Venda venda, int quantidade_produto, float valor) {
        this.produto = produto;
        this.venda = venda;
        this.quantidade_produto = quantidade_produto;
        this.valor = valor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public int getQuantidade_produto() {
        return quantidade_produto;
    }

    public void setQuantidade_produto(int quantidade_produto) {
        this.quantidade_produto = quantidade_produto;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }  
}
