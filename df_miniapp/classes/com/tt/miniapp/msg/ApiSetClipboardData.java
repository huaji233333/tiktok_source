package com.tt.miniapp.msg;

import android.content.Context;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.util.ClipboardManagerUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import java.util.HashMap;
import org.json.JSONObject;

public class ApiSetClipboardData extends b {
  public ApiSetClipboardData(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("data");
      ClipboardManagerUtil.set(str, (Context)AppbrandContext.getInst().getApplicationContext());
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("data", str);
      callbackOk(a.a(hashMap));
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "setClipboardData";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiSetClipboardData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */