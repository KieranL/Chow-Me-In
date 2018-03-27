import { AppPage } from './app.po';

describe('web App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should navigate to right pages', async function() {
    await page.getViewChowsButton().click();
    await expect(page.getCurrentUrl()).toContain('/chow-list');
  });
});
