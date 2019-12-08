package lukuvinkkikirjasto.UI;

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
            String info = request.queryParams("info");
            String tags = request.queryParams("tags");

            library.addLink(header, url, info);
            
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
            String info = request.queryParams("info");
            String tags = request.queryParams("tags");

            library.addBook(header, url, author, isbn, info);
            
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

        get("/listnotes", (request, response) -> {
            int typeFilter = request.queryParams("type_filter") != null ? Integer.parseInt(request.queryParams("type_filter")) : 1;
            String queryTags = request.queryParams("tag_filters");
            List<String> tagFilters = queryTags != null && !queryTags.trim().equals("") 
                    ? Arrays.asList(queryTags.trim().split(" ")) 
                    : new ArrayList<>();
            HashMap<String, Object> model = new HashMap();
            model.put("template", "templates/listnotes.html");
            ArrayList<Note> notes = library.listAll();
            notes.forEach(note -> note.setTags(library.getTagsForNote(note.getId())));
            List<Note> filteredNotes = notes.stream().filter(note -> {
                boolean passesTypeFilter = (typeFilter == 1 ||
                     (typeFilter == 2 && note instanceof Link) ||
                     (typeFilter == 3 && note instanceof Book));
                boolean passesTagFilter = tagFilters.isEmpty() || note.getTags().stream().anyMatch(tag -> tagFilters.contains(tag));
                return passesTypeFilter && passesTagFilter;
            }).collect(Collectors.toList());
            model.put("noteList", filteredNotes);
            model.put("type_filter", typeFilter);
            model.put("tag_filters", tagFilters);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, layout)
            );
        });
    }
}
