package barbershopfx.db.dal;

import barbershopfx.db.entidade.Usuario;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */

public class DALUsuario {
    
  public boolean gravar(Usuario u)
    {
        String sql="insert into usuario (id, email, senha, funcao, criado, modificado) values ('#0', '#1', '#2', '#3', '#4', '#5')";
        sql=sql.replace("#0",""+(Banco.getCon().getMaxPK("usuario", "id")+1));
        sql=sql.replace("#1",u.getEmail());
        sql=sql.replace("#2",u.getSenha());
        sql=sql.replace("#3",u.getFuncao());
        sql=sql.replace("#4", ""+u.getCriado());
        sql=sql.replace("#5", ""+u.getModificado());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Usuario u)
    {
        String sql="update usuario set email='#1', senha='#2', funcao='#3', criado='#4', modificado='#5' where id="+u.getId();
        sql=sql.replace("#1",u.getEmail());
        sql=sql.replace("#2",u.getSenha());
        sql=sql.replace("#3",u.getFuncao());
        sql=sql.replace("#4", ""+u.getCriado());
        sql=sql.replace("#5", ""+u.getModificado());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Usuario u)
    {
        return Banco.getCon().manipular("delete from usuario where id="+u.getId());
    }
    public Usuario get(int id)
    {
        Usuario u=null;
        ResultSet rs = Banco.getCon().consultar("select * from usuario where id="+id);
        try
        {
          if(rs.next())
          {
            u=new Usuario(rs.getInt("id"),rs.getString("email"),rs.getString("senha"),rs.getString("funcao"),rs.getDate("criado").toLocalDate(),rs.getDate("modificado").toLocalDate());
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return u;        
    }
    public List<Usuario> get(String filtro)
    {
        List<Usuario> list=new ArrayList();
        String sql="select * from usuario ";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        sql+=" order by id";          
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Usuario(rs.getInt("id"),rs.getString("email"),rs.getString("senha"),rs.getString("funcao"),rs.getDate("criado").toLocalDate(),rs.getDate("modificado").toLocalDate()));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return list;        
    } 
    
    public Usuario get(String usuario, String senha)
    {
        Usuario u = null;
        String sql="select * from usuario where email='"+usuario+"' and senha='"+senha+"'";
             
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            u = new Usuario(rs.getInt("id"),rs.getString("email"),rs.getString("senha"),rs.getString("funcao"),rs.getDate("criado").toLocalDate(),rs.getDate("modificado").toLocalDate());
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return u;        
    } 
}
