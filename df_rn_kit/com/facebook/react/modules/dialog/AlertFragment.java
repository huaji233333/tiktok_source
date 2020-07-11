package com.facebook.react.modules.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlertFragment extends DialogFragment implements DialogInterface.OnClickListener {
  private final DialogModule.AlertFragmentListener mListener;
  
  public AlertFragment() {}
  
  public AlertFragment(DialogModule.AlertFragmentListener paramAlertFragmentListener, Bundle paramBundle) {
    this.mListener = paramAlertFragmentListener;
    setArguments(paramBundle);
  }
  
  public static Dialog createDialog(Context paramContext, Bundle paramBundle, DialogInterface.OnClickListener paramOnClickListener) {
    AlertDialog.Builder builder = (new AlertDialog.Builder(paramContext)).setTitle(paramBundle.getString("title"));
    if (paramBundle.containsKey("button_positive"))
      builder.setPositiveButton(paramBundle.getString("button_positive"), paramOnClickListener); 
    if (paramBundle.containsKey("button_negative"))
      builder.setNegativeButton(paramBundle.getString("button_negative"), paramOnClickListener); 
    if (paramBundle.containsKey("button_neutral"))
      builder.setNeutralButton(paramBundle.getString("button_neutral"), paramOnClickListener); 
    if (paramBundle.containsKey("message"))
      builder.setMessage(paramBundle.getString("message")); 
    if (paramBundle.containsKey("items"))
      builder.setItems(paramBundle.getCharSequenceArray("items"), paramOnClickListener); 
    return (Dialog)builder.create();
  }
  
  public void onClick(DialogInterface paramDialogInterface, int paramInt) {
    DialogModule.AlertFragmentListener alertFragmentListener = this.mListener;
    if (alertFragmentListener != null)
      alertFragmentListener.onClick(paramDialogInterface, paramInt); 
  }
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    return createDialog((Context)getActivity(), getArguments(), this);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    super.onDismiss(paramDialogInterface);
    DialogModule.AlertFragmentListener alertFragmentListener = this.mListener;
    if (alertFragmentListener != null)
      alertFragmentListener.onDismiss(paramDialogInterface); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\dialog\AlertFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */