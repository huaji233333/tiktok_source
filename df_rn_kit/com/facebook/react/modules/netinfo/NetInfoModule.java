package com.facebook.react.modules.netinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.c.a;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = "NetInfo")
public class NetInfoModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  private String mConnectionType = "unknown";
  
  private final ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;
  
  private String mConnectivityDeprecated = "UNKNOWN";
  
  private final ConnectivityManager mConnectivityManager;
  
  private String mEffectiveConnectionType = "unknown";
  
  private boolean mNoNetworkPermission;
  
  public NetInfoModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
    this.mConnectivityManager = (ConnectivityManager)paramReactApplicationContext.getSystemService("connectivity");
    this.mConnectivityBroadcastReceiver = new ConnectivityBroadcastReceiver();
  }
  
  private WritableMap createConnectivityEventMap() {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    writableNativeMap.putString("network_info", this.mConnectivityDeprecated);
    writableNativeMap.putString("connectionType", this.mConnectionType);
    writableNativeMap.putString("effectiveConnectionType", this.mEffectiveConnectionType);
    return (WritableMap)writableNativeMap;
  }
  
  private String getCurrentConnectionType() {
    try {
      NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo(this.mConnectivityManager);
      if (networkInfo == null || !networkInfo.isConnected())
        return "NONE"; 
      if (ConnectivityManager.isNetworkTypeValid(networkInfo.getType()))
        return networkInfo.getTypeName().toUpperCase(); 
    } catch (SecurityException securityException) {
      this.mNoNetworkPermission = true;
      return "UNKNOWN";
    } 
    return "UNKNOWN";
  }
  
  private String getEffectiveConnectionType(NetworkInfo paramNetworkInfo) {
    switch (paramNetworkInfo.getSubtype()) {
      default:
        return "unknown";
      case 13:
      case 15:
        return "4g";
      case 3:
      case 5:
      case 6:
      case 8:
      case 9:
      case 10:
      case 12:
      case 14:
        return "3g";
      case 1:
      case 2:
      case 4:
      case 7:
      case 11:
        break;
    } 
    return "2g";
  }
  
  private void registerReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    getReactApplicationContext().registerReceiver(this.mConnectivityBroadcastReceiver, intentFilter);
    this.mConnectivityBroadcastReceiver.setRegistered(true);
    updateAndSendConnectionType();
  }
  
  private void sendConnectivityChangedEvent() {
    ((DeviceEventManagerModule.RCTDeviceEventEmitter)getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("networkStatusDidChange", createConnectivityEventMap());
  }
  
  private void unregisterReceiver() {
    if (this.mConnectivityBroadcastReceiver.isRegistered()) {
      getReactApplicationContext().unregisterReceiver(this.mConnectivityBroadcastReceiver);
      this.mConnectivityBroadcastReceiver.setRegistered(false);
    } 
  }
  
  @ReactMethod
  public void getCurrentConnectivity(Promise paramPromise) {
    if (this.mNoNetworkPermission) {
      paramPromise.reject("E_MISSING_PERMISSION", "To use NetInfo on Android, add the following to your AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />", null);
      return;
    } 
    paramPromise.resolve(createConnectivityEventMap());
  }
  
  public String getName() {
    return "NetInfo";
  }
  
  public void initialize() {
    getReactApplicationContext().addLifecycleEventListener((LifecycleEventListener)this);
  }
  
  @ReactMethod
  public void isConnectionMetered(Promise paramPromise) {
    if (this.mNoNetworkPermission) {
      paramPromise.reject("E_MISSING_PERMISSION", "To use NetInfo on Android, add the following to your AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" />", null);
      return;
    } 
    paramPromise.resolve(Boolean.valueOf(a.a(this.mConnectivityManager)));
  }
  
  public void onHostDestroy() {}
  
  public void onHostPause() {
    unregisterReceiver();
  }
  
  public void onHostResume() {
    registerReceiver();
  }
  
  public void updateAndSendConnectionType() {
    // Byte code:
    //   0: ldc 'unknown'
    //   2: astore_3
    //   3: aload_0
    //   4: getfield mConnectivityManager : Landroid/net/ConnectivityManager;
    //   7: invokestatic com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo : (Landroid/net/ConnectivityManager;)Landroid/net/NetworkInfo;
    //   10: astore_2
    //   11: aload_2
    //   12: ifnull -> 83
    //   15: aload_2
    //   16: invokevirtual isConnected : ()Z
    //   19: ifne -> 25
    //   22: goto -> 83
    //   25: aload_2
    //   26: invokevirtual getType : ()I
    //   29: istore_1
    //   30: iload_1
    //   31: ifeq -> 65
    //   34: iload_1
    //   35: iconst_1
    //   36: if_icmpeq -> 180
    //   39: iload_1
    //   40: iconst_4
    //   41: if_icmpeq -> 65
    //   44: iload_1
    //   45: bipush #9
    //   47: if_icmpeq -> 174
    //   50: iload_1
    //   51: bipush #6
    //   53: if_icmpeq -> 168
    //   56: iload_1
    //   57: bipush #7
    //   59: if_icmpeq -> 162
    //   62: goto -> 94
    //   65: aload_0
    //   66: aload_2
    //   67: invokespecial getEffectiveConnectionType : (Landroid/net/NetworkInfo;)Ljava/lang/String;
    //   70: astore_2
    //   71: ldc 'cellular'
    //   73: astore #4
    //   75: aload_2
    //   76: astore_3
    //   77: aload #4
    //   79: astore_2
    //   80: goto -> 97
    //   83: ldc 'none'
    //   85: astore_2
    //   86: goto -> 97
    //   89: aload_0
    //   90: iconst_1
    //   91: putfield mNoNetworkPermission : Z
    //   94: ldc 'unknown'
    //   96: astore_2
    //   97: aload_0
    //   98: invokespecial getCurrentConnectionType : ()Ljava/lang/String;
    //   101: astore #4
    //   103: aload_2
    //   104: aload_0
    //   105: getfield mConnectionType : Ljava/lang/String;
    //   108: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   111: ifeq -> 137
    //   114: aload_3
    //   115: aload_0
    //   116: getfield mEffectiveConnectionType : Ljava/lang/String;
    //   119: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   122: ifeq -> 137
    //   125: aload #4
    //   127: aload_0
    //   128: getfield mConnectivityDeprecated : Ljava/lang/String;
    //   131: invokevirtual equalsIgnoreCase : (Ljava/lang/String;)Z
    //   134: ifne -> 157
    //   137: aload_0
    //   138: aload_2
    //   139: putfield mConnectionType : Ljava/lang/String;
    //   142: aload_0
    //   143: aload_3
    //   144: putfield mEffectiveConnectionType : Ljava/lang/String;
    //   147: aload_0
    //   148: aload #4
    //   150: putfield mConnectivityDeprecated : Ljava/lang/String;
    //   153: aload_0
    //   154: invokespecial sendConnectivityChangedEvent : ()V
    //   157: return
    //   158: astore_2
    //   159: goto -> 89
    //   162: ldc 'bluetooth'
    //   164: astore_2
    //   165: goto -> 97
    //   168: ldc 'wimax'
    //   170: astore_2
    //   171: goto -> 97
    //   174: ldc 'ethernet'
    //   176: astore_2
    //   177: goto -> 97
    //   180: ldc 'wifi'
    //   182: astore_2
    //   183: goto -> 97
    // Exception table:
    //   from	to	target	type
    //   3	11	158	java/lang/SecurityException
    //   15	22	158	java/lang/SecurityException
    //   25	30	158	java/lang/SecurityException
    //   65	71	158	java/lang/SecurityException
  }
  
  class ConnectivityBroadcastReceiver extends BroadcastReceiver {
    private boolean isRegistered;
    
    private ConnectivityBroadcastReceiver() {}
    
    public boolean isRegistered() {
      return this.isRegistered;
    }
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      if (param1Intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
        NetInfoModule.this.updateAndSendConnectionType(); 
    }
    
    public void setRegistered(boolean param1Boolean) {
      this.isRegistered = param1Boolean;
    }
  }
  
  class NetInfoModule {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\netinfo\NetInfoModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */