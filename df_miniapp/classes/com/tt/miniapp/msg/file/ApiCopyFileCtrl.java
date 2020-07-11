package com.tt.miniapp.msg.file;

import android.text.TextUtils;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;
import org.json.JSONObject;

public class ApiCopyFileCtrl extends AbsStringParamCtrl {
  public ApiCopyFileCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    String str1 = optString("srcPath");
    String str2 = optString("destPath");
    File file1 = new File(getRealFilePath(str1));
    File file2 = new File(getRealFilePath(str2));
    if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str1, str2 });
      return false;
    } 
    if (!canRead(file1) || !canWrite(file2)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str1, str2 });
      return false;
    } 
    TTAPkgFile tTAPkgFile = StreamLoader.findFile(str1);
    if ((tTAPkgFile == null && !file1.exists()) || !file2.getParentFile().exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str1, str2 });
      return false;
    } 
    if ((tTAPkgFile != null && FileManager.inst().isUserDirOverLimit(tTAPkgFile.getSize())) || (file1.exists() && FileManager.inst().isUserDirOverLimit(file1.length()))) {
      this.mExtraInfo = "user dir saved file size limit exceeded";
      return false;
    } 
    if (file1.exists()) {
      IOUtils.copyFile(file1, file2, false);
      return true;
    } 
    return (tTAPkgFile != null) ? StreamLoader.extractToFile(str1, file2.getParent(), file2.getName()) : false;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("srcPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("srcPath"), true));
    this.mArgumentsMap.put("destPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("destPath"), true));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiCopyFileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */