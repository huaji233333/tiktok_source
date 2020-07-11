package com.tt.miniapp.streamloader;

import android.text.TextUtils;
import com.tt.miniapp.RequestInceptUtil;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.net.interceptor.BrotliPkgResponseInterceptor;
import java.io.IOException;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.u;
import okhttp3.y;

public class StreamLoaderUtils {
  private static y addCustomInterceptor(y paramy, String paramString) {
    y.a a = paramy.c();
    if (!TextUtils.isEmpty(paramString) && TextUtils.equals(paramString, "br"))
      a.a((u)new BrotliPkgResponseInterceptor()); 
    return a.a();
  }
  
  private static String getPkgCompressTypeFromUrlSuffix(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    int i = paramString.lastIndexOf('.');
    return (i < 0) ? null : paramString.substring(i);
  }
  
  public static ae getPkgResponseFromOffset(String paramString1, long paramLong, String paramString2) throws IOException {
    ac.a a = (new ac.a()).a(paramString1);
    RequestInceptUtil.inceptRequest(a);
    if (paramLong > 0L) {
      StringBuilder stringBuilder = new StringBuilder("bytes=");
      stringBuilder.append(paramLong);
      stringBuilder.append("-");
      a.b("Range", stringBuilder.toString());
    } 
    ac ac = a.c();
    return addCustomInterceptor(NetBus.sOkHttpStreamDownloadClient, paramString2).a(ac).b();
  }
  
  public static ae getResponse(String paramString1, String paramString2) throws IOException {
    ac.a a = (new ac.a()).a(paramString1);
    RequestInceptUtil.inceptRequest(a);
    ac ac = a.c();
    return addCustomInterceptor(NetBus.sOkHttpStreamDownloadClient, paramString2).a(ac).b();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\StreamLoaderUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */