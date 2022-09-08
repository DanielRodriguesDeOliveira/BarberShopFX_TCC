package barbershopfx.db.dal;

import barbershopfx.db.entidade.Cliente;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */

public class DALCliente 
{
    public boolean gravar(Cliente c)
    {   String sql="insert into cliente (id, nome, cpf, cep, endereco, numero, bairro, cidade, complemento, fone, dtnasc)values('#0','#1','#2','#3','#4','#5','#6','#7','#8','#9','#$')";
        sql=sql.replace("#0",""+(Banco.getCon().getMaxPK("cliente", "id")+1));
        sql=sql.replace("#1",c.getNome());
        sql=sql.replace("#2",c.getCpf());
        sql=sql.replace("#3",c.getCep());
        sql=sql.replace("#4",c.getEndereco());
        sql=sql.replace("#5",""+c.getNumero());
        sql=sql.replace("#6",c.getBairro());
        sql=sql.replace("#7",c.getCidade());
        sql=sql.replace("#8",c.getComplemento());
        sql=sql.replace("#9",c.getFone());
        sql=sql.replace("#$",""+c.getDtnasc());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean alterar(Cliente c)
    {
        String sql="update cliente set nome='#1', cpf='#2', cep='#3', endereco='#4', numero='#5', bairro='#6', cidade='#7', complemento='#8', fone='#9', dtnasc='#$' where id="+c.getId();
        sql=sql.replace("#1",c.getNome());
        sql=sql.replace("#2",c.getCpf());
        sql=sql.replace("#3",c.getCep());
        sql=sql.replace("#4",c.getEndereco());
        sql=sql.replace("#5",""+c.getNumero());
        sql=sql.replace("#6",c.getBairro());
        sql=sql.replace("#7",c.getCidade());
        sql=sql.replace("#8",c.getComplemento());
        sql=sql.replace("#9",c.getFone());
        sql=sql.replace("#$",""+c.getDtnasc());
        return Banco.getCon().manipular(sql);
    }
    
    public boolean apagar(Cliente c)
    {
        return Banco.getCon().manipular("delete from cliente where id="+c.getId());
    }
    
    public Cliente get(int id)
    {
        Cliente c=null;
        ResultSet rs = Banco.getCon().consultar("select * from cliente where id="+id);
        try
        {
          if(rs.next())
          {
            c=new Cliente(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf"),rs.getString("cep"),rs.getString("endereco"),rs.getInt("numero"),rs.getString("bairro"),rs.getString("cidade"),rs.getString("complemento"),rs.getString("fone"),rs.getDate("dtnasc").toLocalDate());
          }    
        }catch(Exception ex){System.out.println("erro ao pesquisar");}
        return c;        
    }
    
     public List<Cliente> get(String filtro)
    {
        List<Cliente> list=new ArrayList();
        String sql="select * from cliente ";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        sql+=" order by id";          
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Cliente(rs.getInt("id"),rs.getString("nome"),rs.getString("cpf"),rs.getString("cep"),rs.getString("endereco"),rs.getInt("numero"),rs.getString("bairro"),rs.getString("cidade"),rs.getString("complemento"),rs.getString("fone"),rs.getDate("dtnasc").toLocalDate()));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar");}
        return list;        
    }
}