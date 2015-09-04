package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    public Film getFilmById(int idFilm) throws SQLException {
        Film film = new Film();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Film WHERE ID_FILM = ?");
        try {
            stm.setString(1, Integer.toString(idFilm));
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
    public List<Spettacolo> getSpettacoli(int idFilm) throws SQLException {
        List<Spettacolo> spettacoli = new ArrayList<Spettacolo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Spettacolo WHERE ID_FILM = ? ");
        try {
            stm.setString(1, Integer.toString(idFilm));
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Spettacolo s = new Spettacolo();
                    s.setIdSala(rs.getInt("ID_SALA"));
                    s.setDataOra(rs.getTimestamp("DATA_ORA"));
                    s.setIdFilm(idFilm);
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
     * Restituisce una lista di spettacoli per un determinato film in base al
     * suo ID
     *
     * @param idFilm ID del film
     * @return una lista di spettacoli, oppure null se non ci sono spettacoli
     * @throws SQLException
     */
    public List<Spettacolo> getSpettacoliAttiviByIdFilm(int idFilm) throws SQLException {
        List<Spettacolo> spettacoli = new ArrayList<Spettacolo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Spettacolo WHERE ID_FILM = ? AND DATA_ORA > CURRENT TIMESTAMP");
        try {
            stm.setString(1, Integer.toString(idFilm));
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Spettacolo s = new Spettacolo();
                    s.setIdSala(rs.getInt("ID_SALA"));
                    s.setDataOra(rs.getTimestamp("DATA_ORA"));
                    s.setIdFilm(idFilm);
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
     * Restituisce una lista di spettacoli attivi in questo momento
     *
     * @return una lista di spettacoli, oppure null se non ci sono spettacoli
     * @throws SQLException
     */
    public List<Spettacolo> getSpettacoliAttivi() throws SQLException {
        List<Spettacolo> spettacoli = new ArrayList<Spettacolo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Spettacolo WHERE DATA_ORA > CURRENT TIMESTAMP");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Spettacolo s = new Spettacolo();
                    s.setIdSala(rs.getInt("ID_SALA"));
                    s.setDataOra(rs.getTimestamp("DATA_ORA"));
                    s.setIdFilm(rs.getInt("ID_FILM"));
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
     * Questa funzione ritorna il numero di prenotazioni per un determinato
     * spettacolo
     *
     * @param idSpettacolo ID dello spettacolo
     * @return il numero di prenotazioni, oppure 0 se non c'è uno spettacolo con
     * tale ID
     * @throws SQLException
     */
    public int getNPrenotazioniSpettacolo(int idSpettacolo) throws SQLException {
        int nPrenotaz = 0;

        PreparedStatement stm = con.prepareStatement("select Count(ID_PRENOTAZIONE) as NPrenotazioni "
                + "from Spettacolo left join Prenotazione on Spettacolo.ID_SPETTACOLO = Prenotazione.ID_SPETTACOLO "
                + "where Spettacolo.ID_SPETTACOLO = ?");
        try {
            stm.setString(1, Integer.toString(idSpettacolo));
            ResultSet rs = stm.executeQuery();
            try {
                rs.next();
                nPrenotaz = rs.getInt("NPrenotazioni");
            } catch (SQLException e) {
                return 0;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return nPrenotaz;
    }

    /**
     * Questa funzione ritorna l'incasso per un determinato spettacolo
     *
     * @param idSpettacolo ID dello spettacolo
     * @return l'incasso, oppure 0 se non c'è uno spettacolo con tale ID
     * @throws SQLException
     */
    public int getIncassoSpettacolo(int idSpettacolo) throws SQLException {
        int incasso = 0;

        PreparedStatement stm = con.prepareStatement("select Sum(Prezzo.PREZZO) as Incasso "
                                                    + "from Spettacolo left join Prenotazione on Spettacolo.ID_SPETTACOLO = Prenotazione.ID_SPETTACOLO "
                                                    + "    left join Prezzo on Prenotazione.ID_PREZZO = Prezzo.ID_PREZZO "
                                                    + "where Spettacolo.ID_SPETTACOLO = ?");
        try {
            stm.setString(1, Integer.toString(idSpettacolo));
            ResultSet rs = stm.executeQuery();
            try {
                rs.next();
                incasso = rs.getInt("Incasso");
            } catch (SQLException e) {
                return 0;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return incasso;

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
     * Ritorno un utente in base al suo ID
     *
     * @param idUtente l'ID dell'utente
     * @return null se l'utente non esiste, un oggetto User se l'utente esiste
     */
    public Utente getUtenteById(int idUtente) throws SQLException {
        PreparedStatement stm = con.prepareStatement("SELECT * FROM Utente WHERE ID_UTENTE = ?");
        try {
            stm.setString(1, Integer.toString(idUtente));
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    Utente user = new Utente();
                    user.setIdUtente(idUtente);
                    user.setEmail(rs.getString("EMAIL"));
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

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Utente WHERE email = ? AND password = ?");
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
     * @param idPrezzo l'ID del prezzo
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
     * Restituisce un oggeto Prenotazione in base al suo ID
     *
     * @param idPrenotazione l'ID della prenotazione da restituire
     * @return un oggetto Prezzo, oppure null se non ci sono prezzi con l'id
     *
     * @throws SQLException
     */
    public Prenotazione getPrenotazioneById(int idPrenotazione) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM Prenotazione WHERE ID_PRENOTAZIONE = ?");
        try {
            stm.setString(1, Integer.toString(idPrenotazione));
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    Prenotazione p = new Prenotazione();
                    p.setIdPrenotazione(idPrenotazione);
                    p.setIdUtente(rs.getInt("ID_UTENTE"));
                    p.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    p.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    p.setIdPosto(rs.getInt("ID_POSTO"));
                    p.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));

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

    /**
     * Aggiunge un certo credito ad un utente specificato
     *
     * @param idUtente l'ID dell'utente a cui aggiungere credito
     * @param credito il credito da aggiungere
     * @return true se il credito è stato aggiunto, false altrimenti
     *
     * @throws SQLException
     */
    public boolean addUserCredit(int idUtente, double credito) throws SQLException {

        PreparedStatement stm = con.prepareStatement("UPDATE Utente SET Credito = Credito + ? WHERE ID_UTENTE = ?");
        try {
            stm.setString(1, Double.toString(credito));
            stm.setString(2, Integer.toString(idUtente));

            int righeAggiornate = stm.executeUpdate();
            if (righeAggiornate > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        } finally {
            stm.close();
        }
    }

    /**
     * Aggiunge un certo credito ad un utente specificato
     *
     * @param idUtente l'ID dell'utente a cui aggiungere credito
     * @param credito il credito da aggiungere
     * @return true se il credito è stato aggiunto, false altrimenti
     *
     * @throws SQLException
     */
    public boolean deletePrentoazione(int idPrenotazione) throws SQLException {

        PreparedStatement stm = con.prepareStatement("DELETE FROM Prenotazione WHERE ID_PRENOTAZIONE = ?");
        try {
            stm.setString(1, Integer.toString(idPrenotazione));

            int righeAggiornate = stm.executeUpdate();
            if (righeAggiornate > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        } finally {
            stm.close();
        }
    }

    /**
     * Restituisce una lista di prenotazioni attive in quel moemento, cioe' per
     * spettacoli che devono ancora iniziare.
     *
     * @param idUtente ID dell'utente
     * @return una lista di prenotazioni, oppure null se non ci sono
     * prenotazioni
     * @throws SQLException
     */
    public List<Prenotazione> getPrenotazioniAttive() throws SQLException {
        List<Prenotazione> prenotazioniAttive = new ArrayList<Prenotazione>();

        PreparedStatement stm = con.prepareStatement("SELECT Prenotazione.ID_PRENOTAZIONE, Prenotazione.ID_UTENTE, Prenotazione.ID_SPETTACOLO, Prenotazione.ID_PREZZO, Prenotazione.ID_POSTO, Prenotazione.DATA_ORA_OPERAZIONE "
                + "FROM Prenotazione LEFT JOIN Spettacolo ON Prenotazione.ID_SPETTACOLO = Spettacolo.ID_SPETTACOLO "
                + "WHERE Spettacolo.DATA_ORA > CURRENT TIMESTAMP");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Prenotazione p = new Prenotazione();
                    p.setIdPrenotazione(rs.getInt("ID_PRENOTAZIONE"));
                    p.setIdUtente(rs.getInt("ID_UTENTE"));
                    p.setIdSpettacolo(rs.getInt("ID_SPETTACOLO"));
                    p.setIdPrezzo(rs.getInt("ID_PREZZO"));
                    p.setIdPosto(rs.getInt("ID_POSTO"));
                    p.setDataOraOperazione(rs.getTimestamp("DATA_ORA_OPERAZIONE"));

                    prenotazioniAttive.add(p);
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return prenotazioniAttive;
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
     * sono sala con l'id specificato
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
     * Restituisce il numero di colonne di una sala
     *
     * @param idSala l'ID della sala
     * @return un intero che rappresenta il numero di colonne, oppure 0 se non
     * ci sono sale con l'id specificato
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

    /**
     * Restituisce una lista dei 10 utenti top, con email e incasso generato. La
     * lista è ordinata in modo decrescente in base all'incasso.
     *
     * @return Una LinkedHashMap che ha come chiave l'email dell'utente e come
     * valore l'icasso generato dallo stesso
     * @throws SQLException
     */
    public Map<String, Double> getTop10Users() throws SQLException {
        Map<String, Double> topUsers = new LinkedHashMap<String, Double>();
        PreparedStatement stm
                = con.prepareStatement(
                        "select PRENOTAZIONE.ID_UTENTE, UTENTE.EMAIL as EMAIL, sum(prezzo) as INCASSO "
                        + "from PRENOTAZIONE left join PREZZO on PREZZO.ID_PREZZO = PRENOTAZIONE.ID_PREZZO "
                        + "    left join UTENTE on UTENTE.ID_UTENTE = PRENOTAZIONE.ID_UTENTE "
                        + "group by PRENOTAZIONE.ID_UTENTE, UTENTE.EMAIL "
                        + "order by sum(prezzo) DESC");
        try {
            stm.setMaxRows(10);
            ResultSet rs = stm.executeQuery();

            try {
                while (rs.next()) {
                    topUsers.put(rs.getString("EMAIL"), rs.getDouble("INCASSO"));
                }
            } catch (SQLException e) {
                return null;
            } finally {
                rs.close();
            }

        } catch (SQLException e) {
            return null;
        } finally {
            stm.close();
        }

        return topUsers;
    }

}
