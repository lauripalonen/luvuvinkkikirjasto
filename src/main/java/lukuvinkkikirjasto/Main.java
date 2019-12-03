package lukuvinkkikirjasto;

import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.UI.WebUserInterface;

public class Main {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        //Library library = new Library(); //in memory
        Library library = new Library("links.db"); //in sqlite database
//        UserInterface ui = new UserInterface(library, scanner);
//
//        ui.startLibrary();

        WebUserInterface ui = new WebUserInterface();
        ui.start();
    }
}
