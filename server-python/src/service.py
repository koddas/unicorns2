# -*- coding: utf-8 -*-

from flask import Flask, abort, jsonify, make_response, render_template, request
from unicorns.Unicorn import Unicorn
import storage

app = Flask(__name__)
prefix = '/api/v1'

# The API definition

@app.route(prefix + '/unicorns', methods=['GET', 'POST'])
def unicorn_collection():
    if request.method == 'GET':
        # List the unicorns
        return list_unicorns()
    elif request.method == 'POST':
        # Add a unicorn
        return add_unicorn()

@app.route(prefix + '/unicorns/<int:unicorn>', methods=['GET', 'PUT', 'DELETE'])
def unicorn_resources(unicorn):
    if request.method == 'GET':
        # Retrieve a unicorn
        return get_unicorn(unicorn)
    elif request.method == 'PUT':
        # Update a unicorn
        return update_unicorn(unicorn)
    elif request.method == 'DELETE':
        # Remove a unicorn
        return delete_unicorn(unicorn)

@app.route('/unicorns')
def unicorn_collection_html():
    unicorns = storage.fetch_unicorns()
    return render_template('list.tpl', unicorns=unicorns)

@app.route('/unicorns/<int:unicorn_id>')
def unicorn_resources_html(unicorn_id):
    unicorn = storage.fetch_unicorn(unicorn_id);
    if unicorn is not None:
        return render_template('details.tpl', unicorn=unicorn)
    else:
        abort(404)

@app.errorhandler(404)
def page_not_found(e):
    return render_template('error.tpl'), 404

# Helper functions

def list_unicorns():
    unicorns = storage.fetch_unicorns()
    unicorn_list = []
    for unicorn in unicorns:
       unicorn_list.append(unicorn.toDict())
    return jsonify(unicorn_list)

def add_unicorn():
    json_data = request.get_json()
    unicorn = Unicorn()
    unicorn.fromJSON(json_data)
    unicorn = storage.add_unicorn(unicorn)
    
    if unicorn.id > 0:
        # 201, unicorn added
        return make_response(jsonify(unicorn.toDict()), 201)
    else:
        # 400, couldn't add unicorn
        return '', 400

def get_unicorn(unicorn_id):
    unicorn = storage.fetch_unicorn(unicorn_id);
    if unicorn is not None:
        return jsonify(unicorn.toDict())
    else:
        return '', 404

def update_unicorn(unicorn):
    json_data = request.get_json()
    unicorn = Unicorn()
    unicorn.fromJSON(json_data)
    
    if storage.update_unicorn(unicorn):
        # 204, unicorn updated
        return '', 204
    else:
        # 400, couldn't update unicorn
        return '', 400

def delete_unicorn(unicorn):
    if storage.delete_unicorn(unicorn):
        # 204, unicorn deleted
        return '', 204
    else:
        # 404, unicorn not found
        return '', 404

# Set up the database
storage.setup()
