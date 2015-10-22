package com.example.organiser_m;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;

public class NotificationHandler
{
  Context context;
  int id;
  NotificationManager nm;
  
  NotificationHandler(Context paramContext)
  {
    this.context = paramContext;
    this.id = 0;
    this.nm = ((NotificationManager)this.context.getSystemService("notification"));
  }
  
  public void addNotification(String paramString1, String paramString2)
  {
    Object localObject = new Intent(this.context, MainActivity.class);
    localObject = PendingIntent.getActivity(this.context, 0, (Intent)localObject, 0);
    paramString1 = new NotificationCompat.Builder(this.context).setContentTitle(paramString1).setContentText(paramString2).setSmallIcon(2130837539).setContentIntent((PendingIntent)localObject).build();
    this.nm.notify(this.id, paramString1);
    this.id += 1;
  }
  
  public void clearLastNotification()
  {
    this.nm.cancel(this.id);
    this.id -= 1;
  }
  
  public void clearNotifications()
  {
    this.nm.cancelAll();
    this.id = 0;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\NotificationHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */