# **COMP4350 Winter 2018 Group Project**
Translator's note: "Chow" means an offer to share food

* [What is Chow Me-In?](#what-is-chow-me-in)
    * [Motiviation](#motivation)
    * [Our goal](#our-goal)
* [Technology](#technology)
    * [Our tech stack](#our-teck-stack)
    * [Where is the production environment](#where-is-the-production-environment)
    * [How to setup a local environment](#how-to-setup-a-local-environment)
* [Future](#future)

## **What is Chow Me-In?**
---
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
---

### **Our teck stack**

- Mobile client: Android (Java)
- Web client: Angular 5 (JavaScript ES6)
- Back end: Flask (Python)
- Infrastructure: AWS
- Database: DynamoDB (NoSQL)

### **Where is the production environment?**

You can hit our API at:

https://api.chowme-in.com

sample API call:
https://api.chowme-in.com/chow

You can view our web application at:

https://chowme-in.com


### **How to setup a local environment**

While our production environment is entirely in AWS, you can setup your own local environment.

To do so see:

[Web](web/README.md)

[Server](server/README.md)

[Database](/server/chowmein/database/README.md)

These components all work off of each other -- so in order to get the server working locally you will need to have the database setup properly, in order for the web app to run you will need to have the server up.

## **Future**
---
Stay tuned for more features coming soon...