package testcases.api.us.regression;

import Utility.CommonMethods;
import Utility.apiutils.token.TokenGenerationforPartnerApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.qameta.allure.*;
import testcases.api.us.pojo.Credentials;
import testcases.api.us.pojo.SellerInfo;

public class PartnerApi {

Playwright playwright;
APIRequest request;
APIRequestContext requestContext;
String token;
TokenGenerationforPartnerApi tk=new TokenGenerationforPartnerApi();
private String apikey;
private String leadId;
String url;

String uuid1;
SellerInfo sf=new SellerInfo();



@BeforeSuite
public  void setup() throws IOException {
    playwright=Playwright.create();
    request=playwright.request();
    requestContext=request.newContext();
     apikey=tk.generateapikey();
}

@AfterSuite
public void tearDown() {
        if (playwright != null) {
            playwright.close();
        }
    }


@Description("Test Case to validate the instaquote offer amount creation with VIN")
@Story("Sanity for Partner api5" +
        "")
@Severity(SeverityLevel.CRITICAL)
@Test(groups = {"US_PartnerAPI"},enabled = false)
public void instaquote_offer_with_VIN() throws IOException {

    url=CommonMethods.jsonParse("instantoffer");
    System.out.println(url);
    UUID uuid = UUID.randomUUID();
    uuid1=uuid.toString();
    System.out.println(uuid1);
    sf.setVin(tk.randomVinGenerator());
    System.out.println(tk.randomVinGenerator());
    sf.setPhoneNumber(CommonMethods.create_random_phone_number());
    sf.setLoginEmail(CommonMethods.create_random_email());
    String path="./src/main/java/data/API/Payload/US/Partner/InstaquoteWithVin.json";
    System.out.println(apikey);
    HashMap<String,String> updatejson=new HashMap<>();
    updatejson.put("phoneNumber",sf.getPhoneNumber());
    updatejson.put("email",sf.getLoginEmail());
    updatejson.put("vin",sf.getVin());
    String updatedJson=CommonMethods.getUpdatedPayload(path,updatejson);
    Allure.step("Validate json payload for instaquote with vin");
    Allure.addAttachment("Payload used for creating instaquote with VIN ",updatedJson);
    Allure.step("Url used");
    Allure.addAttachment("Url used is ",url);
    APIResponse instaquoteresponse=requestContext.post(url,
            RequestOptions.create()
                    .setHeader("Content-Type","application/json")
                    .setHeader("Api-Key",apikey)
                    .setHeader("correlationId",uuid1)
                    .setHeader("source","partnercompany")
                    .setData(updatedJson)
            );
    System.out.println(instaquoteresponse.status());
    Allure.step("Status code of the instaquote response "+instaquoteresponse.status());
   // Assert.assertEquals(instaquoteresponse.status(),200);
    System.out.println(instaquoteresponse.text());
    Allure.addAttachment("Response of the instaquote response",instaquoteresponse.text());
    ObjectMapper objectMapper=new ObjectMapper();
    JsonNode jsonNode =objectMapper.readTree(instaquoteresponse.body());
    JsonNode jsonData = jsonNode.get("data");
        if(jsonData.get("isJunk").asBoolean())
        {
            Allure.step("Junk Flow");
            System.out.println("Junk Flow");
        }
        else {
            Allure.step("Auction Flow");
            System.out.println("Auction Flow");
        }
       leadId =jsonData.get("leadId").asText();
        Allure.step("Lead id generated");
        Allure.addAttachment("Lead id generated",leadId);
    System.out.println(leadId);
    String offeramount=jsonData.get("offerAmount").asText();
    Allure.step("Offer amount generated");
    Allure.addAttachment("Offer amount generated",offeramount);
    System.out.println(offeramount);
}

@Description("Test Case to validate the save user info api")
@Story("Sanity for Partner api5")
@Severity(SeverityLevel.CRITICAL)
@Test (dependsOnMethods = "instaquote_offer_with_VIN",enabled = false)
public void Saveuserinfo() throws IOException {
    System.out.println(sf.getPhoneNumber());
    System.out.println(sf.getLoginEmail());
    sf.setFirstName(CommonMethods.random_first_name());
    sf.setLastName(CommonMethods.random_last_name());
    System.out.println(apikey);
    String path="./src/main/java/data/API/Payload/US/Partner/saveuserinfo.json";
    Allure.step("Validate json payload for save user info api");
    HashMap<String,String> updates=new HashMap<>();
    updates.put("vin", sf.getVin());
    updates.put("sellerInfo~loginEmail",sf.getLoginEmail());
    updates.put("sellerInfo~firstName",sf.getFirstName());
    updates.put("sellerInfo~lastName",sf.getLastName());
    updates.put("sellerInfo~phoneNumber",sf.getPhoneNumber());
    String updatedJson=CommonMethods.getUpdatedPayload(path,updates);
    Allure.addAttachment("Payload for creating save user info api",updatedJson);


    url=url+"/"+leadId+"/user-details";
    Allure.addAttachment("Url used:-",url);
    System.out.println(url);
    APIResponse instaquoteresponse=requestContext.post(url,
            RequestOptions.create()
                    .setHeader("Content-Type","application/json")
                    .setHeader("Api-Key",apikey)
                    .setHeader("correlationId",uuid1)
                    .setHeader("source","playwright")
                    .setData(updatedJson)
    );

    Allure.step("Save user info api response used");
    System.out.println(instaquoteresponse.status());
    Assert.assertEquals(instaquoteresponse.status(),200);
    Allure.step("Response status code"+instaquoteresponse.status());
    System.out.println(instaquoteresponse.text());
    Allure.addAttachment("Response captured ",instaquoteresponse.text());

}




    @Description("Test Case to create assignment for an junk api")
    @Story("Sanity for Partner api5")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dependsOnMethods = "Saveuserinfo", enabled = false)
    public void Crateasssignment() throws IOException {

        System.out.println(apikey);
        String path = "./src/main/java/data/API/Payload/US/Partner/createassignment.json";
        Allure.step("Validating JSON payload for assignment creation API");
        HashMap<String, String> updates = new HashMap<>();
        updates.put("vin", sf.getVin());
        updates.put("leadId", leadId);

        String updatedJson = CommonMethods.getUpdatedPayload(path, updates);
        if (updatedJson != null) {
            Allure.addAttachment("Payload for creating assignment API", updatedJson);
        } else {
            Allure.addAttachment("Payload for creating assignment API", "Payload is null");
            Assert.fail("Payload is null. Please check the file path or the update logic.");
        }

        url = CommonMethods.jsonParse("assignment");
        Allure.addAttachment("URL used", url != null ? url : "URL is null");
        System.out.println(url);

        APIResponse assignmentResponse = requestContext.post(url,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Api-Key", apikey)
                        .setHeader("correlationId", uuid1)
                        .setHeader("source", "playwright")
                        .setData(updatedJson)
        );

        Allure.step("Assignment API response received");
        System.out.println("Status: " + assignmentResponse.status());
        Assert.assertEquals(assignmentResponse.status(), 200);
        Allure.step("Response status code: " + assignmentResponse.status());

        String responseText = assignmentResponse.text();
        if (responseText != null) {
            System.out.println(responseText);
            Allure.addAttachment("Response captured", responseText);
        } else {
            Allure.addAttachment("Response captured", "Response text is null");
        }
    }



    @Parameters({"invocationCount"})
    @Test(invocationCount = 10, groups = {"US_PartnerAPI"})
    public void runFullScriptSequentially(String invocationCountParam) throws IOException, InterruptedException {
        // Since our tests have dependencies and shared state, we call them in order.
        // Alternatively, you could extract the logic into separate private methods.
        instaquote_offer_with_VIN();
        Saveuserinfo();
        Crateasssignment();
    }
    }











