/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.agent.list;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.jfoenix.controls.JFXDialog;
import gp.library.database.dao.DatabaseHelper;
import gp.library.gui.agent.AgentController;
import gp.library.gui.agent.update.Update_agentController;
import gp.library.gui.home.HomeController;
import gp.library.gui.qrscanner.QRCodeGenerateController;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class List_agentController implements Initializable {
    DatabaseHelper helper = new DatabaseHelper();
    public static String _matricule;
    public static String _nom;
    public static String _salaire;
    public static String _postnom;
    public static String _genre;
    public static String _dateNaiss;
    public static String _service;
    public static String _fonction;
    
    Writer writer = new MultiFormatWriter();
    
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(List_agentController.class);

    @FXML
    private Label matricule;
    @FXML
    private Label nom;
    @FXML
    private Label salaire;
    @FXML
    private Label postnom;
    @FXML
    private Label genre;
    @FXML
    private Label dateNaiss;
    @FXML
    private Label service;
    @FXML
    private Label fonction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        matricule.setText(_matricule.toUpperCase());
        nom.setText(_nom.toUpperCase());
        postnom.setText(_postnom.toUpperCase());
        salaire.setText(_salaire.toUpperCase());
        genre.setText(_genre.toUpperCase());
        dateNaiss.setText(_dateNaiss.toUpperCase());
        service.setText(_service.toUpperCase());
        fonction.setText(_fonction.toUpperCase());
    }    

    @FXML
    private void updateAgent(ActionEvent event) {
        try {
            Update_agentController.isUpdate = true;
            Update_agentController.fonction.setDesignation(fonction.getText());
            Update_agentController.service.setDesignation(service.getText());
            Update_agentController.agent.setId(matricule.getText());
            Update_agentController.agent.setNom(nom.getText());
            Update_agentController.agent.setPostnom(postnom.getText());
            Update_agentController.agent.setGenre(genre.getText());
            Update_agentController.agent.setDateNaiss(dateNaiss.getText());
            Update_agentController.agent.setSalaire(Double.valueOf(salaire.getText()));
            Update_agentController.agent.setFonction(Update_agentController.fonction);
            Update_agentController.agent.setService(Update_agentController.service);
            
            MyWindow.showFormDialog("Modifier", HomeController.full_container, getClass().getResource(MyConstant.UPDATE_AGENT), JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void deleteData(ActionEvent event){
        try {
            if (helper.deleteFromDatabase("DELETE FROM tAgent WHERE matricule = '"+matricule.getText()+"'")) {
                
                MyWindow.dialogAvertissement("Message", "Supprimé");
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void generateQR(ActionEvent event) {
        Task<BufferedImage> barcodeWriterTask = new Task<BufferedImage>() {
            @Override
            protected BufferedImage call() throws Exception {
                String contents = matricule.getText().trim();
                BarcodeFormat format = (BarcodeFormat) BarcodeFormat.QR_CODE;
                int width = 250;
                int height = 250;
                if(contents == null || (contents != null && contents.isEmpty()) || format == null){
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please enter valid content and barcode format!");
                        alert.setResult(ButtonType.CLOSE);
                        alert.initStyle(StageStyle.UNDECORATED);
                        alert.show();
                    });
                    return null;
                }
                try {
//                    if(format == BarcodeFormat.QR_CODE){
//                        height = 400;
//                    }
                    BufferedImage image = MatrixToImageWriter.toBufferedImage(writer.encode(contents, format, width, height));
                    return image;
                } catch (Exception e) {
                    Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("Cannot generate barcode reason : " + e.getMessage());
                                alert.setResult(ButtonType.CLOSE);
                                alert.initStyle(StageStyle.UNDECORATED);
                                alert.show();
                    });
                    logger.error("Exception : " + e.getMessage());
                    return null;
                }
            }
        };

        barcodeWriterTask.setOnSucceeded(evt -> {
            BufferedImage result = barcodeWriterTask.getValue();
            if(result != null){
                openBarcodeModalWindow(result);
            }
        });

        new Thread(barcodeWriterTask).start();
    }

    private void openBarcodeModalWindow(BufferedImage result) {
        WritableImage image = SwingFXUtils.toFXImage(result, null);
        QRCodeGenerateController.image = image;
        
        MyWindow.createWindow(getClass().getResource(MyConstant.QRCODE_GENERATE), "Générer un QRCode", null, Boolean.FALSE);
    }
    
}
