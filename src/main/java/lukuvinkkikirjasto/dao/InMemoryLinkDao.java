package lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;

public class InMemoryLinkDao implements LinkDao {

    private ArrayList<Link> linkArrayList;
    private ArrayList<Book> bookArrayList;

    public InMemoryLinkDao() {
        initializeDao();
    }

    @Override
    public void addLink(String header, String url) {
        linkArrayList.add(new Link(header, url));
    }

    @Override
    public ArrayList<Link> listLinks() {
        return linkArrayList;
    }

    @Override
    public ArrayList<Note> listAll() {

        ArrayList<Note> noteArrayList = new ArrayList<>();
        noteArrayList.addAll(linkArrayList);
        noteArrayList.addAll(bookArrayList);
        return noteArrayList;
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
    }

}
