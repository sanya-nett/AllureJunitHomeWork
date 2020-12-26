package com.ascherba.otus.fragments.personal;

import com.ascherba.otus.fragments.AbstractFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class describe interface of the additional contacts data on personal page
 */
public class ExtraContactBlock extends AbstractFragment {

    private final static By ADD_BUTTON = By.cssSelector("button.js-formset-add");
    private final static By CONTACT_INFO_ITEM = By.cssSelector(".js-formset-row:not(.hide)");

    public ExtraContactBlock(WebElement element) {
        super(element);
    }

    /**
     * Add new contact item in the additional contacts block
     */
    @Step("Нажать на кнопку добавить контакт")
    public void clickOnAddContactInfo() {
        element.findElement(ADD_BUTTON).click();
    }

    /**
     * @return List of additional contacts (without email/phone number)
     */
    @Step("Получить список всех контактов")
    public List<ExtraContactBlockItem> getContactInfoList() {
        return element.findElements(CONTACT_INFO_ITEM)
                .stream()
                .map(ExtraContactBlockItem::new)
                .collect(Collectors.toList());
    }

}
