package barbershopfx.ui;

import barbershopfx.db.dal.DALUsuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class TelaLoginController implements Initializable 
{
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXPasswordField txtSenha;
    @FXML
    private JFXButton btnEntrar;
    @FXML
    private JFXButton btnSair;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void evtEntrar(ActionEvent event) throws IOException {
      DALUsuario dal = new DALUsuario();
      Alert a = new Alert(Alert.AlertType.INFORMATION);
      if(dal.get(txtLogin.getText(), txtSenha.getText())!= null){
          
          Stage stage = (Stage) btnSair.getScene().getWindow();
          stage.close();
          
        Parent root = FXMLLoader.load(getClass().getResource("TelaMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("BarberShopFX v1.0");
        //stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setMaximized(true);
        stage.show();
      } else{
          a.setContentText("Dados de acesso invalidos!");
          a.showAndWait();
      }
      
    }

    @FXML
    private void evtSair(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
