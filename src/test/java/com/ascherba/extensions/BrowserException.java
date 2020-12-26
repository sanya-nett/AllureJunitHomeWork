package com.ascherba.extensions;

import com.ascherba.otus.BaseTest;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;

import java.util.Optional;

/**
 * Created by aleksandr.scherba on 26.12.2020
 */
public class BrowserException implements AfterTestExecutionCallback {

    @Attachment(value = "Browser screenshot", type = "image/png")
    byte[] saveBrowserScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Browser logs", type = "text/plain")
    String saveBrowserLogs(WebDriver driver) {
        StringBuilder builder = new StringBuilder();
        driver.manage()
                .logs()
                .get(LogType.BROWSER)
                .getAll().forEach(logEntry -> builder.append(logEntry + "\n"));
        return builder.toString();
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (!extensionContext.getExecutionException().isPresent()) {
            return;
        }
        Optional<Object> testInstance = extensionContext.getTestInstance();
        if (testInstance.isPresent()) {
            WebDriver driver = ((BaseTest) testInstance.get()).driver;
            saveBrowserScreenshot(driver);
            saveBrowserLogs(driver);
        }
    }
}
