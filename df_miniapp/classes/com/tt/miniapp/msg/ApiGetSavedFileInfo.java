package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.util.HashMap;
import org.json.JSONObject;

public class ApiGetSavedFileInfo extends b {
  public ApiGetSavedFileInfo(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("filePath");
      FileManager.FileInfo fileInfo = FileManager.inst().getSavedFileInfo(str);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiGetSavedFileInfo", exception.getStackTrace());
      exception = null;
    } 
    if (exception == null) {
      callbackDefaultMsg(false);
      return;
    } 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("size", Long.valueOf(((FileManager.FileInfo)exception).size));
    hashMap.put("createTime", Long.valueOf(((FileManager.FileInfo)exception).createTime));
    callbackOtherExtraMsg(true, hashMap);
  }
  
  public String getActionName() {
    return "getSavedFileInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetSavedFileInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */