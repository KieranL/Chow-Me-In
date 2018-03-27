import {browser, by, element} from 'protractor';

export class ViewChowsPage {
  clickOnChow() {
    element(by.css('.chow')).click();
  }

  getFirstChow() {
    return element(by.css('.chow'));
  }

  getChowFood() {
    return element(by.id('food'));
  }

  getChowPosterName() {
    return element(by.id('posterName'));
  }

  getChowLastUpdated() {
    return element(by.cssContainingText('span', 'Last updated'));
  }

  getChowLocation() {
    return element(by.cssContainingText('div', 'Location'));
  }

  getChowTime() {
    return element(by.cssContainingText('div', 'Time'));
  }

  getChowNotes() {
    return element(by.id('notes'));
  }

  getChowMeInButton() {
    return element(by.buttonText('Chow Me-In!'));
  }

  getEditButton() {
    return element(by.buttonText('Edit'));
  }

  getDeleteButton() {
    return element(by.buttonText('Delete'));
  }

  searchForFood(food: string) {
    element(by.id('search')).sendKeys(food);
  }

  getByFood(food: string) {
    return element(by.cssContainingText('#food', food));
  }
}
