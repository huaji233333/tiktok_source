package com.tt.miniapp.component.nativeview.game;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class GameAbsoluteLayout extends FrameLayout {
  public GameAbsoluteLayout(Context paramContext) {
    super(paramContext);
  }
  
  public GameAbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public GameAbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public GameAbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  public static class LayoutParams extends FrameLayout.LayoutParams {
    public LayoutParams(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      super(param1Int1, param1Int2);
      setXY(param1Int3, param1Int4);
    }
    
    public void setGravity(int param1Int1, int param1Int2) {
      this.gravity &= 0xFF7FFFF8;
      this.gravity &= 0xFFFFFF8F;
      if (1 == param1Int1) {
        this.gravity |= 0x1;
      } else if (2 == param1Int1) {
        this.gravity |= 0x800005;
      } else {
        this.gravity |= 0x800003;
      } 
      if (2 == param1Int2) {
        this.gravity |= 0x50;
        return;
      } 
      if (1 == param1Int2) {
        this.gravity |= 0x10;
        return;
      } 
      this.gravity |= 0x30;
    }
    
    public void setX(int param1Int) {
      this.leftMargin = param1Int;
    }
    
    public void setXY(int param1Int1, int param1Int2) {
      setX(param1Int1);
      setY(param1Int2);
    }
    
    public void setY(int param1Int) {
      this.topMargin = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameAbsoluteLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */