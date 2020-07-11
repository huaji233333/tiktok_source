package com.tt.miniapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.ref.WeakReference;

public class NoLeakDialog extends Dialog {
  private LisCallback mLisCallback;
  
  private Handler mLisHandler;
  
  public NoLeakDialog(Context paramContext) {
    super(paramContext);
    init();
  }
  
  public NoLeakDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    init();
  }
  
  protected NoLeakDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener) {
    super(paramContext, paramBoolean, paramOnCancelListener);
    init();
  }
  
  private void init() {
    this.mLisCallback = new LisCallback((DialogInterface)this);
    this.mLisHandler = new Handler(this.mLisCallback);
  }
  
  public void setOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener) {
    Handler handler = this.mLisHandler;
    if (handler != null) {
      LisCallback lisCallback = this.mLisCallback;
      if (lisCallback != null) {
        if (paramOnCancelListener != null) {
          Message message = Message.obtain(handler, 68);
          this.mLisCallback.setOnCancel(paramOnCancelListener);
          setCancelMessage(message);
          return;
        } 
        lisCallback.setOnCancel(null);
        setCancelMessage(null);
        return;
      } 
    } 
    super.setOnCancelListener(paramOnCancelListener);
  }
  
  public void setOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener) {
    if (this.mLisHandler != null) {
      LisCallback lisCallback = this.mLisCallback;
      if (lisCallback != null) {
        if (paramOnDismissListener != null) {
          lisCallback.setOnDismiss(paramOnDismissListener);
          setDismissMessage(Message.obtain(this.mLisHandler, 67));
          return;
        } 
        lisCallback.setOnDismiss(null);
        setDismissMessage(null);
        return;
      } 
    } 
    super.setOnDismissListener(paramOnDismissListener);
  }
  
  static final class LisCallback implements Handler.Callback {
    private WeakReference<DialogInterface> mDialog;
    
    private WeakReference<DialogInterface.OnCancelListener> mOnCancel;
    
    private WeakReference<DialogInterface.OnDismissListener> mOnDismiss;
    
    private LisCallback(DialogInterface param1DialogInterface) {
      this.mDialog = new WeakReference<DialogInterface>(param1DialogInterface);
    }
    
    public final boolean handleMessage(Message param1Message) {
      int i = param1Message.what;
      if (i != 67) {
        if (i != 68)
          return false; 
        WeakReference<DialogInterface.OnCancelListener> weakReference1 = this.mOnCancel;
        if (weakReference1 != null) {
          DialogInterface.OnCancelListener onCancelListener = weakReference1.get();
          if (onCancelListener != null) {
            onCancelListener.onCancel(this.mDialog.get());
            AppBrandLogger.d("NoLeakDialog", new Object[] { "NoLeak: onCancel" });
          } 
        } 
        return true;
      } 
      WeakReference<DialogInterface.OnDismissListener> weakReference = this.mOnDismiss;
      if (weakReference != null) {
        DialogInterface.OnDismissListener onDismissListener = weakReference.get();
        if (onDismissListener != null) {
          onDismissListener.onDismiss(this.mDialog.get());
          AppBrandLogger.d("NoLeakDialog", new Object[] { "NoLeak: onDismiss" });
        } 
      } 
      return true;
    }
    
    public final void setOnCancel(DialogInterface.OnCancelListener param1OnCancelListener) {
      if (param1OnCancelListener == null) {
        this.mOnCancel = null;
        return;
      } 
      this.mOnCancel = new WeakReference<DialogInterface.OnCancelListener>(param1OnCancelListener);
    }
    
    public final void setOnDismiss(DialogInterface.OnDismissListener param1OnDismissListener) {
      if (param1OnDismissListener == null) {
        this.mOnDismiss = null;
        return;
      } 
      this.mOnDismiss = new WeakReference<DialogInterface.OnDismissListener>(param1OnDismissListener);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dialog\NoLeakDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */