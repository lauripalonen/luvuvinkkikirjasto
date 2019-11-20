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

public class DatabaseLinkDao implements LinkDao {

    private String filePath;

    public DatabaseLinkDao(String fileName) {
        this.filePath = "db/" + fileName;
        initializeDao();
    }

    @Override
    public void addLink(String link) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.filePath);
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
    public ArrayList<String> listLinks() {
        ArrayList<String> links = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.filePath);
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Links");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String link = rs.getString("Link");
                links.add(link);
            }
            stmt.close();
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        if(links == null) {
            return new ArrayList<>();
        }
        return links;
    }

    @Override
    public void initializeDao() {
        createDbFolder();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.filePath);
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

}
