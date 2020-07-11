package com.facebook.react.views.scroll;

import com.a;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import java.util.Map;

public class ReactScrollViewCommandHelper {
  public static Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("scrollTo", Integer.valueOf(1), "scrollToEnd", Integer.valueOf(2), "flashScrollIndicators", Integer.valueOf(3));
  }
  
  public static <T> void receiveCommand(ScrollCommandHandler<T> paramScrollCommandHandler, T paramT, int paramInt, ReadableArray paramReadableArray) {
    a.b(paramScrollCommandHandler);
    a.b(paramT);
    a.b(paramReadableArray);
    if (paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt == 3) {
          paramScrollCommandHandler.flashScrollIndicators(paramT);
          return;
        } 
        throw new IllegalArgumentException(a.a("Unsupported command %d received by %s.", new Object[] { Integer.valueOf(paramInt), paramScrollCommandHandler.getClass().getSimpleName() }));
      } 
      paramScrollCommandHandler.scrollToEnd(paramT, new ScrollToEndCommandData(paramReadableArray.getBoolean(0)));
      return;
    } 
    paramScrollCommandHandler.scrollTo(paramT, new ScrollToCommandData(Math.round(PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(0))), Math.round(PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(1))), paramReadableArray.getBoolean(2)));
  }
  
  public static interface ScrollCommandHandler<T> {
    void flashScrollIndicators(T param1T);
    
    void scrollTo(T param1T, ReactScrollViewCommandHelper.ScrollToCommandData param1ScrollToCommandData);
    
    void scrollToEnd(T param1T, ReactScrollViewCommandHelper.ScrollToEndCommandData param1ScrollToEndCommandData);
  }
  
  public static class ScrollToCommandData {
    public final boolean mAnimated;
    
    public final int mDestX;
    
    public final int mDestY;
    
    ScrollToCommandData(int param1Int1, int param1Int2, boolean param1Boolean) {
      this.mDestX = param1Int1;
      this.mDestY = param1Int2;
      this.mAnimated = param1Boolean;
    }
  }
  
  public static class ScrollToEndCommandData {
    public final boolean mAnimated;
    
    ScrollToEndCommandData(boolean param1Boolean) {
      this.mAnimated = param1Boolean;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ReactScrollViewCommandHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */