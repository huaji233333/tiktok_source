package com.tt.miniapp.component.nativeview;

import com.tt.miniapp.view.webcore.AbsoluteLayout;

public class NativeOffset {
  private int mCurX;
  
  private int mCurY;
  
  public NativeOffset(int paramInt1, int paramInt2) {
    this.mCurX = paramInt1;
    this.mCurY = paramInt2;
  }
  
  public NativeOffset(AbsoluteLayout.LayoutParams paramLayoutParams) {
    this.mCurX = paramLayoutParams.x;
    this.mCurY = paramLayoutParams.y;
  }
  
  private int getOffsetX(int paramInt) {
    return paramInt - this.mCurX;
  }
  
  private int getOffsetY(int paramInt) {
    return paramInt - this.mCurY;
  }
  
  public int getCurX(int paramInt1, int paramInt2) {
    paramInt1 = getOffsetX(paramInt1);
    this.mCurX = paramInt2;
    return paramInt1 + paramInt2;
  }
  
  public int getCurY(int paramInt1, int paramInt2) {
    paramInt1 = getOffsetY(paramInt1);
    this.mCurY = paramInt2;
    return paramInt1 + paramInt2;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\NativeOffset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */