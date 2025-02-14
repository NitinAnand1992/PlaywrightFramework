package Utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import java.io.*;

public class TestListener  implements ITestListener {

    @Override
    public void onStart(ITestContext context) {

        String filepath = System.getProperty("user.dir") + "/allure-report";
        if (CommonMethods.directoryExists(filepath)) {
            System.out.println("Old Allure Report found. Deleting...");
            CommonMethods.deleteDirectory(filepath);
        }
        else
        {
            System.out.println("No Previous Allure Report Found.Skipping deletion");
        }

    }

    @Override
    public void onFinish(ITestContext context)
    {

        CommonMethods.generateAllureReport();
        System.out.println("Test execution finished.Generating the allure report...");
        String input=System.getProperty("user.dir") + "/allure-report/index.html";
        String outputGzip = System.getProperty("user.dir") + "/allure-report/index.html.gz";

        if (CommonMethods.directoryExists(input)) {
            if (CommonMethods.compressHtmlFile(input, outputGzip)) {
                System.out.println("Allure report compressed successfully: " + outputGzip);
            }
        } else {
            System.out.println("No Allure report found after execution. Skipping compression.");
        }

    }


    }




