import {browser, by, element} from 'protractor';

export class ViewChowsPage {
  clickOnChow() {
    element(by.css('.chow')).click();
  }

  getFirstChow() {
    return element(by.css('.chow'));
  }

  async checkChowListInfo() {
    await expect(element(by.id('food')).isDisplayed()).toBeTruthy();
    await expect(element(by.id('posterName')).isDisplayed()).toBeTruthy();
    await expect(element(by.cssContainingText('span', 'Last updated')).isDisplayed()).toBeTruthy();
    await expect(element(by.cssContainingText('div', 'Location')).isDisplayed()).toBeTruthy();
    await expect(element(by.cssContainingText('div', 'Time')).isDisplayed()).toBeTruthy();
  }

  async checkChowDetailInfo() {
    //Text
    await expect(element(by.id('food')).isDisplayed()).toBeTruthy();
    await expect(element(by.id('posterName')).isDisplayed()).toBeTruthy();
    await expect(element(by.cssContainingText('span', 'Last updated')).isDisplayed()).toBeTruthy();
    await expect(element(by.cssContainingText('div', 'Location')).isDisplayed()).toBeTruthy();
    await expect(element(by.cssContainingText('div', 'Time')).isDisplayed()).toBeTruthy();
    await expect(element(by.id('notes')).isDisplayed()).toBeTruthy();

    //Buttons
    await expect(element(by.buttonText('Chow Me-In!')).isDisplayed()).toBeTruthy();
    await expect(element(by.buttonText('Edit')).isDisplayed()).toBeTruthy();
    await expect(element(by.buttonText('Delete')).isDisplayed()).toBeTruthy();
  }

  async checkButtonsDisabled() {
    await expect(element(by.buttonText('Chow Me-In!')).isEnabled()).toBeFalsy();
    await expect(element(by.buttonText('Edit')).isEnabled()).toBeFalsy();
    await expect(element(by.buttonText('Delete')).isEnabled()).toBeFalsy();
  }

  async checkSearch() {
    let food = await element(by.id('food')).getText();
    element(by.id('search')).sendKeys(food);
    expect(element(by.cssContainingText('#food', food)).isDisplayed()).toBeTruthy();
  }
}
