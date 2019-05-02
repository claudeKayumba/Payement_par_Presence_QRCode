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
public class ModeleService {
    private String code, designation;
    private static ModeleService instance;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation == null ? "" : designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public static ModeleService getInstance() {
        return instance == null ? new ModeleService() : instance;
    }
    
}
