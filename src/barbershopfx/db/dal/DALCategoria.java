package barbershopfx.db.dal;

import barbershopfx.db.entidade.Categoria;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */

public class DALCategoria 
{
   public boolean gravar(Categoria c)
    {
        String sql="insert into categoria (id, nome) values ('#0','#1')";
        sql=sql.replace("#0", ""+(Banco.getCon().getMaxPK("categoria", "id")+1));
        sql=sql.replace("#1", ""+c.getNome());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Categoria c)
    {
        String sql="update categoria set nome='#1' where id="+c.getId();
        sql=sql.replace("#1",c.getNome());
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Categoria c)
    {
        return Banco.getCon().manipular("delete from categoria where id="+c.getId());
    }
    public Categoria get(int id)
    {
        Categoria c=null;
        ResultSet rs = Banco.getCon().consultar("select * from categoria where id="+id);
        try
        {
          if(rs.next())
          {
            c=new Categoria(rs.getInt("id"),rs.getString("nome"));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return c;        
    }
    public List<Categoria> get(String filtro)
    {
        List<Categoria> list=new ArrayList();
        String sql="select * from categoria";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Categoria(rs.getInt("id"),rs.getString("nome")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return list;        
    }
}