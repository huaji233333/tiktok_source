package com.tt.miniapp.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.tt.miniapphost.AppBrandLogger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SaftyUtil {
  public static String AESDecrypt(String paramString1, String paramString2, String paramString3) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(paramString2.getBytes(StandardCharsets.UTF_8));
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString1.getBytes(StandardCharsets.UTF_8), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(2, secretKeySpec, ivParameterSpec);
      return StringUtils.newString(cipher.doFinal(Base64.decode(paramString3, 0)));
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("safetyUtil", "AESDecrypt ", exception);
      return null;
    } 
  }
  
  public static String AESEncrypt(String paramString1, String paramString2, String paramString3) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(paramString2.getBytes(StandardCharsets.UTF_8));
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString1.getBytes(StandardCharsets.UTF_8), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(1, secretKeySpec, ivParameterSpec);
      return Base64.encodeToString(cipher.doFinal(paramString3.getBytes()), 2);
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("safetyUtil", "AESEncrypt ", exception);
      return null;
    } 
  }
  
  public static String RandomString(int paramInt) {
    Random random = new Random();
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < paramInt; i++)
      stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62))); 
    return stringBuffer.toString();
  }
  
  public static byte[] encryptHmacSHA1(String paramString1, String paramString2) {
    try {
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString1.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(secretKeySpec);
      return mac.doFinal(paramString2.getBytes(StandardCharsets.UTF_8));
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
    
    } catch (InvalidKeyException invalidKeyException) {}
    AppBrandLogger.e("safetyUtil", new Object[] { invalidKeyException });
    return null;
  }
  
  public static String genRandomString() {
    return RandomString(16);
  }
  
  public static String generateTTCode(Context paramContext, String paramString1, String paramString2) {
    String str1;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString1);
    stringBuilder2.append("#");
    stringBuilder2.append(paramString2);
    String str2 = stringBuilder2.toString();
    paramString1 = null;
    int i = 2;
    while (true) {
      str1 = paramString1;
      if (i >= 0) {
        byte[] arrayOfByte = RSAUtil.encryptContent(paramContext, str2);
        paramString2 = paramString1;
        if (arrayOfByte != null) {
          paramString2 = paramString1;
          if (arrayOfByte.length > 0)
            paramString2 = Base64.encodeToString(arrayOfByte, 2); 
        } 
        str1 = paramString2;
        if (TextUtils.isEmpty(paramString2)) {
          i--;
          paramString1 = paramString2;
          continue;
        } 
      } 
      break;
    } 
    StringBuilder stringBuilder1 = new StringBuilder("generateTTCode = ");
    stringBuilder1.append(str1);
    AppBrandLogger.d("safetyUtil", new Object[] { stringBuilder1.toString() });
    return str1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\SaftyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */