package barbershopfx.db.dal;

import barbershopfx.db.entidade.Misto;
import barbershopfx.db.util.Banco;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class DALMisto {
    public Misto get(int id)
    {
        Misto m=null;
        DALProduto dalp=new DALProduto();
        DALOrcamento dalo=new DALOrcamento();
        DALProduto_Orcamento dalpo=new DALProduto_Orcamento();
        ResultSet rs = Banco.getCon().consultar("select * from produto inner join produto_orcamento on produto.id=produto_orcamento.produto_id inner join orcamento on orcamento.id=produto_orcamento.orcamento_id where orcamento.id="+id);
        try
        {
          if(rs.next())
          {
            m=new Misto(dalp.get(rs.getInt("produto_id")),dalpo.get(rs.getInt("produto_id")),dalo.get(rs.getInt("orcamento_id")));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - M1");}
        return m;        
    }
    
    public List<Misto> get(String filtro)
    {
        List<Misto> list=new ArrayList();
        DALProduto dalp=new DALProduto();
        DALOrcamento dalo=new DALOrcamento();
        DALProduto_Orcamento dalpo=new DALProduto_Orcamento();
        ResultSet rs = Banco.getCon().consultar("select * from produto inner join produto_orcamento on produto.id=produto_orcamento.produto_id inner join orcamento on orcamento.id=produto_orcamento.orcamento_id where orcamento.id="+filtro);
        try
        {
          while(rs.next())
          {
            list.add(new Misto(dalp.get(rs.getInt("produto_id")),dalpo.get(rs.getInt("produto_id")),dalo.get(rs.getInt("orcamento_id"))));
          }    
        }catch(Exception e){System.out.println("erro ao pesquisar - M2");}
        return list;        
    }
}