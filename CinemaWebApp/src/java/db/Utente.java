/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Patrik
 */
public class Utente {
    private int idUtente;
    private String email;
    private String password;
    private double credito;
    private int idRuolo;
    
    public Utente() {}
    
    public Utente(int idUtente, String email, String password, double credito, int idRuolo){
        this.idUtente = idUtente;
        this.email = email;
        this.password = password;
        this.credito = credito;
        this.idRuolo = idRuolo;
    }

    /**
     * @return the idUtente
     */
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * @param idUtente the idUtente to set
     */
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the credito
     */
    public double getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(double credito) {
        this.credito = credito;
    }

    /**
     * @return the idRuolo
     */
    public int getIdRuolo() {
        return idRuolo;
    }

    /**
     * @param idRuolo the idRuolo to set
     */
    public void setIdRuolo(int idRuolo) {
        this.idRuolo = idRuolo;
    }
    
}
