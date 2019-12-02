package lukuvinkkikirjasto;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import java.util.*;
import lukuvinkkikirjasto.domain.Book;
import static org.junit.Assert.*;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Stepdefs {

    //WebDriver driver = new FirefoxDriver();
    WebDriver driver = new HtmlUnitDriver();
    String baseUrl = "http://localhost:3001";
    Library library;
    List<Note> outputs;

    @Before
    public void setup() {
        outputs = new ArrayList<>();
    }

    @Given("Library is initialized")
    public void libraryIsInitialized() {
        library = new Library("testdb.db");

    }

    @And("lisää uusi muistiinpano is selected")
    public void addNewNoteThroughWebUi() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Lisää uusi muistiinpano"));
        element.click();
    }

    @When("a link named {string} with url {string} is added through web UI")
    public void aLinkNamedWithUrlIsAddedThroughWebUi(String name, String url) {
        addLink(name, url);
    }

    @Then("main menu should list item {string}")
    public void mainMenuListsItem(String name) {
        pageHasContent(name);
    }

    @When("a link named {string} with url {string} is added")
    public void aLinkNamedWithUrlIsAdded(String name, String url) {
        library.addLink(name, url);
    }

    @Then("the library should contain a link named {string} with url {string}")
    public void theLibraryShouldContainLink(String name, String url) {
        Link link = new Link(name, url, 0);
        assertTrue(library.containsNote(link));
    }

    @Then("the library should not contain a link named {string} with url {string}")
    public void theLibraryShouldNotContainLink(String name, String url) {
        Link link = new Link(name, url, 0);
        assertFalse(library.listAll().contains((link)));
    }

    @When("links are listed")
    public void linksAreListed() {
        outputs = library.listAll();
    }

    @Then("listing contains link named {string} with url {string}")
    public void listingContainsLink(String name, String url) {
        Note link = new Link(name, url, 0);
        boolean contained = false;
        for (Note note : outputs) {
            if (link.equals(note)) {
                contained = true;
            }
        }
        assertTrue(contained);
    }

    @Then("listing contains links {string} {string} and {string} {string}")
    public void listingContainsLinksAnd(String firstname, String firsturl, String secondname, String secondurl) {
        Link link1 = new Link(firstname, firsturl, 0);
        assertTrue(outputs.contains(link1));
        Link link2 = new Link(secondname, secondurl, 1);
        assertTrue(outputs.contains(link2));
    }

    @Then("listing does not contain link called {string} with url {string} that was not added")
    public void listingDoesNotContainLinkThatWasNotAdded(String notAdded, String missingUrl) {
        Note link = new Link(notAdded, missingUrl, 1);
        boolean contained = false;
        for (Note note : outputs) {
            if (link.equals(note)) {
                System.out.println("This is the note:");
                System.out.println(note);
                System.out.println("This is the link:");
                System.out.println(link);
                contained = true;
            }
        }
        assertFalse(contained);
    }

    @When("a book named {string} found on {string} authored by {string} with isbn {string} is added")
    public void aBookNamedFoundOnAuthoredByWithIsbnIsAdded(String header, String url,
            String author, String isbn
    ) {
        library.addBook(header, url, author, isbn);
    }

    @Then("listing contains book {string} with url {string} with author {string} with isbn {string}")
    public void listingContainsBookWithUrlWithAuthorWithIsbn(String string, String string2,
            String string3, String string4
    ) {
        Book book1 = new Book(string, string2, string3, string4, 0);
        assertTrue(outputs.contains(book1));
    }

    @After
    public void after() {
        library.deleteAllRecords();
        driver.quit();
    }

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void addLink(String header, String url) {
        pageHasContent("Uuden muistiinpanon lisääminen");
        WebElement element = driver.findElement(By.name("header"));
        element.sendKeys(header);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
        element = driver.findElement(By.name("add"));
        element.submit();
    }
}
