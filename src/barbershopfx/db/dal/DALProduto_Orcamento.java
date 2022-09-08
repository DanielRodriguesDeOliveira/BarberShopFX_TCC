package barbershopfx.db.dal;

import barbershopfx.db.entidade.Produto;
import barbershopfx.db.entidade.Produto_Orcamento;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class DALProduto_Orcamento {
    public boolean gravar(Produto_Orcamento po)
    {
        String sql="insert into produto_orcamento (produto_id, orcamento_id, cpf, quantidade_produto) values ('#0','#1','#2')";
        sql=sql.replace("#0", ""+po.getProduto().getId());
        sql=sql.replace("#1", ""+po.getOrcamento().getId());
        sql=sql.replace("#2", ""+po.getQuantidade_produto());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Produto_Orcamento po)
    {
        String sql="update produto_orcamento set orcamento_id='#1', quantidade_produto='#3' where produto_id="+po.getProduto();
        sql=sql.replace("#1", ""+po.getOrcamento().getId());
        sql=sql.replace("#2", ""+po.getQuantidade_produto());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Produto_Orcamento po)
    {
        return Banco.getCon().manipular("delete from produto_orcamento where id="+po.getProduto());
    }
    
    
    public Produto_Orcamento get(int id)
    {
        Produto_Orcamento po=null;
        DALProduto dalp=new DALProduto();
        DALOrcamento dalo=new DALOrcamento();
        ResultSet rs = Banco.getCon().consultar("select * from produto_orcamento where produto_id="+id);
        try
        {
          if(rs.next())
          {
            po=new Produto_Orcamento(dalp.get(rs.getInt("produto_id")),dalo.get(rs.getInt("venda_id")),rs.getInt("quantidade_produto"));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - PO1");}
        return po;        
    }
    public List<Produto_Orcamento> get(String filtro)
    {
        List<Produto_Orcamento> list=new ArrayList();
        DALProduto dalp=new DALProduto();
        DALOrcamento dalo=new DALOrcamento();
        String sql="select * from produto_orcamento";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
            Produto p = dalp.get(rs.getInt("produto_id"));
            list.add(new Produto_Orcamento(p,dalo.get(rs.getInt("orcamento_id")),rs.getInt("quantidade_produto"), p.getValor() * rs.getInt("quantidade_produto")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - PO2");}
        return list;        
    }
}
