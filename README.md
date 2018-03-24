# **COMP4350 Winter 2018 Group Project**
Translator's note: "Chow" means an offer to share food

* [What is Chow Me-In?](#what-is-chow-me-in)
    * [Motiviation](#motivation)
    * [Our goal](#our-goal)
* [Technology](#technology)
    * [Our tech stack](#our-teck-stack)
    * [Where is the production environment](#where-is-the-production-environment)
    * [Login](#login)
    * [How to setup a local environment](#how-to-setup-a-local-environment)
* [Future](#future)

## **What is Chow Me-In?**
Chow Me-In is a food-sharing service that people can use to find local consumers that have an
excess of food from a restaurant willing to be shared. From here a consumer may bid or buy the
excess food from the seller where the consumer and seller are geographically close to one
another

### **Motivation**
We are building this software because it will help to reduce the amount of uneaten food locally
and to allow people to meet new people through the sharing of food. A big part of the motivation
for Chow Me-In is that no similar product currently exists. While there are other food-sharing
apps out there, ours is the only one catering specifically to food ordered at restaurants. Using
Chow Me-In will reduce the overall cost of food for users.

### **Our Goal**
Chow Me-In will be considered successful if users’ monthly budget spent on food before using
the app and after using the app is reduced by at least 10%.

## **Technology**

### **Our teck stack**

- Mobile client: Android (Java)
- Web client: Angular 5 (JavaScript ES6)
- Back end: Flask (Python 3)
- Infrastructure: AWS
- Database: DynamoDB (NoSQL)
- Continuous integration: CircleCI
- Login: AWS Cognito

To see a diagram of our system archiecture, see [here](doc/chowmein%20system%20architecture%20diagram.pdf)


### **Where is the production environment?**

You can hit our API at:

https://api.chowme-in.com

sample API call:
https://api.chowme-in.com/chow

You can view our web application at:

https://chowme-in.com


### **Login**

We are using AWS's Cognito service for login.
You can create an account with us, or login with Google!

*NOTE*

```
We do not store the account credentials, that is all handled by Cognito.

Cognito stores your login info, and when a user tries to login, Cognito handles the verification. If the login is successful then Cognito provides an access token that is stored in Session Storage, and is used to validate everything.
This token only lasts 1 hour, so you will need to login again after the hour.
```

### **How to setup a local environment**

While our production environment is entirely in AWS, you can setup your own local environment.

To do so see:

[Web](web/README.md)

[Mobile](/mobile/README.md)

[Server](server/README.md)

[Database](/server/chowmein/database/README.md)

These components all work off of each other -- so in order to get the server working locally you will need to have the database setup properly, in order for the web app to run you will need to have the server up.

*NOTE*

```
Since login has been added to the project, in order to see your account/create chows/chow in locally, you have to login.
However, the link to login takes you to prod, meaning that after you logged in you are no longer in your local environment.
In order to login in your local environment, do the following:
Locally click the login link and log in.
Open up the dev tools and go to the Application tab, and go to Session Storage.
You should see a few key-value pairs, one being "access_token".
Copy the value of that key-value pair to your clipboard.
Return to your local site, and in the dev tools navigate to the Application tab, and back to Session Storage.
Re-create the "access_token" key value pair here (the value is copied to your clipboard, just need to make the rest).
After this is done, refresh the page and you should be logged in!

This access_token only lasts 1 hour, so after that hour is up you will need to repeat these steps.

There are plans to make this a less "hacky" process, but for the time being this has to be done.

After you are logged in we grab the access token (in the Chrome Dev tools it can be found in 'Application->Session Storage'
```

## **Future**
Stay tuned for more features coming soon...
