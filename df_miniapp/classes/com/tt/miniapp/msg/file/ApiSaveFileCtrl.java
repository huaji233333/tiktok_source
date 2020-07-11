package com.tt.miniapp.msg.file;

import android.text.TextUtils;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import java.util.HashMap;
import org.json.JSONObject;

public class ApiSaveFileCtrl extends AbsStringParamCtrl {
  public ApiSaveFileCtrl(String paramString) {
    super(paramString);
  }
  
  protected boolean handleInvoke() {
    HashMap<Object, Object> hashMap;
    String str1 = optString("tempFilePath");
    String str2 = optString("filePath");
    StringBuilder stringBuilder1 = new StringBuilder();
    StringBuilder stringBuilder2 = new StringBuilder("current thread:");
    stringBuilder2.append(Thread.currentThread().getName());
    AppBrandLogger.d("ApiSaveFileCtrl", new Object[] { stringBuilder2.toString() });
    str1 = FileManager.inst().saveFile(str1, str2, stringBuilder1);
    if (!TextUtils.isEmpty(str1)) {
      hashMap = new HashMap<Object, Object>();
      hashMap.put("savedFilePath", str1);
      this.mExtraData = (HashMap)hashMap;
      return true;
    } 
    this.mExtraInfo = hashMap.toString();
    return false;
  }
  
  protected void parseParam(String paramString) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    this.mArgumentsMap.put("tempFilePath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("tempFilePath"), true));
    this.mArgumentsMap.put("filePath", new AbsFileCtrl.ValuePair<String>(this, jSONObject.optString("filePath"), false));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\ApiSaveFileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */