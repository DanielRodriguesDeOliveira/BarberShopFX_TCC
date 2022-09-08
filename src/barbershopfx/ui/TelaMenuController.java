package barbershopfx.ui;

import barbershopfx.db.util.Banco;
import barbershopfx.db.util.Conexao;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


public class TelaMenuController implements Initializable 
{

    @FXML
    private BorderPane painelprincipal;
    @FXML
    private Button btnAgenda;
    @FXML
    private Button btnCliente;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
    
    @FXML
    private void evtCadAgenda(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaAgenda.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }

    @FXML
    private void evtCadCliente(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaCliente.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }
    
    @FXML
    private void evtCadCategoria(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaCategoria.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }
    
    @FXML
    private void evtCadFuncionario(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaFuncionario.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }
    
    @FXML
    private void evtProdServ(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaProduto.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }

    @FXML
    private void evtUsuarios(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaUsuario.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }
    
    @FXML
    private void evtVenda(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaVenda.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }

    @FXML
    private void evtOrcamento(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaOrcamento.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }
    
    @FXML
    private void evtRelClientes(ActionEvent event) throws SQLException {
        try{
            ResultSet rs = Banco.getCon().consultarPS("select * from cliente");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("ireport/RELClientes.jasper", new HashMap(), relatResul);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);
            jv.toFront();
        }catch(JRException ex){
            System.out.println("Erro: "+ex);
        }
    }


    @FXML
    private void evtSobre(ActionEvent event) {
        try
        {
           Parent root = FXMLLoader.load(getClass().getResource("TelaHome.fxml"));
           painelprincipal.setCenter(root);
        }
        catch(Exception e){}
    }

    @FXML
    private void evtSair(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void evtRelAgenda(ActionEvent event) {
        try{
            ResultSet rs = Banco.getCon().consultarPS("select * from agenda");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("ireport/RELAgenda.jasper", new HashMap(), relatResul);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);
            jv.toFront();
        }catch(JRException ex){
            System.out.println("Erro: "+ex);
        }
    }

    @FXML
    private void evtRelFuncionarios(ActionEvent event) {
        try{
            ResultSet rs = Banco.getCon().consultarPS("select * from funcionario");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("ireport/RELFuncionarios.jasper", new HashMap(), relatResul);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);
            jv.toFront();
        }catch(JRException ex){
            System.out.println("Erro: "+ex);
        }
    }
    
    @FXML
    private void evtRelProdutos(ActionEvent event) {
        try{
            ResultSet rs = Banco.getCon().consultarPS("select * from produto");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("ireport/RELProdutos.jasper", new HashMap(), relatResul);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);
            jv.toFront();
        }catch(JRException ex){
            System.out.println("Erro: "+ex);
        }
    }

    @FXML
    private void evtRelCategorias(ActionEvent event) {
        try{
            ResultSet rs = Banco.getCon().consultarPS("select * from categoria");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("ireport/RELCategorias.jasper", new HashMap(), relatResul);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);
            jv.toFront();
        }catch(JRException ex){
            System.out.println("Erro: "+ex);
        }
    }

    @FXML
    private void evtRelVendas(ActionEvent event) {
        try{
            ResultSet rs = Banco.getCon().consultarPS("select * from venda");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("ireport/RELVendas.jasper", new HashMap(), relatResul);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);
            jv.toFront();
        }catch(JRException ex){
            System.out.println("Erro: "+ex);
        }
    }

    @FXML
    private void evtRelUsuarios(ActionEvent event) {
        try{
            ResultSet rs = Banco.getCon().consultarPS("select * from usuario");
            JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("ireport/RELUsuarios.jasper", new HashMap(), relatResul);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);
            jv.toFront();
        }catch(JRException ex){
            System.out.println("Erro: "+ex);
        }
    }
}
