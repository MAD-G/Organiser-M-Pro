package hirondelle.date4j;

final class DateTimeInterval
{
  private static final int MAX = 9999;
  private static final int MAX_NANOS = 999999999;
  private static final int MIN = 0;
  private static final boolean MINUS = false;
  private static final int MIN_NANOS = 0;
  private static final boolean PLUS = true;
  private Integer fDay;
  private int fDayIncr;
  private DateTime.DayOverflow fDayOverflow;
  private final DateTime fFrom;
  private Integer fHour;
  private int fHourIncr;
  private boolean fIsPlus;
  private Integer fMinute;
  private int fMinuteIncr;
  private Integer fMonth;
  private int fMonthIncr;
  private Integer fNanosecond;
  private int fNanosecondIncr;
  private Integer fSecond;
  private int fSecondIncr;
  private Integer fYear;
  private int fYearIncr;
  
  DateTimeInterval(DateTime paramDateTime, DateTime.DayOverflow paramDayOverflow)
  {
    this.fFrom = paramDateTime;
    checkUnits();
    if (this.fFrom.getYear() == null)
    {
      i = 1;
      this.fYear = Integer.valueOf(i);
      if (this.fFrom.getMonth() != null) {
        break label181;
      }
      i = 1;
      label51:
      this.fMonth = Integer.valueOf(i);
      if (this.fFrom.getDay() != null) {
        break label195;
      }
      i = k;
      label72:
      this.fDay = Integer.valueOf(i);
      if (this.fFrom.getHour() != null) {
        break label209;
      }
      i = 0;
      label92:
      this.fHour = Integer.valueOf(i);
      if (this.fFrom.getMinute() != null) {
        break label223;
      }
      i = 0;
      label112:
      this.fMinute = Integer.valueOf(i);
      if (this.fFrom.getSecond() != null) {
        break label237;
      }
      i = 0;
      label132:
      this.fSecond = Integer.valueOf(i);
      if (this.fFrom.getNanoseconds() != null) {
        break label251;
      }
    }
    label181:
    label195:
    label209:
    label223:
    label237:
    label251:
    for (int i = j;; i = this.fFrom.getNanoseconds().intValue())
    {
      this.fNanosecond = Integer.valueOf(i);
      this.fDayOverflow = paramDayOverflow;
      return;
      i = this.fFrom.getYear().intValue();
      break;
      i = this.fFrom.getMonth().intValue();
      break label51;
      i = this.fFrom.getDay().intValue();
      break label72;
      i = this.fFrom.getHour().intValue();
      break label92;
      i = this.fFrom.getMinute().intValue();
      break label112;
      i = this.fFrom.getSecond().intValue();
      break label132;
    }
  }
  
  private void changeDay()
  {
    int i = 0;
    while (i < this.fDayIncr)
    {
      stepDay();
      i += 1;
    }
  }
  
  private void changeHour()
  {
    int i = 0;
    while (i < this.fHourIncr)
    {
      stepHour();
      i += 1;
    }
  }
  
  private void changeMinute()
  {
    int i = 0;
    while (i < this.fMinuteIncr)
    {
      stepMinute();
      i += 1;
    }
  }
  
  private void changeMonth()
  {
    int i = 0;
    while (i < this.fMonthIncr)
    {
      stepMonth();
      i += 1;
    }
  }
  
  private void changeNanosecond()
  {
    if (this.fIsPlus)
    {
      this.fNanosecond = Integer.valueOf(this.fNanosecond.intValue() + this.fNanosecondIncr);
      if (this.fNanosecond.intValue() <= 999999999) {
        break label84;
      }
      stepSecond();
      this.fNanosecond = Integer.valueOf(this.fNanosecond.intValue() - 999999999 - 1);
    }
    label84:
    while (this.fNanosecond.intValue() >= 0)
    {
      return;
      this.fNanosecond = Integer.valueOf(this.fNanosecond.intValue() - this.fNanosecondIncr);
      break;
    }
    stepSecond();
    this.fNanosecond = Integer.valueOf(this.fNanosecond.intValue() + 999999999 + 1);
  }
  
  private void changeSecond()
  {
    int i = 0;
    while (i < this.fSecondIncr)
    {
      stepSecond();
      i += 1;
    }
  }
  
  private void changeYear()
  {
    if (this.fIsPlus)
    {
      this.fYear = Integer.valueOf(this.fYear.intValue() + this.fYearIncr);
      return;
    }
    this.fYear = Integer.valueOf(this.fFrom.getYear().intValue() - this.fYearIncr);
  }
  
  private void checkRange(Integer paramInteger, String paramString)
  {
    if ((paramInteger.intValue() < 0) || (paramInteger.intValue() > 9999)) {
      throw new IllegalArgumentException(paramString + " is not in the range " + 0 + ".." + 9999);
    }
  }
  
  private void checkRangeNanos(Integer paramInteger)
  {
    if ((paramInteger.intValue() < 0) || (paramInteger.intValue() > 999999999)) {
      throw new IllegalArgumentException("Nanosecond interval is not in the range 0..999999999");
    }
  }
  
  private void checkUnits()
  {
    int j = 1;
    int i;
    if (this.fFrom.unitsAllPresent(new DateTime.Unit[] { DateTime.Unit.YEAR, DateTime.Unit.MONTH, DateTime.Unit.DAY, DateTime.Unit.HOUR, DateTime.Unit.MINUTE, DateTime.Unit.SECOND })) {
      i = j;
    }
    while (i == 0)
    {
      throw new IllegalArgumentException("For interval calculations, DateTime must have year-month-day, or hour-minute-second, or both.");
      if (this.fFrom.unitsAllPresent(new DateTime.Unit[] { DateTime.Unit.YEAR, DateTime.Unit.MONTH, DateTime.Unit.DAY }))
      {
        i = j;
        if (this.fFrom.unitsAllAbsent(new DateTime.Unit[] { DateTime.Unit.HOUR, DateTime.Unit.MINUTE, DateTime.Unit.SECOND })) {}
      }
      else if (this.fFrom.unitsAllAbsent(new DateTime.Unit[] { DateTime.Unit.YEAR, DateTime.Unit.MONTH, DateTime.Unit.DAY }))
      {
        i = j;
        if (this.fFrom.unitsAllPresent(new DateTime.Unit[] { DateTime.Unit.HOUR, DateTime.Unit.MINUTE, DateTime.Unit.SECOND })) {}
      }
      else
      {
        i = 0;
      }
    }
  }
  
  private void handleMonthOverflow()
  {
    int i = numDaysInMonth();
    if (this.fDay.intValue() > i)
    {
      if (DateTime.DayOverflow.Abort == this.fDayOverflow) {
        throw new RuntimeException("Day Overflow: Year:" + this.fYear + " Month:" + this.fMonth + " has " + i + " days, but day has value:" + this.fDay + " To avoid these exceptions, please specify a different DayOverflow policy.");
      }
      if (DateTime.DayOverflow.FirstDay != this.fDayOverflow) {
        break label117;
      }
      this.fDay = Integer.valueOf(1);
      stepMonth();
    }
    label117:
    do
    {
      return;
      if (DateTime.DayOverflow.LastDay == this.fDayOverflow)
      {
        this.fDay = Integer.valueOf(i);
        return;
      }
    } while (DateTime.DayOverflow.Spillover != this.fDayOverflow);
    this.fDay = Integer.valueOf(this.fDay.intValue() - i);
    stepMonth();
  }
  
  private int numDaysInMonth()
  {
    return DateTime.getNumDaysInMonth(this.fYear, this.fMonth).intValue();
  }
  
  private int numDaysInPreviousMonth()
  {
    if (this.fMonth.intValue() > 1) {
      return DateTime.getNumDaysInMonth(this.fYear, Integer.valueOf(this.fMonth.intValue() - 1)).intValue();
    }
    return DateTime.getNumDaysInMonth(Integer.valueOf(this.fYear.intValue() - 1), Integer.valueOf(12)).intValue();
  }
  
  private DateTime plusOrMinus(boolean paramBoolean, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5, Integer paramInteger6, Integer paramInteger7)
  {
    this.fIsPlus = paramBoolean;
    this.fYearIncr = paramInteger1.intValue();
    this.fMonthIncr = paramInteger2.intValue();
    this.fDayIncr = paramInteger3.intValue();
    this.fHourIncr = paramInteger4.intValue();
    this.fMinuteIncr = paramInteger5.intValue();
    this.fSecondIncr = paramInteger6.intValue();
    this.fNanosecondIncr = paramInteger7.intValue();
    checkRange(Integer.valueOf(this.fYearIncr), "Year");
    checkRange(Integer.valueOf(this.fMonthIncr), "Month");
    checkRange(Integer.valueOf(this.fDayIncr), "Day");
    checkRange(Integer.valueOf(this.fHourIncr), "Hour");
    checkRange(Integer.valueOf(this.fMinuteIncr), "Minute");
    checkRange(Integer.valueOf(this.fSecondIncr), "Second");
    checkRangeNanos(Integer.valueOf(this.fNanosecondIncr));
    changeYear();
    changeMonth();
    handleMonthOverflow();
    changeDay();
    changeHour();
    changeMinute();
    changeSecond();
    changeNanosecond();
    return new DateTime(this.fYear, this.fMonth, this.fDay, this.fHour, this.fMinute, this.fSecond, this.fNanosecond);
  }
  
  private void stepDay()
  {
    if (this.fIsPlus)
    {
      this.fDay = Integer.valueOf(this.fDay.intValue() + 1);
      if (this.fDay.intValue() <= numDaysInMonth()) {
        break label69;
      }
      this.fDay = Integer.valueOf(1);
      stepMonth();
    }
    label69:
    while (this.fDay.intValue() >= 1)
    {
      return;
      this.fDay = Integer.valueOf(this.fDay.intValue() - 1);
      break;
    }
    this.fDay = Integer.valueOf(numDaysInPreviousMonth());
    stepMonth();
  }
  
  private void stepHour()
  {
    if (this.fIsPlus)
    {
      this.fHour = Integer.valueOf(this.fHour.intValue() + 1);
      if (this.fHour.intValue() <= 23) {
        break label67;
      }
      this.fHour = Integer.valueOf(0);
      stepDay();
    }
    label67:
    while (this.fHour.intValue() >= 0)
    {
      return;
      this.fHour = Integer.valueOf(this.fHour.intValue() - 1);
      break;
    }
    this.fHour = Integer.valueOf(23);
    stepDay();
  }
  
  private void stepMinute()
  {
    if (this.fIsPlus)
    {
      this.fMinute = Integer.valueOf(this.fMinute.intValue() + 1);
      if (this.fMinute.intValue() <= 59) {
        break label67;
      }
      this.fMinute = Integer.valueOf(0);
      stepHour();
    }
    label67:
    while (this.fMinute.intValue() >= 0)
    {
      return;
      this.fMinute = Integer.valueOf(this.fMinute.intValue() - 1);
      break;
    }
    this.fMinute = Integer.valueOf(59);
    stepHour();
  }
  
  private void stepMonth()
  {
    if (this.fIsPlus)
    {
      this.fMonth = Integer.valueOf(this.fMonth.intValue() + 1);
      if (this.fMonth.intValue() <= 12) {
        break label67;
      }
      this.fMonth = Integer.valueOf(1);
      stepYear();
    }
    label67:
    while (this.fMonth.intValue() >= 1)
    {
      return;
      this.fMonth = Integer.valueOf(this.fMonth.intValue() - 1);
      break;
    }
    this.fMonth = Integer.valueOf(12);
    stepYear();
  }
  
  private void stepSecond()
  {
    if (this.fIsPlus)
    {
      this.fSecond = Integer.valueOf(this.fSecond.intValue() + 1);
      if (this.fSecond.intValue() <= 59) {
        break label67;
      }
      this.fSecond = Integer.valueOf(0);
      stepMinute();
    }
    label67:
    while (this.fSecond.intValue() >= 0)
    {
      return;
      this.fSecond = Integer.valueOf(this.fSecond.intValue() - 1);
      break;
    }
    this.fSecond = Integer.valueOf(59);
    stepMinute();
  }
  
  private void stepYear()
  {
    if (this.fIsPlus)
    {
      this.fYear = Integer.valueOf(this.fYear.intValue() + 1);
      return;
    }
    this.fYear = Integer.valueOf(this.fYear.intValue() - 1);
  }
  
  DateTime minus(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    return plusOrMinus(false, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6), Integer.valueOf(paramInt7));
  }
  
  DateTime plus(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    return plusOrMinus(true, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4), Integer.valueOf(paramInt5), Integer.valueOf(paramInt6), Integer.valueOf(paramInt7));
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\hirondelle\date4j\DateTimeInterval.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */