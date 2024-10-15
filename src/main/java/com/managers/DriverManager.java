package com.managers;

import com.microsoft.playwright.*;
import com.utils.readers.ConfigReader;
import com.utils.playwright.TraceUtils;
import com.utils.playwright.PlaywrightFacade;
import java.nio.file.Path;

public class DriverManager {
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private static ThreadLocal<PlaywrightFacade> playwrightFacade = new ThreadLocal<>();
    private ConfigReader configReader;

    public DriverManager() {
        configReader = new ConfigReader();
        initializeDriver();
    }

    private void initializeDriver() {
        Playwright pw = Playwright.create();
        playwright.set(pw);

        String browserType = configReader.getProperty("browser.type").toLowerCase();
        boolean headless = Boolean.parseBoolean(configReader.getProperty("browser.headless"));

        switch (browserType) {
            case "chromium":
                browser.set(pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "firefox":
                browser.set(pw.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "webkit":
                browser.set(pw.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserType);
        }

        context.set(browser.get().newContext());
        page.set(context.get().newPage());
        playwrightFacade.set(new PlaywrightFacade(page.get(), context.get()));
    }

    public Page getPage() {
        return page.get();
    }

    public PlaywrightFacade getPlaywrightFacade() {
        return playwrightFacade.get();
    }

    public void startTracing(String traceName) {
        if (context.get() != null) {
            context.get().tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }
    }

    public Path stopTracing(String traceName) {
        if (context.get() != null) {
            Path tracePath = TraceUtils.getTraceFilePath(traceName);
            context.get().tracing().stop(new Tracing.StopOptions()
                    .setPath(tracePath));
            return tracePath;
        }
        return null;
    }

    public void closeBrowser() {
        if (page.get() != null) {
            page.get().close();
            page.remove();
        }
        if (context.get() != null) {
            context.get().close();
            context.remove();
        }
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
        playwrightFacade.remove();
    }

    public byte[] takeScreenshot() {
        return getPlaywrightFacade().screenshot().takeFullPageScreenshot();
    }

    public String getPageTitle() {
        return getPage().title();
    }

    public String getCurrentUrl() {
        return getPage().url();
    }
}