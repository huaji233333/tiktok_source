package com.tt.miniapp.manager.basebundle;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseBundleFileManager {
  private static boolean checkFileComplete(String paramString, File paramFile, BaseBundleEventHelper.BaseBundleEvent paramBaseBundleEvent) {
    JSONObject jSONObject;
    File file1;
    File file2 = new File(paramFile, "jssdkcheck.json");
    boolean bool = file2.exists();
    boolean bool1 = false;
    if (!bool)
      return false; 
    String str = IOUtils.readString(file2.getAbsolutePath(), "utf-8");
    file2 = null;
    bool = true;
    try {
      JSONObject jSONObject1 = new JSONObject(str);
      jSONObject = jSONObject1;
    } catch (JSONException jSONException) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append((String)jSONObject);
      stringBuilder1.append("check file json is invalid");
      paramBaseBundleEvent.appendLog(stringBuilder1.toString());
      bool = false;
      file1 = file2;
    } 
    if (bool) {
      Iterator<String> iterator = file1.keys();
      while (iterator.hasNext()) {
        str = iterator.next();
        long l = file1.optLong(str);
        if (!checkFileLength(paramFile.getAbsolutePath(), str, l)) {
          bool = bool1;
          break;
        } 
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder("check file folder result: ");
    stringBuilder.append(bool);
    paramBaseBundleEvent.appendLog(stringBuilder.toString());
    return bool;
  }
  
  private static boolean checkFileLength(String paramString1, String paramString2, long paramLong) {
    File file = new File(paramString1, paramString2);
    return !file.exists() ? false : ((file.length() == paramLong));
  }
  
  public static File getBundleFolderFile(Context paramContext, String paramString) {
    File file = new File(AppbrandUtil.getAppbrandBaseFile(paramContext).getAbsolutePath(), paramString);
    if (!file.exists())
      file.mkdir(); 
    return file;
  }
  
  public static File getBundleServiceFile(Context paramContext, String paramString) {
    File file = getBundleFolderFile(paramContext, paramString);
    if (!file.exists())
      file.mkdir(); 
    file = new File(file, "__dev__");
    if (!file.exists())
      file.mkdir(); 
    return file;
  }
  
  public static int getBundleVersion(Context paramContext, String paramString) {
    try {
      return Integer.valueOf(IOUtils.readString((new File(getBundleServiceFile(paramContext, paramString), "basebundlecheck")).getAbsolutePath(), "utf-8")).intValue();
    } catch (Exception exception) {
      AppBrandLogger.e("BaseBundleFileManager", new Object[] { exception });
      return -1;
    } 
  }
  
  public static File getLatestBaseBundleFile() {
    return new File(AppbrandUtil.getAppServiceDir((Context)AppbrandContext.getInst().getApplicationContext()), "latest_basebundle_version");
  }
  
  public static long getLatestBaseBundleVersion() {
    try {
      File file = getLatestBaseBundleFile();
      if (!file.exists())
        return -1L; 
      String str = IOUtils.readString(file.getAbsolutePath(), "utf-8");
      return TextUtils.isEmpty(str) ? -1L : Long.valueOf(CharacterUtils.replaceBlank(str)).longValue();
    } catch (Exception exception) {
      AppBrandLogger.e("BaseBundleFileManager", new Object[] { exception });
      return -1L;
    } 
  }
  
  public static File getTempBundleFolderFile(Context paramContext, String paramString) {
    File file = new File(AppbrandUtil.getAppbrandBaseFile(paramContext).getAbsolutePath(), paramString);
    if (!file.exists())
      file.mkdir(); 
    return file;
  }
  
  public static File getTempBundleServiceFile(Context paramContext, String paramString) {
    File file = getTempBundleFolderFile(paramContext, paramString);
    if (!file.exists())
      file.mkdir(); 
    file = new File(file, "__dev__");
    if (!file.exists())
      file.mkdir(); 
    return file;
  }
  
  public static File getTempBundleZipFile(Context paramContext, String paramString) {
    File file = getTempBundleFolderFile(paramContext, paramString);
    if (!file.exists())
      file.mkdir(); 
    file = new File(file, "__dev__.zip");
    if (!file.exists())
      try {
        file.createNewFile();
        return file;
      } catch (IOException iOException) {
        AppBrandLogger.e("BaseBundleFileManager", new Object[] { iOException });
      }  
    return file;
  }
  
  public static long renameFileToBaseBundle(Context paramContext, long paramLong, String paramString, boolean paramBoolean, BaseBundleEventHelper.BaseBundleEvent paramBaseBundleEvent) {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   2: monitorenter
    //   3: invokestatic getLatestBaseBundleVersion : ()J
    //   6: lload_1
    //   7: lcmp
    //   8: iflt -> 28
    //   11: iload #4
    //   13: ifne -> 28
    //   16: aload #5
    //   18: ldc 'current baseBundle version bigger or equals than baseBundle version'
    //   20: invokevirtual appendLog : (Ljava/lang/String;)V
    //   23: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   25: monitorexit
    //   26: lconst_0
    //   27: lreturn
    //   28: aload_0
    //   29: aload_3
    //   30: invokestatic getBundleFolderFile : (Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
    //   33: astore #8
    //   35: new java/io/File
    //   38: dup
    //   39: aload_0
    //   40: invokestatic getAppServiceDir : (Landroid/content/Context;)Ljava/io/File;
    //   43: lload_1
    //   44: invokestatic convertVersionCodeToStr : (J)Ljava/lang/String;
    //   47: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   50: astore_0
    //   51: aload #5
    //   53: ldc 'start rename folder to __dev__'
    //   55: invokevirtual appendLog : (Ljava/lang/String;)V
    //   58: aload_0
    //   59: invokevirtual exists : ()Z
    //   62: ifne -> 70
    //   65: aload_0
    //   66: invokevirtual mkdirs : ()Z
    //   69: pop
    //   70: new java/io/File
    //   73: dup
    //   74: aload #8
    //   76: ldc '__dev__'
    //   78: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   81: aload_0
    //   82: invokevirtual renameTo : (Ljava/io/File;)Z
    //   85: istore #6
    //   87: goto -> 100
    //   90: aload #5
    //   92: ldc 'rename folder fail to __dev__'
    //   94: invokevirtual appendLog : (Ljava/lang/String;)V
    //   97: iconst_0
    //   98: istore #6
    //   100: iload #6
    //   102: istore #7
    //   104: iload #6
    //   106: ifeq -> 127
    //   109: iload #6
    //   111: istore #7
    //   113: iload #4
    //   115: ifne -> 127
    //   118: aload_3
    //   119: aload_0
    //   120: aload #5
    //   122: invokestatic checkFileComplete : (Ljava/lang/String;Ljava/io/File;Lcom/tt/miniapp/event/BaseBundleEventHelper$BaseBundleEvent;)Z
    //   125: istore #7
    //   127: iload #7
    //   129: ifne -> 144
    //   132: aload #5
    //   134: ldc 'rename folder fail'
    //   136: invokevirtual appendLog : (Ljava/lang/String;)V
    //   139: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   141: monitorexit
    //   142: lconst_0
    //   143: lreturn
    //   144: aload #5
    //   146: ldc 'rename folder success'
    //   148: invokevirtual appendLog : (Ljava/lang/String;)V
    //   151: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   153: monitorexit
    //   154: lload_1
    //   155: lreturn
    //   156: astore_0
    //   157: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   159: monitorexit
    //   160: aload_0
    //   161: athrow
    //   162: astore #8
    //   164: goto -> 90
    // Exception table:
    //   from	to	target	type
    //   3	11	156	finally
    //   16	23	156	finally
    //   28	58	156	finally
    //   58	70	162	finally
    //   70	87	162	finally
    //   90	97	156	finally
    //   118	127	156	finally
    //   132	139	156	finally
    //   144	151	156	finally
  }
  
  private static void tryUnzipBaseBundle(BaseBundleEventHelper.BaseBundleEvent paramBaseBundleEvent, String paramString1, String paramString2, File paramFile) {
    try {
      StringBuilder stringBuilder = new StringBuilder("unzip");
      stringBuilder.append(paramString1);
      paramBaseBundleEvent.appendLog(stringBuilder.toString());
      IOUtils.unZipFolder(paramFile.getAbsolutePath(), paramString2);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("BaseBundleFileManager", new Object[] { "unzip exception", exception });
      StringBuilder stringBuilder = new StringBuilder("retry unzip fail");
      stringBuilder.append(paramString1);
      paramBaseBundleEvent.appendLog(stringBuilder.toString());
      return;
    } 
  }
  
  public static long unZipAssetsBundle(Context paramContext, String paramString1, String paramString2, BaseBundleEventHelper.BaseBundleEvent paramBaseBundleEvent, boolean paramBoolean) {
    File file = getTempBundleZipFile(paramContext, paramString2);
    try {
      paramBaseBundleEvent.appendLog("start copy buildIn baseBundle to temp dir");
      IOUtils.copyAssets(paramContext, paramString1, file.getAbsolutePath());
      if (file.exists()) {
        long l = unZipFileToBundle(paramContext, file, "buildin_bundle", paramBoolean, paramBaseBundleEvent);
        IOUtils.delete(getTempBundleFolderFile(paramContext, "buildin_bundle"));
        paramBaseBundleEvent.appendLog("delete temp baseBundle dir");
        return l;
      } 
      IOUtils.delete(getTempBundleFolderFile(paramContext, "buildin_bundle"));
      paramBaseBundleEvent.appendLog("delete temp baseBundle dir");
    } catch (Exception exception) {
      AppBrandLogger.e("BaseBundleFileManager", new Object[] { exception });
      paramBaseBundleEvent.appendLog("unZipBuildInBaseBundle exception", exception);
      IOUtils.delete(getTempBundleFolderFile(paramContext, "buildin_bundle"));
      paramBaseBundleEvent.appendLog("delete temp baseBundle dir");
    } finally {}
    return 0L;
  }
  
  public static long unZipFileToBundle(Context paramContext, File paramFile, String paramString, boolean paramBoolean, BaseBundleEventHelper.BaseBundleEvent paramBaseBundleEvent) {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   2: monitorenter
    //   3: aload_1
    //   4: invokevirtual exists : ()Z
    //   7: istore #6
    //   9: iload #6
    //   11: ifne -> 19
    //   14: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   16: monitorexit
    //   17: lconst_0
    //   18: lreturn
    //   19: aload_0
    //   20: aload_2
    //   21: invokestatic getBundleFolderFile : (Landroid/content/Context;Ljava/lang/String;)Ljava/io/File;
    //   24: astore #10
    //   26: new java/lang/StringBuilder
    //   29: dup
    //   30: ldc_w 'start unzip'
    //   33: invokespecial <init> : (Ljava/lang/String;)V
    //   36: astore #11
    //   38: aload #11
    //   40: aload_2
    //   41: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: aload #4
    //   47: aload #11
    //   49: invokevirtual toString : ()Ljava/lang/String;
    //   52: invokevirtual appendLog : (Ljava/lang/String;)V
    //   55: aload #4
    //   57: aload_2
    //   58: aload #10
    //   60: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   63: aload_1
    //   64: invokestatic tryUnzipBaseBundle : (Lcom/tt/miniapp/event/BaseBundleEventHelper$BaseBundleEvent;Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)V
    //   67: iload_3
    //   68: ifne -> 328
    //   71: aload_2
    //   72: new java/io/File
    //   75: dup
    //   76: aload #10
    //   78: ldc '__dev__'
    //   80: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   83: aload #4
    //   85: invokestatic checkFileComplete : (Ljava/lang/String;Ljava/io/File;Lcom/tt/miniapp/event/BaseBundleEventHelper$BaseBundleEvent;)Z
    //   88: istore #7
    //   90: iload #7
    //   92: istore #6
    //   94: iload #7
    //   96: ifne -> 161
    //   99: iconst_0
    //   100: istore #5
    //   102: iload #7
    //   104: istore #6
    //   106: iload #5
    //   108: iconst_3
    //   109: if_icmpgt -> 161
    //   112: iload #7
    //   114: istore #6
    //   116: iload #7
    //   118: ifne -> 161
    //   121: aload #4
    //   123: aload_2
    //   124: aload #10
    //   126: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   129: aload_1
    //   130: invokestatic tryUnzipBaseBundle : (Lcom/tt/miniapp/event/BaseBundleEventHelper$BaseBundleEvent;Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)V
    //   133: aload_2
    //   134: new java/io/File
    //   137: dup
    //   138: aload #10
    //   140: ldc '__dev__'
    //   142: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   145: aload #4
    //   147: invokestatic checkFileComplete : (Ljava/lang/String;Ljava/io/File;Lcom/tt/miniapp/event/BaseBundleEventHelper$BaseBundleEvent;)Z
    //   150: istore #7
    //   152: iload #5
    //   154: iconst_1
    //   155: iadd
    //   156: istore #5
    //   158: goto -> 102
    //   161: iload #6
    //   163: ifne -> 207
    //   166: new java/lang/StringBuilder
    //   169: dup
    //   170: invokespecial <init> : ()V
    //   173: astore_0
    //   174: aload_0
    //   175: aload_2
    //   176: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: pop
    //   180: aload_0
    //   181: ldc_w 'clear dir'
    //   184: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: pop
    //   188: aload #4
    //   190: aload_0
    //   191: invokevirtual toString : ()Ljava/lang/String;
    //   194: invokevirtual appendLog : (Ljava/lang/String;)V
    //   197: aload #10
    //   199: invokestatic clearDir : (Ljava/io/File;)V
    //   202: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   204: monitorexit
    //   205: lconst_0
    //   206: lreturn
    //   207: new java/lang/StringBuilder
    //   210: dup
    //   211: invokespecial <init> : ()V
    //   214: astore #10
    //   216: aload #10
    //   218: aload_2
    //   219: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: pop
    //   223: aload #10
    //   225: ldc_w 'baseBundle unzip success'
    //   228: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: pop
    //   232: aload #4
    //   234: aload #10
    //   236: invokevirtual toString : ()Ljava/lang/String;
    //   239: invokevirtual appendLog : (Ljava/lang/String;)V
    //   242: aload_0
    //   243: aload_2
    //   244: invokestatic getBundleVersion : (Landroid/content/Context;Ljava/lang/String;)I
    //   247: i2l
    //   248: lstore #8
    //   250: new java/lang/StringBuilder
    //   253: dup
    //   254: invokespecial <init> : ()V
    //   257: astore #10
    //   259: aload #10
    //   261: aload_2
    //   262: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: aload #10
    //   268: ldc_w 'get version:'
    //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: pop
    //   275: aload #10
    //   277: lload #8
    //   279: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   282: pop
    //   283: aload #4
    //   285: aload #10
    //   287: invokevirtual toString : ()Ljava/lang/String;
    //   290: invokevirtual appendLog : (Ljava/lang/String;)V
    //   293: aload_0
    //   294: lload #8
    //   296: aload_2
    //   297: iload_3
    //   298: aload #4
    //   300: invokestatic renameFileToBaseBundle : (Landroid/content/Context;JLjava/lang/String;ZLcom/tt/miniapp/event/BaseBundleEventHelper$BaseBundleEvent;)J
    //   303: lstore #8
    //   305: aload_1
    //   306: invokevirtual delete : ()Z
    //   309: pop
    //   310: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   312: monitorexit
    //   313: lload #8
    //   315: lreturn
    //   316: astore_0
    //   317: ldc com/tt/miniapp/manager/basebundle/BaseBundleFileManager
    //   319: monitorexit
    //   320: goto -> 325
    //   323: aload_0
    //   324: athrow
    //   325: goto -> 323
    //   328: iconst_1
    //   329: istore #6
    //   331: goto -> 161
    // Exception table:
    //   from	to	target	type
    //   3	9	316	finally
    //   19	67	316	finally
    //   71	90	316	finally
    //   121	152	316	finally
    //   166	202	316	finally
    //   207	310	316	finally
  }
  
  public static void updateLatestBaseBundleFile(Context paramContext, long paramLong) {
    Application application;
    Context context = paramContext;
    if (paramContext == null) {
      application = AppbrandContext.getInst().getApplicationContext();
      StringBuilder stringBuilder1 = new StringBuilder("AppbrandContext:");
      stringBuilder1.append(application);
      AppBrandLogger.d("BaseBundleFileManager", new Object[] { stringBuilder1.toString() });
    } 
    File file = getLatestBaseBundleFile();
    StringBuilder stringBuilder = new StringBuilder("context:");
    stringBuilder.append(application);
    AppBrandLogger.d("BaseBundleFileManager", new Object[] { stringBuilder.toString() });
    if (application != null)
      try {
        String str = file.getAbsolutePath();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(paramLong);
        IOUtils.writeStringToFile(str, stringBuilder1.toString(), "UTF-8");
        return;
      } catch (Exception exception) {
        return;
      }  
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\BaseBundleFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */