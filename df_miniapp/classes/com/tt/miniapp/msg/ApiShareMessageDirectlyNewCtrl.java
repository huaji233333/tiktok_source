package com.tt.miniapp.msg;

import android.app.Activity;
import android.text.TextUtils;
import com.a;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.share.AutoShareInterceptor;
import com.tt.miniapp.share.ShareLoading;
import com.tt.miniapp.share.ShareRequestHelper;
import com.tt.miniapp.share.ShareUtils;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.game.IGameRecordManager;
import com.tt.miniapphost.game.IPreEditManager;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.e;
import com.tt.option.w.b;
import com.tt.option.w.h;
import java.io.File;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiShareMessageDirectlyNewCtrl extends ApiShareBaseCtrl {
  private String mActionName = "shareAppMessageDirectly";
  
  private boolean mIsHostOptionShare;
  
  public ApiShareMessageDirectlyNewCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public ApiShareMessageDirectlyNewCtrl(String paramString1, int paramInt, e parame, String paramString2) {
    super(paramString1, paramInt, parame);
    this.mActionName = paramString2;
  }
  
  private void dealWithVideoShare() {
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
    hashSet.add("android.permission.READ_EXTERNAL_STORAGE");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity)AppbrandContext.getInst().getCurrentActivity(), hashSet, new PermissionsResultAction() {
          public void onDenied(String param1String) {
            ApiShareMessageDirectlyNewCtrl.this.callbackFail("auth deny");
          }
          
          public void onGranted() {
            try {
              IGameRecordManager iGameRecordManager;
              String str1 = (new JSONObject((ApiShareMessageDirectlyNewCtrl.this.getShareInfoModel()).extra)).optString("videoPath");
              String str2 = FileManager.inst().getRealFilePath(str1);
              if (!BrandPermissionUtils.isGranted(18, true) && (str1.endsWith("shareVideo.mp4") || str1.endsWith("merged.mp4"))) {
                iGameRecordManager = GameModuleController.inst().getGameRecordManager();
                if (iGameRecordManager != null)
                  iGameRecordManager.deleteVideo(str2); 
                ApiShareMessageDirectlyNewCtrl.this.callbackFail("no permission to share video from record screen");
                return;
              } 
              if (!TextUtils.isEmpty((CharSequence)iGameRecordManager)) {
                str2 = StreamLoader.waitExtractFinishIfNeeded(str2);
                File file = new File(str2);
                if (!file.exists()) {
                  ApiShareMessageDirectlyNewCtrl.this.callbackFail(a.e((String)iGameRecordManager));
                  return;
                } 
                if (!FileManager.inst().canRead(file)) {
                  ApiShareMessageDirectlyNewCtrl.this.callbackFail(a.a("file not readable path:%s", new Object[] { iGameRecordManager }));
                  return;
                } 
                ApiShareMessageDirectlyNewCtrl.this.tryGetPreEditResult(str2);
                return;
              } 
            } catch (JSONException jSONException) {
              AppBrandLogger.e("apiShareMessageDirectly", new Object[] { "onGranted", jSONException.getStackTrace() });
            } 
            if (!ApiShareMessageDirectlyNewCtrl.this.callbackHostOptionShare())
              ApiShareMessageDirectlyNewCtrl.this.shareDirectly(); 
          }
        });
  }
  
  private void forbidAutoShare() {
    long l1 = System.currentTimeMillis();
    long l2 = AutoShareInterceptor.mLastClickTime;
    AppBrandLogger.i("apiShareMessageDirectly", new Object[] { "auto share forbidden!!!" });
    callbackFail("can not be shared without clicking by user");
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("interval", l1 - l2);
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity != null)
        jSONObject.put("appId", appInfoEntity.appId); 
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("apiShareMessageDirectly", "", (Throwable)jSONException);
    } 
    AppBrandMonitor.statusRate("mp_forbid_auto_share", 0, jSONObject);
  }
  
  private String getAliasId() {
    h h = getShareInfoModel();
    if (h == null)
      return CharacterUtils.empty(); 
    if (isVideoShare() && !TextUtils.isEmpty(h.extra))
      try {
        return (new JSONObject(h.extra)).optString("alias_id");
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("apiShareMessageDirectly", "", (Throwable)jSONException);
      }  
    return CharacterUtils.empty();
  }
  
  private String getContentType() {
    h h = getShareInfoModel();
    return (h == null) ? CharacterUtils.empty() : ((isVideoShare() && (getShareInfoModel()).isExtraContainVideoPath) ? "screen_record" : h.channel);
  }
  
  public boolean callbackHostOptionShare() {
    b.a a = HostDependManager.getInst().obtainShareInfoCallback();
    if (a != null) {
      this.mIsHostOptionShare = true;
      mSharePosition = "top";
      h h = getShareInfoModel();
      if (h == null) {
        callbackFail(a.c("shareInfoModel"));
        return false;
      } 
      if (AppbrandApplicationImpl.getInst().getAppConfig() != null)
        h.entryPath = (AppbrandApplicationImpl.getInst().getAppConfig()).mEntryPath; 
      try {
        a.onSuccess((new JSONObject(this.mArgs)).toString(), this);
        return true;
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("apiShareMessageDirectly", "", (Throwable)jSONException);
        return true;
      } 
    } 
    return false;
  }
  
  public String getActionName() {
    return this.mActionName;
  }
  
  public h getShareInfoSync(h paramh) {
    if (paramh == null)
      return null; 
    if (TextUtils.equals(paramh.channel, "video") && paramh.isExtraContainVideoPath && AppbrandApplication.getInst().getAppInfo().isGame()) {
      IPreEditManager iPreEditManager = GameModuleController.inst().getPreEditManager();
      if (iPreEditManager != null)
        InnerEventHelper.mpScreenRecordPublishClick(mSharePosition, getAliasId(), iPreEditManager.getFilterType()); 
    } else {
      InnerEventHelper.mpPublishClick(mSharePosition, paramh.channel);
    } 
    if (!TextUtils.isEmpty(paramh.imageUrl))
      paramh.imageUrl = ShareRequestHelper.uploadShareImgSync(paramh.imageUrl); 
    return ShareRequestHelper.getNormalShareInfoSync(paramh, 6000L);
  }
  
  protected boolean interceptNormalShare() {
    if (AppbrandUtil.isBlockShareChannel((getShareInfoModel()).channel)) {
      callbackFail(a.a("permission denied,channel: %s", new Object[] { (getShareInfoModel()).channel }));
      return true;
    } 
    if (AutoShareInterceptor.intercept(getShareInfoModel())) {
      forbidAutoShare();
      return true;
    } 
    if (isVideoShare()) {
      dealWithVideoShare();
      return true;
    } 
    if (callbackHostOptionShare())
      return true; 
    if (isArticleShare()) {
      shareDirectly();
      return true;
    } 
    return false;
  }
  
  public void onCancel(String paramString) {
    h h;
    super.onCancel(paramString);
    if (this.isNormalShare) {
      InnerEventHelper.mpShareResult(getChannel(), mSharePosition, "cancel", null, isTokenShare());
    } else {
      InnerEventHelper.mpPublishDone(mSharePosition, getContentType(), "cancel");
    } 
    callbackCancel();
    if (this.mIsHostOptionShare) {
      h = HostDependManager.getInst().obtainShareInfo();
    } else {
      h = getShareInfoModel();
    } 
    if (h != null)
      ShareRequestHelper.cleanUselessShare(h.token); 
  }
  
  public void onFail(String paramString) {
    h h;
    String str;
    super.onFail(paramString);
    if (this.isNormalShare) {
      str = paramString;
      if (TextUtils.isEmpty(paramString))
        str = a.d("onFail"); 
      InnerEventHelper.mpShareResult(getChannel(), mSharePosition, "fail", null, isTokenShare());
    } else {
      InnerEventHelper.mpPublishDone(mSharePosition, getContentType(), "fail");
      str = paramString;
      if (TextUtils.isEmpty(paramString))
        str = a.d("onFail"); 
    } 
    callbackFail(str);
    if (this.mIsHostOptionShare) {
      h = HostDependManager.getInst().obtainShareInfo();
    } else {
      h = getShareInfoModel();
    } 
    if (h != null)
      ShareRequestHelper.cleanUselessShare(h.token); 
  }
  
  public void onSuccess(String paramString) {
    super.onSuccess(paramString);
    if (this.isNormalShare) {
      InnerEventHelper.mpShareResult(getChannel(), mSharePosition, "success", null, isTokenShare());
      if (!TextUtils.isEmpty(paramString)) {
        sendStateWithShareTicket(paramString);
        return;
      } 
      callbackOk();
      return;
    } 
    if (TextUtils.equals("screen_record", getContentType())) {
      IPreEditManager iPreEditManager = GameModuleController.inst().getPreEditManager();
      if (iPreEditManager != null)
        InnerEventHelper.mpScreenRecordPublishDone(mSharePosition, getAliasId(), iPreEditManager.getFilterType(), iPreEditManager.isFilterApply(), "success"); 
    } else {
      InnerEventHelper.mpPublishDone(mSharePosition, getContentType(), "success");
    } 
    if (!TextUtils.isEmpty(paramString)) {
      sendStateWithShareTicket(paramString);
      return;
    } 
    callbackOk();
  }
  
  public void replaceVideoPath(String paramString) {
    try {
      h h = getShareInfoModel();
      JSONObject jSONObject = new JSONObject(h.extra);
      jSONObject.put("videoPath", FileManager.inst().getSchemaFilePath(paramString));
      h.extra = jSONObject.toString();
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("apiShareMessageDirectly", "", (Throwable)jSONException);
      return;
    } 
  }
  
  public void shareDirectly() {
    final h shareInfoModel = getShareInfoModel();
    if (h == null)
      return; 
    final ShareLoading shareLoading = getShareLoading();
    shareLoading.show();
    Observable.create(new Action() {
          public void act() {
            h h1 = ApiShareMessageDirectlyNewCtrl.this.getShareInfoSync(shareInfoModel);
            shareLoading.hide();
            if (h1 == null) {
              ApiShareMessageDirectlyNewCtrl.this.callbackFail("get shareInfo return null");
              return;
            } 
            ApiShareMessageDirectlyNewCtrl.this.setCanCallback(true);
            AppbrandContext.mainHandler.post(new Runnable() {
                  public void run() {
                    ApiShareMessageDirectlyNewCtrl.this.doShare(shareInfoModel);
                  }
                });
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void tryGetPreEditResult(final String videoPath) {
    IPreEditManager iPreEditManager = GameModuleController.inst().getPreEditManager();
    if (iPreEditManager == null)
      return; 
    iPreEditManager.getPreEditResult(videoPath, new IPreEditManager.PreEditResultCallback() {
          public void onResult(String param1String) {
            if (!TextUtils.isEmpty(param1String) && !TextUtils.equals(videoPath, param1String)) {
              ApiShareMessageDirectlyNewCtrl.this.replaceVideoPath(param1String);
            } else if (TextUtils.isEmpty(param1String) && videoPath.endsWith("preEditedShareVideo.mp4")) {
              param1String = AppbrandConstant.getVideoFilePath();
              ApiShareMessageDirectlyNewCtrl.this.replaceVideoPath(param1String);
            } else {
              param1String = videoPath;
            } 
            if (ShareUtils.isVideoTooShort(param1String)) {
              ApiShareMessageDirectlyNewCtrl.this.callbackFail(a.a("video file is too short path:%s", new Object[] { this.val$videoPath }));
              return;
            } 
            if (!ApiShareMessageDirectlyNewCtrl.this.callbackHostOptionShare())
              ApiShareMessageDirectlyNewCtrl.this.shareDirectly(); 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiShareMessageDirectlyNewCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */