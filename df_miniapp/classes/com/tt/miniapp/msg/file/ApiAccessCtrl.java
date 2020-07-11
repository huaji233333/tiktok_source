package com.tt.miniapp.msg.file;

import android.text.TextUtils;
import com.tt.miniapp.streamloader.StreamLoader;
import java.io.File;
import org.json.JSONObject;

public class ApiAccessCtrl extends AbsStringParamCtrl {
  public ApiAccessCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    String str = optString("path");
    if (TextUtils.isEmpty(str)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str });
      return false;
    } 
    File file = new File(getRealFilePath(str));
    if (!canRead(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str });
      return false;
    } 
    if (!file.exists() && StreamLoader.findFile(str) == null && !StreamLoader.isDirectoryOfPkg(str)) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str });
      return false;
    } 
    return true;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("path", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("path"), true));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiAccessCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */