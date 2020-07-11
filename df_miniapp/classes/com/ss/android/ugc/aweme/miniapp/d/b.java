package com.ss.android.ugc.aweme.miniapp.d;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import org.json.JSONObject;

public final class b extends NativeModule {
  public b(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public final String getName() {
    return "DMTshowModal";
  }
  
  public final String invoke(String paramString, NativeModule.NativeModuleCallback paramNativeModuleCallback) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      String str1 = jSONObject.optString("title");
      String str2 = jSONObject.optString("content");
      String str3 = jSONObject.optString("confirmText");
      if (jSONObject.optBoolean("showCancel")) {
        paramString = jSONObject.optString("cancelText");
      } else {
        paramString = "";
      } 
      String str5 = jSONObject.optString("confirmColor");
      String str4 = jSONObject.optString("cancelColor");
      if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str1)) {
        AppBrandLogger.e("tma_DialogImpl", new Object[] { "empty" });
        return null;
      } 
      AppbrandContext.mainHandler.post(new Runnable(this, str1, str2, paramString, str4, str3, str5, paramNativeModuleCallback) {
            public final void run() {
              b b1 = this.h;
              String str1 = this.a;
              String str2 = this.b;
              String str3 = this.c;
              String str4 = this.d;
              str4 = this.e;
              String str5 = this.f;
              NativeModule.NativeModuleCallback nativeModuleCallback = this.g;
              Activity activity = b1.getCurrentActivity();
              if (activity != null)
                (new AlertDialog.Builder((Context)activity, 2097807360)).setMessage(str2).setTitle(str1).setNegativeButton(str3, new DialogInterface.OnClickListener(b1, nativeModuleCallback) {
                      public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        NativeModule.NativeModuleCallback nativeModuleCallback = this.a;
                        if (nativeModuleCallback != null)
                          nativeModuleCallback.onNativeModuleCall("0"); 
                      }
                    }).setPositiveButton(str4, new DialogInterface.OnClickListener(b1, nativeModuleCallback) {
                      public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        NativeModule.NativeModuleCallback nativeModuleCallback = this.a;
                        if (nativeModuleCallback != null)
                          nativeModuleCallback.onNativeModuleCall("1"); 
                      }
                    }).create().show(); 
            }
          });
      return null;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_DialogImpl", new Object[] { "", exception });
      return null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */