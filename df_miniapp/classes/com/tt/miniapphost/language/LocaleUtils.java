package com.tt.miniapphost.language;

import android.text.TextUtils;
import java.util.Locale;

public class LocaleUtils {
  public static boolean isValidLocaleStr(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    String[] arrayOfString = paramString.split("_");
    return (arrayOfString.length == 2 && !TextUtils.isEmpty(arrayOfString[0]) && !TextUtils.isEmpty(arrayOfString[1]));
  }
  
  public static String locale2String(Locale paramLocale) {
    if (paramLocale == null)
      return null; 
    if (paramLocale.toString().equalsIgnoreCase("zh_TW") || paramLocale.toString().equalsIgnoreCase("zh_TW_#Hant"))
      return "zh-Hant_TW"; 
    if (paramLocale.toString().equalsIgnoreCase("zh_HK") || paramLocale.toString().equalsIgnoreCase("zh_HK_#Hant"))
      return "zh-Hant_HK"; 
    String str2 = paramLocale.getLanguage();
    String str1 = paramLocale.getCountry();
    StringBuilder stringBuilder = new StringBuilder(str2);
    if (!TextUtils.isEmpty(str1)) {
      stringBuilder.append("_");
      stringBuilder.append(str1);
    } 
    return stringBuilder.toString();
  }
  
  public static Locale string2Locale(String paramString) {
    String[] arrayOfString;
    if (TextUtils.isEmpty(paramString))
      return null; 
    if (isValidLocaleStr(paramString)) {
      arrayOfString = paramString.split("_");
      return arrayOfString[0].equalsIgnoreCase("zh-hant") ? new Locale("zh", arrayOfString[1], "#Hant") : new Locale(arrayOfString[0], arrayOfString[1]);
    } 
    return new Locale((String)arrayOfString, "");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\language\LocaleUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */