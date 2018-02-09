import boto3
import time

import database_configuration as config

from botocore.exceptions import ClientError

dynamoDb = None
env = ''

def init(environment):
    print('Initializing db...')
    dynamoDb = boto3.resource('dynamodb', endpoint_url=config.dbenv[environment]['endpoint_url'])
    env = environment

def init_decorator(func):
    def decorator(*args, **kwargs):

        if dynamoDb is None:
            init('local')
        
        return func(*args, **kwargs)
    return decorator

@init_decorator
def get_table(table_name):
    return dynamoDb.Table(table_name)

@init_decorator
def get_table(table_name):
    """
    Returns a table object for the given table_name.
    *Note: this table might not exist. Use table_exists before get_table.
    """

    return dynamoDb.Table(table_name)

@init_decorator
def table_exists(table_name):
    """
    Checks if a table with the given name exists.
    Returns true if a table with the given table_name exists, false otherwise,
    """

    return get_table(table_name) in dynamoDb.tables.all()

@init_decorator
def create_table(table_name):
    """
    Creates a table with the given name.
    The table's key will be 'Id' with type 'Number'.
    Returns:
        true if the table was created.
        false if the table existed before or if the table failed to be created.
    """
        
    if table_exists(table_name):
        print(table_name, ' already exists!')
        return False
    table = dynamoDb.create_table(
        TableName=table_name,
        KeySchema=[
            {
                'AttributeName': 'Id',
                'KeyType': 'HASH'
            }
        ],
        AttributeDefinitions=[
            {
                'AttributeName': 'Id',
                'AttributeType': 'N'
            },
        ],
        ProvisionedThroughput={
            'ReadCapacityUnits': config.read_capacity_units,
            'WriteCapacityUnits': config.write_capacity_units
        }
    )

    return table_exists(table_name)

@init_decorator
def delete_table(table_name):
    """
    Deletes the table with the given name.

    Returns:
        +1: Successfully delete the table.
        -1: Cannot delete the table.
        -2: Table does not exist.
        -3: Other error.
    """

    if env == 'prod':
        print('You can\'t delete a database in prod')
        return -1
    elif not table_exists(table_name):
        print(table_name, ' does not exist!')
        return -2
    else:
        try:
            print('Deleting: ', table_name)

            get_table(table_name).delete()
            time.sleep(5) #allow for deletion

            print(table_name, ' deleted!')
            return 1

        except ClientError as e:
            print('Failed to delete table: ', table_name)
            print('Error: ', e.response['Error']['Message'])
            return -3

@init_decorator
def get_item(table_name, key):
    """ Gets the item with the given key from the given table """

    try:
        response = get_table(table_name).get_item(
            Key={
                'Id': key
            }
        )
        return response.get('Item')

    except ClientError as e:
        print('Failed to get item: ', key)
        print('Error: ', e.response['Error']['Message'])

@init_decorator
def put_item(table_name, item):
    """
        Create a new item.
        If item already exists (same key), then this will delete and create a new item.
    """
        
    try:
        response = get_table(table_name).put_item(
            Item=item
        )
        return response['ResponseMetadata']['HTTPStatusCode'] == 200

    except ClientError as e:
        print('Failed to put item: ', item)
        print('Error: ', e.response['Error']['Message'])

@init_decorator
def update_item(table_name):
        #TODO: Joshua Klassen-implement this function
        return

#TODO: Joshua Klassen-test this
@init_decorator
def batch_write(table_name, items):
    if table_exists(table_name):
    
        table = get_table(table_name)
        with table.batch_writer() as batch:
            for item in items:
                batch.put_item(Item=item)
        return True

@init_decorator
def delete_item(table_name, key):
    table = get_table(table_name)
    response = table.delete_item(
        Key={'Id': key}
    )
    return response['ResponseMetadata']['HTTPStatusCode'] == 200

@init_decorator
def scan(table_name):
    if table_exists(table_name):
        response = get_table(table_name).scan()
        
        return response.get('Items')