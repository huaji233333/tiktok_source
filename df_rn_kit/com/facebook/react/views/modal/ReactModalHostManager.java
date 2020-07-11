package com.facebook.react.views.modal;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.Map;

@ReactModule(name = "RCTModalHostView")
public class ReactModalHostManager extends ViewGroupManager<ReactModalHostView> {
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, final ReactModalHostView view) {
    final EventDispatcher dispatcher = ((UIManagerModule)paramThemedReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
    view.setOnRequestCloseListener(new ReactModalHostView.OnRequestCloseListener() {
          public void onRequestClose(DialogInterface param1DialogInterface) {
            dispatcher.dispatchEvent(new RequestCloseEvent(view.getId()));
          }
        });
    view.setOnShowListener(new DialogInterface.OnShowListener() {
          public void onShow(DialogInterface param1DialogInterface) {
            dispatcher.dispatchEvent(new ShowEvent(view.getId()));
          }
        });
  }
  
  public LayoutShadowNode createShadowNodeInstance() {
    return new ModalHostShadowNode();
  }
  
  protected ReactModalHostView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactModalHostView((Context)paramThemedReactContext);
  }
  
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.builder().put("topRequestClose", MapBuilder.of("registrationName", "onRequestClose")).put("topShow", MapBuilder.of("registrationName", "onShow")).build();
  }
  
  public String getName() {
    return "RCTModalHostView";
  }
  
  public Class<? extends LayoutShadowNode> getShadowNodeClass() {
    return (Class)ModalHostShadowNode.class;
  }
  
  protected void onAfterUpdateTransaction(ReactModalHostView paramReactModalHostView) {
    super.onAfterUpdateTransaction((View)paramReactModalHostView);
    paramReactModalHostView.showOrUpdate();
  }
  
  public void onDropViewInstance(ReactModalHostView paramReactModalHostView) {
    super.onDropViewInstance((View)paramReactModalHostView);
    paramReactModalHostView.onDropInstance();
  }
  
  @ReactProp(name = "animationType")
  public void setAnimationType(ReactModalHostView paramReactModalHostView, String paramString) {
    paramReactModalHostView.setAnimationType(paramString);
  }
  
  @ReactProp(name = "hardwareAccelerated")
  public void setHardwareAccelerated(ReactModalHostView paramReactModalHostView, boolean paramBoolean) {
    paramReactModalHostView.setHardwareAccelerated(paramBoolean);
  }
  
  @ReactProp(name = "transparent")
  public void setTransparent(ReactModalHostView paramReactModalHostView, boolean paramBoolean) {
    paramReactModalHostView.setTransparent(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\ReactModalHostManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */