package com.facebook.react.modules.i18nmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.e.f;
import com.ss.android.ugc.aweme.keva.d;
import java.util.Locale;

public class I18nUtil {
  private static I18nUtil sharedI18nUtilInstance;
  
  public static I18nUtil getInstance() {
    if (sharedI18nUtilInstance == null)
      sharedI18nUtilInstance = new I18nUtil(); 
    return sharedI18nUtilInstance;
  }
  
  private boolean isDevicePreferredLanguageRTL() {
    return (f.a(Locale.getDefault()) == 1);
  }
  
  private boolean isPrefSet(Context paramContext, String paramString, boolean paramBoolean) {
    return d.a(paramContext, "com.facebook.react.modules.i18nmanager.I18nUtil", 0).getBoolean(paramString, paramBoolean);
  }
  
  private boolean isRTLAllowed(Context paramContext) {
    return isPrefSet(paramContext, "RCTI18nUtil_allowRTL", true);
  }
  
  private boolean isRTLForced(Context paramContext) {
    return isPrefSet(paramContext, "RCTI18nUtil_forceRTL", false);
  }
  
  private void setPref(Context paramContext, String paramString, boolean paramBoolean) {
    SharedPreferences.Editor editor = d.a(paramContext, "com.facebook.react.modules.i18nmanager.I18nUtil", 0).edit();
    editor.putBoolean(paramString, paramBoolean);
    editor.apply();
  }
  
  public void allowRTL(Context paramContext, boolean paramBoolean) {
    setPref(paramContext, "RCTI18nUtil_allowRTL", paramBoolean);
  }
  
  public boolean doLeftAndRightSwapInRTL(Context paramContext) {
    return isPrefSet(paramContext, "RCTI18nUtil_makeRTLFlipLeftAndRightStyles", true);
  }
  
  public void forceRTL(Context paramContext, boolean paramBoolean) {
    setPref(paramContext, "RCTI18nUtil_forceRTL", paramBoolean);
  }
  
  public boolean isRTL(Context paramContext) {
    return isRTLForced(paramContext) ? true : ((isRTLAllowed(paramContext) && isDevicePreferredLanguageRTL()));
  }
  
  public void swapLeftAndRightInRTL(Context paramContext, boolean paramBoolean) {
    setPref(paramContext, "RCTI18nUtil_makeRTLFlipLeftAndRightStyles", paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\i18nmanager\I18nUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */