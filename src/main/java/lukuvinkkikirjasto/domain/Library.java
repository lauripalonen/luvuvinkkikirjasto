package lukuvinkkikirjasto.domain;

import java.util.ArrayList;
import lukuvinkkikirjasto.dao.DatabaseLinkDao;
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
    
    public void joinTagToNote(Note note, Tag tag){
        dao.joinTagToNote(note, tag);
    }
    
    public Note getNote(String header, String url){
        return dao.getNote(header, url);
    }
    
    public void removeNote(String id) {
        dao.removeNote(id);
    }
    
    public void addTag(String header){
        dao.addTag(header);
    }

    public void addBook(String header, String url, String author, String isbn) {
        dao.addBook(header, url, author, isbn);
    }
    
    public void addLink(String header, String url) {
        dao.addLink(header, url);
    }

    public boolean containsNote(Note note) {
        for (Note notes : dao.listAllNotes()) {
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
        return dao.listAllNotes();
    }
    
    public ArrayList<Tag> listTags(){
        return dao.listTags();
    }

    public void deleteAllRecords() {
        dao.clearDao();
    }

}
