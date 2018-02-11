from flask import jsonify
import json
from database.database_manager import DatabaseManager as database

class UserController():
    @classmethod
    def create_user(cls, user):        
        return jsonify({"success":{"user": user}})    

    @classmethod
    def get_user(cls, user_id):        
        db = database.getInstance()
        user = db.get_item_as_json('User', user_id)
        return jsonify({"success":{"user": user}})

    @classmethod
    def update_user(cls, user_id, user):        
        return jsonify({"success":{"user": user}})

    @classmethod
    def delete_user(cls, user_id):        
        db = database.getInstance()
        success = db.delete_item('User', user_id)
        return jsonify({"success":success})