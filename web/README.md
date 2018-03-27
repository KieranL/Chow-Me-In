# Web

To see a diagram of our web client architecture, see [here](../doc/chowmein%20web%20client%20architecture%20diagram.pdf)

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.6.4.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.

## Running system tests

To run the system tests please first make sure that your local [database](https://github.com/KieranL/Chow-Me-In/blob/master/server/chowmein/database/README.md) and [server](https://github.com/KieranL/Chow-Me-In/blob/master/server/README.md#how-to-setup-server-locally) are running.

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

*NOTE*

```
We had to limit the number of system tests we could write for the web locally because a lot of functionality relies on the user being signed in which in our app makes use of the external service Cognito. We cannot test this locally especially due to the hacky way we have to get login to work locally (which is not possible with automated UI tests). So currently our system tests for web tests the functionalities that are available everyone but not logged in users, such as viewing chows. We also tried connecting Protractor to the prod URL but couldn't get username and password input working in the Cognito sign in page.
```

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
