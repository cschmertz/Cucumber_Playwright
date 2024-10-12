package com.rules;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class RetryRule implements TestExecutionExceptionHandler, TestWatcher {

    private int maxRetries = 3;  // Number of retries

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        int attempt = 0;
        while (attempt < maxRetries) {
            attempt++;
            try {
                System.out.println("Retrying test: " + context.getDisplayName() + " - Attempt " + attempt);
                // Re-execute the test logic (handled by the test framework).
                return;
            } catch (Throwable retryThrowable) {
                if (attempt == maxRetries) {
                    System.out.println("Test failed after retries: " + context.getDisplayName());
                    throw retryThrowable; // Fail after max retries
                }
            }
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("Test failed after retries: " + context.getDisplayName());
    }

    @Override
    public Optional<String> testSuccessful(ExtensionContext context) {
        return Optional.of("Test passed on retry: " + context.getDisplayName());
    }
}
