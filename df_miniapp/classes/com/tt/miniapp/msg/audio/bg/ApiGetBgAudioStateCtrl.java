package com.tt.miniapp.msg.audio.bg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.background.BgAudioManagerClient;
import com.tt.miniapp.audio.background.BgAudioState;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiGetBgAudioStateCtrl extends b {
  public ApiGetBgAudioStateCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      BgAudioState bgAudioState = BgAudioManagerClient.getInst().getAudioState();
      if (bgAudioState == null) {
        callbackFail("audio state is null");
        return;
      } 
      if (bgAudioState.duration < 0) {
        callbackFail("audio duration < 0");
        return;
      } 
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("paused", bgAudioState.paused);
      jSONObject.put("currentTime", bgAudioState.currentTime);
      jSONObject.put("duration", bgAudioState.duration);
      jSONObject.put("buffered", bgAudioState.bufferd);
      jSONObject.put("volume", bgAudioState.volume);
      callbackOk(jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiGetBgAudioStateCtrl", "act", exception);
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "getBgAudioState";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\bg\ApiGetBgAudioStateCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */