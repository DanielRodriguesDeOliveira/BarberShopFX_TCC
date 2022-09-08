package barbershopfx.ui;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;

import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.Region;

/**
 *
 * @author daniel
 */
public class TelaPrincipalController implements Initializable {
    

    @FXML
    private ScrollPane painelcentral;
    @FXML
    private Pane pane;
    @FXML
    private Button bthome;
    @FXML
    private Button bthome2;
    @FXML
    private Button btuser;
    @FXML
    private Button bthelp;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clkBtHome(null);
    }    

    @FXML
    private void clkItem1(ActionEvent event) {
       
        try
        {
         Parent tela = FXMLLoader.load(getClass().getResource("/sismodelo/ui/Tela1.fxml"));
         painelcentral.setContent(tela);
         
         // centralizando a tela no scroolpane
         this.center(painelcentral.getViewportBounds(), tela);
         painelcentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
        });
        }catch(Exception e){System.out.println(e);}
    }
    private void center(Bounds viewPortBounds, Parent centeredNode) {
        double width = viewPortBounds.getWidth();
        double height = viewPortBounds.getHeight();
        double pwidth=((Region)centeredNode).getPrefWidth();
        double pheight=((Region)centeredNode).getPrefHeight();
        
        if (width > pwidth) {
            centeredNode.setTranslateX((width - pwidth) / 2);
        } else {
            centeredNode.setTranslateX(0);
        }
        if (height > pheight) {
            centeredNode.setTranslateY((height - pheight) / 2);
        } else {
            centeredNode.setTranslateY(0);
        }

    }

    @FXML
    private void clkBtHome(ActionEvent event) {
        try
        {
         Parent tela = FXMLLoader.load(getClass().getResource("/sismodelo/ui/TelaHome.fxml"));
         painelcentral.setContent(tela);
         
         // centralizando a tela no scroolpane
         this.center(painelcentral.getViewportBounds(), tela);
         painelcentral.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.center(newValue, tela);
        });
        }catch(Exception e){System.out.println(e);}
    }
}
