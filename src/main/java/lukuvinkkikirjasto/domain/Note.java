package lukuvinkkikirjasto.domain;

import java.util.ArrayList;

public abstract class Note {

    private String header;
    private ArrayList<String> tags;
    private String url;
    //private String language;

    public Note(String header, String url) {
        this.header = header;
        this.tags = new ArrayList<>();
        this.url = url;
        //this.language = "";
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "header=" + header + ", url=" + url;
    }

}
