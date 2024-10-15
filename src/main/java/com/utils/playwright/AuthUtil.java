package com.utils.playwright;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

import java.nio.file.Path;
import java.util.Base64;
import java.util.Map;

public class AuthUtil extends BasePlaywrightUtil {
    private BrowserContext context;

    public AuthUtil(Page page, BrowserContext context) {
        super(page);
        this.context = context;
    }

    public void setHttpCredentials(String username, String password) {
        context.setExtraHTTPHeaders(Map.of(
                "Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes())
        ));
    }

    public void login(String username, String password, String usernameSelector, String passwordSelector, String submitSelector) {
        page.fill(usernameSelector, username);
        page.fill(passwordSelector, password);
        page.click(submitSelector);
    }

    public void saveAuthState(Path savePath) {
        context.storageState(new BrowserContext.StorageStateOptions().setPath(savePath));
    }

    public void loadAuthState(Path loadPath) {
        // This should be called when creating a new context, not on an existing one
        // You might need to adjust your DriverManager to accommodate this
        // context = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(loadPath));
    }
}