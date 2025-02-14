package testcases.web.directbuy;

import com.microsoft.playwright.*;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Testing {


@Test
public void initialize_browser() {

    Playwright playwright = Playwright.create();
    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    Page page=browser.newPage();
   // page.navigate("https://c-services-qa5.copart.com/instaquote-ws/instaquote/index.html");
    page.navigate("https://www.orangehrm.com/");
    //page.locator("text= Search by VIN number ").click();
    Locator links=page.locator("text=Privacy Policy");
    for(int i=0;i<links.count();i++)
    {
        System.out.println(links.nth(i).textContent());
    }
    page.pause();
}

}
