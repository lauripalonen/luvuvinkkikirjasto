package lukuvinkkikirjasto.domain;

public class Tag {

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
    

}
