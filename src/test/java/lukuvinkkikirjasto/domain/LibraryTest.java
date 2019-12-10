/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkikirjasto.domain;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    Library library;

    @Before
    public void setUp() {
        library = new Library("testdb.db");
    }

    @Test
    public void bookCanBeAddedToLibrary() {
        Book book = new Book("Book header", "dummyurl", "Dummy Author", "9781234567897", 1, "info book");
        library.addBook("Book header", "dummyurl", "Dummy Author", "9781234567897", "info book");
        assertTrue(library.containsNote(book));
    }

    @Test
    public void linkCanBeAddedToLibrary() {
        Link link = new Link("Dummy link", "dummyurl.zxc", 1, "info link");
        library.addLink("Dummy link", "dummyurl.zxc", "info link");
        assertTrue(library.containsNote(link));
    }

    public void notAddedNoteIsNotFoundInLibrary() {
        Note n = new Book("Header", "url", "Author", "1234567", 1, "info book");
        assertFalse(library.containsNote(n));
    }
    
    @Test
    public void sameNoteCannotBeAddedTwice() {
        Book b = new Book("Header", "url", "Author", "1234567", 1, "info book");
        library.addBook("Header", "url", "Author", "1234567", "info book");
        library.addBook("Header", "url", "Author", "1234567", "info book");
        int counter = 0;
        for(Book book : library.listBooks()) {
            if(book.equals(b)) {
                counter++;
            }
        }
        assertEquals(counter, 1);
    }

    @Test
    public void onlyLinksListed() {
        Book b = new Book("Header", "url", "Author", "1234567", 1, "info book");
        Link l = new Link("Link", "link.fi", 2, "info link");
        library.addBook("Header", "url", "Author", "1234567", "info book");
        library.addLink("Link", "link.fi", "info link");
        ArrayList<Link> links = new ArrayList<>();
        ArrayList<Link> linksFromLibrary = library.listLinks();
        links.add(l);
        Collections.sort(links);
        Collections.sort(linksFromLibrary);
        assertEquals(links, linksFromLibrary);
    }

    @Test
    public void onlyBooksListed() {
        Book b = new Book("Header", "url", "Author", "1234567", 1, "info book");
        Link l = new Link("Link", "link.fi", 2, "info link");
        library.addBook("Header", "url", "Author", "1234567", "info book");
        library.addLink("Link", "link.fi", "info link");
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> booksFromLibrary = library.listBooks();
        books.add(b);
        Collections.sort(books);
        Collections.sort(booksFromLibrary);
        assertEquals(books, booksFromLibrary);
    }

    @Test
    public void allListed() {
        Book b = new Book("Header", "url", "Author", "1234567", 1, "info book");
        Link l = new Link("Link", "link.fi", 2, "info link");
        library.addBook("Header", "url", "Author", "1234567", "info book");
        library.addLink("Link", "link.fi", "info link");
        ArrayList<Note> notes = new ArrayList<>();
        ArrayList<Note> notesFromLibrary = library.listAll();
        notes.add(l);
        notes.add(b);
        Collections.sort(notes);
        Collections.sort(notesFromLibrary);
        assertEquals(notes, notesFromLibrary);
    }
    
    @Test 
    public void noteCanBeRemoved() {
        Book b = new Book("Header", "url", "Author", "1234567", 1, "info book");
        library.addBook("Header", "url", "Author", "1234567", "info book");
        library.removeNote(1);
        ArrayList<Note> notes = new ArrayList<>();
        ArrayList<Note> notesFromLibrary = library.listAll();
        assertEquals(notes, notesFromLibrary);
    }
    
    @Test
    public void rightNoteIsRemoved() {
        Book b = new Book("Header", "url", "Author", "1234567", 1, "info book");
        Link l = new Link("Link", "link.fi", 2, "info link");
        library.addBook("Header", "url", "Author", "1234567", "info book");
        library.addLink("Link", "link.fi", "info link");
        library.removeNote(2);
        ArrayList<Note> notes = new ArrayList<>();
        ArrayList<Note> notesFromLibrary = library.listAll();
        notes.add(b);
        assertEquals(notes, notesFromLibrary);
    }

    @Test
    public void tagsAreListed() {
        Tag t = new Tag("header", 1);
        library.addTag("header");
        ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<Tag> tagsFromLibrary = library.listTags();
        tags.add(t);
        assertEquals(tags, tagsFromLibrary);
    }
    
    @Test
    public void bookCanBeModified() {
        Note b1 = new Book("Book", "url.com", "Author", "12345", 1, "great info"); 
        Note b2 = new Book("A Farewell To Arms", "google.fi", "Ernest Hemingway", "123456", 1, "info");
        library.addBook("Book", "url.com", "Author", "12345", "great info");
        library.modifyNote(b1, b2);
        assertTrue(library.containsNote(b2));
        assertFalse(library.containsNote(b1));
    }
    
    
    @After
    public void after() {
        library.deleteAllRecords();
    }
}
