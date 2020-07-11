package com.tt.miniapp.streamloader;

import android.text.TextUtils;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapp.ttapkgdecoder.TTAPkgInfo;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.IOUtils;
import g.g;
import g.q;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class StreamLoader {
  private static volatile LoadTask sLoadTask;
  
  public static boolean extractToFile(String paramString1, String paramString2, String paramString3) {
    if (sLoadTask != null) {
      byte[] arrayOfByte = loadByteFromStream(paramString1);
      if (paramString3 != null && arrayOfByte != null && arrayOfByte.length > 0) {
        g g;
        File file = new File(paramString2, paramString3);
        if (!file.getParentFile().exists())
          file.getParentFile().mkdirs(); 
        String str2 = null;
        String str3 = null;
        paramString3 = null;
        String str1 = paramString3;
        paramString1 = str2;
        paramString2 = str3;
        try {
          if (!file.exists()) {
            str1 = paramString3;
            paramString1 = str2;
            paramString2 = str3;
            file.createNewFile();
          } 
          str1 = paramString3;
          paramString1 = str2;
          paramString2 = str3;
          g g3 = q.a(q.a(file));
          g = g3;
          g g1 = g3;
          g g2 = g3;
          g3.c(arrayOfByte);
          g = g3;
          g1 = g3;
          g2 = g3;
          g3.flush();
        } catch (FileNotFoundException fileNotFoundException) {
          return false;
        } catch (IOException iOException) {
        
        } finally {
          if (g != null)
            try {
              g.close();
            } catch (IOException iOException) {} 
        } 
      } else {
        return false;
      } 
    } else {
      return false;
    } 
    try {
      paramString1.close();
    } catch (IOException iOException) {}
    return false;
  }
  
  public static TTAPkgFile findFile(String paramString) {
    if (sLoadTask != null) {
      try {
        return sLoadTask.findFile(paramString);
      } finally {
        paramString = null;
      } 
    } else {
      StringBuilder stringBuilder = new StringBuilder("findFile not found: ");
      stringBuilder.append(paramString);
      AppBrandLogger.e("StreamLoader", new Object[] { stringBuilder.toString() });
    } 
    return null;
  }
  
  public static LoadTask getLoadTask() {
    return sLoadTask;
  }
  
  public static InputStream getStream(String paramString) {
    if (sLoadTask != null) {
      TTAPkgFile tTAPkgFile = sLoadTask.findFile(paramString);
      if (tTAPkgFile != null)
        return sLoadTask.requestStream(tTAPkgFile); 
    } 
    return null;
  }
  
  public static boolean isDirectoryOfPkg(String paramString) {
    return (sLoadTask != null) ? sLoadTask.isDirectoryOfPkg(paramString) : false;
  }
  
  public static Set<String> listTTAPKG(String paramString) {
    String str = FileManager.inst().getSchemaFilePath(paramString);
    if (str.startsWith("./")) {
      paramString = str.substring(2);
    } else {
      paramString = str;
      if (str.startsWith("/"))
        paramString = str.substring(1); 
    } 
    LoadTask loadTask = getLoadTask();
    HashSet<String> hashSet = new HashSet();
    if (loadTask == null)
      return hashSet; 
    TTAPkgInfo tTAPkgInfo = loadTask.getTTAPkgInfo();
    if (tTAPkgInfo == null)
      return hashSet; 
    Collection collection = tTAPkgInfo.getFileNames();
    if (collection != null && !collection.isEmpty())
      for (String str1 : collection) {
        if (str1 != null && str1.startsWith(paramString)) {
          String[] arrayOfString = PathUtils.relativize(str1, paramString).split("/");
          if (arrayOfString.length > 0)
            hashSet.add(arrayOfString[0]); 
        } 
      }  
    return hashSet;
  }
  
  public static byte[] loadByteFromStream(String paramString) {
    if (sLoadTask != null) {
      TTAPkgFile tTAPkgFile = sLoadTask.findFile(paramString);
      if (tTAPkgFile != null)
        return sLoadTask.requestBytes(tTAPkgFile); 
    } 
    StringBuilder stringBuilder = new StringBuilder("不应该走到这里来的: ");
    stringBuilder.append(paramString);
    AppBrandLogger.eWithThrowable("StreamLoader", stringBuilder.toString(), new Throwable());
    return IOUtils.readBytes(paramString);
  }
  
  public static String loadStringFromStream(String paramString) {
    byte[] arrayOfByte = loadByteFromStream(paramString);
    return (arrayOfByte != null) ? sLoadTask.getStringCache(paramString, arrayOfByte) : null;
  }
  
  public static void streamLoadApp(AppInfoEntity paramAppInfoEntity, File paramFile, String paramString1, String paramString2, RequestType paramRequestType, boolean paramBoolean, StreamLoadListener paramStreamLoadListener) {
    TimeLogger.getInstance().logTimeDuration(new String[] { "StreamLoader_streamLoadApp" });
    if (sLoadTask != null)
      sLoadTask.release(); 
    LoadTask loadTask = new LoadTask(paramAppInfoEntity, paramFile, paramString1, paramString2, paramRequestType, paramBoolean, 10485760);
    sLoadTask = loadTask;
    loadTask.startLoad(paramStreamLoadListener);
    AppBrandLogger.i("StreamLoader", new Object[] { "stream load with pkg file ", paramFile.getAbsolutePath(), ", install at ", paramString1 });
  }
  
  public static String waitExtractFinishIfNeeded(String paramString) {
    String str;
    TTAPkgFile tTAPkgFile = findFile(paramString);
    if (sLoadTask != null && tTAPkgFile != null) {
      str = sLoadTask.waitExtractFinish(tTAPkgFile);
    } else {
      str = "";
    } 
    return TextUtils.isEmpty(str) ? paramString : str;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\StreamLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */