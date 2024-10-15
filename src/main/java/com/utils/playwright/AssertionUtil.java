package com.utils.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AssertionUtil extends BasePlaywrightUtil {

    public AssertionUtil(Page page) {
        super(page);
    }

    public void assertTitle(String expectedTitle) {
        assertThat(page).hasTitle(expectedTitle);
    }

    public void assertUrl(String expectedUrl) {
        assertThat(page).hasURL(expectedUrl);
    }

    public void assertVisible(String selector) {
        assertThat(page.locator(selector)).isVisible();
    }

    public void assertHidden(String selector) {
        assertThat(page.locator(selector)).isHidden();
    }

    public void assertText(String selector, String expectedText) {
        assertThat(page.locator(selector)).hasText(expectedText);
    }

    public void assertEnabled(String selector) {
        assertThat(page.locator(selector)).isEnabled();
    }

    public void assertDisabled(String selector) {
        assertThat(page.locator(selector)).isDisabled();
    }

    public void assertRole(String selector, AriaRole expectedRole) {
        assertThat(page.locator(selector)).hasAttribute("role", expectedRole.toString().toLowerCase());
    }
}