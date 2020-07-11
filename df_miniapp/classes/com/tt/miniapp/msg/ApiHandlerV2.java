package com.tt.miniapp.msg;

import android.text.TextUtils;
import android.util.Log;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.f;
import com.tt.option.e.e;

public abstract class ApiHandlerV2 extends b {
  public f mApiParams;
  
  public ApiHandlerV2(f paramf, int paramInt, e parame) {
    super("", paramInt, parame);
    this.mApiParams = paramf;
  }
  
  public String makeErrMsg(boolean paramBoolean, String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    boolean bool = TextUtils.isEmpty(paramString);
    String str = "ok";
    if (bool) {
      paramString = getActionName();
      if (!paramBoolean)
        str = "fail"; 
      stringBuffer.append(buildErrorMsg(paramString, str));
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      String str1 = getActionName();
      if (!paramBoolean)
        str = "fail"; 
      stringBuilder.append(buildErrorMsg(str1, str));
      stringBuilder.append(" ");
      stringBuilder.append(paramString);
      stringBuffer.append(stringBuilder.toString());
    } 
    return stringBuffer.toString();
  }
  
  public String makeErrStack(Throwable paramThrowable) {
    return (paramThrowable != null) ? Log.getStackTraceString(paramThrowable) : "";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiHandlerV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */