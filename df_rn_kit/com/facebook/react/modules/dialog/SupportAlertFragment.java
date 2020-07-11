package com.facebook.react.modules.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.f;

public class SupportAlertFragment extends f implements DialogInterface.OnClickListener {
  private final DialogModule.AlertFragmentListener mListener;
  
  public SupportAlertFragment() {}
  
  public SupportAlertFragment(DialogModule.AlertFragmentListener paramAlertFragmentListener, Bundle paramBundle) {
    this.mListener = paramAlertFragmentListener;
    setArguments(paramBundle);
  }
  
  public void onClick(DialogInterface paramDialogInterface, int paramInt) {
    DialogModule.AlertFragmentListener alertFragmentListener = this.mListener;
    if (alertFragmentListener != null)
      alertFragmentListener.onClick(paramDialogInterface, paramInt); 
  }
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    return AlertFragment.createDialog((Context)getActivity(), getArguments(), this);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    super.onDismiss(paramDialogInterface);
    DialogModule.AlertFragmentListener alertFragmentListener = this.mListener;
    if (alertFragmentListener != null)
      alertFragmentListener.onDismiss(paramDialogInterface); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\dialog\SupportAlertFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */