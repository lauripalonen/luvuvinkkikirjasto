
package lukuvinkkikirjasto.domain;

import java.util.ArrayList;

public abstract class Note {
    
    private String header;
    private ArrayList<String> tags;
    
    public Note(String header) {
        this.header = header;
        this.tags = new ArrayList<>();
    }
    
    public String getHeader() {
        return this.header;
    }
    
    public ArrayList<String> getTags() {
        return this.tags;
    }
    
    public void addTag(String tag) {
        this.tags.add(tag);
    }
}
