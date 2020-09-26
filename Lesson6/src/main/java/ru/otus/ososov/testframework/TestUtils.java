package ru.otus.ososov.testframework;

public class TestUtils {

    public static void AssertTrue(boolean value){
        if (!value)throw new TestFrameWorkException("result not true");
    }

    public static void AssertFalse(boolean value){
        if (value)throw new TestFrameWorkException("result not false");
    }
}
