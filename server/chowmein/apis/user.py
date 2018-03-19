from flask import Blueprint, request
from controllers.user import UserController
import json

user_api_bp = Blueprint('user', __name__)

@user_api_bp.route('/user/token/<string:userToken>', methods=['GET'])
def get_user(userToken):
    return UserController.get_user(userToken)