package com.tt.miniapp.component.nativeview.picker.framework.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import com.tt.miniapphost.util.UIUtils;

public abstract class BasicPopup<V extends View> implements DialogInterface.OnDismissListener, DialogInterface.OnKeyListener {
  public Activity activity;
  
  private FrameLayout contentLayout;
  
  private Dialog dialog;
  
  private boolean isPrepared;
  
  private int mWindowHeight;
  
  protected int screenHeightPixels;
  
  protected int screenWidthPixels;
  
  public BasicPopup(Activity paramActivity) {
    this.activity = paramActivity;
    DisplayMetrics displayMetrics = paramActivity.getResources().getDisplayMetrics();
    this.screenWidthPixels = displayMetrics.widthPixels;
    this.screenHeightPixels = displayMetrics.heightPixels;
    this.mWindowHeight = (int)UIUtils.dip2Px((Context)paramActivity, 280.0F);
    initDialog();
  }
  
  private void initDialog() {
    this.contentLayout = new FrameLayout((Context)this.activity);
    this.contentLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.contentLayout.setFocusable(true);
    this.contentLayout.setFocusableInTouchMode(true);
    this.dialog = new Dialog((Context)this.activity, 2132607952);
    this.dialog.setCanceledOnTouchOutside(true);
    this.dialog.setCancelable(true);
    this.dialog.setOnKeyListener((DialogInterface.OnKeyListener)this);
    this.dialog.setOnDismissListener((DialogInterface.OnDismissListener)this);
    Window window = this.dialog.getWindow();
    if (window != null) {
      window.setGravity(80);
      window.setBackgroundDrawable((Drawable)new ColorDrawable(0));
      window.requestFeature(1);
      window.setContentView((View)this.contentLayout);
    } 
    setSize(this.screenWidthPixels, this.mWindowHeight);
  }
  
  public void dismiss() {
    dismissImmediately();
  }
  
  protected final void dismissImmediately() {
    _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(this.dialog);
  }
  
  public View getContentView() {
    return this.contentLayout.getChildAt(0);
  }
  
  public Context getContext() {
    return this.dialog.getContext();
  }
  
  public ViewGroup getRootView() {
    return (ViewGroup)this.contentLayout;
  }
  
  public int getScreenHeightPixels() {
    return this.screenHeightPixels;
  }
  
  public int getScreenWidthPixels() {
    return this.screenWidthPixels;
  }
  
  public Window getWindow() {
    return this.dialog.getWindow();
  }
  
  public boolean isShowing() {
    return this.dialog.isShowing();
  }
  
  protected abstract V makeContentView();
  
  public boolean onBackPress() {
    dismiss();
    return false;
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    dismiss();
  }
  
  public final boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getAction() == 0 && paramInt == 4)
      onBackPress(); 
    return false;
  }
  
  public void setAnimationStyle(int paramInt) {
    Window window = this.dialog.getWindow();
    if (window != null)
      window.setWindowAnimations(paramInt); 
  }
  
  public void setCancelable(boolean paramBoolean) {
    this.dialog.setCancelable(paramBoolean);
  }
  
  public void setCanceledOnTouchOutside(boolean paramBoolean) {
    this.dialog.setCanceledOnTouchOutside(paramBoolean);
  }
  
  public void setContentView(View paramView) {
    this.contentLayout.removeAllViews();
    this.contentLayout.addView(paramView);
  }
  
  protected void setContentViewAfter(V paramV) {}
  
  protected void setContentViewBefore() {}
  
  public void setFillScreen(boolean paramBoolean) {
    if (paramBoolean)
      setSize(this.screenWidthPixels, (int)(this.screenHeightPixels * 0.85F)); 
  }
  
  public void setFitsSystemWindows(boolean paramBoolean) {
    this.contentLayout.setFitsSystemWindows(paramBoolean);
  }
  
  public void setGravity(int paramInt) {
    Window window = this.dialog.getWindow();
    if (window != null)
      window.setGravity(paramInt); 
    if (paramInt == 17)
      setWidth((int)(this.screenWidthPixels * 0.7F)); 
  }
  
  public void setHalfScreen(boolean paramBoolean) {
    if (paramBoolean)
      setSize(this.screenWidthPixels, this.screenHeightPixels / 2); 
  }
  
  public void setHeight(int paramInt) {
    setSize(0, paramInt);
  }
  
  public void setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
    this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public void onDismiss(DialogInterface param1DialogInterface) {
            BasicPopup.this.onDismiss(param1DialogInterface);
            onDismissListener.onDismiss(param1DialogInterface);
          }
        });
  }
  
  public void setOnKeyListener(final DialogInterface.OnKeyListener onKeyListener) {
    this.dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
          public boolean onKey(DialogInterface param1DialogInterface, int param1Int, KeyEvent param1KeyEvent) {
            BasicPopup.this.onKey(param1DialogInterface, param1Int, param1KeyEvent);
            return onKeyListener.onKey(param1DialogInterface, param1Int, param1KeyEvent);
          }
        });
  }
  
  public void setPrepared(boolean paramBoolean) {
    this.isPrepared = paramBoolean;
  }
  
  public void setSize(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: istore_3
    //   2: iload_1
    //   3: iconst_m1
    //   4: if_icmpne -> 12
    //   7: aload_0
    //   8: getfield screenWidthPixels : I
    //   11: istore_3
    //   12: iload_3
    //   13: ifne -> 32
    //   16: iload_2
    //   17: ifne -> 32
    //   20: aload_0
    //   21: getfield screenWidthPixels : I
    //   24: istore_1
    //   25: bipush #-2
    //   27: istore #4
    //   29: goto -> 61
    //   32: iload_3
    //   33: ifne -> 47
    //   36: aload_0
    //   37: getfield screenWidthPixels : I
    //   40: istore_1
    //   41: iload_2
    //   42: istore #4
    //   44: goto -> 61
    //   47: iload_3
    //   48: istore_1
    //   49: iload_2
    //   50: istore #4
    //   52: iload_2
    //   53: ifne -> 61
    //   56: iload_3
    //   57: istore_1
    //   58: goto -> 25
    //   61: aload_0
    //   62: getfield contentLayout : Landroid/widget/FrameLayout;
    //   65: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   68: astore #5
    //   70: aload #5
    //   72: ifnonnull -> 90
    //   75: new android/view/ViewGroup$LayoutParams
    //   78: dup
    //   79: iload_1
    //   80: iload #4
    //   82: invokespecial <init> : (II)V
    //   85: astore #5
    //   87: goto -> 103
    //   90: aload #5
    //   92: iload_1
    //   93: putfield width : I
    //   96: aload #5
    //   98: iload #4
    //   100: putfield height : I
    //   103: aload_0
    //   104: getfield contentLayout : Landroid/widget/FrameLayout;
    //   107: aload #5
    //   109: invokevirtual setLayoutParams : (Landroid/view/ViewGroup$LayoutParams;)V
    //   112: return
  }
  
  public void setWidth(int paramInt) {
    setSize(paramInt, 0);
  }
  
  public final void show() {
    if (this.isPrepared && !this.activity.isFinishing()) {
      this.dialog.show();
      showAfter();
      return;
    } 
    setContentViewBefore();
    V v = makeContentView();
    setContentView((View)v);
    setContentViewAfter(v);
    this.isPrepared = true;
    this.dialog.show();
    showAfter();
  }
  
  protected void showAfter() {}
  
  class BasicPopup {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\framework\popup\BasicPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */