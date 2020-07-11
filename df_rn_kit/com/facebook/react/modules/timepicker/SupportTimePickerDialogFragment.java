package com.facebook.react.modules.timepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.f;

public class SupportTimePickerDialogFragment extends f {
  private DialogInterface.OnDismissListener mOnDismissListener;
  
  private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    return TimePickerDialogFragment.createDialog(getArguments(), (Context)getActivity(), this.mOnTimeSetListener);
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


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\timepicker\SupportTimePickerDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */