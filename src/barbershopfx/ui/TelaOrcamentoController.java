package barbershopfx.ui;

import barbershopfx.db.dal.DALProduto;
import barbershopfx.db.dal.DALCliente;
import barbershopfx.db.dal.DALFuncionario;
import barbershopfx.db.dal.DALMista;
import barbershopfx.db.dal.DALOrcamento;
import barbershopfx.db.dal.DALProduto_Orcamento;
import barbershopfx.db.entidade.Produto;
import barbershopfx.db.entidade.Cliente;
import barbershopfx.db.entidade.Funcionario;
import barbershopfx.db.entidade.Mista;
import barbershopfx.db.entidade.Orcamento;
import barbershopfx.db.entidade.Produto_Orcamento;
import barbershopfx.db.util.Banco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TelaOrcamentoController implements Initializable 
{

    @FXML
    private Pane pnlDados;
    @FXML
    private JFXComboBox<Cliente> cbbCliente;
    @FXML
    private JFXComboBox<Funcionario> cbbFuncionario;
    @FXML
    private JFXTextField txtProduto;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private JFXDatePicker dtpData;
    @FXML
    private JFXTextField txtQuantidade;
    @FXML
    private JFXTextField txtValorItem;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnConfirmar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private TableView<Produto> tabelaPesquisa;
    @FXML
    private TableColumn<Produto, Integer> colId;
    @FXML
    private TableColumn<Produto, String> colDescricao;
    @FXML
    private TableColumn<Produto, String> colQuantidade;
    @FXML
    private TableView<Produto_Orcamento> tabelaItens;
    @FXML
    private TableColumn<Produto_Orcamento, Integer> colDescr;
    @FXML
    private TableColumn<Produto_Orcamento, Integer> colQuant;
    @FXML
    private TableColumn<Produto_Orcamento, Float> colValorTotal;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtTotal;
    @FXML
    private JFXTextField txtCodigo;
    @FXML
    private GridPane pnlTabela;
    int codOrcamento;
    float total=0;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        
        colDescr.setCellValueFactory(new PropertyValueFactory("produto")); 
        colQuant.setCellValueFactory(new PropertyValueFactory("quantidade_produto"));
        colValorTotal.setCellValueFactory(new PropertyValueFactory("valor"));
        estadoOriginal();
        carregaTabela("");
        carregaCliente();
        carregaFuncionario();
    }

    @FXML
    private void evtPesquisar(ActionEvent event) {
        carregaTabela("upper(descricao) like '%"+txtProduto.getText().toUpperCase()+"%'");
    }
    
    @FXML
    private void evtTabela(MouseEvent event) {
        Produto p = (Produto) tabelaPesquisa.getSelectionModel().getSelectedItem();
        txtProduto.setText(p.getDescricao());
        txtCodigo.setText(String.valueOf(p.getId()));
        txtQuantidade.setText("1");
        txtValorItem.setText(""+p.getValor());
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        estadoOriginal();
        DALOrcamento dal = new DALOrcamento();
        Orcamento o = new Orcamento(codOrcamento, dtpData.getValue(),Float.parseFloat(txtTotal.getText()),
                cbbCliente.getSelectionModel().getSelectedItem(),cbbFuncionario.getSelectionModel().getSelectedItem());
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(dal.alterar(o)){
         a.setContentText("Orçamento realizado!");
            } else {
                a.setContentText("Problemas ao finalizar orçamento\n"+Banco.getCon().getMensagemErro());
         }
        a.showAndWait();
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        DALOrcamento dal = new DALOrcamento();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(dal.CancelaOrcamento()){
           a.setContentText("Orçamento cancelado!"); 
        }else{
           a.setContentText("Problemas ao cancelar orçamento\n"+Banco.getCon().getMensagemErro());
        }
        a.showAndWait();
    }
    
    @FXML
    private void evtAdd(ActionEvent event) {
        int quantidade=0;
        Orcamento o = new Orcamento();
        Produto p = (Produto) tabelaPesquisa.getSelectionModel().getSelectedItem();
        quantidade = p.getQuantidade();
        DALOrcamento dal = new DALOrcamento();
        //dal.get(p.getId());
        if(quantidade >=Integer.parseInt(txtQuantidade.getText())){
         o.setProduto(p);
         o.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
         o.setId(codOrcamento);
         Alert a = new Alert(Alert.AlertType.INFORMATION);
         if(dal.gravarItens(o)){
             a.setContentText("Item adicionado");
            } else {
                a.setContentText("Problemas ao adicionar item\n"+Banco.getCon().getMensagemErro());
         }
         a.showAndWait();
         somaProduto();
         carregaTabela("");
         carregaTabelaItensVendaProduto("orcamento_id = "+codOrcamento);
        }
    }
    
    @FXML
    private void evtNovo(ActionEvent event) {
        int id = 0;
        Orcamento o = new Orcamento(id);
        DALOrcamento dal = new DALOrcamento();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (dal.gravar(o)) {
                a.setContentText("Orçamento Iniciado");
            } else {
                a.setContentText("Problemas ao iniciar orçamento\n"+Banco.getCon().getMensagemErro());
            }
        a.showAndWait();
        codOrcamento = Banco.getCon().getMaxPK("orcamento", "id");
        txtId.setText(""+codOrcamento);
    }
    
    private void estadoOriginal() {
      pnlTabela.setDisable(false);
      pnlDados.setDisable(false);
      btnConfirmar.setDisable(false);
      btnCancelar.setDisable(false);
      
      ObservableList<Node> componentes = pnlDados.getChildren(); //”limpa” os componentes
      for (Node n : componentes) {
          if (n instanceof TextInputControl) // textfield, textarea e htmleditor
          {
              ((TextInputControl) n).setText("");
          }
      }
      carregaTabela("");
    }
    
    private void carregaTabela(String filtro) { 
      DALProduto dal = new DALProduto();
      List<Produto> res = dal.get(filtro);
      ObservableList<Produto> modelo;
      modelo = FXCollections.observableArrayList(res);
      tabelaPesquisa.setItems(modelo);  
    }
    // Carrega tabela de Itens
    private void carregaTabelaItens(String filtro) { 
      DALMista dal = new DALMista();
      List<Mista> res = dal.get(filtro);
      ObservableList<Mista> modelo;
      modelo = FXCollections.observableArrayList(res);
      //tabelaItens.setItems(modelo);  
      somaProduto();
    }
    
    
    private void carregaTabelaItensVendaProduto(String filtro) { 
      DALProduto_Orcamento dal = new DALProduto_Orcamento();
      List<Produto_Orcamento> res = dal.get(filtro);
      ObservableList<Produto_Orcamento> modelo;
      modelo = FXCollections.observableArrayList(res);
      tabelaItens.setItems(modelo);  
      somaProduto();
    }
    
    private void carregaCliente() {
        DALCliente dal = new DALCliente();
        List<Cliente> res = dal.get("");
        ObservableList<Cliente> modelo;
        modelo = FXCollections.observableArrayList(res);
        cbbCliente.setItems(modelo);
    }
    
    private void carregaFuncionario() {
        DALFuncionario dal = new DALFuncionario();
        List<Funcionario> res = dal.get("");
        ObservableList<Funcionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        cbbFuncionario.setItems(modelo);
    }
    
    public void somaProduto(){
        total=0;
        int qtd=0;
        float valor=0;
        
        ResultSet rs = Banco.getCon().consultar("select * from produto_orcamento inner join produto on produto_orcamento.produto_id = produto.id where orcamento_id="+codOrcamento);
        try {
            while(rs.next()){
                qtd = rs.getInt("quantidade");
                valor = Float.parseFloat(rs.getString("valor"));
                total = total+rs.getFloat("valor")*rs.getInt("quantidade_produto");
            }
            txtTotal.setText(String.valueOf(total));
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Erro ao preencher a soma do total!\n ERRO: "+ex);
            a.showAndWait();
        }
    }
}