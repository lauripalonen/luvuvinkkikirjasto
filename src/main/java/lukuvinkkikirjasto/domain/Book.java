
package lukuvinkkikirjasto.domain;

public class Book extends Note {
    
    private String author;
    private String isbn;
    
    public Book(String header, String url, String author, String isbn, int id, String info) {
        super(header, url, id, info);
        this.author = author;
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }   
    
    public String getAuthor() {
        return this.author;
    }
    
    public String getIsbn() {
        return this.isbn;
    }

    @Override
    public String toString() {
        return "Book: "+ super.toString() + ", author=" + author + ", isbn=" + isbn;
    }
    
}
