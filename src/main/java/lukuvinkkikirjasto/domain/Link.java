
package lukuvinkkikirjasto.domain;

/**
 * First implemention of Note's subclass.
 */
public class Link extends Note {
    
    private String link;
    
    public Link(String header, String link) {
        super(header);
        this.link = link;
    }
    
    public String getLink() {
        return this.link;
    }
}
