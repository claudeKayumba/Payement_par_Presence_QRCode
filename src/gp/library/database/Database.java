/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.database;

import gp.library.utils.SharedPreferences;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class Database {

    static Connection cnx = null;
    private SharedPreferences prefs = new SharedPreferences();

    public Database() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        cnx = DriverManager.getConnection("jdbc:sqlserver://" + prefs.getServerName() + ";databaseName=" + prefs.getDatabase() + "", prefs.getDatabaseUser(), prefs.getDatabasePassword());
        
    }
    
    public Database(String serveur,String database,String username,String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        cnx = DriverManager.getConnection("jdbc:sqlserver://" + serveur + ";databaseName=" + database + "", username, password);
        
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (cnx == null) {
            new Database();
        }
        return cnx;
    }
    
    public static Connection testConnection(String serveur,String database,String username,String password) throws ClassNotFoundException, SQLException {
        new Database(serveur, database, username, password);
        return cnx;
    }

}
