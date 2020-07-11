package com.tt.miniapp.msg.audio.sync;

import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import org.json.JSONObject;

public class GetAudioStateSyncCtrl extends SyncMsgCtrl {
  public GetAudioStateSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      int i = (new JSONObject(this.mParams)).optInt("audioId");
      ApiErrorInfoEntity apiErrorInfoEntity = new ApiErrorInfoEntity();
      AudioManager.AudioState audioState = AudioManager.getInst().getAudioState(i, apiErrorInfoEntity);
      if (audioState == null || audioState.duration < 0L) {
        if (audioState != null) {
          AppBrandLogger.d("tma_GetAudioStateSyncCtrl", new Object[] { "audioState.duration < 0 ", Long.valueOf(audioState.duration) });
          apiErrorInfoEntity.append("audioState.duration == ").append(Long.valueOf(audioState.duration));
        } 
        return makeMsg(false, null, apiErrorInfoEntity.getErrorMsg(), apiErrorInfoEntity.getThrowable());
      } 
      AppBrandLogger.d("tma_GetAudioStateSyncCtrl", new Object[] { "audioState.duration ", Long.valueOf(audioState.duration) });
      String str2 = audioState.src;
      String str1 = FileManager.inst().getSchemaFilePath(str2);
      AppBrandLogger.d("tma_GetAudioStateSyncCtrl", new Object[] { "schemaUrl ", str1, " ", str2 });
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("src", str1);
      jSONObject.put("startTime", audioState.startTime);
      jSONObject.put("paused", audioState.paused);
      jSONObject.put("currentTime", audioState.currentTime);
      jSONObject.put("duration", audioState.duration);
      jSONObject.put("obeyMuteSwitch", audioState.obeyMuteSwitch);
      jSONObject.put("buffered", audioState.buffered);
      return makeOkMsg(jSONObject);
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_GetAudioStateSyncCtrl", "act", exception);
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "getAudioStateSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\sync\GetAudioStateSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */