package com.roomorama.caldroid;

import android.view.View;
import java.util.Date;

public abstract class CaldroidListener
{
  public void onCaldroidViewCreated() {}
  
  public void onChangeMonth(int paramInt1, int paramInt2) {}
  
  public void onLongClickDate(Date paramDate, View paramView) {}
  
  public abstract void onSelectDate(Date paramDate, View paramView);
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\roomorama\caldroid\CaldroidListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */