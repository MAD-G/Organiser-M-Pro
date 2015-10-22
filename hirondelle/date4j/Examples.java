package hirondelle.date4j;

import java.io.PrintStream;
import java.util.Locale;
import java.util.TimeZone;

public final class Examples
{
  private void ageIfBornOnCertainDate()
  {
    DateTime localDateTime1 = DateTime.today(TimeZone.getDefault());
    DateTime localDateTime2 = DateTime.forDateOnly(Integer.valueOf(1995), Integer.valueOf(5), Integer.valueOf(16));
    int j = localDateTime1.getYear().intValue() - localDateTime2.getYear().intValue();
    int i = j;
    if (localDateTime1.getDayOfYear().intValue() < localDateTime2.getDayOfYear().intValue()) {
      i = j - 1;
    }
    log("Age of someone born May 16, 1995 is : " + i);
  }
  
  private void currentDateTime()
  {
    String str = DateTime.now(TimeZone.getDefault()).format("YYYY-MM-DD hh:mm:ss");
    log("Current date-time in default time zone : " + str);
  }
  
  private void currentDateTimeInCairo()
  {
    String str = DateTime.now(TimeZone.getTimeZone("Africa/Cairo")).format("YYYY-MM-DD hh:mm:ss (WWWW)", Locale.getDefault());
    log("Current date-time in Cairo : " + str);
  }
  
  private void daysTillChristmas()
  {
    DateTime localDateTime1 = DateTime.today(TimeZone.getDefault());
    DateTime localDateTime2 = DateTime.forDateOnly(localDateTime1.getYear(), Integer.valueOf(12), Integer.valueOf(25));
    int i = 0;
    if (localDateTime1.isSameDayAs(localDateTime2)) {}
    for (;;)
    {
      log("Number of days till Christmas : " + i);
      return;
      if (localDateTime1.lt(localDateTime2)) {
        i = localDateTime1.numDaysFrom(localDateTime2);
      } else if (localDateTime1.gt(localDateTime2)) {
        i = localDateTime1.numDaysFrom(DateTime.forDateOnly(Integer.valueOf(localDateTime1.getYear().intValue() + 1), Integer.valueOf(12), Integer.valueOf(25)));
      }
    }
  }
  
  private void firstDayOfThisWeek()
  {
    DateTime localDateTime2 = DateTime.today(TimeZone.getDefault());
    int i = localDateTime2.getWeekDay().intValue();
    DateTime localDateTime1 = localDateTime2;
    if (i > 1) {
      localDateTime1 = localDateTime2.minusDays(Integer.valueOf(i - 1));
    }
    log("The first day of this week is : " + localDateTime1);
  }
  
  private void hoursDifferenceBetweenParisAndPerth()
  {
    DateTime localDateTime = DateTime.now(TimeZone.getTimeZone("Europe/Paris"));
    int j = DateTime.now(TimeZone.getTimeZone("Australia/Perth")).getHour().intValue() - localDateTime.getHour().intValue();
    int i = j;
    if (j < 0) {
      i = j + 24;
    }
    log("Numbers of hours difference between Paris and Perth : " + i);
  }
  
  private void imitateISOFormat()
  {
    DateTime localDateTime = DateTime.now(TimeZone.getDefault());
    log("Output using an ISO format: " + localDateTime.format("YYYY-MM-DDThh:mm:ss"));
  }
  
  private void jdkDatesSuctorial()
  {
    DateTime localDateTime1 = DateTime.today(TimeZone.getDefault());
    DateTime localDateTime2 = DateTime.forDateOnly(Integer.valueOf(1996), Integer.valueOf(1), Integer.valueOf(23));
    int i = localDateTime1.getYear().intValue();
    int j = localDateTime2.getYear().intValue();
    log("The number of years the JDK date-time API has been suctorial : " + (i - j));
  }
  
  private static void log(Object paramObject)
  {
    System.out.println(String.valueOf(paramObject));
  }
  
  public static void main(String... paramVarArgs)
  {
    paramVarArgs = new Examples();
    paramVarArgs.currentDateTime();
    paramVarArgs.currentDateTimeInCairo();
    paramVarArgs.ageIfBornOnCertainDate();
    paramVarArgs.optionsExpiry();
    paramVarArgs.daysTillChristmas();
    paramVarArgs.whenIs90DaysFromToday();
    paramVarArgs.whenIs3Months5DaysFromToday();
    paramVarArgs.hoursDifferenceBetweenParisAndPerth();
    paramVarArgs.weeksSinceStart();
    paramVarArgs.timeTillMidnight();
    paramVarArgs.imitateISOFormat();
    paramVarArgs.firstDayOfThisWeek();
    paramVarArgs.jdkDatesSuctorial();
  }
  
  private void optionsExpiry()
  {
    DateTime localDateTime = DateTime.today(TimeZone.getDefault()).getStartOfMonth();
    if (localDateTime.getWeekDay().intValue() == 7) {}
    for (int i = 21;; i = 21 - localDateTime.getWeekDay().intValue())
    {
      localDateTime = DateTime.forDateOnly(localDateTime.getYear(), localDateTime.getMonth(), Integer.valueOf(i));
      log("The 3rd Friday of this month is : " + localDateTime.format("YYYY-MM-DD"));
      return;
    }
  }
  
  private void timeTillMidnight()
  {
    DateTime localDateTime = DateTime.now(TimeZone.getDefault());
    long l = localDateTime.numSecondsFrom(localDateTime.plusDays(Integer.valueOf(1)).getStartOfDay());
    log("This many seconds till midnight : " + l);
  }
  
  private void weeksSinceStart()
  {
    DateTime localDateTime1 = DateTime.today(TimeZone.getDefault());
    DateTime localDateTime2 = DateTime.forDateOnly(Integer.valueOf(2010), Integer.valueOf(9), Integer.valueOf(6));
    int i = localDateTime1.getWeekIndex().intValue();
    int j = localDateTime2.getWeekIndex().intValue();
    log("The number of weeks since Sep 6, 2010 : " + (i - j));
  }
  
  private void whenIs3Months5DaysFromToday()
  {
    DateTime localDateTime = DateTime.today(TimeZone.getDefault()).plus(Integer.valueOf(0), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.FirstDay);
    log("3 months and 5 days from today is : " + localDateTime.format("YYYY-MM-DD"));
  }
  
  private void whenIs90DaysFromToday()
  {
    DateTime localDateTime = DateTime.today(TimeZone.getDefault());
    log("90 days from today is : " + localDateTime.plusDays(Integer.valueOf(90)).format("YYYY-MM-DD"));
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\hirondelle\date4j\Examples.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */