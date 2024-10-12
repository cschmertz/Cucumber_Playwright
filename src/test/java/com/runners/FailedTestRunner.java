package com.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.stepDefinitions,com.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/cucumber-reports,rerun:target/failed_scenarios.txt")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "@target/failed_scenarios.txt")
public class FailedTestRunner {
}
