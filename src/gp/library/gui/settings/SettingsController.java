/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.settings;

import com.jfoenix.controls.*;
import gp.library.database.dao.DatabaseHelper;
import gp.library.gui.agent.AgentController;
import gp.library.gui.avantage.AvantageController;
import gp.library.gui.home.HomeController;
import gp.library.gui.service.ServiceFonctionController;
import gp.library.model.ModeleAvantageRetenu;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import gp.library.utils.SharedPreferences;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SettingsController implements Initializable {

    DatabaseHelper helper = new DatabaseHelper();
    ModeleAvantageRetenu avantageRetenu = ModeleAvantageRetenu.getInstance();
    private String definition = "";
    SharedPreferences prefs = new SharedPreferences();

    public static JFXListView<JFXButton> list_avantage;
    public static JFXListView<JFXButton> list_retenu;
    public static JFXListView<JFXButton> liste_service;
    public static JFXListView<JFXButton> liste_fonction;

    

    @FXML
    private JFXListView<JFXButton> listAvantage;
    private JFXPopup menu_popup;
    @FXML
    private JFXListView<JFXButton> listRetenu;
    @FXML
    private JFXListView<JFXButton> list_service;
    @FXML
    private JFXListView<JFXButton> list_fonction;
    @FXML
    private AnchorPane containt_pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        list_avantage = listAvantage;
        list_retenu = listRetenu;
        liste_service = list_service;
        liste_fonction = list_fonction;
        init();
    }

    private void init() {
        try {
            helper.fillListView(liste_fonction,FontAwesome.Glyph.DESKTOP, "SELECT DISTINCT code, designation FROM tFonction");
            helper.fillListView(liste_service,FontAwesome.Glyph.COGS, "SELECT DISTINCT code, designation FROM tService");
            helper.fillListView(list_avantage,FontAwesome.Glyph.PLUS_CIRCLE, "SELECT DISTINCT id as code, designation FROM tAvantage");
            helper.fillListView(list_retenu,FontAwesome.Glyph.MAIL_REPLY_ALL, "SELECT DISTINCT id as code, designation FROM tRetenu");
        } catch (Exception ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initPopup(MouseEvent event) {

        menu_popup = new JFXPopup();
        containt_pane.getChildren().add(menu_popup);

        JFXButton add = new JFXButton("Nouveau");
        add.setPadding(new Insets(10));
        add.getStyleClass().add("button-popup-menu");
        add.setPrefWidth(100);

        JFXButton edit = new JFXButton("Modifier");
        edit.setPadding(new Insets(10));
        edit.getStyleClass().add("button-popup-menu");
        edit.setPrefWidth(100);

        JFXButton delete = new JFXButton("Supprimer");
        delete.setPadding(new Insets(10));
        delete.getStyleClass().add("button-popup-menu");
        delete.setPrefWidth(100);

        VBox menuPop = new VBox(add, edit, delete);
        menuPop.setPrefWidth(100);
        menu_popup.setContent(menuPop);

        if (event.getSource() == listAvantage) {
            definition = MyConstant.AVANTAGE;
            menu_popup.setSource(listAvantage);
        } else if (event.getSource() == listRetenu) {
            definition = MyConstant.RETENU;
            menu_popup.setSource(listRetenu);
        } else if (event.getSource() == list_fonction) {
            definition = MyConstant.FONCTION;
            menu_popup.setSource(list_fonction);
        } else if (event.getSource() == list_service) {
            definition = MyConstant.SERVICE;
            menu_popup.setSource(list_service);
        }

        add.setOnAction((e) -> {
            prefs.setIdForUpdate("0");
            prefs.removeValueForUpdate();
            setPopupAction("add", definition);
        });

        edit.setOnAction((e) -> {
            setPopupAction("edit", definition);
        });

        delete.setOnAction((e) -> {
            setPopupAction("delete", definition);
        });
    }

    private void setPopupAction(String action, String definition) {
        try {
            menu_popup.close();
            switch (action) {
                case "add": {
                    switch (definition) {
                        case "AVANTAGE": {
                            AvantageController.etat_save = 1;
                            MyWindow.showFormDialog("Ajouter Avantage", HomeController.full_container, getClass().getResource(MyConstant.AVANTAGERETENU), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                        case "RETENU": {
                            AvantageController.etat_save = 0;
                            MyWindow.showFormDialog("Ajouter Retenu", HomeController.full_container, getClass().getResource(MyConstant.AVANTAGERETENU), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                        case "SERVICE": {
                            ServiceFonctionController.etat_save = 0;
                            MyWindow.showFormDialog("Ajouter Service", HomeController.full_container, getClass().getResource(MyConstant.SERVICEFONCTION), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                        case "FONCTION": {
                            ServiceFonctionController.etat_save = 1;
                            MyWindow.showFormDialog("Ajouter Fonction", HomeController.full_container, getClass().getResource(MyConstant.SERVICEFONCTION), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                    }
                }
                break;
                case "edit": {
                    switch (definition) {
                        case "AVANTAGE": {
                            AvantageController.etat_save = 1;
                            MyWindow.showFormDialog("Modifier Avantage", HomeController.full_container, getClass().getResource(MyConstant.AVANTAGERETENU), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                        case "RETENU": {
                            AvantageController.etat_save = 0;
                            MyWindow.showFormDialog("Modifier Retenu", HomeController.full_container, getClass().getResource(MyConstant.AVANTAGERETENU), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                        case "SERVICE": {
                            ServiceFonctionController.etat_save = 0;
                            MyWindow.showFormDialog("Modifier Service", HomeController.full_container, getClass().getResource(MyConstant.SERVICEFONCTION), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                        case "FONCTION": {
                            ServiceFonctionController.etat_save = 1;
                            MyWindow.showFormDialog("Modifier Fonction", HomeController.full_container, getClass().getResource(MyConstant.SERVICEFONCTION), JFXDialog.DialogTransition.CENTER);
                        }
                        break;
                    }
                }
                break;
                case "delete": {
                    switch (definition) {
                        case "AVANTAGE": {

                        }
                        break;
                        case "RETENU": {

                        }
                        break;
                        case "SERVICE": {

                        }
                        break;
                        case "FONCTION": {

                        }
                        break;
                    }
                }
                break;
            }
        } catch (IOException e) {
        }
    }

    private void saveAvantage(ActionEvent event) {
        try {
            MyWindow.showFormDialog("Avantage", HomeController.full_container, getClass().getResource(MyConstant.AVANTAGERETENU), JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void showPopup(MouseEvent event) {
        try {
            initPopup(event);
            menu_popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX() < 50 ? event.getX() : event.getX() - 50, event.getY() - 50);

            JFXListView<JFXButton> listView = (JFXListView<JFXButton>) event.getSource();
            prefs.setValueForUpdate("");
            JFXButton btn = listView.getSelectionModel().getSelectedItem();
            prefs.setIdForUpdate(btn.getAccessibleText());
            System.out.println(prefs.getIdForUpdate());
            System.out.println(btn.getAccessibleText());

            prefs.setValueForUpdate(btn.getText());
        } catch (Exception e) {
        }
    }
}
