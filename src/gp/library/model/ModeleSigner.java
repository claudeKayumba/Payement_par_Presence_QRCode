/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gp.library.model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class ModeleSigner {

    private String agent;
    private String presence;
    private Date date;
    private static ModeleSigner instance;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static ModeleSigner getInstance() {
        return instance == null ? new ModeleSigner() : instance;
    }

}
