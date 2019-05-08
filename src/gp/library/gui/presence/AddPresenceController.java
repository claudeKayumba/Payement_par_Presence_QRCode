/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.presence;

import com.jfoenix.controls.JFXComboBox;
import gp.library.database.dao.DatabaseHelper;
import gp.library.model.ModelePresence;
import gp.library.utils.MyWindow;
import gp.library.utils.SharedPreferences;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AddPresenceController implements Initializable {

    SharedPreferences prefs = new SharedPreferences();
    DatabaseHelper helper = new DatabaseHelper();
    ModelePresence presence = ModelePresence.getInstance();

    @FXML
    private TextField txt_mois;
    @FXML
    private JFXComboBox<String> cmb_config;
    @FXML
    private Label matricule;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            helper.fillComboBox(cmb_config, "SELECT DISTINCT designationMoi FROM tPresence");
        } catch (Exception ex) {
            Logger.getLogger(AddPresenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addPresence(ActionEvent event) {
        try {
            if (!MyWindow.isFieldsempty(txt_mois)) {
                presence.setId(helper.generateID("tPresence", "id"));
                presence.setDesignationMois(txt_mois.getText().trim());
                
                if (helper.update(presence)) {
                    helper.fillComboBox(cmb_config, "SELECT DISTINCT designationMoi FROM tPresence");
                    MyWindow.dialogAvertissement("Message", "Enregistré");
                    MyWindow.initFields(txt_mois);
                }
            } else{
                MyWindow.dialogAvertissement("Message", "Le mois ne doit pas etre vide");
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void configPresence(ActionEvent event) {
        if (!MyWindow.isFieldsempty(cmb_config)) {
            prefs.setPresenceID(cmb_config.getValue());
            MyWindow.dialogAvertissement("Message", "Succes");
        }
    }

}
