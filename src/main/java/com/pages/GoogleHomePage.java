package com.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
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
        searchBoxLocator = page.locator("input[name='q'][type='text']");
        searchButtonLocator = page.locator("input[name='btnK']");
        imFeelingLuckyButtonLocator = page.locator("input[name='btnI']");
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
        return searchResultsLocator.isVisible();
    }

    public boolean isLogoVisible() {
        return logoLocator.isVisible();
    }

    public boolean isSearchBoxVisible() {
        return searchBoxLocator.isVisible();
    }

    public boolean isSearchButtonVisible() {
        return searchButtonLocator.first().isVisible();
    }

    public boolean isImFeelingLuckyButtonVisible() {
        return imFeelingLuckyButtonLocator.first().isVisible();
    }
}