package com.tt.miniapp.util;

import android.content.Context;
import android.util.Base64;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RSAUtil {
  static PublicKey sPublicKey;
  
  public static String AESDecrypt(String paramString1, String paramString2, String paramString3) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(paramString2.getBytes("UTF-8"));
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString1.getBytes("UTF-8"), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(2, secretKeySpec, ivParameterSpec);
      return StringUtils.newString(cipher.doFinal(Base64.decode(paramString3, 0)));
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_RSAUtil", "AESDecrypt ", exception);
      return null;
    } 
  }
  
  public static String AESEncrypt(String paramString1, String paramString2, String paramString3) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(paramString2.getBytes("UTF-8"));
      SecretKeySpec secretKeySpec = new SecretKeySpec(paramString1.getBytes("UTF-8"), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(1, secretKeySpec, ivParameterSpec);
      return StringUtils.newString(Base64.encode(cipher.doFinal(paramString3.getBytes("UTF-8")), 0));
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_RSAUtil", "AESEncrypt ", exception);
      return null;
    } 
  }
  
  private static byte[] encrypt(PublicKey paramPublicKey, byte[] paramArrayOfbyte) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(1, paramPublicKey);
    return cipher.doFinal(paramArrayOfbyte);
  }
  
  public static byte[] encryptContent(Context paramContext, String paramString) {
    try {
      PublicKey publicKey = getPublicKey(paramContext);
      try {
        return encrypt(publicKey, paramString.getBytes());
      } catch (Exception null) {}
    } catch (Exception exception) {
      paramContext = null;
    } 
    AppBrandLogger.e("tma_RSAUtil", new Object[] { "encryptContent fail. PublicKey: ", paramContext, " error: ", exception });
    AppBrandMonitor.statusRate("mp_start_error", 1025, null);
    return null;
  }
  
  private static PublicKey getPublicKey(Context paramContext) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    if (sPublicKey == null)
      sPublicKey = readPublicKey(paramContext); 
    return sPublicKey;
  }
  
  private static PublicKey readPublicKey(Context paramContext) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEwerqo7Lxy2YAAxSh6MW9/hykusVgDsPB8ggNMUEf6yIJ6rFZAkXlfulor5siILeYkgv8E/XiKFUmwYOAjeqVjIfBNPCkvsWoGLpHLBYAerUousjh/6A8b2GNP6Z2KXpW3bjbtOPzlscPmv/bKsAupE811J7mlwtbufAtvCHBtQIDAQAB", 2));
    return KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\RSAUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */