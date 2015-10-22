package com.example.organiser_m;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.foound.widget.AmazingListView;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity
  extends FragmentActivity
{
  private static CaldroidFragment caldroidFragment;
  static FileHandler fHandler;
  static String filename;
  Button buttonAdd;
  Button buttonShow;
  boolean buttonShowPressed = true;
  boolean dateSelected = false;
  AmazingListView itemlist;
  int prevDateType = 0;
  Date prevSelect = null;
  boolean promptShown;
  int selected = -1;
  Calendar selectedDate;
  boolean showEventTimes = false;
  boolean started = false;
  EditText targetText;
  
  private void resetRepeatingEventColors(Event paramEvent)
  {
    int i = 0;
    for (;;)
    {
      if (i > paramEvent.noOfIntervals)
      {
        caldroidFragment.setBackgroundResourceForDate(2131034126, Calendar.getInstance().getTime());
        caldroidFragment.refreshView();
        return;
      }
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.set(6, paramEvent.cal.get(6) + paramEvent.interval * i);
      localCalendar.set(1, paramEvent.cal.get(1));
      caldroidFragment.setBackgroundResourceForDate(2131034122, localCalendar.getTime());
      i += 1;
    }
  }
  
  public static void setColorsForEvents()
  {
    int i = 0;
    if (i >= fHandler.events.size())
    {
      caldroidFragment.refreshView();
      return;
    }
    if (((Event)fHandler.events.get(i)).cal.get(6) != Calendar.getInstance().get(6)) {
      caldroidFragment.setBackgroundResourceForDate(2131034130, ((Event)fHandler.events.get(i)).cal.getTime());
    }
    int j;
    label133:
    int k;
    if (((Event)fHandler.events.get(i)).interval != 0)
    {
      if (((Event)fHandler.events.get(i)).noOfIntervals != -1) {
        break label147;
      }
      j = 100;
      k = 1;
    }
    for (;;)
    {
      if (k > j)
      {
        i += 1;
        break;
        label147:
        j = ((Event)fHandler.events.get(i)).noOfIntervals;
        break label133;
      }
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.set(5, ((Event)fHandler.events.get(i)).cal.get(5));
      localCalendar.set(2, ((Event)fHandler.events.get(i)).cal.get(2));
      localCalendar.set(1, ((Event)fHandler.events.get(i)).cal.get(1));
      localCalendar.set(11, ((Event)fHandler.events.get(i)).cal.get(11));
      localCalendar.add(5, ((Event)fHandler.events.get(i)).interval * k);
      if ((localCalendar.get(6) != Calendar.getInstance().get(6)) || (localCalendar.get(1) != Calendar.getInstance().get(1))) {
        caldroidFragment.setBackgroundResourceForDate(2131034130, localCalendar.getTime());
      }
      k += 1;
    }
  }
  
  private void showHistoryMenu()
  {
    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
  }
  
  private void showSettingsMenu()
  {
    Intent localIntent = new Intent(getApplicationContext(), SettingsActivity.class);
    localIntent.putExtra("Filename", filename);
    startActivity(localIntent);
  }
  
  public void changeListView()
  {
    if (this.buttonShowPressed)
    {
      localObject1 = fHandler.getEventsByDay(this.selectedDate);
      Object localObject2 = fHandler.getRepeatingEventsByDay(this.selectedDate);
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      int m;
      boolean bool;
      int j;
      for (;;)
      {
        if (i >= ((List)localObject2).size())
        {
          m = 0;
          bool = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("pref_key_clock_type", false);
          j = -2;
          if (j < 24) {
            break;
          }
          if (m == 0) {
            break label719;
          }
          localObject1 = new DayListViewAdapter(localArrayList, getApplicationContext());
          this.itemlist.setAdapter((ListAdapter)localObject1);
          return;
        }
        Event localEvent = new Event();
        if (localEvent.type == 0) {
          localEvent.cal = ((Event)((List)localObject2).get(i)).cal;
        }
        localEvent.id = ((Event)((List)localObject2).get(i)).id;
        localEvent.type = ((Event)((List)localObject2).get(i)).type;
        localEvent.desc = ((Event)((List)localObject2).get(i)).desc;
        localEvent.title = (((Event)((List)localObject2).get(i)).title + " (Repeated)");
        localEvent.interval = ((Event)((List)localObject2).get(i)).interval;
        localEvent.noOfIntervals = ((Event)((List)localObject2).get(i)).noOfIntervals;
        localEvent.imp = ((Event)((List)localObject2).get(i)).imp;
        localEvent.done = ((Event)((List)localObject2).get(i)).done;
        ((List)localObject1).add(localEvent);
        i += 1;
      }
      localObject2 = new ArrayList();
      int k = 0;
      if (k >= ((List)localObject1).size())
      {
        if (j != -2) {
          break label564;
        }
        localArrayList.add(Pair.create("Tasks", localObject2));
      }
      for (;;)
      {
        j += 1;
        break;
        if (j == -2)
        {
          i = m;
          if (((Event)((List)localObject1).get(k)).type == 2)
          {
            ((List)localObject2).add((Event)((List)localObject1).get(k));
            i = 1;
          }
        }
        for (;;)
        {
          k += 1;
          m = i;
          break;
          if (j == -1)
          {
            i = m;
            if (((Event)((List)localObject1).get(k)).type == 1)
            {
              ((List)localObject2).add((Event)((List)localObject1).get(k));
              i = 1;
            }
          }
          else
          {
            i = m;
            if (((Event)((List)localObject1).get(k)).cal.get(11) == j)
            {
              i = m;
              if (((Event)((List)localObject1).get(k)).type == 0)
              {
                ((List)localObject2).add((Event)((List)localObject1).get(k));
                i = 1;
              }
            }
          }
        }
        label564:
        if (j == -1) {
          localArrayList.add(Pair.create("Memos", localObject2));
        } else if (bool)
        {
          if (j > 12) {
            localArrayList.add(Pair.create(String.valueOf(j - 12) + ":00 PM", localObject2));
          } else {
            localArrayList.add(Pair.create(String.valueOf(j) + ":00 AM", localObject2));
          }
        }
        else {
          localArrayList.add(Pair.create(String.valueOf(j) + ":00", localObject2));
        }
      }
      label719:
      localObject1 = new AddListViewAdapter(1, getApplicationContext(), this.showEventTimes);
      this.itemlist.setAdapter((ListAdapter)localObject1);
      return;
    }
    Object localObject1 = new AddListViewAdapter(0, getApplicationContext(), this.showEventTimes);
    this.itemlist.setAdapter((ListAdapter)localObject1);
  }
  
  public void getInputFromUser(final int paramInt, final boolean paramBoolean, final View paramView)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    Object localObject1 = getLayoutInflater();
    if (paramInt == 0) {
      localObject1 = ((LayoutInflater)localObject1).inflate(2130903049, null);
    }
    for (;;)
    {
      localBuilder.setView((View)localObject1);
      localBuilder.setTitle("Enter Details");
      localBuilder.setNegativeButton("Confirm", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          EditText localEditText1 = (EditText)((AlertDialog)paramAnonymousDialogInterface).findViewById(2131099681);
          EditText localEditText2 = (EditText)((AlertDialog)paramAnonymousDialogInterface).findViewById(2131099685);
          Spinner localSpinner1 = (Spinner)((AlertDialog)paramAnonymousDialogInterface).findViewById(2131099693);
          Spinner localSpinner2 = (Spinner)((AlertDialog)paramAnonymousDialogInterface).findViewById(2131099695);
          CheckBox localCheckBox = (CheckBox)((AlertDialog)paramAnonymousDialogInterface).findViewById(2131099687);
          RadioButton localRadioButton = (RadioButton)((AlertDialog)paramAnonymousDialogInterface).findViewById(2131099690);
          Event localEvent = new Event();
          localEvent.title = localEditText1.getText().toString();
          localEvent.desc = localEditText2.getText().toString();
          localEvent.imp = localCheckBox.isChecked();
          if (localRadioButton.isChecked()) {
            if (localSpinner1.getSelectedItemId() < 7L)
            {
              localEvent.interval = ((int)(localSpinner1.getSelectedItemId() + 1L));
              if (localSpinner2.getSelectedItemId() == 9L) {
                break label495;
              }
              localEvent.noOfIntervals = (localSpinner2.getSelectedItemPosition() + 2);
              label184:
              localEvent.cal = Calendar.getInstance();
              localEvent.cal.set(6, MainActivity.this.selectedDate.get(6));
              localEvent.cal.set(1, MainActivity.this.selectedDate.get(1));
              if ((paramInt == 0) || (paramInt == 2))
              {
                paramAnonymousDialogInterface = (TimePicker)((AlertDialog)paramAnonymousDialogInterface).findViewById(2131099679);
                localEvent.cal.set(11, paramAnonymousDialogInterface.getCurrentHour().intValue());
                localEvent.cal.set(12, paramAnonymousDialogInterface.getCurrentMinute().intValue());
              }
              localEvent.type = paramInt;
              if (!paramBoolean) {
                break label516;
              }
              paramAnonymousDialogInterface = MainActivity.fHandler.findEventFromView(paramView, MainActivity.this.selectedDate);
              MainActivity.fHandler.removeEvent(paramAnonymousDialogInterface);
              if (paramAnonymousDialogInterface.interval != 0)
              {
                localEvent.cal.set(6, paramAnonymousDialogInterface.cal.get(6));
                localEvent.cal.set(1, paramAnonymousDialogInterface.cal.get(1));
              }
              MainActivity.fHandler.writeEvent(localEvent);
              if (paramAnonymousDialogInterface.interval != 0)
              {
                MainActivity.this.resetRepeatingEventColors(paramAnonymousDialogInterface);
                MainActivity.setColorsForEvents();
              }
              MainActivity.this.changeListView();
              label404:
              if ((MainActivity.this.selectedDate.get(6) != Calendar.getInstance().get(6)) || (MainActivity.this.selectedDate.get(1) != Calendar.getInstance().get(1))) {
                break label536;
              }
            }
          }
          label495:
          label516:
          label536:
          for (MainActivity.this.prevDateType = 1;; MainActivity.this.prevDateType = 2)
          {
            new EventChecker().start(MainActivity.this.getApplicationContext());
            return;
            localEvent.interval = ((int)((localSpinner1.getSelectedItemId() - 6L) * 7L));
            break;
            localEvent.noOfIntervals = -1;
            break label184;
            localEvent.interval = 0;
            localEvent.noOfIntervals = 0;
            break label184;
            MainActivity.fHandler.writeEvent(localEvent);
            MainActivity.this.changeListView();
            MainActivity.setColorsForEvents();
            break label404;
          }
        }
      }).setPositiveButton("Cancel", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.cancel();
        }
      });
      final EditText localEditText1 = (EditText)((View)localObject1).findViewById(2131099681);
      final EditText localEditText2 = (EditText)((View)localObject1).findViewById(2131099685);
      final RadioButton localRadioButton1 = (RadioButton)((View)localObject1).findViewById(2131099690);
      final RadioButton localRadioButton2 = (RadioButton)((View)localObject1).findViewById(2131099689);
      final LinearLayout localLinearLayout = (LinearLayout)((View)localObject1).findViewById(2131099691);
      Object localObject2 = (ImageButton)((View)localObject1).findViewById(2131099682);
      Object localObject3 = (ImageButton)((View)localObject1).findViewById(2131099686);
      localRadioButton1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          localRadioButton2.setChecked(false);
          localLinearLayout.setVisibility(0);
          this.val$scroll.post(new Runnable()
          {
            public void run()
            {
              this.val$scroll.fullScroll(130);
            }
          });
        }
      });
      ((ImageButton)localObject2).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          MainActivity.this.startSpeechActivity(localEditText1);
        }
      });
      ((ImageButton)localObject3).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          MainActivity.this.startSpeechActivity(localEditText2);
        }
      });
      localRadioButton2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          localRadioButton1.setChecked(false);
          localLinearLayout.setVisibility(8);
          localLinearLayout.setFocusable(true);
          localLinearLayout.requestFocus();
        }
      });
      if (paramBoolean)
      {
        paramView = fHandler.findEventFromView(paramView, this.selectedDate);
        localObject2 = (Spinner)((View)localObject1).findViewById(2131099693);
        localObject3 = (Spinner)((View)localObject1).findViewById(2131099695);
        CheckBox localCheckBox = (CheckBox)((View)localObject1).findViewById(2131099687);
        localEditText1.setText(paramView.title);
        localEditText2.setText(paramView.desc);
        localCheckBox.setChecked(paramView.imp);
        if (paramView.interval != 0)
        {
          ((Spinner)localObject2).setSelection(paramView.interval - 1);
          ((Spinner)localObject3).setSelection(paramView.noOfIntervals - 2);
          localLinearLayout.setVisibility(0);
          localRadioButton2.setChecked(false);
          localRadioButton1.setChecked(true);
          if ((paramInt == 0) || (paramInt == 2))
          {
            localObject1 = (TimePicker)((View)localObject1).findViewById(2131099679);
            ((TimePicker)localObject1).setCurrentHour(Integer.valueOf(paramView.cal.get(11)));
            ((TimePicker)localObject1).setCurrentMinute(Integer.valueOf(paramView.cal.get(12)));
          }
        }
      }
      localBuilder.show();
      return;
      if (paramInt == 1) {
        localObject1 = ((LayoutInflater)localObject1).inflate(2130903050, null);
      } else {
        localObject1 = ((LayoutInflater)localObject1).inflate(2130903051, null);
      }
    }
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      do
      {
        return;
      } while ((paramInt2 != -1) || (paramIntent == null));
      paramIntent = paramIntent.getStringArrayListExtra("android.speech.extra.RESULTS");
    } while (paramIntent.isEmpty());
    this.targetText.setText((CharSequence)paramIntent.get(0));
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131099655);
    if (paramConfiguration.orientation == 2)
    {
      localLinearLayout.setOrientation(0);
      paramConfiguration = (LinearLayout)findViewById(2131099656);
      localLinearLayout = (LinearLayout)findViewById(2131099657);
      paramConfiguration.setLayoutParams(new LinearLayout.LayoutParams(-2, -1, 0.5F));
      localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1, 0.8F));
    }
    for (;;)
    {
      this.buttonShowPressed = false;
      setButtonHighlighted(this.buttonAdd, true);
      setButtonHighlighted(this.buttonShow, false);
      changeListView();
      return;
      if (paramConfiguration.orientation == 1)
      {
        localLinearLayout.setOrientation(1);
        paramConfiguration = (LinearLayout)findViewById(2131099656);
        localLinearLayout = (LinearLayout)findViewById(2131099657);
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1, 0.5F);
        localLayoutParams.bottomMargin = 5;
        paramConfiguration.setLayoutParams(localLayoutParams);
        localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1, 0.5F));
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903042);
    Object localObject;
    if ((getResources().getConfiguration().orientation == 1) && (paramBundle == null))
    {
      ((LinearLayout)findViewById(2131099655)).setOrientation(1);
      localObject = (LinearLayout)findViewById(2131099656);
      LinearLayout localLinearLayout = (LinearLayout)findViewById(2131099657);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1, 0.5F);
      localLayoutParams.bottomMargin = 5;
      ((LinearLayout)localObject).setLayoutParams(localLayoutParams);
      localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1, 0.5F));
    }
    this.buttonShow = ((Button)findViewById(2131099658));
    this.buttonAdd = ((Button)findViewById(2131099659));
    this.selectedDate = Calendar.getInstance();
    this.itemlist = ((AmazingListView)findViewById(2131099660));
    filename = "OM_data";
    fHandler = new FileHandler(filename, getApplicationContext());
    fHandler.loadEventsFromFile();
    this.buttonShow.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (MainActivity.this.dateSelected)
        {
          MainActivity.this.buttonShowPressed = true;
          MainActivity.this.setButtonHighlighted(MainActivity.this.buttonAdd, false);
          MainActivity.this.setButtonHighlighted(MainActivity.this.buttonShow, true);
          MainActivity.this.changeListView();
        }
        while (paramAnonymousMotionEvent.getAction() != 0) {
          return true;
        }
        MainActivity.this.promptShown = true;
        paramAnonymousView = new AlertDialog.Builder(MainActivity.this);
        paramAnonymousView.setMessage("Please select a date first!");
        paramAnonymousView.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
        });
        paramAnonymousView.show();
        return true;
      }
    });
    this.buttonAdd.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (MainActivity.this.dateSelected)
        {
          MainActivity.this.buttonShowPressed = false;
          MainActivity.this.setButtonHighlighted(MainActivity.this.buttonAdd, true);
          MainActivity.this.setButtonHighlighted(MainActivity.this.buttonShow, false);
          MainActivity.this.changeListView();
        }
        while (paramAnonymousMotionEvent.getAction() != 0) {
          return true;
        }
        MainActivity.this.promptShown = true;
        paramAnonymousView = new AlertDialog.Builder(MainActivity.this);
        paramAnonymousView.setMessage("Please select a date first!");
        paramAnonymousView.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
        });
        paramAnonymousView.show();
        return true;
      }
    });
    this.itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, final View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Log.i("MainActivity", "pressed");
        if (!MainActivity.this.buttonShowPressed)
        {
          if (paramAnonymousInt == 1)
          {
            MainActivity.this.getInputFromUser(0, false, paramAnonymousView);
            return;
          }
          if (paramAnonymousInt == 0)
          {
            MainActivity.this.getInputFromUser(1, false, paramAnonymousView);
            return;
          }
          MainActivity.this.getInputFromUser(2, false, paramAnonymousView);
          return;
        }
        paramAnonymousAdapterView = new AlertDialog.Builder(MainActivity.this);
        paramAnonymousAdapterView.setMessage("What would you like to do to this event?");
        paramAnonymousAdapterView.setPositiveButton("Edit", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            MainActivity.this.getInputFromUser(0, true, paramAnonymousView);
            MainActivity.caldroidFragment.setBackgroundResourceForDate(2130837541, MainActivity.this.selectedDate.getTime());
          }
        });
        paramAnonymousAdapterView.setNegativeButton("Delete", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface = MainActivity.fHandler.findEventFromView(paramAnonymousView, MainActivity.this.selectedDate);
            MainActivity.fHandler.removeEvent(paramAnonymous2DialogInterface);
            MainActivity.fHandler.loadEventsFromFile();
            MainActivity.this.resetRepeatingEventColors(paramAnonymous2DialogInterface);
            MainActivity.this.changeListView();
            if ((paramAnonymous2DialogInterface.cal.get(6) == Calendar.getInstance().get(6)) && (paramAnonymous2DialogInterface.cal.get(1) == Calendar.getInstance().get(1))) {
              MainActivity.this.prevDateType = 1;
            }
            for (;;)
            {
              new EventChecker().start(MainActivity.this.getApplicationContext());
              MainActivity.setColorsForEvents();
              return;
              paramAnonymous2DialogInterface = MainActivity.fHandler.getEventsByDay(MainActivity.this.selectedDate);
              paramAnonymous2DialogInterface.addAll(MainActivity.fHandler.getRepeatingEventsByDay(MainActivity.this.selectedDate));
              if (paramAnonymous2DialogInterface.isEmpty()) {
                MainActivity.this.prevDateType = 0;
              } else {
                MainActivity.this.prevDateType = 2;
              }
            }
          }
        });
        paramAnonymousAdapterView.show();
      }
    });
    caldroidFragment = new CaldroidFragment();
    if (paramBundle != null) {
      caldroidFragment.restoreStatesFromKey(paramBundle, "CALDROID_SAVED_STATE");
    }
    for (;;)
    {
      paramBundle = getSupportFragmentManager().beginTransaction();
      paramBundle.replace(2131099656, caldroidFragment);
      paramBundle.commit();
      paramBundle = new CaldroidListener()
      {
        public void onSelectDate(Date paramAnonymousDate, View paramAnonymousView)
        {
          MainActivity.this.dateSelected = true;
          MainActivity.this.selectedDate.setTime(paramAnonymousDate);
          MainActivity.this.selectedDateBackgroundChange();
          paramAnonymousDate = new ArrayList();
          paramAnonymousDate.addAll(MainActivity.fHandler.getEventsByDay(MainActivity.this.selectedDate));
          paramAnonymousDate.addAll(MainActivity.fHandler.getRepeatingEventsByDay(MainActivity.this.selectedDate));
          if (paramAnonymousDate.isEmpty())
          {
            MainActivity.this.setButtonHighlighted(MainActivity.this.buttonAdd, true);
            MainActivity.this.setButtonHighlighted(MainActivity.this.buttonShow, false);
          }
          for (MainActivity.this.buttonShowPressed = false;; MainActivity.this.buttonShowPressed = true)
          {
            MainActivity.this.changeListView();
            return;
            MainActivity.this.setButtonHighlighted(MainActivity.this.buttonAdd, false);
            MainActivity.this.setButtonHighlighted(MainActivity.this.buttonShow, true);
          }
        }
      };
      caldroidFragment.setCaldroidListener(paramBundle);
      caldroidFragment.setMinDate(Calendar.getInstance().getTime());
      caldroidFragment.refreshView();
      setColorsForEvents();
      caldroidFragment.setBackgroundResourceForDate(2131034126, this.selectedDate.getTime());
      return;
      paramBundle = new Bundle();
      localObject = Calendar.getInstance();
      paramBundle.putInt("month", ((Calendar)localObject).get(2) + 1);
      paramBundle.putInt("year", ((Calendar)localObject).get(1));
      paramBundle.putBoolean("enableSwipe", true);
      paramBundle.putBoolean("sixWeeksInCalendar", true);
      paramBundle.putBoolean("showNavigationArrows", false);
      caldroidFragment.setArguments(paramBundle);
    }
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
    showHistoryMenu();
    return true;
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putInt("value", 1);
  }
  
  void selectedDateBackgroundChange()
  {
    Calendar localCalendar = Calendar.getInstance();
    if (this.prevSelect != null)
    {
      if (this.prevDateType == 0) {
        caldroidFragment.setBackgroundResourceForDate(2131034122, this.prevSelect);
      }
    }
    else
    {
      List localList1 = fHandler.getEventsByDay(this.selectedDate);
      List localList2 = fHandler.getRepeatingEventsByDay(this.selectedDate);
      if ((localList1.isEmpty()) && (localList2.isEmpty())) {
        break label210;
      }
      caldroidFragment.setBackgroundResourceForDate(2130837541, this.selectedDate.getTime());
    }
    for (this.prevDateType = 2;; this.prevDateType = 0)
    {
      if ((this.selectedDate.get(6) == localCalendar.get(6)) && (this.selectedDate.get(1) == localCalendar.get(1)))
      {
        caldroidFragment.setBackgroundResourceForDate(2130837542, this.selectedDate.getTime());
        this.prevDateType = 1;
      }
      caldroidFragment.refreshView();
      this.prevSelect = this.selectedDate.getTime();
      return;
      if (this.prevDateType == 1)
      {
        caldroidFragment.setBackgroundResourceForDate(2131034126, this.prevSelect);
        break;
      }
      if (this.prevDateType != 2) {
        break;
      }
      caldroidFragment.setBackgroundResourceForDate(2131034130, this.prevSelect);
      break;
      label210:
      caldroidFragment.setBackgroundResourceForDate(2130837540, this.selectedDate.getTime());
    }
  }
  
  void setButtonHighlighted(Button paramButton, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramButton.setBackgroundResource(2131034128);
      return;
    }
    paramButton.setBackgroundResource(2131034125);
  }
  
  public void startSpeechActivity(EditText paramEditText)
  {
    try
    {
      this.targetText = paramEditText;
      paramEditText = new Intent("android.speech.action.RECOGNIZE_SPEECH");
      paramEditText.putExtra("android.speech.extra.LANGUAGE_MODEL", "en-US");
      startActivityForResult(paramEditText, 1);
      return;
    }
    catch (ActivityNotFoundException paramEditText)
    {
      Toast.makeText(getApplicationContext(), "Your device does not support Speech to Text", 0).show();
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */