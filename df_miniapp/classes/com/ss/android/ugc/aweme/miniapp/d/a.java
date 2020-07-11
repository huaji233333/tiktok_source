package com.ss.android.ugc.aweme.miniapp.d;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import com.tt.miniapp.manager.HostActivityManager;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import org.json.JSONArray;
import org.json.JSONObject;

public final class a extends NativeModule {
  public a(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public final void a(Activity paramActivity, String[] paramArrayOfString, NativeModule.NativeModuleCallback paramNativeModuleCallback) {
    com.ss.android.ugc.aweme.miniapp.utils.a a1 = new com.ss.android.ugc.aweme.miniapp.utils.a((Context)paramActivity);
    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(this, paramNativeModuleCallback) {
        public final void onClick(DialogInterface param1DialogInterface, int param1Int) {
          NativeModule.NativeModuleCallback nativeModuleCallback = this.a;
          if (nativeModuleCallback != null)
            nativeModuleCallback.onNativeModuleCall(String.valueOf(param1Int)); 
        }
      };
    a1.a.a((CharSequence[])paramArrayOfString, onClickListener);
    DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener(this, paramNativeModuleCallback) {
        public final void onCancel(DialogInterface param1DialogInterface) {
          NativeModule.NativeModuleCallback nativeModuleCallback = this.a;
          if (nativeModuleCallback != null)
            nativeModuleCallback.onNativeModuleCall("-1"); 
        }
      };
    a1.a.a(onCancelListener);
    a1.a.c();
  }
  
  public final String getName() {
    return "DMTshowActionSheet";
  }
  
  public final String invoke(String paramString, NativeModule.NativeModuleCallback paramNativeModuleCallback) throws Exception {
    JSONArray jSONArray = (new JSONObject(paramString)).optJSONArray("itemList");
    if (jSONArray != null) {
      int j = jSONArray.length();
      String[] arrayOfString = new String[j];
      for (int i = 0; i < j; i++)
        arrayOfString[i] = jSONArray.getString(i); 
      AppbrandContext.mainHandler.post(new Runnable(this, arrayOfString, paramNativeModuleCallback) {
            public final void run() {
              a a1 = this.c;
              String[] arrayOfString = this.a;
              NativeModule.NativeModuleCallback nativeModuleCallback = this.b;
              Activity activity = a1.getCurrentActivity();
              if (activity == null || arrayOfString == null) {
                activity = HostActivityManager.getHostTopActivity();
                if (activity != null) {
                  if (arrayOfString == null)
                    return; 
                } else {
                  return;
                } 
              } 
              a1.a(activity, arrayOfString, nativeModuleCallback);
            }
          });
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */