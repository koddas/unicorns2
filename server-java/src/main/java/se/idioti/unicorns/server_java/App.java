package se.idioti.unicorns.server_java;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App  {
    public static void main( String[] args ) throws Exception {
    	port(5000);
		
		Storage storage = new Storage();
		storage.setup();
		
		// The API definition below
		
		String prefix = "/api/v1";
		staticFiles.location("/public"); 
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		/*
		 * List all unicorns
		 */
		get(prefix + "/unicorns", (req, res) -> {
			List<Unicorn> unicorns = storage.fetchUnicorns();
			res.type("application/json");
			return objectMapper.writeValueAsString(unicorns);
		});

		/*
		 * Add new unicorn
		 */
		post(prefix + "/unicorns", (req, res) -> {
			List<Unicorn> unicorns = storage.fetchUnicorns();
			String output = "";
			
			for (Unicorn unicorn : unicorns) {
				output += unicorn.name + "\n";
			}
			
			return output;
		});
		
		/*
		 * Fetch a unicorn
		 */
		get(prefix + "/unicorns/:id", (req, res) -> {
			int id = Integer.parseInt(req.params(":id"));
			Unicorn unicorn = storage.fetchUnicorn(id);
			if (unicorn == null) {
				res.status(404);
				return "";
			}
			res.type("application/json");
			return objectMapper.writeValueAsString(unicorn);
		});
		
		/*
		 * Update a unicorn
		 */
		put(prefix + "/unicorns/:id", (req, res) -> {
			int id = Integer.parseInt(req.params(":id"));
			Unicorn unicorn = storage.fetchUnicorn(id);
			
			return unicorn.name;
		});
		
		/*
		 * Delete a unicorn
		 */
		delete(prefix + "/unicorns/:id", (req, res) -> {
			int id = Integer.parseInt(req.params(":id"));
			Unicorn unicorn = storage.fetchUnicorn(id);
			
			return unicorn.name;
		});
		
		// The HTML representations below
		
		/*
		 * Fetch an HTML list of all unicorns
		 */
		get("/unicorns", (req, res) -> {
			List<Unicorn> unicorns = storage.fetchUnicorns();
			
			Map<String, Object> context = new HashMap<>();
			List<Map<String, Object>> mappedUnicorns = new ArrayList<Map<String, Object>>();
			for (Unicorn unicorn : unicorns) {
				mappedUnicorns.add(objectMapper.convertValue(unicorn, Map.class));
			}
			context.put("unicorns", mappedUnicorns);
			
			return Utils.render(context, "list.tpl");
		});
		
		/*
		 * Fetch an HTML representation of a unicorn
		 */
		get("/unicorns/:id", (req, res) -> {
			int id = Integer.parseInt(req.params(":id"));
			Unicorn unicorn = storage.fetchUnicorn(id);
			Map<String, Object> context = new HashMap<>();
			context.put("unicorn", objectMapper.convertValue(unicorn, Map.class));

			return Utils.render(context, "details.tpl");
		});
    }
}
