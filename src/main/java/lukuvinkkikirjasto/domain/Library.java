package lukuvinkkikirjasto.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import lukuvinkkikirjasto.dao.DatabaseLinkDao;
import lukuvinkkikirjasto.dao.InMemoryLinkDao;
import lukuvinkkikirjasto.dao.LinkDao;

public class Library {

    LinkDao dao;

    /**
     * initializes link Library with a database
     *
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
    
    public void joinTagToNote(String header, String url, String tagHeader){
        dao.joinTagToNote(getNote(header, url), dao.getTag(tagHeader));
    }
    
    public Set<String> getTags(){
        return dao.getTagsSet();
    }
    
    public Note getNote(String header, String url){
        return dao.getNote(header, url);
    }
    
    public void createTag(String header){
        dao.createTag(header);
    }

    public void addBook(String header, String url, String author, String isbn) {
        dao.addBook(header, url, author, isbn);
    }
    
    public void addLink(String header, String url) {
        dao.addLink(header, url);
    }

    public boolean containsNote(Note note) {
        for (Note notes : dao.listAll()) {
            if (note.equals(notes)) {
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

    public void deleteAllRecords() {
        dao.clearDao();
    }

}
