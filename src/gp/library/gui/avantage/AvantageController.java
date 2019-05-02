/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.avantage;

import com.jfoenix.controls.JFXTextField;
import gp.library.database.dao.DatabaseHelper;
import gp.library.gui.settings.SettingsController;
import static gp.library.gui.settings.SettingsController.list_avantage;
import static gp.library.gui.settings.SettingsController.list_retenu;
import gp.library.model.ModeleAvantageRetenu;
import gp.library.utils.MyWindow;
import gp.library.utils.SharedPreferences;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AvantageController implements Initializable {

    ModeleAvantageRetenu av = ModeleAvantageRetenu.getInstance();
    DatabaseHelper helper = new DatabaseHelper();
    public static int etat_save = 0;
    SharedPreferences prefs = new SharedPreferences();

    @FXML
    private JFXTextField txt_designation;
    @FXML
    private JFXTextField txt_taux;
    @FXML
    private JFXTextField txt_montant;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt_designation.setText(prefs.getValueForUpdate());
    }

    @FXML
    private void initFields(ActionEvent event) {

    }

    @FXML
    private void saveData(ActionEvent event) {
        try {
            if (!MyWindow.isFieldsempty(txt_designation, txt_taux, txt_montant)) {
                av.setId(Integer.valueOf(prefs.getIdForUpdate()));
                av.setDesignation(txt_designation.getText().trim());
                av.setTaux(Double.valueOf(txt_taux.getText().trim()));
                av.setMontant(Double.valueOf(txt_montant.getText().trim()));
                av.setEtat(etat_save);

                if (helper.update(av)) {
                    MyWindow.dialogAvertissement("Message", "Enregistré");
                    if (etat_save == 1) {
                        helper.fillListView(SettingsController.list_avantage,FontAwesome.Glyph.PLUS_CIRCLE, "SELECT DISTINCT id as code, designation FROM tAvantage");
                    } else {
                        helper.fillListView(SettingsController.list_retenu,FontAwesome.Glyph.MAIL_REPLY_ALL, "SELECT DISTINCT id as code, designation FROM tRetenu");
                    }
                }
            } else{
                MyWindow.dialogAvertissement("Avertissement", "Completez tous les champs");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvantageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AvantageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvantageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void checkTaux(KeyEvent event) {
        
    }

}
