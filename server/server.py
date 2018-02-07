#!/usr/bin/python3

from flask import Flask
from apis.chow import chow_api_bp

# EB looks for an 'application' callable by default.
application = Flask(__name__)
application.register_blueprint(chow_api_bp)

if __name__ == "__main__":
    application.run()