package br.com.test.kaioh.addeventcalendar.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import br.com.test.kaioh.addeventcalendar.R;

/**
 * Created by @autor Kaio Henrique on 27/02/2018.
 */

public class CalendarUtil{

    private static final String TAG = CalendarUtil.class.getSimpleName();
    private Context ctx;

    public CalendarUtil(Context ctx){
        this.ctx = ctx;
    }

    public void addEventToCalendar() throws Exception {
        Locale locale = new Locale("pt", "BR");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);

        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = ctx.getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, onTransformResToString(R.string.title_event));
        values.put(CalendarContract.Events.DESCRIPTION, onTransformResToString(R.string.description_event));
        values.put(CalendarContract.Events.ALL_DAY, 0);
        values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis() + 60 * 1000);
        Log.d(TAG, "Data Inicio => " + sdf.format(cal.getTime()));
        values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 2 * 60 * 1000);
        Log.d(TAG, "Data Fim => " + sdf.format(cal.getTime()));
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);

        Uri event = cr.insert(EVENTS_URI, values);

        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 10);
        cr.insert(REMINDERS_URI, values);

    }

    private String getCalendarUriBase(boolean eventUri){
        Uri calendarUri = null;
        try {
            calendarUri = (eventUri) ? Uri.parse("content://com.android.calendar/") :
                                       Uri.parse("content://com.android.calendar/events");
        } catch (Exception e){
            e.printStackTrace();
        }
        return calendarUri.toString();
    }

    private String onTransformResToString(int resource){
        String string = "";
        string = ctx.getResources().getString(resource);
        return string;
    }
}
