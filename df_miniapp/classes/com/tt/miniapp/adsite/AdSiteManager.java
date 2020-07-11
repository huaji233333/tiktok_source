package com.tt.miniapp.adsite;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.component.nativeview.NativeAdWebView;
import com.tt.miniapp.launchcache.LaunchCacheCleanDataManager;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.meta.MetaService;
import com.tt.miniapp.launchcache.meta.RequestResultInfo;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import java.lang.ref.SoftReference;
import java.util.Map;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class AdSiteManager {
  private volatile Boolean isAdSiteBrowser;
  
  private SoftReference<View> mLoadingView;
  
  private NativeAdWebView mWebView;
  
  private AdSiteManager() {}
  
  private Uri.Builder buildUrlFromSchema(String paramString, boolean paramBoolean) {
    MicroSchemaEntity microSchemaEntity = MicroSchemaEntity.parseFromSchema(paramString);
    Uri.Builder builder = new Uri.Builder();
    builder.scheme("ttadcache");
    builder.authority(microSchemaEntity.getAppId());
    Map map = microSchemaEntity.getQuery();
    if (map == null) {
      builder.path("__cache_dir__/index.html");
    } else {
      Object object = map.get("__cache_load_path__");
      if (!paramBoolean && object instanceof String && !"".equals(object)) {
        object = object.toString();
        if (StreamLoader.findFile((String)object) != null) {
          builder.path((String)object);
        } else {
          builder.path("__cache_dir__/index.html");
        } 
      } else {
        builder.path("__cache_dir__/index.html");
      } 
      map.remove("__cache_load_path__");
      JSONObject jSONObject = getJsonFromMap(map);
      if (jSONObject != null)
        for (Map.Entry entry : jSONObject.entrySet()) {
          if (!TextUtils.isEmpty((String)entry.getKey())) {
            String str1;
            jSONObject = (JSONObject)entry.getValue();
            if (jSONObject instanceof JSONAware) {
              builder.appendQueryParameter((String)entry.getKey(), ((JSONAware)jSONObject).toJSONString());
              continue;
            } 
            String str2 = (String)entry.getKey();
            if (jSONObject != null) {
              str1 = jSONObject.toString();
            } else {
              str1 = "";
            } 
            builder.appendQueryParameter(str2, str1);
          } 
        }  
    } 
    if (!TextUtils.isEmpty(microSchemaEntity.getScene()))
      builder.appendQueryParameter("scene", microSchemaEntity.getScene()); 
    return builder;
  }
  
  public static AdSiteManager getInstance() {
    return Holder.INSTANCE;
  }
  
  public static JSONObject getJsonFromMap(Map paramMap) {
    if (paramMap == null)
      return null; 
    try {
      return (JSONObject)(new JSONParser()).parse(JSONValue.toJSONString(paramMap));
    } catch (Exception exception) {
      return null;
    } 
  }
  
  private boolean initIsAdSiteBrowserInner(Context paramContext, AppInfoEntity paramAppInfoEntity) {
    if (paramAppInfoEntity != null) {
      if (paramAppInfoEntity.isGame())
        return false; 
      if (TextUtils.equals("0", paramAppInfoEntity.adSiteVersionFromSchema))
        return false; 
      AppInfoEntity appInfoEntity = parseAppInfoFromCachedMeta(paramContext, paramAppInfoEntity);
      if (TextUtils.equals("1", paramAppInfoEntity.adSiteVersionFromSchema)) {
        if (appInfoEntity != null && !isNewPlan(appInfoEntity))
          LaunchCacheCleanDataManager.INSTANCE.cleanMiniAppCache(paramContext, paramAppInfoEntity.appId); 
        return true;
      } 
      return isNewPlan(appInfoEntity);
    } 
    return false;
  }
  
  private boolean isNewPlan(AppInfoEntity paramAppInfoEntity) {
    return (paramAppInfoEntity != null && paramAppInfoEntity.isAdSite() && TextUtils.equals("1", paramAppInfoEntity.adSiteVersionFromMeta));
  }
  
  private AppInfoEntity parseAppInfoFromCachedMeta(Context paramContext, AppInfoEntity paramAppInfoEntity) {
    if (paramAppInfoEntity.isLocalTest())
      return null; 
    RequestResultInfo requestResultInfo = ((MetaService)AppbrandApplicationImpl.getInst().getService(MetaService.class)).tryFetchLocalMeta(paramContext, paramAppInfoEntity.appId, RequestType.normal);
    return (requestResultInfo != null) ? requestResultInfo.appInfo : null;
  }
  
  public Uri.Builder buildDefaultUrlFromSchema(String paramString) {
    return buildUrlFromSchema(paramString, true);
  }
  
  public Uri.Builder buildUrlFromSchema(String paramString) {
    return buildUrlFromSchema(paramString, false);
  }
  
  public View getLoadingView() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mLoadingView : Ljava/lang/ref/SoftReference;
    //   6: ifnull -> 24
    //   9: aload_0
    //   10: getfield mLoadingView : Ljava/lang/ref/SoftReference;
    //   13: invokevirtual get : ()Ljava/lang/Object;
    //   16: checkcast android/view/View
    //   19: astore_1
    //   20: aload_0
    //   21: monitorexit
    //   22: aload_1
    //   23: areturn
    //   24: aload_0
    //   25: monitorexit
    //   26: aconst_null
    //   27: areturn
    //   28: astore_1
    //   29: aload_0
    //   30: monitorexit
    //   31: aload_1
    //   32: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	28	finally
  }
  
  public NativeAdWebView getWebView(Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mWebView : Lcom/tt/miniapp/component/nativeview/NativeAdWebView;
    //   6: ifnull -> 22
    //   9: aload_0
    //   10: getfield mWebView : Lcom/tt/miniapp/component/nativeview/NativeAdWebView;
    //   13: astore_1
    //   14: aload_0
    //   15: aconst_null
    //   16: putfield mWebView : Lcom/tt/miniapp/component/nativeview/NativeAdWebView;
    //   19: goto -> 34
    //   22: new com/tt/miniapp/component/nativeview/NativeAdWebView
    //   25: dup
    //   26: aload_1
    //   27: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   30: invokespecial <init> : (Landroid/content/Context;)V
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: areturn
    //   38: astore_1
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_1
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	38	finally
    //   22	34	38	finally
  }
  
  public boolean initIsAdSiteBrowser(Context paramContext, AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: aload_0
    //   1: getfield isAdSiteBrowser : Ljava/lang/Boolean;
    //   4: ifnonnull -> 70
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield isAdSiteBrowser : Ljava/lang/Boolean;
    //   13: ifnonnull -> 60
    //   16: invokestatic uptimeMillis : ()J
    //   19: lstore_3
    //   20: aload_0
    //   21: aload_0
    //   22: aload_1
    //   23: aload_2
    //   24: invokespecial initIsAdSiteBrowserInner : (Landroid/content/Context;Lcom/tt/miniapphost/entity/AppInfoEntity;)Z
    //   27: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   30: putfield isAdSiteBrowser : Ljava/lang/Boolean;
    //   33: invokestatic getInstance : ()Lcom/tt/miniapp/util/TimeLogger;
    //   36: iconst_2
    //   37: anewarray java/lang/String
    //   40: dup
    //   41: iconst_0
    //   42: ldc_w 'initIsAdSiteBrowser'
    //   45: aastore
    //   46: dup
    //   47: iconst_1
    //   48: invokestatic uptimeMillis : ()J
    //   51: lload_3
    //   52: lsub
    //   53: invokestatic valueOf : (J)Ljava/lang/String;
    //   56: aastore
    //   57: invokevirtual logTimeDuration : ([Ljava/lang/String;)V
    //   60: aload_0
    //   61: monitorexit
    //   62: goto -> 70
    //   65: astore_1
    //   66: aload_0
    //   67: monitorexit
    //   68: aload_1
    //   69: athrow
    //   70: aload_0
    //   71: getfield isAdSiteBrowser : Ljava/lang/Boolean;
    //   74: invokevirtual booleanValue : ()Z
    //   77: ireturn
    // Exception table:
    //   from	to	target	type
    //   9	60	65	finally
    //   60	62	65	finally
    //   66	68	65	finally
  }
  
  public boolean isAdSiteBrowser() {
    return (this.isAdSiteBrowser != null) ? this.isAdSiteBrowser.booleanValue() : false;
  }
  
  public boolean isAdSiteBrowserInited() {
    return (this.isAdSiteBrowser != null);
  }
  
  public void preload(Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   6: astore_1
    //   7: aload_0
    //   8: getfield mWebView : Lcom/tt/miniapp/component/nativeview/NativeAdWebView;
    //   11: ifnonnull -> 26
    //   14: aload_0
    //   15: new com/tt/miniapp/component/nativeview/NativeAdWebView
    //   18: dup
    //   19: aload_1
    //   20: invokespecial <init> : (Landroid/content/Context;)V
    //   23: putfield mWebView : Lcom/tt/miniapp/component/nativeview/NativeAdWebView;
    //   26: aload_0
    //   27: getfield mLoadingView : Ljava/lang/ref/SoftReference;
    //   30: ifnonnull -> 55
    //   33: aload_0
    //   34: new java/lang/ref/SoftReference
    //   37: dup
    //   38: aload_1
    //   39: invokestatic from : (Landroid/content/Context;)Landroid/view/LayoutInflater;
    //   42: ldc_w 2097676290
    //   45: aconst_null
    //   46: invokevirtual inflate : (ILandroid/view/ViewGroup;)Landroid/view/View;
    //   49: invokespecial <init> : (Ljava/lang/Object;)V
    //   52: putfield mLoadingView : Ljava/lang/ref/SoftReference;
    //   55: aload_0
    //   56: monitorexit
    //   57: return
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	58	finally
    //   26	55	58	finally
  }
  
  static interface Holder {
    public static final AdSiteManager INSTANCE = new AdSiteManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\adsite\AdSiteManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */