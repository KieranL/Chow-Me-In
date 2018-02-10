from flask import jsonify
import json
from database.database_manager import DatabaseManager as database

class ChowController():
    @classmethod
    def get_chows(cls):
        chows = [{"id": 1}, {"id": 2}]
        return jsonify({"success":{"chows": chows}}) #TODO use real data
    
    @classmethod
    def get_chow(cls, chow_id):
        db = database.getInstance()
        chow = db.get_item_as_json('Chow', chow_id)
        return jsonify({"success":{"chow": chow}})