package com.tt.miniapp.util;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.meta.MetaService;
import com.tt.miniapp.launchcache.meta.RequestResultInfo;
import com.tt.miniapp.manager.basebundle.BaseBundleDAO;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandViewWindowRoot;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class RenderSnapShotManager extends AppbrandServiceManager.ServiceBase {
  private final String TAG = "RenderSnapShotManager";
  
  private String mErrorArgs;
  
  private JSONObject mFirstTabBarJson;
  
  public JSONObject mFirstTitleBarJson;
  
  private boolean mIsFirstRender;
  
  private boolean mIsReportSuccessLoad;
  
  public AppbrandSinglePage mPage;
  
  private Runnable mRunnable;
  
  private boolean mSnapShotReady;
  
  private long mVersionCode;
  
  public RenderSnapShotManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private boolean initFirstRenderJSON(String paramString, AppInfoEntity paramAppInfoEntity) {
    String str = "";
    if (TextUtils.isEmpty(paramString))
      return false; 
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "RenderSnapShotManager_initFirstRenderJSON" });
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      String str1 = jSONObject.optString("vdom");
      paramString = str;
      try {
        str = jSONObject.optString("css");
        paramString = str;
        if (!TextUtils.isEmpty(jSONObject.optString("config"))) {
          paramString = str;
          JSONObject jSONObject1 = new JSONObject(jSONObject.optString("config"));
          paramString = str;
          this.mFirstTitleBarJson = jSONObject1.optJSONObject("navigationBar");
          paramString = str;
          this.mFirstTabBarJson = jSONObject1.optJSONObject("tabBar");
        } 
        paramString = str;
        this.mVersionCode = jSONObject.optLong("version_code");
        paramString = str;
      } catch (JSONException jSONException) {}
    } catch (JSONException jSONException2) {
      String str1 = "";
      JSONException jSONException1 = jSONException;
      jSONException = jSONException2;
    } 
    AppBrandLogger.d("RenderSnapShotManager", new Object[] { jSONException });
  }
  
  private boolean isEqualsCurrentVersion(AppInfoEntity paramAppInfoEntity) {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (ChannelUtil.isLocalTest() && BaseBundleDAO.isVdomNotCompareVersionCode((Context)application))
      return true; 
    if (paramAppInfoEntity != null) {
      if (paramAppInfoEntity.isLocalTest())
        return true; 
      RequestResultInfo requestResultInfo = ((MetaService)this.mApp.getService(MetaService.class)).tryFetchLocalMeta((Context)application, paramAppInfoEntity.appId, RequestType.normal);
      if (requestResultInfo != null)
        return (requestResultInfo.appInfo == null) ? true : ((requestResultInfo.appInfo.versionCode == this.mVersionCode)); 
    } 
    return true;
  }
  
  private void onLoadResult(String paramString1, String paramString2) {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("result", paramString1);
      jSONObject.put("errMsg", paramString2);
      if (this.mPage != null)
        this.mPage.getNativeNestWebView().onLoadApp(jSONObject.toString()); 
      return;
    } catch (Exception exception) {
      AppBrandLogger.d("RenderSnapShotManager", new Object[] { exception });
      return;
    } 
  }
  
  public void flushOnUIThread() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mRunnable : Ljava/lang/Runnable;
    //   6: ifnull -> 23
    //   9: aload_0
    //   10: getfield mRunnable : Ljava/lang/Runnable;
    //   13: invokeinterface run : ()V
    //   18: aload_0
    //   19: aconst_null
    //   20: putfield mRunnable : Ljava/lang/Runnable;
    //   23: aload_0
    //   24: monitorexit
    //   25: return
    //   26: astore_1
    //   27: aload_0
    //   28: monitorexit
    //   29: aload_1
    //   30: athrow
    // Exception table:
    //   from	to	target	type
    //   2	23	26	finally
  }
  
  public String getSnapShotErrorArgs() {
    return this.mErrorArgs;
  }
  
  public boolean isSnapShotReady() {
    return this.mSnapShotReady;
  }
  
  public boolean isSnapShotRender() {
    return this.mIsFirstRender;
  }
  
  public void onLoadResultFail(String paramString) {
    if (!this.mIsReportSuccessLoad) {
      if (!isSnapShotRender())
        return; 
      onLoadResult("fail", paramString);
    } 
  }
  
  public void onLoadResultSuccess() {
    if (!isSnapShotRender())
      return; 
    onLoadResult("success", "");
    this.mIsReportSuccessLoad = true;
  }
  
  public void postErrorToLoadingView(String paramString) {
    this.mErrorArgs = paramString;
  }
  
  public void preHandleVDomData(String paramString, AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   6: ifne -> 312
    //   9: aload_2
    //   10: ifnonnull -> 16
    //   13: goto -> 312
    //   16: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   19: aload_2
    //   20: invokevirtual setAppInfo : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   23: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   26: invokevirtual getTmaFeatureConfig : ()Lorg/json/JSONObject;
    //   29: astore #4
    //   31: aload #4
    //   33: ifnull -> 73
    //   36: aload #4
    //   38: ldc 'tma_vdom_test'
    //   40: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   43: astore #4
    //   45: aload #4
    //   47: ifnull -> 320
    //   50: aload #4
    //   52: ldc 'enable'
    //   54: invokevirtual optBoolean : (Ljava/lang/String;)Z
    //   57: ifeq -> 320
    //   60: iconst_1
    //   61: istore_3
    //   62: goto -> 65
    //   65: aload_0
    //   66: iload_3
    //   67: putfield mIsFirstRender : Z
    //   70: goto -> 78
    //   73: aload_0
    //   74: iconst_0
    //   75: putfield mIsFirstRender : Z
    //   78: aload_0
    //   79: getfield mIsFirstRender : Z
    //   82: istore_3
    //   83: iload_3
    //   84: ifne -> 90
    //   87: aload_0
    //   88: monitorexit
    //   89: return
    //   90: aload_0
    //   91: aload_0
    //   92: aload_1
    //   93: aload_2
    //   94: invokespecial initFirstRenderJSON : (Ljava/lang/String;Lcom/tt/miniapphost/entity/AppInfoEntity;)Z
    //   97: putfield mIsFirstRender : Z
    //   100: aload_0
    //   101: getfield mIsFirstRender : Z
    //   104: istore_3
    //   105: iload_3
    //   106: ifne -> 112
    //   109: aload_0
    //   110: monitorexit
    //   111: return
    //   112: invokestatic getInst : ()Lcom/tt/miniapphost/LaunchThreadPool;
    //   115: iconst_1
    //   116: invokevirtual setLowPriorityLaunch : (Z)V
    //   119: aload_0
    //   120: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   123: ldc com/tt/miniapp/util/TimeLogger
    //   125: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   128: checkcast com/tt/miniapp/util/TimeLogger
    //   131: iconst_1
    //   132: anewarray java/lang/String
    //   135: dup
    //   136: iconst_0
    //   137: ldc_w 'RenderSnapShotManager_startUnzipRenderStart'
    //   140: aastore
    //   141: invokevirtual logTimeDuration : ([Ljava/lang/String;)V
    //   144: new org/json/JSONObject
    //   147: dup
    //   148: aload_1
    //   149: invokespecial <init> : (Ljava/lang/String;)V
    //   152: astore_1
    //   153: aload_1
    //   154: ldc 'css'
    //   156: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   159: astore #4
    //   161: aload_1
    //   162: ldc 'vdom'
    //   164: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   167: astore_2
    //   168: aload_1
    //   169: ldc_w 'extra_data'
    //   172: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   175: astore_1
    //   176: aload #4
    //   178: invokestatic decodeBase64 : (Ljava/lang/String;)Lg/i;
    //   181: invokevirtual toByteArray : ()[B
    //   184: invokestatic decodeGzip : ([B)Ljava/lang/String;
    //   187: astore #4
    //   189: aload_2
    //   190: invokestatic decodeBase64 : (Ljava/lang/String;)Lg/i;
    //   193: invokevirtual toByteArray : ()[B
    //   196: invokestatic decodeGzip : ([B)Ljava/lang/String;
    //   199: astore_2
    //   200: new org/json/JSONObject
    //   203: dup
    //   204: invokespecial <init> : ()V
    //   207: astore #5
    //   209: aload #5
    //   211: ldc 'vdom'
    //   213: aload_2
    //   214: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   217: pop
    //   218: aload #5
    //   220: ldc 'css'
    //   222: aload #4
    //   224: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   227: pop
    //   228: aload #5
    //   230: ldc_w 'extra_data'
    //   233: aload_1
    //   234: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   237: pop
    //   238: aload_0
    //   239: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   242: ldc com/tt/miniapp/util/TimeLogger
    //   244: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   247: checkcast com/tt/miniapp/util/TimeLogger
    //   250: iconst_1
    //   251: anewarray java/lang/String
    //   254: dup
    //   255: iconst_0
    //   256: ldc_w 'RenderSnapShotManager_unzipRenderDone'
    //   259: aastore
    //   260: invokevirtual logTimeDuration : ([Ljava/lang/String;)V
    //   263: aload_0
    //   264: new com/tt/miniapp/util/RenderSnapShotManager$1
    //   267: dup
    //   268: aload_0
    //   269: aload #5
    //   271: invokespecial <init> : (Lcom/tt/miniapp/util/RenderSnapShotManager;Lorg/json/JSONObject;)V
    //   274: putfield mRunnable : Ljava/lang/Runnable;
    //   277: aload_0
    //   278: getfield mRunnable : Ljava/lang/Runnable;
    //   281: invokestatic runOnUIThread : (Ljava/lang/Runnable;)V
    //   284: aload_0
    //   285: monitorexit
    //   286: return
    //   287: astore_1
    //   288: aload_0
    //   289: aload_1
    //   290: invokevirtual getMessage : ()Ljava/lang/String;
    //   293: invokevirtual onLoadResultFail : (Ljava/lang/String;)V
    //   296: ldc 'RenderSnapShotManager'
    //   298: iconst_1
    //   299: anewarray java/lang/Object
    //   302: dup
    //   303: iconst_0
    //   304: aload_1
    //   305: aastore
    //   306: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   309: aload_0
    //   310: monitorexit
    //   311: return
    //   312: aload_0
    //   313: monitorexit
    //   314: return
    //   315: astore_1
    //   316: aload_0
    //   317: monitorexit
    //   318: aload_1
    //   319: athrow
    //   320: iconst_0
    //   321: istore_3
    //   322: goto -> 65
    // Exception table:
    //   from	to	target	type
    //   2	9	315	finally
    //   16	31	315	finally
    //   36	45	315	finally
    //   50	60	315	finally
    //   65	70	315	finally
    //   73	78	315	finally
    //   78	83	315	finally
    //   90	105	315	finally
    //   112	144	315	finally
    //   144	284	287	java/lang/Exception
    //   144	284	315	finally
    //   288	309	315	finally
  }
  
  public void ready() {
    this.mSnapShotReady = true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\RenderSnapShotManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */