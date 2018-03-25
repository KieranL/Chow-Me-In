from flask import jsonify
import json
import boto3
import datetime
from database.database_manager import DatabaseManager as database
from util.chow import ChowUtil as chowutil

class ChowController():
    @classmethod
    def create_chow(cls, chow):
        db = database.getInstance()
        success = db.put_item('Chow', chow)
        response = jsonify({"success": success})
        return response

    @classmethod
    def get_chows(cls, remove_expired=True):
        db = database.getInstance()
        chows = db.scan_as_json('Chow')

        # remove chows that are marked as 'isDeleted'
        chows = chowutil.remove_soft_delete(chows)
        
        if (remove_expired):
            chows = chowutil.remove_expired(chows)
        
        #remove chows that have been "chowed in" on
        chows = chowutil.remove_joined_users(chows)

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
        chow['joinedName'] = user['UserAttributes'][2]['Value'] #username is stored here
        chow['joinedEmail'] = user['UserAttributes'][3]['Value'] #email is stored here
        success = db.put_item('Chow', chow)
        return jsonify({"success":success})

    @classmethod
    def unjoin_chow(cls, chow_id, token):
        db = database.getInstance()
        client = boto3.client('cognito-idp', region_name='us-east-2')
        user = client.get_user(AccessToken=token)
        chow = db.get_item_as_json('Chow', chow_id)
        chow['joinedUser'] = None
        chow['joinedName'] = None
        chow['joinedEmail'] = None
        success = db.put_item('Chow', chow)
        return jsonify({"success":success})

    @classmethod
    def get_created_chows(cls, token):
        db = database.getInstance()
        client = boto3.client('cognito-idp', region_name='us-east-2')
        user = client.get_user(AccessToken=token)
        chows = db.scan_as_json_with_criteria('Chow', 'posterUser', user['Username'])

        chows = chowutil.remove_soft_delete(chows)
        chows = chowutil.remove_expired(chows)

        return jsonify(chows)

    @classmethod
    def get_joined_chows(cls, token):
        db = database.getInstance()
        client = boto3.client('cognito-idp', region_name='us-east-2')
        user = client.get_user(AccessToken=token)
        chows = db.scan_as_json_with_criteria('Chow', 'joinedUser', user['Username'])

        chows = chowutil.remove_soft_delete(chows)
        chows = chowutil.remove_expired(chows)
        
        return jsonify(chows)