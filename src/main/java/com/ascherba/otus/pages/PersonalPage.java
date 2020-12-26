package com.ascherba.otus.pages;

import com.ascherba.otus.enums.ContactType;
import com.ascherba.otus.fragments.personal.ExtraContactBlock;
import com.ascherba.otus.fragments.personal.ExtraContactBlockItem;
import com.google.common.collect.Iterables;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class describe interface of personal page
 */
public class PersonalPage extends AbstractPage {

    // Personal data
    @FindBy(css = "#id_fname")
    private WebElement nameField;

    @FindBy(css = "#id_lname")
    private WebElement surnameField;

    @FindBy(css = "#id_fname_latin")
    private WebElement nameLatinField;

    @FindBy(css = "#id_lname_latin")
    private WebElement surnameLatinField;

    @FindBy(css = "#id_blog_name")
    private WebElement blogNameField;

    @FindBy(css = "input[name=date_of_birth]")
    private WebElement birthDatePicker;

    // Main information
    @FindBy(xpath = "//input[@name='country']/../div")
    private WebElement countryField;

    @FindBy(xpath = "//input[@name='country']/../..//div[contains(@class, 'lk-cv-block__select-scroll')]/button")
    private List<WebElement> countryList;

    @FindBy(xpath = "//input[@name='city']/../div")
    private WebElement cityField;

    @FindBy(xpath = "//input[@name='city']/../..//div[contains(@class, 'lk-cv-block__select-scroll')]/button")
    private List<WebElement> cityList;

    @FindBy(xpath = "//input[@name='english_level']/../div")
    private WebElement languageLevelField;

    @FindBy(xpath = "//input[@name='english_level']/../..//div[contains(@class, 'lk-cv-block__select-scroll')]/button")
    private List<WebElement> languageLevelList;

    // Contact data
    @FindBy(css = "div[data-prefix=contact]")
    private WebElement extraContactBlock;

    // Action buttons
    @FindBy(css = ".lk-cv-action-buttons > button[name=continue]")
    private WebElement saveContinueButton;

    @FindBy(css = ".lk-cv-action-buttons > button:not([name=continue])")
    private WebElement saveButton;

    public PersonalPage(WebDriver driver) {
        super(driver);
    }

    // Create custom class for work helpers with elements
    @Step("Очистить поле и ввести: {value}")
    private void cleanFieldAndSetText(WebElement textField, String value) {
        wait.until(ExpectedConditions.visibilityOf(textField)).clear();
        textField.sendKeys(value);
    }

    // Create custom class for work helpers with elements
//    @Step("Взять у элемента текст")
    private String getFieldText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getAttribute("value");
    }

    // Create custom class for work helpers with elements
    private void setDivSelector(List<WebElement> elements, String expectedValue) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(elements))
                    .stream()
                    .filter(element -> element.getText().equalsIgnoreCase(expectedValue))
                    .findFirst().get().click();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(String.format("Not found list element with value: %s", expectedValue));
        }

    }

    /**
     * @return User's name in native language
     */
    @Step("Получить имя пользователя на русском языке")
    public String getName() {
        return getFieldText(nameField);
    }

    /**
     * @return User's name in latin language
     */
    @Step("Получить имя пользователя латиницей")
    public String getLatinName() {
        return getFieldText(nameLatinField);
    }

    /**
     * @return User's surname in native language
     */
    @Step("Получить фамилию пользователя на русском языке")
    public String getSurname() {
        return getFieldText(surnameField);
    }

    /**
     * @return User's surname in latin language
     */
    @Step("Получить фамилию пользователя латиницей")
    public String getLatinSurname() {
        return getFieldText(surnameLatinField);
    }

    /**
     * @return User's blog name
     */
    @Step("Получить имя в блоге")
    public String getBlogName() {
        return getFieldText(blogNameField);
    }

    /**
     * @return User's birth date
     */
    @Step("Получить дату рождения")
    public String getBirthDate() {
        return getFieldText(birthDatePicker);
    }

    /**
     * @return User's country
     */
    @Step("Получить страну")
    public String getCountry() {
        return countryField.getText();
    }

    /**
     * @return User's city
     */
    @Step("Получить город")
    public String getCity() {
        return cityField.getText();
    }

    /**
     * @return User's language level
     */
    @Step("Получить уровень английского")
    public String getLanguageLevel() {
        return languageLevelField.getText();
    }

    /**
     * Set user's name in native language
     *
     * @param name Expected value for user's native name
     */
    @Step("Заполнить имя: {name}")
    public void setName(String name) {
        cleanFieldAndSetText(nameField, name);
    }

    /**
     * Set user's name in latin language
     *
     * @param name Expected value for user's latin name
     */
    @Step("Заполнить имя латиницей: {name}")
    public void setLatinName(String name) {
        cleanFieldAndSetText(nameLatinField, name);
    }

    /**
     * Set user's surname in native language
     *
     * @param surname Expected value for user's native surname
     */
    @Step("Заполнить фамилию: {surname}")
    public void setSurname(String surname) {
        cleanFieldAndSetText(surnameField, surname);
    }

    /**
     * Set user's surname in latin language
     *
     * @param surname Expected value for user's latin surname
     */
    @Step("Заполнить фамилию латиницей: {surname}")
    public void setLatinSurname(String surname) {
        cleanFieldAndSetText(surnameLatinField, surname);
    }

    /**
     * Set user's blog name
     *
     * @param blogName Expected value for user's blog name
     */
    @Step("Заполнить имя в блоге: {blogName}")
    public void setBlogName(String blogName) {
        cleanFieldAndSetText(blogNameField, blogName);
    }

    /**
     * Set user's blog name
     *
     * @param birthDate Expected value for user's blog name
     */
    @Step("Заполнить дату рождения: {birthDate}")
    public void setBirthDate(String birthDate) {
        cleanFieldAndSetText(birthDatePicker, birthDate);
    }

    /**
     * Set user's country
     *
     * @param country Expected value for user's country
     */
    @Step("Заполнить страну: {country}")
    public void setCountry(String country) {
        wait.until(ExpectedConditions.visibilityOf(countryField)).click();
        setDivSelector(countryList, country);
    }

    /**
     * Set user's city
     *
     * @param city Expected value for user's city
     */
    @Step("Заполнить город: {city}")
    public void setCity(String city) {
        // Need to load city list before click
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(cityField, By.cssSelector("span")));
        cityField.click();
        setDivSelector(cityList, city);
    }

    /**
     * Set user's language level
     *
     * @param languageLevel Expected value for user's language level
     */
    @Step("Заполнить уровень английского: {languageLevel}")
    public void setLanguageLevel(String languageLevel) {
        wait.until(ExpectedConditions.visibilityOf(languageLevelField)).click();
        setDivSelector(languageLevelList, languageLevel);
    }

    /**
     * @return Block of additional contacts
     */
    private ExtraContactBlock getExtraContactBlock() {
        return new ExtraContactBlock(wait.until(ExpectedConditions.visibilityOf(extraContactBlock)));
    }

    /**
     * Clean all additional contact data
     */
    @Step("Очистить все дополнительные контакты")
    public void cleanExtraContacts() {
        getExtraContactBlock().getContactInfoList().forEach(ExtraContactBlockItem::clickOnDelete);
    }

    /**
     * @return All additional contacts
     */
    @Step("Получить все дополнительные контакты")
    public HashMap<ContactType, String> getExtraContacts() {
        HashMap<ContactType, String> actualContactData = new HashMap<>();
        for (ExtraContactBlockItem contactBlockItem : getExtraContactBlock().getContactInfoList()) {
            actualContactData.put(
                    contactBlockItem.getContactType(),
                    contactBlockItem.getContactTextData()
            );
        }
        return actualContactData;
    }

    /**
     * Adds a new additional contact block and fill data in it
     *
     * @param contactType Contact type
     * @param contactData Contact text data
     */
    @Step("Добавить новые контактные данные")
    public void addNewExtraContact(ContactType contactType, String contactData) {
        ExtraContactBlock contactBlock = getExtraContactBlock();
        contactBlock.clickOnAddContactInfo();

        // "Найти последнее контактное поле"
        ExtraContactBlockItem lastContactBlockItem = Iterables.getLast(contactBlock.getContactInfoList());
        lastContactBlockItem.setContactData(contactType, contactData);
    }

    /**
     * Save changes and don't redirected to another page
     */
    @Step("Нажать кнопку 'Сохранить и заполнить позже'")
    public void clickOnSaveButton() {
        saveButton.click();
    }

    /**
     * Save changes and redirected to another page
     */
    @Step("Нажать кнопку 'Сохранить и продолжить'")
    public void clickOnSaveAndContinueButton() {
        saveContinueButton.click();
    }
}
