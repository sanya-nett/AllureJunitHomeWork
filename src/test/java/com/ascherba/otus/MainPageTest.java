package com.ascherba.otus;

import com.ascherba.otus.pages.MainPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by aleksandr.scherba on 25.12.2020
 */
@Feature("Главная страница")
@Story("Контактная информация вверху страницы")
public class MainPageTest extends BaseTest {

    MainPage mainPage;

    @BeforeEach
    void openMainPage() {
        mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    @Test
    @DisplayName("В верху страницы есть контактная почта для связи")
    void shouldVisibleContactEmailInHeader() {
        String actualEmail = mainPage.getHeaderContactEmail();
        Assertions.assertEquals("help@otus.ru", actualEmail);
    }

    @Test
    @Flaky
    @DisplayName("В верху страницы есть контактный номер телефона для связи")
    void shouldVisibleContactPhoneInHeader() {
        String actualPhone = mainPage.getHeaderContactPhone();
        // Сломанный тест для прикрепления аттачментов
        Assertions.assertEquals("+7 499 938-92-03", actualPhone);
    }
}
