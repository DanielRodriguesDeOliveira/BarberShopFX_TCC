package barbershopfx.db.entidade;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author daniel
 */
public class Misto {
    protected ObjectProperty<Produto> produto = new SimpleObjectProperty<>();
    protected ObjectProperty<Produto_Orcamento> produto_orcamento = new SimpleObjectProperty<>();
    protected ObjectProperty<Orcamento> orcamento = new SimpleObjectProperty<>();
    
    public Misto(Produto produto, Produto_Orcamento produto_orcamento, Orcamento orcamento) {
        this.produto.set(produto);
        this.produto_orcamento.set(produto_orcamento);
        this.orcamento.set(orcamento); 
    }
    
    public Produto getProduto() {
        return produto.get();
    }

    public Orcamento getOrcamento() {
        return orcamento.get();
    }

    public Produto_Orcamento getProduto_Orcamento() {
        return produto_orcamento.get();
    }
    
    public ObjectProperty<Produto> produtoProperty() {
        return produto;
    }
    
    public ObjectProperty<Orcamento> orcamentoProperty() {
        return orcamento;
    }
    public ObjectProperty<Produto_Orcamento> produto_orcamentoProperty() {
        return produto_orcamento;
    }
    
    public void setProduto(Produto produto) {
        this.produto.set(produto);
    }
    
    public void setOrcamento(Orcamento orcamento) {
        this.orcamento.set(orcamento);
    }
    
    public void setProduto_Orcamento(Produto_Orcamento produto_orcamento) {
        this.produto_orcamento.set(produto_orcamento);
    }

    /*protected Produto produto;
    protected Produto_Orcamento produto_orcamento;
    protected Orcamento orcamento;
    
    public Misto(Produto produto, Produto_Orcamento produto_orcamento, Orcamento orcamento) {
        this.produto = produto;
        this.produto_orcamento = produto_orcamento;
        this.orcamento = orcamento;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto_Orcamento getProduto_orcamento() {
        return produto_orcamento;
    }

    public void setProduto_orcamento(Produto_Orcamento produto_orcamento) {
        this.produto_orcamento = produto_orcamento;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }*/
}
