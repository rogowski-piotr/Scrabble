import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Test {







    public static void main(String[] args) throws FileNotFoundException {

        String [] noweLitery = {"A", "B", "A", "B"};

        Path path = Paths.get("extras/tabela_punktow.txt/");

        File file = new File(path.toString());
        Scanner in = null;


        int suma = 0;

        for(int i=0; i<noweLitery.length; i++) {

            in = new Scanner(file);

            while (in.hasNext()) {
                String linia = in.nextLine();
                if(String.valueOf(linia.charAt(0)).equals(noweLitery[i])) {
                    suma += Integer.parseInt(String.valueOf(linia.charAt(1)));
                    break; }
            } }

        System.out.println(suma);












    }


}
