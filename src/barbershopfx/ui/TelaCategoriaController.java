package barbershopfx.ui;

import barbershopfx.db.dal.DALCategoria;
import barbershopfx.db.entidade.Categoria;
import barbershopfx.db.util.Banco;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class TelaCategoriaController implements Initializable 
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
    private VBox pnlTabela;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private TableView<Categoria> tabela;
    @FXML
    private TableColumn<Categoria, Integer> colId;
    @FXML
    private TableColumn<Categoria, String> colNome;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // vincular as colunas da tabela aos beans
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        
        //...
        estadoOriginal();
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        Categoria c = (Categoria) tabela.getSelectionModel().getSelectedItem();
        txtId.setText(""+c.getId());
        txtNome.setText(c.getNome());
        estadoEdicao();
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALCategoria dal = new DALCategoria();
            Categoria c = tabela.getSelectionModel().getSelectedItem();
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
        Categoria c = new Categoria(id, txtNome.getText());
        DALCategoria dal = new DALCategoria();
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
        DALCategoria dal = new DALCategoria();
        List<Categoria> res = dal.get(filtro);
        ObservableList<Categoria> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
}
