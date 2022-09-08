package barbershopfx.db.entidade;

/**
 *
 * @author daniel
 */
public class Produto_Orcamento {
    protected Produto produto;
    protected Orcamento orcamento;
    protected int quantidade_produto;
    protected float valor;
    

    public Produto_Orcamento() {
    }
    
    public Produto_Orcamento(Produto produto, Orcamento orcamento, int quantidade_produto) {
        this.produto = produto;
        this.orcamento = orcamento;
        this.quantidade_produto = quantidade_produto;
    }
    
    public Produto_Orcamento(Produto produto, Orcamento orcamento, int quantidade_produto, float valor) {
        this.produto = produto;
        this.orcamento = orcamento;
        this.quantidade_produto = quantidade_produto;
        this.valor = valor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setVenda(Orcamento orcamento) {
        this.orcamento = orcamento;
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
