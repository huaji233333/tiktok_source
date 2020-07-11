package com.facebook.react.modules.intent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "IntentAndroid")
public class IntentModule extends ReactContextBaseJavaModule {
  public IntentModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  @ReactMethod
  public void canOpenURL(String paramString, Promise paramPromise) {
    if (paramString == null || paramString.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder("Invalid URL: ");
      stringBuilder.append(paramString);
      paramPromise.reject((Throwable)new JSApplicationIllegalArgumentException(stringBuilder.toString()));
      return;
    } 
    try {
      boolean bool;
      Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      intent.addFlags(268435456);
      if (intent.resolveActivity(getReactApplicationContext().getPackageManager()) != null) {
        bool = true;
      } else {
        bool = false;
      } 
      paramPromise.resolve(Boolean.valueOf(bool));
      return;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder("Could not check if URL '");
      stringBuilder.append(paramString);
      stringBuilder.append("' can be opened: ");
      stringBuilder.append(exception.getMessage());
      paramPromise.reject((Throwable)new JSApplicationIllegalArgumentException(stringBuilder.toString()));
      return;
    } 
  }
  
  @ReactMethod
  public void getInitialURL(Promise paramPromise) {
    try {
      String str;
      Activity activity = getCurrentActivity();
      Intent intent2 = null;
      Intent intent1 = intent2;
      if (activity != null) {
        intent1 = activity.getIntent();
        String str1 = intent1.getAction();
        Uri uri = intent1.getData();
        intent1 = intent2;
        if ("android.intent.action.VIEW".equals(str1)) {
          intent1 = intent2;
          if (uri != null)
            str = uri.toString(); 
        } 
      } 
      paramPromise.resolve(str);
      return;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder("Could not get the initial URL : ");
      stringBuilder.append(exception.getMessage());
      paramPromise.reject((Throwable)new JSApplicationIllegalArgumentException(stringBuilder.toString()));
      return;
    } 
  }
  
  public String getName() {
    return "IntentAndroid";
  }
  
  @ReactMethod
  public void openURL(String paramString, Promise paramPromise) {
    if (paramString == null || paramString.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder("Invalid URL: ");
      stringBuilder.append(paramString);
      paramPromise.reject((Throwable)new JSApplicationIllegalArgumentException(stringBuilder.toString()));
      return;
    } 
    try {
      String str1;
      Activity activity = getCurrentActivity();
      Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      String str2 = getReactApplicationContext().getPackageName();
      ComponentName componentName = intent.resolveActivity(getReactApplicationContext().getPackageManager());
      if (componentName != null) {
        str1 = componentName.getPackageName();
      } else {
        str1 = "";
      } 
      if (activity == null || !str2.equals(str1))
        intent.addFlags(268435456); 
      if (activity != null) {
        activity.startActivity(intent);
      } else {
        getReactApplicationContext().startActivity(intent);
      } 
      paramPromise.resolve(Boolean.valueOf(true));
      return;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder("Could not open URL '");
      stringBuilder.append(paramString);
      stringBuilder.append("': ");
      stringBuilder.append(exception.getMessage());
      paramPromise.reject((Throwable)new JSApplicationIllegalArgumentException(stringBuilder.toString()));
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\intent\IntentModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */