/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.presence.update;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Update_presenceController implements Initializable {

    @FXML
    private Text matricule;
    @FXML
    private Text nom;
    @FXML
    private Text genre;
    @FXML
    private Text service;
    @FXML
    private Text fonction;
    @FXML
    private Text nom1;
    @FXML
    private TextField txtUsername1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void fillData(ActionEvent event) {
    }
    
}
