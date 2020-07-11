package com.tt.miniapp.msg.audio.async;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

public class ApiCreateAudioInstanceCtrl extends b {
  private static AtomicInteger AUDIO_ID = new AtomicInteger(0);
  
  public ApiCreateAudioInstanceCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("audioId", AUDIO_ID.getAndIncrement());
      callbackOk(jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_ApiCreateAudioInstanceCtrl", "act", exception);
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "createAudioInstance";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\audio\async\ApiCreateAudioInstanceCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */