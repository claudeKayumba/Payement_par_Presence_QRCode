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
public class ModelePresence {

    private int id;
    private String designationMois;
    private static ModelePresence instance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignationMois() {
        return designationMois;
    }

    public void setDesignationMois(String designationMois) {
        this.designationMois = designationMois;
    }

    public static ModelePresence getInstance() {
        return instance == null ? new ModelePresence() : instance;
    }

}
