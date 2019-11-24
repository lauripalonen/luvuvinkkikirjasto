package lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import lukuvinkkikirjasto.domain.Book;

public class InMemoryLinkDao implements LinkDao {

    private ArrayList<String> linkArrayList;
    private ArrayList<Book> bookArrayList;
    
    public InMemoryLinkDao() {
        initializeDao();
    }

    @Override
    public void addLink(String link) {
        linkArrayList.add(link);
    }
    
    @Override
    public ArrayList<String> listLinks() {
        return linkArrayList;
    }

    @Override
    public void initializeDao() {
        linkArrayList = new ArrayList<>();
        bookArrayList = new ArrayList<>();
    }

    @Override
    public void clearDao() {
        linkArrayList = new ArrayList<>();
    }

    @Override
    public void addBook(String header, String url, String author, String isbn) {
        bookArrayList.add(new Book(header, url, author, isbn));
        linkArrayList.add(url);
    }
    

}
