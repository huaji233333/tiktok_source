package com.facebook.react.modules.share;

import android.app.Activity;
import android.content.Intent;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "ShareModule")
public class ShareModule extends ReactContextBaseJavaModule {
  public ShareModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public String getName() {
    return "ShareModule";
  }
  
  @ReactMethod
  public void share(ReadableMap paramReadableMap, String paramString, Promise paramPromise) {
    if (paramReadableMap == null) {
      paramPromise.reject("E_INVALID_CONTENT", "Content cannot be null");
      return;
    } 
    try {
      Intent intent2 = new Intent("android.intent.action.SEND");
      intent2.setTypeAndNormalize("text/plain");
      if (paramReadableMap.hasKey("title"))
        intent2.putExtra("android.intent.extra.SUBJECT", paramReadableMap.getString("title")); 
      if (paramReadableMap.hasKey("message"))
        intent2.putExtra("android.intent.extra.TEXT", paramReadableMap.getString("message")); 
      Intent intent1 = Intent.createChooser(intent2, paramString);
      intent1.addCategory("android.intent.category.DEFAULT");
      Activity activity = getCurrentActivity();
      if (activity != null) {
        activity.startActivity(intent1);
      } else {
        getReactApplicationContext().startActivity(intent1);
      } 
      WritableMap writableMap = Arguments.createMap();
      writableMap.putString("action", "sharedAction");
      paramPromise.resolve(writableMap);
      return;
    } catch (Exception exception) {
      paramPromise.reject("E_UNABLE_TO_OPEN_DIALOG", "Failed to open share dialog");
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\share\ShareModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */