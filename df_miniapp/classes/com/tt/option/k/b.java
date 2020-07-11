package com.tt.option.k;

import android.content.Context;
import java.util.Locale;

public interface b {
  boolean doAppBundleSplitInstallAction(Context paramContext);
  
  Locale getInitLocale();
  
  boolean isEnableAppBundleMode();
  
  boolean isEnableI18NRequestRefer();
  
  boolean isEnableI18nNetRequest();
  
  String replaceMicroAppCallName();
  
  String replaceOpenApiDomain();
  
  String replaceSnssdkApiDomain();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\k\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */