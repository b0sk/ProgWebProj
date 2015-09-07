/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import db.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.random;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Patrik
 */
public class SettaProgrammazione extends HttpServlet {

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
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     response.setContentType("text/html;charset=UTF-8");
     try (PrintWriter out = response.getWriter()) {
     out.println("<!DOCTYPE html>");
     out.println("<html>");
     out.println("<head>");
     out.println("<title>Servlet SettaProgrammazione</title>");            
     out.println("</head>");
     out.println("<body>");
     out.println("<h1>Servlet SettaProgrammazione at " + request.getContextPath() + "</h1>");
     out.println("</body>");
     out.println("</html>");
     }
     }*/
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SettaProgrammazione</title>");
            out.println("</head>");
            out.println("<body>");

            // Form per settare la programmazione
            out.println("<h3>Imposta la programmazione:</h1>");
            out.println("<form action=\"SettaProgrammazione\" method=\"POST\">");
            out.println("X: ");
            out.println("<input type=\"text\" name=\"x\" />");
            out.println("<input type=\"submit\" name=\"submit\" />");
            out.println("</form>");

            out.println("<br>");

            // Form per resettare la programmazione a quella di default
            out.println("<form action=\"SettaProgrammazione\" method=\"POST\">");
            out.println("Resetta la programmazione ");
            out.println("<input type=\"hidden\" name=\"reset\" />");
            out.println("<input type=\"submit\" name=\"submit\" value=\"Resetta\"/>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
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
        //processRequest(request, response);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SettaProgrammazione</title>");
            out.println("</head>");
            out.println("<body>");

            if (request.getParameter("reset") != null) {
                // Resetta la programmazione di default
                /*
                 Esempio:
                 UPDATE APP.SPETTACOLO SET "DATA_ORA" = '2015-10-24 14:00:00.0' WHERE ID_SPETTACOLO = 3;
                 ...
                 UPDATE APP.SPETTACOLO SET "DATA_ORA" = '2015-10-24 23:00:00.0' WHERE ID_SPETTACOLO = 22;
                 */

                resetSpettacoli();
                out.println("<p>Spettacoli resettati</p>");
            } else if (request.getParameter("x") != null) {
                // Setta la programmazione in modalita esame
                int x = Integer.parseInt(request.getParameter("x"));
                impostaSpettacoli(x);
                out.println("<p>Spettacoli impostati in base a " + x + "</p>");
            } else {
                //errore
                out.println("<p>Si è verificato un errore</p>");
            }

            out.println("</body>");
            out.println("</html>");

        } catch (ParseException ex) {
            Logger.getLogger(SettaProgrammazione.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SettaProgrammazione.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetSpettacoli() throws ParseException, SQLException {
        String time1 = "2015-10-10 14:00:00.0";
        String time2 = "2015-10-10 17:00:00.0";
        String time3 = "2015-10-10 20:00:00.0";
        String time4 = "2015-10-10 23:00:00.0";

        //Timestamp t1 = new Timestamp(2015, 10, 24, 14, 00, 00, 00);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

        for (int i = 3; i < 23; i++) {
            // time1                
            Date parsedDate = dateFormat.parse(time1);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time2
            i++;
            parsedDate = dateFormat.parse(time2);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time3
            i++;
            parsedDate = dateFormat.parse(time3);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time4
            i++;
            parsedDate = dateFormat.parse(time4);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

        }

        time1 = "2015-10-11 14:00:00.0";
        time2 = "2015-10-11 17:00:00.0";
        time3 = "2015-10-11 20:00:00.0";
        time4 = "2015-10-11 23:00:00.0";

        for (int i = 22; i < 43; i++) {
            // time1                
            Date parsedDate = dateFormat.parse(time1);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time2
            i++;
            parsedDate = dateFormat.parse(time2);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time3
            i++;
            parsedDate = dateFormat.parse(time3);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time4
            i++;
            parsedDate = dateFormat.parse(time4);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

        }

        // nel passato..
        time1 = "2015-06-11 14:00:00.0";
        time2 = "2015-06-11 17:00:00.0";
        time3 = "2015-06-11 20:00:00.0";
        time4 = "2015-06-11 23:00:00.0";

        for (int i = 42; i < 63; i++) {
            // time1                
            Date parsedDate = dateFormat.parse(time1);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time2
            i++;
            parsedDate = dateFormat.parse(time2);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time3
            i++;
            parsedDate = dateFormat.parse(time3);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            //time4
            i++;
            parsedDate = dateFormat.parse(time4);
            timestamp = new Timestamp(parsedDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

        }

    }

    private void impostaSpettacoli(int x) throws SQLException {
        for (int i = 3; i < 23; i++) {
            //System.out.println(i + "----------------");

            //first
            // set the starting time to Current time + rand()*X minutes
            // random ranges from 0.5 to 1.5
            long startTime = (long) (System.currentTimeMillis() + (random() * (x * 60 * 1000)));
            // create the date object with startTime
            Date startTimeDate = new Date(startTime);
            Timestamp timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);

            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+20, timestamp);
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+40, timestamp);
            
            //second
            i++;
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);
            
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+20, timestamp);
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+40, timestamp);

            //third
            i++;
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);
            
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+20, timestamp);
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+40, timestamp);

            //fourth
            i++;
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i, timestamp);
            
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+20, timestamp);
            startTime += x * 60 * 1000;
            startTimeDate = new Date(startTime);
            timestamp = new Timestamp(startTimeDate.getTime());
            manager.setDataOraSpettacolo(i+40, timestamp);

        }

    }

}
