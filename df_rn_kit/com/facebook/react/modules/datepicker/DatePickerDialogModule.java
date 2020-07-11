package com.facebook.react.modules.datepicker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.f;
import android.support.v4.app.k;
import android.widget.DatePicker;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "DatePickerAndroid")
public class DatePickerDialogModule extends ReactContextBaseJavaModule {
  public DatePickerDialogModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private Bundle createFragmentArguments(ReadableMap paramReadableMap) {
    Bundle bundle = new Bundle();
    if (paramReadableMap.hasKey("date") && !paramReadableMap.isNull("date"))
      bundle.putLong("date", (long)paramReadableMap.getDouble("date")); 
    if (paramReadableMap.hasKey("minDate") && !paramReadableMap.isNull("minDate"))
      bundle.putLong("minDate", (long)paramReadableMap.getDouble("minDate")); 
    if (paramReadableMap.hasKey("maxDate") && !paramReadableMap.isNull("maxDate"))
      bundle.putLong("maxDate", (long)paramReadableMap.getDouble("maxDate")); 
    if (paramReadableMap.hasKey("mode") && !paramReadableMap.isNull("mode"))
      bundle.putString("mode", paramReadableMap.getString("mode")); 
    return bundle;
  }
  
  public String getName() {
    return "DatePickerAndroid";
  }
  
  @ReactMethod
  public void open(ReadableMap paramReadableMap, Promise paramPromise) {
    k k;
    Activity activity = getCurrentActivity();
    if (activity == null) {
      paramPromise.reject("E_NO_ACTIVITY", "Tried to open a DatePicker dialog while not attached to an Activity");
      return;
    } 
    if (activity instanceof FragmentActivity) {
      k = ((FragmentActivity)activity).getSupportFragmentManager();
      f f = (f)k.a("DatePickerAndroid");
      if (f != null)
        f.dismiss(); 
      f = new SupportDatePickerDialogFragment();
      if (paramReadableMap != null)
        f.setArguments(createFragmentArguments(paramReadableMap)); 
      datePickerDialogListener = new DatePickerDialogListener(paramPromise);
      f.setOnDismissListener(datePickerDialogListener);
      f.setOnDateSetListener(datePickerDialogListener);
      f.show(k, "DatePickerAndroid");
      return;
    } 
    FragmentManager fragmentManager = k.getFragmentManager();
    DialogFragment dialogFragment = (DialogFragment)fragmentManager.findFragmentByTag("DatePickerAndroid");
    if (dialogFragment != null)
      dialogFragment.dismiss(); 
    dialogFragment = new DatePickerDialogFragment();
    if (datePickerDialogListener != null)
      dialogFragment.setArguments(createFragmentArguments((ReadableMap)datePickerDialogListener)); 
    DatePickerDialogListener datePickerDialogListener = new DatePickerDialogListener(paramPromise);
    dialogFragment.setOnDismissListener(datePickerDialogListener);
    dialogFragment.setOnDateSetListener(datePickerDialogListener);
    dialogFragment.show(fragmentManager, "DatePickerAndroid");
  }
  
  class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener {
    private final Promise mPromise;
    
    private boolean mPromiseResolved;
    
    public DatePickerDialogListener(Promise param1Promise) {
      this.mPromise = param1Promise;
    }
    
    public void onDateSet(DatePicker param1DatePicker, int param1Int1, int param1Int2, int param1Int3) {
      if (!this.mPromiseResolved && DatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("action", "dateSetAction");
        writableNativeMap.putInt("year", param1Int1);
        writableNativeMap.putInt("month", param1Int2);
        writableNativeMap.putInt("day", param1Int3);
        this.mPromise.resolve(writableNativeMap);
        this.mPromiseResolved = true;
      } 
    }
    
    public void onDismiss(DialogInterface param1DialogInterface) {
      if (!this.mPromiseResolved && DatePickerDialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("action", "dismissedAction");
        this.mPromise.resolve(writableNativeMap);
        this.mPromiseResolved = true;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\datepicker\DatePickerDialogModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */