package com.roomorama.caldroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class WeekdayArrayAdapter
  extends ArrayAdapter<String>
{
  public static int textColor = -3355444;
  
  public WeekdayArrayAdapter(Context paramContext, int paramInt, List<String> paramList)
  {
    super(paramContext, paramInt, paramList);
  }
  
  public boolean areAllItemsEnabled()
  {
    return false;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramView = (TextView)super.getView(paramInt, paramView, paramViewGroup);
    if (((String)getItem(paramInt)).length() <= 3) {
      paramView.setTextSize(2, 12.0F);
    }
    for (;;)
    {
      paramView.setTextColor(textColor);
      paramView.setGravity(17);
      return paramView;
      paramView.setTextSize(2, 11.0F);
    }
  }
  
  public boolean isEnabled(int paramInt)
  {
    return false;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\roomorama\caldroid\WeekdayArrayAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */