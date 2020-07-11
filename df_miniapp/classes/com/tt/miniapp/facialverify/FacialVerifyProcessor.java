package com.tt.miniapp.facialverify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import com.storage.async.Action;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.user.TmaUserManager;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.util.SaftyUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.f.b;
import com.tt.option.q.i;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONObject;

public class FacialVerifyProcessor implements OnGetFacialVerifyTicketListener {
  public static int TYPE_AILAB = 1;
  
  public static int TYPE_ALPAY = 2;
  
  public static int TYPE_NONE;
  
  private AiLabParams mAiLabParams;
  
  private String mIdCard;
  
  public Pair<String, String> mKeyPair;
  
  private String mName;
  
  private String mTTCode;
  
  public FacialVerifyCallback mVerifyCallback;
  
  private void doVerify() {
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (!(UserInfoManager.getHostClientUserInfo()).isLogin) {
      TmaUserManager.getInstance().login(new TmaUserManager.HostClientLoginListener() {
            public void onLoginFail() {
              AppBrandLogger.i("FacialVerifyProcessor", new Object[] { "onLoginFail" });
              if (FacialVerifyProcessor.this.mVerifyCallback != null)
                FacialVerifyProcessor.this.mVerifyCallback.onResult(2203, "not login", ""); 
            }
            
            public void onLoginSuccess() {
              AppBrandLogger.d("FacialVerifyProcessor", new Object[] { "FacialVerifyProcessor:onLoginSuccess" });
              FacialVerifyProcessor.this.requestUserAuth(activity);
            }
            
            public void onLoginUnSupport() {
              AppBrandLogger.i("FacialVerifyProcessor", new Object[] { "onLoginUnSupport" });
              if (FacialVerifyProcessor.this.mVerifyCallback != null)
                FacialVerifyProcessor.this.mVerifyCallback.onResult(2203, "not login", ""); 
            }
            
            public void onLoginWhenBackground() {
              AppBrandLogger.i("FacialVerifyProcessor", new Object[] { "onLoginWhenBackground" });
              if (FacialVerifyProcessor.this.mVerifyCallback != null)
                FacialVerifyProcessor.this.mVerifyCallback.onResult(2203, "not login", ""); 
            }
            
            public void onTriggerHostClientLogin() {
              AppBrandLogger.d("FacialVerifyProcessor", new Object[] { "onTriggerHostClientLogin" });
            }
          });
      return;
    } 
    requestUserAuth((Activity)miniappHostBase);
  }
  
  private void getFacialVerifyTicket(final OnGetFacialVerifyTicketListener listener) {
    if (!NetUtil.isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext())) {
      if (listener != null)
        listener.onGetTicketFail(2100, "network not available"); 
      return;
    } 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            OnGetFacialVerifyTicketListener onGetFacialVerifyTicketListener2;
            OnGetFacialVerifyTicketListener onGetFacialVerifyTicketListener1;
            i i1 = FacialVerifyProcessor.this.getCallParamsRequest();
            String str2 = HostDependManager.getInst().doPostBody(i1).a();
            if (TextUtils.isEmpty(str2)) {
              onGetFacialVerifyTicketListener2 = listener;
              if (onGetFacialVerifyTicketListener2 != null)
                onGetFacialVerifyTicketListener2.onGetTicketFail(1200, "response return null"); 
              return;
            } 
            JSONObject jSONObject = (new JsonBuilder((String)onGetFacialVerifyTicketListener2)).build();
            int i = jSONObject.optInt("err_no");
            String str1 = jSONObject.optString("err_msg");
            if (i == 0) {
              JSONObject jSONObject1 = jSONObject.optJSONObject("data");
              i = jSONObject1.optInt("type");
              jSONObject1 = jSONObject1.optJSONObject("param");
              if (i == FacialVerifyProcessor.TYPE_AILAB && jSONObject1 != null) {
                AiLabParams aiLabParams = new AiLabParams(jSONObject1);
                OnGetFacialVerifyTicketListener onGetFacialVerifyTicketListener = listener;
                if (onGetFacialVerifyTicketListener != null) {
                  onGetFacialVerifyTicketListener.onGetTicketSuccess(aiLabParams);
                  return;
                } 
              } else {
                onGetFacialVerifyTicketListener1 = listener;
                if (onGetFacialVerifyTicketListener1 != null)
                  onGetFacialVerifyTicketListener1.onGetTicketFail(1200, "type invalid or param return null"); 
                return;
              } 
            } else {
              OnGetFacialVerifyTicketListener onGetFacialVerifyTicketListener = listener;
              if (onGetFacialVerifyTicketListener != null)
                onGetFacialVerifyTicketListener.onGetTicketFail(FacialVerifyError.wrapServerErrorCode(i), (String)onGetFacialVerifyTicketListener1); 
            } 
          }
        }ThreadPools.longIO());
  }
  
  private void wrapResult(final int errorCode, final String reqOrderNo, final OnWrapFacialVerifyResultListener listener) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            FacialVerifyProcessor.OnWrapFacialVerifyResultListener onWrapFacialVerifyResultListener2;
            i i = FacialVerifyProcessor.this.getWrapResultRequest(errorCode, reqOrderNo);
            String str = HostDependManager.getInst().doPostBody(i).a();
            if (TextUtils.isEmpty(str)) {
              onWrapFacialVerifyResultListener2 = listener;
              if (onWrapFacialVerifyResultListener2 != null)
                onWrapFacialVerifyResultListener2.onFail(1200, "response data return null"); 
              return;
            } 
            JSONObject jSONObject = (new JsonBuilder((String)onWrapFacialVerifyResultListener2)).build();
            if (jSONObject.optInt("err_no") == 0) {
              String str2 = jSONObject.optString("order_number");
              String str1 = SaftyUtil.AESDecrypt((String)FacialVerifyProcessor.this.mKeyPair.first, (String)FacialVerifyProcessor.this.mKeyPair.second, str2);
              StringBuilder stringBuilder = new StringBuilder("get wrap order number = ");
              stringBuilder.append(str2);
              AppBrandLogger.d("FacialVerifyProcessor", new Object[] { stringBuilder.toString() });
              FacialVerifyProcessor.OnWrapFacialVerifyResultListener onWrapFacialVerifyResultListener = listener;
              if (onWrapFacialVerifyResultListener != null)
                onWrapFacialVerifyResultListener.onWrapSuccess(str1); 
              return;
            } 
            FacialVerifyProcessor.OnWrapFacialVerifyResultListener onWrapFacialVerifyResultListener1 = listener;
            if (onWrapFacialVerifyResultListener1 != null)
              onWrapFacialVerifyResultListener1.onFail(FacialVerifyError.wrapAiLabErrorCode(errorCode), ""); 
          }
        }ThreadPools.longIO());
  }
  
  public i getCallParamsRequest() {
    String str1;
    i i = new i(AppbrandConstant.OpenApi.getInst().getFacialVerifyTicketUrl(), "POST");
    String str2 = SaftyUtil.genRandomString();
    String str3 = SaftyUtil.genRandomString();
    this.mKeyPair = new Pair(str2, str3);
    this.mTTCode = SaftyUtil.generateTTCode((Context)AppbrandContext.getInst().getApplicationContext(), str2, str3);
    String str4 = SaftyUtil.AESEncrypt(str2, str3, this.mName);
    String str5 = SaftyUtil.AESEncrypt(str2, str3, this.mIdCard);
    String str6 = SaftyUtil.AESEncrypt(str2, str3, (AppbrandApplication.getInst().getAppInfo()).appId);
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    str3 = "";
    if (initParamsEntity != null) {
      str3 = initParamsEntity.getAppId();
      str1 = initParamsEntity.getUpdateVersionCode();
    } else {
      str1 = "";
    } 
    i.a("identify_name", str4);
    i.a("identify_id", str5);
    i.a("app_id", str6);
    i.a("aid", str3);
    i.a("host_version", str1);
    i.a("ttcode", this.mTTCode);
    return i;
  }
  
  public i getWrapResultRequest(int paramInt, String paramString) {
    String str1;
    String str2;
    if (TextUtils.isEmpty(this.mTTCode)) {
      str1 = SaftyUtil.genRandomString();
      str2 = SaftyUtil.genRandomString();
      this.mKeyPair = new Pair(str1, str2);
      this.mTTCode = SaftyUtil.generateTTCode((Context)AppbrandContext.getInst().getApplicationContext(), str1, str2);
    } else {
      str1 = (String)this.mKeyPair.first;
      str2 = (String)this.mKeyPair.second;
    } 
    i i = new i(AppbrandConstant.OpenApi.getInst().getWrapFacialVerifyResultUrl(), "POST");
    i.a("type", Integer.valueOf(TYPE_AILAB));
    i.a("app_id", SaftyUtil.AESEncrypt(str1, str2, (AppbrandApplication.getInst().getAppInfo()).appId));
    i.a("error_code", SaftyUtil.AESEncrypt(str1, str2, String.valueOf(paramInt)));
    if (!TextUtils.isEmpty(paramString))
      i.a("req_order_number", SaftyUtil.AESEncrypt(str1, str2, paramString)); 
    i.a("ttcode", this.mTTCode);
    if ((paramInt > 3000 && paramInt < 4000) || paramInt == 4998 || paramInt == 5999 || paramInt == 9999) {
      i.a("cancel", SaftyUtil.AESEncrypt(str1, str2, "1"));
      return i;
    } 
    i.a("cancel", SaftyUtil.AESEncrypt(str1, str2, "0"));
    return i;
  }
  
  public boolean handleFaceLiveResult(Intent paramIntent) {
    FacialVerifyCallback facialVerifyCallback2;
    FacialVerifyCallback facialVerifyCallback1;
    if (paramIntent == null) {
      facialVerifyCallback2 = this.mVerifyCallback;
      if (facialVerifyCallback2 != null)
        facialVerifyCallback2.onResult(5001, "intent return null", null); 
      return true;
    } 
    final int err_code = facialVerifyCallback2.getIntExtra("err_code", -1);
    String str2 = facialVerifyCallback2.getStringExtra("err_msg");
    String str1 = facialVerifyCallback2.getStringExtra("req_order_no");
    StringBuilder stringBuilder = new StringBuilder("onFaceLiveResult: err_code = ");
    stringBuilder.append(i);
    stringBuilder.append("errMsg = ");
    stringBuilder.append(str2);
    stringBuilder.append(" requestOrderNo = ");
    stringBuilder.append(str1);
    AppBrandLogger.i("FacialVerifyProcessor", new Object[] { stringBuilder.toString() });
    if (i != 0 && !NetUtil.isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext())) {
      facialVerifyCallback1 = this.mVerifyCallback;
      if (facialVerifyCallback1 != null)
        facialVerifyCallback1.onResult(2100, "", ""); 
      return true;
    } 
    wrapResult(i, (String)facialVerifyCallback1, new OnWrapFacialVerifyResultListener() {
          public void onFail(int param1Int, String param1String) {
            if (FacialVerifyProcessor.this.mVerifyCallback != null)
              FacialVerifyProcessor.this.mVerifyCallback.onResult(FacialVerifyError.wrapAiLabErrorCode(err_code), param1String, ""); 
          }
          
          public void onWrapSuccess(String param1String) {
            StringBuilder stringBuilder = new StringBuilder("wrap reqOrderNumber success = ");
            stringBuilder.append(param1String);
            AppBrandLogger.d("FacialVerifyProcessor", new Object[] { stringBuilder.toString() });
            if (FacialVerifyProcessor.this.mVerifyCallback != null)
              FacialVerifyProcessor.this.mVerifyCallback.onResult(FacialVerifyError.wrapAiLabErrorCode(err_code), "", param1String); 
          }
        });
    return true;
  }
  
  public void onGetTicketFail(int paramInt, String paramString) {
    StringBuilder stringBuilder = new StringBuilder("error_code = ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" errMsg = ");
    stringBuilder.append(paramString);
    AppBrandLogger.i("FacialVerifyProcessor", new Object[] { stringBuilder.toString() });
    FacialVerifyCallback facialVerifyCallback = this.mVerifyCallback;
    if (facialVerifyCallback != null)
      facialVerifyCallback.onResult(paramInt, "", ""); 
  }
  
  public void onGetTicketSuccess(AiLabParams paramAiLabParams) {
    StringBuilder stringBuilder = new StringBuilder("getTicket success ");
    stringBuilder.append(paramAiLabParams.toString());
    AppBrandLogger.d("FacialVerifyProcessor", new Object[] { stringBuilder.toString() });
    this.mAiLabParams = paramAiLabParams;
    doVerify();
  }
  
  public void requestUserAuth(final Activity activity) {
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.FACIAL_VERIFY);
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("name", this.mName);
    BrandPermissionUtils.requestPermissions(activity, "FacialVerify", hashSet, linkedHashMap, new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            AppBrandLogger.i("FacialVerifyProcessor", new Object[] { "permission denied" });
            if (FacialVerifyProcessor.this.mVerifyCallback != null)
              FacialVerifyProcessor.this.mVerifyCallback.onResult(2200, "user denied", ""); 
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            AppBrandLogger.d("FacialVerifyProcessor", new Object[] { "auth pass,start do facial verify" });
            FacialVerifyProcessor.this.startFaceLive(activity);
          }
        }hashMap);
  }
  
  public void startFaceLive(Activity paramActivity) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("uid", this.mAiLabParams.uid);
    hashMap.put("merchant_id", this.mAiLabParams.merchant_id);
    hashMap.put("merchant_app_id", this.mAiLabParams.merchant_app_id);
    hashMap.put("busi_type", this.mAiLabParams.busi_type);
    hashMap.put("source", this.mAiLabParams.source);
    hashMap.put("identity_name", this.mName);
    hashMap.put("identity_code", this.mIdCard);
    hashMap.put("lang", "zh");
    HostDependManager.getInst().startFaceLive(paramActivity, hashMap, new b() {
          public void onResult(int param1Int, String param1String) {
            if (FacialVerifyProcessor.this.mVerifyCallback != null)
              FacialVerifyProcessor.this.mVerifyCallback.onResult(FacialVerifyError.wrapAiLabErrorCode(param1Int), "", ""); 
          }
        });
  }
  
  public void startFacialVerify(String paramString1, String paramString2, FacialVerifyCallback paramFacialVerifyCallback) {
    this.mName = paramString1;
    this.mIdCard = paramString2;
    this.mVerifyCallback = paramFacialVerifyCallback;
    getFacialVerifyTicket(this);
  }
  
  public static interface OnWrapFacialVerifyResultListener {
    void onFail(int param1Int, String param1String);
    
    void onWrapSuccess(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\facialverify\FacialVerifyProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */