package com.tt.miniapp.component.nativeview.picker.wheel;

import android.app.Activity;

public class DatePicker extends DateTimePicker {
  public DatePicker(Activity paramActivity) {
    this(paramActivity, 0);
  }
  
  public DatePicker(Activity paramActivity, int paramInt) {
    super(paramActivity, paramInt, -1);
  }
  
  @Deprecated
  public final void setDateRangeEnd(int paramInt1, int paramInt2) {
    super.setDateRangeEnd(paramInt1, paramInt2);
  }
  
  @Deprecated
  public final void setDateRangeEnd(int paramInt1, int paramInt2, int paramInt3) {
    super.setDateRangeEnd(paramInt1, paramInt2, paramInt3);
  }
  
  @Deprecated
  public final void setDateRangeStart(int paramInt1, int paramInt2) {
    super.setDateRangeStart(paramInt1, paramInt2);
  }
  
  @Deprecated
  public final void setDateRangeStart(int paramInt1, int paramInt2, int paramInt3) {
    super.setDateRangeStart(paramInt1, paramInt2, paramInt3);
  }
  
  public void setLabel(String paramString1, String paramString2, String paramString3) {
    super.setLabel(paramString1, paramString2, paramString3, "", "");
  }
  
  @Deprecated
  public final void setLabel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
    super.setLabel(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void setOnDatePickListener(final OnDatePickListener listener) {
    if (listener == null)
      return; 
    if (listener instanceof OnYearMonthDayPickListener) {
      super.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            public void onDateTimePicked(String param1String1, String param1String2, String param1String3, String param1String4, String param1String5) {
              ((DatePicker.OnYearMonthDayPickListener)listener).onDatePicked(param1String1, param1String2, param1String3);
            }
          });
      return;
    } 
    if (listener instanceof OnYearMonthPickListener) {
      super.setOnDateTimePickListener(new DateTimePicker.OnYearMonthTimePickListener() {
            public void onDateTimePicked(String param1String1, String param1String2, String param1String3, String param1String4) {
              ((DatePicker.OnYearMonthPickListener)listener).onDatePicked(param1String1, param1String2);
            }
          });
      return;
    } 
    if (listener instanceof OnMonthDayPickListener) {
      super.setOnDateTimePickListener(new DateTimePicker.OnMonthDayTimePickListener() {
            public void onDateTimePicked(String param1String1, String param1String2, String param1String3, String param1String4) {
              ((DatePicker.OnMonthDayPickListener)listener).onDatePicked(param1String1, param1String2);
            }
          });
      return;
    } 
    if (listener instanceof OnYearPickListener)
      super.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            public void onDateTimePicked(String param1String1, String param1String2, String param1String3, String param1String4, String param1String5) {
              ((DatePicker.OnYearPickListener)listener).onDateTimePicked(param1String1);
            }
          }); 
  }
  
  @Deprecated
  public final void setOnDateTimePickListener(DateTimePicker.OnDateTimePickListener paramOnDateTimePickListener) {
    super.setOnDateTimePickListener(paramOnDateTimePickListener);
  }
  
  public void setOnWheelListener(final OnWheelListener listener) {
    if (listener == null)
      return; 
    super.setOnWheelListener(new DateTimePicker.OnWheelListener() {
          public void onDayWheeled(int param1Int, String param1String) {
            listener.onDayWheeled(param1Int, param1String);
          }
          
          public void onHourWheeled(int param1Int, String param1String) {}
          
          public void onMinuteWheeled(int param1Int, String param1String) {}
          
          public void onMonthWheeled(int param1Int, String param1String) {
            listener.onMonthWheeled(param1Int, param1String);
          }
          
          public void onYearWheeled(int param1Int, String param1String) {
            listener.onYearWheeled(param1Int, param1String);
          }
        });
  }
  
  @Deprecated
  public final void setOnWheelListener(DateTimePicker.OnWheelListener paramOnWheelListener) {
    super.setOnWheelListener(paramOnWheelListener);
  }
  
  public void setRange(int paramInt1, int paramInt2) {
    super.setRange(paramInt1, paramInt2);
  }
  
  public void setRangeEnd(int paramInt1, int paramInt2) {
    super.setDateRangeEnd(paramInt1, paramInt2);
  }
  
  public void setRangeEnd(int paramInt1, int paramInt2, int paramInt3) {
    super.setDateRangeEnd(paramInt1, paramInt2, paramInt3);
  }
  
  public void setRangeStart(int paramInt1, int paramInt2) {
    super.setDateRangeStart(paramInt1, paramInt2);
  }
  
  public void setRangeStart(int paramInt1, int paramInt2, int paramInt3) {
    super.setDateRangeStart(paramInt1, paramInt2, paramInt3);
  }
  
  public void setSelectedItem(int paramInt) {
    super.setSelectedItem(paramInt, 0, 0, 0);
  }
  
  public void setSelectedItem(int paramInt1, int paramInt2) {
    super.setSelectedItem(paramInt1, paramInt2, 0, 0);
  }
  
  public void setSelectedItem(int paramInt1, int paramInt2, int paramInt3) {
    super.setSelectedItem(paramInt1, paramInt2, paramInt3, 0, 0);
  }
  
  @Deprecated
  public final void setSelectedItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.setSelectedItem(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @Deprecated
  public final void setSelectedItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    super.setSelectedItem(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  @Deprecated
  public void setTimeRangeEnd(int paramInt1, int paramInt2) {
    throw new UnsupportedOperationException("Time range nonsupport");
  }
  
  @Deprecated
  public void setTimeRangeStart(int paramInt1, int paramInt2) {
    throw new UnsupportedOperationException("Time range nonsupport");
  }
  
  public static interface OnDatePickListener {}
  
  public static interface OnMonthDayPickListener extends OnDatePickListener {
    void onDatePicked(String param1String1, String param1String2);
  }
  
  public static interface OnWheelListener {
    void onDayWheeled(int param1Int, String param1String);
    
    void onMonthWheeled(int param1Int, String param1String);
    
    void onYearWheeled(int param1Int, String param1String);
  }
  
  public static interface OnYearMonthDayPickListener extends OnDatePickListener {
    void onDatePicked(String param1String1, String param1String2, String param1String3);
  }
  
  public static interface OnYearMonthPickListener extends OnDatePickListener {
    void onDatePicked(String param1String1, String param1String2);
  }
  
  public static interface OnYearPickListener extends OnDatePickListener {
    void onDateTimePicked(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\DatePicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */