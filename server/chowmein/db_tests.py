import unittest
import json

from server import application as app
from database.database_manager import DatabaseManager as database

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

        # Create table for testing
        self.db = database.getInstance()
        tables = ['UserTest', 'ChowTest']
        for table in tables:
            if not self.db.table_exists(table):
                self.db.create_table(table)

    # executed after each test
    def tearDown(self):
        # Remove test data
        tables = ['UserTest', 'ChowTest']
        for table in tables:
            self.db.delete_table(table)
 
 
###############
#### tests ####
###############

    def test_create_chow(self):
        chow = self.db.get_item_as_json('ChowTest', 1)
        self.assertIsNone(chow, msg='table should not contain any item')

        self.db.put_item('ChowTest', {'food':'strawberry white chocolate mousse cake'})
        chow = self.db.get_item_as_json('ChowTest', 1)
        self.assertIsNotNone(chow, msg='table should not be empty')
        self.assertEqual(chow['id'], 1, msg='id does not match')

    def test_get_chow(self):
        numChows = 2
        self.db.put_item('ChowTest', {'food':'caramel rum apple cake'})
        self.db.put_item('ChowTest', {'food':'cherry tart'})

        chow = self.db.get_item_as_json('ChowTest', 1)
        self.assertEqual(chow['id'], 1, msg='id does not match')
        self.assertEqual(chow['food'],'caramel rum apple cake', msg='unmatched food name')

        # test get all chows
        chows = self.db.scan_as_json('ChowTest')
        self.assertEqual(len(chows), numChows)

    def test_update_chow(self):
        self.db.put_item('ChowTest', {'food':'salted caramel dark chocolate cheesecake'})
        chow = self.db.get_item_as_json('ChowTest', 1)
        self.assertEqual(chow['food'],'salted caramel dark chocolate cheesecake', msg='unmatched food name')

        chow['food'] = 'oreo cheesecake'
        self.db.put_item('ChowTest', chow)
        chow = self.db.get_item_as_json('ChowTest', 1)
        self.assertEqual(chow['food'],'oreo cheesecake', msg='unmatched food name')

    def test_delete_chow(self):
        self.db.put_item('ChowTest', {'food':'stale apple pie'})
        chow = self.db.get_item_as_json('ChowTest', 1)
        self.assertIsNotNone(chow, msg='table should not be empty')

        self.db.delete_item('ChowTest', 1)
        chow = self.db.get_item_as_json('ChowTest', 1)
        self.assertIsNone(chow, msg='table should not contain any item')

if __name__ == "__main__":
    unittest.main()