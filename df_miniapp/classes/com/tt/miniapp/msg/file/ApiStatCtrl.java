package com.tt.miniapp.msg.file;

import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

public class ApiStatCtrl extends AbsStringParamCtrl {
  public ApiStatCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    String str = optString("path");
    File file = new File(getRealFilePath(str));
    if (!canRead(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str });
      return false;
    } 
    if (!file.exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { str });
      return false;
    } 
    Stats stats = Stats.getFileStats(getRealFilePath(str));
    if (stats != null) {
      this.mExtraData = new HashMap<String, Object>();
      this.mExtraData.put("stat", stats.toJson());
      return true;
    } 
    this.mExtraInfo = getFormatExtraInfo("get stat fail %s", new Object[] { str });
    return false;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("path", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("path"), true));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiStatCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */