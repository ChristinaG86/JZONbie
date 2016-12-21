import { JzonbiePage } from './app.po';

describe('jzonbie App', function() {
  let page: JzonbiePage;

  beforeEach(() => {
    page = new JzonbiePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
