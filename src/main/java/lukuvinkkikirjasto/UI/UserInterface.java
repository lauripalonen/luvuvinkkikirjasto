package lukuvinkkikirjasto.UI;

import java.util.ArrayList;
import java.util.Scanner;
import lukuvinkkikirjasto.domain.Library;

/**
 * This class handles text based user interface.
 */
public class UserInterface {

    private final Scanner reader;
    private final Library library;

    public UserInterface(Library library, Scanner reader) {
        this.library = library;
        this.reader = reader;
    }

    /**
     * Just prints title and "starts" main menu.
     */
    public void startLibrary() {
        System.out.println("Tervetuloa lukuvinkkikirjastoosi!");
        mainMenu();
    }

    /**
     * Main menu, lists possible actions and listens user's choices. Method
     * calls depend on user inserts.
     */
    public void mainMenu() {
        while (true) {
            System.out.println("[1] - lisää linkki");
            System.out.println("[2] - selaa linkkejä");
            System.out.println("[x] - lopeta");

            String choice = reader.nextLine();
            if (choice.equals("1")) {
                addLink();
            } else if (choice.equals("2")) {
                // do something...
                listLinks();
            } else if (choice.equals("x")) {
                break;
            }
        }
    }

    /**
     * User interface for adding a new link to library. Simply asks URL of the link.
     */
    public void addLink() {
        System.out.println("Syötä linkin osoite:");
        String link = reader.nextLine();
        library.addLink(link);
        System.out.println();
    }
    
    public void listLinks() {
        System.out.println("Linkkisi:");
        ArrayList<String> links = library.listLinks();
        int counter = 1;
        for(String link : links) {
            System.out.println(counter + ": " + link);
            counter++;
        }
    }
}
