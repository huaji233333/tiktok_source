package com.tt.miniapp.business.frontendapihandle.handler.net;

import android.text.TextUtils;
import com.tt.frontendapiinterface.f;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.msg.ApiHandlerV2;
import com.tt.miniapp.msg.ApiParamParser;
import com.tt.miniapp.msg.bean.NativeBufferItem;
import com.tt.miniapp.websocket.mgr.SocketManagerV2;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.option.e.e;
import g.i;
import java.nio.ByteBuffer;
import java.util.List;

public class ApiOperateSocketTaskV2 extends ApiHandlerV2 {
  private ApiOperateSocketTaskParam.InputParam mInputParam;
  
  public ApiOperateSocketTaskV2(f paramf, int paramInt, e parame) {
    super(paramf, paramInt, parame);
  }
  
  private void callback(ApiOperateSocketTaskParam.OutputParam paramOutputParam) {
    AppbrandApplicationImpl.getInst().getJsBridge().invokeApi(getActionName(), paramOutputParam, this.mCallBackId);
  }
  
  private void closeCollection() {
    boolean bool;
    try {
      int i = this.mInputParam.socketTaskId;
      int j = this.mInputParam.code;
      String str = this.mInputParam.reason;
      bool = SocketManagerV2.getInst().closeSocket(i, j, str);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiOperateSocketTaskV2", exception.getStackTrace());
      bool = false;
    } 
    ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
    outputParam.errMsg = makeErrMsg(bool, null);
    callback(outputParam);
  }
  
  private i getByteString(List<NativeBufferItem> paramList) {
    if (paramList == null)
      return null; 
    try {
      for (NativeBufferItem nativeBufferItem : paramList) {
        if (nativeBufferItem != null && TextUtils.equals(nativeBufferItem.key, "data")) {
          ByteBuffer byteBuffer = nativeBufferItem.value;
          if (byteBuffer != null)
            return i.of(byteBuffer); 
        } 
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiOperateSocketTaskV2", exception.getStackTrace());
    } 
    return null;
  }
  
  private void sendMsg() {
    ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
    try {
      boolean bool1;
      boolean bool2;
      boolean bool3;
      ApiErrorInfoEntity apiErrorInfoEntity = new ApiErrorInfoEntity();
      int i = this.mInputParam.socketTaskId;
      if (!ApiParamParser.isEmptyString(this.mInputParam.data)) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (this.mInputParam.__nativeBuffers__ != null) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      if (bool1) {
        String str = this.mInputParam.data;
        bool3 = SocketManagerV2.getInst().sendText(i, str, apiErrorInfoEntity);
      } else if (bool2) {
        bool3 = SocketManagerV2.getInst().sendArrayBuffer(i, getByteString(this.mInputParam.__nativeBuffers__), apiErrorInfoEntity);
      } else {
        apiErrorInfoEntity.append(paramIllegalMsg("data"));
        bool3 = false;
      } 
      if (bool3) {
        outputParam.errMsg = makeErrMsg(true, null);
      } else {
        outputParam.errMsg = makeErrMsg(false, apiErrorInfoEntity.getErrorMsg());
        Throwable throwable = apiErrorInfoEntity.getThrowable();
        if (throwable != null)
          outputParam.errStack = makeErrStack(throwable); 
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiOperateSocketTaskV2", exception.getStackTrace());
      outputParam.errMsg = makeErrMsg(false, "exception");
      outputParam.errStack = makeErrStack(exception);
    } 
    callback(outputParam);
  }
  
  public void act() {
    if (this.mApiParams == null) {
      AppBrandLogger.e("ApiOperateSocketTaskV2", new Object[] { "input params is null" });
      return;
    } 
    this.mInputParam = (ApiOperateSocketTaskParam.InputParam)this.mApiParams;
    try {
      String str = this.mInputParam.operationType;
      if (TextUtils.equals(str, "send")) {
        sendMsg();
        return;
      } 
      if (TextUtils.equals("close", str)) {
        closeCollection();
        return;
      } 
      ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
      outputParam.errMsg = makeErrMsg(false, paramIllegalMsg("operationType"));
      callback(outputParam);
      return;
    } catch (Exception exception) {
      ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
      outputParam.errMsg = makeErrMsg(false, null);
      callback(outputParam);
      AppBrandLogger.stacktrace(6, "ApiOperateSocketTaskV2", exception.getStackTrace());
      return;
    } 
  }
  
  public String getActionName() {
    return "operateSocketTask";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\net\ApiOperateSocketTaskV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */