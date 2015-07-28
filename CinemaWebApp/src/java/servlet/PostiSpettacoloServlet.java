package servlet;

import db.DBManager;
import db.Posto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Patrik
 */
public class PostiSpettacoloServlet extends HttpServlet {

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
            throws ServletException, IOException {
        int idSpettacolo;

        if (request.getParameter("idSpettacolo") != null) {
            List<Posto> posti;
            List<Posto> postiPrenotati;
            List<String> mappaPosti;
            int righeSala;
            int colonneSala;

            idSpettacolo = Integer.parseInt(request.getParameter("idSpettacolo"));

            try {
                posti = manager.getPostiSpettacolo(idSpettacolo);
                postiPrenotati = manager.getPostiPrenotatiSpettacolo(idSpettacolo);

                if (posti != null) {
                    righeSala = manager.getNRigheSala(posti.get(1).getIdSala());
                    colonneSala = manager.getNColonneSala(posti.get(1).getIdSala());

                    mappaPosti = generaMappaPosti(righeSala, colonneSala, posti, postiPrenotati);
                    System.out.println(mappaPosti);

                    request.setAttribute("nRighe", righeSala);
                    request.setAttribute("nColonne", colonneSala);

                    request.setAttribute("mappaPosti", mappaPosti);
                    
                    request.setAttribute("posti", posti);
                    request.setAttribute("postiPrenotati", postiPrenotati);

                    RequestDispatcher rd = request.getRequestDispatcher("/postiSpettacolo.jsp");
                    rd.forward(request, response);
                } else {
                    // Lo spettacolo non esiste: errore
                    request.setAttribute("errorMessage", "Lo spettacolo non esiste");
                    RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
                    rd.forward(request, response);
                }

            } catch (Exception ex) {
                request.setAttribute("exception", ex.toString());
                request.setAttribute("errorMessage", "Lo spettacolo non esiste");
                RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
                rd.forward(request, response);
            }

        } else {
            // redirecto a home o a errore
            response.sendRedirect(request.getContextPath() + "/errore.jsp");
        }

    }

    /**
     * Restituisce un array di stringhe che rappresenta la mappa dei posti, prendendo
     * in input il numero di righe della sala, il numero di colonne della sala,
     * la lista dei posti e la lista dei posti occupati
     * @param nRighe il numero di righe della sala
     * @param nColonne il numero di colonne della sala
     * @param Posti la lista di tutti i posti dello spettacolo
     * @param postiPrenotati la lista dei posti già prenotati per lo spettacolo
     * @return ArrayListi di stringhe che rappresenta la mappa della sala
     */
    private List<String> generaMappaPosti(int nRighe, int nColonne, List<Posto> Posti, List<Posto> postiPrenotati) {
        List<String> mappaPosti = new ArrayList<String>();

        for (int i = 0; i < nRighe; i++) {
            String riga = "";
            for (int j = 0; j < nColonne; j++) {
                Posto p = Posti.get(Integer.parseInt(Integer.toString(i) + Integer.toString(j)));
                if (isPrenotato(p.getIdPosto(), postiPrenotati)) {
                    riga += "o";
                } else if (p.getEsiste() == true) {
                    riga += "p";
                } else {
                    riga += "x";
                }
            }
            mappaPosti.add(riga);
        }

        return mappaPosti;
    }

    /* Controlla se il posto è già stato prenotato */
    private Boolean isPrenotato(int idPosto, List<Posto> occupati) {
        for (Posto p : occupati) {
            if (p.getIdPosto() == idPosto) {
                return true;
            }
        }

        return false;
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
        processRequest(request, response);
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
        processRequest(request, response);
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
