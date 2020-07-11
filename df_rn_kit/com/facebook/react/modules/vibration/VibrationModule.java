package com.facebook.react.modules.vibration;

import android.os.Vibrator;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "Vibration")
public class VibrationModule extends ReactContextBaseJavaModule {
  public VibrationModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  @ReactMethod
  public void cancel() {
    Vibrator vibrator = (Vibrator)getReactApplicationContext().getSystemService("vibrator");
    if (vibrator != null)
      vibrator.cancel(); 
  }
  
  public String getName() {
    return "Vibration";
  }
  
  @ReactMethod
  public void vibrate(int paramInt) {
    Vibrator vibrator = (Vibrator)getReactApplicationContext().getSystemService("vibrator");
    if (vibrator != null)
      vibrator.vibrate(paramInt); 
  }
  
  @ReactMethod
  public void vibrateByPattern(ReadableArray paramReadableArray, int paramInt) {
    long[] arrayOfLong = new long[paramReadableArray.size()];
    for (int i = 0; i < paramReadableArray.size(); i++)
      arrayOfLong[i] = paramReadableArray.getInt(i); 
    Vibrator vibrator = (Vibrator)getReactApplicationContext().getSystemService("vibrator");
    if (vibrator != null)
      vibrator.vibrate(arrayOfLong, paramInt); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\vibration\VibrationModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */