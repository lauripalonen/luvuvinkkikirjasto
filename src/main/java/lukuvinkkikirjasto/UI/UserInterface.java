package lukuvinkkikirjasto.UI;

import java.util.ArrayList;
import java.util.Scanner;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;

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
            System.out.println("Mitä haluat tehdä?");
            System.out.println("[1] - lisää linkki");
            System.out.println("[2] - selaa muistiinpanoja");
            System.out.println("[x] - lopeta");

            String choice = reader.nextLine();
            if (choice.equals("1")) {
                addLink();
            } else if (choice.equals("2")) {
                listingChoiceMenu();
            } else if (choice.equals("x")) {
                break;
            }
        }
    }

    /**
     * User interface for adding a new link to library. Asks about type of
     * "bookmark".
     */
    public void addLink() {
        while (true) {
            System.out.println("Olet lisäämässä uutta linkkiä, valitse linkin tyyppi:");
            System.out.println("[1] - kirja");
            System.out.println("[2] - pelkkä linkki");
            System.out.println("[x] - palaa päävalikkoon");

            String choice = reader.nextLine();
            if (choice.equals("1")) {
                addBook();
            } else if (choice.equals("2")) {
                addUrl();
            } else if (choice.equals("x")) {
                break;
            }
        }
    }

    public void addBook() {
        System.out.println("Syötä linkin osoite:");
        String url = reader.nextLine();
        System.out.println("Syötä kirjan nimi:");
        String header = reader.nextLine();
        System.out.println("Syötä kirjan kirjoittaja:");
        String author = reader.nextLine();
        System.out.println("Syötä kirjan ISBN-tunnus:");
        String isbn = reader.nextLine();
        library.addBook(header, url, author, isbn);
        System.out.println();
    }

    /**
     * MVP implementation for adding links to library, UI.
     */
    public void addUrl() {
        System.out.println("Syötä linkin osoite:");
        String url = reader.nextLine();
        System.out.println("Syötä linkin otsikko:");
        String header = reader.nextLine();
        library.addLink(header, url);
        System.out.println();
    }
    
    /**
     * This menu is for choosing the types of stored notes that the 
     * user wants to view
     * 
     */
    public void listingChoiceMenu() {
        while (true) {
            System.out.println("Mitkä muistiinpanot listataan?");
            System.out.println("[1] - listaa vain linkit");
            System.out.println("[2] - listaa vain kirjat");
            System.out.println("[3] - listaa kaikki muistiinpanot");
            System.out.println("[x] - palaa päävalikkoon");

            String choice = reader.nextLine();
            if (choice.equals("1")) {
                listLinks();
            } else if (choice.equals("2")) {
                listBooks();
            } else if (choice.equals("3")) {
                listAll();
            } else if (choice.equals("x")) {
                break;
            }
        }
    }
    
    /**
     * User interface for listing links only.
     */
    public void listLinks() {
        System.out.println("Linkkisi:");
        ArrayList<Link> links = library.listLinks();
        int counter = 1;
        for (Link link : links) {
            System.out.println(counter + ": " + link);
            counter++;
        }
        System.out.println();
    }
    
    /**
     * User interface for listing books only.
     */
    public void listBooks() {
        System.out.println("Kirjasi:");
        ArrayList<Book> books = library.listBooks();
        int counter = 1;
        for (Book book : books) {
            System.out.println(counter + ": " + book);
            counter++;
        }
        System.out.println();
    }

    /**
     * User interface for listing all notes. Lists all notes regardless of their type,
     * with URL and leaves out empty links.
     */
    public void listAll() {
        System.out.println("Linkkisi:");
        ArrayList<Note> notes = library.listAll();
        int counter = 1;
        for (Note note : notes) {
            System.out.println(counter + ": " + note);
            counter++;
        }
        System.out.println();
    }
}
