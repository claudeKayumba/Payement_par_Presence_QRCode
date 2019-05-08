/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.gui.home;

import com.jfoenix.controls.JFXButton;
import gp.library.utils.DoughnutChat;
import gp.library.utils.MyConstant;
import gp.library.utils.MyWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {

    JFXButton lastButton = new JFXButton();;
    public static StackPane full_container;
    @FXML
    private StackPane container;
    @FXML
    private VBox dashboard;
    @FXML
    private Pane piechart_pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        full_container = container;
        initPieChart();
    }

    private void initPieChart() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Data1", 10),
                new PieChart.Data("Data2", 25),
                new PieChart.Data("Data3", 30),
                new PieChart.Data("Data4", 40)
        );

        final DoughnutChat doughnutChat = new DoughnutChat(data);
        piechart_pane.getChildren().add(doughnutChat);
    }
    
    private void setButtonStyle(JFXButton currentButton){
        currentButton.setPrefHeight(45);
        lastButton.getStyleClass().setAll("side-menu-btn");
        
        currentButton.getStyleClass().setAll("side-menu-btn-active");
        
        lastButton = currentButton;
    }

    @FXML
    private void goToAgent(ActionEvent event) {
        setButtonStyle((JFXButton) event.getSource());
        MyWindow.makeURLJira(container, getClass().getResource(MyConstant.AGENT));
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        setButtonStyle((JFXButton) event.getSource());
        MyWindow.makeJira(container, dashboard);
    }
    
    @FXML
    private void goToPresence(ActionEvent event) {
        setButtonStyle((JFXButton) event.getSource());
        MyWindow.makeURLJira(container, getClass().getResource(MyConstant.PRESENCE));
    }

    @FXML
    private void goToSettings(ActionEvent event) {
        setButtonStyle((JFXButton) event.getSource());
        MyWindow.makeURLJira(container, getClass().getResource(MyConstant.SETTING));
    }

    @FXML
    private void gotToUsers(ActionEvent event) {
        setButtonStyle((JFXButton) event.getSource());
        MyWindow.makeURLJira(container, getClass().getResource(MyConstant.USER));
    }

    @FXML
    private void goToPayement(ActionEvent event) {
        setButtonStyle((JFXButton) event.getSource());
        MyWindow.makeURLJira(container, getClass().getResource(MyConstant.PAYEMENT));
    }

}
