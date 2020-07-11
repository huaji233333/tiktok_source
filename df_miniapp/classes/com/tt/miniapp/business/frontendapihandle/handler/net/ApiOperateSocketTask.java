package com.tt.miniapp.business.frontendapihandle.handler.net;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.websocket.mgr.SocketManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.option.e.e;
import g.i;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiOperateSocketTask extends b {
  public ApiOperateSocketTask(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void closeCollection() {
    boolean bool;
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      int i = jSONObject.optInt("socketTaskId");
      int j = jSONObject.optInt("code");
      String str = jSONObject.optString("reason");
      bool = SocketManager.getInst().closeSocket(i, j, str);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiOperateSocketTask", exception.getStackTrace());
      bool = false;
    } 
    callbackDefaultMsg(bool);
  }
  
  private i getByteString(JSONArray paramJSONArray) {
    if (paramJSONArray != null) {
      int i = 0;
      try {
        int j = paramJSONArray.length();
        while (i < j) {
          JSONObject jSONObject = paramJSONArray.getJSONObject(i);
          if (TextUtils.equals(jSONObject.getString("key"), "data"))
            return i.decodeBase64(jSONObject.optString("base64")); 
          i++;
        } 
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "ApiOperateSocketTask", exception.getStackTrace());
      } 
    } 
    return null;
  }
  
  private void sendMsg() {
    try {
      String str;
      ApiErrorInfoEntity apiErrorInfoEntity = new ApiErrorInfoEntity();
      JSONObject jSONObject = new JSONObject(this.mArgs);
      int i = jSONObject.optInt("socketTaskId");
      boolean bool1 = jSONObject.has("data");
      boolean bool2 = jSONObject.has("__nativeBuffers__");
      if (bool1) {
        str = jSONObject.optString("data");
        bool1 = SocketManager.getInst().sendText(i, str, apiErrorInfoEntity);
      } else if (bool2) {
        bool1 = SocketManager.getInst().sendArrayBuffer(i, getByteString(str.optJSONArray("__nativeBuffers__")), apiErrorInfoEntity);
      } else {
        apiErrorInfoEntity.append(paramIllegalMsg("data"));
        bool1 = false;
      } 
      if (bool1) {
        callbackDefaultMsg(true);
        return;
      } 
      doCallbackByApiHandler(makeMsgByResult(false, null, apiErrorInfoEntity.getErrorMsg(), apiErrorInfoEntity.getThrowable()).toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiOperateSocketTask", exception.getStackTrace());
      callbackException(exception);
      return;
    } 
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("operationType");
      if (TextUtils.equals(str, "send")) {
        sendMsg();
        return;
      } 
      if (TextUtils.equals("close", str)) {
        closeCollection();
        return;
      } 
      callbackIllegalParam("operationType");
      return;
    } catch (Exception exception) {
      callbackDefaultMsg(false);
      AppBrandLogger.stacktrace(6, "ApiOperateSocketTask", exception.getStackTrace());
      return;
    } 
  }
  
  public String getActionName() {
    return "operateSocketTask";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\net\ApiOperateSocketTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */