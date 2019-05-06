/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.login;

import com.jfoenix.controls.JFXCheckBox;
import gp.library.database.dao.DatabaseHelper;
import gp.library.model.ModeleUser;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LoginController implements Initializable {

    DatabaseHelper helper = new DatabaseHelper();
    ModeleUser user = ModeleUser.getInstance();
    SharedPreferences prefs = new SharedPreferences();

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Text messageError;
    @FXML
    private JFXCheckBox rd_remember;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        messageError.setText("");
    }

    @FXML
    private void connectUser(ActionEvent event) {
        try {
            if (!MyWindow.isFieldsempty(txtUsername, txtPassword)) {
            user.setUsername(txtUsername.getText().trim());
            user.setPassword(txtPassword.getText().trim());
            if (helper.connectUser(user)) {
                prefs.setUserName(txtUsername.getText().trim());
                prefs.setUserPass(txtPassword.getText().trim());
                rememberMe();
                ((Stage) txtUsername.getScene().getWindow()).close();
                MyWindow.createWindow(getClass().getResource(MyConstant.HOME), "Gestion Présence", null, Boolean.TRUE);
            } else {
                messageError.setText("Nom d'utilisateur ou Mot de passe incorect");
//                MyWindow.dialogAvertissement("Avertissement", "Nom d'utilisateur ou Mot de passe incorect");
            }
        } else {
            MyWindow.dialogAvertissement("Avertissement", "Completez les champs vides");
        }
        } catch (Exception e) {
            MyWindow.dialogAvertissement("Erreur", e.getMessage());
        }
    }
    
    private void rememberMe() {
        if (rd_remember.isSelected()) {
            prefs.setRememberMe(Boolean.TRUE);
        } else{
            prefs.setRememberMe(Boolean.FALSE);
        }
    }

}
