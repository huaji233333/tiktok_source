package com.tt.miniapp.msg.image;

import android.graphics.Bitmap;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.FileUtil;
import com.tt.option.e.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCompressImageCtrl extends b {
  public ApiCompressImageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void callbackDecodeFail() {
    callbackFail("decode image fail");
  }
  
  private void callbackSuccess(File paramFile) {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("tempFilePath", FileManager.inst().getSchemaFilePath(paramFile.getAbsolutePath()));
      callbackOk(jSONObject);
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  private void compressJpg(File paramFile1, File paramFile2, int paramInt) {
    // Byte code:
    //   0: aconst_null
    //   1: astore #7
    //   3: aconst_null
    //   4: astore #5
    //   6: aload #5
    //   8: astore #4
    //   10: aload_1
    //   11: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   14: invokestatic decodeFile : (Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   17: astore #6
    //   19: aload #6
    //   21: ifnull -> 187
    //   24: aload #5
    //   26: astore #4
    //   28: new java/io/FileOutputStream
    //   31: dup
    //   32: aload_2
    //   33: invokespecial <init> : (Ljava/io/File;)V
    //   36: astore #5
    //   38: aload #6
    //   40: getstatic android/graphics/Bitmap$CompressFormat.JPEG : Landroid/graphics/Bitmap$CompressFormat;
    //   43: iload_3
    //   44: aload #5
    //   46: invokevirtual compress : (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   49: pop
    //   50: aload #5
    //   52: invokevirtual flush : ()V
    //   55: goto -> 72
    //   58: astore_1
    //   59: ldc 'ApiHandler'
    //   61: iconst_1
    //   62: anewarray java/lang/Object
    //   65: dup
    //   66: iconst_0
    //   67: aload_1
    //   68: aastore
    //   69: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   72: aload #5
    //   74: invokevirtual close : ()V
    //   77: return
    //   78: astore_1
    //   79: ldc 'ApiHandler'
    //   81: iconst_1
    //   82: anewarray java/lang/Object
    //   85: dup
    //   86: iconst_0
    //   87: aload_1
    //   88: aastore
    //   89: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   92: return
    //   93: astore_1
    //   94: aload #5
    //   96: astore #4
    //   98: goto -> 195
    //   101: astore #6
    //   103: goto -> 116
    //   106: astore_1
    //   107: goto -> 195
    //   110: astore #6
    //   112: aload #7
    //   114: astore #5
    //   116: aload #5
    //   118: astore #4
    //   120: ldc 'ApiHandler'
    //   122: iconst_1
    //   123: anewarray java/lang/Object
    //   126: dup
    //   127: iconst_0
    //   128: aload #6
    //   130: aastore
    //   131: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   134: aload #5
    //   136: ifnull -> 187
    //   139: aload #5
    //   141: invokevirtual flush : ()V
    //   144: goto -> 163
    //   147: astore #4
    //   149: ldc 'ApiHandler'
    //   151: iconst_1
    //   152: anewarray java/lang/Object
    //   155: dup
    //   156: iconst_0
    //   157: aload #4
    //   159: aastore
    //   160: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   163: aload #5
    //   165: invokevirtual close : ()V
    //   168: goto -> 187
    //   171: astore #4
    //   173: ldc 'ApiHandler'
    //   175: iconst_1
    //   176: anewarray java/lang/Object
    //   179: dup
    //   180: iconst_0
    //   181: aload #4
    //   183: aastore
    //   184: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   187: aload_1
    //   188: aload_2
    //   189: iconst_0
    //   190: invokestatic copyFile : (Ljava/io/File;Ljava/io/File;Z)I
    //   193: pop
    //   194: return
    //   195: aload #4
    //   197: ifnull -> 244
    //   200: aload #4
    //   202: invokevirtual flush : ()V
    //   205: goto -> 222
    //   208: astore_2
    //   209: ldc 'ApiHandler'
    //   211: iconst_1
    //   212: anewarray java/lang/Object
    //   215: dup
    //   216: iconst_0
    //   217: aload_2
    //   218: aastore
    //   219: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   222: aload #4
    //   224: invokevirtual close : ()V
    //   227: goto -> 244
    //   230: astore_2
    //   231: ldc 'ApiHandler'
    //   233: iconst_1
    //   234: anewarray java/lang/Object
    //   237: dup
    //   238: iconst_0
    //   239: aload_2
    //   240: aastore
    //   241: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   244: aload_1
    //   245: athrow
    // Exception table:
    //   from	to	target	type
    //   10	19	110	java/io/IOException
    //   10	19	106	finally
    //   28	38	110	java/io/IOException
    //   28	38	106	finally
    //   38	50	101	java/io/IOException
    //   38	50	93	finally
    //   50	55	58	java/io/IOException
    //   72	77	78	java/io/IOException
    //   120	134	106	finally
    //   139	144	147	java/io/IOException
    //   163	168	171	java/io/IOException
    //   200	205	208	java/io/IOException
    //   222	227	230	java/io/IOException
  }
  
  private void compressJpg(byte[] paramArrayOfbyte, File paramFile, int paramInt) {
    if (paramArrayOfbyte != null && paramArrayOfbyte.length > 0) {
      Bitmap bitmap = ToolUtils.decodeByteArray(paramArrayOfbyte);
      if (bitmap != null) {
        FileOutputStream fileOutputStream2;
        FileOutputStream fileOutputStream3 = null;
        FileOutputStream fileOutputStream1 = null;
        try {
          fileOutputStream2 = new FileOutputStream(paramFile);
          try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, paramInt, fileOutputStream2);
            try {
              fileOutputStream2.flush();
            } catch (IOException iOException1) {
              AppBrandLogger.e("ApiHandler", new Object[] { iOException1 });
            } 
          } catch (IOException null) {
          
          } finally {
            paramArrayOfbyte = null;
          } 
        } catch (IOException iOException) {
          fileOutputStream2 = fileOutputStream3;
        } finally {}
        fileOutputStream1 = fileOutputStream2;
        AppBrandLogger.e("ApiHandler", new Object[] { iOException });
        if (fileOutputStream2 != null) {
          try {
            fileOutputStream2.flush();
          } catch (IOException iOException1) {
            AppBrandLogger.e("ApiHandler", new Object[] { iOException1 });
          } 
          try {
            fileOutputStream2.close();
          } catch (IOException iOException1) {
            AppBrandLogger.e("ApiHandler", new Object[] { iOException1 });
          } 
        } 
      } 
      copyOtherImage(paramArrayOfbyte, paramFile);
    } 
  }
  
  private void copyOtherImage(File paramFile1, File paramFile2) {
    FileUtil.copyFile(paramFile1, paramFile2, false);
  }
  
  private void copyOtherImage(byte[] paramArrayOfbyte, File paramFile) {
    byte[] arrayOfByte2 = null;
    FileOutputStream fileOutputStream2 = null;
    FileOutputStream fileOutputStream1 = null;
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
      fileOutputStream2 = fileOutputStream1;
      fileOutputStream1 = fileOutputStream;
    } catch (IOException iOException) {
      paramFile = null;
    } finally {
      paramArrayOfbyte = null;
    } 
    byte[] arrayOfByte1 = paramArrayOfbyte;
    File file = paramFile;
    AppBrandLogger.e("ApiHandler", new Object[] { iOException });
    if (paramArrayOfbyte != null)
      try {
        paramArrayOfbyte.close();
      } catch (IOException iOException1) {
        AppBrandLogger.e("ApiHandler", new Object[] { iOException1 });
      }  
    if (paramFile != null)
      try {
        paramFile.close();
        return;
      } catch (IOException iOException1) {
        AppBrandLogger.e("ApiHandler", new Object[] { iOException1 });
      }  
  }
  
  private String getSuffixName(String paramString) {
    return paramString.endsWith(".jpg") ? ".jpg" : (paramString.endsWith(".png") ? ".png" : (paramString.endsWith(".jpeg") ? ".jpeg" : null));
  }
  
  public void act() {
    // Byte code:
    //   0: new org/json/JSONObject
    //   3: dup
    //   4: aload_0
    //   5: getfield mArgs : Ljava/lang/String;
    //   8: invokespecial <init> : (Ljava/lang/String;)V
    //   11: astore #4
    //   13: aload #4
    //   15: ldc 'quality'
    //   17: bipush #80
    //   19: invokevirtual optInt : (Ljava/lang/String;I)I
    //   22: istore_2
    //   23: iload_2
    //   24: iflt -> 421
    //   27: iload_2
    //   28: istore_1
    //   29: iload_2
    //   30: bipush #100
    //   32: if_icmple -> 38
    //   35: goto -> 421
    //   38: aload #4
    //   40: ldc 'src'
    //   42: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   45: astore #6
    //   47: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   50: aload #6
    //   52: invokevirtual getRealFilePath : (Ljava/lang/String;)Ljava/lang/String;
    //   55: astore #5
    //   57: new java/io/File
    //   60: dup
    //   61: aload #5
    //   63: invokespecial <init> : (Ljava/lang/String;)V
    //   66: astore #4
    //   68: aload #4
    //   70: invokevirtual exists : ()Z
    //   73: istore_3
    //   74: iload_3
    //   75: ifeq -> 235
    //   78: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   81: aload #4
    //   83: invokevirtual canRead : (Ljava/io/File;)Z
    //   86: ifeq -> 213
    //   89: aload_0
    //   90: aload #6
    //   92: invokespecial getSuffixName : (Ljava/lang/String;)Ljava/lang/String;
    //   95: astore #5
    //   97: aload #5
    //   99: ifnull -> 208
    //   102: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   105: invokevirtual getTempDir : ()Ljava/io/File;
    //   108: astore #6
    //   110: new java/lang/StringBuilder
    //   113: dup
    //   114: invokespecial <init> : ()V
    //   117: astore #7
    //   119: aload #7
    //   121: invokestatic currentTimeMillis : ()J
    //   124: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload #7
    //   130: aload #5
    //   132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: pop
    //   136: new java/io/File
    //   139: dup
    //   140: aload #6
    //   142: aload #7
    //   144: invokevirtual toString : ()Ljava/lang/String;
    //   147: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   150: astore #6
    //   152: aload #5
    //   154: ldc '.jpg'
    //   156: invokevirtual equals : (Ljava/lang/Object;)Z
    //   159: ifeq -> 180
    //   162: iload_1
    //   163: bipush #100
    //   165: if_icmpge -> 180
    //   168: aload_0
    //   169: aload #4
    //   171: aload #6
    //   173: iload_1
    //   174: invokespecial compressJpg : (Ljava/io/File;Ljava/io/File;I)V
    //   177: goto -> 188
    //   180: aload_0
    //   181: aload #4
    //   183: aload #6
    //   185: invokespecial copyOtherImage : (Ljava/io/File;Ljava/io/File;)V
    //   188: aload #6
    //   190: invokevirtual exists : ()Z
    //   193: ifeq -> 203
    //   196: aload_0
    //   197: aload #6
    //   199: invokespecial callbackSuccess : (Ljava/io/File;)V
    //   202: return
    //   203: aload_0
    //   204: invokespecial callbackDecodeFail : ()V
    //   207: return
    //   208: aload_0
    //   209: invokespecial callbackDecodeFail : ()V
    //   212: return
    //   213: aload_0
    //   214: iconst_2
    //   215: anewarray java/lang/String
    //   218: dup
    //   219: iconst_0
    //   220: ldc 'read'
    //   222: aastore
    //   223: dup
    //   224: iconst_1
    //   225: aload #6
    //   227: aastore
    //   228: invokestatic a : ([Ljava/lang/String;)Ljava/lang/String;
    //   231: invokevirtual callbackFail : (Ljava/lang/String;)V
    //   234: return
    //   235: aload #5
    //   237: invokestatic findFile : (Ljava/lang/String;)Lcom/tt/miniapp/ttapkgdecoder/TTAPkgFile;
    //   240: astore #4
    //   242: aload #4
    //   244: ifnull -> 381
    //   247: aload_0
    //   248: aload #4
    //   250: invokevirtual getFileName : ()Ljava/lang/String;
    //   253: invokespecial getSuffixName : (Ljava/lang/String;)Ljava/lang/String;
    //   256: astore #4
    //   258: aload #4
    //   260: ifnull -> 376
    //   263: aload #5
    //   265: invokestatic loadByteFromStream : (Ljava/lang/String;)[B
    //   268: astore #5
    //   270: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   273: invokevirtual getTempDir : ()Ljava/io/File;
    //   276: astore #6
    //   278: new java/lang/StringBuilder
    //   281: dup
    //   282: invokespecial <init> : ()V
    //   285: astore #7
    //   287: aload #7
    //   289: invokestatic currentTimeMillis : ()J
    //   292: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   295: pop
    //   296: aload #7
    //   298: aload #4
    //   300: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: pop
    //   304: new java/io/File
    //   307: dup
    //   308: aload #6
    //   310: aload #7
    //   312: invokevirtual toString : ()Ljava/lang/String;
    //   315: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   318: astore #6
    //   320: aload #4
    //   322: ldc '.jpg'
    //   324: invokevirtual equals : (Ljava/lang/Object;)Z
    //   327: ifeq -> 348
    //   330: iload_1
    //   331: bipush #100
    //   333: if_icmpge -> 348
    //   336: aload_0
    //   337: aload #5
    //   339: aload #6
    //   341: iload_1
    //   342: invokespecial compressJpg : ([BLjava/io/File;I)V
    //   345: goto -> 356
    //   348: aload_0
    //   349: aload #5
    //   351: aload #6
    //   353: invokespecial copyOtherImage : ([BLjava/io/File;)V
    //   356: aload #6
    //   358: invokevirtual exists : ()Z
    //   361: ifeq -> 371
    //   364: aload_0
    //   365: aload #6
    //   367: invokespecial callbackSuccess : (Ljava/io/File;)V
    //   370: return
    //   371: aload_0
    //   372: invokespecial callbackDecodeFail : ()V
    //   375: return
    //   376: aload_0
    //   377: invokespecial callbackDecodeFail : ()V
    //   380: return
    //   381: aload_0
    //   382: iconst_1
    //   383: anewarray java/lang/String
    //   386: dup
    //   387: iconst_0
    //   388: aload #6
    //   390: aastore
    //   391: invokestatic b : ([Ljava/lang/String;)Ljava/lang/String;
    //   394: invokevirtual callbackFail : (Ljava/lang/String;)V
    //   397: return
    //   398: astore #4
    //   400: ldc 'ApiHandler'
    //   402: iconst_1
    //   403: anewarray java/lang/Object
    //   406: dup
    //   407: iconst_0
    //   408: aload #4
    //   410: aastore
    //   411: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   414: aload_0
    //   415: aload #4
    //   417: invokevirtual callbackFail : (Ljava/lang/Throwable;)V
    //   420: return
    //   421: bipush #80
    //   423: istore_1
    //   424: goto -> 38
    // Exception table:
    //   from	to	target	type
    //   0	23	398	java/lang/Exception
    //   38	74	398	java/lang/Exception
    //   78	97	398	java/lang/Exception
    //   102	162	398	java/lang/Exception
    //   168	177	398	java/lang/Exception
    //   180	188	398	java/lang/Exception
    //   188	202	398	java/lang/Exception
    //   203	207	398	java/lang/Exception
    //   208	212	398	java/lang/Exception
    //   213	234	398	java/lang/Exception
    //   235	242	398	java/lang/Exception
    //   247	258	398	java/lang/Exception
    //   263	330	398	java/lang/Exception
    //   336	345	398	java/lang/Exception
    //   348	356	398	java/lang/Exception
    //   356	370	398	java/lang/Exception
    //   371	375	398	java/lang/Exception
    //   376	380	398	java/lang/Exception
    //   381	397	398	java/lang/Exception
  }
  
  public String getActionName() {
    return "compressImage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\image\ApiCompressImageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */