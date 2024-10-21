package com.runners;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Cucumber
public class TestRunner {
}


