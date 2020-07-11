package com.tt.miniapp.settings.keys;

public enum Settings {
  BDP_ANTI_ADDICTION("enable"),
  BDP_CLOSE_AUTO_SHARE("enable"),
  BDP_CODECACHE_CONFIG("enable"),
  BDP_FAVORITES("enable"),
  BDP_FEEDBACK_REPORT("enable"),
  BDP_GAME_RECORD_MARK("enable"),
  BDP_HELIUM_CONFIG("enable"),
  BDP_JSSDK_ROLLBACK("enable"),
  BDP_LAUNCH_APP_SCENE_LIST("enable"),
  BDP_LAUNCH_LOADING_CONFIG("enable"),
  BDP_META_CONFIG("enable"),
  BDP_MORE_GAME_CENTER("enable"),
  BDP_OFFLINE_ZIP("enable"),
  BDP_REENTER_TIPS("enable"),
  BDP_SHOW_LOADING_BG("enable"),
  BDP_SOCKET_CTRL("enable"),
  BDP_STARTPAGE_PREFETCH("enable"),
  BDP_SWITCH("enable"),
  BDP_TTPKG_CONFIG("enable"),
  BDP_TTREQUEST_CONFIG("enable"),
  TMA_REGION_CONFIG("enable"),
  TMA_SDK_CONFIG("tt_tma_sdk_config"),
  TMA_VDOM_TEST("tt_tma_sdk_config"),
  TT_TIMELINE_SWITCH("tt_tma_sdk_config"),
  TT_TMA_ABTEST("tt_tma_sdk_config"),
  TT_TMA_BLACKLIST("tt_tma_sdk_config"),
  TT_TMA_HEADER_UNITE("tt_tma_sdk_config"),
  TT_TMA_NATIVE_UI("tt_tma_sdk_config"),
  TT_TMA_PROXY_LIST("tt_tma_sdk_config"),
  TT_TMA_SWITCH("tt_tma_sdk_config");
  
  private String name;
  
  static {
    TMA_REGION_CONFIG = new Settings("TMA_REGION_CONFIG", 1, "tt_tma_region_config");
    BDP_META_CONFIG = new Settings("BDP_META_CONFIG", 2, "bdp_meta_config");
    BDP_REENTER_TIPS = new Settings("BDP_REENTER_TIPS", 3, "bdp_reenter_tips");
    TT_TMA_SWITCH = new Settings("TT_TMA_SWITCH", 4, "tt_tma_switch");
    BDP_FAVORITES = new Settings("BDP_FAVORITES", 5, "bdp_favorites");
    TT_TMA_BLACKLIST = new Settings("TT_TMA_BLACKLIST", 6, "tt_tma_blacklist");
    BDP_OFFLINE_ZIP = new Settings("BDP_OFFLINE_ZIP", 7, "bdp_offline_zip");
    TT_TMA_ABTEST = new Settings("TT_TMA_ABTEST", 8, "tt_tma_abtest");
    BDP_TTPKG_CONFIG = new Settings("BDP_TTPKG_CONFIG", 9, "bdp_ttpkg_config");
    BDP_MORE_GAME_CENTER = new Settings("BDP_MORE_GAME_CENTER", 10, "bdp_more_game_center");
    BDP_LAUNCH_APP_SCENE_LIST = new Settings("BDP_LAUNCH_APP_SCENE_LIST", 11, "bdp_launch_app_scene_list");
    BDP_TTREQUEST_CONFIG = new Settings("BDP_TTREQUEST_CONFIG", 12, "bdp_ttrequest_config");
    BDP_STARTPAGE_PREFETCH = new Settings("BDP_STARTPAGE_PREFETCH", 13, "bdp_startpage_prefetch");
    BDP_HELIUM_CONFIG = new Settings("BDP_HELIUM_CONFIG", 14, "bdp_helium_config");
    TT_TMA_NATIVE_UI = new Settings("TT_TMA_NATIVE_UI", 15, "tma_native_ui_test");
    BDP_SOCKET_CTRL = new Settings("BDP_SOCKET_CTRL", 16, "bdp_socket_ctrl");
    BDP_SHOW_LOADING_BG = new Settings("BDP_SHOW_LOADING_BG", 17, "bdp_show_loading_bg");
    TMA_VDOM_TEST = new Settings("TMA_VDOM_TEST", 18, "tma_vdom_test");
    BDP_ANTI_ADDICTION = new Settings("BDP_ANTI_ADDICTION", 19, "bdp_anti_addiction");
    BDP_FEEDBACK_REPORT = new Settings("BDP_FEEDBACK_REPORT", 20, "bdp_feedback_report");
    BDP_JSSDK_ROLLBACK = new Settings("BDP_JSSDK_ROLLBACK", 21, "bdp_jssdk_rollback");
    BDP_CODECACHE_CONFIG = new Settings("BDP_CODECACHE_CONFIG", 22, "bdp_codecache_config");
    BDP_GAME_RECORD_MARK = new Settings("BDP_GAME_RECORD_MARK", 23, "bdp_game_record_mark");
    BDP_CLOSE_AUTO_SHARE = new Settings("BDP_CLOSE_AUTO_SHARE", 24, "bdp_close_auto_share");
    TT_TIMELINE_SWITCH = new Settings("TT_TIMELINE_SWITCH", 25, "tt_timeline_switch");
    TT_TMA_HEADER_UNITE = new Settings("TT_TMA_HEADER_UNITE", 26, "tt_tma_header_unite");
    BDP_SWITCH = new Settings("BDP_SWITCH", 27, "bdp_switch");
    BDP_LAUNCH_LOADING_CONFIG = new Settings("BDP_LAUNCH_LOADING_CONFIG", 28, "bdp_launch_loading_config");
    TT_TMA_PROXY_LIST = new Settings("TT_TMA_PROXY_LIST", 29, "tt_tma_proxy_list");
    $VALUES = new Settings[] { 
        TMA_SDK_CONFIG, TMA_REGION_CONFIG, BDP_META_CONFIG, BDP_REENTER_TIPS, TT_TMA_SWITCH, BDP_FAVORITES, TT_TMA_BLACKLIST, BDP_OFFLINE_ZIP, TT_TMA_ABTEST, BDP_TTPKG_CONFIG, 
        BDP_MORE_GAME_CENTER, BDP_LAUNCH_APP_SCENE_LIST, BDP_TTREQUEST_CONFIG, BDP_STARTPAGE_PREFETCH, BDP_HELIUM_CONFIG, TT_TMA_NATIVE_UI, BDP_SOCKET_CTRL, BDP_SHOW_LOADING_BG, TMA_VDOM_TEST, BDP_ANTI_ADDICTION, 
        BDP_FEEDBACK_REPORT, BDP_JSSDK_ROLLBACK, BDP_CODECACHE_CONFIG, BDP_GAME_RECORD_MARK, BDP_CLOSE_AUTO_SHARE, TT_TIMELINE_SWITCH, TT_TMA_HEADER_UNITE, BDP_SWITCH, BDP_LAUNCH_LOADING_CONFIG, TT_TMA_PROXY_LIST };
  }
  
  Settings(String paramString1) {
    this.name = paramString1;
  }
  
  public final String toString() {
    return this.name;
  }
  
  public enum BdpCloseAutoShare {
    APPID_WHITELIST,
    MAX_CLICK_INTERVAL("max_click_interval");
    
    private String name;
    
    static {
      $VALUES = new BdpCloseAutoShare[] { MAX_CLICK_INTERVAL, APPID_WHITELIST };
    }
    
    BdpCloseAutoShare(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpJssdkRollback {
    ERROR_VERSION("error_versions");
    
    private String name;
    
    static {
    
    }
    
    BdpJssdkRollback(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpLaunchSceneList {
    GRAY_LIST("error_versions"),
    WHITE_LIST("white_list");
    
    private String name;
    
    static {
      $VALUES = new BdpLaunchSceneList[] { WHITE_LIST, GRAY_LIST };
    }
    
    BdpLaunchSceneList(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpMetaConfig {
    MAIN_PROCESS_PREFETCH_KEY("main_process_prefetch"),
    URLS("urls");
    
    private String name;
    
    static {
    
    }
    
    BdpMetaConfig(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpReenterTips {
    TMA("tma"),
    TMG("tmg");
    
    private String name;
    
    static {
    
    }
    
    BdpReenterTips(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpStartpagePrefetchConfig {
    ENABLE("enable"),
    MAX_CONCURRENT_COUNT("max_concurrent_count");
    
    private String name;
    
    static {
    
    }
    
    BdpStartpagePrefetchConfig(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpSwitch {
    DISABLE_TMA("disableTma"),
    DISABLE_TMG("disableTmg");
    
    private String name;
    
    static {
    
    }
    
    BdpSwitch(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpTtPkgConfig {
    PRELOAD_MODE("preload_mode"),
    PRELOAD_PKG_LIMIT("preload_mode"),
    PRELOAD_REAL_CONTENT_LENGTH("preload_mode"),
    BR_DOWNLOAD_TYPES_KEY("disableTmg"),
    HOSTS_ADD_GZIP("disableTmg"),
    NORMAL_LAUNCH_PKG_LIMIT("disableTmg"),
    PKG_COMPRESS_DOWNGRADE("disableTmg");
    
    private String name;
    
    static {
      PKG_COMPRESS_DOWNGRADE = new BdpTtPkgConfig("PKG_COMPRESS_DOWNGRADE", 3, "compress_downgrade");
      BR_DOWNLOAD_TYPES_KEY = new BdpTtPkgConfig("BR_DOWNLOAD_TYPES_KEY", 4, "br_download_types");
      PRELOAD_PKG_LIMIT = new BdpTtPkgConfig("PRELOAD_PKG_LIMIT", 5, "predownload_pkg_limit");
      NORMAL_LAUNCH_PKG_LIMIT = new BdpTtPkgConfig("NORMAL_LAUNCH_PKG_LIMIT", 6, "normal_launch_pkg_limit");
      $VALUES = new BdpTtPkgConfig[] { PRELOAD_MODE, HOSTS_ADD_GZIP, PRELOAD_REAL_CONTENT_LENGTH, PKG_COMPRESS_DOWNGRADE, BR_DOWNLOAD_TYPES_KEY, PRELOAD_PKG_LIMIT, NORMAL_LAUNCH_PKG_LIMIT };
    }
    
    BdpTtPkgConfig(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum BdpTtRequestConfig {
    MP_IDS("preload_mode"),
    REQUEST_TYPE("request_type");
    
    private String name;
    
    static {
      $VALUES = new BdpTtRequestConfig[] { REQUEST_TYPE, MP_IDS };
    }
    
    BdpTtRequestConfig(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum LaunchLoadingConfig {
    HOST_TIP_ICON("host_tip_icon");
    
    private String name;
    
    static {
    
    }
    
    LaunchLoadingConfig(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TimeLineSwitch {
    SWITCH("switch"),
    URL("url");
    
    private String name;
    
    static {
    
    }
    
    TimeLineSwitch(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaABTest {
    AUTHORIZE_LIST("authorize_list");
    
    private String name;
    
    static {
    
    }
    
    TmaABTest(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
    
    public enum AuthorizeList {
      ON("on"),
      DID("url"),
      MP_ID("url");
      
      private String name;
      
      static {
        $VALUES = new AuthorizeList[] { ON, MP_ID, DID };
      }
      
      AuthorizeList(String param2String1) {
        this.name = param2String1;
      }
      
      public final String toString() {
        return this.name;
      }
    }
  }
  
  public enum AuthorizeList {
    DID("authorize_list"),
    MP_ID("authorize_list"),
    ON("on");
    
    private String name;
    
    static {
      DID = new AuthorizeList("DID", 2, "did");
      $VALUES = new AuthorizeList[] { ON, MP_ID, DID };
    }
    
    AuthorizeList(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaBlackList {
    DEVICE("device");
    
    private String name;
    
    static {
    
    }
    
    TmaBlackList(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
    
    public enum DeviceBlackList {
      TMA("tma"),
      TMG("tmg");
      
      private String name;
      
      static {
      
      }
      
      DeviceBlackList(String param2String1) {
        this.name = param2String1;
      }
      
      public final String toString() {
        return this.name;
      }
    }
  }
  
  public enum DeviceBlackList {
    TMA("tma"),
    TMG("tmg");
    
    private String name;
    
    static {
    
    }
    
    DeviceBlackList(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaHeaderUnite {
    IS_NEW_HEADER("isNewHeader");
    
    private String name;
    
    static {
    
    }
    
    TmaHeaderUnite(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaNativeUI {
    ENABLE("enable");
    
    private String name;
    
    static {
    
    }
    
    TmaNativeUI(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaProxyList {
    APP_LIST("app_list");
    
    private String name;
    
    static {
    
    }
    
    TmaProxyList(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaSdkConfig {
    LATEST_SDK_URL("app_list"),
    SDK_UPDATE_VERSION("sdkUpdateVersion"),
    SDK_VERSION("sdkVersion");
    
    private String name;
    
    static {
      $VALUES = new TmaSdkConfig[] { SDK_UPDATE_VERSION, SDK_VERSION, LATEST_SDK_URL };
    }
    
    TmaSdkConfig(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaSwitch {
    PRELOAD_WEBVIEW("preloadWebview"),
    TT_RENDER_IN_BROWSER("preloadWebview"),
    USE_NATIVE_LIVE_PLAYER("preloadWebview"),
    USE_WEBAPP("preloadWebview"),
    VIDEO_EFFECT_SWITCH("preloadWebview"),
    WEBVIEW_STREAM_DOWNGRADE("preloadWebview"),
    CHECK_FOLLOW_AWEME_STATE("tmg"),
    DISABLE_SCHEMA_REMOTE_VALIDATION_FOR_TEST_CHANNEL("tmg"),
    ENABLE_MULTI_THREAD_EJS("tmg"),
    FAVORITES("tmg"),
    LAUNCH_FLAG("tmg"),
    MMKV_SWITCH("tmg"),
    MORE_PANEL("tmg"),
    PAGE_BLOCK("tmg"),
    PRELOAD_TMG("tmg");
    
    private String name;
    
    static {
      LAUNCH_FLAG = new TmaSwitch("LAUNCH_FLAG", 2, "launchFlag");
      FAVORITES = new TmaSwitch("FAVORITES", 3, "favorites");
      WEBVIEW_STREAM_DOWNGRADE = new TmaSwitch("WEBVIEW_STREAM_DOWNGRADE", 4, "webviewStreamDowngrade");
      TT_RENDER_IN_BROWSER = new TmaSwitch("TT_RENDER_IN_BROWSER", 5, "tt_render_in_browser");
      MORE_PANEL = new TmaSwitch("MORE_PANEL", 6, "morePanel");
      VIDEO_EFFECT_SWITCH = new TmaSwitch("VIDEO_EFFECT_SWITCH", 7, "videoEffectSwitch");
      PAGE_BLOCK = new TmaSwitch("PAGE_BLOCK", 8, "pageBlock");
      PRELOAD_TMG = new TmaSwitch("PRELOAD_TMG", 9, "preloadTMG");
      USE_WEBAPP = new TmaSwitch("USE_WEBAPP", 10, "useWebApp");
      USE_NATIVE_LIVE_PLAYER = new TmaSwitch("USE_NATIVE_LIVE_PLAYER", 11, "useNativeLivePlayer");
      ENABLE_MULTI_THREAD_EJS = new TmaSwitch("ENABLE_MULTI_THREAD_EJS", 12, "enable_multi_thread_ejs");
      CHECK_FOLLOW_AWEME_STATE = new TmaSwitch("CHECK_FOLLOW_AWEME_STATE", 13, "check_follow_aweme_state");
      DISABLE_SCHEMA_REMOTE_VALIDATION_FOR_TEST_CHANNEL = new TmaSwitch("DISABLE_SCHEMA_REMOTE_VALIDATION_FOR_TEST_CHANNEL", 14, "disableSchemaRemoteValidationForTestChannel");
      $VALUES = new TmaSwitch[] { 
          PRELOAD_WEBVIEW, MMKV_SWITCH, LAUNCH_FLAG, FAVORITES, WEBVIEW_STREAM_DOWNGRADE, TT_RENDER_IN_BROWSER, MORE_PANEL, VIDEO_EFFECT_SWITCH, PAGE_BLOCK, PRELOAD_TMG, 
          USE_WEBAPP, USE_NATIVE_LIVE_PLAYER, ENABLE_MULTI_THREAD_EJS, CHECK_FOLLOW_AWEME_STATE, DISABLE_SCHEMA_REMOTE_VALIDATION_FOR_TEST_CHANNEL };
    }
    
    TmaSwitch(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
    
    public enum Favorites {
      TMA("tma"),
      TMG("tmg");
      
      private String name;
      
      static {
      
      }
      
      Favorites(String param2String1) {
        this.name = param2String1;
      }
      
      public final String toString() {
        return this.name;
      }
    }
  }
  
  public enum Favorites {
    TMA("tma"),
    TMG("tmg");
    
    private String name;
    
    static {
    
    }
    
    Favorites(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
  
  public enum TmaVdomTest {
    ENABLE("enable");
    
    private String name;
    
    static {
    
    }
    
    TmaVdomTest(String param1String1) {
      this.name = param1String1;
    }
    
    public final String toString() {
      return this.name;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\keys\Settings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */