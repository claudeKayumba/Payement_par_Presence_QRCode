/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.presence;

import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
    private void fillData(ActionEvent event) {
    }

    @FXML
    void scanQRCode(ActionEvent event) {
        MyWindow.createWindow(getClass().getResource(MyConstant.QRCODE_CAMERA_SCANNER), "Presence QRCode", null, Boolean.FALSE);
    }
    
}
