package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.touch.OnInterceptTouchEventListener;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import java.util.Map;

public abstract class ViewManager<T extends View, C extends ReactShadowNode> extends BaseJavaModule {
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, T paramT) {}
  
  public C createShadowNodeInstance() {
    throw new RuntimeException("ViewManager subclasses must implement createShadowNodeInstance()");
  }
  
  public C createShadowNodeInstance(ReactApplicationContext paramReactApplicationContext) {
    return createShadowNodeInstance();
  }
  
  public final T createView(ThemedReactContext paramThemedReactContext, JSResponderHandler paramJSResponderHandler) {
    T t = createViewInstance(paramThemedReactContext);
    addEventEmitters(paramThemedReactContext, t);
    if (t instanceof ReactInterceptingViewGroup)
      ((ReactInterceptingViewGroup)t).setOnInterceptTouchEventListener((OnInterceptTouchEventListener)paramJSResponderHandler); 
    return t;
  }
  
  protected abstract T createViewInstance(ThemedReactContext paramThemedReactContext);
  
  public Map<String, Integer> getCommandsMap() {
    return null;
  }
  
  public Map<String, Object> getExportedCustomBubblingEventTypeConstants() {
    return null;
  }
  
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return null;
  }
  
  public Map<String, Object> getExportedViewConstants() {
    return null;
  }
  
  public abstract String getName();
  
  public Map<String, String> getNativeProps() {
    return ViewManagerPropertyUpdater.getNativeProps((Class)getClass(), getShadowNodeClass());
  }
  
  public abstract Class<? extends C> getShadowNodeClass();
  
  public void onAfterUpdateTransaction(T paramT) {}
  
  public void onDropViewInstance(T paramT) {}
  
  public void receiveCommand(T paramT, int paramInt, ReadableArray paramReadableArray) {}
  
  public abstract void updateExtraData(T paramT, Object paramObject);
  
  public final void updateProperties(T paramT, ReactStylesDiffMap paramReactStylesDiffMap) {
    ViewManagerPropertyUpdater.updateProps(this, (View)paramT, paramReactStylesDiffMap);
    onAfterUpdateTransaction(paramT);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */