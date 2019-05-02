/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.config;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gp.library.database.Database;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import gp.library.utils.SharedPreferences;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ServerController implements Initializable {

    @FXML
    private TextField txtServer;
    @FXML
    private TextField txtDatabase;
    @FXML
    private TextField txtUsername;
    
    SharedPreferences prefs = new SharedPreferences();
    @FXML
    private PasswordField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }
    
    void init(){
        txtServer.setText(prefs.getServerName());
        txtDatabase.setText(prefs.getDatabase());
        txtUsername.setText(prefs.getDatabaseUser());
        txtPassword.setText(prefs.getDatabasePassword());
    }
    
    void saveSettings(){
        prefs.setServerName(txtServer.getText().trim());
        prefs.setDatabaseName(txtDatabase.getText().trim());
        prefs.setDatabaseUser(txtUsername.getText().trim());
        prefs.setDatabasePassword(txtPassword.getText().trim());
    }

    @FXML
    private void saveConnection(ActionEvent event) {
        saveSettings();
        prefs.setConfigServer(Boolean.TRUE);
        ((Stage) txtUsername.getScene().getWindow()).close();
        MyWindow.createWindow(getClass().getResource(MyConstant.LOGIN), "Login", null, Boolean.FALSE);
    }

    @FXML
    private void testConnection(ActionEvent event) {
        try {
            Database.testConnection(
                    txtServer.getText().trim(),
                    txtDatabase.getText().trim(),
                    txtUsername.getText().trim(),
                    txtPassword.getText().trim());
            MyWindow.dialogAvertissement("Message", "Connexion établie");
        } catch (Exception e) {
            MyWindow.dialogAvertissement("Message", e.getMessage());
        }
    }
}
