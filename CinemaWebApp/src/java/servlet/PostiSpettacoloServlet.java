package servlet;

import db   .DBManager;
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

            idSpettacolo = Integer.parseInt(request.getParameter("idSpettacolo"));

            try {
                posti = manager.getPostiSpettacolo(idSpettacolo);
                postiPrenotati = manager.getPostiPrenotatiSpettacolo(idSpettacolo);
                
                
                if (posti != null) {
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
