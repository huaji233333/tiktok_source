package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.share.ShareLoading;
import com.tt.miniapp.share.ShareRequestHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.e.e;
import com.tt.option.w.f;
import com.tt.option.w.g;
import com.tt.option.w.h;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ApiShareBaseCtrl extends b implements g {
  protected static String mSharePosition = "inside";
  
  protected static int sClickPosition;
  
  public boolean canCallback;
  
  public boolean isForeground = true;
  
  protected boolean isNormalShare;
  
  public AtomicBoolean isWaitImgSolidify;
  
  public long mClickShareDialogTime;
  
  public AtomicBoolean mGetDefaultShareInfoEnd;
  
  public AtomicBoolean mGetShareMsgEnd;
  
  public AtomicBoolean mIsShareCancel;
  
  private AppbrandApplicationImpl.ILifecycleObserver mLifecycleObserver = new AppbrandApplicationImpl.ILifecycleObserver() {
      public void onHide() {
        ApiShareBaseCtrl.this.isForeground = false;
      }
      
      public void onShow() {
        ApiShareBaseCtrl apiShareBaseCtrl = ApiShareBaseCtrl.this;
        apiShareBaseCtrl.isForeground = true;
        if (apiShareBaseCtrl.mNeedShare && ApiShareBaseCtrl.this.mShareInfoModel != null) {
          ApiShareBaseCtrl.this.mNeedShare = false;
          AppbrandContext.mainHandler.postDelayed(new Runnable() {
                public void run() {
                  ApiShareBaseCtrl.this.doShare(ApiShareBaseCtrl.this.mShareInfoModel);
                }
              },  200L);
        } 
      }
    };
  
  public boolean mNeedShare;
  
  private long mRealStartShareTime;
  
  public h mShareInfoModel;
  
  public ShareLoading mShareLoading;
  
  public UploadImgListener mUploadImgListener;
  
  protected ApiShareBaseCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
    AppbrandApplicationImpl.getInst().registerLifecycleObserver(this.mLifecycleObserver);
    this.mShareLoading = new ShareLoading();
    this.mShareLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
          public void onCancel(DialogInterface param1DialogInterface) {
            if (ApiShareBaseCtrl.this.mIsShareCancel == null)
              ApiShareBaseCtrl.this.mIsShareCancel = new AtomicBoolean(true); 
            ApiShareBaseCtrl.this.callbackCancel();
            ApiShareBaseCtrl.this.showFailToast();
          }
        });
  }
  
  public static int getClickPosition() {
    return sClickPosition;
  }
  
  private void normalShare() {
    this.isNormalShare = true;
    mSharePosition = "inside";
    InnerEventHelper.mpShareClickEvent("inside", isTokenShare());
    solidifyImgUrl();
    showShareDialog((Activity)AppbrandContext.getInst().getCurrentActivity());
  }
  
  public static void setClickPosition(int paramInt) {
    sClickPosition = paramInt;
  }
  
  private void showCommonShareDialog(Activity paramActivity, f paramf) {
    this.mGetShareMsgEnd = new AtomicBoolean(false);
    HostDependManager.getInst().showShareDialog(paramActivity, paramf);
  }
  
  private void showPictureTokenShareDialog(final Activity activity, final f dialogEventListener) {
    final GetDefaultShareInfoListener getDefaultShareInfoListener = new GetDefaultShareInfoListener() {
        public void onFail() {
          if (ApiShareBaseCtrl.this.mGetDefaultShareInfoEnd != null)
            ApiShareBaseCtrl.this.mGetDefaultShareInfoEnd.set(true); 
          ApiShareBaseCtrl.this.mShareLoading.hide("fail", "showPictureTokenShareDialog fail");
        }
        
        public void onSuccess(final h shareInfoModel) {
          AppbrandContext.mainHandler.post(new Runnable() {
                public void run() {
                  if (ApiShareBaseCtrl.this.mGetDefaultShareInfoEnd != null)
                    ApiShareBaseCtrl.this.mGetDefaultShareInfoEnd.set(true); 
                  HostDependManager.getInst().openShareDialog(activity, shareInfoModel, dialogEventListener);
                }
              });
        }
      };
    this.mGetDefaultShareInfoEnd = new AtomicBoolean(false);
    AppbrandContext.mainHandler.postDelayed(new Runnable() {
          public void run() {
            if (ApiShareBaseCtrl.this.mGetDefaultShareInfoEnd != null && !ApiShareBaseCtrl.this.mGetDefaultShareInfoEnd.get())
              ApiShareBaseCtrl.this.mShareLoading.show(); 
          }
        },  1000L);
    AtomicBoolean atomicBoolean = this.isWaitImgSolidify;
    if (atomicBoolean == null || !atomicBoolean.get()) {
      getDefaultShareInfo(getDefaultShareInfoListener);
      return;
    } 
    this.mUploadImgListener = new UploadImgListener() {
        public void onUploadEnd() {
          ApiShareBaseCtrl.this.getDefaultShareInfo(getDefaultShareInfoListener);
        }
      };
    setUploadImgTimeOutAction();
  }
  
  private void showShareDialog(Activity paramActivity) {
    f f = new f() {
        public void onCancel() {
          ApiShareBaseCtrl.this.callbackCancel();
        }
        
        public void onItemClick(String param1String, boolean param1Boolean) {
          if (TextUtils.equals(param1String, ApiShareBaseCtrl.this.mShareInfoModel.shareType) && ApiShareBaseCtrl.this.canCallback == param1Boolean && ApiShareBaseCtrl.this.mGetShareMsgEnd != null && ApiShareBaseCtrl.this.mGetShareMsgEnd.get()) {
            AppbrandContext.mainHandler.post(new Runnable() {
                  public void run() {
                    ApiShareBaseCtrl.this.doShare(ApiShareBaseCtrl.this.mShareInfoModel);
                  }
                });
            return;
          } 
          ApiShareBaseCtrl.this.mClickShareDialogTime = TimeMeter.currentMillis();
          ApiShareBaseCtrl.this.mShareLoading.initLoading(param1String, ApiShareBaseCtrl.mSharePosition, ApiShareBaseCtrl.this.mClickShareDialogTime, ApiShareBaseCtrl.this.isTokenShare());
          ApiShareBaseCtrl.this.mShareInfoModel.shareType = param1String;
          ApiShareBaseCtrl.this.canCallback = param1Boolean;
          AppbrandContext.mainHandler.postDelayed(new Runnable() {
                public void run() {
                  if (ApiShareBaseCtrl.this.mGetShareMsgEnd != null && !ApiShareBaseCtrl.this.mGetShareMsgEnd.get())
                    ApiShareBaseCtrl.this.mShareLoading.show(); 
                }
              },  1000L);
          if (ApiShareBaseCtrl.this.isWaitImgSolidify == null || !ApiShareBaseCtrl.this.isWaitImgSolidify.get()) {
            ApiShareBaseCtrl.this.beforeGetShareInfo();
            ApiShareBaseCtrl.this.requestShareInfo();
            return;
          } 
          ApiShareBaseCtrl.this.mUploadImgListener = new ApiShareBaseCtrl.UploadImgListener() {
              public void onUploadEnd() {
                ApiShareBaseCtrl.this.beforeGetShareInfo();
                ApiShareBaseCtrl.this.requestShareInfo();
              }
            };
          ApiShareBaseCtrl.this.setUploadImgTimeOutAction();
        }
      };
    if (isTokenShare() && HostDependManager.getInst().isHostOptionShareDialogDependEnable()) {
      showPictureTokenShareDialog(paramActivity, f);
      return;
    } 
    showCommonShareDialog(paramActivity, f);
  }
  
  private void solidifyImgUrl() {
    this.isWaitImgSolidify = new AtomicBoolean(false);
    if (TextUtils.isEmpty(this.mShareInfoModel.imageUrl))
      return; 
    this.isWaitImgSolidify.set(true);
    final long uploadStartTime = TimeMeter.currentMillis();
    ShareRequestHelper.uploadShareImgAsync(this.mShareInfoModel, 1, new ShareRequestHelper.OnShareRequestListener() {
          public void onException(Exception param1Exception) {
            if (ApiShareBaseCtrl.this.isWaitImgSolidify != null)
              ApiShareBaseCtrl.this.isWaitImgSolidify.set(false); 
            if (ApiShareBaseCtrl.this.mUploadImgListener != null) {
              ApiShareBaseCtrl.this.mUploadImgListener.onUploadEnd();
              ApiShareBaseCtrl.this.mUploadImgListener = null;
            } 
          }
          
          public void onFail(String param1String) {
            if (ApiShareBaseCtrl.this.isWaitImgSolidify != null)
              ApiShareBaseCtrl.this.isWaitImgSolidify.set(false); 
            InnerEventHelper.mpShareUpload(ApiShareBaseCtrl.mSharePosition, uploadStartTime, "fail", param1String, ApiShareBaseCtrl.this.isTokenShare());
            if (ApiShareBaseCtrl.this.mUploadImgListener != null) {
              ApiShareBaseCtrl.this.mUploadImgListener.onUploadEnd();
              ApiShareBaseCtrl.this.mUploadImgListener = null;
            } 
          }
          
          public void onSuccess(h param1h) {
            if (ApiShareBaseCtrl.this.isWaitImgSolidify != null)
              ApiShareBaseCtrl.this.isWaitImgSolidify.set(false); 
            if (!TextUtils.equals(ApiShareBaseCtrl.this.mShareInfoModel.imageUrl, param1h.imageUrl))
              ApiShareBaseCtrl.this.mShareInfoModel.imageUrl = param1h.imageUrl; 
            InnerEventHelper.mpShareUpload(ApiShareBaseCtrl.mSharePosition, uploadStartTime, "success", null, ApiShareBaseCtrl.this.isTokenShare());
            if (ApiShareBaseCtrl.this.mUploadImgListener != null) {
              ApiShareBaseCtrl.this.mUploadImgListener.onUploadEnd();
              ApiShareBaseCtrl.this.mUploadImgListener = null;
            } 
          }
        });
  }
  
  public void act() {
    beforeAct();
    this.mShareInfoModel = h.parse(this.mArgs);
    if (this.mShareInfoModel == null) {
      AppBrandLogger.d("ApiHandler", new Object[] { "shareInfoModel is null" });
      callbackFail(a.c("shareInfoModel"));
      return;
    } 
    if (interceptNormalShare())
      return; 
    beforeNormalShare();
    normalShare();
    afterNormalShare();
  }
  
  protected void afterGetShareInfo(h paramh) {}
  
  protected void afterNormalShare() {}
  
  protected void beforeAct() {}
  
  protected void beforeGetShareInfo() {}
  
  protected void beforeNormalShare() {}
  
  protected void clearShareMember() {
    this.isNormalShare = false;
    this.canCallback = false;
    this.isWaitImgSolidify = null;
    this.mGetShareMsgEnd = null;
    this.mGetDefaultShareInfoEnd = null;
    this.mIsShareCancel = null;
    this.mUploadImgListener = null;
  }
  
  protected void doShare(h paramh) {
    AtomicBoolean atomicBoolean = this.mIsShareCancel;
    if (atomicBoolean != null && atomicBoolean.get())
      return; 
    if (!this.isForeground) {
      this.mShareInfoModel = paramh;
      this.mNeedShare = true;
      return;
    } 
    if (!this.canCallback)
      callbackOk(); 
    String str = paramh.channel;
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      HostDependManager.getInst().share((Activity)miniappHostBase, paramh, this);
      AppbrandApplicationImpl.getInst().getForeBackgroundManager().pauseBackgroundOverLimitTimeStrategy();
    } 
    InnerEventHelper.mpShareToPlatform(str, mSharePosition, isTokenShare());
    AppbrandApplicationImpl.getInst().ungisterLifecycleObserver(this.mLifecycleObserver);
  }
  
  protected String getChannel() {
    h h1 = this.mShareInfoModel;
    return (h1 != null) ? h1.channel : "";
  }
  
  public void getDefaultShareInfo(final GetDefaultShareInfoListener listener) {
    ShareRequestHelper.getDefaultShareInfo(this.mShareInfoModel, new ShareRequestHelper.OnShareRequestListener() {
          public void onException(Exception param1Exception) {
            ShareLoading shareLoading = ApiShareBaseCtrl.this.mShareLoading;
            StringBuilder stringBuilder = new StringBuilder("get share info exception: ");
            stringBuilder.append(param1Exception.toString());
            shareLoading.hide("fail", stringBuilder.toString());
            ApiShareBaseCtrl.this.callbackFail(param1Exception);
            ApiShareBaseCtrl.this.showFailToast();
            ApiShareBaseCtrl.GetDefaultShareInfoListener getDefaultShareInfoListener = listener;
            if (getDefaultShareInfoListener != null)
              getDefaultShareInfoListener.onFail(); 
          }
          
          public void onFail(String param1String) {
            ApiShareBaseCtrl.this.mShareLoading.hide("fail", param1String);
            ApiShareBaseCtrl.this.callbackFail(param1String);
            ApiShareBaseCtrl.this.showFailToast();
            ApiShareBaseCtrl.GetDefaultShareInfoListener getDefaultShareInfoListener = listener;
            if (getDefaultShareInfoListener != null)
              getDefaultShareInfoListener.onFail(); 
          }
          
          public void onSuccess(h param1h) {
            ApiShareBaseCtrl.this.mShareLoading.hide("success", null);
            ApiShareBaseCtrl.GetDefaultShareInfoListener getDefaultShareInfoListener = listener;
            if (getDefaultShareInfoListener != null)
              getDefaultShareInfoListener.onSuccess(param1h); 
          }
        });
  }
  
  protected h getShareInfoModel() {
    if (this.mShareInfoModel == null)
      this.mShareInfoModel = h.parse(this.mArgs); 
    return this.mShareInfoModel;
  }
  
  protected ShareLoading getShareLoading() {
    return this.mShareLoading;
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return HostDependManager.getInst().handleActivityShareResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected boolean interceptNormalShare() {
    return false;
  }
  
  protected boolean isArticleShare() {
    h h1 = this.mShareInfoModel;
    return (h1 == null) ? false : (TextUtils.isEmpty(h1.channel) ? false : this.mShareInfoModel.channel.equals("article"));
  }
  
  protected boolean isTokenShare() {
    h h1 = this.mShareInfoModel;
    return (h1 == null) ? false : (TextUtils.isEmpty(h1.channel) ? false : this.mShareInfoModel.channel.equals("token"));
  }
  
  protected boolean isVideoShare() {
    h h1 = this.mShareInfoModel;
    return (h1 == null) ? false : (TextUtils.isEmpty(h1.channel) ? false : this.mShareInfoModel.channel.equals("video"));
  }
  
  public void onCancel(String paramString) {}
  
  public void onFail(String paramString) {}
  
  public void onSuccess(String paramString) {}
  
  public void requestShareInfo() {
    this.mRealStartShareTime = TimeMeter.currentMillis();
    long l1 = 6000L - this.mRealStartShareTime - this.mClickShareDialogTime;
    long l2 = 3000L;
    if (l1 < 3000L)
      l1 = l2; 
    ShareRequestHelper.getNormalShareInfoAsync(this.mShareInfoModel, l1, new ShareRequestHelper.OnShareRequestListener() {
          public void onException(Exception param1Exception) {
            if (ApiShareBaseCtrl.this.mGetShareMsgEnd != null)
              ApiShareBaseCtrl.this.mGetShareMsgEnd.set(true); 
            ShareLoading shareLoading = ApiShareBaseCtrl.this.mShareLoading;
            StringBuilder stringBuilder = new StringBuilder("get share info exception: ");
            stringBuilder.append(param1Exception.toString());
            shareLoading.hide("fail", stringBuilder.toString());
            ApiShareBaseCtrl.this.callbackFail(param1Exception);
            ApiShareBaseCtrl.this.showFailToast();
            ApiShareBaseCtrl.this.clearShareMember();
          }
          
          public void onFail(String param1String) {
            if (ApiShareBaseCtrl.this.mGetShareMsgEnd != null)
              ApiShareBaseCtrl.this.mGetShareMsgEnd.set(true); 
            ApiShareBaseCtrl.this.mShareLoading.hide("fail", param1String);
            ApiShareBaseCtrl.this.callbackFail(param1String);
            ApiShareBaseCtrl.this.showFailToast();
            ApiShareBaseCtrl.this.clearShareMember();
          }
          
          public void onSuccess(final h shareInfoModel) {
            if (ApiShareBaseCtrl.this.mGetShareMsgEnd != null)
              ApiShareBaseCtrl.this.mGetShareMsgEnd.set(true); 
            if (ApiShareBaseCtrl.this.mShareInfoModel != shareInfoModel)
              ApiShareBaseCtrl.this.mShareInfoModel = shareInfoModel; 
            ApiShareBaseCtrl.this.mShareLoading.hide("success", null);
            ApiShareBaseCtrl.this.afterGetShareInfo(shareInfoModel);
            AppbrandContext.mainHandler.post(new Runnable() {
                  public void run() {
                    ApiShareBaseCtrl.this.doShare(shareInfoModel);
                  }
                });
          }
        });
  }
  
  protected void sendStateWithShareTicket(String paramString) {
    try {
      callbackOk(new JSONObject(paramString));
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiHandler", new Object[] { "sendStateWithShareTicket", jSONException });
      callbackOk();
      return;
    } 
  }
  
  protected void setCanCallback(boolean paramBoolean) {
    this.canCallback = paramBoolean;
  }
  
  public void setUploadImgTimeOutAction() {
    AppbrandContext.mainHandler.postDelayed(new Runnable() {
          public void run() {
            if (ApiShareBaseCtrl.this.isWaitImgSolidify != null && !ApiShareBaseCtrl.this.isWaitImgSolidify.get())
              return; 
            if (ApiShareBaseCtrl.this.mUploadImgListener != null) {
              ApiShareBaseCtrl.this.mUploadImgListener.onUploadEnd();
              ApiShareBaseCtrl.this.mUploadImgListener = null;
              AppBrandLogger.d("ApiHandler", new Object[] { "upload img timeout, forward..." });
            } 
          }
        }6000L);
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
  
  public void showFailToast() {
    final String msg = UIUtils.getString(2097742027);
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
            if (miniappHostBase == null)
              return; 
            JSONObject jSONObject = (new JsonBuilder()).put("title", msg).put("duration", Integer.valueOf(1500)).build();
            HostDependManager.getInst().showToast((Context)miniappHostBase, jSONObject.toString(), msg, 1000L, null);
          }
        });
  }
  
  public static interface GetDefaultShareInfoListener {
    void onFail();
    
    void onSuccess(h param1h);
  }
  
  public static interface UploadImgListener {
    void onUploadEnd();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiShareBaseCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */