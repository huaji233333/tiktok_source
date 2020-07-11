package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.io.File;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetFileInfoCtrl extends b {
  public ApiGetFileInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      HashMap<Object, Object> hashMap;
      String str = (new JSONObject(this.mArgs)).optString("filePath");
      File file = new File(FileManager.inst().getRealFilePath(str));
      if (TextUtils.isEmpty(str)) {
        callbackFail(a.a(true, new String[] { getActionName(), str }));
        return;
      } 
      if (FileManager.inst().canRead(file) && file.exists()) {
        hashMap = new HashMap<Object, Object>();
        hashMap.put("size", Long.valueOf(file.length()));
        callbackOk(a.a(hashMap));
        return;
      } 
      callbackFail(a.b(new String[] { getActionName(), (String)hashMap }));
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "ApiHandler", jSONException.getStackTrace());
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "getFileInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetFileInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */