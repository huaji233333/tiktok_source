package com.facebook.react.modules.toast;

import android.content.Context;
import android.widget.Toast;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = "ToastAndroid")
public class ToastModule extends ReactContextBaseJavaModule {
  public ToastModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public Map<String, Object> getConstants() {
    HashMap<String, Integer> hashMap = MapBuilder.newHashMap();
    hashMap.put("SHORT", Integer.valueOf(0));
    hashMap.put("LONG", Integer.valueOf(1));
    hashMap.put("TOP", Integer.valueOf(49));
    hashMap.put("BOTTOM", Integer.valueOf(81));
    hashMap.put("CENTER", Integer.valueOf(17));
    return (Map)hashMap;
  }
  
  public String getName() {
    return "ToastAndroid";
  }
  
  @ReactMethod
  public void show(final String message, final int duration) {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            _lancet.com_ss_android_ugc_aweme_lancet_DesignBugFixLancet_show(Toast.makeText((Context)ToastModule.this.getReactApplicationContext(), message, duration));
          }
          
          class null {}
        });
  }
  
  @ReactMethod
  public void showWithGravity(final String message, final int duration, final int gravity) {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            Toast toast = Toast.makeText((Context)ToastModule.this.getReactApplicationContext(), message, duration);
            toast.setGravity(gravity, 0, 0);
            _lancet.com_ss_android_ugc_aweme_lancet_DesignBugFixLancet_show(toast);
          }
          
          class null {}
        });
  }
  
  @ReactMethod
  public void showWithGravityAndOffset(final String message, final int duration, final int gravity, final int xOffset, final int yOffset) {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            Toast toast = Toast.makeText((Context)ToastModule.this.getReactApplicationContext(), message, duration);
            toast.setGravity(gravity, xOffset, yOffset);
            _lancet.com_ss_android_ugc_aweme_lancet_DesignBugFixLancet_show(toast);
          }
          
          class null {}
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\toast\ToastModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */