package com.roomorama.caldroid;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.caldroid.R.id;
import com.caldroid.R.layout;
import hirondelle.date4j.DateTime;
import hirondelle.date4j.DateTime.DayOverflow;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

@SuppressLint({"DefaultLocale"})
public class CaldroidFragment
  extends DialogFragment
{
  public static final String DIALOG_TITLE = "dialogTitle";
  public static final String DISABLE_DATES = "disableDates";
  public static final String ENABLE_CLICK_ON_DISABLED_DATES = "enableClickOnDisabledDates";
  public static final String ENABLE_SWIPE = "enableSwipe";
  public static int FRIDAY = 0;
  public static final String MAX_DATE = "maxDate";
  public static final String MIN_DATE = "minDate";
  public static int MONDAY = 0;
  public static final String MONTH = "month";
  private static final int MONTH_YEAR_FLAG = 52;
  public static final int NUMBER_OF_PAGES = 4;
  public static int SATURDAY = 0;
  public static final String SELECTED_DATES = "selectedDates";
  public static final String SHOW_NAVIGATION_ARROWS = "showNavigationArrows";
  public static final String SIX_WEEKS_IN_CALENDAR = "sixWeeksInCalendar";
  public static final String START_DAY_OF_WEEK = "startDayOfWeek";
  public static int SUNDAY = 1;
  public static int THURSDAY = 0;
  public static int TUESDAY = 0;
  public static int WEDNESDAY = 0;
  public static final String YEAR = "year";
  public static final String _BACKGROUND_FOR_DATETIME_MAP = "_backgroundForDateTimeMap";
  public static final String _MAX_DATE_TIME = "_maxDateTime";
  public static final String _MIN_DATE_TIME = "_minDateTime";
  public static final String _TEXT_COLOR_FOR_DATETIME_MAP = "_textColorForDateTimeMap";
  public static int disabledBackgroundDrawable = -1;
  public static int disabledTextColor = -7829368;
  public static int selectedBackgroundDrawable;
  public static int selectedTextColor;
  public String TAG = "CaldroidFragment";
  protected HashMap<DateTime, Integer> backgroundForDateTimeMap = new HashMap();
  protected HashMap<String, Object> caldroidData = new HashMap();
  private CaldroidListener caldroidListener;
  protected ArrayList<DateTime> dateInMonthsList;
  private AdapterView.OnItemClickListener dateItemClickListener;
  private AdapterView.OnItemLongClickListener dateItemLongClickListener;
  protected ArrayList<CaldroidGridAdapter> datePagerAdapters = new ArrayList();
  private InfiniteViewPager dateViewPager;
  protected String dialogTitle;
  protected ArrayList<DateTime> disableDates = new ArrayList();
  protected boolean enableClickOnDisabledDates = false;
  protected boolean enableSwipe = true;
  protected HashMap<String, Object> extraData = new HashMap();
  private Time firstMonthTime = new Time();
  private ArrayList<DateGridFragment> fragments;
  private Button leftArrowButton;
  protected DateTime maxDateTime;
  protected DateTime minDateTime;
  protected int month = -1;
  private TextView monthTitleTextView;
  private Formatter monthYearFormatter = new Formatter(this.monthYearStringBuilder, Locale.getDefault());
  private final StringBuilder monthYearStringBuilder = new StringBuilder(50);
  private DatePageChangeListener pageChangeListener;
  private Button rightArrowButton;
  protected ArrayList<DateTime> selectedDates = new ArrayList();
  protected boolean showNavigationArrows = true;
  private boolean sixWeeksInCalendar = true;
  protected int startDayOfWeek = SUNDAY;
  protected HashMap<DateTime, Integer> textColorForDateTimeMap = new HashMap();
  private GridView weekdayGridView;
  protected int year = -1;
  
  static
  {
    MONDAY = 2;
    TUESDAY = 3;
    WEDNESDAY = 4;
    THURSDAY = 5;
    FRIDAY = 6;
    SATURDAY = 7;
    selectedBackgroundDrawable = -1;
    selectedTextColor = -16777216;
  }
  
  private AdapterView.OnItemClickListener getDateItemClickListener()
  {
    if (this.dateItemClickListener == null) {
      this.dateItemClickListener = new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          paramAnonymousAdapterView = (DateTime)CaldroidFragment.this.dateInMonthsList.get(paramAnonymousInt);
          if ((CaldroidFragment.this.caldroidListener == null) || ((!CaldroidFragment.this.enableClickOnDisabledDates) && (((CaldroidFragment.this.minDateTime != null) && (paramAnonymousAdapterView.lt(CaldroidFragment.this.minDateTime))) || ((CaldroidFragment.this.maxDateTime != null) && (paramAnonymousAdapterView.gt(CaldroidFragment.this.maxDateTime))) || ((CaldroidFragment.this.disableDates != null) && (CaldroidFragment.this.disableDates.indexOf(paramAnonymousAdapterView) != -1))))) {
            return;
          }
          paramAnonymousAdapterView = CalendarHelper.convertDateTimeToDate(paramAnonymousAdapterView);
          CaldroidFragment.this.caldroidListener.onSelectDate(paramAnonymousAdapterView, paramAnonymousView);
        }
      };
    }
    return this.dateItemClickListener;
  }
  
  private AdapterView.OnItemLongClickListener getDateItemLongClickListener()
  {
    if (this.dateItemLongClickListener == null) {
      this.dateItemLongClickListener = new AdapterView.OnItemLongClickListener()
      {
        public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          paramAnonymousAdapterView = (DateTime)CaldroidFragment.this.dateInMonthsList.get(paramAnonymousInt);
          if (CaldroidFragment.this.caldroidListener != null)
          {
            if ((!CaldroidFragment.this.enableClickOnDisabledDates) && (((CaldroidFragment.this.minDateTime != null) && (paramAnonymousAdapterView.lt(CaldroidFragment.this.minDateTime))) || ((CaldroidFragment.this.maxDateTime != null) && (paramAnonymousAdapterView.gt(CaldroidFragment.this.maxDateTime))) || ((CaldroidFragment.this.disableDates != null) && (CaldroidFragment.this.disableDates.indexOf(paramAnonymousAdapterView) != -1)))) {
              return false;
            }
            paramAnonymousAdapterView = CalendarHelper.convertDateTimeToDate(paramAnonymousAdapterView);
            CaldroidFragment.this.caldroidListener.onLongClickDate(paramAnonymousAdapterView, paramAnonymousView);
          }
          return true;
        }
      };
    }
    return this.dateItemLongClickListener;
  }
  
  private ArrayList<String> getDaysOfWeek()
  {
    ArrayList localArrayList = new ArrayList();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE", Locale.getDefault());
    DateTime localDateTime = new DateTime(Integer.valueOf(2013), Integer.valueOf(2), Integer.valueOf(17), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)).plusDays(Integer.valueOf(this.startDayOfWeek - SUNDAY));
    int i = 0;
    for (;;)
    {
      if (i >= 7) {
        return localArrayList;
      }
      localArrayList.add(localSimpleDateFormat.format(CalendarHelper.convertDateTimeToDate(localDateTime)).toUpperCase());
      localDateTime = localDateTime.plusDays(Integer.valueOf(1));
      i += 1;
    }
  }
  
  public static CaldroidFragment newInstance(String paramString, int paramInt1, int paramInt2)
  {
    CaldroidFragment localCaldroidFragment = new CaldroidFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("dialogTitle", paramString);
    localBundle.putInt("month", paramInt1);
    localBundle.putInt("year", paramInt2);
    localCaldroidFragment.setArguments(localBundle);
    return localCaldroidFragment;
  }
  
  private void setupDateGridPages(View paramView)
  {
    Object localObject2 = new DateTime(Integer.valueOf(this.year), Integer.valueOf(this.month), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
    this.pageChangeListener = new DatePageChangeListener();
    this.pageChangeListener.setCurrentDateTime((DateTime)localObject2);
    Object localObject1 = getNewDatesGridAdapter(((DateTime)localObject2).getMonth().intValue(), ((DateTime)localObject2).getYear().intValue());
    this.dateInMonthsList = ((CaldroidGridAdapter)localObject1).getDatetimeList();
    Object localObject3 = ((DateTime)localObject2).plus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay);
    CaldroidGridAdapter localCaldroidGridAdapter = getNewDatesGridAdapter(((DateTime)localObject3).getMonth().intValue(), ((DateTime)localObject3).getYear().intValue());
    localObject3 = ((DateTime)localObject3).plus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay);
    localObject3 = getNewDatesGridAdapter(((DateTime)localObject3).getMonth().intValue(), ((DateTime)localObject3).getYear().intValue());
    localObject2 = ((DateTime)localObject2).minus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay);
    localObject2 = getNewDatesGridAdapter(((DateTime)localObject2).getMonth().intValue(), ((DateTime)localObject2).getYear().intValue());
    this.datePagerAdapters.add(localObject1);
    this.datePagerAdapters.add(localCaldroidGridAdapter);
    this.datePagerAdapters.add(localObject3);
    this.datePagerAdapters.add(localObject2);
    this.pageChangeListener.setCaldroidGridAdapters(this.datePagerAdapters);
    this.dateViewPager = ((InfiniteViewPager)paramView.findViewById(R.id.months_infinite_pager));
    this.dateViewPager.setEnabled(this.enableSwipe);
    this.dateViewPager.setSixWeeksInCalendar(this.sixWeeksInCalendar);
    this.dateViewPager.setDatesInMonth(this.dateInMonthsList);
    paramView = new MonthPagerAdapter(getChildFragmentManager());
    this.fragments = paramView.getFragments();
    int i = 0;
    for (;;)
    {
      if (i >= 4)
      {
        paramView = new InfinitePagerAdapter(paramView);
        this.dateViewPager.setAdapter(paramView);
        this.dateViewPager.setOnPageChangeListener(this.pageChangeListener);
        return;
      }
      localObject1 = (DateGridFragment)this.fragments.get(i);
      ((DateGridFragment)localObject1).setGridAdapter((CaldroidGridAdapter)this.datePagerAdapters.get(i));
      ((DateGridFragment)localObject1).setOnItemClickListener(getDateItemClickListener());
      ((DateGridFragment)localObject1).setOnItemLongClickListener(getDateItemLongClickListener());
      i += 1;
    }
  }
  
  public void clearDisableDates()
  {
    this.disableDates.clear();
  }
  
  public void clearSelectedDates()
  {
    this.selectedDates.clear();
  }
  
  public HashMap<String, Object> getCaldroidData()
  {
    this.caldroidData.clear();
    this.caldroidData.put("disableDates", this.disableDates);
    this.caldroidData.put("selectedDates", this.selectedDates);
    this.caldroidData.put("_minDateTime", this.minDateTime);
    this.caldroidData.put("_maxDateTime", this.maxDateTime);
    this.caldroidData.put("startDayOfWeek", Integer.valueOf(this.startDayOfWeek));
    this.caldroidData.put("sixWeeksInCalendar", Boolean.valueOf(this.sixWeeksInCalendar));
    this.caldroidData.put("_backgroundForDateTimeMap", this.backgroundForDateTimeMap);
    this.caldroidData.put("_textColorForDateTimeMap", this.textColorForDateTimeMap);
    return this.caldroidData;
  }
  
  public CaldroidListener getCaldroidListener()
  {
    return this.caldroidListener;
  }
  
  public int getCurrentVirtualPosition()
  {
    int i = this.dateViewPager.getCurrentItem();
    return this.pageChangeListener.getCurrent(i);
  }
  
  public ArrayList<CaldroidGridAdapter> getDatePagerAdapters()
  {
    return this.datePagerAdapters;
  }
  
  public HashMap<String, Object> getExtraData()
  {
    return this.extraData;
  }
  
  public ArrayList<DateGridFragment> getFragments()
  {
    return this.fragments;
  }
  
  public Button getLeftArrowButton()
  {
    return this.leftArrowButton;
  }
  
  public TextView getMonthTitleTextView()
  {
    return this.monthTitleTextView;
  }
  
  public CaldroidGridAdapter getNewDatesGridAdapter(int paramInt1, int paramInt2)
  {
    return new CaldroidGridAdapter(getActivity(), paramInt1, paramInt2, getCaldroidData(), this.extraData);
  }
  
  public Button getRightArrowButton()
  {
    return this.rightArrowButton;
  }
  
  public Bundle getSavedStates()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("month", this.month);
    localBundle.putInt("year", this.year);
    if (this.dialogTitle != null) {
      localBundle.putString("dialogTitle", this.dialogTitle);
    }
    if ((this.selectedDates != null) && (this.selectedDates.size() > 0)) {
      localBundle.putStringArrayList("selectedDates", CalendarHelper.convertToStringList(this.selectedDates));
    }
    if ((this.disableDates != null) && (this.disableDates.size() > 0)) {
      localBundle.putStringArrayList("disableDates", CalendarHelper.convertToStringList(this.disableDates));
    }
    if (this.minDateTime != null) {
      localBundle.putString("minDate", this.minDateTime.format("YYYY-MM-DD"));
    }
    if (this.maxDateTime != null) {
      localBundle.putString("maxDate", this.maxDateTime.format("YYYY-MM-DD"));
    }
    localBundle.putBoolean("showNavigationArrows", this.showNavigationArrows);
    localBundle.putBoolean("enableSwipe", this.enableSwipe);
    localBundle.putInt("startDayOfWeek", this.startDayOfWeek);
    localBundle.putBoolean("sixWeeksInCalendar", this.sixWeeksInCalendar);
    return localBundle;
  }
  
  public GridView getWeekdayGridView()
  {
    return this.weekdayGridView;
  }
  
  public boolean isEnableSwipe()
  {
    return this.enableSwipe;
  }
  
  public boolean isShowNavigationArrows()
  {
    return this.showNavigationArrows;
  }
  
  public boolean isSixWeeksInCalendar()
  {
    return this.sixWeeksInCalendar;
  }
  
  public void moveToDate(Date paramDate)
  {
    moveToDateTime(CalendarHelper.convertDateToDateTime(paramDate));
  }
  
  public void moveToDateTime(DateTime paramDateTime)
  {
    DateTime localDateTime1 = new DateTime(Integer.valueOf(this.year), Integer.valueOf(this.month), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
    DateTime localDateTime2 = localDateTime1.getEndOfMonth();
    if (paramDateTime.lt(localDateTime1))
    {
      paramDateTime = paramDateTime.plus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay);
      this.pageChangeListener.setCurrentDateTime(paramDateTime);
      i = this.dateViewPager.getCurrentItem();
      this.pageChangeListener.refreshAdapters(i);
      this.dateViewPager.setCurrentItem(i - 1);
    }
    while (!paramDateTime.gt(localDateTime2)) {
      return;
    }
    paramDateTime = paramDateTime.minus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay);
    this.pageChangeListener.setCurrentDateTime(paramDateTime);
    int i = this.dateViewPager.getCurrentItem();
    this.pageChangeListener.refreshAdapters(i);
    this.dateViewPager.setCurrentItem(i + 1);
  }
  
  public void nextMonth()
  {
    this.dateViewPager.setCurrentItem(this.pageChangeListener.getCurrentPage() + 1);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    retrieveInitialArgs(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (getDialog() != null) {
      setRetainInstance(true);
    }
    paramLayoutInflater = paramLayoutInflater.inflate(R.layout.calendar_view, paramViewGroup, false);
    this.monthTitleTextView = ((TextView)paramLayoutInflater.findViewById(R.id.calendar_month_year_textview));
    this.leftArrowButton = ((Button)paramLayoutInflater.findViewById(R.id.calendar_left_arrow));
    this.rightArrowButton = ((Button)paramLayoutInflater.findViewById(R.id.calendar_right_arrow));
    this.leftArrowButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CaldroidFragment.this.prevMonth();
      }
    });
    this.rightArrowButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CaldroidFragment.this.nextMonth();
      }
    });
    setShowNavigationArrows(this.showNavigationArrows);
    this.weekdayGridView = ((GridView)paramLayoutInflater.findViewById(R.id.weekday_gridview));
    paramViewGroup = new WeekdayArrayAdapter(getActivity(), 17367043, getDaysOfWeek());
    this.weekdayGridView.setAdapter(paramViewGroup);
    setupDateGridPages(paramLayoutInflater);
    refreshView();
    if (this.caldroidListener != null) {
      this.caldroidListener.onCaldroidViewCreated();
    }
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    if ((getDialog() != null) && (getRetainInstance())) {
      getDialog().setDismissMessage(null);
    }
    super.onDestroyView();
  }
  
  public void prevMonth()
  {
    this.dateViewPager.setCurrentItem(this.pageChangeListener.getCurrentPage() - 1);
  }
  
  protected void refreshMonthTitleTextView()
  {
    this.firstMonthTime.year = this.year;
    this.firstMonthTime.month = (this.month - 1);
    this.firstMonthTime.monthDay = 1;
    long l = this.firstMonthTime.toMillis(true);
    this.monthYearStringBuilder.setLength(0);
    String str = DateUtils.formatDateRange(getActivity(), this.monthYearFormatter, l, l, 52).toString();
    this.monthTitleTextView.setText(str);
  }
  
  public void refreshView()
  {
    if ((this.month == -1) || (this.year == -1)) {}
    for (;;)
    {
      return;
      refreshMonthTitleTextView();
      Iterator localIterator = this.datePagerAdapters.iterator();
      while (localIterator.hasNext())
      {
        CaldroidGridAdapter localCaldroidGridAdapter = (CaldroidGridAdapter)localIterator.next();
        localCaldroidGridAdapter.setCaldroidData(getCaldroidData());
        localCaldroidGridAdapter.setExtraData(this.extraData);
        localCaldroidGridAdapter.notifyDataSetChanged();
      }
    }
  }
  
  public void restoreDialogStatesFromKey(FragmentManager paramFragmentManager, Bundle paramBundle, String paramString1, String paramString2)
  {
    restoreStatesFromKey(paramBundle, paramString1);
    paramBundle = (CaldroidFragment)paramFragmentManager.findFragmentByTag(paramString2);
    if (paramBundle != null)
    {
      paramBundle.dismiss();
      show(paramFragmentManager, paramString2);
    }
  }
  
  public void restoreStatesFromKey(Bundle paramBundle, String paramString)
  {
    if ((paramBundle != null) && (paramBundle.containsKey(paramString))) {
      setArguments(paramBundle.getBundle(paramString));
    }
  }
  
  protected void retrieveInitialArgs(Bundle paramBundle)
  {
    paramBundle = getArguments();
    Object localObject;
    if (paramBundle != null)
    {
      this.month = paramBundle.getInt("month", -1);
      this.year = paramBundle.getInt("year", -1);
      this.dialogTitle = paramBundle.getString("dialogTitle");
      localObject = getDialog();
      if (localObject != null)
      {
        if (this.dialogTitle == null) {
          break label290;
        }
        ((Dialog)localObject).setTitle(this.dialogTitle);
      }
      this.startDayOfWeek = paramBundle.getInt("startDayOfWeek", 1);
      if (this.startDayOfWeek > 7) {
        this.startDayOfWeek %= 7;
      }
      this.showNavigationArrows = paramBundle.getBoolean("showNavigationArrows", true);
      this.enableSwipe = paramBundle.getBoolean("enableSwipe", true);
      this.sixWeeksInCalendar = paramBundle.getBoolean("sixWeeksInCalendar", true);
      this.enableClickOnDisabledDates = paramBundle.getBoolean("enableClickOnDisabledDates", false);
      localObject = paramBundle.getStringArrayList("disableDates");
      if ((localObject != null) && (((ArrayList)localObject).size() > 0))
      {
        localObject = ((ArrayList)localObject).iterator();
        label163:
        if (((Iterator)localObject).hasNext()) {
          break label299;
        }
      }
      localObject = paramBundle.getStringArrayList("selectedDates");
      if ((localObject != null) && (((ArrayList)localObject).size() > 0)) {
        localObject = ((ArrayList)localObject).iterator();
      }
    }
    for (;;)
    {
      if (!((Iterator)localObject).hasNext())
      {
        localObject = paramBundle.getString("minDate");
        if (localObject != null) {
          this.minDateTime = CalendarHelper.getDateTimeFromString((String)localObject, null);
        }
        paramBundle = paramBundle.getString("maxDate");
        if (paramBundle != null) {
          this.maxDateTime = CalendarHelper.getDateTimeFromString(paramBundle, null);
        }
        if ((this.month == -1) || (this.year == -1))
        {
          paramBundle = DateTime.today(TimeZone.getDefault());
          this.month = paramBundle.getMonth().intValue();
          this.year = paramBundle.getYear().intValue();
        }
        return;
        label290:
        ((Dialog)localObject).requestWindowFeature(1);
        break;
        label299:
        localDateTime = CalendarHelper.getDateTimeFromString((String)((Iterator)localObject).next(), "yyyy-MM-dd");
        this.disableDates.add(localDateTime);
        break label163;
      }
      DateTime localDateTime = CalendarHelper.getDateTimeFromString((String)((Iterator)localObject).next(), "yyyy-MM-dd");
      this.selectedDates.add(localDateTime);
    }
  }
  
  public void saveStatesToKey(Bundle paramBundle, String paramString)
  {
    paramBundle.putBundle(paramString, getSavedStates());
  }
  
  public void setBackgroundResourceForDate(int paramInt, Date paramDate)
  {
    paramDate = CalendarHelper.convertDateToDateTime(paramDate);
    this.backgroundForDateTimeMap.put(paramDate, Integer.valueOf(paramInt));
  }
  
  public void setBackgroundResourceForDateTime(int paramInt, DateTime paramDateTime)
  {
    this.backgroundForDateTimeMap.put(paramDateTime, Integer.valueOf(paramInt));
  }
  
  public void setBackgroundResourceForDateTimes(HashMap<DateTime, Integer> paramHashMap)
  {
    this.backgroundForDateTimeMap.putAll(paramHashMap);
  }
  
  public void setBackgroundResourceForDates(HashMap<Date, Integer> paramHashMap)
  {
    this.backgroundForDateTimeMap.clear();
    if ((paramHashMap == null) || (paramHashMap.size() == 0)) {}
    for (;;)
    {
      return;
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (Date)localIterator.next();
        Integer localInteger = (Integer)paramHashMap.get(localObject);
        localObject = CalendarHelper.convertDateToDateTime((Date)localObject);
        this.backgroundForDateTimeMap.put(localObject, localInteger);
      }
    }
  }
  
  public void setCaldroidListener(CaldroidListener paramCaldroidListener)
  {
    this.caldroidListener = paramCaldroidListener;
  }
  
  public void setCalendarDate(Date paramDate)
  {
    setCalendarDateTime(CalendarHelper.convertDateToDateTime(paramDate));
  }
  
  public void setCalendarDateTime(DateTime paramDateTime)
  {
    this.month = paramDateTime.getMonth().intValue();
    this.year = paramDateTime.getYear().intValue();
    if (this.caldroidListener != null) {
      this.caldroidListener.onChangeMonth(this.month, this.year);
    }
    refreshView();
  }
  
  public void setDisableDates(ArrayList<Date> paramArrayList)
  {
    this.disableDates.clear();
    if ((paramArrayList == null) || (paramArrayList.size() == 0)) {}
    for (;;)
    {
      return;
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        DateTime localDateTime = CalendarHelper.convertDateToDateTime((Date)paramArrayList.next());
        this.disableDates.add(localDateTime);
      }
    }
  }
  
  public void setDisableDatesFromString(ArrayList<String> paramArrayList)
  {
    setDisableDatesFromString(paramArrayList, null);
  }
  
  public void setDisableDatesFromString(ArrayList<String> paramArrayList, String paramString)
  {
    this.disableDates.clear();
    if (paramArrayList == null) {}
    for (;;)
    {
      return;
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        DateTime localDateTime = CalendarHelper.getDateTimeFromString((String)paramArrayList.next(), paramString);
        this.disableDates.add(localDateTime);
      }
    }
  }
  
  public void setEnableSwipe(boolean paramBoolean)
  {
    this.enableSwipe = paramBoolean;
    this.dateViewPager.setEnabled(paramBoolean);
  }
  
  public void setExtraData(HashMap<String, Object> paramHashMap)
  {
    this.extraData = paramHashMap;
  }
  
  public void setMaxDate(Date paramDate)
  {
    if (paramDate == null)
    {
      this.maxDateTime = null;
      return;
    }
    this.maxDateTime = CalendarHelper.convertDateToDateTime(paramDate);
  }
  
  public void setMaxDateFromString(String paramString1, String paramString2)
  {
    if (paramString1 == null)
    {
      setMaxDate(null);
      return;
    }
    this.maxDateTime = CalendarHelper.getDateTimeFromString(paramString1, paramString2);
  }
  
  public void setMinDate(Date paramDate)
  {
    if (paramDate == null)
    {
      this.minDateTime = null;
      return;
    }
    this.minDateTime = CalendarHelper.convertDateToDateTime(paramDate);
  }
  
  public void setMinDateFromString(String paramString1, String paramString2)
  {
    if (paramString1 == null)
    {
      setMinDate(null);
      return;
    }
    this.minDateTime = CalendarHelper.getDateTimeFromString(paramString1, paramString2);
  }
  
  public void setMonthTitleTextView(TextView paramTextView)
  {
    this.monthTitleTextView = paramTextView;
  }
  
  public void setSelectedDateStrings(String paramString1, String paramString2, String paramString3)
    throws ParseException
  {
    setSelectedDates(CalendarHelper.getDateFromString(paramString1, paramString3), CalendarHelper.getDateFromString(paramString2, paramString3));
  }
  
  public void setSelectedDates(Date paramDate1, Date paramDate2)
  {
    if ((paramDate1 == null) || (paramDate2 == null) || (paramDate1.after(paramDate2))) {
      return;
    }
    this.selectedDates.clear();
    paramDate1 = CalendarHelper.convertDateToDateTime(paramDate1);
    paramDate2 = CalendarHelper.convertDateToDateTime(paramDate2);
    for (;;)
    {
      if (!paramDate1.lt(paramDate2))
      {
        this.selectedDates.add(paramDate2);
        return;
      }
      this.selectedDates.add(paramDate1);
      paramDate1 = paramDate1.plusDays(Integer.valueOf(1));
    }
  }
  
  public void setShowNavigationArrows(boolean paramBoolean)
  {
    this.showNavigationArrows = paramBoolean;
    if (paramBoolean)
    {
      this.leftArrowButton.setVisibility(0);
      this.rightArrowButton.setVisibility(0);
      return;
    }
    this.leftArrowButton.setVisibility(4);
    this.rightArrowButton.setVisibility(4);
  }
  
  public void setSixWeeksInCalendar(boolean paramBoolean)
  {
    this.sixWeeksInCalendar = paramBoolean;
    this.dateViewPager.setSixWeeksInCalendar(paramBoolean);
  }
  
  public void setTextColorForDate(int paramInt, Date paramDate)
  {
    paramDate = CalendarHelper.convertDateToDateTime(paramDate);
    this.textColorForDateTimeMap.put(paramDate, Integer.valueOf(paramInt));
  }
  
  public void setTextColorForDateTime(int paramInt, DateTime paramDateTime)
  {
    this.textColorForDateTimeMap.put(paramDateTime, Integer.valueOf(paramInt));
  }
  
  public void setTextColorForDateTimes(HashMap<DateTime, Integer> paramHashMap)
  {
    this.textColorForDateTimeMap.putAll(paramHashMap);
  }
  
  public void setTextColorForDates(HashMap<Date, Integer> paramHashMap)
  {
    this.textColorForDateTimeMap.clear();
    if ((paramHashMap == null) || (paramHashMap.size() == 0)) {}
    for (;;)
    {
      return;
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (Date)localIterator.next();
        Integer localInteger = (Integer)paramHashMap.get(localObject);
        localObject = CalendarHelper.convertDateToDateTime((Date)localObject);
        this.textColorForDateTimeMap.put(localObject, localInteger);
      }
    }
  }
  
  public class DatePageChangeListener
    implements ViewPager.OnPageChangeListener
  {
    private ArrayList<CaldroidGridAdapter> caldroidGridAdapters;
    private DateTime currentDateTime;
    private int currentPage = 1000;
    
    public DatePageChangeListener() {}
    
    private int getNext(int paramInt)
    {
      return (paramInt + 1) % 4;
    }
    
    private int getPrevious(int paramInt)
    {
      return (paramInt + 3) % 4;
    }
    
    public ArrayList<CaldroidGridAdapter> getCaldroidGridAdapters()
    {
      return this.caldroidGridAdapters;
    }
    
    public int getCurrent(int paramInt)
    {
      return paramInt % 4;
    }
    
    public DateTime getCurrentDateTime()
    {
      return this.currentDateTime;
    }
    
    public int getCurrentPage()
    {
      return this.currentPage;
    }
    
    public void onPageScrollStateChanged(int paramInt) {}
    
    public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
    
    public void onPageSelected(int paramInt)
    {
      refreshAdapters(paramInt);
      CaldroidFragment.this.setCalendarDateTime(this.currentDateTime);
      CaldroidGridAdapter localCaldroidGridAdapter = (CaldroidGridAdapter)this.caldroidGridAdapters.get(paramInt % 4);
      CaldroidFragment.this.dateInMonthsList.clear();
      CaldroidFragment.this.dateInMonthsList.addAll(localCaldroidGridAdapter.getDatetimeList());
    }
    
    public void refreshAdapters(int paramInt)
    {
      CaldroidGridAdapter localCaldroidGridAdapter1 = (CaldroidGridAdapter)this.caldroidGridAdapters.get(getCurrent(paramInt));
      CaldroidGridAdapter localCaldroidGridAdapter2 = (CaldroidGridAdapter)this.caldroidGridAdapters.get(getPrevious(paramInt));
      CaldroidGridAdapter localCaldroidGridAdapter3 = (CaldroidGridAdapter)this.caldroidGridAdapters.get(getNext(paramInt));
      if (paramInt == this.currentPage)
      {
        localCaldroidGridAdapter1.setAdapterDateTime(this.currentDateTime);
        localCaldroidGridAdapter1.notifyDataSetChanged();
        localCaldroidGridAdapter2.setAdapterDateTime(this.currentDateTime.minus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay));
        localCaldroidGridAdapter2.notifyDataSetChanged();
        localCaldroidGridAdapter3.setAdapterDateTime(this.currentDateTime.plus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay));
        localCaldroidGridAdapter3.notifyDataSetChanged();
      }
      for (;;)
      {
        this.currentPage = paramInt;
        return;
        if (paramInt > this.currentPage)
        {
          this.currentDateTime = this.currentDateTime.plus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay);
          localCaldroidGridAdapter3.setAdapterDateTime(this.currentDateTime.plus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay));
          localCaldroidGridAdapter3.notifyDataSetChanged();
        }
        else
        {
          this.currentDateTime = this.currentDateTime.minus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay);
          localCaldroidGridAdapter2.setAdapterDateTime(this.currentDateTime.minus(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), DateTime.DayOverflow.LastDay));
          localCaldroidGridAdapter2.notifyDataSetChanged();
        }
      }
    }
    
    public void setCaldroidGridAdapters(ArrayList<CaldroidGridAdapter> paramArrayList)
    {
      this.caldroidGridAdapters = paramArrayList;
    }
    
    public void setCurrentDateTime(DateTime paramDateTime)
    {
      this.currentDateTime = paramDateTime;
      CaldroidFragment.this.setCalendarDateTime(this.currentDateTime);
    }
    
    public void setCurrentPage(int paramInt)
    {
      this.currentPage = paramInt;
    }
  }
}


/* Location:              C:\Users\Madhav\Downloads\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\roomorama\caldroid\CaldroidFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */