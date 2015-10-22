package com.roomorama.caldroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class MonthPagerAdapter
  extends FragmentPagerAdapter
{
  private ArrayList<DateGridFragment> fragments;
  
  public MonthPagerAdapter(FragmentManager paramFragmentManager)
  {
    super(paramFragmentManager);
  }
  
  public int getCount()
  {
    return 4;
  }
  
  public ArrayList<DateGridFragment> getFragments()
  {
    int i;
    if (this.fragments == null)
    {
      this.fragments = new ArrayList();
      i = 0;
    }
    for (;;)
    {
      if (i >= getCount()) {
        return this.fragments;
      }
      this.fragments.add(new DateGridFragment());
      i += 1;
    }
  }
  
  public Fragment getItem(int paramInt)
  {
    return (DateGridFragment)getFragments().get(paramInt);
  }
  
  public void setFragments(ArrayList<DateGridFragment> paramArrayList)
  {
    this.fragments = paramArrayList;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\roomorama\caldroid\MonthPagerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */