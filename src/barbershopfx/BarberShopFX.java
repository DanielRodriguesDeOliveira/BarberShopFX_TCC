package barbershopfx;

import barbershopfx.db.util.Banco;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class BarberShopFX extends Application 
{    
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("ui/TelaLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("BarberShopFX v1.0");
        stage.resizableProperty().setValue(Boolean.FALSE);
        //stage.setMaximized(true);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        if(!Banco.conectar())
            JOptionPane.showMessageDialog(null, Banco.getCon().getMensagemErro());
        else
            launch(args);
    }   
}
