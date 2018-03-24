import json
 
def test_chow_api_get_all(myApp):
    response = myApp.get('/chow')
    assert response.status_code == 200
    
    data = json.loads(response.get_data(as_text=True))
    assert data == json.loads(json.dumps([
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
]))

def test_chow_api_get_specific(myApp):
    chow_id = 1

    response = myApp.get('/chow/' + str(chow_id))
    assert response.status_code == 200

    data = json.loads(response.get_data(as_text=True))
    assert data == json.loads(json.dumps(
        {
            'food': 'Large Pep. Pizza',
            'meetLocation': 'Dominos down main',
            'lastUpdated':'2018-02-08T17:55:00',
            'id':1,
            'meetTime':'6:30pm',
            'notes':'I ordered this for 2 but my buddy bailed on me. We can work out payment later.'
        }
    ))

def test_chow_api_create(myApp):
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

    response = myApp.post('/chow', data=json.dumps(chow), content_type='application/json')
    assert response.status_code == 200

def test_chow_api_update(myApp):
    chow = {'id': 1, "notes": "testing"}
    chow_id = 1

    response = myApp.post('/chow/' + str(chow_id), data=json.dumps(chow), content_type='application/json')
    assert response.status_code == 200

    data = json.loads(response.get_data(as_text=True))
    assert data == {'success': True, 'chow': chow}

def test_chow_api_delete(myApp):        
    chow_id = 1
    
    response = myApp.delete('/chow/' + str(chow_id))
    assert response.status_code == 200

    data = json.loads(response.get_data(as_text=True))
    assert data['success'] is True