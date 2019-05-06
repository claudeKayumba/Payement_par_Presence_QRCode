/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.utils;

import java.util.prefs.Preferences;

/**
 *
 * @author Admin
 */
public class SharedPreferences {

    private Preferences prefs = Preferences.userNodeForPackage(SharedPreferences.class);

    public String getServerName() {
        System.out.println(prefs.absolutePath());
        return this.prefs.get("serverName", "localhost");
    }

    public void setServerName(String serverName) {
        this.prefs.put("serverName", serverName);
    }

    public String getDatabase() {
        return this.prefs.get("databaseName", "master");
    }

    public void setDatabaseName(String name) {
        this.prefs.put("databaseName", name);
    }

    public String getDatabaseUser() {
        return this.prefs.get("databaseUser", "sa");
    }

    public void setDatabaseUser(String user) {
        this.prefs.put("databaseUser", user);
    }

    public String getDatabasePassword() {
        return this.prefs.get("databasePassword", "1234");
    }

    public void setDatabasePassword(String password) {
        this.prefs.put("databasePassword", password);
    }

    public void setUserLog(Boolean isLog) {
        this.prefs.putBoolean("userLog", isLog);
    }

    public Boolean getUserLog() {
        return this.prefs.getBoolean("userLog", false);
    }
    
    public void setIdForUpdate(String id) {
        this.prefs.put("ID", id);
    }
    
    public String getIdForUpdate() {
        return this.prefs.get("ID", "0");
    }
    
    public void setValueForUpdate(String val) {
        this.prefs.put("value", val);
    }
    
    public String getValueForUpdate() {
        return this.prefs.get("value", "");
    }
    
    public void removeValueForUpdate() {
        prefs.remove("value");
    }
    
    public void setConfigServer(Boolean isConfig){
        this.prefs.putBoolean("isConfig", isConfig);
    }
    
    public Boolean isConfigServer(){
        return this.prefs.getBoolean("isConfig", false);
    }
    
    public void setRememberMe(Boolean isRemember){
        this.prefs.putBoolean("remeberMe", isRemember);
    }
    
    public Boolean isRememberMe(){
        return this.prefs.getBoolean("remeberMe", false);
    }
    
    public String getUserName() {
        return this.prefs.get("userName", "");
    }

    public void setUserName(String userName) {
        this.prefs.put("userName", userName);
    }
    
    public String getUserPass() {
        return this.prefs.get("userPass", "");
    }

    public void setUserPass(String userPass) {
        this.prefs.put("userPass", userPass);
    }
}
