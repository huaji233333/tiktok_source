package com.tt.miniapp.route;

import android.text.TextUtils;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.navigate2.Nav2Util;
import com.tt.miniapp.util.JsCoreUtils;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.JsonBuilder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class RouteEventCtrl implements IRouteEvent {
  private List<IRouteEventHandler> mDelayMessageList = new ArrayList<IRouteEventHandler>();
  
  private boolean mInit;
  
  private final JSONObject mLaunchOption = new JSONObject();
  
  public JSONObject getLaunchOption() {
    synchronized (this.mLaunchOption) {
      return (new JsonBuilder(this.mLaunchOption.toString())).build();
    } 
  }
  
  public void onAppHide() {
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return; 
    if (this.mInit) {
      JsCoreUtils.sendAppEnterBackground();
      return;
    } 
    this.mDelayMessageList.add(new OnAppHideEvent());
  }
  
  public void onAppLaunch() {
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return; 
    if (this.mInit) {
      JsCoreUtils.sendAppLaunch();
      return;
    } 
    this.mDelayMessageList.add(new OnAppLaunchEvent());
  }
  
  public void onAppRoute(IRouteEvent.RouteEventBean paramRouteEventBean) {
    if (paramRouteEventBean == null)
      return; 
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return; 
    if (this.mInit) {
      JsCoreUtils.sendAppRoute(paramRouteEventBean.getWebViewId(), paramRouteEventBean.getPath(), paramRouteEventBean.getQueryStr(), paramRouteEventBean.getOpenType());
      return;
    } 
    this.mDelayMessageList.add(new OnAppRouteEvent(paramRouteEventBean));
  }
  
  public void onAppShow() {
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return; 
    if (this.mInit) {
      JsCoreUtils.sendAppEnterForeground();
      return;
    } 
    this.mDelayMessageList.add(new OnAppShowEvent());
  }
  
  public void onJsCoreReady() {
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return; 
    if (!this.mInit) {
      this.mInit = true;
      if (!this.mDelayMessageList.isEmpty()) {
        for (IRouteEventHandler iRouteEventHandler : this.mDelayMessageList) {
          if (iRouteEventHandler != null) {
            iRouteEventHandler.act();
            StringBuilder stringBuilder = new StringBuilder("post delay message");
            stringBuilder.append(iRouteEventHandler.getName());
            AppBrandLogger.d("RouteEventCtrl", new Object[] { stringBuilder.toString() });
          } 
        } 
        this.mDelayMessageList.clear();
      } 
    } 
  }
  
  public void setLaunchOption(AppInfoEntity paramAppInfoEntity, AppConfig paramAppConfig) {
    // Byte code:
    //   0: aload_2
    //   1: ifnull -> 304
    //   4: aload_1
    //   5: ifnonnull -> 9
    //   8: return
    //   9: invokestatic getInstance : ()Lcom/tt/miniapp/adsite/AdSiteManager;
    //   12: invokevirtual isAdSiteBrowser : ()Z
    //   15: ifeq -> 19
    //   18: return
    //   19: ldc ''
    //   21: astore_3
    //   22: ldc ''
    //   24: astore #4
    //   26: aload_1
    //   27: invokevirtual isGame : ()Z
    //   30: ifne -> 94
    //   33: aload_1
    //   34: getfield oriStartPage : Ljava/lang/String;
    //   37: astore #5
    //   39: aload #5
    //   41: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   44: ifne -> 58
    //   47: aload #5
    //   49: astore_3
    //   50: aload #5
    //   52: invokestatic isPageValidate : (Ljava/lang/String;)Z
    //   55: ifne -> 63
    //   58: aload_2
    //   59: getfield mEntryPath : Ljava/lang/String;
    //   62: astore_3
    //   63: aload_3
    //   64: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   67: ifne -> 315
    //   70: aload_3
    //   71: ldc '\?'
    //   73: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   76: astore_3
    //   77: aload_3
    //   78: arraylength
    //   79: iconst_1
    //   80: if_icmple -> 305
    //   83: aload_3
    //   84: iconst_0
    //   85: aaload
    //   86: astore_2
    //   87: aload_3
    //   88: iconst_1
    //   89: aaload
    //   90: astore_3
    //   91: goto -> 118
    //   94: aload_1
    //   95: getfield query : Ljava/lang/String;
    //   98: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   101: ifne -> 324
    //   104: aload_1
    //   105: getfield query : Ljava/lang/String;
    //   108: astore #4
    //   110: aload_3
    //   111: astore_2
    //   112: aload #4
    //   114: astore_3
    //   115: goto -> 118
    //   118: aload_0
    //   119: getfield mLaunchOption : Lorg/json/JSONObject;
    //   122: astore #4
    //   124: aload #4
    //   126: monitorenter
    //   127: aload_0
    //   128: getfield mLaunchOption : Lorg/json/JSONObject;
    //   131: ldc 'path'
    //   133: aload_2
    //   134: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   137: pop
    //   138: aload_0
    //   139: getfield mLaunchOption : Lorg/json/JSONObject;
    //   142: ldc 'query'
    //   144: aload_3
    //   145: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   148: pop
    //   149: aload_0
    //   150: getfield mLaunchOption : Lorg/json/JSONObject;
    //   153: ldc 'scene'
    //   155: aload_1
    //   156: getfield scene : Ljava/lang/String;
    //   159: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   162: pop
    //   163: aload_0
    //   164: getfield mLaunchOption : Lorg/json/JSONObject;
    //   167: ldc 'subScene'
    //   169: aload_1
    //   170: getfield subScene : Ljava/lang/String;
    //   173: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   176: pop
    //   177: aload_0
    //   178: getfield mLaunchOption : Lorg/json/JSONObject;
    //   181: ldc 'shareTicket'
    //   183: aload_1
    //   184: getfield shareTicket : Ljava/lang/String;
    //   187: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   190: pop
    //   191: aload_0
    //   192: getfield mLaunchOption : Lorg/json/JSONObject;
    //   195: ldc 'group_id'
    //   197: invokestatic getIns : ()Lcom/tt/miniapp/game/DiversionTool;
    //   200: invokevirtual getGroupId : ()Ljava/lang/String;
    //   203: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   206: pop
    //   207: aload_0
    //   208: aload_1
    //   209: invokevirtual updateLaunchOption : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   212: aload #4
    //   214: monitorexit
    //   215: new java/lang/StringBuilder
    //   218: dup
    //   219: ldc 'path:'
    //   221: invokespecial <init> : (Ljava/lang/String;)V
    //   224: astore #4
    //   226: aload #4
    //   228: aload_2
    //   229: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: pop
    //   233: aload #4
    //   235: ldc 'query:'
    //   237: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: pop
    //   241: aload #4
    //   243: aload_3
    //   244: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload #4
    //   250: ldc 'scene:'
    //   252: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   255: pop
    //   256: aload #4
    //   258: aload_1
    //   259: getfield scene : Ljava/lang/String;
    //   262: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   265: pop
    //   266: ldc 'RouteEventCtrl'
    //   268: iconst_1
    //   269: anewarray java/lang/Object
    //   272: dup
    //   273: iconst_0
    //   274: aload #4
    //   276: invokevirtual toString : ()Ljava/lang/String;
    //   279: aastore
    //   280: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   283: return
    //   284: astore_1
    //   285: aload #4
    //   287: monitorexit
    //   288: aload_1
    //   289: athrow
    //   290: astore_1
    //   291: ldc 'RouteEventCtrl'
    //   293: iconst_1
    //   294: anewarray java/lang/Object
    //   297: dup
    //   298: iconst_0
    //   299: aload_1
    //   300: aastore
    //   301: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   304: return
    //   305: aload_3
    //   306: iconst_0
    //   307: aaload
    //   308: astore_2
    //   309: aload #4
    //   311: astore_3
    //   312: goto -> 118
    //   315: ldc ''
    //   317: astore_2
    //   318: aload #4
    //   320: astore_3
    //   321: goto -> 118
    //   324: ldc ''
    //   326: astore #4
    //   328: aload_3
    //   329: astore_2
    //   330: aload #4
    //   332: astore_3
    //   333: goto -> 118
    // Exception table:
    //   from	to	target	type
    //   26	47	290	java/lang/Exception
    //   50	58	290	java/lang/Exception
    //   58	63	290	java/lang/Exception
    //   63	83	290	java/lang/Exception
    //   94	110	290	java/lang/Exception
    //   118	127	290	java/lang/Exception
    //   127	215	284	finally
    //   215	283	290	java/lang/Exception
    //   285	288	284	finally
    //   288	290	290	java/lang/Exception
  }
  
  public boolean shouldReLaunch(String paramString1, String paramString2) {
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return false; 
    String str = paramString1;
    if (!PageUtil.isPageValidate(paramString1)) {
      AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
      if (appConfig == null)
        return false; 
      str = appConfig.mEntryPath;
    } 
    return !(TextUtils.equals(PageUtil.getCleanPath(str), PageUtil.getCleanPath(paramString2)) && PageUtil.equalQuery(str, paramString2));
  }
  
  public void updateLaunchOption(AppInfoEntity paramAppInfoEntity) {
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return; 
    if (paramAppInfoEntity == null)
      return; 
    synchronized (this.mLaunchOption) {
      (new JsonBuilder(this.mLaunchOption)).put("refererInfo", Nav2Util.getReferer(paramAppInfoEntity)).build();
      StringBuilder stringBuilder = new StringBuilder("updateLaunchOption: ");
      stringBuilder.append(this.mLaunchOption);
      AppBrandLogger.d("RouteEventCtrl", new Object[] { stringBuilder.toString() });
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\RouteEventCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */