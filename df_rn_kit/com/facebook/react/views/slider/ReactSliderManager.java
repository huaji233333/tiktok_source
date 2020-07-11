package com.facebook.react.views.slider;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.SeekBar;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.MapBuilder;
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
import java.util.Map;

public class ReactSliderManager extends SimpleViewManager<ReactSlider> {
  private static final SeekBar.OnSeekBarChangeListener ON_CHANGE_LISTENER = new SeekBar.OnSeekBarChangeListener() {
      public final void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {
        ((UIManagerModule)((ReactContext)param1SeekBar.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactSliderEvent(param1SeekBar.getId(), ((ReactSlider)param1SeekBar).toRealProgress(param1Int), param1Boolean));
      }
      
      public final void onStartTrackingTouch(SeekBar param1SeekBar) {}
      
      public final void onStopTrackingTouch(SeekBar param1SeekBar) {
        ((UIManagerModule)((ReactContext)param1SeekBar.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ReactSlidingCompleteEvent(param1SeekBar.getId(), ((ReactSlider)param1SeekBar).toRealProgress(param1SeekBar.getProgress())));
      }
    };
  
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, ReactSlider paramReactSlider) {
    paramReactSlider.setOnSeekBarChangeListener(ON_CHANGE_LISTENER);
  }
  
  public LayoutShadowNode createShadowNodeInstance() {
    return new ReactSliderShadowNode();
  }
  
  protected ReactSlider createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactSlider((Context)paramThemedReactContext, null, 16842875);
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of("topSlidingComplete", MapBuilder.of("registrationName", "onSlidingComplete"));
  }
  
  public String getName() {
    return "RCTSlider";
  }
  
  public Class getShadowNodeClass() {
    return ReactSliderShadowNode.class;
  }
  
  @ReactProp(defaultBoolean = true, name = "enabled")
  public void setEnabled(ReactSlider paramReactSlider, boolean paramBoolean) {
    paramReactSlider.setEnabled(paramBoolean);
  }
  
  @ReactProp(customType = "Color", name = "maximumTrackTintColor")
  public void setMaximumTrackTintColor(ReactSlider paramReactSlider, Integer paramInteger) {
    Drawable drawable = ((LayerDrawable)paramReactSlider.getProgressDrawable().getCurrent()).findDrawableByLayerId(16908288);
    if (paramInteger == null) {
      drawable.clearColorFilter();
      return;
    } 
    drawable.setColorFilter(paramInteger.intValue(), PorterDuff.Mode.SRC_IN);
  }
  
  @ReactProp(defaultDouble = 1.0D, name = "maximumValue")
  public void setMaximumValue(ReactSlider paramReactSlider, double paramDouble) {
    paramReactSlider.setMaxValue(paramDouble);
  }
  
  @ReactProp(customType = "Color", name = "minimumTrackTintColor")
  public void setMinimumTrackTintColor(ReactSlider paramReactSlider, Integer paramInteger) {
    Drawable drawable = ((LayerDrawable)paramReactSlider.getProgressDrawable().getCurrent()).findDrawableByLayerId(16908301);
    if (paramInteger == null) {
      drawable.clearColorFilter();
      return;
    } 
    drawable.setColorFilter(paramInteger.intValue(), PorterDuff.Mode.SRC_IN);
  }
  
  @ReactProp(defaultDouble = 0.0D, name = "minimumValue")
  public void setMinimumValue(ReactSlider paramReactSlider, double paramDouble) {
    paramReactSlider.setMinValue(paramDouble);
  }
  
  @ReactProp(defaultDouble = 0.0D, name = "step")
  public void setStep(ReactSlider paramReactSlider, double paramDouble) {
    paramReactSlider.setStep(paramDouble);
  }
  
  @ReactProp(customType = "Color", name = "thumbTintColor")
  public void setThumbTintColor(ReactSlider paramReactSlider, Integer paramInteger) {
    if (paramInteger == null) {
      paramReactSlider.getThumb().clearColorFilter();
      return;
    } 
    paramReactSlider.getThumb().setColorFilter(paramInteger.intValue(), PorterDuff.Mode.SRC_IN);
  }
  
  @ReactProp(defaultDouble = 0.0D, name = "value")
  public void setValue(ReactSlider paramReactSlider, double paramDouble) {
    paramReactSlider.setOnSeekBarChangeListener(null);
    paramReactSlider.setValue(paramDouble);
    paramReactSlider.setOnSeekBarChangeListener(ON_CHANGE_LISTENER);
  }
  
  static class ReactSliderShadowNode extends LayoutShadowNode implements YogaMeasureFunction {
    private int mHeight;
    
    private boolean mMeasured;
    
    private int mWidth;
    
    private ReactSliderShadowNode() {
      initMeasureFunction();
    }
    
    private ReactSliderShadowNode(ReactSliderShadowNode param1ReactSliderShadowNode) {
      super(param1ReactSliderShadowNode);
      this.mWidth = param1ReactSliderShadowNode.mWidth;
      this.mHeight = param1ReactSliderShadowNode.mHeight;
      this.mMeasured = param1ReactSliderShadowNode.mMeasured;
      initMeasureFunction();
    }
    
    private void initMeasureFunction() {
      setMeasureFunction(this);
    }
    
    public long measure(YogaNode param1YogaNode, float param1Float1, YogaMeasureMode param1YogaMeasureMode1, float param1Float2, YogaMeasureMode param1YogaMeasureMode2) {
      if (!this.mMeasured) {
        ReactSlider reactSlider = new ReactSlider((Context)getThemedContext(), null, 16842875);
        int i = View.MeasureSpec.makeMeasureSpec(-2, 0);
        reactSlider.measure(i, i);
        this.mWidth = reactSlider.getMeasuredWidth();
        this.mHeight = reactSlider.getMeasuredHeight();
        this.mMeasured = true;
      } 
      return b.a(this.mWidth, this.mHeight);
    }
    
    public ReactSliderShadowNode mutableCopy() {
      return new ReactSliderShadowNode(this);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\slider\ReactSliderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */