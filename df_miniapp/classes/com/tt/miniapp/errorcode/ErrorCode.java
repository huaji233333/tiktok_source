package com.tt.miniapp.errorcode;

public interface ErrorCode {
  public enum DOWNLOAD {
    APP_ID_NULL,
    APP_VERSION_NULL,
    FILE_NOT_FOUND(100, "文件不存在"),
    INVALID_URL(100, "文件不存在"),
    MAGIC_STRING_ERROR(100, "文件不存在"),
    NETWORK_ERROR(101, "网络错误"),
    PKG_FILE_OFFSET_WRONG(101, "网络错误"),
    PKG_MD5_ERROR(101, "网络错误"),
    UNKNOWN(101, "网络错误"),
    UNSUPPORT_TTAPKG_VERSION(101, "网络错误");
    
    private int code;
    
    private String desc;
    
    static {
      INVALID_URL = new DOWNLOAD("INVALID_URL", 3, 103, "下载地址不合法");
      PKG_FILE_OFFSET_WRONG = new DOWNLOAD("PKG_FILE_OFFSET_WRONG", 4, 104, "文件偏移量错误");
      APP_ID_NULL = new DOWNLOAD("APP_ID_NULL", 5, 105, "AppInfo中的appId字段为空");
      APP_VERSION_NULL = new DOWNLOAD("APP_VERSION_NULL", 6, 106, "AppInfo中的version字段为空");
      PKG_MD5_ERROR = new DOWNLOAD("PKG_MD5_ERROR", 7, 107, "ttpkg校验MD5失败");
      UNKNOWN = new DOWNLOAD("UNKNOWN", 8, 108, "出现未知异常");
      UNSUPPORT_TTAPKG_VERSION = new DOWNLOAD("UNSUPPORT_TTAPKG_VERSION", 9, 109, "不支持的小程序包版本");
      $VALUES = new DOWNLOAD[] { FILE_NOT_FOUND, NETWORK_ERROR, MAGIC_STRING_ERROR, INVALID_URL, PKG_FILE_OFFSET_WRONG, APP_ID_NULL, APP_VERSION_NULL, PKG_MD5_ERROR, UNKNOWN, UNSUPPORT_TTAPKG_VERSION };
    }
    
    DOWNLOAD(int param1Int1, String param1String1) {
      this.code = param1Int1;
      this.desc = param1String1;
    }
    
    public final String getCode() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Flow.Download.getCode());
      stringBuilder.append(this.code);
      return stringBuilder.toString();
    }
    
    public final String getDesc() {
      return this.desc;
    }
  }
  
  public enum JSCORE {
    TMA_CORE_NOT_FOUND(100, "tma-core.js不存在"),
    TMG_CORE_EXECUTE_ERROR(100, "tma-core.js不存在"),
    TMG_CORE_NOT_FOUND(100, "tma-core.js不存在"),
    TMG_GAME_JS_EXECUTE_ERROR(100, "tma-core.js不存在"),
    TMG_GAME_JS_NOT_FOUND(100, "tma-core.js不存在"),
    MAIN_JS_EXECUTE_ERROR(101, "网络错误"),
    MAIN_JS_NOT_FOUND(101, "网络错误"),
    TMA_CONFIG_EXECUTE_ERROR(101, "网络错误"),
    TMA_CORE_EXECUTE_ERROR(101, "网络错误");
    
    private int code;
    
    private String desc;
    
    static {
      TMA_CONFIG_EXECUTE_ERROR = new JSCORE("TMA_CONFIG_EXECUTE_ERROR", 2, 102, "执行TMAConfig时出现异常");
      MAIN_JS_NOT_FOUND = new JSCORE("MAIN_JS_NOT_FOUND", 3, 103, "main.js不存在");
      MAIN_JS_EXECUTE_ERROR = new JSCORE("MAIN_JS_EXECUTE_ERROR", 4, 104, "main.js执行失败");
      TMG_CORE_NOT_FOUND = new JSCORE("TMG_CORE_NOT_FOUND", 5, 105, "tmg-core不存在");
      TMG_CORE_EXECUTE_ERROR = new JSCORE("TMG_CORE_EXECUTE_ERROR", 6, 106, "tmg-core执行失败");
      TMG_GAME_JS_NOT_FOUND = new JSCORE("TMG_GAME_JS_NOT_FOUND", 7, 107, "game.js不存在");
      TMG_GAME_JS_EXECUTE_ERROR = new JSCORE("TMG_GAME_JS_EXECUTE_ERROR", 8, 108, "game.js执行失败");
      $VALUES = new JSCORE[] { TMA_CORE_NOT_FOUND, TMA_CORE_EXECUTE_ERROR, TMA_CONFIG_EXECUTE_ERROR, MAIN_JS_NOT_FOUND, MAIN_JS_EXECUTE_ERROR, TMG_CORE_NOT_FOUND, TMG_CORE_EXECUTE_ERROR, TMG_GAME_JS_NOT_FOUND, TMG_GAME_JS_EXECUTE_ERROR };
    }
    
    JSCORE(int param1Int1, String param1String1) {
      this.code = param1Int1;
      this.desc = param1String1;
    }
    
    public final String getCode() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Flow.JsCore.getCode());
      stringBuilder.append(this.code);
      return stringBuilder.toString();
    }
    
    public final String getDesc() {
      return this.desc;
    }
  }
  
  public enum MAIN {
    BEFORE_ON_CREATE_CHECK_FAIL(100, "tma-core.js不存在"),
    DEVICE_BLACK_LIST(100, "tma-core.js不存在"),
    GAME_MODULE_NOT_READY(100, "tma-core.js不存在"),
    GET_LAUNCHCACHE_FILE_LOCK_FAIL(100, "tma-core.js不存在"),
    HELIUM_HANDLE_NULL(100, "tma-core.js不存在"),
    HELIUM_INIT_ERROR(100, "tma-core.js不存在"),
    OPEN_APP_ERROR(100, "tma-core.js不存在"),
    PARSE_APPCONFIG_ERROR(100, "tma-core.js不存在"),
    PLUGIN_NOT_INSTALL(100, "tma-core.js不存在"),
    SCHEME_APPID_NULL(100, "tma-core.js不存在"),
    SCHEME_NOT_MATCH(100, "tma-core.js不存在"),
    SCHEME_NULL_ERROR(100, "tma-core.js不存在"),
    SDK_INIT_ERROR(100, "SDK初始化失败"),
    START_MINI_APP_ERROR(100, "SDK初始化失败");
    
    private int code;
    
    private String desc;
    
    static {
      SCHEME_NOT_MATCH = new MAIN("SCHEME_NOT_MATCH", 2, 102, "scheme的host不为microgame/microapp");
      SCHEME_APPID_NULL = new MAIN("SCHEME_APPID_NULL", 3, 103, "scheme中的AppId字段为null");
      PLUGIN_NOT_INSTALL = new MAIN("PLUGIN_NOT_INSTALL", 4, 104, "小程序插件未安装");
      DEVICE_BLACK_LIST = new MAIN("DEVICE_BLACK_LIST", 5, 105, "设备黑名单");
      START_MINI_APP_ERROR = new MAIN("START_MINI_APP_ERROR", 6, 106, "启动小程序/游戏Activity失败");
      PARSE_APPCONFIG_ERROR = new MAIN("PARSE_APPCONFIG_ERROR", 7, 107, "解析AppConfig失败");
      OPEN_APP_ERROR = new MAIN("OPEN_APP_ERROR", 8, 108, "打开小程序/游戏失败");
      HELIUM_HANDLE_NULL = new MAIN("HELIUM_HANDLE_NULL", 9, 109, "游戏引擎的handler为null");
      HELIUM_INIT_ERROR = new MAIN("HELIUM_INIT_ERROR", 10, 110, "小游戏引擎初始化失败");
      BEFORE_ON_CREATE_CHECK_FAIL = new MAIN("BEFORE_ON_CREATE_CHECK_FAIL", 11, 111, "beforeOnCreate校验失败");
      GAME_MODULE_NOT_READY = new MAIN("GAME_MODULE_NOT_READY", 12, 112, "小游戏模块未加载");
      GET_LAUNCHCACHE_FILE_LOCK_FAIL = new MAIN("GET_LAUNCHCACHE_FILE_LOCK_FAIL", 13, 113, "获取LaunchCache文件锁失败");
      $VALUES = new MAIN[] { 
          SDK_INIT_ERROR, SCHEME_NULL_ERROR, SCHEME_NOT_MATCH, SCHEME_APPID_NULL, PLUGIN_NOT_INSTALL, DEVICE_BLACK_LIST, START_MINI_APP_ERROR, PARSE_APPCONFIG_ERROR, OPEN_APP_ERROR, HELIUM_HANDLE_NULL, 
          HELIUM_INIT_ERROR, BEFORE_ON_CREATE_CHECK_FAIL, GAME_MODULE_NOT_READY, GET_LAUNCHCACHE_FILE_LOCK_FAIL };
    }
    
    MAIN(int param1Int1, String param1String1) {
      this.code = param1Int1;
      this.desc = param1String1;
    }
    
    public final String getCode() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Flow.Main.getCode());
      stringBuilder.append(this.code);
      return stringBuilder.toString();
    }
    
    public final String getDesc() {
      return this.desc;
    }
  }
  
  public enum META {
    CODE_ERROR(100, "SDK初始化失败"),
    HOST_MISMATCH(100, "SDK初始化失败"),
    INVALID_APP_ID(100, "SDK初始化失败"),
    INVALID_JS_SDK(100, "SDK初始化失败"),
    INVALID_VERSION(100, "SDK初始化失败"),
    JSON_ERROR(100, "SDK初始化失败"),
    LOCAL_EXPIRED(100, "SDK初始化失败"),
    NULL(100, "获取的meta结果为null"),
    OFFLINE(100, "获取的meta结果为null"),
    PARSE_ERROR(101, "meta解析异常"),
    PERMISSION_DENY(101, "meta解析异常"),
    QRCODE_EXPIRED(101, "meta解析异常"),
    SAVE_FAIL(101, "meta解析异常"),
    UNKNOWN(101, "meta解析异常");
    
    private int code;
    
    private String desc;
    
    static {
      HOST_MISMATCH = new META("HOST_MISMATCH", 7, 107, "小程序/游戏不支持当前宿主环境");
      PERMISSION_DENY = new META("PERMISSION_DENY", 8, 108, "无权访问小程序/游戏");
      INVALID_JS_SDK = new META("INVALID_JS_SDK", 9, 109, "jsSDK低于小程序/游戏最低限制");
      UNKNOWN = new META("UNKNOWN", 10, 110, "出现未知异常");
      QRCODE_EXPIRED = new META("QRCODE_EXPIRED", 11, 111, "预览二维码过期");
      SAVE_FAIL = new META("SAVE_FAIL", 12, 112, "meta保存失败");
      LOCAL_EXPIRED = new META("LOCAL_EXPIRED", 13, 113, "本地meta过期");
      $VALUES = new META[] { 
          NULL, PARSE_ERROR, CODE_ERROR, INVALID_APP_ID, INVALID_VERSION, JSON_ERROR, OFFLINE, HOST_MISMATCH, PERMISSION_DENY, INVALID_JS_SDK, 
          UNKNOWN, QRCODE_EXPIRED, SAVE_FAIL, LOCAL_EXPIRED };
    }
    
    META(int param1Int1, String param1String1) {
      this.code = param1Int1;
      this.desc = param1String1;
    }
    
    public final String getCode() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Flow.Meta.getCode());
      stringBuilder.append(this.code);
      return stringBuilder.toString();
    }
    
    public final String getDesc() {
      return this.desc;
    }
  }
  
  public enum NETWORK {
    SUCCESS(0, "执行成功"),
    NETWORK_CHANGED_ERROR(101, "meta解析异常"),
    NETWORK_CONNECT_ERROR(101, "meta解析异常"),
    NETWORK_DNS_ERROR(101, "meta解析异常"),
    NETWORK_NOT_AVAILABLE(101, "meta解析异常"),
    NETWORK_UNKNOWN_ERROR(101, "meta解析异常");
    
    private int code;
    
    private String desc;
    
    static {
      NETWORK_CHANGED_ERROR = new NETWORK("NETWORK_CHANGED_ERROR", 2, 101, "网络状态变化");
      NETWORK_DNS_ERROR = new NETWORK("NETWORK_DNS_ERROR", 3, 102, "DNS解析失败");
      NETWORK_CONNECT_ERROR = new NETWORK("NETWORK_CONNECT_ERROR", 4, 103, "网络链接过程的失败(timeout+aborted+refused+reset)");
      NETWORK_UNKNOWN_ERROR = new NETWORK("NETWORK_UNKNOWN_ERROR", 5, 104, "SDK没有匹配的网络错误");
      $VALUES = new NETWORK[] { SUCCESS, NETWORK_NOT_AVAILABLE, NETWORK_CHANGED_ERROR, NETWORK_DNS_ERROR, NETWORK_CONNECT_ERROR, NETWORK_UNKNOWN_ERROR };
    }
    
    NETWORK(int param1Int1, String param1String1) {
      this.code = param1Int1;
      this.desc = param1String1;
    }
    
    public final int getCode() {
      return this.code;
    }
    
    public final String getCodeStr() {
      return String.valueOf(this.code);
    }
    
    public final String getDesc() {
      return this.desc;
    }
  }
  
  public enum WEBVIEW {
    ENTRY_PAGE_NOT_FOUND(0, "执行成功"),
    EXECUTE_PAGE_FRAME_ERROR(0, "执行成功"),
    EXECUTE_PATH_FRAME_ERROR(0, "执行成功"),
    FRAME_HTML_INVALIDATE(0, "执行成功"),
    FRAME_HTML_NOT_FOUND(0, "执行成功"),
    FRAME_JS_INVALIDATE(0, "执行成功"),
    FRAME_JS_NOT_FOUND(0, "执行成功"),
    INVALIDATE_TTPKG_VERSION(0, "执行成功"),
    LOAD_TASK_ERROR(0, "执行成功"),
    ON_RENDER_PROCESS_GONE(0, "执行成功"),
    RECEIVE_WEBVIEW_ERROR(0, "执行成功"),
    TEMPLATE_NOT_FOUND(100, "template.html不存在");
    
    private int code;
    
    private String desc;
    
    static {
      FRAME_JS_INVALIDATE = new WEBVIEW("FRAME_JS_INVALIDATE", 2, 102, "page-frame.js中内容不合法");
      FRAME_HTML_NOT_FOUND = new WEBVIEW("FRAME_HTML_NOT_FOUND", 3, 103, "page-frame.html不存在");
      FRAME_HTML_INVALIDATE = new WEBVIEW("FRAME_HTML_INVALIDATE", 4, 104, "page-frame.html不合法");
      INVALIDATE_TTPKG_VERSION = new WEBVIEW("INVALIDATE_TTPKG_VERSION", 5, 105, "不合法的ttpkg版本");
      LOAD_TASK_ERROR = new WEBVIEW("LOAD_TASK_ERROR", 6, 106, "loadTask为null");
      ENTRY_PAGE_NOT_FOUND = new WEBVIEW("ENTRY_PAGE_NOT_FOUND", 7, 107, "entryPage在appConfig中找不到");
      EXECUTE_PAGE_FRAME_ERROR = new WEBVIEW("EXECUTE_PAGE_FRAME_ERROR", 8, 108, "执行page-frame.js出现异常");
      EXECUTE_PATH_FRAME_ERROR = new WEBVIEW("EXECUTE_PATH_FRAME_ERROR", 9, 109, "执行path-frame.js出现异常");
      RECEIVE_WEBVIEW_ERROR = new WEBVIEW("RECEIVE_WEBVIEW_ERROR", 10, 111, "收到webView执行js异常的事件");
      ON_RENDER_PROCESS_GONE = new WEBVIEW("ON_RENDER_PROCESS_GONE", 11, 112, "收到onRenderProcessGone事件");
      $VALUES = new WEBVIEW[] { 
          TEMPLATE_NOT_FOUND, FRAME_JS_NOT_FOUND, FRAME_JS_INVALIDATE, FRAME_HTML_NOT_FOUND, FRAME_HTML_INVALIDATE, INVALIDATE_TTPKG_VERSION, LOAD_TASK_ERROR, ENTRY_PAGE_NOT_FOUND, EXECUTE_PAGE_FRAME_ERROR, EXECUTE_PATH_FRAME_ERROR, 
          RECEIVE_WEBVIEW_ERROR, ON_RENDER_PROCESS_GONE };
    }
    
    WEBVIEW(int param1Int1, String param1String1) {
      this.code = param1Int1;
      this.desc = param1String1;
    }
    
    public final String getCode() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Flow.WebView.getCode());
      stringBuilder.append(this.code);
      return stringBuilder.toString();
    }
    
    public final String getDesc() {
      return this.desc;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\errorcode\ErrorCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */