import { browser, by, element } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }

  getTitle() {
    return browser.getTitle();
  }

  getViewChowsButton() {
    return element(by.cssContainingText('a', 'View Chows'));
  }

  getCurrentUrl() {
    return browser.getCurrentUrl();
  }
}
