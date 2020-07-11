package com.facebook.react.views.picker;

import android.content.Context;
import android.view.View;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;

@ReactModule(name = "AndroidDropdownPicker")
public class ReactDropdownPickerManager extends ReactPickerManager {
  protected ReactPicker createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactPicker((Context)paramThemedReactContext, 1);
  }
  
  public String getName() {
    return "AndroidDropdownPicker";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\picker\ReactDropdownPickerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */