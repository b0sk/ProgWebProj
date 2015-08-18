/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.DBManager;
import db.Film;
import db.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class AmministrazioneServlet extends HttpServlet {

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
        Utente utente;
        int idUtente;

        HttpSession session = request.getSession(true);
        utente = (Utente) session.getAttribute("utente");

        // se l'utente è loggato come utente amministratore va alla pagina amministrazione
        if (utente != null && utente.getIdRuolo() == 1) {

            //response.sendRedirect(request.getContextPath() + "/amministrazione.jsp");
            // TODO: ottenere liste dal db e mostrarle nella jsp
            
            // OTTIENI lista degli incassi per ogni film:
            // ottieni la lista di tutti i film
            List<Film> films = manager.getFilms();
            // hash map che contiene titolo(String) e prezzo(Double)
            Map<String, Double> incassiFilm = new HashMap<String, Double>();
            // per ogni film salva titole e prezzo in incassi film
            for(Film film : films){
                incassiFilm.put(film.getTitolo(), manager.getIncassoFilm(film.getIdFilm()));
            }

            // OTTIENI la lista dei top 10 users:
            Map<String, Double> top10Users = new LinkedHashMap<String, Double>();
            top10Users = manager.getTop10Users();
            
            // setta gli attributi da inviare ad amministrazione jsp
            request.setAttribute("incassiFilm", incassiFilm);
            request.setAttribute("top10Users", top10Users);
            
            // passa i messaggi alla pagina jsp
            RequestDispatcher rd = request.getRequestDispatcher("/amministrazione.jsp");
            rd.forward(request, response);

        } else {
            // altrimenti redirect alla Home
            response.sendRedirect(request.getContextPath() + "/Home");
        }
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
            Logger.getLogger(AmministrazioneServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AmministrazioneServlet.class.getName()).log(Level.SEVERE, null, ex);
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
