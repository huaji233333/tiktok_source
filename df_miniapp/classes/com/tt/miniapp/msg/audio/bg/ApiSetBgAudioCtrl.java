package com.tt.miniapp.msg.audio.bg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.background.BgAudioManagerClient;
import com.tt.miniapp.audio.background.BgAudioModel;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.option.e.e;

public class ApiSetBgAudioCtrl extends b {
  public ApiSetBgAudioCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    ApiErrorInfoEntity apiErrorInfoEntity = new ApiErrorInfoEntity();
    BgAudioModel bgAudioModel = BgAudioModel.parse(this.mArgs, apiErrorInfoEntity);
    if (bgAudioModel == null) {
      callbackFail(apiErrorInfoEntity.getErrorMsg(), apiErrorInfoEntity.getThrowable());
      return;
    } 
    BgAudioManagerClient.getInst().setAudioModel(bgAudioModel, new BgAudioManagerClient.TaskListener() {
          public void onFail(String param1String, Throwable param1Throwable) {
            ApiSetBgAudioCtrl.this.callbackFail(param1String, param1Throwable);
          }
          
          public void onSuccess() {
            ApiSetBgAudioCtrl.this.callbackOk();
          }
        });
  }
  
  public String getActionName() {
    return "setBgAudioState";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\bg\ApiSetBgAudioCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */