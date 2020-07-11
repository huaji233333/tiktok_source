package com.tt.miniapp.launchcache.meta;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.util.RSAUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.ad.AdModel;
import com.tt.option.q.d;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppInfoHelper {
  private static final String DEFAULT_BATCH_META_URL;
  
  private static final String DEFAULT_META_URL;
  
  private static TTCode sCachedTTCode;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getCurrentDomain());
    stringBuilder.append("/api/apps/v3/meta");
    DEFAULT_META_URL = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getCurrentDomain());
    stringBuilder.append("/api/apps/BatchMeta");
    DEFAULT_BATCH_META_URL = stringBuilder.toString();
    sCachedTTCode = null;
  }
  
  private static j doRequest(String paramString, Map<String, Object> paramMap, RequestType paramRequestType) {
    i i1 = new i(paramString, "GET");
    i1.a(paramMap);
    i i2 = HostDependManager.getInst().convertMetaRequest(i1);
    CrossProcessDataEntity crossProcessDataEntity = HostProcessBridge.getUserInfo();
    if (crossProcessDataEntity != null) {
      String str = (new UserInfoManager.UserInfo(crossProcessDataEntity)).sessionId;
    } else {
      crossProcessDataEntity = null;
    } 
    if (!TextUtils.isEmpty((CharSequence)crossProcessDataEntity))
      i2.a("x-tma-host-sessionid", (String)crossProcessDataEntity); 
    i2.k = 6000L;
    i2.l = 6000L;
    i2.j = 6000L;
    if (paramRequestType != null)
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("request_tag", paramRequestType);
        i2.n = jSONObject;
      } catch (JSONException jSONException) {} 
    j j = NetManager.getInst().request(i2);
    if (DebugUtil.debug())
      AppBrandLogger.d("AppInfoHelper", new Object[] { "doRequest :  url is  ", i2.f(), " & value = ", j.a() }); 
    return j;
  }
  
  private static Map<String, Object> generateBatchParamMap(Context paramContext, Collection<String> paramCollection, String paramString1, String paramString2, String paramString3) {
    JSONArray jSONArray = new JSONArray();
    Iterator<String> iterator = paramCollection.iterator();
    while (iterator.hasNext())
      jSONArray.put(iterator.next()); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("version", paramString3);
    hashMap.put("appidList", jSONArray);
    hashMap.put("ttcode", URLEncoder.encode(paramString1));
    if (!TextUtils.isEmpty(paramString2))
      hashMap.put("token", paramString2); 
    return putCommonParams(paramContext, (Map)hashMap);
  }
  
  private static String generateGetUrl(String paramString, Map<String, Object> paramMap) {
    StringBuilder stringBuilder = new StringBuilder(paramString);
    Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      Map.Entry entry = iterator.next();
      if (bool) {
        stringBuilder.append("?");
        stringBuilder.append((String)entry.getKey());
        stringBuilder.append("=");
        stringBuilder.append(entry.getValue());
        bool = false;
        continue;
      } 
      stringBuilder.append("&");
      stringBuilder.append((String)entry.getKey());
      stringBuilder.append("=");
      stringBuilder.append(entry.getValue());
    } 
    return stringBuilder.toString();
  }
  
  private static Map<String, Object> generateParamMap(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("version", paramString4);
    hashMap.put("appid", paramString1);
    hashMap.put("ttcode", URLEncoder.encode(paramString2));
    hashMap.put("sdk_version", Integer.valueOf(1));
    if (!TextUtils.isEmpty(paramString3))
      hashMap.put("token", paramString3); 
    return putCommonParams(paramContext, (Map)hashMap);
  }
  
  private static String generateTTCode(Context paramContext, String[] paramArrayOfString) {
    // Byte code:
    //   0: getstatic com/tt/miniapp/launchcache/meta/AppInfoHelper.sCachedTTCode : Lcom/tt/miniapp/launchcache/meta/TTCode;
    //   3: astore_3
    //   4: aload_3
    //   5: ifnull -> 31
    //   8: aload_1
    //   9: iconst_0
    //   10: aload_3
    //   11: getfield i : Ljava/lang/String;
    //   14: aastore
    //   15: aload_1
    //   16: iconst_1
    //   17: getstatic com/tt/miniapp/launchcache/meta/AppInfoHelper.sCachedTTCode : Lcom/tt/miniapp/launchcache/meta/TTCode;
    //   20: getfield v : Ljava/lang/String;
    //   23: aastore
    //   24: getstatic com/tt/miniapp/launchcache/meta/AppInfoHelper.sCachedTTCode : Lcom/tt/miniapp/launchcache/meta/TTCode;
    //   27: getfield code : Ljava/lang/String;
    //   30: areturn
    //   31: new java/lang/StringBuilder
    //   34: dup
    //   35: ldc_w 'generateTTCode() called with: context = ['
    //   38: invokespecial <init> : (Ljava/lang/String;)V
    //   41: astore_3
    //   42: aload_3
    //   43: aload_0
    //   44: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload_3
    //   49: ldc_w ']'
    //   52: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: pop
    //   56: ldc 'AppInfoHelper'
    //   58: iconst_1
    //   59: anewarray java/lang/Object
    //   62: dup
    //   63: iconst_0
    //   64: aload_3
    //   65: invokevirtual toString : ()Ljava/lang/String;
    //   68: aastore
    //   69: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   72: ldc_w ''
    //   75: astore_3
    //   76: iconst_3
    //   77: istore_2
    //   78: aload_3
    //   79: astore #4
    //   81: iload_2
    //   82: ifle -> 219
    //   85: aload_1
    //   86: iconst_0
    //   87: invokestatic genRandomString : ()Ljava/lang/String;
    //   90: aastore
    //   91: aload_1
    //   92: iconst_1
    //   93: invokestatic genRandomString : ()Ljava/lang/String;
    //   96: aastore
    //   97: new java/lang/StringBuilder
    //   100: dup
    //   101: invokespecial <init> : ()V
    //   104: astore #4
    //   106: aload #4
    //   108: aload_1
    //   109: iconst_0
    //   110: aaload
    //   111: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: pop
    //   115: aload #4
    //   117: ldc_w '#'
    //   120: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: pop
    //   124: aload #4
    //   126: aload_1
    //   127: iconst_1
    //   128: aaload
    //   129: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: pop
    //   133: aload_0
    //   134: aload #4
    //   136: invokevirtual toString : ()Ljava/lang/String;
    //   139: invokestatic encryptContent : (Landroid/content/Context;Ljava/lang/String;)[B
    //   142: astore #5
    //   144: aload #5
    //   146: ifnull -> 157
    //   149: aload #5
    //   151: bipush #10
    //   153: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   156: astore_3
    //   157: aload_3
    //   158: astore #4
    //   160: aload_3
    //   161: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   164: ifeq -> 219
    //   167: ldc 'AppInfoHelper'
    //   169: bipush #6
    //   171: anewarray java/lang/Object
    //   174: dup
    //   175: iconst_0
    //   176: ldc_w 'ttCode isEmpty. key:'
    //   179: aastore
    //   180: dup
    //   181: iconst_1
    //   182: aload_1
    //   183: iconst_0
    //   184: aaload
    //   185: aastore
    //   186: dup
    //   187: iconst_2
    //   188: ldc_w ' iv:'
    //   191: aastore
    //   192: dup
    //   193: iconst_3
    //   194: aload_1
    //   195: iconst_1
    //   196: aaload
    //   197: aastore
    //   198: dup
    //   199: iconst_4
    //   200: ldc_w ' secret:'
    //   203: aastore
    //   204: dup
    //   205: iconst_5
    //   206: aload #5
    //   208: aastore
    //   209: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   212: iload_2
    //   213: iconst_1
    //   214: isub
    //   215: istore_2
    //   216: goto -> 78
    //   219: ldc com/tt/miniapp/launchcache/meta/AppInfoHelper
    //   221: monitorenter
    //   222: aload #4
    //   224: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   227: ifne -> 262
    //   230: new com/tt/miniapp/launchcache/meta/TTCode
    //   233: dup
    //   234: invokespecial <init> : ()V
    //   237: astore_0
    //   238: aload_0
    //   239: aload_1
    //   240: iconst_0
    //   241: aaload
    //   242: putfield i : Ljava/lang/String;
    //   245: aload_0
    //   246: aload_1
    //   247: iconst_1
    //   248: aaload
    //   249: putfield v : Ljava/lang/String;
    //   252: aload_0
    //   253: aload #4
    //   255: putfield code : Ljava/lang/String;
    //   258: aload_0
    //   259: putstatic com/tt/miniapp/launchcache/meta/AppInfoHelper.sCachedTTCode : Lcom/tt/miniapp/launchcache/meta/TTCode;
    //   262: ldc com/tt/miniapp/launchcache/meta/AppInfoHelper
    //   264: monitorexit
    //   265: ldc 'AppInfoHelper'
    //   267: iconst_2
    //   268: anewarray java/lang/Object
    //   271: dup
    //   272: iconst_0
    //   273: ldc_w 'ttCode '
    //   276: aastore
    //   277: dup
    //   278: iconst_1
    //   279: aload #4
    //   281: aastore
    //   282: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   285: aload #4
    //   287: areturn
    //   288: astore_0
    //   289: ldc com/tt/miniapp/launchcache/meta/AppInfoHelper
    //   291: monitorexit
    //   292: goto -> 297
    //   295: aload_0
    //   296: athrow
    //   297: goto -> 295
    // Exception table:
    //   from	to	target	type
    //   222	262	288	finally
    //   262	265	288	finally
    //   289	292	288	finally
  }
  
  public static String getErrorMpServiceName(RequestType paramRequestType) {
    return (null.$SwitchMap$com$tt$miniapp$launchcache$RequestType[paramRequestType.ordinal()] != 1) ? "mp_start_error" : "mp_preload_error";
  }
  
  private static List<String> getMetaHostList(Context paramContext) {
    ArrayList<String> arrayList = new ArrayList();
    List<String> list = SettingsDAO.getListString(paramContext, new Enum[] { (Enum)Settings.BDP_META_CONFIG, (Enum)Settings.BdpMetaConfig.URLS });
    String str = DEFAULT_META_URL;
    if (list.size() > 0)
      str = list.get(0); 
    arrayList.add(AppbrandContext.getInst().getInitParams().getHostStr(1001, str));
    if (!HostDependManager.getInst().isEnableI18nNetRequest()) {
      str = "https://microapp.bytedance.com/api/apps/v3/meta";
    } else {
      str = "";
    } 
    if (list.size() > 1)
      str = list.get(1); 
    str = AppbrandContext.getInst().getInitParams().getHostStr(1003, str);
    if (!TextUtils.isEmpty(str))
      arrayList.add(str); 
    return arrayList;
  }
  
  public static String getUrlHost(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    try {
      return (new URL(paramString)).getHost();
    } catch (MalformedURLException malformedURLException) {
      AppBrandLogger.e("AppInfoHelper", new Object[] { malformedURLException });
      return "";
    } 
  }
  
  public static boolean isInHostStack(Uri paramUri) {
    return "hostStack".equalsIgnoreCase(paramUri.getQueryParameter("launch_mode"));
  }
  
  public static String mergeMetaSimple(String paramString1, String paramString2, String paramString3, AppInfoEntity paramAppInfoEntity, String paramString4, String paramString5, String paramString6) throws Exception {
    JSONObject jSONObject1 = (new JSONObject(paramString1)).getJSONObject("data");
    paramString2 = RSAUtil.AESDecrypt(paramString2, paramString3, jSONObject1.getString("md5"));
    JSONArray jSONArray = jSONObject1.optJSONArray("path");
    paramAppInfoEntity.md5 = paramString2;
    paramAppInfoEntity.appUrls = new ArrayList();
    int i;
    for (i = 0; i < jSONArray.length(); i++)
      paramAppInfoEntity.appUrls.add(jSONArray.getString(i)); 
    paramString2 = RSAUtil.AESEncrypt(paramString5, paramString6, paramString2);
    JSONObject jSONObject2 = new JSONObject(paramString4);
    JSONObject jSONObject3 = jSONObject2.getJSONObject("data");
    jSONObject3.put("md5", paramString2);
    jSONObject3.put("path", jSONArray);
    jSONObject2.put("data", jSONObject3);
    return jSONObject2.toString();
  }
  
  public static boolean parseAppInfo(String paramString1, String paramString2, String paramString3, String paramString4, RequestType paramRequestType, RequestResultInfo paramRequestResultInfo) {
    JSONObject jSONObject;
    if (TextUtils.isEmpty(paramString1)) {
      jSONObject = new JSONObject();
      try {
        StringBuilder stringBuilder = new StringBuilder("parseAppMeta json is null, meta url = ");
        stringBuilder.append(paramString4);
        stringBuilder.append(" requestType = ");
        stringBuilder.append(paramRequestType);
        jSONObject.put("errMsg", stringBuilder.toString());
        jSONObject.put("isNetworkAvailable", NetUtil.isNetworkAvailable((Context)AppbrandContext.getInst().getApplicationContext()));
      } catch (JSONException jSONException) {
        AppBrandLogger.e("AppInfoHelper", new Object[] { jSONException });
      } 
      AppBrandMonitor.statusRate(getErrorMpServiceName(paramRequestType), 1012, jSONObject);
      AppBrandLogger.e("AppInfoHelper", new Object[] { "parseAppMeta json is null, reason ==  ", jSONObject.toString() });
      paramRequestResultInfo.errorCode = ErrorCode.META.NULL.getCode();
      return false;
    } 
    paramRequestResultInfo.url = paramString4;
    paramRequestResultInfo.encryKey = (String)jSONException;
    paramRequestResultInfo.encryIV = paramString3;
    paramRequestResultInfo.originData = (String)jSONObject;
    if (paramRequestResultInfo.appInfo == null)
      paramRequestResultInfo.appInfo = new AppInfoEntity(); 
    AppInfoEntity appInfoEntity = paramRequestResultInfo.appInfo;
    try {
      jSONObject = new JSONObject((String)jSONObject);
      if (jSONObject.optInt("error") != 0) {
        paramRequestResultInfo.errorCode = ErrorCode.META.CODE_ERROR.getCode();
        return false;
      } 
      jSONObject = jSONObject.optJSONObject("data");
      String str = jSONObject.optString("appid");
      if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, "null")) {
        appInfoEntity.appId = str;
        str = jSONObject.optString("name");
        if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, "null"))
          appInfoEntity.appName = str; 
        str = jSONObject.optString("icon");
        if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, "null"))
          appInfoEntity.icon = str; 
        str = jSONObject.optString("version");
        if (!TextUtils.isEmpty(str) && !TextUtils.equals(str, "null")) {
          boolean bool;
          appInfoEntity.version = str;
          appInfoEntity.state = jSONObject.optInt("state");
          appInfoEntity.versionState = jSONObject.optInt("version_state");
          appInfoEntity.ttId = jSONObject.optString("ttid");
          appInfoEntity.isOpenLocation = jSONObject.optInt("open_location");
          appInfoEntity.ttSafeCode = jSONObject.optString("ttcode");
          appInfoEntity.ttBlackCode = jSONObject.optString("ttblackcode");
          if (jSONObject.optInt("orientation", 0) == 1) {
            bool = true;
          } else {
            bool = false;
          } 
          appInfoEntity.isLandScape = bool;
          appInfoEntity.setType(jSONObject.optInt("type"));
          appInfoEntity.domains = jSONObject.optString("domains");
          appInfoEntity.md5 = jSONObject.optString("md5");
          appInfoEntity.minJssdk = jSONObject.optString("min_jssdk");
          appInfoEntity.shareLevel = jSONObject.optInt("share_level");
          appInfoEntity.encryptextra = jSONObject.optString("extra");
          appInfoEntity.session = jSONObject.optString("session");
          appInfoEntity.gtoken = jSONObject.optString("gtoken");
          appInfoEntity.roomid = jSONObject.optString("roomid");
          appInfoEntity.timelineServerUrl = jSONObject.optString("timeline_server_url");
          appInfoEntity.loadingBg = jSONObject.optString("loading_bg");
          appInfoEntity.versionCode = jSONObject.optLong("version_code");
          appInfoEntity.switchBitmap = jSONObject.optInt("switch_bitmap");
          appInfoEntity.needUpdateSettings = jSONObject.optInt("need_update_setting");
          appInfoEntity.leastVersionCode = jSONObject.optInt("least_version_code");
          JSONArray jSONArray2 = jSONObject.optJSONArray("ad");
          if (jSONArray2 != null) {
            appInfoEntity.adlist = new ArrayList();
            int i;
            for (i = 0; i < jSONArray2.length(); i++)
              appInfoEntity.adlist.add(AdModel.a(jSONArray2.getJSONObject(i).toString())); 
          } 
          jSONArray2 = jSONObject.optJSONArray("path");
          if (jSONArray2 != null) {
            appInfoEntity.appUrls = new ArrayList();
            int i;
            for (i = 0; i < jSONArray2.length(); i++)
              appInfoEntity.appUrls.add(jSONArray2.getString(i)); 
          } 
          appInfoEntity.mExtJson = jSONObject.optString("ext_json");
          appInfoEntity.privacyPolicyUrl = jSONObject.optString("privacy_policy");
          appInfoEntity.pkgCompressType = jSONObject.optString("ttpkg_compress_type");
          appInfoEntity.adSiteVersionFromMeta = jSONObject.optString("ad_site_version");
          AppBrandLogger.d("AppInfoHelper", new Object[] { "appMeta.dataObject ", jSONObject.toString() });
          JSONArray jSONArray1 = jSONObject.optJSONArray("libra_path");
          if (jSONArray1 != null) {
            ArrayList<String> arrayList = new ArrayList();
            int i;
            for (i = 0; i < jSONArray1.length(); i++)
              arrayList.add(jSONArray1.getString(i)); 
            appInfoEntity.libra_path = arrayList;
            AppBrandLogger.d("AppInfoHelper", new Object[] { "appInfo.libra_path ", arrayList });
          } 
          if (!TextUtils.isEmpty(appInfoEntity.encryptextra)) {
            appInfoEntity.encryptextra = RSAUtil.AESDecrypt((String)jSONException, paramString3, appInfoEntity.encryptextra);
            try {
              JSONObject jSONObject1 = new JSONObject(appInfoEntity.encryptextra);
              appInfoEntity.innertype = jSONObject1.optInt("is_inner");
              appInfoEntity.authPass = jSONObject1.optInt("auth_pass", 0);
              AppBrandLogger.d("AppInfoHelper", new Object[] { "appInfo.innertype  = ", Integer.valueOf(appInfoEntity.innertype) });
            } catch (Exception exception) {
              AppBrandLogger.e("AppInfoHelper", new Object[] { "get extra error", exception });
            } 
          } 
          if (!TextUtils.isEmpty(appInfoEntity.ttSafeCode)) {
            appInfoEntity.ttSafeCode = RSAUtil.AESDecrypt((String)jSONException, paramString3, appInfoEntity.ttSafeCode);
            AppBrandLogger.d("AppInfoHelper", new Object[] { "mAppInfo.ttSafeCode ", appInfoEntity.ttSafeCode });
          } 
          if (!TextUtils.isEmpty(appInfoEntity.ttBlackCode)) {
            appInfoEntity.ttBlackCode = RSAUtil.AESDecrypt((String)jSONException, paramString3, appInfoEntity.ttBlackCode);
            AppBrandLogger.d("AppInfoHelper", new Object[] { "mAppInfo.ttBlackCode ", appInfoEntity.ttBlackCode });
          } 
          if (!TextUtils.isEmpty(appInfoEntity.domains)) {
            appInfoEntity.domains = RSAUtil.AESDecrypt((String)jSONException, paramString3, appInfoEntity.domains);
            AppBrandLogger.d("AppInfoHelper", new Object[] { "mAppInfo.domains ", appInfoEntity.domains });
          } 
          if (!TextUtils.isEmpty(appInfoEntity.md5)) {
            appInfoEntity.md5 = RSAUtil.AESDecrypt((String)jSONException, paramString3, appInfoEntity.md5);
            AppBrandLogger.d("AppInfoHelper", new Object[] { "mAppInfo.md5 ", appInfoEntity.md5 });
            return true;
          } 
        } else {
          paramRequestResultInfo.errorCode = ErrorCode.META.INVALID_VERSION.getCode();
          return false;
        } 
      } else {
        paramRequestResultInfo.errorCode = ErrorCode.META.INVALID_APP_ID.getCode();
        return false;
      } 
    } catch (JSONException jSONException1) {
      paramRequestResultInfo.errorCode = ErrorCode.META.JSON_ERROR.getCode();
      paramRequestResultInfo.errorMsg = Log.getStackTraceString((Throwable)jSONException1);
      AppBrandLogger.e("AppInfoHelper", new Object[] { "", jSONException1 });
      return false;
    } catch (Exception exception) {
      paramRequestResultInfo.errorCode = ErrorCode.META.UNKNOWN.getCode();
      paramRequestResultInfo.errorMsg = Log.getStackTraceString(exception);
      AppBrandLogger.e("AppInfoHelper", new Object[] { "", exception });
      return false;
    } 
    return true;
  }
  
  public static void preload(Context paramContext) {
    generateTTCode(paramContext, new String[2]);
  }
  
  private static Map<String, Object> putCommonParams(Context paramContext, Map<String, Object> paramMap) {
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      paramMap.put("aid", initParamsEntity.getAppId());
      paramMap.put("version_code", initParamsEntity.getVersionCode());
      paramMap.put("bdp_version_code", initParamsEntity.getVersionCode());
      paramMap.put("bdp_device_id", d.a());
      paramMap.put("channel", initParamsEntity.getChannel());
      paramMap.put("device_platform", initParamsEntity.getDevicePlatform());
      paramMap.put("bdp_device_platform", initParamsEntity.getDevicePlatform());
      paramMap.put("os_version", initParamsEntity.getOsVersion());
      paramMap.put("tma_jssdk_version", BaseBundleManager.getInst().getSdkCurrentVersionStr(paramContext));
    } 
    return paramMap;
  }
  
  public static AppInfoRequestResult request(Context paramContext, AppInfoEntity paramAppInfoEntity, RequestType paramRequestType) {
    String str2;
    AppInfoRequestResult appInfoRequestResult = new AppInfoRequestResult();
    boolean bool = ProcessUtil.isMainProcess(paramContext);
    byte b = 0;
    if (bool) {
      i = 0;
    } else if (ProcessUtil.isMiniappProcess()) {
      i = 1;
    } else {
      i = -1;
    } 
    appInfoRequestResult.fromProcess = i;
    String str3 = paramAppInfoEntity.appId;
    String str4 = paramAppInfoEntity.token;
    if (!TextUtils.isEmpty(paramAppInfoEntity.versionType)) {
      str2 = paramAppInfoEntity.versionType;
    } else {
      str2 = "current";
    } 
    paramAppInfoEntity.versionType = str2;
    String str1 = paramAppInfoEntity.versionType;
    String[] arrayOfString = new String[2];
    String str5 = generateTTCode(paramContext, arrayOfString);
    appInfoRequestResult.appId = str3;
    appInfoRequestResult.encryKey = arrayOfString[0];
    appInfoRequestResult.encryIV = arrayOfString[1];
    appInfoRequestResult.generateMetaParamsBegin = System.currentTimeMillis();
    appInfoRequestResult.generateMetaParamsBeginCpuTime = SystemClock.elapsedRealtime();
    Map<String, Object> map = generateParamMap(paramContext, str3, str5, str4, str1);
    appInfoRequestResult.generateMetaParamsEnd = System.currentTimeMillis();
    appInfoRequestResult.generateMetaParamsEndCpuTime = SystemClock.elapsedRealtime();
    List<String> list = getMetaHostList(paramContext);
    int j = list.size();
    int i = b;
    while (i < j) {
      AppInfoRequestResult.RequestMetaRecord requestMetaRecord = new AppInfoRequestResult.RequestMetaRecord();
      str3 = list.get(i);
      requestMetaRecord.url = generateGetUrl(str3, map);
      requestMetaRecord.startTimeStamp = System.currentTimeMillis();
      requestMetaRecord.startCpuTime = SystemClock.elapsedRealtime();
      j j1 = doRequest(str3, map, paramRequestType);
      requestMetaRecord.stopTimeStamp = System.currentTimeMillis();
      requestMetaRecord.stopCpuTime = SystemClock.elapsedRealtime();
      requestMetaRecord.code = j1.b;
      requestMetaRecord.data = j1.a();
      requestMetaRecord.message = j1.c;
      requestMetaRecord.throwable = Log.getStackTraceString(j1.f);
      appInfoRequestResult.requestRecordList.add(requestMetaRecord);
      if (TextUtils.isEmpty(requestMetaRecord.data))
        i++; 
    } 
    return appInfoRequestResult;
  }
  
  public static BatchMetaRequestResult requestBatchMeta(Context paramContext, Collection<String> paramCollection, RequestType paramRequestType) {
    BatchMetaRequestResult batchMetaRequestResult = new BatchMetaRequestResult();
    String[] arrayOfString = new String[2];
    Map<String, Object> map = generateBatchParamMap(paramContext, paramCollection, generateTTCode(paramContext, arrayOfString), null, "current");
    batchMetaRequestResult.url = generateGetUrl(DEFAULT_BATCH_META_URL, map);
    batchMetaRequestResult.encryKey = arrayOfString[0];
    batchMetaRequestResult.encryIV = arrayOfString[1];
    String str = doRequest(DEFAULT_BATCH_META_URL, map, paramRequestType).a();
    try {
      if (!TextUtils.isEmpty(str)) {
        JSONObject jSONObject = new JSONObject(str);
        int i = jSONObject.getInt("error");
        if (i == 0) {
          JSONArray jSONArray = jSONObject.getJSONArray("data");
          if (jSONArray != null && jSONArray.length() != 0) {
            paramCollection = new ArrayList<String>();
            for (i = 0; i < jSONArray.length(); i++)
              paramCollection.add(jSONArray.getString(i)); 
            batchMetaRequestResult.originDataList = (List<String>)paramCollection;
            return batchMetaRequestResult;
          } 
          StringBuilder stringBuilder1 = new StringBuilder("respData.data is null or empty: ");
          stringBuilder1.append(jSONArray);
          class ParseBatchMetaResultException extends Exception {
            ParseBatchMetaResultException(AppInfoHelper this$0) {
              super((String)this$0);
            }
          };
          throw new ParseBatchMetaResultException(stringBuilder1.toString());
        } 
        StringBuilder stringBuilder = new StringBuilder("respData.error is not 0: ");
        stringBuilder.append(i);
        throw new ParseBatchMetaResultException(stringBuilder.toString());
      } 
      throw new ParseBatchMetaResultException("respData is empty");
    } catch (Exception exception) {
      AppBrandLogger.e("AppInfoHelper", new Object[] { exception });
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getMessage());
      stringBuilder.append("\n");
      stringBuilder.append(Log.getStackTraceString(exception));
      batchMetaRequestResult.errorMsg = stringBuilder.toString();
      return batchMetaRequestResult;
    } 
  }
  
  public static AppInfoEntity requestMeta(String paramString) {
    return requestMeta(paramString, "current");
  }
  
  public static AppInfoEntity requestMeta(String paramString1, String paramString2) {
    Application application = AppbrandContext.getInst().getApplicationContext();
    String[] arrayOfString = new String[2];
    Map<String, Object> map = generateParamMap((Context)application, paramString1, generateTTCode((Context)application, arrayOfString), null, paramString2);
    String str2 = AppbrandContext.getInst().getInitParams().getHostStr(1001, DEFAULT_META_URL);
    j j = doRequest(str2, map, RequestType.jump_single);
    String str1 = generateGetUrl(str2, map);
    RequestResultInfo requestResultInfo = new RequestResultInfo();
    return parseAppInfo(j.a(), arrayOfString[0], arrayOfString[1], str1, RequestType.jump_single, requestResultInfo) ? requestResultInfo.appInfo : null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\AppInfoHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */