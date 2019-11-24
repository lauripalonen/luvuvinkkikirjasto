package lukuvinkkikirjasto;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import java.util.*;
import static org.junit.Assert.*;
import lukuvinkkikirjasto.domain.Library;
import org.junit.Before;

public class Stepdefs {
    Library library;
    List<String> outputs;
    
    @Before
    public void setup() {
        outputs = new ArrayList<>();
    }
    
    @Given("Library is initialized")
    public void libraryIsInitiaized() {
        library = new Library();
    }
    
    @When("a link {string} is added")
    public void linkIsAdded(String name) {
        library.addLink(name);
    }
    
    @Then("the library should contain a link named {string}")
    public void theLibraryShouldContainLink(String name) {
        assertTrue(library.containsLink(name));
    }
    
    @Then("the library should not contain a link named {string}")
    public void theLibraryShouldNotContainLink(String name) {
        assertFalse(library.containsLink(name));
    }
    
    @When("links are listed")
    public void linksAreListed() {
        outputs = library.listLinks();
    }
    
    @Then("listing contains link {string}")
    public void listingContainsLink(String link) {
        assertTrue(outputs.contains(link));
    }
    
    @Then("listing contains links {string} and {string}")
    public void listingContainsLinksAnd(String first, String second) {
        assertTrue(outputs.contains(first));
        assertTrue(outputs.contains(second));
        
    }
    
    @Then("listing does not contain link {string} that was not added")
    public void listingDoesNotContainLinkThatWasNotAdded(String notAdded) {
        assertFalse(outputs.contains(notAdded));
    }
    
    
}
