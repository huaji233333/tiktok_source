package com.tt.miniapp.call;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import org.json.JSONException;
import org.json.JSONObject;

public class PhoneCallImpl extends NativeModule {
  public PhoneCallImpl(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public String getName() {
    return "makePhoneCall";
  }
  
  public String invoke(final String phoneNumber, final NativeModule.NativeModuleCallback nativeModuleCallback) throws Exception {
    try {
      phoneNumber = (new JSONObject(phoneNumber)).optString("phoneNumber");
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              Application application;
              String str1;
              Activity activity = PhoneCallImpl.this.getCurrentActivity();
              if (activity == null)
                application = AppbrandContext.getInst().getApplicationContext(); 
              boolean bool = false;
              boolean bool1 = TextUtils.isEmpty(phoneNumber);
              String str2 = " ";
              if (!bool1) {
                try {
                  PhoneCallHelper.markPhoneCall((Context)application, phoneNumber);
                  bool = true;
                  str1 = str2;
                } catch (Exception exception) {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(" ");
                  stringBuilder.append(a.a(exception));
                  str1 = stringBuilder.toString();
                } 
              } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" ");
                stringBuilder.append(a.c("phoneNumber"));
                str1 = stringBuilder.toString();
              } 
              NativeModule.NativeModuleCallback nativeModuleCallback = nativeModuleCallback;
              if (nativeModuleCallback != null) {
                if (bool) {
                  nativeModuleCallback.onNativeModuleCall("ok");
                  return;
                } 
                StringBuilder stringBuilder = new StringBuilder("fail");
                stringBuilder.append(str1);
                nativeModuleCallback.onNativeModuleCall(stringBuilder.toString());
              } 
            }
          });
    } catch (JSONException jSONException) {
      AppBrandLogger.e("PhoneCallImpl", new Object[] { "invoke", jSONException.getStackTrace() });
      if (nativeModuleCallback != null) {
        StringBuilder stringBuilder = new StringBuilder("fail ");
        stringBuilder.append(a.a((Throwable)jSONException));
        nativeModuleCallback.onNativeModuleCall(stringBuilder.toString());
      } 
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\call\PhoneCallImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */