package com.example.organiser_m;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventChecker
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramIntent = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(1, "");
    NotificationHandler localNotificationHandler = new NotificationHandler(paramContext);
    Object localObject = new FileHandler("OM_data", paramContext);
    new ArrayList();
    Calendar localCalendar = Calendar.getInstance();
    ((FileHandler)localObject).loadEventsFromFile();
    List localList = ((FileHandler)localObject).getEventsByDay(localCalendar);
    localList.addAll(((FileHandler)localObject).getRepeatingEventsByDay(localCalendar));
    localObject = AppWidgetManager.getInstance(paramContext);
    ((AppWidgetManager)localObject).notifyAppWidgetViewDataChanged(((AppWidgetManager)localObject).getAppWidgetIds(new ComponentName(paramContext, WidgetProvider.class)), 2131099700);
    paramContext = PreferenceManager.getDefaultSharedPreferences(paramContext);
    boolean bool;
    int i;
    if (paramContext.getBoolean("pref_key_receive_notifications", true))
    {
      paramIntent.acquire();
      localNotificationHandler.clearNotifications();
      bool = paramContext.getBoolean("pref_key_clock_type", false);
      i = localList.size() - 1;
      if (i < 0) {
        paramIntent.release();
      }
    }
    else
    {
      return;
    }
    int j = ((Event)localList.get(i)).cal.get(11);
    if ((((Event)localList.get(i)).cal.get(12) / 60 + j > localCalendar.get(11) + localCalendar.get(12) / 60) || (((Event)localList.get(i)).type == 1))
    {
      if (((Event)localList.get(i)).type != 1) {
        break label295;
      }
      localNotificationHandler.addNotification("Memo", ((Event)localList.get(i)).title);
    }
    for (;;)
    {
      i -= 1;
      break;
      label295:
      if ((((Event)localList.get(i)).type == 2) && (!((Event)localList.get(i)).done) && (((Event)localList.get(i)).cal.before(Calendar.getInstance())))
      {
        if (bool)
        {
          if (((Event)localList.get(i)).cal.get(11) > 12) {
            localNotificationHandler.addNotification("Deadline: " + String.valueOf(((Event)localList.get(i)).cal.get(11) - 12) + ":" + String.valueOf(((Event)localList.get(i)).cal.get(12)) + " PM", ((Event)localList.get(i)).title);
          } else {
            localNotificationHandler.addNotification("Deadline: " + String.valueOf(((Event)localList.get(i)).cal.get(11)) + ":" + String.valueOf(((Event)localList.get(i)).cal.get(12)) + " AM", ((Event)localList.get(i)).title);
          }
        }
        else {
          localNotificationHandler.addNotification("Deadline: " + String.valueOf(((Event)localList.get(i)).cal.get(11)) + ":" + String.valueOf(((Event)localList.get(i)).cal.get(12)), ((Event)localList.get(i)).title);
        }
      }
      else if (bool)
      {
        if (((Event)localList.get(i)).cal.get(11) > 12) {
          localNotificationHandler.addNotification(String.valueOf(((Event)localList.get(i)).cal.get(11) - 12) + ":" + String.valueOf(((Event)localList.get(i)).cal.get(12)) + " PM", ((Event)localList.get(i)).title);
        } else {
          localNotificationHandler.addNotification(String.valueOf(((Event)localList.get(i)).cal.get(11)) + ":" + String.valueOf(((Event)localList.get(i)).cal.get(12)) + " AM", ((Event)localList.get(i)).title);
        }
      }
      else {
        localNotificationHandler.addNotification(String.valueOf(((Event)localList.get(i)).cal.get(11)) + ":" + String.valueOf(((Event)localList.get(i)).cal.get(12)), ((Event)localList.get(i)).title);
      }
    }
  }
  
  public void start(Context paramContext)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    paramContext = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, EventChecker.class), 0);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, 1);
    localCalendar.set(11, 0);
    localCalendar.set(12, 30);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    localAlarmManager.setRepeating(0, localCalendar.getTimeInMillis(), 86400000L, paramContext);
    try
    {
      paramContext.send();
      return;
    }
    catch (PendingIntent.CanceledException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void stop(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent(paramContext, EventChecker.class), 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
    new NotificationHandler(paramContext).clearNotifications();
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\EventChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */