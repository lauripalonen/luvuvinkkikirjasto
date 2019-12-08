/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkikirjasto.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import lukuvinkkikirjasto.domain.Tag;

public abstract class Dao {

    protected String filePath;

    public Dao(String fileName) {
        this.filePath = "db/" + fileName;
    }

    public Dao() {
    }

    public void addLink(String header, String url, String info) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Notes "
                    + "(Header, URL, Author, ISBN, Type, Info) "
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

    public void addBook(String header, String url, String author, String isbn, String info) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Notes "
                    + "(Header, URL, Author, ISBN, Type, Info) "
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
                Book book = (Book) updatedNote;
                PreparedStatement stmtBook = connection.prepareStatement("UPDATE Notes SET "
                        + "Header=?, URL=?, Author=?, ISBN=?, Type=?, Info=? "
                        + "WHERE id = ?");
                stmtBook.setString(1, book.getHeader());
                stmtBook.setString(2, book.getUrl());
                stmtBook.setString(3, book.getAuthor());
                stmtBook.setString(4, book.getIsbn());
                stmtBook.setString(5, "Book");
                stmtBook.setString(6, book.getInfo());
                stmtBook.setInt(7, id);
                stmtBook.executeUpdate();
                stmtBook.close();
                updateTagsOfNote(oldNote, updatedNote);
            } else if (type.equals("Link")) {
                //update link
                Link link = (Link) updatedNote;
                PreparedStatement stmtLink = connection.prepareStatement("Update Notes SET "
                        + "Header=?, URL=?, Type=?, Info=? WHERE id = ?");
                stmtLink.setString(1, link.getHeader());
                stmtLink.setString(2, link.getUrl());
                stmtLink.setString(3, "Link");
                stmtLink.setString(4, link.getInfo());
                stmtLink.setInt(5, id);
                stmtLink.executeUpdate();
                stmtLink.close();
            }
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

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

    public boolean addTag(String header) {
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Tags (Header) " + "VALUES (?)");
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

    public void updateTagsOfNote(Note oldNote, Note updatedNote) {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmtDelete = connection.prepareStatement("DELETE FROM notes_tags WHERE note_id = ?");
            stmtDelete.setInt(1, oldNote.getId());
            stmtDelete.executeUpdate();
            stmtDelete.close();
            for (String tagString : updatedNote.getTags()) {
                Tag tag = getTag(tagString);
                if (tag != null) {
                    PreparedStatement stmt = connection.prepareStatement("INSERT INTO notes_tags (note_id, tag_id)"
                            + " VALUES (?, ?)");
                    stmt.setInt(1, oldNote.getId());
                    stmt.setInt(2, tag.getId());
                    stmt.executeUpdate();
                    stmt.close();
                }
            }
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLinkDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected abstract Connection getConnection();

    public abstract void clearDao();

}
