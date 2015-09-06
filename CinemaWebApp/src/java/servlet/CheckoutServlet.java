/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.DBManager;
import db.Prezzo;
import db.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckoutServlet extends HttpServlet {

    private DBManager manager;

    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }

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
        //processRequest(request, response);
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
        //processRequest(request, response);

        Utente utente;
        String tipoPagamento;
        double totalePagamento = 0;
        double credito = 0;
        int idSpettacolo;
        Map<Integer, Prezzo> carrello;

        HttpSession session = request.getSession(true);

        utente = (Utente) session.getAttribute("utente");
        carrello = (HashMap) session.getAttribute("carrello");
        idSpettacolo = (int) session.getAttribute("idSpettacoloCarrello");

        tipoPagamento = request.getParameter("tipoPagamento");

        // calcola il totale da pagare
        Iterator it = carrello.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Prezzo prezzo = (Prezzo) pair.getValue();
            totalePagamento += prezzo.getPrezzo();
        }

        if (tipoPagamento.equals("cartaCredito")) {
            // se l'utente paga con carta di credito
            credito = totalePagamento;
        } else if (tipoPagamento.equals("creditoUtente")) {
            // se l'utente paga con credito utente
            credito = utente.getCredito();
        } else {
            // altrimenti errrore
        }

        if (credito < totalePagamento) {
            // errore non abbastanza credito
            request.setAttribute("exception", "Credito non sufficiente");
            RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
            rd.forward(request, response);
        } else {

            boolean allowPrenotazione = true;
            Iterator iter = carrello.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry pair = (Map.Entry) iter.next();
                int idPosto = (int) pair.getKey();

                try {
                    // se uno dei posti è gia occupato non permette la prenotazione
                    if (!manager.isPostoLibero(idPosto, idSpettacolo)) {
                        allowPrenotazione = false;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Prenota i posti
            if (allowPrenotazione == true) {
                iter = carrello.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry pair = (Map.Entry) iter.next();
                    int idPosto = (int) pair.getKey();
                    Prezzo p = (Prezzo) pair.getValue();
                    int idPrezzo = (int) p.getIdPrezzo();

                    try {
                        manager.prenotaPosto(utente.getIdUtente(), idSpettacolo, idPrezzo, idPosto);
                    } catch (SQLException ex) {
                        Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // SCALA CREDITO
                    credito -= p.getPrezzo();
                }

                // Se l'utente ha pagato con il credito
                // addebita il credito rimanente all'utente
                if (tipoPagamento.equals("creditoUtente")) {
                    try {
                        manager.setUserCredit(utente.getIdUtente(), credito);
                        // aggiorna la sessione con i nuovi dati utente
                        Utente u = manager.getUtente(utente.getEmail());
                        session.setAttribute("utente", u);
                    } catch (SQLException ex) {
                        Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // Invia email
                // Redirect a pagina di successo
                request.setAttribute("succes", 1);
                RequestDispatcher rd = request.getRequestDispatcher("/prenotazioneMessage.jsp");
                rd.forward(request, response);
            } else {
                // errore posto prenotato
                request.setAttribute("exception", "Uno dei posti è già stato prenotato");
                RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
                rd.forward(request, response);
            }

        }

    }

}
