package com.ascherba.otus;

import com.ascherba.extensions.BrowserException;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Created by aleksandr.scherba on 25.12.2020
 */
@Epic("Веб-сайт OTUS")
@ExtendWith(BrowserException.class)
public class BaseTest {


    private final static String SELENOID_HOST = "http://91.206.15.114:4444/wd/hub";
    public WebDriver driver;

    private void runBrowser() {
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
        chromeOptions.setCapability("enableVNC", true);
        chromeOptions.setCapability("screenResolution", "1920x1080");
        chromeOptions.setCapability("sessionTimeout", "10m");
        try {
            driver = new RemoteWebDriver(new URL(SELENOID_HOST), chromeOptions);
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @BeforeEach
    @Step("Конфигурация и создание браузера")
    private void configureDriver() {
        runBrowser();
    }

    @AfterEach
    @Step("Закрытие браузера")
    private void closeBrowser() {
        Optional.ofNullable(driver).ifPresent(WebDriver::quit);
    }

    @Step("Перезапустить браузер")
    public void restartBrowser() {
        runBrowser();
    }

}
