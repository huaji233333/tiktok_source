package com.tt.miniapp.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.b.a;
import com.tt.b.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.TitleBarHelper;
import com.tt.miniapp.component.nativeview.game.RoundedImageView;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.manager.TmaFeatureConfigManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.FeignHostConfig;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.miniapphost.util.UIUtils;
import org.json.JSONObject;

public class LaunchLoadingView extends RelativeLayout {
  public volatile boolean isShowingError;
  
  public Activity mActivity;
  
  public RelativeLayout mDefaultLoadingContainer;
  
  public TextView mDownloadProgressTv;
  
  public TextView mFailMsgTv;
  
  public ImageView mHostIcon;
  
  public FrameLayout mHostLoadingContainer;
  
  public RoundedImageView mIconImg;
  
  public TimeMeter mLoadStartTime;
  
  public ImageView mLoadingBgImageView;
  
  public View mLoadingTitleBar;
  
  public TextView mNameTv;
  
  public boolean mUsingHostLoading;
  
  public LaunchLoadingView(Context paramContext) {
    super(paramContext);
    LayoutInflater.from(paramContext).inflate(2097676322, (ViewGroup)this);
    initView();
  }
  
  private void feignHostLoadingView() {
    final FeignHostConfig config = FeignHostConfig.inst();
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            View view;
            Drawable drawable;
            if (isGame) {
              view = config.getGameLaunchLoadingView();
            } else {
              view = config.getAppLaunchLoadingView();
            } 
            if (view != null) {
              LaunchLoadingView launchLoadingView = LaunchLoadingView.this;
              launchLoadingView.mUsingHostLoading = true;
              launchLoadingView.mDefaultLoadingContainer.setVisibility(4);
              LaunchLoadingView.this.mHostLoadingContainer.setVisibility(0);
              if (view.getParent() != null)
                ((ViewGroup)view.getParent()).removeView(view); 
              LaunchLoadingView.this.mHostLoadingContainer.addView(view);
            } else {
              LaunchLoadingView launchLoadingView = LaunchLoadingView.this;
              launchLoadingView.mUsingHostLoading = false;
              launchLoadingView.mDefaultLoadingContainer.setVisibility(0);
              LaunchLoadingView.this.mHostLoadingContainer.setVisibility(4);
              LaunchLoadingView.this.mIconImg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                      if (LaunchLoadingView.this.mIconImg.getMeasuredHeight() != 0) {
                        LaunchLoadingView.this.mIconImg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        LaunchLoadingView.this.updateViews(AppbrandApplicationImpl.getInst().getAppInfo());
                      } 
                    }
                  });
            } 
            if (isGame) {
              drawable = config.getGameLoadingMoreMenuDrawable();
            } else {
              drawable = config.getAppLoadingMoreMenuDrawable();
            } 
            if (drawable != null) {
              ImageView imageView = (ImageView)LaunchLoadingView.this.mLoadingTitleBar.findViewById(2097545408);
              imageView.setImageDrawable(drawable);
              if (!HostDependManager.getInst().isTitlebarMoreMenuVisible())
                imageView.setVisibility(4); 
            } 
            if (isGame) {
              drawable = config.getGameLoadingCloseIconDrawable();
            } else {
              drawable = config.getAppLoadingCloseIconDrawable();
            } 
            if (drawable != null)
              ((ImageView)LaunchLoadingView.this.mLoadingTitleBar.findViewById(2097545405)).setImageDrawable(drawable); 
            if (isGame) {
              drawable = config.getGameLoadingCapsuleDrawable();
            } else {
              drawable = config.getAppLoadingCapsuleDrawable();
            } 
            if (drawable != null) {
              LaunchLoadingView.this.mLoadingTitleBar.findViewById(2097545404).setBackground(drawable);
              LaunchLoadingView.this.findViewById(2097545407).setVisibility(4);
            } 
          }
        });
  }
  
  private void initView() {
    this.mDefaultLoadingContainer = (RelativeLayout)findViewById(2097545464);
    this.mHostLoadingContainer = (FrameLayout)findViewById(2097545226);
    this.mLoadingTitleBar = findViewById(2097545330);
    this.mLoadingTitleBar.findViewById(2097545355).setVisibility(4);
    this.mLoadingBgImageView = (ImageView)this.mDefaultLoadingContainer.findViewById(2097545327);
    this.mDownloadProgressTv = (TextView)this.mDefaultLoadingContainer.findViewById(2097545270);
    this.mIconImg = (RoundedImageView)this.mDefaultLoadingContainer.findViewById(2097545328);
    this.mNameTv = (TextView)this.mDefaultLoadingContainer.findViewById(2097545247);
    this.mFailMsgTv = (TextView)this.mDefaultLoadingContainer.findViewById(2097545275);
    this.mHostIcon = (ImageView)this.mDefaultLoadingContainer.findViewById(2097545310);
  }
  
  public void hideBottomTip() {
    if (!this.mUsingHostLoading)
      findViewById(2097545311).setVisibility(4); 
  }
  
  public void initWithActivity(Activity paramActivity) {
    this.mActivity = paramActivity;
  }
  
  public WindowInsets onApplyWindowInsets(WindowInsets paramWindowInsets) {
    if (!this.mUsingHostLoading) {
      int i = (getContext().getResources().getConfiguration()).orientation;
      View view = findViewById(2097545256);
      RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.mNameTv.getLayoutParams();
      if (i == 1) {
        layoutParams.removeRule(3);
        layoutParams.addRule(2, view.getId());
      } else {
        layoutParams.removeRule(2);
        layoutParams.addRule(3, view.getId());
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.mHostIcon.getLayoutParams();
        marginLayoutParams.bottomMargin = (int)UIUtils.dip2Px(getContext(), 67.0F);
        this.mHostIcon.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams);
      } 
      this.mNameTv.requestLayout();
    } 
    Activity activity = this.mActivity;
    if (activity != null)
      UIUtils.configTitleBarWithHeight((Context)activity, this.mLoadingTitleBar); 
    return super.onApplyWindowInsets(paramWindowInsets);
  }
  
  protected void onAttachedToWindow() {
    FeignHostConfig.LaunchLoadingListener launchLoadingListener;
    super.onAttachedToWindow();
    feignHostLoadingView();
    FeignHostConfig feignHostConfig = FeignHostConfig.inst();
    if (AppbrandContext.getInst().isGame()) {
      launchLoadingListener = feignHostConfig.getGameLaunchLoadingListener();
    } else {
      launchLoadingListener = launchLoadingListener.getAppLaunchLoadingListener();
    } 
    if (launchLoadingListener != null)
      launchLoadingListener.onLoadingStart(); 
  }
  
  public void onUsed() {
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())
      this.mLoadingTitleBar.findViewById(2097545406).setVisibility(4); 
  }
  
  public void removeLoadingLayout() {
    removeLoadingLayout((Runnable)null);
  }
  
  public void removeLoadingLayout(final Runnable runOnRemoved) {
    FeignHostConfig.LaunchLoadingListener launchLoadingListener;
    if (this.isShowingError)
      return; 
    FeignHostConfig feignHostConfig = FeignHostConfig.inst();
    if (AppbrandContext.getInst().isGame()) {
      launchLoadingListener = feignHostConfig.getGameLaunchLoadingListener();
    } else {
      launchLoadingListener = launchLoadingListener.getAppLaunchLoadingListener();
    } 
    if (launchLoadingListener != null)
      launchLoadingListener.onLoadingEnd(); 
    AppBrandLogger.d("LaunchLoadingView", new Object[] { "removeLoadingLayout" });
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.0F);
            alphaAnimation.setDuration(100L);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                  public void onAnimationEnd(Animation param2Animation) {
                    LaunchLoadingView.this.post(new Runnable() {
                          public void run() {
                            ViewParent viewParent = LaunchLoadingView.this.getParent();
                            if (viewParent instanceof ViewGroup)
                              ((ViewGroup)viewParent).removeView((View)LaunchLoadingView.this); 
                          }
                        });
                  }
                  
                  public void onAnimationRepeat(Animation param2Animation) {}
                  
                  public void onAnimationStart(Animation param2Animation) {}
                });
            LaunchLoadingView.this.startAnimation((Animation)alphaAnimation);
            Runnable runnable = runOnRemoved;
            if (runnable != null)
              ThreadUtil.runOnUIThread(runnable, alphaAnimation.getDuration()); 
          }
        });
  }
  
  public void setLoadStartTime(TimeMeter paramTimeMeter) {
    this.mLoadStartTime = paramTimeMeter;
  }
  
  public void setLoadingTitlebarVisibility(int paramInt) {
    this.mLoadingTitleBar.setVisibility(paramInt);
  }
  
  public void showFailMessage(final String str, final boolean canRetry, final boolean canRestart) {
    if (!this.mUsingHostLoading)
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              LaunchLoadingView launchLoadingView = LaunchLoadingView.this;
              launchLoadingView.isShowingError = true;
              launchLoadingView.mDownloadProgressTv.setVisibility(4);
              LaunchLoadingView.this.mFailMsgTv.setVisibility(0);
              if (canRetry) {
                String str1;
                SpannableString spannableString;
                if (canRestart) {
                  str1 = LaunchLoadingView.this.getContext().getString(2097741873);
                } else {
                  str1 = LaunchLoadingView.this.getContext().getString(2097742013);
                } 
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(" ");
                stringBuilder.append(str1);
                String str2 = stringBuilder.toString();
                float f = LaunchLoadingView.this.mFailMsgTv.getPaint().measureText(str2);
                if (LaunchLoadingView.this.getMeasuredWidth() - UIUtils.dip2Px(LaunchLoadingView.this.getContext(), 150.0F) > f) {
                  StringBuilder stringBuilder1 = new StringBuilder();
                  stringBuilder1.append(str);
                  stringBuilder1.append(" ");
                  stringBuilder1.append(str1);
                  spannableString = new SpannableString(stringBuilder1.toString());
                } else {
                  StringBuilder stringBuilder1 = new StringBuilder();
                  stringBuilder1.append(str);
                  stringBuilder1.append("\n");
                  stringBuilder1.append(str1);
                  spannableString = new SpannableString(stringBuilder1.toString());
                } 
                LaunchLoadingView.this.mFailMsgTv.setMovementMethod(LinkMovementMethod.getInstance());
                spannableString.setSpan(new ClickableSpan() {
                      public void onClick(View param2View) {
                        EventParamsValue.PARAMS_EXIT_TYPE = "others";
                        EventParamsValue.IS_OTHER_FLAG = true;
                        if (LaunchLoadingView.this.mActivity != null) {
                          if (canRestart) {
                            Event.builder("mp_restart_miniapp").flush();
                            InnerHostProcessBridge.restartApp((AppbrandApplicationImpl.getInst().getAppInfo()).appId, AppbrandApplicationImpl.getInst().getSchema());
                            return;
                          } 
                          ToolUtils.onActivityExit(LaunchLoadingView.this.mActivity, 1);
                        } 
                      }
                      
                      public void updateDrawState(TextPaint param2TextPaint) {
                        super.updateDrawState(param2TextPaint);
                        param2TextPaint.setColor(-13987625);
                        param2TextPaint.setUnderlineText(false);
                      }
                    }spannableString.length() - str1.length(), spannableString.length(), 17);
                LaunchLoadingView.this.mFailMsgTv.setHighlightColor(0);
                LaunchLoadingView.this.mFailMsgTv.setText((CharSequence)spannableString);
                return;
              } 
              LaunchLoadingView.this.mFailMsgTv.setText(str);
            }
          }); 
  }
  
  public void showHostTipIfNeed(AppInfoEntity paramAppInfoEntity) {
    Application application = AppbrandContext.getInst().getApplicationContext();
    JSONObject jSONObject = SettingsDAO.getJSONObject((Context)application, new Enum[] { (Enum)Settings.BDP_LAUNCH_LOADING_CONFIG, (Enum)Settings.LaunchLoadingConfig.HOST_TIP_ICON });
    if (jSONObject == null)
      return; 
    if (paramAppInfoEntity.isGame()) {
      str = "micro_game";
    } else {
      str = "micro_app";
    } 
    String str = jSONObject.optString(str, "");
    if (!TextUtils.isEmpty(str)) {
      c c = (new c(str)).a((View)this.mHostIcon).a(new a() {
            public void onFail(Exception param1Exception) {}
            
            public void onSuccess() {
              ThreadUtil.runOnUIThread(new Runnable() {
                    public void run() {
                      LaunchLoadingView.this.mHostIcon.setVisibility(0);
                    }
                  });
            }
          });
      HostDependManager.getInst().loadImage((Context)application, c);
    } 
  }
  
  public void showLoadingBgIfNeed(AppInfoEntity paramAppInfoEntity) {
    if (TmaFeatureConfigManager.getInstance().isTmgShowLoadingBgEnable() && !TextUtils.isEmpty(paramAppInfoEntity.loadingBg)) {
      c c = (new c(paramAppInfoEntity.loadingBg)).a((View)this.mLoadingBgImageView).a(new a() {
            public void onFail(Exception param1Exception) {
              AppBrandLogger.e("LaunchLoadingView", new Object[] { param1Exception });
            }
            
            public void onSuccess() {
              long l = LaunchLoadingView.this.mLoadStartTime.getMillisAfterStart();
              l = Math.max(0L, TmaFeatureConfigManager.getInstance().getShowLoadingBgDelayTime() - l);
              ThreadUtil.runOnUIThread(new Runnable() {
                    public void run() {
                      if (LaunchLoadingView.this.getVisibility() != 0)
                        return; 
                      AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
                      alphaAnimation.setDuration(500L);
                      LaunchLoadingView.this.mLoadingBgImageView.startAnimation((Animation)alphaAnimation);
                      LaunchLoadingView.this.mLoadingBgImageView.setVisibility(0);
                      LaunchLoadingView.this.mNameTv.setTextColor(-1);
                      LaunchLoadingView.this.mDownloadProgressTv.setTextColor(-1);
                      LoadStateManager.getIns().setLoadingBgState("define");
                    }
                  }l);
            }
          });
      HostDependManager.getInst().loadImage(getContext(), c);
    } 
  }
  
  public void updateProgressTv(final int visibility, final int progress) {
    if (this.isShowingError)
      return; 
    if (!this.mUsingHostLoading)
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              if (LaunchLoadingView.this.isShowingError)
                return; 
              LaunchLoadingView.this.mDownloadProgressTv.setVisibility(visibility);
              TextView textView = LaunchLoadingView.this.mDownloadProgressTv;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(progress);
              stringBuilder.append("%");
              textView.setText(stringBuilder.toString());
            }
          }); 
  }
  
  public void updateViews(final AppInfoEntity appInfo) {
    if (!this.mUsingHostLoading)
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              c c;
              GradientDrawable gradientDrawable = new GradientDrawable();
              gradientDrawable.setColor(-723466);
              float f1 = NativeUIParamsEntity.getInst().getMicroAppLogoCornerRadiusRatio();
              float f2 = LaunchLoadingView.this.mIconImg.getHeight();
              LaunchLoadingView.this.mIconImg.setCornerRadius(f1 * f2);
              if (!TextUtils.isEmpty(appInfo.icon)) {
                c = (new c(appInfo.icon)).a((Drawable)gradientDrawable).a((View)LaunchLoadingView.this.mIconImg);
                HostDependManager.getInst().loadImage(LaunchLoadingView.this.getContext(), c);
              } else if (LaunchLoadingView.this.mIconImg.getDrawable() == null) {
                LaunchLoadingView.this.mIconImg.setImageDrawable((Drawable)c);
              } 
              if (!TextUtils.isEmpty(appInfo.appName))
                LaunchLoadingView.this.mNameTv.setText(appInfo.appName); 
              if (TitleBarHelper.isBackButtonStyle()) {
                LaunchLoadingView.this.mLoadingTitleBar.findViewById(2097545356).setVisibility(0);
                LaunchLoadingView.this.mLoadingTitleBar.findViewById(2097545404).setVisibility(4);
              } 
              LaunchLoadingView.this.showHostTipIfNeed(appInfo);
              LaunchLoadingView.this.showLoadingBgIfNeed(appInfo);
            }
          }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\LaunchLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */