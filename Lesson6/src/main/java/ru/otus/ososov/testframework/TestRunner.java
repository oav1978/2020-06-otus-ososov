package ru.otus.ososov.testframework;

import ru.otus.ososov.testframework.annotations.After;
import ru.otus.ososov.testframework.annotations.Before;
import ru.otus.ososov.testframework.annotations.Test;
import ru.otus.ososov.testframework.model.TestResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    public static void test(Class<?> clazz) {
        System.out.println("Runing " + clazz);
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> beforeMethods = getBeforeMethods(declaredMethods)
                .stream()
                .sorted(new Comparator<Method>() {
                    @Override
                    public int compare(Method m0, Method m1) {
                        return Integer.compare(m0.getAnnotation(Before.class).order(), m1.getAnnotation(Before.class).order());
                    }
                })
                .collect(Collectors.toList());
        List<Method> afterMethods = getAfterMethods(declaredMethods)
                .stream()
                .sorted(new Comparator<Method>() {
                    @Override
                    public int compare(Method o1, Method o2) {
                        return Integer.compare(o1.getAnnotation(After.class).order(), o2.getAnnotation(After.class).order());
                    }
                })
                .collect(Collectors.toList());
        ArrayList<Method> testMethods = getTestMethods(declaredMethods);
        ArrayList<TestResult> resultsList = new ArrayList<>();
        for (Method testMethod : testMethods) {
            TestResult testResult = new TestResult(testMethod);
            resultsList.add(testResult);
            Object obj = createObject(clazz);
            if ((executeMethods(beforeMethods, obj)) &&
                    (executeMethod(testMethod, obj)) &&
                    (executeMethods(afterMethods, obj))) {
                testResult.setPassed(true);
            }

        }
        int passedCount = resultsList.stream().filter(value -> value.isPassed()).collect(Collectors.toList()).size();
        int failureCount = resultsList.size() - passedCount;
        System.out.println("Test methods run: " + resultsList.size() + ", Passed: " + passedCount + " Failures: " + failureCount);
    }

    private static boolean executeMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            boolean doSetAccess = false;
            if (!method.canAccess(obj)) {
                doSetAccess = true;
            }
            if (((doSetAccess) && (method.trySetAccessible())) || (!doSetAccess)) {
                try {
                    method.invoke(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else return false;
        }
        return true;
    }

    private static boolean executeMethod(Method method, Object obj) {
        boolean doSetAccess = false;
        if (!method.canAccess(obj)) {
            doSetAccess = true;
        }
        if (((doSetAccess) && (method.trySetAccessible())) || (!doSetAccess)) {
            try {
                method.invoke(obj);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else return false;
    }

    private static Object createObject(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new TestFrameWorkException("Can't create object. Testing is breaking.");
    }

    private static ArrayList<Method> getBeforeMethods(Method[] declaredMethods) {
        ArrayList<Method> results = new ArrayList<>();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Before.class)) {
                results.add(method);
            }
        }
        return results;
    }

    private static ArrayList<Method> getAfterMethods(Method[] declaredMethods) {
        ArrayList<Method> results = new ArrayList<>();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(After.class)) {
                results.add(method);
            }
        }
        return results;
    }

    private static ArrayList<Method> getTestMethods(Method[] declaredMethods) {
        ArrayList<Method> results = new ArrayList<>();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Test.class)) {
                results.add(method);
            }
        }
        return results;
    }
}
