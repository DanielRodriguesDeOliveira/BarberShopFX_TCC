package barbershopfx.db.dal;

import barbershopfx.db.entidade.Funcionario;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */

public class DALFuncionario {
    
    public boolean gravar(Funcionario f)
    {
        String sql="insert into funcionario (id, nome, cpf, usuario_id) values ('#0','#1','#2','#3')";
        sql=sql.replace("#0", ""+(Banco.getCon().getMaxPK("funcionario", "id")+1));
        sql=sql.replace("#1",f.getNome());
        sql=sql.replace("#2",f.getCpf());
        sql=sql.replace("#3", ""+f.getUsuario().getId());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Funcionario f)
    {
        String sql="update funcionario set nome='#1', cpf='#2', usuario_id='#3' where id="+f.getId();
        sql=sql.replace("#1",f.getNome());
        sql=sql.replace("#2",f.getCpf());
        sql=sql.replace("#3", ""+f.getUsuario().getId());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Funcionario f)
    {
        return Banco.getCon().manipular("delete from funcionario where id="+f.getId());
    }
    public Funcionario get(int id)
    {
        Funcionario f=null;
        DALUsuario dale=new DALUsuario();
        ResultSet rs = Banco.getCon().consultar("select * from funcionario where id="+id);
        try
        {
          if(rs.next())
          {
            f=new Funcionario(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf"),dale.get(rs.getInt("usuario_id")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - F1");}
        return f;        
    }
    public List<Funcionario> get(String filtro)
    {
        List<Funcionario> list=new ArrayList();
        DALUsuario dale=new DALUsuario();
        String sql="select * from funcionario";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Funcionario(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf"),dale.get(rs.getInt("usuario_id"))));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - F2");}
        return list;        
    }
}