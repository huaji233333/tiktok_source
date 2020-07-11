package com.tt.miniapp.component.nativeview.picker.wheel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.util.DateUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.DebugUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class DateTimePicker extends WheelPicker {
  @Deprecated
  public static final int HOUR = 4;
  
  @Deprecated
  public static final int HOUR_OF_DAY = 3;
  
  public int dateMode = 0;
  
  public String dayLabel;
  
  public ArrayList<String> days = new ArrayList<String>();
  
  private int endDay = 31;
  
  private int endHour;
  
  private int endMinute = 59;
  
  private int endMonth = 12;
  
  private int endYear = 2020;
  
  private String hourLabel;
  
  public ArrayList<String> hours = new ArrayList<String>();
  
  private boolean mIsShowLable = false;
  
  public String minuteLabel;
  
  public ArrayList<String> minutes = new ArrayList<String>();
  
  public String monthLabel;
  
  public ArrayList<String> months = new ArrayList<String>();
  
  private OnDateTimePickListener onDateTimePickListener;
  
  public OnWheelListener onWheelListener;
  
  public boolean resetWhileWheel = true;
  
  public int selectedDayIndex = 0;
  
  public String selectedHour = "";
  
  public String selectedMinute = "";
  
  public int selectedMonthIndex = 0;
  
  public int selectedYearIndex = 0;
  
  private int startDay = 1;
  
  private int startHour;
  
  private int startMinute = 0;
  
  private int startMonth = 1;
  
  private int startYear = 2010;
  
  private int textSize = 17;
  
  private int timeMode = 3;
  
  private List<WeakReference<WheelView>> wheelViews = new ArrayList<WeakReference<WheelView>>(5);
  
  private String yearLabel;
  
  public ArrayList<String> years = new ArrayList<String>();
  
  public DateTimePicker(Activity paramActivity, int paramInt) {
    this(paramActivity, 0, paramInt);
  }
  
  public DateTimePicker(Activity paramActivity, int paramInt1, int paramInt2) {
    super(paramActivity);
    this.yearLabel = paramActivity.getString(2097742059);
    this.monthLabel = paramActivity.getString(2097741954);
    this.dayLabel = paramActivity.getString(2097741882);
    this.hourLabel = paramActivity.getString(2097741926);
    this.minuteLabel = paramActivity.getString(2097741953);
    if (paramInt1 != -1 || paramInt2 != -1) {
      if (paramInt1 == 0 && paramInt2 != -1)
        if (this.screenWidthPixels < 720) {
          this.textSize = 14;
        } else if (this.screenWidthPixels < 480) {
          this.textSize = 12;
        }  
      this.dateMode = paramInt1;
      if (paramInt2 == 4) {
        this.startHour = 1;
        this.endHour = 12;
      } else {
        this.startHour = 0;
        this.endHour = 23;
      } 
      this.timeMode = paramInt2;
      return;
    } 
    throw new IllegalArgumentException("The modes are NONE at the same time");
  }
  
  private String addLable(String paramString1, String paramString2) {
    if (this.mIsShowLable)
      return paramString1; 
    String str = paramString2;
    if (TextUtils.isEmpty(paramString2))
      str = ""; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(str);
    return stringBuilder.toString();
  }
  
  private int findItemIndex(ArrayList<String> paramArrayList, int paramInt) {
    int i = Collections.binarySearch(paramArrayList, Integer.valueOf(paramInt), new Comparator() {
          public int compare(Object param1Object1, Object param1Object2) {
            String str2 = param1Object1.toString();
            String str1 = param1Object2.toString();
            param1Object1 = str2;
            if (str2.startsWith("0"))
              param1Object1 = str2.substring(1); 
            param1Object2 = str1;
            if (str1.startsWith("0"))
              param1Object2 = str1.substring(1); 
            try {
              int i = Integer.parseInt((String)param1Object1);
              int j = Integer.parseInt((String)param1Object2);
              return i - j;
            } catch (NumberFormatException numberFormatException) {
              AppBrandLogger.stacktrace(6, "DateTimePicker", numberFormatException.getStackTrace());
              return 0;
            } 
          }
        });
    paramInt = i;
    if (i < 0)
      paramInt = 0; 
    return paramInt;
  }
  
  private void initHourData() {
    byte b;
    this.hours.clear();
    if (!this.resetWhileWheel) {
      if (this.timeMode == 3) {
        b = Calendar.getInstance().get(11);
      } else {
        b = Calendar.getInstance().get(10);
      } 
    } else {
      b = 0;
    } 
    for (int i = this.startHour; i <= this.endHour; i++) {
      String str = DateUtils.fillZero(i);
      if (!this.resetWhileWheel && i == b)
        this.selectedHour = str; 
      this.hours.add(str);
    } 
    if (this.hours.indexOf(this.selectedHour) == -1)
      this.selectedHour = this.hours.get(0); 
    if (!this.resetWhileWheel)
      this.selectedMinute = DateUtils.fillZero(Calendar.getInstance().get(12)); 
  }
  
  private void initYearData() {
    this.years.clear();
    int i = this.startYear;
    int j = this.endYear;
    if (i == j) {
      this.years.add(String.valueOf(i));
    } else {
      int k = i;
      if (i < j) {
        while (i <= this.endYear) {
          this.years.add(String.valueOf(i));
          i++;
        } 
      } else {
        while (k >= this.endYear) {
          this.years.add(String.valueOf(k));
          k--;
        } 
      } 
    } 
    if (!this.resetWhileWheel) {
      i = this.dateMode;
      if (i == 0 || i == 1) {
        i = this.years.indexOf(DateUtils.fillZero(Calendar.getInstance().get(1)));
        if (i == -1) {
          this.selectedYearIndex = 0;
          return;
        } 
        this.selectedYearIndex = i;
      } 
    } 
  }
  
  public ArrayList<String> addLable(ArrayList<String> paramArrayList, String paramString) {
    if (this.mIsShowLable)
      return paramArrayList; 
    String str = paramString;
    if (TextUtils.isEmpty(paramString))
      str = ""; 
    ArrayList<String> arrayList = new ArrayList();
    for (String str1 : paramArrayList) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append(str);
      arrayList.add(stringBuilder.toString());
    } 
    return arrayList;
  }
  
  public void changeDayData(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: iload_2
    //   2: invokestatic calculateDaysInMonth : (II)I
    //   5: istore #6
    //   7: aload_0
    //   8: getfield resetWhileWheel : Z
    //   11: ifne -> 83
    //   14: aload_0
    //   15: getfield selectedDayIndex : I
    //   18: iload #6
    //   20: if_icmplt -> 31
    //   23: aload_0
    //   24: iload #6
    //   26: iconst_1
    //   27: isub
    //   28: putfield selectedDayIndex : I
    //   31: aload_0
    //   32: getfield days : Ljava/util/ArrayList;
    //   35: invokevirtual size : ()I
    //   38: istore_3
    //   39: aload_0
    //   40: getfield selectedDayIndex : I
    //   43: istore #4
    //   45: iload_3
    //   46: iload #4
    //   48: if_icmple -> 68
    //   51: aload_0
    //   52: getfield days : Ljava/util/ArrayList;
    //   55: iload #4
    //   57: invokevirtual get : (I)Ljava/lang/Object;
    //   60: checkcast java/lang/String
    //   63: astore #8
    //   65: goto -> 87
    //   68: invokestatic getInstance : ()Ljava/util/Calendar;
    //   71: iconst_5
    //   72: invokevirtual get : (I)I
    //   75: invokestatic fillZero : (I)Ljava/lang/String;
    //   78: astore #8
    //   80: goto -> 87
    //   83: ldc ''
    //   85: astore #8
    //   87: aload_0
    //   88: getfield days : Ljava/util/ArrayList;
    //   91: invokevirtual clear : ()V
    //   94: iload_1
    //   95: aload_0
    //   96: getfield startYear : I
    //   99: if_icmpne -> 158
    //   102: iload_2
    //   103: aload_0
    //   104: getfield startMonth : I
    //   107: if_icmpne -> 158
    //   110: iload_1
    //   111: aload_0
    //   112: getfield endYear : I
    //   115: if_icmpne -> 158
    //   118: iload_2
    //   119: aload_0
    //   120: getfield endMonth : I
    //   123: if_icmpne -> 158
    //   126: aload_0
    //   127: getfield startDay : I
    //   130: istore_1
    //   131: iload_1
    //   132: aload_0
    //   133: getfield endDay : I
    //   136: if_icmpgt -> 291
    //   139: aload_0
    //   140: getfield days : Ljava/util/ArrayList;
    //   143: iload_1
    //   144: invokestatic fillZero : (I)Ljava/lang/String;
    //   147: invokevirtual add : (Ljava/lang/Object;)Z
    //   150: pop
    //   151: iload_1
    //   152: iconst_1
    //   153: iadd
    //   154: istore_1
    //   155: goto -> 131
    //   158: iload_1
    //   159: aload_0
    //   160: getfield startYear : I
    //   163: if_icmpne -> 204
    //   166: iload_2
    //   167: aload_0
    //   168: getfield startMonth : I
    //   171: if_icmpne -> 204
    //   174: aload_0
    //   175: getfield startDay : I
    //   178: istore_1
    //   179: iload_1
    //   180: iload #6
    //   182: if_icmpgt -> 291
    //   185: aload_0
    //   186: getfield days : Ljava/util/ArrayList;
    //   189: iload_1
    //   190: invokestatic fillZero : (I)Ljava/lang/String;
    //   193: invokevirtual add : (Ljava/lang/Object;)Z
    //   196: pop
    //   197: iload_1
    //   198: iconst_1
    //   199: iadd
    //   200: istore_1
    //   201: goto -> 179
    //   204: aload_0
    //   205: getfield endYear : I
    //   208: istore #7
    //   210: iconst_1
    //   211: istore #5
    //   213: iconst_1
    //   214: istore #4
    //   216: iload #5
    //   218: istore_3
    //   219: iload_1
    //   220: iload #7
    //   222: if_icmpne -> 266
    //   225: iload #5
    //   227: istore_3
    //   228: iload_2
    //   229: aload_0
    //   230: getfield endMonth : I
    //   233: if_icmpne -> 266
    //   236: iload #4
    //   238: istore_1
    //   239: iload_1
    //   240: aload_0
    //   241: getfield endDay : I
    //   244: if_icmpgt -> 291
    //   247: aload_0
    //   248: getfield days : Ljava/util/ArrayList;
    //   251: iload_1
    //   252: invokestatic fillZero : (I)Ljava/lang/String;
    //   255: invokevirtual add : (Ljava/lang/Object;)Z
    //   258: pop
    //   259: iload_1
    //   260: iconst_1
    //   261: iadd
    //   262: istore_1
    //   263: goto -> 239
    //   266: iload_3
    //   267: iload #6
    //   269: if_icmpgt -> 291
    //   272: aload_0
    //   273: getfield days : Ljava/util/ArrayList;
    //   276: iload_3
    //   277: invokestatic fillZero : (I)Ljava/lang/String;
    //   280: invokevirtual add : (Ljava/lang/Object;)Z
    //   283: pop
    //   284: iload_3
    //   285: iconst_1
    //   286: iadd
    //   287: istore_3
    //   288: goto -> 266
    //   291: aload_0
    //   292: getfield resetWhileWheel : Z
    //   295: ifne -> 322
    //   298: aload_0
    //   299: getfield days : Ljava/util/ArrayList;
    //   302: aload #8
    //   304: invokevirtual indexOf : (Ljava/lang/Object;)I
    //   307: istore_2
    //   308: iload_2
    //   309: istore_1
    //   310: iload_2
    //   311: iconst_m1
    //   312: if_icmpne -> 317
    //   315: iconst_0
    //   316: istore_1
    //   317: aload_0
    //   318: iload_1
    //   319: putfield selectedDayIndex : I
    //   322: return
  }
  
  public void changeMinuteData(int paramInt) {
    this.minutes.clear();
    int i = this.startHour;
    int j = this.endHour;
    if (i == j) {
      paramInt = this.startMinute;
      i = this.endMinute;
      if (paramInt > i) {
        this.startMinute = i;
        this.endMinute = paramInt;
      } 
      for (paramInt = this.startMinute; paramInt <= this.endMinute; paramInt++)
        this.minutes.add(DateUtils.fillZero(paramInt)); 
    } else if (paramInt == i) {
      for (paramInt = this.startMinute; paramInt <= 59; paramInt++)
        this.minutes.add(DateUtils.fillZero(paramInt)); 
    } else if (paramInt == j) {
      for (paramInt = 0; paramInt <= this.endMinute; paramInt++)
        this.minutes.add(DateUtils.fillZero(paramInt)); 
    } else {
      for (paramInt = 0; paramInt <= 59; paramInt++)
        this.minutes.add(DateUtils.fillZero(paramInt)); 
    } 
    if (this.minutes.indexOf(this.selectedMinute) == -1)
      this.selectedMinute = this.minutes.get(0); 
  }
  
  public void changeMonthData(int paramInt) {
    String str;
    boolean bool = this.resetWhileWheel;
    boolean bool2 = true;
    boolean bool1 = true;
    if (!bool) {
      int j = this.months.size();
      int k = this.selectedMonthIndex;
      if (j > k) {
        str = this.months.get(k);
      } else {
        str = DateUtils.fillZero(Calendar.getInstance().get(2) + 1);
      } 
    } else {
      str = "";
    } 
    this.months.clear();
    int i = this.startMonth;
    if (i > 0) {
      int j = this.endMonth;
      if (j > 0 && i <= 12 && j <= 12) {
        int m = this.startYear;
        int k = this.endYear;
        if (m == k) {
          paramInt = i;
          if (i > j) {
            for (paramInt = j; paramInt >= this.startMonth; paramInt--)
              this.months.add(DateUtils.fillZero(paramInt)); 
          } else {
            while (paramInt <= this.endMonth) {
              this.months.add(DateUtils.fillZero(paramInt));
              paramInt++;
            } 
          } 
        } else if (paramInt == m) {
          while (i <= 12) {
            this.months.add(DateUtils.fillZero(i));
            i++;
          } 
        } else {
          i = bool2;
          if (paramInt == k) {
            for (paramInt = bool1; paramInt <= this.endMonth; paramInt++)
              this.months.add(DateUtils.fillZero(paramInt)); 
          } else {
            while (i <= 12) {
              this.months.add(DateUtils.fillZero(i));
              i++;
            } 
          } 
        } 
        if (!this.resetWhileWheel) {
          i = this.months.indexOf(str);
          paramInt = i;
          if (i == -1)
            paramInt = 0; 
          this.selectedMonthIndex = paramInt;
        } 
        return;
      } 
    } 
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Month out of range [1-12]");
    throw illegalArgumentException;
  }
  
  public int getAdapterIndex(int paramInt1, int paramInt2) {
    return (paramInt1 >= paramInt2) ? (paramInt2 - 1) : paramInt1;
  }
  
  public String getSelectedDay() {
    int i = this.dateMode;
    if (i == 0 || i == 2) {
      if (this.days.size() <= this.selectedDayIndex)
        this.selectedDayIndex = this.days.size() - 1; 
      return this.days.get(this.selectedDayIndex);
    } 
    return "";
  }
  
  public String getSelectedHour() {
    return (this.timeMode != -1) ? this.selectedHour : "";
  }
  
  public String getSelectedMinute() {
    return (this.timeMode != -1) ? this.selectedMinute : "";
  }
  
  public String getSelectedMonth() {
    if (this.dateMode != -1) {
      if (this.months.size() <= this.selectedMonthIndex)
        this.selectedMonthIndex = this.months.size() - 1; 
      return this.months.get(this.selectedMonthIndex);
    } 
    return "";
  }
  
  public String getSelectedYear() {
    int i = this.dateMode;
    if (i == 0 || i == 1 || i == 5) {
      if (this.years.size() <= this.selectedYearIndex)
        this.selectedYearIndex = this.years.size() - 1; 
      return this.years.get(this.selectedYearIndex);
    } 
    return "";
  }
  
  public View makeCenterView() {
    int i = this.dateMode;
    if ((i == 0 || i == 1 || i == 5) && this.years.size() == 0)
      initYearData(); 
    i = this.dateMode;
    if (i == 0 || (i == 1 && this.months.size() == 0))
      changeMonthData(DateUtils.trimZero(getSelectedYear())); 
    i = this.dateMode;
    if ((i == 0 || i == 2) && this.days.size() == 0) {
      if (this.dateMode == 0) {
        i = DateUtils.trimZero(getSelectedYear());
      } else {
        i = Calendar.getInstance(Locale.CHINA).get(1);
      } 
      changeDayData(i, DateUtils.trimZero(getSelectedMonth()));
    } 
    if (this.timeMode != -1 && this.hours.size() == 0)
      initHourData(); 
    if (this.timeMode != -1 && this.minutes.size() == 0)
      changeMinuteData(DateUtils.trimZero(this.selectedHour)); 
    LinearLayout linearLayout = new LinearLayout((Context)this.activity);
    linearLayout.setOrientation(0);
    linearLayout.setGravity(17);
    WheelView wheelView5 = createWheelView();
    final WheelView monthView = createWheelView();
    final WheelView dayView = createWheelView();
    WheelView wheelView2 = createWheelView();
    final WheelView minuteView = createWheelView();
    this.wheelViews.add(new WeakReference<WheelView>(wheelView5));
    this.wheelViews.add(new WeakReference<WheelView>(wheelView4));
    this.wheelViews.add(new WeakReference<WheelView>(wheelView3));
    this.wheelViews.add(new WeakReference<WheelView>(wheelView2));
    this.wheelViews.add(new WeakReference<WheelView>(wheelView1));
    i = this.dateMode;
    if (i == 0 || i == 1 || i == 5) {
      wheelView5.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0F));
      wheelView5.setItems(addLable(this.years, this.yearLabel), this.selectedYearIndex);
      wheelView5.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            public void onSelected(int param1Int) {
              DateTimePicker dateTimePicker2 = DateTimePicker.this;
              dateTimePicker2.selectedYearIndex = param1Int;
              String str = dateTimePicker2.years.get(DateTimePicker.this.selectedYearIndex);
              if (DateTimePicker.this.onWheelListener != null)
                DateTimePicker.this.onWheelListener.onYearWheeled(DateTimePicker.this.selectedYearIndex, str); 
              if (DateTimePicker.this.resetWhileWheel) {
                DateTimePicker dateTimePicker = DateTimePicker.this;
                dateTimePicker.selectedMonthIndex = 0;
                dateTimePicker.selectedDayIndex = 0;
              } 
              param1Int = DateUtils.trimZero(str);
              DateTimePicker.this.changeMonthData(param1Int);
              WheelView wheelView2 = monthView;
              DateTimePicker dateTimePicker3 = DateTimePicker.this;
              wheelView2.setItems(dateTimePicker3.addLable(dateTimePicker3.months, DateTimePicker.this.monthLabel), DateTimePicker.this.selectedMonthIndex);
              if (DateTimePicker.this.onWheelListener != null)
                DateTimePicker.this.onWheelListener.onMonthWheeled(DateTimePicker.this.selectedMonthIndex, DateTimePicker.this.months.get(DateTimePicker.this.selectedMonthIndex)); 
              DateTimePicker dateTimePicker1 = DateTimePicker.this;
              dateTimePicker1.changeDayData(param1Int, DateUtils.trimZero(dateTimePicker1.months.get(DateTimePicker.this.selectedMonthIndex)));
              WheelView wheelView1 = dayView;
              dateTimePicker3 = DateTimePicker.this;
              wheelView1.setItems(dateTimePicker3.addLable(dateTimePicker3.days, DateTimePicker.this.dayLabel), DateTimePicker.this.selectedDayIndex);
              if (DateTimePicker.this.onWheelListener != null)
                DateTimePicker.this.onWheelListener.onDayWheeled(DateTimePicker.this.selectedDayIndex, DateTimePicker.this.days.get(DateTimePicker.this.selectedDayIndex)); 
            }
          });
      linearLayout.addView(wheelView5);
      if (this.mIsShowLable && !TextUtils.isEmpty(this.yearLabel)) {
        TextView textView = createLabelView();
        textView.setTextSize(this.textSize);
        textView.setText(this.yearLabel);
        linearLayout.addView((View)textView);
      } 
    } 
    i = this.dateMode;
    if (i == 0 || i == 1) {
      wheelView4.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0F));
      wheelView4.setItems(addLable(this.months, this.monthLabel), this.selectedMonthIndex);
      wheelView4.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            public void onSelected(int param1Int) {
              DateTimePicker dateTimePicker = DateTimePicker.this;
              int i = dateTimePicker.getAdapterIndex(param1Int, dateTimePicker.months.size());
              if (i < 0) {
                DebugUtil.outputError("DateTimePicker", new Object[] { "invalid Index. index:", Integer.valueOf(i), "months.size():", Integer.valueOf(this.this$0.months.size()), "originIndex:", Integer.valueOf(param1Int) });
                return;
              } 
              dateTimePicker = DateTimePicker.this;
              dateTimePicker.selectedMonthIndex = i;
              String str = dateTimePicker.months.get(DateTimePicker.this.selectedMonthIndex);
              if (DateTimePicker.this.onWheelListener != null)
                DateTimePicker.this.onWheelListener.onMonthWheeled(DateTimePicker.this.selectedMonthIndex, str); 
              if (DateTimePicker.this.dateMode == 0 || DateTimePicker.this.dateMode == 2) {
                if (DateTimePicker.this.resetWhileWheel)
                  DateTimePicker.this.selectedDayIndex = 0; 
                if (DateTimePicker.this.dateMode == 0) {
                  param1Int = DateUtils.trimZero(DateTimePicker.this.getSelectedYear());
                } else {
                  param1Int = Calendar.getInstance(Locale.CHINA).get(1);
                } 
                DateTimePicker.this.changeDayData(param1Int, DateUtils.trimZero(str));
                WheelView wheelView = dayView;
                DateTimePicker dateTimePicker1 = DateTimePicker.this;
                wheelView.setItems(dateTimePicker1.addLable(dateTimePicker1.days, DateTimePicker.this.dayLabel), DateTimePicker.this.selectedDayIndex);
                if (DateTimePicker.this.onWheelListener != null)
                  DateTimePicker.this.onWheelListener.onDayWheeled(DateTimePicker.this.selectedDayIndex, DateTimePicker.this.days.get(DateTimePicker.this.selectedDayIndex)); 
              } 
            }
          });
      linearLayout.addView(wheelView4);
      if (this.mIsShowLable && !TextUtils.isEmpty(this.monthLabel)) {
        TextView textView = createLabelView();
        textView.setTextSize(this.textSize);
        textView.setText(this.monthLabel);
        linearLayout.addView((View)textView);
      } 
    } 
    if (this.dateMode == 0) {
      wheelView3.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0F));
      wheelView3.setItems(addLable(this.days, this.dayLabel), this.selectedDayIndex);
      wheelView3.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            public void onSelected(int param1Int) {
              DateTimePicker dateTimePicker = DateTimePicker.this;
              int i = dateTimePicker.getAdapterIndex(param1Int, dateTimePicker.days.size());
              if (i < 0) {
                DebugUtil.outputError("DateTimePicker", new Object[] { "invalid Index. index:", Integer.valueOf(i), "days.size():", Integer.valueOf(this.this$0.days.size()), "originIndex:", Integer.valueOf(param1Int) });
                return;
              } 
              dateTimePicker = DateTimePicker.this;
              dateTimePicker.selectedDayIndex = i;
              if (dateTimePicker.onWheelListener != null)
                DateTimePicker.this.onWheelListener.onDayWheeled(DateTimePicker.this.selectedDayIndex, DateTimePicker.this.days.get(DateTimePicker.this.selectedDayIndex)); 
            }
          });
      linearLayout.addView(wheelView3);
      if (this.mIsShowLable && !TextUtils.isEmpty(this.dayLabel)) {
        TextView textView = createLabelView();
        textView.setTextSize(this.textSize);
        textView.setText(this.dayLabel);
        linearLayout.addView((View)textView);
      } 
    } 
    if (this.timeMode != -1) {
      wheelView2.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0F));
      wheelView2.setItems(addLable(this.hours, this.hourLabel), addLable(this.selectedHour, this.hourLabel));
      wheelView2.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            public void onSelected(int param1Int) {
              DateTimePicker dateTimePicker1 = DateTimePicker.this;
              dateTimePicker1.selectedHour = dateTimePicker1.hours.get(param1Int);
              if (DateTimePicker.this.onWheelListener != null)
                DateTimePicker.this.onWheelListener.onHourWheeled(param1Int, DateTimePicker.this.selectedHour); 
              dateTimePicker1 = DateTimePicker.this;
              dateTimePicker1.changeMinuteData(DateUtils.trimZero(dateTimePicker1.selectedHour));
              WheelView wheelView = minuteView;
              DateTimePicker dateTimePicker2 = DateTimePicker.this;
              wheelView.setItems(dateTimePicker2.addLable(dateTimePicker2.minutes, DateTimePicker.this.minuteLabel), DateTimePicker.this.selectedMinute);
            }
          });
      linearLayout.addView(wheelView2);
      if (this.mIsShowLable && !TextUtils.isEmpty(this.hourLabel)) {
        TextView textView = createLabelView();
        textView.setTextSize(this.textSize);
        textView.setText(this.hourLabel);
        linearLayout.addView((View)textView);
      } 
      wheelView1.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0F));
      wheelView1.setItems(addLable(this.minutes, this.minuteLabel), addLable(this.selectedMinute, this.minuteLabel));
      wheelView1.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            public void onSelected(int param1Int) {
              DateTimePicker dateTimePicker = DateTimePicker.this;
              int i = dateTimePicker.getAdapterIndex(param1Int, dateTimePicker.minutes.size());
              if (i < 0) {
                DebugUtil.outputError("DateTimePicker", new Object[] { "invalid Index. index:", Integer.valueOf(i), "minutes.size():", Integer.valueOf(this.this$0.minutes.size()), "originIndex:", Integer.valueOf(param1Int) });
                return;
              } 
              dateTimePicker = DateTimePicker.this;
              dateTimePicker.selectedMinute = dateTimePicker.minutes.get(i);
              if (DateTimePicker.this.onWheelListener != null)
                DateTimePicker.this.onWheelListener.onMinuteWheeled(i, DateTimePicker.this.selectedMinute); 
            }
          });
      linearLayout.addView(wheelView1);
      if (this.mIsShowLable && !TextUtils.isEmpty(this.minuteLabel)) {
        TextView textView = createLabelView();
        textView.setTextSize(this.textSize);
        textView.setText(this.minuteLabel);
        linearLayout.addView((View)textView);
      } 
    } 
    return (View)linearLayout;
  }
  
  public void onSubmit() {
    if (this.onDateTimePickListener == null)
      return; 
    Iterator<WeakReference<WheelView>> iterator = this.wheelViews.iterator();
    while (iterator.hasNext()) {
      WheelView wheelView = ((WeakReference<WheelView>)iterator.next()).get();
      if (wheelView != null)
        wheelView.stopScroll(); 
    } 
    this.wheelViews.clear();
    String str1 = getSelectedYear();
    String str2 = getSelectedMonth();
    String str3 = getSelectedDay();
    String str4 = getSelectedHour();
    String str5 = getSelectedMinute();
    int i = this.dateMode;
    if (i != -1) {
      if (i != 0) {
        if (i != 1) {
          if (i != 2) {
            if (i != 5)
              return; 
            ((OnYearMonthDayTimePickListener)this.onDateTimePickListener).onDateTimePicked(str1, str2, str3, str4, str5);
            return;
          } 
          ((OnMonthDayTimePickListener)this.onDateTimePickListener).onDateTimePicked(str2, str3, str4, str5);
          return;
        } 
        ((OnYearMonthTimePickListener)this.onDateTimePickListener).onDateTimePicked(str1, str2, str4, str5);
        return;
      } 
      ((OnYearMonthDayTimePickListener)this.onDateTimePickListener).onDateTimePicked(str1, str2, str3, str4, str5);
      return;
    } 
    ((OnTimePickListener)this.onDateTimePickListener).onDateTimePicked(str4, str5);
  }
  
  public void setDateRangeEnd(int paramInt1, int paramInt2) {
    int i = this.dateMode;
    if (i != -1) {
      if (i != 0) {
        if (i == 1) {
          this.endYear = paramInt1;
          this.endMonth = paramInt2;
        } else if (i == 2) {
          this.endMonth = paramInt1;
          this.endDay = paramInt2;
        } 
        initYearData();
        return;
      } 
      throw new IllegalArgumentException("Not support year/month/day mode");
    } 
    throw new IllegalArgumentException("Date mode invalid");
  }
  
  public void setDateRangeEnd(int paramInt1, int paramInt2, int paramInt3) {
    if (this.dateMode != -1) {
      this.endYear = paramInt1;
      this.endMonth = paramInt2;
      this.endDay = paramInt3;
      initYearData();
      return;
    } 
    throw new IllegalArgumentException("Date mode invalid");
  }
  
  public void setDateRangeStart(int paramInt1, int paramInt2) {
    int i = this.dateMode;
    if (i != -1) {
      if (i != 0) {
        if (i == 1) {
          this.startYear = paramInt1;
          this.startMonth = paramInt2;
        } else if (i == 2) {
          i = Calendar.getInstance(Locale.CHINA).get(1);
          this.endYear = i;
          this.startYear = i;
          this.startMonth = paramInt1;
          this.startDay = paramInt2;
        } 
        initYearData();
        return;
      } 
      throw new IllegalArgumentException("Not support year/month/day mode");
    } 
    throw new IllegalArgumentException("Date mode invalid");
  }
  
  public void setDateRangeStart(int paramInt1, int paramInt2, int paramInt3) {
    if (this.dateMode != -1) {
      this.startYear = paramInt1;
      this.startMonth = paramInt2;
      this.startDay = paramInt3;
      initYearData();
      return;
    } 
    throw new IllegalArgumentException("Date mode invalid");
  }
  
  public void setLabel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
    this.yearLabel = paramString1;
    this.monthLabel = paramString2;
    this.dayLabel = paramString3;
    this.hourLabel = paramString4;
    this.minuteLabel = paramString5;
  }
  
  public void setOnDateTimePickListener(OnDateTimePickListener paramOnDateTimePickListener) {
    this.onDateTimePickListener = paramOnDateTimePickListener;
  }
  
  public void setOnWheelListener(OnWheelListener paramOnWheelListener) {
    this.onWheelListener = paramOnWheelListener;
  }
  
  @Deprecated
  public void setRange(int paramInt1, int paramInt2) {
    if (this.dateMode != -1) {
      this.startYear = paramInt1;
      this.endYear = paramInt2;
      initYearData();
      return;
    } 
    throw new IllegalArgumentException("Date mode invalid");
  }
  
  public void setResetWhileWheel(boolean paramBoolean) {
    this.resetWhileWheel = paramBoolean;
  }
  
  public void setSelectedItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = this.dateMode;
    if (i != 0) {
      if (i == 2) {
        i = Calendar.getInstance(Locale.CHINA).get(1);
        this.endYear = i;
        this.startYear = i;
        changeMonthData(i);
        changeDayData(i, paramInt1);
        this.selectedMonthIndex = findItemIndex(this.months, paramInt1);
        this.selectedDayIndex = findItemIndex(this.days, paramInt2);
      } else if (i == 1) {
        changeMonthData(paramInt1);
        this.selectedYearIndex = findItemIndex(this.years, paramInt1);
        this.selectedMonthIndex = findItemIndex(this.months, paramInt2);
      } 
      if (this.timeMode != -1) {
        this.selectedHour = DateUtils.fillZero(paramInt3);
        this.selectedMinute = DateUtils.fillZero(paramInt4);
      } 
      return;
    } 
    throw new IllegalArgumentException("Date mode invalid");
  }
  
  public void setSelectedItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    changeMonthData(paramInt1);
    changeDayData(paramInt1, paramInt2);
    this.selectedYearIndex = findItemIndex(this.years, paramInt1);
    this.selectedMonthIndex = findItemIndex(this.months, paramInt2);
    this.selectedDayIndex = findItemIndex(this.days, paramInt3);
    if (this.timeMode != -1) {
      this.selectedHour = DateUtils.fillZero(paramInt4);
      this.selectedMinute = DateUtils.fillZero(paramInt5);
    } 
  }
  
  public void setTimeRangeEnd(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield timeMode : I
    //   4: iconst_m1
    //   5: if_icmpeq -> 108
    //   8: iconst_0
    //   9: istore #4
    //   11: iload_1
    //   12: iflt -> 25
    //   15: iload_2
    //   16: iflt -> 25
    //   19: iload_2
    //   20: bipush #59
    //   22: if_icmple -> 28
    //   25: iconst_1
    //   26: istore #4
    //   28: iload #4
    //   30: istore_3
    //   31: aload_0
    //   32: getfield timeMode : I
    //   35: iconst_4
    //   36: if_icmpne -> 54
    //   39: iload_1
    //   40: ifeq -> 52
    //   43: iload #4
    //   45: istore_3
    //   46: iload_1
    //   47: bipush #12
    //   49: if_icmple -> 54
    //   52: iconst_1
    //   53: istore_3
    //   54: iload_3
    //   55: istore #4
    //   57: aload_0
    //   58: getfield timeMode : I
    //   61: iconst_3
    //   62: if_icmpne -> 77
    //   65: iload_3
    //   66: istore #4
    //   68: iload_1
    //   69: bipush #24
    //   71: if_icmplt -> 77
    //   74: iconst_1
    //   75: istore #4
    //   77: iload #4
    //   79: ifne -> 97
    //   82: aload_0
    //   83: iload_1
    //   84: putfield endHour : I
    //   87: aload_0
    //   88: iload_2
    //   89: putfield endMinute : I
    //   92: aload_0
    //   93: invokespecial initHourData : ()V
    //   96: return
    //   97: new java/lang/IllegalArgumentException
    //   100: dup
    //   101: ldc_w 'Time out of range'
    //   104: invokespecial <init> : (Ljava/lang/String;)V
    //   107: athrow
    //   108: new java/lang/IllegalArgumentException
    //   111: dup
    //   112: ldc_w 'Time mode invalid'
    //   115: invokespecial <init> : (Ljava/lang/String;)V
    //   118: athrow
  }
  
  public void setTimeRangeStart(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield timeMode : I
    //   4: iconst_m1
    //   5: if_icmpeq -> 108
    //   8: iconst_0
    //   9: istore #4
    //   11: iload_1
    //   12: iflt -> 25
    //   15: iload_2
    //   16: iflt -> 25
    //   19: iload_2
    //   20: bipush #59
    //   22: if_icmple -> 28
    //   25: iconst_1
    //   26: istore #4
    //   28: iload #4
    //   30: istore_3
    //   31: aload_0
    //   32: getfield timeMode : I
    //   35: iconst_4
    //   36: if_icmpne -> 54
    //   39: iload_1
    //   40: ifeq -> 52
    //   43: iload #4
    //   45: istore_3
    //   46: iload_1
    //   47: bipush #12
    //   49: if_icmple -> 54
    //   52: iconst_1
    //   53: istore_3
    //   54: iload_3
    //   55: istore #4
    //   57: aload_0
    //   58: getfield timeMode : I
    //   61: iconst_3
    //   62: if_icmpne -> 77
    //   65: iload_3
    //   66: istore #4
    //   68: iload_1
    //   69: bipush #24
    //   71: if_icmplt -> 77
    //   74: iconst_1
    //   75: istore #4
    //   77: iload #4
    //   79: ifne -> 97
    //   82: aload_0
    //   83: iload_1
    //   84: putfield startHour : I
    //   87: aload_0
    //   88: iload_2
    //   89: putfield startMinute : I
    //   92: aload_0
    //   93: invokespecial initHourData : ()V
    //   96: return
    //   97: new java/lang/IllegalArgumentException
    //   100: dup
    //   101: ldc_w 'Time out of range'
    //   104: invokespecial <init> : (Ljava/lang/String;)V
    //   107: athrow
    //   108: new java/lang/IllegalArgumentException
    //   111: dup
    //   112: ldc_w 'Time mode invalid'
    //   115: invokespecial <init> : (Ljava/lang/String;)V
    //   118: athrow
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DateMode {}
  
  protected static interface OnDateTimePickListener {}
  
  @Deprecated
  public static interface OnMonthDayPickListener extends OnMonthDayTimePickListener {}
  
  public static interface OnMonthDayTimePickListener extends OnDateTimePickListener {
    void onDateTimePicked(String param1String1, String param1String2, String param1String3, String param1String4);
  }
  
  public static interface OnTimePickListener extends OnDateTimePickListener {
    void onDateTimePicked(String param1String1, String param1String2);
  }
  
  public static interface OnWheelListener {
    void onDayWheeled(int param1Int, String param1String);
    
    void onHourWheeled(int param1Int, String param1String);
    
    void onMinuteWheeled(int param1Int, String param1String);
    
    void onMonthWheeled(int param1Int, String param1String);
    
    void onYearWheeled(int param1Int, String param1String);
  }
  
  public static interface OnYearMonthDayTimePickListener extends OnDateTimePickListener {
    void onDateTimePicked(String param1String1, String param1String2, String param1String3, String param1String4, String param1String5);
  }
  
  @Deprecated
  public static interface OnYearMonthPickListener extends OnYearMonthTimePickListener {}
  
  public static interface OnYearMonthTimePickListener extends OnDateTimePickListener {
    void onDateTimePicked(String param1String1, String param1String2, String param1String3, String param1String4);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface TimeMode {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\DateTimePicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */