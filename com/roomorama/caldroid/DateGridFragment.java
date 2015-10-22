package com.roomorama.caldroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import com.caldroid.R.layout;

public class DateGridFragment
  extends Fragment
{
  private CaldroidGridAdapter gridAdapter;
  private GridView gridView;
  private AdapterView.OnItemClickListener onItemClickListener;
  private AdapterView.OnItemLongClickListener onItemLongClickListener;
  
  public CaldroidGridAdapter getGridAdapter()
  {
    return this.gridAdapter;
  }
  
  public GridView getGridView()
  {
    return this.gridView;
  }
  
  public AdapterView.OnItemClickListener getOnItemClickListener()
  {
    return this.onItemClickListener;
  }
  
  public AdapterView.OnItemLongClickListener getOnItemLongClickListener()
  {
    return this.onItemLongClickListener;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.gridView = ((GridView)paramLayoutInflater.inflate(R.layout.date_grid_fragment, paramViewGroup, false));
    if (this.gridAdapter != null) {
      this.gridView.setAdapter(this.gridAdapter);
    }
    if (this.onItemClickListener != null) {
      this.gridView.setOnItemClickListener(this.onItemClickListener);
    }
    if (this.onItemLongClickListener != null) {
      this.gridView.setOnItemLongClickListener(this.onItemLongClickListener);
    }
    return this.gridView;
  }
  
  public void setGridAdapter(CaldroidGridAdapter paramCaldroidGridAdapter)
  {
    this.gridAdapter = paramCaldroidGridAdapter;
  }
  
  public void setOnItemClickListener(AdapterView.OnItemClickListener paramOnItemClickListener)
  {
    this.onItemClickListener = paramOnItemClickListener;
  }
  
  public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener paramOnItemLongClickListener)
  {
    this.onItemLongClickListener = paramOnItemLongClickListener;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\roomorama\caldroid\DateGridFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */