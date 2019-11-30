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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    }

    private void createNoteTable() {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes ("
                    + "id integer PRIMARY KEY,"
                    + "Header varchar(300), "
                    + "URL varchar(300), "
                    + "Author varchar(48), "
                    + "ISBN varchar(48),"
                    + "Type varchar(16));"
            );
            stmt.executeUpdate();
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void createTagTable() {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tags ("
                    + "id integer PRIMARY KEY,"
                    + "Header varchar(300);"
            );
            stmt.executeUpdate();
            stmt.close();
            connection.commit();
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
                links.add(new Link(header, url));
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
                books.add(new Book(header, url, author, isbn));
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
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private Connection getConnection() {
        URI dbUri = new URI(System.getenv("DATABSE_URL"));
        //String dbUrl = System.getenv("DATABASE_URL");

        try {

            if(URI != null){
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

                return DriverManager.getConnection(dbUrl, username, password);
            }

            return DriverManager.getConnection("jdbc:sqlite:" + this.filePath);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<Note> listAll() {
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
                if (type.equals("Book")) {
                    notes.add(new Book(header, url, author, isbn));
                } else if (type.equals("Link")) {
                    notes.add(new Link(header, url));
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Tag> getTagsSet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void joinTagToNote(Note note, Tag tag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createTag(String header) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Tag getTag(String tagHeader) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
