package com.tt.miniapp.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;

public class ShareLoading {
  public DialogInterface.OnCancelListener cancelListener;
  
  public boolean isDialogShowing;
  
  public boolean isTokenShare;
  
  private Dialog loadingDialog;
  
  public String sharePosition;
  
  public String shareType;
  
  public long startTime;
  
  public ShareLoading() {}
  
  public ShareLoading(String paramString1, String paramString2, long paramLong, boolean paramBoolean) {
    this.shareType = paramString1;
    this.sharePosition = paramString2;
    this.startTime = paramLong;
    this.isTokenShare = paramBoolean;
  }
  
  private Dialog getLoadingDialog(String paramString) {
    Dialog dialog = this.loadingDialog;
    if (dialog != null)
      return dialog; 
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null && !miniappHostBase.isFinishing()) {
      Dialog dialog1 = HostDependManager.getInst().getLoadingDialog((Activity)miniappHostBase, paramString);
      if (dialog1 != null) {
        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(false);
      } 
      return dialog1;
    } 
    return null;
  }
  
  public void hide() {
    Dialog dialog = this.loadingDialog;
    if (dialog != null && this.isDialogShowing) {
      this.isDialogShowing = false;
      _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(dialog);
    } 
  }
  
  public void hide(String paramString1, String paramString2) {
    Dialog dialog = this.loadingDialog;
    if (dialog != null && this.isDialogShowing) {
      this.isDialogShowing = false;
      _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(dialog);
      InnerEventHelper.mpShareWindow(this.shareType, this.sharePosition, this.startTime, paramString1, paramString2, this.isTokenShare);
    } 
  }
  
  public void initLoading(String paramString1, String paramString2, long paramLong, boolean paramBoolean) {
    this.shareType = paramString1;
    this.sharePosition = paramString2;
    this.startTime = paramLong;
    this.isTokenShare = paramBoolean;
  }
  
  public void setOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener) {
    this.cancelListener = paramOnCancelListener;
  }
  
  public void show() {
    this.loadingDialog = getLoadingDialog(UIUtils.getString(2097741923));
    Dialog dialog = this.loadingDialog;
    if (dialog == null)
      return; 
    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
          public void onCancel(DialogInterface param1DialogInterface) {
            ShareLoading shareLoading = ShareLoading.this;
            shareLoading.isDialogShowing = false;
            InnerEventHelper.mpShareWindow(shareLoading.shareType, ShareLoading.this.sharePosition, ShareLoading.this.startTime, "cancel", null, ShareLoading.this.isTokenShare);
            if (ShareLoading.this.cancelListener != null)
              ShareLoading.this.cancelListener.onCancel(param1DialogInterface); 
          }
        });
    this.loadingDialog.show();
    this.isDialogShowing = true;
  }
  
  class ShareLoading {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\share\ShareLoading.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */