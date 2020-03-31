import javax.swing.*;
import java.awt.*;

/**
 * Obiekt <code>Kafelek</code>
 * @author Piotr Rogowski
 * @version 1.0 29/01/2020
 */

public class Kafelek extends JPanel {

    public String litera = null;
    public int rodzaj;
    boolean ruszalne;
    boolean naPlanszy;
    public String opis = null;

    final static Color kolorPustych = Color.gray.brighter();                    // rodzaj 0
    final static Color kolorZLiterkami = Color.orange.darker();                 // rodzaj 1
    final static Color kolorLiterek = Color.black;
    final static Color kolorSrodkowego = Color.orange;                          // rodzaj 2
    final static Color kolorCiemnoNiebieski = Color.blue.darker();              // rodzaj 3
    final static Color kolorJasnoNiebieski = Color.blue.brighter();             // rodzaj 4
    final static Color kolorCiemnoCzerwone = Color.red.darker();                // rodzaj 5
    final static Color kolorJasnoCzerwone = Color.red.brighter();               // rodzaj 6
    final static Color kolorMydlo = Color.white;                                // rodzaj 7


    @Override
    public String toString(){ return "RODZAJ: " + String.valueOf(rodzaj) + "; RUSZALNE: " + String.valueOf(ruszalne) + "; NA PLANSZY: " + String.valueOf(naPlanszy) + "; LITERKA: " + litera; }

    Kafelek(){  }           // tworzy kafelek 'pusty'

    Kafelek(int x){         // tworzy kafelek inicjujący plansze
        rodzaj = x;
        ruszalne = true;
        naPlanszy = true;
        dodajOpis(); }

    Kafelek(String literka){
        if(literka == "X")
            rodzaj = 7;
        else
            rodzaj = 1;
        litera = literka; // dorobić literka dla mydełka
        ruszalne = true;
        naPlanszy = false;
        this.add(new JLabel(literka)); }

    private void dodajOpis(){
        if(this.rodzaj == 6)
            opis = "Podwójna premia literowa";
        if(this.rodzaj == 5)
            opis = "Potrójna premia literowa";
        if(this.rodzaj == 3)
            opis = "Podwójna premia słowna";
        if(this.rodzaj == 4)
            opis = "Potrójna premia słowna"; }

    public void paint(Graphics g) {

        Dimension d = getSize();
        if(rodzaj == 0){
            g.setColor(kolorPustych);                                               // Wypełnia panel kolorem w zalezżnosci od rodzaju kafelka
            g.fillRect(0, 0, d.width - 1, d.height - 1); }
        if(rodzaj == 1){
            g.setColor(kolorZLiterkami);
            g.fillRect(0, 0, d.width - 1, d.height - 1);
            g.setColor(kolorLiterek);
            g.drawString(litera, d.width/2, d.height/2); }
        if(rodzaj == 2){
            g.setColor(kolorSrodkowego);
            g.fillRect(0, 0, d.width - 1, d.height - 1); }
        if(rodzaj == 3){
            g.setColor(kolorCiemnoNiebieski);
            g.fillRect(0, 0, d.width - 1, d.height - 1); }
        if(rodzaj == 4){
            g.setColor(kolorJasnoNiebieski);
            g.fillRect(0, 0, d.width - 1, d.height - 1); }
        if(rodzaj == 5){
            g.setColor(kolorCiemnoCzerwone);
            g.fillRect(0, 0, d.width - 1, d.height - 1); }
        if(rodzaj == 6){
            g.setColor(kolorJasnoCzerwone);
            g.fillRect(0, 0, d.width - 1, d.height - 1); }
        if(rodzaj == 7){
            g.setColor(kolorMydlo);
            g.fillRect(0, 0, d.width - 1, d.height - 1); }

        g.setColor(Color.blue.darker().darker());                                   // Rysuje ramkę dookoła panela
        g.drawRect(0, 0, d.width - 1, d.height - 1);           // *

    }

}
