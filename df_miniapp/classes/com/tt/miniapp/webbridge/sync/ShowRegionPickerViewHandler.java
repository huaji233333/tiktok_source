package com.tt.miniapp.webbridge.sync;

import android.app.Activity;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.y.b;
import org.json.JSONArray;
import org.json.JSONObject;

public class ShowRegionPickerViewHandler extends BasePickerEventHandler {
  public ShowRegionPickerViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      final String[] currentArray;
      JSONObject jSONObject = new JSONObject(this.mArgs);
      JSONArray jSONArray = jSONObject.optJSONArray("current");
      final String headString = jSONObject.optString("customItem", null);
      if (jSONArray == null) {
        jSONObject = null;
      } else {
        String[] arrayOfString1 = new String[jSONArray.length()];
        int i = 0;
        while (true) {
          arrayOfString = arrayOfString1;
          if (i < jSONArray.length()) {
            arrayOfString1[i] = jSONArray.getString(i);
            i++;
            continue;
          } 
          break;
        } 
      } 
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              ShowRegionPickerViewHandler showRegionPickerViewHandler;
              if (ShowRegionPickerViewHandler.this.mRender == null) {
                AppBrandLogger.e("tma_ShowRegionPickerViewHandler", new Object[] { "current render is null" });
                showRegionPickerViewHandler = ShowRegionPickerViewHandler.this;
                showRegionPickerViewHandler.invokeHandler(showRegionPickerViewHandler.makeFailMsg("current render is null"));
                return;
              } 
              Activity activity = ShowRegionPickerViewHandler.this.mRender.getCurrentActivity();
              if (activity == null) {
                AppBrandLogger.e("tma_ShowRegionPickerViewHandler", new Object[] { "activity is null" });
                showRegionPickerViewHandler = ShowRegionPickerViewHandler.this;
                showRegionPickerViewHandler.invokeHandler(showRegionPickerViewHandler.makeFailMsg("activity is null"));
                return;
              } 
              ShowRegionPickerViewHandler.this.showRegionPickerViewFinal((Activity)showRegionPickerViewHandler, currentArray, headString);
            }
          });
      return null;
    } catch (Exception exception) {
      invokeHandler(makeFailMsg(exception));
      AppBrandLogger.stacktrace(6, "tma_ShowRegionPickerViewHandler", exception.getStackTrace());
      return null;
    } 
  }
  
  public String getApiName() {
    return "showRegionPickerView";
  }
  
  public void showRegionPickerViewFinal(Activity paramActivity, String[] paramArrayOfString, String paramString) {
    HostDependManager.getInst().showRegionPickerView(paramActivity, paramString, paramArrayOfString, new b.e() {
          public void onCancel() {
            AppBrandLogger.d("tma_ShowRegionPickerViewHandler", new Object[] { "onWheeled onCancel" });
            ShowRegionPickerViewHandler.this.makeCancelMsg("showRegionPickerView");
          }
          
          public void onConfirm(String[] param1ArrayOfString1, String[] param1ArrayOfString2) {
            boolean bool = false;
            AppBrandLogger.d("tma_ShowRegionPickerViewHandler", new Object[] { "onWheeled onConfirm" });
            try {
              JSONObject jSONObject = new JSONObject();
              jSONObject.put("errMsg", ShowRegionPickerViewHandler.this.buildErrorMsg("showRegionPickerView", "ok"));
              JSONArray jSONArray2 = new JSONArray();
              int j = param1ArrayOfString1.length;
              int i;
              for (i = 0; i < j; i++)
                jSONArray2.put(param1ArrayOfString1[i]); 
              JSONArray jSONArray1 = new JSONArray();
              j = param1ArrayOfString2.length;
              for (i = bool; i < j; i++)
                jSONArray1.put(param1ArrayOfString2[i]); 
              jSONObject.put("value", jSONArray2);
              jSONObject.put("code", jSONArray1);
              AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(ShowRegionPickerViewHandler.this.mRender.getWebViewId(), ShowRegionPickerViewHandler.this.mCallBackId, jSONObject.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "tma_ShowRegionPickerViewHandler", exception.getStackTrace());
              ShowRegionPickerViewHandler showRegionPickerViewHandler = ShowRegionPickerViewHandler.this;
              showRegionPickerViewHandler.invokeHandler(showRegionPickerViewHandler.makeFailMsg(exception));
              return;
            } 
          }
          
          public void onDismiss() {
            AppBrandLogger.d("tma_ShowRegionPickerViewHandler", new Object[] { "onWheeled onDismiss" });
            ShowRegionPickerViewHandler.this.makeCancelMsg("showRegionPickerView");
          }
          
          public void onFailure(String param1String) {
            AppBrandLogger.d("tma_ShowRegionPickerViewHandler", new Object[] { "onWheeled onFailure", param1String });
            ShowRegionPickerViewHandler.this.makeCancelMsg("showRegionPickerView");
          }
          
          public void onWheeled(int param1Int1, int param1Int2, Object param1Object) {
            AppBrandLogger.d("tma_ShowRegionPickerViewHandler", new Object[] { "onWheeled column", Integer.valueOf(param1Int1), " index ", Integer.valueOf(param1Int2), " item ", param1Object });
            try {
              param1Object = new JSONObject();
              param1Object.put("column", param1Int1);
              param1Object.put("current", param1Int2);
              AppbrandApplicationImpl.getInst().getWebViewManager().publish(ShowRegionPickerViewHandler.this.mRender.getWebViewId(), "onRegionPickerViewChange", param1Object.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.stacktrace(6, "tma_ShowRegionPickerViewHandler", exception.getStackTrace());
              return;
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ShowRegionPickerViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */