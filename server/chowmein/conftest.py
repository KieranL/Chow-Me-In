import pytest
from server import application as app
from database.database_manager import DatabaseManager as database
from dbinit import initialize_local_database as init_db

# conftest allows multiple test files to share fixture functions
# fixture functions do not need to be imported, pytest automatically discovers them

@pytest.fixture
def myApp():
    app.config['TESTING'] = True
    app.config['WTF_CSRF_ENABLED'] = False
    app.config['DEBUG'] = False

    #not sure if this will work or not
    app.config['DYNAMO_ENABLE_LOCAL'] = True
    app.config['DYNAMO_LOCAL_HOST'] = 'localhost'
    app.config['DYNAMO_LOCAL_PORT'] = 8000

    myApp = app.test_client()

    # Disable sending emails during unit testing
    assert app.debug == False
    init_db()
    return myApp

@pytest.fixture
def myDynamodb(request):
    # Create table for testing
    db = database.getInstance()
    db.create_table('ChowTest')
    def remove_table():
        print('delete table ChowTest')
        db.delete_table('ChowTest')
    request.addfinalizer(remove_table)
    return db
