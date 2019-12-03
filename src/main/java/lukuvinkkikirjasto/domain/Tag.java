package lukuvinkkikirjasto.domain;

public class Tag implements Comparable {

    private String header;
    private int id;

    public Tag(String header, int id) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getId() {
        return id;
    }
    
    @Override 
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Tag)) {
            return false;
        }
        Tag t = (Tag) o;
        return this.header.equals(t.getHeader());
    }
    
    @Override
    public int compareTo(Object o) {
        if (o == null || o == this) {
            return 0;
        }
        Tag t = (Tag) o;
        return this.header.compareToIgnoreCase(t.getHeader());
    }
}
