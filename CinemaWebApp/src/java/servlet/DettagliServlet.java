package servlet;

import db.DBManager;
import db.Film;
import db.Genere;
import db.Spettacolo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class DettagliServlet extends HttpServlet {

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
        int idFilm;
        Film film = new Film();
        Genere genere = new Genere();
        List<Spettacolo> spettacoli;

        if (request.getParameter("idFilm") != null) {
            idFilm = Integer.parseInt(request.getParameter("idFilm"));

            try {
                film = manager.getFilmById(idFilm);
                spettacoli = manager.getSpettacoli(idFilm);
                genere = manager.getGenereById(film.getIdGenere());

                request.setAttribute("film", film);
                request.setAttribute("spettacoli", spettacoli);
                request.setAttribute("genere", genere);

                RequestDispatcher rd = request.getRequestDispatcher("/dettagli.jsp");
                rd.forward(request, response);
            } catch (Exception ex) {
                // MOSTRA PAGINA DI ERRORE con messaggi
                request.setAttribute("exception", ex.toString());
                RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
                rd.forward(request, response);

                //throw new ServletException(ex);
            }

        } else {
            // redirect a pagina di errore
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
