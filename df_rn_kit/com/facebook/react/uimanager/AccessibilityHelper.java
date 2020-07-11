package com.facebook.react.uimanager;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.RadioButton;

class AccessibilityHelper {
  private static final View.AccessibilityDelegate BUTTON_DELEGATE = new View.AccessibilityDelegate() {
      public final void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
        param1AccessibilityEvent.setClassName(Button.class.getName());
      }
      
      public final void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfo param1AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfo);
        param1AccessibilityNodeInfo.setClassName(Button.class.getName());
      }
    };
  
  private static final View.AccessibilityDelegate RADIOBUTTON_CHECKED_DELEGATE = new View.AccessibilityDelegate() {
      public final void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
        param1AccessibilityEvent.setClassName(RadioButton.class.getName());
        param1AccessibilityEvent.setChecked(true);
      }
      
      public final void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfo param1AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfo);
        param1AccessibilityNodeInfo.setClassName(RadioButton.class.getName());
        param1AccessibilityNodeInfo.setCheckable(true);
        param1AccessibilityNodeInfo.setChecked(true);
      }
    };
  
  private static final View.AccessibilityDelegate RADIOBUTTON_UNCHECKED_DELEGATE = new View.AccessibilityDelegate() {
      public final void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
        param1AccessibilityEvent.setClassName(RadioButton.class.getName());
        param1AccessibilityEvent.setChecked(false);
      }
      
      public final void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfo param1AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfo);
        param1AccessibilityNodeInfo.setClassName(RadioButton.class.getName());
        param1AccessibilityNodeInfo.setCheckable(true);
        param1AccessibilityNodeInfo.setChecked(false);
      }
    };
  
  public static void sendAccessibilityEvent(View paramView, int paramInt) {
    paramView.sendAccessibilityEvent(paramInt);
  }
  
  public static void updateAccessibilityComponentType(View paramView, String paramString) {
    if (paramString == null) {
      paramView.setAccessibilityDelegate(null);
      return;
    } 
    byte b = -1;
    int i = paramString.hashCode();
    if (i != -1377687758) {
      if (i != -1320494052) {
        if (i == -714126251 && paramString.equals("radiobutton_checked"))
          b = 1; 
      } else if (paramString.equals("radiobutton_unchecked")) {
        b = 2;
      } 
    } else if (paramString.equals("button")) {
      b = 0;
    } 
    if (b != 0) {
      if (b != 1) {
        if (b != 2) {
          paramView.setAccessibilityDelegate(null);
          return;
        } 
        paramView.setAccessibilityDelegate(RADIOBUTTON_UNCHECKED_DELEGATE);
        return;
      } 
      paramView.setAccessibilityDelegate(RADIOBUTTON_CHECKED_DELEGATE);
      return;
    } 
    paramView.setAccessibilityDelegate(BUTTON_DELEGATE);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\AccessibilityHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */