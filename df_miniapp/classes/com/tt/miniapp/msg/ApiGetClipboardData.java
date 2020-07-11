package com.tt.miniapp.msg;

import android.content.Context;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.util.ClipboardManagerUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import java.util.HashMap;

public class ApiGetClipboardData extends b {
  public ApiGetClipboardData(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    String str = ClipboardManagerUtil.get((Context)AppbrandContext.getInst().getApplicationContext());
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("data", str);
    callbackOk(a.a(hashMap));
  }
  
  public String getActionName() {
    return "getClipboardData";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetClipboardData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */