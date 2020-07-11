package com.tt.miniapp;

import android.text.TextUtils;
import com.storage.async.Action;
import com.tt.miniapp.launchcache.meta.AppInfoHelper;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppConfig {
  private static final Map<String, List> sReplaceKeyPartWithExtMap = new HashMap<String, List>() {
    
    };
  
  public Global global;
  
  public boolean isBackToHome = false;
  
  public HashMap<String, String> loadpages = new HashMap<String, String>();
  
  private JSONObject mAppConfigJson;
  
  private AuthorizeDescription mAuthorizeDescription;
  
  public String mEntryPath;
  
  private JSONObject mExtConfigJson;
  
  private LaunchAppConfig mLaunchAppConfig;
  
  private NetworkTimeout mNetworkTimeout;
  
  private TabBar mTabBar;
  
  public Set<AppInfoEntity> naviToAppInfoList = new HashSet<AppInfoEntity>();
  
  public Set<String> naviToAppList = new HashSet<String>();
  
  public Page page;
  
  private ArrayList<String> pageList = new ArrayList<String>();
  
  public String screenOrientation;
  
  public static String cutHtmlSuffix(String paramString) {
    String str = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      str = paramString;
      if (paramString.endsWith(".html"))
        str = paramString.substring(0, paramString.length() - 5); 
    } 
    return str;
  }
  
  private static JSONObject getExtConfigJson() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity != null) {
      String str = appInfoEntity.mExtJson;
      if (!TextUtils.isEmpty(str))
        try {
          return new JSONObject(str);
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(6, "tma_AppConfig", jSONException.getStackTrace());
        }  
    } 
    return null;
  }
  
  private static JSONArray getJSONArrayFromConfig(JSONObject paramJSONObject1, JSONObject paramJSONObject2, String paramString) {
    if (paramJSONObject2 != null && paramJSONObject2.has(paramString)) {
      JSONArray jSONArray = paramJSONObject2.optJSONArray(paramString);
    } else {
      paramJSONObject2 = null;
    } 
    return (JSONArray)merge(paramJSONObject1.optJSONArray(paramString), paramJSONObject2, paramString, paramString);
  }
  
  private static JSONObject getJSONObjectFromConfig(JSONObject paramJSONObject1, JSONObject paramJSONObject2, String paramString) {
    return getJSONObjectFromConfig(paramJSONObject1, paramJSONObject2, paramString, paramString);
  }
  
  private static JSONObject getJSONObjectFromConfig(JSONObject paramJSONObject1, JSONObject paramJSONObject2, String paramString1, String paramString2) {
    if (paramJSONObject2 != null && paramJSONObject2.has(paramString2)) {
      paramJSONObject2 = paramJSONObject2.optJSONObject(paramString2);
    } else {
      paramJSONObject2 = null;
    } 
    return merge(paramJSONObject1.optJSONObject(paramString1), paramJSONObject2, paramString1, paramString2);
  }
  
  private static void getNaviToAppMeta(final AppConfig appConfig) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            Iterator<String> iterator = appConfig.naviToAppList.iterator();
            while (iterator.hasNext()) {
              AppInfoEntity appInfoEntity = AppInfoHelper.requestMeta(iterator.next());
              if (appInfoEntity != null)
                appConfig.naviToAppInfoList.add(appInfoEntity); 
            } 
          }
        },  ThreadPools.longIO());
  }
  
  public static String getPagePath(String paramString) {
    String str = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      int i = paramString.indexOf("?");
      str = paramString;
      if (i > 0)
        str = paramString.substring(0, i); 
    } 
    return cutHtmlSuffix(str);
  }
  
  private static String getStringFromConfig(JSONObject paramJSONObject1, JSONObject paramJSONObject2, String paramString) {
    if (paramJSONObject2 != null && paramJSONObject2.has(paramString)) {
      String str = paramJSONObject2.optString(paramString);
    } else {
      paramJSONObject2 = null;
    } 
    return (String)merge(paramJSONObject1.optString(paramString), paramJSONObject2, paramString, paramString);
  }
  
  private static <T> T merge(T paramT1, T paramT2, String paramString1, String paramString2) {
    JSONObject jSONObject;
    JSONArray jSONArray;
    if (paramT1 == null)
      return paramT2; 
    if (paramT2 == null)
      return paramT1; 
    boolean bool = paramT1 instanceof JSONObject;
    int i = 0;
    if (bool) {
      StringBuilder stringBuilder;
      if (!(paramT2 instanceof JSONObject)) {
        stringBuilder = new StringBuilder(" merge JSONObject 类型不匹配。 appConfigValue：");
        stringBuilder.append(paramT1);
        stringBuilder.append(" extConfigValue：");
        stringBuilder.append(paramT2);
        DebugUtil.outputError("tma_AppConfig", new Object[] { stringBuilder.toString() });
        return paramT2;
      } 
      JSONObject jSONObject1 = (JSONObject)paramT1;
      jSONObject = preHandleExtValueBeforeMerge(paramString2, (JSONObject)paramT2);
      List list = sReplaceKeyPartWithExtMap.get(stringBuilder);
      Iterator<String> iterator = jSONObject.keys();
      while (iterator.hasNext()) {
        String str = iterator.next();
        Object object = jSONObject.opt(str);
        if (list != null && list.contains(str)) {
          try {
            jSONObject1.put(str, object);
          } catch (JSONException jSONException) {
            AppBrandLogger.e("tma_AppConfig", new Object[] { "mergeJsonObject", jSONException });
          } 
          continue;
        } 
        try {
          jSONObject1.put((String)jSONException, merge(jSONObject1.opt((String)jSONException), object, null, null));
        } catch (JSONException jSONException1) {
          AppBrandLogger.e("tma_AppConfig", new Object[] { "mergeJsonObject", jSONException1 });
        } 
      } 
      return paramT1;
    } 
    if (paramT1 instanceof JSONArray) {
      if (!(jSONObject instanceof JSONArray)) {
        StringBuilder stringBuilder = new StringBuilder(" merge JSONArray 类型不匹配。 appConfigValue：");
        stringBuilder.append(paramT1);
        stringBuilder.append(" extConfigValue：");
        stringBuilder.append(jSONObject);
        DebugUtil.outputError("tma_AppConfig", new Object[] { stringBuilder.toString() });
        return (T)jSONObject;
      } 
      JSONArray jSONArray1 = (JSONArray)paramT1;
      jSONArray = (JSONArray)jSONObject;
      int j = jSONArray.length();
      while (i < j) {
        jSONArray1.put(jSONArray.opt(i));
        i++;
      } 
      return paramT1;
    } 
    return (T)jSONArray;
  }
  
  public static AppConfig parseAppConfig(String paramString) throws JSONException {
    JSONObject jSONObject1;
    AppBrandLogger.i("tma_AppConfig", new Object[] { "appJson = ", paramString });
    AppConfig appConfig = new AppConfig();
    JSONObject jSONObject3 = getExtConfigJson();
    AppBrandLogger.d("tma_AppConfig", new Object[] { "extConfigJson", jSONObject3 });
    appConfig.mExtConfigJson = jSONObject3;
    JSONObject jSONObject4 = new JSONObject(paramString);
    appConfig.mAppConfigJson = jSONObject4;
    appConfig.mEntryPath = getStringFromConfig(jSONObject4, jSONObject3, "entryPagePath");
    appConfig.mEntryPath = cutHtmlSuffix(appConfig.mEntryPath);
    AppBrandLogger.d("tma_AppConfig", new Object[] { "appConfig.mEntryPath = ", appConfig.mEntryPath });
    JSONArray jSONArray2 = getJSONArrayFromConfig(jSONObject4, jSONObject3, "pages");
    if (jSONArray2 != null) {
      int j = jSONArray2.length();
      for (int i = 0; i < j; i++)
        appConfig.pageList.add(jSONArray2.optString(i)); 
    } 
    JSONObject jSONObject2 = getJSONObjectFromConfig(jSONObject4, jSONObject3, "loadPage");
    if (jSONObject2 != null)
      for (String str : appConfig.pageList)
        appConfig.loadpages.put(str, jSONObject2.optString(str));  
    if (AppbrandApplicationImpl.getInst().getAppInfo().isGame()) {
      JSONArray jSONArray = jSONObject4.optJSONArray("ttNavigateToMiniGameAppIdList");
      GameModuleController.inst().onGameInstall(jSONArray);
    } 
    JSONArray jSONArray1 = jSONObject4.optJSONArray("navigateToMiniProgramAppIdList");
    if (jSONArray1 != null) {
      int j = jSONArray1.length();
      for (int i = 0; i < j; i++)
        appConfig.naviToAppList.add(jSONArray1.optString(i)); 
      getNaviToAppMeta(appConfig);
    } 
    JSONObject jSONObject5 = jSONObject4.optJSONObject("global");
    jSONArray1 = null;
    if (jSONObject5 == null) {
      if (jSONObject3 != null)
        jSONObject1 = jSONObject3.getJSONObject("window"); 
    } else {
      jSONObject1 = getJSONObjectFromConfig(jSONObject5, jSONObject3, "window");
    } 
    appConfig.global = Global.parseGlobal(jSONObject1);
    appConfig.page = Page.parsePage(getJSONObjectFromConfig(jSONObject4, jSONObject3, "page", "extPages"));
    appConfig.screenOrientation = getStringFromConfig(jSONObject4, jSONObject3, "deviceOrientation");
    if (TextUtils.isEmpty(appConfig.screenOrientation))
      appConfig.screenOrientation = "portrait"; 
    AppBrandLogger.d("tma_AppConfig", new Object[] { "appConfig", appConfig });
    return appConfig;
  }
  
  private static <T> T preHandleExtValueBeforeMerge(String paramString, T paramT) {
    JSONObject jSONObject;
    if (TextUtils.equals(paramString, "extPages") && paramT instanceof JSONObject) {
      JSONObject jSONObject1 = new JSONObject();
      jSONObject = (JSONObject)paramT;
      Iterator<String> iterator = jSONObject.keys();
      while (iterator.hasNext()) {
        String str = iterator.next();
        JSONObject jSONObject2 = new JSONObject();
        try {
          jSONObject2.put("window", jSONObject.opt(str));
          jSONObject1.put(str, jSONObject2);
        } catch (JSONException jSONException) {
          AppBrandLogger.d("tma_AppConfig", new Object[] { "preHandleExtValueBeforeMerge", jSONException });
        } 
      } 
      return (T)jSONObject1;
    } 
    return (T)jSONObject;
  }
  
  public AuthorizeDescription getAuthorizeDescription() {
    if (this.mAuthorizeDescription == null)
      this.mAuthorizeDescription = AuthorizeDescription.parseAuthorizeDescription(this.mAppConfigJson.optJSONObject("permission")); 
    return this.mAuthorizeDescription;
  }
  
  public LaunchAppConfig getLaunchAppConfig() {
    if (this.mLaunchAppConfig == null)
      this.mLaunchAppConfig = LaunchAppConfig.parseLaunchAppConfig(getJSONObjectFromConfig(this.mAppConfigJson, this.mExtConfigJson, "ttLaunchApp")); 
    return this.mLaunchAppConfig;
  }
  
  public Set<AppInfoEntity> getNaviToAppInfoList() {
    return this.naviToAppInfoList;
  }
  
  public Set<String> getNaviToAppList() {
    return this.naviToAppList;
  }
  
  public NetworkTimeout getNetworkTimeout() {
    if (this.mNetworkTimeout == null)
      this.mNetworkTimeout = NetworkTimeout.parseNetworkTimeout(this.mAppConfigJson.optJSONObject("networkTimeout")); 
    return this.mNetworkTimeout;
  }
  
  public ArrayList<String> getPageList() {
    return this.pageList;
  }
  
  public JSONObject getPrefetches() {
    return this.mAppConfigJson.optJSONObject("prefetches");
  }
  
  public TabBar getTabBar() {
    if (this.mTabBar == null)
      this.mTabBar = TabBar.parseTabBar(getJSONObjectFromConfig(this.mAppConfigJson, this.mExtConfigJson, "tabBar")); 
    return this.mTabBar;
  }
  
  public static class AuthorizeDescription {
    private String address;
    
    private String album;
    
    private String camera;
    
    private String record;
    
    private String userLocation;
    
    public static AuthorizeDescription parseAuthorizeDescription(JSONObject param1JSONObject) {
      AuthorizeDescription authorizeDescription = new AuthorizeDescription();
      if (param1JSONObject != null) {
        JSONObject jSONObject = param1JSONObject.optJSONObject("scope.userLocation");
        if (jSONObject != null)
          authorizeDescription.userLocation = jSONObject.optString("desc", null); 
        jSONObject = param1JSONObject.optJSONObject("scope.address");
        if (jSONObject != null)
          authorizeDescription.address = jSONObject.optString("desc", null); 
        jSONObject = param1JSONObject.optJSONObject("scope.record");
        if (jSONObject != null)
          authorizeDescription.record = jSONObject.optString("desc", null); 
        jSONObject = param1JSONObject.optJSONObject("scope.album");
        if (jSONObject != null)
          authorizeDescription.album = jSONObject.optString("desc", null); 
        param1JSONObject = param1JSONObject.optJSONObject("scope.camera");
        if (param1JSONObject != null)
          authorizeDescription.camera = param1JSONObject.optString("desc", null); 
      } 
      return authorizeDescription;
    }
    
    public String getAddress() {
      return this.address;
    }
    
    public String getAlbum() {
      return this.album;
    }
    
    public String getCamera() {
      return this.camera;
    }
    
    public String getRecord() {
      return this.record;
    }
    
    public String getUserLocation() {
      return this.userLocation;
    }
  }
  
  public static class Global {
    public AppConfig.Window window;
    
    public static Global parseGlobal(JSONObject param1JSONObject) {
      Global global = new Global();
      if (param1JSONObject == null)
        return global; 
      global.window = AppConfig.Window.parseWindow(param1JSONObject);
      return global;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{window: ");
      stringBuilder.append(this.window);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
  
  public static class LaunchAppConfig {
    public String appName;
    
    public String packageName;
    
    public static LaunchAppConfig parseLaunchAppConfig(JSONObject param1JSONObject) {
      LaunchAppConfig launchAppConfig = new LaunchAppConfig();
      if (param1JSONObject != null) {
        launchAppConfig.appName = param1JSONObject.optString("appName");
        launchAppConfig.packageName = param1JSONObject.optString("androidPackageName");
      } 
      return launchAppConfig;
    }
  }
  
  public static class NetworkTimeout {
    public long connectSocket = 60000L;
    
    public long downloadFile = 60000L;
    
    public long request = 60000L;
    
    public long uploadFile = 60000L;
    
    public static NetworkTimeout parseNetworkTimeout(JSONObject param1JSONObject) {
      NetworkTimeout networkTimeout = new NetworkTimeout();
      if (param1JSONObject != null) {
        long l1 = param1JSONObject.optLong("request");
        long l2 = param1JSONObject.optLong("uploadFile");
        long l3 = param1JSONObject.optLong("downloadFile");
        long l4 = param1JSONObject.optLong("connectSocket");
        if (l1 > 0L && l1 < 60000L)
          networkTimeout.request = l1; 
        if (l2 > 0L && l2 < 60000L)
          networkTimeout.uploadFile = l2; 
        if (l3 > 0L && l3 < 60000L)
          networkTimeout.downloadFile = l3; 
        if (l4 > 0L && l4 < 60000L)
          networkTimeout.connectSocket = l4; 
      } 
      return networkTimeout;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{request: ");
      stringBuilder.append(this.request);
      stringBuilder.append("uploadFile: ");
      stringBuilder.append(this.uploadFile);
      stringBuilder.append("downloadFile: ");
      stringBuilder.append(this.downloadFile);
      stringBuilder.append("connectSocket: ");
      stringBuilder.append(this.connectSocket);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
  
  public static class Page {
    private JSONObject mJsonObject;
    
    private Map<String, AppConfig.Window> pageConfig = new ConcurrentHashMap<String, AppConfig.Window>();
    
    private Page(JSONObject param1JSONObject) {
      this.mJsonObject = param1JSONObject;
    }
    
    public static Page parsePage(JSONObject param1JSONObject) {
      return new Page(param1JSONObject);
    }
    
    public AppConfig.Window getWindow(String param1String) {
      JSONObject jSONObject = this.mJsonObject;
      if (jSONObject == null)
        return null; 
      jSONObject = jSONObject.optJSONObject(param1String);
      if (jSONObject == null)
        return null; 
      AppConfig.Window window = AppConfig.Window.parseWindow(jSONObject.optJSONObject("window"));
      this.pageConfig.put(AppConfig.cutHtmlSuffix(param1String), window);
      return window;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{pageConfig: ");
      stringBuilder.append(this.pageConfig);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
  
  public static class TabBar {
    public String backgroundColor;
    
    public String borderStyle;
    
    public String color;
    
    public String selectedColor;
    
    public ArrayList<TabContent> tabs;
    
    public static TabBar parseTabBar(JSONObject param1JSONObject) {
      TabBar tabBar = new TabBar();
      if (param1JSONObject != null) {
        tabBar.color = UIUtils.rgbaToFullARGBStr(param1JSONObject.optString("color"), "#222222");
        tabBar.selectedColor = UIUtils.rgbaToFullARGBStr(param1JSONObject.optString("selectedColor"), "#F85959");
        tabBar.borderStyle = param1JSONObject.optString("borderStyle");
        tabBar.backgroundColor = UIUtils.rgbaToFullARGBStr(param1JSONObject.optString("backgroundColor"), "#ffffff");
        JSONArray jSONArray = param1JSONObject.optJSONArray("list");
        if (jSONArray != null) {
          tabBar.tabs = new ArrayList<TabContent>();
          int i = 0;
          int j = jSONArray.length();
          while (i < j) {
            JSONObject jSONObject = jSONArray.optJSONObject(i);
            if (jSONObject != null) {
              TabContent tabContent = new TabContent();
              tabBar.tabs.add(tabContent);
              tabContent.pagePath = jSONObject.optString("pagePath");
              tabContent.pagePath = AppConfig.cutHtmlSuffix(tabContent.pagePath);
              tabContent.iconPath = jSONObject.optString("iconPath");
              tabContent.selectedIconPath = jSONObject.optString("selectedIconPath");
              tabContent.text = jSONObject.optString("text");
            } 
            i++;
          } 
        } 
      } 
      return tabBar;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{color: ");
      stringBuilder.append(this.color);
      stringBuilder.append(", selectedColor: ");
      stringBuilder.append(this.selectedColor);
      stringBuilder.append(", borderStyle: ");
      stringBuilder.append(this.borderStyle);
      stringBuilder.append(", backgroundColor: ");
      stringBuilder.append(this.backgroundColor);
      stringBuilder.append(", tabs: ");
      stringBuilder.append(this.tabs);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
    
    public static class TabContent {
      public String iconPath;
      
      public String pagePath;
      
      public String selectedIconPath;
      
      public String text;
      
      public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{pagePath: ");
        stringBuilder.append(this.pagePath);
        stringBuilder.append(", iconPath: ");
        stringBuilder.append(this.iconPath);
        stringBuilder.append(", selectedIconPath: ");
        stringBuilder.append(this.selectedIconPath);
        stringBuilder.append(", text: ");
        stringBuilder.append(this.text);
        stringBuilder.append("}");
        return stringBuilder.toString();
      }
    }
  }
  
  public static class TabContent {
    public String iconPath;
    
    public String pagePath;
    
    public String selectedIconPath;
    
    public String text;
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{pagePath: ");
      stringBuilder.append(this.pagePath);
      stringBuilder.append(", iconPath: ");
      stringBuilder.append(this.iconPath);
      stringBuilder.append(", selectedIconPath: ");
      stringBuilder.append(this.selectedIconPath);
      stringBuilder.append(", text: ");
      stringBuilder.append(this.text);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
  
  public static class Window {
    public String backgroundColor;
    
    public String backgroundTextStyle;
    
    public boolean disableScroll;
    
    public boolean disableSwipeBack;
    
    public boolean enablePullDownRefresh;
    
    public HashMap<String, Window> extend = new HashMap<String, Window>();
    
    public boolean hasBackgroundColor;
    
    public boolean hasBackgroundTextStyle;
    
    public boolean hasDisableScroll;
    
    public boolean hasDisableSwipeBack;
    
    public boolean hasEnablePullDownRefresh;
    
    public boolean hasExtend;
    
    public boolean hasNavigationBarBackgroundColor;
    
    public boolean hasNavigationBarTextStyle;
    
    public boolean hasNavigationBarTitleText;
    
    public boolean hasNavigationStyle;
    
    public boolean hasTransparentTitle;
    
    public String navigationBarBackgroundColor;
    
    public String navigationBarTextStyle;
    
    public String navigationBarTitleText;
    
    public String navigationStyle;
    
    public String transparentTitle;
    
    private static void coverWindow(String param1String, Window param1Window) {
      StringBuilder stringBuilder;
      if (param1String == null)
        return; 
      Window window = param1Window.extend.get(param1String);
      if (window == null) {
        stringBuilder = new StringBuilder("小程序无");
        stringBuilder.append(param1String);
        stringBuilder.append("的window配置");
        AppBrandLogger.e("tma_AppConfig", new Object[] { stringBuilder.toString() });
        return;
      } 
      if (window.hasNavigationBarBackgroundColor) {
        ((Window)stringBuilder).navigationBarBackgroundColor = UIUtils.rgbaToFullARGBStr(window.navigationBarBackgroundColor, "#000000");
        ((Window)stringBuilder).hasNavigationBarBackgroundColor = true;
      } 
      if (window.hasNavigationBarTextStyle) {
        ((Window)stringBuilder).navigationBarTextStyle = window.navigationBarTextStyle;
        ((Window)stringBuilder).hasNavigationBarTextStyle = true;
      } 
      if (window.hasNavigationBarTitleText) {
        ((Window)stringBuilder).navigationBarTitleText = window.navigationBarTitleText;
        ((Window)stringBuilder).hasNavigationBarTitleText = true;
      } 
      if (window.hasEnablePullDownRefresh) {
        ((Window)stringBuilder).enablePullDownRefresh = window.enablePullDownRefresh;
        ((Window)stringBuilder).hasEnablePullDownRefresh = true;
      } 
      if (window.hasBackgroundColor) {
        ((Window)stringBuilder).backgroundColor = UIUtils.rgbaToFullARGBStr(window.backgroundColor, "#ffffff");
        ((Window)stringBuilder).hasBackgroundColor = true;
      } 
      if (window.hasBackgroundTextStyle) {
        ((Window)stringBuilder).backgroundTextStyle = window.backgroundTextStyle;
        ((Window)stringBuilder).hasBackgroundTextStyle = true;
      } 
      if (window.hasNavigationStyle) {
        ((Window)stringBuilder).navigationStyle = window.navigationStyle;
        ((Window)stringBuilder).hasNavigationStyle = true;
      } 
      if (window.hasDisableScroll) {
        ((Window)stringBuilder).disableScroll = window.disableScroll;
        ((Window)stringBuilder).hasDisableScroll = true;
      } 
      if (window.hasDisableSwipeBack) {
        ((Window)stringBuilder).disableSwipeBack = window.disableSwipeBack;
        ((Window)stringBuilder).hasDisableSwipeBack = true;
      } 
      if (window.hasTransparentTitle) {
        ((Window)stringBuilder).transparentTitle = window.transparentTitle;
        ((Window)stringBuilder).hasTransparentTitle = true;
      } 
    }
    
    public static Window parseWindow(JSONObject param1JSONObject) {
      if (param1JSONObject == null)
        return null; 
      Window window = new Window();
      window.hasNavigationBarBackgroundColor = param1JSONObject.has("navigationBarBackgroundColor");
      if (window.hasNavigationBarBackgroundColor) {
        String str2 = UIUtils.rgbaToFullARGBStr(param1JSONObject.optString("navigationBarBackgroundColor"), "#000000");
        String str1 = str2;
        if (str2.length() == 9) {
          StringBuilder stringBuilder = new StringBuilder("#");
          stringBuilder.append(str2.substring(3));
          str1 = stringBuilder.toString();
        } 
        window.navigationBarBackgroundColor = str1;
      } 
      window.hasNavigationBarTextStyle = param1JSONObject.has("navigationBarTextStyle");
      if (window.hasNavigationBarTextStyle)
        window.navigationBarTextStyle = param1JSONObject.optString("navigationBarTextStyle"); 
      if (TextUtils.isEmpty(window.navigationBarTextStyle))
        window.navigationBarTextStyle = "white"; 
      window.hasNavigationBarTitleText = param1JSONObject.has("navigationBarTitleText");
      if (window.hasNavigationBarTitleText)
        window.navigationBarTitleText = param1JSONObject.optString("navigationBarTitleText"); 
      window.hasEnablePullDownRefresh = param1JSONObject.has("enablePullDownRefresh");
      if (window.hasEnablePullDownRefresh)
        window.enablePullDownRefresh = param1JSONObject.optBoolean("enablePullDownRefresh"); 
      window.hasBackgroundColor = param1JSONObject.has("backgroundColor");
      if (window.hasBackgroundColor)
        window.backgroundColor = UIUtils.rgbaToFullARGBStr(param1JSONObject.optString("backgroundColor"), "#ffffff"); 
      window.hasBackgroundTextStyle = param1JSONObject.has("backgroundTextStyle");
      if (window.hasBackgroundTextStyle)
        window.backgroundTextStyle = param1JSONObject.optString("backgroundTextStyle"); 
      window.hasNavigationStyle = param1JSONObject.has("navigationStyle");
      if (window.hasNavigationStyle) {
        window.navigationStyle = param1JSONObject.optString("navigationStyle");
      } else {
        window.navigationStyle = "default";
      } 
      window.hasDisableScroll = param1JSONObject.has("disableScroll");
      if (window.hasDisableScroll)
        window.disableScroll = param1JSONObject.optBoolean("disableScroll"); 
      window.hasDisableSwipeBack = param1JSONObject.has("disableSwipeBack");
      if (window.hasDisableSwipeBack)
        window.disableSwipeBack = param1JSONObject.optBoolean("disableSwipeBack"); 
      window.hasExtend = param1JSONObject.has("extend");
      if (window.hasExtend) {
        JSONObject jSONObject = param1JSONObject.optJSONObject("extend");
        Iterator<String> iterator = jSONObject.keys();
        while (iterator.hasNext()) {
          String str = iterator.next();
          window.extend.put(str, parseWindow(jSONObject.optJSONObject(str)));
        } 
      } 
      window.hasTransparentTitle = param1JSONObject.has("transparentTitle");
      if (window.hasTransparentTitle) {
        window.transparentTitle = param1JSONObject.optString("transparentTitle");
      } else {
        window.transparentTitle = "none";
      } 
      coverWindow(AppbrandContext.getInst().getInitParams().getAppName(), window);
      return window;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("{navigationBarBackgroundColor: ");
      stringBuilder.append(this.navigationBarBackgroundColor);
      stringBuilder.append(", navigationBarTextStyle: ");
      stringBuilder.append(this.navigationBarTextStyle);
      stringBuilder.append(", navigationBarTitleText: ");
      stringBuilder.append(this.navigationBarTitleText);
      stringBuilder.append(", enablePullDownRefresh: ");
      stringBuilder.append(this.enablePullDownRefresh);
      stringBuilder.append(", backgroundColor: ");
      stringBuilder.append(this.backgroundColor);
      stringBuilder.append(", backgroundTextStyle: ");
      stringBuilder.append(this.backgroundTextStyle);
      stringBuilder.append(", navigationStyle: ");
      stringBuilder.append(this.navigationStyle);
      stringBuilder.append(", disableScroll: ");
      stringBuilder.append(this.disableScroll);
      stringBuilder.append(", disableSwipeBack: ");
      stringBuilder.append(this.disableSwipeBack);
      stringBuilder.append(", transparentTitle: ");
      stringBuilder.append(this.transparentTitle);
      stringBuilder.append(", extend: {");
      stringBuilder = new StringBuilder(stringBuilder.toString());
      for (String str : this.extend.keySet()) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append(": ");
        stringBuilder1.append(((Window)this.extend.get(str)).toString());
        stringBuilder.append(stringBuilder1.toString());
      } 
      stringBuilder.append("}");
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\AppConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */