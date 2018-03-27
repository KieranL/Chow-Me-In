import { ViewChowsPage } from './view-chows.po';

describe('View Chows page', () => {
  let page: ViewChowsPage;

  beforeEach(() => {
    page = new ViewChowsPage();
  });

  it ('should show appropriate information in chows list',async function() {
    if(page.getFirstChow()) {
      await expect(page.getChowFood().isDisplayed()).toBeTruthy();
      await expect(page.getChowPosterName().isDisplayed()).toBeTruthy();
      await expect(page.getChowLastUpdated().isDisplayed()).toBeTruthy();
      await expect(page.getChowLocation().isDisplayed()).toBeTruthy();
      await expect(page.getChowTime().isDisplayed()).toBeTruthy();
    }
  });

  it ('should show chow details when click on chow',async function() {
    if(page.getFirstChow()) {
      page.clickOnChow();

      //Text
      await expect(page.getChowFood().isDisplayed()).toBeTruthy();
      await expect(page.getChowPosterName().isDisplayed()).toBeTruthy();
      await expect(page.getChowLastUpdated().isDisplayed()).toBeTruthy();
      await expect(page.getChowLocation().isDisplayed()).toBeTruthy();
      await expect(page.getChowTime().isDisplayed()).toBeTruthy();
      await expect(page.getChowNotes().isDisplayed()).toBeTruthy();

      //Buttons
      await expect(page.getChowMeInButton().isDisplayed()).toBeTruthy();
      await expect(page.getEditButton().isDisplayed()).toBeTruthy();
      await expect(page.getDeleteButton().isDisplayed()).toBeTruthy();
    }
  });

  it ('should disable buttons when not logged in',async function() {
    if(page.getFirstChow()) {
      page.clickOnChow();
      await expect(page.getChowMeInButton().isEnabled()).toBeFalsy();
      await expect(page.getEditButton().isEnabled()).toBeFalsy();
      await expect(page.getDeleteButton().isEnabled()).toBeFalsy();
    }
  });

  it ('should be able to search by food name',async function() {
    if(page.getFirstChow()) {
      let food = await page.getChowFood().getText();
      page.searchForFood(food);
      await expect(page.getByFood(food).isDisplayed()).toBeTruthy();
    }
  });
});
