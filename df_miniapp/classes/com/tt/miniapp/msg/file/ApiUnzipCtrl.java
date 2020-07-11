package com.tt.miniapp.msg.file;

import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;
import org.json.JSONObject;

public class ApiUnzipCtrl extends AbsStringParamCtrl {
  public ApiUnzipCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() throws Exception {
    String str1 = optString("zipFilePath");
    String str2 = optString("targetPath");
    File file1 = new File(getRealFilePath(str1));
    File file2 = new File(getRealFilePath(str2));
    if (!file2.exists())
      file2.mkdirs(); 
    StreamLoader.waitExtractFinishIfNeeded(str1);
    if (!canRead(file1)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str1 });
      return false;
    } 
    if (!canWrite(file2)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str2 });
      return false;
    } 
    if (!file1.exists() || !file1.isFile() || !file2.exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str1, str2 });
      return false;
    } 
    long l = IOUtils.getZipTrueSize(file1.getAbsolutePath());
    if (FileManager.inst().isUserDirOverLimit(l)) {
      this.mExtraInfo = "user dir saved file size limit exceeded";
      return false;
    } 
    try {
      IOUtils.unZipFolder(file1.getAbsolutePath(), file2.getAbsolutePath(), true);
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("ApiUnzipCtrl", new Object[] { exception });
      throw exception;
    } 
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("zipFilePath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("zipFilePath"), true));
    this.mArgumentsMap.put("targetPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("targetPath"), false));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiUnzipCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */