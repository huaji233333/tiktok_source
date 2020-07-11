package com.tt.miniapp.util;

import com.tt.miniapphost.AppBrandLogger;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringUtils {
  public static String newString(byte[] paramArrayOfbyte) {
    return newString(paramArrayOfbyte, "UTF-8");
  }
  
  public static String newString(byte[] paramArrayOfbyte, String paramString) {
    try {
      return new String(paramArrayOfbyte, paramString);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      StringBuilder stringBuilder = new StringBuilder("new String 编码错误:");
      stringBuilder.append(paramString);
      AppBrandLogger.eWithThrowable("StringUtils", stringBuilder.toString(), unsupportedEncodingException);
      return "";
    } 
  }
  
  public static String newString(byte[] paramArrayOfbyte, Charset paramCharset) {
    if (paramCharset == null)
      paramCharset = StandardCharsets.UTF_8; 
    return newString(paramArrayOfbyte, paramCharset.name());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */