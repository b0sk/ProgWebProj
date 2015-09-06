/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

public class Biglietto {

    private String infoCinema = "3883 Howard Hughes Pkwy, Las Vegas, NV 89169";
    private String nomeCinema = "CinemaWebApp";
    private String emailUtente;
    private String titoloFilm;
    private int idPosto;
    private int idSala;
    private int rigaPosto;
    private int colonnaPosto;
    private double prezzoBiglietto;
    private String tipoBiglietto;
    private String dataOraSpettacolo;

    public Biglietto() {
    }

    public Biglietto(String emailUtente, String titoloFilm, int idPosto, int idSala, int rigaPosto, int colonnaPosto, 
                double prezzoBiglietto, String tipoBiglietto, String dataOraSpettacolo) {
        this.emailUtente = emailUtente;
        this.titoloFilm = titoloFilm;
        this.idPosto = idPosto;
        this.idSala = idSala;
        this.rigaPosto = rigaPosto;
        this.colonnaPosto = colonnaPosto;
        this.prezzoBiglietto = prezzoBiglietto;
        this.tipoBiglietto = tipoBiglietto;
        this.dataOraSpettacolo = dataOraSpettacolo;
    }

    /**
     * @return the infoCinema
     */
    public String getInfoCinema() {
        return infoCinema;
    }

    /**
     * @param infoCinema the infoCinema to set
     */
    public void setInfoCinema(String infoCinema) {
        this.infoCinema = infoCinema;
    }

    /**
     * @return the nomeCinema
     */
    public String getNomeCinema() {
        return nomeCinema;
    }

    /**
     * @param nomeCinema the nomeCinema to set
     */
    public void setNomeCinema(String nomeCinema) {
        this.nomeCinema = nomeCinema;
    }

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
     * @return the rigaPosto
     */
    public int getRigaPosto() {
        return rigaPosto;
    }

    /**
     * @param rigaPosto the rigaPosto to set
     */
    public void setRigaPosto(int rigaPosto) {
        this.rigaPosto = rigaPosto;
    }

    /**
     * @return the colonnaPosto
     */
    public int getColonnaPosto() {
        return colonnaPosto;
    }

    /**
     * @param colonnaPosto the colonnaPosto to set
     */
    public void setColonnaPosto(int colonnaPosto) {
        this.colonnaPosto = colonnaPosto;
    }

    /**
     * @return the prezzoBiglietto
     */
    public double getPrezzoBiglietto() {
        return prezzoBiglietto;
    }

    /**
     * @param prezzoBiglietto the prezzoBiglietto to set
     */
    public void setPrezzoBiglietto(double prezzoBiglietto) {
        this.prezzoBiglietto = prezzoBiglietto;
    }

    /**
     * @return the tipoBiglietto
     */
    public String getTipoBiglietto() {
        return tipoBiglietto;
    }

    /**
     * @param tipoBiglietto the tipoBiglietto to set
     */
    public void setTipoBiglietto(String tipoBiglietto) {
        this.tipoBiglietto = tipoBiglietto;
    }

    /**
     * @return the dataOraSpettacolo
     */
    public String getDataOraSpettacolo() {
        return dataOraSpettacolo;
    }

    /**
     * @param dataOraSpettacolo the dataOraSpettacolo to set
     */
    public void setDataOraSpettacolo(String dataOraSpettacolo) {
        this.dataOraSpettacolo = dataOraSpettacolo;
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
     * @return the emailUtente
     */
    public String getEmailUtente() {
        return emailUtente;
    }

    /**
     * @param emailUtente the emailUtente to set
     */
    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

}
