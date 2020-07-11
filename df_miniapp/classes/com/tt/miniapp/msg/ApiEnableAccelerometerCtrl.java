package com.tt.miniapp.msg;

import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.senser.AccelermeterManager;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiEnableAccelerometerCtrl extends b {
  public ApiEnableAccelerometerCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("interval");
      boolean bool = jSONObject.optBoolean("enable");
      if (!TextUtils.isEmpty(str)) {
        byte b1 = -1;
        int i = str.hashCode();
        if (i != -1039745817) {
          if (i != 3732) {
            if (i == 3165170 && str.equals("game"))
              b1 = 0; 
          } else if (str.equals("ui")) {
            b1 = 1;
          } 
        } else if (str.equals("normal")) {
          b1 = 2;
        } 
        if (b1 != 0) {
          if (b1 != 1) {
            if (b1 != 2) {
              AccelermeterManager.getInst((Context)AppbrandContext.getInst().getApplicationContext()).setInterval(200);
            } else {
              AccelermeterManager.getInst((Context)AppbrandContext.getInst().getApplicationContext()).setInterval(200);
            } 
          } else {
            AccelermeterManager.getInst((Context)AppbrandContext.getInst().getApplicationContext()).setInterval(60);
          } 
        } else {
          AccelermeterManager.getInst((Context)AppbrandContext.getInst().getApplicationContext()).setInterval(20);
        } 
      } else {
        AccelermeterManager.getInst((Context)AppbrandContext.getInst().getApplicationContext()).setInterval(200);
      } 
      if (bool) {
        bool = AccelermeterManager.getInst((Context)AppbrandContext.getInst().getApplicationContext()).open();
      } else {
        bool = AccelermeterManager.getInst((Context)AppbrandContext.getInst().getApplicationContext()).close();
      } 
      if (bool) {
        callbackOk();
        return;
      } 
      callbackFail("sensor unsupport or disable");
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "enableAccelerometer";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiEnableAccelerometerCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */