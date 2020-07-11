package com.tt.miniapp.component.nativeview.picker.wheel;

import android.app.Activity;

public class TimePicker extends DateTimePicker {
  public TimePicker(Activity paramActivity) {
    this(paramActivity, 3);
  }
  
  public TimePicker(Activity paramActivity, int paramInt) {
    super(paramActivity, -1, paramInt);
  }
  
  @Deprecated
  public final void setDateRangeEnd(int paramInt1, int paramInt2) {
    throw new UnsupportedOperationException("Data range nonsupport");
  }
  
  @Deprecated
  public final void setDateRangeEnd(int paramInt1, int paramInt2, int paramInt3) {
    throw new UnsupportedOperationException("Date range nonsupport");
  }
  
  @Deprecated
  public final void setDateRangeStart(int paramInt1, int paramInt2) {
    throw new UnsupportedOperationException("Date range nonsupport");
  }
  
  @Deprecated
  public final void setDateRangeStart(int paramInt1, int paramInt2, int paramInt3) {
    throw new UnsupportedOperationException("Date range nonsupport");
  }
  
  public void setLabel(String paramString1, String paramString2) {
    super.setLabel("", "", "", paramString1, paramString2);
  }
  
  @Deprecated
  public final void setLabel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
    super.setLabel(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  @Deprecated
  public final void setOnDateTimePickListener(DateTimePicker.OnDateTimePickListener paramOnDateTimePickListener) {
    super.setOnDateTimePickListener(paramOnDateTimePickListener);
  }
  
  public void setOnTimePickListener(final OnTimePickListener listener) {
    if (listener == null)
      return; 
    super.setOnDateTimePickListener(new DateTimePicker.OnTimePickListener() {
          public void onDateTimePicked(String param1String1, String param1String2) {
            listener.onTimePicked(param1String1, param1String2);
          }
        });
  }
  
  @Deprecated
  public final void setOnWheelListener(DateTimePicker.OnWheelListener paramOnWheelListener) {
    super.setOnWheelListener(paramOnWheelListener);
  }
  
  public void setOnWheelListener(final OnWheelListener listener) {
    if (listener == null)
      return; 
    super.setOnWheelListener(new DateTimePicker.OnWheelListener() {
          public void onDayWheeled(int param1Int, String param1String) {}
          
          public void onHourWheeled(int param1Int, String param1String) {
            listener.onHourWheeled(param1Int, param1String);
          }
          
          public void onMinuteWheeled(int param1Int, String param1String) {
            listener.onMinuteWheeled(param1Int, param1String);
          }
          
          public void onMonthWheeled(int param1Int, String param1String) {}
          
          public void onYearWheeled(int param1Int, String param1String) {}
        });
  }
  
  @Deprecated
  public void setRange(int paramInt1, int paramInt2) {
    super.setTimeRangeStart(paramInt1, 0);
    super.setTimeRangeEnd(paramInt2, 59);
  }
  
  public void setRangeEnd(int paramInt1, int paramInt2) {
    super.setTimeRangeEnd(paramInt1, paramInt2);
  }
  
  public void setRangeStart(int paramInt1, int paramInt2) {
    super.setTimeRangeStart(paramInt1, paramInt2);
  }
  
  public void setSelectedItem(int paramInt1, int paramInt2) {
    super.setSelectedItem(0, 0, paramInt1, paramInt2);
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
    super.setTimeRangeEnd(paramInt1, paramInt2);
  }
  
  @Deprecated
  public void setTimeRangeStart(int paramInt1, int paramInt2) {
    super.setTimeRangeStart(paramInt1, paramInt2);
  }
  
  public static interface OnTimePickListener {
    void onTimePicked(String param1String1, String param1String2);
  }
  
  public static interface OnWheelListener {
    void onHourWheeled(int param1Int, String param1String);
    
    void onMinuteWheeled(int param1Int, String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\TimePicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */