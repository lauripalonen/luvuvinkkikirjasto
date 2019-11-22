package lukuvinkkikirjasto.domain;

import java.util.ArrayList;

public abstract class Note {

    private String header;
    private ArrayList<String> tags;
    private String url;
    private String language;
    
    public Note(String header) {
        this.header = header;
        this.tags = new ArrayList<>();
        this.url = "";
        this.language = "";
    }

    public String getHeader() {
        return this.header;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public String getUrl() {
        return this.url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }        
    
    public void addTag(String tag) {
        this.tags.add(tag);
    }

    /**
     * Sets url for note. Also checks that url's length is not over 256
     * characters to avoid collision with database's configurations.
     *
     * @param url
     * @return false if url is too long, otherwise true
     */
    public boolean addUrl(String url) {
        if (url.length() > 300) {
            System.out.println("URL must not be over 300 characters."); //Tämän voisi injektoida, jos/kun aiomme speksata tietokantaa tarkemmin.
            return false;
        } else {
            this.url = url;
            return true;
        }
    }
}
