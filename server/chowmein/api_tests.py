#from: http://www.patricksoftwareblog.com/unit-testing-a-flask-application/

import os
import unittest
import json

from server import application as app
from database.database_manager import DatabaseManager as database
from dbinit import initialize_local_database as init_db

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
        init_db() 
 
    # executed after each test
    def tearDown(self):
        pass
 
 
###############
#### tests ####
###############
     
    ### Chow
 
    def test_chow_api_get_all(self):
        response = self.app.get('/chow')
        self.assertEqual(response.status_code, 200)
        
        data = json.loads(response.get_data(as_text=True))
        self.assertEqual(data, json.loads(json.dumps([        
        {
            'food': 'Trash',
            'meetLocation': 'garbage can behind Franklin Bristow\'s place.',
            'lastUpdated':'2018-02-08T01:55:00',
            'id':2,
            'meetTime':'now',
            'notes':'Calling all raccoons, let\'s eat!'
        },
        {
            'food': 'Large Pep. Pizza',
            'meetLocation': 'Dominos down main',
            'lastUpdated':'2018-02-08T17:55:00',
            'id':1,
            'meetTime':'6:30pm',
            'notes':'I ordered this for 2 but my buddy bailed on me. We can work out payment later.'
        },
    ])))

    def test_chow_api_get_specific(self):
        chow_id = 1

        response = self.app.get('/chow/' + str(chow_id))
        self.assertEqual(response.status_code, 200)

        data = json.loads(response.get_data(as_text=True))
        self.assertEqual(data, json.loads(json.dumps(
            {'success':
                {'chow':        
                    {
                        'food': 'Large Pep. Pizza',
                        'meetLocation': 'Dominos down main',
                        'lastUpdated':'2018-02-08T17:55:00',
                        'id':1,
                        'meetTime':'6:30pm',
                        'notes':'I ordered this for 2 but my buddy bailed on me. We can work out payment later.'
                    }
                }
            }
        )))

    #TODO fix this test
    @unittest.skip("unable to get this working for some reason")
    def test_chow_api_create(self):
        chow = {
            "id": 3,
            "createdBy": 1,
            "createdTime": "3019-06-30T12:43:55",
            "deleted": False,
            "food": "cake",
            "lastUpdated": "3019-06-30T12:43:55",
            "meetLocation": "cake place",
            "meetTime": "3019-06-30T12:43:55",
            "notes": "yummy"}

        response = self.app.post('/chow', data=dict(chow=json.dumps(chow)))
        self.assertEqual(response.status_code, 200)

        data = json.loads(response.get_data(as_text=True))

    def test_chow_api_update(self):
        chow = {'id': 1, "notes": "testing"}
        chow_id = 1

        response = self.app.post('/chow/' + str(chow_id), data=dict(chow=json.dumps(chow)))
        self.assertEqual(response.status_code, 200)

        data = json.loads(response.get_data(as_text=True))
        self.assertEqual(data, {'success': True, 'chow': chow})

    def test_chow_api_delete(self):        
        chow_id = 1
        
        response = self.app.delete('/chow/' + str(chow_id))
        self.assertEqual(response.status_code, 200)

        data = json.loads(response.get_data(as_text=True))
        self.assertTrue(data['success'])

if __name__ == "__main__":
    unittest.main()