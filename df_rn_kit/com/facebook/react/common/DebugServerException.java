package com.facebook.react.common;

import android.text.TextUtils;
import com.facebook.common.e.a;
import org.json.JSONException;
import org.json.JSONObject;

public class DebugServerException extends RuntimeException {
  public DebugServerException(String paramString) {
    super(paramString);
  }
  
  private DebugServerException(String paramString1, String paramString2, int paramInt1, int paramInt2) {
    super(stringBuilder.toString());
  }
  
  public DebugServerException(String paramString, Throwable paramThrowable) {
    super(paramString, paramThrowable);
  }
  
  public static DebugServerException makeGeneric(String paramString1, String paramString2, Throwable paramThrowable) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append("\n\nTry the following to fix the issue:\n• Ensure that the packager server is running\n• Ensure that your device/emulator is connected to your machine and has USB debugging enabled - run 'adb devices' to see a list of connected devices\n• Ensure Airplane Mode is disabled\n• If you're on a physical device connected to the same machine, run 'adb reverse tcp:8081 tcp:8081' to forward requests from your device\n• If your device is on the same Wi-Fi network, set 'Debug server host & port for device' in 'Dev settings' to your machine's IP address and the port of the local dev server - e.g. 10.0.1.1:8081\n\n");
    stringBuilder.append(paramString2);
    return new DebugServerException(stringBuilder.toString(), paramThrowable);
  }
  
  public static DebugServerException makeGeneric(String paramString, Throwable paramThrowable) {
    return makeGeneric(paramString, "", paramThrowable);
  }
  
  public static DebugServerException parse(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      String str = jSONObject.getString("filename");
      return new DebugServerException(jSONObject.getString("description"), shortenFileName(str), jSONObject.getInt("lineNumber"), jSONObject.getInt("column"));
    } catch (JSONException jSONException) {
      StringBuilder stringBuilder = new StringBuilder("Could not parse DebugServerException from: ");
      stringBuilder.append(paramString);
      a.b("ReactNative", stringBuilder.toString(), (Throwable)jSONException);
      return null;
    } 
  }
  
  private static String shortenFileName(String paramString) {
    String[] arrayOfString = paramString.split("/");
    return arrayOfString[arrayOfString.length - 1];
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\DebugServerException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */