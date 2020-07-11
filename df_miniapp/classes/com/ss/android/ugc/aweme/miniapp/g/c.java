package com.ss.android.ugc.aweme.miniapp.g;

import android.content.Context;
import com.ss.android.ugc.aweme.dfbase.c.f;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.option.k.a;
import com.tt.option.k.b;
import java.util.Locale;

public class c extends a {
  public final b a() {
    return super.a();
  }
  
  public boolean doAppBundleSplitInstallAction(Context paramContext) {
    f.a(paramContext);
    return true;
  }
  
  public Locale getInitLocale() {
    Locale locale = MiniAppService.inst().getCurrentLocale();
    return (locale != null) ? locale : Locale.ENGLISH;
  }
  
  public boolean isEnableAppBundleMode() {
    return true;
  }
  
  public boolean isEnableI18NRequestRefer() {
    return true;
  }
  
  public boolean isEnableI18nNetRequest() {
    return true;
  }
  
  public String replaceMicroAppCallName() {
    return "Feature";
  }
  
  public String replaceOpenApiDomain() {
    return "https://developer-sg.byteoversea.com";
  }
  
  public String replaceSnssdkApiDomain() {
    return "https://sg-content.bytedance.net";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */