package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiGetSavedFileListCtrl extends b {
  public ApiGetSavedFileListCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      List list = FileManager.inst().getFileListInfo();
      JSONObject jSONObject = new JSONObject();
      JSONArray jSONArray = new JSONArray();
      if (list != null)
        for (FileManager.FileInfo fileInfo : list) {
          JSONObject jSONObject1 = new JSONObject();
          jSONObject1.put("filePath", fileInfo.filePath);
          jSONObject1.put("createTime", fileInfo.createTime);
          jSONObject1.put("size", fileInfo.size);
          jSONArray.put(jSONObject1);
        }  
      jSONObject.put("fileList", jSONArray);
      callbackOk(jSONObject);
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      AppBrandLogger.stacktrace(6, "ApiGetSavedFileListCtrl", exception.getStackTrace());
      return;
    } 
  }
  
  public String getActionName() {
    return "getSavedFileList";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetSavedFileListCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */