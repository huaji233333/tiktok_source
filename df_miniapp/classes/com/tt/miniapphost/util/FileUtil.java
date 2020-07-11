package com.tt.miniapphost.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
  public static String calculateMD5(File paramFile) {
    // Byte code:
    //   0: ldc 'MD5'
    //   2: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
    //   5: astore_2
    //   6: new java/io/FileInputStream
    //   9: dup
    //   10: aload_0
    //   11: invokespecial <init> : (Ljava/io/File;)V
    //   14: astore_0
    //   15: sipush #1024
    //   18: newarray byte
    //   20: astore_3
    //   21: aload_0
    //   22: aload_3
    //   23: invokevirtual read : ([B)I
    //   26: istore_1
    //   27: iload_1
    //   28: ifle -> 41
    //   31: aload_2
    //   32: aload_3
    //   33: iconst_0
    //   34: iload_1
    //   35: invokevirtual update : ([BII)V
    //   38: goto -> 21
    //   41: ldc '%32s'
    //   43: iconst_1
    //   44: anewarray java/lang/Object
    //   47: dup
    //   48: iconst_0
    //   49: new java/math/BigInteger
    //   52: dup
    //   53: iconst_1
    //   54: aload_2
    //   55: invokevirtual digest : ()[B
    //   58: invokespecial <init> : (I[B)V
    //   61: bipush #16
    //   63: invokevirtual toString : (I)Ljava/lang/String;
    //   66: aastore
    //   67: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   70: bipush #32
    //   72: bipush #48
    //   74: invokevirtual replace : (CC)Ljava/lang/String;
    //   77: astore_2
    //   78: aload_0
    //   79: invokevirtual close : ()V
    //   82: aload_2
    //   83: areturn
    //   84: astore_0
    //   85: ldc 'FileUtil'
    //   87: iconst_2
    //   88: anewarray java/lang/Object
    //   91: dup
    //   92: iconst_0
    //   93: ldc 'Exception on closing MD5 input stream'
    //   95: aastore
    //   96: dup
    //   97: iconst_1
    //   98: aload_0
    //   99: aastore
    //   100: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   103: aload_2
    //   104: areturn
    //   105: astore_2
    //   106: goto -> 121
    //   109: astore_2
    //   110: new java/lang/RuntimeException
    //   113: dup
    //   114: ldc 'Unable to process file for MD5'
    //   116: aload_2
    //   117: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   120: athrow
    //   121: aload_0
    //   122: invokevirtual close : ()V
    //   125: goto -> 147
    //   128: astore_0
    //   129: ldc 'FileUtil'
    //   131: iconst_2
    //   132: anewarray java/lang/Object
    //   135: dup
    //   136: iconst_0
    //   137: ldc 'Exception on closing MD5 input stream'
    //   139: aastore
    //   140: dup
    //   141: iconst_1
    //   142: aload_0
    //   143: aastore
    //   144: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   147: aload_2
    //   148: athrow
    //   149: astore_0
    //   150: ldc 'FileUtil'
    //   152: iconst_2
    //   153: anewarray java/lang/Object
    //   156: dup
    //   157: iconst_0
    //   158: ldc 'Exception while getting FileInputStream'
    //   160: aastore
    //   161: dup
    //   162: iconst_1
    //   163: aload_0
    //   164: aastore
    //   165: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   168: aconst_null
    //   169: areturn
    //   170: astore_0
    //   171: ldc 'FileUtil'
    //   173: iconst_2
    //   174: anewarray java/lang/Object
    //   177: dup
    //   178: iconst_0
    //   179: ldc 'Exception while getting digest'
    //   181: aastore
    //   182: dup
    //   183: iconst_1
    //   184: aload_0
    //   185: aastore
    //   186: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   189: aconst_null
    //   190: areturn
    // Exception table:
    //   from	to	target	type
    //   0	6	170	java/security/NoSuchAlgorithmException
    //   6	15	149	java/io/FileNotFoundException
    //   21	27	109	java/io/IOException
    //   21	27	105	finally
    //   31	38	109	java/io/IOException
    //   31	38	105	finally
    //   41	78	109	java/io/IOException
    //   41	78	105	finally
    //   78	82	84	java/io/IOException
    //   110	121	105	finally
    //   121	125	128	java/io/IOException
  }
  
  public static boolean checkMD5(String paramString, File paramFile) {
    if (TextUtils.isEmpty(paramString) || paramFile == null) {
      AppBrandLogger.e("FileUtil", new Object[] { "MD5 string empty or updateFile null" });
      return false;
    } 
    String str = calculateMD5(paramFile);
    if (str == null) {
      AppBrandLogger.e("FileUtil", new Object[] { "calculatedDigest null" });
      return false;
    } 
    AppBrandLogger.d("FileUtil", new Object[] { "Calculated digest: ", str });
    AppBrandLogger.d("FileUtil", new Object[] { "Provided digest: ", paramString });
    return str.toLowerCase().startsWith(paramString.toLowerCase());
  }
  
  public static void clearDir(File paramFile) {
    if (paramFile != null && paramFile.exists()) {
      if (!paramFile.isDirectory())
        return; 
      delete(paramFile);
    } 
  }
  
  public static void clearFileContent(File paramFile) {
    if (paramFile.exists() && paramFile.isFile())
      try {
        FileWriter fileWriter = new FileWriter(paramFile);
        fileWriter.write("");
        fileWriter.close();
        return;
      } catch (IOException iOException) {
        return;
      }  
  }
  
  public static void copyAssets(Context paramContext, String paramString1, String paramString2) {
    IOException iOException2;
    IOException iOException3;
    AssetManager assetManager = paramContext.getAssets();
    String str = null;
    FileOutputStream fileOutputStream = null;
    try {
      InputStream inputStream = assetManager.open(paramString1);
      try {
        File file = new File(paramString2);
        if (!file.getParentFile().exists())
          file.getParentFile().mkdirs(); 
        fileOutputStream = new FileOutputStream(file);
      } catch (IOException null) {
      
      } finally {
        paramString1 = null;
      } 
    } catch (IOException null) {
    
    } finally {
      paramString1 = null;
      assetManager = null;
    } 
    try {
      AppBrandLogger.stacktrace(6, "FileUtil", paramString2.getStackTrace());
      return;
    } finally {
      paramString1 = null;
      iOException2 = iOException1;
    } 
    if (iOException1 != null)
      try {
        iOException1.close();
      } catch (IOException iOException) {
        AppBrandLogger.stacktrace(6, "FileUtil", iOException.getStackTrace());
      }  
    if (iOException2 != null)
      try {
        iOException2.close();
      } catch (IOException iOException) {
        AppBrandLogger.stacktrace(6, "FileUtil", iOException.getStackTrace());
      }  
    throw paramString1;
  }
  
  public static int copyFile(File paramFile1, File paramFile2, boolean paramBoolean) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getParentFile : ()Ljava/io/File;
    //   4: invokevirtual exists : ()Z
    //   7: ifne -> 18
    //   10: aload_1
    //   11: invokevirtual getParentFile : ()Ljava/io/File;
    //   14: invokevirtual mkdirs : ()Z
    //   17: pop
    //   18: aconst_null
    //   19: astore #4
    //   21: aconst_null
    //   22: astore #6
    //   24: aload_1
    //   25: invokevirtual exists : ()Z
    //   28: ifne -> 36
    //   31: aload_1
    //   32: invokevirtual createNewFile : ()Z
    //   35: pop
    //   36: new java/io/FileInputStream
    //   39: dup
    //   40: aload_0
    //   41: invokespecial <init> : (Ljava/io/File;)V
    //   44: astore #5
    //   46: new java/io/FileOutputStream
    //   49: dup
    //   50: aload_1
    //   51: invokespecial <init> : (Ljava/io/File;)V
    //   54: astore #4
    //   56: sipush #4096
    //   59: newarray byte
    //   61: astore_1
    //   62: aload #5
    //   64: aload_1
    //   65: invokevirtual read : ([B)I
    //   68: istore_3
    //   69: iload_3
    //   70: ifle -> 84
    //   73: aload #4
    //   75: aload_1
    //   76: iconst_0
    //   77: iload_3
    //   78: invokevirtual write : ([BII)V
    //   81: goto -> 62
    //   84: iload_2
    //   85: ifeq -> 93
    //   88: aload_0
    //   89: invokevirtual delete : ()Z
    //   92: pop
    //   93: aload #5
    //   95: invokevirtual close : ()V
    //   98: goto -> 113
    //   101: astore_0
    //   102: bipush #6
    //   104: ldc 'FileUtil'
    //   106: aload_0
    //   107: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   110: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   113: aload #4
    //   115: invokevirtual close : ()V
    //   118: iconst_0
    //   119: ireturn
    //   120: astore_0
    //   121: bipush #6
    //   123: ldc 'FileUtil'
    //   125: aload_0
    //   126: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   129: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   132: iconst_0
    //   133: ireturn
    //   134: astore_0
    //   135: goto -> 146
    //   138: astore_1
    //   139: goto -> 159
    //   142: astore_0
    //   143: aconst_null
    //   144: astore #4
    //   146: aload #5
    //   148: astore_1
    //   149: aload_0
    //   150: astore #5
    //   152: goto -> 198
    //   155: astore_1
    //   156: aconst_null
    //   157: astore #4
    //   159: aload #5
    //   161: astore_0
    //   162: goto -> 185
    //   165: astore #5
    //   167: aconst_null
    //   168: astore_0
    //   169: aload #4
    //   171: astore_1
    //   172: aload_0
    //   173: astore #4
    //   175: goto -> 198
    //   178: astore_1
    //   179: aconst_null
    //   180: astore #4
    //   182: aload #6
    //   184: astore_0
    //   185: new java/lang/RuntimeException
    //   188: dup
    //   189: aload_1
    //   190: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   193: athrow
    //   194: astore #5
    //   196: aload_0
    //   197: astore_1
    //   198: aload_1
    //   199: ifnull -> 221
    //   202: aload_1
    //   203: invokevirtual close : ()V
    //   206: goto -> 221
    //   209: astore_0
    //   210: bipush #6
    //   212: ldc 'FileUtil'
    //   214: aload_0
    //   215: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   218: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   221: aload #4
    //   223: ifnull -> 246
    //   226: aload #4
    //   228: invokevirtual close : ()V
    //   231: goto -> 246
    //   234: astore_0
    //   235: bipush #6
    //   237: ldc 'FileUtil'
    //   239: aload_0
    //   240: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   243: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   246: goto -> 252
    //   249: aload #5
    //   251: athrow
    //   252: goto -> 249
    // Exception table:
    //   from	to	target	type
    //   24	36	178	java/lang/Exception
    //   24	36	165	finally
    //   36	46	178	java/lang/Exception
    //   36	46	165	finally
    //   46	56	155	java/lang/Exception
    //   46	56	142	finally
    //   56	62	138	java/lang/Exception
    //   56	62	134	finally
    //   62	69	138	java/lang/Exception
    //   62	69	134	finally
    //   73	81	138	java/lang/Exception
    //   73	81	134	finally
    //   88	93	138	java/lang/Exception
    //   88	93	134	finally
    //   93	98	101	java/lang/Exception
    //   113	118	120	java/lang/Exception
    //   185	194	194	finally
    //   202	206	209	java/lang/Exception
    //   226	231	234	java/lang/Exception
  }
  
  private static void copyFile(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
    byte[] arrayOfByte = new byte[4096];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (i != -1) {
        paramOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      break;
    } 
  }
  
  public static boolean copyFolder(File paramFile1, File paramFile2) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual isDirectory : ()Z
    //   4: ifeq -> 79
    //   7: aload_1
    //   8: invokevirtual exists : ()Z
    //   11: ifne -> 19
    //   14: aload_1
    //   15: invokevirtual mkdir : ()Z
    //   18: pop
    //   19: aload_0
    //   20: invokevirtual list : ()[Ljava/lang/String;
    //   23: astore #4
    //   25: aload #4
    //   27: arraylength
    //   28: istore_3
    //   29: iconst_0
    //   30: istore_2
    //   31: iload_2
    //   32: iload_3
    //   33: if_icmpge -> 77
    //   36: aload #4
    //   38: iload_2
    //   39: aaload
    //   40: astore #5
    //   42: new java/io/File
    //   45: dup
    //   46: aload_0
    //   47: aload #5
    //   49: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   52: new java/io/File
    //   55: dup
    //   56: aload_1
    //   57: aload #5
    //   59: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   62: invokestatic copyFolder : (Ljava/io/File;Ljava/io/File;)Z
    //   65: ifne -> 70
    //   68: iconst_0
    //   69: ireturn
    //   70: iload_2
    //   71: iconst_1
    //   72: iadd
    //   73: istore_2
    //   74: goto -> 31
    //   77: iconst_1
    //   78: ireturn
    //   79: aconst_null
    //   80: astore #5
    //   82: new java/io/FileInputStream
    //   85: dup
    //   86: aload_0
    //   87: invokespecial <init> : (Ljava/io/File;)V
    //   90: astore #4
    //   92: new java/io/FileOutputStream
    //   95: dup
    //   96: aload_1
    //   97: invokespecial <init> : (Ljava/io/File;)V
    //   100: astore_0
    //   101: sipush #1024
    //   104: newarray byte
    //   106: astore_1
    //   107: aload #4
    //   109: aload_1
    //   110: invokevirtual read : ([B)I
    //   113: istore_2
    //   114: iload_2
    //   115: ifle -> 128
    //   118: aload_0
    //   119: aload_1
    //   120: iconst_0
    //   121: iload_2
    //   122: invokevirtual write : ([BII)V
    //   125: goto -> 107
    //   128: aload #4
    //   130: invokevirtual close : ()V
    //   133: goto -> 148
    //   136: astore_1
    //   137: bipush #6
    //   139: ldc 'FileUtil'
    //   141: aload_1
    //   142: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   145: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   148: aload_0
    //   149: invokevirtual close : ()V
    //   152: iconst_1
    //   153: ireturn
    //   154: astore_0
    //   155: bipush #6
    //   157: ldc 'FileUtil'
    //   159: aload_0
    //   160: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   163: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   166: iconst_1
    //   167: ireturn
    //   168: astore_1
    //   169: goto -> 265
    //   172: astore_1
    //   173: goto -> 185
    //   176: astore_1
    //   177: aconst_null
    //   178: astore_0
    //   179: goto -> 265
    //   182: astore_1
    //   183: aconst_null
    //   184: astore_0
    //   185: goto -> 204
    //   188: astore_1
    //   189: aconst_null
    //   190: astore_0
    //   191: aload_0
    //   192: astore #4
    //   194: goto -> 265
    //   197: astore_1
    //   198: aconst_null
    //   199: astore_0
    //   200: aload #5
    //   202: astore #4
    //   204: bipush #6
    //   206: ldc 'FileUtil'
    //   208: aload_1
    //   209: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   212: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   215: aload #4
    //   217: ifnull -> 240
    //   220: aload #4
    //   222: invokevirtual close : ()V
    //   225: goto -> 240
    //   228: astore_1
    //   229: bipush #6
    //   231: ldc 'FileUtil'
    //   233: aload_1
    //   234: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   237: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   240: aload_0
    //   241: ifnull -> 262
    //   244: aload_0
    //   245: invokevirtual close : ()V
    //   248: iconst_0
    //   249: ireturn
    //   250: astore_0
    //   251: bipush #6
    //   253: ldc 'FileUtil'
    //   255: aload_0
    //   256: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   259: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   262: iconst_0
    //   263: ireturn
    //   264: astore_1
    //   265: aload #4
    //   267: ifnull -> 292
    //   270: aload #4
    //   272: invokevirtual close : ()V
    //   275: goto -> 292
    //   278: astore #4
    //   280: bipush #6
    //   282: ldc 'FileUtil'
    //   284: aload #4
    //   286: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   289: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   292: aload_0
    //   293: ifnull -> 315
    //   296: aload_0
    //   297: invokevirtual close : ()V
    //   300: goto -> 315
    //   303: astore_0
    //   304: bipush #6
    //   306: ldc 'FileUtil'
    //   308: aload_0
    //   309: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   312: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   315: goto -> 320
    //   318: aload_1
    //   319: athrow
    //   320: goto -> 318
    // Exception table:
    //   from	to	target	type
    //   82	92	197	java/io/IOException
    //   82	92	188	finally
    //   92	101	182	java/io/IOException
    //   92	101	176	finally
    //   101	107	172	java/io/IOException
    //   101	107	168	finally
    //   107	114	172	java/io/IOException
    //   107	114	168	finally
    //   118	125	172	java/io/IOException
    //   118	125	168	finally
    //   128	133	136	java/io/IOException
    //   148	152	154	java/io/IOException
    //   204	215	264	finally
    //   220	225	228	java/io/IOException
    //   244	248	250	java/io/IOException
    //   270	275	278	java/io/IOException
    //   296	300	303	java/io/IOException
  }
  
  public static void delete(File paramFile) {
    if (paramFile.isFile()) {
      paramFile.delete();
      return;
    } 
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      if (arrayOfFile == null || arrayOfFile.length == 0) {
        paramFile.delete();
        return;
      } 
      for (int i = 0; i < arrayOfFile.length; i++)
        delete(arrayOfFile[i]); 
      paramFile.delete();
      return;
    } 
  }
  
  public static long getDirSizeNonRecursive(File paramFile) {
    long l1 = 0L;
    long l2 = l1;
    if (paramFile != null) {
      l2 = l1;
      if (paramFile.exists()) {
        if (!paramFile.isDirectory())
          return 0L; 
        File[] arrayOfFile = paramFile.listFiles();
        int j = arrayOfFile.length;
        int i = 0;
        while (true) {
          l2 = l1;
          if (i < j) {
            l1 += arrayOfFile[i].length();
            i++;
            continue;
          } 
          break;
        } 
      } 
    } 
    return l2;
  }
  
  public static String getFileExtension(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    int i = paramString.lastIndexOf(".");
    return (i > 0) ? paramString.substring(i) : "";
  }
  
  public static long getFileSize(File paramFile) {
    return (paramFile == null || !paramFile.exists()) ? 0L : getFileSizeInner(paramFile);
  }
  
  private static long getFileSizeInner(File paramFile) {
    File[] arrayOfFile;
    long l;
    if (paramFile.isDirectory()) {
      arrayOfFile = paramFile.listFiles();
      long l1 = 0L;
      if (arrayOfFile == null)
        return 0L; 
      int j = arrayOfFile.length;
      int i = 0;
      while (true) {
        l = l1;
        if (i < j) {
          l1 += getFileSize(arrayOfFile[i]);
          i++;
          continue;
        } 
        break;
      } 
    } else {
      l = arrayOfFile.length();
    } 
    return l;
  }
  
  public static String getPrefixName(File paramFile) {
    if (paramFile == null)
      return ""; 
    String str2 = paramFile.getName();
    int i = str2.lastIndexOf(".");
    String str1 = str2;
    if (i > 0)
      str1 = str2.substring(0, i); 
    return str1;
  }
  
  public static byte[] readBytes(String paramString) {
    try {
      FileInputStream fileInputStream = new FileInputStream(paramString);
      byte[] arrayOfByte = new byte[fileInputStream.available()];
      fileInputStream.read(arrayOfByte);
      fileInputStream.close();
      return arrayOfByte;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "FileUtil", exception.getStackTrace());
      return null;
    } 
  }
  
  public static String readString(String paramString1, String paramString2) {
    byte[] arrayOfByte = readBytes(paramString1);
    try {
      return new String(arrayOfByte, paramString2);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "FileUtil", exception.getStackTrace());
      return null;
    } 
  }
  
  public static void scanFileInDir(File paramFile, Collection<File> paramCollection) {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile != null) {
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++) {
        File file = arrayOfFile[i];
        paramCollection.add(file);
        scanFileInDir(file, paramCollection);
      } 
    } 
  }
  
  public static void unZipFolder(String paramString1, String paramString2) throws Exception {
    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(paramString1));
    while (true) {
      ZipEntry zipEntry = zipInputStream.getNextEntry();
      if (zipEntry != null) {
        String str = zipEntry.getName();
        if (TextUtils.isEmpty(str) || !str.contains("../")) {
          if (zipEntry.isDirectory()) {
            str = str.substring(0, str.length() - 1);
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(paramString2);
            stringBuilder1.append(File.separator);
            stringBuilder1.append(str);
            (new File(stringBuilder1.toString())).mkdirs();
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramString2);
          stringBuilder.append(File.separator);
          stringBuilder.append(str);
          File file = new File(stringBuilder.toString());
          if (!file.getParentFile().exists())
            file.getParentFile().mkdirs(); 
          file.createNewFile();
          FileOutputStream fileOutputStream = new FileOutputStream(file);
          byte[] arrayOfByte = new byte[1024];
          while (true) {
            int i = zipInputStream.read(arrayOfByte);
            if (i != -1) {
              fileOutputStream.write(arrayOfByte, 0, i);
              fileOutputStream.flush();
              continue;
            } 
            fileOutputStream.close();
          } 
          break;
        } 
        continue;
      } 
      zipInputStream.close();
      return;
    } 
  }
  
  public static boolean writeByteToFile(String paramString, byte[] paramArrayOfbyte) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    null = new File(paramString);
    Exception exception = null;
    FileOutputStream fileOutputStream = null;
    try {
    
    } catch (Exception exception1) {
    
    } finally {
      paramArrayOfbyte = null;
      FileOutputStream fileOutputStream1 = fileOutputStream;
      if (fileOutputStream1 != null)
        try {
          fileOutputStream1.close();
        } catch (IOException iOException) {} 
      if (paramArrayOfbyte != null)
        try {
          paramArrayOfbyte.close();
        } catch (Exception exception1) {} 
    } 
    if (iOException != null)
      try {
        iOException.close();
      } catch (IOException iOException1) {} 
    if (paramString != null)
      paramString.close(); 
    return true;
  }
  
  public static boolean writeStringToFile(String paramString1, String paramString2, String paramString3) throws Exception {
    if (TextUtils.isEmpty(paramString1))
      return false; 
    File file = new File(paramString1);
    if (!file.exists())
      file.createNewFile(); 
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
    bufferedWriter.write(paramString2);
    bufferedWriter.flush();
    bufferedWriter.close();
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\FileUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */