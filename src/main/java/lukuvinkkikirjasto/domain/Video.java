package lukuvinkkikirjasto.domain;

/**
 * Subclass for Note. Handles Video-typed notes.
 */
public class Video extends Note {        
    
    public Video(String header, String url) {
        super(header, url);
    }
    
}