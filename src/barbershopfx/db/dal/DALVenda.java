package barbershopfx.db.dal;

import barbershopfx.db.entidade.Venda;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */

public class DALVenda {
   int codProd;
    public boolean gravar(Venda v)
    {
        String sql="insert into venda (id, data, valor_venda, cliente_id, funcionario_id) values ('#0', '#1', '#2', '#3', '#4')";
        sql=sql.replace("#0",""+(Banco.getCon().getMaxPK("venda", "id")+1));
        sql=sql.replace("#1", ""+v.getData());
        sql=sql.replace("#2", ""+v.getValor());
        sql=sql.replace("#3", ""+v.getCliente().getId());
        sql=sql.replace("#4", ""+v.getFuncionario().getId());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean gravarItens(Venda v)
    {
        boolean retorno = false;
        try{
        String sql = "insert into produto_venda (produto_id, venda_id, quantidade_produto) values ('#0', '#1', '#2')";
        String sql2;
        sql=sql.replace("#0", ""+v.getProduto().getId());
        sql=sql.replace("#1", ""+v.getId());
        sql=sql.replace("#2", ""+v.getQuantidade());
        retorno = Banco.getCon().manipular(sql);
        //Baixa de estoque
            int quant = 0, resul=0;
            ResultSet rs = Banco.getCon().consultarPS("select * from produto where id="+v.getProduto().getId());
            rs.first();
            
            quant = rs.getInt("quantidade");
            resul = quant-v.getQuantidade();
  
            sql2 = "update produto set quantidade='#1' where id='#2'";
            sql2=sql2.replace("#1", ""+resul);
            sql2=sql2.replace("#2", String.valueOf(v.getProduto().getId()));
            Banco.getCon().manipular(sql2);
            System.out.println("Produto adicionado!");
        } catch (Exception e){System.out.println("Erro ao adicionar produto"+e.getMessage());}
        return retorno;
    }
    
    public boolean alterar(Venda v)
    {
        String sql="update venda set data='#1', valor_venda=#2, cliente_id=#3, funcionario_id=#4 where id="+v.getId();
        sql=sql.replace("#1", ""+v.getData());
        sql=sql.replace("#2", ""+v.getValor());
        sql=sql.replace("#3", ""+v.getCliente().getId());
        sql=sql.replace("#4", ""+v.getFuncionario().getId());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Venda v)
    {
        return Banco.getCon().manipular("delete from venda where id="+v.getId());
    }
    
    public Venda get(int id)
    {
        Venda v=null;
        DALCliente dalc=new DALCliente();
        DALFuncionario dalf=new DALFuncionario();
        ResultSet rs = Banco.getCon().consultar("select * from venda where id="+id);
        try
        {
          if(rs.next())
          {
            v=new Venda(rs.getInt("id"),rs.getDate("data").toLocalDate(),rs.getFloat("valor_venda"),dalc.get(rs.getInt("cliente_id")),dalf.get(rs.getInt("funcionario_id")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - V1");}
        return v;        
    }
    
    public List<Venda> get(String filtro)
    {
        List<Venda> list=new ArrayList();
        String sql="select * from venda";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        DALCliente dalc=new DALCliente();
        DALFuncionario dalf=new DALFuncionario();
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Venda(rs.getInt("id"),rs.getDate("data").toLocalDate(),rs.getFloat("valor_venda"),dalc.get(rs.getInt("cliente_id")),dalf.get(rs.getInt("funcionario_id"))));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - V2");}
        return list;        
    }  
    
    public ArrayList getItens(String filtro)
    {
        ArrayList dados = new ArrayList();
        String sql="select * from produto inner join produto_venda on produto.id=produto_venda.produto_id inner join venda on venda.id=produto_venda.venda_id where venda.id="+filtro;
        
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            dados.add(new Object[]{rs.getString("nome"),rs.getString("quantidade"),rs.getString("valor")});
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - V3");}
        return dados;        
    }  
    
    public boolean CancelaVenda(){
       boolean condicao = false;
       ResultSet rs = Banco.getCon().consultarPS("select * from venda inner join produto_venda on venda.id = produto_venda.venda_id "
               +"inner join produto on produto_venda.produto_id=produto.id where valor_venda=0");
       try{
           rs.first();
           do{
               int qtdProd = rs.getInt("quantidade");
               int qtdVenda = rs.getInt("quantidade_produto");
               int soma = qtdProd+qtdVenda;
               System.out.println("Soma: "+soma); 
               String sql = "update produto set quantidade='#1' where id='#2'";
               sql=sql.replace("#1", ""+soma);
               sql=sql.replace("#2", ""+rs.getInt("produto_id"));
               System.out.println("Sql: "+sql); 
               Banco.getCon().manipular(sql);
               Banco.getCon().manipular("delete from produto_venda where venda_id="+rs.getInt("venda_id"));
           }while(rs.next());
           
           condicao = Banco.getCon().manipular("delete from venda where valor_venda="+0);
           System.out.println("Dados venda cancelados com sucesso!"); 
       }catch (Exception e) {
            System.out.println("Erro ao cancelar a venda!\n ERRO: "+e);
        }
       return condicao;
    }
    
    public void achaCodProduto(String nome){
       ResultSet rs = Banco.getCon().consultar("select * from produto where nome_produto='"+nome+"'");
        try {
            rs.first();
            codProd = rs.getInt("produto_id");
        } catch (Exception e) {
            System.out.println("Erro ao encontrar produto!\n ERRO: "+e);
        }
    }
}