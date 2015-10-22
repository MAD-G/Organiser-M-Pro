package com.example.organiser_m;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.foound.widget.AmazingAdapter;

public class AddListViewAdapter
  extends AmazingAdapter
{
  Context context;
  boolean showEventTimes;
  int type;
  
  public AddListViewAdapter(int paramInt, Context paramContext, boolean paramBoolean)
  {
    this.context = paramContext;
    this.type = paramInt;
    this.showEventTimes = paramBoolean;
  }
  
  protected void bindSectionHeader(View paramView, int paramInt, boolean paramBoolean)
  {
    paramView.findViewById(2131099675).setVisibility(8);
  }
  
  public void configurePinnedHeader(View paramView, int paramInt1, int paramInt2) {}
  
  public View getAmazingView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramViewGroup = paramView;
    paramView = paramViewGroup;
    if (paramViewGroup == null) {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903047, null);
    }
    paramViewGroup = (TextView)paramView.findViewById(2131099672);
    TextView localTextView = (TextView)paramView.findViewById(2131099673);
    paramViewGroup.setText(getItem(paramInt));
    localTextView.setText("");
    return paramView;
  }
  
  public int getCount()
  {
    int i = 1;
    if (this.type == 0) {
      i = 3;
    }
    while (this.type == 1) {
      return i;
    }
    return 0;
  }
  
  public String getItem(int paramInt)
  {
    if (this.type == 0)
    {
      if (paramInt == 0) {
        return "Memo";
      }
      if (paramInt == 1) {
        return "Event";
      }
      return "Task";
    }
    return "No Events or Memos";
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public int getPositionForSection(int paramInt)
  {
    return 0;
  }
  
  public int getSectionForPosition(int paramInt)
  {
    return 0;
  }
  
  public Object[] getSections()
  {
    return null;
  }
  
  protected void onNextPageRequested(int paramInt) {}
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\AddListViewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */