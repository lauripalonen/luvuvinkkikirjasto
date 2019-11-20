/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkikirjasto;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

import lukuvinkkikirjasto.domain.Library;

public class Stepdefs {
    Library library;
    
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
}
