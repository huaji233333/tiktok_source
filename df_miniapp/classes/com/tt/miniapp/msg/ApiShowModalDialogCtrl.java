package com.tt.miniapp.msg;

import android.app.Activity;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiShowModalDialogCtrl extends b {
  public String mCancelColor;
  
  public String mCancelText;
  
  public String mConfirmColor;
  
  public String mConfirmText;
  
  public String mContent;
  
  public boolean mShowCancel;
  
  public String mTitle;
  
  public ApiShowModalDialogCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      this.mTitle = jSONObject.optString("title");
      this.mContent = jSONObject.optString("content");
      this.mConfirmText = jSONObject.optString("confirmText");
      this.mCancelText = jSONObject.optString("cancelText");
      this.mShowCancel = true;
      Object object = jSONObject.opt("showCancel");
      if (object instanceof Integer) {
        boolean bool;
        if (((Integer)object).intValue() == 1) {
          bool = true;
        } else {
          bool = false;
        } 
        this.mShowCancel = bool;
      } else {
        this.mShowCancel = jSONObject.optBoolean("showCancel", true);
      } 
      this.mConfirmColor = jSONObject.optString("confirmColor");
      this.mCancelColor = jSONObject.optString("cancelColor");
      if (TextUtils.isEmpty(this.mContent) && TextUtils.isEmpty(this.mTitle)) {
        AppBrandLogger.e("tma_ApiShowModalDialogCtrl", new Object[] { "empty" });
        callbackFail("title和content不能同时为空");
        return;
      } 
      if (TextUtils.isEmpty(this.mCancelText))
        this.mCancelText = UIUtils.getString(2097741944); 
      if (!this.mShowCancel)
        this.mCancelText = ""; 
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
              if (miniappHostBase != null)
                HostDependManager.getInst().showModal((Activity)miniappHostBase, ApiShowModalDialogCtrl.this.mArgs, ApiShowModalDialogCtrl.this.mTitle, ApiShowModalDialogCtrl.this.mContent, ApiShowModalDialogCtrl.this.mShowCancel, ApiShowModalDialogCtrl.this.mCancelText, ApiShowModalDialogCtrl.this.mCancelColor, ApiShowModalDialogCtrl.this.mConfirmText, ApiShowModalDialogCtrl.this.mConfirmColor, new NativeModule.NativeModuleCallback<Integer>() {
                      public void onNativeModuleCall(Integer param2Integer) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                          int i = param2Integer.intValue();
                          boolean bool2 = false;
                          if (i == 1) {
                            bool1 = true;
                          } else {
                            bool1 = false;
                          } 
                          jSONObject.put("confirm", bool1);
                          boolean bool1 = bool2;
                          if (param2Integer.intValue() != 1)
                            bool1 = true; 
                          jSONObject.put("cancel", bool1);
                          ApiShowModalDialogCtrl.this.callbackOk(jSONObject);
                          return;
                        } catch (JSONException jSONException) {
                          ApiShowModalDialogCtrl.this.callbackFail((Throwable)jSONException);
                          return;
                        } 
                      }
                    }); 
            }
          });
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "showModal";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiShowModalDialogCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */