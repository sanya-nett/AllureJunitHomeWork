package com.ascherba.otus.fragments;

import org.openqa.selenium.WebElement;

/**
 * Abstract class for common web fragments/elements
 */
public abstract class AbstractFragment {

    protected final WebElement element;

    public AbstractFragment(WebElement element) {
        this.element = element;
    }
}
