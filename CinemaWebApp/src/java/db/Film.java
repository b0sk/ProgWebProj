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
public class Film {
    private int idFilm;
    private String titolo;
    private int idGenere;
    private String urlTrailer;
    private int durata;
    private String trama;
    private String uriLocandina;
    
    public Film(){}
    
    public Film(int idFilm, String titolo, int idGenere, String urlTrailer, int durata, String trama, String uriLocandina){
        this.idFilm = idFilm;
        this.titolo = titolo;
        this.idGenere = idGenere;
        this.urlTrailer = urlTrailer;
        this.durata = durata;
        this.trama = trama;
        this.uriLocandina = uriLocandina;
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
     * @return the titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * @param titolo the titolo to set
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
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
     * @return the urlTrailer
     */
    public String getUrlTrailer() {
        return urlTrailer;
    }

    /**
     * @param urlTrailer the urlTrailer to set
     */
    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    /**
     * @return the durata
     */
    public int getDurata() {
        return durata;
    }

    /**
     * @param durata the durata to set
     */
    public void setDurata(int durata) {
        this.durata = durata;
    }

    /**
     * @return the trama
     */
    public String getTrama() {
        return trama;
    }

    /**
     * @param trama the trama to set
     */
    public void setTrama(String trama) {
        this.trama = trama;
    }

    /**
     * @return the uriLocandina
     */
    public String getUriLocandina() {
        return uriLocandina;
    }

    /**
     * @param uriLocandina the uriLocandina to set
     */
    public void setUriLocandina(String uriLocandina) {
        this.uriLocandina = uriLocandina;
    }
}
