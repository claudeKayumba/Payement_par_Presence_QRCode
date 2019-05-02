/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.agent.update;

import com.github.javafaker.Faker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import gp.library.database.dao.DatabaseHelper;
import gp.library.model.ModeleAgent;
import gp.library.model.ModeleFonctionService;
import gp.library.model.ModeleService;
import gp.library.utils.MyWindow;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Update_agentController implements Initializable {

    DatabaseHelper helper = new DatabaseHelper();
    public static ModeleAgent agent = ModeleAgent.getInstance();
    public static ModeleService service = ModeleService.getInstance();
    public static ModeleFonctionService fonction = ModeleFonctionService.getInstance();
    public static boolean isUpdate = false;

    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXComboBox<String> cmb_fonction;
    @FXML
    private JFXComboBox<String> cmb_service;
    @FXML
    private JFXTextField txt_nom;
    @FXML
    private JFXTextField txt_postnom;
    @FXML
    private JFXDatePicker txt_date;
    @FXML
    private JFXRadioButton rd_m;
    @FXML
    private JFXRadioButton rd_f;
    @FXML
    private JFXTextField txt_salaire;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    private void init() {
        try {
            setData();
            helper.fillComboBox(cmb_fonction, "SELECT DISTINCT designation FROM tFonction");
            helper.fillComboBox(cmb_service, "SELECT DISTINCT designation FROM tService");
        } catch (Exception ex) {
            Logger.getLogger(Update_agentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setData(){
        if (isUpdate) {
            agent.setId(agent.getId());
            txt_nom.setText(agent.getNom());
            txt_postnom.setText(agent.getPostnom());
            txt_date.setValue(helper.LOCAL_DATE(agent.getDateNaiss()));
            txt_salaire.setText(""+agent.getSalaire());
            cmb_fonction.setValue(agent.getFonction().getDesignation());
            cmb_service.setValue(agent.getService().getDesignation());
            gender.selectToggle(agent.getGenre() == "M" ? rd_m : rd_f);
        }else{
            agent.setId(0);
        }
        
    }
    
    @FXML
    private void initFields(ActionEvent event) {
        MyWindow.initFields(txt_nom, txt_postnom, txt_date, txt_salaire, cmb_fonction, cmb_service);
        gender.selectToggle(null);
    }

    @FXML
    private void saveData(ActionEvent event) {
//        test();
        if (!MyWindow.isFieldsempty(txt_nom, txt_postnom, txt_date, txt_salaire, cmb_fonction, cmb_service)) {
            try {
                service.setDesignation(cmb_service.getSelectionModel().getSelectedItem());
                fonction.setDesignation(cmb_fonction.getSelectionModel().getSelectedItem());

                agent.setNom(txt_nom.getText().trim());
                agent.setPostnom(txt_postnom.getText().trim());
                agent.setDateNaiss(txt_date.getValue().toString());
                agent.setGenre(rd_m.isSelected() ? "M" : "F");
                agent.setSalaire(Double.valueOf(txt_salaire.getText().trim()));
                agent.setService(service);
                agent.setFonction(fonction);

                if (helper.update(agent)) {
                    MyWindow.dialogAvertissement("Message", "Enregistré");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Update_agentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Update_agentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            MyWindow.dialogAvertissement("Avertissement", "Completez les champs vides");
        }
    }

    private void test() {
        try {
            Faker data = new Faker(Locale.FRANCE);
            for (int i = 1; i <= 25; i++) {
                service.setDesignation(cmb_service.getSelectionModel().getSelectedItem());
                fonction.setDesignation(cmb_fonction.getSelectionModel().getSelectedItem());
                agent.setId(i);
                agent.setNom(data.name().firstName());
                agent.setPostnom(data.name().lastName());
                agent.setDateNaiss(txt_date.getValue().toString());
                agent.setGenre(rd_m.isSelected() ? "M" : "F");
                agent.setSalaire(data.number().numberBetween(500, 10000));
                agent.setService(service);
                agent.setFonction(fonction);

                if (helper.update(agent)) {
                    System.out.println("ok");
                }else{
                    System.out.println("pas ok");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
