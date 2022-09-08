package barbershopfx.ui;

import barbershopfx.db.dal.DALCliente;
import barbershopfx.db.entidade.Cliente;
import barbershopfx.db.util.Banco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class TelaClienteController implements Initializable {
    
    @FXML
    private JFXButton btnNovo;
    @FXML
    private JFXButton btnAlterar;
    @FXML
    private JFXButton btnApagar;
    @FXML
    private JFXButton btnConfirmar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private AnchorPane pnlDados;
    @FXML
    private VBox pnlTabela;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtCpf;
    @FXML
    private JFXTextField txtCep;
    @FXML
    private JFXTextField txtEndereco;
    @FXML
    private JFXTextField txtNumero;
    @FXML
    private JFXTextField txtBairro;
    @FXML
    private JFXTextField txtCidade;
    @FXML
    private JFXTextField txtComplemento;
    @FXML
    private JFXTextField txtFone;
    @FXML
    private JFXDatePicker dtpDtNasc;
    @FXML
    private TableView<Cliente> tabela;
    @FXML
    private TableColumn<Cliente, Integer> colId;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colCPF;
    @FXML
    private TableColumn<Cliente, String> colCEP;
    @FXML
    private TableColumn<Cliente, String> colEndereco;
    @FXML
    private TableColumn<Cliente, Integer> colNumero;
    @FXML
    private TableColumn<Cliente, String> colBairro;
    @FXML
    private TableColumn<Cliente, String> colCidade;
    @FXML
    private TableColumn<Cliente, String> colComplemento;
    @FXML
    private TableColumn<Cliente, String> colFone;
    @FXML
    private TableColumn<Cliente, LocalDate> colDtNasc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // vincular as colunas da tabela aos beans
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
        colCEP.setCellValueFactory(new PropertyValueFactory("cep"));
        colEndereco.setCellValueFactory(new PropertyValueFactory("endereco"));
        colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        colBairro.setCellValueFactory(new PropertyValueFactory("bairro"));
        colCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
        colComplemento.setCellValueFactory(new PropertyValueFactory("complemento"));
        colFone.setCellValueFactory(new PropertyValueFactory("fone"));
        colDtNasc.setCellValueFactory(new PropertyValueFactory("dtnasc"));
        //...
        estadoOriginal(); 
    }    

    @FXML
    private void evtNovo(ActionEvent event) {
       estadoEdicao(); 
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
       Cliente c = (Cliente) tabela.getSelectionModel().getSelectedItem();
       txtId.setText("" +c.getId());
       txtNome.setText(c.getNome());
       txtCpf.setText(c.getCpf());
       txtCep.setText(c.getCep());
       txtEndereco.setText(c.getEndereco());
       txtNumero.setText(""+c.getNumero());
       txtBairro.setText(c.getBairro());
       txtCidade.setText(c.getCidade());
       txtComplemento.setText(c.getComplemento());
       txtFone.setText(c.getFone());
       dtpDtNasc.setValue(c.getDtnasc());
       
       estadoEdicao(); 
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
         DALCliente dal = new DALCliente();
         Cliente c = tabela.getSelectionModel().getSelectedItem();
         dal.apagar(c);
         carregaTabela("");
        }  
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
       int id;
        try {
            id = Integer.parseInt(txtId.getText());
        } catch (Exception e) {
            id = 0;
        }
        Cliente c = new Cliente(id, txtNome.getText(), txtCpf.getText(), txtCep.getText(), txtEndereco.getText(), Integer.parseInt(txtNumero.getText()), txtBairro.getText(), txtCidade.getText(),txtComplemento.getText(), txtFone.getText(), dtpDtNasc.getValue());
        DALCliente dal = new DALCliente();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (c.getId() == 0) // novo cadastro
        {
            if (dal.gravar(c)) {
                a.setContentText("Gravado com Sucesso");
            } else {
                a.setContentText("Problemas ao Gravar\n"+Banco.getCon().getMensagemErro());
            }
        } else //alteração de cadastro
        {
            if (dal.alterar(c)) {
                a.setContentText("Alterado com Sucesso");
            } else {
                a.setContentText("Problemas ao Alterar\n"+Banco.getCon().getMensagemErro());
            }
        }
        a.showAndWait();
        estadoOriginal(); 
    }

    @FXML
    private void evtCancelar(ActionEvent event) {
       estadoOriginal(); 
    }

    @FXML
    private void evtDigitar(KeyEvent event) {
    }

    @FXML
    private void evtPesquisar(ActionEvent event) {
      carregaTabela("upper(nome) like '%"+txtPesquisar.getText().toUpperCase()+"%'");  
    }

    @FXML
    private void evtTabela(MouseEvent event) {
      if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btnAlterar.setDisable(false);
            btnApagar.setDisable(false);
        }  
    }
    private void estadoOriginal() {
      pnlTabela.setDisable(false);
      pnlDados.setDisable(true);
      btnConfirmar.setDisable(true);
      btnCancelar.setDisable(false);
      btnApagar.setDisable(true);
      btnAlterar.setDisable(true);
      btnNovo.setDisable(false);
      
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
      // p.e. : carregaEstados();
      pnlTabela.setDisable(true);
      pnlDados.setDisable(false);
      btnConfirmar.setDisable(false);
      btnApagar.setDisable(true);
      btnAlterar.setDisable(true);
      txtNome.requestFocus();  
    }
    
    private void carregaTabela(String filtro) {
      DALCliente dal = new DALCliente();
      List<Cliente> res = dal.get(filtro);
      ObservableList<Cliente> modelo;
      modelo = FXCollections.observableArrayList(res);
      tabela.setItems(modelo);  
    }
}