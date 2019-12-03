package lukuvinkkikirjasto.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URI;
import java.net.URISyntaxException;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import lukuvinkkikirjasto.domain.Tag;

public class DatabaseLinkDao implements LinkDao {

    private String filePath;

    public DatabaseLinkDao(String fileName) {
        this.filePath = "db/" + fileName;
        createDbFolder();
        createNoteTable();
        createTagTable();
        createTagNoteAssociationTable();
    }

    private void createNoteTable() {

        //HEROKU
        if(System.getenv("DATABASE_URL") != null){
            try {
                Connection connection = getConnection();
                PreparedStatement stmt  = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes ("
                        + "id SERIAL PRIMARY KEY,"
                        + "Header varchar(300) NOT NULL, "
                        + "URL varchar(300) NOT NULL, "
                        + "Author varchar(48), "
                        + "ISBN varchar(48),"
                        + "Type varchar(16));"
                );
                stmt.executeUpdate();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        //LOCAL
        } else {
            try {
                Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes ("
                        + "id integer PRIMARY KEY,"
                        + "Header varchar(300) NOT NULL, "
                        + "URL varchar(300) NOT NULL, "
                        + "Author varchar(48), "
                        + "ISBN varchar(48),"
                        + "Type varchar(16));"
                );


                stmt.executeUpdate();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        }
    }

    private void createTagTable() {
        //HEROKU
        if(System.getenv("DATABASE_URL") != null){
            try {
                Connection connection = getConnection();

                PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tags ("
                        + "id SERIAL PRIMARY KEY,"
                        + "Header varchar(300),"
                        + "UNIQUE(Header))"
                );

                stmt.executeUpdate();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        //LOCAL
        } else {
            try {
                Connection connection = getConnection();

                PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tags ("
                        + "id integer PRIMARY KEY,"
                        + "Header varchar(300),"
                        + "UNIQUE(Header))"
                );

                stmt.executeUpdate();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    private void createTagNoteAssociationTable() {
        //HEROKU
        if(System.getenv("DATABASE_URL") != null){
            try {
                Connection connection = getConnection();

                PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS notes_tags ("
                        + " note_id integer NOT NULL,"
                        + " tag_id SERIAL NOT NULL,"
                        + " FOREIGN KEY(note_id) REFERENCES Notes(id),"
                        + " FOREIGN KEY(tag_id) REFERENCES Tags(id),"
                        + " PRIMARY KEY (note_id, tag_id))"
                );
                stmt.executeUpdate();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        //LOCAL
        try {
            Connection connection = getConnection();

            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS notes_tags ("
                    + " note_id integer NOT NULL,"
                    + " tag_id integer NOT NULL,"
                    + " FOREIGN KEY(note_id) REFERENCES Notes(id),"
                    + " FOREIGN KEY(tag_id) REFERENCES Tags(id),"
                    + " PRIMARY KEY (note_id, tag_id))"
            );
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void createDbFolder() {
        Path folderPath = Paths.get("db");
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (IOException ex) {
                Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void addLink(String header, String url) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Notes (Header, URL, Author, ISBN, Type) "
                    + "VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, header);
            stmt.setString(2, url);
            stmt.setString(3, "");
            stmt.setString(4, "");
            stmt.setString(5, "Link");
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addBook(String header, String url, String author, String isbn) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Notes (Header, URL, Author, ISBN, Type) "
                    + "VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, header);
            stmt.setString(2, url);
            stmt.setString(3, author);
            stmt.setString(4, isbn);
            stmt.setString(5, "Book");
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public ArrayList<Link> listLinks() {
        ArrayList<Link> links = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Notes WHERE Type='Link'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String header = rs.getString("Header");
                String url = rs.getString("URL");
                int id = rs.getInt("id");
                links.add(new Link(header, url, id));
            }
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return links;
    }

    @Override
    public ArrayList<Book> listBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Notes WHERE Type='Book'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String header = rs.getString("Header");
                String url = rs.getString("URL");
                String author = rs.getString("Author");
                String isbn = rs.getString("ISBN");
                int id = rs.getInt("id");
                books.add(new Book(header, url, author, isbn, id));
            }
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return books;
    }

    @Override
    public void clearDao() {
        if(System.getenv("DATABASE_URL") != null) {
            try {
                Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM Notes");
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } else {

            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    private static Connection getConnectionHeroku() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
    
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    private Connection getConnection() {
        String dbUrl = System.getenv("DATABASE_URL");

        if(dbUrl != null){
            try {
                return getConnectionHeroku();
            } catch (URISyntaxException uriEx){
                Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, uriEx);
            } catch (SQLException sqlEx) {
                Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, sqlEx);
                return null;
            }
        }

        try {
            return DriverManager.getConnection("jdbc:sqlite:" + this.filePath);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<Note> listAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Notes");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String header = rs.getString("Header");
                String url = rs.getString("URL");
                String author = rs.getString("Author");
                String isbn = rs.getString("ISBN");
                String type = rs.getString("Type");
                int id = rs.getInt("id");
                if (type.equals("Book")) {
                    notes.add(new Book(header, url, author, isbn, id));
                } else if (type.equals("Link")) {
                    notes.add(new Link(header, url, id));
                }
            }
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return notes;
    }

    @Override
    public Note getNote(String header, String url) {
        Note note = null;
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Notes WHERE Header = ? AND URL = ?");
            stmt.setString(1, header);
            stmt.setString(2, url);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String noteHeader = rs.getString("Header");
                String noteUrl = rs.getString("URL");
                String noteAuthor = rs.getString("Author");
                String noteIsbn = rs.getString("ISBN");
                String type = rs.getString("Type");
                if (type.equals("Link")) {
                    note = new Link(noteHeader, noteUrl, id);
                } else if (type.equals("Book")) {
                    note = new Book(noteHeader, noteUrl, noteAuthor, noteIsbn, id);
                }
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return note;
    }

    @Override
    public void joinTagToNote(Note note, Tag tag) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO notes_tags (note_id, tag_id)"
                    + " VALUES (?, ?)");
            stmt.setInt(1, note.getId());
            stmt.setInt(2, tag.getId());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean addTag(String header) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Tags (Header) "
                    + "VALUES (?)");
            stmt.setString(1, header);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    @Override
    public Tag getTag(String tagHeader) {
        Tag tag = null;
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tags WHERE Header = ?");
            stmt.setString(1, tagHeader);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String header = rs.getString("Header");
                tag = new Tag(header, id);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return tag;
    }
    
    public ArrayList<String> getTagsForNote(int noteId) {
        ArrayList<String> tags = new ArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tags t "
                    + "INNER JOIN notes_tags nt "
                    + "ON t.id = nt.tag_id AND nt.note_id = ?");
            stmt.setInt(1, noteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String header = rs.getString("Header");
                tags.add(header);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return tags;
    }

    @Override
    public void removeNote(String id) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Notes WHERE id = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public ArrayList<Tag> listTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tags");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String header = rs.getString("Header");
                int id = rs.getInt("id");
                tags.add(new Tag(header, id));
            }
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return tags;
    }

}
