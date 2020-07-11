package com.tt.miniapp.event;

import android.os.Build;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.event.origin.OriginHelper;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.streamloader.LoadTask;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.EventHelper;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.TimeMeter;
import java.util.Map;
import org.json.JSONObject;

public class InnerEventHelper extends EventHelper {
  private static Event.Builder buildMpLoadResult(long paramLong1, String paramString1, String paramString2, long paramLong2, long paramLong3, String paramString3, boolean paramBoolean) {
    String str;
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      str = "mp_shell_ad_load_result";
    } else {
      str = "mp_load_result";
    } 
    Event.Builder builder2 = Event.builder(str).kv("duration", Long.valueOf(paramLong1));
    if (AppbrandApplication.getInst().getAppInfo() != null) {
      str = (AppbrandApplication.getInst().getAppInfo()).launchType;
    } else {
      str = null;
    } 
    Event.Builder builder1 = builder2.kv("launch_type", str).kv("result_type", paramString1).kv("ever_stopped", Boolean.valueOf(paramBoolean)).kv("load_type", getLoadType()).kv("error_msg", paramString2).kv("total_duration", Long.valueOf(paramLong3)).kv("open_duration", Long.valueOf(paramLong2)).kv("load_state", paramString3).kv("load_pkg_type", getLoadPkgType()).kv("load_pkg_source", getPkgSourceInt()).kv("load_pkg_source_str", getPkgSourceType()).kv("load_first_launch", getPkgLoaded());
    if (((RenderSnapShotManager)AppbrandApplicationImpl.getInst().getService(RenderSnapShotManager.class)).isSnapShotRender()) {
      paramString1 = "snapshot";
    } else {
      paramString1 = "standard";
    } 
    return builder1.kv("render_type", paramString1).kv("load_image", LoadStateManager.getIns().getLoadingBgState());
  }
  
  private static String getLoadPkgType() {
    LoadTask loadTask = StreamLoader.getLoadTask();
    return (loadTask != null) ? loadTask.getLoadPkgType() : null;
  }
  
  private static String getLoadType() {
    LoadTask loadTask = StreamLoader.getLoadTask();
    if (loadTask != null) {
      Boolean bool = loadTask.isUseLocalPkg();
    } else {
      loadTask = null;
    } 
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    return (loadTask == Boolean.TRUE) ? "restart" : ((appInfoEntity != null && appInfoEntity.getFromType == 1) ? "local_meta" : "");
  }
  
  private static Integer getPkgLoaded() {
    LoadTask loadTask = StreamLoader.getLoadTask();
    return (loadTask == null) ? null : Integer.valueOf(loadTask.isFirstLaunch() ^ true);
  }
  
  @Deprecated
  private static Integer getPkgSourceInt() {
    RequestType requestType = getPkgSourceType();
    if (requestType == null)
      return null; 
    int i = null.$SwitchMap$com$tt$miniapp$launchcache$RequestType[requestType.ordinal()];
    return (i != 1) ? ((i != 2) ? ((i != 3) ? ((i != 4) ? Integer.valueOf(-1) : Integer.valueOf(3)) : Integer.valueOf(2)) : Integer.valueOf(1)) : Integer.valueOf(0);
  }
  
  private static RequestType getPkgSourceType() {
    LoadTask loadTask = StreamLoader.getLoadTask();
    return (loadTask == null) ? null : loadTask.getSourceType();
  }
  
  public static void mpAsyncApply(String paramString1, String paramString2) {
    Event.builder("mp_apply").kv("mp_latest_version", paramString1).kv("mp_current_version", paramString2).flush();
  }
  
  public static void mpAsyncNotify(String paramString1, String paramString2) {
    Event.builder("mp_notify").kv("mp_latest_version", paramString1).kv("mp_current_version", paramString2).flush();
  }
  
  public static void mpAuthoritySetting(boolean paramBoolean) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static void mpBaseBundleFileNotFound(String paramString) {
    Event.builder("event_mp_basebundle_file_not_found").kv("file_path", paramString).flush();
  }
  
  public static void mpCpJsLoadResult(String paramString1, long paramLong, String paramString2) {
    Event.builder("mp_cpjs_load_result").kv("duration", Long.valueOf(paramLong)).kv("result_type", paramString1).kv("error_msg", paramString2).flush();
  }
  
  public static void mpDiskOccupy(JSONObject paramJSONObject) {
    Event.builder("mp_storage_occupy").addKVJsonObject(paramJSONObject).flush();
  }
  
  public static void mpEnterPageEvent(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2) {
    Event.Builder builder2 = Event.builder("mp_enter_page").kv("page_path", paramString1);
    paramString1 = paramString2;
    if (paramString2 == null)
      paramString1 = ""; 
    Event.Builder builder1 = builder2.kv("query", paramString1).kv("has_webview", Integer.valueOf(paramInt1));
    if (!TextUtils.isEmpty(paramString3)) {
      builder1.kv("last_page_path", paramString3);
      builder1.kv("last_has_webview", Integer.valueOf(paramInt2));
    } 
    builder1.flush();
  }
  
  public static void mpFileDownloadResultEvent(String paramString1, String paramString2) {
    Event.builder("mp_file_download_result").kv("result_type", paramString1).kv("msg", paramString2).flush();
  }
  
  public static void mpFirstContentfulPaint(String paramString1, long paramLong, ArrayMap<String, Long> paramArrayMap, String paramString2, boolean paramBoolean, String paramString3, int paramInt) {
    Event.Builder builder2 = Event.builder("mp_first_contentful_paint");
    for (Map.Entry entry : paramArrayMap.entrySet())
      builder2.kv((String)entry.getKey(), entry.getValue()); 
    Event.Builder builder1 = builder2.kv("result_type", paramString1).kv("open_duration", Long.valueOf(paramLong)).kv("stop_reason", paramString2).kv("ever_stopped", Boolean.valueOf(paramBoolean)).kv("last_error", TimeLogger.getInstance().getLastErrorLog());
    if (AppbrandApplication.getInst().getAppInfo() != null) {
      paramString1 = (AppbrandApplication.getInst().getAppInfo()).launchType;
    } else {
      paramString1 = null;
    } 
    builder1 = builder1.kv("launch_type", paramString1).kv("load_type", getLoadType()).kv("load_state", paramString3).kv("progress", Integer.valueOf(paramInt)).kv("load_pkg_type", getLoadPkgType()).kv("load_pkg_source", getPkgSourceInt()).kv("load_first_launch", getPkgLoaded());
    if (((RenderSnapShotManager)AppbrandApplicationImpl.getInst().getService(RenderSnapShotManager.class)).isSnapShotRender()) {
      paramString1 = "snapshot";
    } else {
      paramString1 = "standard";
    } 
    builder1.kv("render_type", paramString1).flush();
  }
  
  public static void mpJsLoadResult(String paramString1, long paramLong, String paramString2) {
    Event.builder("mp_js_load_result").kv("result_type", paramString1).kv("duration", Long.valueOf(paramLong)).kv("error_msg", paramString2).flush();
  }
  
  public static void mpJumpEnsure(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    byte b;
    Event.Builder builder2 = Event.builder("mp_jump_ensure").kv("dest_mp_id", paramString);
    if (paramBoolean3) {
      paramString = "white_list";
    } else {
      paramString = "user_access";
    } 
    builder2 = builder2.kv("ensure_way", paramString);
    if (paramBoolean1) {
      paramString = "yes";
    } else {
      paramString = "no";
    } 
    Event.Builder builder1 = builder2.kv("result_type", paramString);
    if (paramBoolean2) {
      b = 1;
    } else {
      b = 2;
    } 
    builder1.kv("is_game_center", Integer.valueOf(b)).flush();
  }
  
  public static void mpLaunch(long paramLong) {
    String str;
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      str = "mp_shell_ad_launch";
    } else {
      str = "mp_launch";
    } 
    Event.builder(str).kv("duration", Long.valueOf(paramLong)).kv("lang", LocaleManager.getInst().getCurrentLang()).addKVJsonObject(OriginHelper.getOriginJson()).flush();
  }
  
  public static void mpLoadDomReady(long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean) {
    String str;
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      str = "mp_shell_ad_load_domready";
    } else {
      str = "mp_load_domready";
    } 
    Event.Builder builder = Event.builder(str).kv("duration", Long.valueOf(paramLong1)).kv("total_duration", Long.valueOf(paramLong3)).kv("open_duration", Long.valueOf(paramLong2)).kv("ever_stopped", Boolean.valueOf(paramBoolean)).kv("result_type", "success");
    if (((RenderSnapShotManager)AppbrandApplicationImpl.getInst().getService(RenderSnapShotManager.class)).isSnapShotRender()) {
      str = "snapshot";
    } else {
      str = "standard";
    } 
    builder.kv("render_type", str).kv("error_msg", CharacterUtils.empty()).flush();
  }
  
  public static void mpLoadResult(long paramLong1, String paramString1, String paramString2, long paramLong2, long paramLong3, String paramString3) {
    buildMpLoadResult(paramLong1, paramString1, paramString2, paramLong2, paramLong3, paramString3, false).flush();
  }
  
  public static void mpLoadResultInner(long paramLong1, String paramString1, String paramString2, long paramLong2, long paramLong3, String paramString3) {
    buildMpLoadResult(paramLong1, paramString1, paramString2, paramLong2, paramLong3, paramString3, false).kv("__inner_handled", Boolean.valueOf(true)).flush();
  }
  
  public static void mpLoadResultSuccess(long paramLong1, long paramLong2, long paramLong3, String paramString, boolean paramBoolean) {
    buildMpLoadResult(paramLong1, "success", "", paramLong2, paramLong3, paramString, paramBoolean).flush();
  }
  
  public static void mpMetaRequestResult(AppInfoEntity paramAppInfoEntity, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, int paramInt, String paramString5) {
    Event.Builder builder = Event.builder("mp_meta_request_result", paramAppInfoEntity).kv("request_type", paramString1).kv("request_host", paramString2).kv("result_type", paramString3).kv("duration", Long.valueOf(paramLong)).kv("from_process", Integer.valueOf(paramInt)).kv("error_msg", paramString5);
    if (!TextUtils.isEmpty(paramString4))
      builder.kv("launch_from", paramString4); 
    builder.flush();
  }
  
  public static void mpMetaRequestStart(AppInfoEntity paramAppInfoEntity, String paramString1, String paramString2) {
    Event.Builder builder = Event.builder("mp_meta_request_start", paramAppInfoEntity).kv("request_type", paramString1);
    if (!TextUtils.isEmpty(paramString2))
      builder.kv("launch_from", paramString2); 
    builder.flush();
  }
  
  public static void mpMicroGameRunError(String paramString) {
    Event.builder("mp_game_run_error").kv("device_identify", AppbrandUtil.getCurrentDeviceFlag()).kv("device_model", Build.MODEL).kv("error_msg", paramString).flush();
  }
  
  public static void mpMoreGameBtnClick(boolean paramBoolean1, String paramString, boolean paramBoolean2) {
    String str;
    Event.Builder builder = Event.builder("mp_component_click").kv("component_name", "in_mp_jump");
    if (paramBoolean2) {
      str = "native";
    } else {
      str = "api";
    } 
    builder = builder.kv("component_style", str);
    if (paramBoolean1) {
      str = "success";
    } else {
      str = "fail";
    } 
    builder.kv("result_type", str).kv("msg", paramString).flush();
  }
  
  public static void mpMoreGameItemClick(String paramString) {
    Event.builder("mp_jump_icon_click").kv("dest_mp_id", paramString).flush();
  }
  
  public static void mpMoreGameItemShow(String paramString) {
    Event.builder("mp_jump_icon_show").kv("dest_mp_id", paramString).flush();
  }
  
  public static void mpPageLoadResult(String paramString1, String paramString2, long paramLong, String paramString3) {
    String str;
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      str = "mp_shell_ad_page_load_result";
    } else {
      str = "mp_page_load_result";
    } 
    Event.builder(str).kv("page_path", paramString1).kv("result_type", paramString2).kv("duration", Long.valueOf(paramLong)).kv("error_msg", paramString3).flush();
  }
  
  public static void mpPageLoadStart(String paramString) {
    String str;
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      str = "mp_shell_ad_page_load_start";
    } else {
      str = "mp_page_load_start";
    } 
    Event.builder(str).kv("page_path", paramString).flush();
  }
  
  public static void mpPreloadResult(String paramString, long paramLong) {
    Event.builder("mp_preload_result").kv("result_type", paramString).kv("duration", Long.valueOf(paramLong)).flush();
  }
  
  public static void mpPublishClick(String paramString1, String paramString2) {
    Event.builder("mp_publish_click").kv("position", paramString1).kv("content_type", paramString2).flush();
  }
  
  public static void mpPublishDone(String paramString1, String paramString2, String paramString3) {
    Event.builder("mp_publish_done").kv("position", paramString1).kv("content_type", paramString2).kv("result_type", paramString3).flush();
  }
  
  public static void mpRenderResult(String paramString1, long paramLong, String paramString2) {
    Event.builder("mp_render_result").kv("result_type", paramString1).kv("duration", Long.valueOf(paramLong)).kv("error_msg", paramString2).flush();
  }
  
  public static void mpSchemaAssess(String paramString) {
    String str1;
    Event.Builder builder = Event.builder("mp_schema_assess");
    MicroSchemaEntity.LaunchFromCheckResult launchFromCheckResult = MicroSchemaEntity.checkLaunchFrom(paramString);
    String str2 = null;
    if (launchFromCheckResult == null) {
      launchFromCheckResult = null;
    } else {
      str1 = MicroSchemaEntity.checkLaunchFrom(paramString).getName();
    } 
    builder = builder.kv("launch_from_check", str1);
    if (MicroSchemaEntity.checkTTid(paramString) == null) {
      str1 = null;
    } else {
      str1 = MicroSchemaEntity.checkTTid(paramString).getName();
    } 
    builder = builder.kv("ttid_check", str1);
    if (MicroSchemaEntity.checkScene(paramString) == null) {
      str1 = null;
    } else {
      str1 = MicroSchemaEntity.checkScene(paramString).getName();
    } 
    builder = builder.kv("scene_check", str1);
    if (MicroSchemaEntity.checkBdpsum(paramString) == null) {
      str1 = str2;
    } else {
      str1 = MicroSchemaEntity.checkBdpsum(paramString).getName();
    } 
    builder.kv("bdpsum_check", str1).kv("schema_string", paramString).flush();
  }
  
  public static void mpScreenRecordPublishClick(String paramString1, String paramString2, String paramString3) {
    Event.builder("mp_publish_click").kv("position", paramString1).kv("content_type", "screen_record").kv("alias_id", paramString2).kv("filter_type", paramString3).flush();
  }
  
  public static void mpScreenRecordPublishDone(String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4) {
    Event.Builder builder = Event.builder("mp_publish_done").kv("position", paramString1).kv("content_type", "screen_record").kv("alias_id", paramString2).kv("filter_type", paramString3);
    if (paramBoolean) {
      paramString1 = "success";
    } else {
      paramString1 = "fail";
    } 
    builder.kv("filter_result", paramString1).kv("result_type", paramString4).flush();
  }
  
  public static void mpSdkValidation(String paramString) {
    Event.builder("mp_sdk_validation").kv("lib_version", paramString).flush();
  }
  
  public static void mpShareClickEvent(String paramString, boolean paramBoolean) {
    Event.Builder builder = Event.builder("mp_share_click").kv("page_path", AppConfig.cutHtmlSuffix(AppbrandApplication.getInst().getCurrentPagePath())).kv("position", paramString);
    if (paramBoolean) {
      paramString = "token";
    } else {
      paramString = "link";
    } 
    builder.kv("share_type", paramString).flush();
  }
  
  public static void mpShareResult(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean) {
    Event.Builder builder = Event.builder("mp_share_result").kv("page_path", AppConfig.cutHtmlSuffix(AppbrandApplication.getInst().getCurrentPagePath())).kv("share_platform", paramString1).kv("position", paramString2);
    if (paramBoolean) {
      paramString1 = "token";
    } else {
      paramString1 = "link";
    } 
    builder.kv("share_type", paramString1).kv("result_type", paramString3).kv("error_msg", paramString4).flush();
  }
  
  public static void mpShareToPlatform(String paramString1, String paramString2, boolean paramBoolean) {
    Event.Builder builder = Event.builder("mp_share_to_platform").kv("page_path", AppConfig.cutHtmlSuffix(AppbrandApplication.getInst().getCurrentPagePath())).kv("share_platform", paramString1).kv("position", paramString2);
    if (paramBoolean) {
      paramString1 = "token";
    } else {
      paramString1 = "link";
    } 
    builder.kv("share_type", paramString1).flush();
  }
  
  public static void mpShareUpload(String paramString1, long paramLong, String paramString2, String paramString3, boolean paramBoolean) {
    if (paramLong == 0L)
      return; 
    long l = TimeMeter.currentMillis();
    Event.Builder builder = Event.builder("mp_share_upload").kv("page_path", AppConfig.cutHtmlSuffix(AppbrandApplication.getInst().getCurrentPagePath())).kv("position", paramString1);
    if (paramBoolean) {
      paramString1 = "token";
    } else {
      paramString1 = "link";
    } 
    builder.kv("share_type", paramString1).kv("result_type", paramString2).kv("duration", Long.valueOf(l - paramLong)).kv("error_msg", paramString3).flush();
  }
  
  public static void mpShareWindow(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, boolean paramBoolean) {
    if (paramLong == 0L)
      return; 
    long l = TimeMeter.currentMillis();
    Event.Builder builder = Event.builder("mp_share_window").kv("page_path", AppConfig.cutHtmlSuffix(AppbrandApplication.getInst().getCurrentPagePath())).kv("share_platform", paramString1).kv("position", paramString2);
    if (paramBoolean) {
      paramString1 = "token";
    } else {
      paramString1 = "link";
    } 
    builder.kv("share_type", paramString1).kv("result_type", paramString3).kv("duration", Long.valueOf(l - paramLong)).kv("error_msg", paramString4).flush();
  }
  
  public static void mpStayPageEvent(String paramString1, String paramString2, long paramLong, String paramString3, boolean paramBoolean) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static void mpTechnologyMsg(String paramString) {
    Event.builder("mp_technology_msg").kv("device_model", Build.MODEL).kv("msg", paramString).flush();
  }
  
  public static void mpVersionInfo(boolean paramBoolean, String paramString, long paramLong) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\InnerEventHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */