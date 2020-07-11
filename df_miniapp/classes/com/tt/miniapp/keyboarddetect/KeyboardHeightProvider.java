package com.tt.miniapp.keyboarddetect;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import com.tt.frontendapiinterface.k;
import com.tt.miniapp.util.ConcaveScreenUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;

public class KeyboardHeightProvider extends PopupWindow {
  private static int keyboardLandscapeHeight;
  
  private static int keyboardPortraitHeight;
  
  public static int mkeyboardId;
  
  private Activity activity;
  
  private int mLastChangeHeight;
  
  private long mLastChangeTime;
  
  private k observer;
  
  private View parentView;
  
  public View popupView;
  
  public KeyboardHeightProvider(Activity paramActivity) {
    super((Context)paramActivity);
    this.activity = paramActivity;
    try {
      Activity activity;
      if (paramActivity instanceof com.tt.miniapphost.MiniappHostBase) {
        Application application = AppbrandContext.getInst().getApplicationContext();
      } else {
        activity = paramActivity;
      } 
      this.popupView = LayoutInflater.from((Context)activity).inflate(2097676341, null, false);
      setContentView(this.popupView);
    } finally {
      Exception exception = null;
      AppBrandLogger.e("tma_sample_KeyboardHeightProvider", new Object[] { exception });
    } 
    this.parentView = paramActivity.findViewById(16908290);
    setWidth(0);
    setHeight(-1);
    View view = this.popupView;
    if (view != null)
      view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
              if (KeyboardHeightProvider.this.popupView != null)
                KeyboardHeightProvider.this.handleOnGlobalLayout(); 
            }
          }); 
  }
  
  public static int getKeyboardHeight() {
    int i = (AppbrandContext.getInst().getCurrentActivity().getResources().getConfiguration()).orientation;
    return (i == 1) ? keyboardPortraitHeight : ((i == 2) ? keyboardLandscapeHeight : 0);
  }
  
  private int getScreenOrientation() {
    return (this.activity.getResources().getConfiguration()).orientation;
  }
  
  private void notifyKeyboardHeightChanged(int paramInt1, int paramInt2) {
    k k1 = this.observer;
    if (k1 != null)
      k1.onKeyboardHeightChanged(paramInt1, paramInt2); 
  }
  
  public void close() {
    this.observer = null;
    dismiss();
  }
  
  public void handleOnGlobalLayout() {
    int i;
    Point point = new Point();
    this.activity.getWindowManager().getDefaultDisplay().getSize(point);
    Rect rect = new Rect();
    this.popupView.getWindowVisibleDisplayFrame(rect);
    AppBrandLogger.d("tma_sample_KeyboardHeightProvider", new Object[] { "popupView.getHeight() ", Integer.valueOf(this.popupView.getHeight()), " rect top", Integer.valueOf(rect.top), " rect bottom", Integer.valueOf(rect.bottom), " screenSize.y ", Integer.valueOf(point.y) });
    int m = getScreenOrientation();
    int j = point.y - rect.bottom;
    if (ConcaveScreenUtils.isVivoConcaveScreen()) {
      if (j == 0) {
        i = 120;
      } else {
        i = j;
        if (Math.abs(j - this.mLastChangeHeight) < 20)
          return; 
      } 
    } else {
      i = j;
      if (Math.abs(j - this.mLastChangeHeight) < 20)
        return; 
    } 
    AppBrandLogger.d("tma_sample_KeyboardHeightProvider", new Object[] { "keyboardHeight ", Integer.valueOf(i), " mLastChangeHeight ", Integer.valueOf(this.mLastChangeHeight) });
    if (this.mLastChangeHeight == Math.abs(i) && System.currentTimeMillis() - this.mLastChangeTime < 100L)
      return; 
    this.mLastChangeHeight = Math.abs(i);
    if (m == 1) {
      keyboardPortraitHeight = i;
      notifyKeyboardHeightChanged(keyboardPortraitHeight, m);
    } else {
      keyboardLandscapeHeight = i;
      notifyKeyboardHeightChanged(keyboardLandscapeHeight, m);
    } 
    this.mLastChangeTime = System.currentTimeMillis();
  }
  
  public void setKeyboardHeightObserver(k paramk) {
    this.observer = paramk;
  }
  
  public void start() {
    if (!this.activity.isFinishing()) {
      if (this.activity.isDestroyed())
        return; 
      if (!isShowing() && this.parentView.getWindowToken() != null) {
        setBackgroundDrawable((Drawable)new ColorDrawable(0));
        try {
          showAtLocation(this.parentView, 0, 0, 0);
          return;
        } catch (Exception exception) {
          AppBrandLogger.eWithThrowable("tma_sample_KeyboardHeightProvider", "start", exception);
        } 
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\keyboarddetect\KeyboardHeightProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */