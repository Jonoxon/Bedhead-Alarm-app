package jono.bedheadalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jono on 6/07/2016.
 * I GOT NO CLUE
 */

public class AlarmTrigger {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    // i think i need something like this but iz dunno
    private AlarmTrigger() { }

    // this code is quite messed...
    //https://developer.android.com/training/scheduling/alarms.html
    public void TimeCreate (int hour, int min, int daysofweek){
        //idk why these context and AlarmReceiver things are red :(
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        for (Integer day : GetDays(daysofweek)) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.DAY_OF_WEEK, day);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);

            // set inexact repeating alarm with interval of 7 days
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY*7, alarmIntent);
            }

    }

    //https://developer.android.com/training/location/geofencing.html#RequestGeofences
    //need to specify intent
    public void GPSCreate (double lat, double lon, int radius, int trigger) {

        mGeofenceList.add(new Geofence.Builder()
                //UUID here eventually
                .setRequestId(entry.getKey())

                .setCircularRegion(
                        lat,
                        lon,
                        radius
                )
                // forever and ever ?
                .setExpirationDuration(-1)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    public int getNextDay(int days) {
        //hm... uh......
        Calendar calendar = Calendar.getInstance();
        //goes to the day after the current one
        int checkday = calendar.get(Calendar.DAY_OF_WEEK)+1;

        //need something to check it with?
        int nextday = checkday;

        while ((nextday & Days.checkday) == 0) {
            // somehow this works
            checkday += 1;
        }
        return checkday;
    }


    public List<Integer> GetDays (int days) {
        // this can be deleted eventually...
        final List<Integer> daysofweek = new ArrayList<>();
        if ((days & Days.SUNDAY) != 0) {
            daysofweek.add(1);
        }
        if ((days & Days.MONDAY) != 0) {
            daysofweek.add(2);
        }
        if ((days & Days.TUESDAY) != 0) {
            daysofweek.add(3);
        }
        if ((days & Days.WEDNESDAY) != 0) {
            daysofweek.add(4);
        }
        if ((days & Days.THURSDAY) != 0) {
            daysofweek.add(5);
        }
        if ((days & Days.FRIDAY) != 0) {
            daysofweek.add(6);
        }
        if ((days & Days.SATURDAY) != 0) {
            daysofweek.add(7);
        }
            return daysofweek;
    }
}
