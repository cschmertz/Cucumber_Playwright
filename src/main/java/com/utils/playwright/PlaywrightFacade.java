package com.utils.playwright;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class PlaywrightFacade {
    private LocatorUtil locatorUtil;
    private DownloadUtil downloadUtil;
    private DialogUtil dialogUtil;
    private FrameUtil frameUtil;
    private AssertionUtil assertionUtil;
    private AuthUtil authUtil;
    private NavigationUtil navigationUtil;
    private ScreenshotUtil screenshotUtil;

    public PlaywrightFacade(Page page, BrowserContext context) {
        this.locatorUtil = new LocatorUtil(page);
        this.downloadUtil = new DownloadUtil(page);
        this.dialogUtil = new DialogUtil(page);
        this.frameUtil = new FrameUtil(page);
        this.assertionUtil = new AssertionUtil(page);
        this.authUtil = new AuthUtil(page, context);
        this.navigationUtil = new NavigationUtil(page);
        this.screenshotUtil = new ScreenshotUtil(page);
    }

    public LocatorUtil locator() {
        return locatorUtil;
    }

    public DownloadUtil download() {
        return downloadUtil;
    }

    public DialogUtil dialog() {
        return dialogUtil;
    }

    public FrameUtil frame() {
        return frameUtil;
    }

    public AssertionUtil assertion() {
        return assertionUtil;
    }

    public AuthUtil auth() {
        return authUtil;
    }

    public NavigationUtil navigation() {
        return navigationUtil;
    }

    public ScreenshotUtil screenshot() {
        return screenshotUtil;
    }
}