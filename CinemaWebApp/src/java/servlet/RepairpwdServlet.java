package servlet;

import db.DBManager;
import db.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;


/**
 *
 * @author Patrik
 */
public class RepairpwdServlet extends HttpServlet {

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

        if (email != null) {
            Utente utente;
            try {
                utente = manager.getUtente(email);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }

            // se l'utente non esiste, mostra un messaggio di errore
            if (utente == null) {
                //manda messaggio di errore a rapairpwd.jsp
                // metto il messaggio di errore come attributo di Request e lo inoltro a repairpwd.jsp
                request.setAttribute("errorMessage", "Nessun utente è registrato con questa e-mail");
                RequestDispatcher rd = request.getRequestDispatcher("/repairpwd.jsp");
                rd.forward(request, response);
            } else {
                //altrimenti invia l'email con la password
                sendMail(email, utente.getPassword());
                request.getRequestDispatcher("/repairpwdSucces.jsp").forward(request, response);
            }

        } else {
            //errore
            request.setAttribute("errorMessage", "Parametro inputEmail non impostato");
            RequestDispatcher rd = request.getRequestDispatcher("/repairpwd.jsp");
            rd.forward(request, response);
        }

    }

    private void sendMail(String toEmail, String psw) {
        try {
            final String username = "cinemawebapp@gmail.com";
            final String password = "cinemawebapp1";
            
            // Get a Properties object to set the mailing configuration
            // parameters
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
            //We create the session object with the authentication information
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication
                        getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            //Create a new message
            Message msg = new MimeMessage(session);
            //Set the FROM and TO fields –
            msg.setFrom(new InternetAddress(username + ""));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail, false));
            msg.setSubject("Recupero password Cinemawebapp");
            msg.setText("La tua password è: " + psw);
            msg.setSentDate(new Date());
            //System.out.println("\nTrying to send email...\n");
            //We create the transport object to actually send the e-mail
            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            //System.out.println("\nEmail sent!\n");
        } catch (Exception e) {
            e.printStackTrace();
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
