package com.facebook.react.modules.network;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class ResponseUtil {
  public static void onDataReceived(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt, WritableMap paramWritableMap) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt);
    writableArray.pushMap(paramWritableMap);
    paramRCTDeviceEventEmitter.emit("didReceiveNetworkData", writableArray);
  }
  
  public static void onDataReceived(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt, String paramString) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt);
    writableArray.pushString(paramString);
    paramRCTDeviceEventEmitter.emit("didReceiveNetworkData", writableArray);
  }
  
  public static void onDataReceivedProgress(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt, long paramLong1, long paramLong2) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt);
    writableArray.pushInt((int)paramLong1);
    writableArray.pushInt((int)paramLong2);
    paramRCTDeviceEventEmitter.emit("didReceiveNetworkDataProgress", writableArray);
  }
  
  public static void onDataSend(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt, long paramLong1, long paramLong2) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt);
    writableArray.pushInt((int)paramLong1);
    writableArray.pushInt((int)paramLong2);
    paramRCTDeviceEventEmitter.emit("didSendNetworkData", writableArray);
  }
  
  public static void onIncrementalDataReceived(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt, String paramString, long paramLong1, long paramLong2) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt);
    writableArray.pushString(paramString);
    writableArray.pushInt((int)paramLong1);
    writableArray.pushInt((int)paramLong2);
    paramRCTDeviceEventEmitter.emit("didReceiveNetworkIncrementalData", writableArray);
  }
  
  public static void onRequestError(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt, String paramString, IOException paramIOException) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt);
    writableArray.pushString(paramString);
    if (paramIOException != null && paramIOException.getClass() == SocketTimeoutException.class)
      writableArray.pushBoolean(true); 
    paramRCTDeviceEventEmitter.emit("didCompleteNetworkResponse", writableArray);
  }
  
  public static void onRequestSuccess(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt);
    writableArray.pushNull();
    paramRCTDeviceEventEmitter.emit("didCompleteNetworkResponse", writableArray);
  }
  
  public static void onResponseReceived(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt1, int paramInt2, WritableMap paramWritableMap, String paramString) {
    WritableArray writableArray = Arguments.createArray();
    writableArray.pushInt(paramInt1);
    writableArray.pushInt(paramInt2);
    writableArray.pushMap(paramWritableMap);
    writableArray.pushString(paramString);
    paramRCTDeviceEventEmitter.emit("didReceiveNetworkResponse", writableArray);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\ResponseUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */