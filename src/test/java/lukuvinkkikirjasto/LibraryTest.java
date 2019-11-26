/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkikirjasto;

import java.util.ArrayList;
import java.util.Arrays;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {
    Library library;
    
    @Before
    public void setUp() {
        library = new Library();
    }
    
    @Test
    public void bookCanBeAddedToLibrary() {
        Book book = new Book("Book header", "dummyurl", "Dummy Author", "9781234567897");
        library.addBook("Book header", "dummyurl", "Dummy Author", "9781234567897");
        assertTrue(library.containsNote(book));
    }
    
    @Test
    public void linkCanBeAddedToLibrary() {
        Link link = new Link("Dummy link", "dummyurl.zxc");
        library.addLink("Dummy link", "dummyurl.zxc");
        assertTrue(library.containsNote(link));
    }
    
    public void notAddedNoteIsNotFoundInLibrary() {
        Note n = new Book("Header", "url", "Author", "1234567");
        assertFalse(library.containsNote(n));
    }
    
    @Test
    public void onlyLinksListed() {
        Book b = new Book("Header", "url", "Author", "1234567");
        Link l = new Link("Link", "link.fi");
        library.addBook("Header", "url", "Author", "1234567");
        library.addLink("Link", "link.fi");
        ArrayList<Link> links = new ArrayList<>();
        links.add(l);
        assertEquals(links, library.listLinks());
    }
    
    @Test
    public void onlyBooksListed() {
        Book b = new Book("Header", "url", "Author", "1234567");
        Link l = new Link("Link", "link.fi");
        library.addBook("Header", "url", "Author", "1234567");
        library.addLink("Link", "link.fi");
        ArrayList<Book> books = new ArrayList<>();
        books.add(b);
        assertEquals(books, library.listBooks());
    }
    
    @Test
    public void allListed() {
        Book b = new Book("Header", "url", "Author", "1234567");
        Link l = new Link("Link", "link.fi");
        library.addBook("Header", "url", "Author", "1234567");
        library.addLink("Link", "link.fi");
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(l);
        notes.add(b);
        assertEquals(notes, library.listAll());
    }
}
