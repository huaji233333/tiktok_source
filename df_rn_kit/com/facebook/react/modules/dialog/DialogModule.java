package com.facebook.react.modules.dialog;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.k;
import com.facebook.common.e.a;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import java.util.Map;

@ReactModule(name = "DialogManagerAndroid")
public class DialogModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  static final Map<String, Object> CONSTANTS = MapBuilder.of("buttonClicked", "buttonClicked", "dismissed", "dismissed", "buttonPositive", Integer.valueOf(-1), "buttonNegative", Integer.valueOf(-2), "buttonNeutral", Integer.valueOf(-3));
  
  public boolean mIsInForeground;
  
  public DialogModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private FragmentManagerHelper getFragmentManagerHelper() {
    Activity activity = getCurrentActivity();
    return (activity == null) ? null : ((activity instanceof FragmentActivity) ? new FragmentManagerHelper(((FragmentActivity)activity).getSupportFragmentManager()) : new FragmentManagerHelper(activity.getFragmentManager()));
  }
  
  public Map<String, Object> getConstants() {
    return CONSTANTS;
  }
  
  public String getName() {
    return "DialogManagerAndroid";
  }
  
  public void initialize() {
    getReactApplicationContext().addLifecycleEventListener(this);
  }
  
  public void onHostDestroy() {}
  
  public void onHostPause() {
    this.mIsInForeground = false;
  }
  
  public void onHostResume() {
    this.mIsInForeground = true;
    FragmentManagerHelper fragmentManagerHelper = getFragmentManagerHelper();
    if (fragmentManagerHelper != null) {
      fragmentManagerHelper.showPendingAlert();
      return;
    } 
    a.b(DialogModule.class, "onHostResume called but no FragmentManager found");
  }
  
  @ReactMethod
  public void showAlert(ReadableMap paramReadableMap, Callback paramCallback1, final Callback actionCallback) {
    final FragmentManagerHelper fragmentManagerHelper = getFragmentManagerHelper();
    int i = 0;
    if (fragmentManagerHelper == null) {
      paramCallback1.invoke(new Object[] { "Tried to show an alert while not attached to an Activity" });
      return;
    } 
    final Bundle args = new Bundle();
    if (paramReadableMap.hasKey("title"))
      bundle.putString("title", paramReadableMap.getString("title")); 
    if (paramReadableMap.hasKey("message"))
      bundle.putString("message", paramReadableMap.getString("message")); 
    if (paramReadableMap.hasKey("buttonPositive"))
      bundle.putString("button_positive", paramReadableMap.getString("buttonPositive")); 
    if (paramReadableMap.hasKey("buttonNegative"))
      bundle.putString("button_negative", paramReadableMap.getString("buttonNegative")); 
    if (paramReadableMap.hasKey("buttonNeutral"))
      bundle.putString("button_neutral", paramReadableMap.getString("buttonNeutral")); 
    if (paramReadableMap.hasKey("items")) {
      ReadableArray readableArray = paramReadableMap.getArray("items");
      CharSequence[] arrayOfCharSequence = new CharSequence[readableArray.size()];
      while (i < readableArray.size()) {
        arrayOfCharSequence[i] = readableArray.getString(i);
        i++;
      } 
      bundle.putCharSequenceArray("items", arrayOfCharSequence);
    } 
    if (paramReadableMap.hasKey("cancelable"))
      bundle.putBoolean("cancelable", paramReadableMap.getBoolean("cancelable")); 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            fragmentManagerHelper.showNewAlert(DialogModule.this.mIsInForeground, args, actionCallback);
          }
        });
  }
  
  class AlertFragmentListener implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    private final Callback mCallback;
    
    private boolean mCallbackConsumed;
    
    public AlertFragmentListener(Callback param1Callback) {
      this.mCallback = param1Callback;
    }
    
    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
      if (!this.mCallbackConsumed && DialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
        this.mCallback.invoke(new Object[] { "buttonClicked", Integer.valueOf(param1Int) });
        this.mCallbackConsumed = true;
      } 
    }
    
    public void onDismiss(DialogInterface param1DialogInterface) {
      if (!this.mCallbackConsumed && DialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
        this.mCallback.invoke(new Object[] { "dismissed" });
        this.mCallbackConsumed = true;
      } 
    }
  }
  
  class FragmentManagerHelper {
    private final FragmentManager mFragmentManager;
    
    private Object mFragmentToShow;
    
    private final k mSupportFragmentManager;
    
    public FragmentManagerHelper(FragmentManager param1FragmentManager) {
      this.mFragmentManager = param1FragmentManager;
    }
    
    public FragmentManagerHelper(k param1k) {
      this.mSupportFragmentManager = param1k;
    }
    
    private void dismissExisting() {
      if (isUsingSupportLibrary()) {
        SupportAlertFragment supportAlertFragment = (SupportAlertFragment)this.mSupportFragmentManager.a("com.facebook.catalyst.react.dialog.DialogModule");
        if (supportAlertFragment != null)
          supportAlertFragment.dismissAllowingStateLoss(); 
        return;
      } 
      AlertFragment alertFragment = (AlertFragment)this.mFragmentManager.findFragmentByTag("com.facebook.catalyst.react.dialog.DialogModule");
      if (alertFragment != null)
        alertFragment.dismissAllowingStateLoss(); 
    }
    
    private boolean isUsingSupportLibrary() {
      return (this.mSupportFragmentManager != null);
    }
    
    public void showNewAlert(boolean param1Boolean, Bundle param1Bundle, Callback param1Callback) {
      SupportAlertFragment supportAlertFragment;
      UiThreadUtil.assertOnUiThread();
      dismissExisting();
      if (param1Callback != null) {
        DialogModule.AlertFragmentListener alertFragmentListener = new DialogModule.AlertFragmentListener(param1Callback);
      } else {
        param1Callback = null;
      } 
      if (isUsingSupportLibrary()) {
        supportAlertFragment = new SupportAlertFragment((DialogModule.AlertFragmentListener)param1Callback, param1Bundle);
        if (param1Boolean) {
          if (param1Bundle.containsKey("cancelable"))
            supportAlertFragment.setCancelable(param1Bundle.getBoolean("cancelable")); 
          supportAlertFragment.show(this.mSupportFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
          return;
        } 
        this.mFragmentToShow = supportAlertFragment;
        return;
      } 
      AlertFragment alertFragment = new AlertFragment((DialogModule.AlertFragmentListener)supportAlertFragment, param1Bundle);
      if (param1Boolean) {
        if (param1Bundle.containsKey("cancelable"))
          alertFragment.setCancelable(param1Bundle.getBoolean("cancelable")); 
        alertFragment.show(this.mFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
        return;
      } 
      this.mFragmentToShow = alertFragment;
    }
    
    public void showPendingAlert() {
      UiThreadUtil.assertOnUiThread();
      if (this.mFragmentToShow == null)
        return; 
      if (isUsingSupportLibrary()) {
        ((SupportAlertFragment)this.mFragmentToShow).show(this.mSupportFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
      } else {
        ((AlertFragment)this.mFragmentToShow).show(this.mFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
      } 
      this.mFragmentToShow = null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\dialog\DialogModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */