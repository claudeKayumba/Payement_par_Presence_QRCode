/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Admin
 */
public class MyWindow {
    
    private static final String IMAGE_LOC = "/gp/library/gui/images/XING_104px.png";
    
    private static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(IMAGE_LOC));
    }

    public static void createWindow(URL URL, String title, Stage parentStage, Boolean resizable) {
        try {
            Parent root = FXMLLoader.load(URL);
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
                stage.initModality(Modality.APPLICATION_MODAL);
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setResizable(resizable);
            stage.setTitle(title);
            MyWindow.setStageIcon(stage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/gp/library/gui/style/style.css");
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void makeURLJira(Node node, URL url) {
        try {
            Node child = FXMLLoader.load(url);
            if (node instanceof StackPane) {
                StackPane contain_area = (StackPane) node;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            } else if (node instanceof VBox) {
                VBox contain_area = (VBox) node;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(child);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeJira(Node container, Node node) {
        try {
            if (container instanceof StackPane) {
                StackPane contain_area = (StackPane) container;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(node);
            } else if (container instanceof VBox) {
                VBox contain_area = (VBox) container;
                contain_area.getChildren().removeAll();
                contain_area.getChildren().setAll(node);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showFormDialog(String title, StackPane rootPane, URL location, JFXDialog.DialogTransition transition) throws IOException {
        JFXDialog dialog;
        Node node = FXMLLoader.load(location);
        JFXDialogLayout dl = new JFXDialogLayout();
//        dl.setPadding(Insets.EMPTY);
        dl.setHeading(new Label(title));
        dl.setBody(node);
        dialog = new JFXDialog(rootPane, dl, transition, false);

        JFXButton ok = new JFXButton("Fermer");
//        ok.getStyleClass().add("dialogButton");
        ok.setOnAction(e -> {
            dialog.close();
        });

        dl.setActions(ok);
        dialog.show(rootPane);

    }

    public static void dialogAvertissement(String titre, String message) {
        Notifications n = Notifications.create()
                .title(titre)
                .text("\n                              " + message)
                .graphic(null)
                .hideAfter(javafx.util.Duration.seconds(2.5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
        n.showWarning();
    }

    public static boolean isFieldsempty(Node... field) {
        int i = 0;
        while (i < field.length) {
            if (field[i] instanceof TextField) {
                TextField text = (TextField) field[i];
                if (text.getText() == null || text.getText().isEmpty()) {
                    break;
                }
                i++;
            } else if (field[i] instanceof PasswordField) {
                PasswordField pass = (PasswordField) field[i];
                if (pass.getText() == null || pass.getText().isEmpty()) {
                    break;
                }
                i++;
            } else if (field[i] instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) field[i];
                if (comboBox.getValue() == null) {
                    break;
                }
                i++;
            } else if (field[i] instanceof DatePicker) {
                DatePicker date = (DatePicker) field[i];
                if (date.getValue() == null) {
                    break;
                }
                i++;
            } else if (field[i] instanceof TextArea) {
                TextArea area = (TextArea) field[i];
                if (area.getText().trim().isEmpty()) {
                    break;
                }
                i++;
            } else if (field[i] instanceof Label) {
                Label txt = (Label) field[i];
                if (txt.getText() == null || txt.getText().isEmpty()) {
                    break;
                }
                i++;
            } else if (field[i] instanceof Text) {
                Text txt = (Text) field[i];
                if (txt.getText() == null) {
                    break;
                }
                i++;
            }
        }
        return i != field.length;
    }

    public static void initFields(Node... field) {
        for (Node f : field) {
            if (f instanceof TextField) {
                TextField text = (TextField) f;
                text.setText(null);
            } else if (f instanceof DatePicker) {
                DatePicker text = (DatePicker) f;
                text.setValue(null);
            } else if (f instanceof TextArea) {
                TextArea text = (TextArea) f;
                text.setText(null);
            } else if (f instanceof ComboBox) {
                ComboBox text = (ComboBox) f;
                text.setValue(null);
            } else if (f instanceof Label) {
                Label txt = (Label) f;
                txt.setText(null);
            } else if (f instanceof Text) {
                Text txt = (Text) f;
                txt.setText(null);
            } else if (f instanceof PasswordField) {
                PasswordField txt = (PasswordField) f;
                txt.setText(null);
            }
        }
    }
}
