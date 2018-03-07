from flask import jsonify
import json
import boto3
from database.database_manager import DatabaseManager as database

class ChowController():
    @classmethod
    def create_chow(cls, chow):
        db = database.getInstance()
        success = db.put_item('Chow', chow)
        response = jsonify({"success": success})
        return response

    @classmethod
    def get_chows(cls):
        db = database.getInstance()
        chows = db.scan_as_json('Chow')

        #remove chows that are marked as 'isDeleted'
        chows = [chow for chow in chows if not (chow.has_key('deleted') and chow['deleted'] == 1)]
        
        response = jsonify(chows)
        return response
    
    @classmethod
    def get_chow(cls, chow_id):
        db = database.getInstance()
        chow = db.get_item_as_json('Chow', chow_id)
        response = jsonify(chow)
        return response

    @classmethod
    def update_chow(cls, chow_id, chow):
        db = database.getInstance()
        chow['id'] = chow_id
        success = db.put_item('Chow', chow)
        return jsonify({"success": success, "chow": chow})

    @classmethod
    def delete_chow(cls, chow_id):
        db = database.getInstance()
        success = db.soft_delete_item('Chow', chow_id)
        return jsonify({"success":success})
    @classmethod
    def join_chow(cls, chow_id, token):
        db = database.getInstance()
        client = boto3.client('cognito-idp', region_name='us-east-2')
        user = client.get_user(AccessToken=token)
        chow = db.get_item_as_json('Chow', chow_id)
        chow['joinedUser'] = user['Username']
        success = db.put_item('Chow', chow)
        return jsonify({"success":success})