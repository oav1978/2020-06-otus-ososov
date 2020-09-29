package ru.otus.ososov;

import ru.otus.ososov.testframework.annotations.After;
import ru.otus.ososov.testframework.annotations.Before;
import ru.otus.ososov.testframework.annotations.Test;

import static ru.otus.ososov.testframework.TestUtils.AssertTrue;
import static ru.otus.ososov.testframework.TestUtils.AssertFalse;

import java.util.ArrayList;

public class PasswordValidatorTest {

    private String phrase1;
    private String phrase2;
    private String phrase3;
    private String phrase4;
    private ArrayList<String> results = new ArrayList<>();
    private PasswordValidator passwordValidator;

    @Before(order = 2)
    private void before1() {
        System.out.println("execute before1 method");
        phrase1 = "abcde1234";
        phrase2 = "abCD1@";
    }

    @Before(order = 1)
    private void before2() {
        System.out.println("execute before2 method");
        phrase3 = "abcde23!";
        phrase4 = "Abcde23!";
    }

    @Test
    private void test1() {
        //before();
        System.out.println("execute test1 method");
        passwordValidator = new PasswordValidator(true);
        AssertFalse(passwordValidator.validate(phrase1));
        AssertTrue(passwordValidator.validate(phrase2));
        AssertFalse(passwordValidator.validate(phrase3));
        AssertTrue(passwordValidator.validate(phrase4));
    }

    @Test
    private void test2() {
        //before();
        System.out.println("execute test2 method");
        passwordValidator = new PasswordValidator(false);
        AssertFalse(passwordValidator.validate(phrase1));
        AssertTrue(passwordValidator.validate(phrase2));
        AssertFalse(passwordValidator.validate(phrase3));
        AssertTrue(passwordValidator.validate(phrase4));
    }

    @After(order = 1)
    private void after() {
        System.out.println("Execute after method");
    }
}
