package com.tt.miniapp.msg.audio.async;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiOperateAudioCtrl extends b {
  public ApiOperateAudioCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      int i = jSONObject.optInt("audioId");
      String str = jSONObject.optString("operationType");
      AudioManager.TaskListener taskListener = new AudioManager.TaskListener() {
          public void onFail(String param1String, Throwable param1Throwable) {
            ApiOperateAudioCtrl.this.callbackFail(param1String, param1Throwable);
          }
          
          public void onSuccess() {
            ApiOperateAudioCtrl.this.callbackOk();
          }
        };
      if (TextUtils.equals(str, "play")) {
        AudioManager.getInst().play(i, taskListener);
        return;
      } 
      if (TextUtils.equals(str, "pause")) {
        AudioManager.getInst().pause(i, taskListener);
        return;
      } 
      if (TextUtils.equals(str, "stop")) {
        AudioManager.getInst().stop(i, taskListener);
        return;
      } 
      if (TextUtils.equals(str, "seek")) {
        AudioManager.getInst().seek(i, jSONObject.optInt("currentTime"), taskListener);
        return;
      } 
      callbackFail(a.b("operationType"));
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiOperateAudioCtrl", "act", exception);
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "operateAudio";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\async\ApiOperateAudioCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */