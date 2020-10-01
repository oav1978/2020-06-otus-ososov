package ru.otus.ososov;

import java.util.ArrayList;

public class TestGC {

    private final int sleepTime;
    private final int counter=10000;
    private ArrayList<Object> dataList=new ArrayList<>();
    private boolean doStop=false;

    public TestGC(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void stop(){
        doStop=true;
    }

    public void run() throws InterruptedException {
        while (!doStop) {
            for (int i = 0; i < counter; i++) {
                dataList.add(new String(Integer.toString(i)));
            }
            for (int i = 0; i < counter/2; i++) {
                dataList.remove(dataList.size()-1-i);
            }
            Thread.sleep(sleepTime);
        }
    }
}
