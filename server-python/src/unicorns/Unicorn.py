# -*- coding: utf-8 -*-

from unicorns.Location import Location
import json

class Unicorn:
    '''
    A simple class representing a unicorn.
    '''
    
    id = 0
    name = ""
    description = ""
    reportedBy = ""
    spottedWhere = Location()
    spottedWhen = 0
    image = ""

    def __init__(self):
        pass
    
    def fromDB(self, data):
        '''
        Populates a unicorn with data from a database query.
        '''
        
        self.id = data[0]
        self.name = data[1]
        self.description = data[2]
        self.reportedBy = data[3]
        self.spottedWhere.name = data[4]
        self.spottedWhere.lat = data[5]
        self.spottedWhere.lon = data[6]
        self.spottedWhen = data[7]
        self.image = data[8]
    
    def fromJSON(self, data):
        '''
        Populates a unicorn with data from a JSON object.
        '''
        self.id = data['id']
        self.name = data['name']
        self.description = data['description']
        self.reportedBy = data['reportedBy']
        self.spottedWhere.name = data['spottedWhere']['name']
        self.spottedWhere.lat = data['spottedWhere']['lat']
        self.spottedWhere.lon = data['spottedWhere']['lon']
        self.spottedWhen = data['spottedWhen']
        self.image = data['image']
    
    def toDict(self):
        '''
        Creates a dictionary with values from this unicorn. This is nifty
        when adding unicorns into databases.
        '''
        
        return {
                'id': self.id,
                'name': self.name,
                'description': self.description,
                'reportedBy': self.reportedBy,
                'spottedWhere': {
                    'name': self.spottedWhere.name,
                    'lat': self.spottedWhere.lat,
                    'lon': self.spottedWhere.lon
                },
                'spottedWhen': self.spottedWhen,
                'image': self.image
                }
