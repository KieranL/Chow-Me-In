from flask import jsonify
import json
import boto3
from database.database_manager import DatabaseManager as database

class UserController():
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