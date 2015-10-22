package hirondelle.date4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DateTimeParser
{
  private static final String CL = "\\:";
  private static final String COLON = ":";
  private static final Pattern DATE = Pattern.compile("(\\d{1,4})-(\\d\\d)-(\\d\\d)|(\\d{1,4})-(\\d\\d)|(\\d{1,4})");
  private static final Integer NUM_DIGITS = Integer.valueOf("9");
  private static final String NUM_DIGITS_FOR_FRACTIONAL_SECONDS = "9";
  private static final int THIRD_POSITION = 2;
  private static final Pattern TIME = Pattern.compile("(\\d\\d)\\:(\\d\\d)\\:(\\d\\d)\\.(\\d{1,9})|(\\d\\d)\\:(\\d\\d)\\:(\\d\\d)|(\\d\\d)\\:(\\d\\d)|(\\d\\d)");
  private static final String TT = "(\\d\\d)";
  private Integer fDay;
  private Integer fHour;
  private Integer fMinute;
  private Integer fMonth;
  private Integer fNanosecond;
  private Integer fSecond;
  private Integer fYear;
  
  private String convertToNanoseconds(String paramString)
  {
    paramString = new StringBuilder(paramString);
    while (paramString.length() < NUM_DIGITS.intValue()) {
      paramString.append("0");
    }
    return paramString.toString();
  }
  
  private String getGroup(Matcher paramMatcher, int... paramVarArgs)
  {
    int j = paramVarArgs.length;
    String str = null;
    int i = 0;
    for (;;)
    {
      if (i < j)
      {
        str = paramMatcher.group(paramVarArgs[i]);
        if (str == null) {}
      }
      else
      {
        return str;
      }
      i += 1;
    }
  }
  
  private boolean hasColonInThirdPlace(String paramString)
  {
    boolean bool = false;
    if (paramString.length() >= 2) {
      bool = ":".equals(paramString.substring(2, 3));
    }
    return bool;
  }
  
  private void parseDate(String paramString)
  {
    Matcher localMatcher = DATE.matcher(paramString);
    if (localMatcher.matches())
    {
      paramString = getGroup(localMatcher, new int[] { 1, 4, 6 });
      if (paramString != null) {
        this.fYear = Integer.valueOf(paramString);
      }
      paramString = getGroup(localMatcher, new int[] { 2, 5 });
      if (paramString != null) {
        this.fMonth = Integer.valueOf(paramString);
      }
      paramString = getGroup(localMatcher, new int[] { 3 });
      if (paramString != null) {
        this.fDay = Integer.valueOf(paramString);
      }
      return;
    }
    throw new UnknownDateTimeFormat("Unexpected format for date:" + paramString);
  }
  
  private void parseTime(String paramString)
  {
    Matcher localMatcher = TIME.matcher(paramString);
    if (localMatcher.matches())
    {
      paramString = getGroup(localMatcher, new int[] { 1, 5, 8, 10 });
      if (paramString != null) {
        this.fHour = Integer.valueOf(paramString);
      }
      paramString = getGroup(localMatcher, new int[] { 2, 6, 9 });
      if (paramString != null) {
        this.fMinute = Integer.valueOf(paramString);
      }
      paramString = getGroup(localMatcher, new int[] { 3, 7 });
      if (paramString != null) {
        this.fSecond = Integer.valueOf(paramString);
      }
      paramString = getGroup(localMatcher, new int[] { 4 });
      if (paramString != null) {
        this.fNanosecond = Integer.valueOf(convertToNanoseconds(paramString));
      }
      return;
    }
    throw new UnknownDateTimeFormat("Unexpected format for time:" + paramString);
  }
  
  private Parts splitIntoDateAndTime(String paramString)
  {
    Parts localParts = new Parts(null);
    int j = getDateTimeSeparator(paramString);
    if ((j > 0) && (j < paramString.length())) {}
    for (int i = 1; i != 0; i = 0)
    {
      localParts.datePart = paramString.substring(0, j);
      localParts.timePart = paramString.substring(j + 1);
      return localParts;
    }
    if (hasColonInThirdPlace(paramString))
    {
      localParts.timePart = paramString;
      return localParts;
    }
    localParts.datePart = paramString;
    return localParts;
  }
  
  int getDateTimeSeparator(String paramString)
  {
    int j = paramString.indexOf(" ");
    int i = j;
    if (j == -1) {
      i = paramString.indexOf("T");
    }
    return i;
  }
  
  DateTime parse(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("DateTime string is null");
    }
    paramString = splitIntoDateAndTime(paramString.trim());
    if (paramString.hasTwoParts())
    {
      parseDate(paramString.datePart);
      parseTime(paramString.timePart);
    }
    for (;;)
    {
      return new DateTime(this.fYear, this.fMonth, this.fDay, this.fHour, this.fMinute, this.fSecond, this.fNanosecond);
      if (paramString.hasDateOnly()) {
        parseDate(paramString.datePart);
      } else if (paramString.hasTimeOnly()) {
        parseTime(paramString.timePart);
      }
    }
  }
  
  private class Parts
  {
    String datePart;
    String timePart;
    
    private Parts() {}
    
    boolean hasDateOnly()
    {
      return this.timePart == null;
    }
    
    boolean hasTimeOnly()
    {
      return this.datePart == null;
    }
    
    boolean hasTwoParts()
    {
      return (this.datePart != null) && (this.timePart != null);
    }
  }
  
  static final class UnknownDateTimeFormat
    extends RuntimeException
  {
    private static final long serialVersionUID = -7179421566055773208L;
    
    UnknownDateTimeFormat(String paramString)
    {
      super();
    }
    
    UnknownDateTimeFormat(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\hirondelle\date4j\DateTimeParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */