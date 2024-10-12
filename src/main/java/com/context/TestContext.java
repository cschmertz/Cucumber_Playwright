package com.context;


import com.managers.DriverManager;

public class TestContext {

    private DriverManager driverManager;

    public TestContext() {
        driverManager = new DriverManager();
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }
}
