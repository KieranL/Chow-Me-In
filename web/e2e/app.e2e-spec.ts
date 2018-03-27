import { AppPage } from './app.po';

describe('web App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should have a title', async function() {
    await page.navigateTo();
    expect(page.getTitle()).toEqual('Chow Me-In');
  });
});
