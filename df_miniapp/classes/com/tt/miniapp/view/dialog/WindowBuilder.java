package com.tt.miniapp.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import com.tt.miniapp.util.WeakHandler;
import java.lang.ref.WeakReference;

public abstract class WindowBuilder implements DialogInterface, View.OnKeyListener, View.OnTouchListener, WeakHandler.IHandler {
  public WeakReference<View> mAttachView;
  
  private boolean mBackDisable;
  
  private boolean mBlockDismiss;
  
  private boolean mCancelable;
  
  private WeakReference<Context> mContextRef;
  
  private boolean mCreated;
  
  private WeakHandler mHandler;
  
  private final boolean mNoToken;
  
  private DialogInterface.OnCancelListener mOnCancelListener;
  
  private DialogInterface.OnDismissListener mOnDismissListener;
  
  private DialogInterface.OnKeyListener mOnKeyListener;
  
  private DialogInterface.OnShowListener mOnShowListener;
  
  public Runnable mPendingShowRunnable;
  
  protected ViewGroup mRootView;
  
  public IBinder mToken;
  
  protected WindowBase mWindowBase;
  
  public WindowBuilder() {
    this((View)null);
  }
  
  public WindowBuilder(Activity paramActivity) {
    this(paramActivity.getWindow().getDecorView());
  }
  
  public WindowBuilder(View paramView) {
    boolean bool;
    IBinder iBinder;
    this.mHandler = new WeakHandler(this);
    if (paramView == null) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mNoToken = bool;
    if (this.mNoToken) {
      iBinder = null;
    } else {
      iBinder = paramView.getWindowToken();
    } 
    this.mToken = iBinder;
    if (this.mToken == null && !this.mNoToken) {
      this.mAttachView = new WeakReference<View>(paramView);
      paramView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
              View view = WindowBuilder.this.mAttachView.get();
              if (view == null)
                return; 
              WindowBuilder.this.mAttachView.clear();
              WindowBuilder.this.mAttachView = null;
              view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
              if (WindowBuilder.this.mToken == null)
                WindowBuilder.this.mToken = view.getWindowToken(); 
              if (WindowBuilder.this.mToken != null) {
                if (WindowBuilder.this.mPendingShowRunnable == null)
                  return; 
                if (!WindowBuilder.this.isShowing())
                  WindowBuilder.this.mPendingShowRunnable.run(); 
                WindowBuilder.this.mPendingShowRunnable = null;
              } 
            }
          });
    } 
    if (paramView != null)
      this.mContextRef = new WeakReference<Context>(paramView.getContext()); 
    initBuilder();
  }
  
  public static void closeSelf(WindowBuilder paramWindowBuilder) {
    if (paramWindowBuilder != null && paramWindowBuilder.isShowing())
      paramWindowBuilder.close(); 
  }
  
  private void initBuilder() {
    this.mWindowBase = getWindowBase();
    if (this.mWindowBase != null) {
      this.mRootView = getRootView();
      ViewGroup viewGroup = this.mRootView;
      if (viewGroup != null) {
        viewGroup.setFocusableInTouchMode(true);
        this.mRootView.setOnKeyListener(this);
        this.mRootView.setOnTouchListener(this);
        return;
      } 
      throw new NullPointerException("getRootView() can't return null");
    } 
    throw new NullPointerException("getWindowBase() can't return null");
  }
  
  public void cancel() {
    this.mHandler.sendEmptyMessage(68);
    close();
  }
  
  public void close() {
    close(-1);
  }
  
  public void close(int paramInt) {
    dispatchDismissEvent(paramInt);
    if (!this.mBlockDismiss) {
      this.mWindowBase.remove();
      return;
    } 
    this.mBlockDismiss = false;
  }
  
  public void dismiss() {
    close();
  }
  
  public void dispatchDismissEvent(int paramInt) {
    this.mHandler.sendEmptyMessage(67);
    onDismissEvent(paramInt);
  }
  
  protected void dispatchOnCreate(Bundle paramBundle) {
    if (!this.mCreated) {
      onCreate(paramBundle);
      this.mCreated = true;
    } 
  }
  
  public View findViewById(int paramInt) {
    return this.mRootView.findViewById(paramInt);
  }
  
  public Context getContext() {
    WeakReference<Context> weakReference = this.mContextRef;
    return (weakReference != null) ? weakReference.get() : null;
  }
  
  public abstract ViewGroup getRootView();
  
  public abstract WindowBase getWindowBase();
  
  public WindowManager getWindowManager() {
    WindowBase windowBase = this.mWindowBase;
    return (windowBase != null) ? windowBase.getWindowManager() : null;
  }
  
  public void handleMsg(Message paramMessage) {
    DialogInterface.OnShowListener onShowListener;
    DialogInterface.OnCancelListener onCancelListener;
    switch (paramMessage.what) {
      default:
        return;
      case 69:
        onShowListener = this.mOnShowListener;
        if (onShowListener != null) {
          onShowListener.onShow(this);
          return;
        } 
        return;
      case 68:
        onCancelListener = this.mOnCancelListener;
        if (onCancelListener != null) {
          onCancelListener.onCancel(this);
          return;
        } 
        return;
      case 67:
        break;
    } 
    DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
    if (onDismissListener != null)
      onDismissListener.onDismiss(this); 
  }
  
  public boolean isShowing() {
    return this.mWindowBase.isShowing();
  }
  
  public boolean onBackPressed() {
    return false;
  }
  
  protected void onCreate(Bundle paramBundle) {}
  
  public void onDismissEvent(int paramInt) {}
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {
    if (this.mBackDisable)
      return false; 
    DialogInterface.OnKeyListener onKeyListener = this.mOnKeyListener;
    if (onKeyListener != null && onKeyListener.onKey(this, paramInt, paramKeyEvent))
      return true; 
    if (paramInt == 4 && paramKeyEvent.getAction() == 0) {
      if (!onBackPressed())
        close(-2); 
      return true;
    } 
    return false;
  }
  
  protected void onPreShow() {
    if (!this.mCreated)
      dispatchOnCreate(null); 
  }
  
  protected void onShow() {
    this.mHandler.sendEmptyMessage(69);
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    if (!this.mCancelable)
      return false; 
    int i = (int)paramMotionEvent.getX();
    int j = (int)paramMotionEvent.getY();
    if (paramMotionEvent.getAction() == 0 && (i < 0 || i >= this.mRootView.getWidth() || j < 0 || j >= this.mRootView.getHeight())) {
      close(-3);
      return true;
    } 
    if (paramMotionEvent.getAction() == 4) {
      close(-3);
      return true;
    } 
    return false;
  }
  
  public void requestBlockDismiss() {
    this.mBlockDismiss = true;
  }
  
  public void setBackDisable(boolean paramBoolean) {
    this.mBackDisable = paramBoolean;
  }
  
  public void setCancelable(boolean paramBoolean) {
    this.mCancelable = paramBoolean;
  }
  
  public void setCanceledOnTouchOutside(boolean paramBoolean) {
    this.mCancelable = paramBoolean;
  }
  
  public void setFlags(int paramInt1, int paramInt2) {
    WindowManager.LayoutParams layoutParams = this.mWindowBase.getLayoutParams();
    layoutParams.flags = paramInt1 & paramInt2 | layoutParams.flags & (paramInt2 ^ 0xFFFFFFFF);
  }
  
  public void setOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener) {
    this.mOnCancelListener = paramOnCancelListener;
  }
  
  public void setOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setOnKeyListener(DialogInterface.OnKeyListener paramOnKeyListener) {
    this.mOnKeyListener = paramOnKeyListener;
  }
  
  public void setOnShowListener(DialogInterface.OnShowListener paramOnShowListener) {
    this.mOnShowListener = paramOnShowListener;
  }
  
  public void show() {
    show(0, 0);
  }
  
  public void show(final int x, final int y) {
    if (this.mNoToken || this.mToken != null) {
      onPreShow();
      this.mWindowBase.show((View)this.mRootView, x, y, this.mToken);
      onShow();
      return;
    } 
    this.mPendingShowRunnable = new Runnable() {
        public void run() {
          WindowBuilder.this.onPreShow();
          WindowBuilder.this.mWindowBase.show((View)WindowBuilder.this.mRootView, x, y, WindowBuilder.this.mToken);
          WindowBuilder.this.onShow();
        }
      };
  }
  
  public void startAnimation(int paramInt, Animation paramAnimation) {
    this.mRootView.findViewById(paramInt).startAnimation(paramAnimation);
  }
  
  public void update(int paramInt1, int paramInt2) {
    this.mWindowBase.update(paramInt1, paramInt2);
  }
  
  public void update(WindowManager.LayoutParams paramLayoutParams) {
    this.mWindowBase.update(paramLayoutParams);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\WindowBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */