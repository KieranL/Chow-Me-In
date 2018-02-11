from flask import Blueprint, request
from controllers.user import UserController
import json

user_api_bp = Blueprint('user', __name__)

@user_api_bp.route('/user', methods=['POST'])
def create_user() :
    data = json.loads(request.form['user'])
    return UserController.create_user(data)

@user_api_bp.route('/user/<int:user_id>', methods=['GET'])
def get_user(user_id) :
    return UserController.get_user(user_id)

@user_api_bp.route('/user/<int:user_id>', methods=['POST'])
def update_user(user_id) :
    data = json.loads(request.form['user'])
    return UserController.update_user(user_id, data)

@user_api_bp.route('/user/<int:user_id>', methods=['DELETE'])
def delete_user(user_id) :
    return UserController.delete_user(user_id)