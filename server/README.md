# Server

## Server Setup

`pip install -r requirements.txt`

## Local DB Setup

Guide in the database [README](https://github.com/KieranL/Chow-Me-In/tree/master/server/chowmein/database)

## Running the server

`python server.py`

## JSON Schemas

### Chow
```javascript
{
  "chow": {
     "id": 2,
     "createdBy": 420,
     "createdTime": "yyyy-mm-ddThh:mm:ss",
     "deleted": false,
     "food": "",
     "lastUpdated": "yyyy-mm-ddThh:mm:ss",
     "meetLocation": "",
     "meetTime": "yyyy-mm-ddThh:mm:ss",
     "notes": ""
  }
}
```

## Unit Testing

### DB unit tests

Make sure `DynamoDB Local` is running, database README has instructions on set up

`python db_tests.py`
