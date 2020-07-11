package com.facebook.react.packagerconnection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;

public class PackagerConnectionSettings {
  private static final String TAG = PackagerConnectionSettings.class.getSimpleName();
  
  private final String mPackageName;
  
  private final SharedPreferences mPreferences;
  
  public PackagerConnectionSettings(Context paramContext) {
    this.mPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.mPackageName = paramContext.getPackageName();
  }
  
  public String getDebugServerHost() {
    String str = this.mPreferences.getString("debug_http_host", null);
    if (!TextUtils.isEmpty(str))
      return (String)a.b(str); 
    str = AndroidInfoHelpers.getServerHost();
    if (str.equals("localhost"))
      a.b(TAG, "You seem to be running on device. Run 'adb reverse tcp:8081 tcp:8081' to forward the debug server's port to the device."); 
    return str;
  }
  
  public String getInspectorServerHost() {
    return AndroidInfoHelpers.getInspectorProxyHost();
  }
  
  public String getPackageName() {
    return this.mPackageName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\packagerconnection\PackagerConnectionSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */