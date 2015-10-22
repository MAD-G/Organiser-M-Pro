package com.example.organiser_m;

import android.content.Intent;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class WidgetService
  extends RemoteViewsService
{
  public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent paramIntent)
  {
    return new WidgetViewsFactory(getApplicationContext(), paramIntent);
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\WidgetService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */