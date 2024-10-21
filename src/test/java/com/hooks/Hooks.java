package com.hooks;

import com.context.TestContext;
import com.utils.playwright.TraceUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class Hooks {
    private final TestContext testContext;
    private static final String TRACE_DIR = "traces";

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        testContext.getDriverManager().initializeDriver(); // Initialize driver for each scenario
        String traceName = scenario.getName().replaceAll("\\s+", "_").toLowerCase();
        testContext.getDriverManager().startTracing(traceName);
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logFailureDetails(scenario);
                byte[] screenshot = testContext.getDriverManager().takeScreenshot();
                scenario.attach(screenshot, "image/png", "Screenshot");
            }

            String traceName = scenario.getName().replaceAll("\\s+", "_").toLowerCase();
            Path tracePath = testContext.getDriverManager().stopTracing(traceName);

            scenario.attach(tracePath.toAbsolutePath().toString(), "text/plain", "Trace File Path");
        } finally {
            testContext.getDriverManager().closeBrowser();
        }

        TraceUtils.deleteOldTraces(TRACE_DIR);
    }

    private void logFailureDetails(Scenario scenario) {
        System.out.println("-----> After annotation: Scenario completed with failure");
        System.out.println("Scenario ID: " + scenario.getId());
        System.out.println("Scenario Name: " + scenario.getName());
        System.out.println("Scenario Status: Failed");
        System.out.println("Error Message: " + scenario.getStatus());
        System.out.println("Timestamp: " + LocalDateTime.now());
    }
}