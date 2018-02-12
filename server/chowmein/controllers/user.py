from flask import jsonify
import json
from database.database_manager import DatabaseManager as database

class UserController():
    @classmethod
    def create_user(cls, user):
        db = database.getInstance()
        success = db.put_item('User', user)
        return jsonify({"success": success, "user": user})      

    @classmethod
    def get_user(cls, user_id):        
        db = database.getInstance()
        user = db.get_item_as_json('User', user_id)
        return jsonify({"success":{"user": user}})

    @classmethod
    def update_user(cls, user_id, user):        
        db = database.getInstance()
        user['Id'] = user_id
        success = db.put_item('User', user)
        return jsonify({"success": success, "user": user})

    @classmethod
    def delete_user(cls, user_id):        
        db = database.getInstance()
        success = db.delete_item('User', user_id)
        return jsonify({"success":success})