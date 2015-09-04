package servlet;

import db.DBManager;
import db.Posto;
import db.Prezzo;
import db.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RiepilogoPrenotazioneServlet extends HttpServlet {

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
    /* protected void processRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     response.setContentType("text/html;charset=UTF-8");
     try (PrintWriter out = response.getWriter()) {
     out.println("<!DOCTYPE html>");
     out.println("<html>");
     out.println("<head>");
     out.println("<title>Servlet RiepilogoPrenotazioneServlet</title>");            
     out.println("</head>");
     out.println("<body>");
     out.println("<h1>Servlet RiepilogoPrenotazioneServlet at " + request.getContextPath() + "</h1>");
     out.println("</body>");
     out.println("</html>");
     }
     }*/
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

        Utente utente;
        int idUtente;
        int idSpettacolo;
        String[] strIdPrezzi;
        String[] strIdPosti;
        List<Prezzo> prezzi;
        int prezzoTotale = 0;
        //List<Posto> posti;

        HttpSession session = request.getSession(true);
        utente = (Utente) session.getAttribute("utente");

        // controlla se l'utente è loggato
        if (utente != null) {
            System.out.println("UTENTE loggato");
            idUtente = utente.getIdUtente();
            idSpettacolo = Integer.parseInt(request.getParameter("idSpettacolo"));
            strIdPrezzi = request.getParameterValues("tipo-biglietto");
            strIdPosti = request.getParameterValues("id-posto");

            prezzi = new ArrayList();
            //posti = new ArrayList();
            try {
                // aggiungi gli oggetti prezzo alla lista dei prezzi
                for (int i = 0; i < strIdPrezzi.length; i++) {
                    prezzi.add(manager.getPrezzoById(Integer.parseInt(strIdPrezzi[i])));
                    //posti.add(manager.getPostoById(Integer.parseInt(strIdPosti[i])));
                }

                // calcola il prezzo totale
                for (Prezzo p : prezzi) {
                    prezzoTotale += p.getPrezzo();
                }

                // setta i parametri della richiesta e passali a postiSpettacolo.jsp
                request.setAttribute("prezzi", prezzi);
                request.setAttribute("prezzoTotale", prezzoTotale);
                request.setAttribute("strIdPosti", strIdPosti);

                RequestDispatcher rd = request.getRequestDispatcher("/riepilogoPrenotazione.jsp");
                rd.forward(request, response);
            } catch (Exception e) {
                // redirect a pagina di errore
                response.sendRedirect(request.getContextPath() + "/errore.jsp");
            }

            /*
             System.out.println("idUtente: " + idUtente);
             System.out.println("idPrezzi:");
             for(int i=0; i<strIdPrezzi.length; i++){
             System.out.println("- " + strIdPrezzi[i]);
             }
             System.out.println("idPosti:");
             for(int i=0; i<strIdPosti.length; i++){
             System.out.println("- " + strIdPosti[i]);
             }
             */
        } else {
            // se l'utente non è loggato rimanda al login...
            System.out.println("UTENTE non loggato");
        }

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
        // Rimanda a una pagina di errore
        response.sendRedirect(request.getContextPath() + "/errore.jsp");

    }

}
