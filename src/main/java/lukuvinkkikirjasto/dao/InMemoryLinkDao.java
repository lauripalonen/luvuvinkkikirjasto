package lukuvinkkikirjasto.dao;

import java.util.ArrayList;

public class InMemoryLinkDao implements LinkDao {

    private ArrayList<String> linkArrayList;

    public InMemoryLinkDao() {
        initializeDao();
    }

    @Override
    public void addLink(String link) {
        linkArrayList.add(link);
    }

    @Override
    public void initializeDao() {
        linkArrayList = new ArrayList<>();
    }
    

}
