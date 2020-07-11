package com.tt.miniapp.webbridge.sync;

import android.app.Activity;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.y.b;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ShowPickerViewHandler extends WebEventHandler {
  public ShowPickerViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      final int current = jSONObject.optInt("current");
      JSONArray jSONArray = jSONObject.optJSONArray("array");
      final ArrayList<String> values = new ArrayList();
      if (jSONArray != null) {
        int j = 0;
        int k = jSONArray.length();
        while (j < k) {
          arrayList.add(jSONArray.getString(j));
          j++;
        } 
      } 
      if (arrayList.size() > 0) {
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                if (ShowPickerViewHandler.this.mRender == null) {
                  AppBrandLogger.e("tma_ShowPickerViewHandler", new Object[] { "current render is null" });
                  ShowPickerViewHandler.this.invokeHandler("current render is null");
                  return;
                } 
                Activity activity = ShowPickerViewHandler.this.mRender.getCurrentActivity();
                if (activity == null) {
                  AppBrandLogger.e("tma_ShowPickerViewHandler", new Object[] { "activity is null" });
                  ShowPickerViewHandler.this.invokeHandler("activity is null");
                  return;
                } 
                if (activity.isFinishing()) {
                  AppBrandLogger.e("tma_ShowPickerViewHandler", new Object[] { "activity is finishing" });
                  ShowPickerViewHandler.this.invokeHandler("activity is finishing");
                  return;
                } 
                HostDependManager.getInst().showPickerView(activity, ShowPickerViewHandler.this.mArgs, current, values, new b.c<String>() {
                      public void onCancel() {
                        AppBrandLogger.d("tma_ShowPickerViewHandler", new Object[] { "ShowPickerViewHandler2 onCancel" });
                        AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowPickerViewHandler.this.mRender.getWebViewId(), ShowPickerViewHandler.this.mCallBackId, ShowPickerViewHandler.this.makeMsg(true, -1));
                      }
                      
                      public void onDismiss() {
                        AppBrandLogger.d("tma_ShowPickerViewHandler", new Object[] { "ShowPickerViewHandler2 onDismiss" });
                        AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowPickerViewHandler.this.mRender.getWebViewId(), ShowPickerViewHandler.this.mCallBackId, ShowPickerViewHandler.this.makeMsg(true, -1));
                      }
                      
                      public void onFailure(String param2String) {
                        AppBrandLogger.d("tma_ShowPickerViewHandler", new Object[] { "ShowPickerViewHandler2 onFailure" });
                        AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowPickerViewHandler.this.mRender.getWebViewId(), ShowPickerViewHandler.this.mCallBackId, ShowPickerViewHandler.this.makeMsg(true, -1));
                      }
                      
                      public void onItemPicked(int param2Int, String param2String) {
                        AppBrandLogger.d("tma_ShowPickerViewHandler", new Object[] { "ShowPickerViewHandler2 onItemPicked index ", Integer.valueOf(param2Int), " item ", param2String });
                        AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowPickerViewHandler.this.mRender.getWebViewId(), ShowPickerViewHandler.this.mCallBackId, ShowPickerViewHandler.this.makeMsg(false, param2Int));
                      }
                    });
              }
            });
        return "";
      } 
      return makeFailMsg("empty array");
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_ShowPickerViewHandler", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "showPickerView";
  }
  
  String makeMsg(boolean paramBoolean, int paramInt) {
    try {
      JSONObject jSONObject = new JSONObject();
      if (paramBoolean) {
        jSONObject.put("errMsg", buildErrorMsg("showPickerView", "cancel"));
      } else {
        jSONObject.put("errMsg", buildErrorMsg("showPickerView", "ok"));
        jSONObject.put("index", paramInt);
      } 
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_ShowPickerViewHandler", exception.getStackTrace());
      return CharacterUtils.empty();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ShowPickerViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */