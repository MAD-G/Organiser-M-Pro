package com.example.organiser_m;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ResetEvents
  extends BroadcastReceiver
{
  EventChecker ev = new EventChecker();
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
      this.ev.start(paramContext);
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\ResetEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */