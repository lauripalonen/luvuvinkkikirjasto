package lukuvinkkikirjasto.domain;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Note implements Comparable {

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final Note other = (Note) obj;
        if (!Objects.equals(this.header, other.header)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || o == this) {
            return 0;
        }
        Note otherNote = (Note) o;
        return this.header.compareToIgnoreCase(otherNote.header);
    }

}
