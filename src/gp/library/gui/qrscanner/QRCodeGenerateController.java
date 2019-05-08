/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.qrscanner;

import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class QRCodeGenerateController implements Initializable {

    public static WritableImage image;

    @FXML
    private ImageView imageQR;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageQR.setImage(image);
    }

    @FXML
    private void generateQR(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("IMAGE files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog((Stage) imageQR.getScene().getWindow());

            if (file != null) {
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(renderedImage,"png",file);
            }
        } catch (Exception e) {
        }
    }

}
