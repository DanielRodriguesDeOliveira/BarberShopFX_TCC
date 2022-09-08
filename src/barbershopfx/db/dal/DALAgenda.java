package barbershopfx.db.dal;

import barbershopfx.db.entidade.Agenda;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */

public class DALAgenda {
 
  public boolean gravar(Agenda a)
    {
        String sql="insert into agenda (id, data, horario, funcionario_id, cliente_id) values ('#0','#1','#2','#3','#4')";
        sql=sql.replace("#0",""+(Banco.getCon().getMaxPK("agenda", "id")+1));
        sql=sql.replace("#1", ""+a.getData());
        sql=sql.replace("#2", ""+a.getHorario());
        sql=sql.replace("#3", ""+a.getFuncionario().getId());
        sql=sql.replace("#4", ""+a.getCliente().getId());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Agenda a)
    {
        String sql="update agenda set data='#1', horario='#2', funcionario_id='#3', cliente_id='#4' where id="+a.getId();
        sql=sql.replace("#1", ""+a.getData());
        sql=sql.replace("#2", ""+a.getHorario());
        sql=sql.replace("#3", ""+a.getFuncionario().getId());
        sql=sql.replace("#4", ""+a.getCliente().getId());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Agenda a)
    {
        return Banco.getCon().manipular("delete from agenda where id="+a.getId());
    }
    public Agenda get(int id)
    {
        Agenda a=null;
        DALFuncionario dale=new DALFuncionario();
        DALCliente dale2=new DALCliente();
        ResultSet rs = Banco.getCon().consultar("select * from agenda where id="+id);
        try
        {
          if(rs.next())
          {
            a=new Agenda(rs.getInt("id"),rs.getDate("data").toLocalDate(),rs.getTime("horario").toLocalTime(),dale.get(rs.getInt("funcionario_id")),dale2.get(rs.getInt("cliente_id")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return a;        
    }
    public List<Agenda> get(String filtro)
    {
        List<Agenda> list=new ArrayList();
        DALFuncionario dale=new DALFuncionario();
        DALCliente dale2=new DALCliente();
        String sql="select * from agenda";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Agenda(rs.getInt("id"),rs.getDate("data").toLocalDate(),rs.getTime("horario").toLocalTime(),dale.get(rs.getInt("funcionario_id")),dale2.get(rs.getInt("cliente_id"))));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return list;        
    }  
    
    public boolean verificar(Agenda a)
    {
        ResultSet rs = Banco.getCon().consultar("select * from agenda where data='"+a.getData()+"' and horario='"+a.getHorario()+"' and funcionario_id="+a.getFuncionario().getId());
        System.out.println("select * from agenda where data="+a.getData()+"and horario="+a.getHorario());
        try   
        {
          if(rs.next())
          {
            System.out.println("Achou");
            return true;
          }   
          System.out.println("deu certo");
            return false; 
        }catch(Exception e){
            System.out.println("erro ao pesquisar"+e.getMessage());
            return false;
        }
    }  
}
