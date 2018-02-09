import boto3
import time

import database_configuration as config

from botocore.exceptions import ClientError

class DatabaseManager:

    def __init__(self, env):
        self.dynamoDb = boto3.resource('dynamodb', endpoint_url=config.dbenv[env]['endpoint_url'])
        self.env = env

    def get_table(self, table_name):
        return self.dynamoDb.Table(table_name)

    def table_exists(self, table_name):
        return self.get_table(table_name) in self.dynamoDb.tables.all()

    def create_table(self, table_name, read_capacity=5, write_capacity=5):
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
                'ReadCapacityUnits': read_capacity,
                'WriteCapacityUnits': write_capacity
            }
        )

        return self.table_exists(table_name)

    def delete_table(self, table_name):
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
        try:
            response = self.get_table(table_name).get_item(
                Key={
                    'Id': key
                }
            )
            return response['Item']
        except ClientError as e:
            print('Failed to get item: ', key)
            print('Error: ', e.response['Error']['Message'])

    def put_item(self, table_name, item):
        """
            Create a new item
            If item already exists (same key), then this will delete and create a new item
        """
        try:
            response = self.get_table(table_name).put_item(
                Item=item
            )
            return response['ResponseMetadata']['HTTPStatusCode'] == 200
        except ClientError as e:
            print('Failed to put item: ', item)
            print('Error: ', e.response['Error']['Message'])

#### TESTS ####

db = DatabaseManager('prod')

user_table = 'User'
chow_table = 'Chow'

if not db.table_exists(user_table):
    db.create_table(user_table)
    db.put_item(user_table, {'Id': 420})

if not db.table_exists(chow_table):
    db.create_table(chow_table)
    db.put_item(chow_table, {'Id': 1})

#print('Creating table test')
#print(db.create_table('Josh_test'))

#should fail
#db.delete_table('Josh_test')

#should succeed
print(db.table_exists(chow_table))

print(db.get_item('Chow', 1))
print(db.get_item('User', 420))

#print(db.put_item('User', {"FName":"Bob"}))

#print(db.get_table('Josh_test'))

#db.create_table('Josh_test')

#db.delete_table('Josh_test')
