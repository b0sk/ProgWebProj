package servlet;

import db.DBManager;
import db.Film;
import db.Prenotazione;
import db.PrenotazioneUtente;
import db.Sala;
import db.Spettacolo;
import db.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Patrik
 */
public class PaginaUtenteServlet extends HttpServlet {

    private DBManager manager;

    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Prenotazione> prenotazioni;
        List<PrenotazioneUtente> prenotazioniUtente;
        Utente utente;
        int idUtente;

        HttpSession session = request.getSession(true);
        utente = (Utente) session.getAttribute("utente");
        idUtente = utente.getIdUtente();

        //response.sendRedirect(request.getContextPath() + "/paginaUtente.jsp");
        // TODO: ottenere lista prenotazioni dell'utente dal db e mostrarle nella jsp
        try {
            prenotazioni = manager.getPrenotazioniUtente(idUtente);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

        prenotazioniUtente = new ArrayList<PrenotazioneUtente>();
        for (Prenotazione p : prenotazioni) {
            PrenotazioneUtente pu = new PrenotazioneUtente();
            pu.setTitoloFilm(getTitoloFilm(p.getIdSpettacolo()));
            pu.setSala(getSalaSpettacolo(p.getIdSpettacolo()));
            pu.setIdPosto(p.getIdPosto());
            pu.setDataOra(getDataOraSpettacolo(p.getIdSpettacolo()));
            pu.setPrezzo(manager.getPrezzoById(p.getIdPrezzo()).getPrezzo());

            prenotazioniUtente.add(pu);
        }

        request.setAttribute("prenotazioniUtente", prenotazioniUtente);

        RequestDispatcher rd = request.getRequestDispatcher("/paginaUtente.jsp");
        rd.forward(request, response);
    }

    private String getTitoloFilm(int idSpettacolo) throws SQLException {
        Spettacolo spettacolo = manager.getSpettacoloById(idSpettacolo);
        Film film = manager.getFilmById(Integer.toString(spettacolo.getIdFilm()));
        return film.getTitolo();
    }

    private String getSalaSpettacolo(int idSpettacolo) throws SQLException {
        Spettacolo spettacolo = manager.getSpettacoloById(idSpettacolo);
        Sala sala = manager.getSalaById(spettacolo.getIdSala());
        return sala.getDescrizione();
    }

    private Timestamp getDataOraSpettacolo(int idSpettacolo) throws SQLException {
        Spettacolo spettacolo = manager.getSpettacoloById(idSpettacolo);
        return spettacolo.getDataOra();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PaginaUtenteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PaginaUtenteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
