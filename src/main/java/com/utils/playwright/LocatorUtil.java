package com.utils.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LocatorUtil extends BasePlaywrightUtil {

    public LocatorUtil(Page page) {
        super(page);
    }

    public Locator getLocator(String selector) {
        return page.locator(selector);
    }

    public void click(String selector) {
        getLocator(selector).click();
    }

    public void fill(String selector, String text) {
        getLocator(selector).fill(text);
    }

    public String getText(String selector) {
        return getLocator(selector).innerText();
    }

    public boolean isVisible(String selector) {
        return getLocator(selector).isVisible();
    }

    public void waitForElement(String selector, WaitForSelectorState state) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setState(state));
    }
}