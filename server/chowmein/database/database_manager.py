from __future__ import print_function

import os
import boto3
import json
import decimal
import time

import database.database_configuration as config

from botocore.exceptions import ClientError
from boto3.dynamodb.conditions import Key, Attr

class DecimalEncoder(json.JSONEncoder):
    """
    DecimalEncoder:
        A helper class found in the AWS docs that converts a DynamoDB item to JSON
    """
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            if o % 1 > 0:
                return float(o)
            else:
                return int(o)
        return super(DecimalEncoder, self).default(o)

class DatabaseManager:
    
    """
    DatabaseManager:
        Singleton wrapper for DatabaseConnection
    """

    instance = None

    @staticmethod
    def getInstance():
        """Get the instance of DatabaseConnection"""
        if DatabaseManager.instance is None:
            
            env = os.getenv('DB_ENV', 'local')
            if env != 'prod' and env != 'local':
                env = 'local'

            DatabaseManager.instance = DatabaseManager.__DatabaseConnection(env)
        return DatabaseManager.instance

    class __DatabaseConnection:
        
        """
        DatabaseConnection:
            Handles interactions with an AWS DynamoDB instance.
        """

        def __init__(self, env):
            self.dynamoDb = boto3.resource('dynamodb', endpoint_url=config.dbenv[env]['endpoint_url'], region_name='ca-central-1')
            self.env = env # used to check permissions with deleting tables

        def get_table(self, table_name):
            """
            Returns a table object for the given table_name.
            *Note: this table might not exist. Use table_exists before get_table.
            """
            return self.dynamoDb.Table(table_name)

        def table_exists(self, table_name):
            """
            Checks if a table with the given name exists.
            Returns true if a table with the given table_name exists, false otherwise,
            """
            return self.get_table(table_name) in self.dynamoDb.tables.all()

        def create_table(self, table_name):
            """
            Creates a table with the given name.
            The table's key will be 'Id' with type 'Number'.
            Returns:
                true if the table was created.
                false if the table existed before or if the table failed to be created.
            """
            
            if self.table_exists(table_name):
                print('Table:', table_name,' already exists!')
                return False
            table = self.dynamoDb.create_table(
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

            return self.table_exists(table_name)

        def delete_table(self, table_name):
            """
            Deletes the table with the given name.

            Returns:
                 0: Successfully deleted the table.
                -1: Not allowed to delete the table.
                -2: Table does not exist.
                -3: Other error.
            """
            if self.env == 'prod':
                print('You can\'t delete a database in prod')
                return -1
            elif not self.table_exists(table_name):
                print('Table:', table_name, 'does not exist!')
                return -2
            else:
                try:
                    print('Deleting table:', table_name)

                    self.get_table(table_name).delete()
                    time.sleep(5) #allow for deletion

                    print('Table:', table_name, 'deleted!')
                    return 0
                except ClientError as e:
                    print('Failed to delete table:', table_name)
                    print('Error:', e.response['Error']['Message'])
                    return -3

        def get_item(self, table_name, key):
            """ Gets the item with the given key from the given table """
            try:
                response = self.get_table(table_name).get_item(
                    Key={
                        'Id': key
                    }
                )
                return response.get('Item')

            except ClientError as e:
                print('Failed to get item:', key)
                print('Error:', e.response['Error']['Message'])

        def put_item(self, table_name, item):
            """
                Create a new item.
                If item already exists (same key), then this will delete and create a new item.
            """

            #This is a hack
            #if item doesn't have an idea, it tries to get the next available id
            #should probably change the id in that case to something we can determine on our own
            if not 'Id' in item:
                item['Id'] = self.get_max_primarykey(table_name)

            try:
                response = self.get_table(table_name).put_item(
                    Item=item
                )
                return response['ResponseMetadata']['HTTPStatusCode'] == 200
            except ClientError as e:
                print('Failed to put item:', item)
                print('Error:', e.response['Error']['Message'])

        def update_item(self, table_name):
            #TODO: Joshua Klassen-implement this function
            return

        #TODO: Joshua Klassen-test this
        def batch_write(self, table_name, items):
            """ Write items in a batch """
            if self.table_exists(table_name):
            
                table = self.get_table(table_name)
                with table.batch_writer() as batch:
                    for item in items:
                        batch.put_item(Item=item)
                return True

        def delete_item(self, table_name, key):
            """ Delete item with the given key from the given table """
            table = self.get_table(table_name)
            response = table.delete_item(
                Key={'Id': key}
            )
            return response['ResponseMetadata']['HTTPStatusCode'] == 200

        def scan(self, table_name):
            """ 
            Scans the entire database 
            This is a costly action, use carefully
            """

            if self.table_exists(table_name):
                response = self.get_table(table_name).scan()

                return response.get('Items')

        def get_max_primarykey(self, table_name):
            table = self.get_table(table_name)

            response = table.scan(
                ProjectionExpression="#id",
                ExpressionAttributeNames={ "#id": "Id", }
            )

            MaxValue = 0
            for item in response['Items']:
                key = item['Id']
                MaxValue = max(key, MaxValue)

            return MaxValue + 1

        def dynamodbItem_to_string(self, item):
            """ Convert an item from .get_item() to a string """
            return json.dumps(item, indent=4, cls=DecimalEncoder)

        def get_item_as_string(self, table_name, key):
            """ Wraps .get_item() to return a string """
            return self.dynamodbItem_to_string(self.get_item(table_name, key))

        #TODO: Joshua Klassen: determine if this should be the default behaviour
        def get_item_as_json(self, table_name, key):
            """ Wraps .get_item() to return a json object """
            return json.loads(self.get_item_as_string(table_name, key))

        #TODO: Joshua Klassen: determine if this should be the default behaviour
        def scan_as_json(self, table_name):
            items = self.scan(table_name)
            response = []
            for item in items:
                response.append(json.loads(json.dumps(item, cls=DecimalEncoder)))
            
            return response