package com.tt.miniapp.favorite;

import android.app.Activity;
import android.content.Context;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.d;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import com.tt.miniapp.msg.favorite.ApiAddToFavorites;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.user.TmaUserManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.g.b;
import java.util.HashMap;
import org.json.JSONObject;

public class FavoriteGuideWidget extends AppbrandServiceManager.ServiceBase {
  public FavoriteGuideBarView mBarView;
  
  public Callback mCallback;
  
  public FavoriteGuideTipView mTipView;
  
  public FavoriteGuideWidget(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private void addMiniAppToFavoriteListFail() {
    FavoriteEvent.onGuideClickResult(false);
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            String str = FavoriteGuideWidget.this.mCallback.getActivity().getString(2097741891);
            HostDependManager.getInst().showToast((Context)FavoriteGuideWidget.this.mCallback.getActivity(), null, str, 0L, null);
          }
        });
  }
  
  private void addMiniAppToFavoriteListSuccess(final boolean isFirst) {
    FavoriteEvent.onGuideClickResult(true);
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            if (isFirst) {
              AppBrandLogger.d("FavoriteGuideWidget", new Object[] { "addMiniAppToFavoriteList", "firstFavorite" });
              HostDependManager.getInst().firstFavoriteAction();
              return;
            } 
            String str = FavoriteGuideWidget.this.mCallback.getActivity().getString(2097741895);
            HostDependManager.getInst().showToast((Context)FavoriteGuideWidget.this.mCallback.getActivity(), null, str, 0L, "success");
          }
        });
  }
  
  public static void dismissAllFavoriteGuide() {
    dismissFavoriteGuide(0);
  }
  
  public static void dismissFavoriteGuide(int paramInt) {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      IActivityProxy iActivityProxy = miniappHostBase.getActivityProxy();
      if (iActivityProxy != null)
        iActivityProxy.dismissFavoriteGuide(paramInt); 
    } 
  }
  
  private d showBar(FavoriteGuideModel paramFavoriteGuideModel) {
    FavoriteGuideBarView favoriteGuideBarView = new FavoriteGuideBarView(paramFavoriteGuideModel, new FavoriteGuideView.Callback() {
          public Activity getActivity() {
            return FavoriteGuideWidget.this.mCallback.getActivity();
          }
          
          public boolean isGame() {
            return FavoriteGuideWidget.this.mCallback.isGame();
          }
          
          public void onClickAddButton() {
            FavoriteGuideWidget.this.addMiniAppToFavoriteList();
          }
          
          public void onDismiss() {
            FavoriteGuideWidget.this.mBarView = null;
          }
        });
    d d = favoriteGuideBarView.check();
    if (!d.a)
      return d; 
    dismissBar();
    favoriteGuideBarView.show();
    this.mBarView = favoriteGuideBarView;
    return d.a();
  }
  
  private d showTip(FavoriteGuideModel paramFavoriteGuideModel) {
    FavoriteGuideTipView favoriteGuideTipView = new FavoriteGuideTipView(paramFavoriteGuideModel, new FavoriteGuideView.Callback() {
          public Activity getActivity() {
            return FavoriteGuideWidget.this.mCallback.getActivity();
          }
          
          public boolean isGame() {
            return FavoriteGuideWidget.this.mCallback.isGame();
          }
          
          public void onClickAddButton() {
            FavoriteGuideWidget.this.addMiniAppToFavoriteList();
          }
          
          public void onDismiss() {
            FavoriteGuideWidget.this.mTipView = null;
          }
        });
    d d = favoriteGuideTipView.check();
    if (!d.a)
      return d; 
    dismissTip();
    favoriteGuideTipView.show();
    this.mTipView = favoriteGuideTipView;
    return d.a();
  }
  
  public void addMiniAppToFavoriteList() {
    if (TmaUserManager.getInstance().isLogin()) {
      doAddMiniAppToFavoriteList();
      return;
    } 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("key_favorite_login_flag", "");
    TmaUserManager.getInstance().login(new TmaUserManager.HostClientLoginListener() {
          public void onLoginFail() {
            String str;
            FavoriteEvent.onGuideClickResult(false);
            b b = HostDependManager.getInst().getHostCustomFavoriteEntity((Context)FavoriteGuideWidget.this.mCallback.getActivity());
            if (FavoriteGuideWidget.this.mCallback.isGame()) {
              str = b.f;
            } else {
              str = ((b)str).e;
            } 
            HostDependManager.getInst().showToast((Context)FavoriteGuideWidget.this.mCallback.getActivity(), null, str, 0L, null);
          }
          
          public void onLoginSuccess() {
            FavoriteGuideWidget.this.doAddMiniAppToFavoriteList();
          }
          
          public void onLoginUnSupport() {}
          
          public void onLoginWhenBackground() {}
          
          public void onTriggerHostClientLogin() {}
        }hashMap);
  }
  
  public boolean addMiniAppToFavoriteListAction() {
    String str = ApiAddToFavorites.addMiniappToCurrentUserFavoriteListOfNet((AppbrandApplicationImpl.getInst().getAppInfo()).appId);
    try {
      JSONObject jSONObject = new JSONObject(str);
      int i = jSONObject.optInt("error", 1);
      AppBrandLogger.d("FavoriteGuideWidget", new Object[] { "addMiniAppToFavoriteList", "error == ", Integer.valueOf(i) });
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i != 0) {
        jSONObject = jSONObject.optJSONObject("data");
        if (jSONObject != null) {
          boolean bool1 = jSONObject.optBoolean("isFirst", false);
          addMiniAppToFavoriteListSuccess(bool1);
          return true;
        } 
      } else {
        AppBrandLogger.d("FavoriteGuideWidget", new Object[] { "addMiniAppToFavoriteList", jSONObject.optString("data", "not errMsg") });
        addMiniAppToFavoriteListFail();
        return false;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("FavoriteGuideWidget", new Object[] { exception });
      addMiniAppToFavoriteListFail();
      return false;
    } 
    boolean bool = false;
    addMiniAppToFavoriteListSuccess(bool);
    return true;
  }
  
  public void dismissAll() {
    dismissTip();
    dismissBar();
  }
  
  public void dismissBar() {
    FavoriteGuideBarView favoriteGuideBarView = this.mBarView;
    if (favoriteGuideBarView != null) {
      favoriteGuideBarView.dismiss(false);
      this.mBarView = null;
    } 
  }
  
  public void dismissTip() {
    FavoriteGuideTipView favoriteGuideTipView = this.mTipView;
    if (favoriteGuideTipView != null) {
      favoriteGuideTipView.dismiss(false);
      this.mTipView = null;
    } 
  }
  
  public void doAddMiniAppToFavoriteList() {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            if (FavoriteGuideWidget.this.addMiniAppToFavoriteListAction())
              ThreadUtil.runOnUIThread(new Runnable() {
                    public void run() {
                      FavoriteGuideWidget.this.dismissAll();
                    }
                  },  ); 
          }
        },  Schedulers.longIO());
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_ROUTE})
  public void onAppRoute() {
    dismissAll();
  }
  
  public void registerCallback(Callback paramCallback) {
    this.mCallback = paramCallback;
  }
  
  public d show(FavoriteGuideModel paramFavoriteGuideModel) {
    d d = FavoriteUtils.checkCommonLimit();
    return !d.a ? d : (FavoriteUtils.isFeedScene() ? d.a("launch from feed not support favorites tip") : ((this.mCallback.isGame() && !"tip".equals(paramFavoriteGuideModel.type)) ? d.a("game not support floating tip") : ("tip".equals(paramFavoriteGuideModel.type) ? showTip(paramFavoriteGuideModel) : showBar(paramFavoriteGuideModel))));
  }
  
  public static interface Callback {
    Activity getActivity();
    
    boolean isGame();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteGuideWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */