package com.tt.miniapp.view.webcore.scroller;

import android.content.Context;
import com.tt.miniapp.util.ConcaveScreenUtils;

public class WebViewScrollerFactory {
  public static WebViewScroller createScrollerSimple(Context paramContext) {
    return (WebViewScroller)(ConcaveScreenUtils.isVivoConcaveScreen() ? new VivoConcaveWebViewScroller(paramContext) : new CommonWebViewScroller(paramContext));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\scroller\WebViewScrollerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */