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
public class ModeleAgent {

    private int id;
    private String nom, postnom, genre;
    private ModeleFonctionService fonction;
    private ModeleService service;
    private String dateNaiss;
    private double salaire;

    private static ModeleAgent instance;

    public ModeleAgent() {
    }

    public ModeleAgent(int id, String nom, String postnom, String genre, ModeleFonctionService fonction, ModeleService service, String dateNaiss, double salaire) {
        this.id = id;
        this.nom = nom;
        this.postnom = postnom;
        this.genre = genre;
        this.fonction = fonction;
        this.service = service;
        this.dateNaiss = dateNaiss;
        this.salaire = salaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public ModeleService getService() {
        return service;
    }

    public void setService(ModeleService service) {
        this.service = service;
    }

    public ModeleFonctionService getFonction() {
        return fonction;
    }

    public void setFonction(ModeleFonctionService fonction) {
        this.fonction = fonction;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public static ModeleAgent getInstance() {
        return instance == null ? new ModeleAgent() : instance;
    }

}
