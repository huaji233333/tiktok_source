package com.tt.frontendapiinterface;

import android.content.Intent;
import android.text.TextUtils;
import com.tt.miniapp.business.frontendapihandle.base.ApiCallbackHandler;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.extra.ICrossProcessOperator;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.option.e.e;
import java.util.HashMap;
import org.json.JSONObject;

@Deprecated
public abstract class b extends ApiCallbackHandler implements ICrossProcessOperator {
  @Deprecated
  public static final String API_CALLBACK_ERRMSG = "errMsg";
  
  @Deprecated
  public static final String API_CALLBACK_ERRSTACK = "errStack";
  
  @Deprecated
  public static final String API_CALLBACK_EXCEPTION = "exception";
  
  public e mApiHandlerCallback;
  
  public String mArgs;
  
  public int mCallBackId;
  
  public b(String paramString, int paramInt, e parame) {
    this.mArgs = paramString;
    this.mCallBackId = paramInt;
    this.mApiHandlerCallback = parame;
  }
  
  private void apiHandlerCallback(String paramString) {
    this.mApiHandlerCallback.callback(this.mCallBackId, paramString);
    onApiHandlerCallback();
  }
  
  @Deprecated
  public static String buildErrorMsg(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(":");
    stringBuilder.append(paramString2);
    return stringBuilder.toString();
  }
  
  protected abstract void act();
  
  public void callbackApiHandleResult(ApiCallResult paramApiCallResult) {
    apiHandlerCallback(paramApiCallResult.toString());
  }
  
  public final void callbackAppUnSupportFeature() {
    callbackFail("feature is not supported in app");
  }
  
  @Deprecated
  public void callbackDefaultMsg(boolean paramBoolean) {
    callbackMsg(paramBoolean, (HashMap<String, Object>)null, (String)null);
  }
  
  @Deprecated
  public void callbackException(Throwable paramThrowable) {
    doCallbackByApiHandler(makeMsgByResult(false, (HashMap<String, Object>)null, "exception", paramThrowable).toString());
  }
  
  @Deprecated
  public void callbackExtraInfoMsg(boolean paramBoolean, String paramString) {
    callbackMsg(paramBoolean, (HashMap<String, Object>)null, paramString);
  }
  
  public final void callbackIllegalParam(String paramString) {
    callbackFail(a.b(paramString));
  }
  
  @Deprecated
  public void callbackMsg(boolean paramBoolean, HashMap<String, Object> paramHashMap, String paramString) {
    doCallbackByApiHandler(makeMsgByResult(paramBoolean, paramHashMap, paramString).toString());
  }
  
  public final void callbackOnOriginProcess(String paramString) {
    if (paramString == null) {
      callbackFail("get empty remote process result");
      return;
    } 
    apiHandlerCallback(paramString);
  }
  
  @Deprecated
  public void callbackOtherExtraMsg(boolean paramBoolean, HashMap<String, Object> paramHashMap) {
    callbackMsg(paramBoolean, paramHashMap, (String)null);
  }
  
  public final void doAct() {
    if (shouldHandleActivityResult())
      (c.a()).a.add(this); 
    try {
      return;
    } finally {
      Exception exception = null;
      DebugUtil.logOrThrow("ApiHandler", new Object[] { "ApiHandler act", exception });
      callbackFail(exception);
    } 
  }
  
  @Deprecated
  public void doCallbackByApiHandler(String paramString) {
    apiHandlerCallback(paramString);
  }
  
  public abstract String getActionName();
  
  public final String getApiName() {
    return getActionName();
  }
  
  protected String[] getNeededPermissions() {
    return null;
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return false;
  }
  
  public final boolean isSwitchProcessOperateAsync() {
    return true;
  }
  
  @Deprecated
  public JSONObject makeMsgByResult(boolean paramBoolean, HashMap<String, Object> paramHashMap, String paramString) {
    return makeMsgByResult(paramBoolean, paramHashMap, paramString, (Throwable)null);
  }
  
  @Deprecated
  public JSONObject makeMsgByResult(boolean paramBoolean, HashMap<String, Object> paramHashMap, String paramString, Throwable paramThrowable) {
    if (paramBoolean) {
      ApiCallResult.a a1 = ApiCallResult.a.a(getActionName());
      if (!TextUtils.isEmpty(paramString)) {
        a1.d(paramString);
      } else if (paramThrowable != null) {
        a1.a(paramThrowable);
      } 
      a1.a(paramHashMap);
      return (a1.a()).a;
    } 
    ApiCallResult.a a = ApiCallResult.a.b(getActionName());
    if (!TextUtils.isEmpty(paramString)) {
      a.d(paramString);
    } else if (paramThrowable != null) {
      a.a(paramThrowable);
    } 
    a.a(paramHashMap);
    return (a.a()).a;
  }
  
  public final boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return handleActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected void onApiHandlerCallback() {}
  
  public final void operateFinishOnGoalProcess(String paramString, AsyncIpcHandler paramAsyncIpcHandler) {
    paramAsyncIpcHandler.callback(CrossProcessDataEntity.Builder.create().put("apiExecuteResult", paramString).build(), true);
  }
  
  public String operateProcessIdentify() {
    return "miniAppProcess";
  }
  
  @Deprecated
  protected String paramIllegalMsg(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("param:");
    stringBuilder.append(paramString);
    stringBuilder.append(" illegal");
    return stringBuilder.toString();
  }
  
  public boolean shouldHandleActivityResult() {
    return false;
  }
  
  public void unRegesterResultHandler() {
    (c.a()).a.remove(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\frontendapiinterface\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */