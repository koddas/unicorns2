package se.idioti.unicorns.server_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for setting up a unicorn database. Not only that,
 * but it can also read from and write to the database. Just wow.
 * 
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class Storage {
	
	private Connection connection = null;
	
	/**
	 * Sets up the database connection.
	 * 
	 * @throws ClassNotFoundException
	 */
	public Storage() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		 try {
			connection = DriverManager.getConnection("jdbc:sqlite:unicorns.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the database connection.
	 */
	protected void finalize() throws Throwable {
		try {
			connection.close();
		} catch (SQLException e) {
		}
		super.finalize();
	}
	
	/**
	 * Throws the database away and then recreates it.
	 */
	public void setup() {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS unicorns");
			statement.executeUpdate("CREATE TABLE unicorns (id INTEGER PRIMARY KEY, name TEXT, description TEXT, reportedBy TEXT, location TEXT, lat REAL, lon REAL, spottedWhen DATETIME CURRENT_TIMESTAMP, image TEXT)");
		    
			statement.executeUpdate("INSERT INTO unicorns VALUES (1, 'Nordsvensk travhörning', 'Den nordsvenska travhörningen är en gammal fin lantras. Den har ett trevligt temperament, är uthållig och trivs bra i den nordskandinaviska vintern. Jag fick en glimt av den under en tjänsteresa till Sundsvall under min tid på Telia.', 'Johan', 'Sundsvall, Sverige', 62.4402, 17.3409, '2008-09-23 12:00:00', 'http://unicorns.idioti.se/bilder/nordsvensk.jpg')");
			statement.executeUpdate("INSERT INTO unicorns VALUES (2, 'Karibisk strandponny', 'En lynnig ras som hittas i den karibiska övärlden. Dras till saltvatten och lever av fisk och skaldjur. Just det här exemplaret skådades under en familjesemester. Den sprang ut framför bilen så att min svåger fick svänga och körde över en duva. Oerhört tragiskt.', 'Johan', 'Bahia Honda Key, USA', 24.6661, -81.2636, '2014-10-26 23:00:00', 'http://unicorns.idioti.se/bilder/strandponny.jpg')");
			statement.executeUpdate("INSERT INTO unicorns VALUES (3, 'Nattaktiv hornlöpare', 'Under en tur med mina scouter sov jag vid det gamla slottet i Strečno. Det är en pittoresk ruin, som tydligen är någon form av hotspot för den här enhörningsrasen. De tenderar att mest röra sig nattetid, från vilket de fått sitt namn. Notera det ovanligt tunna hornet. Ett riktigt praktexemplar!', 'Johan', 'Strečno, Slovakien', 49.1778, 18.8902, '2015-09-08 12:14:15', 'http://unicorns.idioti.se/bilder/nattaktiv.jpg')");
			statement.executeUpdate("INSERT INTO unicorns VALUES (4, 'Småväxt enhörning', 'Morsans gamla granne var veterinär och hade en hel uppsjö av djur. Hundar, höns, hängbukssvin och en småväxt enhörning vid namn Morris. Morris var, trots sin något bistra uppsyn, en trevlig varelse. Till skillnad från alla andra enhörningar jag stött på spinner den här rasen och äter kattmat. En oerhört spännande varelse. Yes.', 'Johan', 'Östra Grevie, Sverige', 55.671, 12.5212, '2013-08-23 22:08:00', 'http://unicorns.idioti.se/bilder/smavaxt.jpg')");
			
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetches a list of all unicorns.
	 * 
	 * @return A list of unicorns.
	 */
	public List<Unicorn> fetchUnicorns() {
		List<Unicorn> unicorns = new ArrayList<Unicorn>();
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM unicorns");
			while (rs.next()) {
				Unicorn unicorn = new Unicorn();
				
				unicorn.id = rs.getInt("id");
				unicorn.name = rs.getString("name");
				unicorn.description = rs.getString("description");
				unicorn.reportedBy = rs.getString("reportedBy");
				unicorn.spottedWhere.name = rs.getString("location");
				unicorn.spottedWhere.lat = rs.getDouble("lat");
				unicorn.spottedWhere.lon = rs.getDouble("lon");
				unicorn.spottedWhen = Timestamp.valueOf(rs.getString("spottedWhen"));
				unicorn.image = rs.getString("image");
				
				unicorns.add(unicorn);
			}
			
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return unicorns;
	}
	
	/**
	 * Fetches a unicorn.
	 * 
	 * @param id The id number of a unicorm.
	 * @return A unicorn object.
	 */
	public Unicorn fetchUnicorn(int id) {
		Unicorn unicorn = new Unicorn();
		
		try {
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT * FROM unicorns WHERE id = " + id);
			if (rs.next()) {
				unicorn.id = rs.getInt("id");
				unicorn.name = rs.getString("name");
				unicorn.description = rs.getString("description");
				unicorn.reportedBy = rs.getString("reportedBy");
				unicorn.spottedWhere.name = rs.getString("location");
				unicorn.spottedWhere.lat = rs.getDouble("lat");
				unicorn.spottedWhere.lon = rs.getDouble("lon");
				unicorn.spottedWhen = Timestamp.valueOf(rs.getString("spottedWhen"));
				unicorn.image = rs.getString("image");
			} else {
				unicorn = null;
			}
			
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return unicorn;
	}
	
	/**
	 * Adds a new unicorn.
	 * 
	 * @param unicorn A new unicorn object.
	 * @return Returns true if the unicorn was added.
	 * @throws SQLException 
	 */
	public boolean addUnicorn(Unicorn unicorn) {
		boolean added = false;
		try {
			connection.setAutoCommit(false); // This is because SQLite isn't complete...
			Statement statement = connection.createStatement();
			
			String sql = "INSERT INTO unicorns (name, description, reportedBy, "
					   + "location, lat, lon, spottedWhen, image) "
					   + "VALUES ('" + unicorn.name + "', "
					   + "'" + unicorn.description + "', "
					   + "'" + unicorn.reportedBy + "', "
					   + "'" + unicorn.spottedWhere.name + "', "
					   + unicorn.spottedWhere.lat + ", "
					   + unicorn.spottedWhere.lon + ", "
					   + "'" + unicorn.spottedWhen.toString() + "', "
					   + "'" + unicorn.image + "');";
			
			statement.executeUpdate(sql);
			statement.close();
			
			// This is because SQLite isn't complete...
			ResultSet generatedKeys = statement.executeQuery("SELECT last_insert_rowid()");
			if (generatedKeys.next()) {
				unicorn.id = generatedKeys.getInt(1);
				added = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
		return added;
	}
	
	/**
	 * Updates an existing unicorn.
	 * 
	 * @param unicorn A unicorn object.
	 * @return Returns true if the unicorn was updated.
	 * @throws SQLException 
	 */
	public boolean updateUnicorn(Unicorn unicorn){
		boolean updated = false;
		
		try {
			Statement statement = connection.createStatement();

			String sql = "UPDATE unicorns SET id = " + unicorn.id + ", "
					+ "name = '" + unicorn.name + "', "
					+ "description = '" + unicorn.description + "', "
					+ "reportedBy = '" + unicorn.reportedBy + "', "
					+ "location = '" + unicorn.spottedWhere.name + "', "
					+ "lat = " + unicorn.spottedWhere.lat + ", "
					+ "lon = " + unicorn.spottedWhere.lon + ", "
					+ "spottedWhen = '" + unicorn.spottedWhen.toString() + "', "
					+ "image = '" + unicorn.image + "' "
					+ "WHERE id = " + unicorn.id + ";";

			updated = statement.executeUpdate(sql) > 0;

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
	
	/**
	 * Deletes a unicorn from the database.
	 * 
	 * @param id The id of a unicorn
	 * @return Returns true if the unicorn was deleted.
	 * @throws SQLException 
	 */
	public boolean deleteUnicorn(int id) {
		boolean deleted = false;
		
		try {
			Statement statement = connection.createStatement();
	
			deleted = statement.executeUpdate("DELETE FROM unicorns WHERE id = " + id) > 0;
	
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return deleted;
	}
}
