package servlet;

import db.DBManager;
import db.Utente;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Patrik
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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

        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");

        /* controlla nel DB se esiste l'utente (email+password) e autenticalo */
        Utente user;
        try {

            user = manager.authenticateUtente(email, password);

        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        // se non esiste, ridirigo verso pagina di login con messaggio di errore
        if (user == null) {
            // metto il messaggio di errore come attributo di Request, così nel JSP si vede il messaggio
            request.setAttribute("message", "Email e/o password sbagliate!");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        } else {
            // imposto l'utente connesso come attributo di sessione
            HttpSession session = request.getSession(true);
            session.setAttribute("utente", user);
            
            // controlla se c'è gia un carrello nella sessione
            if (session.getAttribute("carrello") == null) {
                // se non c'è mando un redirect alla home
                response.sendRedirect(request.getContextPath() + "/Home");
            }else{
                // altrimenti mandalo alla conferma di prenotazione
                response.sendRedirect(request.getContextPath() + "/RiepilogoPrenotazioneServlet?idSpettacolo="+session.getAttribute("idSpettacoloCarrello"));
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
