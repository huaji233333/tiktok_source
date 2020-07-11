package com.tt.miniapp.msg.file;

import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.util.FileUtil;
import java.io.File;
import org.json.JSONObject;

public class ApiRmDirCtrl extends AbsStringParamCtrl {
  public ApiRmDirCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    String str = optString("dirPath");
    boolean bool2 = optBoolean("recursive");
    File file = new File(getRealFilePath(str));
    boolean bool3 = canWrite(file);
    boolean bool1 = true;
    if (!bool3 || FileManager.inst().isUserDir(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str });
      return false;
    } 
    if (!file.exists() || !file.isDirectory()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { str });
      return false;
    } 
    if (bool2) {
      FileUtil.delete(file);
    } else {
      bool1 = file.delete();
    } 
    if (!bool1)
      this.mExtraInfo = "directory not empty"; 
    return bool1;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("dirPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("dirPath"), true));
    this.mArgumentsMap.put("recursive", new AbsFileCtrl.ValuePair<Boolean>(this, Boolean.valueOf(jSONObject.optBoolean("recursive")), false));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiRmDirCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */