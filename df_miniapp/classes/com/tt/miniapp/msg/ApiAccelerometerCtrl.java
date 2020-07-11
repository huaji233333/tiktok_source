package com.tt.miniapp.msg;

import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.senser.AccelermeterManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.e.e;
import org.json.JSONObject;

@Deprecated
public class ApiAccelerometerCtrl extends b {
  private String functionName;
  
  public ApiAccelerometerCtrl(String paramString1, String paramString2, int paramInt, e parame) {
    super(paramString2, paramInt, parame);
    this.functionName = paramString1;
  }
  
  public void act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail(a.c("activity"));
      return;
    } 
    boolean bool = false;
    try {
      String str = (new JSONObject(this.mArgs)).optString("interval");
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
              AccelermeterManager.getInst((Context)miniappHostBase).setInterval(200);
            } else {
              AccelermeterManager.getInst((Context)miniappHostBase).setInterval(200);
            } 
          } else {
            AccelermeterManager.getInst((Context)miniappHostBase).setInterval(60);
          } 
        } else {
          AccelermeterManager.getInst((Context)miniappHostBase).setInterval(20);
        } 
      } else {
        AccelermeterManager.getInst((Context)miniappHostBase).setInterval(200);
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiAccelerometerCtrl", new Object[] { "act", exception });
    } 
    if (TextUtils.equals(this.functionName, "startAccelerometer")) {
      bool = AccelermeterManager.getInst((Context)miniappHostBase).open();
    } else if (TextUtils.equals(this.functionName, "stopAccelerometer")) {
      bool = AccelermeterManager.getInst((Context)miniappHostBase).close();
    } 
    if (bool) {
      callbackOk();
      return;
    } 
    callbackFail("sensor unsupport or disable");
  }
  
  public String getActionName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiAccelerometerCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */