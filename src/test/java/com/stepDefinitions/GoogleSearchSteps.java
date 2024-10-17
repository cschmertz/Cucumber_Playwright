package com.stepDefinitions;

import com.context.TestContext;
import com.pages.GoogleHomePage;
import com.utils.readers.ConfigReader;
import io.cucumber.java.en.*;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Assertions;

public class GoogleSearchSteps {
    private Page page;
    private GoogleHomePage googleHomePage;
    private ConfigReader configReader;

    public GoogleSearchSteps(TestContext testContext) {
        this.page = testContext.getDriverManager().getPage();
        this.googleHomePage = new GoogleHomePage(page);
        this.configReader = new ConfigReader();
    }

    @Given("I am on the Google homepage")
    public void iAmOnGoogleHomepage() {
        String baseUrl = configReader.getBaseUrl();
        page.navigate(baseUrl);
    }

    @When("I enter {string} into the search box")
    public void iEnterIntoSearchBox(String searchQuery) {
        googleHomePage.enterSearchQuery(searchQuery);
    }

    @When("I click the search button")
    public void iClickSearchButton() {
        googleHomePage.clickSearchButton();
    }

    @Then("I should see search results")
    public void iShouldSeeSearchResults() {
        Assertions.assertTrue(page.url().contains("search?q="), "URL does not contain search query");
        Assertions.assertTrue(googleHomePage.areSearchResultsVisible(), "Search results are not visible");
    }

    @When("I click the {string} button")
    public void iClickButton(String buttonName) {
        if (buttonName.equals("I'm Feeling Lucky")) {
            googleHomePage.clickImFeelingLuckyButton();
        } else {
            throw new IllegalArgumentException("Unknown button: " + buttonName);
        }
    }

    @Then("I should be taken directly to a relevant page")
    public void iShouldBeTakenToRelevantPage() {
        Assertions.assertFalse(page.url().contains("google.com"), "Page URL still contains google.com");
    }

    @Then("I should see the Google logo")
    public void iShouldSeeGoogleLogo() {
        Assertions.assertTrue(googleHomePage.isLogoVisible(), "Google logo is not visible");
    }

    @Then("I should see the search box")
    public void iShouldSeeSearchBox() {
        Assertions.assertTrue(googleHomePage.isSearchBoxVisible(), "Search box is not visible");
    }

    @Then("I should see the {string} button")
    public void iShouldSeeButton(String buttonName) {
        if (buttonName.equals("Google Search")) {
            Assertions.assertTrue(googleHomePage.isSearchButtonVisible(), "Google Search button is not visible");
        } else if (buttonName.equals("I'm Feeling Lucky")) {
            Assertions.assertTrue(googleHomePage.isImFeelingLuckyButtonVisible(), "I'm Feeling Lucky button is not visible");
        } else {
            throw new IllegalArgumentException("Unknown button: " + buttonName);
        }
    }
}