import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Obiekt <code>ScrabbleGUI</code>
 * @author Piotr Rogowski
 * @version 1.0 29/01/2020
 */

public class ScrabbleGUI implements ActionListener {

    JFrame szkielet = new JFrame("Scrabble");
    static Plansza plansza = new Plansza();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    static JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    static JLabel etykietaGracz1 = new JLabel("Player 1", JLabel.CENTER);
    static JLabel etykietaGracz2 = new JLabel("Player 2", JLabel.CENTER);
    static JLabel etykietaGracz1Czas = new JLabel("CZAS", JLabel.CENTER);
    static JLabel etykietaGracz2Czas = new JLabel("CZAS", JLabel.CENTER);
    JLabel etykietaGracz1Pkt = new JLabel("0", JLabel.CENTER);
    JLabel etykietaGracz2Pkt = new JLabel("0", JLabel.CENTER);
    static JLabel etykietaPozostaleLiter = new JLabel("100", JLabel.CENTER);
    static JButton przyciskGotowe = new JButton("Gotowe");
    Container kontener = szkielet.getContentPane();

    // "AAAAAAAAAEEEEEEEIIIIIIIINNNNNOOOOOORRRRSSSSWWWWZZZZZCCCDDDKKKLLLMMMPPPTTTYYYYBBGGHHJJŁŁUUĄĘFÓŚŻĆŃŹXX"
    static String bazaBostepnych = "AAAAAAAAAEEEEEEEIIIIIIIINNNNNOOOOOORRRRSSSSWWWWZZZZZCCCDDDKKKLLLMMMPPPTTTYYYYBBGGHHJJŁŁUUĄĘFÓŚŻĆŃŹXX";
    static Kafelek[] tablicaKafelkowGracza1;
    static Kafelek[] tablicaKafelkowGracza2;
    static boolean turaGracz1;
    static boolean turaGracz2;

    ScrabbleGUI() {

        szkielet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                    // Odpowiednie zamkniecie programu

        Dimension wymiaryNapisow = new Dimension(((Integer)Toolkit.getDefaultToolkit().getScreenSize().width)*1/15,((Integer)Toolkit.getDefaultToolkit().getScreenSize().height)*1/30);
        etykietaGracz2.setPreferredSize(wymiaryNapisow);
        etykietaGracz1Czas.setPreferredSize(new Dimension(wymiaryNapisow.width/2,wymiaryNapisow.height));
        etykietaGracz2Czas.setPreferredSize(new Dimension(wymiaryNapisow.width/2,wymiaryNapisow.height));
        etykietaGracz1Pkt.setPreferredSize(new Dimension(wymiaryNapisow.width/2,wymiaryNapisow.height));
        etykietaGracz2Pkt.setPreferredSize(new Dimension(wymiaryNapisow.width/2,wymiaryNapisow.height));
        panel1.setLayout( new GridLayout(1,2) );
        panel2.setLayout( new GridLayout(1,4) );
        panel3.setLayout( new GridLayout(1,7) );
        panel4.setLayout( new GridLayout(2,1) );
        panel5.setLayout( new GridLayout(4,1) );    //scala powyzsze panele jeden pod drugim
        panel1.add(etykietaGracz1);
        panel1.add(etykietaGracz2);
        panel2.add(etykietaGracz1Czas);
        panel2.add(etykietaGracz1Pkt);
        panel2.add(etykietaGracz2Czas);
        panel2.add(etykietaGracz2Pkt);
        tablicaKafelkowGracza1 = new Kafelek[7];
        tablicaKafelkowGracza2 = new Kafelek[7];
        for(int i=0; i<7; i++){
            tablicaKafelkowGracza2[i] = new Kafelek(losuj());
            tablicaKafelkowGracza2[i].addMouseListener(plansza); }
        for(int i=0;i<7;i++){
            tablicaKafelkowGracza1[i] = new Kafelek(losuj());
            tablicaKafelkowGracza1[i].addMouseListener(plansza);
            panel3.add( tablicaKafelkowGracza1[i] ); }
        panel4.add(etykietaPozostaleLiter);
        panel4.add(przyciskGotowe);
        panel5.add(panel1);
        panel5.add(panel2);
        panel5.add(panel3);
        panel5.add(panel4);
        kontener.add(plansza, BorderLayout.CENTER);
        kontener.add(panel5,BorderLayout.EAST);
        szkielet.setSize(new Dimension(((Integer)Toolkit.getDefaultToolkit().getScreenSize().width)*3/5,((Integer)Toolkit.getDefaultToolkit().getScreenSize().height)*4/5));
        szkielet.setVisible(true);
        szkielet.addMouseListener(plansza);
        przyciskGotowe.addActionListener(this);
        szkielet.setLocationRelativeTo(null);


        iloscPozostalychLiter();                                 //okresla ilosc pozostalych metod gdy nie znajdzie pliku wyłączy program
        //etykietaGracz1.setText(JOptionPane.showInputDialog("Gracz 1"));     //input nazw graczy
        //etykietaGracz2.setText(JOptionPane.showInputDialog("Gracz 2"));
        if(etykietaGracz1.getText().length() == 0 || etykietaGracz2.getText().length() == 0){   // ustawienie nazw graczy na Player 1 i 2
            etykietaGracz1.setText("Player 1");
            etykietaGracz2.setText("Player 2"); }

        ScrabbleGUI.turaGracz1 = true;          //wazne aby określić kolejność graczy przed wywołaniem metody run z klasy Zegar
        ScrabbleGUI.turaGracz2 = false;
        Zegar zegar = new Zegar();
        zegar.run();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(turaGracz1) {
            int tmp = Integer.parseInt(etykietaGracz1Pkt.getText());
            tmp += plansza.sprawdz();
            etykietaGracz1Pkt.setText(String.valueOf(tmp));

            for(int i=0;i<7;i++){
                if(tablicaKafelkowGracza1[i].rodzaj==0) {
                    tablicaKafelkowGracza1[i] = new Kafelek(losuj());
                    tablicaKafelkowGracza1[i].addMouseListener(plansza);
                    panel3.add(tablicaKafelkowGracza1[i]); } }

            zmianaTury();
            zmianaTury();

        }
        else {
            int tmp = Integer.parseInt(etykietaGracz2Pkt.getText());
            tmp += plansza.sprawdz();
            etykietaGracz2Pkt.setText(String.valueOf(tmp));

            for(int i=0;i<7;i++){
                if(tablicaKafelkowGracza2[i].rodzaj==0) {
                    tablicaKafelkowGracza2[i] = new Kafelek(losuj());
                    tablicaKafelkowGracza2[i].addMouseListener(plansza);
                    panel3.add(tablicaKafelkowGracza2[i]); } }

            zmianaTury();
            zmianaTury();

        }


        zmianaTury();
    }

    static void zmianaTury(){
        if(turaGracz1 == true){
            turaGracz1 = false;
            turaGracz2 = true;
            for(int i=0; i<7; i++){
                panel3.remove(i);
                panel3.add(tablicaKafelkowGracza2[i],i); } }
        else{
            turaGracz2 = false;
            turaGracz1 = true;
            for(int i=0; i<7; i++) {
                panel3.remove(i);
                panel3.add(tablicaKafelkowGracza1[i],i); } }
        panel3.getParent().validate();
        panel3.getParent().repaint();
    }

    void iloscPozostalychLiter() {
        etykietaPozostaleLiter.setText("Pozostałe literki: " + String.valueOf(bazaBostepnych.length())); }

    String losuj(){     // zwraca wylosowany z znak z bazy wszystkich liter i zmiejsza baze o wylosowany element w przypadku braku elementów zwraca pusty String
        if(bazaBostepnych.length() != 0){
            int ilosc = bazaBostepnych.length();
            Random random = new Random();
            int wylosowany = (int)random.nextInt();
            String tmp1 = "";
            String tmp2 = "";
            if(wylosowany < 0)
                wylosowany = wylosowany * -1;
            wylosowany = wylosowany % ilosc;
            String literka = String.valueOf(bazaBostepnych.charAt(wylosowany));
            for( int i=0; i<wylosowany; i++ )
                tmp1 = tmp1 + String.valueOf(bazaBostepnych.charAt(i));
            for( int i=wylosowany+1; i<bazaBostepnych.length(); i++ )
                tmp2 = tmp2 + String.valueOf(bazaBostepnych.charAt(i));
            bazaBostepnych = tmp1 + tmp2;
            return literka;
        }
        // zwracane w przypadku braku elementów
        else return ""; }


}

















