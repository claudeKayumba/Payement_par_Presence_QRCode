/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.presence.list;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class List_presenceController implements Initializable {

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
    }    

    @FXML
    private void updateAgent(ActionEvent event) {
    }

    @FXML
    private void deleteData(ActionEvent event) {
    }
    
}
