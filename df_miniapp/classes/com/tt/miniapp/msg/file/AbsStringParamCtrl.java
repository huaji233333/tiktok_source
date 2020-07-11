package com.tt.miniapp.msg.file;

import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.a;

public abstract class AbsStringParamCtrl extends AbsFileCtrl<String, ApiCallResult> {
  public AbsStringParamCtrl(String paramString) {
    super(paramString);
  }
  
  protected ApiCallResult exception(Throwable paramThrowable) {
    ApiCallResult.a a = ApiCallResult.a.b(this.mApiName);
    if (paramThrowable instanceof org.json.JSONException) {
      a.d(a.a(this.mArgs));
    } else {
      a.d(a.a(paramThrowable));
    } 
    return a.a();
  }
  
  protected ApiCallResult fail() {
    return ApiCallResult.a.b(this.mApiName).d(trim(this.mExtraInfo)).a();
  }
  
  protected ApiCallResult success() {
    return ApiCallResult.a.a(this.mApiName).d(trim(this.mExtraInfo)).a(this.mExtraData).a();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\file\AbsStringParamCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */