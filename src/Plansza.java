import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


/**
 * Obiekt <code>Plansza</code>
 * @author Piotr Rogowski
 * @version 1.1 30/01/2020
 */
public class Plansza extends JPanel implements MouseListener {

    static Kafelek[][] macierzPlanszy;                                          // zawiera 15x15 elementow typu Kafelek trorzących plansze
    static Kafelek[][] macierzPlanszyWzorcowa;
    Kafelek tmpKafelek;
    int tmpIndexPlanszy;
    int tmpIndexTablicy;
    static Kafelek enteredKafelek;

    private static final Path pathDictionaryPL = Paths.get("extras/dictionary/pl.txt"); // sciezka do slownika polskiego


    Plansza(){
        macierzPlanszy = new Kafelek[15][15];
        macierzPlanszyWzorcowa = new Kafelek[15][15];
        this.setLayout( new GridLayout(15,15) );                    // wykorzystujemy funkcjonalnosc klasy bazowej tworzac siatkową strukture 15x15
        for( int i = 0; i < 15; i++ ){
            for( int j = 0; j < 15; j++ ){
                macierzPlanszy[i][j] = new Kafelek(0);
                macierzPlanszyWzorcowa[i][j] = new Kafelek(0);
                macierzPlanszy[i][j].addMouseListener(this);
                macierzPlanszyWzorcowa[i][j].addMouseListener(this);
                this.add( macierzPlanszy[i][j] ); } }
        inicjujPolaSpecjalne();

        /*zmien(2,2,"P");
        zmien(2,3,"I");
        macierzPlanszy[1][1].ruszalne = false;
        macierzPlanszy[1][2].ruszalne = false;*/
        zmien(2,2,"P");
        zmien(3,2,"I");
        macierzPlanszy[1][1].ruszalne = false;
        macierzPlanszy[2][1].ruszalne = false;
        //zmien(2,1,"I");
        //zmien(3,1,"O");
        //zmien(4,1,"T");
        //zmien(5,2,"R");
    }

    private void inicjujPolaSpecjalne(){
        zmien(8,8,2);
        zmien(7,7,4);
        zmien(9,7,4);
        zmien(7,9,4);
        zmien(9,9,4);
        zmien(6,6,3);
        zmien(10,6,3);
        zmien(6,10,3);
        zmien(10,10,3);
        zmien(2,2,6);
        zmien(3,3,6);
        zmien(4,4,6);
        zmien(5,5,6);
        zmien(5,11,6);
        zmien(4,12,6);
        zmien(3,13,6);
        zmien(2,14,6);
        zmien(11,5,6);
        zmien(12,4,6);
        zmien(13,3,6);
        zmien(14,2,6);
        zmien(11,11,6);
        zmien(12,12,6);
        zmien(13,13,6);
        zmien(14,14,6);
        zmien(8,4,4);
        zmien(7,3,4);
        zmien(9,3,4);
        zmien(4,1,4);
        zmien(12,1,4);
        zmien(6,2,3);
        zmien(10,2,3);
        zmien(8,1,5);
        zmien(1,1,5);
        zmien(15,1,5);
        zmien(1,15,5);
        zmien(15,15,5);
        zmien(4,8,4);
        zmien(3,7,4);
        zmien(3,9,4);
        zmien(2,6,3);
        zmien(2,10,3);
        zmien(1,8,5);
        zmien(8,1,5);
        zmien(8,15,5);
        zmien(15,8,5);
        zmien(1,4,4);
        zmien(1,12,4);
        zmien(8,12,4);
        zmien(7,13,4);
        zmien(9,13,4);
        zmien(4,15,4);
        zmien(12,15,4);
        zmien(6,14,3);
        zmien(10,14,3);
        zmien(12,8,4);
        zmien(13,7,4);
        zmien(13,9,4);
        zmien(15,4,4);
        zmien(15,12,4);
        zmien(14,6,3);
        zmien(14,10,3); }
    private void zmien(int x, int y, int rodzaj){        // moze byc uzywana tylko w metodzie inicjuj plansze w innym przypadku zaburzy klona macierzyPlanszy
        remove(((y-1)*15+x)-1);
        macierzPlanszy[x-1][y-1] = new Kafelek(rodzaj);
        macierzPlanszy[x-1][y-1].addMouseListener(this);
        macierzPlanszy[x-1][y-1].naPlanszy = true;
        macierzPlanszyWzorcowa[x-1][y-1] = new Kafelek(rodzaj);
        macierzPlanszyWzorcowa[x-1][y-1].addMouseListener(this);
        macierzPlanszyWzorcowa[x-1][y-1].naPlanszy = true;
        add( macierzPlanszy[x-1][y-1],((y-1)*15+x)-1 ); }
    private void zmien(int x, int y, String literka){        // TEST
        remove(((y-1)*15+x)-1);
        macierzPlanszy[x-1][y-1] = new Kafelek(literka);
        macierzPlanszy[x-1][y-1].addMouseListener(this);
        macierzPlanszy[x-1][y-1].naPlanszy = true;
        add( macierzPlanszy[x-1][y-1],((y-1)*15+x)-1 ); }
    public void przywroc(int x, int y){
        remove(((y-1)*15+x)-1);
        macierzPlanszy[x-1][y-1] = macierzPlanszyWzorcowa[x-1][y-1];
        macierzPlanszy[x-1][y-1].addMouseListener(this);
        macierzPlanszy[x-1][y-1].naPlanszy = true;
        add( macierzPlanszy[x-1][y-1],((y-1)*15+x)-1 );
        getParent().validate();
        getParent().repaint(); }
    public void przywroc(int index){
        int x = (index % 15)+1;
        int y = (index / 15)+1;
        przywroc(x,y); }
    public int getIndexPlanszy(Component comp){
        boolean elementExist;
        int i = 0;
        if(this.getComponent(0) != null) elementExist = true;
        else elementExist = false;

        while(elementExist){
            if( this.getComponent(i) != null ){
                if( comp == this.getComponent(i) ) return i;
                else i++; }
            else elementExist = false; }
        return -1; }
    public int getIndexTablicy(Component comp){
        Kafelek[] tab = new Kafelek[7];
        if(ScrabbleGUI.turaGracz1 == true)  tab = ScrabbleGUI.tablicaKafelkowGracza1;
        else    tab = ScrabbleGUI.tablicaKafelkowGracza2;
        for(int i=0; i<7; i++)
            if(tab[i] == comp)
                return i;
        return -1; }


    //obsługa poruszania kafelkow po planszy
    @Override
    public void mouseClicked(MouseEvent e) {

        // pierwsze klikniecie opiektu typu Kafelek
        if( tmpKafelek == null && e.getSource().getClass().getName() == "Kafelek" ){
            Kafelek aktualny = (Kafelek)e.getSource();

            // dla kafelkow bedacych na planszy parametr ruszalne musi byc true
            if(aktualny.ruszalne == true && aktualny.naPlanszy == true && (aktualny.rodzaj==1||aktualny.rodzaj==7)) {
                tmpKafelek = aktualny;
                tmpIndexPlanszy = this.getIndexPlanszy(aktualny); }

            // dla kafelkow bedacych u gracza parametr ruszalne musi byc true
            else if(aktualny.ruszalne == true && aktualny.naPlanszy == false && (aktualny.rodzaj==1||aktualny.rodzaj==7)) {
                tmpKafelek = aktualny;
                tmpIndexTablicy = getIndexTablicy(aktualny); }

            System.out.println(tmpKafelek);
        }

        // drugie klikniecie obiektu typu Kafelek
        else if( tmpKafelek != null && e.getSource().getClass().getName() == "Kafelek" ){
            Kafelek aktualny = (Kafelek)e.getSource();

            // 1 przypadek gdy oba są na planszy i maja atrybut ruszalne == true
            if(aktualny.ruszalne && aktualny.naPlanszy && tmpKafelek.naPlanszy){
                System.out.println("oba na planszy");
                // pierwszy z literka a drugi inicjujacy plansze
                if((tmpKafelek.rodzaj==1||tmpKafelek.rodzaj==7) && (aktualny.rodzaj!=1&&aktualny.rodzaj!=7)){
                    System.out.println("pierwszy literka drugi inicjujacy");
                    przywroc(getIndexPlanszy(tmpKafelek));
                    int xAkt = getIndexPlanszy(aktualny) % 15;
                    int yAkt = getIndexPlanszy(aktualny) / 15;
                    macierzPlanszy[xAkt][yAkt] = tmpKafelek;
                    this.add(macierzPlanszy[xAkt][yAkt],getIndexPlanszy(aktualny));
                    this.remove(getIndexPlanszy(aktualny));
                }
                // oba z literkami lub mydelko
                else if((tmpKafelek.rodzaj==1||tmpKafelek.rodzaj==7) && (aktualny.rodzaj==1||aktualny.rodzaj==7)){
                    System.out.println("oba sa literkami");
                    int indexAkt = getIndexPlanszy(aktualny);
                    this.add(aktualny,tmpIndexPlanszy);
                    this.add(tmpKafelek,indexAkt);
                }
            }

            // 2 przypadek gdy oba sa na rece
            else if(aktualny.ruszalne && !aktualny.naPlanszy && !tmpKafelek.naPlanszy){
                System.out.println("oba są u gracza");
                Kafelek[] tab;
                if(ScrabbleGUI.turaGracz1 == true)  tab = ScrabbleGUI.tablicaKafelkowGracza1;
                else    tab = ScrabbleGUI.tablicaKafelkowGracza2;
                ScrabbleGUI.panel3.add(tmpKafelek,getIndexTablicy(aktualny));
                ScrabbleGUI.panel3.add(aktualny,tmpIndexTablicy);
                tab[getIndexTablicy(aktualny)] = tmpKafelek;
                tab[tmpIndexTablicy] = aktualny;
            }
            // 3 przypadek gdy 1 jest na rece a 2 na planszy
            else if(aktualny.ruszalne && aktualny.naPlanszy && !tmpKafelek.naPlanszy){
                System.out.println("pierwszy u gracza, drugi na planszy");
                Kafelek[] tab;
                if(ScrabbleGUI.turaGracz1 == true)  tab = ScrabbleGUI.tablicaKafelkowGracza1;
                else    tab = ScrabbleGUI.tablicaKafelkowGracza2;
                int xAkt = getIndexPlanszy(aktualny) % 15;
                int yAkt = getIndexPlanszy(aktualny) / 15;

                // gdy oba są literkami
                if((tmpKafelek.rodzaj==1||tmpKafelek.rodzaj==7) && (aktualny.rodzaj==1||aktualny.rodzaj==7)){
                    System.out.println("oba są literkami");
                    tmpIndexPlanszy = getIndexPlanszy(aktualny);
                    tab[tmpIndexTablicy] = aktualny;
                    tab[tmpIndexTablicy].naPlanszy = false;
                    macierzPlanszy[xAkt][yAkt] = tmpKafelek;
                    macierzPlanszy[xAkt][yAkt].naPlanszy = true;
                    ScrabbleGUI.panel3.add(tab[tmpIndexTablicy],tmpIndexTablicy);
                    this.add(macierzPlanszy[xAkt][yAkt], tmpIndexPlanszy);
                }
                // gdy pierwszy jest literka a drugi tworzy plansze
                else if(aktualny.naPlanszy==true && (tmpKafelek.rodzaj==1||tmpKafelek.rodzaj==7) && (aktualny.rodzaj!=1&&aktualny.rodzaj!=7)){
                    System.out.println("pierwszy jest literka a drugi tworzacy");
                    tmpIndexPlanszy = getIndexPlanszy(aktualny);
                    tab[tmpIndexTablicy] = new Kafelek(0);
                    tab[tmpIndexTablicy].naPlanszy = false;
                    tab[tmpIndexTablicy].addMouseListener(this);
                    macierzPlanszy[xAkt][yAkt] = tmpKafelek;
                    macierzPlanszy[xAkt][yAkt].naPlanszy = true;
                    ScrabbleGUI.panel3.add(tab[tmpIndexTablicy],tmpIndexTablicy);
                    this.remove(tmpIndexPlanszy);
                    this.add(macierzPlanszy[xAkt][yAkt], tmpIndexPlanszy);
                }
            }
            // 4 przypadek gdy 1 jest na planszy a 2 na rece
            else if(aktualny.ruszalne && !aktualny.naPlanszy && tmpKafelek.naPlanszy && (tmpKafelek.rodzaj==1||tmpKafelek.rodzaj==7)){
                System.out.println("pierwszy na planszy, drugi u gracza");

                // gdy pierwszy jest literka drugi pusty
                if(aktualny.rodzaj == 0){
                    System.out.println("pierwszy literka drugi pusty");
                    tmpIndexTablicy = getIndexTablicy(aktualny);
                    Kafelek[] tab;
                    if(ScrabbleGUI.turaGracz1 == true)  tab = ScrabbleGUI.tablicaKafelkowGracza1;
                    else    tab = ScrabbleGUI.tablicaKafelkowGracza2;
                    przywroc(tmpIndexPlanszy);
                    tab[tmpIndexTablicy] = tmpKafelek;
                    tab[tmpIndexTablicy].naPlanszy = false;
                    ScrabbleGUI.panel3.remove(tmpIndexTablicy);
                    ScrabbleGUI.panel3.add(tab[tmpIndexTablicy],tmpIndexTablicy);
                }

                // gdy pierwszy i drugi są literkami
                else if(aktualny.rodzaj==1 || aktualny.rodzaj==7){
                    System.out.println("pierwszy i drugi sa literkami");

                    tmpIndexTablicy = getIndexTablicy(aktualny);
                    Kafelek[] tab;
                    if(ScrabbleGUI.turaGracz1 == true)  tab = ScrabbleGUI.tablicaKafelkowGracza1;
                    else    tab = ScrabbleGUI.tablicaKafelkowGracza2;
                    int xAkt = tmpIndexPlanszy % 15;
                    int yAkt = tmpIndexPlanszy / 15;
                    tab[tmpIndexTablicy] = tmpKafelek;
                    tab[tmpIndexTablicy].naPlanszy = false;
                    macierzPlanszy[xAkt][yAkt] = aktualny;
                    macierzPlanszy[xAkt][yAkt].naPlanszy = true;
                    ScrabbleGUI.panel3.add(tab[tmpIndexTablicy],tmpIndexTablicy);
                    this.add(macierzPlanszy[xAkt][yAkt], tmpIndexPlanszy);
                }
            }

            tmpKafelek = null;
            tmpIndexPlanszy = 0;
            tmpIndexTablicy = 0;
            System.out.println(aktualny);
        }

        getParent().repaint();
        getParent().validate();
    }
    // tworzenie opisów po najechaniu na pole specjalne w etykiecie dla pozostałych liter
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource().getClass().getName() == "Kafelek") {
            enteredKafelek = (Kafelek)e.getSource();
            if(((Kafelek) e.getSource()).opis != null)
                ScrabbleGUI.etykietaPozostaleLiter.setText(((Kafelek) e.getSource()).opis);
            else
                ScrabbleGUI.etykietaPozostaleLiter.setText(String.valueOf(ScrabbleGUI.bazaBostepnych.length())); }
        else enteredKafelek = null; }
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }


    // metoda sprawdzająca czy nowe slowo zawiera sie w slowniku
    static boolean zawieraWSlowniku(String szukany) {
        File file = new File(pathDictionaryPL.toString());
        Scanner in = null;

        try { in = new Scanner(file);
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        while (in.hasNext()) {
            String zdanie = in.nextLine();
            if(zdanie.equals(szukany.toLowerCase()))
                return true;
        }
        return false;
    }


    //metoda obliczajaca punkty
    static int obliczPkt(String [] noweLitery) {
        File file = new File("extras/tabela_punktow.txt/");
        Scanner in = null;


        int suma = 0;

        for(int i=0; i<noweLitery.length; i++) {

            try {
                in = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (in.hasNext()) {
                String linia = in.nextLine();
                if(String.valueOf(linia.charAt(0)).equals(noweLitery[i])) {
                    suma += Integer.parseInt(String.valueOf(linia.charAt(1)));
                    break; }
            } }

        return suma;
    }

    // sprawdza czy slowo jest poprawne i zwraca odpowiednia liczbe punktów
    public int sprawdz(){

        boolean jedenElement = false;
        String[][] tabWszystkie = new String[15][15];
        String[][] tabNowe = new String[15][15];
        int [][] tabWspolzednych = new int[15][2];
        int tmp = 0;

        for(int y=0; y<15; y++){
            for(int x=0; x<15; x++){
                if(macierzPlanszy[x][y].ruszalne && macierzPlanszy[x][y].litera!=null){
                    tabNowe[x][y] = macierzPlanszy[x][y].litera;
                    tabWspolzednych[tmp][0] = x+1;
                    tabWspolzednych[tmp][1] = y+1;
                    tmp++; }
                tabWszystkie[x][y] = macierzPlanszy[x][y].litera; }
        }

        boolean zmianaX = false;
        boolean zmianaY = false;

        for(int i=1; i<15; i++){
            if(tabWspolzednych[i][0] != tabWspolzednych[i-1][0] && tabWspolzednych[i-1][0] != 0 && tabWspolzednych[i][0] != 0)
                zmianaX = true;
            if(tabWspolzednych[i][1] != tabWspolzednych[i-1][1] && tabWspolzednych[i-1][1] != 0 && tabWspolzednych[i][1] != 0)
                zmianaY = true; }

        System.out.println("miana na x: " + zmianaX);
        System.out.println("miana na y: " + zmianaY);


        // przypadek braku zmiany lub dodania jednego elementu
        if(!zmianaX && !zmianaY) {
            System.out.println("brak zmiany lub jeden element");
            // sprawdzamy czy wystepuje jeden element
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 15; j++)
                    if (tabWspolzednych[j][i] != 0)
                        jedenElement = true;

            // gdy jest jeden element
            if(jedenElement){
                boolean gora = false;       // czy znajduje się kafelek z litera u góry
                boolean dol = false;        // * na dole
                boolean prawo = false;      // * po jego prawej
                boolean lewo = false;       // 8 po jego lewej

                boolean pionow = false;     // słowo ułożone pionowo
                boolean poziom = false;     // słowo ułożone poziomo
                boolean poczatek = false;   // literka znajduje się na początku wyrazu
                boolean srodek = false;     // literka znajduje się w środku wyrazu

                // współrzędne nowego elementu
                int x = tabWspolzednych[0][0];
                int y = tabWspolzednych[0][1];

                System.out.println("jeden element");
                System.out.println("X: " + x + ", Y: " + y);


                // do dwoch tablic przepisujemy cały kontekst jednego kafelka
                String [] pionowa = new String[15];
                String [] pozioma = new String[15];


                StringBuilder sbPoziom = new StringBuilder();
                StringBuilder sbPion = new StringBuilder();
                for(int i=0; i<15; i++) {

                    // stworzenie slow w poziomie i pionie
                    if (macierzPlanszy[x - 1][i].litera != null) sbPion.append(macierzPlanszy[x - 1][i].litera);
                    else sbPion.append(" ");

                    if (macierzPlanszy[i][y - 1].litera != null) sbPoziom.append(macierzPlanszy[i][y - 1].litera);
                    else sbPoziom.append(" ");
                }


                StringBuilder slowoPoz = new StringBuilder();
                for(int k=0; k<15; k++) {

                    boolean trwaSlowo = false;
                    if(macierzPlanszy[k][y-1].litera != null)
                        trwaSlowo = true;

                    if(trwaSlowo)
                        slowoPoz.append(macierzPlanszy[k][y-1].litera);

                    else {
                        if(k != 0 && k != 14 && macierzPlanszy[k-1][y-1].litera != null) {
                            System.out.println(slowoPoz.toString());
                            if(slowoPoz.toString().length() > 1 && !zawieraWSlowniku(slowoPoz.toString()))
                                return 0;                                // SPRAWDZENIE
                            slowoPoz = new StringBuilder(); } } }


                String slowoPionowe = sbPion.toString();
                StringBuilder sbPionowe = new StringBuilder();

                for(int k=0; k<15; k++) {

                    if(slowoPionowe.charAt(k) != ' ')
                        sbPionowe.append(slowoPionowe.charAt(k));

                    else if(k != 0 && k != 15 && slowoPionowe.charAt(k-1) != ' ') {
                        System.out.println(sbPionowe.toString());
                        if(sbPionowe.toString().length() > 1 && !zawieraWSlowniku(sbPionowe.toString()))
                            return 0;                                   // SPRAWDZANIE
                        sbPionowe = new StringBuilder(); } }
            }

            // gdy nie ma zmiany
            else
                return 0;
        }

        // przypadek błędnego rozmieszczenia elementów
        else if(zmianaX && zmianaY){

            System.out.println("zmiana na X i Y (błędne romieszczenie elementów)");

            for(int j=0; j<15; j++) {
                if(tabWspolzednych[j][0] != 0) {  // warunek wystepowanaia ustawionego elementu
                    int x = tabWspolzednych[j][0];
                    int y = tabWspolzednych[j][1];
                    int index = getIndexPlanszy(macierzPlanszy[x-1][y-1]);

                    for(int i=0; i<7; i++){     //przeszukanie reki gracza w celu znalezienia wolnego miejsca

                        Kafelek element = (Kafelek)ScrabbleGUI.panel3.getComponent(i);
                        Kafelek[] tabGracza = new Kafelek[7];

                        if(element.rodzaj == 0){    // Tu operacja powrotu kafelka do reki gracza
                            if(ScrabbleGUI.turaGracz1 == true) tabGracza = ScrabbleGUI.tablicaKafelkowGracza1;
                            else tabGracza = ScrabbleGUI.tablicaKafelkowGracza2;
                            int tmpRodzaj = -1;
                            String tmpLitera = "";

                            if(macierzPlanszy[x-1][y-1].rodzaj==1 || macierzPlanszy[x-1][y-1].rodzaj==7){
                                tmpRodzaj = macierzPlanszy[x-1][y-1].rodzaj;
                                tmpLitera = macierzPlanszy[x-1][y-1].litera; }

                            this.przywroc(index);

                            if(tmpRodzaj==1 || tmpRodzaj==7){
                                tabGracza[i] = new Kafelek(tmpLitera);
                                tabGracza[i].addMouseListener(this);
                                ScrabbleGUI.panel3.remove(i);
                                ScrabbleGUI.panel3.add(tabGracza[i], i);
                                getParent().repaint();
                                getParent().validate(); } } } } }

            return 0;
        }

        // przypadek ułożenia elementów pionowo
        else if(!zmianaX && zmianaY){
            System.out.println("zmiana Y (pionowo)");


            // określenie ilosci nowych elementów
            int nowe = 0;
            for(int i=0; i<15; i++)
                if (tabWspolzednych[i][0] != 0)
                    nowe++;

            for(int i=0; i<nowe; i++) {
                int y = tabWspolzednych[i][1];

                StringBuilder slowoPoz = new StringBuilder();
                for(int k=0; k<15; k++) {

                    if(macierzPlanszy[k][y-1].litera != null)
                        slowoPoz.append(macierzPlanszy[k][y-1].litera);

                    else if(k != 0 && k != 14 && macierzPlanszy[k-1][y-1].litera != null) {
                        if(slowoPoz.toString().length() > 1 && !zawieraWSlowniku(slowoPoz.toString())) {
                            System.out.println(slowoPoz.toString());
                            return 0; }                                                         // SPRAWDZANIE
                        slowoPoz = new StringBuilder(); } } }

            int x = tabWspolzednych[0][0];

            StringBuilder sbPion = new StringBuilder();
            for(int i=0; i<15; i++) {
                // stworzenie slow w poziomie i pionie
                if (macierzPlanszy[x - 1][i].litera != null) sbPion.append(macierzPlanszy[x - 1][i].litera);
                else sbPion.append(" "); }

            String slowoPionowe = sbPion.toString();
            StringBuilder sbPionowe = new StringBuilder();

            for(int k=0; k<15; k++) {

                if(slowoPionowe.charAt(k) != ' ')
                    sbPionowe.append(slowoPionowe.charAt(k));
                else if(k != 0 && k != 15 && slowoPionowe.charAt(k-1) != ' ') {
                    if(sbPionowe.toString().length() > 1 && !zawieraWSlowniku(sbPionowe.toString())) {
                        System.out.println(sbPionowe.toString());
                        return 0; }                                      // SPRAWDZENIE

                    sbPionowe = new StringBuilder(); } }
        }

        // przypadek ułożenia elementów poziomo
        else if(zmianaX && !zmianaY){
            System.out.println("zmiana X (poziomo)");



            // określenie ilosci nowych elementów
            int nowe = 0;
            for(int i=0; i<15; i++)
                if (tabWspolzednych[i][0] != 0)
                    nowe++;

            for(int i=0; i<nowe; i++) {
                int x = tabWspolzednych[i][0];


                StringBuilder sbPion = new StringBuilder();
                for(int j=0; j<15; j++) {
                    // stworzenie slow w pionie
                    if (macierzPlanszy[x - 1][j].litera != null) sbPion.append(macierzPlanszy[x - 1][j].litera);
                    else sbPion.append(" "); }


                String slowoPionowe = sbPion.toString();
                StringBuilder sbPionowe = new StringBuilder();

                for(int k=0; k<15; k++) {

                    if(slowoPionowe.charAt(k) != ' ')
                        sbPionowe.append(slowoPionowe.charAt(k));

                    else if(k != 0 && k != 15 && slowoPionowe.charAt(k-1) != ' ') {
                        System.out.println(sbPionowe.toString());
                        if(sbPionowe.toString().length() > 1 && !zawieraWSlowniku(sbPionowe.toString())) {
                            return 0; }                                                             // SPRAWDZANIE
                        sbPionowe = new StringBuilder(); } } }

            int y = tabWspolzednych[0][1];
            StringBuilder sbPion = new StringBuilder();
            for(int i=0; i<15; i++) {
                // stworzenie slow w poziomie i pionie
                if (macierzPlanszy[i][y-1].litera != null) sbPion.append(macierzPlanszy[i][y-1].litera);
                else sbPion.append(" "); }

            String slowoPionowe = sbPion.toString();
            StringBuilder sbPionowe = new StringBuilder();

            for(int k=0; k<15; k++) {

                if(slowoPionowe.charAt(k) != ' ')
                    sbPionowe.append(slowoPionowe.charAt(k));
                else if(k != 0 && k != 15 && slowoPionowe.charAt(k-1) != ' ') {
                    System.out.println(sbPionowe.toString());
                    if(sbPionowe.toString().length() > 1 && !zawieraWSlowniku(sbPionowe.toString()))
                        return 0;                                                                           // SPRAWDZANIE
                    sbPionowe = new StringBuilder(); } }
        }


        // OBLICZYC PUNKTY
        int nowe = 0;
        for(int i=0; i<15; i++)
            if (tabWspolzednych[i][0] != 0)
                nowe++;

        String [] noweLiterki = new String[nowe];
        for(int i=0; i<nowe; i++) {
            noweLiterki[i] = macierzPlanszy[tabWspolzednych[i][0] - 1][tabWspolzednych[i][1] - 1].litera;
            macierzPlanszy[tabWspolzednych[i][0] - 1][tabWspolzednych[i][1] - 1].ruszalne = false;
        }



        return obliczPkt(noweLiterki);
    }



}
