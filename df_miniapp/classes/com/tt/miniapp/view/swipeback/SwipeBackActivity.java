package com.tt.miniapp.view.swipeback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.tt.frontendapiinterface.k;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapphost.util.UIUtils;
import com.tt.miniapphost.view.BaseActivity;

public class SwipeBackActivity extends BaseActivity implements k {
  public boolean isKeyBoardShow;
  
  private int mDefaultFragmentBackground;
  
  private SwipeBackLayout mSwipeBackLayout;
  
  public KeyboardHeightProvider provider;
  
  public View findViewById(int paramInt) {
    View view = super.findViewById(paramInt);
    if (view == null) {
      SwipeBackLayout swipeBackLayout = this.mSwipeBackLayout;
      if (swipeBackLayout != null)
        return swipeBackLayout.findViewById(paramInt); 
    } 
    return view;
  }
  
  public void finish() {
    super.finish();
  }
  
  int getDefaultFragmentBackground() {
    return this.mDefaultFragmentBackground;
  }
  
  public SwipeBackLayout getSwipeBackLayout() {
    return this.mSwipeBackLayout;
  }
  
  void onActivityCreate() {
    getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
    getWindow().getDecorView().setBackgroundDrawable(null);
    this.mSwipeBackLayout = new SwipeBackLayout((Context)this);
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
    this.mSwipeBackLayout.setLayoutParams(layoutParams);
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed() {
    finish();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    onActivityCreate();
    this.provider = new KeyboardHeightProvider((Activity)this);
    this.provider.setKeyboardHeightObserver(this);
    this.mSwipeBackLayout.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            if (SwipeBackActivity.this.isKeyBoardShow)
              InputMethodUtil.hideSoftKeyboard((Activity)SwipeBackActivity.this); 
            return false;
          }
        });
    if (Build.VERSION.SDK_INT != 26)
      UIUtils.setActivityOrientation((Activity)this, 1); 
  }
  
  public void onDestroy() {
    KeyboardHeightProvider keyboardHeightProvider = this.provider;
    if (keyboardHeightProvider != null)
      keyboardHeightProvider.close(); 
    super.onDestroy();
  }
  
  public void onKeyboardHeightChanged(int paramInt1, int paramInt2) {
    if (paramInt1 > 0) {
      this.isKeyBoardShow = true;
      return;
    } 
    this.isKeyBoardShow = false;
  }
  
  public void onNewIntent(Intent paramIntent) {}
  
  public void onPostCreate(Bundle paramBundle) {
    super.onPostCreate(paramBundle);
    this.mSwipeBackLayout.attachToActivity((FragmentActivity)this);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {}
  
  public void onResume() {
    super.onResume();
    getWindow().getDecorView().post(new Runnable() {
          public void run() {
            SwipeBackActivity.this.provider.start();
          }
        });
    View view = getSwipeBackLayout().getChildAt(0);
    if (view != null && view.getX() > 0.0F)
      view.layout(0, view.getTop(), view.getRight(), view.getBottom()); 
  }
  
  protected void setDefaultFragmentBackground(int paramInt) {
    this.mDefaultFragmentBackground = paramInt;
  }
  
  protected void setEdgeLevel(int paramInt) {
    this.mSwipeBackLayout.setEdgeLevel(paramInt);
  }
  
  protected void setEdgeLevel(SwipeBackLayout.EdgeLevel paramEdgeLevel) {
    this.mSwipeBackLayout.setEdgeLevel(paramEdgeLevel);
  }
  
  public void setSwipeBackEnable(boolean paramBoolean) {
    this.mSwipeBackLayout.setEnableGesture(paramBoolean);
  }
  
  public boolean swipeBackPriority() {
    return (getSupportFragmentManager().e() <= 1);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\swipeback\SwipeBackActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */