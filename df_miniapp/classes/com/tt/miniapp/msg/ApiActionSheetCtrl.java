package com.tt.miniapp.msg;

import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiActionSheetCtrl extends b {
  public ApiActionSheetCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONArray jSONArray = (new JSONObject(this.mArgs)).optJSONArray("itemList");
      if (jSONArray != null) {
        final ArrayList<String> list = new ArrayList();
        for (int i = 0;; i++) {
          if (i < jSONArray.length()) {
            String str = jSONArray.getString(i);
            if (!TextUtils.isEmpty(str))
              arrayList.add(str); 
          } else {
            if (arrayList.size() == 0) {
              callbackFail("itemList不能为空");
              return;
            } 
            if (arrayList.size() > 6) {
              callbackFail("param.itemList should has at most 6 items");
              return;
            } 
            AppbrandContext.mainHandler.post(new Runnable() {
                  public void run() {
                    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
                    if (miniappHostBase == null) {
                      ApiActionSheetCtrl.this.callbackFail("MiniAppActivity is null");
                      return;
                    } 
                    int j = list.size();
                    String[] arrayOfString = new String[j];
                    for (int i = 0; i < j; i++)
                      arrayOfString[i] = list.get(i); 
                    HostDependManager.getInst().showActionSheet((Context)miniappHostBase, ApiActionSheetCtrl.this.mArgs, arrayOfString, new NativeModule.NativeModuleCallback<Integer>() {
                          public void onNativeModuleCall(Integer param2Integer) {
                            int i = param2Integer.intValue();
                            if (i < 0) {
                              ApiActionSheetCtrl.this.callbackFail("cancel");
                              return;
                            } 
                            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                            hashMap.put("tapIndex", Integer.valueOf(i));
                            ApiActionSheetCtrl.this.callbackOk(hashMap);
                          }
                        });
                  }
                });
            return;
          } 
        } 
      } 
      callbackFail("itemList is empty");
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_ApiActionSheetCtrl", jSONException.getStackTrace());
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "showActionSheet";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiActionSheetCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */