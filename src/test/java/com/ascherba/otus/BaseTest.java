package com.ascherba.otus;

import com.ascherba.extensions.BrowserException;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.logging.Level;

/**
 * Created by aleksandr.scherba on 25.12.2020
 */
@Epic("Веб-сайт OTUS")
@ExtendWith(BrowserException.class)
public class BaseTest {

    protected static final WebDriverManager driverManager = ChromeDriverManager.chromedriver();
    public WebDriver driver;

    private ChromeOptions getOptions() {
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
        return chromeOptions;
    }

    @BeforeAll
    public static void installDriver() {
        driverManager.setup();
    }

    @BeforeEach
    @Step("Конфигурация и создание браузера")
    private void configureDriver() {
        driver = new ChromeDriver(getOptions());
    }

    @AfterEach
    @Step("Закрытие браузера")
    private void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Перезапустить браузер")
    public void restartBrowser() {
        driver.quit();
        driver = new ChromeDriver(getOptions());
    }

}
