
package lukuvinkkikirjasto;

import lukuvinkkikirjasto.domain.Note;
import lukuvinkkikirjasto.domain.Video;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class VideoTest {
    
    Note video;
        
    @Before
    public void setUp() {
        this.video = new Video("Header", "url");
    }
    
    @Test
    public void constructorIsWorkingRight() {
        assertEquals("Header", video.getHeader());
        assertEquals("url", video.getUrl());
    }
}
