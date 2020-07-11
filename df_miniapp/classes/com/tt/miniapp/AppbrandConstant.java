package com.tt.miniapp;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.AppbrandUtil;
import java.io.File;
import java.io.IOException;

public class AppbrandConstant {
  public static String PreEditManager_OUTPUT_FILE_NAME = "preEditedShareVideo.mp4";
  
  private static File sMiniAppDownloadDir;
  
  public static File sMiniAppRootDir;
  
  public static File getJsBundleDir(Context paramContext) {
    String str2 = BaseBundleManager.getInst().getSdkCurrentVersionStr(paramContext);
    String str1 = str2;
    if (TextUtils.isEmpty(str2))
      str1 = "-1"; 
    File file = new File(AppbrandUtil.getAppServiceDir(paramContext), str1);
    if (!file.exists())
      InnerEventHelper.mpBaseBundleFileNotFound(file.getAbsolutePath()); 
    return file;
  }
  
  public static File getJsBundleDir(Context paramContext, String paramString) {
    File file = new File(AppbrandUtil.getAppServiceDir(paramContext), paramString);
    if (!file.exists())
      InnerEventHelper.mpBaseBundleFileNotFound(file.getAbsolutePath()); 
    return file;
  }
  
  public static String getMergeVideoFilePath() {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(FileManager.inst().getTempDir().getCanonicalPath());
      stringBuilder.append(File.separator);
      stringBuilder.append("merged.mp4");
      return stringBuilder.toString();
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "AppbrandConstant", iOException.getStackTrace());
      return "";
    } 
  }
  
  public static File getMiniAppRootDir(Context paramContext) {
    String str = HostDependManager.getInst().getPrefixPath();
    boolean bool = TextUtils.isEmpty(str);
    if (sMiniAppRootDir == null) {
      String str1 = "appbrand";
      if ((bool ^ true) != 0) {
        StringBuilder stringBuilder = new StringBuilder("appbrand");
        stringBuilder.append(File.separator);
        stringBuilder.append(str);
        str1 = stringBuilder.toString();
      } 
      sMiniAppRootDir = new File(paramContext.getFilesDir(), str1);
    } 
    if (!sMiniAppRootDir.exists())
      sMiniAppRootDir.mkdir(); 
    return sMiniAppRootDir;
  }
  
  public static File getOldBaseBundleDir() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (application != null) ? new File(getMiniAppRootDir((Context)application), "__dev__") : null;
  }
  
  public static String getPreEditVideoPath() {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(FileManager.inst().getTempDir().getCanonicalPath());
      stringBuilder.append(File.separator);
      stringBuilder.append(PreEditManager_OUTPUT_FILE_NAME);
      return stringBuilder.toString();
    } catch (IOException iOException) {
      AppBrandLogger.e("PreEditManager", new Object[] { iOException });
      return "";
    } 
  }
  
  public static String getVideoFilePath() {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(FileManager.inst().getTempDir().getCanonicalPath());
      stringBuilder.append(File.separator);
      stringBuilder.append("shareVideo.mp4");
      return stringBuilder.toString();
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "AppbrandConstant", iOException.getStackTrace());
      return "";
    } 
  }
  
  public static class ApiErrorExtraMsg {}
  
  public static class AppApi {
    @Deprecated
    public static final String API_GET_SCREEN_BRIGHTNESS = "getScreenBrightness";
    
    @Deprecated
    public static final String API_SET_SCREEN_BRIGHTNESS = "setScreenBrightness";
    
    public static class LIBRA_API {
      public static final String API_DISABLE_POP_GESTURE = "disablePopGesture";
      
      public static final String API_END_EDITING = "endEditing";
      
      public static final String API_GETSYSTEMINFO = "getSystemInfo";
      
      public static final String API_GET_LIBRA_API_LIST = "getLibraAPIList";
      
      public static final String API_HIDETOAST = "hideToast";
      
      public static final String API_OPENSCHEMA = "openSchema";
      
      public static final String API_REPORT_ANALYTICS = "reportAnalytics";
      
      public static final String API_REPORT_CUSTOM_EVENT = "reportCustomEvent";
      
      public static final String API_SET_STATUSBAR_STYLE = "setStatusBarStyle";
      
      public static final String API_SHOWTOAST = "showToast";
    }
  }
  
  public static class LIBRA_API {
    public static final String API_DISABLE_POP_GESTURE = "disablePopGesture";
    
    public static final String API_END_EDITING = "endEditing";
    
    public static final String API_GETSYSTEMINFO = "getSystemInfo";
    
    public static final String API_GET_LIBRA_API_LIST = "getLibraAPIList";
    
    public static final String API_HIDETOAST = "hideToast";
    
    public static final String API_OPENSCHEMA = "openSchema";
    
    public static final String API_REPORT_ANALYTICS = "reportAnalytics";
    
    public static final String API_REPORT_CUSTOM_EVENT = "reportCustomEvent";
    
    public static final String API_SET_STATUSBAR_STYLE = "setStatusBarStyle";
    
    public static final String API_SHOWTOAST = "showToast";
  }
  
  public static class AppInfo {
    public static final String VERSION_CODE = "version_code";
  }
  
  public static class AppInstall {}
  
  public static class AppPackage {}
  
  public static class AppRouter {}
  
  public static class Commond {}
  
  public static class Error_Code {}
  
  public static class Error_Msg {}
  
  public static class GameApi {}
  
  @Deprecated
  public static class Http_Domain {}
  
  public static class Http_Result {}
  
  public static class InnerLaunchFrom {}
  
  public static class InnerLocation {}
  
  public static class Interval {}
  
  public static class JSType {}
  
  public static class JsApi {}
  
  public static class MGConstantUtil {}
  
  public static class MapParams {}
  
  public static class MonitorServiceName {}
  
  public static class MonitorStatus {}
  
  public static class NativeWebCommand {}
  
  public static class OpenApi {
    private static volatile OpenApi sInst;
    
    private boolean isI18n;
    
    private String mCurrentBaseUrl = "https://developer-sg.toutiao.com";
    
    private OpenApi() {
      HostDependManager.getInst().isEnableI18nNetRequest();
      chooseDomain(true, HostDependManager.getInst().replaceOpenApiDomain());
    }
    
    private void chooseDomain(boolean param1Boolean, String param1String) {
      if (!param1Boolean) {
        this.mCurrentBaseUrl = "https://developer.toutiao.com";
        return;
      } 
      this.mCurrentBaseUrl = "https://developer-sg.toutiao.com";
      if (!TextUtils.isEmpty(param1String))
        this.mCurrentBaseUrl = param1String; 
    }
    
    public static OpenApi getInst() {
      // Byte code:
      //   0: getstatic com/tt/miniapp/AppbrandConstant$OpenApi.sInst : Lcom/tt/miniapp/AppbrandConstant$OpenApi;
      //   3: ifnonnull -> 37
      //   6: ldc com/tt/miniapp/AppbrandConstant$OpenApi
      //   8: monitorenter
      //   9: getstatic com/tt/miniapp/AppbrandConstant$OpenApi.sInst : Lcom/tt/miniapp/AppbrandConstant$OpenApi;
      //   12: ifnonnull -> 25
      //   15: new com/tt/miniapp/AppbrandConstant$OpenApi
      //   18: dup
      //   19: invokespecial <init> : ()V
      //   22: putstatic com/tt/miniapp/AppbrandConstant$OpenApi.sInst : Lcom/tt/miniapp/AppbrandConstant$OpenApi;
      //   25: ldc com/tt/miniapp/AppbrandConstant$OpenApi
      //   27: monitorexit
      //   28: goto -> 37
      //   31: astore_0
      //   32: ldc com/tt/miniapp/AppbrandConstant$OpenApi
      //   34: monitorexit
      //   35: aload_0
      //   36: athrow
      //   37: getstatic com/tt/miniapp/AppbrandConstant$OpenApi.sInst : Lcom/tt/miniapp/AppbrandConstant$OpenApi;
      //   40: areturn
      // Exception table:
      //   from	to	target	type
      //   9	25	31	finally
      //   25	28	31	finally
      //   32	35	31	finally
    }
    
    public String getABOUT_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/about?");
      return stringBuilder.toString();
    }
    
    public String getADD_MINIAPP_TO_COLLECTION_LIST() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/collect/addcollect");
      return stringBuilder.toString();
    }
    
    public String getAPI_BLACKLIST_V2() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/device/blacklist");
      return stringBuilder.toString();
    }
    
    public String getCHECK_FOLLOW_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/follow/state");
      return stringBuilder.toString();
    }
    
    public String getCHECK_ORDER_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/game/payment/query");
      return stringBuilder.toString();
    }
    
    public String getCLEAN_SHARE_MESSAGE() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/share/delete_share_token");
      return stringBuilder.toString();
    }
    
    public String getCUSTOMER_SERVICE_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/im/url/generate");
      return stringBuilder.toString();
    }
    
    public String getCurrentDomain() {
      return this.mCurrentBaseUrl;
    }
    
    public String getDO_FOLLOW_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/follow/media/follow");
      return stringBuilder.toString();
    }
    
    public String getFRIEND_CLOUD_STORAGE_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/storage/friend?");
      return stringBuilder.toString();
    }
    
    public String getFacialVerifyTicketUrl() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/facial_recognition/v1/get_ticket");
      return stringBuilder.toString();
    }
    
    public String getGET_MINIAPP_COLLECTION_LIST() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/collect/getcollectlist");
      return stringBuilder.toString();
    }
    
    public String getLOGIN_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/v2/login?appid=");
      return stringBuilder.toString();
    }
    
    public String getNOT_SUPPORT_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/unsupported");
      return stringBuilder.toString();
    }
    
    public String getOFFLINE_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/appdown");
      return stringBuilder.toString();
    }
    
    public String getOPENID_TO_UID_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/user/uid?");
      return stringBuilder.toString();
    }
    
    public String getOpenIdUrl() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/user/openid");
      return stringBuilder.toString();
    }
    
    public String getPageFrameFakeURLHost() {
      return HostDependManager.getInst().isEnableI18NRequestRefer() ? "https://tmaservice.developer-sg.byteoversea.com" : "https://tmaservice.developer.toutiao.com";
    }
    
    public String getQUERY_ACCOUNT_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/follow/media/get");
      return stringBuilder.toString();
    }
    
    public String getRANK_DATA_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/rank");
      return stringBuilder.toString();
    }
    
    public String getRECENT_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/history");
      return stringBuilder.toString();
    }
    
    public String getREMOVE_CLOUD_STORAGE_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/storage/remove");
      return stringBuilder.toString();
    }
    
    public String getREMOVE_MINIAPP_FROM_COLLECTION_LIST() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/collect/removecollect");
      return stringBuilder.toString();
    }
    
    public String getREQUEST_ORDER_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/game/payment/new");
      return stringBuilder.toString();
    }
    
    public String getRequestRefere() {
      return HostDependManager.getInst().isEnableI18NRequestRefer() ? "https://tmaservice.developer-sg.byteoversea.com/" : "https://tmaservice.developer.toutiao.com/";
    }
    
    public String getSAVE_CLOUD_STORAGE_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/storage/user");
      return stringBuilder.toString();
    }
    
    public String getSAVE_PERMISSION_GRANT() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/authorization/set");
      return stringBuilder.toString();
    }
    
    public String getSET_USER_GROUP() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/user/group");
      return stringBuilder.toString();
    }
    
    public String getSHARE_MESSAGE() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/share/share_message");
      return stringBuilder.toString();
    }
    
    public String getSHARE_MESSAGE_DEFAULT() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/share/default_share_info");
      return stringBuilder.toString();
    }
    
    public String getSHARE_QUERY_OPEN_GID() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/share/query_open_gid");
      return stringBuilder.toString();
    }
    
    public String getSHARE_UPLOAD_IMG() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/share/upload_image");
      return stringBuilder.toString();
    }
    
    public String getSNAP_SHOT_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/snapshot");
      return stringBuilder.toString();
    }
    
    public String getSORT_COLLECTION_LIST() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/collect/sortcollect");
      return stringBuilder.toString();
    }
    
    public String getSUFFIX_META() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/suffix_meta");
      return stringBuilder.toString();
    }
    
    public String getSYSTEMDOWN() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/systemdown");
      return stringBuilder.toString();
    }
    
    public String getSchemaV2ValidationUrl() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/valid_schema");
      return stringBuilder.toString();
    }
    
    public String getTemplateMsgInfoUrl() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/subscribe_notification/user/v1/show");
      return stringBuilder.toString();
    }
    
    public String getUNSUPPORTED() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/unsupported");
      return stringBuilder.toString();
    }
    
    public String getUNSUPPORTED_MODEL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/unsupported?type=model_unsupported");
      return stringBuilder.toString();
    }
    
    public String getUNSUPPORTED_OS() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/unsupported?type=os_unsupported");
      return stringBuilder.toString();
    }
    
    public String getUSERINFO_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/v2/user/info?appid=");
      return stringBuilder.toString();
    }
    
    public String getUSER_CLOUD_STORAGE_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/storage/user?");
      return stringBuilder.toString();
    }
    
    public String getUSER_LOCATION_URL() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/location/user");
      return stringBuilder.toString();
    }
    
    public String getUploadOrderInfoUrl() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/order/upload_order_info");
      return stringBuilder.toString();
    }
    
    public String getWrapFacialVerifyResultUrl() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/facial_recognition/v1/wrap_res");
      return stringBuilder.toString();
    }
    
    public String reportSubscribeMsgUrl() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/apps/subscribe_notification/user/v1/subscribe");
      return stringBuilder.toString();
    }
  }
  
  public static class OpenSchemaExtra {}
  
  public static class OpenSchemaLaunchFlag {}
  
  public static class Open_Appbrand_Params {}
  
  public static class PackageName {}
  
  public static class PrivateApi {}
  
  public static class Repost {}
  
  public static class Scope {}
  
  public static class SharePreferences {}
  
  public static class SnssdkAPI {
    private static volatile SnssdkAPI sInst;
    
    private String mCurrentBaseUrl = "https://i.snssdk.com";
    
    private SnssdkAPI(boolean param1Boolean) {
      if (param1Boolean) {
        this.mCurrentBaseUrl = "https://i.sgsnssdk.com";
        String str = HostDependManager.getInst().replaceSnssdkApiDomain();
        if (!TextUtils.isEmpty(str))
          this.mCurrentBaseUrl = str; 
        return;
      } 
      this.mCurrentBaseUrl = "https://i.snssdk.com";
    }
    
    public static SnssdkAPI getInst() {
      // Byte code:
      //   0: getstatic com/tt/miniapp/AppbrandConstant$SnssdkAPI.sInst : Lcom/tt/miniapp/AppbrandConstant$SnssdkAPI;
      //   3: ifnonnull -> 43
      //   6: ldc com/tt/miniapp/AppbrandConstant$OpenApi
      //   8: monitorenter
      //   9: getstatic com/tt/miniapp/AppbrandConstant$SnssdkAPI.sInst : Lcom/tt/miniapp/AppbrandConstant$SnssdkAPI;
      //   12: ifnonnull -> 31
      //   15: new com/tt/miniapp/AppbrandConstant$SnssdkAPI
      //   18: dup
      //   19: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
      //   22: invokevirtual isEnableI18nNetRequest : ()Z
      //   25: invokespecial <init> : (Z)V
      //   28: putstatic com/tt/miniapp/AppbrandConstant$SnssdkAPI.sInst : Lcom/tt/miniapp/AppbrandConstant$SnssdkAPI;
      //   31: ldc com/tt/miniapp/AppbrandConstant$OpenApi
      //   33: monitorexit
      //   34: goto -> 43
      //   37: astore_0
      //   38: ldc com/tt/miniapp/AppbrandConstant$OpenApi
      //   40: monitorexit
      //   41: aload_0
      //   42: athrow
      //   43: getstatic com/tt/miniapp/AppbrandConstant$SnssdkAPI.sInst : Lcom/tt/miniapp/AppbrandConstant$SnssdkAPI;
      //   46: areturn
      // Exception table:
      //   from	to	target	type
      //   9	31	37	finally
      //   31	34	37	finally
      //   38	41	37	finally
    }
    
    public String getFeedbackImageUpload() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/feedback/image/v1/upload/");
      return stringBuilder.toString();
    }
    
    public String getFeedbackQuestionList() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/feedback/2/common_feedback_list/");
      return stringBuilder.toString();
    }
    
    public String getFeedbackSubmit() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/feedback/2/post_message/");
      return stringBuilder.toString();
    }
    
    public String getReportContent() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/api/feedback/v1/report_content/");
      return stringBuilder.toString();
    }
    
    public String getReportOption() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mCurrentBaseUrl);
      stringBuilder.append("/feedback/2/report/option/");
      return stringBuilder.toString();
    }
  }
  
  public static class TabConfig {}
  
  public static class TechType {}
  
  public static class TitleBarConfig {}
  
  public static class VideoAttribute {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\AppbrandConstant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */