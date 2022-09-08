package barbershopfx.db.dal;

import barbershopfx.db.entidade.Produto;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */

public class DALProduto {
    
  public boolean gravar(Produto p)
    {
        String sql="insert into produto (id, descricao, valor, quantidade, categoria_id) values ('#0','#1','#2','#3','#4')";
        sql=sql.replace("#0",""+(Banco.getCon().getMaxPK("produto", "id")+1));
        sql=sql.replace("#1",p.getDescricao());
        sql=sql.replace("#2",""+p.getValor());
        sql=sql.replace("#3",""+p.getQuantidade());
        sql=sql.replace("#4", ""+p.getCategoria().getId());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Produto p)
    {
        String sql="update produto set descricao='#1', valor='#2', quantidade='#3', categoria_id='#4' where id="+p.getId();
        sql=sql.replace("#1",p.getDescricao());
        sql=sql.replace("#2",""+p.getValor());
        sql=sql.replace("#3",""+p.getQuantidade());
        sql=sql.replace("#4", ""+p.getCategoria().getId());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Produto p)
    {
        return Banco.getCon().manipular("delete from produto where id="+p.getId());
    }
    public Produto get(int id)
    {
        Produto p=null;
        DALCategoria dalc=new DALCategoria();
        ResultSet rs = Banco.getCon().consultar("select * from produto where id="+id);
        try
        {
          if(rs.next())
          {
            p=new Produto(rs.getInt("id"),rs.getString("descricao"),rs.getFloat("valor"),rs.getInt("quantidade"),dalc.get(rs.getInt("categoria_id")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - P1");}
        return p;        
    }
    public List<Produto> get(String filtro)
    {
        List<Produto> list=new ArrayList();
        DALCategoria dalc=new DALCategoria();
        String sql="select * from produto";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        sql+=" order by id"; 
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Produto(rs.getInt("id"),rs.getString("descricao"),rs.getFloat("valor"),rs.getInt("quantidade"),dalc.get(rs.getInt("categoria_id"))));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - P2");}
        return list;        
    }  
}
