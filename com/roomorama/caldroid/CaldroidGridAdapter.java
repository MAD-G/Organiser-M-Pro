package com.roomorama.caldroid;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.caldroid.R.color;
import com.caldroid.R.drawable;
import com.caldroid.R.layout;
import hirondelle.date4j.DateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class CaldroidGridAdapter
  extends BaseAdapter
{
  protected HashMap<String, Object> caldroidData;
  protected Context context;
  protected ArrayList<DateTime> datetimeList;
  protected ArrayList<DateTime> disableDates;
  protected HashMap<DateTime, Integer> disableDatesMap = new HashMap();
  protected HashMap<String, Object> extraData;
  protected DateTime maxDateTime;
  protected DateTime minDateTime;
  protected int month;
  protected Resources resources;
  protected ArrayList<DateTime> selectedDates;
  protected HashMap<DateTime, Integer> selectedDatesMap = new HashMap();
  protected boolean sixWeeksInCalendar;
  protected int startDayOfWeek;
  protected DateTime today;
  protected int year;
  
  public CaldroidGridAdapter(Context paramContext, int paramInt1, int paramInt2, HashMap<String, Object> paramHashMap1, HashMap<String, Object> paramHashMap2)
  {
    this.month = paramInt1;
    this.year = paramInt2;
    this.context = paramContext;
    this.caldroidData = paramHashMap1;
    this.extraData = paramHashMap2;
    this.resources = paramContext.getResources();
    populateFromCaldroidData();
  }
  
  private void populateFromCaldroidData()
  {
    this.disableDates = ((ArrayList)this.caldroidData.get("disableDates"));
    Iterator localIterator;
    if (this.disableDates != null)
    {
      this.disableDatesMap.clear();
      localIterator = this.disableDates.iterator();
      if (localIterator.hasNext()) {}
    }
    else
    {
      this.selectedDates = ((ArrayList)this.caldroidData.get("selectedDates"));
      if (this.selectedDates != null)
      {
        this.selectedDatesMap.clear();
        localIterator = this.selectedDates.iterator();
      }
    }
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.minDateTime = ((DateTime)this.caldroidData.get("_minDateTime"));
        this.maxDateTime = ((DateTime)this.caldroidData.get("_maxDateTime"));
        this.startDayOfWeek = ((Integer)this.caldroidData.get("startDayOfWeek")).intValue();
        this.sixWeeksInCalendar = ((Boolean)this.caldroidData.get("sixWeeksInCalendar")).booleanValue();
        this.datetimeList = CalendarHelper.getFullWeeks(this.month, this.year, this.startDayOfWeek, this.sixWeeksInCalendar);
        return;
        localDateTime = (DateTime)localIterator.next();
        this.disableDatesMap.put(localDateTime, Integer.valueOf(1));
        break;
      }
      DateTime localDateTime = (DateTime)localIterator.next();
      this.selectedDatesMap.put(localDateTime, Integer.valueOf(1));
    }
  }
  
  protected void customizeTextView(int paramInt, TextView paramTextView)
  {
    paramTextView.setTextColor(-16777216);
    DateTime localDateTime = (DateTime)this.datetimeList.get(paramInt);
    if (localDateTime.getMonth().intValue() != this.month) {
      paramTextView.setTextColor(this.resources.getColor(R.color.caldroid_darker_gray));
    }
    int j = 0;
    int i = 0;
    if (((this.minDateTime != null) && (localDateTime.lt(this.minDateTime))) || ((this.maxDateTime != null) && (localDateTime.gt(this.maxDateTime))) || ((this.disableDates != null) && (this.disableDatesMap.containsKey(localDateTime))))
    {
      paramTextView.setTextColor(CaldroidFragment.disabledTextColor);
      if (CaldroidFragment.disabledBackgroundDrawable == -1)
      {
        paramTextView.setBackgroundResource(R.drawable.disable_cell);
        paramInt = j;
        if (localDateTime.equals(getToday()))
        {
          paramTextView.setBackgroundResource(R.drawable.red_border_gray_bg);
          paramInt = j;
        }
        label156:
        if ((this.selectedDates == null) || (!this.selectedDatesMap.containsKey(localDateTime))) {
          break label286;
        }
        if (CaldroidFragment.selectedBackgroundDrawable == -1) {
          break label269;
        }
        paramTextView.setBackgroundResource(CaldroidFragment.selectedBackgroundDrawable);
        label189:
        paramTextView.setTextColor(CaldroidFragment.selectedTextColor);
        label196:
        if ((paramInt != 0) && (i != 0))
        {
          if (!localDateTime.equals(getToday())) {
            break label291;
          }
          paramTextView.setBackgroundResource(R.drawable.red_border);
        }
      }
    }
    for (;;)
    {
      paramTextView.setText(localDateTime.getDay());
      setCustomResources(localDateTime, paramTextView, paramTextView);
      return;
      paramTextView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
      break;
      paramInt = 1;
      break label156;
      label269:
      paramTextView.setBackgroundColor(this.resources.getColor(R.color.caldroid_sky_blue));
      break label189;
      label286:
      i = 1;
      break label196;
      label291:
      paramTextView.setBackgroundResource(R.drawable.cell_bg);
    }
  }
  
  public HashMap<String, Object> getCaldroidData()
  {
    return this.caldroidData;
  }
  
  public int getCount()
  {
    return this.datetimeList.size();
  }
  
  public ArrayList<DateTime> getDatetimeList()
  {
    return this.datetimeList;
  }
  
  public ArrayList<DateTime> getDisableDates()
  {
    return this.disableDates;
  }
  
  public HashMap<String, Object> getExtraData()
  {
    return this.extraData;
  }
  
  public Object getItem(int paramInt)
  {
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public DateTime getMaxDateTime()
  {
    return this.maxDateTime;
  }
  
  public DateTime getMinDateTime()
  {
    return this.minDateTime;
  }
  
  public ArrayList<DateTime> getSelectedDates()
  {
    return this.selectedDates;
  }
  
  protected DateTime getToday()
  {
    if (this.today == null) {
      this.today = CalendarHelper.convertDateToDateTime(new Date());
    }
    return this.today;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)this.context.getSystemService("layout_inflater");
    paramViewGroup = (TextView)paramView;
    if (paramView == null) {
      paramViewGroup = (TextView)localLayoutInflater.inflate(R.layout.date_cell, null);
    }
    customizeTextView(paramInt, paramViewGroup);
    return paramViewGroup;
  }
  
  public void setAdapterDateTime(DateTime paramDateTime)
  {
    this.month = paramDateTime.getMonth().intValue();
    this.year = paramDateTime.getYear().intValue();
    this.datetimeList = CalendarHelper.getFullWeeks(this.month, this.year, this.startDayOfWeek, this.sixWeeksInCalendar);
  }
  
  public void setCaldroidData(HashMap<String, Object> paramHashMap)
  {
    this.caldroidData = paramHashMap;
    populateFromCaldroidData();
  }
  
  protected void setCustomResources(DateTime paramDateTime, View paramView, TextView paramTextView)
  {
    Object localObject = (HashMap)this.caldroidData.get("_backgroundForDateTimeMap");
    if (localObject != null)
    {
      localObject = (Integer)((HashMap)localObject).get(paramDateTime);
      if (localObject != null) {
        paramView.setBackgroundResource(((Integer)localObject).intValue());
      }
    }
    paramView = (HashMap)this.caldroidData.get("_textColorForDateTimeMap");
    if (paramView != null)
    {
      paramDateTime = (Integer)paramView.get(paramDateTime);
      if (paramDateTime != null) {
        paramTextView.setTextColor(this.resources.getColor(paramDateTime.intValue()));
      }
    }
  }
  
  public void setDisableDates(ArrayList<DateTime> paramArrayList)
  {
    this.disableDates = paramArrayList;
  }
  
  public void setExtraData(HashMap<String, Object> paramHashMap)
  {
    this.extraData = paramHashMap;
  }
  
  public void setMaxDateTime(DateTime paramDateTime)
  {
    this.maxDateTime = paramDateTime;
  }
  
  public void setMinDateTime(DateTime paramDateTime)
  {
    this.minDateTime = paramDateTime;
  }
  
  public void setSelectedDates(ArrayList<DateTime> paramArrayList)
  {
    this.selectedDates = paramArrayList;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\roomorama\caldroid\CaldroidGridAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */