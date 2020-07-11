package com.facebook.react.devsupport;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.facebook.common.e.a;
import com.facebook.react.bridge.ReactContext;

class DebugOverlayController {
  private FrameLayout mFPSDebugViewContainer;
  
  private final ReactContext mReactContext;
  
  private final WindowManager mWindowManager;
  
  public DebugOverlayController(ReactContext paramReactContext) {
    this.mReactContext = paramReactContext;
    this.mWindowManager = (WindowManager)paramReactContext.getSystemService("window");
  }
  
  private static boolean canHandleIntent(Context paramContext, Intent paramIntent) {
    return (paramIntent.resolveActivity(paramContext.getPackageManager()) != null);
  }
  
  private static boolean hasPermission(Context paramContext, String paramString) {
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 4096);
      if (packageInfo.requestedPermissions != null) {
        String[] arrayOfString = packageInfo.requestedPermissions;
        int j = arrayOfString.length;
        for (int i = 0; i < j; i++) {
          boolean bool = arrayOfString[i].equals(paramString);
          if (bool)
            return true; 
        } 
      } 
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      a.c("ReactNative", "Error while retrieving package info", (Throwable)nameNotFoundException);
    } 
    return false;
  }
  
  private static boolean permissionCheck(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 23) ? (!!Settings.canDrawOverlays(paramContext)) : hasPermission(paramContext, "android.permission.SYSTEM_ALERT_WINDOW");
  }
  
  public static void requestPermission(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(paramContext)) {
      StringBuilder stringBuilder = new StringBuilder("package:");
      stringBuilder.append(paramContext.getPackageName());
      Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse(stringBuilder.toString()));
      intent.setFlags(268435456);
      a.b("ReactNative", "Overlay permissions needs to be granted in order for react native apps to run in dev mode");
      if (canHandleIntent(paramContext, intent))
        paramContext.startActivity(intent); 
    } 
  }
  
  public void setFpsDebugViewVisible(boolean paramBoolean) {
    if (paramBoolean && this.mFPSDebugViewContainer == null) {
      if (!permissionCheck((Context)this.mReactContext)) {
        a.a("ReactNative", "Wait for overlay permission to be set");
        return;
      } 
      this.mFPSDebugViewContainer = new FpsView(this.mReactContext);
      WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, WindowOverlayCompat.TYPE_SYSTEM_OVERLAY, 24, -3);
      this.mWindowManager.addView((View)this.mFPSDebugViewContainer, (ViewGroup.LayoutParams)layoutParams);
      return;
    } 
    if (!paramBoolean) {
      FrameLayout frameLayout = this.mFPSDebugViewContainer;
      if (frameLayout != null) {
        frameLayout.removeAllViews();
        this.mWindowManager.removeView((View)this.mFPSDebugViewContainer);
        this.mFPSDebugViewContainer = null;
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DebugOverlayController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */