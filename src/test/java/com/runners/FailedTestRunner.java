package com.runners;

import io.cucumber.junit.platform.engine.Cucumber;

@Cucumber
public class FailedTestRunner {

    static {
        System.setProperty("cucumber.features", "@target/failed_scenarios.txt");
    }
}

