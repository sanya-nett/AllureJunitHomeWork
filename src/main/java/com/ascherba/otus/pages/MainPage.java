package com.ascherba.otus.pages;

import com.ascherba.otus.enums.ProfileMenu;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends AbstractPage {

    private final static String PAGE_URL = "https://otus.ru/";

    @FindBy(css = ".ic-blog-default-avatar")
    private WebElement personalIcon;

    @FindBy(css = ".header2_subheader-link__mail")
    private WebElement headerContactEmail;

    @FindBy(css = ".header2_subheader-link__phone")
    private WebElement headerContactPhone;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public void openPage() {
        driver.get(PAGE_URL);
    }

    @Step("Открыть выпадающий список для профиля")
    private void openProfileMenu() {
        moveToElement(wait.until(ExpectedConditions.visibilityOf(personalIcon)));
    }

    @Step("Выбрать {profileMenu} из выпадающего спика для профиля")
    private void selectProfileMenu(ProfileMenu profileMenu) {
        String selector = String.format(
                ".header2__right .header2-menu__dropdown-link[href^=\"%s\"]",
                profileMenu.getLinkPrefix()
        );
        driver.findElement(By.cssSelector(selector)).click();
    }

    @Step("Получить контактную почту из шапки")
    public String getHeaderContactEmail() {
        return wait.until(ExpectedConditions.visibilityOf(headerContactEmail)).getText();
    }

    @Step("Получить контактную почту из шапки")
    public String getHeaderContactPhone() {
        return wait.until(ExpectedConditions.visibilityOf(headerContactPhone)).getText();
    }

    @Step("Перейти в личный кабинет через выпадающий список профиля")
    public PersonalPage openPersonalPage() {
        openProfileMenu();
        selectProfileMenu(ProfileMenu.PERSONAL);
        return new PersonalPage(driver);
    }
}
