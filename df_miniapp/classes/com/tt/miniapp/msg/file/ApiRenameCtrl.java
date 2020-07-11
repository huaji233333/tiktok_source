package com.tt.miniapp.msg.file;

import java.io.File;
import org.json.JSONObject;

public class ApiRenameCtrl extends AbsStringParamCtrl {
  public ApiRenameCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    String str1 = optString("oldPath");
    String str2 = optString("newPath");
    File file1 = new File(getRealFilePath(str1));
    File file2 = new File(getRealFilePath(str2));
    if (!canRead(file1) || !canWrite(file2)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str1, str2 });
      return false;
    } 
    if (!file1.exists() || !file2.getParentFile().exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str1, str2 });
      return false;
    } 
    boolean bool = file1.renameTo(file2);
    if (!bool)
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str1, str2 }); 
    return bool;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("oldPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("oldPath"), true));
    this.mArgumentsMap.put("newPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("newPath"), true));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiRenameCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */