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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

//    @When("a book named {string} found on {string} authored by {string} with isbn {string} is added through web UI")
//    public void aBookNamedFoundOnAuthoredByWithIsbnIsAddedThroughWebUi(String name, String url, String author, String isbn) {
//        addBookThroughWebUi(name, url, author, isbn);
//    }

    @Then("list books menu should list item {string}")
    public void booksMenuListsItem(String name) {
        browseBooks();
        pageHasContent(name);
    }

    @Then("list all menu should list item {string}")
    public void mainMenuListsItem(String name) {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Tarkastele muistiinpanojasi"));
        element.click();
        pageHasContent(name);
    }

    @Then("list books menu should not list item {string}")
    public void listBooksMenuShouldNotListItem(String name) {
        pageHasNoContent(name);
    }

    @When("a link named {string} with url {string} is added")
    public void aLinkNamedWithUrlIsAdded(String name, String url) {
        library.addLink(name, url, "");
    }

    @Then("the library should contain a link named {string} with url {string}")
    public void theLibraryShouldContainLink(String name, String url) {
        Link link = new Link(name, url, 0, "");
        assertTrue(library.containsNote(link));
    }

    @Then("the library should not contain a link named {string} with url {string}")
    public void theLibraryShouldNotContainLink(String name, String url) {
        Link link = new Link(name, url, 0, "");
        assertFalse(library.listAll().contains((link)));
    }

    @When("links are listed")
    public void linksAreListed() {
        outputs = library.listAll();
    }

    @Then("listing contains link named {string} with url {string}")
    public void listingContainsLink(String name, String url) {
        Note link = new Link(name, url, 0, "");
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
        Link link1 = new Link(firstname, firsturl, 0, "");
        assertTrue(outputs.contains(link1));
        Link link2 = new Link(secondname, secondurl, 1, "");
        assertTrue(outputs.contains(link2));
    }

    @Then("listing does not contain link called {string} with url {string} that was not added")
    public void listingDoesNotContainLinkThatWasNotAdded(String notAdded, String missingUrl) {
        Note link = new Link(notAdded, missingUrl, 1, "");
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
        library.addBook(header, url, author, isbn, "");
    }

    @Then("listing contains book {string} with url {string} with author {string} with isbn {string}")
    public void listingContainsBookWithUrlWithAuthorWithIsbn(String header, String url, String author, String isbn) {
        Book book1 = new Book(header, url, author, isbn, 0, "");
        assertTrue(outputs.contains(book1));
    }
    
    @When("a note named {string} with url {string} and info {string} and tag {string} is added through web UI")
    public void noteWithTagIsadded(String header, String url, String info, String tag) {
        addNote(header, url, info, tag);
    }
    
    @Then("list all menu should list item {string} with url {string} and info {string} and tag {string}")
    public void listingContainsNoteWithTag(String header, String url, String info, String tag) {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Tarkastele muistiinpanojasi"));
        element.click();
        pageHasContent(header + " - " + url + " - Lisätiedot: " + info + " - Tagit: " + tag);
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
    
    private void addNote(String header, String url, String info, String tag) {
        pageHasContent("Uuden muistiinpanon lisääminen");
        WebElement element = driver.findElement(By.name("header"));
        element.sendKeys(header);
        element = driver.findElement(By.name("url"));
        element.sendKeys(url);
//        driver.findElement(By.name("info")).click();
        element = driver.findElement(By.name("info"));
        element.sendKeys(info);
        element = driver.findElement(By.name("tags"));
        element.sendKeys(tag);
        element = driver.findElement(By.name("add"));
        element.submit();
    }

//    private void addBookThroughWebUi(String name, String url, String author, String isbn) {
//        pageHasContent("Uuden muistiinpanon lisääminen");
//        WebElement element = driver.findElement(By.name("noteType"));
//        Select noteType = new Select(element);
//        noteType.selectByValue("2");
//        
//        element = driver.findElement(By.name("header"));
//        element.sendKeys(name);
//        element = driver.findElement(By.name("url"));
//        element.sendKeys(url);
//        element = driver.findElement(By.name("author"));
//        element.sendKeys(author);
//        element = driver.findElement(By.name("isbn"));
//        element.sendKeys(isbn);
//        element = driver.findElement(By.name("add"));
//        element.submit();
//    }

    private void browseBooks() {
        driver.get(baseUrl);
        WebElement element = driver.findElement(By.linkText("Tarkastele muistiinpanojasi"));
        element.click();
        element = driver.findElement(By.name("listType"));
        Select listType = new Select(element);
        listType.selectByValue("3");
    }

    private void pageHasNoContent(String name) {
        assertFalse(driver.getPageSource().contains(name));
    }
}
