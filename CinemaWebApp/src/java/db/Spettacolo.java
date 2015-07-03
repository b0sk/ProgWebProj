/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Stalker
 */
public class Spettacolo implements Serializable {
    private int idSpettacolo;
    private int idFilm;
    private Timestamp dataOra;
    private int idSala;
   
        public Spettacolo() {}
    
    public Spettacolo(int idSpettacolo, int idFilm, Timestamp dataOra, int idSala){
        this.idSpettacolo = idSpettacolo;
        this.idFilm = idFilm;
        this.dataOra = dataOra;
        this.idSala = idSala;
    }

    /**
     * @return the idSpettacolo
     */
    public int getIdSpettacolo() {
        return idSpettacolo;
    }

    /**
     * @param idSpettacolo the idSpettacolo to set
     */
    public void setIdSpettacolo(int idSpettacolo) {
        this.idSpettacolo = idSpettacolo;
    }

    /**
     * @return the idFilm
     */
    public int getIdFilm() {
        return idFilm;
    }

    /**
     * @param idFilm the idFilm to set
     */
    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
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
}
