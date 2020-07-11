package com.tt.miniapp.msg;

import android.content.Intent;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.facialverify.FacialVerifyCallback;
import com.tt.miniapp.facialverify.FacialVerifyError;
import com.tt.miniapp.facialverify.FacialVerifyProcessor;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiFacialRecognitionVerifyCtrl extends b implements FacialVerifyCallback {
  private FacialVerifyProcessor mFacialVerifyProcessor;
  
  public ApiFacialRecognitionVerifyCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.optString("name");
      String str2 = jSONObject.optString("idCardNumber");
      if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
        onResult(2001, "invalid params", (String)null);
        return;
      } 
      this.mFacialVerifyProcessor = new FacialVerifyProcessor();
      this.mFacialVerifyProcessor.startFacialVerify(str1, str2, this);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("ApiFacialRecognitionVerifyCtrl", "", (Throwable)jSONException);
      onResult(4004, "", "");
      return;
    } 
  }
  
  public String getActionName() {
    return "startFacialRecognitionVerify";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt1 == 3333) {
      FacialVerifyProcessor facialVerifyProcessor = this.mFacialVerifyProcessor;
      if (facialVerifyProcessor != null)
        return facialVerifyProcessor.handleFaceLiveResult(paramIntent); 
    } 
    return super.handleActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onResult(int paramInt, String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errCode", paramInt);
      jSONObject.put("verifyResult", paramString2);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("ApiFacialRecognitionVerifyCtrl", "", (Throwable)jSONException);
    } 
    if (paramInt == 0) {
      callbackOk(jSONObject);
      return;
    } 
    if (paramInt == 3200) {
      callbackCancel(FacialVerifyError.wrapErrMsg(paramInt), jSONObject);
      return;
    } 
    callbackFail(FacialVerifyError.wrapErrMsg(paramInt), jSONObject);
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiFacialRecognitionVerifyCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */