package lukuvinkkikirjasto.UI;

import java.util.ArrayList;
import java.util.HashMap;
import lukuvinkkikirjasto.domain.Book;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Link;
import lukuvinkkikirjasto.domain.Note;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import spark.template.velocity.VelocityTemplateEngine;

public class WebUserInterface {
    
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
    
    public void start() {
        Library library = new Library("links.db");
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

            library.addLink(header, url);

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

            library.addBook(header, url, author, isbn);

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
            model.put("noteList", notes);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/listlinks", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/listlinks.html");
            ArrayList<Link> links = library.listLinks();
            model.put("linkList", links);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });

        get("/listbooks", (request, response) -> {
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/listbooks.html");
            ArrayList<Book> books = library.listBooks();
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
