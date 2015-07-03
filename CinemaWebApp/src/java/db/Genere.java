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

public class Genere implements Serializable {
    private int idGenere;
    private String descrizione;
   
        public Genere() {}
    
    public Genere(int idGenere, String descrizione){
        this.idGenere = idGenere;
        this.descrizione = descrizione;
    }

    /**
     * @return the idGenere
     */
    public int getIdGenere() {
        return idGenere;
    }

    /**
     * @param idGenere the idGenere to set
     */
    public void setIdGenere(int idGenere) {
        this.idGenere = idGenere;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}