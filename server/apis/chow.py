from flask import Blueprint
from controllers.chow import ChowController

chow_api_bp = Blueprint('chow', __name__)

@chow_api_bp.route('/chows', methods=['GET'])
def get_chows() :
    return ChowController.get_chows()