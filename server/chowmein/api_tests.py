#from: http://www.patricksoftwareblog.com/unit-testing-a-flask-application/

import os
import unittest
import json

from server import application as app
from database.database_manager import DatabaseManager as database

test_chow = {
        "id": 1,
        "createdBy": 420,
        "createdTime": "3019-06-30T12:43:55",
        "deleted": False,
        "food": "cake",
        "lastUpdated": "3019-06-30T12:43:55",
        "meetLocation": "cake place",
        "meetTime": "3019-06-30T12:43:55",
        "notes": "yummy"}

class BasicTests(unittest.TestCase):
 
    ############################
    #### setup and teardown ####
    ############################
 
    # executed prior to each test
    def setUp(self):
        app.config['TESTING'] = True
        app.config['WTF_CSRF_ENABLED'] = False
        app.config['DEBUG'] = False

        #not sure if this will work or not
        app.config['DYNAMO_ENABLE_LOCAL'] = True
        app.config['DYNAMO_LOCAL_HOST'] = 'localhost'
        app.config['DYNAMO_LOCAL_PORT'] = 8000

        self.app = app.test_client()
 
        # Disable sending emails during unit testing
        self.assertEqual(app.debug, False)
        db = database.getInstance()
        tables = ['User', 'Chow']
        for table in tables:
            if db.table_exists(table):
                db.delete_table(table)
        
        for table in tables:
            if not db.table_exists(table):
                db.create_table(table)
 
    # executed after each test
    def tearDown(self):
        pass
 
 
###############
#### tests ####
###############
     
    def test_chow_api_get_all(self):
        response = self.app.get('/chow')
        self.assertEqual(response.status_code, 200)
        
        #how to get response data
        data = json.loads(response.get_data(as_text=True))
        print(data)

    def test_chow_api_get_specific(self):
        chow_id = 1

        response = self.app.get('/chow/' + str(chow_id))
        self.assertEqual(response.status_code, 200)

    def test_chow_api_create_chow(self):
        chow = test_chow
        response = self.app.post('/chow', data=dict(chow=json.dumps(chow)))
        self.assertEqual(response.status_code, 200)

    def test_chow_api_update_chow(self):
        chow = {"notes": "testing"}
        chow_id = 1

        response = self.app.post('/chow/' + str(chow_id), data=dict(chow=json.dumps(chow)))
        self.assertEqual(response.status_code, 200)

    def test_chow_api_delete_chow(self):        
        chow_id = 1
        
        response = self.app.delete('/chow/' + str(chow_id))
        self.assertEqual(response.status_code, 200)

if __name__ == "__main__":
    unittest.main()