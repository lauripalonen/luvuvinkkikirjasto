package lukuvinkkikirjasto.domain;

import lukuvinkkikirjasto.dao.DatabaseLinkDao;
import lukuvinkkikirjasto.dao.InMemoryLinkDao;
import lukuvinkkikirjasto.dao.LinkDao;

public class Library {

    LinkDao dao;
    
    /**
     * initializes link Library with a database
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

}
