package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.favorite.FavoriteGuideWidget;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.msg.favorite.ApiAddToFavorites;
import com.tt.miniapp.msg.favorite.ApiGetFavoritesList;
import com.tt.miniapp.msg.favorite.ApiRemoveFromFavorites;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteMiniAppMenuItem extends MenuItemAdapter {
  public static LinkedHashSet<String> sFavoriteSet;
  
  private final Activity mActivity;
  
  private MenuItemView mItemView;
  
  public FavoriteMiniAppMenuItem(final Activity activity) {
    this.mActivity = activity;
    this.mItemView = new MenuItemView((Context)activity);
    boolean bool = isCollected();
    this.mItemView.setIcon(makeIcon(activity, bool));
    this.mItemView.setLabel(makeLabel((Context)activity, bool));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ThreadUtil.runOnWorkThread(new Action() {
                  public void act() {
                    FavoriteMiniAppMenuItem.onClickFavoriteAction((Context)activity, FavoriteMiniAppMenuItem.isCollected(), (AppbrandApplication.getInst().getAppInfo()).appId);
                    MenuDialog.inst(activity).dismiss();
                  }
                }Schedulers.longIO());
          }
        });
    if (isDisplayFavoriteEnter()) {
      this.mItemView.setVisibility(0);
      return;
    } 
    this.mItemView.setVisibility(8);
  }
  
  public static boolean clickAddMiniAppToFavoriteList(String paramString) {
    final String addSuccessText;
    final String addFailText;
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    Event.builder("mp_collect_click").flush();
    if (AppbrandContext.getInst().isGame()) {
      str1 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).h;
    } else {
      str1 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).g;
    } 
    if (AppbrandContext.getInst().isGame()) {
      str2 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).j;
    } else {
      str2 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).i;
    } 
    paramString = ApiAddToFavorites.addMiniappToCurrentUserFavoriteListOfNet(paramString);
    if (paramString == null) {
      Event.builder("mp_collect_click_result").kv("result_type", "fail").flush();
      ThreadUtil.runOnUIThread(new Runnable() {
            public final void run() {
              HostDependManager.getInst().showToast((Context)activity, null, addFailText, 0L, null);
            }
          });
      return false;
    } 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      int i = jSONObject.optInt("error", 1);
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "addMiniAppToFavoriteList", "error == ", Integer.valueOf(i) });
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i != 0) {
        Event.builder("mp_collect_click_result").kv("result_type", "success").flush();
        jSONObject = jSONObject.optJSONObject("data");
        if (jSONObject != null) {
          ThreadUtil.runOnUIThread(new Runnable() {
                public final void run() {
                  if (isFirst) {
                    AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "addMiniAppToFavoriteList", "firstFavorite" });
                    HostDependManager.getInst().firstFavoriteAction();
                    return;
                  } 
                  HostDependManager.getInst().showToast((Context)activity, null, addSuccessText, 0L, "success");
                }
              });
          return true;
        } 
      } else {
        AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "addMiniAppToFavoriteList", jSONObject.optString("data", "not errMsg") });
        Event.builder("mp_collect_click_result").kv("result_type", "fail").flush();
        ThreadUtil.runOnUIThread(new Runnable() {
              public final void run() {
                HostDependManager.getInst().showToast((Context)activity, null, addFailText, 0L, null);
              }
            });
        return false;
      } 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("FavoriteMiniAppMenuItem", new Object[] { jSONException });
      Event.builder("mp_collect_click_result").kv("result_type", "fail").flush();
      ThreadUtil.runOnUIThread(new Runnable() {
            public final void run() {
              HostDependManager.getInst().showToast((Context)activity, null, addFailText, 0L, null);
            }
          });
      return false;
    } 
    return true;
  }
  
  public static boolean clickRemoveMiniAppFromFavoriteList(String paramString) {
    final String removeFavoriteSuccessText;
    final String removeFavoriteFailText;
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    Event.builder("mp_collect_cancel").flush();
    if (AppbrandContext.getInst().isGame()) {
      str1 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).l;
    } else {
      str1 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).k;
    } 
    if (AppbrandContext.getInst().isGame()) {
      str2 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).n;
    } else {
      str2 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)miniappHostBase)).m;
    } 
    paramString = ApiRemoveFromFavorites.removeMiniappFromCurrentUserFavoriteListOfNet(paramString);
    if (paramString == null) {
      Event.builder("mp_collect_cancel_result").kv("result_type", "fail").flush();
      ThreadUtil.runOnUIThread(new Runnable() {
            public final void run() {
              HostDependManager.getInst().showToast((Context)activity, null, removeFavoriteFailText, 0L, null);
            }
          });
      return false;
    } 
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      int i = jSONObject.optInt("error", 1);
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "removeMiniAppFromFavoriteList", "error == ", Integer.valueOf(i) });
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i != 0) {
        Event.builder("mp_collect_cancel_result").kv("result_type", "success").flush();
        ThreadUtil.runOnUIThread(new Runnable() {
              public final void run() {
                HostDependManager.getInst().showToast((Context)activity, null, removeFavoriteSuccessText, 0L, "success");
              }
            });
        return true;
      } 
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "removeMiniAppFromFavoriteList", jSONObject.optString("data", "not errMsg") });
    } catch (JSONException jSONException) {
      AppBrandLogger.e("FavoriteMiniAppMenuItem", new Object[] { jSONException });
    } 
    Event.builder("mp_collect_cancel_result").kv("result_type", "fail").flush();
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            HostDependManager.getInst().showToast((Context)activity, null, removeFavoriteFailText, 0L, null);
          }
        });
    return false;
  }
  
  public static boolean isCollected() {
    LinkedHashSet linkedHashSet = InnerHostProcessBridge.getFavoriteSet();
    String str = (AppbrandApplication.getInst().getAppInfo()).appId;
    if (linkedHashSet != null) {
      Iterator<String> iterator = linkedHashSet.iterator();
      while (iterator.hasNext()) {
        if (str.contentEquals(iterator.next()))
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean isDisplayFavoriteEnter() {
    return ((AppbrandApplicationImpl.getInst().getAppInfo().isBox() ^ true) != 0 && isDisplayFavoriteEnterPlatformLevel() && isDisplayFavoriteEnterHostLevel());
  }
  
  public static boolean isDisplayFavoriteEnterHostLevel() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return AppbrandContext.getInst().isGame() ? (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)application)).p : (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)application)).o;
  }
  
  public static boolean isDisplayFavoriteEnterPlatformLevel() {
    String str = InnerHostProcessBridge.getFavoriteSettings();
    if (str != null) {
      try {
        JSONObject jSONObject = new JSONObject(str);
        int i = jSONObject.optInt("tma", 0);
        try {
          int j = jSONObject.optInt("tmg", 0);
        } catch (JSONException null) {}
      } catch (JSONException jSONException) {
        boolean bool1 = false;
      } 
      AppBrandLogger.e("FavoriteMiniAppMenuItem", new Object[] { jSONException });
    } else {
      boolean bool1 = false;
    } 
    boolean bool = false;
  }
  
  private Drawable makeIcon(Activity paramActivity, boolean paramBoolean) {
    return paramBoolean ? paramActivity.getDrawable(2097479737) : paramActivity.getDrawable(2097479733);
  }
  
  private String makeLabel(Context paramContext, boolean paramBoolean) {
    String str1;
    String str2;
    if (AppbrandContext.getInst().isGame()) {
      str2 = (HostDependManager.getInst().getHostCustomFavoriteEntity(paramContext)).b;
    } else {
      str2 = (HostDependManager.getInst().getHostCustomFavoriteEntity(paramContext)).a;
    } 
    if (AppbrandContext.getInst().isGame()) {
      str1 = (HostDependManager.getInst().getHostCustomFavoriteEntity(paramContext)).d;
    } else {
      str1 = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)str1)).c;
    } 
    return paramBoolean ? str1 : str2;
  }
  
  public static void onClickFavoriteAction(final Context context, final boolean isCollected, final String currentMiniAppID) {
    if ((UserInfoManager.getHostClientUserInfo()).isLogin) {
      if (isCollected) {
        clickRemoveMiniAppFromFavoriteList(currentMiniAppID);
        return;
      } 
      if (clickAddMiniAppToFavoriteList(currentMiniAppID)) {
        ThreadUtil.runOnUIThread(new Runnable() {
              public final void run() {
                FavoriteGuideWidget.dismissAllFavoriteGuide();
              }
            });
        return;
      } 
    } else {
      FavoriteModule.setOnLoginHostSuccessListener(new onLoginHostSuccessListener() {
            public final void onLoginSuccess() {
              if (isCollected) {
                FavoriteMiniAppMenuItem.clickRemoveMiniAppFromFavoriteList(currentMiniAppID);
                return;
              } 
              if (FavoriteMiniAppMenuItem.clickAddMiniAppToFavoriteList(currentMiniAppID))
                ThreadUtil.runOnUIThread(new Runnable() {
                      public void run() {
                        FavoriteGuideWidget.dismissAllFavoriteGuide();
                      }
                    }); 
            }
          });
      FavoriteModule.setOnLoginHostFailListener(new onLoginHostFailListener() {
            public final void onLoginHostFail() {
              if (!isCollected) {
                String str;
                if (AppbrandContext.getInst().isGame()) {
                  str = (HostDependManager.getInst().getHostCustomFavoriteEntity(context)).f;
                } else {
                  str = (HostDependManager.getInst().getHostCustomFavoriteEntity(context)).e;
                } 
                HostDependManager.getInst().showToast(context, null, str, 0L, null);
              } 
            }
          });
      if (FavoriteModule.mHostClientLoginListener != null) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("key_favorite_login_flag", "");
        UserInfoManager.requestLoginHostClient(FavoriteModule.mHostClientLoginListener, hashMap, "favorite");
        return;
      } 
      AppBrandLogger.e("FavoriteMiniAppMenuItem", new Object[] { "mHostClientLoginListener can't be null!" });
    } 
  }
  
  public final String getId() {
    return "favorite_mini_app";
  }
  
  public final MenuItemView getView() {
    return this.mItemView;
  }
  
  public void onMenuShow() {
    boolean bool = isCollected();
    getView().setLabel(makeLabel((Context)this.mActivity, bool));
    getView().setIcon(makeIcon(this.mActivity, bool));
  }
  
  public static class FavoriteModule extends NativeModule {
    public static UserInfoManager.HostClientLoginListener mHostClientLoginListener = new UserInfoManager.HostClientLoginListener() {
        public final void onLoginFail() {
          AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginFail" });
          if (FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostFailListener != null)
            FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostFailListener.onLoginHostFail(); 
        }
        
        public final void onLoginSuccess() {
          AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginSuccess" });
          if (FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostSuccessListener != null) {
            FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostSuccessListener.onLoginSuccess();
            ApiGetFavoritesList.getCurrentUserMiniAppFavoriteListFromNet();
          } 
        }
        
        public final void onLoginUnSupport() {
          AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginUnSupport" });
        }
        
        public final void onLoginWhenBackground() {
          AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginWhenBackground" });
        }
        
        public final void onTriggerHostClientLogin(String param2String) {
          StringBuilder stringBuilder = new StringBuilder("eventSource == ");
          stringBuilder.append(param2String);
          AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onTriggerHostClientLogin", stringBuilder.toString() });
          FavoriteMiniAppMenuItem.FavoriteModule.mTriggeredHostClientLogin = true;
          FavoriteMiniAppMenuItem.FavoriteModule.mTriggeredHostClientLoginEventSource = param2String;
        }
      };
    
    public static boolean mTriggeredHostClientLogin;
    
    public static String mTriggeredHostClientLoginEventSource;
    
    public static FavoriteMiniAppMenuItem.onLoginHostFailListener onLoginHostFailListener;
    
    public static FavoriteMiniAppMenuItem.onLoginHostSuccessListener onLoginHostSuccessListener;
    
    public FavoriteModule(AppbrandContext param1AppbrandContext) {
      super(param1AppbrandContext);
    }
    
    public static void setOnLoginHostFailListener(FavoriteMiniAppMenuItem.onLoginHostFailListener param1onLoginHostFailListener) {
      onLoginHostFailListener = param1onLoginHostFailListener;
    }
    
    public static void setOnLoginHostSuccessListener(FavoriteMiniAppMenuItem.onLoginHostSuccessListener param1onLoginHostSuccessListener) {
      onLoginHostSuccessListener = param1onLoginHostSuccessListener;
    }
    
    public String getName() {
      return null;
    }
    
    public <T> String invoke(String param1String, NativeModule.NativeModuleCallback<T> param1NativeModuleCallback) {
      return null;
    }
    
    public boolean onActivityResult(int param1Int1, int param1Int2, Intent param1Intent) {
      boolean bool;
      String str = mTriggeredHostClientLoginEventSource;
      if (str != null && str.contentEquals("favorite")) {
        bool = true;
      } else {
        bool = false;
      } 
      if (mTriggeredHostClientLogin) {
        UserInfoManager.HostClientLoginListener hostClientLoginListener = mHostClientLoginListener;
        if (hostClientLoginListener != null && bool) {
          mTriggeredHostClientLogin = false;
          mTriggeredHostClientLoginEventSource = null;
          return UserInfoManager.handleHostClientLoginResult(param1Int1, param1Int2, param1Intent, hostClientLoginListener);
        } 
      } 
      return false;
    }
  }
  
  static final class null implements UserInfoManager.HostClientLoginListener {
    public final void onLoginFail() {
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginFail" });
      if (FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostFailListener != null)
        FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostFailListener.onLoginHostFail(); 
    }
    
    public final void onLoginSuccess() {
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginSuccess" });
      if (FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostSuccessListener != null) {
        FavoriteMiniAppMenuItem.FavoriteModule.onLoginHostSuccessListener.onLoginSuccess();
        ApiGetFavoritesList.getCurrentUserMiniAppFavoriteListFromNet();
      } 
    }
    
    public final void onLoginUnSupport() {
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginUnSupport" });
    }
    
    public final void onLoginWhenBackground() {
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onLoginWhenBackground" });
    }
    
    public final void onTriggerHostClientLogin(String param1String) {
      StringBuilder stringBuilder = new StringBuilder("eventSource == ");
      stringBuilder.append(param1String);
      AppBrandLogger.d("FavoriteMiniAppMenuItem", new Object[] { "onTriggerHostClientLogin", stringBuilder.toString() });
      FavoriteMiniAppMenuItem.FavoriteModule.mTriggeredHostClientLogin = true;
      FavoriteMiniAppMenuItem.FavoriteModule.mTriggeredHostClientLoginEventSource = param1String;
    }
  }
  
  static interface onLoginHostFailListener {
    void onLoginHostFail();
  }
  
  static interface onLoginHostSuccessListener {
    void onLoginSuccess();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\FavoriteMiniAppMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */