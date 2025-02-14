package testcases.api.us.regression;

import com.microsoft.playwright.APIRequestContext;
import org.testng.annotations.Test;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

public class DirectBuyPortal {

    @Test
    public void token() {
        try (Playwright playwright = Playwright.create()) {
            // Create API Request Context
            APIRequestContext requestContext = playwright.request().newContext();

            // Define API endpoint and form data
            String endpoint = "https://c-partner-qa5.cashforcars.com/api/v1/publishers/token";

            // Perform POST request
            APIResponse response = requestContext.post(endpoint, RequestOptions.create()
                    .setHeader("Accept-Encoding", "gzip, deflate, br") // Accept-Encoding header
                    .setHeader("Cookie", "JSESSIONID=2F964F9869C5C08C1C6165CDB9E61A2C") // Cookie header
                    .setHeader("Host", "c-partner-qa5.cashforcars.com") // Host header
                    .setMultipart(FormData.create()
                            .set("grant_type", "password")
                            .set("username", "sowmya@qa-copart-test.com")
                            .set("password", "Test@123")

            ));
            System.out.println(response.status());
            System.out.println(response.text());
        }

    }}
