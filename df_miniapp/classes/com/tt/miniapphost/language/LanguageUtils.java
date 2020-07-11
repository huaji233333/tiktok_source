package com.tt.miniapphost.language;

import android.content.Context;
import android.content.res.Configuration;
import com.tt.miniapphost.AppBrandLogger;
import java.util.Locale;

public class LanguageUtils {
  public static String appendLanguageQueryParam() {
    Locale locale = LocaleManager.getInst().getCurrentLocale();
    if (locale == null)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder("language=");
    stringBuilder.append(locale.getLanguage());
    String str = stringBuilder.toString();
    stringBuilder = new StringBuilder("append query lang:");
    stringBuilder.append(str);
    AppBrandLogger.d("LanguageUtils", new Object[] { stringBuilder.toString() });
    return str;
  }
  
  public static Locale getStartLocaleConfig() {
    Locale locale = LocaleManager.getInst().getCurrentHostSetLocale();
    return (locale != null) ? locale : null;
  }
  
  private static void setLocaleToContext(Context paramContext, Locale paramLocale) {
    if (paramLocale == null)
      return; 
    if (paramContext == null)
      return; 
    Configuration configuration = paramContext.getResources().getConfiguration();
    if (configuration == null)
      return; 
    configuration.locale = paramLocale;
    configuration.setLocale(paramLocale);
    paramContext.getResources().updateConfiguration(configuration, paramContext.getResources().getDisplayMetrics());
  }
  
  public static void updateResourceLocale(Context paramContext) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\language\LanguageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */