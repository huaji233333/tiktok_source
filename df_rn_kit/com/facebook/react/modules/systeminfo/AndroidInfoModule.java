package com.facebook.react.modules.systeminfo;

import android.app.UiModeManager;
import android.os.Build;
import android.provider.Settings;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = "PlatformConstants")
public class AndroidInfoModule extends ReactContextBaseJavaModule {
  public AndroidInfoModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private String uiMode() {
    int i = ((UiModeManager)getReactApplicationContext().getSystemService("uimode")).getCurrentModeType();
    return (i != 1) ? ((i != 2) ? ((i != 3) ? ((i != 4) ? ((i != 6) ? "unknown" : "watch") : "tv") : "car") : "desk") : "normal";
  }
  
  public Map<String, Object> getConstants() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("Version", Integer.valueOf(Build.VERSION.SDK_INT));
    hashMap.put("Release", Build.VERSION.RELEASE);
    hashMap.put("Serial", Build.SERIAL);
    hashMap.put("Fingerprint", Build.FINGERPRINT);
    hashMap.put("Model", Build.MODEL);
    hashMap.put("ServerHost", AndroidInfoHelpers.getServerHost());
    hashMap.put("isTesting", Boolean.valueOf("true".equals(System.getProperty("IS_TESTING"))));
    hashMap.put("reactNativeVersion", ReactNativeVersion.VERSION);
    hashMap.put("uiMode", uiMode());
    hashMap.put("androidID", Settings.Secure.getString(getReactApplicationContext().getContentResolver(), "android_id"));
    return (Map)hashMap;
  }
  
  public String getName() {
    return "PlatformConstants";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\systeminfo\AndroidInfoModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */