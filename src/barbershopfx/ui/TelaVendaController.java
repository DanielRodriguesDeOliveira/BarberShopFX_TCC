package barbershopfx.ui;

import barbershopfx.db.dal.DALProduto;
import barbershopfx.db.dal.DALCliente;
import barbershopfx.db.dal.DALFuncionario;
import barbershopfx.db.dal.DALMista;
import barbershopfx.db.dal.DALProduto_Venda;
import barbershopfx.db.dal.DALVenda;
import barbershopfx.db.entidade.Produto;
import barbershopfx.db.entidade.Cliente;
import barbershopfx.db.entidade.Funcionario;
import barbershopfx.db.entidade.Mista;
import barbershopfx.db.entidade.Produto_Venda;
import barbershopfx.db.entidade.Venda;
import barbershopfx.db.util.Banco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TelaVendaController implements Initializable 
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
    private TableView<Produto_Venda> tabelaItens;
    @FXML
    private TableColumn<Produto_Venda, Integer> colDescr;
    @FXML
    private TableColumn<Produto_Venda, Integer> colQuant;
    @FXML
    private TableColumn<Produto_Venda, Float> colValorTotal;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtTotal;
    @FXML
    private GridPane pnlTabela;
    int codVenda;
    float total=0;
    @FXML
    private JFXTextField txtCodigo;
    
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
        //tabelaPesquisa.getSelectionModel().getSelectedIndex();
        Produto p = (Produto) tabelaPesquisa.getSelectionModel().getSelectedItem();
        txtProduto.setText(p.getDescricao());
        txtCodigo.setText(String.valueOf(p.getId()));
        txtQuantidade.setText("1");
        txtValorItem.setText(""+p.getValor());
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        int valido;
        LocalDate atual =LocalDate.now();
        estadoOriginal();
        DALVenda dal = new DALVenda();
        Venda v = new Venda(codVenda, dtpData.getValue(),Float.parseFloat(txtTotal.getText()),
                cbbCliente.getSelectionModel().getSelectedItem(),cbbFuncionario.getSelectionModel().getSelectedItem());
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        valido = valida(v);
        if(valido>0)
        {
           a.setContentText("Preencher todos os campos!"); 
           estadoEdicao();
        }else {
            if (atual.isAfter(v.getData())) {
                a.setContentText("Data Inválida!");
                estadoEdicao();
            } else {
                if (dal.alterar(v)) {
                    a.setContentText("Venda finalizada!");
                    estadoOriginal();
                    tabelaItens.getItems().clear();
                } else {
                    a.setContentText("Problemas ao finalizar venda\n" + Banco.getCon().getMensagemErro());
                    estadoOriginal();
                    tabelaItens.getItems().clear();
                }
            }
        }
        a.showAndWait();
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
        DALVenda dal = new DALVenda();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if(dal.CancelaVenda()){
           a.setContentText("Venda cancelada!"); 
        }else{
           a.setContentText("Problemas ao cancelar venda\n"+Banco.getCon().getMensagemErro());
        }
        a.showAndWait();
        estadoOriginal();
        tabelaItens.getItems().clear();
    }
    
    /*@FXML
    private void evtAtualiza(InputMethodEvent event) {
        float valorTotal;
        valorTotal = Float.parseFloat(txtValorItem.getText())*Integer.parseInt(txtQuantidade.getText());
        txtTotal.setText(String.valueOf(valorTotal));
    }*/

    @FXML
    private void evtAdd(ActionEvent event) {
        int quantidade=0;
        Venda v = new Venda();
        Produto p = (Produto) tabelaPesquisa.getSelectionModel().getSelectedItem();
        quantidade = p.getQuantidade();
        DALVenda dal = new DALVenda();
        //dal.get(p.getId());
        if(quantidade >=Integer.parseInt(txtQuantidade.getText())){
         v.setProduto(p);
         v.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
         v.setId(codVenda);
         Alert a = new Alert(Alert.AlertType.INFORMATION);
         if(dal.gravarItens(v)){
             a.setContentText("Item adicionado");
            } else {
                a.setContentText("Problemas ao adicionar item\n"+Banco.getCon().getMensagemErro());
         }
         a.showAndWait();
         somaProduto();
         carregaTabela("");
         carregaTabelaItensVendaProduto(" venda_id = "+codVenda);
        }
    }
    
    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
        int id = 0;
        Venda v = new Venda(id);
        DALVenda dal = new DALVenda();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (dal.gravar(v)) {
                a.setContentText("Venda Iniciada");
            } else {
                a.setContentText("Problemas ao iniciar venda\n"+Banco.getCon().getMensagemErro());
            }
        a.showAndWait();
        codVenda = Banco.getCon().getMaxPK("venda", "id");
        txtId.setText(""+codVenda);
    }
    
    private void estadoOriginal() {
      pnlTabela.setDisable(true);
      btnNovo.setDisable(false);
      btnPesquisar.setDisable(true);
      btnAdd.setDisable(true);
      btnConfirmar.setDisable(true);
      btnCancelar.setDisable(true);
      
      ObservableList<Node> componentes = pnlDados.getChildren(); //”limpa” os componentes
      for (Node n : componentes) {
          if (n instanceof TextInputControl) // textfield, textarea e htmleditor
          {
              ((TextInputControl) n).setText("");
          }
      }
      carregaTabela("");
    }
    
    private void estadoEdicao() {     
        // carregar os componentes da tela (listbox, combobox, ...)
        pnlTabela.setDisable(false);
        btnNovo.setDisable(true);
        btnPesquisar.setDisable(false);
        btnAdd.setDisable(false);
        btnConfirmar.setDisable(false);
        btnCancelar.setDisable(false);
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
      DALProduto_Venda dal = new DALProduto_Venda();
      List<Produto_Venda> res = dal.get(filtro);
      ObservableList<Produto_Venda> modelo;
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
        
        ResultSet rs = Banco.getCon().consultar("select * from produto_venda inner join produto on produto_venda.produto_id = produto.id where venda_id="+codVenda);
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
    
    private int valida(Venda v){
        int campo =0;
        if( v.getData()== null)
            campo++;
        if(v.getCliente() == null)
            campo++;
        if(v.getFuncionario() == null)
            campo++;
        return campo;
    }
}