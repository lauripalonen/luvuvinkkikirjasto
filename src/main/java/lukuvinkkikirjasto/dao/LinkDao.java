package lukuvinkkikirjasto.dao;

import java.util.ArrayList;

public interface LinkDao {

    void addLink(String url);
    
    void addBook(String header, String url, String author, String isbn);
    
    ArrayList<String> listLinks();

    void initializeDao();
    
    void clearDao();
}
