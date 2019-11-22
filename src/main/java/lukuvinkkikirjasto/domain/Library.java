package lukuvinkkikirjasto.domain;

import java.util.ArrayList;
import lukuvinkkikirjasto.dao.DatabaseLinkDao;
import lukuvinkkikirjasto.dao.InMemoryLinkDao;
import lukuvinkkikirjasto.dao.LinkDao;

public class Library {

    LinkDao dao;
    
    /**
     * initializes link Library with a database
     * @param fileName
     */
    public Library(String fileName) {
        this.dao = new DatabaseLinkDao(fileName);
    }
    
    /**
     * initializes a mock link Library in memory
     */
    public Library() {
        this.dao = new InMemoryLinkDao();
    }

    public void addLink(String link) {
        dao.addLink(link);
    }
    
    public boolean containsLink(String link) {
        return dao.listLinks().contains(link);
    }
    
    public ArrayList<String> listLinks() {
        return dao.listLinks();
    }

}
