package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.util.HashMap;
import org.json.JSONObject;

public class ApiSaveFileCtrl extends b {
  public ApiSaveFileCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      final String tempFilePath = jSONObject.optString("tempFilePath");
      final String targetFilePath = jSONObject.optString("filePath");
      final StringBuilder failSB = new StringBuilder();
      if (!TextUtils.isEmpty(str1)) {
        Observable.create(new Function<String>() {
              public String fun() {
                return FileManager.inst().saveFile(tempFilePath, targetFilePath, failSB);
              }
            }).schudleOn(Schedulers.shortIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
              public void onError(Throwable param1Throwable) {
                ApiSaveFileCtrl.this.callbackFail(param1Throwable);
              }
              
              public void onSuccess(String param1String) {
                if (!TextUtils.isEmpty(param1String)) {
                  HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                  hashMap.put("savedFilePath", param1String);
                  ApiSaveFileCtrl.this.callbackOk(hashMap);
                  return;
                } 
                ApiSaveFileCtrl.this.callbackFail(failSB.toString());
              }
            });
        return;
      } 
      callbackIllegalParam("tempFilePath");
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiHandler", exception.getStackTrace());
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "saveFile";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiSaveFileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */