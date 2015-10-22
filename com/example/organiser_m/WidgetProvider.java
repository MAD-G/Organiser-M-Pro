package com.example.organiser_m;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider
  extends AppWidgetProvider
{
  public void onDeleted(Context paramContext, int[] paramArrayOfInt)
  {
    super.onDeleted(paramContext, paramArrayOfInt);
  }
  
  public void onDisabled(Context paramContext)
  {
    super.onDisabled(paramContext);
  }
  
  public void onEnabled(Context paramContext)
  {
    super.onEnabled(paramContext);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(paramContext);
    if (paramIntent.getAction().equals("com.example.organiser_m.TOAST_ACTION"))
    {
      paramIntent.getIntExtra("appWidgetId", 0);
      int i = paramIntent.getIntExtra("com.example.organiser_m.EXTRA_ITEM", 0);
      Toast.makeText(paramContext, "Touched view " + i, 0).show();
    }
    for (;;)
    {
      super.onReceive(paramContext, paramIntent);
      return;
      if (paramIntent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
        localAppWidgetManager.notifyAppWidgetViewDataChanged(localAppWidgetManager.getAppWidgetIds(new ComponentName(paramContext, WidgetProvider.class)), 2131099700);
      }
    }
  }
  
  public void onUpdate(Context paramContext, AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt)
  {
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfInt.length)
      {
        super.onUpdate(paramContext, paramAppWidgetManager, paramArrayOfInt);
        return;
      }
      Intent localIntent1 = new Intent(paramContext, WidgetService.class);
      localIntent1.putExtra("appWidgetId", paramArrayOfInt[i]);
      localIntent1.setData(Uri.parse(localIntent1.toUri(1)));
      RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130903055);
      localRemoteViews.setRemoteAdapter(paramArrayOfInt[i], 2131099700, localIntent1);
      localRemoteViews.setEmptyView(2131099700, 2131099701);
      Intent localIntent2 = new Intent(paramContext, WidgetProvider.class);
      localIntent2.setAction("com.example.organiser_m.TOAST_ACTION");
      localIntent2.putExtra("appWidgetId", paramArrayOfInt[i]);
      localIntent1.setData(Uri.parse(localIntent1.toUri(1)));
      localRemoteViews.setPendingIntentTemplate(2131099700, PendingIntent.getBroadcast(paramContext, 0, localIntent2, 0));
      paramAppWidgetManager.updateAppWidget(paramArrayOfInt[i], localRemoteViews);
      i += 1;
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\WidgetProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */