package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlebar.ITitleBar;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.option.e.e;

public class ApiShowMorePanelCtrl extends b {
  public ApiShowMorePanelCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            IActivityProxy iActivityProxy = AppbrandContext.getInst().getCurrentActivity().getActivityProxy();
            if (iActivityProxy == null) {
              ApiShowMorePanelCtrl.this.callbackFail(a.c("IActivityProxy"));
              return;
            } 
            ITitleBar iTitleBar = iActivityProxy.getTitleBar();
            if (iTitleBar == null) {
              ApiShowMorePanelCtrl.this.callbackFail(a.c("ITitleBar"));
              return;
            } 
            iTitleBar.performMoreButtonClick();
            ApiShowMorePanelCtrl.this.callbackOk();
          }
        });
  }
  
  public String getActionName() {
    return "showMorePanel";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiShowMorePanelCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */