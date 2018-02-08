from flask import Blueprint
from controllers.chow import ChowController

chow_api_bp = Blueprint('chow', __name__)

@chow_api_bp.route('/chows', methods=['GET'])
def get_chows() :
    return ChowController.get_chows()

@chow_api_bp.route('/chow/<int:chow_id>', methods=['GET'])
def get_chow(chow_id) :
    return ChowController.get_chow(chow_id)