
package lukuvinkkikirjasto;

import java.util.ArrayList;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Note;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BookTest {
    
    Note book;
    
    @Before
    public void setUp() {
        this.book = new Book("Header", "url", "Author", "44556677", 0);
    }
    
    @Test
    public void constructorIsWorkingRight() {
        assertEquals(book.getUrl(), "url");
        assertEquals(book.getHeader(), "Header");
        Book b = (Book) book;
        assertEquals("Author", b.getAuthor());
        assertEquals("44556677", b.getIsbn());
    }
    
    @Test
    public void toStringIsWorking() {
        assertEquals("Book: header=Header, url=url, author=Author, isbn=44556677", book.toString());
    }
    
    @Test
    public void tagCanBeAdded() {
        book.addTag("tag");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag");
        assertEquals(tags, book.getTags());
    }
}
