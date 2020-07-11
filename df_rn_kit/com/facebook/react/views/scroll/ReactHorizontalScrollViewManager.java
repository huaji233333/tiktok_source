package com.facebook.react.views.scroll;

import android.content.Context;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.a;

@ReactModule(name = "AndroidHorizontalScrollView")
public class ReactHorizontalScrollViewManager extends ViewGroupManager<ReactHorizontalScrollView> implements ReactScrollViewCommandHelper.ScrollCommandHandler<ReactHorizontalScrollView> {
  private static final int[] SPACING_TYPES = new int[] { 8, 0, 2, 1, 3 };
  
  private FpsListener mFpsListener;
  
  public ReactHorizontalScrollViewManager() {
    this(null);
  }
  
  public ReactHorizontalScrollViewManager(FpsListener paramFpsListener) {
    this.mFpsListener = paramFpsListener;
  }
  
  public ReactHorizontalScrollView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactHorizontalScrollView((Context)paramThemedReactContext, this.mFpsListener);
  }
  
  public void flashScrollIndicators(ReactHorizontalScrollView paramReactHorizontalScrollView) {
    paramReactHorizontalScrollView.flashScrollIndicators();
  }
  
  public String getName() {
    return "AndroidHorizontalScrollView";
  }
  
  public void receiveCommand(ReactHorizontalScrollView paramReactHorizontalScrollView, int paramInt, ReadableArray paramReadableArray) {
    ReactScrollViewCommandHelper.receiveCommand(this, paramReactHorizontalScrollView, paramInt, paramReadableArray);
  }
  
  public void scrollTo(ReactHorizontalScrollView paramReactHorizontalScrollView, ReactScrollViewCommandHelper.ScrollToCommandData paramScrollToCommandData) {
    if (paramScrollToCommandData.mAnimated) {
      paramReactHorizontalScrollView.smoothScrollTo(paramScrollToCommandData.mDestX, paramScrollToCommandData.mDestY);
      return;
    } 
    paramReactHorizontalScrollView.scrollTo(paramScrollToCommandData.mDestX, paramScrollToCommandData.mDestY);
  }
  
  public void scrollToEnd(ReactHorizontalScrollView paramReactHorizontalScrollView, ReactScrollViewCommandHelper.ScrollToEndCommandData paramScrollToEndCommandData) {
    int i = paramReactHorizontalScrollView.getChildAt(0).getWidth() + paramReactHorizontalScrollView.getPaddingRight();
    if (paramScrollToEndCommandData.mAnimated) {
      paramReactHorizontalScrollView.smoothScrollTo(i, paramReactHorizontalScrollView.getScrollY());
      return;
    } 
    paramReactHorizontalScrollView.scrollTo(i, paramReactHorizontalScrollView.getScrollY());
  }
  
  @ReactPropGroup(customType = "Color", names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
  public void setBorderColor(ReactHorizontalScrollView paramReactHorizontalScrollView, int paramInt, Integer paramInteger) {
    float f1;
    float f2 = 1.0E21F;
    if (paramInteger == null) {
      f1 = 1.0E21F;
    } else {
      f1 = (paramInteger.intValue() & 0xFFFFFF);
    } 
    if (paramInteger != null)
      f2 = (paramInteger.intValue() >>> 24); 
    paramReactHorizontalScrollView.setBorderColor(SPACING_TYPES[paramInt], f1, f2);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius"})
  public void setBorderRadius(ReactHorizontalScrollView paramReactHorizontalScrollView, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    if (paramInt == 0) {
      paramReactHorizontalScrollView.setBorderRadius(f);
      return;
    } 
    paramReactHorizontalScrollView.setBorderRadius(f, paramInt - 1);
  }
  
  @ReactProp(name = "borderStyle")
  public void setBorderStyle(ReactHorizontalScrollView paramReactHorizontalScrollView, String paramString) {
    paramReactHorizontalScrollView.setBorderStyle(paramString);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"})
  public void setBorderWidth(ReactHorizontalScrollView paramReactHorizontalScrollView, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat))
      f = PixelUtil.toPixelFromDIP(paramFloat); 
    paramReactHorizontalScrollView.setBorderWidth(SPACING_TYPES[paramInt], f);
  }
  
  @ReactProp(customType = "Color", defaultInt = 0, name = "endFillColor")
  public void setBottomFillColor(ReactHorizontalScrollView paramReactHorizontalScrollView, int paramInt) {
    paramReactHorizontalScrollView.setEndFillColor(paramInt);
  }
  
  @ReactProp(name = "overScrollMode")
  public void setOverScrollMode(ReactHorizontalScrollView paramReactHorizontalScrollView, String paramString) {
    paramReactHorizontalScrollView.setOverScrollMode(ReactScrollViewHelper.parseOverScrollMode(paramString));
  }
  
  @ReactProp(name = "pagingEnabled")
  public void setPagingEnabled(ReactHorizontalScrollView paramReactHorizontalScrollView, boolean paramBoolean) {
    paramReactHorizontalScrollView.setPagingEnabled(paramBoolean);
  }
  
  @ReactProp(name = "removeClippedSubviews")
  public void setRemoveClippedSubviews(ReactHorizontalScrollView paramReactHorizontalScrollView, boolean paramBoolean) {
    paramReactHorizontalScrollView.setRemoveClippedSubviews(paramBoolean);
  }
  
  @ReactProp(defaultBoolean = true, name = "scrollEnabled")
  public void setScrollEnabled(ReactHorizontalScrollView paramReactHorizontalScrollView, boolean paramBoolean) {
    paramReactHorizontalScrollView.setScrollEnabled(paramBoolean);
  }
  
  @ReactProp(name = "scrollPerfTag")
  public void setScrollPerfTag(ReactHorizontalScrollView paramReactHorizontalScrollView, String paramString) {
    paramReactHorizontalScrollView.setScrollPerfTag(paramString);
  }
  
  @ReactProp(name = "sendMomentumEvents")
  public void setSendMomentumEvents(ReactHorizontalScrollView paramReactHorizontalScrollView, boolean paramBoolean) {
    paramReactHorizontalScrollView.setSendMomentumEvents(paramBoolean);
  }
  
  @ReactProp(name = "showsHorizontalScrollIndicator")
  public void setShowsHorizontalScrollIndicator(ReactHorizontalScrollView paramReactHorizontalScrollView, boolean paramBoolean) {
    paramReactHorizontalScrollView.setHorizontalScrollBarEnabled(paramBoolean);
  }
  
  @ReactProp(name = "snapToInterval")
  public void setSnapToInterval(ReactHorizontalScrollView paramReactHorizontalScrollView, float paramFloat) {
    paramReactHorizontalScrollView.setSnapInterval((int)(paramFloat * (DisplayMetricsHolder.getScreenDisplayMetrics()).density));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ReactHorizontalScrollViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */