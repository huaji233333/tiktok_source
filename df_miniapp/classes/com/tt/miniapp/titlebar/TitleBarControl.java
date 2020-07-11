package com.tt.miniapp.titlebar;

import android.content.Context;
import android.view.MotionEvent;
import com.tt.miniapp.base.utils.DensityUtil;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class TitleBarControl {
  private static TitleBarControl mInstance = new TitleBarControl();
  
  public static final ArrayList<WeakReference<BaseTitleBar>> sTitleBarList = new ArrayList<WeakReference<BaseTitleBar>>();
  
  private boolean isShowCapsuleButton = true;
  
  private boolean isShowStatusBar = true;
  
  public static TitleBarControl getInst() {
    return mInstance;
  }
  
  public static void showTitlebarNormal(final BaseTitleBar titleBar) {
    if (titleBar == null)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            titleBar.setTitleBarRadius(0.0F, 0.0F, 0.0F, 0.0F);
          }
        });
  }
  
  public static void showTitlebarRadius(final BaseTitleBar titleBar) {
    if (titleBar == null)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            float f = DensityUtil.dip2px((Context)AppbrandContext.getInst().getApplicationContext(), 8.0F);
            titleBar.setTitleBarRadius(f, f, 0.0F, 0.0F);
          }
        });
  }
  
  public void addBaseTitleBar(BaseTitleBar paramBaseTitleBar) {
    sTitleBarList.add(new WeakReference<BaseTitleBar>(paramBaseTitleBar));
  }
  
  public void forceSetCapsuleButtonState(boolean paramBoolean) {
    this.isShowCapsuleButton = paramBoolean;
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      IActivityProxy iActivityProxy = miniappHostBase.getActivityProxy();
      if (iActivityProxy != null) {
        ITitleBar iTitleBar = iActivityProxy.getTitleBar();
        if (iTitleBar != null)
          iTitleBar.setTitleBarCapsuleVisible(paramBoolean); 
      } 
    } 
  }
  
  public boolean isShowCapsuleButton() {
    return this.isShowCapsuleButton;
  }
  
  public boolean isShowStatusBar() {
    return this.isShowStatusBar;
  }
  
  public boolean isTouchPointInCurrentTitleBar(MotionEvent paramMotionEvent) {
    for (int i = sTitleBarList.size() - 1; i >= 0; i--) {
      BaseTitleBar baseTitleBar = ((WeakReference<BaseTitleBar>)sTitleBarList.get(i)).get();
      if (baseTitleBar != null && baseTitleBar.isVisible())
        return baseTitleBar.isTouchPointInTitleBar(paramMotionEvent); 
    } 
    return false;
  }
  
  public void removeBaseTitleBar(BaseTitleBar paramBaseTitleBar) {
    for (WeakReference<BaseTitleBar> weakReference : sTitleBarList) {
      if ((BaseTitleBar)weakReference.get() == paramBaseTitleBar) {
        sTitleBarList.remove(weakReference);
        break;
      } 
    } 
  }
  
  public void updateCapsuleButtonState(boolean paramBoolean) {
    this.isShowCapsuleButton = paramBoolean;
    Iterator<WeakReference<BaseTitleBar>> iterator = sTitleBarList.iterator();
    while (iterator.hasNext()) {
      BaseTitleBar baseTitleBar = ((WeakReference<BaseTitleBar>)iterator.next()).get();
      if (baseTitleBar != null)
        baseTitleBar.updateCapsuleButtonVisible(paramBoolean); 
    } 
  }
  
  public void updateStatusBarState(boolean paramBoolean) {
    this.isShowStatusBar = paramBoolean;
    Iterator<WeakReference<BaseTitleBar>> iterator = sTitleBarList.iterator();
    while (iterator.hasNext()) {
      BaseTitleBar baseTitleBar = ((WeakReference<BaseTitleBar>)iterator.next()).get();
      if (baseTitleBar != null)
        baseTitleBar.updateStatusBarVisible(paramBoolean); 
    } 
  }
  
  public void updateTitlebarRadius(boolean paramBoolean) {
    Iterator<WeakReference<BaseTitleBar>> iterator = sTitleBarList.iterator();
    while (iterator.hasNext()) {
      BaseTitleBar baseTitleBar = ((WeakReference<BaseTitleBar>)iterator.next()).get();
      if (baseTitleBar != null) {
        if (paramBoolean) {
          showTitlebarRadius(baseTitleBar);
          continue;
        } 
        showTitlebarNormal(baseTitleBar);
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlebar\TitleBarControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */