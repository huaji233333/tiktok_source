package com.tt.miniapp.msg;

import com.tt.miniapp.exit.AppBrandExitManager;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;

public class ApiCloseCallbackReturnCtrl extends SyncMsgCtrl {
  public ApiCloseCallbackReturnCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              AppBrandExitManager.getInst().onBeforeExitReturn(isClosed);
            }
          });
      return makeOkMsg();
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("ApiCloseCallbackReturnCtrl", "", (Throwable)jSONException);
      return makeFailMsg("jsonException");
    } 
  }
  
  public String getName() {
    return "onBeforeCloseReturnSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiCloseCallbackReturnCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */