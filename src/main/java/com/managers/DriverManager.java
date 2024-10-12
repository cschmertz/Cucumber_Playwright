package com.managers;

import com.microsoft.playwright.*;
import com.utils.ConfigReader;

public class DriverManager {

    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private ConfigReader configReader;

    public DriverManager() {
        configReader = new ConfigReader();
        initializeDriver();
    }

    private void initializeDriver() {
        Playwright pw = Playwright.create();
        playwright.set(pw);

        String browserType = configReader.getBrowser().toLowerCase();
        boolean headless = configReader.isHeadless();

        switch (browserType.toUpperCase()) {
            case "CHROME":
                browser.set(pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "FIREFOX":
                browser.set(pw.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "WEBKIT":
                browser.set(pw.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserType);
        }

        // Create a new browser context for each thread
        context.set(browser.get().newContext());

        // Create a new page in the browser context
        page.set(context.get().newPage());
    }

    public Page getPage() {
        return page.get();
    }

    public void closeBrowser() {
        if (context.get() != null) {
            context.get().close();
        }
        if (browser.get() != null) {
            browser.get().close();
        }
        if (playwright.get() != null) {
            playwright.get().close();
        }
    }
}
