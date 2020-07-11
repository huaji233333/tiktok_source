package com.facebook.react.views.scroll;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;

public class ReactScrollViewHelper {
  public static void emitScrollBeginDragEvent(ViewGroup paramViewGroup) {
    emitScrollEvent(paramViewGroup, ScrollEventType.BEGIN_DRAG);
  }
  
  public static void emitScrollEndDragEvent(ViewGroup paramViewGroup, float paramFloat1, float paramFloat2) {
    emitScrollEvent(paramViewGroup, ScrollEventType.END_DRAG, paramFloat1, paramFloat2);
  }
  
  public static void emitScrollEvent(ViewGroup paramViewGroup, float paramFloat1, float paramFloat2) {
    emitScrollEvent(paramViewGroup, ScrollEventType.SCROLL, paramFloat1, paramFloat2);
  }
  
  private static void emitScrollEvent(ViewGroup paramViewGroup, ScrollEventType paramScrollEventType) {
    emitScrollEvent(paramViewGroup, paramScrollEventType, 0.0F, 0.0F);
  }
  
  private static void emitScrollEvent(ViewGroup paramViewGroup, ScrollEventType paramScrollEventType, float paramFloat1, float paramFloat2) {
    View view = paramViewGroup.getChildAt(0);
    if (view == null)
      return; 
    ((UIManagerModule)((ReactContext)paramViewGroup.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(ScrollEvent.obtain(paramViewGroup.getId(), paramScrollEventType, paramViewGroup.getScrollX(), paramViewGroup.getScrollY(), paramFloat1, paramFloat2, view.getWidth(), view.getHeight(), paramViewGroup.getWidth(), paramViewGroup.getHeight()));
  }
  
  public static void emitScrollMomentumBeginEvent(ViewGroup paramViewGroup, int paramInt1, int paramInt2) {
    emitScrollEvent(paramViewGroup, ScrollEventType.MOMENTUM_BEGIN, paramInt1, paramInt2);
  }
  
  public static void emitScrollMomentumEndEvent(ViewGroup paramViewGroup) {
    emitScrollEvent(paramViewGroup, ScrollEventType.MOMENTUM_END);
  }
  
  public static int parseOverScrollMode(String paramString) {
    if (paramString == null || paramString.equals("auto"))
      return 1; 
    if (paramString.equals("always"))
      return 0; 
    if (paramString.equals("never"))
      return 2; 
    StringBuilder stringBuilder = new StringBuilder("wrong overScrollMode: ");
    stringBuilder.append(paramString);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ReactScrollViewHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */