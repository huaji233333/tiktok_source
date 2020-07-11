package com.facebook.react.views.drawer;

import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.common.e.a;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.drawer.events.DrawerClosedEvent;
import com.facebook.react.views.drawer.events.DrawerOpenedEvent;
import com.facebook.react.views.drawer.events.DrawerSlideEvent;
import com.facebook.react.views.drawer.events.DrawerStateChangedEvent;
import java.util.Map;

@ReactModule(name = "AndroidDrawerLayout")
public class ReactDrawerLayoutManager extends ViewGroupManager<ReactDrawerLayout> {
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, ReactDrawerLayout paramReactDrawerLayout) {
    paramReactDrawerLayout.setDrawerListener(new DrawerEventEmitter(paramReactDrawerLayout, ((UIManagerModule)paramThemedReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher()));
  }
  
  public void addView(ReactDrawerLayout paramReactDrawerLayout, View paramView, int paramInt) {
    if (getChildCount((ViewGroup)paramReactDrawerLayout) < 2) {
      if (paramInt == 0 || paramInt == 1) {
        paramReactDrawerLayout.addView(paramView, paramInt);
        paramReactDrawerLayout.setDrawerProperties();
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("The only valid indices for drawer's child are 0 or 1. Got ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" instead.");
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    throw new JSApplicationIllegalArgumentException("The Drawer cannot have more than two children");
  }
  
  protected ReactDrawerLayout createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactDrawerLayout((ReactContext)paramThemedReactContext);
  }
  
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("openDrawer", Integer.valueOf(1), "closeDrawer", Integer.valueOf(2));
  }
  
  @ReactProp(defaultFloat = NaNF, name = "drawerWidth")
  public void getDrawerWidth(ReactDrawerLayout paramReactDrawerLayout, float paramFloat) {
    int i;
    if (Float.isNaN(paramFloat)) {
      i = -1;
    } else {
      i = Math.round(PixelUtil.toPixelFromDIP(paramFloat));
    } 
    paramReactDrawerLayout.setDrawerWidth(i);
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of("topDrawerSlide", MapBuilder.of("registrationName", "onDrawerSlide"), "topDrawerOpened", MapBuilder.of("registrationName", "onDrawerOpen"), "topDrawerClosed", MapBuilder.of("registrationName", "onDrawerClose"), "topDrawerStateChanged", MapBuilder.of("registrationName", "onDrawerStateChanged"));
  }
  
  public Map getExportedViewConstants() {
    return MapBuilder.of("DrawerPosition", MapBuilder.of("Left", Integer.valueOf(8388611), "Right", Integer.valueOf(8388613)));
  }
  
  public String getName() {
    return "AndroidDrawerLayout";
  }
  
  public boolean needsCustomLayoutForChildren() {
    return true;
  }
  
  public void receiveCommand(ReactDrawerLayout paramReactDrawerLayout, int paramInt, ReadableArray paramReadableArray) {
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      paramReactDrawerLayout.closeDrawer();
      return;
    } 
    paramReactDrawerLayout.openDrawer();
  }
  
  @ReactProp(name = "drawerLockMode")
  public void setDrawerLockMode(ReactDrawerLayout paramReactDrawerLayout, String paramString) {
    if (paramString == null || "unlocked".equals(paramString)) {
      paramReactDrawerLayout.setDrawerLockMode(0);
      return;
    } 
    if ("locked-closed".equals(paramString)) {
      paramReactDrawerLayout.setDrawerLockMode(1);
      return;
    } 
    if ("locked-open".equals(paramString)) {
      paramReactDrawerLayout.setDrawerLockMode(2);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Unknown drawerLockMode ");
    stringBuilder.append(paramString);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  @ReactProp(defaultInt = 8388611, name = "drawerPosition")
  public void setDrawerPosition(ReactDrawerLayout paramReactDrawerLayout, int paramInt) {
    if (8388611 == paramInt || 8388613 == paramInt) {
      paramReactDrawerLayout.setDrawerPosition(paramInt);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Unknown drawerPosition ");
    stringBuilder.append(paramInt);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void setElevation(ReactDrawerLayout paramReactDrawerLayout, float paramFloat) {
    if (Build.VERSION.SDK_INT >= 21)
      try {
        ReactDrawerLayout.class.getMethod("setDrawerElevation", new Class[] { float.class }).invoke(paramReactDrawerLayout, new Object[] { Float.valueOf(PixelUtil.toPixelFromDIP(paramFloat)) });
        return;
      } catch (Exception exception) {
        a.b("ReactNative", "setDrawerElevation is not available in this version of the support lib.", exception);
      }  
  }
  
  public static class DrawerEventEmitter implements DrawerLayout.c {
    private final DrawerLayout mDrawerLayout;
    
    private final EventDispatcher mEventDispatcher;
    
    public DrawerEventEmitter(DrawerLayout param1DrawerLayout, EventDispatcher param1EventDispatcher) {
      this.mDrawerLayout = param1DrawerLayout;
      this.mEventDispatcher = param1EventDispatcher;
    }
    
    public void onDrawerClosed(View param1View) {
      this.mEventDispatcher.dispatchEvent((Event)new DrawerClosedEvent(this.mDrawerLayout.getId()));
    }
    
    public void onDrawerOpened(View param1View) {
      this.mEventDispatcher.dispatchEvent((Event)new DrawerOpenedEvent(this.mDrawerLayout.getId()));
    }
    
    public void onDrawerSlide(View param1View, float param1Float) {
      this.mEventDispatcher.dispatchEvent((Event)new DrawerSlideEvent(this.mDrawerLayout.getId(), param1Float));
    }
    
    public void onDrawerStateChanged(int param1Int) {
      this.mEventDispatcher.dispatchEvent((Event)new DrawerStateChangedEvent(this.mDrawerLayout.getId(), param1Int));
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\drawer\ReactDrawerLayoutManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */