from __future__ import print_function
from database.database_manager import DatabaseManager as database

def initialize_local_database(require_db_confirm = False):
    
    #map raw_input (python2) to input (python3)
    try:
        input = raw_input
    except NameError:
        pass

    tables = ['User', 'Chow']

    users = [

        {
            'fName': 'Snoop',
            'lName': 'Dogg'
        },
        {
            'fName': 'Dat',
            'lName': 'Boi'
        },
        {
            'fName': 'Mr',
            'lName': 'Raccoon'
        },
    ]

    chows = [
        {
            'food': 'Large Pep. Pizza',
            'meetLocation': 'Dominos down main',
            'meetTime':'2999-02-08T17:55:00',
            'lastUpdated':'2018-02-08T17:55:00',
            'notes':'I ordered this for 2 but my buddy bailed on me. We can work out payment later.'
        },
        {
            'food': 'Trash',
            'meetLocation': 'garbage can behind Franklin Bristow\'s place.',
            'meetTime':'1234-02-08T01:55:00',
            'lastUpdated':'2018-02-08T01:55:00',
            'notes':'Calling all raccoons, let\'s eat!'
        },
    ]

    if(require_db_confirm):
        print('Make sure DynamoDB Local is running continuing!')
        input("Press Enter to continue...")

    db = database.getInstance()

    print('Setting up tables...')
    for table in tables:

        #if table already exists - delete it
        if db.table_exists(table):
            db.delete_table(table)
            
        if db.create_table(table):
            print('Table:',table,'was created!')
    print('Tables were successfully setup!')

    print('Populating users...')
    for user in users:
        db.put_item('User', user)
    print('Done!')

    print('Populating chows...')
    for chow in chows:
        db.put_item('Chow', chow)
    print('Done!')

    print('Local db setup complete!')

if __name__ == "__main__":
    initialize_local_database(True)