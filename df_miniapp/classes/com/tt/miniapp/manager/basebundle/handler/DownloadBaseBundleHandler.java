package com.tt.miniapp.manager.basebundle.handler;

import android.content.Context;
import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.manager.basebundle.BaseBundleDAO;
import com.tt.miniapp.manager.basebundle.BaseBundleFileManager;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.net.download.AbstractDownloadListener;
import com.tt.miniapp.net.download.DownloadManager;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.StorageUtil;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.option.q.f;
import com.tt.option.q.i;
import java.io.File;
import java.util.List;
import okhttp3.ae;

public class DownloadBaseBundleHandler extends BaseBundleHandler {
  private static int retryCount;
  
  public final boolean[] isDownloadFinish = new boolean[] { false };
  
  public final Object lock = new Object();
  
  private String getMd5FromUrl(String paramString) {
    int i = paramString.lastIndexOf(".");
    return paramString.substring(paramString.lastIndexOf("_") + 1, i);
  }
  
  private void startDownload(final String latestSDKUrl, final BaseBundleEventHelper.BaseBundleEvent baseBundleEvent, final BundleHandlerParam param, final String localVersion, final String latestVersion) {
    BaseBundleEventHelper.BaseBundleEvent baseBundleEvent = param.baseBundleEvent;
    StringBuilder stringBuilder = new StringBuilder("start download");
    stringBuilder.append(latestSDKUrl);
    baseBundleEvent.appendLog(stringBuilder.toString());
    final TimeMeter beginDownloadTime = TimeMeter.newAndStart();
    final f tmaFileRequest = new f(latestSDKUrl, false);
    f.a = StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()).getPath();
    f.b = getMd5FromUrl(latestSDKUrl);
    DownloadManager.get().asyncDownload(f.f(), ((i)f).f, f.a, f.b, (DownloadManager.OnDownloadListener)new AbstractDownloadListener() {
          public void onDownloadFailed(String param1String, Throwable param1Throwable) {
            baseBundleEvent.appendLog("remote basebundle download failed");
            AppBrandLogger.e("DownloadBaseBundleHandler", new Object[] { param1String, param1Throwable });
            long l = beginDownloadTime.getMillisAfterStart();
            InnerEventHelper.mpLibResult("mp_lib_download_result", localVersion, latestVersion, "fail", "jssdk tmp file download fail", l);
            baseBundleEvent.appendLog("remote base bundle download failed");
            BundleHandlerParam bundleHandlerParam = param;
            bundleHandlerParam.isLastTaskSuccess = false;
            DownloadBaseBundleHandler.this.retryDownload(latestSDKUrl, baseBundleEvent, bundleHandlerParam, localVersion, latestVersion);
          }
          
          public void onDownloadSuccess(ae param1ae) {
            super.onDownloadSuccess(param1ae);
            File file = new File(tmaFileRequest.a, tmaFileRequest.b);
            long l = beginDownloadTime.getMillisAfterStart();
            if (file.exists()) {
              if (tmaFileRequest.b.equals(CharacterUtils.md5Hex(file))) {
                baseBundleEvent.appendLog("remote basebundle download success, md5 verify success");
                BundleHandlerParam bundleHandlerParam = param;
                bundleHandlerParam.isLastTaskSuccess = true;
                bundleHandlerParam.targetZipFile = file;
                InnerEventHelper.mpLibResult("mp_lib_download_result", localVersion, latestVersion, "success", "", l);
              } else {
                baseBundleEvent.appendLog("remote basebundle md5 not equals");
                InnerEventHelper.mpLibResult("mp_lib_download_result", localVersion, latestVersion, "fail", "md5_fail", l);
                param.isLastTaskSuccess = false;
              } 
            } else {
              baseBundleEvent.appendLog("remote basebundle download fail");
              param.isLastTaskSuccess = false;
              baseBundleEvent.appendLog("remote basebundle not exist");
              InnerEventHelper.mpLibResult("mp_lib_download_result", localVersion, latestVersion, "fail", "md5_fail", l);
            } 
            if (param.isLastTaskSuccess)
              synchronized (DownloadBaseBundleHandler.this.lock) {
                DownloadBaseBundleHandler.this.isDownloadFinish[0] = true;
                DownloadBaseBundleHandler.this.lock.notifyAll();
                return;
              }  
            DownloadBaseBundleHandler.this.retryDownload(latestSDKUrl, baseBundleEvent, param, localVersion, latestVersion);
          }
          
          public void onDownloading(int param1Int, long param1Long1, long param1Long2) {}
        });
  }
  
  public BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    if (paramBundleHandlerParam.isIgnoreTask)
      return paramBundleHandlerParam; 
    String str1 = BaseBundleManager.getInst().getSdkCurrentVersionStr(paramContext);
    String str2 = BaseBundleDAO.getJsSdkSP(paramContext).getString("sdk_update_version", "");
    String str3 = BaseBundleDAO.getJsSdkSP(paramContext).getString("latest_sdk_url", "");
    BaseBundleEventHelper.BaseBundleEvent baseBundleEvent = paramBundleHandlerParam.baseBundleEvent;
    long l = paramBundleHandlerParam.timeMeter.getMillisAfterStart();
    List list = SettingsDAO.getListString(paramContext, new Enum[] { (Enum)Settings.BDP_JSSDK_ROLLBACK, (Enum)Settings.BdpJssdkRollback.ERROR_VERSION });
    String str4 = AppbrandUtil.convertVersionCodeToStr(BaseBundleFileManager.getLatestBaseBundleVersion());
    if (list != null) {
      if (list.contains(str4)) {
        InnerEventHelper.mpLibResult("mp_lib_validation_result", str1, str2, "rollback", "", -1L);
        baseBundleEvent.appendLog("rollback buildin basebundle");
        paramBundleHandlerParam.bundleVersion = BaseBundleFileManager.unZipAssetsBundle(paramContext, "__dev__.zip", "buildin_bundle", baseBundleEvent, true);
        paramBundleHandlerParam.isIgnoreTask = true;
        return paramBundleHandlerParam;
      } 
      if (list.contains(str2)) {
        InnerEventHelper.mpLibResult("mp_lib_validation_result", str1, str2, "rollback_no_update", "", -1L);
        baseBundleEvent.appendLog("no need update to error basebundle version");
        paramBundleHandlerParam.isIgnoreTask = true;
        return paramBundleHandlerParam;
      } 
    } 
    InnerEventHelper.mpLibResult("mp_lib_request_result", str1, str2, "success", "", l);
    baseBundleEvent.appendLog("request remote basebundle success");
    if (AppbrandUtil.convertVersionStrToCode(str1) >= AppbrandUtil.convertVersionStrToCode(str2) && BaseBundleManager.getInst().isRealBaseBundleReadyNow()) {
      InnerEventHelper.mpLibResult("mp_lib_validation_result", str1, str2, "no_update", "", -1L);
      baseBundleEvent.appendLog("no need update remote basebundle version");
      paramBundleHandlerParam.isIgnoreTask = true;
      return paramBundleHandlerParam;
    } 
    InnerEventHelper.mpLibResult("mp_lib_validation_result", str1, str2, "need_update", "", -1L);
    baseBundleEvent.appendLog("remote basebundle version validate, start download remote basebundle");
    paramBundleHandlerParam.timeMeter = TimeMeter.newAndStart();
    startDownload(str3, baseBundleEvent, paramBundleHandlerParam, str1, str2);
    synchronized (this.lock) {
      while (true) {
        boolean bool = this.isDownloadFinish[0];
        if (!bool) {
          try {
            this.lock.wait();
          } catch (InterruptedException interruptedException) {
            AppBrandLogger.d("DownloadBaseBundleHandler", new Object[] { interruptedException });
          } 
          continue;
        } 
        return paramBundleHandlerParam;
      } 
    } 
  }
  
  public void retryDownload(String paramString1, BaseBundleEventHelper.BaseBundleEvent paramBaseBundleEvent, BundleHandlerParam paramBundleHandlerParam, String paramString2, String paramString3) {
    while (retryCount < 3 && !paramBundleHandlerParam.isLastTaskSuccess) {
      retryCount++;
      StringBuilder stringBuilder = new StringBuilder("retry download count: ");
      stringBuilder.append(retryCount);
      paramBaseBundleEvent.appendLog(stringBuilder.toString());
      paramBundleHandlerParam.isLastTaskSuccess = true;
      startDownload(paramString1, paramBaseBundleEvent, paramBundleHandlerParam, paramString2, paramString3);
      synchronized (this.lock) {
        while (true) {
          boolean bool = this.isDownloadFinish[0];
          if (!bool) {
            try {
              this.lock.wait();
            } catch (InterruptedException interruptedException) {
              AppBrandLogger.d("DownloadBaseBundleHandler", new Object[] { interruptedException });
            } 
            continue;
          } 
        } 
      } 
    } 
    synchronized (this.lock) {
      this.isDownloadFinish[0] = true;
      this.lock.notifyAll();
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\DownloadBaseBundleHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */