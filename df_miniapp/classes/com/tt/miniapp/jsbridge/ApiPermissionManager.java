package com.tt.miniapp.jsbridge;

import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.option.e.e;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiPermissionManager {
  private static List<String> controllApi;
  
  private static String[] dxpp;
  
  private static List<String> sAdControlApi = Arrays.asList(new String[] { "getAdSiteBaseInfo", "insertAdHTMLWebView", "updateAdHTMLWebView", "removeAdHTMLWebView" });
  
  private static volatile List<String> sBlackAPIList;
  
  private static JSONArray sBlackListJsonArray;
  
  private static List<String> sHostMethodWhiteList;
  
  private static List<String> sWhiteAPIList;
  
  private static JSONArray sWhiteListJsonArray;
  
  static {
    controllApi = Arrays.asList(new String[] { 
          "dealUserRelation", "createDxppTask", "getUseDuration", "getGeneralInfo", "_serviceGetPhoneNumber", "getUsageRecord", "preloadMiniProgram", "addShortcut", "showMorePanel", "setMenuButtonVisibility", 
          "checkShortcut", "onBeforeCloseReturnSync", "requestSubscribeMessage", "sendUmengEventV1" });
    dxpp = new String[] { "nloadA", "createDow", "ppTask" };
  }
  
  public static boolean canUseHostMethod(String paramString) {
    List<String> list = sHostMethodWhiteList;
    return (list == null || list.isEmpty()) ? false : sHostMethodWhiteList.contains(paramString);
  }
  
  public static List<String> getBlackAPIList() {
    return sBlackAPIList;
  }
  
  public static JSONArray getBlackListJsonArray() {
    if (sBlackListJsonArray == null)
      sBlackListJsonArray = new JSONArray(); 
    return sBlackListJsonArray;
  }
  
  private static String getPermissonErrorMsg(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(":fail platform auth deny");
      jSONObject.put("errMsg", stringBuilder.toString());
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiPermissionManager", exception.getStackTrace());
      return "";
    } 
  }
  
  public static JSONArray getWhiteListJsonArray() {
    if (sWhiteListJsonArray == null)
      sWhiteListJsonArray = new JSONArray(); 
    return sWhiteListJsonArray;
  }
  
  public static void initApiBlackList(String paramString) {
    if (TextUtils.isEmpty(paramString)) {
      sBlackAPIList = new ArrayList<String>();
      return;
    } 
    try {
      sBlackAPIList = jsonArray2List(new JSONArray(paramString));
      return;
    } catch (Exception exception) {
      sBlackAPIList = new ArrayList<String>();
      return;
    } 
  }
  
  public static void initApiWhiteList(String paramString) {
    if (TextUtils.isEmpty(paramString)) {
      sWhiteAPIList = new ArrayList<String>();
      return;
    } 
    try {
      JSONArray jSONArray = new JSONArray(paramString);
      sWhiteListJsonArray = jSONArray;
      sWhiteAPIList = jsonArray2List(jSONArray);
      return;
    } catch (Exception exception) {
      sWhiteAPIList = new ArrayList<String>();
      return;
    } 
  }
  
  public static void initHostMethodWhiteList(String paramString) {
    if (TextUtils.isEmpty(paramString)) {
      sHostMethodWhiteList = new ArrayList<String>();
      return;
    } 
    try {
      sHostMethodWhiteList = jsonArray2List((new JSONObject(paramString)).getJSONArray("host_method_whitelist"));
      return;
    } catch (Exception exception) {
      sHostMethodWhiteList = new ArrayList<String>();
      return;
    } 
  }
  
  public static boolean intercept(String paramString, int paramInt) {
    if (sBlackAPIList != null && sBlackAPIList.contains(paramString)) {
      StringBuilder stringBuilder = new StringBuilder("intercept event:");
      stringBuilder.append(paramString);
      InnerEventHelper.mpTechnologyMsg(stringBuilder.toString());
      AppbrandApplication.getInst().getJsBridge().returnAsyncResult(paramInt, getPermissonErrorMsg(paramString));
      return true;
    } 
    if (controllApi.contains(paramString)) {
      String str = mapToWhiteEvent(paramString);
      List<String> list = sWhiteAPIList;
      if (list == null || !list.contains(str)) {
        AppbrandApplication.getInst().getJsBridge().returnAsyncResult(paramInt, getPermissonErrorMsg(paramString));
        StringBuilder stringBuilder = new StringBuilder("intercept event:");
        stringBuilder.append(paramString);
        InnerEventHelper.mpTechnologyMsg(stringBuilder.toString());
        return true;
      } 
    } 
    return false;
  }
  
  public static boolean interceptAdApi(String paramString, int paramInt, e parame) {
    if (sBlackAPIList != null && sBlackAPIList.contains(paramString)) {
      StringBuilder stringBuilder = new StringBuilder("intercept event:");
      stringBuilder.append(paramString);
      InnerEventHelper.mpTechnologyMsg(stringBuilder.toString());
      parame.callback(paramInt, getPermissonErrorMsg(paramString));
      return true;
    } 
    if (sAdControlApi.contains(paramString)) {
      if (AppbrandApplicationImpl.getInst().getAppInfo().isAdSite())
        return false; 
      List<String> list = sWhiteAPIList;
      if (list == null || !list.contains(paramString)) {
        StringBuilder stringBuilder = new StringBuilder("intercept event:");
        stringBuilder.append(paramString);
        InnerEventHelper.mpTechnologyMsg(stringBuilder.toString());
        parame.callback(paramInt, getPermissonErrorMsg(paramString));
        return true;
      } 
    } 
    return false;
  }
  
  public static boolean isAdSiteMiniApp() {
    List<String> list = sWhiteAPIList;
    if (list != null && list.contains("getAdSiteBaseInfo"))
      return true; 
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    return (appInfoEntity != null && appInfoEntity.isAdSite());
  }
  
  public static boolean isCanGetUserInfo() {
    List<String> list = sWhiteAPIList;
    return (list != null && list.contains("getUserInfo"));
  }
  
  private static List<String> jsonArray2List(JSONArray paramJSONArray) throws JSONException {
    ArrayList<String> arrayList = new ArrayList();
    if (paramJSONArray == null)
      return arrayList; 
    int j = paramJSONArray.length();
    for (int i = 0; i < j; i++)
      arrayList.add(paramJSONArray.getString(i)); 
    return arrayList;
  }
  
  private static String mapToWhiteEvent(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return paramString; 
    String str = paramString;
    if ("createDxppTask".equals(paramString)) {
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append(dxpp[1]);
      stringBuffer.append(dxpp[0]);
      stringBuffer.append(dxpp[2]);
      str = stringBuffer.toString();
    } 
    return str;
  }
  
  public static boolean shouldCallbackBeforeClose() {
    List<String> list = sWhiteAPIList;
    return (list != null && list.contains("onBeforeCloseReturnSync"));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\ApiPermissionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */