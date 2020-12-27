package com.ascherba.otus;

import com.ascherba.otus.data.User;
import com.ascherba.otus.pages.MainSignPage;
import com.ascherba.otus.pages.PersonalPage;
import com.google.common.collect.Maps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

@Feature("Профиль пользователя")
@Story("Персональные данные")
public class PersonalPageTest extends BaseTest {

    private final String USER_EMAIL = System.getProperty("login", "rahog66236@abbuzz.com");
    private final String USER_PASSWORD = System.getProperty("password", "qaz123qaz");

    private PersonalPage authAndMoveToPersonalPage() {
        MainSignPage mainSignPage = new MainSignPage(driver);
        mainSignPage.openPage();
        mainSignPage.authorization(USER_EMAIL, USER_PASSWORD);
        return mainSignPage.openPersonalPage();
    }

    @Test
    @DisplayName("Персональные данные пользователя сохраняются после перезапуска браузера")
    public void checkThatPersonalInformationSaveAfterRestartBrowser() {
        PersonalPage personalPage = authAndMoveToPersonalPage();

        step("Заполнить персональные данные на русском языке", () -> {
            personalPage.setName(User.NAME);
            personalPage.setSurname(User.SURNAME);
        });

        step("Заполнить персональные данные латиницей", () -> {
            personalPage.setLatinName(User.NAME_LATIN);
            personalPage.setLatinSurname(User.SURNAME_LATIN);
        });

        step("Заполнить имя в блоге и дату рождения", () -> {
            personalPage.setBlogName(User.BLOG_NAME);
            personalPage.setBirthDate(User.BIRTH_DATE);
        });

        step("Заполнить страну/город/уровень английского", () -> {
            personalPage.setCountry(User.COUNTRY);
            personalPage.setCity(User.CITY);
            personalPage.setLanguageLevel(User.LANGUAGE_LEVEL);
        });

        step("Очистка тестовых данных после выполнения теста", () -> {
            personalPage.cleanExtraContacts();
            User.EXTRA_CONTACT_DATA.forEach(personalPage::addNewExtraContact);
            personalPage.clickOnSaveButton();
        });

        restartBrowser();
        authAndMoveToPersonalPage();
        personalPage.setDriver(driver);
        step(String.format("Проверить, что имя пользователя = %s", User.NAME), () ->
                Assertions.assertEquals(User.NAME, personalPage.getName()));

        step(String.format("Проверить, что фамилия пользователя = %s", User.SURNAME), () ->
                Assertions.assertEquals(User.SURNAME, personalPage.getSurname()));

        step(String.format("Проверить, что имя пользователя латиницей = %s", User.NAME_LATIN), () ->
                Assertions.assertEquals(User.NAME_LATIN, personalPage.getLatinName()));

        step(String.format("Проверить, что фамилия пользователя латиницей = %s", User.SURNAME_LATIN), () ->
                Assertions.assertEquals(User.SURNAME_LATIN, personalPage.getLatinSurname()));

        step(String.format("Проверить, что имя в блоге = %s", User.BLOG_NAME), () ->
                Assertions.assertEquals(User.BLOG_NAME, personalPage.getBlogName()));

        step(String.format("Проверить, что дата рождения = %s", User.BIRTH_DATE), () ->
                Assertions.assertEquals(User.BIRTH_DATE, personalPage.getBirthDate()));

        step(String.format("Проверить, что страна = %s", User.COUNTRY), () ->
                Assertions.assertEquals(User.COUNTRY, personalPage.getCountry()));

        step(String.format("Проверить, что город = %s", User.CITY), () ->
                Assertions.assertEquals(User.CITY, personalPage.getCity()));

        step(String.format("Проверить, что уровень английского = %s", User.LANGUAGE_LEVEL), () ->
                Assertions.assertEquals(User.LANGUAGE_LEVEL, personalPage.getLanguageLevel()));

        step("Проверить, что контактные данные добавились", () ->
                Assertions.assertTrue(Maps.difference(
                        User.EXTRA_CONTACT_DATA,
                        personalPage.getExtraContacts()
                ).areEqual()));
    }
}
