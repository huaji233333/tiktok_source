package com.tt.miniapp.impl;

import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.share.ShareLoading;
import com.tt.miniapp.share.ShareRequestHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.game.IPreEditManager;
import com.tt.option.w.b;
import com.tt.option.w.d;
import com.tt.option.w.e;
import com.tt.option.w.g;
import com.tt.option.w.h;
import java.util.HashMap;
import org.json.JSONObject;

public class HostOptionShareDependImpl implements b, g {
  public h mFullShareInfoModel;
  
  public g mOnShareEventListener;
  
  public b.a obtainShareInfoCallback;
  
  public void getShareBaseInfo(final String channel, final d onGetShareBaseInfoListener) {
    try {
      this.obtainShareInfoCallback = new b.a() {
          public void onFail() {
            HostOptionShareDependImpl.this.mFullShareInfoModel = null;
            d d1 = onGetShareBaseInfoListener;
            if (d1 != null)
              d1.onFail(); 
            HostOptionShareDependImpl.this.obtainShareInfoCallback = null;
          }
          
          public void onSuccess(String param1String, final g onShareEventListener) {
            HostOptionShareDependImpl hostOptionShareDependImpl = HostOptionShareDependImpl.this;
            hostOptionShareDependImpl.obtainShareInfoCallback = null;
            hostOptionShareDependImpl.mFullShareInfoModel = null;
            hostOptionShareDependImpl.mOnShareEventListener = onShareEventListener;
            h h = h.parse(param1String);
            if (h != null)
              h.channel = channel; 
            final ShareLoading shareLoading = new ShareLoading();
            shareLoading.show();
            HostOptionShareDependImpl.this.getShareToken(h, new e() {
                  public void onFail(String param2String) {
                    shareLoading.hide("fail", param2String);
                    if (onGetShareBaseInfoListener != null)
                      onGetShareBaseInfoListener.onFail(); 
                    g g1 = onShareEventListener;
                    if (g1 != null)
                      g1.onFail(param2String); 
                  }
                  
                  public void onSuccess(h param2h) {
                    shareLoading.hide();
                    if (onGetShareBaseInfoListener != null)
                      onGetShareBaseInfoListener.onSuccess(param2h, HostOptionShareDependImpl.this); 
                  }
                });
          }
        };
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("channel", channel);
      if (!TextUtils.isEmpty(AppbrandApplicationImpl.getInst().getCurrentWebViewUrl()))
        hashMap.put("webViewUrl", AppbrandApplicationImpl.getInst().getCurrentWebViewUrl()); 
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onShareAppMessage", (new JSONObject(hashMap)).toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "HostOptionShareDependImpl", exception.getStackTrace());
      return;
    } 
  }
  
  public void getShareToken(final h shareInfoModel, final e onGetShareTokenInfoListener) {
    if (shareInfoModel == null) {
      if (onGetShareTokenInfoListener != null)
        onGetShareTokenInfoListener.onFail("shareInfoModel is null"); 
      return;
    } 
    Observable.create(new Action() {
          public void act() {
            try {
              if (!TextUtils.isEmpty(shareInfoModel.imageUrl))
                shareInfoModel.imageUrl = ShareRequestHelper.uploadShareImgSync(shareInfoModel.imageUrl); 
              boolean bool = TextUtils.isEmpty(shareInfoModel.channel);
              if (!bool && shareInfoModel.channel.equals("video") && shareInfoModel.isExtraContainVideoPath && AppbrandApplication.getInst().getAppInfo().isGame()) {
                String str;
                if (!TextUtils.isEmpty(shareInfoModel.extra)) {
                  str = (new JSONObject(shareInfoModel.extra)).optString("alias_id");
                } else {
                  str = "";
                } 
                IPreEditManager iPreEditManager = GameModuleController.inst().getPreEditManager();
                if (iPreEditManager != null)
                  InnerEventHelper.mpScreenRecordPublishClick("top", str, iPreEditManager.getFilterType()); 
              } else {
                InnerEventHelper.mpPublishClick("top", shareInfoModel.channel);
              } 
              HostOptionShareDependImpl.this.mFullShareInfoModel = ShareRequestHelper.getNormalShareInfoSync(shareInfoModel, 6000L);
              if (onGetShareTokenInfoListener != null) {
                if (HostOptionShareDependImpl.this.mFullShareInfoModel != null) {
                  onGetShareTokenInfoListener.onSuccess(HostOptionShareDependImpl.this.mFullShareInfoModel);
                  return;
                } 
                onGetShareTokenInfoListener.onFail("getShareToken fail");
              } 
              return;
            } catch (Exception exception) {
              AppBrandLogger.eWithThrowable("HostOptionShareDependImpl", "", exception);
              e e1 = onGetShareTokenInfoListener;
              if (e1 != null)
                e1.onFail(exception.getMessage()); 
              return;
            } 
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public boolean isBlockChanelDefault(String paramString, boolean paramBoolean) {
    return false;
  }
  
  public h obtainShareInfo() {
    return this.mFullShareInfoModel;
  }
  
  public b.a obtainShareInfoCallback() {
    return this.obtainShareInfoCallback;
  }
  
  public void onCancel(String paramString) {
    g g1 = this.mOnShareEventListener;
    if (g1 != null) {
      g1.onCancel(paramString);
      this.mOnShareEventListener = null;
    } 
    this.mFullShareInfoModel = null;
  }
  
  public void onFail(String paramString) {
    g g1 = this.mOnShareEventListener;
    if (g1 != null) {
      g1.onFail(paramString);
      this.mOnShareEventListener = null;
    } 
    this.mFullShareInfoModel = null;
  }
  
  public void onSuccess(String paramString) {
    g g1 = this.mOnShareEventListener;
    if (g1 != null) {
      g1.onSuccess(paramString);
      this.mOnShareEventListener = null;
    } 
    this.mFullShareInfoModel = null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionShareDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */