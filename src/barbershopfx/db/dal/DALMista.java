package barbershopfx.db.dal;

import barbershopfx.db.entidade.Mista;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class DALMista {
    public Mista get(int id)
    {
        Mista m=null;
        DALProduto dalp=new DALProduto();
        DALVenda dalv=new DALVenda();
        DALProduto_Venda dalpv=new DALProduto_Venda();
        ResultSet rs = Banco.getCon().consultar("select * from produto inner join produto_venda on produto.id=produto_venda.produto_id inner join venda on venda.id=produto_venda.venda_id where venda.id="+id);
        try
        {
          if(rs.next())
          {
            m=new Mista(dalp.get(rs.getInt("produto_id")),dalpv.get(rs.getInt("produto_id")),dalv.get(rs.getInt("venda_id")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - M1");}
        return m;        
    }
    
    public List<Mista> get(String filtro)
    {
        List<Mista> list=new ArrayList();
        DALProduto dalp=new DALProduto();
        DALVenda dalv=new DALVenda();
        DALProduto_Venda dalpv=new DALProduto_Venda();
        ResultSet rs = Banco.getCon().consultar("select * from produto inner join produto_venda on produto.id=produto_venda.produto_id inner join venda on venda.id=produto_venda.venda_id where venda.id="+filtro);
        try
        {
          while(rs.next())
          {
            list.add(new Mista(dalp.get(rs.getInt("produto_id")),dalpv.get(rs.getInt("produto_id")),dalv.get(rs.getInt("venda_id"))));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - M2");}
        return list;        
    }
}
