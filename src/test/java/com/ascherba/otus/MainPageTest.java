package com.ascherba.otus;

import com.ascherba.otus.pages.MainPage;
import com.ascherba.otus.pages.MainSignPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

/**
 * Created by aleksandr.scherba on 25.12.2020
 */
@Feature("Главная страница")
@Story("Контактная информация вверху страницы")
public class MainPageTest extends BaseTest {

    private final String USER_EMAIL = System.getProperty("login", "rahog662361@abbuzz.com");
    private final String USER_PASSWORD = System.getProperty("password", "qaz123qaz");

    @Test
    @Flaky
    @DisplayName("При вводе несуществующих данных пользователя должна быть ошибка")
    @Description("Сломана проверка в тесте для прикрепления аттачмертов")
    void shouldFailedAuthorizationWithUnknownUser() {
        MainSignPage signPage = new MainSignPage(driver);
        signPage.openPage();
        signPage.authorization(USER_EMAIL, USER_PASSWORD);
        step("Проверить, что форма авторизации открыта", () ->
            // Тут должна быть проверка, что форма видима
            Assertions.assertFalse(signPage.getSignFormDisplayedStatus()));
    }
}
