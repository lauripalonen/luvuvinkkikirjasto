package lukuvinkkikirjasto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import lukuvinkkikirjasto.UI.UserInterface;
import lukuvinkkikirjasto.domain.Library;
import lukuvinkkikirjasto.domain.Note;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {
    
    static String portFromEnv = new ProcessBuilder().environment().get("PORT");
    static String layout = "templates/layout.html";
    
    static void setEnvPort(String port){
        portFromEnv = port;
    }
    
    static int findOutPort() {
        if ( portFromEnv!=null ) {
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
        
        port(findOutPort());
        
        get("/", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.html");
            ArrayList<Note> notes = library.listAll();
            model.put("noteList", notes);
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        
    }

}
