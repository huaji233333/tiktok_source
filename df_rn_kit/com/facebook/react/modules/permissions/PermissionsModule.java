package com.facebook.react.modules.permissions;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.util.SparseArray;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import java.util.ArrayList;

@ReactModule(name = "PermissionsAndroid")
public class PermissionsModule extends ReactContextBaseJavaModule implements PermissionListener {
  private final String DENIED = "denied";
  
  private final String GRANTED = "granted";
  
  private final String NEVER_ASK_AGAIN = "never_ask_again";
  
  private final SparseArray<Callback> mCallbacks = new SparseArray();
  
  private int mRequestCode;
  
  public PermissionsModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private PermissionAwareActivity getPermissionAwareActivity() {
    Activity activity = getCurrentActivity();
    if (activity != null) {
      if (activity instanceof PermissionAwareActivity)
        return (PermissionAwareActivity)activity; 
      throw new IllegalStateException("Tried to use permissions API but the host Activity doesn't implement PermissionAwareActivity.");
    } 
    throw new IllegalStateException("Tried to use permissions API while not attached to an Activity.");
  }
  
  @ReactMethod
  public void checkPermission(String paramString, Promise paramPromise) {
    Context context = getReactApplicationContext().getBaseContext();
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = true;
    boolean bool1 = true;
    if (i < 23) {
      if (context.checkPermission(paramString, Process.myPid(), Process.myUid()) != 0)
        bool1 = false; 
      paramPromise.resolve(Boolean.valueOf(bool1));
      return;
    } 
    if (context.checkSelfPermission(paramString) == 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    paramPromise.resolve(Boolean.valueOf(bool1));
  }
  
  public String getName() {
    return "PermissionsAndroid";
  }
  
  public boolean onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    ((Callback)this.mCallbacks.get(paramInt)).invoke(new Object[] { paramArrayOfint, getPermissionAwareActivity() });
    this.mCallbacks.remove(paramInt);
    return (this.mCallbacks.size() == 0);
  }
  
  @ReactMethod
  public void requestMultiplePermissions(ReadableArray paramReadableArray, final Promise promise) {
    final WritableNativeMap grantedPermissions = new WritableNativeMap();
    final ArrayList<String> permissionsToCheck = new ArrayList();
    Context context = getReactApplicationContext().getBaseContext();
    int i = 0;
    int j = 0;
    while (true) {
      if (i < paramReadableArray.size()) {
        String str2 = paramReadableArray.getString(i);
        int k = Build.VERSION.SDK_INT;
        String str1 = "granted";
        if (k < 23) {
          if (context.checkPermission(str2, Process.myPid(), Process.myUid()) != 0)
            str1 = "denied"; 
          writableNativeMap.putString(str2, str1);
        } else if (context.checkSelfPermission(str2) == 0) {
          writableNativeMap.putString(str2, "granted");
        } else {
          arrayList.add(str2);
          i++;
        } 
        j++;
      } else {
        break;
      } 
      i++;
    } 
    if (paramReadableArray.size() == j) {
      promise.resolve(writableNativeMap);
      return;
    } 
    try {
      PermissionAwareActivity permissionAwareActivity = getPermissionAwareActivity();
      this.mCallbacks.put(this.mRequestCode, new Callback() {
            public void invoke(Object... param1VarArgs) {
              int i = 0;
              int[] arrayOfInt = (int[])param1VarArgs[0];
              PermissionAwareActivity permissionAwareActivity = (PermissionAwareActivity)param1VarArgs[1];
              while (i < permissionsToCheck.size()) {
                String str = permissionsToCheck.get(i);
                if (arrayOfInt.length > 0 && arrayOfInt[i] == 0) {
                  grantedPermissions.putString(str, "granted");
                } else if (permissionAwareActivity.shouldShowRequestPermissionRationale(str)) {
                  grantedPermissions.putString(str, "denied");
                } else {
                  grantedPermissions.putString(str, "never_ask_again");
                } 
                i++;
              } 
              promise.resolve(grantedPermissions);
            }
          });
      permissionAwareActivity.requestPermissions(arrayList.<String>toArray(new String[0]), this.mRequestCode, this);
      this.mRequestCode++;
      return;
    } catch (IllegalStateException illegalStateException) {
      promise.reject("E_INVALID_ACTIVITY", illegalStateException);
      return;
    } 
  }
  
  @ReactMethod
  public void requestPermission(final String permission, final Promise promise) {
    Context context = getReactApplicationContext().getBaseContext();
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i < 23) {
      if (context.checkPermission(permission, Process.myPid(), Process.myUid()) == 0)
        bool = true; 
      promise.resolve(Boolean.valueOf(bool));
      return;
    } 
    if (context.checkSelfPermission(permission) == 0) {
      promise.resolve("granted");
      return;
    } 
    try {
      PermissionAwareActivity permissionAwareActivity = getPermissionAwareActivity();
      this.mCallbacks.put(this.mRequestCode, new Callback() {
            public void invoke(Object... param1VarArgs) {
              int[] arrayOfInt = (int[])param1VarArgs[0];
              if (arrayOfInt.length > 0 && arrayOfInt[0] == 0) {
                promise.resolve("granted");
                return;
              } 
              if (((PermissionAwareActivity)param1VarArgs[1]).shouldShowRequestPermissionRationale(permission)) {
                promise.resolve("denied");
                return;
              } 
              promise.resolve("never_ask_again");
            }
          });
      i = this.mRequestCode;
      permissionAwareActivity.requestPermissions(new String[] { permission }, i, this);
      this.mRequestCode++;
      return;
    } catch (IllegalStateException illegalStateException) {
      promise.reject("E_INVALID_ACTIVITY", illegalStateException);
      return;
    } 
  }
  
  @ReactMethod
  public void shouldShowRequestPermissionRationale(String paramString, Promise paramPromise) {
    if (Build.VERSION.SDK_INT < 23) {
      paramPromise.resolve(Boolean.valueOf(false));
      return;
    } 
    try {
      paramPromise.resolve(Boolean.valueOf(getPermissionAwareActivity().shouldShowRequestPermissionRationale(paramString)));
      return;
    } catch (IllegalStateException illegalStateException) {
      paramPromise.reject("E_INVALID_ACTIVITY", illegalStateException);
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\permissions\PermissionsModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */