import { ViewChowsPage } from './view-chows.po';

describe('View chows page', () => {
  let page: ViewChowsPage;

  beforeEach(() => {
    page = new ViewChowsPage();
  });

  it ('should show appropriate information in chows list',async function() {
    if(page.getFirstChow()) {
      await page.checkChowListInfo();
    }
  });

  it ('should show chow details when click on chow',async function() {
    if (page.getFirstChow()) {
      page.clickOnChow();
      await page.checkChowDetailInfo();
    }
  });

  it ('should disable buttons when not logged in',async function() {
    if (page.getFirstChow()) {
      page.clickOnChow();
      await page.checkButtonsDisabled();
    }
  });

  it ('should be able to search by food name',async function() {
    if (page.getFirstChow()) {
      await page.checkSearch();
    }
  });
});
