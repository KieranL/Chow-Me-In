from flask import jsonify

class ChowController():
    @classmethod
    def get_chows(cls):
        return jsonify({"success":{"chows": []}})