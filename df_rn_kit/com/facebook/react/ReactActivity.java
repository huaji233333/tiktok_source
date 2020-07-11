package com.facebook.react;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

public abstract class ReactActivity extends Activity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {
  private final ReactActivityDelegate mDelegate = createReactActivityDelegate();
  
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new ReactActivityDelegate(this, getMainComponentName());
  }
  
  protected String getMainComponentName() {
    return null;
  }
  
  protected final ReactInstanceManager getReactInstanceManager() {
    return this.mDelegate.getReactInstanceManager();
  }
  
  protected final ReactNativeHost getReactNativeHost() {
    return this.mDelegate.getReactNativeHost();
  }
  
  public void invokeDefaultOnBackPressed() {
    super.onBackPressed();
  }
  
  protected final void loadApp(String paramString) {
    this.mDelegate.loadApp(paramString);
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    this.mDelegate.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed() {
    if (!this.mDelegate.onBackPressed())
      super.onBackPressed(); 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.mDelegate.onCreate(paramBundle);
  }
  
  protected void onDestroy() {
    super.onDestroy();
    this.mDelegate.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return (this.mDelegate.onKeyDown(paramInt, paramKeyEvent) || super.onKeyDown(paramInt, paramKeyEvent));
  }
  
  public boolean onKeyLongPress(int paramInt, KeyEvent paramKeyEvent) {
    return (this.mDelegate.onKeyLongPress(paramInt, paramKeyEvent) || super.onKeyLongPress(paramInt, paramKeyEvent));
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    return (this.mDelegate.onKeyUp(paramInt, paramKeyEvent) || super.onKeyUp(paramInt, paramKeyEvent));
  }
  
  public void onNewIntent(Intent paramIntent) {
    if (!this.mDelegate.onNewIntent(paramIntent))
      super.onNewIntent(paramIntent); 
  }
  
  protected void onPause() {
    super.onPause();
    this.mDelegate.onPause();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    this.mDelegate.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
  }
  
  protected void onResume() {
    super.onResume();
    this.mDelegate.onResume();
  }
  
  public void requestPermissions(String[] paramArrayOfString, int paramInt, PermissionListener paramPermissionListener) {
    this.mDelegate.requestPermissions(paramArrayOfString, paramInt, paramPermissionListener);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */