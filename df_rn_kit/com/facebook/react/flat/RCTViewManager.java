package com.facebook.react.flat;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactDrawableHelper;
import java.util.Map;

public final class RCTViewManager extends FlatViewManager {
  private static final int[] TMP_INT_ARRAY = new int[2];
  
  private static PointerEvents parsePointerEvents(String paramString) {
    if (paramString != null) {
      byte b = -1;
      switch (paramString.hashCode()) {
        case 3387192:
          if (paramString.equals("none"))
            b = 0; 
          break;
        case 3005871:
          if (paramString.equals("auto"))
            b = 1; 
          break;
        case -2089112978:
          if (paramString.equals("box-only"))
            b = 3; 
          break;
        case -2089141766:
          if (paramString.equals("box-none"))
            b = 2; 
          break;
      } 
      if (b != 0) {
        if (b != 1) {
          if (b != 2) {
            if (b == 3)
              return PointerEvents.BOX_ONLY; 
          } else {
            return PointerEvents.BOX_NONE;
          } 
        } else {
          return PointerEvents.AUTO;
        } 
      } else {
        return PointerEvents.NONE;
      } 
    } 
    return PointerEvents.AUTO;
  }
  
  public final RCTView createShadowNodeInstance() {
    return new RCTView();
  }
  
  public final Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("hotspotUpdate", Integer.valueOf(1), "setPressed", Integer.valueOf(2));
  }
  
  public final String getName() {
    return "RCTView";
  }
  
  public final Class<RCTView> getShadowNodeClass() {
    return RCTView.class;
  }
  
  public final void receiveCommand(FlatViewGroup paramFlatViewGroup, int paramInt, ReadableArray paramReadableArray) {
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      if (paramReadableArray != null && paramReadableArray.size() == 1) {
        paramFlatViewGroup.setPressed(paramReadableArray.getBoolean(0));
        return;
      } 
      throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'setPressed' command");
    } 
    if (paramReadableArray != null && paramReadableArray.size() == 2) {
      if (Build.VERSION.SDK_INT >= 21) {
        paramFlatViewGroup.getLocationOnScreen(TMP_INT_ARRAY);
        paramFlatViewGroup.drawableHotspotChanged(PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(0)) - TMP_INT_ARRAY[0], PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(1)) - TMP_INT_ARRAY[1]);
      } 
      return;
    } 
    throw new JSApplicationIllegalArgumentException("Illegal number of arguments for 'updateHotspot' command");
  }
  
  @ReactProp(name = "hitSlop")
  public final void setHitSlop(FlatViewGroup paramFlatViewGroup, ReadableMap paramReadableMap) {
    if (paramReadableMap == null) {
      paramFlatViewGroup.setHitSlopRect((Rect)null);
      return;
    } 
    paramFlatViewGroup.setHitSlopRect(new Rect((int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("left")), (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("top")), (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("right")), (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("bottom"))));
  }
  
  @ReactProp(name = "nativeBackgroundAndroid")
  public final void setHotspot(FlatViewGroup paramFlatViewGroup, ReadableMap paramReadableMap) {
    Drawable drawable;
    if (paramReadableMap == null) {
      paramReadableMap = null;
    } else {
      drawable = ReactDrawableHelper.createDrawableFromJSDescription(paramFlatViewGroup.getContext(), paramReadableMap);
    } 
    paramFlatViewGroup.setHotspot(drawable);
  }
  
  @ReactProp(name = "needsOffscreenAlphaCompositing")
  public final void setNeedsOffscreenAlphaCompositing(FlatViewGroup paramFlatViewGroup, boolean paramBoolean) {
    paramFlatViewGroup.setNeedsOffscreenAlphaCompositing(paramBoolean);
  }
  
  @ReactProp(name = "pointerEvents")
  public final void setPointerEvents(FlatViewGroup paramFlatViewGroup, String paramString) {
    paramFlatViewGroup.setPointerEvents(parsePointerEvents(paramString));
  }
  
  @ReactProp(name = "removeClippedSubviews")
  public final void setRemoveClippedSubviews(FlatViewGroup paramFlatViewGroup, boolean paramBoolean) {
    paramFlatViewGroup.setRemoveClippedSubviews(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */