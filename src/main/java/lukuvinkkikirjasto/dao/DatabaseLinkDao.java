package lukuvinkkikirjasto.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseLinkDao implements LinkDao {

    private String fileName;

    public DatabaseLinkDao(String fileName) {
        this.fileName = fileName;
        initializeDao();
    }

    @Override
    public void addLink(String link) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/" + this.fileName);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Links (Link) VALUES (?)");
            stmt.setString(1, link);
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void initializeDao() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:db/" + this.fileName);
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Links (Link varchar(300))");
            stmt.executeUpdate();
            stmt.close();
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearDao() {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        initializeDao();
    }

}
