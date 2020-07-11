package com.tt.miniapp.msg.file.write;

import android.text.TextUtils;
import com.tt.miniapp.msg.file.AbsFileCtrl;
import com.tt.miniapp.msg.file.AbsStringParamCtrl;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiWriteFileCtrl extends AbsStringParamCtrl {
  private String mBufferData;
  
  public ApiWriteFileCtrl(String paramString) {
    super(paramString);
  }
  
  private String getBase64String(JSONArray paramJSONArray) {
    if (paramJSONArray != null) {
      int i = 0;
      try {
        int j = paramJSONArray.length();
        while (i < j) {
          JSONObject jSONObject = paramJSONArray.getJSONObject(i);
          if (TextUtils.equals(jSONObject.optString("key"), "data"))
            return jSONObject.optString("base64"); 
          i++;
        } 
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "ApiWriteFileCtrl", exception.getStackTrace());
      } 
    } 
    return null;
  }
  
  public boolean handleInvoke() throws Exception {
    String str3 = optString("filePath");
    String str1 = optString("data");
    String str2 = optString("encoding");
    File file = new File(getRealFilePath(str3));
    AppBrandLogger.d("ApiWriteFileCtrl", new Object[] { "filePath ", str3, " \n data ", str1, " \n encoding ", str2 });
    if (!canWrite(file)) {
      this.mExtraInfo = getPermissionDenyStr(new String[] { this.mApiName, str3 });
      return false;
    } 
    if (!file.getParentFile().exists()) {
      this.mExtraInfo = getNoSuchFileStr(new String[] { this.mApiName, str3 });
      return false;
    } 
    str3 = this.mBufferData;
    if (str3 != null) {
      int i = (str3.getBytes()).length;
      if (FileManager.inst().isUserDirOverLimit(i)) {
        this.mExtraInfo = "user dir saved file size limit exceeded";
        return false;
      } 
      ToolUtils.writeStringToFile(file.getAbsolutePath(), str3, "base64");
      return true;
    } 
    if (!TextUtils.isEmpty(str1) && str1.getBytes() != null) {
      int i = (str1.getBytes()).length;
      if (FileManager.inst().isUserDirOverLimit(i)) {
        this.mExtraInfo = "user dir saved file size limit exceeded";
        return false;
      } 
      ToolUtils.writeStringToFile(file.getAbsolutePath(), str1, str2);
    } 
    return true;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("filePath", new AbsFileCtrl.ValuePair((AbsFileCtrl)this, jSONObject.optString("filePath"), true));
    this.mArgumentsMap.put("data", new AbsFileCtrl.ValuePair((AbsFileCtrl)this, jSONObject.optString("data"), false));
    this.mArgumentsMap.put("encoding", new AbsFileCtrl.ValuePair((AbsFileCtrl)this, jSONObject.optString("encoding", "utf-8"), false));
    if (jSONObject.has("__nativeBuffers__"))
      this.mBufferData = getBase64String(jSONObject.optJSONArray("__nativeBuffers__")); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\write\ApiWriteFileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */