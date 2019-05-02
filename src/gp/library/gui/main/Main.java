package gp.library.gui.main;

import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import gp.library.utils.SharedPreferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

public class Main extends Application{
    SharedPreferences prefs = new SharedPreferences();
    @Override
    public void start(Stage stage) throws Exception {
        if (prefs.isConfigServer()) {
            MyWindow.createWindow(getClass().getResource(MyConstant.LOGIN), "Login", null, Boolean.FALSE);
        }else{
            MyWindow.createWindow(getClass().getResource(MyConstant.SERVER), "Configuration de Serveur", null, Boolean.FALSE);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
