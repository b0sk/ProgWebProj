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
 * @author Patrik
 */
public class Prenotazione implements Serializable {
    private int idPrenotazione;
    private int idUtente;
    private int idSpettacolo;
    private int idPrezzo;
    private int idPosto;
    private Timestamp dataOraOperazione;
    
    public Prenotazione(){}
    
    public Prenotazione(int idPrenotazione, int idUtente, int idSpettacolo, int idPrezzo, int idPosto, Timestamp dataOraOperazione){
        this.idPrenotazione = idPrenotazione;
        this.idUtente = idUtente;
        this.idSpettacolo = idSpettacolo;
        this.idPrezzo = idPrezzo;
        this.idPosto = idPosto;
        this.dataOraOperazione = dataOraOperazione;
    }

    /**
     * @return the idPrenotazione
     */
    public int getIdPrenotazione() {
        return idPrenotazione;
    }

    /**
     * @param idPrenotazione the idPrenotazione to set
     */
    public void setIdPrenotazione(int idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
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
     * @return the idPrezzo
     */
    public int getIdPrezzo() {
        return idPrezzo;
    }

    /**
     * @param idPrezzo the idPrezzo to set
     */
    public void setIdPrezzo(int idPrezzo) {
        this.idPrezzo = idPrezzo;
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
     * @return the dataOraOperazione
     */
    public Timestamp getDataOraOperazione() {
        return dataOraOperazione;
    }

    /**
     * @param dataOraOperazione the dataOraOperazione to set
     */
    public void setDataOraOperazione(Timestamp dataOraOperazione) {
        this.dataOraOperazione = dataOraOperazione;
    }
}
