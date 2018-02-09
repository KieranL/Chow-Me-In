from flask import jsonify
import json
from database.database_manager import DatabaseManager

class ChowController():
    @classmethod
    def get_chows(cls):
        chows = [{"id": 1}, {"id": 2}]
        return jsonify({"success":{"chows": chows}}) #TODO use real data
    
    @classmethod
    def get_chow(cls, chow_id):
        db = DatabaseManager('local')
        chow = db.get_item('Chow', chow_id)
        return jsonify({"success":{"chow": chow}}) #TODO use real data