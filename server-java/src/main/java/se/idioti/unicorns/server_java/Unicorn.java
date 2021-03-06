package se.idioti.unicorns.server_java;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A simple class representing a unicorn.
 * 
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class Unicorn {
	public int id = 0;
	public String name = "";
	public String description = "";
	public String reportedBy = "";
	public Location spottedWhere = new Location();
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Timestamp spottedWhen = new Timestamp(0);
	public String image = "";
	
	public Unicorn() {
		
	}
}
