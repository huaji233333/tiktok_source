package com.facebook.react.modules.timepicker;

public enum TimePickerMode {
  CLOCK, DEFAULT, SPINNER;
  
  static {
    DEFAULT = new TimePickerMode("DEFAULT", 2);
    $VALUES = new TimePickerMode[] { CLOCK, SPINNER, DEFAULT };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\timepicker\TimePickerMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */