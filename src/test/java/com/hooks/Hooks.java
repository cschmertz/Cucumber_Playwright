package com.hooks;

import com.context.ScenarioContext;
import com.managers.DriverManager;

import java.time.LocalDateTime;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    private DriverManager driverManager;
    private ScenarioContext scenarioContext;

    public Hooks(ScenarioContext context) {
        this.scenarioContext = context;
    }

    @Before
    public void setUp() {
        driverManager = new DriverManager();
        scenarioContext.setPage(driverManager.getPage());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("-----> After annotation: Scenario completed with failure");
            System.out.println("Scenario ID: " + scenario.getId());
            System.out.println("Scenario Name: " + scenario.getName());
            System.out.println("Scenario Status: Failed");
            System.out.println("Error Message: " + scenario.getStatus());
            System.out.println("Timestamp: " + LocalDateTime.now());
        }
        if (driverManager != null) {
            driverManager.closeBrowser();
        }
    }
}
