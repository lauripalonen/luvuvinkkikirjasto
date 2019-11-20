package lukuvinkkikirjasto;

import java.util.Scanner;
import lukuvinkkikirjasto.domain.Library;

public class Main {

    public static void main(String[] args) {
        System.out.println("Lisää linkki: ");
        Library library = new Library(); //in memory
        //Library library = new Library("links.db"); //in sqlite database
        Scanner scanner = new Scanner(System.in);
        library.addLink(scanner.nextLine());

    }

}
