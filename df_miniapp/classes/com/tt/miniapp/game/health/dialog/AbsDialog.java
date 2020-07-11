package com.tt.miniapp.game.health.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.f;
import android.support.v4.app.k;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapp.dialog.NoLeakDialog;
import com.tt.miniapphost.AppBrandLogger;

public abstract class AbsDialog extends f implements View.OnClickListener {
  protected byte mCloseSetting;
  
  private DismissCallback mDismissCallback;
  
  protected int mStyleResId = 2097807367;
  
  protected void callbackDismiss() {
    DismissCallback dismissCallback = this.mDismissCallback;
    if (dismissCallback == null)
      return; 
    this.mDismissCallback = null;
    dismissCallback.onDismiss();
  }
  
  public void dismissAllowingStateLoss() {
    Exception exception;
    try {
      super.dismissAllowingStateLoss();
      callbackDismiss();
      return;
    } catch (Exception null) {
      AppBrandLogger.eWithThrowable("AbsDialog", "dismissAllowingStateLoss exp", exception);
      callbackDismiss();
      return;
    } finally {}
    callbackDismiss();
    throw exception;
  }
  
  protected boolean invalidState(boolean paramBoolean) {
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null && !fragmentActivity.isFinishing()) {
      if (fragmentActivity.isDestroyed())
        return true; 
      k k = getFragmentManager();
      if (k != null)
        return k.g() ? true : ((paramBoolean && k.h())); 
    } 
    return true;
  }
  
  public void onClick(View paramView) {}
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setStyle(2, this.mStyleResId);
    paramBundle = getArguments();
    if (paramBundle == null)
      return; 
    this.mCloseSetting = paramBundle.getByte("key_close_setting", (byte)0).byteValue();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    DialogInterface.OnKeyListener onKeyListener;
    NoLeakDialog noLeakDialog = new NoLeakDialog((Context)getActivity(), this.mTheme);
    byte b = this.mCloseSetting;
    boolean bool2 = false;
    if ((b & 0x2) != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    noLeakDialog.setCancelable(bool1);
    if (bool1) {
      paramBundle = null;
    } else {
      onKeyListener = new DialogInterface.OnKeyListener() {
          public boolean onKey(DialogInterface param1DialogInterface, int param1Int, KeyEvent param1KeyEvent) {
            return (4 == param1Int);
          }
        };
    } 
    noLeakDialog.setOnKeyListener(onKeyListener);
    boolean bool1 = bool2;
    if ((this.mCloseSetting & 0x1) != 0)
      bool1 = true; 
    noLeakDialog.setCanceledOnTouchOutside(bool1);
    return (Dialog)noLeakDialog;
  }
  
  public void onDestroyView() {
    View view = getView();
    if (view instanceof ViewGroup)
      ((ViewGroup)view).removeAllViews(); 
    super.onDestroyView();
  }
  
  public AbsDialog runOnDismiss(DismissCallback paramDismissCallback) {
    this.mDismissCallback = paramDismissCallback;
    return this;
  }
  
  public AbsDialog show(FragmentActivity paramFragmentActivity) {
    k k = paramFragmentActivity.getSupportFragmentManager();
    if (!paramFragmentActivity.isFinishing() && !paramFragmentActivity.isDestroyed() && k != null && !k.h())
      try {
        show(k, "");
        return this;
      } catch (IllegalStateException illegalStateException) {
        AppBrandLogger.eWithThrowable("AbsDialog", "show dialog exp", illegalStateException);
      }  
    callbackDismiss();
    return this;
  }
  
  public static interface BtnListener {
    void onBtnClick(AbsDialog param1AbsDialog);
  }
  
  public static interface DismissCallback {
    void onDismiss();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\game\health\dialog\AbsDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */