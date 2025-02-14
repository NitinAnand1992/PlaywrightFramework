package testcases.web.instaquote;
import Utility.TracingUtils;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;
import java.util.regex.Pattern;

public class Instaquote {




        public static void main(String[] args) {
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                BrowserContext context = browser.newContext();

                TracingUtils.startTracing(context);

                Page page = context.newPage();
                page.navigate("https://c-services-qa5.copart.com/instaquote-ws/instaquote/#/steps/step1");
                page.getByLabel("search").click();
                page.getByLabel("search").fill("2007");
                page.getByLabel("2007").click();
                page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^My Car$"))).click();
                page.getByLabel("search").click();
                page.getByLabel("search").fill("to");
                page.getByText("TOYOTA", new Page.GetByTextOptions().setExact(true)).click();
                page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^My Car$"))).click();
                page.getByLabel("CAMRY", new Page.GetByLabelOptions().setExact(true)).click();
                page.getByLabel("LE", new Page.GetByLabelOptions().setExact(true)).click();
                page.locator("div").filter(new Locator.FilterOptions().setHasText("Loading...")).first().click();
                page.getByRole(AriaRole.TEXTBOX).fill("1321321313");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
                page.getByLabel("I have a clean title").click();
                page.getByPlaceholder("Please enter the 5-digit ZIP").click();
                page.getByPlaceholder("Please enter the 5-digit ZIP").fill("45424");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
                page.getByLabel("Does not start").click();
                page.locator("#pn_id_16").click();
                page.locator("form").filter(new Locator.FilterOptions().setHasText("Enter your vehicle's")).getByRole(AriaRole.TEXTBOX).click();
                page.locator("form").filter(new Locator.FilterOptions().setHasText("Enter your vehicle's")).getByRole(AriaRole.TEXTBOX).fill("999");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes")).click();
                page.getByLabel("Yes").click();
                page.getByLabel("Yes").click();
                page.getByText("Front", new Page.GetByTextOptions().setExact(true)).click();
                page.locator("#path3515-45").click();
                page.getByText("Rear", new Page.GetByTextOptions().setExact(true)).click();
                page.getByText("Left", new Page.GetByTextOptions().setExact(true)).click();
                page.getByText("Right", new Page.GetByTextOptions().setExact(true)).click();
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
                page.getByLabel("No").click();
                page.getByLabel("None").click();
                page.getByLabel("No").click();
                page.getByPlaceholder("Enter Email").click();
                page.getByPlaceholder("Enter Email").fill("testing@qa-copart-test.com");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get Offer")).click();
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept offer now")).click();
                page.getByRole(AriaRole.TEXTBOX).first().click();
                page.getByRole(AriaRole.TEXTBOX).first().fill("test");
                page.getByRole(AriaRole.TEXTBOX).first().press("Tab");
                page.getByRole(AriaRole.TEXTBOX).nth(1).fill("piv");
                page.getByRole(AriaRole.TEXTBOX).nth(4).click();
                page.getByRole(AriaRole.TEXTBOX).nth(4).fill("32847927489273498");
                page.getByLabel("By accepting this offer you").check();
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
                page.locator("div").filter(new Locator.FilterOptions().setHasText("Loading...")).first().click();
                page.getByRole(AriaRole.TEXTBOX).first().fill("test");
                page.getByRole(AriaRole.TEXTBOX).first().press("Tab");
                page.getByRole(AriaRole.TEXTBOX).nth(1).fill("test");
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
                TracingUtils.stopTracing(context);
            }
        }
    }


