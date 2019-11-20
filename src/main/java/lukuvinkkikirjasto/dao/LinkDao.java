package lukuvinkkikirjasto.dao;

import java.util.ArrayList;

public interface LinkDao {

    void addLink(String link);
    
    ArrayList<String> listLinks();

    void initializeDao();
    
    void clearDao();
}
