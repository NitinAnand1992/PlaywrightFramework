package testcases.web.parterportal;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.Test;

public class PartnerPortal {

@Test
public void intialize_browser()
{

    Playwright playwright=Playwright.create();

    BrowserType.LaunchOptions lp=new BrowserType.LaunchOptions();
    lp.setChannel("msedge");
    lp.setHeadless(false);
    Browser browser =playwright.chromium().launch(lp);
    Page page=browser.newPage();
    page.navigate("https://c-directbuy-qa4.cashforcars.com/login");
    String title=page.title();
    System.out.println(title);
    String url=page.url();
    System.out.println("url is "+url);

    browser.close();
    playwright.close();
}

}
