package com.antonyt.infiniteviewpager;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class InfinitePagerAdapter
  extends PagerAdapter
{
  private PagerAdapter adapter;
  
  public InfinitePagerAdapter(PagerAdapter paramPagerAdapter)
  {
    this.adapter = paramPagerAdapter;
  }
  
  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    int i = getRealCount();
    this.adapter.destroyItem(paramViewGroup, paramInt % i, paramObject);
  }
  
  public void finishUpdate(ViewGroup paramViewGroup)
  {
    this.adapter.finishUpdate(paramViewGroup);
  }
  
  public int getCount()
  {
    return Integer.MAX_VALUE;
  }
  
  public int getRealCount()
  {
    return this.adapter.getCount();
  }
  
  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    int i = getRealCount();
    return this.adapter.instantiateItem(paramViewGroup, paramInt % i);
  }
  
  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    return this.adapter.isViewFromObject(paramView, paramObject);
  }
  
  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
    this.adapter.restoreState(paramParcelable, paramClassLoader);
  }
  
  public Parcelable saveState()
  {
    return this.adapter.saveState();
  }
  
  public void startUpdate(ViewGroup paramViewGroup)
  {
    this.adapter.startUpdate(paramViewGroup);
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\antonyt\infiniteviewpager\InfinitePagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */