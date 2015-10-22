package com.antonyt.infiniteviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import hirondelle.date4j.DateTime;
import java.util.ArrayList;

public class InfiniteViewPager
  extends ViewPager
{
  public static final int OFFSET = 1000;
  private ArrayList<DateTime> datesInMonth;
  private boolean enabled = true;
  private int rowHeight = 0;
  private boolean sixWeeksInCalendar = false;
  
  public InfiniteViewPager(Context paramContext)
  {
    super(paramContext);
  }
  
  public InfiniteViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ArrayList<DateTime> getDatesInMonth()
  {
    return this.datesInMonth;
  }
  
  public boolean isEnabled()
  {
    return this.enabled;
  }
  
  public boolean isSixWeeksInCalendar()
  {
    return this.sixWeeksInCalendar;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.enabled) {
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    return false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int j = this.datesInMonth.size() / 7;
    int i;
    if (View.MeasureSpec.getMode(paramInt2) == Integer.MIN_VALUE)
    {
      i = 1;
      int k = getMeasuredHeight();
      paramInt2 = paramInt1;
      if (i != 0)
      {
        paramInt2 = paramInt1;
        if (this.rowHeight == 0)
        {
          paramInt1 = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
          paramInt2 = paramInt1;
          if (getChildCount() > 0)
          {
            View localView = getChildAt(0);
            localView.measure(paramInt1, View.MeasureSpec.makeMeasureSpec(k, Integer.MIN_VALUE));
            this.rowHeight = (localView.getMeasuredHeight() / j);
            paramInt2 = paramInt1;
          }
        }
      }
      if (!this.sixWeeksInCalendar) {
        break label137;
      }
    }
    label137:
    for (paramInt1 = this.rowHeight * 6;; paramInt1 = this.rowHeight * j)
    {
      super.onMeasure(paramInt2, View.MeasureSpec.makeMeasureSpec(paramInt1 + 3, 1073741824));
      return;
      i = 0;
      break;
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.enabled) {
      return super.onTouchEvent(paramMotionEvent);
    }
    return false;
  }
  
  public void setAdapter(PagerAdapter paramPagerAdapter)
  {
    super.setAdapter(paramPagerAdapter);
    setCurrentItem(1000);
  }
  
  public void setDatesInMonth(ArrayList<DateTime> paramArrayList)
  {
    this.datesInMonth = paramArrayList;
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.enabled = paramBoolean;
  }
  
  public void setSixWeeksInCalendar(boolean paramBoolean)
  {
    this.sixWeeksInCalendar = paramBoolean;
    this.rowHeight = 0;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\antonyt\infiniteviewpager\InfiniteViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */