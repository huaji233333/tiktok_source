package com.tt.miniapp.locate;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tt.miniapp.maplocate.TMALocation;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.secrecy.SecrecyManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import org.json.JSONObject;

public class LocateCrossProcessRequester implements Handler.Callback {
  public static volatile TMALocation sCachedLocation;
  
  private Handler handler;
  
  boolean isLocateFinished;
  
  private String mCallApi;
  
  private LocateResultCallbck mCallback;
  
  private IpcCallback mIPCCallback;
  
  private long mTimeoutForLocate;
  
  public LocateCrossProcessRequester(String paramString) {
    this.mCallApi = paramString;
    this.handler = new Handler(Looper.getMainLooper(), this);
    this.mIPCCallback = new IpcCallback() {
        public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
          AppBrandLogger.d("LocateCrossProcessRequester", new Object[] { "onIpcCallback ", param1CrossProcessDataEntity });
          LocateCrossProcessRequester.this.stopTimer();
          if (param1CrossProcessDataEntity == null) {
            LocateCrossProcessRequester.this.callBackFailedWithCache("callback failed");
            return;
          } 
          String str = param1CrossProcessDataEntity.getString("locationResult");
          if (TextUtils.isEmpty(str)) {
            LocateCrossProcessRequester.this.callBackFailedWithCache("ipcnull");
            return;
          } 
          try {
            TMALocation tMALocation = TMALocation.fromJson(new JSONObject(str));
            if (tMALocation == null) {
              LocateCrossProcessRequester.this.callBackFailedWithCache("other");
              return;
            } 
            if (param1CrossProcessDataEntity.getInt("code") == -1) {
              LocateCrossProcessRequester locateCrossProcessRequester = LocateCrossProcessRequester.this;
              StringBuilder stringBuilder = new StringBuilder("loctype:");
              stringBuilder.append(tMALocation.getLocType());
              stringBuilder.append("_code:");
              stringBuilder.append(tMALocation.getStatusCode());
              stringBuilder.append("_rawcode:");
              stringBuilder.append(tMALocation.getRawImplStatusCode());
              locateCrossProcessRequester.callBackFailedWithCache(stringBuilder.toString());
              return;
            } 
            if (tMALocation.getStatusCode() == 0) {
              AppBrandLogger.d("LocateCrossProcessRequester", new Object[] { "onIpcCallback SUCCESS" });
              LocateCrossProcessRequester.sCachedLocation = tMALocation;
              LocateCrossProcessRequester.this.callBackSuccess(tMALocation);
            } 
            return;
          } catch (Exception exception) {
            AppBrandLogger.eWithThrowable("LocateCrossProcessRequester", "fromjson", exception);
            LocateCrossProcessRequester.this.callBackFailedWithCache("tmalocation_fromjson");
            return;
          } 
        }
        
        public void onIpcConnectError() {
          LocateCrossProcessRequester.this.callBackFailedWithCache("ipc fail");
        }
      };
  }
  
  private void notifyFailed(String paramString) {
    LocateResultCallbck locateResultCallbck = this.mCallback;
    if (locateResultCallbck != null)
      locateResultCallbck.onFailed(paramString); 
    SecrecyManager.inst().notifyStateStop(12);
  }
  
  private void notifySuccess(TMALocation paramTMALocation) {
    if (SecrecyManager.inst().isSecrecyDenied(12)) {
      notifyFailed(BrandPermissionUtils.makePermissionErrorMsg(this.mCallApi));
      return;
    } 
    LocateResultCallbck locateResultCallbck = this.mCallback;
    if (locateResultCallbck != null)
      locateResultCallbck.onSuccess(new TMALocation(paramTMALocation)); 
    SecrecyManager.inst().notifyStateStop(12);
  }
  
  public void callBackFailedWithCache(String paramString) {
    if (this.isLocateFinished)
      return; 
    StringBuilder stringBuilder = new StringBuilder("callBackFailedWithCache：");
    stringBuilder.append(paramString);
    AppBrandLogger.d("LocateCrossProcessRequester", new Object[] { stringBuilder.toString() });
    TMALocation tMALocation = sCachedLocation;
    if (TMALocation.isSuccess(tMALocation)) {
      notifySuccess(tMALocation);
      return;
    } 
    notifyFailed(paramString);
    stopTimer();
    this.isLocateFinished = true;
  }
  
  public void callBackSuccess(TMALocation paramTMALocation) {
    if (this.isLocateFinished)
      return; 
    notifySuccess(paramTMALocation);
    this.isLocateFinished = true;
  }
  
  public TMALocation getCachedLocation() {
    SecrecyManager.inst().notifyStateStart(12);
    SecrecyManager.inst().notifyStateStop(12);
    return sCachedLocation;
  }
  
  public boolean handleMessage(Message paramMessage) {
    if (paramMessage.what == 1) {
      AppBrandLogger.d("LocateCrossProcessRequester", new Object[] { "locate timeout" });
      this.mIPCCallback.finishListenIpcCallback();
      callBackFailedWithCache("timeout");
      return true;
    } 
    return false;
  }
  
  public void startCrossProcessLocate(long paramLong, LocateResultCallbck paramLocateResultCallbck) {
    SecrecyManager.inst().notifyStateStart(12);
    this.mTimeoutForLocate = paramLong;
    this.handler.sendEmptyMessageDelayed(1, this.mTimeoutForLocate);
    this.mCallback = paramLocateResultCallbck;
    AppBrandLogger.d("LocateCrossProcessRequester", new Object[] { "startCrossProcessLocate cross process" });
    InnerHostProcessBridge.startLocate(this.mIPCCallback);
  }
  
  public void stopTimer() {
    AppBrandLogger.d("LocateCrossProcessRequester", new Object[] { "locate stopTimer" });
    this.handler.removeMessages(1);
  }
  
  public static interface LocateResultCallbck {
    void onFailed(String param1String);
    
    void onSuccess(TMALocation param1TMALocation);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\locate\LocateCrossProcessRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */