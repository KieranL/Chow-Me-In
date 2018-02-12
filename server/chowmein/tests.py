#from: http://www.patricksoftwareblog.com/unit-testing-a-flask-application/

import os
import unittest
import json

from server import application as app

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
 
    # executed after each test
    def tearDown(self):
        pass
 
 
###############
#### tests ####
###############
 
    #def test_main_page(self):
        #response = self.app.get('/', follow_redirects=True)
        #self.assertEqual(response.status_code, 200)
    
    def test_chow_api_get_all(self):
        response = self.app.get('/chow')
        self.assertEqual(response.status_code, 200)
        
        #how to get response data
        #data = json.loads(response.get_data(as_text=True))

        response = self.app.get('/chow/200')
        self.assertEqual(response.status_code, 200)

    def test_chow_api_get_specific(self):
        chow_id = 1

        response = self.app.get('/chow/' + str(chow_id))
        self.assertEqual(response.status_code, 200)

if __name__ == "__main__":
    unittest.main()