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

    public void addBook(String header, String url, String author, String isbn) {
        dao.addBook(header, url, author, isbn);
    }
    
    public void addLink(String header, String url) {
        dao.addLink(header, url);
    }
    
    public boolean containsLink(Link link) {
        for (Link listed : dao.listLinks()) {
            if (link.equals(listed)) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Link> listLinks() {
        return dao.listLinks();
    }
    
    public ArrayList<Book> listBooks() {
        return dao.listBooks();
    }
    
    public ArrayList<Note> listAll() {
        return dao.listAll();
    }    

}
