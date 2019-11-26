package lukuvinkkikirjasto;

import lukuvinkkikirjasto.domain.Library;
import org.junit.After;
import org.junit.Before;

public class LibraryTestDB extends LibraryTest {
    
    @Before
    @Override
    public void setUp() {
        library = new Library("testdb.db");
    }
    
    @After
    public void after() {
        library.deleteAllRecords();
    }
    
}
