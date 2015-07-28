import java.io.FileOutputStream;
import java.util.Date;

/*
import com.lowagie.itextpdf.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.BaseColor;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
*/
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class BigliettoPDF {
 private static String FILE = "Test.pdf";
 private static Font bigFont  = new Font(Font.FontFamily.TIMES_ROMAN, 18,  Font.BOLD);
 private static Font redFont  = new Font(Font.FontFamily.TIMES_ROMAN, 12,  Font.NORMAL, BaseColor.RED);
 private static Font subFont  = new Font(Font.FontFamily.TIMES_ROMAN, 16,  Font.BOLD);
 private static Font smallBold  = new Font(Font.FontFamily.TIMES_ROMAN, 12,  Font.BOLD);
 
 public static void main(String[] args) {
  try {
   Document document = new Document();
   PdfWriter.getInstance(document, new FileOutputStream(FILE));
   document.open();
   aggiungiMetaDati(document);
   aggiungiPrefazione(document);
   //aggiungiContenuto(document);
   document.close();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
  
 /* iText permette di aggiungere metadati al pdf che possono essere 
  * visualizzati in Adobe Reader da File -> Proprietà */
 private static void aggiungiMetaDati(Document document) {
  document.addTitle("Prenotazione \"[TITOLO FILM]\"");
//  document.addSubject("Uso di iText");
  //document.addKeywords("Java, PDF, iText");
  document.addAuthor("CinemaWebApp");
  document.addCreator("CinemaWebApp");
 }
 
 private static void aggiungiPrefazione(Document document) throws DocumentException {
  Paragraph prefazione = new Paragraph();
   
  // Aggiungiamo una linea vuota
  aggiungiLineaVuota(prefazione, 1);
   
  // Aggiungiamo il titolo
  prefazione.add(new Paragraph("Prenotazione \"[TITOLO FILM]\"", bigFont));
 
  aggiungiLineaVuota(prefazione, 1);
   
  // Questa linea scrive "Documento generato da: nome utente, data"
  prefazione.add(new Paragraph("Film: Titolo", smallBold));
   
  prefazione.add(new Paragraph("Data: data spettacolo...", smallBold));
   
  prefazione.add(new Paragraph("Ora: ora spettacolo..", smallBold));
  
  prefazione.add(new Paragraph("Tipo biglietto: tipo..", smallBold));
  
  prefazione.add(new Paragraph("Sala X", smallBold));
 
  prefazione.add(new Paragraph("Posto Y", smallBold));
  
  prefazione.add(new Paragraph("CinemaWebApp", smallBold));
  prefazione.add(new Paragraph("3883 Howard Hughes Pkwy, Las Vegas, NV 89169", smallBold));
  
  // Aggiunta al documento
  document.add(prefazione);
   
  // Nuova pagina
  document.newPage();
 }
 
 private static void aggiungiContenuto(Document document) throws DocumentException {
 
  // Il secondo parametro è il numero di capitolo
  Chapter chapter = new Chapter(new Paragraph("Primo Capitolo", bigFont), 1);
 
  Paragraph sectionParagraph = new Paragraph("Sezione 1", subFont);
  Section section = chapter.addSection(sectionParagraph);
  section.add(new Paragraph("Paragrafo 1"));
 
  sectionParagraph = new Paragraph("Sezione 2", subFont);
  section = chapter.addSection(sectionParagraph);
  section.add(new Paragraph("Paragrafo 1"));
  section.add(new Paragraph("Paragrafo 2"));
  section.add(new Paragraph("Paragrafo 3"));
 
  // Aggiungiamo una lista
  creaLista(section);
   
  Paragraph paragraph = new Paragraph();
  aggiungiLineaVuota(paragraph, 2);
  section.add(paragraph);
 
  // Aggiungiamo una tabella
  creaTabella(section);
 
  // Aggiunta al documento
  document.add(chapter);
 
  // Prossimo capitolo
 
  // Il secondo parametro è il numero di capitolo
  chapter = new Chapter(new Paragraph("Secondo Capitolo", bigFont), 2);
 
  sectionParagraph = new Paragraph("Sezione 1", subFont);
  section = chapter.addSection(sectionParagraph);
  section.add(new Paragraph("Paragrafo 1"));
 
  // Aggiunta al documento
  document.add(chapter);
 }
 
 private static void creaTabella(Section section) throws BadElementException {
  PdfPTable tabella = new PdfPTable(3);
 
  // tabella.setBorderColor(BaseColor.GRAY);
  // tabella.setPadding(4);
  // tabella.setSpacing(4);
  // tabella.setBorderWidth(1);
 
  PdfPCell c1 = new PdfPCell(new Phrase("Titolo 1"));
  c1.setHorizontalAlignment(Element.ALIGN_CENTER);
  c1.setGrayFill(0.8f);
  tabella.addCell(c1);
 
  c1 = new PdfPCell(new Phrase("Titolo 2"));
  c1.setHorizontalAlignment(Element.ALIGN_CENTER);
  c1.setGrayFill(0.8f);
  tabella.addCell(c1);
 
  c1 = new PdfPCell(new Phrase("Titolo 3"));
  c1.setHorizontalAlignment(Element.ALIGN_CENTER);
  c1.setGrayFill(0.8f);
  tabella.addCell(c1);
  tabella.setHeaderRows(1);
 
  tabella.addCell("1.0");
  tabella.addCell("1.1");
  tabella.addCell("1.2");
  tabella.addCell("2.1");
  tabella.addCell("2.2");
  tabella.addCell("2.3");
 
  section.add(tabella);
 
 }
 
 private static void creaLista(Section section) {
  List list = new List(true, false, 10);
  list.add(new ListItem("Punto primo"));
  list.add(new ListItem("Punto secondo"));
  list.add(new ListItem("Punto terzo"));
  section.add(list);
 }
 
 private static void aggiungiLineaVuota(Paragraph paragraph, int number) {
  for (int i = 0; i < number; i++) {
   paragraph.add(new Paragraph(" "));
  }
 }
}