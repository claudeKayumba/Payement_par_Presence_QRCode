/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.presence;

import com.jfoenix.controls.JFXDialog;
import gp.library.gui.home.HomeController;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class PresenceController implements Initializable {

    @FXML
    private TextField txtUsername1;
    @FXML
    private VBox box_container;
    @FXML
    private Pagination pagination;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    void scanQRCode(ActionEvent event) {
        MyWindow.createWindow(getClass().getResource(MyConstant.QRCODE_CAMERA_SCANNER), "Presence QRCode", new Stage(), Boolean.FALSE);
    }

    @FXML
    private void config(ActionEvent event) throws IOException {
        MyWindow.showFormDialog("Configurer Presence", HomeController.full_container, getClass().getResource(MyConstant.ADD_PRESENCE_ID), JFXDialog.DialogTransition.CENTER);
    }
    
}
