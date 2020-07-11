package com.facebook.react.modules.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerDialogFragment extends DialogFragment {
  private DatePickerDialog.OnDateSetListener mOnDateSetListener;
  
  private DialogInterface.OnDismissListener mOnDismissListener;
  
  static Dialog createDialog(Bundle paramBundle, Context paramContext, DatePickerDialog.OnDateSetListener paramOnDateSetListener) {
    DismissableDatePickerDialog dismissableDatePickerDialog;
    Calendar calendar = Calendar.getInstance();
    if (paramBundle != null && paramBundle.containsKey("date"))
      calendar.setTimeInMillis(paramBundle.getLong("date")); 
    int i = calendar.get(1);
    int j = calendar.get(2);
    int k = calendar.get(5);
    DatePickerMode datePickerMode2 = DatePickerMode.DEFAULT;
    DatePickerMode datePickerMode1 = datePickerMode2;
    if (paramBundle != null) {
      datePickerMode1 = datePickerMode2;
      if (paramBundle.getString("mode", null) != null)
        datePickerMode1 = DatePickerMode.valueOf(paramBundle.getString("mode").toUpperCase(Locale.US)); 
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      int m = null.$SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode[datePickerMode1.ordinal()];
      if (m != 1) {
        if (m != 2) {
          if (m != 3) {
            paramContext = null;
          } else {
            dismissableDatePickerDialog = new DismissableDatePickerDialog(paramContext, paramOnDateSetListener, i, j, k);
          } 
        } else {
          dismissableDatePickerDialog = new DismissableDatePickerDialog((Context)dismissableDatePickerDialog, dismissableDatePickerDialog.getResources().getIdentifier("SpinnerDatePickerDialog", "style", dismissableDatePickerDialog.getPackageName()), paramOnDateSetListener, i, j, k);
        } 
      } else {
        dismissableDatePickerDialog = new DismissableDatePickerDialog((Context)dismissableDatePickerDialog, dismissableDatePickerDialog.getResources().getIdentifier("CalendarDatePickerDialog", "style", dismissableDatePickerDialog.getPackageName()), paramOnDateSetListener, i, j, k);
      } 
    } else {
      dismissableDatePickerDialog = new DismissableDatePickerDialog((Context)dismissableDatePickerDialog, paramOnDateSetListener, i, j, k);
      i = null.$SwitchMap$com$facebook$react$modules$datepicker$DatePickerMode[datePickerMode1.ordinal()];
      if (i != 1) {
        if (i == 2)
          dismissableDatePickerDialog.getDatePicker().setCalendarViewShown(false); 
      } else {
        dismissableDatePickerDialog.getDatePicker().setCalendarViewShown(true);
        dismissableDatePickerDialog.getDatePicker().setSpinnersShown(false);
      } 
    } 
    DatePicker datePicker = dismissableDatePickerDialog.getDatePicker();
    if (paramBundle != null && paramBundle.containsKey("minDate")) {
      calendar.setTimeInMillis(paramBundle.getLong("minDate"));
      calendar.set(11, 0);
      calendar.set(12, 0);
      calendar.set(13, 0);
      calendar.set(14, 0);
      datePicker.setMinDate(calendar.getTimeInMillis());
    } else {
      datePicker.setMinDate(-2208988800001L);
    } 
    if (paramBundle != null && paramBundle.containsKey("maxDate")) {
      calendar.setTimeInMillis(paramBundle.getLong("maxDate"));
      calendar.set(11, 23);
      calendar.set(12, 59);
      calendar.set(13, 59);
      calendar.set(14, 999);
      datePicker.setMaxDate(calendar.getTimeInMillis());
    } 
    return (Dialog)dismissableDatePickerDialog;
  }
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    return createDialog(getArguments(), (Context)getActivity(), this.mOnDateSetListener);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    super.onDismiss(paramDialogInterface);
    DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
    if (onDismissListener != null)
      onDismissListener.onDismiss(paramDialogInterface); 
  }
  
  void setOnDateSetListener(DatePickerDialog.OnDateSetListener paramOnDateSetListener) {
    this.mOnDateSetListener = paramOnDateSetListener;
  }
  
  void setOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\datepicker\DatePickerDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */