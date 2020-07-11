package com.tt.miniapp.process.handler;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;

public class CrossProcessCallHandler {
  public static void handleHostProcessReceivedAsyncCall(CrossProcessCallEntity paramCrossProcessCallEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    String str = paramCrossProcessCallEntity.getCallType();
    CrossProcessDataEntity crossProcessDataEntity = paramCrossProcessCallEntity.getCallData();
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("CrossProcessCallHandler", new Object[] { "handleHostProcessReceivedAsyncCall error callType. crossProcessCallEntity:", paramCrossProcessCallEntity });
      return;
    } 
    if (InnerHostProcessCallHandler.handleAsyncCall(str, crossProcessDataEntity, paramCrossProcessCallEntity.getCallExtraData(), paramAsyncIpcHandler))
      return; 
    IAsyncHostDataHandler iAsyncHostDataHandler = AppbrandContext.getInst().getAsyncHandler(str);
    if (iAsyncHostDataHandler != null)
      iAsyncHostDataHandler.action(crossProcessDataEntity, paramAsyncIpcHandler); 
  }
  
  public static CrossProcessDataEntity handleHostProcessReceivedSyncCall(CrossProcessCallEntity paramCrossProcessCallEntity) {
    String str = paramCrossProcessCallEntity.getCallType();
    CrossProcessDataEntity crossProcessDataEntity1 = paramCrossProcessCallEntity.getCallData();
    CrossProcessDataEntity crossProcessDataEntity2 = paramCrossProcessCallEntity.getCallExtraData();
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("CrossProcessCallHandler", new Object[] { "handleHostProcessReceivedSyncCall error callType. crossProcessCallEntity:", paramCrossProcessCallEntity });
      return null;
    } 
    CrossProcessDataEntity[] arrayOfCrossProcessDataEntity = new CrossProcessDataEntity[1];
    if (InnerHostProcessCallHandler.handleSyncCall(str, crossProcessDataEntity1, crossProcessDataEntity2, arrayOfCrossProcessDataEntity))
      return arrayOfCrossProcessDataEntity[0]; 
    ISyncHostDataHandler iSyncHostDataHandler = AppbrandContext.getInst().getSyncHandler(str);
    return (iSyncHostDataHandler != null) ? iSyncHostDataHandler.action(crossProcessDataEntity1) : null;
  }
  
  public static void handleMiniAppReceivedAsyncCall(CrossProcessCallEntity paramCrossProcessCallEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    String str = paramCrossProcessCallEntity.getCallType();
    CrossProcessDataEntity crossProcessDataEntity1 = paramCrossProcessCallEntity.getCallData();
    CrossProcessDataEntity crossProcessDataEntity2 = paramCrossProcessCallEntity.getCallExtraData();
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("CrossProcessCallHandler", new Object[] { "handleMiniAppReceivedAsyncCall error callType. crossProcessCallEntity:", paramCrossProcessCallEntity });
      return;
    } 
    InnerMiniAppProcessCallHandler.handleAsyncCall(str, crossProcessDataEntity1, crossProcessDataEntity2, paramAsyncIpcHandler);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\handler\CrossProcessCallHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */