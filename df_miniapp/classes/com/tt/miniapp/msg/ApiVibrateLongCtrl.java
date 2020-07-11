package com.tt.miniapp.msg;

import android.os.Vibrator;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;

public class ApiVibrateLongCtrl extends b {
  public ApiVibrateLongCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    Vibrator vibrator = (Vibrator)AppbrandContext.getInst().getApplicationContext().getSystemService("vibrator");
    if (vibrator != null && vibrator.hasVibrator()) {
      vibrator.vibrate(400L);
      callbackOk();
      return;
    } 
    callbackFail("vibrator disable");
  }
  
  public String getActionName() {
    return "vibrateLong";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiVibrateLongCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */