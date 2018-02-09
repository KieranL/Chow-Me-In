import boto3
import time

import database_configuration as config

from botocore.exceptions import ClientError

class DatabaseManager:
    """
    DatabaseManager:
        Handles interactions with an AWS DynamoDB instance.
    """

    def __init__(self, env):
        self.dynamoDb = boto3.resource('dynamodb', endpoint_url=config.dbenv[env]['endpoint_url'])
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
            print(table_name, ' already exists!')
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
            +1: Successfully delete the table.
            -1: Cannot delete the table.
            -2: Table does not exist.
            -3: Other error.
        """
        if self.env == 'prod':
            print('You can\'t delete a database in prod')
            return -1
        elif not self.table_exists(table_name):
            print(table_name, ' does not exist!')
            return -2
        else:
            try:
                print('Deleting: ', table_name)

                self.get_table(table_name).delete()
                time.sleep(5) #allow for deletion

                print(table_name, ' deleted!')
                return 1
            except ClientError as e:
                print('Failed to delete table: ', table_name)
                print('Error: ', e.response['Error']['Message'])
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
            print('Failed to get item: ', key)
            print('Error: ', e.response['Error']['Message'])

    def put_item(self, table_name, item):
        """
            Create a new item.
            If item already exists (same key), then this will delete and create a new item.
        """
        try:
            response = self.get_table(table_name).put_item(
                Item=item
            )
            return response['ResponseMetadata']['HTTPStatusCode'] == 200
        except ClientError as e:
            print('Failed to put item: ', item)
            print('Error: ', e.response['Error']['Message'])

    def update_item(self, table_name):
        #TODO: Joshua Klassen-implement this function
        return

    #TODO: Joshua Klassen-test this
    def batch_write(self, table_name, items):
        if self.table_exists(table_name):
        
            table = self.get_table(table_name)
            with table.batch_writer() as batch:
                for item in items:
                    batch.put_item(Item=item)
            return True

    def delete_item(self, table_name, key):
        table = self.get_table(table_name)
        response = table.delete_item(
            Key={'Id': key}
        )
        return response['ResponseMetadata']['HTTPStatusCode'] == 200

    def scan(self, table_name):
        if self.table_exists(table_name):
            response = self.get_table(table_name).scan()

            return response.get('Items')