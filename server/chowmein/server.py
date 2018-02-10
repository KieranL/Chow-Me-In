#!/usr/bin/python3

from flask import Flask
from apis.chow import chow_api_bp

from database.database_manager import DatabaseManager as database

# EB looks for an 'application' callable by default.
application = Flask(__name__)

#register API endpoint files here
application.register_blueprint(chow_api_bp)

if __name__ == "__main__":
    db = database.getInstance()

    #create any needed tables
    tables = ['User', 'Chow']
    for table in tables:
        if not db.table_exists(table):
            db.create_table(table)

    application.run()