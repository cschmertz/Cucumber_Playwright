package com.context;

import com.microsoft.playwright.Page;

public class ScenarioContext {

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}


