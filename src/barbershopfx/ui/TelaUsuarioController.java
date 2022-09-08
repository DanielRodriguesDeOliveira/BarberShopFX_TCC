package barbershopfx.ui;

import barbershopfx.db.dal.DALUsuario;
import barbershopfx.db.entidade.Usuario;
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


public class TelaUsuarioController implements Initializable 
{

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
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtSenha;
    @FXML
    private JFXTextField txtFuncao;
    @FXML
    private JFXDatePicker dtpCriado;
    @FXML
    private JFXDatePicker dtpModificado;
    @FXML
    private VBox pnlTabela;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private TableView<Usuario> tabela;
    @FXML
    private TableColumn<Usuario, Integer> colId;
    @FXML
    private TableColumn<Usuario, String> colEmail;
    @FXML
    private TableColumn<Usuario, String> colSenha;
    @FXML
    private TableColumn<Usuario, String> colFuncao;
    @FXML
    private TableColumn<Usuario, LocalDate> colCriado;
    @FXML
    private TableColumn<Usuario, LocalDate> colModificado;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // vincular as colunas da tabela aos beans
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colSenha.setCellValueFactory(new PropertyValueFactory("senha"));
        colFuncao.setCellValueFactory(new PropertyValueFactory("funcao"));
        colCriado.setCellValueFactory(new PropertyValueFactory("criado"));
        colModificado.setCellValueFactory(new PropertyValueFactory("modificado"));
        //...
        estadoOriginal();
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        Usuario u = (Usuario) tabela.getSelectionModel().getSelectedItem();
        txtId.setText(""+u.getId());
        txtEmail.setText(u.getEmail());
        txtSenha.setText(u.getSenha());
        txtFuncao.setText(u.getFuncao());
        dtpCriado.setValue(u.getCriado());
        dtpModificado.setValue(u.getModificado());
       
        estadoEdicao(); 
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
         DALUsuario dal = new DALUsuario();
         Usuario u = tabela.getSelectionModel().getSelectedItem();
         dal.apagar(u);
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
        Usuario u = new Usuario(id, txtEmail.getText(), txtSenha.getText(), txtFuncao.getText(), dtpCriado.getValue(), dtpModificado.getValue());
        DALUsuario dal = new DALUsuario();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (u.getId() == 0) // novo cadastro
        {
            if (dal.gravar(u)) {
                a.setContentText("Gravado com Sucesso");
            } else {
                a.setContentText("Problemas ao Gravar\n"+Banco.getCon().getMensagemErro());
            }
        } else //alteração de cadastro
        {
            if (dal.alterar(u)) {
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
        carregaTabela("upper(email) like '%"+txtPesquisar.getText().toUpperCase()+"%'");
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
      pnlTabela.setDisable(true);
      pnlDados.setDisable(false);
      btnConfirmar.setDisable(false);
      btnApagar.setDisable(true);
      btnAlterar.setDisable(true);
      txtEmail.requestFocus();  
    }
    
    private void carregaTabela(String filtro) {
      DALUsuario dal = new DALUsuario();
      List<Usuario> res = dal.get(filtro);
      ObservableList<Usuario> modelo;
      modelo = FXCollections.observableArrayList(res);
      tabela.setItems(modelo);  
    }
}
