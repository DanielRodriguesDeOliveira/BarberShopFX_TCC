package barbershopfx.db.dal;

import barbershopfx.db.entidade.Produto;
import barbershopfx.db.entidade.Produto_Venda;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danie
 */
public class DALProduto_Venda {
    public boolean gravar(Produto_Venda pv)
    {
        String sql="insert into produto_venda (produto_id, venda_id, cpf, quantidade_produto) values ('#0','#1','#2')";
        sql=sql.replace("#0", ""+pv.getProduto().getId());
        sql=sql.replace("#1", ""+pv.getVenda().getId());
        sql=sql.replace("#2", ""+pv.getQuantidade_produto());
        return Banco.getCon().manipular(sql);
    }
    public boolean alterar(Produto_Venda pv)
    {
        String sql="update produto_venda set venda_id='#1', quantidade_produto='#3' where produto_id="+pv.getProduto();
        sql=sql.replace("#1", ""+pv.getVenda().getId());
        sql=sql.replace("#2", ""+pv.getQuantidade_produto());
        return Banco.getCon().manipular(sql);
    }
    public boolean apagar(Produto_Venda pv)
    {
        return Banco.getCon().manipular("delete from produto_venda where id="+pv.getProduto());
    }
    
    
    public Produto_Venda get(int id)
    {
        Produto_Venda pv=null;
        DALProduto dalp=new DALProduto();
        DALVenda dalv=new DALVenda();
        ResultSet rs = Banco.getCon().consultar("select * from produto_venda where produto_id="+id);
        try
        {
          if(rs.next())
          {
            pv=new Produto_Venda(dalp.get(rs.getInt("produto_id")),dalv.get(rs.getInt("venda_id")),rs.getInt("quantidade_produto"));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - PV1");}
        return pv;        
    }
    public List<Produto_Venda> get(String filtro)
    {
        List<Produto_Venda> list=new ArrayList();
        DALProduto dalp=new DALProduto();
        DALVenda dalv=new DALVenda();
        String sql="select * from produto_venda";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs = Banco.getCon().consultar(sql);
        try
        {
          while(rs.next())
          {
              Produto p = dalp.get(rs.getInt("produto_id"));
            list.add(new Produto_Venda(p,dalv.get(rs.getInt("venda_id")),rs.getInt("quantidade_produto"), p.getValor() * rs.getInt("quantidade_produto")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - PV2");}
        return list;        
    }
}
