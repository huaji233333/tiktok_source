package com.facebook.react.modules.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.f;

public class SupportDatePickerDialogFragment extends f {
  private DatePickerDialog.OnDateSetListener mOnDateSetListener;
  
  private DialogInterface.OnDismissListener mOnDismissListener;
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    return DatePickerDialogFragment.createDialog(getArguments(), (Context)getActivity(), this.mOnDateSetListener);
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


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\datepicker\SupportDatePickerDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */