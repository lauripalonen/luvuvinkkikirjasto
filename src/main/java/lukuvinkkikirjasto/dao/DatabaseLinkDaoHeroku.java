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

public class DatabaseLinkDaoHeroku implements LinkDao {

    //private String filePath;

    public DatabaseLinkDaoHeroku() {
        //this.filePath = "db/" + fileName;
        //createDbFolder();
        createNoteTable();
        createTagTable();
        createTagNoteAssociationTable();
    }

    private void createNoteTable() {

            try {
                Connection connection = getConnection();
                PreparedStatement stmt  = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes ("
                        + "id SERIAL PRIMARY KEY,"
                        + "Header varchar(300) NOT NULL, "
                        + "URL varchar(300) NOT NULL, "
                        + "Author varchar(48), "
                        + "ISBN varchar(48),"
                        + "Type varchar(16),"
                        + "Info varchar(600));"
                );
                stmt.executeUpdate();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
    }

    private void createTagTable() {

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

    }

    private void createTagNoteAssociationTable() {

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

  /*  private void createDbFolder() {
        Path folderPath = Paths.get("db");
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (IOException ex) {
                Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }*/

    @Override
    public void addLink(String header, String url, String info) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Notes (Header, URL, Author, ISBN, Type, Info) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, header);
            stmt.setString(2, url);
            stmt.setString(3, "");
            stmt.setString(4, "");
            stmt.setString(5, "Link");
            stmt.setString(6, info);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addBook(String header, String url, String author, String isbn, String info) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Notes (Header, URL, Author, ISBN, Type, Info) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, header);
            stmt.setString(2, url);
            stmt.setString(3, author);
            stmt.setString(4, isbn);
            stmt.setString(5, "Book");
            stmt.setString(6, info);
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
                String info = rs.getString("Info");
                links.add(new Link(header, url, id, info));
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
                String info = rs.getString("Info");
                books.add(new Book(header, url, author, isbn, id, info));
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
    public void modifyNote(Note oldNote, Note updatedNote) {
        int id = oldNote.getId();
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT Type FROM Notes WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            String type = rs.getString("Type");
            stmt.close();
            rs.close();
            if (type.equals("Book")) {
                    //update book
                Book book = (Book)updatedNote;
                PreparedStatement stmtBook = connection.prepareStatement("UPDATE Notes SET (Header, URL, Author, ISBN, Type, Info) "
                    + "WHERE id = ? "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                stmtBook.setString(1, book.getHeader());
                stmtBook.setString(2, book.getUrl());
                stmtBook.setString(3, book.getAuthor());
                stmtBook.setString(4, book.getIsbn());
                stmtBook.setString(5, "Book");
                stmtBook.setString(6, book.getInfo());
                stmtBook.setInt(7, id);
                stmtBook.executeUpdate();
                stmtBook.close();
                    // update notes_tags table??
            } else if (type.equals("Link")) {
                    //update link
                Link link = (Link)updatedNote;
                PreparedStatement stmtLink = connection.prepareStatement("Update Notes SET (Header, URL, Type, Info) WHERE id = ?"
                    + "VALUES (?, ?, ?, ?, ?)");
                stmtLink.setString(1, link.getHeader());
                stmtLink.setString(2, link.getUrl());
                stmtLink.setString(3, "Link");
                stmtLink.setString(4, link.getInfo());
                stmtLink.setInt(5, id);
                stmtLink.executeUpdate();
                stmtLink.close();
                    // update notes_tags table??
            }
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }

    @Override
    public void clearDao() {
            try {
                Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM Notes");
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        /*} else {

            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }*/
    }

    private Connection getConnection() {

        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

            return DriverManager.getConnection(dbUrl, username, password);
        } catch (URISyntaxException uriEx){
            Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, uriEx);
            return null;
        } catch (SQLException sqlEx) {
            Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, sqlEx);
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
                String info = rs.getString("Info");
                if (type.equals("Book")) {
                    notes.add(new Book(header, url, author, isbn, id, info));
                } else if (type.equals("Link")) {
                    notes.add(new Link(header, url, id, info));
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
                String info = rs.getString("Info");
                if (type.equals("Link")) {
                    note = new Link(noteHeader, noteUrl, id, info);
                } else if (type.equals("Book")) {
                    note = new Book(noteHeader, noteUrl, noteAuthor, noteIsbn, id, info);
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
    
    @Override
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
