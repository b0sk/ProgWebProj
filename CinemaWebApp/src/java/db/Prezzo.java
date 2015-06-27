/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;

/**
 *
 * @author Patrik
 */
public class Prezzo implements Serializable {
    private int idPrezzo;
    private String tipo;
    private double prezzo;
    
    public Prezzo(){}
    
    public Prezzo(int idPrezzo, String tipo, int prezzo){
        this.idPrezzo = idPrezzo;
        this.tipo = tipo;
        this.prezzo = prezzo;
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
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
