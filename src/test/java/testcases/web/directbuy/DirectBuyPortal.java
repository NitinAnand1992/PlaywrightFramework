package testcases.web.directbuy;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.awt.*;
import java.util.*;
public class DirectBuyPortal {

        public static void main(String[] args) {
            try (Playwright playwright = Playwright.create()) {

                Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
                int width=(int)screenSize.getWidth();
                int height=(int)screenSize.getHeight();
                System.out.println(width);
                System.out.println(height);
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                BrowserContext  browserContext =browser.newContext(new Browser.NewContextOptions().setViewportSize(width,height));
                Page page=browserContext.newPage();
                page.navigate("https://c-directbuy-qa5.cashforcars.com/login");
                Locator registerbtn=page.locator("text=Register Now");
                registerbtn.click();
                page.pause();
            }
        }
    }

