from flask import jsonify

class ChowController():
    @classmethod
    def get_chows(cls):
        chows = [{"id": 1}, {"id": 2}]
        return jsonify({"success":{"chows": chows}}) #TODO use real data
    
    @classmethod
    def get_chow(cls, chow_id):
        chow = {"id": chow_id}
        return jsonify({"success":{"chow": chow}}) #TODO use real data