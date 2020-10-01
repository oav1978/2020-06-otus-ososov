package ru.otus.ososov;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.ArrayList;
import java.util.OptionalDouble;

public class TestNotificationListener implements NotificationListener {
    ArrayList<Long> timeList=new ArrayList<>();
    long maxTime=0;
    long minTime=Long.MAX_VALUE;
    private final String gcName;

    public TestNotificationListener(String gcName) {
        this.gcName = gcName;
    }

    /**
     * Invoked when a JMX notification occurs.
     * The implementation of this method should return as soon as possible, to avoid
     * blocking its notification broadcaster.
     *
     * @param notification The notification.
     * @param handback     An opaque object which helps the listener to associate
     *                     information regarding the MBean emitter. This object is passed to the
     *                     addNotificationListener call and resent, without modification, to the
     */
    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            String gcName = info.getGcName();
            String gcAction = info.getGcAction();
            String gcCause = info.getGcCause();
            long startTime = info.getGcInfo().getStartTime();
            long duration = info.getGcInfo().getDuration();
            if (duration>maxTime)maxTime=duration;
            if (duration<minTime)minTime=duration;
            timeList.add(duration);
            OptionalDouble average = timeList
                    .stream()
                    .mapToLong(a -> a)
                    .average();
            if (average.isPresent())
            System.out.println(gcName+"---  Min duration: "+minTime+"ms, Max duration: "+maxTime+"ms, Avg duration: "+average.getAsDouble()+"ms");
        }
    }
}
