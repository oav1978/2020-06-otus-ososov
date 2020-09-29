package ru.otus.ososov.testframework.model;

import java.lang.reflect.Method;

public class TestResult {
    private final Method method;
    private boolean passed;

    public TestResult(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
