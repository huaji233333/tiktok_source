package com.tt.miniapp.msg.audio.async;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapp.audio.AudioStateModule;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;

public class ApiSetAudioState extends b {
  public ApiSetAudioState(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      AudioStateModule audioStateModule = AudioStateModule.parse(this.mArgs);
      AudioManager.TaskListener taskListener = new AudioManager.TaskListener() {
          public void onFail(String param1String, Throwable param1Throwable) {
            ApiSetAudioState.this.callbackFail(param1String, param1Throwable);
          }
          
          public void onSuccess() {
            ApiSetAudioState.this.callbackOk();
          }
        };
      AudioManager.getInst().setAudioState(audioStateModule, taskListener);
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiSetAudioState", "act", exception);
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "setAudioState";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\async\ApiSetAudioState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */