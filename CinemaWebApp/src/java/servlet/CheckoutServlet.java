/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.itextpdf.text.BaseColor;
import db.DBManager;
import db.Film;
import db.Posto;
import db.Prezzo;
import db.Spettacolo;
import db.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Biglietto;

// PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

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
        response.sendRedirect(request.getContextPath() + "/prenotazioneMessage.jsp");
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
            response.sendRedirect(request.getContextPath() + "/prenotazioneMessage.jsp");
        }

        //se l'untente non ha abbastanza credito
        if (credito < totalePagamento) {
            // errore non abbastanza credito
            request.setAttribute("exception", "Credito non sufficiente");
            RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
            rd.forward(request, response);
            // errore
            // response.sendRedirect(request.getContextPath() + "/prenotazioneMessage.jsp");
        } else {

            // contiene una lista di oggetti biglietto per creare il file pdf
            List<Biglietto> bigliettiPerPDF = new ArrayList();

            // questa variabile indica se la prenotazione è permessa oppure no
            // (se ad esempio un posto è gia prenotato viene settata a false)
            boolean allowPrenotazione = true;

            // controlla se tutti i posti sono liberi e genera la lista bigliettiPerPDF
            Iterator iter = carrello.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry pair = (Map.Entry) iter.next();
                int idPosto = (int) pair.getKey();
                Prezzo prezzo = (Prezzo) pair.getValue();

                try {
                    // se uno dei posti è gia occupato non permette la prenotazione
                    if (!manager.isPostoLibero(idPosto, idSpettacolo)) {
                        allowPrenotazione = false;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                ////////////////////////////////////////////////////////////////
                // genera il biglietto e aggiungilo alla lista
                Biglietto b = new Biglietto();
                Posto pst;
                Spettacolo spett;
                Film f;
                Timestamp dataTimestamp;
                String dataStr;
                try {
                    spett = manager.getSpettacoloById(idSpettacolo);
                    f = manager.getFilmById(spett.getIdFilm());
                    pst = manager.getPostoById(idPosto);

                    dataTimestamp = spett.getDataOra();
                    dataStr = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataTimestamp);

                    b.setEmailUtente(utente.getEmail());
                    b.setTitoloFilm(f.getTitolo());
                    b.setIdPosto(pst.getIdPosto());
                    b.setIdSala(pst.getIdSala());
                    b.setRigaPosto(pst.getRiga());
                    b.setColonnaPosto(pst.getColonna());
                    b.setPrezzoBiglietto(prezzo.getPrezzo());
                    b.setTipoBiglietto(prezzo.getTipo());
                    b.setDataOraSpettacolo(dataStr);

                } catch (SQLException ex) {
                    Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                bigliettiPerPDF.add(b);
                ////////////////////////////////////////////////////////////////
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
                generaPdf(bigliettiPerPDF);

                // Redirect a pagina di successo
                request.setAttribute("succes", 1);
                RequestDispatcher rd = request.getRequestDispatcher("/prenotazioneMessage.jsp");
                rd.forward(request, response);
            } else {
                // errore posto già prenotato
                request.setAttribute("succes", 0);
                RequestDispatcher rd = request.getRequestDispatcher("/prenotazioneMessage.jsp");
                rd.forward(request, response);
                
                // errore posto prenotato
                //request.setAttribute("exception", "Uno dei posti è già stato prenotato");
                //RequestDispatcher rd = request.getRequestDispatcher("/errore.jsp");
                //rd.forward(request, response);
            }

        }

    }

    private void generaPdf(List<Biglietto> biglietti) {
         String FILE = getServletContext().getRealPath("/") + "/WEB-INF/PDF/Testone.pdf";

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            aggiungiMetaDati(document, biglietti.get(0).getTitoloFilm());

            for (Biglietto b : biglietti) {
                aggiungiBiglietto(document, b);
            }
            document.close();
        } catch (Exception e) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        /* //debug:
         System.out.println("BIGLIETTO:");
         System.out.println("Titolo: " + b.getTitoloFilm());
         System.out.println("ID posto: " + b.getIdPosto());
         System.out.println("Riga posto: " + b.getRigaPosto());
         System.out.println("Colonna posto: " + b.getColonnaPosto());
         System.out.println("Prezzo: " + b.getPrezzoBiglietto());
         System.out.println("TipoBiglietto: " + b.getTipoBiglietto());
         System.out.println("DataOra: " + b.getDataOraSpettacolo());
         System.out.println("---------------");
         */
    }

    private static void aggiungiMetaDati(Document document, String titolo) {
        document.addTitle("Prenotazione " + titolo);
        document.addAuthor("CinemaWebApp");
        document.addCreator("CinemaWebApp");
    }

    private static void aggiungiBiglietto(Document document, Biglietto biglietto) throws DocumentException {

        Font bigFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font bigFontOrange = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.ORANGE);
        Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font verySmall = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

        Paragraph paragrafoBigl = new Paragraph();

        // Aggiunge due linee vuota
        aggiungiLineaVuota(paragrafoBigl,
                2);

        // Aggiunge il titolo
        paragrafoBigl.add(
                new Paragraph("Ticket di prenotazione per " + biglietto.getTitoloFilm(), bigFontOrange));
        // Aggiungiamo il titolo
        paragrafoBigl.add(
                new Paragraph("Utente: " + biglietto.getEmailUtente(), verySmall));

        aggiungiLineaVuota(paragrafoBigl,
                1);

        // Aggiungi informazioni sulla prenotazione
        paragrafoBigl.add(
                new Paragraph("Film: " + biglietto.getTitoloFilm(), smallBold));

        paragrafoBigl.add(
                new Paragraph("Data e ora: " + biglietto.getDataOraSpettacolo(), smallBold));

        paragrafoBigl.add(
                new Paragraph("Tipo biglietto: " + biglietto.getTipoBiglietto() + " - "
                        + biglietto.getPrezzoBiglietto() + " €", smallBold));

        paragrafoBigl.add(
                new Paragraph("Sala " + biglietto.getIdSala(), smallBold));

        paragrafoBigl.add(
                new Paragraph("Posto " + biglietto.getIdPosto() 
                        + " (riga: " + biglietto.getRigaPosto() + " - colonna : " 
                        + biglietto.getColonnaPosto() + ")", smallBold));

        paragrafoBigl.add(
                new Paragraph("CinemaWebApp", smallBold));
        paragrafoBigl.add(
                new Paragraph("3883 Howard Hughes Pkwy, Las Vegas, NV 89169", smallBold));

        // Aggiunta al documento
        document.add(paragrafoBigl);

        // Nuova pagina
        document.newPage();
    }

    private static void aggiungiLineaVuota(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
