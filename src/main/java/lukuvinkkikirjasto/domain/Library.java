package lukuvinkkikirjasto.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    
    public void modifyNote(Note old, Note updated) {
        dao.modifyNote(old, updated);
    }
    
    public void addTag(String header) {
        List<String> existingTags = listTags().stream().map(tag -> tag.getHeader()).collect(Collectors.toList());
        if (!existingTags.contains(header)) {
            dao.addTag(header);
        }
    }
    
    public Tag getTag(String header) {
        return dao.getTag(header);
    }

    public void addBook(String header, String url, String author, String isbn, String info) {
        Book b = new Book(header, url, author, isbn, 1, info);
        if(!containsNote(b)) {
            dao.addBook(header, url, author, isbn, info);
        }
    }
    
    public void addLink(String header, String url, String info) {
        Link l = new Link(header, url, 1, info);
        if(!containsNote(l)) {
            dao.addLink(header, url, info);
        }
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
    
    public ArrayList<String> getTagsForNote(int noteId) {
        return dao.getTagsForNote(noteId);
    }

    public void deleteAllRecords() {
        dao.clearDao();
    }

}
