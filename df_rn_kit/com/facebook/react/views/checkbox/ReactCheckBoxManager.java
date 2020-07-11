package com.facebook.react.views.checkbox;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ReactCheckBoxManager extends SimpleViewManager<ReactCheckBox> {
  private static final CompoundButton.OnCheckedChangeListener ON_CHECKED_CHANGE_LISTENER = new CompoundButton.OnCheckedChangeListener() {
      public final void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
        ((UIManagerModule)((ReactContext)param1CompoundButton.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactCheckBoxEvent(param1CompoundButton.getId(), param1Boolean));
      }
    };
  
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, ReactCheckBox paramReactCheckBox) {
    paramReactCheckBox.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
  }
  
  protected ReactCheckBox createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactCheckBox((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "AndroidCheckBox";
  }
  
  @ReactProp(defaultBoolean = true, name = "enabled")
  public void setEnabled(ReactCheckBox paramReactCheckBox, boolean paramBoolean) {
    paramReactCheckBox.setEnabled(paramBoolean);
  }
  
  @ReactProp(name = "on")
  public void setOn(ReactCheckBox paramReactCheckBox, boolean paramBoolean) {
    paramReactCheckBox.setOnCheckedChangeListener(null);
    paramReactCheckBox.setOn(paramBoolean);
    paramReactCheckBox.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\checkbox\ReactCheckBoxManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */