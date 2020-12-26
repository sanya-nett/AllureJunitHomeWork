package com.ascherba.otus.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static io.qameta.allure.Allure.step;

@FindBy(css = "div[data-modal-id='new-log-reg']")
public class MainSignPage extends MainPage {

    @FindBy(css = "button[data-modal-id=new-log-reg]")
    private WebElement authButton;

    @FindBy(css = "div[data-modal-id='new-log-reg']")
    private WebElement signForm;

    // Selectors for signIn/authorization tab
    @FindBy(css = "div[data-mode=login] input[name=email]")
    private WebElement loginEmailField;

    @FindBy(css = "div[data-mode=login] input[name=password]")
    private WebElement loginPasswordField;

    @FindBy(css = "div[data-mode=login] button[type=submit]")
    private WebElement loginSubmitButton;

    public MainSignPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть форму входа/регистрации")
    private void openSignForm() {
        authButton.click();
        wait.until(ExpectedConditions.visibilityOf(signForm));
    }

    @Step("Получить видимость формы авторизации")
    public boolean getSignFormDisplayedStatus() {
        return signForm.isDisplayed();
    }

    /**
     * @param email User email for authorization
     */
    @Step("Ввести в поле email: {email}")
    private void setLoginEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(loginEmailField)).sendKeys(email);
    }

    /**
     * @param password User password for authorization
     */
    @Step("Ввести в поле password: {password}")
    private void setLoginPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(loginPasswordField)).sendKeys(password);
    }

    /**
     * Click on authorization submit
     */
    @Step("Нажать на кнопку 'Войти'")
    private void clickOnAuthSubmit() {
        loginSubmitButton.click();
    }

    /**
     * Authorization in the system
     *
     * @param email    User's email
     * @param password User's password
     */
    @Step("Авторизация в системе")
    public void authorization(String email, String password) {
        openSignForm();
        step("Заполнить данные для авторизации");
        setLoginEmail(email);
        setLoginPassword(password);
        clickOnAuthSubmit();
    }

}
