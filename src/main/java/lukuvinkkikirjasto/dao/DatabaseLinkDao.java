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

public class DatabaseLinkDao extends Dao {
    public DatabaseLinkDao(String fileName) {
        super(fileName);
        createDbFolder();
        createNoteTable();
        createTagTable();
        createTagNoteAssociationTable();
    }

    private void createNoteTable() {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes ("
                    + "id integer PRIMARY KEY,"
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

    private void createTagNoteAssociationTable() {
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

    private static Connection getConnectionHeroku() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        return DriverManager.getConnection(dbUrl, username, password);
    }

    @Override
    protected Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + this.filePath);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLinkDao.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void clearDao() {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
