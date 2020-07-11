package com.tt.miniapp;

import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.q.d;
import java.util.Map;
import okhttp3.ac;

public class RequestInceptUtil {
  public static String getRequestReferer() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getRequestRefere());
    if (ProcessUtil.isMiniappProcess()) {
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity != null && appInfoEntity.appId != null && appInfoEntity.version != null) {
        stringBuilder.append("?appid=");
        stringBuilder.append(appInfoEntity.appId);
        stringBuilder.append("&version=");
        stringBuilder.append(appInfoEntity.version);
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static void inceptRequest(ac.a parama) {
    parama.b("User-Agent");
    String str = ToolUtils.getCustomUA();
    AppBrandLogger.d("tma_RequestInceptUtil", new Object[] { "custom UA = ", str });
    parama.b("User-Agent", str);
    if (d.b())
      parama.b("referer"); 
    parama.b("referer", getRequestReferer());
  }
  
  public static void interceptRequest(Map<String, String> paramMap) {
    for (String str1 : paramMap.keySet()) {
      if (str1.equalsIgnoreCase("User-Agent"))
        paramMap.remove(str1); 
      if (str1.equalsIgnoreCase("referer") && d.b())
        paramMap.remove(str1); 
    } 
    String str = ToolUtils.getCustomUA();
    AppBrandLogger.d("tma_RequestInceptUtil", new Object[] { "custom UA = ", str });
    paramMap.put("User-Agent", str);
    paramMap.put("referer", getRequestReferer());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\RequestInceptUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */