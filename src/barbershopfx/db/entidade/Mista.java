package barbershopfx.db.entidade;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author daniel
 */
public class Mista {
    protected ObjectProperty<Produto> produto = new SimpleObjectProperty<>();
    protected ObjectProperty<Produto_Venda> produto_venda = new SimpleObjectProperty<>();
    protected ObjectProperty<Venda> venda = new SimpleObjectProperty<>();
    
    public Mista(Produto produto, Produto_Venda produto_venda, Venda venda) {
        this.produto.set(produto);
        this.produto_venda.set(produto_venda);
        this.venda.set(venda); 
    }
    
    public Produto getProduto() {
        return produto.get();
    }

    public Venda getVenda() {
        return venda.get();
    }

    public Produto_Venda getProduto_Venda() {
        return produto_venda.get();
    }
    
    public ObjectProperty<Produto> produtoProperty() {
        return produto;
    }
    
    public ObjectProperty<Venda> vendaProperty() {
        return venda;
    }
    public ObjectProperty<Produto_Venda> produto_vendaProperty() {
        return produto_venda;
    }
    
    public void setProduto(Produto produto) {
        this.produto.set(produto);
    }
    
    public void setVenda(Venda venda) {
        this.venda.set(venda);
    }
    
    public void setProduto_Venda(Produto_Venda produto_venda) {
        this.produto_venda.set(produto_venda);
    }

    /*protected Produto produto;
    protected Produto_Venda produto_venda;
    protected Venda venda;
    
    public Mista(Produto produto, Produto_Venda produto_venda, Venda venda) {
        this.produto = produto;
        this.produto_venda = produto_venda;
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto_Venda getProduto_venda() {
        return produto_venda;
    }

    public void setProduto_venda(Produto_Venda produto_venda) {
        this.produto_venda = produto_venda;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }*/
}
