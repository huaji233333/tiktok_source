package com.tt.miniapp.dialog;

import android.app.Activity;
import android.text.TextUtils;
import com.tt.miniapp.view.dialog.ModalDialog;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import org.json.JSONObject;

public class DialogImpl extends NativeModule {
  public DialogImpl(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public String getName() {
    return "showModal";
  }
  
  public String invoke(final String resultCancelText, final NativeModule.NativeModuleCallback nativeModuleCallback) {
    final boolean showCancel;
    final String title;
    final String content;
    final String confirmText;
    final String confirmColor;
    final String cancelColor;
    try {
      JSONObject jSONObject = new JSONObject(resultCancelText);
      str1 = jSONObject.optString("title");
      str2 = jSONObject.optString("content");
      str3 = jSONObject.optString("confirmText");
      resultCancelText = jSONObject.optString("cancelText");
      bool = jSONObject.optBoolean("showCancel");
      str4 = jSONObject.optString("confirmColor");
      str5 = jSONObject.optString("cancelColor");
      if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str1)) {
        AppBrandLogger.e("tma_DialogImpl", new Object[] { "empty" });
        return null;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("tma_DialogImpl", new Object[] { "", exception });
      return null;
    } 
    if (!bool)
      resultCancelText = ""; 
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            DialogImpl.this.showModeDialog(title, content, showCancel, resultCancelText, cancelColor, confirmText, confirmColor, nativeModuleCallback);
          }
        });
    return null;
  }
  
  public void showModeDialog(String paramString1, String paramString2, boolean paramBoolean, String paramString3, String paramString4, String paramString5, String paramString6, final NativeModule.NativeModuleCallback nativeModuleCallback) {
    Activity activity = getCurrentActivity();
    if (activity != null)
      ModalDialog.Builder.builder(activity).title(paramString1).content(paramString2).positiveBtnText(paramString5).negativeBtnText(paramString3).onPositiveBtnClickListener(new ModalDialog.OnPositiveBtnClickListener() {
            public void onClick() {
              NativeModule.NativeModuleCallback nativeModuleCallback = nativeModuleCallback;
              if (nativeModuleCallback != null)
                nativeModuleCallback.onNativeModuleCall("1"); 
            }
          }).onNegativeBtnClickListener(new ModalDialog.OnNegativeBtnClickListener() {
            public void onClick() {
              NativeModule.NativeModuleCallback nativeModuleCallback = nativeModuleCallback;
              if (nativeModuleCallback != null)
                nativeModuleCallback.onNativeModuleCall("0"); 
            }
          }).build().show(); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dialog\DialogImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */