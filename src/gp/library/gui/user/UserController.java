/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import gp.library.database.dao.DatabaseHelper;
import gp.library.model.ModeleUser;
import gp.library.utils.MyWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author Jenny
 */
public class UserController implements Initializable {

    DatabaseHelper helper = new DatabaseHelper();
    ModeleUser user = ModeleUser.getInstance();
    @FXML
    private JFXTextField txt_id;
    @FXML
    private JFXTextField txt_username;
    @FXML
    private JFXTextField txt_password;
    @FXML
    private JFXListView<JFXButton> list_users;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            helper.fillListView(list_users,FontAwesome.Glyph.USER, "SELECT code as id, CONCAT(username,' : ',passwords) as designation FROM tUsers");
        } catch (Exception e) {
        }
    }

    @FXML
    private void initFields(ActionEvent event) {
        MyWindow.initFields(txt_id, txt_username, txt_password);
    }

    @FXML
    private void showPopup(MouseEvent event) {
    }

    @FXML
    private void saveData(ActionEvent event) {
        try {
            if (!MyWindow.isFieldsempty(txt_id, txt_username, txt_password)) {
                user.setId(Integer.valueOf(txt_id.getText().trim()));
                user.setUsername(txt_username.getText().trim());
                user.setPassword(txt_password.getText().trim());

                if (helper.update(user)) {
                    helper.fillListView(list_users,FontAwesome.Glyph.USER, "SELECT code as id, CONCAT(username,' : ',passwords) as designation FROM tUsers");
                    MyWindow.dialogAvertissement("Message", "Enregistré");
                }
            } else {
                MyWindow.dialogAvertissement("Avertissement", "Completez les champs vides");
            }
        } catch (Exception e) {
        }
    }

}
