package com.tt.miniapp.webbridge.sync.map;

import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.a;

public class MapTempUtil {
  public static String makeFailMsg(String paramString1, String paramString2, int paramInt) {
    return ApiCallResult.a.a(paramString1, paramString2, paramInt).toString();
  }
  
  public static String makeFailMsg(String paramString, Throwable paramThrowable, int paramInt) {
    return ApiCallResult.a.a(paramString, a.a(paramThrowable), paramInt).toString();
  }
  
  public static class MapErrCode {}
  
  public static class MapErrMsg {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\map\MapTempUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */