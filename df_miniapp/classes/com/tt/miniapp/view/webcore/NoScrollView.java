package com.tt.miniapp.view.webcore;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class NoScrollView extends ScrollView {
  public NoScrollView(Context paramContext) {
    super(paramContext);
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent) {
    return false;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    return false;
  }
  
  public void requestChildFocus(View paramView1, View paramView2) {
    if (getParent() != null)
      getParent().requestChildFocus(paramView1, paramView2); 
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean) {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\NoScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */