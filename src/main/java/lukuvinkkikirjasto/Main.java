package lukuvinkkikirjasto;

import java.util.Scanner;
import lukuvinkkikirjasto.UI.UserInterface;
import lukuvinkkikirjasto.domain.Library;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Library library = new Library(); //in memory
        Library library = new Library("links.db"); //in sqlite database
        UserInterface ui = new UserInterface(library, scanner);

        ui.startLibrary();
    }

}
