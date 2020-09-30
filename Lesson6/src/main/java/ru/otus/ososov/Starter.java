package ru.otus.ososov;

import ru.otus.ososov.testframework.TestRunner;

import java.util.*;

/**
 * @author Ososov
 * Created 20.07.2020
 */
public class Starter {

    public static void main(String[] args) {
        System.out.println("Starting our tests:");
        TestRunner.test(PasswordValidatorTest.class);
    }

}
