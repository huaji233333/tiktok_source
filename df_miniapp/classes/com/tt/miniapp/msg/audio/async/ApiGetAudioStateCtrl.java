package com.tt.miniapp.msg.audio.async;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiGetAudioStateCtrl extends b {
  public ApiGetAudioStateCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      int i = (new JSONObject(this.mArgs)).optInt("audioId");
      AppBrandLogger.d("tma_ApiGetAudioStateCtrl", new Object[] { "audioId ", Integer.valueOf(i) });
      ApiErrorInfoEntity apiErrorInfoEntity = new ApiErrorInfoEntity();
      AudioManager.AudioState audioState = AudioManager.getInst().getAudioState(i, apiErrorInfoEntity);
      if (audioState == null || audioState.duration < 0L) {
        if (audioState != null) {
          AppBrandLogger.d("tma_ApiGetAudioStateCtrl", new Object[] { "audioState.duration < 0 ", Long.valueOf(audioState.duration) });
          apiErrorInfoEntity.append("audioState.duration == ").append(Long.valueOf(audioState.duration));
        } 
        callbackFail(apiErrorInfoEntity.getErrorMsg(), apiErrorInfoEntity.getThrowable());
        return;
      } 
      AppBrandLogger.d("tma_ApiGetAudioStateCtrl", new Object[] { "audioState.currentTime ", Long.valueOf(audioState.currentTime) });
      String str2 = audioState.src;
      String str1 = FileManager.inst().getSchemaFilePath(str2);
      AppBrandLogger.d("tma_ApiGetAudioStateCtrl", new Object[] { "schemaUrl ", str1, " ", str2 });
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("src", str1);
      jSONObject.put("startTime", audioState.startTime);
      jSONObject.put("paused", audioState.paused);
      jSONObject.put("currentTime", audioState.currentTime);
      jSONObject.put("duration", audioState.duration);
      jSONObject.put("obeyMuteSwitch", audioState.obeyMuteSwitch);
      jSONObject.put("buffered", audioState.buffered);
      jSONObject.put("loop", audioState.loop);
      jSONObject.put("autoplay", audioState.autoplay);
      jSONObject.put("volume", audioState.volume);
      callbackOk(jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiGetAudioStateCtrl", "act", exception);
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "getAudioState";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\async\ApiGetAudioStateCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */