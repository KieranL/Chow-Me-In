from flask import Blueprint, request
from controllers.chow import ChowController
import json

chow_api_bp = Blueprint('chow', __name__)

@chow_api_bp.route('/chow', methods=['POST'])
def create_chow(): 
    data = json.loads(request.form['chow'])
    return ChowController.create_chow(data)

@chow_api_bp.route('/chow', methods=['GET'])
def get_chows():
    return ChowController.get_chows()

@chow_api_bp.route('/chow/<int:chow_id>', methods=['GET'])
def get_chow(chow_id):
    return ChowController.get_chow(chow_id)

@chow_api_bp.route('/chow/<int:chow_id>', methods=['POST'])
def update_chow(chow_id): 
    data = json.loads(request.form['chow'])
    return ChowController.update_chow(chow_id, data)

@chow_api_bp.route('/chow/<int:chow_id>', methods=['DELETE'])
def delete_chow(chow_id): 
    return ChowController.delete_chow(chow_id)