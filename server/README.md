# **Server**

* [How to setup server locally](#how-to-setup-server-locally)
  * [Installation](#installation)
  * [Local database setup](#local-database-setup)
  * [Running the server](#running-the-server)
* [Data models](#data-models)
  * [User](#user)
  * [Chow](#chow)
* [API](#api)
  * [User Endpoint](#user-endpoint)
    * [Getting a user](#getting-a-user)
  * [Chow Endpoint](#chow-endpoint)
    * [Creating a chow](#creating-a-chow)
    * [Getting all chows](#getting-all-chows)
    * [Getting a chow](#getting-a-chow)
    * [Updating a chow](#updating-a-chow)
    * [Deleting a chow](#deleting-a-chow)
* [Unit Testing](#unit-testing)

## **How to setup server locally**
---
### **Installation**

`pip install -r requirements.txt`

### ***Local database Setup***
In order for the server to run locally, you will need a local database.
To setup one up, see [here](chowmein/database/README.md) 

### **Running the server**
Once you have a local database up and running, you can start the server by running:

`python server.py`

Alternatively, you can start the server by running:

`export FLASK_APP=server.py`

`python -m flask run --host=0.0.0.0`

This will start server with an option to listen on all interfaces on the system

## **Data Models**
---
Currently we have 2 data models, a `user` and a `chow`.
A `chow` means an offer to share food (see [here](../README.md))

### **User**
A `user` is a representation of someone using Chow Me-In.
A *user* can post *chows*, and accept *chows*.

A `user` is pulled from AWS Cognito, use the Boto3 [CognitoIdentityProvider](http://boto3.readthedocs.io/en/latest/reference/services/cognito-idp.html#CognitoIdentityProvider.Client.get_user) client

### **Chow**
A `chow` means an offer to share food (see [here](../README.md))

A `chow` consists of:
```
{
  id:           a unique integer used to identify a chow,
  createdBy:    an integer that corresponds to a user,
  createdTime:  the time the chow was created in the format: "yyyy-mm-ddThh:mm:ss",
  deleted:      used for soft deletion of chows,
  food:         a description of the food that is offered,
  lastUpdated:  the last time the chow was updated in the format: "yyyy-mm-ddThh:mm:ss",
  meetLocation: a description of where to meet in person,
  meetTime:     the time to meet in the format: "yyyy-mm-ddThh:mm:ss",
  notes:        any additional notes the poster of the chow may want to share,
  posterUser:   the username of the user who posted this chow,
  posterName:   the name of the user who posted this chow,
  posterEmail:  the email of the user who posted this chow,
  posterPhone:  the phone number of the user who posted this chow
}
```

## **API**
---
Our API consists of various endpoints of which can be hit to perform CRUD operations on our database.

Currently our 2 endpoints are:
- /user
- /chow

## **User Endpoint**
For more information on the `user` object, see [here](#user)

### **Getting a user**
Gets the `user` belonging to a given `access_token`
```
Endpoint: /user/token/{access_token}
Method: GET
Returns: {
  "success":  True/False,
  "User":     User object from boto3
}
```
### **Chow Endpoint**
For more information on the `chow` object, see [here](#chow)

### **Creating a chow**
Creates a new `chow` in the system
```
Endpoint: /chow
Method: POST
Body: {
  id: -1 (optional)
  ...
}
Returns: {
  "success":  True/False
}  
```

### **Getting all chows**
Gets all `chow`s in the system
```
Endpoint: /chow
Method: GET
Returns: {
  ...
  a list of all chows
  ...
}
```

### **Getting a chow**
Gets the `chow` with the given `id`
```
Endpoint: /chow/{id}
Method: GET
Returns: {
  chow
}
```

### **Updating a chow**
Updates the `chow` with the given `id`
```
Endpoint: /chow/{id}
Method: POST
Body: {
  any key/value listed here will overwrite the existing value
}
Returns: {
  "success":  True/False,
  "Chow":     the updated chow
}  
```

### **deleting a chow**
Deletes the `chow` with the given `id`
```
Endpoint: /chow/{id}
Method: DELETE
Returns: {
  "success": True/False
}
```

## Unit Testing
---

Make sure `DynamoDB Local` is running, see [here](chowmein/database/README.md).

Make sure `Pytest` is installed.

To run all backend unit tests, inside server/chowmein run:

`python -m pytest`

To run a specific backend unit test:

`python -m pytest -k <test_name>.py`
