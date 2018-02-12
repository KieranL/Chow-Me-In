from flask import jsonify
import json
from database.database_manager import DatabaseManager as database

class ChowController():
    @classmethod
    def create_chow(cls, chow):
        db = database.getInstance()
        success = db.put_item('Chow', chow)
        return jsonify({"success": success})

    @classmethod
    def get_chows(cls):
        db = database.getInstance()
        chows = db.scan_as_json('Chow')
        return jsonify({"success":{"chows": chows}})
    
    @classmethod
    def get_chow(cls, chow_id):
        db = database.getInstance()
        chow = db.get_item_as_json('Chow', chow_id)
        return jsonify({"success":{"chow": chow}})

    @classmethod
    def update_chow(cls, chow_id, chow):
        db = database.getInstance()
        chow['id'] = chow_id
        success = db.put_item('Chow', chow)
        return jsonify({"success": success, "chow": chow})

    @classmethod
    def delete_chow(cls, chow_id):
        db = database.getInstance()
        success = db.delete_item('Chow', chow_id)
        return jsonify({"success":success})