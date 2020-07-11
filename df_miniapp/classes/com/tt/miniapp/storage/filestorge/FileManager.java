package com.tt.miniapp.storage.filestorge;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IAppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.StorageUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
  private String mAppId;
  
  private String mCodeRootFilePath = "";
  
  private String mFileFilePath;
  
  private File mMiniAppCacheDir;
  
  private String mTempFilePath;
  
  private String mUserFilePath;
  
  private FileManager() {
    reforceInit();
  }
  
  static void ensureDir(File paramFile) {
    if (!paramFile.exists())
      paramFile.mkdirs(); 
  }
  
  private String getRealFilePath(String paramString1, String paramString2, String paramString3) {
    String str = paramString1.substring(paramString3.length());
    if (!TextUtils.isEmpty(str) && str.startsWith(File.separator)) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString2);
      stringBuilder1.append(paramString1.substring(paramString3.length()));
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString2);
    stringBuilder.append(File.separator);
    stringBuilder.append(paramString1.substring(paramString3.length()));
    return stringBuilder.toString();
  }
  
  private void initAllFilePath() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    this.mMiniAppCacheDir = new File(StorageUtil.getExternalCacheDir((Context)application), "TT/sandbox");
    try {
      this.mTempFilePath = getTempDir().getCanonicalPath();
      this.mUserFilePath = getUserDir().getCanonicalPath();
      this.mFileFilePath = StorageUtil.getExternalFilesDir((Context)application).getCanonicalPath();
      return;
    } catch (IOException iOException) {
      AppBrandLogger.e("tma_FileManager", new Object[] { "initAllFilePath", iOException });
      InnerEventHelper.mpTechnologyMsg("initAllFilePath");
      return;
    } 
  }
  
  public static FileManager inst() {
    return Holder.instance;
  }
  
  public static boolean isChildOfOrEqual(File paramFile1, File paramFile2) {
    try {
      String str = paramFile2.getCanonicalPath();
      while (paramFile1 != null) {
        if (paramFile1.getCanonicalPath().equals(str))
          return true; 
        paramFile1 = paramFile1.getParentFile();
      } 
      return false;
    } catch (IOException iOException) {
      return false;
    } 
  }
  
  public static boolean isTTFileDir(String paramString) {
    return (paramString == null) ? false : paramString.startsWith("ttfile");
  }
  
  public boolean canRead(File paramFile) {
    try {
      String str = paramFile.getCanonicalPath();
      if (TextUtils.isEmpty(str))
        return false; 
      if (!TextUtils.equals(str, this.mUserFilePath)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mUserFilePath);
        stringBuilder.append(File.separator);
        if (str.startsWith(stringBuilder.toString()))
          return true; 
        if (!TextUtils.equals(str, this.mTempFilePath)) {
          stringBuilder = new StringBuilder();
          stringBuilder.append(this.mTempFilePath);
          stringBuilder.append(File.separator);
          if (str.startsWith(stringBuilder.toString()))
            return true; 
          if (!TextUtils.equals(str, this.mCodeRootFilePath)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mCodeRootFilePath);
            stringBuilder.append(File.separator);
            boolean bool = str.startsWith(stringBuilder.toString());
            if (bool)
              return true; 
          } else {
            return true;
          } 
        } else {
          return true;
        } 
      } else {
        return true;
      } 
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_FileManager", iOException.getStackTrace());
    } 
    return false;
  }
  
  public boolean canWrite(File paramFile) {
    try {
      String str = paramFile.getCanonicalPath();
      if (TextUtils.isEmpty(str))
        return false; 
      if (!TextUtils.equals(str, this.mUserFilePath)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mUserFilePath);
        stringBuilder.append(File.separator);
        boolean bool = str.startsWith(stringBuilder.toString());
        if (bool)
          return true; 
      } else {
        return true;
      } 
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_FileManager", iOException.getStackTrace());
    } 
    return false;
  }
  
  public void clearTempDir() {
    IOUtils.clearDir(getTempDir());
  }
  
  public void clearUserDir() {
    IOUtils.clearDir(getUserDir());
  }
  
  long getFileCreateTime(File paramFile) {
    return paramFile.lastModified();
  }
  
  public List<FileInfo> getFileListInfo() {
    ArrayList<FileInfo> arrayList = new ArrayList();
    File[] arrayOfFile = getUserDir().listFiles();
    if (arrayOfFile == null)
      return arrayList; 
    int j = arrayOfFile.length;
    for (int i = 0; i < j; i++) {
      File file = arrayOfFile[i];
      FileInfo fileInfo = new FileInfo();
      fileInfo.createTime = getFileCreateTime(file);
      fileInfo.filePath = getSchemaFilePath(file);
      fileInfo.size = getFileSize(file);
      arrayList.add(fileInfo);
    } 
    return arrayList;
  }
  
  long getFileSize(File paramFile) {
    return paramFile.length();
  }
  
  public String getRealFilePath(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return this.mCodeRootFilePath; 
    if (paramString.startsWith("ttfile://user"))
      return getRealFilePath(paramString, this.mUserFilePath, "ttfile://user"); 
    if (paramString.startsWith("ttfile://temp"))
      return getRealFilePath(paramString, this.mTempFilePath, "ttfile://temp"); 
    if (paramString.startsWith("http"))
      return paramString; 
    String str = paramString;
    if (!paramString.startsWith(this.mCodeRootFilePath)) {
      str = paramString;
      if (!paramString.startsWith(this.mUserFilePath)) {
        str = paramString;
        if (!paramString.startsWith(this.mTempFilePath)) {
          if (paramString.startsWith(this.mFileFilePath))
            return paramString; 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(this.mCodeRootFilePath);
          stringBuilder.append(File.separator);
          stringBuilder.append(paramString);
          str = stringBuilder.toString();
        } 
      } 
    } 
    return str;
  }
  
  public String getRealSchemaPath(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("file://");
    stringBuilder.append(getRealFilePath(paramString));
    return stringBuilder.toString();
  }
  
  public FileInfo getSavedFileInfo(String paramString) {
    File file = new File(getRealFilePath(paramString));
    if (canRead(file) && file.exists()) {
      FileInfo fileInfo = new FileInfo();
      fileInfo.filePath = paramString;
      fileInfo.createTime = getFileCreateTime(file);
      fileInfo.size = getFileSize(file);
      return fileInfo;
    } 
    return null;
  }
  
  public String getSchemaFilePath(File paramFile) {
    try {
      return getSchemaFilePath(paramFile.getCanonicalPath());
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_FileManager", iOException.getStackTrace());
      return null;
    } 
  }
  
  public String getSchemaFilePath(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    if (paramString.startsWith(this.mUserFilePath)) {
      StringBuilder stringBuilder = new StringBuilder("ttfile://user");
      stringBuilder.append(paramString.substring(this.mUserFilePath.length()));
      return stringBuilder.toString();
    } 
    if (paramString.startsWith(this.mTempFilePath)) {
      StringBuilder stringBuilder = new StringBuilder("ttfile://temp");
      stringBuilder.append(paramString.substring(this.mTempFilePath.length()));
      return stringBuilder.toString();
    } 
    String str = paramString;
    if (paramString.startsWith(this.mCodeRootFilePath)) {
      if (paramString.length() - this.mCodeRootFilePath.length() > 0)
        return paramString.substring(this.mCodeRootFilePath.length() + 1); 
      str = paramString.substring(this.mCodeRootFilePath.length());
    } 
    return str;
  }
  
  public File getTempDir() {
    return getTempDir(this.mAppId);
  }
  
  public File getTempDir(String paramString) {
    String str2 = HostDependManager.getInst().getPrefixPath();
    boolean bool = TextUtils.isEmpty(str2);
    File file2 = this.mMiniAppCacheDir;
    StringBuilder stringBuilder = new StringBuilder("temp/");
    String str1 = paramString;
    if ((bool ^ true) != 0) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str2);
      stringBuilder1.append(File.separator);
      stringBuilder1.append(paramString);
      str1 = stringBuilder1.toString();
    } 
    stringBuilder.append(str1);
    File file1 = new File(file2, stringBuilder.toString());
    ensureDir(file1);
    return file1;
  }
  
  public File getUserDir() {
    return getUserDir(this.mAppId);
  }
  
  public File getUserDir(String paramString) {
    String str2 = HostDependManager.getInst().getPrefixPath();
    boolean bool = TextUtils.isEmpty(str2);
    File file2 = this.mMiniAppCacheDir;
    StringBuilder stringBuilder = new StringBuilder("user/");
    String str1 = paramString;
    if ((bool ^ true) != 0) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str2);
      stringBuilder1.append(File.separator);
      stringBuilder1.append(paramString);
      str1 = stringBuilder1.toString();
    } 
    stringBuilder.append(str1);
    File file1 = new File(file2, stringBuilder.toString());
    ensureDir(file1);
    return file1;
  }
  
  public boolean isChildOfInstallDir(File paramFile) {
    return isChildOfOrEqual(paramFile, new File(this.mCodeRootFilePath));
  }
  
  public boolean isUserDir(File paramFile) {
    try {
      String str = paramFile.getCanonicalPath();
      if (!TextUtils.equals(str, this.mUserFilePath)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mUserFilePath);
        stringBuilder.append(File.separator);
        boolean bool = TextUtils.equals(str, stringBuilder.toString());
        if (!bool)
          return false; 
      } 
      return true;
    } catch (IOException iOException) {
      return false;
    } 
  }
  
  public boolean isUserDir(String paramString) {
    return !TextUtils.isEmpty(paramString) ? paramString.startsWith("ttfile://user") : false;
  }
  
  public boolean isUserDirOverLimit(long paramLong) {
    long l;
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null)
      return true; 
    if (appInfoEntity.isGame()) {
      l = 52428800L;
    } else {
      l = 10485760L;
    } 
    return (paramLong + IOUtils.getDirSizeNonRecursive(getUserDir()) > l);
  }
  
  public void reforceInit() {
    IAppbrandApplication iAppbrandApplication = AppbrandApplication.getInst();
    if (iAppbrandApplication != null) {
      AppInfoEntity appInfoEntity = iAppbrandApplication.getAppInfo();
      if (appInfoEntity != null)
        this.mAppId = appInfoEntity.appId; 
    } 
    initAllFilePath();
    long l = System.currentTimeMillis();
    clearTempDir();
    AppBrandLogger.d("tma_FileManager", new Object[] { "clearTempDir cost time == ", Long.valueOf(System.currentTimeMillis() - l) });
  }
  
  public boolean removeSavedFile(String paramString) {
    File file = new File(getRealFilePath(paramString));
    return !canWrite(file) ? false : (file.exists() ? file.delete() : false);
  }
  
  public String saveFile(String paramString1, String paramString2, StringBuilder paramStringBuilder) {
    File file1;
    String str = getRealFilePath(paramString1);
    if (TextUtils.isEmpty(paramString2)) {
      file1 = getUserDir();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(System.currentTimeMillis());
      stringBuilder.append(IOUtils.getFileExtension(str));
      file1 = new File(file1, stringBuilder.toString());
    } else {
      file1 = new File(getRealFilePath(paramString2));
    } 
    File file2 = new File(str);
    if (!canRead(file2)) {
      paramStringBuilder.append(a.a("permission denied, %s %s", new Object[] { "saveFile", paramString1 }));
      return null;
    } 
    if (!canWrite(file1)) {
      paramStringBuilder.append(a.a("permission denied, %s %s", new Object[] { "saveFile", paramString2 }));
      return null;
    } 
    if (!file2.exists()) {
      paramStringBuilder.append(a.a("no such file or directory, %s %s", new Object[] { "saveFile", paramString1 }));
      return null;
    } 
    if (!file1.getParentFile().exists())
      try {
        paramStringBuilder.append(a.a("no such file or directory, %s %s", new Object[] { "saveFile", getSchemaFilePath(file1.getParentFile().getCanonicalPath()) }));
        return null;
      } catch (IOException iOException) {
        paramStringBuilder.append(a.a("no such file or directory, %s %s", new Object[] { "saveFile", getSchemaFilePath(file1.getParent()) }));
        AppBrandLogger.stacktrace(5, "tma_FileManager", iOException.getStackTrace());
        return null;
      }  
    if (isUserDirOverLimit(file2.length())) {
      paramStringBuilder.append("user dir saved file size limit exceeded");
      return null;
    } 
    if (TextUtils.equals((CharSequence)iOException, paramString2)) {
      AppBrandLogger.d("tma_FileManager", new Object[] { "TextUtils.equals(tempPath, targetFilePath)" });
      return getSchemaFilePath(file1);
    } 
    if (IOUtils.copyFile(new File(str), file1, false) == 0)
      return getSchemaFilePath(file1); 
    paramStringBuilder.append("copy file fail");
    return null;
  }
  
  public void setCodeRootFilePath(String paramString) {
    try {
      if (!TextUtils.isEmpty(paramString) && TextUtils.isEmpty(this.mCodeRootFilePath))
        this.mCodeRootFilePath = (new File(paramString)).getCanonicalPath(); 
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_FileManager", new Object[] { "setmCodeRootFilePath", exception });
      return;
    } 
  }
  
  public static class FileInfo {
    public long createTime;
    
    public String filePath;
    
    public long size;
  }
  
  static class Holder {
    static final FileManager instance = new FileManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\filestorge\FileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */