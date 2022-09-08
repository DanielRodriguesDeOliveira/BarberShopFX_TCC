package barbershopfx.db.dal;

import barbershopfx.db.entidade.Orcamento;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class DALOrcamento {
   int codProd;
    public boolean gravar(Orcamento o)
    {
        String sql="insert into orcamento (id, data, valor_orcamento, cliente_id, funcionario_id) values ('#0', '#1', '#2', '#3', '#4')";
        sql=sql.replace("#0",""+(Banco.getCon().getMaxPK("orcamento", "id")+1));
        sql=sql.replace("#1", ""+o.getData());
        sql=sql.replace("#2", ""+o.getValor());
        sql=sql.replace("#3", ""+o.getCliente().getId());
        sql=sql.replace("#4", ""+o.getFuncionario().getId());
        
        return Banco.getCon().manipular(sql);
    }
    
    public boolean gravarItens(Orcamento o)
    {
        boolean retorno = false;
        try{
        String sql = "insert into produto_orcamento(produto_id, orcamento_id, quantidade_produto) values ('#0', '#1', '#2')";
        String sql2;
        sql=sql.replace("#0", ""+o.getProduto().getId());
        sql=sql.replace("#1", ""+o.getId());
        sql=sql.replace("#2", ""+o.getQuantidade());
        retorno = Banco.getCon().manipular(sql);
        /*Baixa de estoque
            int quant = 0, resul=0;
            ResultSet rs = Banco.getCon().consultarPS("select * from produto where id="+o.getProduto().getId());
            rs.first();
            
            quant = rs.getInt("quantidade");
            resul = quant-o.getQuantidade();
  
            sql2 = "update produto set quantidade='#1' where id='#2'";
            sql2=sql2.replace("#1", ""+resul);
            sql2=sql2.replace("#2", String.valueOf(o.getProduto().getId()));
            Banco.getCon().manipular(sql2);*/
            System.out.println("Produto adicionado!");
        } catch (Exception e){System.out.println("Erro ao adicionar produto"+e.getMessage());}
        return retorno;
    }
    
    public boolean alterar(Orcamento o)
    {
        String sql="update orcamento set data='#1', valor_orcamento=#2, cliente_id=#3, funcionario_id=#4 where id="+o.getId();
        sql=sql.replace("#1", ""+o.getData());
        sql=sql.replace("#2", ""+o.getValor());
        sql=sql.replace("#3", ""+o.getCliente().getId());
        sql=sql.replace("#4", ""+o.getFuncionario().getId());
        
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Orcamento o)
    {
        return Banco.getCon().manipular("delete from orcamento where id="+o.getId());
    }
    
    public Orcamento get(int id)
    {
        Orcamento o=null;
        DALCliente dalc=new DALCliente();
        DALFuncionario dalf=new DALFuncionario();
        ResultSet rs = Banco.getCon().consultar("select * from orcamento where id="+id);
        try
        {
          if(rs.next())
          {
            o=new Orcamento(rs.getInt("id"),rs.getDate("data").toLocalDate(),rs.getFloat("valor_orcamento"),dalc.get(rs.getInt("cliente_id")),dalf.get(rs.getInt("funcionario_id")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - V1");}
        return o;        
    }
    
    public List<Orcamento> get(String filtro)
    {
        List<Orcamento> list=new ArrayList();
        String sql="select * from orcamento";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        DALCliente dalc=new DALCliente();
        DALFuncionario dalf=new DALFuncionario();
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            list.add(new Orcamento(rs.getInt("id"),rs.getDate("data").toLocalDate(),rs.getFloat("valor_orcamento"),dalc.get(rs.getInt("cliente_id")),dalf.get(rs.getInt("funcionario_id"))));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - V2");}
        return list;        
    }  
    
    public ArrayList getItens(String filtro)
    {
        ArrayList dados = new ArrayList();
        String sql="select * from produto inner join produto_orcamento on produto.id=produto_orcamento.produto_id inner join orcamento on orcamento.id=produto_orcamento.orcamento_id where orcamento.id="+filtro;
        
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
    
    public boolean CancelaOrcamento(){
       boolean condicao = false;
       ResultSet rs = Banco.getCon().consultarPS("select * from orcamento inner join produto_orcamento on orcamento.id = produto_orcamento.orcamento_id "
               +"inner join produto on produto_orcamento.produto_id=produto.id where valor_orcamento=0");
       try{
           rs.first();
           do{
               /*int qtdProd = rs.getInt("quantidade");
               int qtdVenda = rs.getInt("quantidade_produto");
               int soma = qtdProd+qtdVenda;
               System.out.println("Soma: "+soma); 
               String sql = "update produto set quantidade='#1' where id='#2'";
               sql=sql.replace("#1", ""+soma);
               sql=sql.replace("#2", ""+rs.getInt("produto_id"));
               System.out.println("Sql: "+sql); 
               Banco.getCon().manipular(sql);*/
               Banco.getCon().manipular("delete from produto_orcamento where orcamento_id="+rs.getInt("orcamento_id"));
           }while(rs.next());
           
           condicao = Banco.getCon().manipular("delete from orcamento where valor_orcamento="+0);
           System.out.println("Dados de orçamento cancelados com sucesso!"); 
       }catch (Exception e) {
            System.out.println("Erro ao cancelar a orçamento!\n ERRO: "+e);
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
