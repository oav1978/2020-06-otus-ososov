package ru.otus.ososov;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ososov
 * Created 20.07.2020
 */
public class GcTest {

    public static void main(String[] args) throws Exception {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = new TestNotificationListener(gcbean.getName());
            emitter.addNotificationListener(listener, null, null);
        }
        TestGC mbean = new TestGC(1);
        mbean.run();
    }

}
