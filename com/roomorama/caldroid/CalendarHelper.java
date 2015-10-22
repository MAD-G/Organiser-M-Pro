package com.roomorama.caldroid;

import hirondelle.date4j.DateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class CalendarHelper
{
  public static SimpleDateFormat MMMFormat = new SimpleDateFormat("MMM", Locale.getDefault());
  public static SimpleDateFormat yyyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
  
  public static Date convertDateTimeToDate(DateTime paramDateTime)
  {
    int i = paramDateTime.getYear().intValue();
    int j = paramDateTime.getMonth().intValue();
    int k = paramDateTime.getDay().intValue();
    paramDateTime = Calendar.getInstance();
    paramDateTime.clear();
    paramDateTime.set(i, j - 1, k);
    return paramDateTime.getTime();
  }
  
  public static DateTime convertDateToDateTime(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.clear();
    localCalendar.setTime(paramDate);
    return new DateTime(Integer.valueOf(localCalendar.get(1)), Integer.valueOf(localCalendar.get(2) + 1), Integer.valueOf(localCalendar.get(5)), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
  }
  
  public static ArrayList<String> convertToStringList(ArrayList<DateTime> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList();
    paramArrayList = paramArrayList.iterator();
    for (;;)
    {
      if (!paramArrayList.hasNext()) {
        return localArrayList;
      }
      localArrayList.add(((DateTime)paramArrayList.next()).format("YYYY-MM-DD"));
    }
  }
  
  public static Date getDateFromString(String paramString1, String paramString2)
    throws ParseException
  {
    if (paramString2 == null) {}
    for (paramString2 = yyyyMMddFormat;; paramString2 = new SimpleDateFormat(paramString2, Locale.ENGLISH)) {
      return paramString2.parse(paramString1);
    }
  }
  
  public static DateTime getDateTimeFromString(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = convertDateToDateTime(getDateFromString(paramString1, paramString2));
      return paramString1;
    }
    catch (ParseException paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public static ArrayList<DateTime> getFullWeeks(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    DateTime localDateTime2 = new DateTime(Integer.valueOf(paramInt2), Integer.valueOf(paramInt1), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
    DateTime localDateTime1 = localDateTime2.plusDays(Integer.valueOf(localDateTime2.getNumDaysInMonth() - 1));
    paramInt2 = localDateTime2.getWeekDay().intValue();
    paramInt1 = paramInt2;
    if (paramInt2 < paramInt3) {
      paramInt1 = paramInt2 + 7;
    }
    if (paramInt1 <= 0)
    {
      label88:
      paramInt1 = 0;
      label90:
      if (paramInt1 < localDateTime1.getDay().intValue()) {
        break label246;
      }
      paramInt2 = paramInt3 - 1;
      paramInt1 = paramInt2;
      if (paramInt2 == 0) {
        paramInt1 = 7;
      }
      if (localDateTime1.getWeekDay().intValue() != paramInt1)
      {
        paramInt2 = 1;
        do
        {
          localDateTime2 = localDateTime1.plusDays(Integer.valueOf(paramInt2));
          localArrayList.add(localDateTime2);
          paramInt2 += 1;
        } while (localDateTime2.getWeekDay().intValue() != paramInt1);
      }
      if (paramBoolean)
      {
        paramInt1 = localArrayList.size();
        paramInt2 = paramInt1 / 7;
        localDateTime1 = (DateTime)localArrayList.get(paramInt1 - 1);
        paramInt1 = 1;
      }
    }
    for (;;)
    {
      if (paramInt1 > (6 - paramInt2) * 7)
      {
        return localArrayList;
        DateTime localDateTime3 = localDateTime2.minusDays(Integer.valueOf(paramInt1 - paramInt3));
        if (!localDateTime3.lt(localDateTime2)) {
          break label88;
        }
        localArrayList.add(localDateTime3);
        paramInt1 -= 1;
        break;
        label246:
        localArrayList.add(localDateTime2.plusDays(Integer.valueOf(paramInt1)));
        paramInt1 += 1;
        break label90;
      }
      localArrayList.add(localDateTime1.plusDays(Integer.valueOf(paramInt1)));
      paramInt1 += 1;
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\roomorama\caldroid\CalendarHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */