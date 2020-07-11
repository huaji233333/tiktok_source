package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.f;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.business.frontendapihandle.handler.net.ApiOperateSocketTask;
import com.tt.miniapp.business.frontendapihandle.handler.net.ApiOperateSocketTaskV2;
import com.tt.miniapp.jsbridge.JsBridge;
import com.tt.miniapp.monitor.RouterMonitorTask;
import com.tt.miniapp.msg.ad.ApiCreateBannerAdCtrl;
import com.tt.miniapp.msg.ad.ApiCreateVideoAdCtrl;
import com.tt.miniapp.msg.ad.ApiOperateBannerAdCtrl;
import com.tt.miniapp.msg.ad.ApiOperateVideoAdCtrl;
import com.tt.miniapp.msg.ad.ApiUpdateBannerAdCtrl;
import com.tt.miniapp.msg.audio.async.ApiCreateAudioInstanceCtrl;
import com.tt.miniapp.msg.audio.async.ApiDestroyAudioInstanceCtrl;
import com.tt.miniapp.msg.audio.async.ApiGetAudioStateCtrl;
import com.tt.miniapp.msg.audio.async.ApiOperateAudioCtrl;
import com.tt.miniapp.msg.audio.async.ApiSetAudioState;
import com.tt.miniapp.msg.audio.bg.ApiGetBgAudioContextCtrl;
import com.tt.miniapp.msg.audio.bg.ApiGetBgAudioStateCtrl;
import com.tt.miniapp.msg.audio.bg.ApiOperateBgAudioCtrl;
import com.tt.miniapp.msg.audio.bg.ApiSetBgAudioCtrl;
import com.tt.miniapp.msg.download.ApiCreateDxppTask;
import com.tt.miniapp.msg.download.ApiOperateDxppTask;
import com.tt.miniapp.msg.favorite.ApiAddToFavorites;
import com.tt.miniapp.msg.favorite.ApiAddToUserFavorites;
import com.tt.miniapp.msg.favorite.ApiGetFavoritesList;
import com.tt.miniapp.msg.favorite.ApiRemoveFromFavorites;
import com.tt.miniapp.msg.favorite.ApiShowFavoriteGuide;
import com.tt.miniapp.msg.favorite.ApiSortFavorites;
import com.tt.miniapp.msg.file.FileSystemManager;
import com.tt.miniapp.msg.file.FileSystemManagerV2;
import com.tt.miniapp.msg.image.ApiChooseImageCtrl;
import com.tt.miniapp.msg.image.ApiCompressImageCtrl;
import com.tt.miniapp.msg.image.ApiGetImageInfoCtrl;
import com.tt.miniapp.msg.image.ApiPreviewImageCtrl;
import com.tt.miniapp.msg.image.ApiSaveImageToPhotosAlbumCtrl;
import com.tt.miniapp.msg.navigation.ApiHideHomeButtonCtrl;
import com.tt.miniapp.msg.navigation.ApiHideNavigationBarLoadingCtrl;
import com.tt.miniapp.msg.navigation.ApiSetNavigationBarColor;
import com.tt.miniapp.msg.navigation.ApiSetNavigationBarTitle;
import com.tt.miniapp.msg.navigation.ApiShowNavigationBarLoadingCtrl;
import com.tt.miniapp.msg.onUserCaptureScreen.ApiOffUserCaptureScreenCtrl;
import com.tt.miniapp.msg.onUserCaptureScreen.ApiOnUserCaptureScreenCtrl;
import com.tt.miniapp.msg.shortcut.ApiAddShortcutCtrl;
import com.tt.miniapp.msg.shortcut.ApiCheckShortcutCtrl;
import com.tt.miniapp.msg.sync.ApiOperateFollowButton;
import com.tt.miniapp.msg.tabbar.ApiHideTabBar;
import com.tt.miniapp.msg.tabbar.ApiHideTabBarRedDot;
import com.tt.miniapp.msg.tabbar.ApiRemoveTabBarBadge;
import com.tt.miniapp.msg.tabbar.ApiSetTabBarItem;
import com.tt.miniapp.msg.tabbar.ApiSetTabBarStyle;
import com.tt.miniapp.msg.tabbar.ApiSetTabbarBadge;
import com.tt.miniapp.msg.tabbar.ApiShowTabBar;
import com.tt.miniapp.msg.tabbar.ApiShowTabBarRedDot;
import com.tt.miniapp.msg.video.ApiChooseVideoCtrl;
import com.tt.miniapp.msg.video.ApiSaveVideoToPhotosAlbumCtrl;
import com.tt.miniapp.msg.wifi.ApiGetConnectedWifiCtrl;
import com.tt.miniapp.msg.wifi.ApiGetWifiListCtrl;
import com.tt.miniapp.msg.wifi.ApiOffGetWifiListCtrl;
import com.tt.miniapp.msg.wifi.ApiOnGetWifiListCtrl;
import com.tt.miniapp.process.extra.CrossProcessOperatorScheduler;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapp.util.WebviewSchemaUtil;
import com.tt.miniapp.webbridge.aysnc.ApiOpenCustomerServiceCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.option.e.e;
import com.tt.option.e.f;

public class ApiInvokeCtrl {
  public String mApi;
  
  private e mApiHandlerCalllback;
  
  private f mApiInputParam;
  
  private String mArgs;
  
  public int mCallbackId;
  
  public ApiInvokeCtrl(JsBridge.NativeApiEvent paramNativeApiEvent) {
    this.mApi = paramNativeApiEvent.mApi;
    this.mArgs = paramNativeApiEvent.mParams;
    this.mCallbackId = paramNativeApiEvent.mCallbackId;
    this.mApiHandlerCalllback = paramNativeApiEvent.mApiHandlerCallback;
    this.mApiInputParam = paramNativeApiEvent.mInputParam;
  }
  
  private void onAppRoute() {
    AppBrandLogger.d("tma_ApiInvokeCtrl", new Object[] { "mApi : ", this.mApi, " mArgs : ", this.mArgs });
    final PageUtil.PageRouterParams pageRouterParams = PageUtil.parseRouteParams(this.mArgs);
    RouterMonitorTask.startPageRouter(pageRouterParams.url);
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            ApiCallResult.a a = ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).route(ApiInvokeCtrl.this.mApi, pageRouterParams);
            if (a != null)
              AppbrandApplicationImpl.getInst().getJsBridge().returnAsyncResult(ApiInvokeCtrl.this.mCallbackId, ApiInvokeCtrl.this.makeAppRouteCallbackMsg(a)); 
          }
        });
  }
  
  public void doAct() {
    b b1;
    if (this.mApi.equals("navigateTo") || this.mApi.equals("navigateBack") || this.mApi.equals("redirectTo") || this.mApi.equals("reLaunch") || this.mApi.equals("switchTab")) {
      AppBrandLogger.d("tma_ApiInvokeCtrl", new Object[] { "Start ", Long.valueOf(System.currentTimeMillis()) });
      onAppRoute();
      return;
    } 
    ApiKeyboardCtrl apiKeyboardCtrl = null;
    AppBrandLogger.d("tma_ApiInvokeCtrl", new Object[] { "doAct mApi ", this.mApi });
    if (this.mApi.equals("createAudioInstance")) {
      ApiCreateAudioInstanceCtrl apiCreateAudioInstanceCtrl = new ApiCreateAudioInstanceCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("destroyAudioInstance")) {
      ApiDestroyAudioInstanceCtrl apiDestroyAudioInstanceCtrl = new ApiDestroyAudioInstanceCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("setAudioState")) {
      ApiSetAudioState apiSetAudioState = new ApiSetAudioState(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("operateAudio")) {
      ApiOperateAudioCtrl apiOperateAudioCtrl = new ApiOperateAudioCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getAudioState")) {
      ApiGetAudioStateCtrl apiGetAudioStateCtrl = new ApiGetAudioStateCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getStorage")) {
      b1 = new ApiGetStorageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("setStorage")) {
      b1 = new ApiSetStorageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getStorageInfo")) {
      b1 = new ApiGetStorageInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("clearStorage")) {
      b1 = new ApiClearStorageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("removeStorage")) {
      b1 = new ApiRemoveStorageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("showModal")) {
      b1 = new ApiShowModalDialogCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("showActionSheet")) {
      b1 = new ApiActionSheetCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("showToast")) {
      b1 = new ApiShowToastCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("hideToast")) {
      b1 = new ApiHideToastCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getLocation")) {
      b1 = new ApiGetLocationCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("chooseImage")) {
      ApiChooseImageCtrl apiChooseImageCtrl = new ApiChooseImageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("previewImage")) {
      ApiPreviewImageCtrl apiPreviewImageCtrl = new ApiPreviewImageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("compressImage")) {
      ApiCompressImageCtrl apiCompressImageCtrl = new ApiCompressImageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getSystemInfo")) {
      b1 = new ApiGetSystemInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getNetworkType")) {
      b1 = new ApiGetNetworkTypeCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getConnectedWifi")) {
      ApiGetConnectedWifiCtrl apiGetConnectedWifiCtrl = new ApiGetConnectedWifiCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getWifiList")) {
      ApiGetWifiListCtrl apiGetWifiListCtrl = new ApiGetWifiListCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("onGetWifiList")) {
      ApiOnGetWifiListCtrl apiOnGetWifiListCtrl = new ApiOnGetWifiListCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("offGetWifiList")) {
      ApiOffGetWifiListCtrl apiOffGetWifiListCtrl = new ApiOffGetWifiListCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("chooseVideo")) {
      ApiChooseVideoCtrl apiChooseVideoCtrl = new ApiChooseVideoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("scanCode")) {
      b1 = new ApiScanCodeCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("saveImageToPhotosAlbum")) {
      ApiSaveImageToPhotosAlbumCtrl apiSaveImageToPhotosAlbumCtrl = new ApiSaveImageToPhotosAlbumCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("startPullDownRefresh")) {
      b1 = new ApiStartPullDownRefreshCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("stopPullDownRefresh")) {
      b1 = new ApiStopPullDownRefreshCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("setNavigationBarTitle")) {
      ApiSetNavigationBarTitle apiSetNavigationBarTitle = new ApiSetNavigationBarTitle(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("setNavigationBarColor")) {
      ApiSetNavigationBarColor apiSetNavigationBarColor = new ApiSetNavigationBarColor(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getUserInfo")) {
      b1 = new ApiGetUserInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("login")) {
      b1 = new ApiLoginCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("saveFile")) {
      b1 = new ApiSaveFileCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getSavedFileList")) {
      b1 = new ApiGetSavedFileListCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("removeSavedFile")) {
      b1 = new ApiRemoveSavedFileCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getSavedFileInfo")) {
      b1 = new ApiGetSavedFileInfo(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getFileInfo")) {
      b1 = new ApiGetFileInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("setClipboardData")) {
      b1 = new ApiSetClipboardData(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getClipboardData")) {
      b1 = new ApiGetClipboardData(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("operateSocketTask")) {
      f f1 = this.mApiInputParam;
      if (f1 != null) {
        ApiOperateSocketTaskV2 apiOperateSocketTaskV2 = new ApiOperateSocketTaskV2(f1, this.mCallbackId, this.mApiHandlerCalllback);
      } else {
        ApiOperateSocketTask apiOperateSocketTask = new ApiOperateSocketTask(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
      } 
    } else if (this.mApi.equals("vibrateLong")) {
      b1 = new ApiVibrateLongCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("vibrateShort")) {
      b1 = new ApiVibrateShortCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getImageInfo")) {
      ApiGetImageInfoCtrl apiGetImageInfoCtrl = new ApiGetImageInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("checkSession")) {
      b1 = new ApiCheckSessionCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("saveVideoToPhotosAlbum")) {
      ApiSaveVideoToPhotosAlbumCtrl apiSaveVideoToPhotosAlbumCtrl = new ApiSaveVideoToPhotosAlbumCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("shareAppMessageDirectly")) {
      b1 = new ApiShareMessageDirectlyNewCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("systemLog")) {
      b1 = new ApiSystemLogCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("dealUserRelation")) {
      b1 = new ApiDealUserRelationCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("enableAccelerometer")) {
      b1 = new ApiEnableAccelerometerCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("base64ToTempFilePath")) {
      b1 = new ApiBase64ToTempFilePathCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("startAccelerometer")) {
      b1 = new ApiAccelerometerCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("stopAccelerometer")) {
      b1 = new ApiAccelerometerCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("startCompass")) {
      b1 = new ApiCompassCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("stopCompass")) {
      b1 = new ApiCompassCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("exitMiniProgram")) {
      b1 = new ApiExitMiniProgramCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getBatteryInfo")) {
      b1 = new ApiGetBatteryInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("getScreenBrightness")) {
      b1 = new ApiScreenCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("setKeepScreenOn")) {
      if ((AppbrandApplication.getInst().getAppInfo()).type == 2) {
        b1 = new ApiScreenCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
      } else {
        b1 = new ApisetKeepScreenOnCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
      } 
    } else if (this.mApi.equals("setScreenBrightness")) {
      b1 = new ApiScreenCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("onUserCaptureScreen")) {
      ApiOnUserCaptureScreenCtrl apiOnUserCaptureScreenCtrl = new ApiOnUserCaptureScreenCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("offUserCaptureScreen")) {
      ApiOffUserCaptureScreenCtrl apiOffUserCaptureScreenCtrl = new ApiOffUserCaptureScreenCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("writeFile")) {
      f f1 = this.mApiInputParam;
      if (f1 != null) {
        FileSystemManagerV2 fileSystemManagerV2 = new FileSystemManagerV2(this.mApi, f1, this.mCallbackId, this.mApiHandlerCalllback);
      } else {
        FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
      } 
    } else if (this.mApi.equals("access")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("copyFile")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("mkdir")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("readFile")) {
      f f1 = this.mApiInputParam;
      if (f1 != null) {
        FileSystemManagerV2 fileSystemManagerV2 = new FileSystemManagerV2(this.mApi, f1, this.mCallbackId, this.mApiHandlerCalllback);
      } else {
        FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
      } 
    } else if (this.mApi.equals("rename")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("rmdir")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("readdir")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("stat")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("unlink")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else if (this.mApi.equals("unzip")) {
      FileSystemManager fileSystemManager = new FileSystemManager(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    } else {
      b1 = apiKeyboardCtrl;
      if (!this.mApi.equals("isDirectory")) {
        b1 = apiKeyboardCtrl;
        if (!this.mApi.equals("isFile"))
          if (this.mApi.equals("showKeyboard")) {
            b1 = new ApiKeyboardCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("hideKeyboard")) {
            b1 = new ApiKeyboardCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("updateKeyboard")) {
            b1 = new ApiKeyboardCtrl(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("sentryReport")) {
            ApiErrorLogCtrl apiErrorLogCtrl = new ApiErrorLogCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("makePhoneCall")) {
            ApiMakePhoneCallCtrl apiMakePhoneCallCtrl = new ApiMakePhoneCallCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("openLocation")) {
            ApiOpenLocationCtrl apiOpenLocationCtrl = new ApiOpenLocationCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("chooseLocation")) {
            ApiChooseLocationCtrl apiChooseLocationCtrl = new ApiChooseLocationCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("chooseAddress")) {
            ApiChooseAddressCtrl apiChooseAddressCtrl = new ApiChooseAddressCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("createBannerAd")) {
            ApiCreateBannerAdCtrl apiCreateBannerAdCtrl = new ApiCreateBannerAdCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("updateBannerAd")) {
            ApiUpdateBannerAdCtrl apiUpdateBannerAdCtrl = new ApiUpdateBannerAdCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("operateBannerAd")) {
            ApiOperateBannerAdCtrl apiOperateBannerAdCtrl = new ApiOperateBannerAdCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("createVideoAd")) {
            ApiCreateVideoAdCtrl apiCreateVideoAdCtrl = new ApiCreateVideoAdCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("operateVideoAd")) {
            ApiOperateVideoAdCtrl apiOperateVideoAdCtrl = new ApiOperateVideoAdCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("shareVideo")) {
            ApiShareVideoCtrl apiShareVideoCtrl = new ApiShareVideoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("requestPayment")) {
            ApiRequestPayCtrl apiRequestPayCtrl = new ApiRequestPayCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("authorize")) {
            ApiAuthorizeCtrl apiAuthorizeCtrl = new ApiAuthorizeCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getSetting")) {
            ApiGetSettingCtrl apiGetSettingCtrl = new ApiGetSettingCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("openSetting")) {
            ApiOpenSettingCtrl apiOpenSettingCtrl = new ApiOpenSettingCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showShareMenu")) {
            ApiShowShareMenuCtrl apiShowShareMenuCtrl = new ApiShowShareMenuCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("hideShareMenu")) {
            ApiHideShareMenuCtrl apiHideShareMenuCtrl = new ApiHideShareMenuCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getGeneralInfo")) {
            ApiGetGeneralInfoCtrl apiGetGeneralInfoCtrl = new ApiGetGeneralInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getCloudStorageByRelation")) {
            ApiGetFriendCloudStorageInfoCtrl apiGetFriendCloudStorageInfoCtrl = new ApiGetFriendCloudStorageInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getUserCloudStorage")) {
            ApiGetUserCloudStorageInfoCtrl apiGetUserCloudStorageInfoCtrl = new ApiGetUserCloudStorageInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("setUserCloudStorage")) {
            ApiSetUserCloudStorageInfoCtrl apiSetUserCloudStorageInfoCtrl = new ApiSetUserCloudStorageInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("removeUserCloudStorage")) {
            ApiRemoveUserCloudStorageInfoCtrl apiRemoveUserCloudStorageInfoCtrl = new ApiRemoveUserCloudStorageInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("openUserProfile")) {
            ApiOpenProfileCtrl apiOpenProfileCtrl = new ApiOpenProfileCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("requestWXPayment")) {
            ApiWXRequestPayCtrl apiWXRequestPayCtrl = new ApiWXRequestPayCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("applyUpdate")) {
            ApiApplyUpdateCtrl apiApplyUpdateCtrl = new ApiApplyUpdateCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("createDxppTask")) {
            ApiCreateDxppTask apiCreateDxppTask = new ApiCreateDxppTask(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("operateDxppTask")) {
            ApiOperateDxppTask apiOperateDxppTask = new ApiOperateDxppTask(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("callHostMethod")) {
            ApiCallHostMethodCtrl apiCallHostMethodCtrl = new ApiCallHostMethodCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getBackgroundAudioContext")) {
            ApiGetBgAudioContextCtrl apiGetBgAudioContextCtrl = new ApiGetBgAudioContextCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("setBgAudioState")) {
            ApiSetBgAudioCtrl apiSetBgAudioCtrl = new ApiSetBgAudioCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getBgAudioState")) {
            ApiGetBgAudioStateCtrl apiGetBgAudioStateCtrl = new ApiGetBgAudioStateCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("operateBgAudio")) {
            ApiOperateBgAudioCtrl apiOperateBgAudioCtrl = new ApiOperateBgAudioCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("_webviewGetPhoneNumber")) {
            ApiWebviewGetPhoneNumberCtrl apiWebviewGetPhoneNumberCtrl = new ApiWebviewGetPhoneNumberCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("_serviceGetPhoneNumber")) {
            ApiServiceGetPhoneNumberCtrl apiServiceGetPhoneNumberCtrl = new ApiServiceGetPhoneNumberCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getShareInfo")) {
            ApiGetShareInfoCtrl apiGetShareInfoCtrl = new ApiGetShareInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("navigateToMiniProgram")) {
            ApiNavigateToMiniappCtrl apiNavigateToMiniappCtrl = new ApiNavigateToMiniappCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("navigateBackMiniProgram")) {
            ApiNavigateBackMiniappCtrl apiNavigateBackMiniappCtrl = new ApiNavigateBackMiniappCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("setTabBarBadge")) {
            ApiSetTabbarBadge apiSetTabbarBadge = new ApiSetTabbarBadge(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("removeTabBarBadge")) {
            ApiRemoveTabBarBadge apiRemoveTabBarBadge = new ApiRemoveTabBarBadge(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showTabBarRedDot")) {
            ApiShowTabBarRedDot apiShowTabBarRedDot = new ApiShowTabBarRedDot(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("hideTabBarRedDot")) {
            ApiHideTabBarRedDot apiHideTabBarRedDot = new ApiHideTabBarRedDot(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("hideTabBar")) {
            ApiHideTabBar apiHideTabBar = new ApiHideTabBar(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showTabBar")) {
            ApiShowTabBar apiShowTabBar = new ApiShowTabBar(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("setTabBarItem")) {
            ApiSetTabBarItem apiSetTabBarItem = new ApiSetTabBarItem(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("setTabBarStyle")) {
            ApiSetTabBarStyle apiSetTabBarStyle = new ApiSetTabBarStyle(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getCloudStorageByLocation")) {
            ApiGetCloudStorageByLocation apiGetCloudStorageByLocation = new ApiGetCloudStorageByLocation(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("openModalWebview")) {
            ApiOpenModalWebViewCtrl apiOpenModalWebViewCtrl = new ApiOpenModalWebViewCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("closeModalWebview")) {
            ApiCloseModalWebViewCtrl apiCloseModalWebViewCtrl = new ApiCloseModalWebViewCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("checkFollowState")) {
            ApiCheckFollowStateCtrl apiCheckFollowStateCtrl = new ApiCheckFollowStateCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("operateFollowButton")) {
            ApiOperateFollowButton apiOperateFollowButton = new ApiOperateFollowButton(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("followOfficialAccount")) {
            ApiFollowOfficialAccount apiFollowOfficialAccount = new ApiFollowOfficialAccount(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getRecentAppList")) {
            ApiGetRecentAppsCtrl apiGetRecentAppsCtrl = new ApiGetRecentAppsCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("removeFromRecentAppList")) {
            ApiRemoveRecentAppsCtrl apiRemoveRecentAppsCtrl = new ApiRemoveRecentAppsCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("openDocument")) {
            ApiOpenDocument apiOpenDocument = new ApiOpenDocument(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("reportTimelinePoints")) {
            ApiReportTimeLinePointsCtrl apiReportTimeLinePointsCtrl = new ApiReportTimeLinePointsCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getFavoritesList")) {
            ApiGetFavoritesList apiGetFavoritesList = new ApiGetFavoritesList(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("addToFavorites")) {
            ApiAddToFavorites apiAddToFavorites = new ApiAddToFavorites(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("removeFromFavorites")) {
            ApiRemoveFromFavorites apiRemoveFromFavorites = new ApiRemoveFromFavorites(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("sortFavorites")) {
            ApiSortFavorites apiSortFavorites = new ApiSortFavorites(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showFavoriteGuide")) {
            ApiShowFavoriteGuide apiShowFavoriteGuide = new ApiShowFavoriteGuide(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("addToUserFavorites")) {
            ApiAddToUserFavorites apiAddToUserFavorites = new ApiAddToUserFavorites(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("operateModalWebviewState")) {
            ApiOperateModalWebViewStateCtrl apiOperateModalWebViewStateCtrl = new ApiOperateModalWebViewStateCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("reportJsRuntimeError")) {
            ApiReportJsRuntimeErrorCtrl apiReportJsRuntimeErrorCtrl = new ApiReportJsRuntimeErrorCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getUserStateDirectly")) {
            ApiGetUserStateDirectlyCtrl apiGetUserStateDirectlyCtrl = new ApiGetUserStateDirectlyCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getTimingSettings")) {
            ApiGetTimingSettingCtrl apiGetTimingSettingCtrl = new ApiGetTimingSettingCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("preloadMiniProgram")) {
            ApiPreloadMiniProgramCtrl apiPreloadMiniProgramCtrl = new ApiPreloadMiniProgramCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("addShortcut")) {
            ApiAddShortcutCtrl apiAddShortcutCtrl = new ApiAddShortcutCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showMorePanel")) {
            ApiShowMorePanelCtrl apiShowMorePanelCtrl = new ApiShowMorePanelCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("setMenuButtonVisibility")) {
            ApiSetMenuButtonVisibilityCtrl apiSetMenuButtonVisibilityCtrl = new ApiSetMenuButtonVisibilityCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("checkShortcut")) {
            ApiCheckShortcutCtrl apiCheckShortcutCtrl = new ApiCheckShortcutCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getHostLaunchQuery")) {
            ApiGetHostLaunchQueryCtrl apiGetHostLaunchQueryCtrl = new ApiGetHostLaunchQueryCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getAdSiteBaseInfo")) {
            ApiGetAdSiteBaseInfoCtrl apiGetAdSiteBaseInfoCtrl = new ApiGetAdSiteBaseInfoCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showNavigationBarLoading")) {
            ApiShowNavigationBarLoadingCtrl apiShowNavigationBarLoadingCtrl = new ApiShowNavigationBarLoadingCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("hideNavigationBarLoading")) {
            ApiHideNavigationBarLoadingCtrl apiHideNavigationBarLoadingCtrl = new ApiHideNavigationBarLoadingCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("hideHomeButton")) {
            ApiHideHomeButtonCtrl apiHideHomeButtonCtrl = new ApiHideHomeButtonCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("openCustomerService")) {
            ApiOpenCustomerServiceCtrl apiOpenCustomerServiceCtrl = new ApiOpenCustomerServiceCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("addHostEventListener")) {
            ApiAddHostEventListenerCtrl apiAddHostEventListenerCtrl = new ApiAddHostEventListenerCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("removeHostEventListener")) {
            ApiRemoveHostEventListenerCtrl apiRemoveHostEventListenerCtrl = new ApiRemoveHostEventListenerCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showErrorPage")) {
            ApiShowErrorPageCtrl apiShowErrorPageCtrl = new ApiShowErrorPageCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("navigateToVideoView")) {
            ApiNavigateToVideoViewCtrl apiNavigateToVideoViewCtrl = new ApiNavigateToVideoViewCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getUseDuration")) {
            ApiGetUseDurationCtrl apiGetUseDurationCtrl = new ApiGetUseDurationCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("startFacialRecognitionVerify")) {
            ApiFacialRecognitionVerifyCtrl apiFacialRecognitionVerifyCtrl = new ApiFacialRecognitionVerifyCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("showInteractionBar")) {
            ApiShowInteractionBarCtrl apiShowInteractionBarCtrl = new ApiShowInteractionBarCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("hideInteractionBar")) {
            ApiHideInteractionBarCtrl apiHideInteractionBarCtrl = new ApiHideInteractionBarCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getLocalPhoneNumber")) {
            ApiGetLocalPhoneNumberCtrl apiGetLocalPhoneNumberCtrl = new ApiGetLocalPhoneNumberCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else if (this.mApi.equals("getLocalPhoneNumberToken")) {
            ApiGetLocalPhoneNumberTokenCtrl apiGetLocalPhoneNumberTokenCtrl = new ApiGetLocalPhoneNumberTokenCtrl(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
          } else {
            b1 = apiKeyboardCtrl;
            if (this.mApi.equals("sendUmengEventV1"))
              ApiSendUmeng apiSendUmeng = new ApiSendUmeng(this.mArgs, this.mCallbackId, this.mApiHandlerCalllback); 
          }  
      } 
    } 
    b b2 = GameModuleController.inst().invokeAsyncApi(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
    if (b2 != null)
      b1 = b2; 
    b2 = b1;
    if (WebviewSchemaUtil.isLark()) {
      f.a a = AppbrandContext.getInst().getExtensionApiCreator();
      b2 = b1;
      if (a != null) {
        b b = a.a(this.mApi, this.mArgs, this.mCallbackId, this.mApiHandlerCalllback);
        b2 = b1;
        if (b != null)
          b2 = b; 
      } 
    } 
    if (b2 != null) {
      CrossProcessOperatorScheduler.apiHandlerAct(b2);
      return;
    } 
    try {
      String str = ApiCallResult.a.a(this.mApi, "api is not exist", 0).toString();
      this.mApiHandlerCalllback.callback(this.mCallbackId, str);
      AppBrandLogger.d("tma_ApiInvokeCtrl", new Object[] { str });
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_ApiInvokeCtrl", exception.getStackTrace());
      return;
    } 
  }
  
  public String makeAppRouteCallbackMsg(ApiCallResult.a parama) {
    parama.a("callbackID", Integer.valueOf(this.mCallbackId));
    return parama.a().toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiInvokeCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */