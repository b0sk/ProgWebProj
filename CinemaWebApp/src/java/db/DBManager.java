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
     * @return Film desiderato, oppure null se non c'è un film con tale ID
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
     * @return l'oggetto genere, oppure null se non c'è un genere con tale ID
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
     * @return una lista di spettacoli, oppure null se non ci sono spettacoli
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
     * @return null se l'utente non è autenticato, un oggetto User se l'utente
     * esiste ed è autenticato
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

    /**
     * Ottiene la lista di tutti i prezzi dal DB
     *
     * @return ArrayList contenente tutti i prezzi
     * @throws SQLException
     */
    public List<Prezzo> getPrezzi() throws SQLException {
        List<Prezzo> prezzi = new ArrayList<Prezzo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Prezzo");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Prezzo p = new Prezzo();
                    p.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    p.setPrezzo(rs.getDouble("PREZZO"));
                    p.setTipo(rs.getString("TIPO"));

                    prezzi.add(p);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return prezzi;
    }

    /**
     * Restituisce un oggeto Prezzo in base al suo ID
     *
     * @param idPrezzp l'ID del prezzo
     * @return un oggetto Prezzo, oppure null se non ci sono prezzi con l'id
     * specificato
     * @throws SQLException
     */
    public Prezzo getPrezzoById(int idPrezzo) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Prezzo WHERE ID_PREZZO = ?");
        try {
            stm.setString(1, Integer.toString(idPrezzo));
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    Prezzo p = new Prezzo();
                    p.setIdPrezzo(idPrezzo);
                    p.setPrezzo(rs.getDouble("PREZZO"));
                    p.setTipo(rs.getString("TIPO"));

                    return p;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    /**
     * Restituisce una lista di prenotazioni di un determinato utente in base al
     * suo ID
     *
     * @param idUtente ID dell'utente
     * @return una lista di prenotazioni, oppure null se non ci sono
     * prenotazioni
     * @throws SQLException
     */
    public List<Prenotazione> getPrenotazioniUtente(int idUtente) throws SQLException {
        List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Prenotazione WHERE ID_UTENTE = ?");
        try {
            stm.setString(1, Integer.toString(idUtente));
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Prenotazione p = new Prenotazione();
                    p.setIdPrenotazione(rs.getInt("ID_PRENOTAZIONE"));
                    p.setIdUtente(idUtente);
                    p.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    p.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    p.setIdPosto(rs.getInt("ID_POSTO"));
                    p.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));

                    prenotazioni.add(p);
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return prenotazioni;
    }

    /**
     * Restituisce un oggeto spettacolo in base al suo ID
     *
     * @param idSpettacolo l'ID dello spettacolo
     * @return un oggetto Spettacolo, oppure null se non ci sono spettacoli con
     * l'id specificato
     * @throws SQLException
     */
    public Spettacolo getSpettacoloById(int idSpettacolo) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Spettacolo WHERE ID_SPETTACOLO = ?");
        try {
            stm.setString(1, Integer.toString(idSpettacolo));
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    Spettacolo s = new Spettacolo();
                    s.setIdSpettacolo(idSpettacolo);
                    s.setIdFilm(rs.getInt("ID_FILM"));
                    s.setDataOra(rs.getTimestamp("DATA_ORA"));
                    s.setIdSala(rs.getInt("ID_SALA"));

                    return s;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }

    /**
     * Restituisce un oggeto Sala in base al suo ID
     *
     * @param idSala l'ID della sala
     * @return un oggetto Sala, oppure null se non ci sono sale con l'id
     * specificato
     * @throws SQLException
     */
    public Sala getSalaById(int idSala) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Sala WHERE ID_SALA = ?");
        try {
            stm.setString(1, Integer.toString(idSala));
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    Sala s = new Sala();
                    s.setIdSala(idSala);
                    s.setDescrizione(rs.getString("DESCRIZIONE"));

                    return s;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }

    /**
     * Restituisce una lista di posti per un determinato spettacolo in base al
     * suo ID
     *
     * @param idSpettacolo ID dello spettacolo
     * @return una lista di posti, oppure null se non ci sono spettacoli con
     * tale id
     * @throws SQLException
     */
    public List<Posto> getPostiSpettacolo(int idSpettacolo) throws SQLException {
        List<Posto> posti = new ArrayList<Posto>();

        PreparedStatement stm = con.prepareStatement("select * from spettacolo "
                + "left join sala on spettacolo.id_sala = sala.id_sala "
                + "left join posto on posto.id_sala = sala.id_sala "
                + "where id_spettacolo = ?");// esiste = true and id_spettacolo = ?

        try {
            stm.setString(1, Integer.toString(idSpettacolo));
            ResultSet rs = stm.executeQuery();

            try {
                while (rs.next()) {
                    Posto p = new Posto();
                    p.setIdPosto(rs.getInt("ID_POSTO"));
                    p.setIdSala(rs.getInt("ID_SALA"));
                    p.setRiga(rs.getInt("RIGA"));
                    p.setColonna(rs.getInt("COLONNA"));
                    p.setEsiste(rs.getBoolean("ESISTE"));

                    posti.add(p);
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return posti;
    }

    /**
     * Restituisce una lista di posti PRENRENOTATI per un determinato spettacolo
     * in base al suo ID
     *
     * @param idSpettacolo ID dello spettacolo
     * @return una lista di posti liberi, oppure null se non ci sono posti
     * liberi
     * @throws SQLException
     */
    public List<Posto> getPostiPrenotatiSpettacolo(int idSpettacolo) throws SQLException {
        List<Posto> posti = new ArrayList<Posto>();

        PreparedStatement stm = con.prepareStatement("select * from APP.PRENOTAZIONE "
                + "left join APP.POSTO on PRENOTAZIONE.ID_POSTO = POSTO.ID_POSTO "
                + "where id_spettacolo = ?");

        try {
            stm.setString(1, Integer.toString(idSpettacolo));
            ResultSet rs = stm.executeQuery();

            try {
                while (rs.next()) {
                    Posto p = new Posto();
                    p.setIdPosto(rs.getInt("ID_POSTO"));
                    p.setIdSala(rs.getInt("ID_SALA"));
                    p.setRiga(rs.getInt("RIGA"));
                    p.setColonna(rs.getInt("COLONNA"));
                    p.setEsiste(rs.getBoolean("ESISTE"));

                    posti.add(p);
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return posti;
    }

    /**
     * Restituisce il numero di righe di una sala
     *
     * @param idSala l'ID della sala
     * @return un intero che rappresenta il numero di righe, oppure 0 se non ci
     * sono sala con l'id specificato specificato
     * @throws SQLException
     */
    public int getNRigheSala(int idSala) throws SQLException {

        PreparedStatement stm
                = con.prepareStatement("SELECT max(RIGA) as nRighe "
                                        + "FROM POSTO "
                                        + "WHERE ID_SALA = ?");
        try {
            stm.setString(1, Integer.toString(idSala));
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    int nRighe = rs.getInt("nRighe");
                    return nRighe;
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                return 0;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }

    /**
     * Restituisce il numero di righe di una sala
     *
     * @param idSala l'ID della sala
     * @return un intero che rappresenta il numero di righe, oppure 0 se non ci
     * sono sala con l'id specificato specificato
     * @throws SQLException
     */
    public int getNColonneSala(int idSala) throws SQLException {

        PreparedStatement stm
                = con.prepareStatement("SELECT max(COLONNA) as nColonne "
                                        + "FROM POSTO "
                                        + "WHERE ID_SALA = ?");
        try {
            stm.setString(1, Integer.toString(idSala));
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    int nColonne = rs.getInt("nColonne");
                    return nColonne;
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                return 0;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }
    
    /////////////// da correggere //////////////////////////////////////////////
    /**
     * Restituisce l'incasso di un film, in base al suo ID
     *
     * @param idFilm l'ID del film
     * @return L'incasso del film, oppure 0 se non esiste un film con l'id
     * specificato
     * @throws SQLException
     */
    public double getIncassoFilm(int idFilm) throws SQLException {

        PreparedStatement stm
                = con.prepareStatement(/*"select films.id_film, sum(incassi.incasso) as incassoFilm "
                         + "from "
                         + "(select id_spettacolo, sum(prezzo) as incasso from APP.PRENOTAZIONE "
                         + "left join APP.PREZZO on PREZZO.ID_PREZZO = PRENOTAZIONE.ID_PREZZO "
                         + "group by id_spettacolo) as incassi "
                         + "join "
                         + "(select id_spettacolo, id_film "
                         + "from spettacolo) as films "
                         + "on incassi.id_spettacolo = films.id_spettacolo "
                         + "group by films.id_film "
                         + "having films.id_film = ?"*/
                        "select SPETTACOLO.ID_FILM, sum(prezzo) as incasso "
                        + "from PRENOTAZIONE left join PREZZO on PREZZO.ID_PREZZO = PRENOTAZIONE.ID_PREZZO "
                        + "left join SPETTACOLO on SPETTACOLO.ID_SPETTACOLO = PRENOTAZIONE.ID_SPETTACOLO "
                        + "group by ID_FILM "
                        + "having ID_FILM = ?");
        try {
            stm.setString(1, Integer.toString(idFilm));
            ResultSet rs = stm.executeQuery();
//            System.out.println("CIAOO");
            try {
                if (rs.next()) {
                    double ris = rs.getDouble("incasso");
//                    System.out.println(ris);
                    return ris;
                } else {
                    return 0.0;
                }
            } finally {
                rs.close();
            }
        } catch (SQLException e) {
            //System.out.println(e);
            return 0.0;
        } finally {
            stm.close();
        }
    }
////////////////////////////////////////////////////////////////////////////////

}
