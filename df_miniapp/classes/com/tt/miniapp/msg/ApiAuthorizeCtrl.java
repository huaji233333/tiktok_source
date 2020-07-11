package com.tt.miniapp.msg;

import android.app.Activity;
import android.text.TextUtils;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.option.e.e;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiAuthorizeCtrl extends b {
  public ApiAuthorizeCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void allAuthorizeFiltered(final boolean isMultiPermission, final LinkedHashMap<Integer, String> authorizeResultMap) {
    HashSet<Integer> hashSet = new HashSet();
    for (Map.Entry<Integer, String> entry : authorizeResultMap.entrySet()) {
      if (((String)entry.getValue()).contentEquals("system auth deny"))
        hashSet.add(entry.getKey()); 
    } 
    recursionRequestSysPermissions(authorizeResultMap, (Activity)AppbrandContext.getInst().getCurrentActivity(), hashSet.iterator(), new RecursionRequestSysPermissionsFinishCallback() {
          public void onFinished(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            try {
              boolean bool1;
              JSONObject jSONObject = new JSONObject();
              boolean bool2 = true;
              Iterator<Map.Entry> iterator = authorizeResultMap.entrySet().iterator();
              while (true) {
                bool1 = bool2;
                if (iterator.hasNext()) {
                  if (((String)((Map.Entry)iterator.next()).getValue()).contentEquals("ok")) {
                    bool1 = false;
                    break;
                  } 
                  continue;
                } 
                break;
              } 
              String str = "authorize:ok";
              if (bool1)
                if (isMultiPermission) {
                  str = "authorize:fail";
                } else {
                  str = "authorize:fail auth deny";
                }  
              jSONObject.put("errMsg", str);
              if (authorizeResultMap.size() > 0)
                jSONObject.put("data", ApiAuthorizeCtrl.this.obtainCallbackDataJsonObject(authorizeResultMap)); 
              ApiAuthorizeCtrl.this.mApiHandlerCallback.callback(ApiAuthorizeCtrl.this.mCallBackId, jSONObject.toString());
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.stacktrace(6, "tma_ApiAuthorizeCtrl", jSONException.getStackTrace());
              ApiAuthorizeCtrl.this.callbackDefaultMsg(false);
              return;
            } 
          }
        });
  }
  
  private List<String> checkInvalidScope(String paramString) throws JSONException {
    ArrayList<String> arrayList = new ArrayList();
    JSONObject jSONObject = new JSONObject(paramString);
    AppBrandLogger.d("tma_ApiAuthorizeCtrl", new Object[] { paramString });
    paramString = jSONObject.optString("scope");
    HashSet hashSet = new HashSet();
    if (!TextUtils.isEmpty(paramString))
      hashSet.addAll(Arrays.asList(paramString.split(","))); 
    for (String str : hashSet) {
      if (HostDependManager.getInst().scopeToBrandPermission(str) == null)
        arrayList.add(str); 
    } 
    return arrayList;
  }
  
  private void filterSeparateAuthorizePermissions(Set<BrandPermissionUtils.BrandPermission> paramSet, LinkedHashMap<Integer, String> paramLinkedHashMap) {
    Iterator<BrandPermissionUtils.BrandPermission> iterator = paramSet.iterator();
    while (iterator.hasNext()) {
      BrandPermissionUtils.BrandPermission brandPermission = iterator.next();
      if (BrandPermissionUtils.BrandPermission.sSeparatePermissionList.contains(brandPermission)) {
        paramLinkedHashMap.put(Integer.valueOf(brandPermission.getPermissionType()), "invalid scope");
        iterator.remove();
      } 
    } 
  }
  
  private void getUserInfoPermission(final Set<BrandPermissionUtils.BrandPermission> needAuthPermissions, LinkedHashMap<Integer, String> paramLinkedHashMap) {
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    try {
      CrossProcessDataEntity crossProcessDataEntity = HostProcessBridge.getUserInfo();
      if (crossProcessDataEntity != null) {
        UserInfoManager.UserInfo userInfo = new UserInfoManager.UserInfo(crossProcessDataEntity);
      } else {
        crossProcessDataEntity = null;
      } 
      final String session = InnerHostProcessBridge.getPlatformSession((AppbrandApplication.getInst().getAppInfo()).appId);
      if (TextUtils.isEmpty(str)) {
        AppBrandLogger.e("tma_ApiAuthorizeCtrl", new Object[] { "session is empty" });
        callbackExtraInfoMsg(false, "session is empty");
        return;
      } 
      if (!((UserInfoManager.UserInfo)crossProcessDataEntity).isLogin) {
        callbackExtraInfoMsg(false, "platform auth deny");
        return;
      } 
      Observable.create(new Function<String>() {
            public String fun() {
              String str2;
              StringBuilder stringBuilder2 = new StringBuilder();
              stringBuilder2.append(AppbrandConstant.OpenApi.getInst().getUSERINFO_URL());
              stringBuilder2.append((AppbrandApplication.getInst().getAppInfo()).appId);
              String str4 = stringBuilder2.toString();
              if (AppbrandContext.getInst().getInitParams() != null) {
                str2 = AppbrandContext.getInst().getInitParams().getAppId();
              } else {
                str2 = "";
              } 
              String str3 = str4;
              if (!TextUtils.isEmpty(str2)) {
                AppBrandLogger.d("tma_ApiAuthorizeCtrl", new Object[] { "aid = ", str2 });
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str4);
                stringBuilder.append("&aid=");
                stringBuilder.append(str2);
                str3 = stringBuilder.toString();
              } 
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append(str3);
              stringBuilder1.append("&session=");
              stringBuilder1.append(session);
              String str1 = stringBuilder1.toString();
              str1 = NetManager.getInst().request(str1).a();
              AppBrandLogger.d("tma_ApiAuthorizeCtrl", new Object[] { str1 });
              return str1;
            }
          }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber() {
            public void onError(Throwable param1Throwable) {
              ApiAuthorizeCtrl.this.callbackExtraInfoMsg(false, "server error ");
              AppBrandLogger.e("tma_ApiAuthorizeCtrl", new Object[] { param1Throwable });
            }
            
            public void onSuccess(Object param1Object) {
              String str1;
              param1Object = param1Object;
              final boolean hasRequestPermission = TextUtils.isEmpty((CharSequence)param1Object);
              String str2 = "";
              JSONObject jSONObject = null;
              if (bool) {
                AppBrandMonitor.statusRate("mp_start_error", 1021, null);
                param1Object = "";
                hashMap = null;
                str1 = str2;
              } else {
                try {
                  hashMap = (HashMap<Object, Object>)new JSONObject((String)param1Object);
                  try {
                    int i = hashMap.optInt("error", -1);
                    if (i == 0) {
                      JSONObject jSONObject1 = hashMap.optJSONObject("data").optJSONObject("userInfo");
                      param1Object = jSONObject1.getString("nickName");
                      try {
                        str1 = jSONObject1.getString("avatarUrl");
                        jSONObject = jSONObject1;
                      } catch (JSONException jSONException) {
                        AppBrandLogger.stacktrace(6, "tma_ApiAuthorizeCtrl", jSONException.getStackTrace());
                        str1 = str2;
                      } 
                    } else {
                      param1Object = ApiAuthorizeCtrl.this;
                      StringBuilder stringBuilder = new StringBuilder("server error ");
                      stringBuilder.append(i);
                      param1Object.callbackExtraInfoMsg(false, stringBuilder.toString());
                      return;
                    } 
                  } catch (JSONException jSONException) {
                    param1Object = "";
                    AppBrandLogger.stacktrace(6, "tma_ApiAuthorizeCtrl", jSONException.getStackTrace());
                    str1 = str2;
                  } 
                } catch (JSONException jSONException) {
                  param1Object = "";
                  hashMap = null;
                  AppBrandLogger.stacktrace(6, "tma_ApiAuthorizeCtrl", jSONException.getStackTrace());
                  str1 = str2;
                } 
              } 
              if (hashMap == null || jSONObject == null) {
                ApiAuthorizeCtrl.this.callbackExtraInfoMsg(false, "server error ");
                return;
              } 
              HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
              hashMap.put("nickName", param1Object);
              hashMap.put("avatarUrl", str1);
              bool = BrandPermissionUtils.hasRequestPermission(11);
              BrandPermissionUtils.requestPermissions(activity, ApiAuthorizeCtrl.this.getActionName(), needAuthPermissions, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
                    public void onDenied(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                      if (!hasRequestPermission)
                        PermissionHelper.reportAuthFailResult("user_info", "mp_reject"); 
                      JSONObject jSONObject = ApiAuthorizeCtrl.this.makeCallbackJsonObj("authorize:fail auth deny", param2LinkedHashMap);
                      ApiAuthorizeCtrl.this.mApiHandlerCallback.callback(ApiAuthorizeCtrl.this.mCallBackId, jSONObject.toString());
                    }
                    
                    public void onGranted(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                      if (!hasRequestPermission)
                        PermissionHelper.reportAuthSuccessResult("user_info"); 
                      JSONObject jSONObject = ApiAuthorizeCtrl.this.makeCallbackJsonObj("authorize:ok", param2LinkedHashMap);
                      ApiAuthorizeCtrl.this.mApiHandlerCallback.callback(ApiAuthorizeCtrl.this.mCallBackId, jSONObject.toString());
                    }
                  }hashMap);
            }
          });
      return;
    } catch (Exception exception) {
      callbackDefaultMsg(false);
      AppBrandLogger.stacktrace(6, "tma_ApiAuthorizeCtrl", exception.getStackTrace());
      return;
    } 
  }
  
  private Set<BrandPermissionUtils.BrandPermission> initApiParam(String paramString) throws JSONException {
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    JSONObject jSONObject = new JSONObject(paramString);
    AppBrandLogger.d("tma_ApiAuthorizeCtrl", new Object[] { paramString });
    paramString = jSONObject.optString("scope");
    HashSet hashSet1 = new HashSet();
    if (!TextUtils.isEmpty(paramString))
      hashSet1.addAll(Arrays.asList(paramString.split(","))); 
    for (String str : hashSet1) {
      BrandPermissionUtils.BrandPermission brandPermission = HostDependManager.getInst().scopeToBrandPermission(str);
      if (brandPermission != null)
        hashSet.add(brandPermission); 
    } 
    return hashSet;
  }
  
  private boolean isWhiteMiniApp() {
    Iterator<String> iterator;
    List list = HostProcessBridge.getPermissionDialogABTestMPID();
    String str = (AppbrandApplication.getInst().getAppInfo()).appId;
    if (list != null) {
      iterator = list.iterator();
      while (iterator.hasNext()) {
        if (str.contentEquals(iterator.next())) {
          boolean bool1 = true;
          AppBrandLogger.e("tma_ApiAuthorizeCtrl", new Object[] { "isWhiteMiniApp == ", Boolean.valueOf(bool1) });
          return bool1;
        } 
      } 
    } 
    boolean bool = false;
    while (iterator.hasNext()) {
      if (str.contentEquals(iterator.next())) {
        bool = true;
        AppBrandLogger.e("tma_ApiAuthorizeCtrl", new Object[] { "isWhiteMiniApp == ", Boolean.valueOf(bool) });
        return bool;
      } 
    } 
  }
  
  private void requestPermissions(Set<BrandPermissionUtils.BrandPermission> paramSet, LinkedHashMap<Integer, String> paramLinkedHashMap) {
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    BrandPermissionUtils.requestPermissions((Activity)miniappHostBase, getActionName(), paramSet, paramLinkedHashMap, new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            String str;
            if (param1LinkedHashMap.size() > 1) {
              str = "multiple";
            } else if (param1LinkedHashMap.keySet().iterator().hasNext()) {
              str = BrandPermissionUtils.appbrandpermissionTypeToEventParamVal(((Integer)param1LinkedHashMap.keySet().iterator().next()).intValue());
            } else {
              str = null;
            } 
            PermissionHelper.reportAuthFailResult(str, "mp_reject");
            if (param1LinkedHashMap.size() == 1) {
              str = "authorize:fail auth deny";
            } else {
              str = "authorize:fail";
            } 
            JSONObject jSONObject = ApiAuthorizeCtrl.this.makeCallbackJsonObj(str, param1LinkedHashMap);
            ApiAuthorizeCtrl.this.mApiHandlerCallback.callback(ApiAuthorizeCtrl.this.mCallBackId, jSONObject.toString());
            AppBrandLogger.e("tma_ApiAuthorizeCtrl", new Object[] { "callback", jSONObject.toString() });
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<Integer> hashSet = new HashSet();
            for (Map.Entry<Integer, String> entry : param1LinkedHashMap.entrySet()) {
              if (((String)entry.getValue()).equals("ok"))
                hashSet.add(entry.getKey()); 
            } 
            ApiAuthorizeCtrl.this.recursionRequestSysPermissions(param1LinkedHashMap, activity, hashSet.iterator(), new ApiAuthorizeCtrl.RecursionRequestSysPermissionsFinishCallback() {
                  public void onFinished(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                    String str1;
                    if (param2LinkedHashMap.size() > 1) {
                      str1 = "multiple";
                    } else if (param2LinkedHashMap.keySet().iterator().hasNext()) {
                      str1 = BrandPermissionUtils.appbrandpermissionTypeToEventParamVal(((Integer)param2LinkedHashMap.keySet().iterator().next()).intValue());
                    } else {
                      str1 = null;
                    } 
                    if (param2LinkedHashMap.size() == 1) {
                      str2 = "authorize:fail auth deny";
                    } else {
                      str2 = "authorize:fail";
                    } 
                    Iterator<Map.Entry> iterator = param2LinkedHashMap.entrySet().iterator();
                    String str4 = "mp_reject";
                    String str5 = "fail";
                    String str3 = str2;
                    String str2 = str4;
                    while (iterator.hasNext()) {
                      Map.Entry entry = iterator.next();
                      String str = str5;
                      str4 = str3;
                      if (((String)entry.getValue()).equals("ok")) {
                        str = "success";
                        str4 = "authorize:ok";
                      } 
                      str5 = str;
                      str3 = str4;
                      if (((String)entry.getValue()).equals("system auth deny")) {
                        str2 = "system_reject";
                        str5 = str;
                        str3 = str4;
                      } 
                    } 
                    if (str5.contentEquals("fail")) {
                      PermissionHelper.reportAuthFailResult(str1, str2);
                    } else {
                      PermissionHelper.reportAuthSuccessResult(str1);
                    } 
                    JSONObject jSONObject = ApiAuthorizeCtrl.this.makeCallbackJsonObj(str3, param2LinkedHashMap);
                    ApiAuthorizeCtrl.this.mApiHandlerCallback.callback(ApiAuthorizeCtrl.this.mCallBackId, jSONObject.toString());
                    AppBrandLogger.e("tma_ApiAuthorizeCtrl", new Object[] { "callback", jSONObject.toString() });
                  }
                });
          }
        }null);
  }
  
  public void act() {
    if (!TextUtils.isEmpty(this.mArgs))
      try {
        boolean bool;
        List<String> list = checkInvalidScope(this.mArgs);
        int i = list.size();
        if (i > 0) {
          JSONObject jSONObject1 = new JSONObject();
          JSONObject jSONObject2 = new JSONObject();
          Iterator<String> iterator = list.iterator();
          while (iterator.hasNext())
            jSONObject2.put(iterator.next(), "invalid scope"); 
          jSONObject1.put("errMsg", "authorize:fail invalid scope");
          jSONObject1.put("data", jSONObject2);
          this.mApiHandlerCallback.callback(this.mCallBackId, jSONObject1.toString());
          return;
        } 
        Set<BrandPermissionUtils.BrandPermission> set = initApiParam(this.mArgs);
        int j = set.size();
        i = 1;
        if (j > 1) {
          bool = true;
        } else {
          bool = false;
        } 
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
        BrandPermissionUtils.filterAuthorizedPermissions(set, linkedHashMap);
        if (bool)
          for (BrandPermissionUtils.BrandPermission brandPermission : set) {
            if (BrandPermissionUtils.BrandPermission.sSeparatePermissionList.contains(brandPermission)) {
              callbackExtraInfoMsg(false, "invalid scope");
              return;
            } 
          }  
        if (set.size() > 0)
          i = 0; 
        if (i != 0) {
          allAuthorizeFiltered(bool, (LinkedHashMap)linkedHashMap);
          return;
        } 
        if (bool) {
          if (HostProcessBridge.isOnWhiteList()) {
            if (isWhiteMiniApp()) {
              requestPermissions(set, (LinkedHashMap)linkedHashMap);
              return;
            } 
            callbackExtraInfoMsg(false, "invalid scope");
            return;
          } 
          requestPermissions(set, (LinkedHashMap)linkedHashMap);
          return;
        } 
        if (((BrandPermissionUtils.BrandPermission)set.iterator().next()).equals(BrandPermissionUtils.BrandPermission.USER_INFO)) {
          getUserInfoPermission(set, (LinkedHashMap)linkedHashMap);
          return;
        } 
        requestPermissions(set, (LinkedHashMap)linkedHashMap);
        return;
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "tma_ApiAuthorizeCtrl", jSONException.getStackTrace());
      }  
    callbackDefaultMsg(false);
  }
  
  public String getActionName() {
    return "authorize";
  }
  
  public JSONObject makeCallbackJsonObj(String paramString, LinkedHashMap<Integer, String> paramLinkedHashMap) {
    JSONObject jSONObject1 = new JSONObject();
    JSONObject jSONObject2 = new JSONObject();
    try {
      jSONObject1.put("errMsg", paramString);
      for (Map.Entry<Integer, String> entry : paramLinkedHashMap.entrySet())
        jSONObject2.put(HostDependManager.getInst().permissionTypeToPermission(((Integer)entry.getKey()).intValue()).getScope(), entry.getValue()); 
      jSONObject1.put("data", jSONObject2);
      return jSONObject1;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_ApiAuthorizeCtrl", new Object[] { "requestPermissions", jSONException });
      return jSONObject1;
    } 
  }
  
  public JSONObject obtainCallbackDataJsonObject(LinkedHashMap<Integer, String> paramLinkedHashMap) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    for (Map.Entry<Integer, String> entry : paramLinkedHashMap.entrySet()) {
      BrandPermissionUtils.BrandPermission brandPermission = HostDependManager.getInst().permissionTypeToPermission(((Integer)entry.getKey()).intValue());
      if (brandPermission != null) {
        String str = (String)entry.getValue();
        jSONObject.put(brandPermission.getScope(), str);
      } 
    } 
    return jSONObject;
  }
  
  public void recursionRequestSysPermissions(final LinkedHashMap<Integer, String> grantedResult, final Activity activity, final Iterator<Integer> iterator, final RecursionRequestSysPermissionsFinishCallback callback) {
    HashSet hashSet;
    if (!iterator.hasNext()) {
      callback.onFinished(grantedResult);
      return;
    } 
    final BrandPermissionUtils.BrandPermission brandPermission = HostDependManager.getInst().permissionTypeToPermission(((Integer)iterator.next()).intValue());
    if (brandPermission.getSysPermissions() == null) {
      hashSet = new HashSet();
    } else {
      hashSet = new HashSet(Arrays.asList((Object[])brandPermission.getSysPermissions()));
    } 
    if (!hashSet.isEmpty()) {
      PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
            public void onDenied(String param1String) {
              grantedResult.put(Integer.valueOf(brandPermission.getPermissionType()), "system auth deny");
              ApiAuthorizeCtrl.this.recursionRequestSysPermissions(grantedResult, activity, iterator, callback);
            }
            
            public void onGranted() {
              grantedResult.put(Integer.valueOf(brandPermission.getPermissionType()), "ok");
              ApiAuthorizeCtrl.this.recursionRequestSysPermissions(grantedResult, activity, iterator, callback);
            }
          });
      return;
    } 
    recursionRequestSysPermissions(grantedResult, activity, iterator, callback);
  }
  
  static interface RecursionRequestSysPermissionsFinishCallback {
    void onFinished(LinkedHashMap<Integer, String> param1LinkedHashMap);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiAuthorizeCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */