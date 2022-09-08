package barbershopfx.db.entidade;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author daniel
 */

public class Produto 
{
    protected int id;
    protected String descricao;
    protected float valor;
    protected int quantidade;
    protected Categoria categoria;

    public Produto() 
    {
    }

    public Produto(String descricao, float valor, int quantidade, Categoria categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public Produto(int id, String descricao, float valor, int quantidade, Categoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() 
    {
        return descricao.toUpperCase();
    }
    
    /*protected IntegerProperty id = new SimpleIntegerProperty();
    protected StringProperty descricao = new SimpleStringProperty();
    protected StringProperty valor = new SimpleStringProperty();
    protected StringProperty quantidade = new SimpleStringProperty();
    protected Categoria categoria;
    
    public Produto(String descricao, String valor, String quantidade, Categoria categoria) {
        this.descricao.set(descricao);
        this.valor.set(valor);
        this.quantidade.set(quantidade);
        this.categoria = categoria;
    }
    
    public Produto(int id, String descricao, String valor, String quantidade, Categoria categoria) {
        this.id.setValue(id);
        this.descricao.set(descricao);
        this.valor.set(valor);
        this.quantidade.set(quantidade);
        this.categoria = categoria;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty IdProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty nameProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }
    
     public String getValor() {
        return valor.get();
    }

    public StringProperty valorProperty() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor.set(valor);
    }

    public String getQuantidade() {
        return quantidade.get();
    }
    
    public StringProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade.set(quantidade);
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }*/
}
