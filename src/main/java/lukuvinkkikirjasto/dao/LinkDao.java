package lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import lukuvinkkikirjasto.domain.Tag;

public interface LinkDao {

    public void addLink(String header, String url);
    
    public void addBook(String header, String url, String author, String isbn);
    
    public ArrayList<Link> listLinks();
    
    public ArrayList<Book> listBooks();
    
//    ArrayList<Tag> listTagsByNote();
//    ArrayList<Tag> listNotesByTag();

    
    public void clearDao();
    
    public ArrayList<Note> listAllNotes();
    public void joinTagToNote(Note note, Tag tag);
    public Note getNote(String header, String url);
    public boolean addTag(String header);
    public Tag getTag(String tagHeader);
    
    public void removeNote(String id);

    public ArrayList<Tag> listTags();
    public ArrayList<String> getTagsForNote(int noteId);
    
}
