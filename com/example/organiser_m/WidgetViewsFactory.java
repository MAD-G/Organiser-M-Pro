package com.example.organiser_m;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import java.util.Calendar;
import java.util.List;

public class WidgetViewsFactory
  implements RemoteViewsService.RemoteViewsFactory
{
  List<Event> all;
  private int appWidgetId;
  Context context;
  
  public WidgetViewsFactory(Context paramContext, Intent paramIntent)
  {
    this.context = paramContext;
    this.appWidgetId = paramIntent.getIntExtra("appWidgetId", 0);
    initialize_Data();
  }
  
  private void initialize_Data()
  {
    Calendar localCalendar = Calendar.getInstance();
    FileHandler localFileHandler = new FileHandler("OM_data", this.context);
    localFileHandler.loadEventsFromFile();
    this.all = localFileHandler.getEventsByDay(localCalendar);
    this.all.addAll(localFileHandler.getRepeatingEventsByDay(localCalendar));
  }
  
  public int getCount()
  {
    return this.all.size();
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public RemoteViews getLoadingView()
  {
    return null;
  }
  
  public RemoteViews getViewAt(int paramInt)
  {
    RemoteViews localRemoteViews = new RemoteViews(this.context.getPackageName(), 2130903054);
    if (((Event)this.all.get(paramInt)).cal.get(12) < 10) {}
    for (Object localObject = "0" + String.valueOf(((Event)this.all.get(paramInt)).cal.get(12));; localObject = String.valueOf(((Event)this.all.get(paramInt)).cal.get(12)))
    {
      localRemoteViews.setTextViewText(2131099697, String.valueOf(((Event)this.all.get(paramInt)).cal.get(11)) + ":" + (String)localObject);
      localRemoteViews.setTextViewText(2131099698, ((Event)this.all.get(paramInt)).title);
      localRemoteViews.setTextViewText(2131099699, ((Event)this.all.get(paramInt)).desc);
      localObject = new Bundle();
      ((Bundle)localObject).putInt("com.example.organiser_m.EXTRA_ITEM", paramInt);
      Intent localIntent = new Intent();
      localIntent.putExtras((Bundle)localObject);
      localRemoteViews.setOnClickFillInIntent(2131099698, localIntent);
      localRemoteViews.setOnClickFillInIntent(2131099699, localIntent);
      localRemoteViews.setOnClickFillInIntent(2131099697, localIntent);
      return localRemoteViews;
    }
  }
  
  public int getViewTypeCount()
  {
    return 1;
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  public void onCreate() {}
  
  public void onDataSetChanged()
  {
    initialize_Data();
    Log.i("WidgetViewFactory", "Datasetchanged");
  }
  
  public void onDestroy()
  {
    this.all.clear();
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\WidgetViewsFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */