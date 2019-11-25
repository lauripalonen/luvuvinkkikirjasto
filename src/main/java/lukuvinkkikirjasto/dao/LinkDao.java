package lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;

public interface LinkDao {

    void addLink(String header, String url);
    
    void addBook(String header, String url, String author, String isbn);
    
    ArrayList<Link> listLinks();

    void initializeDao();
    
    void clearDao();
    
    public ArrayList<Note> listAll();
    
}
