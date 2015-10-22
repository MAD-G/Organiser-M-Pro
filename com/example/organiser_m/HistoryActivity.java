package com.example.organiser_m;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import com.foound.widget.AmazingListView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HistoryActivity
  extends Activity
{
  String filename;
  String searchQuery;
  
  private void populateHistory()
  {
    AmazingListView localAmazingListView = (AmazingListView)findViewById(2131099654);
    List localList = MainActivity.fHandler.pastEvents;
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    if (i >= localList.size())
    {
      localAmazingListView.setAdapter(new DayListViewAdapter(localArrayList1, getApplicationContext()));
      return;
    }
    int k = 0;
    int j = 0;
    for (;;)
    {
      if (j >= localArrayList1.size()) {}
      for (j = k;; j = k)
      {
        if (j == 0)
        {
          ArrayList localArrayList2 = new ArrayList();
          localArrayList2.add((Event)localList.get(i));
          localArrayList1.add(Pair.create(String.valueOf(((Event)localList.get(i)).cal.get(5)) + "th " + ((Event)localList.get(i)).cal.getDisplayName(2, 1, Locale.US) + ", " + String.valueOf(((Event)localList.get(i)).cal.get(1)), localArrayList2));
        }
        i += 1;
        break;
        if (!((String)((Pair)localArrayList1.get(j)).first).equals(String.valueOf(((Event)localList.get(i)).cal.get(5)) + "th " + ((Event)localList.get(i)).cal.getDisplayName(2, 1, Locale.US) + ", " + String.valueOf(((Event)localList.get(i)).cal.get(1)))) {
          break label379;
        }
        k = 1;
        ((List)((Pair)localArrayList1.get(j)).second).add((Event)localList.get(i));
      }
      label379:
      j += 1;
    }
  }
  
  private void populateHistory(boolean paramBoolean)
  {
    AmazingListView localAmazingListView = (AmazingListView)findViewById(2131099654);
    List localList = MainActivity.fHandler.pastEvents;
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    if (i >= localList.size())
    {
      localAmazingListView.setAdapter(new DayListViewAdapter(localArrayList1, getApplicationContext()));
      return;
    }
    int k = 0;
    int j = 0;
    label65:
    label76:
    ArrayList localArrayList2;
    if (j >= localArrayList1.size()) {
      if (k == 0)
      {
        localArrayList2 = new ArrayList();
        if (!paramBoolean) {
          break label1189;
        }
        if (((Event)localList.get(i)).title.isEmpty()) {
          break label1067;
        }
        k = this.searchQuery.length() - ((Event)localList.get(i)).title.length();
        j = k;
        if (k < 0) {
          j = 0;
        }
        if (!((Event)localList.get(i)).title.substring(0, this.searchQuery.length() - j).toLowerCase(Locale.US).equals(this.searchQuery)) {
          break label945;
        }
        localArrayList2.add((Event)localList.get(i));
      }
    }
    for (;;)
    {
      localArrayList1.add(Pair.create(String.valueOf(((Event)localList.get(i)).cal.get(5)) + "th " + ((Event)localList.get(i)).cal.getDisplayName(2, 1, Locale.US) + ", " + String.valueOf(((Event)localList.get(i)).cal.get(1)), localArrayList2));
      i += 1;
      break;
      if (((String)((Pair)localArrayList1.get(j)).first).equals(String.valueOf(((Event)localList.get(i)).cal.get(5)) + "th " + ((Event)localList.get(i)).cal.getDisplayName(2, 1, Locale.US) + ", " + String.valueOf(((Event)localList.get(i)).cal.get(1))))
      {
        int n = 1;
        if (paramBoolean)
        {
          if (!((Event)localList.get(i)).title.isEmpty())
          {
            m = this.searchQuery.length() - ((Event)localList.get(i)).title.length();
            k = m;
            if (m < 0) {
              k = 0;
            }
            if (((Event)localList.get(i)).title.substring(0, this.searchQuery.length() - k).toLowerCase(Locale.US).equals(this.searchQuery))
            {
              ((List)((Pair)localArrayList1.get(j)).second).add((Event)localList.get(i));
              k = n;
              break label76;
            }
            k = n;
            if (((Event)localList.get(i)).desc.isEmpty()) {
              break label76;
            }
            k = this.searchQuery.length() - ((Event)localList.get(i)).desc.length();
            m = k;
            if (k < 0) {
              m = 0;
            }
            k = n;
            if (!((Event)localList.get(i)).desc.substring(0, this.searchQuery.length() - m).toLowerCase(Locale.US).equals(this.searchQuery)) {
              break label76;
            }
            ((List)((Pair)localArrayList1.get(j)).second).add((Event)localList.get(i));
            k = n;
            break label76;
          }
          k = this.searchQuery.length() - ((Event)localList.get(i)).desc.length();
          int m = k;
          if (k < 0) {
            m = 0;
          }
          k = n;
          if (((Event)localList.get(i)).desc.isEmpty()) {
            break label76;
          }
          k = n;
          if (!((Event)localList.get(i)).desc.substring(0, this.searchQuery.length() - m).toLowerCase(Locale.US).equals(this.searchQuery)) {
            break label76;
          }
          ((List)((Pair)localArrayList1.get(j)).second).add((Event)localList.get(i));
          k = n;
          break label76;
        }
        ((List)((Pair)localArrayList1.get(j)).second).add((Event)localList.get(i));
        k = n;
        break label76;
      }
      j += 1;
      break label65;
      label945:
      if (!((Event)localList.get(i)).desc.isEmpty())
      {
        k = this.searchQuery.length() - ((Event)localList.get(i)).desc.length();
        j = k;
        if (k < 0) {
          j = 0;
        }
        if (((Event)localList.get(i)).desc.substring(0, this.searchQuery.length() - j).toLowerCase(Locale.US).equals(this.searchQuery))
        {
          localArrayList2.add((Event)localList.get(i));
          continue;
          label1067:
          if (!((Event)localList.get(i)).desc.isEmpty())
          {
            k = this.searchQuery.length() - ((Event)localList.get(i)).desc.length();
            j = k;
            if (k < 0) {
              j = 0;
            }
            if (((Event)localList.get(i)).desc.substring(0, this.searchQuery.length() - j).toLowerCase(Locale.US).equals(this.searchQuery))
            {
              localArrayList2.add((Event)localList.get(i));
              continue;
              label1189:
              localArrayList2.add((Event)localList.get(i));
            }
          }
        }
      }
    }
  }
  
  private void setupActionBar()
  {
    getActionBar().setDisplayHomeAsUpEnabled(true);
  }
  
  private void showSettingsMenu()
  {
    Intent localIntent = new Intent(getApplicationContext(), SettingsActivity.class);
    localIntent.putExtra("Filename", this.filename);
    startActivity(localIntent);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903041);
    ((SearchView)findViewById(2131099653)).setOnQueryTextListener(new SearchView.OnQueryTextListener()
    {
      public boolean onQueryTextChange(String paramAnonymousString)
      {
        if (!paramAnonymousString.isEmpty())
        {
          HistoryActivity.this.searchQuery = paramAnonymousString.toLowerCase(Locale.US);
          Log.i("HistoryActivity", HistoryActivity.this.searchQuery);
          HistoryActivity.this.populateHistory(true);
        }
        for (;;)
        {
          return false;
          HistoryActivity.this.populateHistory();
        }
      }
      
      public boolean onQueryTextSubmit(String paramAnonymousString)
      {
        return false;
      }
    });
    setupActionBar();
    populateHistory();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131492865, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    case 2131099702: 
      showSettingsMenu();
      return true;
    }
    NavUtils.navigateUpFromSameTask(this);
    return true;
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\HistoryActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */