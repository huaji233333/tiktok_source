package com.tt.miniapp.view.keyboard;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import com.tt.miniapp.view.SizeDetectFrameLayout;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.util.UIUtils;

public class KeyboardLayout extends SizeDetectFrameLayout {
  public boolean isKeyboardActive;
  
  public int keyboardHeight;
  
  public KeyboardLayoutListener keyboardLayoutListener;
  
  public int mLastKeyboardHeight;
  
  public Runnable runnable = new Runnable() {
      public void run() {
        MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
        if (miniappHostBase == null)
          return; 
        Rect rect = new Rect();
        miniappHostBase.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int i = UIUtils.getScreenHeightWithNavBar(KeyboardLayout.this.getContext());
        int j = i - rect.bottom;
        boolean bool = false;
        if (Math.abs(j) > i / 5)
          bool = true; 
        KeyboardLayout keyboardLayout = KeyboardLayout.this;
        keyboardLayout.keyboardHeight = j;
        keyboardLayout.isKeyboardActive = bool;
        if (keyboardLayout.keyboardLayoutListener != null && KeyboardLayout.this.mLastKeyboardHeight != KeyboardLayout.this.keyboardHeight) {
          KeyboardLayout.this.keyboardLayoutListener.onKeyboardStateChanged(KeyboardLayout.this.isKeyboardActive, KeyboardLayout.this.keyboardHeight);
          keyboardLayout = KeyboardLayout.this;
          keyboardLayout.mLastKeyboardHeight = keyboardLayout.keyboardHeight;
        } 
      }
    };
  
  private int screenHeight;
  
  public KeyboardLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null, 0);
  }
  
  public KeyboardLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public KeyboardLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    getViewTreeObserver().addOnGlobalLayoutListener(new KeyboardOnGlobalLayoutListener());
  }
  
  public int getKeyboardHeight() {
    return this.keyboardHeight;
  }
  
  public KeyboardLayoutListener getKeyboardLayoutListener() {
    return this.keyboardLayoutListener;
  }
  
  public boolean isKeyboardActive() {
    return this.isKeyboardActive;
  }
  
  protected void onWindowVisibilityChanged(int paramInt) {
    super.onWindowVisibilityChanged(paramInt);
    if (paramInt == 8) {
      this.keyboardHeight = 0;
      this.mLastKeyboardHeight = 0;
      this.isKeyboardActive = false;
    } 
  }
  
  public void setKeyboardLayoutListener(KeyboardLayoutListener paramKeyboardLayoutListener) {
    this.keyboardLayoutListener = paramKeyboardLayoutListener;
  }
  
  public static interface KeyboardLayoutListener {
    void onKeyboardStateChanged(boolean param1Boolean, int param1Int);
  }
  
  class KeyboardOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private KeyboardOnGlobalLayoutListener() {}
    
    public void onGlobalLayout() {
      KeyboardLayout keyboardLayout = KeyboardLayout.this;
      keyboardLayout.postDelayed(keyboardLayout.runnable, 100L);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\keyboard\KeyboardLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */