package ru.otus.ososov.testframework;

import ru.otus.ososov.testframework.context.After;
import ru.otus.ososov.testframework.context.Before;
import ru.otus.ososov.testframework.context.Test;
import ru.otus.ososov.testframework.service.TestResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunner {

    public static void test(Class<?> clazz){
        System.out.println("Runing "+clazz);
        Object obj=createObject(clazz);
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> beforeMethods = getBeforeMethods(declaredMethods)
                .stream()
                .sorted(new Comparator <Method> (){
                    @Override
                    public int compare(Method m0, Method m1) {
                        return m0.getAnnotation(Before.class).order()-m1.getAnnotation(Before.class).order();
                    }
                })
                .collect(Collectors.toList());
        List<Method> afterMethods = getAfterMethods(declaredMethods)
                .stream()
                .sorted(new Comparator<Method>() {
                    @Override
                    public int compare(Method o1, Method o2) {
                        return o1.getAnnotation(After.class).order()-o2.getAnnotation(After.class).order();
                    }
                })
                .collect(Collectors.toList());
        ArrayList<Method> testMethods = getTestMethods(declaredMethods);
        ArrayList<TestResult> resultsList=new ArrayList<>();
        for (Method testMethod:testMethods) {
            TestResult testResult = new TestResult(testMethod);
            resultsList.add(testResult);
            if ((executeMethods(beforeMethods,obj))&&
                    (executeMethod(testMethod,obj))&&
                    (executeMethods(afterMethods, obj))) {
                testResult.setPassed(true);
            }
        }
        int passedCount = resultsList.stream().filter(value -> value.isPassed()).collect(Collectors.toList()).size();
        int failureCount=resultsList.size()-passedCount;
        System.out.println("Test methods run: "+resultsList.size()+", Passed: "+passedCount+" Failures: "+failureCount);
    }

    private static boolean executeMethods(List<Method> methods,Object obj){
        for (Method method:methods){
            boolean doSetAccess=false;
            if (!method.canAccess(obj)){
                doSetAccess=true;
            }
            if (((doSetAccess)&&(method.trySetAccessible()))||(!doSetAccess)) {
                try {
                    method.invoke(obj);
                    if (doSetAccess)method.setAccessible(false);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return false;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return false;
                }
            } else return false;
        }
        return true;
    }

    private static boolean executeMethod(Method method,Object obj){
        boolean doSetAccess=false;
        if (!method.canAccess(obj)){
            doSetAccess=true;
        }
        if (((doSetAccess)&&(method.trySetAccessible()))||(!doSetAccess)) {
            try {
                method.invoke(obj);
                if (doSetAccess)method.setAccessible(false);
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                if (doSetAccess)method.setAccessible(false);
                return false;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                if (doSetAccess)method.setAccessible(false);
                return false;
            }
        } else return false;
    }

    private static Object createObject(Class<?> clazz){
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Method> getBeforeMethods(Method[] declaredMethods){
        ArrayList<Method> results=new ArrayList<>();
        for (int i=0;i<declaredMethods.length;i++){
            if (declaredMethods[i].isAnnotationPresent(Before.class)){
                results.add(declaredMethods[i]);
            }
        }
        return results;
    }

    private static ArrayList<Method> getAfterMethods(Method[] declaredMethods){
        ArrayList<Method> results=new ArrayList<>();
        for (int i=0;i<declaredMethods.length;i++){
            if (declaredMethods[i].isAnnotationPresent(After.class)){
                results.add(declaredMethods[i]);
            }
        }
        return results;
    }

    private static ArrayList<Method> getTestMethods(Method[] declaredMethods){
        ArrayList<Method> results=new ArrayList<>();
        for (int i=0;i<declaredMethods.length;i++){
            if (declaredMethods[i].isAnnotationPresent(Test.class)){
                results.add(declaredMethods[i]);
            }
        }
        return results;
    }
}
