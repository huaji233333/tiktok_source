package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiRemoveSavedFileCtrl extends b {
  public ApiRemoveSavedFileCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("filePath");
      if (!FileManager.inst().removeSavedFile(str)) {
        callbackFail(a.a(true, new String[] { getActionName(), str }));
        return;
      } 
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiHandler", exception.getStackTrace());
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "removeSavedFile";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiRemoveSavedFileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */