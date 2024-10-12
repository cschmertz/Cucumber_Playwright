package com.runners;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.ConfigurationParameter;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
//import static io.cucumber.junit.platform.engine.Constants.TAGS_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Path to your feature files
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "stepDefinitions") // Step definitions package
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-reports/Cucumber.json, junit:target/cucumber-reports/Cucumber.xml, html:target/cucumber-report.html, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:, listeners.TestFailureListener") // Report generation and custom listeners
//@ConfigurationParameter(key = TAGS_PROPERTY_NAME, value = "@Google") // Filters scenarios based on the tag
public class TestRunner {
}

