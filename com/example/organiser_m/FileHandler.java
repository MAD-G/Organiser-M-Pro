package com.example.organiser_m;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FileHandler
{
  Context context;
  List<Event> events = new ArrayList();
  String filename;
  int highestId = 0;
  List<Event> pastEvents = new ArrayList();
  
  FileHandler(String paramString, Context paramContext)
  {
    this.filename = paramString;
    this.context = paramContext;
  }
  
  private int getNewID()
  {
    if (this.events.size() > 0)
    {
      this.highestId += 1;
      return this.highestId;
    }
    return 0;
  }
  
  public void OverwriteFile(String paramString)
  {
    try
    {
      FileOutputStream localFileOutputStream = this.context.openFileOutput(this.filename, 0);
      localFileOutputStream.write(paramString.getBytes());
      localFileOutputStream.close();
      return;
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public Event findEventFromView(View paramView, Calendar paramCalendar)
  {
    List localList = getEventsByDay(paramCalendar);
    localList.addAll(getRepeatingEventsByDay(paramCalendar));
    paramView = (TextView)paramView.findViewById(2131099674);
    int i = 0;
    for (;;)
    {
      if (i >= localList.size())
      {
        Log.i("FileHandler", "Null");
        return null;
      }
      if (Integer.parseInt(paramView.getText().toString()) == ((Event)localList.get(i)).id)
      {
        Log.i("FileHandler", "Found");
        return (Event)localList.get(i);
      }
      i += 1;
    }
  }
  
  public String getEventDesc(String paramString)
  {
    int i = 0;
    for (;;)
    {
      if (i >= this.events.size()) {
        return null;
      }
      if (((Event)this.events.get(i)).title.equals(paramString)) {
        return ((Event)this.events.get(i)).desc;
      }
      i += 1;
    }
  }
  
  public List<Event> getEventsByDay(Calendar paramCalendar)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i >= this.events.size()) {
        return localArrayList;
      }
      if ((((Event)this.events.get(i)).cal.get(2) == paramCalendar.get(2)) && (((Event)this.events.get(i)).cal.get(1) == paramCalendar.get(1)) && (((Event)this.events.get(i)).cal.get(5) == paramCalendar.get(5)) && (((Event)this.events.get(i)).interval == 0))
      {
        Event localEvent = new Event();
        localEvent.cal = Calendar.getInstance();
        localEvent.cal.set(6, ((Event)this.events.get(i)).cal.get(6));
        localEvent.cal.set(1, ((Event)this.events.get(i)).cal.get(1));
        localEvent.cal.set(11, ((Event)this.events.get(i)).cal.get(11));
        localEvent.cal.set(12, ((Event)this.events.get(i)).cal.get(12));
        localEvent.desc = ((Event)this.events.get(i)).desc;
        localEvent.title = ((Event)this.events.get(i)).title;
        localEvent.interval = ((Event)this.events.get(i)).interval;
        localEvent.noOfIntervals = ((Event)this.events.get(i)).noOfIntervals;
        localEvent.id = ((Event)this.events.get(i)).id;
        localEvent.imp = ((Event)this.events.get(i)).imp;
        localEvent.type = ((Event)this.events.get(i)).type;
        localEvent.done = ((Event)this.events.get(i)).done;
        localArrayList.add(localEvent);
      }
      i += 1;
    }
  }
  
  public Event getNextEvent()
  {
    Calendar localCalendar = Calendar.getInstance();
    Object localObject1 = new Event();
    ((Event)localObject1).cal = Calendar.getInstance();
    ((Event)localObject1).cal.set(6, 2070);
    int i = 0;
    for (;;)
    {
      if (i >= this.events.size())
      {
        localObject2 = localObject1;
        if (((Event)localObject1).cal.get(6) == 2070) {
          localObject2 = null;
        }
        return (Event)localObject2;
      }
      Object localObject2 = localObject1;
      if (((Event)this.events.get(i)).cal.after(localCalendar))
      {
        localObject2 = localObject1;
        if (((Event)this.events.get(i)).cal.before(((Event)localObject1).cal)) {
          localObject2 = (Event)this.events.get(i);
        }
      }
      i += 1;
      localObject1 = localObject2;
    }
  }
  
  public List<Event> getRepeatingEventsByDay(Calendar paramCalendar)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i >= this.events.size())
      {
        i = 0;
        if (i < localArrayList2.size()) {
          break;
        }
        return localArrayList1;
      }
      if (((Event)this.events.get(i)).interval != 0)
      {
        localObject = new Event();
        ((Event)localObject).cal = Calendar.getInstance();
        ((Event)localObject).cal.set(6, ((Event)this.events.get(i)).cal.get(6));
        ((Event)localObject).cal.set(1, ((Event)this.events.get(i)).cal.get(1));
        ((Event)localObject).cal.set(11, ((Event)this.events.get(i)).cal.get(11));
        ((Event)localObject).cal.set(12, ((Event)this.events.get(i)).cal.get(12));
        ((Event)localObject).desc = ((Event)this.events.get(i)).desc;
        ((Event)localObject).title = ((Event)this.events.get(i)).title;
        ((Event)localObject).interval = ((Event)this.events.get(i)).interval;
        ((Event)localObject).noOfIntervals = ((Event)this.events.get(i)).noOfIntervals;
        ((Event)localObject).id = ((Event)this.events.get(i)).id;
        ((Event)localObject).imp = ((Event)this.events.get(i)).imp;
        ((Event)localObject).type = ((Event)this.events.get(i)).type;
        ((Event)localObject).done = ((Event)this.events.get(i)).done;
        localArrayList2.add(localObject);
      }
      i += 1;
    }
    int k = 0;
    Object localObject = Calendar.getInstance();
    ((Calendar)localObject).set(5, ((Event)localArrayList2.get(i)).cal.get(5));
    ((Calendar)localObject).set(2, ((Event)localArrayList2.get(i)).cal.get(2));
    ((Calendar)localObject).set(1, ((Event)localArrayList2.get(i)).cal.get(1));
    int j = k;
    if (!((Calendar)localObject).before(paramCalendar))
    {
      if ((((Calendar)localObject).get(6) != paramCalendar.get(6)) || (((Calendar)localObject).get(1) != paramCalendar.get(1))) {
        break label615;
      }
      j = k;
    }
    for (;;)
    {
      if (!((Calendar)localObject).before(paramCalendar))
      {
        if ((j % ((Event)localArrayList2.get(i)).interval == 0) && ((j / ((Event)localArrayList2.get(i)).interval <= ((Event)localArrayList2.get(i)).noOfIntervals) || (((Event)localArrayList2.get(i)).noOfIntervals == -1))) {
          localArrayList1.add((Event)localArrayList2.get(i));
        }
        label615:
        i += 1;
        break;
      }
      ((Calendar)localObject).add(5, 1);
      j += 1;
    }
  }
  
  public void loadEventsFromFile()
  {
    this.events.clear();
    StringBuffer localStringBuffer = readFile();
    int i = 0;
    int j = 0;
    Object localObject1 = new Event();
    int k;
    if (localStringBuffer != null) {
      k = 0;
    }
    for (;;)
    {
      if (k >= localStringBuffer.length()) {
        return;
      }
      switch (localStringBuffer.charAt(k))
      {
      default: 
        k += 1;
      }
    }
    ((Event)localObject1).type = Integer.parseInt(localStringBuffer.substring(i, k));
    Object localObject2 = Calendar.getInstance();
    if (((Event)localObject1).cal.get(1) <= ((Calendar)localObject2).get(1)) {
      if (((Event)localObject1).cal.get(1) < ((Calendar)localObject2).get(1)) {
        this.pastEvents.add(localObject1);
      }
    }
    for (;;)
    {
      i = k + 1;
      j = 0;
      break;
      if (((Event)localObject1).cal.get(6) < ((Calendar)localObject2).get(6))
      {
        this.pastEvents.add(localObject1);
      }
      else
      {
        this.events.add(localObject1);
        continue;
        this.events.add(localObject1);
      }
    }
    if (j == 0)
    {
      localObject1 = new Event();
      ((Event)localObject1).cal = Calendar.getInstance();
      ((Event)localObject1).id = Integer.parseInt(localStringBuffer.substring(i, k));
      localObject2 = localObject1;
      if (((Event)localObject1).id > this.highestId)
      {
        this.highestId += 1;
        localObject2 = localObject1;
      }
    }
    for (;;)
    {
      i = k + 1;
      j += 1;
      localObject1 = localObject2;
      break;
      if (j == 1)
      {
        ((Event)localObject1).title = localStringBuffer.substring(i, k);
        localObject2 = localObject1;
      }
      else if (j == 2)
      {
        ((Event)localObject1).cal.set(5, Integer.parseInt(localStringBuffer.substring(i, k)));
        localObject2 = localObject1;
      }
      else if (j == 3)
      {
        ((Event)localObject1).cal.set(2, Integer.parseInt(localStringBuffer.substring(i, k)));
        localObject2 = localObject1;
      }
      else if (j == 4)
      {
        ((Event)localObject1).cal.set(1, Integer.parseInt(localStringBuffer.substring(i, k)));
        localObject2 = localObject1;
      }
      else if (j == 5)
      {
        ((Event)localObject1).cal.set(11, Integer.parseInt(localStringBuffer.substring(i, k)));
        localObject2 = localObject1;
      }
      else if (j == 6)
      {
        ((Event)localObject1).cal.set(12, Integer.parseInt(localStringBuffer.substring(i, k)));
        localObject2 = localObject1;
      }
      else if (j == 7)
      {
        ((Event)localObject1).desc = localStringBuffer.substring(i, k);
        localObject2 = localObject1;
      }
      else if (j == 8)
      {
        ((Event)localObject1).interval = Integer.parseInt(localStringBuffer.substring(i, k));
        localObject2 = localObject1;
      }
      else if (j == 9)
      {
        ((Event)localObject1).noOfIntervals = Integer.parseInt(localStringBuffer.substring(i, k));
        localObject2 = localObject1;
      }
      else
      {
        localObject2 = localObject1;
        if (j == 10)
        {
          ((Event)localObject1).done = Boolean.valueOf(localStringBuffer.substring(i, k)).booleanValue();
          localObject2 = localObject1;
        }
      }
    }
  }
  
  public void printEvents()
  {
    int i = 0;
    for (;;)
    {
      if (i >= this.events.size()) {
        return;
      }
      Toast.makeText(this.context, String.valueOf(i) + ((Event)this.events.get(i)).title, 0).show();
      i += 1;
    }
  }
  
  public StringBuffer readFile()
  {
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(this.context.openFileInput(this.filename)));
      StringBuffer localStringBuffer = new StringBuffer();
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          return localStringBuffer;
        }
        localStringBuffer.append(str);
      }
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void removeEvent(Event paramEvent)
  {
    StringBuffer localStringBuffer = readFile();
    int i2 = 0;
    int n = 0;
    int i1 = 0;
    int i = 0;
    int m;
    if (localStringBuffer != null)
    {
      m = 0;
      if (m < localStringBuffer.length()) {}
    }
    else
    {
      return;
    }
    int j;
    int i4;
    int k;
    int i3;
    if (localStringBuffer.charAt(m) == '¬')
    {
      if (i != 0)
      {
        localStringBuffer.delete(i1, m + 1);
        try
        {
          FileOutputStream localFileOutputStream = this.context.openFileOutput(this.filename, 0);
          localFileOutputStream.write(localStringBuffer.toString().getBytes());
          localFileOutputStream.close();
          this.events.remove(paramEvent);
          return;
        }
        catch (IOException paramEvent)
        {
          paramEvent.printStackTrace();
          return;
        }
      }
      j = m + 1;
      i4 = m + 1;
      k = 0;
      i3 = i;
    }
    for (;;)
    {
      m += 1;
      i = i3;
      i1 = i4;
      i2 = j;
      n = k;
      break;
      i3 = i;
      i4 = i1;
      j = i2;
      k = n;
      if (localStringBuffer.charAt(m) == '|')
      {
        j = i;
        if (n == 0)
        {
          j = i;
          if (Integer.valueOf(localStringBuffer.substring(i2, m)).intValue() == paramEvent.id) {
            j = 1;
          }
        }
        i = m + 1;
        k = n + 1;
        i3 = j;
        i4 = i1;
        j = i;
      }
    }
  }
  
  public void writeEvent(Event paramEvent)
  {
    try
    {
      FileOutputStream localFileOutputStream = this.context.openFileOutput(this.filename, 32768);
      paramEvent.id = getNewID();
      localFileOutputStream.write(String.valueOf(paramEvent.id + "|" + paramEvent.title + "|" + paramEvent.cal.get(5) + "|" + paramEvent.cal.get(2) + "|" + paramEvent.cal.get(1) + "|" + paramEvent.cal.get(11) + "|" + paramEvent.cal.get(12) + "|" + paramEvent.desc + "|" + paramEvent.interval + "|" + paramEvent.noOfIntervals + "|" + String.valueOf(paramEvent.done) + "|" + paramEvent.type + "¬").getBytes());
      localFileOutputStream.close();
      this.events.add(paramEvent);
      return;
    }
    catch (IOException paramEvent)
    {
      paramEvent.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\example\organiser_m\FileHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */