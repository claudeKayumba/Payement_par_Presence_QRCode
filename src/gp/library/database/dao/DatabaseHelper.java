/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.database.dao;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import gp.library.database.Database;
import gp.library.model.ModeleAgent;
import gp.library.model.ModeleAvantageRetenu;
import gp.library.model.ModeleFonctionService;
import gp.library.model.ModeleService;
import gp.library.model.ModeleUser;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.FontAwesome.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

/**
 *
 * @author Admin
 */
public class DatabaseHelper {

    PreparedStatement pst;
    ResultSet rs;

    public boolean update(Object obj) throws ClassNotFoundException, SQLException {

        if (obj instanceof ModeleFonctionService) {
            ModeleFonctionService fonctionService = (ModeleFonctionService) obj;

            pst = Database.getConnection().prepareCall("EXEC sp_update_fonction_service ?,?,?");
            pst.setInt(1, fonctionService.getCode());
            pst.setString(2, fonctionService.getDesignation());
            pst.setInt(3, fonctionService.getEtat());

            pst.executeUpdate();

            return true;
        } else if (obj instanceof ModeleAvantageRetenu) {
            ModeleAvantageRetenu ar = (ModeleAvantageRetenu) obj;

            pst = Database.getConnection().prepareCall("EXEC sp_update_avantage_retenu ?,?,?,?,?");
            pst.setInt(1, Integer.valueOf(ar.getId()));
            pst.setString(2, ar.getDesignation());
            pst.setDouble(3, ar.getTaux());
            pst.setDouble(4, ar.getMontant());
            pst.setInt(5, ar.getEtat());

            pst.executeUpdate();

            return true;
        } else if (obj instanceof ModeleAgent) {
            ModeleAgent agent = (ModeleAgent) obj;

            pst = Database.getConnection().prepareCall("EXEC sp_update_agent ?,?,?,?,?,?,?,?");
            pst.setInt(1, agent.getId());
            pst.setString(2, agent.getNom());
            pst.setString(3, agent.getPostnom());
            pst.setDate(4, Date.valueOf(agent.getDateNaiss()));
            pst.setString(5, agent.getGenre());
            pst.setDouble(6, agent.getSalaire());
            pst.setString(7, agent.getService().getDesignation());
            pst.setString(8, agent.getFonction().getDesignation());

            pst.executeUpdate();

            return true;
        } else if (obj instanceof ModeleUser) {
            ModeleUser user = (ModeleUser) obj;

            pst = Database.getConnection().prepareCall("EXEC sp_update_user ?,?,?");
            pst.setInt(1, user.getId());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getPassword());

            pst.executeUpdate();

            return true;
        }
        return false;
    }

    public void fillListView(JFXListView<JFXButton> listView,Glyph iconName, String SQL) throws Exception {
        ObservableList<JFXButton> list = FXCollections.observableArrayList();
        rs = Database.getConnection().createStatement().executeQuery(SQL);
        String code = "";
        listView.getItems().clear();
        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                //Iterate Column from database
                GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");
                if (i % 2 == 0) {
                    JFXButton button = new JFXButton(rs.getObject(i).toString());
                    button.setAccessibleText(code);
                    button.setPrefWidth(250);
                    button.setContentDisplay(ContentDisplay.LEFT);
                    button.setAlignment(Pos.CENTER_LEFT);
                    button.setGraphicTextGap(10);
                    button.setGraphic(fontAwesome.create(iconName).color(Color.CHOCOLATE).size(20));
                    button.setStyle("-fx-background-color:transparent;");
                    list.add(button);
                } else {
                    code = rs.getObject(i).toString().toString();
                }

            }
        }
        listView.setItems(list);
    }

    public void fillComboBox(ComboBox comboBox, String SQL) throws Exception {
        ObservableList<Object> list = FXCollections.observableArrayList();
        rs = Database.getConnection().createStatement().executeQuery(SQL);
        comboBox.getItems().clear();
        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                //Iterate Column from database
                list.add(rs.getString(i).toUpperCase());
            }
        }
        comboBox.setItems(list);
    }

    public ObservableList<ModeleAgent> getAgentList(String SQL) throws Exception {
        ObservableList<ModeleAgent> list = FXCollections.observableArrayList();
        list.removeAll();
        rs = Database.getConnection().createStatement().executeQuery(SQL);
        while (rs.next()) {
            //Iterate Column from database
            ModeleAgent agent = new ModeleAgent();
            ModeleFonctionService fonction = new ModeleFonctionService();
            fonction.setDesignation(rs.getString("fonction"));
            ModeleService service = new ModeleService();
            service.setDesignation(rs.getString("service"));

            agent.setId(rs.getInt("id"));
            agent.setNom(rs.getString("nom"));
            agent.setPostnom(rs.getString("postnom"));
            agent.setGenre(rs.getObject("genre").toString());
            agent.setSalaire(rs.getDouble("salaire"));
            agent.setFonction(fonction);
            agent.setService(service);
            agent.setDateNaiss(rs.getObject("dateNaiss").toString());
            list.add(agent);
        }

        return list;
    }

    public boolean deleteFromDatabase(String sql) {
        try {
            pst = Database.getConnection().prepareStatement(sql);
            pst.executeUpdate();
            return true;

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int generateID(String table, String attribut) throws Exception {
        try {
            pst = Database.getConnection().prepareStatement("SELECT COALESCE(MAX("+attribut+"),0)+1 FROM " + table + "");
            rs = pst.executeQuery();
            if (rs.next()) {
                return (rs.getInt(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new Exception(e.getMessage());
        }
        return 0;
    }

    public boolean connectUser(ModeleUser user) throws SQLException, ClassNotFoundException {
        pst = Database.getConnection().prepareStatement("SELECT * FROM tUsers WHERE username = '" + user.getUsername() + "' and passwords = '" + user.getPassword() + "'");
        rs = pst.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
//    public static SwingNode Repport(String path, String querry){
//        SwingNode swingNode = null;
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    java.nio.file.Path currentRelativePath = Paths.get("");
//                    String s = currentRelativePath.toAbsolutePath().toString();
//                    JasperDesign jasperDesign = JRXmlLoader.load(s + path);
//                    
//                    JRDesignQuery jrquery = new JRDesignQuery();
//                    jrquery.setText(querry);
//                    jasperDesign.setQuery(jrquery);
//                    
//                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                    JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnect.getConnection());
//                    //JRViewer viewer = new JRViewer(JasperPrint);
//                    
//                    swingNode.setContent(new JRViewer(JasperPrint));
//                    
//                } catch (JRException | SQLException | ClassNotFoundException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//        return swingNode;
//    }
}
