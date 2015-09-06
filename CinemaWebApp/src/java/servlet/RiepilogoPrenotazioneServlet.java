package servlet;

import db.DBManager;
import db.Posto;
import db.Prezzo;
import db.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {

        Utente utente;
        int idUtente;
        int idSpettacolo;
        String[] strIdPrezzi;
        String[] strIdPosti;
        Map<Integer, Prezzo> carrello;
        //List<Prezzo> prezzi;

        double prezzoTotale = 0;
        //List<Posto> posti;

        HttpSession session = request.getSession(true);
        utente = (Utente) session.getAttribute("utente");

        idSpettacolo = Integer.parseInt(request.getParameter("idSpettacolo"));
        strIdPrezzi = request.getParameterValues("tipo-biglietto");
        strIdPosti = request.getParameterValues("id-posto");

        //prezzi = new ArrayList();
        //posti = new ArrayList();
        carrello = new HashMap();
        
        try {
            // popola la hashmap con id del Posto e il prezzo a lui associato
            for (int i = 0; i < strIdPrezzi.length; i++) {
                //prezzi.add(manager.getPrezzoById(Integer.parseInt(strIdPrezzi[i])));
                //posti.add(manager.getPostoById(Integer.parseInt(strIdPosti[i])));
                int idPosto = Integer.parseInt(strIdPosti[i]);
                carrello.put(idPosto, manager.getPrezzoById(Integer.parseInt(strIdPrezzi[i])));
                //calcola il perzzo totale
                prezzoTotale += carrello.get(idPosto).getPrezzo();
                System.out.println(""+ prezzoTotale + " "+ carrello.get(idPosto).getPrezzo());
                //System.err.println(""+ carrello.get(idPosto).getPrezzo());
            }

            // setta i parametri della richiesta e passali a postiSpettacolo.jsp
            //request.setAttribute("prezzi", prezzi);
            
            
            //request.setAttribute("prezzoTotale", prezzoTotale);   tolto nn funziona correttamente
            session.setAttribute("prezzoTotale", prezzoTotale);   //Questo funziona
            //request.setAttribute("Id")

            session.setAttribute("carrello", carrello);
            session.setAttribute("idSpettacoloCarrello", idSpettacolo);
            //request.setAttribute("strIdPosti", strIdPosti);

        } catch (Exception e) {
            // redirect a pagina di erroreù
            //System.out.println("LOL");
            //response.sendRedirect(request.getContextPath() + "/errore.jsp");
        }

        // controlla se l'utente è loggato
        if (utente != null) {
            //System.out.println("UTENTE loggato");
            //idUtente = utente.getIdUtente();
            RequestDispatcher rd = request.getRequestDispatcher("/riepilogoPrenotazione.jsp");
            rd.forward(request, response);
        } else {
            // se l'utente non è loggato rimanda al login...
            //System.out.println("UTENTE non loggato");
            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
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
                processRequest(request, response);
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
        //response.sendRedirect(request.getContextPath() + "/errore.jsp");
        processRequest(request, response);

    }

}
