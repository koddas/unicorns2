package se.idioti.unicorns.server_java;

import java.util.Map;

import spark.ModelAndView;
import spark.template.jtwig.JtwigTemplateEngine;

public class Utils {
	public static String render(Map<String, Object> model, String templatePath) {
	    return new JtwigTemplateEngine().render(new ModelAndView(model, templatePath));
	}
}
