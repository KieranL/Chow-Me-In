from flask import jsonify
import json
import boto3
from database.database_manager import DatabaseManager as database

class UserController():
    @classmethod
    def create_user(cls, user):
        db = database.getInstance()
        success = db.put_item('User', user)
        return jsonify({"success": success, "user": user})      

    @classmethod
    def update_user(cls, user_id, user):        
        db = database.getInstance()
        user['id'] = user_id
        success = db.put_item('User', user)
        return jsonify({"success": success, "user": user})

    @classmethod
    def delete_user(cls, user_id):        
        db = database.getInstance()
        success = db.delete_item('User', user_id)
        return jsonify({"success":success})

    @classmethod
    def get_user(cls, userToken):
        user = None
        success = True

        try:
            client = boto3.client('cognito-idp', region_name='us-east-2')
            user = client.get_user(AccessToken=userToken)
        except:
            success = False

        return jsonify({"success": success, "user": user})