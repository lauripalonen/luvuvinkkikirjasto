/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkikirjasto;

import java.util.Arrays;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Link;
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
    
}
