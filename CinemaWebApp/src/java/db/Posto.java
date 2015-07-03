/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;

/**
 *
 * @author Stalker
 */
public class Posto implements Serializable {
    private int idPosto;
    private int idSala;
    private int riga;
    private int colonna;
    private boolean esiste;
    
   
        public Posto() {}
    
    public Posto(int idPosto, int idSala, int riga, int colonna, boolean esiste){
        this.idPosto = idPosto;
        this.idSala = idSala;
        this.riga = riga;
        this.colonna = colonna;
        this.esiste = esiste;
    }

    /**
     * @return the idPosto
     */
    public int getIdPosto() {
        return idPosto;
    }

    /**
     * @param idPosto the idPosto to set
     */
    public void setIdPosto(int idPosto) {
        this.idPosto = idPosto;
    }

    /**
     * @return the idSala
     */
    public int getIdSala() {
        return idSala;
    }

    /**
     * @param idSala the idSala to set
     */
    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    /**
     * @return the riga
     */
    public int getRiga() {
        return riga;
    }

    /**
     * @param riga the riga to set
     */
    public void setRiga(int riga) {
        this.riga = riga;
    }

    /**
     * @return the colonna
     */
    public int getColonna() {
        return colonna;
    }

    /**
     * @param colonna the colonna to set
     */
    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    /**
     * @return the colonna
     */
    public boolean getEsiste() {
        return esiste;
    }

    /**
     * @param esiste the esiste to set
     */
    public void setEsiste(boolean esiste) {
        this.esiste = esiste;
    }   
}