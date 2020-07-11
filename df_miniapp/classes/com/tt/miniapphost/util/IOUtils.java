package com.tt.miniapphost.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.a;
import com.tt.miniapp.util.StringUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class IOUtils {
  private static final String[] PATH_PROJECTION = new String[] { "_data" };
  
  public static String calculateMD5(File paramFile) {
    return calculateMD5(paramFile, 1024);
  }
  
  public static String calculateMD5(File paramFile, int paramInt) {
    // Byte code:
    //   0: ldc 'MD5'
    //   2: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
    //   5: astore_2
    //   6: new java/io/FileInputStream
    //   9: dup
    //   10: aload_0
    //   11: invokespecial <init> : (Ljava/io/File;)V
    //   14: astore_0
    //   15: iload_1
    //   16: newarray byte
    //   18: astore_3
    //   19: aload_0
    //   20: aload_3
    //   21: invokevirtual read : ([B)I
    //   24: istore_1
    //   25: iload_1
    //   26: ifle -> 39
    //   29: aload_2
    //   30: aload_3
    //   31: iconst_0
    //   32: iload_1
    //   33: invokevirtual update : ([BII)V
    //   36: goto -> 19
    //   39: ldc '%32s'
    //   41: iconst_1
    //   42: anewarray java/lang/Object
    //   45: dup
    //   46: iconst_0
    //   47: new java/math/BigInteger
    //   50: dup
    //   51: iconst_1
    //   52: aload_2
    //   53: invokevirtual digest : ()[B
    //   56: invokespecial <init> : (I[B)V
    //   59: bipush #16
    //   61: invokevirtual toString : (I)Ljava/lang/String;
    //   64: aastore
    //   65: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   68: bipush #32
    //   70: bipush #48
    //   72: invokevirtual replace : (CC)Ljava/lang/String;
    //   75: astore_2
    //   76: aload_0
    //   77: invokevirtual close : ()V
    //   80: aload_2
    //   81: areturn
    //   82: astore_0
    //   83: ldc 'ContentValues'
    //   85: ldc ''
    //   87: aload_0
    //   88: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   91: aload_2
    //   92: areturn
    //   93: astore_2
    //   94: goto -> 109
    //   97: astore_2
    //   98: new java/lang/RuntimeException
    //   101: dup
    //   102: ldc 'Unable to process file for MD5'
    //   104: aload_2
    //   105: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   108: athrow
    //   109: aload_0
    //   110: invokevirtual close : ()V
    //   113: goto -> 125
    //   116: astore_0
    //   117: ldc 'ContentValues'
    //   119: ldc ''
    //   121: aload_0
    //   122: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   125: aload_2
    //   126: athrow
    //   127: astore_0
    //   128: aconst_null
    //   129: areturn
    // Exception table:
    //   from	to	target	type
    //   0	6	127	java/security/NoSuchAlgorithmException
    //   6	15	127	java/io/FileNotFoundException
    //   19	25	97	java/io/IOException
    //   19	25	93	finally
    //   29	36	97	java/io/IOException
    //   29	36	93	finally
    //   39	76	97	java/io/IOException
    //   39	76	93	finally
    //   76	80	82	java/io/IOException
    //   98	109	93	finally
    //   109	113	116	java/io/IOException
  }
  
  public static boolean checkMD5(String paramString, File paramFile) {
    if (!TextUtils.isEmpty(paramString)) {
      if (paramFile == null)
        return false; 
      String str = calculateMD5(paramFile);
      return (str == null) ? false : str.toLowerCase().startsWith(paramString.toLowerCase());
    } 
    return false;
  }
  
  public static void clearDir(File paramFile) {
    if (paramFile != null && paramFile.exists()) {
      if (!paramFile.isDirectory())
        return; 
      delete(paramFile);
    } 
  }
  
  public static void clearDir(String paramString) throws Exception {
    File file = new File(paramString);
    if (!file.exists())
      return; 
    File[] arrayOfFile = file.listFiles();
    int j = arrayOfFile.length;
    for (int i = 0; i < j; i++) {
      if (arrayOfFile[i].isDirectory()) {
        removeDir(arrayOfFile[i].getAbsolutePath());
      } else if (arrayOfFile[i].isFile()) {
        arrayOfFile[i].delete();
      } 
    } 
  }
  
  public static void close(Closeable paramCloseable) {
    if (paramCloseable != null)
      try {
        paramCloseable.close();
        return;
      } catch (IOException iOException) {
        AppBrandLogger.eWithThrowable("ContentValues", "", iOException);
      }  
  }
  
  public static int copy(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
    long l = copyLarge(paramInputStream, paramOutputStream);
    return (l > 2147483647L) ? -1 : (int)l;
  }
  
  public static void copyAssets(Context paramContext, String paramString1, String paramString2) {
    null = paramContext.getAssets();
    IOException iOException = null;
    Exception exception2 = null;
    try {
      Exception exception;
    } catch (IOException iOException1) {
    
    } finally {
      paramString2 = null;
      exception1 = exception2;
      if (paramString2 != null)
        try {
          paramString2.close();
        } catch (IOException iOException1) {
          AppBrandLogger.eWithThrowable("ContentValues", "", iOException1);
        }  
      if (exception1 != null)
        try {
          exception1.close();
        } catch (IOException exception1) {
          AppBrandLogger.eWithThrowable("ContentValues", "", exception1);
        }  
    } 
    if (paramContext != null)
      try {
        paramContext.close();
      } catch (IOException iOException1) {
        AppBrandLogger.eWithThrowable("ContentValues", "", iOException1);
      }  
    if (exception1 != null)
      try {
        exception1.close();
        return;
      } catch (IOException iOException1) {
        AppBrandLogger.eWithThrowable("ContentValues", "", iOException1);
      }  
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
    //   98: goto -> 110
    //   101: astore_0
    //   102: ldc 'ContentValues'
    //   104: ldc ''
    //   106: aload_0
    //   107: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   110: aload #4
    //   112: invokevirtual close : ()V
    //   115: iconst_0
    //   116: ireturn
    //   117: astore_0
    //   118: ldc 'ContentValues'
    //   120: ldc ''
    //   122: aload_0
    //   123: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   126: iconst_0
    //   127: ireturn
    //   128: astore_0
    //   129: goto -> 140
    //   132: astore_1
    //   133: goto -> 153
    //   136: astore_0
    //   137: aconst_null
    //   138: astore #4
    //   140: aload #5
    //   142: astore_1
    //   143: aload_0
    //   144: astore #5
    //   146: goto -> 192
    //   149: astore_1
    //   150: aconst_null
    //   151: astore #4
    //   153: aload #5
    //   155: astore_0
    //   156: goto -> 179
    //   159: astore #5
    //   161: aconst_null
    //   162: astore_0
    //   163: aload #4
    //   165: astore_1
    //   166: aload_0
    //   167: astore #4
    //   169: goto -> 192
    //   172: astore_1
    //   173: aconst_null
    //   174: astore #4
    //   176: aload #6
    //   178: astore_0
    //   179: new java/lang/RuntimeException
    //   182: dup
    //   183: aload_1
    //   184: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   187: athrow
    //   188: astore #5
    //   190: aload_0
    //   191: astore_1
    //   192: aload_1
    //   193: ifnull -> 212
    //   196: aload_1
    //   197: invokevirtual close : ()V
    //   200: goto -> 212
    //   203: astore_0
    //   204: ldc 'ContentValues'
    //   206: ldc ''
    //   208: aload_0
    //   209: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   212: aload #4
    //   214: ifnull -> 234
    //   217: aload #4
    //   219: invokevirtual close : ()V
    //   222: goto -> 234
    //   225: astore_0
    //   226: ldc 'ContentValues'
    //   228: ldc ''
    //   230: aload_0
    //   231: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   234: goto -> 240
    //   237: aload #5
    //   239: athrow
    //   240: goto -> 237
    // Exception table:
    //   from	to	target	type
    //   24	36	172	java/lang/Exception
    //   24	36	159	finally
    //   36	46	172	java/lang/Exception
    //   36	46	159	finally
    //   46	56	149	java/lang/Exception
    //   46	56	136	finally
    //   56	62	132	java/lang/Exception
    //   56	62	128	finally
    //   62	69	132	java/lang/Exception
    //   62	69	128	finally
    //   73	81	132	java/lang/Exception
    //   73	81	128	finally
    //   88	93	132	java/lang/Exception
    //   88	93	128	finally
    //   93	98	101	java/lang/Exception
    //   110	115	117	java/lang/Exception
    //   179	188	188	finally
    //   196	200	203	java/lang/Exception
    //   217	222	225	java/lang/Exception
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
    //   15: invokevirtual mkdirs : ()Z
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
    //   133: goto -> 145
    //   136: astore_1
    //   137: ldc 'ContentValues'
    //   139: ldc ''
    //   141: aload_1
    //   142: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   145: aload_0
    //   146: invokevirtual close : ()V
    //   149: iconst_1
    //   150: ireturn
    //   151: astore_0
    //   152: ldc 'ContentValues'
    //   154: ldc ''
    //   156: aload_0
    //   157: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   160: iconst_1
    //   161: ireturn
    //   162: astore_1
    //   163: goto -> 250
    //   166: astore_1
    //   167: goto -> 179
    //   170: astore_1
    //   171: aconst_null
    //   172: astore_0
    //   173: goto -> 250
    //   176: astore_1
    //   177: aconst_null
    //   178: astore_0
    //   179: goto -> 198
    //   182: astore_1
    //   183: aconst_null
    //   184: astore_0
    //   185: aload_0
    //   186: astore #4
    //   188: goto -> 250
    //   191: astore_1
    //   192: aconst_null
    //   193: astore_0
    //   194: aload #5
    //   196: astore #4
    //   198: ldc 'ContentValues'
    //   200: ldc ''
    //   202: aload_1
    //   203: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   206: aload #4
    //   208: ifnull -> 228
    //   211: aload #4
    //   213: invokevirtual close : ()V
    //   216: goto -> 228
    //   219: astore_1
    //   220: ldc 'ContentValues'
    //   222: ldc ''
    //   224: aload_1
    //   225: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   228: aload_0
    //   229: ifnull -> 247
    //   232: aload_0
    //   233: invokevirtual close : ()V
    //   236: iconst_0
    //   237: ireturn
    //   238: astore_0
    //   239: ldc 'ContentValues'
    //   241: ldc ''
    //   243: aload_0
    //   244: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   247: iconst_0
    //   248: ireturn
    //   249: astore_1
    //   250: aload #4
    //   252: ifnull -> 274
    //   255: aload #4
    //   257: invokevirtual close : ()V
    //   260: goto -> 274
    //   263: astore #4
    //   265: ldc 'ContentValues'
    //   267: ldc ''
    //   269: aload #4
    //   271: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   274: aload_0
    //   275: ifnull -> 294
    //   278: aload_0
    //   279: invokevirtual close : ()V
    //   282: goto -> 294
    //   285: astore_0
    //   286: ldc 'ContentValues'
    //   288: ldc ''
    //   290: aload_0
    //   291: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   294: goto -> 299
    //   297: aload_1
    //   298: athrow
    //   299: goto -> 297
    // Exception table:
    //   from	to	target	type
    //   82	92	191	java/io/IOException
    //   82	92	182	finally
    //   92	101	176	java/io/IOException
    //   92	101	170	finally
    //   101	107	166	java/io/IOException
    //   101	107	162	finally
    //   107	114	166	java/io/IOException
    //   107	114	162	finally
    //   118	125	166	java/io/IOException
    //   118	125	162	finally
    //   128	133	136	java/io/IOException
    //   145	149	151	java/io/IOException
    //   198	206	249	finally
    //   211	216	219	java/io/IOException
    //   232	236	238	java/io/IOException
    //   255	260	263	java/io/IOException
    //   278	282	285	java/io/IOException
  }
  
  public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
    byte[] arrayOfByte = new byte[4096];
    long l = 0L;
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (-1 != i) {
        paramOutputStream.write(arrayOfByte, 0, i);
        l += i;
        continue;
      } 
      return l;
    } 
  }
  
  public static Bitmap decodeFile(File paramFile) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new android/graphics/BitmapFactory$Options
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: astore_3
    //   10: new java/io/FileInputStream
    //   13: dup
    //   14: aload_0
    //   15: invokespecial <init> : (Ljava/io/File;)V
    //   18: astore_1
    //   19: aload_1
    //   20: astore_0
    //   21: aload_1
    //   22: aconst_null
    //   23: aload_3
    //   24: invokestatic decodeStream : (Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   27: astore_2
    //   28: aload_1
    //   29: invokevirtual close : ()V
    //   32: aload_2
    //   33: areturn
    //   34: astore_0
    //   35: bipush #6
    //   37: ldc 'ContentValues'
    //   39: aload_0
    //   40: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   43: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   46: aload_2
    //   47: areturn
    //   48: astore_2
    //   49: goto -> 61
    //   52: astore_0
    //   53: aload_2
    //   54: astore_1
    //   55: goto -> 103
    //   58: astore_2
    //   59: aconst_null
    //   60: astore_1
    //   61: aload_1
    //   62: astore_0
    //   63: bipush #6
    //   65: ldc 'ContentValues'
    //   67: aload_2
    //   68: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   71: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   74: aload_1
    //   75: ifnull -> 96
    //   78: aload_1
    //   79: invokevirtual close : ()V
    //   82: aconst_null
    //   83: areturn
    //   84: astore_0
    //   85: bipush #6
    //   87: ldc 'ContentValues'
    //   89: aload_0
    //   90: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   93: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   96: aconst_null
    //   97: areturn
    //   98: astore_2
    //   99: aload_0
    //   100: astore_1
    //   101: aload_2
    //   102: astore_0
    //   103: aload_1
    //   104: ifnull -> 126
    //   107: aload_1
    //   108: invokevirtual close : ()V
    //   111: goto -> 126
    //   114: astore_1
    //   115: bipush #6
    //   117: ldc 'ContentValues'
    //   119: aload_1
    //   120: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   123: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   126: aload_0
    //   127: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	58	java/io/FileNotFoundException
    //   2	19	52	finally
    //   21	28	48	java/io/FileNotFoundException
    //   21	28	98	finally
    //   28	32	34	java/lang/Exception
    //   63	74	98	finally
    //   78	82	84	java/lang/Exception
    //   107	111	114	java/lang/Exception
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
  
  public static String fileSize(long paramLong) {
    if (paramLong <= 0L)
      return "0"; 
    double d1 = paramLong;
    int i = (int)(Math.log10(d1) / Math.log10(1024.0D));
    StringBuilder stringBuilder = new StringBuilder();
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
    double d2 = Math.pow(1024.0D, i);
    Double.isNaN(d1);
    stringBuilder.append(decimalFormat.format(d1 / d2));
    stringBuilder.append(" ");
    (new String[5])[0] = "B";
    (new String[5])[1] = "kB";
    (new String[5])[2] = "MB";
    (new String[5])[3] = "GB";
    (new String[5])[4] = "TB";
    stringBuilder.append((new String[5])[i]);
    return stringBuilder.toString();
  }
  
  public static File getCacheDirectory(Context paramContext) {
    return getCacheDirectory(paramContext, true);
  }
  
  public static File getCacheDirectory(Context paramContext, boolean paramBoolean) {
    File file1;
    String str2;
    String str1 = "";
    try {
      str2 = Environment.getExternalStorageState();
      str1 = str2;
    } catch (NullPointerException|IncompatibleClassChangeError null) {}
    if (paramBoolean && "mounted".equals(str1) && hasExternalStoragePermission(paramContext)) {
      str2 = (String)getExternalCacheDir(paramContext);
    } else {
      str2 = null;
    } 
    str1 = str2;
    if (str2 == null)
      file1 = paramContext.getCacheDir(); 
    File file2 = file1;
    if (file1 == null) {
      StringBuilder stringBuilder = new StringBuilder("/data/data/");
      stringBuilder.append(paramContext.getPackageName());
      stringBuilder.append("/cache/");
      file2 = new File(stringBuilder.toString());
    } 
    return file2;
  }
  
  private static String getDataColumn(Context paramContext, Uri paramUri, String paramString, String[] paramArrayOfString) {
    try {
      Cursor cursor = paramContext.getContentResolver().query(paramUri, new String[] { "_data" }, paramString, paramArrayOfString, null);
      return null;
    } finally {
      paramContext = null;
      if (paramContext != null)
        paramContext.close(); 
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
  
  public static File getExternalCacheDir(Context paramContext) {
    String str1;
    File file;
    try {
      str1 = Environment.getExternalStorageState();
    } catch (Exception exception) {
      str1 = "";
    } 
    if ("mounted".equals(str1)) {
      File file1 = paramContext.getExternalCacheDir();
    } else {
      str1 = null;
    } 
    String str2 = str1;
    if (str1 == null)
      file = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data"), paramContext.getPackageName()), "cache"); 
    return file;
  }
  
  public static File getExternalFilesDir(Context paramContext) {
    File file1;
    try {
      str = Environment.getExternalStorageState();
    } catch (Exception exception) {
      str = "";
    } 
    boolean bool = "mounted".equals(str);
    String str = null;
    if (bool)
      file1 = paramContext.getExternalFilesDir(null); 
    File file2 = file1;
    if (file1 == null)
      file2 = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data"), paramContext.getPackageName()), "files"); 
    if (file2 != null && !file2.exists())
      file2.mkdirs(); 
    return file2;
  }
  
  public static String getFileExtension(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    int i = paramString.lastIndexOf(".");
    return (i > 0) ? paramString.substring(i) : "";
  }
  
  public static String getFilePathByUri(Context paramContext, Uri paramUri) {
    String str1;
    Cursor cursor;
    if ("file".equals(paramUri.getScheme()))
      return paramUri.getPath(); 
    boolean bool = "content".equals(paramUri.getScheme());
    Context context2 = null;
    String str2 = null;
    Context context1 = null;
    if (bool && Build.VERSION.SDK_INT < 19) {
      cursor = paramContext.getContentResolver().query(paramUri, new String[] { "_data" }, null, null, null);
      paramContext = context2;
      if (cursor != null) {
        paramContext = context1;
        if (cursor.moveToFirst()) {
          int i = cursor.getColumnIndexOrThrow("_data");
          paramContext = context1;
          if (i >= 0)
            str1 = cursor.getString(i); 
        } 
        cursor.close();
      } 
      return str1;
    } 
    if ("content".equals(cursor.getScheme()) && Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri((Context)str1, (Uri)cursor)) {
      String[] arrayOfString;
      StringBuilder stringBuilder;
      if (isExternalStorageDocument((Uri)cursor)) {
        arrayOfString = DocumentsContract.getDocumentId((Uri)cursor).split(":");
        if ("primary".equalsIgnoreCase(arrayOfString[0])) {
          stringBuilder = new StringBuilder();
          stringBuilder.append(Environment.getExternalStorageDirectory());
          stringBuilder.append("/");
          stringBuilder.append(arrayOfString[1]);
          return stringBuilder.toString();
        } 
      } else {
        String str;
        if (isDownloadsDocument((Uri)stringBuilder)) {
          str = DocumentsContract.getDocumentId((Uri)stringBuilder);
          return getDataColumn((Context)arrayOfString, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(str).longValue()), null, null);
        } 
        if (isMediaDocument((Uri)str)) {
          Uri uri;
          String[] arrayOfString1 = DocumentsContract.getDocumentId((Uri)str).split(":");
          String str3 = arrayOfString1[0];
          if ("image".equals(str3)) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
          } else if ("video".equals(str3)) {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
          } else {
            str = str2;
            if ("audio".equals(str3))
              uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; 
          } 
          return getDataColumn((Context)arrayOfString, uri, "_id=?", new String[] { arrayOfString1[1] });
        } 
      } 
    } 
    return null;
  }
  
  public static String getFilesDirPath(Context paramContext) throws NullPointerException {
    if (paramContext != null) {
      String str1;
      String str2 = null;
      try {
      
      } finally {
        paramContext = null;
      } 
      if (!TextUtils.isEmpty(str1))
        return str1; 
      throw new NullPointerException("Cannot Create Files Dir");
    } 
    throw new NullPointerException("Context is NUll");
  }
  
  public static String getFromAssets(Context paramContext, String paramString) {
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramContext.getResources().getAssets().open(paramString)));
      String str = "";
      while (true) {
        String str1 = bufferedReader.readLine();
        if (str1 != null) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str);
          stringBuilder.append(str1);
          str = stringBuilder.toString();
          continue;
        } 
        return str;
      } 
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("ContentValues", "", exception);
      return "";
    } 
  }
  
  public static byte[] getFromAssetsByte(Context paramContext, String paramString) {
    try {
    
    } catch (Exception exception) {
    
    } finally {
      paramString = null;
      if (paramString != null)
        try {
          paramString.close();
        } catch (Exception exception) {
          AppBrandLogger.eWithThrowable("ContentValues", "", exception);
        }  
    } 
    if (paramContext != null)
      try {
        paramContext.close();
        return null;
      } catch (Exception exception) {
        AppBrandLogger.eWithThrowable("ContentValues", "", exception);
      }  
    return null;
  }
  
  public static File getIndividualCacheDirectory(Context paramContext, String paramString) {
    File file1 = getCacheDirectory(paramContext);
    File file2 = new File(file1, paramString);
    return (!file2.exists() && !file2.mkdir()) ? file1 : file2;
  }
  
  public static String getSizeByUnit(double paramDouble) {
    if (paramDouble == 0.0D)
      return "0K"; 
    if (paramDouble >= 1048576.0D) {
      paramDouble /= 1048576.0D;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(a.a(Locale.getDefault(), "%.1f", new Object[] { Double.valueOf(paramDouble) }));
      stringBuilder1.append("M");
      return stringBuilder1.toString();
    } 
    paramDouble /= 1024.0D;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(a.a(Locale.getDefault(), "%.1f", new Object[] { Double.valueOf(paramDouble) }));
    stringBuilder.append("K");
    return stringBuilder.toString();
  }
  
  public static long getZipTrueSize(String paramString) {
    long l3;
    long l1 = 0L;
    long l2 = l1;
    try {
      Enumeration<? extends ZipEntry> enumeration = (new ZipFile(paramString)).entries();
      while (true) {
        l2 = l1;
        l3 = l1;
        if (enumeration.hasMoreElements()) {
          l2 = l1;
          l3 = ((ZipEntry)enumeration.nextElement()).getSize();
          l1 += l3;
          continue;
        } 
        break;
      } 
    } catch (IOException iOException) {
      AppBrandLogger.eWithThrowable("ContentValues", "", iOException);
      l3 = l2;
    } 
    return l3;
  }
  
  private static boolean hasExternalStoragePermission(Context paramContext) {
    return (paramContext.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0);
  }
  
  public static boolean isAssetsFileExist(Context paramContext, String paramString) {
    AssetManager assetManager = paramContext.getAssets();
    try {
      InputStream inputStream = assetManager.open(paramString);
      return (inputStream != null);
    } catch (IOException iOException) {
      return false;
    } 
  }
  
  private static boolean isDownloadsDocument(Uri paramUri) {
    return "com.android.providers.downloads.documents".equals(paramUri.getAuthority());
  }
  
  private static boolean isExternalStorageDocument(Uri paramUri) {
    return "com.android.externalstorage.documents".equals(paramUri.getAuthority());
  }
  
  private static boolean isMediaDocument(Uri paramUri) {
    return "com.android.providers.media.documents".equals(paramUri.getAuthority());
  }
  
  public static byte[] readBytes(String paramString) {
    try {
      FileInputStream fileInputStream = new FileInputStream(paramString);
      byte[] arrayOfByte = new byte[fileInputStream.available()];
      fileInputStream.read(arrayOfByte);
      fileInputStream.close();
      return arrayOfByte;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("ContentValues", "", exception);
      return null;
    } 
  }
  
  public static int readInt(String paramString1, String paramString2, int paramInt) {
    byte[] arrayOfByte = readBytes(paramString1);
    try {
      String str = new String(arrayOfByte, paramString2);
      if (!TextUtils.isEmpty(str))
        return Integer.parseInt(str); 
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("ContentValues", "", exception);
    } 
    return paramInt;
  }
  
  public static String readString(String paramString1, String paramString2) {
    return StringUtils.newString(readBytes(paramString1), paramString2);
  }
  
  public static void removeDir(String paramString) throws Exception {
    delete(new File(paramString));
  }
  
  public static byte[] toByteArray(InputStream paramInputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    copy(paramInputStream, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }
  
  public static void unZipFolder(InputStream paramInputStream, String paramString) throws Exception {
    unZipFolder(paramInputStream, paramString, false);
  }
  
  public static void unZipFolder(InputStream paramInputStream, String paramString, boolean paramBoolean) throws Exception {
    paramInputStream = new ZipInputStream(paramInputStream);
    while (true) {
      ZipEntry zipEntry = paramInputStream.getNextEntry();
      if (zipEntry != null) {
        String str = zipEntry.getName();
        if (!paramBoolean || TextUtils.isEmpty(str) || !str.contains("../")) {
          if (zipEntry.isDirectory()) {
            str = str.substring(0, str.length() - 1);
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(paramString);
            stringBuilder1.append(File.separator);
            stringBuilder1.append(str);
            (new File(stringBuilder1.toString())).mkdirs();
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramString);
          stringBuilder.append(File.separator);
          stringBuilder.append(str);
          File file = new File(stringBuilder.toString());
          if (!file.getParentFile().exists())
            file.getParentFile().mkdirs(); 
          file.createNewFile();
          FileOutputStream fileOutputStream = new FileOutputStream(file);
          byte[] arrayOfByte = new byte[1024];
          while (true) {
            int i = paramInputStream.read(arrayOfByte);
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
      paramInputStream.close();
      return;
    } 
  }
  
  public static void unZipFolder(String paramString1, String paramString2) throws Exception {
    unZipFolder(new FileInputStream(paramString1), paramString2, false);
  }
  
  public static void unZipFolder(String paramString1, String paramString2, boolean paramBoolean) throws Exception {
    unZipFolder(new FileInputStream(paramString1), paramString2, paramBoolean);
  }
  
  public static void writeBytes(String paramString, byte[] paramArrayOfbyte) throws IOException {
    if (paramArrayOfbyte != null) {
      if (paramArrayOfbyte.length == 0)
        return; 
      FileOutputStream fileOutputStream = new FileOutputStream(paramString);
      DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
      dataOutputStream.write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
      dataOutputStream.close();
      fileOutputStream.close();
    } 
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


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\IOUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */