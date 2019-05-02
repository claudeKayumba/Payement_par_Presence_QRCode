/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.agent;

import com.jfoenix.controls.JFXDialog;
import gp.library.database.dao.DatabaseHelper;
import gp.library.gui.agent.list.List_agentController;
import gp.library.gui.agent.update.Update_agentController;
import gp.library.gui.home.HomeController;
import gp.library.model.ModeleAgent;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.dialog.ProgressDialog;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AgentController implements Initializable {

    DatabaseHelper helper = new DatabaseHelper();
    ObservableList<ModeleAgent> listAgent;

    private VBox table_list;
    @FXML
    private TextField txtUsername1;
    @FXML
    private Pagination pagination;
    @FXML
    private VBox box_container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    @FXML
    void fillData() {
//        charger();
        init();
    }

    private void init() {
        try {
            listAgent = helper.getAgentList("select * from v_liste_agent");
            
            table_list = new VBox(5);
            table_list.setAlignment(Pos.CENTER);//listAgent.size() % 2 == 0 ? (listAgent.size() / rowsPerPage()) : 
            pagination.setPageCount(listAgent.size() % rowsPerPage() != 0 ? listAgent.size() / rowsPerPage() + 1 : listAgent.size() / rowsPerPage());
            pagination.setCurrentPageIndex(0);
//            pagination = new Pagination((listAgent.size() / rowsPerPage() + 1), 0);
            pagination.setStyle("-fx-border-color:red;");
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    if (pageIndex > listAgent.size() / rowsPerPage() + 1) { //+1
                        return null;
                    } else {
                        return createPage(pageIndex);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void charger() {
        try {
            Service<Boolean> service = new Service<Boolean>() {
                @Override
                protected Task<Boolean> createTask() {
                    return new Task<Boolean>() {
                        @Override
                        protected Boolean call() throws Exception {
                            System.out.println("on start");
                            updateMessage("Connexion...");
                            try {
                                listAgent = helper.getAgentList("select * from v_liste_agent");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            return true;
                        }
                    };
                }

            };
            service.setOnSucceeded(e -> {
                //do your processing
                System.out.println("ok");
            });
            service.setOnFailed(e -> {
                //do your processing
                System.out.println("failed");
            });

            ProgressDialog pd = new ProgressDialog(service);
            pd.setContentText("Please wait while the window loads...");
            pd.initStyle(StageStyle.UNDECORATED);
            pd.initModality(Modality.WINDOW_MODAL);
            pd.initOwner(((Stage) table_list.getScene().getWindow()));
            service.start();
        } catch (Exception e) {
        }
    }

    public int itemsPerPage() {
        return 1;
    }

    public int rowsPerPage() {
        return 10;
    }

    public ScrollPane createPage(int pageIndex) {
        table_list.getChildren().clear();
        int lastIndex = 0;
        int displace = listAgent.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = listAgent.size() / rowsPerPage();
        } else {
            lastIndex = listAgent.size() / rowsPerPage() - 1;
        }

        int page = pageIndex * itemsPerPage();
        ScrollPane spane = new ScrollPane();
        table_list.setPrefWidth(spane.getPrefWidth());
        for (int i = page; i < page + itemsPerPage(); i++) {

            if (lastIndex == pageIndex) {
                for (int j = pageIndex * rowsPerPage(); j < listAgent.size(); j++) { //pageIndex * rowsPerPage() + displace
                    setFieldsData(
                            listAgent.get(j).getId(),
                            listAgent.get(j).getNom(),
                            listAgent.get(j).getPostnom(),
                            listAgent.get(j).getGenre(),
                            listAgent.get(j).getDateNaiss(),
                            listAgent.get(j).getSalaire(),
                            listAgent.get(j).getFonction().getDesignation(),
                            listAgent.get(j).getService().getDesignation()
                    );
                }
            } else {
                for (int j = pageIndex * rowsPerPage(); j < pageIndex * rowsPerPage() + rowsPerPage(); j++) {
                    setFieldsData(
                            listAgent.get(j).getId(),
                            listAgent.get(j).getNom(),
                            listAgent.get(j).getPostnom(),
                            listAgent.get(j).getGenre(),
                            listAgent.get(j).getDateNaiss(),
                            listAgent.get(j).getSalaire(),
                            listAgent.get(j).getFonction().getDesignation(),
                            listAgent.get(j).getService().getDesignation()
                    );
                }
            }

            spane = new ScrollPane(table_list);
        }

        return spane;
    }

    private void setFieldsData(Object... data) {
        try {
            List_agentController._matricule = data[0].toString();
            List_agentController._nom = data[1].toString();
            List_agentController._postnom = data[2].toString();
            List_agentController._genre = data[3].toString();
            List_agentController._dateNaiss = data[4].toString();
            List_agentController._salaire = data[5].toString();
            List_agentController._fonction = data[6].toString();
            List_agentController._service = data[7].toString();
            
            Node node = FXMLLoader.load(getClass().getResource(MyConstant.LIST_AGENT));
            table_list.getChildren().add(node);
        } catch (IOException ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveAgent(ActionEvent event) {
        try {
            Update_agentController.isUpdate = false;
            MyWindow.showFormDialog("Ajouter un nouvel Agent", HomeController.full_container, getClass().getResource(MyConstant.UPDATE_AGENT), JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(AgentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
