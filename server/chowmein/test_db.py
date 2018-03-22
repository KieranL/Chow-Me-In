import json

def test_create_chow(myDynamodb):
    chow = myDynamodb.get_item_as_json('ChowTest', 1)
    assert chow is None

    myDynamodb.put_item('ChowTest', {'food':'strawberry white chocolate mousse cake'})
    chow = myDynamodb.get_item_as_json('ChowTest', 1)
    assert chow is not None
    assert chow['id'] == 1

def test_get_chow(myDynamodb):
    numChows = 2
    myDynamodb.put_item('ChowTest', {'food':'caramel rum apple cake'})
    myDynamodb.put_item('ChowTest', {'food':'cherry tart'})

    chow = myDynamodb.get_item_as_json('ChowTest', 1)
    assert chow['id'] == 1
    assert chow['food'] == 'caramel rum apple cake'

    # test get all chows
    chows = myDynamodb.scan_as_json('ChowTest')
    assert len(chows) == numChows

def test_update_chow(myDynamodb):
    myDynamodb.put_item('ChowTest', {'food':'salted caramel dark chocolate cheesecake'})
    chow = myDynamodb.get_item_as_json('ChowTest', 1)
    assert chow['food'] == 'salted caramel dark chocolate cheesecake'

    chow['food'] = 'oreo cheesecake'
    myDynamodb.put_item('ChowTest', chow)
    chow = myDynamodb.get_item_as_json('ChowTest', 1)
    assert chow['food'] == 'oreo cheesecake'

def test_delete_chow(myDynamodb):
    myDynamodb.put_item('ChowTest', {'food':'stale apple pie'})
    chow = myDynamodb.get_item_as_json('ChowTest', 1)
    assert chow is not None

    myDynamodb.delete_item('ChowTest', 1)
    chow = myDynamodb.get_item_as_json('ChowTest', 1)
    assert chow is None
