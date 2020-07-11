package com.tt.miniapp.msg.file;

import java.io.File;
import org.json.JSONObject;

public class ApiUnlinkCtrl extends AbsStringParamCtrl {
  public ApiUnlinkCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    String str = optString("filePath");
    File file = new File(getRealFilePath(str));
    if (!canWrite(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str });
      return false;
    } 
    if (!file.exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { str });
      return false;
    } 
    if (!file.isFile()) {
      this.mExtraInfo = getFormatExtraInfo("operation not permitted, %s %s", new Object[] { this.mApiName, str });
      return false;
    } 
    return file.delete();
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("filePath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("filePath"), true));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiUnlinkCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */