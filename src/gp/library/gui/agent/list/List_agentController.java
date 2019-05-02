/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.agent.list;

import com.jfoenix.controls.JFXDialog;
import gp.library.database.dao.DatabaseHelper;
import gp.library.gui.agent.AgentController;
import gp.library.gui.agent.update.Update_agentController;
import gp.library.gui.home.HomeController;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class List_agentController implements Initializable {
    DatabaseHelper helper = new DatabaseHelper();
    public static String _matricule;
    public static String _nom;
    public static String _salaire;
    public static String _postnom;
    public static String _genre;
    public static String _dateNaiss;
    public static String _service;
    public static String _fonction;

    @FXML
    private Text matricule;
    @FXML
    private Text nom;
    @FXML
    private Text salaire;
    @FXML
    private Text postnom;
    @FXML
    private Text genre;
    @FXML
    private Text dateNaiss;
    @FXML
    private Text service;
    @FXML
    private Text fonction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        matricule.setText(_matricule.toUpperCase());
        nom.setText(_nom.toUpperCase());
        postnom.setText(_postnom.toUpperCase());
        salaire.setText(_salaire.toUpperCase());
        genre.setText(_genre.toUpperCase());
        dateNaiss.setText(_dateNaiss.toUpperCase());
        service.setText(_service.toUpperCase());
        fonction.setText(_fonction.toUpperCase());
    }    

    @FXML
    private void updateAgent(ActionEvent event) {
        try {
            Update_agentController.isUpdate = true;
            Update_agentController.fonction.setDesignation(fonction.getText());
            Update_agentController.service.setDesignation(service.getText());
            Update_agentController.agent.setId(Integer.valueOf(matricule.getText()));
            Update_agentController.agent.setNom(nom.getText());
            Update_agentController.agent.setPostnom(postnom.getText());
            Update_agentController.agent.setGenre(genre.getText());
            Update_agentController.agent.setDateNaiss(dateNaiss.getText());
            Update_agentController.agent.setSalaire(Double.valueOf(salaire.getText()));
            Update_agentController.agent.setFonction(Update_agentController.fonction);
            Update_agentController.agent.setService(Update_agentController.service);
            MyWindow.showFormDialog("Modifier", HomeController.full_container, getClass().getResource(MyConstant.UPDATE_AGENT), JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void deleteData(ActionEvent event){
        try {
            if (helper.deleteFromDatabase("DELETE FROM tAgent WHERE id = "+matricule.getText()+"")) {
                
                MyWindow.dialogAvertissement("Message", "Supprimé");
            }
        } catch (Exception e) {
        }
    }
    
}
