/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.DBManager;
import db.Film;
import db.Spettacolo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
        String idFilm;
        Film film = new Film();
        List<Spettacolo> spettacoli;

        if (request.getParameter("idFilm") != null) {
            idFilm = request.getParameter("idFilm");
            
            try {
                film = manager.getFilmById(idFilm);
                spettacoli = manager.getSpettacoli(idFilm);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>idFilm: " + idFilm + "</h1>");
                out.println("<h1>Titolo: " + film.getTitolo() + "</h1>");
                out.println("<h1>Genere: " + film.getIdGenere()+ "</h1>");
                out.println("<h1>Trailer: " + film.getUrlTrailer() + "</h1>");
                out.println("<h1>Durata: " + film.getDurata() + "</h1>");
                out.println("<h1>Trama: " + film.getTrama() + "</h1>");
                out.println("<h1>Locandina: " + film.getUriLocandina() + "</h1>");
                
                out.println("<br>");
                
                out.println("<h1>Spettacoli</h1>");
                for(int i=0; i< spettacoli.size(); i++){
                    out.println("<h2>Sala: " + spettacoli.get(i).getIdSala() + " Data/ora: " + spettacoli.get(i).getDataOra()+ "</h2>");
                }
                
                out.println("</body>");
                out.println("</html>");
            }
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
