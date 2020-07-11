package com.tt.miniapp.msg.audio.async;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiDestroyAudioInstanceCtrl extends b {
  public ApiDestroyAudioInstanceCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      int i = (new JSONObject(this.mArgs)).optInt("audioId");
      ApiErrorInfoEntity apiErrorInfoEntity = new ApiErrorInfoEntity();
      if (AudioManager.getInst().releaseAudio(i, apiErrorInfoEntity)) {
        callbackOk();
        return;
      } 
      callbackFail(apiErrorInfoEntity.getErrorMsg(), apiErrorInfoEntity.getThrowable());
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiDestroyAudioInstanceCtrl", "act", exception);
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "destroyAudioInstance";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\async\ApiDestroyAudioInstanceCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */