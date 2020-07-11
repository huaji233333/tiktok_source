package com.tt.miniapp.business.frontendapihandle.handler.subscribe;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.storage.async.Action;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.data.TemplateMsgInfo;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.data.TemplateMsgLimitInfo;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.util.SubscribeMsgShowRecordUtil;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.util.TemplateMsgCacheUtil;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.user.TmaUserManager;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubScribeMessageProcessor {
  public TemplateMsgLimitInfo mTotalLimit;
  
  public SubscribeMessageCallback subscribeMessageCallback;
  
  public ArrayMap<String, TemplateMsgInfo> templateMsgInfoArrayMap = new ArrayMap();
  
  public ArrayMap<String, String> templateResultMap = new ArrayMap();
  
  private JSONArray filterInvalidTemplates(JSONArray paramJSONArray) {
    JSONArray jSONArray = new JSONArray();
    for (int i = 0; i < paramJSONArray.length(); i++) {
      String str = paramJSONArray.optString(i);
      TemplateMsgInfo templateMsgInfo = (TemplateMsgInfo)this.templateMsgInfoArrayMap.get(str);
      if (templateMsgInfo != null && templateMsgInfo.isValid()) {
        jSONArray.put(str);
      } else {
        this.templateResultMap.put(str, "ban");
      } 
    } 
    if (jSONArray.length() == 0) {
      SubscribeMessageCallback subscribeMessageCallback = this.subscribeMessageCallback;
      if (subscribeMessageCallback != null)
        subscribeMessageCallback.onResult(1004, "template not exist or invalid", null); 
    } 
    return jSONArray;
  }
  
  private JSONArray filterShowCountLimitTemplates(JSONArray paramJSONArray) {
    JSONArray jSONArray = new JSONArray();
    for (int i = 0; i < paramJSONArray.length(); i++) {
      String str = paramJSONArray.optString(i);
      TemplateMsgInfo templateMsgInfo = (TemplateMsgInfo)this.templateMsgInfoArrayMap.get(str);
      if (templateMsgInfo != null && !SubscribeMsgShowRecordUtil.hasReachLimitCount(str, this.mTotalLimit, templateMsgInfo.getLimitInfo())) {
        jSONArray.put(str);
      } else {
        StringBuilder stringBuilder = new StringBuilder("subscribe template message: ");
        stringBuilder.append(str);
        stringBuilder.append(", has reach max count");
        AppBrandLogger.i("SubscribeMessageProcessor", new Object[] { stringBuilder.toString() });
        this.templateResultMap.put(str, "reject");
      } 
    } 
    if (jSONArray.length() == 0) {
      SubscribeMessageCallback subscribeMessageCallback = this.subscribeMessageCallback;
      if (subscribeMessageCallback != null)
        subscribeMessageCallback.onResult(1005, "request count out of limit ", null); 
    } 
    return jSONArray;
  }
  
  private void initTemplateInfo(final JSONArray templateIds) {
    int i = 0;
    boolean bool = false;
    while (i < templateIds.length()) {
      String str = templateIds.optString(i);
      TemplateMsgLimitInfo templateMsgLimitInfo = TemplateMsgCacheUtil.getSavedTotalLimit();
      TemplateMsgInfo templateMsgInfo = TemplateMsgCacheUtil.getSavedTemplateMsgInfo(str);
      if (templateMsgLimitInfo != null && templateMsgInfo != null) {
        this.mTotalLimit = templateMsgLimitInfo;
        this.templateMsgInfoArrayMap.put(str, templateMsgInfo);
        StringBuilder stringBuilder2 = new StringBuilder("cached totalLimit = ");
        stringBuilder2.append(templateMsgLimitInfo.toString());
        AppBrandLogger.d("SubscribeMessageProcessor", new Object[] { stringBuilder2.toString() });
        StringBuilder stringBuilder1 = new StringBuilder("cached templateMsgInfo = ");
        stringBuilder1.append(templateMsgInfo.toString());
        AppBrandLogger.d("SubscribeMessageProcessor", new Object[] { stringBuilder1.toString() });
        if (templateMsgInfo.needUpdateOnline())
          bool = true; 
        i++;
        continue;
      } 
      queryTemplateInfoOnline(templateIds, new OnQueryTemplateInfoListener() {
            public void onFail(int param1Int, String param1String) {
              if (param1Int == -1000) {
                if (SubScribeMessageProcessor.this.subscribeMessageCallback != null) {
                  SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(2001, "network error", null);
                  return;
                } 
              } else if (SubScribeMessageProcessor.this.subscribeMessageCallback != null) {
                SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(2002, "service error", null);
              } 
            }
            
            public void onSuccess(TemplateMsgLimitInfo param1TemplateMsgLimitInfo, ArrayMap<String, TemplateMsgInfo> param1ArrayMap) {
              SubScribeMessageProcessor subScribeMessageProcessor = SubScribeMessageProcessor.this;
              subScribeMessageProcessor.mTotalLimit = param1TemplateMsgLimitInfo;
              subScribeMessageProcessor.templateMsgInfoArrayMap = param1ArrayMap;
            }
          });
      return;
    } 
    if (bool)
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              AppBrandLogger.d("SubscribeMessageProcessor", new Object[] { "async update templateInfo" });
              SubScribeMessageProcessor.this.queryTemplateInfoOnline(templateIds, null);
            }
          }ThreadPools.defaults()); 
  }
  
  public void queryTemplateInfoOnline(JSONArray paramJSONArray, OnQueryTemplateInfoListener paramOnQueryTemplateInfoListener) {
    if (!NetUtil.isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext())) {
      if (paramOnQueryTemplateInfoListener != null)
        paramOnQueryTemplateInfoListener.onFail(-1000, "network error"); 
      return;
    } 
    i i = new i(AppbrandConstant.OpenApi.getInst().getTemplateMsgInfoUrl(), "POST");
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    i.a("app_id", appInfoEntity.appId);
    i.a("aid", initParamsEntity.getAppId());
    i.a("tpl_ids", paramJSONArray);
    j j = HostDependManager.getInst().doPostBody(i);
    if (j != null && !TextUtils.isEmpty(j.a())) {
      StringBuilder stringBuilder = new StringBuilder("query templateInfo result = ");
      stringBuilder.append(j.a());
      String str2 = stringBuilder.toString();
      int k = 0;
      AppBrandLogger.d("SubscribeMessageProcessor", new Object[] { str2 });
      JSONObject jSONObject = (new JsonBuilder(j.a())).build();
      int m = jSONObject.optInt("err_no");
      if (m == 0) {
        final TemplateMsgLimitInfo totalLimitInfo = new TemplateMsgLimitInfo(jSONObject.optJSONObject("total_limit"));
        jSONObject = jSONObject.optJSONObject("tpl_data");
        ArrayMap<String, TemplateMsgInfo> arrayMap = new ArrayMap();
        final LinkedList<TemplateMsgInfo> templateMsgInfoList = new LinkedList();
        if (jSONObject != null)
          while (k < paramJSONArray.length()) {
            String str = paramJSONArray.optString(k);
            JSONObject jSONObject1 = jSONObject.optJSONObject(str);
            if (jSONObject1 != null) {
              TemplateMsgInfo templateMsgInfo = new TemplateMsgInfo(str, jSONObject1);
              arrayMap.put(str, templateMsgInfo);
              linkedList.add(templateMsgInfo);
            } 
            k++;
          }  
        if (paramOnQueryTemplateInfoListener != null)
          paramOnQueryTemplateInfoListener.onSuccess(templateMsgLimitInfo, arrayMap); 
        ThreadUtil.runOnWorkThread(new Action() {
              public void act() {
                TemplateMsgCacheUtil.saveTotalLimit(totalLimitInfo);
                Iterator<TemplateMsgInfo> iterator = templateMsgInfoList.iterator();
                while (iterator.hasNext())
                  TemplateMsgCacheUtil.saveTemplateMsgInfo(iterator.next()); 
                templateMsgInfoList.clear();
              }
            },  ThreadPools.defaults());
        return;
      } 
      String str1 = jSONObject.optString("err_tips");
      if (paramOnQueryTemplateInfoListener != null)
        paramOnQueryTemplateInfoListener.onFail(m, str1); 
      return;
    } 
    if (paramOnQueryTemplateInfoListener != null)
      paramOnQueryTemplateInfoListener.onFail(-1002, "request fail"); 
  }
  
  public void reportSubscribeMsg(final JSONArray templateIds) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            if (!NetUtil.isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext())) {
              if (SubScribeMessageProcessor.this.subscribeMessageCallback != null)
                SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(2001, "network error", null); 
              return;
            } 
            i i = new i(AppbrandConstant.OpenApi.getInst().reportSubscribeMsgUrl(), "POST");
            i.a("tpl_ids", templateIds);
            i.a("appId", (AppbrandApplication.getInst().getAppInfo()).appId);
            i.a("aid", Integer.valueOf(AppbrandContext.getInst().getInitParams().getAppId()));
            JSONObject jSONObject = (new JsonBuilder("{}")).build();
            try {
              jSONObject.put("mp_name", (AppbrandApplication.getInst().getAppInfo()).appName);
              jSONObject.put("mp_version", (AppbrandApplication.getInst().getAppInfo()).versionCode);
            } catch (JSONException jSONException) {
              AppBrandLogger.eWithThrowable("SubscribeMessageProcessor", "", (Throwable)jSONException);
            } 
            i.a("extra", jSONObject.toString());
            UserInfoManager.UserInfo userInfo = UserInfoManager.getHostClientUserInfo();
            if (userInfo != null && !TextUtils.isEmpty(userInfo.sessionId))
              i.a("X-Tma-Host-Sessionid", userInfo.sessionId); 
            j j = HostDependManager.getInst().doPostBody(i);
            if (j != null && !TextUtils.isEmpty(j.a())) {
              JSONObject jSONObject1 = (new JsonBuilder(j.a())).build();
              int k = jSONObject1.optInt("err_no");
              if (k == 0) {
                int m = templateIds.length();
                jSONObject1 = jSONObject1.optJSONObject("result");
                for (k = 0; k < m; k++) {
                  String str = templateIds.optString(k);
                  int n = jSONObject1.optInt(str);
                  if (n != 1) {
                    if (n == 2)
                      SubScribeMessageProcessor.this.templateResultMap.put(str, "reject"); 
                  } else {
                    SubScribeMessageProcessor.this.templateResultMap.put(str, "accept");
                  } 
                } 
                if (SubScribeMessageProcessor.this.subscribeMessageCallback != null) {
                  SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(0, "", SubScribeMessageProcessor.this.templateResultMap);
                  return;
                } 
              } else {
                String str = jSONObject1.optString("err_tips");
                StringBuilder stringBuilder = new StringBuilder("report subscribe fail, err_no = ");
                stringBuilder.append(k);
                stringBuilder.append(" err_tip = ");
                stringBuilder.append(str);
                AppBrandLogger.i("SubscribeMessageProcessor", new Object[] { stringBuilder.toString() });
                if (SubScribeMessageProcessor.this.subscribeMessageCallback != null)
                  SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(2002, "service error", null); 
                return;
              } 
            } else if (SubScribeMessageProcessor.this.subscribeMessageCallback != null) {
              SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(2002, "service error", null);
            } 
          }
        }ThreadPools.longIO());
  }
  
  public void requestSubScribeMessage(final JSONArray finalFilteredTemplates, SubscribeMessageCallback paramSubscribeMessageCallback) {
    this.subscribeMessageCallback = paramSubscribeMessageCallback;
    initTemplateInfo(finalFilteredTemplates);
    JSONArray jSONArray = filterInvalidTemplates(finalFilteredTemplates);
    finalFilteredTemplates = jSONArray;
    if (jSONArray.length() > 0)
      finalFilteredTemplates = filterShowCountLimitTemplates(jSONArray); 
    if (finalFilteredTemplates.length() == 0)
      return; 
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    LinkedHashSet<BrandPermissionUtils.BrandPermission> linkedHashSet = new LinkedHashSet();
    linkedHashSet.add(BrandPermissionUtils.BrandPermission.SUBSCRIBE_MESSAGE);
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    final String templateId = finalFilteredTemplates.optString(0);
    TemplateMsgInfo templateMsgInfo = (TemplateMsgInfo)this.templateMsgInfoArrayMap.get(str);
    if (templateMsgInfo != null) {
      hashMap.put("templateId", str);
      hashMap.put("title", templateMsgInfo.getTitle());
      hashMap.put("subtitle", templateMsgInfo.getContent());
    } 
    BrandPermissionUtils.requestPermissions((Activity)miniappHostBase, "requestSubscribeMessage", linkedHashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            AppBrandLogger.i("SubscribeMessageProcessor", new Object[] { "auth denied" });
            if (SubScribeMessageProcessor.this.subscribeMessageCallback != null) {
              SubScribeMessageProcessor.this.templateResultMap.put(templateId, "reject");
              SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(3001, "auth denied", SubScribeMessageProcessor.this.templateResultMap);
            } 
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!(UserInfoManager.getHostClientUserInfo()).isLogin) {
              TmaUserManager.getInstance().login(new TmaUserManager.HostClientLoginListener() {
                    public void onLoginFail() {
                      AppBrandLogger.i("SubscribeMessageProcessor", new Object[] { "onLoginFail" });
                      if (SubScribeMessageProcessor.this.subscribeMessageCallback != null)
                        SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(3002, "login fail", null); 
                    }
                    
                    public void onLoginSuccess() {
                      SubScribeMessageProcessor.this.reportSubscribeMsg(finalFilteredTemplates);
                      SubscribeMsgShowRecordUtil.updateUser();
                    }
                    
                    public void onLoginUnSupport() {
                      AppBrandLogger.i("SubscribeMessageProcessor", new Object[] { "onLoginUnSupport" });
                      if (SubScribeMessageProcessor.this.subscribeMessageCallback != null)
                        SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(3002, "login un support", null); 
                    }
                    
                    public void onLoginWhenBackground() {
                      AppBrandLogger.i("SubscribeMessageProcessor", new Object[] { "onLoginWhenBackground" });
                      if (SubScribeMessageProcessor.this.subscribeMessageCallback != null)
                        SubScribeMessageProcessor.this.subscribeMessageCallback.onResult(3002, "login when background", null); 
                    }
                    
                    public void onTriggerHostClientLogin() {
                      AppBrandLogger.d("SubscribeMessageProcessor", new Object[] { "onTriggerHostClientLogin" });
                    }
                  });
              return;
            } 
            SubScribeMessageProcessor.this.reportSubscribeMsg(finalFilteredTemplates);
          }
        }hashMap);
  }
  
  public static interface OnQueryTemplateInfoListener {
    void onFail(int param1Int, String param1String);
    
    void onSuccess(TemplateMsgLimitInfo param1TemplateMsgLimitInfo, ArrayMap<String, TemplateMsgInfo> param1ArrayMap);
  }
  
  public static interface SubscribeMessageCallback {
    void onResult(int param1Int, String param1String, ArrayMap<String, String> param1ArrayMap);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\subscribe\SubScribeMessageProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */