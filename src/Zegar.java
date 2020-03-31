/**
 * Obiekt <code>Zegar</code>
 * @author Piotr Rogowski
 * @version 1.0 29/01/2020
 */

public class Zegar extends Thread {

    int czasGracza1 = 600;
    int czasGracza2 = 600;

    public void run(){

        while(czasGracza1 > 0 || czasGracza2 > 0) {

            //System.out.println("tura gracza1: " + String.valueOf(ScrabbleGUI.turaGracz1) + ", tura gracza2: " + String.valueOf(ScrabbleGUI.turaGracz2));

            try { sleep(1000); }                                     // odczekanie jednej sekundy
            catch (InterruptedException e) { e.printStackTrace(); }


            if(czasGracza1 == 0 && ScrabbleGUI.turaGracz1==true)         //zmiena tury w przypadku gdy jednemuz graczy skonczy sie czas
                ScrabbleGUI.zmianaTury();
            if(czasGracza2 == 0 && ScrabbleGUI.turaGracz2==true)
                ScrabbleGUI.zmianaTury();


            if(ScrabbleGUI.turaGracz1 == true) czasGracza1--;  //dekrementacja czasu gracza którego jest akualna tura
            else czasGracza2--;


            if(ScrabbleGUI.turaGracz1 == true){                //wyswietlanie czas gracza ktorego tura jest akualnie
                ScrabbleGUI.etykietaGracz1Czas.setText( String.valueOf(czasGracza1/60) + ":" + String.valueOf(czasGracza1%60) );
                ScrabbleGUI.etykietaGracz2Czas.setText( String.valueOf(czasGracza2/60) + ":" + String.valueOf(czasGracza2%60) ); }
            else{
                ScrabbleGUI.etykietaGracz1Czas.setText( String.valueOf(czasGracza1/60) + ":" + String.valueOf(czasGracza1%60) );
                ScrabbleGUI.etykietaGracz2Czas.setText( String.valueOf(czasGracza2/60) + ":" + String.valueOf(czasGracza2%60) ); }
        }

        System.out.println("czasG1 po wyjsciu z petli: " + czasGracza1);
        System.out.println("czasG2 po wyjsciu z petli: " + czasGracza2);

        ScrabbleGUI.etykietaGracz1Czas.setText( "time out" );
        ScrabbleGUI.etykietaGracz2Czas.setText( "time out" );
    }

}
