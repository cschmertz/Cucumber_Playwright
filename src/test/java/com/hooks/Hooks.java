package com.hooks;

import com.context.ScenarioContext;
import com.managers.DriverManager;
import com.utils.playwright.TraceUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class Hooks {
    private DriverManager driverManager;
    private ScenarioContext scenarioContext;
    private static final String TRACE_DIR = "traces";

    public Hooks(ScenarioContext context) {
        this.scenarioContext = context;
    }

    @Before
    public void setUp(Scenario scenario) {
        driverManager = new DriverManager();
        scenarioContext.setPage(driverManager.getPage());

        // Start tracing
        String traceName = scenario.getName().replaceAll("\\s+", "_").toLowerCase();
        driverManager.startTracing(traceName);
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logFailureDetails(scenario);
            }

            // Stop tracing and save trace file
            String traceName = scenario.getName().replaceAll("\\s+", "_").toLowerCase();
            Path tracePath = driverManager.stopTracing(traceName);

            // Attach trace file to scenario
            scenario.attach(tracePath.toAbsolutePath().toString(), "text/plain", "Trace File Path");
        } finally {
            if (driverManager != null) {
                driverManager.closeBrowser();
            }
        }

        // Clean up old traces
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
