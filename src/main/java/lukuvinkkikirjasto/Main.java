package lukuvinkkikirjasto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import lukuvinkkikirjasto.domain.Tag;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {

    static String portFromEnv = new ProcessBuilder().environment().get("PORT");
    static String layout = "templates/layout.html";

    static void setEnvPort(String port) {
        portFromEnv = port;
    }

    static int findOutPort() {
        if (portFromEnv != null) {
            return Integer.parseInt(portFromEnv);
        }

        return 3001;
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        //Library library = new Library(); //in memory
        Library library = new Library("links.db"); //in sqlite database
//        UserInterface ui = new UserInterface(library, scanner);
//
//        ui.startLibrary();

        staticFiles.location("/public");
        port(findOutPort());

        get("/", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.html");
            ArrayList<Note> notes = library.listAll();
            model.put("noteList", notes);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/newlink", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/newlink.html");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/newlink", (request, response) -> {
            String header = request.queryParams("header");
            String url = request.queryParams("url");
            String tags = request.queryParams("tags");
            
            library.addLink(header, url);
            
            Note note = library.getNote(header, url);
            List<String> tagList = Arrays.asList(tags.split(" "));
            tagList.forEach(tagHeader -> {
                library.addTag(tagHeader);
                Tag tag = library.getTag(tagHeader);
                library.joinTagToNote(note, tag);
            });

            response.redirect("/");
            return null;
        });

        get("/newbook", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/newbook.html");
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/newbook", (request, response) -> {
            String header = request.queryParams("header");
            String url = request.queryParams("url");
            String author = request.queryParams("author");
            String isbn = request.queryParams("isbn");
            String tags = request.queryParams("tags");

            library.addBook(header, url, author, isbn);
            
            Note note = library.getNote(header, url);
            List<String> tagList = Arrays.asList(tags.split(" "));
            tagList.forEach(tagHeader -> {
                library.addTag(tagHeader);
                Tag tag = library.getTag(tagHeader);
                library.joinTagToNote(note, tag);
            });

            response.redirect("/");
            return null;
        });

        post("/changetype", (request, response) -> {
            int noteType = Integer.parseInt(request.queryParams("noteType"));
            if (noteType == 1) {
                response.redirect("/newlink");
            } else if (noteType == 2) {
                response.redirect("/newbook");
            }
            return null;
        });

        get("/listall", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/listall.html");
            ArrayList<Note> notes = library.listAll();
            notes.forEach(note -> note.setTags(library.getTagsForNote(note.getId())));
            model.put("noteList", notes);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/listlinks", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/listlinks.html");
            ArrayList<Link> links = library.listLinks();
            links.forEach(link -> link.setTags(library.getTagsForNote(link.getId())));
            model.put("linkList", links);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/listbooks", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/listbooks.html");
            ArrayList<Book> books = library.listBooks();
            books.forEach(book -> book.setTags(library.getTagsForNote(book.getId())));
            model.put("bookList", books);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        post("/changelist", (request, response) -> {
            int listType = Integer.parseInt(request.queryParams("listType"));
            if (listType == 1) {
                response.redirect("/listall");
            } else if (listType == 2) {
                response.redirect("/listlinks");
            } else if (listType == 3) {
                response.redirect("/listbooks");
            }
            return null;
        });

    }

}
