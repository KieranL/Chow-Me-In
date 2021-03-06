#!/usr/bin/python3

import sys
sys.path.insert(0, '/opt/python/current/app/server/chowmein')

from flask import Flask
from flask_cors import CORS
from database.database_manager import DatabaseManager as database

from apis.chow import chow_api_bp
from apis.user import user_api_bp

# EB looks for an 'application' callable by default.
application = Flask(__name__)
CORS(application)

#register API endpoint files here
application.register_blueprint(chow_api_bp)
application.register_blueprint(user_api_bp)

if __name__ == "__main__":
    db = database.getInstance()

    #create any needed tables
    tables = ['Chow']
    for table in tables:
        if not db.table_exists(table):
            db.create_table(table)

    application.run()
