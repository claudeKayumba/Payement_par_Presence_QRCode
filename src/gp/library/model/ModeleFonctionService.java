/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.model;

/**
 *
 * @author Admin
 */
public class ModeleFonctionService {
    private int code, etat;
    private String designation;
    private static ModeleFonctionService instance;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation == null ? "" : designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    public static ModeleFonctionService getInstance() {
        return instance == null ? new ModeleFonctionService() : instance;
    }
    
}
