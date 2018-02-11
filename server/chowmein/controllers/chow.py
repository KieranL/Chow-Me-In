from flask import jsonify
import json
from database.database_manager import DatabaseManager as database

class ChowController():
    @classmethod
    def create_chow(cls, chow):
        return chow #TODO use real data

    @classmethod
    def get_chows(cls):
        chows = [{"id": 1}, {"id": 2}]
        return jsonify({"success":{"chows": chows}}) #TODO use real data
    
    @classmethod
    def get_chow(cls, chow_id):
        db = database.getInstance()
        chow = db.get_item_as_json('Chow', chow_id)
        return jsonify({"success":{"chow": chow}})

    @classmethod
    def update_chow(cls, chow_id, chow):
        return chow #TODO use real data

    @classmethod
    def delete_chow(cls, chow_id):
        db = database.getInstance()
        success = db.delete_item('Chow', chow_id)
        return jsonify({"success":success})