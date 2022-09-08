package barbershopfx.ui;

import barbershopfx.db.dal.DALAgenda;
import barbershopfx.db.dal.DALCliente;
import barbershopfx.db.dal.DALFuncionario;
import barbershopfx.db.entidade.Agenda;
import barbershopfx.db.entidade.Cliente;
import barbershopfx.db.entidade.Funcionario;
import barbershopfx.db.util.Banco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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


public class TelaAgendaController implements Initializable 
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
    private JFXDatePicker dtpData;
    @FXML
    private JFXTimePicker dtpHorario;
    @FXML
    private JFXComboBox<Cliente> cbbCliente;
    @FXML
    private JFXComboBox<Funcionario> cbbFuncionario;
    @FXML
    private VBox pnlTabela;
    @FXML
    private JFXDatePicker dtpPesquisar;
    @FXML
    private JFXButton btnPesquisar;
    @FXML
    private TableView<Agenda> tabela;
    @FXML
    private TableColumn<Agenda, Integer> colId;
    @FXML
    private TableColumn<Agenda, LocalDate> colData;
    @FXML
    private TableColumn<Agenda, LocalTime> colHorario;
    @FXML
    private TableColumn<Agenda, Integer> colFuncionario;
    @FXML
    private TableColumn<Agenda, Integer> colCliente;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // vincular as colunas da tabela aos beans
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colData.setCellValueFactory(new PropertyValueFactory("data"));
        colHorario.setCellValueFactory(new PropertyValueFactory("horario"));
        colFuncionario.setCellValueFactory(new PropertyValueFactory("funcionario"));
        colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        //...
        estadoOriginal();
        carregaFuncionario();
        carregaCliente();
    }

    @FXML
    private void evtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void evtAlterar(ActionEvent event) {
        Agenda a = (Agenda) tabela.getSelectionModel().getSelectedItem();
        txtId.setText("" + a.getId());
        dtpData.setValue(a.getData());
        dtpHorario.setValue(a.getHorario());
        cbbFuncionario.getSelectionModel().select(0);
        cbbFuncionario.getSelectionModel().select(a.getFuncionario());
        cbbCliente.getSelectionModel().select(0);
        cbbCliente.getSelectionModel().select(a.getCliente());
        estadoEdicao();
    }

    @FXML
    private void evtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            DALAgenda dal = new DALAgenda();
            Agenda ag = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(ag);
            carregaTabela("");
        }
    }

    @FXML
    private void evtConfirmar(ActionEvent event) {
        int id, valido;
        LocalDate atual =LocalDate.now();
        try {
            id = Integer.parseInt(txtId.getText());
        } catch (Exception e) {
            id = 0;
        }
        Agenda ag = new Agenda(id, dtpData.getValue(), dtpHorario.getValue(),
                cbbFuncionario.getSelectionModel().getSelectedItem(),cbbCliente.getSelectionModel().getSelectedItem());
        DALAgenda dal = new DALAgenda();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        valido = valida(ag);
        if(valido>0){
           a.setContentText("Preencher todos os campos!");     
        }
        else{
             if(atual.isAfter(ag.getData())){
             a.setContentText("Data Inválida!");  
             }
             else{
                 if(dal.verificar(ag)){
                  a.setContentText("já está agendado!");   
                 }
                 else{
                     if (ag.getId() == 0) // novo cadastro
                 {
                     if (dal.gravar(ag)) {
                         a.setContentText("Gravado com Sucesso");
                     } else {
                         a.setContentText("Problemas ao Gravar\n" + Banco.getCon().getMensagemErro());
                     }
                 } else //alteração de cadastro
                 {
                     if (dal.alterar(ag)) {
                         a.setContentText("Alterado com Sucesso");
                     } else {
                         a.setContentText("Problemas ao Alterar\n" + Banco.getCon().getMensagemErro());
                     }
                 }
                 estadoOriginal();
                 }
             }
        }
        a.showAndWait();
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
        carregaTabela("data = '%"+dtpPesquisar.getValue()+"%'");
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
        carregaFuncionario();
        carregaCliente();
        pnlTabela.setDisable(true);
        pnlDados.setDisable(false);
        btnConfirmar.setDisable(false);
        btnNovo.setDisable(true);
        btnApagar.setDisable(true);
        btnAlterar.setDisable(true);
        dtpData.requestFocus();
    }
    
    private void carregaTabela(String filtro) {
        DALAgenda dal = new DALAgenda();
        List<Agenda> res = dal.get(filtro);
        ObservableList<Agenda> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    private void carregaFuncionario() {
        DALFuncionario dal = new DALFuncionario();
        List<Funcionario> res = dal.get("");
        ObservableList<Funcionario> modelo;
        modelo = FXCollections.observableArrayList(res);
        cbbFuncionario.setItems(modelo);
    }
    
    private void carregaCliente() {
        DALCliente dal = new DALCliente();
        List<Cliente> res = dal.get("");
        ObservableList<Cliente> modelo;
        modelo = FXCollections.observableArrayList(res);
        cbbCliente.setItems(modelo);
    }
    
    private int valida(Agenda ag){
        int campo =0;
        if( ag.getData()== null)
            campo++;
        if(ag.getHorario() == null)
            campo++;
        if(ag.getFuncionario() == null)
            campo++;
        if(ag.getCliente() == null)
            campo++;
        return campo;
    }
}
