package com.tt.miniapp.feedback.report;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class ScrollableEditText extends AppCompatEditText {
  private Boolean mIntercept;
  
  private float mLastTouchY;
  
  public ScrollableEditText(Context paramContext) {
    super(paramContext);
  }
  
  public ScrollableEditText(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public ScrollableEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i != 1) {
        if (i == 2 && this.mIntercept == null) {
          i = (int)(this.mLastTouchY - paramMotionEvent.getRawY());
          if (Math.abs(i) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
            this.mIntercept = Boolean.valueOf(canScrollVertically(i));
            getParent().requestDisallowInterceptTouchEvent(this.mIntercept.booleanValue());
          } 
        } 
      } else if (Math.abs((int)(this.mLastTouchY - paramMotionEvent.getRawY())) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
        return true;
      } 
    } else {
      this.mLastTouchY = paramMotionEvent.getRawY();
      getParent().requestDisallowInterceptTouchEvent(true);
      this.mIntercept = null;
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public static abstract class AbsTextWatcher implements TextWatcher {
    public void afterTextChanged(Editable param1Editable) {}
    
    public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
    
    public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\report\ScrollableEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */