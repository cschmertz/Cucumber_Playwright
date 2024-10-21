package com.managers;

import com.microsoft.playwright.*;
import com.utils.readers.ConfigReader;
import com.utils.playwright.TraceUtils;
import com.utils.playwright.PlaywrightFacade;
import java.nio.file.Path;

public class DriverManager {
    private static final ThreadLocal<Playwright> playwright = ThreadLocal.withInitial(Playwright::create);
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();
    private static final ThreadLocal<PlaywrightFacade> playwrightFacade = new ThreadLocal<>();
    private final ConfigReader configReader;

    public DriverManager() {
        configReader = new ConfigReader();
    }

    public void initializeDriver() {
        Playwright pw = playwright.get();

        String browserType = configReader.getBrowser().toLowerCase();
        boolean headless = configReader.isHeadless();

        Browser b;
        switch (browserType) {
            case "chromium":
                b = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "firefox":
                b = pw.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "webkit":
                b = pw.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserType);
        }

        browser.set(b);
        context.set(b.newContext());
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
        BrowserContext ctx = context.get();
        if (ctx != null) {
            ctx.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }
    }

    public Path stopTracing(String traceName) {
        BrowserContext ctx = context.get();
        if (ctx != null) {
            Path tracePath = TraceUtils.getTraceFilePath(traceName);
            ctx.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
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