package com.listeners;

import com.managers.ExtentReportManager;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class ReportListener implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
        publisher.registerHandlerFor(TestCaseStarted.class, this::caseStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
    }

    private void runStarted(TestRunStarted event) {
        ExtentReportManager.initReport();
    }

    private void caseStarted(TestCaseStarted event) {
        ExtentReportManager.createTest(event.getTestCase().getName());
    }

    private void stepFinished(TestStepFinished event) {
        if (event.getResult().getStatus().isOk()) {
            ExtentReportManager.logPass(event.getTestStep().getCodeLocation());
        } else {
            ExtentReportManager.logFail(event.getTestStep().getCodeLocation());
        }
    }

    private void runFinished(TestRunFinished event) {
        ExtentReportManager.flushReport();
    }
}
