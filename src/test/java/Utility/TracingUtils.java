package Utility;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Attachment;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class TracingUtils {
    private static final String TRACE_FILE_PATH = "allure-results/trace.zip";

    public static void startTracing(BrowserContext context) {
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
    }

    public static void stopTracing(BrowserContext context) {
        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(TRACE_FILE_PATH)));
        attachTrace(TRACE_FILE_PATH);
    }

    @Attachment(value = "Playwright Trace", type = "application/zip")
    private static byte[] attachTrace(String tracePath) {
        try {
            return Files.readAllBytes(Paths.get(tracePath));
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

}
