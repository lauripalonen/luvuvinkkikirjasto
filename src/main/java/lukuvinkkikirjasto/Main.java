package lukuvinkkikirjasto;

import java.sql.SQLException;
import java.util.Scanner;
import lukuvinkkikirjasto.dao.LinkDao;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkDao linkDao = new LinkDao("links.db");
        System.out.println("Lisää linkki:");
        String link = scanner.nextLine();
        linkDao.addLink(link);
    }

}
