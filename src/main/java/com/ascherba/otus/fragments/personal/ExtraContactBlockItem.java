package com.ascherba.otus.fragments.personal;

import com.ascherba.otus.enums.ContactType;
import com.ascherba.otus.fragments.AbstractFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This class describe interface of each additional contact data on personal page
 */
public class ExtraContactBlockItem extends AbstractFragment {

    private final static By CONTACT_TYPE_DROP_DOWN = By.cssSelector("span.placeholder");
    private final static By CONTACT_TYPE_INPUT = By.cssSelector("input.js-custom-select-input");
    private final static By CONTACT_TYPE_BUTTON = By.cssSelector(".js-custom-select-options > button");
    private final static By TEXT_FIELD = By.cssSelector("input[type='text']");
    private final static By DELETE_BUTTON = By.cssSelector(".container__col_md-0 button.js-formset-delete");

    public ExtraContactBlockItem(WebElement element) {
        super(element);
    }

    /**
     * Remove contact data
     */
    @Step("Нажать на кнопку 'Удалить' для блока контакта")
    public void clickOnDelete() {
        element.findElement(DELETE_BUTTON).click();
    }

    /**
     * Open select menu for choose necessary contact type
     */
    @Step("Открыть выпадающий список 'Способ связи'")
    private void openContactTypeMenu() {
        element.findElement(CONTACT_TYPE_DROP_DOWN).click();
    }

    /**
     * @return Current contact type
     */
    @Step("Получить установленный способ связи для контакта")
    public ContactType getContactType() {
        String contactValue = element.findElement(CONTACT_TYPE_INPUT).getAttribute("value");
        return ContactType.getTypeByValue(contactValue);
    }

    /**
     * @return Current contact data from text field
     */
    @Step("Получить установленное значение для контакта")
    public String getContactTextData() {
        return element.findElement(TEXT_FIELD).getAttribute("value");
    }

    /**
     * @param contactType Expected contact type
     */
    @Step("Установить {contactType} способ связи")
    private void setContactType(ContactType contactType) {
        List<WebElement> allTypeContacts = element.findElements(CONTACT_TYPE_BUTTON);
        WebElement expectedContactType = allTypeContacts.stream()
                .filter(WebElement::isDisplayed)
                .filter(webElement ->
                        ContactType.getTypeByValue(webElement.getAttribute("data-value")) == contactType)
                .findFirst().orElse(null);
        assert expectedContactType != null;
        expectedContactType.click();
    }

    /**
     * @param data Expected contact data to text field
     */
    @Step("Ввести в текстовое поле: {data}")
    private void setContactTextData(String data) {
        element.findElement(TEXT_FIELD).sendKeys(data);
    }

    /**
     * Public function for set contact type and data
     *
     * @param contactType Expected contact type
     * @param contactData Expected contact data to text field
     */
    @Step("Заполнить контактную информацию")
    public void setContactData(ContactType contactType, String contactData) {
        openContactTypeMenu();
        setContactType(contactType);
        setContactTextData(contactData);
    }

}
