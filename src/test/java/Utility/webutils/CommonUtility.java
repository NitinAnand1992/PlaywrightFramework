package Utility.webutils;
import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CommonUtility {

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot(Page page) {
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }





}