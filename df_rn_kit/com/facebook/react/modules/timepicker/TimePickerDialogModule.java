package com.facebook.react.modules.timepicker;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.f;
import android.support.v4.app.k;
import android.widget.TimePicker;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "TimePickerAndroid")
public class TimePickerDialogModule extends ReactContextBaseJavaModule {
  public TimePickerDialogModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private Bundle createFragmentArguments(ReadableMap paramReadableMap) {
    Bundle bundle = new Bundle();
    if (paramReadableMap.hasKey("hour") && !paramReadableMap.isNull("hour"))
      bundle.putInt("hour", paramReadableMap.getInt("hour")); 
    if (paramReadableMap.hasKey("minute") && !paramReadableMap.isNull("minute"))
      bundle.putInt("minute", paramReadableMap.getInt("minute")); 
    if (paramReadableMap.hasKey("is24Hour") && !paramReadableMap.isNull("is24Hour"))
      bundle.putBoolean("is24Hour", paramReadableMap.getBoolean("is24Hour")); 
    if (paramReadableMap.hasKey("mode") && !paramReadableMap.isNull("mode"))
      bundle.putString("mode", paramReadableMap.getString("mode")); 
    return bundle;
  }
  
  public String getName() {
    return "TimePickerAndroid";
  }
  
  @ReactMethod
  public void open(ReadableMap paramReadableMap, Promise paramPromise) {
    k k;
    Activity activity = getCurrentActivity();
    if (activity == null) {
      paramPromise.reject("E_NO_ACTIVITY", "Tried to open a TimePicker dialog while not attached to an Activity");
      return;
    } 
    if (activity instanceof FragmentActivity) {
      k = ((FragmentActivity)activity).getSupportFragmentManager();
      f f = (f)k.a("TimePickerAndroid");
      if (f != null)
        f.dismiss(); 
      f = new SupportTimePickerDialogFragment();
      if (paramReadableMap != null)
        f.setArguments(createFragmentArguments(paramReadableMap)); 
      timePickerDialogListener = new TimePickerDialogListener(paramPromise);
      f.setOnDismissListener(timePickerDialogListener);
      f.setOnTimeSetListener(timePickerDialogListener);
      f.show(k, "TimePickerAndroid");
      return;
    } 
    FragmentManager fragmentManager = k.getFragmentManager();
    DialogFragment dialogFragment = (DialogFragment)fragmentManager.findFragmentByTag("TimePickerAndroid");
    if (dialogFragment != null)
      dialogFragment.dismiss(); 
    dialogFragment = new TimePickerDialogFragment();
    if (timePickerDialogListener != null)
      dialogFragment.setArguments(createFragmentArguments((ReadableMap)timePickerDialogListener)); 
    TimePickerDialogListener timePickerDialogListener = new TimePickerDialogListener(paramPromise);
    dialogFragment.setOnDismissListener(timePickerDialogListener);
    dialogFragment.setOnTimeSetListener(timePickerDialogListener);
    dialogFragment.show(fragmentManager, "TimePickerAndroid");
  }
  
  class TimePickerDialogListener implements TimePickerDialog.OnTimeSetListener, DialogInterface.OnDismissListener {
    private final Promise mPromise;
    
    private boolean mPromiseResolved;
    
    public TimePickerDialogListener(Promise param1Promise) {
      this.mPromise = param1Promise;
    }
    
    public void onDismiss(DialogInterface param1DialogInterface) {
      if (!this.mPromiseResolved && TimePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("action", "dismissedAction");
        this.mPromise.resolve(writableNativeMap);
        this.mPromiseResolved = true;
      } 
    }
    
    public void onTimeSet(TimePicker param1TimePicker, int param1Int1, int param1Int2) {
      if (!this.mPromiseResolved && TimePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("action", "timeSetAction");
        writableNativeMap.putInt("hour", param1Int1);
        writableNativeMap.putInt("minute", param1Int2);
        this.mPromise.resolve(writableNativeMap);
        this.mPromiseResolved = true;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\timepicker\TimePickerDialogModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */