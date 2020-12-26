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

        PersonalPage firstPersonalPage = personalPage;
        step("Заполнить персональные данные на русском языке", () -> {
            firstPersonalPage.setName(User.NAME);
            firstPersonalPage.setSurname(User.SURNAME);
        });

        step("Заполнить персональные данные латиницей", () -> {
            firstPersonalPage.setLatinName(User.NAME_LATIN);
            firstPersonalPage.setLatinSurname(User.SURNAME_LATIN);
        });

        step("Заполнить имя в блоге и дату рождения", () -> {
            firstPersonalPage.setBlogName(User.BLOG_NAME);
            firstPersonalPage.setBirthDate(User.BIRTH_DATE);
        });

        step("Заполнить страну/город/уровень английского", () -> {
            firstPersonalPage.setCountry(User.COUNTRY);
            firstPersonalPage.setCity(User.CITY);
            firstPersonalPage.setLanguageLevel(User.LANGUAGE_LEVEL);
        });

        step("Очистка тестовых данных после выполнения теста", () -> {
            firstPersonalPage.cleanExtraContacts();
            User.EXTRA_CONTACT_DATA.forEach(firstPersonalPage::addNewExtraContact);
            firstPersonalPage.clickOnSaveButton();
        });

        restartBrowser();
        personalPage = authAndMoveToPersonalPage();
        PersonalPage secondPersonalPage = personalPage;
        step(String.format("Проверить, что имя пользователя = %s", User.NAME), () ->
                Assertions.assertEquals(User.NAME, secondPersonalPage.getName()));

        step(String.format("Проверить, что фамилия пользователя = %s", User.SURNAME), () ->
                Assertions.assertEquals(User.SURNAME, secondPersonalPage.getSurname()));

        step(String.format("Проверить, что имя пользователя латиницей = %s", User.NAME_LATIN), () ->
                Assertions.assertEquals(User.NAME_LATIN, secondPersonalPage.getLatinName()));

        step(String.format("Проверить, что фамилия пользователя латиницей = %s", User.SURNAME_LATIN), () ->
                Assertions.assertEquals(User.SURNAME_LATIN, secondPersonalPage.getLatinSurname()));

        step(String.format("Проверить, что имя в блоге = %s", User.BLOG_NAME), () ->
                Assertions.assertEquals(User.BLOG_NAME, secondPersonalPage.getBlogName()));

        step(String.format("Проверить, что дата рождения = %s", User.BIRTH_DATE), () ->
                Assertions.assertEquals(User.BIRTH_DATE, secondPersonalPage.getBirthDate()));

        step(String.format("Проверить, что страна = %s", User.COUNTRY), () ->
                Assertions.assertEquals(User.COUNTRY, secondPersonalPage.getCountry()));

        step(String.format("Проверить, что город = %s", User.CITY), () ->
                Assertions.assertEquals(User.CITY, secondPersonalPage.getCity()));

        step(String.format("Проверить, что уровень английского = %s", User.LANGUAGE_LEVEL), () ->
                Assertions.assertEquals(User.LANGUAGE_LEVEL, secondPersonalPage.getLanguageLevel()));

        step("Проверить, что контактные данные добавились", () ->
                Assertions.assertTrue(Maps.difference(
                        User.EXTRA_CONTACT_DATA,
                        secondPersonalPage.getExtraContacts()
                ).areEqual()));
    }
}
