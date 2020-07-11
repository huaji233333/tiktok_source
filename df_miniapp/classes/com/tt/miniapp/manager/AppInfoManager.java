package com.tt.miniapp.manager;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapp.navigate2.Nav2Util;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import org.json.JSONException;
import org.json.JSONObject;

public class AppInfoManager {
  public static AppInfoEntity generateInitAppInfo(String paramString) {
    AppInfoEntity appInfoEntity = new AppInfoEntity();
    readAppInfoFromSchemaCommon(paramString, appInfoEntity);
    Uri uri = Uri.parse(paramString);
    String str2 = uri.getQueryParameter("meta");
    if (!TextUtils.isEmpty(str2))
      parseAppInfoFromSchemaMeta(appInfoEntity, str2, appInfoEntity.isLocalTest() ^ true); 
    if (TextUtils.isEmpty(uri.getQueryParameter("version"))) {
      str2 = uri.getQueryParameter("url");
      if (!TextUtils.isEmpty(str2))
        parseAppInfoFromSchemaUrl(appInfoEntity, str2); 
    } 
    str2 = uri.getQueryParameter("inspect");
    if (!TextUtils.isEmpty(str2))
      parseAppInfoFromSchemaInspect(appInfoEntity, str2); 
    String str1 = uri.getQueryParameter("toolbarStyle");
    if (!TextUtils.isEmpty(str1))
      appInfoEntity.toolbarStyle = Integer.parseInt(str1); 
    return appInfoEntity;
  }
  
  public static void parseAppInfoFromSchemaInspect(AppInfoEntity paramAppInfoEntity, String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      paramAppInfoEntity.session = jSONObject.optString("session");
      paramAppInfoEntity.gtoken = jSONObject.optString("gtoken");
      paramAppInfoEntity.roomid = jSONObject.optString("roomid");
      paramAppInfoEntity.timelineServerUrl = jSONObject.optString("timeline_server_url");
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("AppInfoManager", new Object[] { "parseAppInfoFromSchemaInspect", exception });
      return;
    } 
  }
  
  public static void parseAppInfoFromSchemaMeta(AppInfoEntity paramAppInfoEntity, String paramString, boolean paramBoolean) {
    AppBrandLogger.d("AppInfoManager", new Object[] { "parseAppInfoFromSchemaMeta ", paramString, " isReleaseVersionMiniApp:", Boolean.valueOf(paramBoolean) });
    if (TextUtils.isEmpty(paramString))
      return; 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      paramAppInfoEntity.icon = jSONObject.optString("icon");
      paramAppInfoEntity.appName = jSONObject.optString("name");
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("AppInfoManager", new Object[] { "parseAppInfoFromSchemaMeta", exception });
      return;
    } 
  }
  
  public static void parseAppInfoFromSchemaUrl(AppInfoEntity paramAppInfoEntity, String paramString) {
    if (TextUtils.isEmpty(paramString))
      return; 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      paramAppInfoEntity.session = jSONObject.optString("session");
      paramAppInfoEntity.gtoken = jSONObject.optString("gtoken");
      paramAppInfoEntity.roomid = jSONObject.optString("roomid");
      paramAppInfoEntity.timelineServerUrl = jSONObject.optString("timeline_server_url");
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("AppInfoManager", new Object[] { "parseAppInfoFromSchemaInspect", exception });
      return;
    } 
  }
  
  public static boolean parseSchemaInV1(String paramString, AppInfoEntity paramAppInfoEntity) {
    Uri uri = Uri.parse(paramString);
    paramAppInfoEntity.appId = uri.getQueryParameter("app_id");
    if (!TextUtils.isEmpty(uri.getQueryParameter("launch_from")))
      paramAppInfoEntity.launchFrom = uri.getQueryParameter("launch_from"); 
    if (uri.getQueryParameter("scene") == null) {
      if (TextUtils.isEmpty(paramAppInfoEntity.launchFrom)) {
        paramAppInfoEntity.scene = "";
      } else {
        paramAppInfoEntity.scene = HostDependManager.getInst().getScene(paramAppInfoEntity.launchFrom);
      } 
    } else {
      paramAppInfoEntity.scene = uri.getQueryParameter("scene");
    } 
    if (TextUtils.isEmpty(paramAppInfoEntity.scene))
      paramAppInfoEntity.scene = "0"; 
    if (uri.getQueryParameter("sub_scene") == null) {
      paramAppInfoEntity.subScene = "";
    } else {
      paramAppInfoEntity.subScene = uri.getQueryParameter("sub_scene");
    } 
    paramAppInfoEntity.shareTicket = uri.getQueryParameter("shareTicket");
    paramAppInfoEntity.startPage = uri.getQueryParameter("start_page");
    paramAppInfoEntity.query = uri.getQueryParameter("query");
    paramAppInfoEntity.extra = uri.getQueryParameter("extra");
    paramAppInfoEntity.bdpLaunchQuery = uri.getQueryParameter("bdp_launch_query");
    if (!TextUtils.isEmpty(uri.getQueryParameter("name")))
      paramAppInfoEntity.appName = uri.getQueryParameter("name"); 
    if (!TextUtils.isEmpty(uri.getQueryParameter("icon")))
      paramAppInfoEntity.icon = uri.getQueryParameter("icon"); 
    if (TextUtils.equals(uri.getHost(), "microapp")) {
      paramAppInfoEntity.setType(1);
    } else if (TextUtils.equals(uri.getHost(), "microgame")) {
      paramAppInfoEntity.setType(2);
    } 
    if (!TextUtils.isEmpty(uri.getQueryParameter("ttid")))
      paramAppInfoEntity.ttId = uri.getQueryParameter("ttid"); 
    String str = uri.getQueryParameter("version_type");
    if (!TextUtils.isEmpty(str))
      paramAppInfoEntity.versionType = str; 
    paramAppInfoEntity.token = uri.getQueryParameter("token");
    paramAppInfoEntity.oriStartPage = paramAppInfoEntity.startPage;
    paramAppInfoEntity.bdpLog = uri.getQueryParameter("bdp_log");
    paramAppInfoEntity.launchType = uri.getQueryParameter("bdp_launch_type");
    if (!TextUtils.isEmpty(paramAppInfoEntity.bdpLog))
      try {
        JSONObject jSONObject = new JSONObject(paramAppInfoEntity.bdpLog);
        String str1 = jSONObject.optString("launch_from");
        String str2 = jSONObject.optString("ttid");
        if (!TextUtils.isEmpty(str1))
          paramAppInfoEntity.launchFrom = str1; 
        if (!TextUtils.isEmpty(str2))
          paramAppInfoEntity.ttId = str2; 
        paramAppInfoEntity.location = jSONObject.optString("location");
        paramAppInfoEntity.bizLocation = jSONObject.optString("biz_location");
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "AppInfoManager", jSONException.getStackTrace());
      }  
    Nav2Util.initRefererInfo(paramAppInfoEntity, uri.getQueryParameter("referer_info"));
    paramAppInfoEntity.adSiteVersionFromSchema = uri.getQueryParameter("__ad_site_version__");
    paramAppInfoEntity.isAutoTest = TextUtils.equals("1", uri.getQueryParameter("auto_test"));
    return true;
  }
  
  public static boolean parseSchemaInV2(String paramString, AppInfoEntity paramAppInfoEntity) {
    Uri uri = Uri.parse(paramString);
    String str = uri.getQueryParameter("version");
    if (TextUtils.isEmpty(str))
      return false; 
    if (str.equals("v2")) {
      paramAppInfoEntity.schemaVersion = str;
      paramAppInfoEntity.appId = uri.getQueryParameter("app_id");
      paramAppInfoEntity.bdpLog = uri.getQueryParameter("bdp_log");
      paramAppInfoEntity.techType = uri.getQueryParameter("tech_type");
      if (!TextUtils.isEmpty(uri.getQueryParameter("launch_from")))
        paramAppInfoEntity.launchFrom = uri.getQueryParameter("launch_from"); 
      if (!TextUtils.isEmpty(uri.getQueryParameter("ttid")))
        paramAppInfoEntity.ttId = uri.getQueryParameter("ttid"); 
      if (!TextUtils.isEmpty(paramAppInfoEntity.bdpLog))
        try {
          JSONObject jSONObject = new JSONObject(paramAppInfoEntity.bdpLog);
          String str1 = jSONObject.optString("launch_from");
          String str2 = jSONObject.optString("ttid");
          if (!TextUtils.isEmpty(str1))
            paramAppInfoEntity.launchFrom = str1; 
          if (!TextUtils.isEmpty(str2))
            paramAppInfoEntity.ttId = str2; 
          paramAppInfoEntity.location = jSONObject.optString("location");
          paramAppInfoEntity.bizLocation = jSONObject.optString("biz_location");
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(6, "AppInfoManager", jSONException.getStackTrace());
        }  
      if (uri.getQueryParameter("scene") == null) {
        if (TextUtils.isEmpty(paramAppInfoEntity.launchFrom)) {
          paramAppInfoEntity.scene = "";
        } else {
          paramAppInfoEntity.scene = HostDependManager.getInst().getScene(paramAppInfoEntity.launchFrom);
        } 
      } else {
        paramAppInfoEntity.scene = uri.getQueryParameter("scene");
      } 
      if (TextUtils.isEmpty(paramAppInfoEntity.scene))
        paramAppInfoEntity.scene = "0"; 
      paramAppInfoEntity.shareTicket = uri.getQueryParameter("shareTicket");
      paramAppInfoEntity.startPage = uri.getQueryParameter("start_page");
      paramAppInfoEntity.query = uri.getQueryParameter("query");
      paramAppInfoEntity.oriStartPage = paramAppInfoEntity.startPage;
      str = uri.getQueryParameter("version_type");
      if (!TextUtils.isEmpty(str))
        paramAppInfoEntity.versionType = str; 
      paramAppInfoEntity.token = uri.getQueryParameter("token");
      paramAppInfoEntity.extra = uri.getQueryParameter("extra");
      paramAppInfoEntity.bdpLaunchQuery = uri.getQueryParameter("bdp_launch_query");
      paramAppInfoEntity.launchType = uri.getQueryParameter("bdp_launch_type");
      paramAppInfoEntity.snapShotCompileVersion = uri.getQueryParameter("snapshot_compile_version");
      paramAppInfoEntity.sourceMd5 = uri.getQueryParameter("source_md5");
      if (TextUtils.equals(uri.getHost(), "microapp")) {
        paramAppInfoEntity.type = 1;
      } else if (TextUtils.equals(uri.getHost(), "microgame")) {
        paramAppInfoEntity.type = 2;
      } 
      Nav2Util.initRefererInfo(paramAppInfoEntity, uri.getQueryParameter("referer_info"));
      paramAppInfoEntity.adSiteVersionFromSchema = uri.getQueryParameter("__ad_site_version__");
      paramAppInfoEntity.isAutoTest = TextUtils.equals("1", uri.getQueryParameter("auto_test"));
      return true;
    } 
    return false;
  }
  
  public static void readAppInfoFromSchemaCommon(String paramString, AppInfoEntity paramAppInfoEntity) {
    if (!parseSchemaInV2(paramString, paramAppInfoEntity))
      parseSchemaInV1(paramString, paramAppInfoEntity); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\AppInfoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */