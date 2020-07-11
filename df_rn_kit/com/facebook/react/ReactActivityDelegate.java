package com.facebook.react;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import com.facebook.i.a.a;
import com.facebook.react.bridge.Callback;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionListener;

public class ReactActivityDelegate {
  private final Activity mActivity;
  
  private DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;
  
  private final FragmentActivity mFragmentActivity;
  
  private final String mMainComponentName;
  
  public PermissionListener mPermissionListener;
  
  private Callback mPermissionsCallback;
  
  private ReactRootView mReactRootView;
  
  public ReactActivityDelegate(Activity paramActivity, String paramString) {
    this.mActivity = paramActivity;
    this.mMainComponentName = paramString;
  }
  
  public ReactActivityDelegate(FragmentActivity paramFragmentActivity, String paramString) {
    this.mFragmentActivity = paramFragmentActivity;
    this.mMainComponentName = paramString;
  }
  
  private Context getContext() {
    Activity activity = this.mActivity;
    return (Context)((activity != null) ? activity : a.b(this.mFragmentActivity));
  }
  
  private Activity getPlainActivity() {
    return (Activity)getContext();
  }
  
  protected ReactRootView createRootView() {
    return new ReactRootView(getContext());
  }
  
  protected Bundle getLaunchOptions() {
    return null;
  }
  
  public ReactInstanceManager getReactInstanceManager() {
    return getReactNativeHost().getReactInstanceManager();
  }
  
  protected ReactNativeHost getReactNativeHost() {
    return ((ReactApplication)getPlainActivity().getApplication()).getReactNativeHost();
  }
  
  protected void loadApp(String paramString) {
    if (this.mReactRootView == null) {
      this.mReactRootView = createRootView();
      this.mReactRootView.startReactApplication(getReactNativeHost().getReactInstanceManager(), paramString, getLaunchOptions());
      getPlainActivity().setContentView((View)this.mReactRootView);
      return;
    } 
    throw new IllegalStateException("Cannot loadApp while app is already running.");
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (getReactNativeHost().hasInstance())
      getReactNativeHost().getReactInstanceManager().onActivityResult(getPlainActivity(), paramInt1, paramInt2, paramIntent); 
  }
  
  public boolean onBackPressed() {
    if (getReactNativeHost().hasInstance()) {
      getReactNativeHost().getReactInstanceManager().onBackPressed();
      return true;
    } 
    return false;
  }
  
  protected void onCreate(Bundle paramBundle) {
    String str = this.mMainComponentName;
    if (str != null)
      loadApp(str); 
    this.mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
  }
  
  protected void onDestroy() {
    ReactRootView reactRootView = this.mReactRootView;
    if (reactRootView != null) {
      reactRootView.unmountReactApplication();
      this.mReactRootView = null;
    } 
    if (getReactNativeHost().hasInstance())
      getReactNativeHost().getReactInstanceManager().onHostDestroy(getPlainActivity()); 
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (getReactNativeHost().hasInstance() && getReactNativeHost().getUseDeveloperSupport() && paramInt == 90) {
      paramKeyEvent.startTracking();
      return true;
    } 
    return false;
  }
  
  public boolean onKeyLongPress(int paramInt, KeyEvent paramKeyEvent) {
    if (getReactNativeHost().hasInstance() && getReactNativeHost().getUseDeveloperSupport() && paramInt == 90) {
      getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
      return true;
    } 
    return false;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    if (getReactNativeHost().hasInstance() && getReactNativeHost().getUseDeveloperSupport()) {
      if (paramInt == 82) {
        getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
        return true;
      } 
      if (((DoubleTapReloadRecognizer)a.b(this.mDoubleTapReloadRecognizer)).didDoubleTapR(paramInt, getPlainActivity().getCurrentFocus())) {
        getReactNativeHost().getReactInstanceManager().getDevSupportManager().handleReloadJS();
        return true;
      } 
    } 
    return false;
  }
  
  public boolean onNewIntent(Intent paramIntent) {
    if (getReactNativeHost().hasInstance()) {
      getReactNativeHost().getReactInstanceManager().onNewIntent(paramIntent);
      return true;
    } 
    return false;
  }
  
  protected void onPause() {
    if (getReactNativeHost().hasInstance())
      getReactNativeHost().getReactInstanceManager().onHostPause(getPlainActivity()); 
  }
  
  public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
    this.mPermissionsCallback = new Callback() {
        public void invoke(Object... param1VarArgs) {
          if (ReactActivityDelegate.this.mPermissionListener != null && ReactActivityDelegate.this.mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults))
            ReactActivityDelegate.this.mPermissionListener = null; 
        }
      };
  }
  
  protected void onResume() {
    if (getReactNativeHost().hasInstance())
      getReactNativeHost().getReactInstanceManager().onHostResume(getPlainActivity(), (DefaultHardwareBackBtnHandler)getPlainActivity()); 
    Callback callback = this.mPermissionsCallback;
    if (callback != null) {
      callback.invoke(new Object[0]);
      this.mPermissionsCallback = null;
    } 
  }
  
  public void requestPermissions(String[] paramArrayOfString, int paramInt, PermissionListener paramPermissionListener) {
    this.mPermissionListener = paramPermissionListener;
    getPlainActivity().requestPermissions(paramArrayOfString, paramInt);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactActivityDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */