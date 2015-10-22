package hirondelle.date4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DateTimeFormatter
{
  private static final int AM = 0;
  private static final String D = "D";
  private static final String DD = "DD";
  private static final String EMPTY_STRING = "";
  private static final Pattern ESCAPED_RANGE = Pattern.compile("\\|[^\\|]*\\|");
  private static final String ESCAPE_CHAR = "|";
  private static final Pattern FRACTIONALS = Pattern.compile("f{1,9}");
  private static final String M = "M";
  private static final String MM = "MM";
  private static final String MMM = "MMM";
  private static final String MMMM = "MMMM";
  private static final int PM = 1;
  private static final List<String> TOKENS = new ArrayList();
  private static final String WWW = "WWW";
  private static final String WWWW = "WWWW";
  private static final String YY = "YY";
  private static final String YYYY = "YYYY";
  private static final String a = "a";
  private static final String h = "h";
  private static final String h12 = "h12";
  private static final String hh = "hh";
  private static final String hh12 = "hh12";
  private static final String m = "m";
  private static final String mm = "mm";
  private static final String s = "s";
  private static final String ss = "ss";
  private final Map<Locale, List<String>> fAmPm = new LinkedHashMap();
  private final CustomLocalization fCustomLocalization;
  private Collection<EscapedRange> fEscapedRanges;
  private final String fFormat;
  private Collection<InterpretedRange> fInterpretedRanges;
  private final Locale fLocale;
  private final Map<Locale, List<String>> fMonths = new LinkedHashMap();
  private final Map<Locale, List<String>> fWeekdays = new LinkedHashMap();
  
  static
  {
    TOKENS.add("YYYY");
    TOKENS.add("YY");
    TOKENS.add("MMMM");
    TOKENS.add("MMM");
    TOKENS.add("MM");
    TOKENS.add("M");
    TOKENS.add("DD");
    TOKENS.add("D");
    TOKENS.add("WWWW");
    TOKENS.add("WWW");
    TOKENS.add("hh12");
    TOKENS.add("h12");
    TOKENS.add("hh");
    TOKENS.add("h");
    TOKENS.add("mm");
    TOKENS.add("m");
    TOKENS.add("ss");
    TOKENS.add("s");
    TOKENS.add("a");
    TOKENS.add("fffffffff");
    TOKENS.add("ffffffff");
    TOKENS.add("fffffff");
    TOKENS.add("ffffff");
    TOKENS.add("fffff");
    TOKENS.add("ffff");
    TOKENS.add("fff");
    TOKENS.add("ff");
    TOKENS.add("f");
  }
  
  DateTimeFormatter(String paramString)
  {
    this.fFormat = paramString;
    this.fLocale = null;
    this.fCustomLocalization = null;
    validateState();
  }
  
  DateTimeFormatter(String paramString, List<String> paramList1, List<String> paramList2, List<String> paramList3)
  {
    this.fFormat = paramString;
    this.fLocale = null;
    this.fCustomLocalization = new CustomLocalization(paramList1, paramList2, paramList3);
    validateState();
  }
  
  DateTimeFormatter(String paramString, Locale paramLocale)
  {
    this.fFormat = paramString;
    this.fLocale = paramLocale;
    this.fCustomLocalization = null;
    validateState();
  }
  
  private String addLeadingZero(String paramString)
  {
    String str = paramString;
    if (Util.textHasContent(paramString))
    {
      str = paramString;
      if (paramString.length() == 1) {
        str = "0" + paramString;
      }
    }
    return str;
  }
  
  private String amPmIndicator(Integer paramInteger)
  {
    String str = "";
    if (paramInteger != null)
    {
      if (this.fCustomLocalization != null) {
        str = lookupCustomAmPmFor(paramInteger);
      }
    }
    else {
      return str;
    }
    if (this.fLocale != null) {
      return lookupAmPmFor(paramInteger);
    }
    throw new IllegalArgumentException("Your date pattern requires either a Locale, or your own custom localizations for text:" + Util.quote(this.fFormat));
  }
  
  private void findEscapedRanges()
  {
    Matcher localMatcher = ESCAPED_RANGE.matcher(this.fFormat);
    while (localMatcher.find())
    {
      EscapedRange localEscapedRange = new EscapedRange(null);
      localEscapedRange.Start = localMatcher.start();
      localEscapedRange.End = (localMatcher.end() - 1);
      this.fEscapedRanges.add(localEscapedRange);
    }
  }
  
  private String firstNChars(String paramString, int paramInt)
  {
    String str = paramString;
    if (Util.textHasContent(paramString))
    {
      str = paramString;
      if (paramString.length() >= paramInt) {
        str = paramString.substring(0, paramInt);
      }
    }
    return str;
  }
  
  private String firstThreeChars(String paramString)
  {
    String str = paramString;
    if (Util.textHasContent(paramString))
    {
      str = paramString;
      if (paramString.length() >= 3) {
        str = paramString.substring(0, 3);
      }
    }
    return str;
  }
  
  private String fullMonth(Integer paramInteger)
  {
    String str = "";
    if (paramInteger != null)
    {
      if (this.fCustomLocalization != null) {
        str = lookupCustomMonthFor(paramInteger);
      }
    }
    else {
      return str;
    }
    if (this.fLocale != null) {
      return lookupMonthFor(paramInteger);
    }
    throw new IllegalArgumentException("Your date pattern requires either a Locale, or your own custom localizations for text:" + Util.quote(this.fFormat));
  }
  
  private String fullWeekday(Integer paramInteger)
  {
    String str = "";
    if (paramInteger != null)
    {
      if (this.fCustomLocalization != null) {
        str = lookupCustomWeekdayFor(paramInteger);
      }
    }
    else {
      return str;
    }
    if (this.fLocale != null) {
      return lookupWeekdayFor(paramInteger);
    }
    throw new IllegalArgumentException("Your date pattern requires either a Locale, or your own custom localizations for text:" + Util.quote(this.fFormat));
  }
  
  private String getAmPmTextFor(Integer paramInteger)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("a", this.fLocale);
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    localGregorianCalendar.set(1, 2000);
    localGregorianCalendar.set(2, 6);
    localGregorianCalendar.set(5, 15);
    localGregorianCalendar.set(11, paramInteger.intValue());
    return localSimpleDateFormat.format(localGregorianCalendar.getTime());
  }
  
  private InterpretedRange getInterpretation(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = this.fInterpretedRanges.iterator();
    if (localIterator.hasNext())
    {
      InterpretedRange localInterpretedRange = (InterpretedRange)localIterator.next();
      if (localInterpretedRange.Start != paramInt) {
        break label49;
      }
      localObject = localInterpretedRange;
    }
    label49:
    for (;;)
    {
      break;
      return (InterpretedRange)localObject;
    }
  }
  
  private void interpretInput(DateTime paramDateTime)
  {
    String str1 = this.fFormat;
    Iterator localIterator = TOKENS.iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      Matcher localMatcher = Pattern.compile(str2).matcher(str1);
      while (localMatcher.find())
      {
        InterpretedRange localInterpretedRange = new InterpretedRange(null);
        localInterpretedRange.Start = localMatcher.start();
        localInterpretedRange.End = (localMatcher.end() - 1);
        if (!isInEscapedRange(localInterpretedRange))
        {
          localInterpretedRange.Text = interpretThe(localMatcher.group(), paramDateTime);
          this.fInterpretedRanges.add(localInterpretedRange);
        }
      }
      str1 = str1.replace(str2, withCharDenotingAlreadyInterpreted(str2));
    }
  }
  
  private String interpretThe(String paramString, DateTime paramDateTime)
  {
    if ("YYYY".equals(paramString)) {
      return valueStr(paramDateTime.getYear());
    }
    if ("YY".equals(paramString)) {
      return noCentury(valueStr(paramDateTime.getYear()));
    }
    if ("MMMM".equals(paramString)) {
      return fullMonth(Integer.valueOf(paramDateTime.getMonth().intValue()));
    }
    if ("MMM".equals(paramString)) {
      return firstThreeChars(fullMonth(Integer.valueOf(paramDateTime.getMonth().intValue())));
    }
    if ("MM".equals(paramString)) {
      return addLeadingZero(valueStr(paramDateTime.getMonth()));
    }
    if ("M".equals(paramString)) {
      return valueStr(paramDateTime.getMonth());
    }
    if ("DD".equals(paramString)) {
      return addLeadingZero(valueStr(paramDateTime.getDay()));
    }
    if ("D".equals(paramString)) {
      return valueStr(paramDateTime.getDay());
    }
    if ("WWWW".equals(paramString)) {
      return fullWeekday(Integer.valueOf(paramDateTime.getWeekDay().intValue()));
    }
    if ("WWW".equals(paramString)) {
      return firstThreeChars(fullWeekday(Integer.valueOf(paramDateTime.getWeekDay().intValue())));
    }
    if ("hh".equals(paramString)) {
      return addLeadingZero(valueStr(paramDateTime.getHour()));
    }
    if ("h".equals(paramString)) {
      return valueStr(paramDateTime.getHour());
    }
    if ("h12".equals(paramString)) {
      return valueStr(twelveHourStyle(paramDateTime.getHour()));
    }
    if ("hh12".equals(paramString)) {
      return addLeadingZero(valueStr(twelveHourStyle(paramDateTime.getHour())));
    }
    if ("a".equals(paramString)) {
      return amPmIndicator(Integer.valueOf(paramDateTime.getHour().intValue()));
    }
    if ("mm".equals(paramString)) {
      return addLeadingZero(valueStr(paramDateTime.getMinute()));
    }
    if ("m".equals(paramString)) {
      return valueStr(paramDateTime.getMinute());
    }
    if ("ss".equals(paramString)) {
      return addLeadingZero(valueStr(paramDateTime.getSecond()));
    }
    if ("s".equals(paramString)) {
      return valueStr(paramDateTime.getSecond());
    }
    if (paramString.startsWith("f"))
    {
      if (FRACTIONALS.matcher(paramString).matches()) {
        return firstNChars(nanosWithLeadingZeroes(paramDateTime.getNanoseconds()), paramString.length());
      }
      throw new IllegalArgumentException("Unknown token in date formatting pattern: " + paramString);
    }
    throw new IllegalArgumentException("Unknown token in date formatting pattern: " + paramString);
  }
  
  private boolean isInEscapedRange(InterpretedRange paramInterpretedRange)
  {
    Iterator localIterator = this.fEscapedRanges.iterator();
    while (localIterator.hasNext())
    {
      EscapedRange localEscapedRange = (EscapedRange)localIterator.next();
      if ((localEscapedRange.Start <= paramInterpretedRange.Start) && (paramInterpretedRange.Start <= localEscapedRange.End)) {
        return true;
      }
    }
    return false;
  }
  
  private String lookupAmPmFor(Integer paramInteger)
  {
    if (!this.fAmPm.containsKey(this.fLocale))
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(getAmPmTextFor(Integer.valueOf(6)));
      localArrayList.add(getAmPmTextFor(Integer.valueOf(18)));
      this.fAmPm.put(this.fLocale, localArrayList);
    }
    if (paramInteger.intValue() < 12) {
      return (String)((List)this.fAmPm.get(this.fLocale)).get(0);
    }
    return (String)((List)this.fAmPm.get(this.fLocale)).get(1);
  }
  
  private String lookupCustomAmPmFor(Integer paramInteger)
  {
    if (paramInteger.intValue() < 12) {
      return (String)this.fCustomLocalization.AmPmIndicators.get(0);
    }
    return (String)this.fCustomLocalization.AmPmIndicators.get(1);
  }
  
  private String lookupCustomMonthFor(Integer paramInteger)
  {
    return (String)this.fCustomLocalization.Months.get(paramInteger.intValue() - 1);
  }
  
  private String lookupCustomWeekdayFor(Integer paramInteger)
  {
    return (String)this.fCustomLocalization.Weekdays.get(paramInteger.intValue() - 1);
  }
  
  private String lookupMonthFor(Integer paramInteger)
  {
    if (!this.fMonths.containsKey(this.fLocale))
    {
      ArrayList localArrayList = new ArrayList();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MMMM", this.fLocale);
      int i = 0;
      while (i <= 11)
      {
        GregorianCalendar localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.set(1, 2000);
        localGregorianCalendar.set(2, i);
        localGregorianCalendar.set(5, 15);
        localArrayList.add(localSimpleDateFormat.format(localGregorianCalendar.getTime()));
        i += 1;
      }
      this.fMonths.put(this.fLocale, localArrayList);
    }
    return (String)((List)this.fMonths.get(this.fLocale)).get(paramInteger.intValue() - 1);
  }
  
  private String lookupWeekdayFor(Integer paramInteger)
  {
    if (!this.fWeekdays.containsKey(this.fLocale))
    {
      ArrayList localArrayList = new ArrayList();
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEEE", this.fLocale);
      int i = 8;
      while (i <= 14)
      {
        GregorianCalendar localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.set(1, 2009);
        localGregorianCalendar.set(2, 1);
        localGregorianCalendar.set(5, i);
        localArrayList.add(localSimpleDateFormat.format(localGregorianCalendar.getTime()));
        i += 1;
      }
      this.fWeekdays.put(this.fLocale, localArrayList);
    }
    return (String)((List)this.fWeekdays.get(this.fLocale)).get(paramInteger.intValue() - 1);
  }
  
  private String nanosWithLeadingZeroes(Integer paramInteger)
  {
    for (paramInteger = valueStr(paramInteger); paramInteger.length() < 9; paramInteger = "0" + paramInteger) {}
    return paramInteger;
  }
  
  private String nextLetter(int paramInt)
  {
    return this.fFormat.substring(paramInt, paramInt + 1);
  }
  
  private String noCentury(String paramString)
  {
    String str = "";
    if (Util.textHasContent(paramString)) {
      str = paramString.substring(2);
    }
    return str;
  }
  
  private String produceFinalOutput()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    if (i < this.fFormat.length())
    {
      String str = nextLetter(i);
      InterpretedRange localInterpretedRange = getInterpretation(i);
      int j;
      if (localInterpretedRange != null)
      {
        localStringBuilder.append(localInterpretedRange.Text);
        j = localInterpretedRange.End;
      }
      for (;;)
      {
        i = j + 1;
        break;
        j = i;
        if (!"|".equals(str))
        {
          localStringBuilder.append(str);
          j = i;
        }
      }
    }
    return localStringBuilder.toString();
  }
  
  private Integer twelveHourStyle(Integer paramInteger)
  {
    Integer localInteger = paramInteger;
    if (paramInteger != null)
    {
      if (paramInteger.intValue() != 0) {
        break label21;
      }
      localInteger = Integer.valueOf(12);
    }
    label21:
    do
    {
      return localInteger;
      localInteger = paramInteger;
    } while (paramInteger.intValue() <= 12);
    return Integer.valueOf(paramInteger.intValue() - 12);
  }
  
  private void validateState()
  {
    if (!Util.textHasContent(this.fFormat)) {
      throw new IllegalArgumentException("DateTime format has no content.");
    }
  }
  
  private String valueStr(Object paramObject)
  {
    String str = "";
    if (paramObject != null) {
      str = String.valueOf(paramObject);
    }
    return str;
  }
  
  private String withCharDenotingAlreadyInterpreted(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 1;
    while (i <= paramString.length())
    {
      localStringBuilder.append("@");
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  String format(DateTime paramDateTime)
  {
    this.fEscapedRanges = new ArrayList();
    this.fInterpretedRanges = new ArrayList();
    findEscapedRanges();
    interpretInput(paramDateTime);
    return produceFinalOutput();
  }
  
  private final class CustomLocalization
  {
    List<String> AmPmIndicators;
    List<String> Months;
    List<String> Weekdays;
    
    CustomLocalization(List<String> paramList1, List<String> paramList2)
    {
      if (paramList1.size() != 12) {
        throw new IllegalArgumentException("Your List of custom months must have size 12, but its size is " + paramList1.size());
      }
      if (paramList2.size() != 7) {
        throw new IllegalArgumentException("Your List of custom weekdays must have size 7, but its size is " + paramList2.size());
      }
      List localList;
      if (localList.size() != 2) {
        throw new IllegalArgumentException("Your List of custom a.m./p.m. indicators must have size 2, but its size is " + localList.size());
      }
      this.Months = paramList1;
      this.Weekdays = paramList2;
      this.AmPmIndicators = localList;
    }
  }
  
  private static final class EscapedRange
  {
    int End;
    int Start;
  }
  
  private static final class InterpretedRange
  {
    int End;
    int Start;
    String Text;
    
    public String toString()
    {
      return "Start:" + this.Start + " End:" + this.End + " '" + this.Text + "'";
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\hirondelle\date4j\DateTimeFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */