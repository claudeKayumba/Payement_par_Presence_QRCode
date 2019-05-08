/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.payement;

import com.jfoenix.controls.JFXDialog;
import gp.library.gui.agent.AgentController;
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

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class PayementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addPayement(ActionEvent event) {
        try {
            MyWindow.showFormDialog("", HomeController.full_container, getClass().getResource(MyConstant.UPDATE_PAYEMENT), JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
