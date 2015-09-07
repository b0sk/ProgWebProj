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
import java.util.Date;
import java.util.Properties;

import utils.Biglietto;

// PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
// QR-CODE
import com.itextpdf.text.pdf.BarcodeQRCode;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
// MAIL
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class CheckoutServlet extends HttpServlet {

    private String PDF_DIRECTORY;

    private DBManager manager;

    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
        this.PDF_DIRECTORY = getServletContext().getRealPath("/") + "/WEB-INF/PDF/";
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

        try {
            // Controlla se lo spettacolo è al momento attivo
            if (!manager.isSpettacoloAttivo(idSpettacolo)) {
                // errrore lo spettacolo è già passato
                response.sendRedirect(request.getContextPath() + "/prenotazioneMessage.jsp");
            } else {

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
                    request.setAttribute("errorMessag", "Credito non sufficiente");
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

                        // se uno dei posti è gia occupato non permette la prenotazione
                        if (!manager.isPostoLibero(idPosto, idSpettacolo)) {
                            allowPrenotazione = false;
                        }

                        ////////////////////////////////////////////////////////////////
                        // genera il biglietto e aggiungilo alla lista
                        Biglietto b = new Biglietto();
                        Posto pst;
                        Spettacolo spett;
                        Film f;
                        Timestamp dataTimestamp;
                        String dataStr;

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

                        bigliettiPerPDF.add(b);
                        ////////////////////////////////////////////////////////////////
                    }

                    // Se la prenotazione è permessa, prenota i posti
                    if (allowPrenotazione == true) {

                        iter = carrello.entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry pair = (Map.Entry) iter.next();
                            int idPosto = (int) pair.getKey();
                            Prezzo p = (Prezzo) pair.getValue();
                            int idPrezzo = (int) p.getIdPrezzo();

                            manager.prenotaPosto(utente.getIdUtente(), idSpettacolo, idPrezzo, idPosto);

                            // SCALA CREDITO
                            credito -= p.getPrezzo();
                        }

                        // Se l'utente ha pagato con il credito
                        // addebita il credito rimanente all'utente
                        if (tipoPagamento.equals("creditoUtente")) {
                            manager.setUserCredit(utente.getIdUtente(), credito);
                            // aggiorna la sessione con i nuovi dati utente
                            Utente u = manager.getUtente(utente.getEmail());
                            session.setAttribute("utente", u);
                        }

                        // genera il file PDF contente i biglietti
                        generaPdf(bigliettiPerPDF, utente.getEmail() + ".pdf");
                        // invia l'email
                        sendMailWithAttachment(utente.getEmail(), PDF_DIRECTORY + utente.getEmail() + ".pdf");

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
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void generaPdf(List<Biglietto> biglietti, String filename) {
        String file = PDF_DIRECTORY + filename;

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
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
        // Genera e aggiungi il QR-Code
        // Create QR Code by using BarcodeQRCode Class
        BarcodeQRCode my_code = new BarcodeQRCode(biglietto.getEmailUtente() + "\n"
                + biglietto.getTitoloFilm() + "\n"
                + biglietto.getDataOraSpettacolo() + "\n"
                + "posto " + biglietto.getIdPosto() + " (" + biglietto.getRigaPosto() + "-" + biglietto.getColonnaPosto() + ")" + "\n"
                + biglietto.getTipoBiglietto() + " - prezzo: " + biglietto.getPrezzoBiglietto(), 200, 200, null);
        // Get Image corresponding to the input string
        Image qr_image = my_code.getImage();
        // Stamp the QR image into the PDF document
        paragrafoBigl.add(qr_image);

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

    private void sendMailWithAttachment(String toEmail, String percorsoAllegato) {
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
            // Set Subjetc
            msg.setSubject("Prenotazione CinemaWebApp");
            // Set sent date
            msg.setSentDate(new Date());

            // Create multipart message
            Multipart multipart = new MimeMultipart();
            // Set Text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("La tua prenotazione è stata effettuata con successo.\n "
                    + "Il PDF allegato a questa e-mail contiene i biglietti che hai prenotato.");
            // Add pdf attachment
            DataSource source = new FileDataSource(percorsoAllegato);
            BodyPart messageBodyPart2 = new MimeBodyPart();
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName("Biglietto.pdf");

            // Add parts to Multipart message
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            msg.setContent(multipart);
            
            //We create the transport object to actually send the e-mail
            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, username, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
