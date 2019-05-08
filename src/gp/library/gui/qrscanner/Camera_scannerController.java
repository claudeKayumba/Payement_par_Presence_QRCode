/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.qrscanner;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import gp.library.database.dao.DatabaseHelper;
import gp.library.model.ModeleAgent;
import gp.library.model.ModeleSigner;
import gp.library.utils.MyWindow;
import gp.library.utils.SharedPreferences;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Camera_scannerController implements Initializable {

    DatabaseHelper helper = new DatabaseHelper();
    ModeleSigner signer = ModeleSigner.getInstance();
    ObservableList<ModeleAgent> listAgent;
    SharedPreferences prefs = new SharedPreferences();
    private static Logger logger = LoggerFactory.getLogger(Camera_scannerController.class);

    @FXML
    private Label lblStatus;
    @FXML
    private AnchorPane webcamContainerAnchorPane;
    @FXML
    private Text genre;
    @FXML
    private Text matricule;
    @FXML
    private Text service;
    @FXML
    private Text fonction;
    @FXML
    private Text nom;
    @FXML
    private Text postnom;

    private Webcam defaultWebcam = null;
    private WebcamPanel defaultWebcamPanel = null;
    private final SwingNode defaultWebcamPanelNode = new SwingNode();
    Writer writer = new MultiFormatWriter();

    private Runnable barcodeScannerRunnable = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        defaultWebcam = Webcam.getDefault();
        defaultWebcam.setViewSize(WebcamResolution.QVGA.getSize());
        defaultWebcamPanel = new WebcamPanel(defaultWebcam, true);
        creatDefaultWebcamPanel(defaultWebcamPanelNode);
        init();
    }

    private void creatDefaultWebcamPanel(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            if (defaultWebcamPanel != null) {
                swingNode.setContent(defaultWebcamPanel);
            }
        });
    }

    public void init() {
        initUI();
        barcodeScannerRunnable = getBarcodeReaderThread();
        new Thread(barcodeScannerRunnable).start();
    }

    private void initUI() {
        Platform.runLater(() -> {
            webcamContainerAnchorPane.getChildren().clear();
            webcamContainerAnchorPane.getChildren().add(defaultWebcamPanelNode);
        });

    }

    private Runnable getBarcodeReaderThread() {
        return () -> {
            logger.info("Starting Barcode Reader Thread");
            BufferedImage image = null;
            Reader reader = new MultiFormatReader();
            Result lastResult = null;
            if (defaultWebcam != null) {
                while (defaultWebcam.isOpen()) {
                    if ((image = defaultWebcam.getImage()) == null) {
                        continue;
                    }
                    try {
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        final Result result = reader.decode(bitmap);
                        if (result != null && result.getText() != null) {
                            if (lastResult == null) {
                                logger.info("Barcode text is " + result.getText());
                                Platform.runLater(() -> getResult(result.getText()));
                            } else if (lastResult.getText() != null && !lastResult.getText().equals(result.getText())) {
                                logger.info("Barcode text is " + result.getText());
                                Platform.runLater(() -> getResult(result.getText()));
                            }
                        }
                        lastResult = result;
                    } catch (NotFoundException notEx) {

                    } catch (Exception ex) {
                        logger.error("Exception while reading barcode : ", ex);
                    }

                }
            }
        };
    }

    private void getResult(String result) {
        try {
            listAgent = helper.getAgentList("select * from v_liste_agent where matricule='" + result + "'");
            signer.setAgent(result);
            signer.setPresence(prefs.getPresenceID());
            signer.setDate(Date.valueOf(LocalDate.now()));
            MyWindow.initFields(matricule, nom, postnom, genre, fonction, service);
            if (helper.update(signer)) {
                matricule.setText(result);
                nom.setText(listAgent.get(0).getNom());
                postnom.setText(listAgent.get(0).getPostnom());
                genre.setText(listAgent.get(0).getGenre());
                fonction.setText(listAgent.get(0).getFonction().getDesignation());
                service.setText(listAgent.get(0).getService().getDesignation());
            }

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Camera_scannerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void fillData(ActionEvent event) {
        onClose();
    }

    public void onClose() {
        defaultWebcam.close();
        logger.info("Closing Application Root Controller");
        ((Stage) lblStatus.getScene().getWindow()).close();
    }

}
