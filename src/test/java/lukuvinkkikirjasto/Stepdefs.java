package lukuvinkkikirjasto;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.util.*;
import lukuvinkkikirjasto.domain.Book;
import static org.junit.Assert.*;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import org.junit.Before;

public class Stepdefs {
    Library library;
    List<Note> outputs;
    
    @Before
    public void setup() {
        outputs = new ArrayList<>();
    }
    
    @Given("Library is initialized")
    public void libraryIsInitiaized() {
        library = new Library();
    }
    
    @When("a link named {string} with url {string} is added")
    public void aLinkNamedWithUrlIsAdded(String name, String url) {
        library.addLink(name, url);
    }
    
    @Then("the library should contain a link named {string} with url {string}")
    public void theLibraryShouldContainLink(String name, String url) {
        Link link = new Link(name, url);
        assertTrue(library.containsNote(link));
    }
    
    @Then("the library should not contain a link named {string} with url {string}")
    public void theLibraryShouldNotContainLink(String name, String url) {
        Link link = new Link(name, url);
        assertFalse(library.containsNote(link));
    }
    
    @When("links are listed")
    public void linksAreListed() {
        outputs = library.listAll();
    }
    
    @Then("listing contains link named {string} with url {string}")
    public void listingContainsLink(String name, String url) {
        Note link = new Link(name, url);
        boolean contained = false;
        for (Note note : outputs) {
            if(link.equals(note)) {
                contained = true;
            }
        }
        assertTrue(contained);
    }
    
    @Then("listing contains links {string} {string} and {string} {string}")
    public void listingContainsLinksAnd(String firstname, String firsturl, String secondname, String secondurl) {
        Link link1 = new Link(firstname, firsturl);
        assertTrue(outputs.contains(link1));
        Link link2 = new Link(secondname, secondurl);
        assertTrue(outputs.contains(link2));   
    }
    
    @Then("listing does not contain link called {string} with url {string} that was not added")
    public void listingDoesNotContainLinkThatWasNotAdded(String notAdded, String missingUrl) {
        Note link =new Link(notAdded, missingUrl);
        boolean contained = false;
        for (Note note : outputs) {
            if(link.equals(note)) {
                contained = true;
            }
        }
        assertFalse(contained);
    }
    
    @When("a book named {string} found on {string} authored by {string} with isbn {string} is added")
    public void aBookNamedFoundOnAuthoredByWithIsbnIsAdded(String header, String url, String author, String isbn) {
        library.addBook(header, url, author, isbn);
    }
    
    @Then("listing contains book {string} with url {string} with author {string} with isbn {string}")
    public void listingContainsBookWithUrlWithAuthorWithIsbn(String string, String string2, String string3, String string4) {
        Book book1 = new Book(string, string2, string3, string4);
        assertTrue(outputs.contains(book1));
    }
    
}
