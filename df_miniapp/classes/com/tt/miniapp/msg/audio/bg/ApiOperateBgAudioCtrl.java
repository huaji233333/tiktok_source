package com.tt.miniapp.msg.audio.bg;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.background.BgAudioManagerClient;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiOperateBgAudioCtrl extends b {
  public ApiOperateBgAudioCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("operationType");
      BgAudioManagerClient.TaskListener taskListener = new BgAudioManagerClient.TaskListener() {
          public void onFail(String param1String, Throwable param1Throwable) {
            ApiOperateBgAudioCtrl.this.callbackFail(param1String, param1Throwable);
          }
          
          public void onSuccess() {
            ApiOperateBgAudioCtrl.this.callbackOk();
          }
        };
      if (TextUtils.equals(str, "play")) {
        BgAudioManagerClient.getInst().play(taskListener);
        return;
      } 
      if (TextUtils.equals(str, "pause")) {
        BgAudioManagerClient.getInst().pause(taskListener);
        return;
      } 
      if (TextUtils.equals(str, "stop")) {
        BgAudioManagerClient.getInst().stop(taskListener);
        return;
      } 
      if (TextUtils.equals(str, "seek")) {
        int i = jSONObject.optInt("currentTime");
        BgAudioManagerClient.getInst().seek(i, taskListener);
        return;
      } 
      callbackFail(a.b("operationType"));
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiOperateBgAudioCtrl", "act", exception);
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "operateBgAudio";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\bg\ApiOperateBgAudioCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */