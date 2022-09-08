package barbershopfx.ui;

import barbershopfx.db.dal.DALFuncionario;
import barbershopfx.db.dal.DALUsuario;
import barbershopfx.db.entidade.Funcionario;
import barbershopfx.db.entidade.Usuario;
import barbershopfx.db.util.Banco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class TelaFuncionarioController implements Initializable 
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
    private JFXTextField txtNome;
    @FXML
    private JFXTextField txtCPF;
    @FXML
    private JFXComboBox<Usuario> cbbUsuario;
    @FXML
    private VBox pnlTabela;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private TableView<Funcionario> tabela;
    @FXML
    private TableColumn<Funcionario, Integer> colId;
    @FXML
    private TableColumn<Funcionario, String> colNome;
    @FXML
    private TableColumn<Funcionario, String> colCPF;
    @FXML
    private TableColumn<Funcionario, Integer> colUsuario;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // vincular as colunas da tabela aos beans
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory("cpf"));
        colUsuario.setCellValueFactory(new PropertyValueFactory("usuario"));
        //...
        estadoOriginal();
        carregaUsuario();
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        Funcionario f = (Funcionario) tabela.getSelectionModel().getSelectedItem();
        txtId.setText("" + f.getId());
        txtNome.setText(f.getNome());
        txtCPF.setText(f.getCpf());
        cbbUsuario.getSelectionModel().select(0);
        cbbUsuario.getSelectionModel().select(f.getUsuario());
        estadoEdicao();
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALFuncionario dal = new DALFuncionario();
            Funcionario f = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(f);
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
        Funcionario f = new Funcionario(id, txtNome.getText(), txtCPF.getText(),
                cbbUsuario.getSelectionModel().getSelectedItem());
        DALFuncionario dal = new DALFuncionario();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (f.getId() == 0) // novo cadastro
        {
            if (dal.gravar(f)) {
                a.setContentText("Gravado com Sucesso");
            } else {
                a.setContentText("Problemas ao Gravar\n"+Banco.getCon().getMensagemErro());
            }
        } else //alteração de cadastro
        {
            if (dal.alterar(f)) {
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
            if (n instanceof ComboBox) {
                ((ComboBox) n).getItems().clear();
            }
        }
        carregaTabela("");
    }

    private void estadoEdicao() {     
        // carregar os componentes da tela (listbox, combobox, ...)
        carregaUsuario();
        pnlTabela.setDisable(true);
        pnlDados.setDisable(false);
        btnConfirmar.setDisable(false);
        btnNovo.setDisable(true);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        txtNome.requestFocus();
    }
    
    private void carregaTabela(String filtro) {
        DALFuncionario dal = new DALFuncionario();
        List<Funcionario> res = dal.get(filtro);
        ObservableList<Funcionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    private void carregaUsuario() {
        DALUsuario dal = new DALUsuario();
        List<Usuario> res = dal.get("");
        ObservableList<Usuario> modelo;
        modelo = FXCollections.observableArrayList(res);
        cbbUsuario.setItems(modelo);
    }
}