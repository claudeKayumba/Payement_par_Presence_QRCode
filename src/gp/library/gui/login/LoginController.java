/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.login;

import gp.library.database.dao.DatabaseHelper;
import gp.library.model.ModeleUser;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
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

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Text messageError;

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

}
