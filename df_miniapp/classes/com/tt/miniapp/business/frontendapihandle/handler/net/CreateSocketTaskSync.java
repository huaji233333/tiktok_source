package com.tt.miniapp.business.frontendapihandle.handler.net;

import android.text.TextUtils;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.websocket.common.WSRequest;
import com.tt.miniapp.websocket.mgr.SocketManager;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class CreateSocketTaskSync extends SyncMsgCtrl {
  public CreateSocketTaskSync(String paramString) {
    super(paramString);
  }
  
  private String makeMsg(boolean paramBoolean, int paramInt) {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("socketTaskId", paramInt);
      if (paramBoolean) {
        jSONObject.put("socketType", SocketManager.getInst().getSocketType(paramInt));
        jSONObject.put("errMsg", buildErrorMsg("createSocketTask", "ok"));
      } else {
        jSONObject.put("errMsg", buildErrorMsg("createSocketTask", "fail"));
      } 
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrl", exception.getStackTrace());
      return "";
    } 
  }
  
  public String act() {
    WSRequest wSRequest = WSRequest.parse(this.mParams);
    if (wSRequest == null || TextUtils.isEmpty(wSRequest.url))
      return makeMsg(false, -1); 
    if (!NetUtil.isSafeDomain("socket", wSRequest.url))
      return makeMsg(false, -1); 
    int i = SocketManager.getInst().createTask(wSRequest);
    return (i == -1) ? makeMsg(false, -1) : makeMsg(true, i);
  }
  
  public String getName() {
    return "createSocketTask";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\net\CreateSocketTaskSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */