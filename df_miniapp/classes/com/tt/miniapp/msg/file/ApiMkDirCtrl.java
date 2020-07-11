package com.tt.miniapp.msg.file;

import java.io.File;
import org.json.JSONObject;

public class ApiMkDirCtrl extends AbsStringParamCtrl {
  public ApiMkDirCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    String str = optString("dirPath");
    boolean bool = optBoolean("recursive");
    File file = new File(getRealFilePath(str));
    if (!canWrite(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str });
      return false;
    } 
    if (file.exists()) {
      this.mExtraInfo = getFormatExtraInfo("file already exists, %s %s", new Object[] { this.mApiName, str });
      return false;
    } 
    if (!bool && !file.getParentFile().exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str });
      return false;
    } 
    return file.mkdirs();
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("dirPath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("dirPath"), true));
    this.mArgumentsMap.put("recursive", new AbsFileCtrl.ValuePair<Boolean>(this, Boolean.valueOf(jSONObject.optBoolean("recursive")), false));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiMkDirCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */