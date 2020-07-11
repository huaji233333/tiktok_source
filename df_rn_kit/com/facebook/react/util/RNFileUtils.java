package com.facebook.react.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class RNFileUtils {
  public static String base64Md5(String paramString) {
    if (paramString == null)
      return ""; 
    try {
      return base64Md5(paramString.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return "";
    } 
  }
  
  public static String base64Md5(byte[] paramArrayOfbyte) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(paramArrayOfbyte);
      return Base64.encodeToString(messageDigest.digest(), 2);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      return "";
    } 
  }
  
  public static void extractSo(String paramString1, String paramString2) throws IOException {
    ZipFile zipFile = new ZipFile(paramString1);
    ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(paramString1)));
    while (true) {
      ZipEntry zipEntry = zipInputStream.getNextEntry();
      if (zipEntry != null) {
        if (!zipEntry.isDirectory() && zipEntry.getName().contains("lib/armeabi/") && zipEntry.getName().contains("reactnativejni")) {
          String[] arrayOfString = zipEntry.getName().split("/");
          String str = arrayOfString[arrayOfString.length - 1];
          InputStream inputStream = zipFile.getInputStream(zipEntry);
          byte[] arrayOfByte = new byte[1024];
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramString2);
          stringBuilder.append("/");
          stringBuilder.append(str);
          FileOutputStream fileOutputStream = new FileOutputStream(stringBuilder.toString());
          while (inputStream.read(arrayOfByte) != -1)
            fileOutputStream.write(arrayOfByte); 
          fileOutputStream.close();
        } 
        continue;
      } 
      zipInputStream.closeEntry();
      return;
    } 
  }
  
  public static String loadAsset(String paramString, Context paramContext) {
    if (paramContext == null || TextUtils.isEmpty(paramString))
      return null; 
    try {
      return readStreamToString(paramContext.getAssets().open(paramString));
    } catch (IOException iOException) {
      iOException.printStackTrace();
      return "";
    } 
  }
  
  public static String loadFileOrAsset(String paramString, Context paramContext) {
    if (!TextUtils.isEmpty(paramString)) {
      File file = new File(paramString);
      if (file.exists()) {
        try {
          return readStreamToString(new FileInputStream(file));
        } catch (FileNotFoundException fileNotFoundException) {
          fileNotFoundException.printStackTrace();
        } 
      } else {
        return loadAsset((String)fileNotFoundException, paramContext);
      } 
    } 
    return "";
  }
  
  public static boolean loadSplitJsBundle(String paramString) {
    return (paramString != null && paramString.startsWith("\"use splitCommon:true\";"));
  }
  
  public static String md5(String paramString) {
    if (paramString == null)
      return ""; 
    try {
      return md5(paramString.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      return "";
    } 
  }
  
  public static String md5(byte[] paramArrayOfbyte) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(paramArrayOfbyte);
      return (new BigInteger(1, messageDigest.digest())).toString(16);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      return "";
    } 
  }
  
  private static String readStreamToString(InputStream paramInputStream) {
    // Byte code:
    //   0: aconst_null
    //   1: astore #5
    //   3: aconst_null
    //   4: astore_3
    //   5: aload_3
    //   6: astore_2
    //   7: new java/lang/StringBuilder
    //   10: dup
    //   11: aload_0
    //   12: invokevirtual available : ()I
    //   15: bipush #10
    //   17: iadd
    //   18: invokespecial <init> : (I)V
    //   21: astore #4
    //   23: aload_3
    //   24: astore_2
    //   25: new java/io/BufferedReader
    //   28: dup
    //   29: new java/io/InputStreamReader
    //   32: dup
    //   33: aload_0
    //   34: invokespecial <init> : (Ljava/io/InputStream;)V
    //   37: invokespecial <init> : (Ljava/io/Reader;)V
    //   40: astore_3
    //   41: sipush #4096
    //   44: newarray char
    //   46: astore_2
    //   47: aload_3
    //   48: aload_2
    //   49: invokevirtual read : ([C)I
    //   52: istore_1
    //   53: iload_1
    //   54: ifle -> 69
    //   57: aload #4
    //   59: aload_2
    //   60: iconst_0
    //   61: iload_1
    //   62: invokevirtual append : ([CII)Ljava/lang/StringBuilder;
    //   65: pop
    //   66: goto -> 47
    //   69: aload #4
    //   71: invokevirtual toString : ()Ljava/lang/String;
    //   74: astore_2
    //   75: aload_3
    //   76: invokevirtual close : ()V
    //   79: goto -> 82
    //   82: aload_0
    //   83: ifnull -> 90
    //   86: aload_0
    //   87: invokevirtual close : ()V
    //   90: aload_2
    //   91: areturn
    //   92: astore_2
    //   93: goto -> 147
    //   96: astore_2
    //   97: aload_2
    //   98: astore #4
    //   100: goto -> 118
    //   103: astore #4
    //   105: aload_2
    //   106: astore_3
    //   107: aload #4
    //   109: astore_2
    //   110: goto -> 147
    //   113: astore #4
    //   115: aload #5
    //   117: astore_3
    //   118: aload_3
    //   119: astore_2
    //   120: aload #4
    //   122: invokevirtual printStackTrace : ()V
    //   125: aload_3
    //   126: ifnull -> 136
    //   129: aload_3
    //   130: invokevirtual close : ()V
    //   133: goto -> 136
    //   136: aload_0
    //   137: ifnull -> 144
    //   140: aload_0
    //   141: invokevirtual close : ()V
    //   144: ldc ''
    //   146: areturn
    //   147: aload_3
    //   148: ifnull -> 158
    //   151: aload_3
    //   152: invokevirtual close : ()V
    //   155: goto -> 158
    //   158: aload_0
    //   159: ifnull -> 166
    //   162: aload_0
    //   163: invokevirtual close : ()V
    //   166: goto -> 171
    //   169: aload_2
    //   170: athrow
    //   171: goto -> 169
    //   174: astore_3
    //   175: goto -> 82
    //   178: astore_0
    //   179: aload_2
    //   180: areturn
    //   181: astore_2
    //   182: goto -> 136
    //   185: astore_0
    //   186: goto -> 144
    //   189: astore_3
    //   190: goto -> 158
    //   193: astore_0
    //   194: goto -> 166
    // Exception table:
    //   from	to	target	type
    //   7	23	113	java/io/IOException
    //   7	23	103	finally
    //   25	41	113	java/io/IOException
    //   25	41	103	finally
    //   41	47	96	java/io/IOException
    //   41	47	92	finally
    //   47	53	96	java/io/IOException
    //   47	53	92	finally
    //   57	66	96	java/io/IOException
    //   57	66	92	finally
    //   69	75	96	java/io/IOException
    //   69	75	92	finally
    //   75	79	174	java/io/IOException
    //   86	90	178	java/io/IOException
    //   120	125	103	finally
    //   129	133	181	java/io/IOException
    //   140	144	185	java/io/IOException
    //   151	155	189	java/io/IOException
    //   162	166	193	java/io/IOException
  }
  
  public static boolean saveFile(String paramString, byte[] paramArrayOfbyte, Context paramContext) {
    if (!TextUtils.isEmpty(paramString) && paramArrayOfbyte != null) {
      if (paramContext == null)
        return false; 
      Exception exception = null;
      paramContext = null;
      try {
      
      } catch (Exception exception1) {
      
      } finally {
        Context context = paramContext;
        if (context != null)
          try {
            context.close();
          } catch (IOException iOException) {
            iOException.printStackTrace();
          }  
      } 
      if (paramString != null)
        try {
          paramString.close();
          return false;
        } catch (IOException iOException) {
          iOException.printStackTrace();
        }  
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\util\RNFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */