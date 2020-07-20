package ru.otus.ososov;

import ru.otus.ososov.model.DIYarrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

/**
 * @author Ososov
 * Created 20.07.2020
 */
public class Starter {
    private static int capacity=30;

    public static void main(String[] args) {
        System.out.println("Lesson03: own implementation List<T>");
        DIYarrayList<Integer> dataList=new DIYarrayList<>();
        for (int i=0;i<capacity;i++){
            dataList.add(Integer.valueOf(i));
        }
        System.out.println("Заполняем наш массив dataList:");
        printArray(dataList);
        System.out.println("Заполнили. Создаем новый массив dataList1.");
        DIYarrayList<Integer> dataList1=new DIYarrayList<>();
        System.out.println("Проверяем работу метода Collections.addAll(Collection<? super T> c, T... elements)");
        Integer[] intArray=new Integer[capacity];
        for (int i=0;i<capacity;i++){
            intArray[i]=Integer.valueOf(capacity+i);
        }
        Collections.addAll(dataList1,intArray);
        System.out.println("Выведем полученный массив dataList1");
        printArray(dataList1);
        System.out.println("Проверяем работу метода Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)");
        DIYarrayList<Integer> dataList2=new DIYarrayList<>(capacity);
        System.out.println("Выведем полученный массив dataList2 путем копирования с dataList1");
        Collections.copy(dataList2,dataList1);
        printArray(dataList2);
        System.out.println("Проверяем работу метода Collections.static <T> void sort(List<T> list, Comparator<? super T> c)");
        System.out.println("Отсортируем массив dataList2 в обратном порядке");
        Comparator<Integer> comparatorInteger=new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        };
        Collections.sort(dataList2, comparatorInteger);
        System.out.println("Выведем полученный массив dataList2 после сортировки");
        printArray(dataList2);
    }

    private static <T> void printArray(DIYarrayList<T> valueList) {
        String s="{";
        for (T obj:valueList)s+=obj.toString()+",";
        if (s.endsWith(",")) {
            s = s.substring(0, s.length() - 1) + "}";
        }
        System.out.println(s);
    }

}
