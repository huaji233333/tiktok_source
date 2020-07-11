package com.tt.miniapphost.render.export;

import com.tt.miniapphost.render.internal.InterfaceConverter.ConvertToMethod;

public interface PlatformViewLayersScrollListener {
  @ConvertToMethod("onBoundsChanged")
  void onBoundsChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  @ConvertToMethod("onScrollChanged")
  void onScrollChanged(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\render\export\PlatformViewLayersScrollListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */