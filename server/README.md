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
    * [Creating a user](#creating-a-user)
    * [Getting a user](#getting-a-user)
    * [Updating a user](#updating-a-user)
    * [Deleting a user](#deleting-a-user)
  * [Chow Endpoint](#chow-endpoint)
    * [Creating a chow](#creating-a-chow)
    * [Getting all chows](#getting-all-chows)
    * [Getting a chow](#getting-a-chow)
    * [Updating a chow](#updating-a-chow)
    * [Deleting a chow](#deleting-a-chow)
* [Unit Testing](#unit-testing)
  * [Database unit tests](#database-unit-tests)

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

## **Data Models**
---
Currently we have 2 data models, a `user` and a `chow`.
A `chow` means an offer to share food (see [here](../README.md))

### **User**
A `user` is a representation of someone using Chow Me-In.
A *user* can post *chows*, and accept *chows*.

A `user` consists of:
```
{
  id:     a unique integer used to identify a user,
  name:  the  name of the user
}
```
More information will be added to this model in the future.

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

### **Creating a user**
Creates a new `user` in the system
```
Endpoint: /user
Method: POST
Body: {
  user: {
    ...
  }
}
Returns: {
  "success":  True/False,
  "User":     user #Note this is the same user that was passed in the body
}  
```

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
### **Updating a user**
Updates the `user` with the given `id`
```
Endpoint: /user/{id}
Method: POST
Body: {
  user: {
    ...
    any key/value listed here will overwrite the existing value
    ...
  }
}
Returns: {
  "success":  True/False,
  "User":     the updated user
}  
```

### **Deleting a user**
Deletes the `user` with the given `id`
```
Endpoint: /user/{id}
Method: DELETE
Returns: {
  "success": True/False
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
### Database unit tests

Make sure `DynamoDB Local` is running, see [here](chowmein/database/README.md).
Then run:

`python db_tests.py`
`python api_tests.py`