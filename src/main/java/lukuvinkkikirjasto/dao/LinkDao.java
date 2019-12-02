package lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import lukuvinkkikirjasto.domain.Tag;

public interface LinkDao {

    void addLink(String header, String url);
    
    void addBook(String header, String url, String author, String isbn);
    
    ArrayList<Link> listLinks();
    
    ArrayList<Book> listBooks();
    
//    ArrayList<Tag> listTagsByNote();
//    ArrayList<Tag> listNotesByTag();

    
    void clearDao();
    
    public ArrayList<Note> listAllNotes();
    void joinTagToNote(Note note, Tag tag);
    public Note getNote(String header, String url);
    public void addTag(String header);
    public Tag getTag(String tagHeader);
    
    public void removeNote(String id);

    public ArrayList<Tag> listTags();
    
}
