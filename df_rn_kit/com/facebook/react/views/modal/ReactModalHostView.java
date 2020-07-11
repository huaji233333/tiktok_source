package com.facebook.react.views.modal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import com.facebook.i.a.a;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.view.ReactViewGroup;
import java.util.ArrayList;

public class ReactModalHostView extends ViewGroup implements LifecycleEventListener {
  private String mAnimationType;
  
  private Dialog mDialog;
  
  private boolean mHardwareAccelerated;
  
  private DialogRootViewGroup mHostView;
  
  public OnRequestCloseListener mOnRequestCloseListener;
  
  private DialogInterface.OnShowListener mOnShowListener;
  
  private boolean mPropertyRequiresNewDialog;
  
  private boolean mTransparent;
  
  public ReactModalHostView(Context paramContext) {
    super(paramContext);
    ((ReactContext)paramContext).addLifecycleEventListener((LifecycleEventListener)this);
    this.mHostView = new DialogRootViewGroup(paramContext);
  }
  
  private void dismiss() {
    if (this.mDialog != null) {
      Activity activity = getCurrentActivity();
      if (this.mDialog.isShowing() && (activity == null || !activity.isFinishing()))
        _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(this.mDialog); 
      this.mDialog = null;
      ((ViewGroup)this.mHostView.getParent()).removeViewAt(0);
    } 
  }
  
  private View getContentView() {
    FrameLayout frameLayout = new FrameLayout(getContext());
    frameLayout.addView((View)this.mHostView);
    frameLayout.setFitsSystemWindows(true);
    return (View)frameLayout;
  }
  
  private Activity getCurrentActivity() {
    return ((ReactContext)getContext()).getCurrentActivity();
  }
  
  private void updateProperties() {
    a.a(this.mDialog, "mDialog must exist when we call updateProperties");
    if (this.mTransparent) {
      this.mDialog.getWindow().clearFlags(2);
      return;
    } 
    this.mDialog.getWindow().setDimAmount(0.5F);
    this.mDialog.getWindow().setFlags(2, 2);
  }
  
  public void addChildrenForAccessibility(ArrayList<View> paramArrayList) {}
  
  public void addView(View paramView, int paramInt) {
    this.mHostView.addView(paramView, paramInt);
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    return false;
  }
  
  public View getChildAt(int paramInt) {
    return this.mHostView.getChildAt(paramInt);
  }
  
  public int getChildCount() {
    return this.mHostView.getChildCount();
  }
  
  public Dialog getDialog() {
    return this.mDialog;
  }
  
  public void onDropInstance() {
    ((ReactContext)getContext()).removeLifecycleEventListener((LifecycleEventListener)this);
    dismiss();
  }
  
  public void onHostDestroy() {
    onDropInstance();
  }
  
  public void onHostPause() {}
  
  public void onHostResume() {
    showOrUpdate();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void removeView(View paramView) {
    this.mHostView.removeView(paramView);
  }
  
  public void removeViewAt(int paramInt) {
    View view = getChildAt(paramInt);
    this.mHostView.removeView(view);
  }
  
  protected void setAnimationType(String paramString) {
    this.mAnimationType = paramString;
    this.mPropertyRequiresNewDialog = true;
  }
  
  protected void setHardwareAccelerated(boolean paramBoolean) {
    this.mHardwareAccelerated = paramBoolean;
    this.mPropertyRequiresNewDialog = true;
  }
  
  protected void setOnRequestCloseListener(OnRequestCloseListener paramOnRequestCloseListener) {
    this.mOnRequestCloseListener = paramOnRequestCloseListener;
  }
  
  protected void setOnShowListener(DialogInterface.OnShowListener paramOnShowListener) {
    this.mOnShowListener = paramOnShowListener;
  }
  
  protected void setTransparent(boolean paramBoolean) {
    this.mTransparent = paramBoolean;
  }
  
  protected void showOrUpdate() {
    Activity activity1;
    if (this.mDialog != null)
      if (this.mPropertyRequiresNewDialog) {
        dismiss();
      } else {
        updateProperties();
        return;
      }  
    this.mPropertyRequiresNewDialog = false;
    int i = 1980170254;
    if (this.mAnimationType.equals("fade")) {
      i = 1980170255;
    } else if (this.mAnimationType.equals("slide")) {
      i = 1980170256;
    } 
    Activity activity2 = getCurrentActivity();
    if (activity2 == null) {
      Context context = getContext();
    } else {
      activity1 = activity2;
    } 
    this.mDialog = new Dialog((Context)activity1, i);
    this.mDialog.setContentView(getContentView());
    updateProperties();
    this.mDialog.setOnShowListener(this.mOnShowListener);
    this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
          public boolean onKey(DialogInterface param1DialogInterface, int param1Int, KeyEvent param1KeyEvent) {
            if (param1KeyEvent.getAction() == 1) {
              if (param1Int == 4) {
                a.a(ReactModalHostView.this.mOnRequestCloseListener, "setOnRequestCloseListener must be called by the manager");
                ReactModalHostView.this.mOnRequestCloseListener.onRequestClose(param1DialogInterface);
                return true;
              } 
              Activity activity = ((ReactContext)ReactModalHostView.this.getContext()).getCurrentActivity();
              if (activity != null)
                return activity.onKeyUp(param1Int, param1KeyEvent); 
            } 
            return false;
          }
        });
    this.mDialog.getWindow().setSoftInputMode(16);
    if (this.mHardwareAccelerated)
      this.mDialog.getWindow().addFlags(16777216); 
    if (activity2 == null || !activity2.isFinishing())
      this.mDialog.show(); 
  }
  
  static class DialogRootViewGroup extends ReactViewGroup implements RootView {
    private final JSTouchDispatcher mJSTouchDispatcher = new JSTouchDispatcher((ViewGroup)this);
    
    public DialogRootViewGroup(Context param1Context) {
      super(param1Context);
    }
    
    private EventDispatcher getEventDispatcher() {
      return ((UIManagerModule)getReactContext().getNativeModule(UIManagerModule.class)).getEventDispatcher();
    }
    
    public ReactContext getReactContext() {
      return (ReactContext)getContext();
    }
    
    public void handleException(Throwable param1Throwable) {
      getReactContext().handleException(new RuntimeException(param1Throwable));
    }
    
    public void onChildStartedNativeGesture(MotionEvent param1MotionEvent) {
      this.mJSTouchDispatcher.onChildStartedNativeGesture(param1MotionEvent, getEventDispatcher());
    }
    
    public boolean onInterceptTouchEvent(MotionEvent param1MotionEvent) {
      this.mJSTouchDispatcher.handleTouchEvent(param1MotionEvent, getEventDispatcher());
      return super.onInterceptTouchEvent(param1MotionEvent);
    }
    
    public void onSizeChanged(final int w, final int h, final int viewTag, int param1Int4) {
      super.onSizeChanged(w, h, viewTag, param1Int4);
      if (getChildCount() > 0) {
        viewTag = getChildAt(0).getId();
        ReactContext reactContext = getReactContext();
        reactContext.runOnLayoutThread((Runnable)new GuardedRunnable(reactContext) {
              public void runGuarded() {
                ((UIManagerModule)ReactModalHostView.DialogRootViewGroup.this.getReactContext().getNativeModule(UIManagerModule.class)).updateNodeSize(viewTag, w, h);
              }
            });
      } 
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      this.mJSTouchDispatcher.handleTouchEvent(param1MotionEvent, getEventDispatcher());
      super.onTouchEvent(param1MotionEvent);
      return true;
    }
    
    public void requestDisallowInterceptTouchEvent(boolean param1Boolean) {}
  }
  
  class null extends GuardedRunnable {
    null(ReactContext param1ReactContext) {
      super(param1ReactContext);
    }
    
    public void runGuarded() {
      ((UIManagerModule)this.this$0.getReactContext().getNativeModule(UIManagerModule.class)).updateNodeSize(viewTag, w, h);
    }
  }
  
  public static interface OnRequestCloseListener {
    void onRequestClose(DialogInterface param1DialogInterface);
  }
  
  class ReactModalHostView {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\ReactModalHostView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */