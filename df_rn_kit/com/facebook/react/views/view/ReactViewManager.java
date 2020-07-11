package com.facebook.react.views.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.a;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

@ReactModule(name = "RCTView")
public class ReactViewManager extends ViewGroupManager<ReactViewGroup> {
  private static final int[] SPACING_TYPES = new int[] { 8, 0, 2, 1, 3, 4, 5 };
  
  private int getPrefixMarkedViewCount(ReactViewGroup paramReactViewGroup, int paramInt) {
    Integer integer = new Integer(1);
    int i = 0;
    int j;
    for (j = 0; i < paramReactViewGroup.mDeleteMark.size() && i <= paramInt + j; j = k) {
      int k = j;
      if (((Integer)paramReactViewGroup.mDeleteMark.get(i)).equals(integer))
        k = j + 1; 
      i++;
    } 
    return j;
  }
  
  public void addView(ReactViewGroup paramReactViewGroup, View paramView, int paramInt) {
    if (paramReactViewGroup.getRemoveClippedSubviews()) {
      paramReactViewGroup.addViewWithSubviewClippingEnabled(paramView, paramInt);
      return;
    } 
    if (paramReactViewGroup.mMarkedChildCount == -1) {
      paramReactViewGroup.addView(paramView, paramInt);
      return;
    } 
    paramInt += getPrefixMarkedViewCount(paramReactViewGroup, paramInt);
    paramReactViewGroup.mDeleteMark.add(paramInt, new Integer(0));
    paramReactViewGroup.addView(paramView, paramInt);
  }
  
  public ReactViewGroup createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactViewGroup((Context)paramThemedReactContext);
  }
  
  public View getChildAt(ReactViewGroup paramReactViewGroup, int paramInt) {
    return paramReactViewGroup.getRemoveClippedSubviews() ? paramReactViewGroup.getChildAtWithSubviewClippingEnabled(paramInt) : ((paramReactViewGroup.mMarkedChildCount == -1) ? paramReactViewGroup.getChildAt(paramInt) : paramReactViewGroup.getChildAt(paramInt + getPrefixMarkedViewCount(paramReactViewGroup, paramInt)));
  }
  
  public int getChildCount(ReactViewGroup paramReactViewGroup) {
    return paramReactViewGroup.getRemoveClippedSubviews() ? paramReactViewGroup.getAllChildrenCount() : ((paramReactViewGroup.mMarkedChildCount == -1) ? paramReactViewGroup.getChildCount() : (paramReactViewGroup.getChildCount() - paramReactViewGroup.mMarkedChildCount));
  }
  
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("hotspotUpdate", Integer.valueOf(1), "setPressed", Integer.valueOf(2));
  }
  
  public String getName() {
    return "RCTView";
  }
  
  public View getUnmarkedChildAt(ReactViewGroup paramReactViewGroup, int paramInt) {
    return paramReactViewGroup.getRemoveClippedSubviews() ? paramReactViewGroup.getChildAtWithSubviewClippingEnabled(paramInt) : paramReactViewGroup.getChildAt(paramInt);
  }
  
  public int getUnmarkedChildCount(ReactViewGroup paramReactViewGroup) {
    return paramReactViewGroup.getRemoveClippedSubviews() ? paramReactViewGroup.getAllChildrenCount() : paramReactViewGroup.getChildCount();
  }
  
  public void markView(ReactViewGroup paramReactViewGroup, int paramInt) {
    if (paramReactViewGroup.mMarkedChildCount == -1) {
      int i = 0;
      paramReactViewGroup.mMarkedChildCount = 0;
      paramReactViewGroup.mDeleteMark = new ArrayList<Integer>();
      Integer integer = new Integer(0);
      while (i < paramReactViewGroup.getChildCount()) {
        paramReactViewGroup.mDeleteMark.add(i, integer);
        i++;
      } 
    } 
    paramReactViewGroup.mMarkedChildCount++;
    paramReactViewGroup.mDeleteMark.set(paramInt + getPrefixMarkedViewCount(paramReactViewGroup, paramInt), new Integer(1));
  }
  
  public void receiveCommand(ReactViewGroup paramReactViewGroup, int paramInt, ReadableArray paramReadableArray) {
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      if (paramReadableArray != null && paramReadableArray.size() == 1) {
        paramReactViewGroup.setPressed(paramReadableArray.getBoolean(0));
        return;
      } 
      throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'setPressed' command");
    } 
    if (paramReadableArray != null && paramReadableArray.size() == 2) {
      if (Build.VERSION.SDK_INT >= 21)
        paramReactViewGroup.drawableHotspotChanged(PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(0)), PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(1))); 
      return;
    } 
    throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'updateHotspot' command");
  }
  
  public void removeAllViews(ReactViewGroup paramReactViewGroup) {
    if (paramReactViewGroup.getRemoveClippedSubviews()) {
      paramReactViewGroup.removeAllViewsWithSubviewClippingEnabled();
      return;
    } 
    if (paramReactViewGroup.mMarkedChildCount != -1) {
      paramReactViewGroup.mMarkedChildCount = 0;
      paramReactViewGroup.mDeleteMark.clear();
    } 
    paramReactViewGroup.removeAllViews();
  }
  
  public void removeViewAt(ReactViewGroup paramReactViewGroup, int paramInt) {
    if (paramReactViewGroup.getRemoveClippedSubviews()) {
      View view = getChildAt(paramReactViewGroup, paramInt);
      if (view.getParent() != null)
        paramReactViewGroup.removeView(view); 
      paramReactViewGroup.removeViewWithSubviewClippingEnabled(view);
      return;
    } 
    if (paramReactViewGroup.mMarkedChildCount != -1) {
      if (((Integer)paramReactViewGroup.mDeleteMark.get(paramInt)).equals(new Integer(1)))
        paramReactViewGroup.mMarkedChildCount--; 
      paramReactViewGroup.mDeleteMark.remove(paramInt);
    } 
    paramReactViewGroup.removeViewAt(paramInt);
  }
  
  @ReactProp(name = "accessible")
  public void setAccessible(ReactViewGroup paramReactViewGroup, boolean paramBoolean) {
    paramReactViewGroup.setFocusable(paramBoolean);
  }
  
  @ReactPropGroup(customType = "Color", names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor", "borderStartColor", "borderEndColor"})
  public void setBorderColor(ReactViewGroup paramReactViewGroup, int paramInt, Integer paramInteger) {
    float f1;
    float f2 = 1.0E21F;
    if (paramInteger == null) {
      f1 = 1.0E21F;
    } else {
      f1 = (paramInteger.intValue() & 0xFFFFFF);
    } 
    if (paramInteger != null)
      f2 = (paramInteger.intValue() >>> 24); 
    paramReactViewGroup.setBorderColor(SPACING_TYPES[paramInt], f1, f2);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderRadius", "borderTopLeftRadius", "borderTopRightRadius", "borderBottomRightRadius", "borderBottomLeftRadius", "borderTopStartRadius", "borderTopEndRadius", "borderBottomStartRadius", "borderBottomEndRadius"})
  public void setBorderRadius(ReactViewGroup paramReactViewGroup, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat)) {
      f = paramFloat;
      if (paramFloat < 0.0F)
        f = 1.0E21F; 
    } 
    paramFloat = f;
    if (!a.a(f))
      paramFloat = PixelUtil.toPixelFromDIP(f); 
    if (paramInt == 0) {
      paramReactViewGroup.setBorderRadius(paramFloat);
      return;
    } 
    paramReactViewGroup.setBorderRadius(paramFloat, paramInt - 1);
  }
  
  @ReactProp(name = "borderStyle")
  public void setBorderStyle(ReactViewGroup paramReactViewGroup, String paramString) {
    paramReactViewGroup.setBorderStyle(paramString);
  }
  
  @ReactPropGroup(defaultFloat = 1.0E21F, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth", "borderStartWidth", "borderEndWidth"})
  public void setBorderWidth(ReactViewGroup paramReactViewGroup, int paramInt, float paramFloat) {
    float f = paramFloat;
    if (!a.a(paramFloat)) {
      f = paramFloat;
      if (paramFloat < 0.0F)
        f = 1.0E21F; 
    } 
    paramFloat = f;
    if (!a.a(f))
      paramFloat = PixelUtil.toPixelFromDIP(f); 
    paramReactViewGroup.setBorderWidth(SPACING_TYPES[paramInt], paramFloat);
  }
  
  @ReactProp(name = "collapsable")
  public void setCollapsable(ReactViewGroup paramReactViewGroup, boolean paramBoolean) {}
  
  @ReactProp(name = "hitSlop")
  public void setHitSlop(ReactViewGroup paramReactViewGroup, ReadableMap paramReadableMap) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    if (paramReadableMap == null) {
      paramReactViewGroup.setHitSlopRect((Rect)null);
      return;
    } 
    boolean bool = paramReadableMap.hasKey("left");
    int i = 0;
    if (bool) {
      bool1 = (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("left"));
    } else {
      bool1 = false;
    } 
    if (paramReadableMap.hasKey("top")) {
      bool2 = (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("top"));
    } else {
      bool2 = false;
    } 
    if (paramReadableMap.hasKey("right")) {
      bool3 = (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("right"));
    } else {
      bool3 = false;
    } 
    if (paramReadableMap.hasKey("bottom"))
      i = (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("bottom")); 
    paramReactViewGroup.setHitSlopRect(new Rect(bool1, bool2, bool3, i));
  }
  
  @ReactProp(name = "nativeBackgroundAndroid")
  public void setNativeBackground(ReactViewGroup paramReactViewGroup, ReadableMap paramReadableMap) {
    Drawable drawable;
    if (paramReadableMap == null) {
      paramReadableMap = null;
    } else {
      drawable = ReactDrawableHelper.createDrawableFromJSDescription(paramReactViewGroup.getContext(), paramReadableMap);
    } 
    paramReactViewGroup.setTranslucentBackgroundDrawable(drawable);
  }
  
  @ReactProp(name = "nativeForegroundAndroid")
  public void setNativeForeground(ReactViewGroup paramReactViewGroup, ReadableMap paramReadableMap) {
    Drawable drawable;
    if (paramReadableMap == null) {
      paramReadableMap = null;
    } else {
      drawable = ReactDrawableHelper.createDrawableFromJSDescription(paramReactViewGroup.getContext(), paramReadableMap);
    } 
    paramReactViewGroup.setForeground(drawable);
  }
  
  @ReactProp(name = "needsOffscreenAlphaCompositing")
  public void setNeedsOffscreenAlphaCompositing(ReactViewGroup paramReactViewGroup, boolean paramBoolean) {
    paramReactViewGroup.setNeedsOffscreenAlphaCompositing(paramBoolean);
  }
  
  @ReactProp(name = "overflow")
  public void setOverflow(ReactViewGroup paramReactViewGroup, String paramString) {
    paramReactViewGroup.setOverflow(paramString);
  }
  
  @ReactProp(name = "pointerEvents")
  public void setPointerEvents(ReactViewGroup paramReactViewGroup, String paramString) {
    if (paramString == null) {
      paramReactViewGroup.setPointerEvents(PointerEvents.AUTO);
      return;
    } 
    paramReactViewGroup.setPointerEvents(PointerEvents.valueOf(paramString.toUpperCase(Locale.US).replace("-", "_")));
  }
  
  @ReactProp(name = "removeClippedSubviews")
  public void setRemoveClippedSubviews(ReactViewGroup paramReactViewGroup, boolean paramBoolean) {
    paramReactViewGroup.setRemoveClippedSubviews(paramBoolean);
  }
  
  @ReactProp(name = "hasTVPreferredFocus")
  public void setTVPreferredFocus(ReactViewGroup paramReactViewGroup, boolean paramBoolean) {
    if (paramBoolean) {
      paramReactViewGroup.setFocusable(true);
      paramReactViewGroup.setFocusableInTouchMode(true);
      paramReactViewGroup.requestFocus();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\view\ReactViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */