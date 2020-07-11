package com.tt.miniapp.business.frontendapihandle.handler.net;

import android.text.TextUtils;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.miniapp.msg.sync.SyncMsgCtrlV2;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.websocket.common.WSRequest;
import com.tt.miniapp.websocket.mgr.SocketManagerV2;
import com.tt.miniapphost.AppBrandLogger;

public class CreateSocketTaskSyncV2 extends SyncMsgCtrlV2 {
  public CreateSocketTaskSyncV2(f paramf) {
    super(paramf);
  }
  
  private g makeMsg(boolean paramBoolean, int paramInt) {
    ApiCreateSocketTaskParam.OutputParam outputParam = new ApiCreateSocketTaskParam.OutputParam();
    try {
      outputParam.socketTaskId = paramInt;
      if (paramBoolean) {
        outputParam.socketType = SocketManagerV2.getInst().getSocketType(paramInt);
        outputParam.errMsg = buildErrorMsg("createSocketTask", "ok");
        return outputParam;
      } 
      outputParam.errMsg = buildErrorMsg("createSocketTask", "fail");
      return outputParam;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrlV2", exception.getStackTrace());
      return outputParam;
    } 
  }
  
  public g act() {
    WSRequest wSRequest = WSRequest.parse((ApiCreateSocketTaskParam.InputParam)this.mParams);
    if (wSRequest == null || TextUtils.isEmpty(wSRequest.url))
      return makeMsg(false, -1); 
    if (!NetUtil.isSafeDomain("socket", wSRequest.url))
      return makeMsg(false, -1); 
    int i = SocketManagerV2.getInst().createTask(wSRequest);
    return (i == -1) ? makeMsg(false, -1) : makeMsg(true, i);
  }
  
  public String getName() {
    return "createSocketTask";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\net\CreateSocketTaskSyncV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */