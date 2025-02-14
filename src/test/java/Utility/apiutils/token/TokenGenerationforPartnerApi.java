package Utility.apiutils.token;
import Utility.CommonMethods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Allure;
import org.testng.Assert;
import testcases.api.us.pojo.Credentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TokenGenerationforPartnerApi {


    Playwright playwright=Playwright.create();
    APIRequest request=playwright.request();
    APIRequestContext requestContext=request.newContext(new APIRequest.NewContextOptions().setIgnoreHTTPSErrors(true));

    public  String token_generation(String PartnerType) throws IOException {
        String username="";
        String password=" ";
        Credentials cr;
        switch (PartnerType)
        {
            case "Revshare":
             cr =CommonMethods.parsetokenjson("Revshare");
            username=cr.getUsername();
            System.out.println(username);
            password= cr.getPassword();
            System.out.println(password);
            break;
            case "PPL":
                 cr =CommonMethods.parsetokenjson("PPL");
                username=cr.getUsername();
                System.out.println(username);
                password= cr.getPassword();
                System.out.println(password);
                break;
            case "RSESxcluded":
                cr =CommonMethods.parsetokenjson("RSESxcluded");
                username=cr.getUsername();
                System.out.println(username);
                password= cr.getPassword();
                System.out.println(password);
                break;
            case "Charity":
                cr =CommonMethods.parsetokenjson("Charity");
                username=cr.getUsername();
                System.out.println(username);
                password= cr.getPassword();
                System.out.println(password);
                break;

        }

        String url= CommonMethods.jsonParse("partnerapi");
        Allure.step("Sending POST request to: " + url);

        System.out.println(username);
        System.out.println(password);
        APIResponse apiResponse =  requestContext.post( url,
                RequestOptions.create()
                        .setHeader("Accept-Encoding", "gzip, deflate, br") // Accept-Encoding header
                        // .setHeader("Cookie", "JSESSIONID=2F964F9869C5C08C1C6165CDB9E61A2C") // Cookie header
                        .setHeader("Host", "c-partner-qa5.cashforcars.com")
                        .setMultipart(FormData.create()
                                .set("grant_type","password")
                                .set("username",username)
                                .set("password",password)
                        )
        );
        Allure.step("Enter valid username and password");
        Allure.step("Validating the response body");
        System.out.println(apiResponse.text());
        Allure.addAttachment("Response Body", "application/json", apiResponse.text());
        Allure.step("Validating the response status code");
        System.out.println(apiResponse.status());
        System.out.println(apiResponse.statusText());
        Assert.assertEquals(apiResponse.status(),200);
       // Assert.assertEquals(apiResponse.statusText(),"OK");
        ObjectMapper objectMapper=new ObjectMapper();
        Allure.step("Click login button and verify success");
        JsonNode postJsonResponse= objectMapper.readTree(apiResponse.body());
        System.out.println(postJsonResponse.toPrettyString());
       String token=postJsonResponse.get("access_token").asText();
        System.out.println("Token generated is"+token);
        return token;
    }



    public  String randomVinGenerator()
    {
        String vin=null;
       try {
           String url= CommonMethods.jsonParse("randomvin");
           APIResponse apiResponse =  requestContext.get( url,
                   RequestOptions.create()
                           .setHeader("Accept-Encoding", "gzip, deflate, br") // Accept-Encoding header
                              );
            vin=apiResponse.text().trim();
       } catch (Exception e) {
           System.out.println(e);
       }

        return vin;
    }

    public String generateapikey() throws IOException {
        byte[] fileBytes=null;
        File file=new File("./src/main/java/data/API/payload/US/Partner/apikey.json");
        fileBytes= Files.readAllBytes(file.toPath());
        String token=token_generation("Revshare");

        String url=CommonMethods.jsonParse("APIKeyGenerator");
        Allure.step("Url used for hitting the api key"+url);
        Allure.step("");
        APIResponse apiKeyResponse =  requestContext.post(url,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json") // Accept-Encoding header
                        .setHeader("Authorization", "Bearer "+token) // Cookie header
                        //.setHeader("Host", "c-partner-qa5.cashforcars.com")
                        .setData(fileBytes));
        System.out.println(apiKeyResponse.status());
        Assert.assertEquals(apiKeyResponse.status(),200);
        // Assert.assertEquals(apiKeyResponse.statusText(),"Created");
        Allure.step("Api Key generator"+apiKeyResponse.text());
        System.out.println(apiKeyResponse.text());
         String apikey=apiKeyResponse.text();
         return  apikey;
    }


}
