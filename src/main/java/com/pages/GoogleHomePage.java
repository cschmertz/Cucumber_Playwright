package com.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class GoogleHomePage {
    private final Page page;

    private Locator logoLocator;
    private Locator searchBoxLocator;
    private Locator searchButtonLocator;
    private Locator imFeelingLuckyButtonLocator;
    private Locator searchResultsLocator;

    public GoogleHomePage(Page page) {
        this.page = page;
        initializeLocators();
    }

    private void initializeLocators() {
        logoLocator = page.locator("img[alt='Google']");
        searchBoxLocator = page.locator("textarea[name='q']");
        searchButtonLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Google Search")).first();
        imFeelingLuckyButtonLocator = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("I'm Feeling Lucky")).first();
        searchResultsLocator = page.locator("#search");
    }

    public void enterSearchQuery(String query) {
        searchBoxLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        searchBoxLocator.fill(query);
    }

    public void clickSearchButton() {
        searchButtonLocator.first().click();
    }

    public void clickImFeelingLuckyButton() {
        imFeelingLuckyButtonLocator.first().click();
    }

    public boolean areSearchResultsVisible() {
        searchResultsLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        return searchResultsLocator.isVisible();
    }

    public boolean isLogoVisible() {
        return logoLocator.isVisible();
    }

    public boolean isSearchBoxVisible() {
        searchButtonLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        return searchBoxLocator.isVisible();
    }

    public boolean isSearchButtonVisible() {
        return searchButtonLocator.first().isVisible();
    }

    public boolean isImFeelingLuckyButtonVisible() {
        imFeelingLuckyButtonLocator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        return imFeelingLuckyButtonLocator.first().isVisible();
    }
}