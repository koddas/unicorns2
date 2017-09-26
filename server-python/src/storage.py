# -*- coding: utf-8 -*-

import sqlite3
from unicorns.Unicorn import Unicorn

def setup():
    '''
    Creates a database, empties it and populates it with four unicorns.
    '''
    
    conn = sqlite3.connect('unicorns.db')
    c = conn.cursor()
    
    c.execute("DROP TABLE IF EXISTS unicorns")
    c.execute("CREATE TABLE unicorns (id INTEGER PRIMARY KEY, name TEXT, description TEXT, reportedBy TEXT, location TEXT, lat REAL, lon REAL, spottedWhen DATETIME CURRENT_TIMESTAMP, image TEXT)")
    
    c.execute("INSERT INTO unicorns VALUES (1, 'Nordsvensk travhörning', 'Den nordsvenska travhörningen är en gammal fin lantras. Den har ett trevligt temperament, är uthållig och trivs bra i den nordskandinaviska vintern. Jag fick en glimt av den under en tjänsteresa till Sundsvall under min tid på Telia.', 'Johan', 'Sundsvall, Sverige', 62.4402, 17.3409, '0000-00-00 00:00:00', 'http://unicorns.idioti.se/bilder/nordsvensk.jpg')")
    c.execute("INSERT INTO unicorns VALUES (2, 'Karibisk strandponny', 'En lynnig ras som hittas i den karibiska övärlden. Dras till saltvatten och lever av fisk och skaldjur. Just det här exemplaret skådades under en familjesemester. Den sprang ut framför bilen så att min svåger fick svänga och körde över en duva. Oerhört tragiskt.', 'Johan', 'Bahia Honda Key, USA', 24.6661, -81.2636, '2014-10-26 23:00:00', 'http://unicorns.idioti.se/bilder/strandponny.jpg')")
    c.execute("INSERT INTO unicorns VALUES (3, 'Nattaktiv hornlöpare', 'Under en tur med mina scouter sov jag vid det gamla slottet i Strečno. Det är en pittoresk ruin, som tydligen är någon form av hotspot för den här enhörningsrasen. De tenderar att mest röra sig nattetid, från vilket de fått sitt namn. Notera det ovanligt tunna hornet. Ett riktigt praktexemplar!', 'Johan', 'Strečno, Slovakien', 49.1778, 18.8902, '2015-09-08 12:14:15', 'http://unicorns.idioti.se/bilder/nattaktiv.jpg')")
    c.execute("INSERT INTO unicorns VALUES (4, 'Småväxt enhörning', 'Morsans gamla granne var veterinär och hade en hel uppsjö av djur. Hundar, höns, hängbukssvin och en småväxt enhörning vid namn Morris. Morris var, trots sin något bistra uppsyn, en trevlig varelse. Till skillnad från alla andra enhörningar jag stött på spinner den här rasen och äter kattmat. En oerhört spännande varelse. Yes.', 'Johan', 'Östra Grevie, Sverige', 55.671, 12.5212, '2013-08-23 22:08:00', 'http://unicorns.idioti.se/bilder/smavaxt.jpg')")
    
    conn.commit()
    conn.close()

def fetch_unicorns():
    '''
    Fetches all unicorns in the database. Returns a list with them.
    '''
    
    unicorns = []
    conn = sqlite3.connect('unicorns.db')
    c = conn.cursor()
    
    for row in c.execute("SELECT * FROM unicorns"):
        unicorn = Unicorn()
        unicorn.fromDB(row)
        unicorns.append(unicorn)
    
    conn.close()
    return unicorns
    
def fetch_unicorn(unicorn_id):
    '''
    Fetches a specific unicorn from the database.
    '''
    
    unicorn = Unicorn()
    conn = sqlite3.connect('unicorns.db')
    c = conn.cursor()
    
    c.execute("SELECT * FROM unicorns WHERE id = ?", (int(unicorn_id), ))
    row = c.fetchone()
    if row:
        unicorn.fromDB(row)
    else:
        unicorn = None
    conn.close()

    return unicorn

def add_unicorn(unicorn):
    '''
    Adds a unicorn. The parameter is a Unicorn object.
    '''
    flattened_unicorn = unicorn.toDict()
    flattened_unicorn['location'] = flattened_unicorn['spottedWhere']['name']
    flattened_unicorn['lat'] = flattened_unicorn['spottedWhere']['lat']
    flattened_unicorn['lon'] = flattened_unicorn['spottedWhere']['lon']
    
    conn = sqlite3.connect('unicorns.db')
    c = conn.cursor()
    
    c.execute("INSERT INTO unicorns (name, description, reportedBy, location, " +
              "lat, lon, spottedWhen, image)"
              "VALUES (:name, :description, :reportedBy, :location, " +
              ":lat, :lon, :spottedWhen, :image)",
              flattened_unicorn)
    unicorn.id = c.lastrowid;    

    conn.commit()
    conn.close()

    return unicorn

def update_unicorn(unicorn):
    '''
    Updates a unicorn. The parameter is a Unicorn object.
    '''
    
    conn = sqlite3.connect('unicorns.db')
    c = conn.cursor()
    
    c.execute("UPDATE unicorns SET id=:id, name=:name, description=:description, " +
              "reportedBy=:reportedBy, location=:spottedWhereName, " +
              "lat=:spottedWhereLat, lon=:spottedWhereLon, " +
              "spottedWhen=:spottedWhen, image=:image WHERE id=:id",
              unicorn.toDict())
    
    conn.commit()
    conn.close()

def delete_unicorn(unicorn_id):
    '''
    Deletes a given unicorn from the databse.
    '''
    
    conn = sqlite3.connect('unicorns.db')
    conn.execute("DELETE FROM unicorns WHERE id=?", (unicorn_id, ))
    conn.commit()
    conn.close()
