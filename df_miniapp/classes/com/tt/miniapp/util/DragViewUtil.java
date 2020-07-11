package com.tt.miniapp.util;

import android.view.MotionEvent;
import android.view.View;

public class DragViewUtil {
  public static void drag(View paramView) {
    drag(paramView, 0L);
  }
  
  public static void drag(View paramView, long paramLong) {
    paramView.setOnTouchListener(new TouchListener(paramLong));
  }
  
  static class TouchListener implements View.OnTouchListener {
    private long delay;
    
    private long downTime;
    
    private float downX;
    
    private float downY;
    
    private TouchListener() {}
    
    private TouchListener(long param1Long) {
      this.delay = param1Long;
    }
    
    public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
      int i = param1MotionEvent.getAction();
      if (i != 0) {
        if (i == 2 && System.currentTimeMillis() - this.downTime >= this.delay) {
          float f1 = param1MotionEvent.getX() - this.downX;
          float f2 = param1MotionEvent.getY() - this.downY;
          if (f1 != 0.0F && f2 != 0.0F) {
            i = (int)(param1View.getLeft() + f1);
            int j = (int)(param1View.getRight() + f1);
            param1View.layout(i, (int)(param1View.getTop() + f2), j, (int)(param1View.getBottom() + f2));
            param1View.getLayoutParams();
          } 
        } 
      } else {
        this.downX = param1MotionEvent.getX();
        this.downY = param1MotionEvent.getY();
        this.downTime = System.currentTimeMillis();
      } 
      return false;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\DragViewUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */