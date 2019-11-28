package lukuvinkkikirjasto.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import lukuvinkkikirjasto.domain.Tag;

public class InMemoryLinkDao implements LinkDao {

    private ArrayList<Link> linkArrayList;
    private ArrayList<Book> bookArrayList;
    private HashMap<String, Tag> tagMap;
    private HashMap<Note, ArrayList<Tag>> tagListMap;
    private HashMap<Tag, ArrayList<Note>> noteListMap;
    

    public InMemoryLinkDao() {
        initializeDao();
    }
    
    @Override
    public void initializeDao() {
        linkArrayList = new ArrayList<>();
        bookArrayList = new ArrayList<>();
        tagMap = new HashMap<>();
        tagListMap = new HashMap<>();
        noteListMap = new HashMap<>();
    }
    
    @Override
    public void joinTagToNote(Note note, Tag tag) {
        tagListMap.putIfAbsent(note, new ArrayList<Tag>());
        tagListMap.get(note).add(tag);
        noteListMap.putIfAbsent(tag, new ArrayList<Note>());
        noteListMap.get(tag).add(note);
    }

    @Override
    public Note getNote(String header, String url) {
        ArrayList<Note> all = listAll();
        for(Note note : all) {
            if (note.getHeader().equals(header) && note.getUrl().equals(url)) {
                return note;
            }
        }
        
        return null;
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
    public ArrayList<Book> listBooks() {
        return bookArrayList;
    }

    @Override
    public ArrayList<Note> listAll() {

        ArrayList<Note> noteArrayList = new ArrayList<>();
        noteArrayList.addAll(linkArrayList);
        noteArrayList.addAll(bookArrayList);
        return noteArrayList;
    }


    @Override
    public void clearDao() {
        linkArrayList = new ArrayList<>();
    }

    @Override
    public void addBook(String header, String url, String author, String isbn) {
        bookArrayList.add(new Book(header, url, author, isbn));
    }

    @Override
    public Set<String> getTagsSet() {
        return tagMap.keySet();
    }

    @Override
    public Tag getTag(String tagHeader) {
       return tagMap.get(tagHeader);
    }

    @Override
    public void createTag(String header) {
        Tag newTag = new Tag(header);
        tagMap.putIfAbsent(header, newTag);
    }
    

}
