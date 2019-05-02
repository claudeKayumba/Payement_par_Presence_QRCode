/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.service;

import com.jfoenix.controls.JFXTextField;
import gp.library.database.dao.DatabaseHelper;
import gp.library.gui.settings.SettingsController;
import static gp.library.gui.settings.SettingsController.liste_fonction;
import static gp.library.gui.settings.SettingsController.liste_service;
import gp.library.model.ModeleFonctionService;
import gp.library.model.ModeleService;
import gp.library.utils.MyWindow;
import gp.library.utils.SharedPreferences;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ServiceFonctionController implements Initializable {

    ModeleFonctionService fonction = ModeleFonctionService.getInstance();
    ModeleService service = ModeleService.getInstance();
    DatabaseHelper helper = new DatabaseHelper();
    SharedPreferences prefs = new SharedPreferences();

    public static int etat_save;
    public static JFXTextField txtdesignation;

    @FXML
    private JFXTextField txt_designation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtdesignation = txt_designation;
        txtdesignation.setText(prefs.getValueForUpdate());
    }

    @FXML
    private void initFields(ActionEvent event) {
        MyWindow.initFields(txt_designation);
    }

    @FXML
    private void saveData(ActionEvent event) {
        try {
            if (!MyWindow.isFieldsempty(txt_designation)) {
                fonction.setCode(Integer.valueOf(prefs.getIdForUpdate()));
                fonction.setDesignation(txt_designation.getText().trim());
                fonction.setEtat(etat_save);
                if (helper.update(fonction)) {
                    MyWindow.dialogAvertissement("Message", "Enregistré");
                    MyWindow.initFields(txt_designation);
                    prefs.setIdForUpdate("0");
                    prefs.removeValueForUpdate();
                    if (etat_save == 1) {
                        helper.fillListView(SettingsController.liste_fonction,FontAwesome.Glyph.DESKTOP, "SELECT DISTINCT code, designation FROM tFonction");
                    }
                    helper.fillListView(SettingsController.liste_service,FontAwesome.Glyph.COGS, "SELECT DISTINCT code, designation FROM tService");
                }
            } else {
                MyWindow.dialogAvertissement("Avertissement", "Completez la designation");
            }
        } catch (Exception e) {
            MyWindow.dialogAvertissement("Avertissement", e.getMessage());
        }
    }

}
