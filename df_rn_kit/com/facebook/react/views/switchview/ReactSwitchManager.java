package com.facebook.react.views.switchview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.CompoundButton;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.b;

public class ReactSwitchManager extends SimpleViewManager<ReactSwitch> {
  private static final CompoundButton.OnCheckedChangeListener ON_CHECKED_CHANGE_LISTENER = new CompoundButton.OnCheckedChangeListener() {
      public final void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
        ((UIManagerModule)((ReactContext)param1CompoundButton.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactSwitchEvent(param1CompoundButton.getId(), param1Boolean));
      }
    };
  
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, ReactSwitch paramReactSwitch) {
    paramReactSwitch.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
  }
  
  public LayoutShadowNode createShadowNodeInstance() {
    return new ReactSwitchShadowNode();
  }
  
  protected ReactSwitch createViewInstance(ThemedReactContext paramThemedReactContext) {
    ReactSwitch reactSwitch = new ReactSwitch((Context)paramThemedReactContext);
    reactSwitch.setShowText(false);
    return reactSwitch;
  }
  
  public String getName() {
    return "AndroidSwitch";
  }
  
  public Class getShadowNodeClass() {
    return ReactSwitchShadowNode.class;
  }
  
  @ReactProp(defaultBoolean = true, name = "enabled")
  public void setEnabled(ReactSwitch paramReactSwitch, boolean paramBoolean) {
    paramReactSwitch.setEnabled(paramBoolean);
  }
  
  @ReactProp(name = "on")
  public void setOn(ReactSwitch paramReactSwitch, boolean paramBoolean) {
    paramReactSwitch.setOnCheckedChangeListener(null);
    paramReactSwitch.setOn(paramBoolean);
    paramReactSwitch.setOnCheckedChangeListener(ON_CHECKED_CHANGE_LISTENER);
  }
  
  @ReactProp(customType = "Color", name = "thumbTintColor")
  public void setThumbTintColor(ReactSwitch paramReactSwitch, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactSwitch.getThumbDrawable().clearColorFilter();
      return;
    } 
    paramReactSwitch.getThumbDrawable().setColorFilter(paramInteger.intValue(), PorterDuff.Mode.MULTIPLY);
  }
  
  @ReactProp(customType = "Color", name = "trackTintColor")
  public void setTrackTintColor(ReactSwitch paramReactSwitch, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactSwitch.getTrackDrawable().clearColorFilter();
      return;
    } 
    paramReactSwitch.getTrackDrawable().setColorFilter(paramInteger.intValue(), PorterDuff.Mode.MULTIPLY);
  }
  
  static class ReactSwitchShadowNode extends LayoutShadowNode implements YogaMeasureFunction {
    private int mHeight;
    
    private boolean mMeasured;
    
    private int mWidth;
    
    private ReactSwitchShadowNode() {
      initMeasureFunction();
    }
    
    private ReactSwitchShadowNode(ReactSwitchShadowNode param1ReactSwitchShadowNode) {
      super(param1ReactSwitchShadowNode);
      this.mWidth = param1ReactSwitchShadowNode.mWidth;
      this.mHeight = param1ReactSwitchShadowNode.mHeight;
      this.mMeasured = param1ReactSwitchShadowNode.mMeasured;
      initMeasureFunction();
    }
    
    private void initMeasureFunction() {
      setMeasureFunction(this);
    }
    
    public long measure(YogaNode param1YogaNode, float param1Float1, YogaMeasureMode param1YogaMeasureMode1, float param1Float2, YogaMeasureMode param1YogaMeasureMode2) {
      if (!this.mMeasured) {
        ReactSwitch reactSwitch = new ReactSwitch((Context)getThemedContext());
        reactSwitch.setShowText(false);
        int i = View.MeasureSpec.makeMeasureSpec(-2, 0);
        reactSwitch.measure(i, i);
        this.mWidth = reactSwitch.getMeasuredWidth();
        this.mHeight = reactSwitch.getMeasuredHeight();
        this.mMeasured = true;
      } 
      return b.a(this.mWidth, this.mHeight);
    }
    
    public ReactSwitchShadowNode mutableCopy() {
      return new ReactSwitchShadowNode(this);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\switchview\ReactSwitchManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */