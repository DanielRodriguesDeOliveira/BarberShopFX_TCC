package barbershopfx.ui;

import barbershopfx.db.dal.DALCategoria;
import barbershopfx.db.dal.DALProduto;
import barbershopfx.db.entidade.Categoria;
import barbershopfx.db.entidade.Produto;
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


public class TelaProdutoController implements Initializable 
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
    private JFXTextField txtDescricao;
    @FXML
    private JFXComboBox<Categoria> cbbCategoria;
    @FXML
    private JFXTextField txtValor;
    @FXML
    private JFXTextField txtQuantidade;
    @FXML
    private VBox pnlTabela;
    @FXML
    private JFXTextField txtPesquisar;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private TableView<Produto> tabela;
    @FXML
    private TableColumn<Produto, Integer> colId;
    @FXML
    private TableColumn<Produto, String> colDescricao;
    @FXML
    private TableColumn<Produto, String> colValor;
    @FXML
    private TableColumn<Produto, String> colQuantidade;
    @FXML
    private TableColumn<Produto, Integer> colCategoria;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // vincular as colunas da tabela aos beans
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory("valor"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        //...
        estadoOriginal();
        carregaCategoria();
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        Produto p = (Produto) tabela.getSelectionModel().getSelectedItem();
        txtId.setText(""+p.getId());
        txtDescricao.setText(p.getDescricao());
        txtValor.setText(""+p.getValor());
        txtQuantidade.setText(""+p.getQuantidade());
        cbbCategoria.getSelectionModel().select(0);
        cbbCategoria.getSelectionModel().select(p.getCategoria());
        estadoEdicao();
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALProduto dal = new DALProduto();
            Produto p = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(p);
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
        Produto p = new Produto(id, txtDescricao.getText(), Float.parseFloat(txtValor.getText()), Integer.parseInt(txtQuantidade.getText()),
                cbbCategoria.getSelectionModel().getSelectedItem());
        DALProduto dal = new DALProduto();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (p.getId() == 0) // novo cadastro
        {
            if (dal.gravar(p)) {
                a.setContentText("Gravado com Sucesso");
            } else {
                a.setContentText("Problemas ao Gravar\n"+Banco.getCon().getMensagemErro());
            }
        } else //alteração de cadastro
        {
            if (dal.alterar(p)) {
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
        carregaTabela("upper(descricao) like '%"+txtPesquisar.getText().toUpperCase()+"%'");
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
        carregaCategoria();
        pnlTabela.setDisable(true);
        pnlDados.setDisable(false);
        btnConfirmar.setDisable(false);
        btnNovo.setDisable(true);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        txtDescricao.requestFocus();
    }
    
    private void carregaTabela(String filtro) {
        DALProduto dal = new DALProduto();
        List<Produto> res = dal.get(filtro);
        ObservableList<Produto> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    private void carregaCategoria() {
        DALCategoria dal = new DALCategoria();
        List<Categoria> res = dal.get("");
        ObservableList<Categoria> modelo;
        modelo = FXCollections.observableArrayList(res);
        cbbCategoria.setItems(modelo);
    }
}