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
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;

public class DatabaseLinkDao implements LinkDao {

    private String filePath;

    public DatabaseLinkDao(String fileName) {
        this.filePath = "db/" + fileName;
        initializeDao();
    }

    @Override
    public void addLink(String header, String url) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Notes (Header, URL, Author, ISBN, Type) VALUES (?, ?, ?, ?, ?)");
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
    public void initializeDao() {
        createDbFolder();
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes ("
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

    public void createDbFolder() {
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
    public void clearDao() {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        initializeDao();
    }

    private Connection getConnection() {
        try {
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

}
