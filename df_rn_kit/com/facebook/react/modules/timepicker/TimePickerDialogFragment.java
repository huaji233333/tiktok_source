package com.facebook.react.modules.timepicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimePickerDialogFragment extends DialogFragment {
  private DialogInterface.OnDismissListener mOnDismissListener;
  
  private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
  
  static Dialog createDialog(Bundle paramBundle, Context paramContext, TimePickerDialog.OnTimeSetListener paramOnTimeSetListener) {
    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(11);
    int j = calendar.get(12);
    boolean bool = DateFormat.is24HourFormat(paramContext);
    TimePickerMode timePickerMode2 = TimePickerMode.DEFAULT;
    TimePickerMode timePickerMode1 = timePickerMode2;
    if (paramBundle != null) {
      timePickerMode1 = timePickerMode2;
      if (paramBundle.getString("mode", null) != null)
        timePickerMode1 = TimePickerMode.valueOf(paramBundle.getString("mode").toUpperCase(Locale.US)); 
    } 
    if (paramBundle != null) {
      i = paramBundle.getInt("hour", calendar.get(11));
      j = paramBundle.getInt("minute", calendar.get(12));
      bool = paramBundle.getBoolean("is24Hour", DateFormat.is24HourFormat(paramContext));
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      if (timePickerMode1 == TimePickerMode.CLOCK)
        return (Dialog)new DismissableTimePickerDialog(paramContext, paramContext.getResources().getIdentifier("ClockTimePickerDialog", "style", paramContext.getPackageName()), paramOnTimeSetListener, i, j, bool); 
      if (timePickerMode1 == TimePickerMode.SPINNER)
        return (Dialog)new DismissableTimePickerDialog(paramContext, paramContext.getResources().getIdentifier("SpinnerTimePickerDialog", "style", paramContext.getPackageName()), paramOnTimeSetListener, i, j, bool); 
    } 
    return (Dialog)new DismissableTimePickerDialog(paramContext, paramOnTimeSetListener, i, j, bool);
  }
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    return createDialog(getArguments(), (Context)getActivity(), this.mOnTimeSetListener);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    super.onDismiss(paramDialogInterface);
    DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
    if (onDismissListener != null)
      onDismissListener.onDismiss(paramDialogInterface); 
  }
  
  public void setOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener paramOnTimeSetListener) {
    this.mOnTimeSetListener = paramOnTimeSetListener;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\timepicker\TimePickerDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */