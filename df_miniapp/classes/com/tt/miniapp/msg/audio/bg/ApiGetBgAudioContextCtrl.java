package com.tt.miniapp.msg.audio.bg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.background.BgAudioManagerClient;
import com.tt.option.e.e;

public class ApiGetBgAudioContextCtrl extends b {
  public ApiGetBgAudioContextCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    BgAudioManagerClient.getInst().obtainManager(new BgAudioManagerClient.IBindCallback() {
          public void onBind(int param1Int) {
            if (param1Int != -1) {
              ApiGetBgAudioContextCtrl.this.callbackOk();
              return;
            } 
            ApiGetBgAudioContextCtrl.this.callbackFail("bind fail");
          }
        });
  }
  
  public String getActionName() {
    return "getBackgroundAudioContext";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\bg\ApiGetBgAudioContextCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */