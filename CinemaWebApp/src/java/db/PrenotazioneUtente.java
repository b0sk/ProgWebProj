/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Timestamp;

/**
 *
 * @author Patrik
 */
public class PrenotazioneUtente {
    private String titoloFilm;
    private String sala;
    private int idPosto;
    private Timestamp dataOra;
    private double prezzo;

    /**
     * @return the titoloFilm
     */
    public String getTitoloFilm() {
        return titoloFilm;
    }

    /**
     * @param titoloFilm the titoloFilm to set
     */
    public void setTitoloFilm(String titoloFilm) {
        this.titoloFilm = titoloFilm;
    }

    /**
     * @return the sala
     */
    public String getSala() {
        return sala;
    }

    /**
     * @param sala the idSala to set
     */
    public void setSala(String sala) {
        this.sala = sala;
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
     * @return the dataOra
     */
    public Timestamp getDataOra() {
        return dataOra;
    }

    /**
     * @param dataOra the dataOra to set
     */
    public void setDataOra(Timestamp dataOra) {
        this.dataOra = dataOra;
    }

    /**
     * @return the prezzo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * @param prezzo the prezzo to set
     */
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
    
    
    
}
