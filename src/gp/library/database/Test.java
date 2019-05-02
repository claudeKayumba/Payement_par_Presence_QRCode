/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.database;

import com.github.javafaker.Faker;
import gp.library.database.dao.DatabaseHelper;
import gp.library.model.ModeleFonctionService;
import gp.library.model.ModeleService;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Test {

    public static void main(String[] args) {
        Faker data = new Faker(Locale.FRANCE);
        ModeleService service = ModeleService.getInstance();
        DatabaseHelper helper = new DatabaseHelper();
        for (int i = 1; i < 20; i++) {
            try {
                service.setCode(""+i);
                service.setDesignation(data.company().industry());
                
                if (helper.update(service)) {
                    System.out.println("ok");
                } else {
                    System.out.println("pas ok");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
