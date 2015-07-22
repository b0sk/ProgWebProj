/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Patrik
 */
public class DBManager implements Serializable {

    private transient Connection con;

    public DBManager(String dburl) throws SQLException {

        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());

        } catch (Exception e) {
            throw new RuntimeException(e.toString(), e);
        }

        Connection con = DriverManager.getConnection(dburl);
        this.con = con;

    }

    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }

    /**
     * Ottiene la lista ti tutti i film dal DB
     *
     * @return ArrayList contente tutti i film
     * @throws SQLException
     */
    public List<Film> getFilms() throws SQLException {
        List<Film> films = new ArrayList<Film>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Film");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Film f = new Film();
                    f.setIdFilm(rs.getInt("ID_FILM"));
                    f.setTitolo(rs.getString("TITOLO"));
                    f.setTrama(rs.getString("TRAMA"));
                    f.setDurata(rs.getInt("DURATA"));
                    f.setIdGenere(rs.getInt("ID_GENERE"));
                    f.setUriLocandina(rs.getString("URI_LOCANDINA"));
                    f.setUrlTrailer(rs.getString("URL_TRAILER"));

                    films.add(f);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return films;
    }

    /**
     * Ottiene un Film dal DB in base al suo ID
     *
     * @param idFilm ID del film da ottenere
     * @return Film desiderato, opure null se non c'è un film con tale ID
     * @throws SQLException
     */
    public Film getFilmById(String idFilm) throws SQLException {
        Film film = new Film();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Film WHERE ID_FILM = ?");
        try {
            stm.setString(1, idFilm);
            ResultSet rs = stm.executeQuery();
            try {
                rs.next();
                film.setIdFilm(rs.getInt("ID_FILM"));
                film.setTitolo(rs.getString("TITOLO"));
                film.setTrama(rs.getString("TRAMA"));
                film.setDurata(rs.getInt("DURATA"));
                film.setIdGenere(rs.getInt("ID_GENERE"));
                film.setUriLocandina(rs.getString("URI_LOCANDINA"));
                film.setUrlTrailer(rs.getString("URL_TRAILER"));
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return film;
    }

    /**
     * Questa funzione ritorna un oggetto Genere sapendo il suo ID
     *
     * @param idGenere ID del genere da ottenere
     * @return l'oggetto genere, opure null se non c'è un genere con tale ID
     * @throws SQLException
     */
    public Genere getGenereById(int idGenere) throws SQLException {
        Genere genere = new Genere();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Genere WHERE ID_GENERE = ?");
        try {
            stm.setString(1, Integer.toString(idGenere));
            ResultSet rs = stm.executeQuery();
            try {
                rs.next();
                genere.setIdGenere(rs.getInt("ID_GENERE"));
                genere.setDescrizione(rs.getString("DESCRIZIONE"));
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return genere;

    }

    /**
     * Restituisce una lista di spettacoli per un determinato film in base al
     * suo ID
     *
     * @param idFilm ID del film
     * @return una lista di spettacoli, opure null se non ci sono spettacoli
     * @throws SQLException
     */
    public List<Spettacolo> getSpettacoli(String idFilm) throws SQLException {
        List<Spettacolo> spettacoli = new ArrayList<Spettacolo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Spettacolo WHERE ID_FILM = ?");
        try {
            stm.setString(1, idFilm);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Spettacolo s = new Spettacolo();
                    s.setIdSala(rs.getInt("ID_SALA"));
                    s.setDataOra(rs.getTimestamp("DATA_ORA"));
                    s.setIdFilm(Integer.parseInt(idFilm));
                    s.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));

                    spettacoli.add(s);
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return spettacoli;
    }

    /**
     * Registra un nuovo utente
     *
     * @param email l'indirizzo email dell'utente
     * @param password la password dell'utente
     * @return true se l'utente viene registrato correttamente, false altrimenti
     * @throws SQLException
     */
    public boolean registraUtente(String email, String password) throws SQLException {
        // Controlla se l'utente è gia registrato con la stessa email
        boolean retval; // valore di ritorno della funzione
        int cnt = 0; // numero di entry con la stessa email trovate (dovrebbe sempre essere 0 o 1)
        PreparedStatement stm = con.prepareStatement("SELECT COUNT(*) AS CNT FROM UTENTE WHERE EMAIL = ?");
        try {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt(1);
            }

            // Se trova un utente con la stessa mail non esegue l'insert
            if (cnt != 0) {
                retval = false;
            } else {
                // Se non c'è gia lo iserisce
                stm = con.prepareStatement("INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO) VALUES (?, ?, 0, 2)");
                stm.setString(1, email);
                stm.setString(2, password);
                stm.executeUpdate();
                retval = true;
            }
        } finally {
            stm.close();
        }

        return retval;

    }
    /**
     * Ritorno un utente in base ad un email
     * 
     * @param email l'email dell'utente
     * @return null se l'utente non esiste, un oggetto User se l'utente esiste
     */
    public Utente getUtente(String email) throws SQLException {
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Utente WHERE email= ?");
        try {
            stm.setString(1, email);
            
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    Utente user = new Utente();
                    user.setIdUtente(rs.getInt("ID_UTENTE"));
                    user.setEmail(email);
                    user.setPassword(rs.getString("PASSWORD"));
                    user.setIdRuolo(rs.getInt("ID_RUOLO"));
                    user.setCredito(rs.getDouble("CREDITO"));
                    return user;
                } else {
                    return null;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }
    
    /**
     * Autentica un utente in base ad un email e ad una password
     * 
     * @param email l'email dell'utente
     * @param password la password
     * @return null se l'utente non è autenticato, un oggetto User se l'utente esiste ed è autenticato
     */
    public Utente authenticateUtente(String email, String password) throws SQLException {
        
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Utente WHERE email= ? AND password = ?");
        try {
            stm.setString(1, email);
            stm.setString(2, password);
            
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    Utente user = new Utente();
                    user.setIdUtente(rs.getInt("ID_UTENTE"));
                    user.setEmail(email);
                    user.setPassword("PASSWORD");
                    user.setIdRuolo(rs.getInt("ID_RUOLO"));
                    user.setCredito(rs.getDouble("CREDITO"));
                    return user;
                } else {
                    return null;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }
}
