package com.example.organiser_m;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.foound.widget.AmazingAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayListViewAdapter
  extends AmazingAdapter
{
  List<Pair<String, List<Event>>> all = new ArrayList();
  Context context;
  
  public DayListViewAdapter(List<Pair<String, List<Event>>> paramList, Context paramContext)
  {
    this.all.addAll(paramList);
    this.context = paramContext;
  }
  
  protected void bindSectionHeader(View paramView, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramView.findViewById(2131099675).setVisibility(0);
      ((TextView)paramView.findViewById(2131099675)).setText(getSections()[getSectionForPosition(paramInt)]);
      return;
    }
    paramView.findViewById(2131099675).setVisibility(8);
  }
  
  public void configurePinnedHeader(View paramView, int paramInt1, int paramInt2)
  {
    paramView = (TextView)paramView;
    paramView.setText(getSections()[getSectionForPosition(paramInt1)]);
    paramView.setBackgroundColor(paramInt2 << 24 | 0xBBFFBB);
    paramView.setTextColor(paramInt2 << 24);
  }
  
  public View getAmazingView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    final Event localEvent = getItem(paramInt);
    paramViewGroup = paramView;
    TextView localTextView1;
    TextView localTextView2;
    boolean bool;
    if (paramView == null)
    {
      paramView = (LayoutInflater)this.context.getSystemService("layout_inflater");
      if (localEvent.type != 1) {
        break label327;
      }
      paramView = paramView.inflate(2130903052, null);
      paramViewGroup = (TextView)paramView.findViewById(2131099672);
      localTextView1 = (TextView)paramView.findViewById(2131099673);
      localTextView2 = (TextView)paramView.findViewById(2131099674);
      paramViewGroup.setText(localEvent.title);
      localTextView1.setText(localEvent.desc);
      localTextView2.setText(String.valueOf(localEvent.id));
      if ((localEvent.type == 0) || (localEvent.type == 2))
      {
        localTextView1 = (TextView)paramView.findViewById(2131099671);
        if (localEvent.imp) {
          localTextView1.setTextColor(this.context.getResources().getColor(2131034122));
        }
        bool = PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean("pref_key_clock_type", false);
        if (localEvent.cal.get(12) >= 10) {
          break label560;
        }
        paramViewGroup = "0" + String.valueOf(localEvent.cal.get(12));
        label221:
        if (localEvent.type != 0) {
          break label668;
        }
        if (!bool) {
          break label625;
        }
        if (localEvent.cal.get(11) <= 12) {
          break label577;
        }
        localTextView1.setText(String.valueOf(localEvent.cal.get(11) - 12) + ":" + paramViewGroup + " PM");
      }
    }
    for (;;)
    {
      paramViewGroup = paramView;
      if (localEvent.imp)
      {
        paramView.setBackgroundColor(this.context.getResources().getColor(2131034133));
        paramViewGroup = paramView;
      }
      return paramViewGroup;
      label327:
      if (localEvent.type == 2)
      {
        paramView = paramView.inflate(2130903053, null);
        paramViewGroup = (TextView)paramView.findViewById(2131099672);
        localTextView1 = (TextView)paramView.findViewById(2131099673);
        localTextView2 = (TextView)paramView.findViewById(2131099674);
        paramViewGroup.setText(localEvent.title);
        localTextView1.setText(localEvent.desc);
        if (localEvent.imp)
        {
          paramViewGroup.setTextColor(this.context.getResources().getColor(2131034122));
          localTextView1.setTextColor(this.context.getResources().getColor(2131034122));
        }
        localTextView2.setText(String.valueOf(localEvent.id));
        paramViewGroup = (CheckBox)paramView.findViewById(2131099672);
        paramViewGroup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
          public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
          {
            MainActivity.fHandler.removeEvent(localEvent);
            localEvent.done = paramAnonymousBoolean;
            MainActivity.fHandler.writeEvent(localEvent);
            MainActivity.fHandler.loadEventsFromFile();
            new EventChecker().start(DayListViewAdapter.this.context);
          }
        });
        paramViewGroup.setChecked(localEvent.done);
        break;
      }
      paramView = paramView.inflate(2130903047, null);
      paramViewGroup = (TextView)paramView.findViewById(2131099672);
      localTextView1 = (TextView)paramView.findViewById(2131099673);
      localTextView2 = (TextView)paramView.findViewById(2131099674);
      paramViewGroup.setText(localEvent.title);
      localTextView1.setText(localEvent.desc);
      localTextView2.setText(String.valueOf(localEvent.id));
      break;
      label560:
      paramViewGroup = String.valueOf(localEvent.cal.get(12));
      break label221;
      label577:
      localTextView1.setText(String.valueOf(localEvent.cal.get(11)) + ":" + paramViewGroup + " AM");
      continue;
      label625:
      localTextView1.setText(String.valueOf(localEvent.cal.get(11)) + ":" + paramViewGroup);
      continue;
      label668:
      if (localEvent.type == 2) {
        if (bool)
        {
          if (localEvent.cal.get(11) > 12) {
            localTextView1.setText("Deadline: " + String.valueOf(localEvent.cal.get(11) - 12) + ":" + paramViewGroup + " PM");
          } else {
            localTextView1.setText("Deadline: " + String.valueOf(localEvent.cal.get(11)) + ":" + paramViewGroup + " AM");
          }
        }
        else {
          localTextView1.setText("Deadline: " + String.valueOf(localEvent.cal.get(11)) + ":" + paramViewGroup);
        }
      }
    }
  }
  
  public int getCount()
  {
    int j = 0;
    int i = 0;
    for (;;)
    {
      if (i >= this.all.size()) {
        return j;
      }
      j += ((List)((Pair)this.all.get(i)).second).size();
      i += 1;
    }
  }
  
  public Event getItem(int paramInt)
  {
    int j = 0;
    int i = 0;
    for (;;)
    {
      if (i >= this.all.size()) {
        return null;
      }
      if ((paramInt >= j) && (paramInt < ((List)((Pair)this.all.get(i)).second).size() + j)) {
        return (Event)((List)((Pair)this.all.get(i)).second).get(paramInt - j);
      }
      j += ((List)((Pair)this.all.get(i)).second).size();
      i += 1;
    }
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public int getPositionForSection(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = 0;
    }
    int j = i;
    if (i >= this.all.size()) {
      j = this.all.size() - 1;
    }
    paramInt = 0;
    i = 0;
    for (;;)
    {
      int k;
      if (i >= this.all.size()) {
        k = 0;
      }
      do
      {
        return k;
        k = paramInt;
      } while (j == i);
      paramInt += ((List)((Pair)this.all.get(i)).second).size();
      i += 1;
    }
  }
  
  public int getSectionForPosition(int paramInt)
  {
    int j = 0;
    int i = 0;
    for (;;)
    {
      int k;
      if (i >= this.all.size()) {
        k = -1;
      }
      do
      {
        return k;
        if (paramInt < j) {
          break;
        }
        k = i;
      } while (paramInt < ((List)((Pair)this.all.get(i)).second).size() + j);
      j += ((List)((Pair)this.all.get(i)).second).size();
      i += 1;
    }
  }
  
  public String[] getSections()
  {
    String[] arrayOfString = new String[this.all.size()];
    int i = 0;
    for (;;)
    {
      if (i >= this.all.size()) {
        return arrayOfString;
      }
      arrayOfString[i] = ((String)((Pair)this.all.get(i)).first);
      i += 1;
    }
  }
  
  protected void onNextPageRequested(int paramInt) {}
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\DayListViewAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */